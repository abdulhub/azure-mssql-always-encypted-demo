package com.abdul.azure.azuremssqlalwaysencrypteddemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdul.azure.azuremssqlalwaysencrypteddemo.domain.ContactInfo;

 

@Repository
public interface CustomerRepository extends JpaRepository<ContactInfo, String> { 

}
