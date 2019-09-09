# Create a column master key in Azure Key Vault and encrypt the column

# Create a column master key in Azure Key Vault and encrypt the column

# Azure subscription and service princial settings 
$applicationId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxx";
$secret = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
$tenantId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
$SubscriptionId = "xxxxxxxxxxxxxxxxxxxxxx"

# Azure Key Vault settings
$akvName = "stepcount"
$akvKeyName = "PoshKey1"

# Column Encryption Key and Master key Name in the DB
$cekName = "CEKPosh1"
$cmkName = "CMKPosh1"

$col1 = "dbo.testtable.phone"
$col2 = "dbo.testtable.ssn"


# Connect to your database (Azure SQL database).
$serverName = "xxxxxxxxxxxx"
$databaseName = "testdb"
$dbpassword = "abduladmin"
$dbuser = "abdul"
$sqlConnectionString = "Data Source=$servername;Initial Catalog=$databasename;User ID=$dbuser;Password=$dbpassword;MultipleActiveResultSets=False;Connect Timeout=30;Encrypt=True;TrustServerCertificate=False;Column Encryption Setting=Enabled"
$connection = New-Object Microsoft.SqlServer.Management.Common.ServerConnection
$connection.ConnectionString = $connStr
$connection.Connect()
$server = New-Object Microsoft.SqlServer.Management.Smo.Server($connection)
$database = $server.Databases[$databaseName] 
$database.CreateDate

  
# Login into Azure Subsription
$securePassword = $secret | ConvertTo-SecureString -AsPlainText -Force
$credential = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $applicationId, $securePassword
Connect-AzureRmAccount -ServicePrincipal  -Credential $credential -TenantId $tenantId

# Sets the context for the below cmdlets to the specified subscription.
$azureCtx = Set-AzureRMConteXt -SubscriptionId $SubscriptionId
# Create a key in the Vault
$akvKey = Add-AzureKeyVaultKey -VaultName $akvName -Name $akvKeyName -Destination "Software"



# Create a SqlColumnMasterKeySettings object for your column master key. 
$cmkSettings = New-SqlAzureKeyVaultColumnMasterKeySettings -KeyURL $akvKey.ID 

# Create column master key metadata in the database.
New-SqlColumnMasterKey -Name $cmkName -InputObject $database -ColumnMasterKeySettings $cmkSettings



# Authenticate to Azure (in this example using same credential as login)
#   * Enter a Client ID, Secret, and Tenant ID:
Add-SqlAzureAuthenticationContext -ClientID $applicationId -Secret $secret -Tenant $tenantId


# Generate a column encryption key, encrypt it with the column master key and create column encryption key metadata in the database. 
New-SqlColumnEncryptionKey -Name $cekName -InputObject $database -ColumnMasterKey $cmkName

# Change encryption schema
$encryptionChanges = @()

# Add changes for table [dbo].[artist]
$encryptionChanges += New-SqlColumnEncryptionSettings -ColumnName $col1     -EncryptionType Deterministic -EncryptionKey $cekName
Set-SqlColumnEncryption -ColumnEncryptionSettings $encryptionChanges -InputObject $database
 
##https://stackoverflow.com/questions/25682703/connect-to-sql-server-database-from-powershell
#https://docs.microsoft.com/en-us/sql/relational-databases/security/encryption/configure-always-encrypted-using-powershell?view=sql-server-2017
##https://docs.microsoft.com/en-us/sql/powershell/download-sql-server-ps-module?view=sql-server-2017
 
