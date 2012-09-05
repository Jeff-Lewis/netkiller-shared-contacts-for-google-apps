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

/**
 * @author Jitender
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class SubjectEvaluationEvent implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key classKey;

	@Persistent
	private Key subjectKey;

	@NotPersistent
	private Double weightage;
	
	public Double getWeightage() {
		return weightage;
	}

	public void setWeightage(Double weightage) {
		this.weightage = weightage;
	}

	@Persistent
	private Key teacherKey;

	@Persistent
	private Key evaluationComponentKey;

	@Persistent
	private Key evaluationSubComponentKey;

	@Persistent
	private String eventName;

	@Persistent
	private String eventNameUpperCase;

	public String getEventNameUpperCase() {
		return eventNameUpperCase;
	}

	public void setEventNameUpperCase(String eventNameUpperCase) {
		this.eventNameUpperCase = eventNameUpperCase;
	}

	@Persistent
	private Date datetime;

	@Persistent
	private Double maxMarks;

	@Persistent
	private Key componentCalcMethodKey;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	@NotPersistent
	private Value componentCalcMethod;
	
	@Persistent
	private String  calculationMethod;

	public String getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	@Persistent
	private Key termKey;

	@Persistent
	private Key stageKey;

	@Persistent
	private Integer sequenceNumber;

	@Persistent
	private Boolean isEnabled = false;

	@Persistent
	private Boolean isTemplateGenerated = false;
	
	@Persistent
	private Boolean isVisible = false;
	
	@Persistent
	private Boolean isSubmitMarks = false;

	public Boolean getIsSubmitMarks() {
		return isSubmitMarks;
	}

	public void setIsSubmitMarks(Boolean isSubmitMarks) {
		this.isSubmitMarks = isSubmitMarks;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the isTemplateGenerated
	 */
	public Boolean getIsTemplateGenerated() {
		return isTemplateGenerated;
	}

	/**
	 * @param isTemplateGenerated
	 *            the isTemplateGenerated to set
	 */
	public void setIsTemplateGenerated(Boolean isTemplateGenerated) {
		this.isTemplateGenerated = isTemplateGenerated;
	}

	/**
	 * @return the isEnabled
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Persistent
	private Key componentStructureKey;

	@NotPersistent
	private MyClass myclass;

	@NotPersistent
	private Subject subject;

	@NotPersistent
	private Teacher teacher;

	public MyClass getMyclass() {
		return myclass;
	}

	public void setMyclass(MyClass myclass) {
		this.myclass = myclass;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@NotPersistent
	private Value componentStructure;

	@NotPersistent
	private EvaluationSubComponent evaluationSubComponent;

	@NotPersistent
	private EvaluationComponent evaluationComponent;

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

	public EvaluationSubComponent getEvaluationSubComponent() {
		return evaluationSubComponent;
	}

	public void setEvaluationSubComponent(
			EvaluationSubComponent evaluationSubComponent) {
		this.evaluationSubComponent = evaluationSubComponent;
	}

	public EvaluationComponent getEvaluationComponent() {
		return evaluationComponent;
	}

	public void setEvaluationComponent(EvaluationComponent evaluationComponent) {
		this.evaluationComponent = evaluationComponent;
	}

	public Key getComponentStructureKey() {
		return componentStructureKey;
	}

	public void setComponentStructureKey(Key componentStructureKey) {
		this.componentStructureKey = componentStructureKey;
	}

	public Value getComponentStructure() {
		return componentStructure;
	}

	public void setComponentStructure(Value componentStructure) {
		this.componentStructure = componentStructure;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Key getTermKey() {
		return termKey;
	}

	public void setTermKey(Key termKey) {
		this.termKey = termKey;
	}

	@NotPersistent
	private Term term;

	public Key getKey() {
		return key;
	}

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

	public Key getEvaluationComponentKey() {
		return evaluationComponentKey;
	}

	public void setEvaluationComponentKey(Key evaluationComponentKey) {
		this.evaluationComponentKey = evaluationComponentKey;
	}

	public Key getEvaluationSubComponentKey() {
		return evaluationSubComponentKey;
	}

	public void setEvaluationSubComponentKey(Key evaluationSubComponentKey) {
		this.evaluationSubComponentKey = evaluationSubComponentKey;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(Double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public Key getComponentCalcMethodKey() {
		return componentCalcMethodKey;
	}

	public void setComponentCalcMethodKey(Key componentCalcMethodKey) {
		this.componentCalcMethodKey = componentCalcMethodKey;
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

	public Value getComponentCalcMethod() {
		return componentCalcMethod;
	}

	public void setComponentCalcMethod(Value componentCalcMethod) {
		this.componentCalcMethod = componentCalcMethod;
	}

	@Override
	public void jdoPreStore() {

		StringBuilder stringBuilder = new StringBuilder();

		if (eventName != null && !(eventName.isEmpty())) {
			stringBuilder.append(eventName.toUpperCase());
		} else {
			eventNameUpperCase = null;
		}

		eventNameUpperCase = stringBuilder.toString();

	}

	/**
	 * @return the stageKey
	 */
	public Key getStageKey() {
		return stageKey;
	}

	/**
	 * @param stageKey
	 *            the stageKey to set
	 */
	public void setStageKey(Key stageKey) {
		this.stageKey = stageKey;
	}

	@NotPersistent
	private EvaluationStage evaluationStage;

	/**
	 * @return the evaluationStage
	 */
	public EvaluationStage getEvaluationStage() {
		return evaluationStage;
	}

	/**
	 * @param evaluationStage
	 *            the evaluationStage to set
	 */
	public void setEvaluationStage(EvaluationStage evaluationStage) {
		this.evaluationStage = evaluationStage;
	}

	@Override
	public String toString() {
		return "SubjectEvaluationEvent [key=" + key + ", classKey=" + classKey
				+ ", subjectKey=" + subjectKey + ", weightage=" + weightage
				+ ", teacherKey=" + teacherKey + ", evaluationComponentKey="
				+ evaluationComponentKey + ", evaluationSubComponentKey="
				+ evaluationSubComponentKey + ", eventName=" + eventName
				+ ", eventNameUpperCase=" + eventNameUpperCase + ", datetime="
				+ datetime + ", maxMarks=" + maxMarks
				+ ", componentCalcMethodKey=" + componentCalcMethodKey
				+ ", lastModifiedDate=" + lastModifiedDate + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy
				+ ", componentCalcMethod=" + componentCalcMethod
				+ ", calculationMethod=" + calculationMethod + ", termKey="
				+ termKey + ", stageKey=" + stageKey + ", sequenceNumber="
				+ sequenceNumber + ", isEnabled=" + isEnabled
				+ ", isTemplateGenerated=" + isTemplateGenerated
				+ ", isVisible=" + isVisible + ", isSubmitMarks="
				+ isSubmitMarks + ", componentStructureKey="
				+ componentStructureKey + ", myclass=" + myclass + ", subject="
				+ subject + ", teacher=" + teacher + ", componentStructure="
				+ componentStructure + ", evaluationSubComponent="
				+ evaluationSubComponent + ", evaluationComponent="
				+ evaluationComponent + ", isDeleted=" + isDeleted + ", term="
				+ term + ", evaluationStage=" + evaluationStage + "]";
	}

}
