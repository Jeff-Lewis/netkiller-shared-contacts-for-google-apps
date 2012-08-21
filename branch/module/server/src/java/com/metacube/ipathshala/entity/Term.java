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
 * Term Entity
 * 
 * @author Harish
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)

public class Term  implements Serializable {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Boolean isDeleted = false;
	
	@Persistent
	private String name;

	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;

	@Persistent
	private String workingDays;

	@Persistent
	private Key academicYearKey;
	
	@Persistent
	private Key academicYearStructureKey;
	
	@NotPersistent
	private AcademicYearStructure academicYearStructure;

	@NotPersistent
	private AcademicYear academicYear;

	@Persistent
	private Key sequenceKey;

	@NotPersistent
	private Value sequenceValue;
	
	@Persistent
	private Boolean active;
	
	@Persistent
	private Date lastModifiedDate;

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

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
	@Persistent
	private Boolean termEndExam;
	
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

	

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Key getSequenceKey() {
		return sequenceKey;
	}

	public void setSequenceKey(Key sequenceKey) {
		this.sequenceKey = sequenceKey;
	}

	public Value getSequenceValue() {
		return sequenceValue;
	}

	public void setSequenceValue(Value sequenceValue) {
		this.sequenceValue = sequenceValue;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
	}

	public Key getAcademicYearStructureKey() {
		return academicYearStructureKey;
	}

	public void setAcademicYearStructureKey(Key academicYearStructureKey) {
		this.academicYearStructureKey = academicYearStructureKey;
	}

	public AcademicYearStructure getAcademicYearStructure() {
		return academicYearStructure;
	}

	public void setAcademicYearStructure(AcademicYearStructure academicYearStructure) {
		this.academicYearStructure = academicYearStructure;
	}

	@Override
	public String toString() {
		return "Term [key=" + key + ", isDeleted=" + isDeleted + ", name="
				+ name + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", workingDays=" + workingDays + ", academicYearKey="
				+ academicYearKey + ", academicYear=" + academicYear
				+ ", sequenceKey=" + sequenceKey + ", sequenceValue="
				+ sequenceValue + ", active=" + active + ",termEndExam = " + termEndExam + "]";
	}
	
	//Default constructor
	public Term(){	}
	
	public Term(String name,Key academicYearStructureKey, Key academicYearKey, Key sequenceKey){
		this.name = name;
		this.academicYearStructureKey = academicYearStructureKey;
		this.academicYearKey = academicYearKey;
		this.sequenceKey = sequenceKey;
	}

	public void setTermEndExam(Boolean termEndExam) {
		this.termEndExam = termEndExam;
	}

	public Boolean getTermEndExam() {
		return termEndExam;
	}

}
