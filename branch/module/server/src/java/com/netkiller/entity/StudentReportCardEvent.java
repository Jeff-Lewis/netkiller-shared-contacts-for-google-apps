package com.netkiller.entity;

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
import com.netkiller.cache.AppCache;

/**
 * Entity that will hold relation information about StudentStudentstudentReportCardEvent.
 * 
 * @author Ankit
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class StudentReportCardEvent extends EntityCache implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key studentKey;
	
	@NotPersistent
	private Student student;

	@NotPersistent
	private String studentKeyString;
	
	@NotPersistent
	private String studentBusinessKey;

	@Persistent
	private Key reportCardEventKey;
	
	@NotPersistent
	private ReportCardEvent reportCardEvent;
	
	@NotPersistent
	private String reportCardEventKeyString;
	
	@NotPersistent
	private String reportCardEventBusinessKey;
	
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
	private String reportCardPath;
	
	@Persistent
	private String remarks;
	
	@Persistent
	private String statusUpperCase;

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
		if(StringUtils.isNotBlank(getStatus()))
			this.setStatusUpperCase(getStatus().toUpperCase());
		
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
	 * @return the studentKey
	 */
	public Key getStudentKey() {
		return studentKey;
	}

	/**
	 * @param studentKey the studentKey to set
	 */
	public void setStudentKey(Key studentKey) {
		this.studentKey = studentKey;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the studentKeyString
	 */
	public String getStudentKeyString() {
		return studentKeyString;
	}

	/**
	 * @param studentKeyString the studentKeyString to set
	 */
	public void setStudentKeyString(String studentKeyString) {
		this.studentKeyString = studentKeyString;
	}

	/**
	 * @return the studentBusinessKey
	 */
	public String getStudentBusinessKey() {
		return studentBusinessKey;
	}

	/**
	 * @param studentBusinessKey the studentBusinessKey to set
	 */
	public void setStudentBusinessKey(String studentBusinessKey) {
		this.studentBusinessKey = studentBusinessKey;
	}

	/**
	 * @return the reportCardEventKey
	 */
	public Key getReportCardEventKey() {
		return reportCardEventKey;
	}

	/**
	 * @param reportCardEventKey the reportCardEventKey to set
	 */
	public void setReportCardEventKey(Key reportCardEventKey) {
		this.reportCardEventKey = reportCardEventKey;
	}

	/**
	 * @return the reportCardEvent
	 */
	public ReportCardEvent getReportCardEvent() {
		return reportCardEvent;
	}

	/**
	 * @param reportCardEvent the reportCardEvent to set
	 */
	public void setReportCardEvent(ReportCardEvent reportCardEvent) {
		this.reportCardEvent = reportCardEvent;
	}

	/**
	 * @return the reportCardEventKeyString
	 */
	public String getReportCardEventKeyString() {
		return reportCardEventKeyString;
	}

	/**
	 * @param reportCardEventKeyString the reportCardEventKeyString to set
	 */
	public void setReportCardEventKeyString(String reportCardEventKeyString) {
		this.reportCardEventKeyString = reportCardEventKeyString;
	}

	/**
	 * @return the reportCardEventBusinessKey
	 */
	public String getReportCardEventBusinessKey() {
		return reportCardEventBusinessKey;
	}

	/**
	 * @param reportCardEventBusinessKey the reportCardEventBusinessKey to set
	 */
	public void setReportCardEventBusinessKey(String reportCardEventBusinessKey) {
		this.reportCardEventBusinessKey = reportCardEventBusinessKey;
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
	 * @return the reportCardPath
	 */
	public String getReportCardPath() {
		return reportCardPath;
	}

	/**
	 * @param reportCardPath the reportCardPath to set
	 */
	public void setReportCardPath(String reportCardPath) {
		this.reportCardPath = reportCardPath;
	}

	/**
	 * @return the statusUpperCase
	 */
	public String getStatusUpperCase() {
		return statusUpperCase;
	}

	/**
	 * @param statusUpperCase the statusUpperCase to set
	 */
	public void setStatusUpperCase(String statusUpperCase) {
		this.statusUpperCase = statusUpperCase;
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
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentReportCardEvent [key=" + key + ", studentKey="
				+ studentKey + ", student=" + student + ", studentKeyString="
				+ studentKeyString + ", studentBusinessKey="
				+ studentBusinessKey + ", reportCardEventKey="
				+ reportCardEventKey + ", reportCardEvent=" + reportCardEvent
				+ ", reportCardEventKeyString=" + reportCardEventKeyString
				+ ", reportCardEventBusinessKey=" + reportCardEventBusinessKey
				+ ", academicYearKey=" + academicYearKey + ", academicYear="
				+ academicYear + ", academicYearBusinessKey="
				+ academicYearBusinessKey + ", academicYearKeyString="
				+ academicYearKeyString + ", status=" + status
				+ ", reportCardPath=" + reportCardPath + ", remarks=" + remarks
				+ ", statusUpperCase=" + statusUpperCase
				+ ", lastModifiedDate=" + lastModifiedDate + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + ", isDeleted="
				+ isDeleted + "]";
	}

	
	
	
}