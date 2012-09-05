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

import org.springframework.web.multipart.MultipartFile;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

/**
 * 
 * @author dhruvsharma
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Set implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String setName;

	@Persistent
	private String setNameUpperCase;

	@Persistent
	private String setOrder;

	@Persistent
	private Key parentSetKey;

	@NotPersistent
	private String parentSetKeyString;

	@NotPersistent
	private String parentSetBusinessKey;

	@NotPersistent
	private Set parentSet;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
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

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getSetNameUpperCase() {
		return setNameUpperCase;
	}

	public void setSetNameUpperCase(String setNameUpperCase) {
		this.setNameUpperCase = setNameUpperCase;
	}

	public String getSetOrder() {
		return setOrder;
	}

	public void setSetOrder(String setOrder) {
		this.setOrder = setOrder;
	}

	public Key getParentSetKey() {
		return parentSetKey;
	}

	public void setParentSetKey(Key parentSetKey) {
		this.parentSetKey = parentSetKey;
	}

	public String getParentSetKeyString() {
		return parentSetKeyString;
	}

	public void setParentSetKeyString(String parentSetKeyString) {
		this.parentSetKeyString = parentSetKeyString;
	}

	public String getParentSetBusinessKey() {
		return parentSetBusinessKey;
	}

	public void setParentSetBusinessKey(String parentSetBusinessKey) {
		this.parentSetBusinessKey = parentSetBusinessKey;
	}

	public Set getParentSet() {
		return parentSet;
	}

	public void setParentSet(Set parentSet) {
		this.parentSet = parentSet;
	}

	@Override
	public String toString() {
		return "Set [key=" + key + ", setName=" + setName
				+ ", setNameUpperCase=" + setNameUpperCase + ", setOrder="
				+ setOrder + ", parentSetKey=" + parentSetKey
				+ ", parentSetKeyString=" + parentSetKeyString
				+ ", parentSetBusinessKey=" + parentSetBusinessKey
				+ ", parentSet=" + parentSet + "]";
	}

	@Override
	public void jdoPreStore() {
		if (setName != null) {
			setNameUpperCase = setName.toUpperCase();
		} else {
			setNameUpperCase = null;
		}

	}

}
