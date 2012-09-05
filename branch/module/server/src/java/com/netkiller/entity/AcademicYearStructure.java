package com.netkiller.entity;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class AcademicYearStructure {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Boolean isDeleted = false;
	
	@Persistent
	private String name;
	
	@Persistent
	private Key fromLevelKey;
	
	@Persistent
	private Key toLevelKey;

	@NotPersistent
	private Value fromLevelValue;
	
	@NotPersistent
	private Value toLevelValue;
	
	@Persistent
	private Integer terms;
	
	@Persistent
	private Integer stages;
	
	@Persistent
	private Key academicYearKey;
	
	@NotPersistent
	private AcademicYear academicYear;
	
	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	@Persistent
	private Date lastModifiedDate;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Key getFromLevelKey() {
		return fromLevelKey;
	}

	public void setFromLevelKey(Key fromLevel) {
		this.fromLevelKey = fromLevel;
	}

	public Key getToLevelKey() {
		return toLevelKey;
	}

	public void setToLevelKey(Key toLevel) {
		this.toLevelKey = toLevel;
	}

	public Value getFromLevelValue() {
		return fromLevelValue;
	}

	public void setFromLevelValue(Value fromLevelValue) {
		this.fromLevelValue = fromLevelValue;
	}

	public Value getToLevelValue() {
		return toLevelValue;
	}

	public void setToLevelValue(Value toLevelValue) {
		this.toLevelValue = toLevelValue;
	}

	public Integer getTerms() {
		return terms;
	}

	public void setTerms(Integer terms) {
		this.terms = terms;
	}

	public Integer getStages() {
		return stages;
	}

	public void setStages(Integer stages) {
		this.stages = stages;
	}

	public Key getAcademicYearKey() {
		return academicYearKey;
	}

	public void setAcademicYearKey(Key academicYearKey) {
		this.academicYearKey = academicYearKey;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}	

}

