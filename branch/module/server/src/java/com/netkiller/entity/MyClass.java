/**
 * 
 */
package com.netkiller.entity;

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
import com.google.gdata.data.DateTime;

/**
 * MyClass entity
 * 
 * @author knagar
 * 
 */

/**
 * @author administrator
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class MyClass implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;

	@Persistent
	private String nameUpperCase;

	@Persistent
	private String description;

	@Persistent
	private Key levelKey;

	@Persistent
	private Key classTypeKey;

	@Persistent
	private Boolean active;

	@Persistent
	private Key academicYearKey;

	@Persistent
	private Key locationKey;

	@Persistent
	private Key classTeacherKey;

	@NotPersistent
	private AcademicYear academicYear;

	@NotPersistent
	private Location location;

	@NotPersistent
	private Teacher classTeacher;

	@NotPersistent
	private String academicYearBusinessKey;

	@NotPersistent
	private String locationBusinessKey;

	@NotPersistent
	private String classTeacherBusinessKey;

	@NotPersistent
	private String academicYearKeyString;

	@NotPersistent
	private String locationKeyString;

	@NotPersistent
	private String classTeacherKeyString;

	@NotPersistent
	private Value levelValue;

	@NotPersistent
	private Value classTypeValue;
		
	@Persistent
	private String eventCalendarId;

	@Persistent
	private String classSiteName;

	@Persistent
	private String assignmentCalendarId;

	@Persistent
	private Boolean isDeleted = false;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the nameUpperCase
	 */
	public String getNameUpperCase() {
		return nameUpperCase;
	}

	/**
	 * @param nameUpperCase
	 *            the nameUpperCase to set
	 */
	public void setNameUpperCase(String nameUpperCase) {
		this.nameUpperCase = nameUpperCase;
	}

	/**
	 * @return the levelKey
	 */
	public Key getLevelKey() {
		return levelKey;
	}

	/**
	 * @param levelKey
	 *            the levelKey to set
	 */
	public void setLevelKey(Key levelKey) {
		this.levelKey = levelKey;
	}

	/**
	 * @return the classTypeKey
	 */
	public Key getClassTypeKey() {
		return classTypeKey;
	}

	/**
	 * @param classTypeKey
	 *            the classTypeKey to set
	 */
	public void setClassTypeKey(Key classTypeKey) {
		this.classTypeKey = classTypeKey;
	}

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the classTeacher
	 */
	public Teacher getClassTeacher() {
		return classTeacher;
	}

	/**
	 * @param classTeacher
	 *            the classTeacher to set
	 */
	public void setClassTeacher(Teacher classTeacher) {
		this.classTeacher = classTeacher;
	}

	/**
	 * @return the academicYearBusinessKey
	 */
	public String getAcademicYearBusinessKey() {
		return academicYearBusinessKey;
	}

	/**
	 * @param academicYearBusinessKey
	 *            the academicYearBusinessKey to set
	 */
	public void setAcademicYearBusinessKey(String academicYearBusinessKey) {
		this.academicYearBusinessKey = academicYearBusinessKey;
	}

	/**
	 * @return the locationBusinessKey
	 */
	public String getLocationBusinessKey() {
		return locationBusinessKey;
	}

	/**
	 * @param locationBusinessKey
	 *            the locationBusinessKey to set
	 */
	public void setLocationBusinessKey(String locationBusinessKey) {
		this.locationBusinessKey = locationBusinessKey;
	}

	/**
	 * @return the classTeacherBusinessKey
	 */
	public String getClassTeacherBusinessKey() {
		return classTeacherBusinessKey;
	}

	/**
	 * @param classTeacherBusinessKey
	 *            the classTeacherBusinessKey to set
	 */
	public void setClassTeacherBusinessKey(String classTeacherBusinessKey) {
		this.classTeacherBusinessKey = classTeacherBusinessKey;
	}

	/**
	 * @return the academicYearKeyString
	 */
	public String getAcademicYearKeyString() {
		return academicYearKeyString;
	}

	/**
	 * @param academicYearKeyString
	 *            the academicYearKeyString to set
	 */
	public void setAcademicYearKeyString(String academicYearKeyString) {
		this.academicYearKeyString = academicYearKeyString;
	}

	/**
	 * @return the locationKeyString
	 */
	public String getLocationKeyString() {
		return locationKeyString;
	}

	/**
	 * @param locationKeyString
	 *            the locationKeyString to set
	 */
	public void setLocationKeyString(String locationKeyString) {
		this.locationKeyString = locationKeyString;
	}

	/**
	 * @return the classTeacherKeyString
	 */
	public String getClassTeacherKeyString() {
		return classTeacherKeyString;
	}

	/**
	 * @param classTeacherKeyString
	 *            the classTeacherKeyString to set
	 */
	public void setClassTeacherKeyString(String classTeacherKeyString) {
		this.classTeacherKeyString = classTeacherKeyString;
	}

	/**
	 * @return the levelValue
	 */
	public Value getLevelValue() {
		return levelValue;
	}

	/**
	 * @param levelValue
	 *            the levelValue to set
	 */
	public void setLevelValue(Value levelValue) {
		this.levelValue = levelValue;
	}

	/**
	 * @return the classTypeValue
	 */
	public Value getClassTypeValue() {
		return classTypeValue;
	}

	/**
	 * @param classTypeValue
	 *            the classTypeValue to set
	 */
	public void setClassTypeValue(Value classTypeValue) {
		this.classTypeValue = classTypeValue;
	}

	/**
	 * @param classTeacherKey
	 *            the classTeacherKey to set
	 */
	public void setClassTeacherKey(Key classTeacherKey) {
		this.classTeacherKey = classTeacherKey;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return academicYearKey
	 */
	public Key getAcademicYearKey() {
		return academicYearKey;
	}

	/**
	 * @param academicYearKey
	 */
	public void setAcademicYearKey(Key academicYearKey) {
		this.academicYearKey = academicYearKey;
	}

	/**
	 * @return locationKey
	 */
	public Key getLocationKey() {
		return locationKey;
	}

	/**
	 * @param locationKey
	 */
	public void setLocationKey(Key locationKey) {
		this.locationKey = locationKey;
	}

	/**
	 * @return classTeacherKey
	 */
	public Key getClassTeacherKey() {
		return classTeacherKey;
	}

	/**
	 * @return the eventCalendarId
	 */
	public String getEventCalendarId() {
		return eventCalendarId;
	}

	/**
	 * @param eventCalendarId
	 *            the eventCalendarId to set
	 */
	public void setEventCalendarId(String eventCalendarId) {
		this.eventCalendarId = eventCalendarId;
	}

	/**
	 * @return the classSiteURL
	 */
	public String getClassSiteName() {
		return classSiteName;
	}

	/**
	 * @param classSiteURL
	 *            the classSiteURL to set
	 */
	public void setClassSiteName(String classSiteName) {
		this.classSiteName = classSiteName;
	}

	/**
	 * @return the assignmentCalendarId
	 */
	public String getAssignmentCalendarId() {
		return assignmentCalendarId;
	}

	/**
	 * @param assignmentCalendarId
	 *            the assignmentCalendarId to set
	 */
	public void setAssignmentCalendarId(String assignmentCalendarId) {
		this.assignmentCalendarId = assignmentCalendarId;
	}


	@Override
	public String toString() {
		return "MyClass [key=" + key + ", name=" + name + ", nameUpperCase="
				+ nameUpperCase + ", description=" + description
				+ ", levelKey=" + levelKey + ", classTypeKey=" + classTypeKey
				+ ", active=" + active + ", academicYearKey=" + academicYearKey
				+ ", locationKey=" + locationKey + ", classTeacherKey="
				+ classTeacherKey + ", academicYear=" + academicYear
				+ ", location=" + location + ", classTeacher=" + classTeacher
				+ ", academicYearBusinessKey=" + academicYearBusinessKey
				+ ", locationBusinessKey=" + locationBusinessKey
				+ ", classTeacherBusinessKey=" + classTeacherBusinessKey
				+ ", academicYearKeyString=" + academicYearKeyString
				+ ", locationKeyString=" + locationKeyString
				+ ", classTeacherKeyString=" + classTeacherKeyString
				+ ", levelValue=" + levelValue + ", classTypeValue="
				+ classTypeValue + ", eventCalendarId=" + eventCalendarId
				+ ", classSiteName=" + classSiteName
				+ ", assignmentCalendarId=" + assignmentCalendarId
				+ ", isDeleted=" + isDeleted + "]";
	}

	@Override
	public void jdoPreStore() {
		if (name != null) {
			nameUpperCase = name.toUpperCase();
		} else {
			nameUpperCase = null;
		}
	}

	@Override
	public boolean equals(Object myclass) {
		boolean result = false;
		if (((MyClass) myclass).getKey().equals(this.getKey())) {
			result = true;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(String.valueOf(this.getKey().getId()));

	}

}