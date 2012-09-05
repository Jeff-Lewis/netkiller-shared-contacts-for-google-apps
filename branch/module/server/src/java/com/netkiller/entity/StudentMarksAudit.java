package com.netkiller.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class StudentMarksAudit implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key studentMarksKey;

	public Key getStudentMarksKey() {
		return studentMarksKey;
	}

	public void setStudentMarksKey(Key studentMarksKey) {
		this.studentMarksKey = studentMarksKey;
	}

	@Persistent
	private Key subjectEvaluationEventKey;

	@Persistent
	private Key studentKey;

	@Persistent
	private Double marks;

	@Persistent
	private String grade;

	@Persistent
	private String comments;

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
		return "StudentMarks [key=" + key + ", subjectEvaluationEventKey="
				+ subjectEvaluationEventKey + ", studentKey=" + studentKey
				+ ", marks=" + marks + ", grade=" + grade + ", components="
				+ comments + ", lastModifiedDate=" + lastModifiedDate
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + "]";
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getSubjectEvaluationEventKey() {
		return subjectEvaluationEventKey;
	}

	public void setSubjectEvaluationEventKey(Key subjectEvaluationEventKey) {
		this.subjectEvaluationEventKey = subjectEvaluationEventKey;
	}

	public Key getStudentKey() {
		return studentKey;
	}

	public void setStudentKey(Key studentKey) {
		this.studentKey = studentKey;
	}

	public Double getMarks() {
		return marks;
	}

	public void setMarks(Double marks) {
		this.marks = marks;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
	public void jdoPreStore() {
		// TODO Auto-generated method stub

	}

}

