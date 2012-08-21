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
import com.google.gdata.data.DateTime;

/**
 * Event entity.
 * 
 * @author knagar
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Event implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String title;

	@Persistent
	private Date fromDate;

	@Persistent
	private Date toDate;

	@NotPersistent
	private String isAllDayEvent;
	
	public String getIsAllDayEvent() {
		return isAllDayEvent;
	}     

	public void setIsAllDayEvent(String isAllDayEvent) {
		this.isAllDayEvent = isAllDayEvent;
	}
	@Persistent
	private String description;
	
	@Persistent
	private Key academicYearKey;
	
	@NotPersistent
	private AcademicYear academicYear;

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

	@Persistent
	private String allDayEvent;
	
	@Persistent
	private Boolean active;

	@Persistent
	private String titleUpperCase;

	@Persistent
	private Key myclassKey;

	@NotPersistent
	private MyClass myClass;
	
	@NotPersistent
	private String myclassBusinessKey;
	
	@NotPersistent
	private String myclassKeyString;
	
	@Persistent
	private Key teacherKey;

	@NotPersistent
	private Teacher teacher;
	
	@NotPersistent
	private String teacherBusinessKey;
	
	@NotPersistent
	private String teacherKeyString;
	
	@Persistent
	private Key eventTypeKey;
	
	@Persistent
	private String sessionAccount;
	
	@NotPersistent
	private Value eventTypeValue;
	
	@Persistent
	private String calendarEventEntryId;

	@Persistent
	private Boolean isDeleted = false ;
	
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the titleUpperCase
	 */
	public String getTitleUpperCase() {
		return titleUpperCase;
	}

	/**
	 * @param titleUpperCase the titleUpperCase to set
	 */
	public void setTitleUpperCase(String titleUpperCase) {
		this.titleUpperCase = titleUpperCase;
	}

	/**
	 * @return the myclassKey
	 */
	public Key getMyclassKey() {
		return myclassKey;
	}

	/**
	 * @param myclassKey the myclassKey to set
	 */
	public void setMyclassKey(Key myclassKey) {
		this.myclassKey = myclassKey;
	}

	/**
	 * @return the myClass
	 */
	public MyClass getMyClass() {
		return myClass;
	}

	/**
	 * @param myClass the myClass to set
	 */
	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}

	/**
	 * @return the myclassBusinessKey
	 */
	public String getMyclassBusinessKey() {
		return myclassBusinessKey;
	}

	/**
	 * @param myclassBusinessKey the myclassBusinessKey to set
	 */
	public void setMyclassBusinessKey(String myclassBusinessKey) {
		this.myclassBusinessKey = myclassBusinessKey;
	}

	/**
	 * @return the myclassKeyString
	 */
	public String getMyclassKeyString() {
		return myclassKeyString;
	}

	/**
	 * @param myclassKeyString the myclassKeyString to set
	 */
	public void setMyclassKeyString(String myclassKeyString) {
		this.myclassKeyString = myclassKeyString;
	}

	/**
	 * @return the teacherKey
	 */
	public Key getTeacherKey() {
		return teacherKey;
	}

	/**
	 * @param teacherKey the teacherKey to set
	 */
	public void setTeacherKey(Key teacherKey) {
		this.teacherKey = teacherKey;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the teacherBusinessKey
	 */
	public String getTeacherBusinessKey() {
		return teacherBusinessKey;
	}

	/**
	 * @param teacherBusinessKey the teacherBusinessKey to set
	 */
	public void setTeacherBusinessKey(String teacherBusinessKey) {
		this.teacherBusinessKey = teacherBusinessKey;
	}

	/**
	 * @return the teacherKeyString
	 */
	public String getTeacherKeyString() {
		return teacherKeyString;
	}

	/**
	 * @param teacherKeyString the teacherKeyString to set
	 */
	public void setTeacherKeyString(String teacherKeyString) {
		this.teacherKeyString = teacherKeyString;
	}

	/**
	 * @return the allDayEvent
	 */
	public String getAllDayEvent() {
		return allDayEvent;
	}

	/**
	 * @param allDayEvent the allDayEvent to set
	 */
	public void setAllDayEvent(String allDayEvent) {
		this.allDayEvent = allDayEvent;
	}
	
	/**
	 * @return the allDayEventKey
	 */
	public Key getEventTypeKey() {
		return eventTypeKey;
	}

	/**
	 * @param allDayEventKey the allDayEventKey to set
	 */
	public void setEventTypeKey(Key eventTypeKey) {
		this.eventTypeKey = eventTypeKey;
	}

	/**
	 * @return the sessionAccount
	 */
	public String getSessionAccount() {
		return sessionAccount;
	}

	/**
	 * @param sessionAccount the sessionAccount to set
	 */
	public void setSessionAccount(String sessionAccount) {
		this.sessionAccount = sessionAccount;
	}

	/**
	 * @return the allDayEventValue
	 */
	public Value getEventTypeValue() {
		return eventTypeValue;
	}

	/**
	 * @param allDayEventValue the allDayEventValue to set
	 */
	public void setEventTypeValue(Value eventTypeValue) {
		this.eventTypeValue = eventTypeValue;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCalendarEventEntryId() {
		return calendarEventEntryId;
	}

	public void setCalendarEventEntryId(String calendarEventEntryId) {
		this.calendarEventEntryId = calendarEventEntryId;
	}

	@Override
	public String toString() {
		return "Event [key=" + key + ", title=" + title + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", description="
				+ description + ", academicYearKey=" + academicYearKey
				+ ", academicYear=" + academicYear + ", allDayEvent="
				+ allDayEvent + ", active=" + active + ", titleUpperCase="
				+ titleUpperCase + ", myclassKey=" + myclassKey + ", myClass="
				+ myClass + ", myclassBusinessKey=" + myclassBusinessKey
				+ ", myclassKeyString=" + myclassKeyString + ", teacherKey="
				+ teacherKey + ", teacher=" + teacher + ", teacherBusinessKey="
				+ teacherBusinessKey + ", teacherKeyString=" + teacherKeyString
				+ ", eventTypeKey=" + eventTypeKey + ", sessionAccount="
				+ sessionAccount + ", eventTypeValue=" + eventTypeValue
				+ ", calendarEventEntryId=" + calendarEventEntryId
				+ ", isDeleted=" + isDeleted + "]";
	}

	@Override
	public void jdoPreStore() {
		if (title != null) {
			titleUpperCase = title.toUpperCase();
		} else {
			titleUpperCase = null;
		}

	}
}