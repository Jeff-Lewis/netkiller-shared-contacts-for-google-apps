package com.metacube.ipathshala.entity;

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
public class TermAttendance implements StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Key termKey;
	
	@Persistent
	private Key studentKey;
	
	@Persistent
	private Double termAttendace;
	
	@Persistent
	private Double workingDays;

	@NotPersistent
	private Term term;

	@NotPersistent
	private Student student;
	
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
	
	public TermAttendance() {
		
	}
	
	public TermAttendance(Key studentKey, Key termKey, Student student){
		this.studentKey = studentKey;
		this.termKey = termKey;
		this.student = student;
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

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getTermKey() {
		return termKey;
	}

	public void setTermKey(Key termKey) {
		this.termKey = termKey;
	}

	public Key getStudentKey() {
		return studentKey;
	}

	public void setStudentKey(Key studentKey) {
		this.studentKey = studentKey;
	}

	public Double getTermAttendace() {
		return termAttendace;
	}

	public void setTermAttendace(Double termAttendace) {
		this.termAttendace = termAttendace;
	}

	public Double getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(Double workingDays) {
		this.workingDays = workingDays;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "TermAttendance [key=" + key + ", termKey=" + termKey
				+ ", studentKey=" + studentKey + ", termAttendace="
				+ termAttendace + ", workingDays=" + workingDays + ", term="
				+ term + ", student=" + student + ", lastModifiedDate="
				+ lastModifiedDate + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + ", isDeleted=" + isDeleted + "]";
	}
	
	@Override
	public void jdoPreStore() {
		// TODO Auto-generated method stub

	}

}
