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
 * TimetableEntry entity.
 * 
 * @author sabir
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class TimetableEntry implements Serializable {
	
	public enum WeekDay {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
	};
	
	public enum RepeatOption {
		ALL, ODD, EVEN
	};

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Key classKey;
	
	@Persistent
	private Key periodKey;
	
	@Persistent
	private Key classSubjectTeacherKey;
	
	@Persistent
	private WeekDay weekDay;
	
	@Persistent
	private RepeatOption repeatOption;
	
	@Persistent
	private Date fromDate;
	
	@Persistent
	private Date toDate;
	
	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	@Override
	public String toString() {
		return "TimetableEntry [key=" + key + ", classKey=" + classKey
				+ ", periodKey=" + periodKey + ", classSubjectTeacherKey="
				+ classSubjectTeacherKey + ", weekDay=" + weekDay
				+ ", repeatOption=" + repeatOption + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", classSubjectTeacher="
				+ classSubjectTeacher + "]";
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

	@NotPersistent
	private ClassSubjectTeacher classSubjectTeacher;

	public Key getClassSubjectTeacherKey() {
		return classSubjectTeacherKey;
	}

	public void setClassSubjectTeacherKey(Key classSubjectTeacherKey) {
		this.classSubjectTeacherKey = classSubjectTeacherKey;
	}

	public ClassSubjectTeacher getClassSubjectTeacher() {
		return classSubjectTeacher;
	}

	public void setClassSubjectTeacher(ClassSubjectTeacher classSubjectTeacher) {
		this.classSubjectTeacher = classSubjectTeacher;
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
	 * @return the periodKey
	 */
	public Key getPeriodKey() {
		return periodKey;
	}

	/**
	 * @param periodKey the periodKey to set
	 */
	public void setPeriodKey(Key periodKey) {
		this.periodKey = periodKey;
	}


	/**
	 * @return the weekDay
	 */
	public WeekDay getWeekDay() {
		return weekDay;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}

	/**
	 * @return the repeatOption
	 */
	public RepeatOption getRepeatOption() {
		return repeatOption;
	}

	/**
	 * @param repeatOption the repeatOption to set
	 */
	public void setRepeatOption(RepeatOption repeatOption) {
		this.repeatOption = repeatOption;
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
	
}
