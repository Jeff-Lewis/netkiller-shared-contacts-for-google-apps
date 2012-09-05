package com.netkiller.entity;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class DomainAdmin {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key accountTypeKey;

	@Persistent
	private Value accountTypeValue;

	@Persistent
	private String domainName;

	@Persistent
	private String adminEmail;

	@Persistent
	private Integer totalCounts;

	@Persistent
	private Date registeredDate;

	@Override
	public String toString() {
		return "DomainAdmin [key=" + key + ", accountTypeKey=" + accountTypeKey
				+ ", accountTypeValue=" + accountTypeValue + ", domainName="
				+ domainName + ", adminEmail=" + adminEmail + ", totalCounts="
				+ totalCounts + ", registeredDate=" + registeredDate + "]";
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getAccountTypeKey() {
		return accountTypeKey;
	}

	public void setAccountTypeKey(Key accountTypeKey) {
		this.accountTypeKey = accountTypeKey;
	}

	public Value getAccountTypeValue() {
		return accountTypeValue;
	}

	public void setAccountTypeValue(Value accountTypeValue) {
		this.accountTypeValue = accountTypeValue;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public Integer getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(Integer totalCounts) {
		this.totalCounts = totalCounts;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
}
