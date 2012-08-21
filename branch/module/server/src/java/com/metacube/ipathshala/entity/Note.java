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
 * Academic_Year entity.
 * 
 * @author knagar
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Note implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key academicSessionId;

	
	@NotPersistent
	private Student student;
	
	@Persistent
	private Key studentId;
	
	@NotPersistent
	private String studentKeyString ;
	
	@NotPersistent
	private String studentBusinessKey ;

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
	 * @return the studentKeyString
	 */
	public String getStudentKeyString() {
		return studentKeyString;
	}

	/**
	 * @param studentKeyString the studentKeyString to set
	 */
	public void setStudentKeyString(String studentKeyString) {
		this.studentKeyString = studentKeyString;
	}

	/**
	 * @return the studentBusinessKey
	 */
	public String getStudentBusinessKey() {
		return studentBusinessKey;
	}

	/**
	 * @param studentBusinessKey the studentBusinessKey to set
	 */
	public void setStudentBusinessKey(String studentBusinessKey) {
		this.studentBusinessKey = studentBusinessKey;
	}

	/**
	 * @return the noteSubjectUpperCase
	 */
	public String getNoteSubjectUpperCase() {
		return noteSubjectUpperCase;
	}

	/**
	 * @param noteSubjectUpperCase the noteSubjectUpperCase to set
	 */
	public void setNoteSubjectUpperCase(String noteSubjectUpperCase) {
		this.noteSubjectUpperCase = noteSubjectUpperCase;
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
	 * @return the parentKeyString
	 */
	public String getParentKeyString() {
		return parentKeyString;
	}

	/**
	 * @param parentKeyString the parentKeyString to set
	 */
	public void setParentKeyString(String parentKeyString) {
		this.parentKeyString = parentKeyString;
	}

	/**
	 * @return the parentBusinessKey
	 */
	public String getParentBusinessKey() {
		return parentBusinessKey;
	}

	/**
	 * @param parentBusinessKey the parentBusinessKey to set
	 */
	public void setParentBusinessKey(String parentBusinessKey) {
		this.parentBusinessKey = parentBusinessKey;
	}


	@Persistent
	private Date noteDate;

	@Persistent
	private String noteSubject;
	
	@Persistent
	private String noteSubjectUpperCase;
	
	@Persistent
	private String noteDescription;
	
	@NotPersistent
	private Value notePriorityValue;

	@Persistent
	private Key notePriorityKey;
	
	@Persistent
	private String sender;
	
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}


	@NotPersistent
	private Value senderValue;

	@Persistent
	private Key senderKey;
	
	
	@NotPersistent
	private Teacher teacher;

	@Persistent
	private Key teacherId;
	
	@NotPersistent
	private String teacherKeyString ;
	
	@NotPersistent
	private String teacherBusinessKey ;

	@Persistent
	private boolean visibleToStudent;

	@Persistent
	private boolean mustRespond;
	
	@Persistent
	private Key noteStatusKey;
	
	
	@NotPersistent
	private Value noteStatusValue;
	
	@NotPersistent
	private String userId;
	
	@Persistent
	private Boolean isDeleted = false ;
	
	@Persistent
	private Boolean sendSMS;
	
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	@Persistent
	private Date dueDate;
	
	@NotPersistent
	private Parent parent;

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
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
	 * @return the parent
	 */
	public Parent getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}


	@Persistent
	private Key parentId;
	
	@NotPersistent
	private String parentKeyString ;
	
	@NotPersistent
	private String parentBusinessKey ;

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
	 * @return the academicSessionId
	 */
	public Key getAcademicSessionId() {
		return academicSessionId;
	}

	/**
	 * @param academicSessionId the academicSessionId to set
	 */
	public void setAcademicSessionId(Key academicSessionId) {
		this.academicSessionId = academicSessionId;
	}

	/**
	 * @return the studentId
	 */
	public Key getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Key studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the noteDate
	 */
	public Date getNoteDate() {
		return noteDate;
	}

	/**
	 * @param noteDate the noteDate to set
	 */
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	/**
	 * @return the noteSubject
	 */
	public String getNoteSubject() {
		return noteSubject;
	}

	/**
	 * @param noteSubject the noteSubject to set
	 */
	public void setNoteSubject(String noteSubject) {
		this.noteSubject = noteSubject;
	}

	/**
	 * @return the noteDescription
	 */
	public String getNoteDescription() {
		return noteDescription;
	}

	/**
	 * @param noteDescription the noteDescription to set
	 */
	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}



	/**
	 * @return the teacherId
	 */
	public Key getTeacherId() {
		return teacherId;
	}

	/**
	 * @param teacherId the teacherId to set
	 */
	public void setTeacherId(Key teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * @return the visibleToStudent
	 */
	public boolean isVisibleToStudent() {
		return visibleToStudent;
	}

	/**
	 * @param visibleToStudent the visibleToStudent to set
	 */
	public void setVisibleToStudent(boolean visibleToStudent) {
		this.visibleToStudent = visibleToStudent;
	}

	/**
	 * @return the mustRespond
	 */
	public boolean getMustRespond() {
		return mustRespond;
	}

	/**
	 * @param mustRespond the mustRespond to set
	 */
	public void setMustRespond(boolean mustRespond) {
		this.mustRespond = mustRespond;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the parentId
	 */
	public Key getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Key parentId) {
		this.parentId = parentId;
	}

	@Override
	public void jdoPreStore() {	
		if (noteSubject != null) {
			noteSubjectUpperCase = noteSubject.toUpperCase();
		} else {
			noteSubjectUpperCase = null;
		}
	}



	@Override
	public String toString() {
		return "Note [key=" + key + ", academicSessionId=" + academicSessionId
				+ ", student=" + student + ", studentId=" + studentId
				+ ", studentKeyString=" + studentKeyString
				+ ", studentBusinessKey=" + studentBusinessKey + ", noteDate="
				+ noteDate + ", noteSubject=" + noteSubject
				+ ", noteSubjectUpperCase=" + noteSubjectUpperCase
				+ ", noteDescription=" + noteDescription
				+ ", notePriorityValue=" + notePriorityValue
				+ ", notePriorityKey=" + notePriorityKey + ", sender=" + sender
				+ ", senderValue=" + senderValue + ", senderKey=" + senderKey
				+ ", teacher=" + teacher + ", teacherId=" + teacherId
				+ ", teacherKeyString=" + teacherKeyString
				+ ", teacherBusinessKey=" + teacherBusinessKey
				+ ", visibleToStudent=" + visibleToStudent + ", mustRespond="
				+ mustRespond + ", noteStatusKey=" + noteStatusKey
				+ ", noteStatusValue=" + noteStatusValue + ", userId=" + userId
				+ ", isDeleted=" + isDeleted + ", sendSMS=" + sendSMS
				+ ", dueDate=" + dueDate + ", parent=" + parent + ", parentId="
				+ parentId + ", parentKeyString=" + parentKeyString
				+ ", parentBusinessKey=" + parentBusinessKey + "]";
	}

	/**
	 * @return the notePriorityKeyValue
	 */
	public Value getNotePriorityValue() {
		return notePriorityValue;
	}

	/**
	 * @param notePriorityKeyValue the notePriorityKeyValue to set
	 */
	public void setNotePriorityValue(Value notePriorityValue) {
		this.notePriorityValue = notePriorityValue;
	}

	/**
	 * @return the notePriorityKey
	 */
	public Key getNotePriorityKey() {
		return notePriorityKey;
	}

	/**
	 * @param notePriorityKey the notePriorityKey to set
	 */
	public void setNotePriorityKey(Key notePriorityKey) {
		this.notePriorityKey = notePriorityKey;
	}

	/**
	 * @return the senderValue
	 */
	public Value getSenderValue() {
		return senderValue;
	}

	/**
	 * @param senderValue the senderValue to set
	 */
	public void setSenderValue(Value senderValue) {
		this.senderValue = senderValue;
	}

	/**
	 * @return the senderKey
	 */
	public Key getSenderKey() {
		return senderKey;
	}

	/**
	 * @param senderKey the senderKey to set
	 */
	public void setSenderKey(Key senderKey) {
		this.senderKey = senderKey;
	}

	/**
	 * @return the noteStatusKey
	 */
	public Key getNoteStatusKey() {
		return noteStatusKey;
	}

	/**
	 * @param noteStatusKey the noteStatusKey to set
	 */
	public void setNoteStatusKey(Key noteStatusKey) {
		this.noteStatusKey = noteStatusKey;
	}

	/**
	 * @return the noteStatusValue
	 */
	public Value getNoteStatusValue() {
		return noteStatusValue;
	}

	/**
	 * @param noteStatusValue the noteStatusValue to set
	 */
	public void setNoteStatusValue(Value noteStatusValue) {
		this.noteStatusValue = noteStatusValue;
	}

	public Boolean getSendSMS() {
		return sendSMS!=null?sendSMS:false;
	}

	public void setSendSMS(Boolean sendSMS) {
		this.sendSMS = sendSMS;
	}


}
