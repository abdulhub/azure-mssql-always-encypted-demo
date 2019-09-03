package com.abdul.azure.azuremssqlalwaysencrypteddemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.azure.azuremssqlalwaysencrypteddemo.domain.ContactInfo;

 

 

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	
	
	 private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	private CrudRepository<ContactInfo, String> repository;

    @Autowired
    public CustomerController(CrudRepository<ContactInfo, String> repository) {
        this.repository = repository;
    }
    
    
    @GetMapping 
    public Iterable<ContactInfo> customers() {
    	 logger.info("Getting customers " );
        return repository.findAll();
    }
    
    
    @GetMapping(value = "/{id}")
    public ContactInfo getById(@PathVariable String id) {
        logger.info("Getting customer " + id);
        return repository.findById(id).orElse(null);
    }
    
    

}
