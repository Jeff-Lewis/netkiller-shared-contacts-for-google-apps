package com.netkiller.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;


/**
 * GradingScale entity
 * 
 * @author saurabh
 *
 */

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class GradingScale implements Serializable, StoreCallback {
	


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	
	
	
	@Persistent
	private String nameUpperCase;
	
	@Persistent
	private String gradeScaleSteps;
	
	public String getNameUpperCase() {
		return nameUpperCase;
	}

	public void setNameUpperCase(String nameUpperCase) {
		this.nameUpperCase = nameUpperCase;
	}

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

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGradeScaleSteps() {
		return gradeScaleSteps;
	}

	public void setGradeScaleSteps(String gradeScaleSteps) {
		this.gradeScaleSteps = gradeScaleSteps;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

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

	@Override
	public void jdoPreStore() {
		if (!StringUtils.isBlank(name)) {
			nameUpperCase = name.toUpperCase();
		} else {
			nameUpperCase = null;
		}
		
	}
	
	@Override
	public  String toString() {		
		return "GradingScale [key=" + key + ", name=" + name + ", gradeScaleSteps="+ gradeScaleSteps
			 +"]";
	}
	
	@Override
	public boolean equals(Object gradingScale) {
		boolean result = false;
		if (((GradingScale) gradingScale).getKey().equals(this.getKey())) {
			result = true;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(String.valueOf(this.getKey().getId()));

	}

}
