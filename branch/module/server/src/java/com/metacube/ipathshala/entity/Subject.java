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
 * Subject entity.
 * 
 * @author knagar
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Subject implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String subjectName;
	
	@Persistent
	private Double version;

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
	 * @return the version
	 */
	public Double getVersion() {
		return version;
	}



	/**
	 * @param version the version to set
	 */
	public void setVersion(Double version) {
		this.version = version;
	}

	@Persistent
	private String subjectNameUpperCase;

	@Persistent
	private String subjectDescription;

	@Persistent
	private String subjectGroup;
	






	public Key getSubjectLevelKey() {
		return subjectLevelKey;
	}



	public void setSubjectLevelKey(Key subjectLevelKey) {
		this.subjectLevelKey = subjectLevelKey;
	}



	public Value getSubjectLevelValue() {
		return subjectLevelValue;
	}



	public void setSubjectLevelValue(Value subjectLevelValue) {
		this.subjectLevelValue = subjectLevelValue;
	}

	@Persistent
	private Key subjectLevelKey;
	
	@NotPersistent
	private Value subjectLevelValue;

	@Persistent
	private Boolean active;
	
	@Persistent
	private Boolean isDeleted = false ;
	
	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;
	
	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	


	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}



	/**
	 * @param fromDate the fromDate to set
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
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}



	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * 
	 * @return SubjectNameUpperCase
	 */
	public String getSubjectNameUpperCase() {
		return subjectNameUpperCase;
	}

	/**
	 * 
	 * @param subjectNameUpperCase
	 */
	public void setSubjectNameUpperCase(String subjectNameUpperCase) {
		this.subjectNameUpperCase = subjectNameUpperCase;
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
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName
	 *            the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the subjectDescription
	 */
	public String getSubjectDescription() {
		return subjectDescription;
	}

	/**
	 * @param subjectDescription
	 *            the subjectDescription to set
	 */
	public void setSubjectDescription(String subjectDescription) {
		this.subjectDescription = subjectDescription;
	}

	/**
	 * @return the subjectGroup
	 */
	public String getSubjectGroup() {
		return subjectGroup;
	}

	/**
	 * @param subjectGroup
	 *            the subjectGroup to set
	 */
	public void setSubjectGroup(String subjectGroup) {
		this.subjectGroup = subjectGroup;
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

	

	@Override
	public String toString() {
		return "Subject [key=" + key + ", subjectName=" + subjectName
				+ ", version=" + version + ", subjectNameUpperCase="
				+ subjectNameUpperCase + ", subjectDescription="
				+ subjectDescription + ", subjectGroup=" + subjectGroup
				+ ", subjectLevelKey=" + subjectLevelKey
				+ ", subjectLevelValue=" + subjectLevelValue + ", active="
				+ active + ", isDeleted=" + isDeleted + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}

	@Override
	public void jdoPreStore() {
		if (subjectName != null) {
			subjectNameUpperCase = subjectName.toUpperCase();
		} else {
			subjectNameUpperCase = null;
		}

	}
	

	@Override
	public boolean equals(Object subject) {
		boolean result = false;
		if (((Subject) subject).getKey().equals(this.getKey())) {
			result = true;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(String.valueOf(this.getKey().getId()));

	}
}