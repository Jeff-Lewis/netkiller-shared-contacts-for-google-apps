package com.netkiller.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.netkiller.cache.AppCache;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class School extends EntityCache implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String schoolName;

	@Persistent
	private String address;

	@Persistent
	private boolean isDeleted;

	@Persistent
	private String phoneNo;

	@Persistent
	private String faxNo;

	@Persistent
	private String email;

	@Persistent
	private String websiteName;

	@Persistent
	private String affiliationNo;
	

	@Persistent
	private String schoolNameUpperCase;

	public String getSchoolNameUpperCase() {
		return schoolNameUpperCase;
	}

	public void setSchoolNameUpperCase(String schoolNameUpperCase) {
		this.schoolNameUpperCase = schoolNameUpperCase;
	}

	@Override
	public String toString() {
		return "School [key=" + key + ", schoolName=" + schoolName
				+ ", address=" + address + ", isDeleted=" + isDeleted
				+ ", phoneNo=" + phoneNo + ", faxNo=" + faxNo + ", email="
				+ email + ", websiteName=" + websiteName + ", affiliationNo="
				+ affiliationNo + "]";
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getAffiliationNo() {
		return affiliationNo;
	}

	public void setAffiliationNo(String affiliationNo) {
		this.affiliationNo = affiliationNo;
	}

	@Override
	protected AppCache getEntityCache() {

		return null;
	}

	@Override
	protected void setDefaultColumn() {
		StringBuilder stringBuilder = new StringBuilder();

		if (schoolName != null && !(schoolName.isEmpty())) {
			stringBuilder.append(schoolName.toUpperCase());
			schoolNameUpperCase = stringBuilder.toString();
		}

		else {
			schoolNameUpperCase = null;
		}
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
