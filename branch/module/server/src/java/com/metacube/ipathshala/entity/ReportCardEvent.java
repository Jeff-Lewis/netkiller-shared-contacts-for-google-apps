package com.metacube.ipathshala.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.cache.AppCache;

/**
 * Entity that will hold relation information about reportCardEvent.
 * 
 * @author Ankit
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class ReportCardEvent extends EntityCache implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key classKey;
	
	@Persistent
	private String name;
	
	@NotPersistent
	private MyClass myclass;

	@NotPersistent
	private String classKeyString;
	
	@NotPersistent
	private String classBusinessKey;

	@Persistent
	private Key reportCardTypeKey;
	
	@NotPersistent
	private Value reportCardTypeValue;
	
	@NotPersistent
	private String reportCardTypeKeyString;
	
	@NotPersistent
	private String reportCardTypeBusinessKey;
	
	@Persistent
	private Key termKey;
	
	@NotPersistent
	private Term term;
	
	@NotPersistent
	private String termKeyString;
	
	@NotPersistent
	private String termBusinessKey;
	
	@Persistent
	private Key stageKey;
	
	@NotPersistent
	private EvaluationStage stage;
	
	@NotPersistent
	private String stageKeyString;
	
	@NotPersistent
	private String stageBusinessKey;
	
	@Persistent
	private Key academicYearKey;
	
	@NotPersistent
	private AcademicYear academicYear;
	
	@NotPersistent
	private String academicYearBusinessKey;
	
	@NotPersistent
	private String academicYearKeyString;
	
	@Persistent
	private String status;
	
	@Persistent
	private Boolean isPublished;
	
	@Persistent
	private String nameUpperCase;

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

	@Override
	protected AppCache getEntityCache() {
		return null;
	}

	@Override
	protected void setDefaultColumn() {
		if(StringUtils.isNotBlank(getName()))
			this.setNameUpperCase(getName().toUpperCase());
		
	}

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
	 * @return the myclass
	 */
	public MyClass getMyclass() {
		return myclass;
	}

	/**
	 * @param myclass the myclass to set
	 */
	public void setMyclass(MyClass myclass) {
		this.myclass = myclass;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the classKeyString
	 */
	public String getClassKeyString() {
		return classKeyString;
	}

	/**
	 * @param classKeyString the classKeyString to set
	 */
	public void setClassKeyString(String classKeyString) {
		this.classKeyString = classKeyString;
	}

	/**
	 * @return the classBusinessKey
	 */
	public String getClassBusinessKey() {
		return classBusinessKey;
	}

	/**
	 * @param classBusinessKey the classBusinessKey to set
	 */
	public void setClassBusinessKey(String classBusinessKey) {
		this.classBusinessKey = classBusinessKey;
	}

	/**
	 * @return the reportCardTypeKey
	 */
	public Key getReportCardTypeKey() {
		return reportCardTypeKey;
	}

	/**
	 * @param reportCardTypeKey the reportCardTypeKey to set
	 */
	public void setReportCardTypeKey(Key reportCardTypeKey) {
		this.reportCardTypeKey = reportCardTypeKey;
	}

	/**
	 * @return the reportCardTypeKeyString
	 */
	public String getReportCardTypeKeyString() {
		return reportCardTypeKeyString;
	}

	/**
	 * @param reportCardTypeKeyString the reportCardTypeKeyString to set
	 */
	public void setReportCardTypeKeyString(String reportCardTypeKeyString) {
		this.reportCardTypeKeyString = reportCardTypeKeyString;
	}

	/**
	 * @return the reportCardTypeBusinessKey
	 */
	public String getReportCardTypeBusinessKey() {
		return reportCardTypeBusinessKey;
	}

	/**
	 * @param reportCardTypeBusinessKey the reportCardTypeBusinessKey to set
	 */
	public void setReportCardTypeBusinessKey(String reportCardTypeBusinessKey) {
		this.reportCardTypeBusinessKey = reportCardTypeBusinessKey;
	}

	/**
	 * @return the termKey
	 */
	public Key getTermKey() {
		return termKey;
	}

	/**
	 * @param termKey the termKey to set
	 */
	public void setTermKey(Key termKey) {
		this.termKey = termKey;
	}

	/**
	 * @return the termKeyString
	 */
	public String getTermKeyString() {
		return termKeyString;
	}

	/**
	 * @param termKeyString the termKeyString to set
	 */
	public void setTermKeyString(String termKeyString) {
		this.termKeyString = termKeyString;
	}

	/**
	 * @return the termBusinessKey
	 */
	public String getTermBusinessKey() {
		return termBusinessKey;
	}

	/**
	 * @param termBusinessKey the termBusinessKey to set
	 */
	public void setTermBusinessKey(String termBusinessKey) {
		this.termBusinessKey = termBusinessKey;
	}

	/**
	 * @return the stageKey
	 */
	public Key getStageKey() {
		return stageKey;
	}

	/**
	 * @param stageKey the stageKey to set
	 */
	public void setStageKey(Key stageKey) {
		this.stageKey = stageKey;
	}

	/**
	 * @return the stageKeyString
	 */
	public String getStageKeyString() {
		return stageKeyString;
	}

	/**
	 * @param stageKeyString the stageKeyString to set
	 */
	public void setStageKeyString(String stageKeyString) {
		this.stageKeyString = stageKeyString;
	}

	/**
	 * @return the stageBusinessKey
	 */
	public String getStageBusinessKey() {
		return stageBusinessKey;
	}

	/**
	 * @param stageBusinessKey the stageBusinessKey to set
	 */
	public void setStageBusinessKey(String stageBusinessKey) {
		this.stageBusinessKey = stageBusinessKey;
	}

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	/**
	 * @param academicYear the academicYear to set
	 */
	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the academicYearBusinessKey
	 */
	public String getAcademicYearBusinessKey() {
		return academicYearBusinessKey;
	}

	/**
	 * @param academicYearBusinessKey the academicYearBusinessKey to set
	 */
	public void setAcademicYearBusinessKey(String academicYearBusinessKey) {
		this.academicYearBusinessKey = academicYearBusinessKey;
	}

	/**
	 * @return the academicYearKeyString
	 */
	public String getAcademicYearKeyString() {
		return academicYearKeyString;
	}

	/**
	 * @param academicYearKeyString the academicYearKeyString to set
	 */
	public void setAcademicYearKeyString(String academicYearKeyString) {
		this.academicYearKeyString = academicYearKeyString;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the isPublished
	 */
	public Boolean getIsPublished() {
		return isPublished;
	}

	/**
	 * @param isPublished the isPublished to set
	 */
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
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
	 * @return the reportCardTypeValue
	 */
	public Value getReportCardTypeValue() {
		return reportCardTypeValue;
	}

	/**
	 * @param reportCardTypeValue the reportCardTypeValue to set
	 */
	public void setReportCardTypeValue(Value reportCardTypeValue) {
		this.reportCardTypeValue = reportCardTypeValue;
	}

	/**
	 * @return the term
	 */
	public Term getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(Term term) {
		this.term = term;
	}

	/**
	 * @return the stage
	 */
	public EvaluationStage getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(EvaluationStage stage) {
		this.stage = stage;
	}

	/**
	 * @return the academicYearKey
	 */
	public Key getAcademicYearKey() {
		return academicYearKey;
	}

	/**
	 * @param academicYearKey the academicYearKey to set
	 */
	public void setAcademicYearKey(Key academicYearKey) {
		this.academicYearKey = academicYearKey;
	}

	/**
	 * @return the nameUpperCase
	 */
	public String getNameUpperCase() {
		return nameUpperCase;
	}

	/**
	 * @param nameUpperCase the nameUpperCase to set
	 */
	public void setNameUpperCase(String nameUpperCase) {
		this.nameUpperCase = nameUpperCase;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportCardEvent [key=" + key + ", classKey=" + classKey
				+ ", name=" + name + ", myclass=" + myclass
				+ ", classKeyString=" + classKeyString + ", classBusinessKey="
				+ classBusinessKey + ", reportCardTypeKey=" + reportCardTypeKey
				+ ", reportCardTypeValue=" + reportCardTypeValue
				+ ", reportCardTypeKeyString=" + reportCardTypeKeyString
				+ ", reportCardTypeBusinessKey=" + reportCardTypeBusinessKey
				+ ", termKey=" + termKey + ", term=" + term
				+ ", termKeyString=" + termKeyString + ", termBusinessKey="
				+ termBusinessKey + ", stageKey=" + stageKey + ", stage="
				+ stage + ", stageKeyString=" + stageKeyString
				+ ", stageBusinessKey=" + stageBusinessKey
				+ ", academicYearKey=" + academicYearKey + ", academicYear="
				+ academicYear + ", academicYearBusinessKey="
				+ academicYearBusinessKey + ", academicYearKeyString="
				+ academicYearKeyString + ", status=" + status
				+ ", isPublished=" + isPublished + ", nameUpperCase="
				+ nameUpperCase + ", lastModifiedDate=" + lastModifiedDate
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + ", isDeleted="
				+ isDeleted + "]";
	}

	
	

	

	
	
}