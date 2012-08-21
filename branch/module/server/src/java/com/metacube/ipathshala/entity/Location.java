/**
 * 
 */
package com.metacube.ipathshala.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

/**
 * Subject entity.
 * 
 * @author knagar
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Location implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String locationName;

	@Persistent
	private String locationNameUpperCase;

	@Persistent
	private String locationDescription;

	@Persistent
	private String locationCapacity;

	@Persistent
	private Boolean isDeleted = false;

	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

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

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

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
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the locationDescription
	 */
	public String getLocationDescription() {
		return locationDescription;
	}

	/**
	 * @param locationDescription
	 *            the locationDescription to set
	 */
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	/**
	 * @return the locationCapacity
	 */
	public String getLocationCapacity() {
		return locationCapacity;
	}

	/**
	 * @param locationCapacity
	 *            the locationCapacity to set
	 */
	public void setLocationCapacity(String locationCapacity) {
		this.locationCapacity = locationCapacity;
	}

	public String getLocationNameUpperCase() {
		return locationNameUpperCase;
	}

	public void setLocationNameUpperCase(String locationNameUpperCase) {
		this.locationNameUpperCase = locationNameUpperCase;
	}

	@Override
	public String toString() {
		return "Location [key=" + key + ", locationName=" + locationName
				+ ", locationNameUpperCase=" + locationNameUpperCase
				+ ", locationDescription=" + locationDescription
				+ ", locationCapacity=" + locationCapacity + ", isDeleted="
				+ isDeleted + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ "]";
	}

	@Override
	public void jdoPreStore() {
		if (locationName != null) {
			locationNameUpperCase = locationName.toUpperCase();
		} else {
			locationNameUpperCase = null;
		}

	}
}
