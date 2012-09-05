package com.netkiller.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import org.springframework.web.multipart.MultipartFile;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

/**
 * 
 * @author administrator
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Assignment implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String title;

	@Persistent
	private String titleUpperCase;

	@Persistent
	private Key myclassKey;

	@NotPersistent
	private MyClass myClass;

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

	@NotPersistent
	private String myclassBusinessKey;

	@NotPersistent
	private String myclassKeyString;

	@Persistent
	private Key subjectKey;

	@NotPersistent
	private Subject subject;

	@NotPersistent
	private String subjectBusinessKey;

	@NotPersistent
	private String subjectKeyString;

	@Persistent
	private Key teacherKey;

	@NotPersistent
	private Teacher teacher;

	@NotPersistent
	private String teacherBusinessKey;

	@NotPersistent
	private String teacherKeyString;

	@Persistent
	private String description;

	@Persistent
	private Date submissionDate;

	@Persistent
	private Boolean active;

	// @Persistent
	// private String parentPageId = "736381373835375885";

	@Persistent
	private String assignmentId;

	@NotPersistent
	private byte[] fileByteArray;

	@NotPersistent
	private MultipartFile file;

	@Persistent
	private Date postedDate;

	@Persistent
	private Boolean isDeleted = false;

	@Persistent
	private String calendarEventId;

	@Persistent
	private String attachmentId;

	/**
	 * @return the attachmentId
	 */
	public String getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId
	 *            the attachmentId to set
	 */
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * @return the calendarEventId
	 */
	public String getCalendarEventId() {
		return calendarEventId;
	}

	/**
	 * @param calendarEventId
	 *            the calendarEventId to set
	 */
	public void setCalendarEventId(String calendarEventId) {
		this.calendarEventId = calendarEventId;
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
	 * @return the postedDate
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * @param postedDate
	 *            the postedDate to set
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	/**
	 * @return the file
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleUpperCase() {
		return titleUpperCase;
	}

	public void setTitleUpperCase(String titleUpperCase) {
		this.titleUpperCase = titleUpperCase;
	}

	public Key getMyclassKey() {
		return myclassKey;
	}

	public void setMyclassKey(Key myclassKey) {
		this.myclassKey = myclassKey;
	}

	public MyClass getMyClass() {
		return myClass;
	}

	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}

	public String getMyclassBusinessKey() {
		return myclassBusinessKey;
	}

	public void setMyclassBusinessKey(String myclassBusinessKey) {
		this.myclassBusinessKey = myclassBusinessKey;
	}

	public String getMyclassKeyString() {
		return myclassKeyString;
	}

	public void setMyclassKeyString(String myclassKeyString) {
		this.myclassKeyString = myclassKeyString;
	}

	public Key getSubjectKey() {
		return subjectKey;
	}

	public void setSubjectKey(Key subjectKey) {
		this.subjectKey = subjectKey;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getSubjectBusinessKey() {
		return subjectBusinessKey;
	}

	public void setSubjectBusinessKey(String subjectBusinessKey) {
		this.subjectBusinessKey = subjectBusinessKey;
	}

	public String getSubjectKeyString() {
		return subjectKeyString;
	}

	public void setSubjectKeyString(String subjectKeyString) {
		this.subjectKeyString = subjectKeyString;
	}

	public Key getTeacherKey() {
		return teacherKey;
	}

	public void setTeacherKey(Key teacherKey) {
		this.teacherKey = teacherKey;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getTeacherBusinessKey() {
		return teacherBusinessKey;
	}

	public void setTeacherBusinessKey(String teacherBusinessKey) {
		this.teacherBusinessKey = teacherBusinessKey;
	}

	public String getTeacherKeyString() {
		return teacherKeyString;
	}

	public void setTeacherKeyString(String teacherKeyString) {
		this.teacherKeyString = teacherKeyString;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	/*
	 * public String getParentPageId() { return parentPageId; }
	 * 
	 * public void setParentPageId(String parentPageId) { this.parentPageId =
	 * parentPageId; }
	 */

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public byte[] getFileByteArray() {
		return fileByteArray;
	}

	public void setFileByteArray(byte[] fileByteArray) {
		this.fileByteArray = fileByteArray;
	}

	@Override
	public String toString() {
		return "Assignment [key=" + key + ", title=" + title
				+ ", titleUpperCase=" + titleUpperCase + ", myclassKey="
				+ myclassKey + ", myClass=" + myClass + ", academicYearKey="
				+ academicYearKey + ", myclassBusinessKey="
				+ myclassBusinessKey + ", myclassKeyString=" + myclassKeyString
				+ ", subjectKey=" + subjectKey + ", subject=" + subject
				+ ", subjectBusinessKey=" + subjectBusinessKey
				+ ", subjectKeyString=" + subjectKeyString + ", teacherKey="
				+ teacherKey + ", teacher=" + teacher + ", teacherBusinessKey="
				+ teacherBusinessKey + ", teacherKeyString=" + teacherKeyString
				+ ", description=" + description + ", submissionDate="
				+ submissionDate + ", active=" + active + ", assignmentId="
				+ assignmentId + ", fileByteArray="
				+ Arrays.toString(fileByteArray) + ", file=" + file
				+ ", postedDate=" + postedDate + ", isDeleted=" + isDeleted
				+ ", calendarEventId=" + calendarEventId + ", attachmentId="
				+ attachmentId + "]";
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
