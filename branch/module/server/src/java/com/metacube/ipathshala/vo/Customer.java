package com.metacube.ipathshala.vo;

/**
 * 
 */

import java.util.Date;

/**
 * @author Harish
 * 
 */
public class Customer {

	private Long Id;
	private String domain;
	private String adminEmail;
	private String accountType;
	private Date registeredDate;
	private Date upgradedDate;
	private Integer totalContacts;

	public Customer() {
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public Date getUpgradedDate() {
		return upgradedDate;
	}

	public void setUpgradedDate(Date upgradedDate) {
		this.upgradedDate = upgradedDate;
	}

	public Integer getTotalContacts() {
		return totalContacts;
	}

	public void setTotalContacts(Integer totalContacts) {
		this.totalContacts = totalContacts;
	}

}
