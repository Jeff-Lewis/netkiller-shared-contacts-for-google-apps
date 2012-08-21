package com.metacube.ipathshala.entity;

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
import com.google.gdata.data.DateTime;
import com.metacube.ipathshala.cache.AppCache;

/**
 * Entity that will hold relation information about announcement.
 * 
 * @author Ankit
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Announcement extends EntityCache implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key classKey;

	@Persistent
	private Key subjectKey;
	
	@Persistent
	private Key type;
	
	@Persistent
	private Key academicYearKey;
	
	
	@NotPersistent
	private AcademicYear academicYear;
	
	@NotPersistent
	private String academicYearBusinessKey;
	
	@NotPersistent
	private String academicYearKeyString;
	
	@Persistent
	private String title;
	
	@Persistent
	private String announcementEntryId;
	
	@Persistent
	private String titleUpperCase;
	
	@Persistent
	private String detail;

	@NotPersistent
	private MyClass myclass;

	@NotPersistent
	private Subject subject;
	
	@NotPersistent
	private String classKeyString;
	
	@NotPersistent
	private String subjectKeyString;
	
	@NotPersistent
	private String classBusinessKey;
	
	@NotPersistent
	private String subjectBusinessKey;
	
	@NotPersistent
	private Value announcementTypeValue;
	
	@NotPersistent
	private String announcementTypeKeyString;
	
	@NotPersistent
	private String announcementTypeBusinessKey;

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
	 * @return the subjectKey
	 */
	public Key getSubjectKey() {
		return subjectKey;
	}

	/**
	 * @param subjectKey the subjectKey to set
	 */
	public void setSubjectKey(Key subjectKey) {
		this.subjectKey = subjectKey;
	}

	/**
	 * @return the type
	 */
	public Key getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Key type) {
		this.type = type;
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
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the myclass
	 */
	public MyClass getMyclass() {
		return myclass;
	}

	/**
	 * @param myclass the myclass to set
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
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
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
	 * @return the classKeyString
	 */
	public String getClassKeyString() {
		return classKeyString;
	}

	/**
	 * @param classKeyString the classKeyString to set
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
	 * @param subjectKeyString the subjectKeyString to set
	 */
	public void setSubjectKeyString(String subjectKeyString) {
		this.subjectKeyString = subjectKeyString;
	}

	/**
	 * @return the classBusinessKey
	 */
	public String getClassBusinessKey() {
		return classBusinessKey;
	}

	/**
	 * @param classBusinessKey the classBusinessKey to set
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
	 * @param subjectBusinessKey the subjectBusinessKey to set
	 */
	public void setSubjectBusinessKey(String subjectBusinessKey) {
		this.subjectBusinessKey = subjectBusinessKey;
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
	 * @return the announcementEntryId
	 */
	public String getAnnouncementEntryId() {
		return announcementEntryId;
	}

	/**
	 * @param announcementEntryId the announcementEntryId to set
	 */
	public void setAnnouncementEntryId(String announcementEntryId) {
		this.announcementEntryId = announcementEntryId;
	}

	/**
	 * @return the announcementTypeValue
	 */
	public Value getAnnouncementTypeValue() {
		return announcementTypeValue;
	}

	/**
	 * @param announcementTypeValue the announcementTypeValue to set
	 */
	public void setAnnouncementTypeValue(Value announcementTypeValue) {
		this.announcementTypeValue = announcementTypeValue;
	}


	/**
	 * @return the announcementTypeKeyString
	 */
	public String getAnnouncementTypeKeyString() {
		return announcementTypeKeyString;
	}

	/**
	 * @param announcementTypeKeyString the announcementTypeKeyString to set
	 */
	public void setAnnouncementTypeKeyString(String announcementTypeKeyString) {
		this.announcementTypeKeyString = announcementTypeKeyString;
	}

	/**
	 * @return the announcementTypeBusinessKey
	 */
	public String getAnnouncementTypeBusinessKey() {
		return announcementTypeBusinessKey;
	}

	/**
	 * @param announcementTypeBusinessKey the announcementTypeBusinessKey to set
	 */
	public void setAnnouncementTypeBusinessKey(String announcementTypeBusinessKey) {
		this.announcementTypeBusinessKey = announcementTypeBusinessKey;
	}

	@Override
	protected AppCache getEntityCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setDefaultColumn() {
		if(StringUtils.isNotBlank(getTitle()))
			this.setTitleUpperCase(getTitle().toUpperCase());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Announcement [key=" + key + ", classKey=" + classKey
				+ ", subjectKey=" + subjectKey + ", type=" + type
				+ ", academicYearKey=" + academicYearKey + ", academicYear="
				+ academicYear + ", academicYearBusinessKey="
				+ academicYearBusinessKey + ", academicYearKeyString="
				+ academicYearKeyString + ", title=" + title
				+ ", announcementEntryId=" + announcementEntryId
				+ ", titleUpperCase=" + titleUpperCase + ", detail=" + detail
				+ ", myclass=" + myclass + ", subject=" + subject
				+ ", classKeyString=" + classKeyString + ", subjectKeyString="
				+ subjectKeyString + ", classBusinessKey=" + classBusinessKey
				+ ", subjectBusinessKey=" + subjectBusinessKey
				+ ", announcementTypeValue=" + announcementTypeValue
				+ ", announcementTypeKeyString=" + announcementTypeKeyString
				+ ", announcementTypeBusinessKey="
				+ announcementTypeBusinessKey + ", lastModifiedDate="
				+ lastModifiedDate + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + ", isDeleted=" + isDeleted + "]";
	}
	
	
	
	
	
}