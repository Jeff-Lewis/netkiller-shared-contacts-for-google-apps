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
public class EvaluationStage {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Boolean isDeleted = false;
	
	@Persistent
	private String name;
	
	@Persistent
	private Key stageSequenceKey;

	@NotPersistent
	private Value stageSequenceValue;
	
	@Persistent
	private Date startDate;
	
	@Persistent
	private Date endDate;
	
	@Persistent
	private Key academicYearKey;
	
	@NotPersistent
	private AcademicYear academicYear;
	
	@Persistent
	private Key termKey;
	
	@NotPersistent
	private Term term;
	
	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	@Persistent
	private Date lastModifiedDate;
	
	@Persistent
	private Date examStartDate;
	
	@Persistent
	private Date examEndDate;
	
	@Persistent
	private Date submitResultBy;
	
	@Persistent
	private Date publishResultBy;
	
	
	public Date getSubmitResultBy() {
		return submitResultBy;
	}

	public void setSubmitResultBy(Date submitResultBy) {
		this.submitResultBy = submitResultBy;
	}

	public Date getPublishResultBy() {
		return publishResultBy;
	}

	public void setPublishResultBy(Date publishResultBy) {
		this.publishResultBy = publishResultBy;
	}

	

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

	public Key getStageSequenceKey() {
		return stageSequenceKey;
	}

	public void setStageSequenceKey(Key sequenceKey) {
		this.stageSequenceKey = sequenceKey;
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

	public Value getStageSequenceValue() {
		return stageSequenceValue;
	}

	public void setStageSequenceValue(Value sequenceValue) {
		this.stageSequenceValue = sequenceValue;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Key getTermKey() {
		return termKey;
	}

	public void setTermKey(Key termKey) {
		this.termKey = termKey;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}
	
	public Date getExamStartDate() {
		return examStartDate;
	}

	public void setExamStartDate(Date examStartDate) {
		this.examStartDate = examStartDate;
	}

	public Date getExamEndDate() {
		return examEndDate;
	}

	public void setExamEndDate(Date examEndDate) {
		this.examEndDate = examEndDate;
	}
	
	@Override
	public String toString() {
		return "EvaluationStage [key=" + key + ", isDeleted=" + isDeleted
				+ ", name=" + name + ", stageSequenceKey=" + stageSequenceKey
				+ ", stageSequenceValue=" + stageSequenceValue + ", startDate="
				+ startDate + ", endDate=" + endDate + ", academicYearKey="
				+ academicYearKey + ", academicYear=" + academicYear
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate="
				+ lastModifiedDate + ", examStartDate=" + examStartDate +  ", examEndDate=" + examEndDate  
				+ ",submitResultBy = " + submitResultBy + ",publishResultBy = " + publishResultBy  
				+  "]";
	}

	//default constructor	
	public EvaluationStage() {
	
	}
	
	public EvaluationStage(String name, Key termKey, Key academicYearKey, Key sequenceKey){
		this.name = name;
		this.termKey = termKey;
		this.academicYearKey = academicYearKey;
		this.stageSequenceKey = sequenceKey;
	}

}
