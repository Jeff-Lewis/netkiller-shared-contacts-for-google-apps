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
import javax.jdo.annotations.Unique;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;
import com.netkiller.cache.AppCache;
import com.netkiller.cache.impl.ParentCache;

/**
 * Parent entity.
 * 
 * @author knagar
 * 
 */

/**
 * detachable=true acts as an instruction to DataNeucleus enhancement process to
 * add methods necessary to utilise the attach/detach process. Its required to
 * have it for entities which are to be used as 'detached' entities. Remember
 * that, a) Calling detachCopy on an object that is not detachable will return a
 * transient instance that is a COPY of the original, so use the COPY
 * thereafter. b) Calling detachCopy on an object that is detachable will return
 * a detached instance that is a COPY of the original, so use this COPY
 * thereafter
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Parent extends EntityCache implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String fatherFirstName;

	@Persistent
	private String fatherFirstNameUpperCase;
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Persistent
	private Boolean active;

	@Persistent
	private String fatherMiddleName;

	@Persistent
	private String fatherLastName;

	@Persistent
	private String motherFirstName;

	@Persistent
	private String motherMiddleName;

	@Persistent
	private String password;

	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;

	@Persistent
	private String userDefinedStringField1;

	@Persistent
	private String userDefinedStringField2;

	@Persistent
	private String userDefinedStringField3;

	@Persistent
	private Date userDefinedDateField1;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
	@Persistent
	private String fatherDesignation;
	
	@Persistent
	private String motherDesignation;
	
	

	public String getFatherDesignation() {
		return fatherDesignation;
	}

	public void setFatherDesignation(String fatherDesignation) {
		this.fatherDesignation = fatherDesignation;
	}

	public String getMotherDesignation() {
		return motherDesignation;
	}

	public void setMotherDesignation(String motherDesignation) {
		this.motherDesignation = motherDesignation;
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

	@Persistent
	private String motherLastName;

	@Unique
	@Persistent
	private String fatherOfficeLandlineNumberCode;

	@Persistent
	private String fatherOfficeLandlineNumber;

	@Persistent
	private String motherOfficeLandlineNumberCode;

	@Persistent
	private String motherOfficeLandlineNumber;

	@Persistent
	private String fatherCellphoneNumberCode;

	@Persistent
	private String fatherCellphoneNumber;

	@Persistent
	private String motherCellphoneNumberCode;

	@Persistent
	private String motherCellphoneNumber;

	@Persistent
	private String fatherEmailAddress;

	@Persistent
	private String motherEmailAddress;

	@Persistent
	private Key fatherOccupation;

	@Persistent
	private Key motherOccupation;

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

	@NotPersistent
	private Value presentAddressCityValue;

	@NotPersistent
	private Value presentAddressStateValue;

	@NotPersistent
	private Value presentAddressCountryValue;

	@Persistent
	private String presentAddressAlternateCity;

	// Permanent address information
	@Persistent
	private String permanentAddressLine1;

	@Persistent
	private String permanentAddressLine2;

	@Persistent
	private Key permanentAddressCityKey;

	@Persistent
	private Key permanentAddressStateKey;

	@Persistent
	private Key permanentAddressCountryKey;

	@NotPersistent
	private Value permanentAddressCityValue;

	@Persistent
	private String permanentAddressAlternateCity;

	@NotPersistent
	private Value permanentAddressStateValue;

	@NotPersistent
	private Value permanentAddressCountryValue;

	@Persistent
	private String permanentAddressPincode;

	/**
	 * A boolean value to specify that father is primary contact or not
	 */
	@Persistent
	private boolean isFatherPrimaryContact = true;

	// parent office information
	@Persistent
	private String fatherOfficeOrganizationName;

	@Persistent
	private String motherOfficeOrganizationName;

	/**
	 * The home landline number
	 */
	@Persistent
	private String homeLandlineNumberCode;

	@Persistent
	private String homeLandlineNumber;

	@Persistent
	private String userId;

	@NotPersistent
	private Value fatherOccupationValue;

	@NotPersistent
	private Value motherOccupationValue;

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
	 * @return the fatherFirstName
	 */
	public String getFatherFirstName() {
		return fatherFirstName;
	}

	/**
	 * @param fatherFirstName
	 *            the fatherFirstName to set
	 */
	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	/**
	 * @return the fatherFirstNameUpperCase
	 */
	public String getFatherFirstNameUpperCase() {
		return fatherFirstNameUpperCase;
	}

	/**
	 * @param fatherFirstNameUpperCase
	 *            the fatherFirstNameUpperCase to set
	 */
	public void setFatherFirstNameUpperCase(String fatherFirstNameUpperCase) {
		this.fatherFirstNameUpperCase = fatherFirstNameUpperCase;
	}

	/**
	 * @return the fatherMiddleName
	 */
	public String getFatherMiddleName() {
		return fatherMiddleName;
	}

	/**
	 * @param fatherMiddleName
	 *            the fatherMiddleName to set
	 */
	public void setFatherMiddleName(String fatherMiddleName) {
		this.fatherMiddleName = fatherMiddleName;
	}

	/**
	 * @return the fatherLastName
	 */
	public String getFatherLastName() {
		return fatherLastName;
	}

	/**
	 * @param fatherLastName
	 *            the fatherLastName to set
	 */
	public void setFatherLastName(String fatherLastName) {
		this.fatherLastName = fatherLastName;
	}

	/**
	 * @return the motherFirstName
	 */
	public String getMotherFirstName() {
		return motherFirstName;
	}

	/**
	 * @param motherFirstName
	 *            the motherFirstName to set
	 */
	public void setMotherFirstName(String motherFirstName) {
		this.motherFirstName = motherFirstName;
	}

	/**
	 * @return the motherMiddleName
	 */
	public String getMotherMiddleName() {
		return motherMiddleName;
	}

	/**
	 * @param motherMiddleName
	 *            the motherMiddleName to set
	 */
	public void setMotherMiddleName(String motherMiddleName) {
		this.motherMiddleName = motherMiddleName;
	}

	/**
	 * @return the motherLastName
	 */
	public String getMotherLastName() {
		return motherLastName;
	}

	/**
	 * @param motherLastName
	 *            the motherLastName to set
	 */
	public void setMotherLastName(String motherLastName) {
		this.motherLastName = motherLastName;
	}

	/**
	 * @return the fatherOfficeLandlineNumberCode
	 */
	public String getFatherOfficeLandlineNumberCode() {
		return fatherOfficeLandlineNumberCode;
	}

	/**
	 * @param fatherOfficeLandlineNumberCode
	 *            the fatherOfficeLandlineNumberCode to set
	 */
	public void setFatherOfficeLandlineNumberCode(
			String fatherOfficeLandlineNumberCode) {
		this.fatherOfficeLandlineNumberCode = fatherOfficeLandlineNumberCode;
	}

	/**
	 * @return the fatherOfficeLandlineNumber
	 */
	public String getFatherOfficeLandlineNumber() {
		return fatherOfficeLandlineNumber;
	}

	/**
	 * @param fatherOfficeLandlineNumber
	 *            the fatherOfficeLandlineNumber to set
	 */
	public void setFatherOfficeLandlineNumber(String fatherOfficeLandlineNumber) {
		this.fatherOfficeLandlineNumber = fatherOfficeLandlineNumber;
	}

	/**
	 * @return the motherOfficeLandlineNumberCode
	 */
	public String getMotherOfficeLandlineNumberCode() {
		return motherOfficeLandlineNumberCode;
	}

	/**
	 * @param motherOfficeLandlineNumberCode
	 *            the motherOfficeLandlineNumberCode to set
	 */
	public void setMotherOfficeLandlineNumberCode(
			String motherOfficeLandlineNumberCode) {
		this.motherOfficeLandlineNumberCode = motherOfficeLandlineNumberCode;
	}

	/**
	 * @return the motherOfficeLandlineNumber
	 */
	public String getMotherOfficeLandlineNumber() {
		return motherOfficeLandlineNumber;
	}

	/**
	 * @param motherOfficeLandlineNumber
	 *            the motherOfficeLandlineNumber to set
	 */
	public void setMotherOfficeLandlineNumber(String motherOfficeLandlineNumber) {
		this.motherOfficeLandlineNumber = motherOfficeLandlineNumber;
	}

	/**
	 * @return the fatherCellphoneNumberCode
	 */
	public String getFatherCellphoneNumberCode() {
		return fatherCellphoneNumberCode;
	}

	/**
	 * @param fatherCellphoneNumberCode
	 *            the fatherCellphoneNumberCode to set
	 */
	public void setFatherCellphoneNumberCode(String fatherCellphoneNumberCode) {
		this.fatherCellphoneNumberCode = fatherCellphoneNumberCode;
	}

	/**
	 * @return the fatherCellphoneNumber
	 */
	public String getFatherCellphoneNumber() {
		return fatherCellphoneNumber;
	}

	/**
	 * @param fatherCellphoneNumber
	 *            the fatherCellphoneNumber to set
	 */
	public void setFatherCellphoneNumber(String fatherCellphoneNumber) {
		this.fatherCellphoneNumber = fatherCellphoneNumber;
	}

	/**
	 * @return the motherCellphoneNumberCode
	 */
	public String getMotherCellphoneNumberCode() {
		return motherCellphoneNumberCode;
	}

	/**
	 * @param motherCellphoneNumberCode
	 *            the motherCellphoneNumberCode to set
	 */
	public void setMotherCellphoneNumberCode(String motherCellphoneNumberCode) {
		this.motherCellphoneNumberCode = motherCellphoneNumberCode;
	}

	/**
	 * @return the motherCellphoneNumber
	 */
	public String getMotherCellphoneNumber() {
		return motherCellphoneNumber;
	}

	/**
	 * @param motherCellphoneNumber
	 *            the motherCellphoneNumber to set
	 */
	public void setMotherCellphoneNumber(String motherCellphoneNumber) {
		this.motherCellphoneNumber = motherCellphoneNumber;
	}

	/**
	 * @return the fatherEmailAddress
	 */
	public String getFatherEmailAddress() {
		return fatherEmailAddress;
	}

	/**
	 * @param fatherEmailAddress
	 *            the fatherEmailAddress to set
	 */
	public void setFatherEmailAddress(String fatherEmailAddress) {
		this.fatherEmailAddress = fatherEmailAddress;
	}

	/**
	 * @return the motherEmailAddress
	 */
	public String getMotherEmailAddress() {
		return motherEmailAddress;
	}

	/**
	 * @param motherEmailAddress
	 *            the motherEmailAddress to set
	 */
	public void setMotherEmailAddress(String motherEmailAddress) {
		this.motherEmailAddress = motherEmailAddress;
	}

	/**
	 * @return the fatherOccupation
	 */
	public Key getFatherOccupation() {
		return fatherOccupation;
	}

	/**
	 * @param fatherOccupation
	 *            the fatherOccupation to set
	 */
	public void setFatherOccupation(Key fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	/**
	 * @return the motherOccupation
	 */
	public Key getMotherOccupation() {
		return motherOccupation;
	}

	/**
	 * @param motherOccupation
	 *            the motherOccupation to set
	 */
	public void setMotherOccupation(Key motherOccupation) {
		this.motherOccupation = motherOccupation;
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
	 * @return the presentAddressCityKey
	 */
	public Key getPresentAddressCityKey() {
		return presentAddressCityKey;
	}

	/**
	 * @param presentAddressCityKey
	 *            the presentAddressCityKey to set
	 */
	public void setPresentAddressCityKey(Key presentAddressCityKey) {
		this.presentAddressCityKey = presentAddressCityKey;
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
	 * @return the presentAddressStateKey
	 */
	public Key getPresentAddressStateKey() {
		return presentAddressStateKey;
	}

	/**
	 * @param presentAddressStateKey
	 *            the presentAddressStateKey to set
	 */
	public void setPresentAddressStateKey(Key presentAddressStateKey) {
		this.presentAddressStateKey = presentAddressStateKey;
	}

	/**
	 * @return the presentAddressCountryKey
	 */
	public Key getPresentAddressCountryKey() {
		return presentAddressCountryKey;
	}

	/**
	 * @param presentAddressCountryKey
	 *            the presentAddressCountryKey to set
	 */
	public void setPresentAddressCountryKey(Key presentAddressCountryKey) {
		this.presentAddressCountryKey = presentAddressCountryKey;
	}

	/**
	 * @return the presentAddressCityValue
	 */
	public Value getPresentAddressCityValue() {
		return presentAddressCityValue;
	}

	/**
	 * @param presentAddressCityValue
	 *            the presentAddressCityValue to set
	 */
	public void setPresentAddressCityValue(Value presentAddressCityValue) {
		this.presentAddressCityValue = presentAddressCityValue;
	}

	/**
	 * @return the presentAddressStateValue
	 */
	public Value getPresentAddressStateValue() {
		return presentAddressStateValue;
	}

	/**
	 * @param presentAddressStateValue
	 *            the presentAddressStateValue to set
	 */
	public void setPresentAddressStateValue(Value presentAddressStateValue) {
		this.presentAddressStateValue = presentAddressStateValue;
	}

	/**
	 * @return the presentAddressCountryValue
	 */
	public Value getPresentAddressCountryValue() {
		return presentAddressCountryValue;
	}

	/**
	 * @param presentAddressCountryValue
	 *            the presentAddressCountryValue to set
	 */
	public void setPresentAddressCountryValue(Value presentAddressCountryValue) {
		this.presentAddressCountryValue = presentAddressCountryValue;
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
	 * @return the permanentAddressCityKey
	 */
	public Key getPermanentAddressCityKey() {
		return permanentAddressCityKey;
	}

	/**
	 * @param permanentAddressCityKey
	 *            the permanentAddressCityKey to set
	 */
	public void setPermanentAddressCityKey(Key permanentAddressCityKey) {
		this.permanentAddressCityKey = permanentAddressCityKey;
	}

	/**
	 * @return the permanentAddressStateKey
	 */
	public Key getPermanentAddressStateKey() {
		return permanentAddressStateKey;
	}

	/**
	 * @param permanentAddressStateKey
	 *            the permanentAddressStateKey to set
	 */
	public void setPermanentAddressStateKey(Key permanentAddressStateKey) {
		this.permanentAddressStateKey = permanentAddressStateKey;
	}

	/**
	 * @return the permanentAddressCountryKey
	 */
	public Key getPermanentAddressCountryKey() {
		return permanentAddressCountryKey;
	}

	/**
	 * @param permanentAddressCountryKey
	 *            the permanentAddressCountryKey to set
	 */
	public void setPermanentAddressCountryKey(Key permanentAddressCountryKey) {
		this.permanentAddressCountryKey = permanentAddressCountryKey;
	}

	/**
	 * @return the permanentAddressCityValue
	 */
	public Value getPermanentAddressCityValue() {
		return permanentAddressCityValue;
	}

	/**
	 * @param permanentAddressCityValue
	 *            the permanentAddressCityValue to set
	 */
	public void setPermanentAddressCityValue(Value permanentAddressCityValue) {
		this.permanentAddressCityValue = permanentAddressCityValue;
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
	 * @return the permanentAddressStateValue
	 */
	public Value getPermanentAddressStateValue() {
		return permanentAddressStateValue;
	}

	/**
	 * @param permanentAddressStateValue
	 *            the permanentAddressStateValue to set
	 */
	public void setPermanentAddressStateValue(Value permanentAddressStateValue) {
		this.permanentAddressStateValue = permanentAddressStateValue;
	}

	/**
	 * @return the permanentAddressCountryValue
	 */
	public Value getPermanentAddressCountryValue() {
		return permanentAddressCountryValue;
	}

	/**
	 * @param permanentAddressCountryValue
	 *            the permanentAddressCountryValue to set
	 */
	public void setPermanentAddressCountryValue(
			Value permanentAddressCountryValue) {
		this.permanentAddressCountryValue = permanentAddressCountryValue;
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
	 * @return the isFatherPrimaryContact
	 */
	public boolean getIsFatherPrimaryContact() {
		return isFatherPrimaryContact;
	}

	/**
	 * @param isFatherPrimaryContact
	 *            the isFatherPrimaryContact to set
	 */
	public void setIsFatherPrimaryContact(boolean isFatherPrimaryContact) {
		this.isFatherPrimaryContact = isFatherPrimaryContact;
	}

	/**
	 * @return the fatherOfficeOrganizationName
	 */
	public String getFatherOfficeOrganizationName() {
		return fatherOfficeOrganizationName;
	}

	/**
	 * @param fatherOfficeOrganizationName
	 *            the fatherOfficeOrganizationName to set
	 */
	public void setFatherOfficeOrganizationName(
			String fatherOfficeOrganizationName) {
		this.fatherOfficeOrganizationName = fatherOfficeOrganizationName;
	}

	/**
	 * @return the motherOfficeOrganizationName
	 */
	public String getMotherOfficeOrganizationName() {
		return motherOfficeOrganizationName;
	}

	/**
	 * @param motherOfficeOrganizationName
	 *            the motherOfficeOrganizationName to set
	 */
	public void setMotherOfficeOrganizationName(
			String motherOfficeOrganizationName) {
		this.motherOfficeOrganizationName = motherOfficeOrganizationName;
	}

	/**
	 * @return the homeLandlineNumberCode
	 */
	public String getHomeLandlineNumberCode() {
		return homeLandlineNumberCode;
	}

	/**
	 * @param homeLandlineNumberCode
	 *            the homeLandlineNumberCode to set
	 */
	public void setHomeLandlineNumberCode(String homeLandlineNumberCode) {
		this.homeLandlineNumberCode = homeLandlineNumberCode;
	}

	/**
	 * @return the homeLandlineNumber
	 */
	public String getHomeLandlineNumber() {
		return homeLandlineNumber;
	}

	/**
	 * @param homeLandlineNumber
	 *            the homeLandlineNumber to set
	 */
	public void setHomeLandlineNumber(String homeLandlineNumber) {
		this.homeLandlineNumber = homeLandlineNumber;
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

	/**
	 * @return the fatherOccupationValue
	 */
	public Value getFatherOccupationValue() {
		return fatherOccupationValue;
	}

	/**
	 * @param fatherOccupationValue
	 *            the fatherOccupationValue to set
	 */
	public void setFatherOccupationValue(Value fatherOccupationValue) {
		this.fatherOccupationValue = fatherOccupationValue;
	}

	/**
	 * @return the motherOccupationValue
	 */
	public Value getMotherOccupationValue() {
		return motherOccupationValue;
	}

	/**
	 * @param motherOccupationValue
	 *            the motherOccupationValue to set
	 */
	public void setMotherOccupationValue(Value motherOccupationValue) {
		this.motherOccupationValue = motherOccupationValue;
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

	public void setFatherPrimaryContact(boolean isFatherPrimaryContact) {
		this.isFatherPrimaryContact = isFatherPrimaryContact;
	}

	@Override
	public String toString() {
		return "Parent [key=" + key + ", fatherFirstName=" + fatherFirstName
				+ ", fatherFirstNameUpperCase=" + fatherFirstNameUpperCase
				+ ", fatherMiddleName=" + fatherMiddleName
				+ ", fatherLastName=" + fatherLastName + ", motherFirstName="
				+ motherFirstName + ", motherMiddleName=" + motherMiddleName
				+ ", password=" + password + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", userDefinedStringField1="
				+ userDefinedStringField1 + ", userDefinedStringField2="
				+ userDefinedStringField2 + ", userDefinedStringField3="
				+ userDefinedStringField3 + ", userDefinedDateField1="
				+ userDefinedDateField1 + ", motherLastName=" + motherLastName
				+ ", fatherOfficeLandlineNumberCode="
				+ fatherOfficeLandlineNumberCode
				+ ", fatherOfficeLandlineNumber=" + fatherOfficeLandlineNumber
				+ ", motherOfficeLandlineNumberCode="
				+ motherOfficeLandlineNumberCode
				+ ", motherOfficeLandlineNumber=" + motherOfficeLandlineNumber
				+ ", fatherCellphoneNumberCode=" + fatherCellphoneNumberCode
				+ ", fatherCellphoneNumber=" + fatherCellphoneNumber
				+ ", motherCellphoneNumberCode=" + motherCellphoneNumberCode
				+ ", motherCellphoneNumber=" + motherCellphoneNumber
				+ ", fatherEmailAddress=" + fatherEmailAddress
				+ ", motherEmailAddress=" + motherEmailAddress
				+ ", fatherOccupation=" + fatherOccupation
				+ ", motherOccupation=" + motherOccupation
				+ ", presentAddressLine1=" + presentAddressLine1
				+ ", presentAddressLine2=" + presentAddressLine2
				+ ", presentAddressCityKey=" + presentAddressCityKey
				+ ", presentAddressPincode=" + presentAddressPincode
				+ ", presentAddressStateKey=" + presentAddressStateKey
				+ ", presentAddressCountryKey=" + presentAddressCountryKey
				+ ", presentAddressCityValue=" + presentAddressCityValue
				+ ", presentAddressStateValue=" + presentAddressStateValue
				+ ", presentAddressCountryValue=" + presentAddressCountryValue
				+ ", presentAddressAlternateCity="
				+ presentAddressAlternateCity + ", permanentAddressLine1="
				+ permanentAddressLine1 + ", permanentAddressLine2="
				+ permanentAddressLine2 + ", permanentAddressCityKey="
				+ permanentAddressCityKey + ", permanentAddressStateKey="
				+ permanentAddressStateKey + ", permanentAddressCountryKey="
				+ permanentAddressCountryKey + ", permanentAddressCityValue="
				+ permanentAddressCityValue
				+ ", permanentAddressAlternateCity="
				+ permanentAddressAlternateCity
				+ ", permanentAddressStateValue=" + permanentAddressStateValue
				+ ", permanentAddressCountryValue="
				+ permanentAddressCountryValue + ", permanentAddressPincode="
				+ permanentAddressPincode + ", isFatherPrimaryContact="
				+ isFatherPrimaryContact + ", fatherOfficeOrganizationName="
				+ fatherOfficeOrganizationName
				+ ", motherOfficeOrganizationName="
				+ motherOfficeOrganizationName + ", homeLandlineNumberCode="
				+ homeLandlineNumberCode + ", homeLandlineNumber="
				+ homeLandlineNumber + ", userId=" + userId
				+ ", fatherOccupationValue=" + fatherOccupationValue
				+ ", motherOccupationValue=" + motherOccupationValue
				+ ", isDeleted=" + isDeleted + "]";
	}

	@Override
	protected AppCache getEntityCache() {
		ParentCache parentCache = new ParentCache(Student.class.getSimpleName());
		parentCache.initializeCache();
		return parentCache;
	}

	@Override
	protected void setDefaultColumn() {
		StringBuilder stringBuilder = new StringBuilder();
		if (isFatherPrimaryContact == true) {
			if (fatherFirstName != null || fatherMiddleName != null
					|| fatherLastName != null) {
				if (fatherFirstName != null && !(fatherFirstName.isEmpty())) {
					stringBuilder.append(fatherFirstName.toUpperCase());
				}
				if (fatherMiddleName != null && !(fatherMiddleName.isEmpty())) {
					stringBuilder.append(" ");
					stringBuilder.append(fatherMiddleName.toUpperCase());
				}
				if (fatherLastName != null && !(fatherLastName.isEmpty())) {
					stringBuilder.append(" ");
					stringBuilder.append(fatherLastName.toUpperCase());
				}
				fatherFirstNameUpperCase = stringBuilder.toString();
			} else {
				fatherFirstNameUpperCase = null;
			}
		} else {
			if (motherFirstName != null || motherMiddleName != null
					|| motherLastName != null) {
				if (motherFirstName != null && !(motherFirstName.isEmpty())) {
					stringBuilder.append(motherFirstName.toUpperCase());
				}
				if (motherMiddleName != null && !(motherMiddleName.isEmpty())) {
					stringBuilder.append(" ");
					stringBuilder.append(motherMiddleName.toUpperCase());
				}
				if (motherLastName != null && !(motherLastName.isEmpty())) {
					stringBuilder.append(" ");
					stringBuilder.append(motherLastName.toUpperCase());
				}
				fatherFirstNameUpperCase = stringBuilder.toString();
			} else {
				fatherFirstNameUpperCase = null;
			}
		}
	}
}