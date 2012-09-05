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
 * Period entity.
 * 
 * @author sparakh
 * 
 */

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Period implements Serializable, StoreCallback {

	public String getNameUpperCase() {
		return nameUpperCase;
	}

	public void setNameUpperCase(String nameUpperCase) {
		this.nameUpperCase = nameUpperCase;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;

	@Persistent
	private String nameUpperCase;

	@Persistent
	private String stFromHour;

	@Persistent
	private String stFromMin;

	@Persistent
	private String stToHour;

	@Persistent
	private String stToMin;

	@Persistent
	private String wtFromHour;

	@Persistent
	private String wtFromMin;

	@Persistent
	private String wtToHour;

	@Persistent
	private String wtToMin;

	@Persistent
	private String description;

	@Persistent
	private Boolean active;

	@Persistent
	private Key typeKey;

	@NotPersistent
	private Value typeValue;
	
	@Persistent
	private Key academicYearKey;
	
	@NotPersistent
	private AcademicYear academicYear;
	
	@NotPersistent
	private String academicYearBusinessKey;
	
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
	 * @return the academicYearBusinessKey
	 */
	public String getAcademicYearBusinessKey() {
		return academicYearBusinessKey;
	}

	/**
	 * @param academicYearBusinessKey the academicYearBusinessKey to set
	 */
	public void setAcademicYearBusinessKey(String academicYearBusinessKey) {
		this.academicYearBusinessKey = academicYearBusinessKey;
	}

	@NotPersistent
	private String academicYearKeyString;

	/**
	 * @return the academicYearKeyString
	 */
	public String getAcademicYearKeyString() {
		return academicYearKeyString;
	}

	/**
	 * @param academicYearKeyString the academicYearKeyString to set
	 */
	public void setAcademicYearKeyString(String academicYearKeyString) {
		this.academicYearKeyString = academicYearKeyString;
	}

	/**
	 * @return the academicYearKey
	 */
	public Key getAcademicYearKey() {
		return academicYearKey;
	}
	

	/**
	 * @param academicYearKey the academicYearKey to set
	 */
	public void setAcademicYearKey(Key academicYearKey) {
		this.academicYearKey = academicYearKey;
	}

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	/**
	 * @param academicYear the academicYear to set
	 */
	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	@Persistent
	private Boolean isDeleted = false ;
	
	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the stFromHour
	 */
	public String getStFromHour() {
		return stFromHour;
	}

	/**
	 * @param stFromHour
	 *            the stFromHour to set
	 */
	public void setStFromHour(String stFromHour) {
		this.stFromHour = stFromHour;
	}

	/**
	 * @return the stFromMin
	 */
	public String getStFromMin() {
		return stFromMin;
	}

	/**
	 * @param stFromMin
	 *            the stFromMin to set
	 */
	public void setStFromMin(String stFromMin) {
		this.stFromMin = stFromMin;
	}

	/**
	 * @return the stToHour
	 */
	public String getStToHour() {
		return stToHour;
	}

	/**
	 * @param stToHour
	 *            the stToHour to set
	 */
	public void setStToHour(String stToHour) {
		this.stToHour = stToHour;
	}

	/**
	 * @return the stToMin
	 */
	public String getStToMin() {
		return stToMin;
	}

	/**
	 * @param stToMin
	 *            the stToMin to set
	 */
	public void setStToMin(String stToMin) {
		this.stToMin = stToMin;
	}

	/**
	 * @return the wtFromHour
	 */
	public String getWtFromHour() {
		return wtFromHour;
	}

	/**
	 * @param wtFromHour
	 *            the wtFromHour to set
	 */
	public void setWtFromHour(String wtFromHour) {
		this.wtFromHour = wtFromHour;
	}

	/**
	 * @return the wtFromMin
	 */
	public String getWtFromMin() {
		return wtFromMin;
	}

	/**
	 * @param wtFromMin
	 *            the wtFromMin to set
	 */
	public void setWtFromMin(String wtFromMin) {
		this.wtFromMin = wtFromMin;
	}

	/**
	 * @return the wtToHour
	 */
	public String getWtToHour() {
		return wtToHour;
	}

	/**
	 * @param wtToHour
	 *            the wtToHour to set
	 */
	public void setWtToHour(String wtToHour) {
		this.wtToHour = wtToHour;
	}

	/**
	 * @return the wtToMin
	 */
	public String getWtToMin() {
		return wtToMin;
	}

	/**
	 * @param wtToMin
	 *            the wtToMin to set
	 */
	public void setWtToMin(String wtToMin) {
		this.wtToMin = wtToMin;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	public Key getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(Key typeKey) {
		this.typeKey = typeKey;
	}

	public Value getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(Value typeValue) {
		this.typeValue = typeValue;
	}

	

	@Override
	public String toString() {
		return "Period [key=" + key + ", name=" + name + ", nameUpperCase="
				+ nameUpperCase + ", stFromHour=" + stFromHour + ", stFromMin="
				+ stFromMin + ", stToHour=" + stToHour + ", stToMin=" + stToMin
				+ ", wtFromHour=" + wtFromHour + ", wtFromMin=" + wtFromMin
				+ ", wtToHour=" + wtToHour + ", wtToMin=" + wtToMin
				+ ", description=" + description + ", active=" + active
				+ ", typeKey=" + typeKey + ", typeValue=" + typeValue
				+ ", academicYearKey=" + academicYearKey + ", academicYear="
				+ academicYear + ", academicYearBusinessKey="
				+ academicYearBusinessKey + ", academicYearKeyString="
				+ academicYearKeyString + ", isDeleted=" + isDeleted + "]";
	}

	@Override
	public void jdoPreStore() {
		if (name != null) {
			nameUpperCase = name.toUpperCase();
		} else {
			nameUpperCase = null;
		}

	}
}