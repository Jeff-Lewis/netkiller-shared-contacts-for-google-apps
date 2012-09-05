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

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class EvaluationSubComponent implements Serializable, StoreCallback {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Key evaluationComponentKey;
	
	@Persistent
	private String subComponentName;
	
	@Persistent
	private String subComponentDescription;
	
	@Persistent
	private String subComponentRemarks;
	
	@Persistent
	private Integer displaySequence = 0;
	
	@Persistent
	private Boolean active;
	
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
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



	@NotPersistent
	private EvaluationComponent evaluationComponent;
	

	@Override
	public String toString() {
		return "EvaluationSubComponent [key=" + key
				+ ", evaluationComponentKey=" + evaluationComponentKey
				+ ", subComponentName=" + subComponentName
				+ ", subComponentDescription=" + subComponentDescription
				+ ", subComponentRemarks=" + subComponentRemarks
				+ ", displaySequence=" + displaySequence + ", active=" + active
				+ ", lastModifiedDate=" + lastModifiedDate + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + "]";
	}



	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public Key getEvaluationComponentKey() {
		return evaluationComponentKey;
	}



	public void setEvaluationComponentKey(Key evaluationComponentKey) {
		this.evaluationComponentKey = evaluationComponentKey;
	}



	public String getSubComponentName() {
		return subComponentName;
	}



	public void setSubComponentName(String subComponentName) {
		this.subComponentName = subComponentName;
	}



	public String getSubComponentDescription() {
		return subComponentDescription;
	}



	public void setSubComponentDescription(String subComponentDescription) {
		this.subComponentDescription = subComponentDescription;
	}



	public String getSubComponentRemarks() {
		return subComponentRemarks;
	}



	public void setSubComponentRemarks(String subComponentRemarks) {
		this.subComponentRemarks = subComponentRemarks;
	}



	public Integer getDisplaySequence() {
		return displaySequence;
	}



	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
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
		// TODO Auto-generated method stub
		
	}

}
