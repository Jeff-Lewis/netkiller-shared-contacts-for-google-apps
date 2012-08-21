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
 * @author dhruvsharma
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Value implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key setKey;

	@Persistent
	private String value;

	@Persistent
	private String valueUpperCase;

	@Persistent
	private int orderIndex;

	@Persistent
	private Key parentValueKey;

	@NotPersistent
	private Set set;

	@NotPersistent
	private Value parentValue;

	@NotPersistent
	private String setBusinessKey;

	@NotPersistent
	private String setKeyString;

	@NotPersistent
	private String parentValueBusinessKey;

	@NotPersistent
	private String parentValueKeyString;

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

	public Key getSetKey() {
		return setKey;
	}

	public void setSetKey(Key setKey) {
		this.setKey = setKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueUpperCase() {
		return valueUpperCase;
	}

	public void setValueUpperCase(String valueUpperCase) {
		this.valueUpperCase = valueUpperCase;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Key getParentValueKey() {
		return parentValueKey;
	}

	public void setParentValueKey(Key parentValueKey) {
		this.parentValueKey = parentValueKey;
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
	}

	public String getSetBusinessKey() {
		return setBusinessKey;
	}

	public void setSetBusinessKey(String setBusinessKey) {
		this.setBusinessKey = setBusinessKey;
	}

	public String getSetKeyString() {
		return setKeyString;
	}

	public void setSetKeyString(String setKeyString) {
		this.setKeyString = setKeyString;
	}

	public String getParentValueBusinessKey() {
		return parentValueBusinessKey;
	}

	public void setParentValueBusinessKey(String parentValueBusinessKey) {
		this.parentValueBusinessKey = parentValueBusinessKey;
	}

	public String getParentValueKeyString() {
		return parentValueKeyString;
	}

	public void setParentValueKeyString(String parentValueKeyString) {
		this.parentValueKeyString = parentValueKeyString;
	}

	public Value getParentValue() {
		return parentValue;
	}

	public void setParentValue(Value parentValue) {
		this.parentValue = parentValue;
	}

	@Override
	public void jdoPreStore() {
		if (value != null) {
			valueUpperCase = value.toUpperCase();
		} else {
			valueUpperCase = null;
		}

	}

	@Override
	public String toString() {
		return "Value [key=" + key + ", setKey=" + setKey + ", value=" + value
				+ ", valueUpperCase=" + valueUpperCase + ", orderIndex="
				+ orderIndex + ", parentValueKey=" + parentValueKey + ", set="
				+ set + ", parentValue=" + parentValue + ", setBusinessKey="
				+ setBusinessKey + ", setKeyString=" + setKeyString
				+ ", parentValueBusinessKey=" + parentValueBusinessKey
				+ ", parentValueKeyString=" + parentValueKeyString + "]";
	}

}
