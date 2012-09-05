/**
 * 
 */
package com.netkiller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;
import com.netkiller.cache.AppCache;
import com.netkiller.cache.impl.StudentCache;

/**
 * Student entity.
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
public class Student extends EntityCache implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	
	@NotPersistent
	private String rollNumber;

	@Persistent
	private String enrollmentNumber;

	@Persistent
	private Date enrollmentDate;

	@Persistent
	private String firstName;

	@Persistent
	private String firstNameUpperCase;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
	@Persistent
	private String bloodGroup;

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
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

	public String getFirstNameUpperCase() {
		return firstNameUpperCase;
	}

	public void setFirstNameUpperCase(String firstNameUpperCase) {
		this.firstNameUpperCase = firstNameUpperCase;
	}

	@Persistent
	private String middleName;

	@Persistent
	private String lastName;

	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;

	@Persistent
	private Date dateRelieved;

	@Persistent
	private Key gender;

	@Persistent
	private String primaryLandlineNumberCode;

	@Persistent
	private String primaryLandlineNumber;

	@Persistent
	private String primaryCellNumberCode;

	@Persistent
	private String primaryCellNumber;

	@Persistent
	private String userId;

	@Persistent
	private String password;

	@Persistent
	private Key parentKey;

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
	private Parent parent;

	// @NotPersistent
	// private MyClass myClass;

	@NotPersistent
	private String parentBusinessKey;

	@NotPersistent
	private String parentKeyString;

	@NotPersistent
	private Value genderValue;

	@NotPersistent
	private Value presentAddressCityValue;

	@NotPersistent
	private Value presentAddressStateValue;

	@NotPersistent
	private Value presentAddressCountryValue;

	@Persistent
	private Boolean active;

	@Persistent
	private Boolean isDeleted = false;
	
	@Persistent
	private String house;
	
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

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
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

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * The living status of the student
	 */
	@Persistent
	private Key livingStatusKey;

	public Key getLivingStatusKey() {
		return livingStatusKey;
	}

	public void setLivingStatusKey(Key livingStatusKey) {
		this.livingStatusKey = livingStatusKey;
	}

	@Persistent
	private String presentAddressAlternateCity;

	public void setPresentAddressAlternateCity(
			String presentAddressAlternateCity) {
		this.presentAddressAlternateCity = presentAddressAlternateCity;
	}

	public String getPresentAddressAlternateCity() {
		return this.presentAddressAlternateCity;
	}

	@NotPersistent
	private Value livingStatusValue;

	public void setLivingStatusValue(Value livingStatusValue) {
		this.livingStatusValue = livingStatusValue;
	}

	public Value getLivingStatusValue() {
		return this.livingStatusValue;
	}

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
	 * @return the enrollmentNumber
	 */
	public String getEnrollmentNumber() {
		return enrollmentNumber;
	}

	/**
	 * @param enrollmentNumber
	 *            the enrollmentNumber to set
	 */
	public void setEnrollmentNumber(String enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
	}

	/**
	 * @return the enrollmentDate
	 */
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	/**
	 * @param enrollmentDate
	 *            the enrollmentDate to set
	 */
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
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
	 * @return the gender
	 */
	public Key getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(Key gender) {
		this.gender = gender;
	}

	/**
	 * @return the primaryLandlineNumberCode
	 */
	public String getPrimaryLandlineNumberCode() {
		return primaryLandlineNumberCode;
	}

	/**
	 * @param primaryLandlineNumberCode
	 *            the primaryLandlineNumberCode to set
	 */
	public void setPrimaryLandlineNumberCode(String primaryLandlineNumberCode) {
		this.primaryLandlineNumberCode = primaryLandlineNumberCode;
	}

	/**
	 * @return the primaryLandlineNumber
	 */
	public String getPrimaryLandlineNumber() {
		return primaryLandlineNumber;
	}

	/**
	 * @param primaryLandlineNumber
	 *            the primaryLandlineNumber to set
	 */
	public void setPrimaryLandlineNumber(String primaryLandlineNumber) {
		this.primaryLandlineNumber = primaryLandlineNumber;
	}

	/**
	 * @return the primaryCellNumberCode
	 */
	public String getPrimaryCellNumberCode() {
		return primaryCellNumberCode;
	}

	/**
	 * @param primaryCellNumberCode
	 *            the primaryCellNumberCode to set
	 */
	public void setPrimaryCellNumberCode(String primaryCellNumberCode) {
		this.primaryCellNumberCode = primaryCellNumberCode;
	}

	/**
	 * @return the primaryCellNumber
	 */
	public String getPrimaryCellNumber() {
		return primaryCellNumber;
	}

	/**
	 * @param primaryCellNumber
	 *            the primaryCellNumber to set
	 */
	public void setPrimaryCellNumber(String primaryCellNumber) {
		this.primaryCellNumber = primaryCellNumber;
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

	/*
	 * public MyClass getMyClass() { return myClass; }
	 */

	public String getPresentAddressLine1() {
		return presentAddressLine1;
	}

	/**
	 * 
	 * @param myClass
	 */
	/*
	 * public void setMyClass(MyClass myClass) { this.myClass = myClass; }
	 */

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
	 * @param parentKey
	 *            the parentKey to set
	 */
	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
	}

	/**
	 * @return the parent
	 */
	public Parent getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public String getParentBusinessKey() {
		return parentBusinessKey;
	}

	public void setParentBusinessKey(String parentBusinessKey) {
		this.parentBusinessKey = parentBusinessKey;
	}

	public Value getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(Value genderValue) {
		this.genderValue = genderValue;
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

	public Key getParentKey() {
		return parentKey;
	}

	public void setParentKeyString(String parentKeyString) {
		this.parentKeyString = parentKeyString;
	}

	public String getParentKeyString() {
		return this.parentKeyString;
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

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
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
	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}


	@Override
	public String toString() {
		return "Student [key=" + key + ", enrollmentNumber=" + enrollmentNumber
				+ ", enrollmentDate=" + enrollmentDate + ", firstName="
				+ firstName + ", firstNameUpperCase=" + firstNameUpperCase
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", dateRelieved=" + dateRelieved + ", gender=" + gender
				+ ", primaryLandlineNumberCode=" + primaryLandlineNumberCode
				+ ", primaryLandlineNumber=" + primaryLandlineNumber
				+ ", primaryCellNumberCode=" + primaryCellNumberCode
				+ ", primaryCellNumber=" + primaryCellNumber + ", userId="
				+ userId + ", password=" + password + ", parentKey="
				+ parentKey + ", presentAddressLine1=" + presentAddressLine1
				+ ", presentAddressLine2=" + presentAddressLine2
				+ ", presentAddressCityKey=" + presentAddressCityKey
				+ ", presentAddressPincode=" + presentAddressPincode
				+ ", presentAddressStateKey=" + presentAddressStateKey
				+ ", presentAddressCountryKey=" + presentAddressCountryKey
				+ ", parent=" + parent + ", parentBusinessKey="
				+ parentBusinessKey + ", parentKeyString=" + parentKeyString
				+ ", genderValue=" + genderValue + ", presentAddressCityValue="
				+ presentAddressCityValue + ", presentAddressStateValue="
				+ presentAddressStateValue + ", presentAddressCountryValue="
				+ presentAddressCountryValue + ", active=" + active
				+ ", isDeleted=" + isDeleted + ", house=" + house
				+ ", bloodGroupKey=" + bloodGroupKey + ", bloodGroupValue="
				+ bloodGroupValue + ", dateOfBirth=" + dateOfBirth
				+ ", userDefinedStringField1=" + userDefinedStringField1
				+ ", userDefinedStringField2=" + userDefinedStringField2
				+ ", userDefinedStringField3=" + userDefinedStringField3
				+ ", userDefinedDateField1=" + userDefinedDateField1
				+ ", livingStatusKey=" + livingStatusKey
				+ ", presentAddressAlternateCity="
				+ presentAddressAlternateCity + ", livingStatusValue="
				+ livingStatusValue + "]";
	}

	/*public void jdoPreStore() {
		if (firstName != null) {
			firstNameUpperCase = firstName.toUpperCase();
		} else {
			firstNameUpperCase = null;
		}
	}*/

	
	@Override
	public void setDefaultColumn() {
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
	protected AppCache getEntityCache() {
		StudentCache studentCache = new StudentCache(Student.class.getSimpleName());
		studentCache.initializeCache();
		return studentCache;
	}



}