package com.netkiller.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Contact implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String firstName;

	@Persistent
	private String lastName;

	@Persistent
	private String fullName;

	@Persistent
	private String cmpnyName;

	@Persistent
	private String cmpnyTitle;

	@Persistent
	private String cmpnyDepartment;

	@Persistent
	private String workEmail;

	@Persistent
	private String homeEmail;

	@Persistent
	private String otherEmail;

	@Persistent
	private String workPhone;

	@Persistent
	private String homePhone;

	@Persistent
	private String mobileNumber;

	@Persistent
	private String workAddress;

	@Persistent
	private String homeAddress;

	@Persistent
	private String otherAddress;

	@Persistent
	private String notes;

	@Persistent
	private Boolean isDeleted = false;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCmpnyName() {
		return cmpnyName;
	}

	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}

	public String getCmpnyTitle() {
		return cmpnyTitle;
	}

	public void setCmpnyTitle(String cmpnyTitle) {
		this.cmpnyTitle = cmpnyTitle;
	}

	public String getCmpnyDepartment() {
		return cmpnyDepartment;
	}

	public void setCmpnyDepartment(String cmpnyDepartment) {
		this.cmpnyDepartment = cmpnyDepartment;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getHomeEmail() {
		return homeEmail;
	}

	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}

	public String getOtherEmail() {
		return otherEmail;
	}

	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getOtherAddress() {
		return otherAddress;
	}

	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Contacts [key=" + key + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", fullName=" + fullName
				+ ", cmpnyName=" + cmpnyName + ", cmpnyTitle=" + cmpnyTitle
				+ ", cmpnyDepartment=" + cmpnyDepartment + ", workEmail="
				+ workEmail + ", homeEmail=" + homeEmail + ", otherEmail="
				+ otherEmail + ", workPhone=" + workPhone + ", homePhone="
				+ homePhone + ", mobileNumber=" + mobileNumber
				+ ", workAddress=" + workAddress + ", homeAddress="
				+ homeAddress + ", otherAddress=" + otherAddress + ", notes="
				+ notes + ", isDeleted=" + isDeleted + ", lastModifiedDate="
				+ lastModifiedDate + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + "]";
	}

}
