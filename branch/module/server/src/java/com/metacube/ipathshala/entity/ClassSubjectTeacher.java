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
import com.google.gdata.data.DateTime;

/**
 * Entity that will hold relation information between Class, Subject and
 * Teacher.
 * 
 * @author sabir
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class ClassSubjectTeacher implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key classKey;

	@Persistent
	private Key subjectKey;
	
	@Persistent
	private Key evalSchKey;
	
	@NotPersistent
	private EvaluationScheme evalSch;

	@Persistent
	private Key teacherKey;

	@Persistent
	private String description;

	public Key getEvalSchKey() {
		return evalSchKey;
	}

	public void setEvalSchKey(Key evalSchKey) {
		this.evalSchKey = evalSchKey;
	}

	public EvaluationScheme getEvalSch() {
		return evalSch;
	}

	public void setEvalSch(EvaluationScheme evalSch) {
		this.evalSch = evalSch;
	}

	@NotPersistent
	private String classBusinessKey;

	@NotPersistent
	private String subjectBusinessKey;

	@NotPersistent
	private String teacherBusinessKey;

	@NotPersistent
	private String classKeyString;

	@NotPersistent
	private String subjectKeyString;

	@NotPersistent
	private String teacherKeyString;

	@NotPersistent
	private MyClass myclass;

	@NotPersistent
	private Subject subject;

	@NotPersistent
	private Teacher teacher;

	@Persistent
	private Key academicYearKey;
	
	@Persistent
	private Key locationKey ;
	
	@NotPersistent
	private Location location ;
	
	@NotPersistent
	private String locationKeyString ;
	
	@NotPersistent
	private String locationBusinessKey ;

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
	
	@Persistent
	private Boolean isEvalSchemeRemovable = true ;
	
	@Persistent
	private Boolean instructorIncharge=false;
	
	@Persistent
	private Boolean isSubject = true;
	
	public Boolean getIsSubject() {
		return isSubject;
	}

	public void setIsSubject(Boolean isSubject) {
		this.isSubject = isSubject;
	}

	public Boolean getInstructorIncharge() {
		return instructorIncharge;
	}

	public void setInstructorIncharge(Boolean instructorIncharge) {
		this.instructorIncharge = instructorIncharge;
	}

	/**
	 * @return the isEvalSchemeRemovable
	 */
	public Boolean getIsEvalSchemeRemovable() {
		return isEvalSchemeRemovable;
	}

	/**
	 * @param isEvalSchemeRemovable the isEvalSchemeRemovable to set
	 */
	public void setIsEvalSchemeRemovable(Boolean isEvalSchemeRemovable) {
		this.isEvalSchemeRemovable = isEvalSchemeRemovable;
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
	 * @return the locationKeyString
	 */
	public String getLocationKeyString() {
		return locationKeyString;
	}

	/**
	 * @param locationKeyString the locationKeyString to set
	 */
	public void setLocationKeyString(String locationKeyString) {
		this.locationKeyString = locationKeyString;
	}

	/**
	 * @return the locationBusinessKey
	 */
	public String getLocationBusinessKey() {
		return locationBusinessKey;
	}

	/**
	 * @param locationBusinessKey the locationBusinessKey to set
	 */
	public void setLocationBusinessKey(String locationBusinessKey) {
		this.locationBusinessKey = locationBusinessKey;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the locationKey
	 */
	public Key getLocationKey() {
		return locationKey;
	}

	/**
	 * @param locationKey the locationKey to set
	 */
	public void setLocationKey(Key locationKey) {
		this.locationKey = locationKey;
	}

	@Override
	public String toString() {
		return "ClassSubjectTeacher [key=" + key + ", classKey=" + classKey
				+ ", subjectKey=" + subjectKey + ", evalSchKey=" + evalSchKey
				+ ", evalSch=" + evalSch + ", teacherKey=" + teacherKey
				+ ", description=" + description + ", classBusinessKey="
				+ classBusinessKey + ", subjectBusinessKey="
				+ subjectBusinessKey + ", teacherBusinessKey="
				+ teacherBusinessKey + ", classKeyString=" + classKeyString
				+ ", subjectKeyString=" + subjectKeyString
				+ ", teacherKeyString=" + teacherKeyString + ", myclass="
				+ myclass + ", subject=" + subject + ", teacher=" + teacher
				+ ", academicYearKey=" + academicYearKey + ", locationKey="
				+ locationKey + ", location=" + location
				+ ", locationKeyString=" + locationKeyString
				+ ", locationBusinessKey=" + locationBusinessKey
				+ ", lastModifiedDate=" + lastModifiedDate + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + ", isDeleted="
				+ isDeleted + ", getEvalSchKey()=" + getEvalSchKey()
				+ ", getEvalSch()=" + getEvalSch() + ", getIsDeleted()="
				+ getIsDeleted() + ", getLastModifiedDate()="
				+ getLastModifiedDate() + ", getCreatedDate()="
				+ getCreatedDate() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getLastModifiedBy()=" + getLastModifiedBy()
				+ ", getLocationKeyString()=" + getLocationKeyString()
				+ ", getLocationBusinessKey()=" + getLocationBusinessKey()
				+ ", getLocation()=" + getLocation() + ", getLocationKey()="
				+ getLocationKey() + ", getAcademicYearKey()="
				+ getAcademicYearKey() + ", getMyclass()=" + getMyclass()
				+ ", getSubject()=" + getSubject() + ", getTeacher()="
				+ getTeacher() + ", getKey()=" + getKey() + ", getClassKey()="
				+ getClassKey() + ", getSubjectKey()=" + getSubjectKey()
				+ ", getTeacherKey()=" + getTeacherKey()
				+ ", getDescription()=" + getDescription()
				+ ", getClassBusinessKey()=" + getClassBusinessKey()
				+ ", getSubjectBusinessKey()=" + getSubjectBusinessKey()
				+ ", getTeacherBusinessKey()=" + getTeacherBusinessKey()
				+ ", getClassKeyString()=" + getClassKeyString()
				+ ", getSubjectKeyString()=" + getSubjectKeyString()
				+ ", getTeacherKeyString()=" + getTeacherKeyString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
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
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
	 * @return the subjectKey
	 */
	public Key getSubjectKey() {
		return subjectKey;
	}

	/**
	 * @param subjectKey
	 *            the subjectKey to set
	 */
	public void setSubjectKey(Key subjectKey) {
		this.subjectKey = subjectKey;
	}

	/**
	 * @return the teacherKey
	 */
	public Key getTeacherKey() {
		return teacherKey;
	}

	/**
	 * @param teacherKey
	 *            the teacherKey to set
	 */
	public void setTeacherKey(Key teacherKey) {
		this.teacherKey = teacherKey;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the subjectBusinessKey
	 */
	public String getSubjectBusinessKey() {
		return subjectBusinessKey;
	}

	/**
	 * @param subjectBusinessKey
	 *            the subjectBusinessKey to set
	 */
	public void setSubjectBusinessKey(String subjectBusinessKey) {
		this.subjectBusinessKey = subjectBusinessKey;
	}

	/**
	 * @return the teacherBusinessKey
	 */
	public String getTeacherBusinessKey() {
		return teacherBusinessKey;
	}

	/**
	 * @param teacherBusinessKey
	 *            the teacherBusinessKey to set
	 */
	public void setTeacherBusinessKey(String teacherBusinessKey) {
		this.teacherBusinessKey = teacherBusinessKey;
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
	 * @return the subjectKeyString
	 */
	public String getSubjectKeyString() {
		return subjectKeyString;
	}

	/**
	 * @param subjectKeyString
	 *            the subjectKeyString to set
	 */
	public void setSubjectKeyString(String subjectKeyString) {
		this.subjectKeyString = subjectKeyString;
	}

	/**
	 * @return the teacherKeyString
	 */
	public String getTeacherKeyString() {
		return teacherKeyString;
	}

	/**
	 * @param teacherKeyString
	 *            the teacherKeyString to set
	 */
	public void setTeacherKeyString(String teacherKeyString) {
		this.teacherKeyString = teacherKeyString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((academicYearKey == null) ? 0 : academicYearKey.hashCode());
		result = prime * result + ((classKey == null) ? 0 : classKey.hashCode());
		result = prime * result + ((evalSchKey == null) ? 0 : evalSchKey.hashCode());
		result = prime * result + ((instructorIncharge == null) ? 0 : instructorIncharge.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((isEvalSchemeRemovable == null) ? 0 : isEvalSchemeRemovable.hashCode());
		result = prime * result + ((isSubject == null) ? 0 : isSubject.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((locationKey == null) ? 0 : locationKey.hashCode());
		result = prime * result + ((subjectKey == null) ? 0 : subjectKey.hashCode());
		result = prime * result + ((teacherKey == null) ? 0 : teacherKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassSubjectTeacher other = (ClassSubjectTeacher) obj;
		if (academicYearKey == null) {
			if (other.academicYearKey != null)
				return false;
		} else if (!academicYearKey.equals(other.academicYearKey))
			return false;
		if (classKey == null) {
			if (other.classKey != null)
				return false;
		} else if (!classKey.equals(other.classKey))
			return false;
		if (evalSchKey == null) {
			if (other.evalSchKey != null)
				return false;
		} else if (!evalSchKey.equals(other.evalSchKey))
			return false;
		if (instructorIncharge == null) {
			if (other.instructorIncharge != null)
				return false;
		} else if (!instructorIncharge.equals(other.instructorIncharge))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (isEvalSchemeRemovable == null) {
			if (other.isEvalSchemeRemovable != null)
				return false;
		} else if (!isEvalSchemeRemovable.equals(other.isEvalSchemeRemovable))
			return false;
		if (isSubject == null) {
			if (other.isSubject != null)
				return false;
		} else if (!isSubject.equals(other.isSubject))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (locationKey == null) {
			if (other.locationKey != null)
				return false;
		} else if (!locationKey.equals(other.locationKey))
			return false;
		if (subjectKey == null) {
			if (other.subjectKey != null)
				return false;
		} else if (!subjectKey.equals(other.subjectKey))
			return false;
		if (teacherKey == null) {
			if (other.teacherKey != null)
				return false;
		} else if (!teacherKey.equals(other.teacherKey))
			return false;
		return true;
	}



	/*
	 * @Override public int hashCode() { return
	 * (int)(this.getClassKey().getId()*
	 * this.getClassKey().getId()+this.getSubjectKey
	 * ().getId()*this.getSubjectKey
	 * ().getId()+this.getTeacherKey().getId()*this.getTeacherKey().getId()); }
	 */

}
