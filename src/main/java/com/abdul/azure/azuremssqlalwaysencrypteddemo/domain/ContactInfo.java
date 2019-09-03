package com.abdul.azure.azuremssqlalwaysencrypteddemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ContactInfo {
	
	

	 @Id
	 @GeneratedValue
	 private String id;
	 
	 private String email;
	 
	 private String phone;
	 
	 private String ssn;
	 
	 public ContactInfo(String id, String email, String phone, String ssn) {
			super();
			this.id = id;
			this.email = email;
			this.phone = phone;
			this.ssn = ssn;
		}

	public ContactInfo() {
	 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", phone=" + phone + ", ssn=" + ssn + "]";
	}

}
