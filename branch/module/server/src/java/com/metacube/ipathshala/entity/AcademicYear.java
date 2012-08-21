/**
 * 
 */
package com.metacube.ipathshala.entity;

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
 * Academic_Year entity.
 * 
 * @author knagar
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class AcademicYear implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;

	@Persistent
	private String nameUpperCase;

	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;

	@Persistent
	private String description;

	@Persistent
	private Boolean active;

	@Persistent
	private Key typeKey;

	@Persistent
	private String sessionAccount;

	@NotPersistent
	private Value typeValue;

	@Persistent
	private Boolean isDeleted = false;

	@Persistent
	private Boolean isDefaultAcademicYear = false;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	public Boolean getIsDefaultAcademicYear() {
		return isDefaultAcademicYear;
	}

	public void setIsDefaultAcademicYear(Boolean isDefaultAcademicYear) {
		this.isDefaultAcademicYear = isDefaultAcademicYear;
	}

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

	/**
	 * @return the sessionAccount
	 */
	public String getSessionAccount() {
		return sessionAccount;
	}

	/**
	 * @param sessionAccount
	 *            the sessionAccount to set
	 */
	public void setSessionAccount(String sessionAccount) {
		this.sessionAccount = sessionAccount;
	}

	public String getNameUpperCase() {
		return nameUpperCase;
	}

	public void setNameUpperCase(String nameUpperCase) {
		this.nameUpperCase = nameUpperCase;
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

	@Override
	public String toString() {
		return "AcademicYear [key=" + key + ", name=" + name
				+ ", nameUpperCase=" + nameUpperCase + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", description=" + description
				+ ", active=" + active + ", typeKey=" + typeKey
				+ ", sessionAccount=" + sessionAccount + ", typeValue="
				+ typeValue + ", isDeleted=" + isDeleted
				+ ", isDefaultAcademicYear=" + isDefaultAcademicYear + "]";
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
