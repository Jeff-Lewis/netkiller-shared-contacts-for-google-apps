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

import com.google.appengine.api.datastore.Key;

/**
 * @author vishesh
 *
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class ClassGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Key classKey;
	
	@Persistent
	private Key groupKey;
	
	@NotPersistent
	private MyClass myClass;
	
	@NotPersistent
	private MyClass groupClass;

	@Persistent
	private Boolean active;

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
	
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the classKey
	 */
	public Key getClassKey() {
		return classKey;
	}

	/**
	 * @param classKey the classKey to set
	 */
	public void setClassKey(Key classKey) {
		this.classKey = classKey;
	}

	/**
	 * @return the groupKey
	 */
	public Key getGroupKey() {
		return groupKey;
	}

	/**
	 * @param groupKey the groupKey to set
	 */
	public void setGroupKey(Key groupKey) {
		this.groupKey = groupKey;
	}

	/**
	 * @return the myClass
	 */
	public MyClass getMyClass() {
		return myClass;
	}

	/**
	 * @param myClass the myClass to set
	 */
	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}

	/**
	 * @return the groupClass
	 */
	public MyClass getGroupClass() {
		return groupClass;
	}

	/**
	 * @param groupClass the groupClass to set
	 */
	public void setGroupClass(MyClass groupClass) {
		this.groupClass = groupClass;
	}
	
	

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

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
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassGroup [key=" + key + ", classKey=" + classKey
				+ ", groupKey=" + groupKey + ", myClass=" + myClass
				+ ", groupClass=" + groupClass + ", active=" + active
				+ ", isDeleted=" + isDeleted + ", lastModifiedDate="
				+ lastModifiedDate + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
