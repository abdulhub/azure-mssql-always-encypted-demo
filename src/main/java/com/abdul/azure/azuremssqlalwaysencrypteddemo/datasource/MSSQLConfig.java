package com.abdul.azure.azuremssqlalwaysencrypteddemo.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider;
import com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionKeyStoreProvider;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;

 

 

@Configuration
public class MSSQLConfig {
	
	
	    @Value("${azure.vault.clientId}")
	    private String clientId;
	    @Value("${azure.vault.clientSecret}")
	    private String clientSecret;
	    
	    @Value("${spring.datasource.username}")
	    private String dbUser;
	    @Value("${spring.datasource.password}")
	    private String dbPassword;
	    
	    @Value("${spring.datasource.driver-class-name}")
	    private String driverClass;
	    
	    @Value("${spring.datasource.url}")
	    private String url;
	    
	    
	    @Bean
	    @Primary
	    public DataSource dataSource() throws SQLException { 
	    	
	    	DataSource dataSource = DataSourceBuilder
	                .create()
	                .username(dbUser)
	                .password(dbPassword)
	                .url(url)
	                .driverClassName(driverClass)
	                .build();
	    	
	    	
	    	SQLServerColumnEncryptionAzureKeyVaultProvider akvProvider =
                    new SQLServerColumnEncryptionAzureKeyVaultProvider(clientId, clientSecret);

            Map<String, SQLServerColumnEncryptionKeyStoreProvider> keyStoreMap = new HashMap<String, SQLServerColumnEncryptionKeyStoreProvider>();
            keyStoreMap.put(akvProvider.getName(), akvProvider);

            SQLServerConnection.registerColumnEncryptionKeyStoreProviders(keyStoreMap);

	    	
	    	
			return dataSource;

	    	
	    }
	
	
}
