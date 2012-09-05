/**
 * 
 */
package com.netkiller.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

/**
 * Teacher entity.
 * 
 * @author knagar
 * 
 */

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Teacher implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String firstName;

	@Persistent
	private String firstNameUpperCase;

	@Persistent
	private String middleName;

	@Persistent
	private String lastName;

	@Persistent
	private String qualification;

	@Persistent
	private Date dateJoined;

	@Persistent
	private Date dateRelieved;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

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

	/**
	 * @return the dateRelieved
	 */
	public Date getDateRelieved() {
		return dateRelieved;
	}

	/**
	 * @param dateRelieved
	 *            the dateRelieved to set
	 */
	public void setDateRelieved(Date dateRelieved) {
		this.dateRelieved = dateRelieved;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	/**
	 * @return the employeeId
	 */
	@Persistent
	private Key genderKey;

	@NotPersistent
	private Value genderValue;

	public Key getGenderKey() {
		return genderKey;
	}

	public void setGenderKey(Key genderKey) {
		this.genderKey = genderKey;
	}

	public Value getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(Value genderValue) {
		this.genderValue = genderValue;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Persistent
	private String employeeId;

	@Persistent
	private String landlineNumberCode;

	@Persistent
	private String landlineNumber;

	@Persistent
	private String cellNumberCode;

	@Persistent
	private String cellNumber;

	@Persistent
	private Key teachingLevelKey;

	@Persistent
	private String presentAddressLine1;

	@Persistent
	private String presentAddressLine2;

	@Persistent
	private Key presentAddressCityKey;

	@Persistent
	private String presentAddressPincode;

	@Persistent
	private Key presentAddressStateKey;

	@Persistent
	private Key presentAddressCountryKey;

	@Persistent
	private String permanentAddressLine1;

	@Persistent
	private String permanentAddressLine2;

	@Persistent
	private Key permanentAddressCityKey;

	@Persistent
	private String permanentAddressAlternateCity;

	@Persistent
	private String presentAddressAlternateCity;

	@Persistent
	private String permanentAddressPincode;

	@Persistent
	private Key permanentAddressStateKey;

	@Persistent
	private Key permanentAddressCountryKey;

	@Persistent
	private String userId;

	@Persistent
	private String password;

	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;
	
	@Persistent
	private Key titleKey;
	
	@NotPersistent
	private Value titleValue;
	
	@Persistent
	private String designation;
	
	@Persistent
	private String pan;
	
	@Persistent
	private Key bloodGroupKey;
	
	@NotPersistent
	private Value bloodGroupValue;
	
	@Persistent
	private Date dateOfBirth;
	
	@Persistent
	private String userDefinedStringField1;
	
	@Persistent
	private String userDefinedStringField2;
	
	@Persistent
	private String userDefinedStringField3;
	
	@Persistent
	private Date userDefinedDateField1;

	@Persistent
	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the permanentAddressAlternateCity
	 */
	public String getPermanentAddressAlternateCity() {
		return permanentAddressAlternateCity;
	}

	/**
	 * @param permanentAddressAlternateCity
	 *            the permanentAddressAlternateCity to set
	 */
	public void setPermanentAddressAlternateCity(
			String permanentAddressAlternateCity) {
		this.permanentAddressAlternateCity = permanentAddressAlternateCity;
	}

	/**
	 * @return the presentAddressAlternateCity
	 */
	public String getPresentAddressAlternateCity() {
		return presentAddressAlternateCity;
	}

	/**
	 * @param presentAddressAlternateCity
	 *            the presentAddressAlternateCity to set
	 */
	public void setPresentAddressAlternateCity(
			String presentAddressAlternateCity) {
		this.presentAddressAlternateCity = presentAddressAlternateCity;
	}

	@Persistent
	private Boolean isDeleted = false;

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Persistent
	private Boolean active;

	@NotPersistent
	private Value teachingLevelValue;

	@NotPersistent
	private Value presentAddressCityValue;

	@NotPersistent
	private Value presentAddressStateValue;

	@NotPersistent
	private Value presentAddressCountryValue;

	@NotPersistent
	private Value permanentAddressCityValue;

	@NotPersistent
	private Value permanentAddressStateValue;

	@NotPersistent
	private Value permanentAddressCountryValue;

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the qualification
	 */
	public String getQualification() {
		return qualification;
	}

	/**
	 * @param qualification
	 *            the qualification to set
	 */
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	/**
	 * @return the landlineNumberCode
	 */
	public String getLandlineNumberCode() {
		return landlineNumberCode;
	}

	/**
	 * @param landlineNumberCode
	 *            the landlineNumberCode to set
	 */
	public void setLandlineNumberCode(String landlineNumberCode) {
		this.landlineNumberCode = landlineNumberCode;
	}

	/**
	 * @return the landlineNumber
	 */
	public String getLandlineNumber() {
		return landlineNumber;
	}

	/**
	 * @param landlineNumber
	 *            the landlineNumber to set
	 */
	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	/**
	 * @return the cellNumberCode
	 */
	public String getCellNumberCode() {
		return cellNumberCode;
	}

	/**
	 * @param cellNumberCode
	 *            the cellNumberCode to set
	 */
	public void setCellNumberCode(String cellNumberCode) {
		this.cellNumberCode = cellNumberCode;
	}

	/**
	 * @return the cellNumber
	 */
	public String getCellNumber() {
		return cellNumber;
	}

	/**
	 * @param cellNumber
	 *            the cellNumber to set
	 */
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	/**
	 * @return the presentAddressLine1
	 */
	public String getPresentAddressLine1() {
		return presentAddressLine1;
	}

	/**
	 * @param presentAddressLine1
	 *            the presentAddressLine1 to set
	 */
	public void setPresentAddressLine1(String presentAddressLine1) {
		this.presentAddressLine1 = presentAddressLine1;
	}

	/**
	 * @return the presentAddressLine2
	 */
	public String getPresentAddressLine2() {
		return presentAddressLine2;
	}

	/**
	 * @param presentAddressLine2
	 *            the presentAddressLine2 to set
	 */
	public void setPresentAddressLine2(String presentAddressLine2) {
		this.presentAddressLine2 = presentAddressLine2;
	}

	/**
	 * @return the presentAddressPincode
	 */
	public String getPresentAddressPincode() {
		return presentAddressPincode;
	}

	/**
	 * @param presentAddressPincode
	 *            the presentAddressPincode to set
	 */
	public void setPresentAddressPincode(String presentAddressPincode) {
		this.presentAddressPincode = presentAddressPincode;
	}

	/**
	 * @return the permanentAddressLine1
	 */
	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}

	/**
	 * @param permanentAddressLine1
	 *            the permanentAddressLine1 to set
	 */
	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}

	/**
	 * @return the permanentAddressLine2
	 */
	public String getPermanentAddressLine2() {
		return permanentAddressLine2;
	}

	/**
	 * @param permanentAddressLine2
	 *            the permanentAddressLine2 to set
	 */
	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}

	/**
	 * @return the permanentAddressPincode
	 */
	public String getPermanentAddressPincode() {
		return permanentAddressPincode;
	}

	/**
	 * @param permanentAddressPincode
	 *            the permanentAddressPincode to set
	 */
	public void setPermanentAddressPincode(String permanentAddressPincode) {
		this.permanentAddressPincode = permanentAddressPincode;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getFirstNameUpperCase() {
		return firstNameUpperCase;
	}

	public void setFirstNameUpperCase(String firstNameUpperCase) {
		this.firstNameUpperCase = firstNameUpperCase;
	}

	public Key getTeachingLevelKey() {
		return teachingLevelKey;
	}

	public void setTeachingLevelKey(Key teachingLevelKey) {
		this.teachingLevelKey = teachingLevelKey;
	}

	public Key getPresentAddressCityKey() {
		return presentAddressCityKey;
	}

	public void setPresentAddressCityKey(Key presentAddressCityKey) {
		this.presentAddressCityKey = presentAddressCityKey;
	}

	public Key getPresentAddressStateKey() {
		return presentAddressStateKey;
	}

	public void setPresentAddressStateKey(Key presentAddressStateKey) {
		this.presentAddressStateKey = presentAddressStateKey;
	}

	public Key getPresentAddressCountryKey() {
		return presentAddressCountryKey;
	}

	public void setPresentAddressCountryKey(Key presentAddressCountryKey) {
		this.presentAddressCountryKey = presentAddressCountryKey;
	}

	public Key getPermanentAddressCityKey() {
		return permanentAddressCityKey;
	}

	public void setPermanentAddressCityKey(Key permanentAddressCityKey) {
		this.permanentAddressCityKey = permanentAddressCityKey;
	}

	public Key getPermanentAddressStateKey() {
		return permanentAddressStateKey;
	}

	public void setPermanentAddressStateKey(Key permanentAddressStateKey) {
		this.permanentAddressStateKey = permanentAddressStateKey;
	}

	public Key getPermanentAddressCountryKey() {
		return permanentAddressCountryKey;
	}

	public void setPermanentAddressCountryKey(Key permanentAddressCountryKey) {
		this.permanentAddressCountryKey = permanentAddressCountryKey;
	}

	public Value getTeachingLevelValue() {
		return teachingLevelValue;
	}

	public void setTeachingLevelValue(Value teachingLevelValue) {
		this.teachingLevelValue = teachingLevelValue;
	}

	public Value getPresentAddressCityValue() {
		return presentAddressCityValue;
	}

	public void setPresentAddressCityValue(Value presentAddressCityValue) {
		this.presentAddressCityValue = presentAddressCityValue;
	}

	public Value getPresentAddressStateValue() {
		return presentAddressStateValue;
	}

	public void setPresentAddressStateValue(Value presentAddressStateValue) {
		this.presentAddressStateValue = presentAddressStateValue;
	}

	public Value getPresentAddressCountryValue() {
		return presentAddressCountryValue;
	}

	public void setPresentAddressCountryValue(Value presentAddressCountryValue) {
		this.presentAddressCountryValue = presentAddressCountryValue;
	}

	public Value getPermanentAddressCityValue() {
		return permanentAddressCityValue;
	}

	public void setPermanentAddressCityValue(Value permanentAddressCityValue) {
		this.permanentAddressCityValue = permanentAddressCityValue;
	}

	public Value getPermanentAddressStateValue() {
		return permanentAddressStateValue;
	}

	public void setPermanentAddressStateValue(Value permanentAddressStateValue) {
		this.permanentAddressStateValue = permanentAddressStateValue;
	}

	public Value getPermanentAddressCountryValue() {
		return permanentAddressCountryValue;
	}

	public void setPermanentAddressCountryValue(
			Value permanentAddressCountryValue) {
		this.permanentAddressCountryValue = permanentAddressCountryValue;
	}

	public Key getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(Key titleKey) {
		this.titleKey = titleKey;
	}

	public Value getTitleValue() {
		return titleValue;
	}

	public void setTitleValue(Value titleValue) {
		this.titleValue = titleValue;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Key getBloodGroupKey() {
		return bloodGroupKey;
	}

	public void setBloodGroupKey(Key bloodGroupKey) {
		this.bloodGroupKey = bloodGroupKey;
	}

	public Value getBloodGroupValue() {
		return bloodGroupValue;
	}

	public void setBloodGroupValue(Value bloodGroupValue) {
		this.bloodGroupValue = bloodGroupValue;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUserDefinedStringField1() {
		return userDefinedStringField1;
	}

	public void setUserDefinedStringField1(String userDefinedStringField1) {
		this.userDefinedStringField1 = userDefinedStringField1;
	}

	public String getUserDefinedStringField2() {
		return userDefinedStringField2;
	}

	public void setUserDefinedStringField2(String userDefinedStringField2) {
		this.userDefinedStringField2 = userDefinedStringField2;
	}

	public String getUserDefinedStringField3() {
		return userDefinedStringField3;
	}

	public void setUserDefinedStringField3(String userDefinedStringField3) {
		this.userDefinedStringField3 = userDefinedStringField3;
	}

	public Date getUserDefinedDateField1() {
		return userDefinedDateField1;
	}

	public void setUserDefinedDateField1(Date userDefinedDateField1) {
		this.userDefinedDateField1 = userDefinedDateField1;
	}

	@Override
	public void jdoPreStore() {
		StringBuilder stringBuilder = new StringBuilder();
		if (firstName != null || middleName != null || lastName != null) {
			if (firstName != null && !(firstName.isEmpty())) {
				stringBuilder.append(firstName.toUpperCase());
			}
			if (middleName != null && !(middleName.isEmpty())) {
				stringBuilder.append(" ");
				stringBuilder.append(middleName.toUpperCase());
			}
			if (lastName != null && !(lastName.isEmpty())) {
				stringBuilder.append(" ");
				stringBuilder.append(lastName.toUpperCase());
			}
			firstNameUpperCase = stringBuilder.toString();
		} else {
			firstNameUpperCase = null;
		}
	}

	@Override
	public String toString() {
		return "Teacher [key=" + key + ", firstName=" + firstName
				+ ", firstNameUpperCase=" + firstNameUpperCase
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", qualification=" + qualification + ", dateJoined="
				+ dateJoined + ", dateRelieved=" + dateRelieved
				+ ", genderKey=" + genderKey + ", genderValue=" + genderValue
				+ ", employeeId=" + employeeId + ", landlineNumberCode="
				+ landlineNumberCode + ", landlineNumber=" + landlineNumber
				+ ", cellNumberCode=" + cellNumberCode + ", cellNumber="
				+ cellNumber + ", teachingLevelKey=" + teachingLevelKey
				+ ", presentAddressLine1=" + presentAddressLine1
				+ ", presentAddressLine2=" + presentAddressLine2
				+ ", presentAddressCityKey=" + presentAddressCityKey
				+ ", presentAddressPincode=" + presentAddressPincode
				+ ", presentAddressStateKey=" + presentAddressStateKey
				+ ", presentAddressCountryKey=" + presentAddressCountryKey
				+ ", permanentAddressLine1=" + permanentAddressLine1
				+ ", permanentAddressLine2=" + permanentAddressLine2
				+ ", permanentAddressCityKey=" + permanentAddressCityKey
				+ ", permanentAddressAlternateCity="
				+ permanentAddressAlternateCity
				+ ", presentAddressAlternateCity="
				+ presentAddressAlternateCity + ", permanentAddressPincode="
				+ permanentAddressPincode + ", permanentAddressStateKey="
				+ permanentAddressStateKey + ", permanentAddressCountryKey="
				+ permanentAddressCountryKey + ", userId=" + userId
				+ ", password=" + password + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", titleKey=" + titleKey
				+ ", titleValue=" + titleValue + ", designation=" + designation
				+ ", pan=" + pan + ", bloodGroupKey=" + bloodGroupKey
				+ ", bloodGroupValue=" + bloodGroupValue + ", dateOfBirth="
				+ dateOfBirth + ", userDefinedStringField1="
				+ userDefinedStringField1 + ", userDefinedStringField2="
				+ userDefinedStringField2 + ", userDefinedStringField3="
				+ userDefinedStringField3 + ", userDefinedDateField1="
				+ userDefinedDateField1 + ", isDeleted=" + isDeleted
				+ ", active=" + active + ", teachingLevelValue="
				+ teachingLevelValue + ", presentAddressCityValue="
				+ presentAddressCityValue + ", presentAddressStateValue="
				+ presentAddressStateValue + ", presentAddressCountryValue="
				+ presentAddressCountryValue + ", permanentAddressCityValue="
				+ permanentAddressCityValue + ", permanentAddressStateValue="
				+ permanentAddressStateValue
				+ ", permanentAddressCountryValue="
				+ permanentAddressCountryValue + "]";
	}
}
