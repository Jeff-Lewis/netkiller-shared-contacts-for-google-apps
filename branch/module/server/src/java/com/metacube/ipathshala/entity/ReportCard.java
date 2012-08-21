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

import org.springframework.web.multipart.MultipartFile;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;


@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class ReportCard implements Serializable, StoreCallback{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private BlobKey blobKey;
	
	@NotPersistent
    private Blob template;
	
	@Persistent
	private Key fromLevelKey;
	
	@NotPersistent
	private Value fromLevelValue;
	
	@Persistent
	private Key toLevelKey;
	
	@NotPersistent
	private Value toLevelValue;
	
	@Persistent
	private Key academicYearKey;
	
	@NotPersistent
	private AcademicYear academicYear;
	
	@Persistent
	private Key reportCardTypeKey;
	
	@NotPersistent
	private Value reportCardType;
	
	@Persistent
	private Key termKey;
	
	@NotPersistent
	private Term term;
	
	@Persistent
	private Key stageKey;
	
	@NotPersistent
	private EvaluationStage stage;
		
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

	public BlobKey getBlobKey() {
		return blobKey;
	}

	public void setBlobKey(BlobKey blobKey) {
		this.blobKey = blobKey;
	}

	public Blob getTemplate() {
		return template;
	}

	public void setTemplate(Blob template) {
		this.template = template;
	}

	public Key getFromLevelKey() {
		return fromLevelKey;
	}

	public void setFromLevelKey(Key fromLevelKey) {
		this.fromLevelKey = fromLevelKey;
	}

	public Value getFromLevelValue() {
		return fromLevelValue;
	}

	public void setFromLevelValue(Value fromLevelValue) {
		this.fromLevelValue = fromLevelValue;
	}

	public Key getToLevelKey() {
		return toLevelKey;
	}

	public void setToLevelKey(Key toLevelKey) {
		this.toLevelKey = toLevelKey;
	}

	public Value getToLevelValue() {
		return toLevelValue;
	}

	public void setToLevelValue(Value toLevelValue) {
		this.toLevelValue = toLevelValue;
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

	public Key getStageKey() {
		return stageKey;
	}

	public void setStageKey(Key stageKey) {
		this.stageKey = stageKey;
	}

	public EvaluationStage getStage() {
		return stage;
	}

	public void setStage(EvaluationStage stage) {
		this.stage = stage;
	}

	public Key getReportCardTypeKey() {
		return reportCardTypeKey;
	}

	public void setReportCardTypeKey(Key reportCardTypeKey) {
		this.reportCardTypeKey = reportCardTypeKey;
	}

	public Value getReportCardType() {
		return reportCardType;
	}

	public void setReportCardType(Value reportCardType) {
		this.reportCardType = reportCardType;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	@Override
	public String toString() {
		return "ReportCard [key=" + key + ", name=" + name + ", blobKey="
				+ blobKey + ", template=" + template + ", fromLevelKey="
				+ fromLevelKey + ", fromLevelValue=" + fromLevelValue
				+ ", toLevelKey=" + toLevelKey + ", toLevelValue="
				+ toLevelValue + ", academicYearKey=" + academicYearKey
				+ ", academicYear=" + academicYear + ", reportCardTypeKey="
				+ reportCardTypeKey + ", reportCardType=" + reportCardType
				+ ", termKey=" + termKey + ", term=" + term + ", stageKey="
				+ stageKey + ", stage=" + stage + ", lastModifiedDate="
				+ lastModifiedDate + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + ", isDeleted=" + isDeleted + "]";
	}

	@Override
	public void jdoPreStore(){
		
	}
	
	
}
