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
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;

/**
 * Attendance entity.
 * 
 * @author Jitender
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Attendance implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key myClassKey;
	
	@Persistent
	private Key termKey;
	
	@Persistent
	private Key periodKey;
	
	@Persistent
	private Key studentKey;

	@Persistent
	private Integer termAttendance;

	@Persistent
	private Date attendanceDate;

	@Persistent
	private Boolean isDeleted = false;

	@Persistent
	private Boolean isPresent = true;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
	@NotPersistent
	private Student student;
	
	@NotPersistent
	private MyClass myClass;
	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public MyClass getMyClass() {
		return myClass;
	}


	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}


	public Attendance(){
	}


	public Attendance(Key myClassKey, Key studentKey, Date attendanceDate,Student student) {
		super();
		this.myClassKey = myClassKey;
		this.studentKey = studentKey;
		this.attendanceDate = attendanceDate;
		this.student = student;
	}
	
	public Attendance(Key myClassKey, Key studentKey, Key termKey,Student student){
		super();
		this.myClassKey = myClassKey;
		this.studentKey = studentKey;
		this.termKey = termKey;
		this.student = student;
	}


	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	/**
	 * @return the isPresent
	 */
	public Boolean getIsPresent() {
		return isPresent;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	public Key getStudentKey() {
		return studentKey;
	}

	public void setStudentKey(Key studentKey) {
		this.studentKey = studentKey;
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
	public String toString() {
		return "AcademicYear [key=" + key + ", studentClassKey=" + myClassKey
				+ ", termKey=" + termKey + ", periodKey=" + periodKey
				+ ", termAttendance=" + termAttendance + ", " +
						"attendanceDate=" + attendanceDate
				+ ", isDeleted=" + isDeleted + ", isPresent=" + isPresent
				+ "]";
	}



	public Key getMyClassKey() {
		return myClassKey;
	}

	public void setMyClassKey(Key myClassKey) {
		this.myClassKey = myClassKey;
	}

	public Key getTermKey() {
		return termKey;
	}

	public void setTermKey(Key termKey) {
		this.termKey = termKey;
	}

	public Key getPeriodKey() {
		return periodKey;
	}

	public void setPeriodKey(Key periodKey) {
		this.periodKey = periodKey;
	}

	public Integer getTermAttendance() {
		return termAttendance;
	}

	public void setTermAttendance(Integer termAttendance) {
		this.termAttendance = termAttendance;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	@Override
	public void jdoPreStore() {
		// TODO Auto-generated method stub
		
	}


}
