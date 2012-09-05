package com.netkiller.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

/**
 * Entity that will hold relation information between Class, Subject and
 * Teacher.
 * 
 * @author saurab
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class ClassStudent implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key classKey;

	@Persistent
	private Key studentKey;
	
	@NotPersistent
	private StudentMiscellaneous studentMiscellaneous;

	@NotPersistent
	private String studentKeyString;

	public StudentMiscellaneous getStudentMiscellaneous() {
		return studentMiscellaneous;
	}

	public void setStudentMiscellaneous(StudentMiscellaneous studentMiscellaneous) {
		this.studentMiscellaneous = studentMiscellaneous;
	}

	@NotPersistent
	private String classKeyString;

	@NotPersistent
	private MyClass myclass;

	@NotPersistent
	private String classBusinessKey;

	@NotPersistent
	private String studentBusinessKey;

	@Persistent
	private Key academicYearKey;

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

	/**
	 * @return the academicYearKey
	 */
	public Key getAcademicYearKey() {
		return academicYearKey;
	}

	/**
	 * @param academicYearKey
	 *            the academicYearKey to set
	 */
	public void setAcademicYearKey(Key academicYearKey) {
		this.academicYearKey = academicYearKey;
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

	/**
	 * @return the classKey
	 */
	public Key getClassKey() {
		return classKey;
	}

	/**
	 * @param classKey
	 *            the classKey to set
	 */
	public void setClassKey(Key classKey) {
		this.classKey = classKey;
	}

	/**
	 * @return the studentKey
	 */
	public Key getStudentKey() {
		return studentKey;
	}

	/**
	 * @param studentKey
	 *            the studentKey to set
	 */
	public void setStudentKey(Key studentKey) {
		this.studentKey = studentKey;
	}

	/**
	 * @return the myclass
	 */
	public MyClass getMyclass() {
		return myclass;
	}

	/**
	 * @param myclass
	 *            the myclass to set
	 */
	public void setMyclass(MyClass myclass) {
		this.myclass = myclass;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	@NotPersistent
	private Student student;

	/**
	 * @return the studentKeyString
	 */
	public String getStudentKeyString() {
		return studentKeyString;
	}

	/**
	 * @param studentKeyString
	 *            the studentKeyString to set
	 */
	public void setStudentKeyString(String studentKeyString) {
		this.studentKeyString = studentKeyString;
	}

	/**
	 * @return the classKeyString
	 */
	public String getClassKeyString() {
		return classKeyString;
	}

	/**
	 * @param classKeyString
	 *            the classKeyString to set
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
	 * @param classBusinessKey
	 *            the classBusinessKey to set
	 */
	public void setClassBusinessKey(String classBusinessKey) {
		this.classBusinessKey = classBusinessKey;
	}

	/**
	 * @return the studentBusinessKey
	 */
	public String getStudentBusinessKey() {
		return studentBusinessKey;
	}

	/**
	 * @param studentBusinessKey
	 *            the studentBusinessKey to set
	 */
	public void setStudentBusinessKey(String studentBusinessKey) {
		this.studentBusinessKey = studentBusinessKey;
	}

	@Override
	public String toString() {
		return "ClassStudent [key=" + key + ", classKey=" + classKey
				+ ", studentKey=" + studentKey + ", studentKeyString="
				+ studentKeyString + ", classKeyString=" + classKeyString
				+ ", myclass=" + myclass + ", classBusinessKey="
				+ classBusinessKey + ", studentBusinessKey="
				+ studentBusinessKey + ", academicYearKey=" + academicYearKey
				+ ", student=" + student + "]";
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		ClassStudent classStudent = (ClassStudent) obj;
		if (classStudent.getClassKey().equals(this.getClassKey())
				&& classStudent.getStudentKey().equals(this.getStudentKey())) {
			isEqual = true;
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return (int) (this.getClassKey().getId() * this.getClassKey().getId() + this.getStudentKey().getId()
				* this.getStudentKey().getId());
	}

}
