/**
 * 
 */
package com.metacube.ipathshala.reportcard;

import java.util.HashMap;
import java.util.Map;

import com.metacube.ipathshala.entity.EvaluationStage;

/**
 * @author vishesh
 * 
 */
public class StageInfo {

	// Optionally we can add here the stage object, which might come in use

	private String stageName;
	private EvaluationStage evaluationStage;

	/**
	 * If the record if for stage level
	 */

	// map of Subject name and marks
	@Deprecated
	private Map<String, MarksInfo> subjectMarksInfoMap;

	// map of component name and marks
	@Deprecated
	private Map<String, MarksInfo> componentMarksInfoMap;

	// map of subcomponent name and marks
	@Deprecated
	private Map<String, MarksInfo> subComponentMarksInfoMap;

	// map of subcomponent name and marks
	@Deprecated
	private Map<String, MarksInfo> childSubComponentMarksInfoMap;

	private Map<String, SubjectInfo> subjectInfoMap;

	public StageInfo() {
		this.subjectMarksInfoMap = new HashMap<String, MarksInfo>();
		this.componentMarksInfoMap = new HashMap<String, MarksInfo>();
		this.subComponentMarksInfoMap = new HashMap<String, MarksInfo>();
		this.childSubComponentMarksInfoMap = new HashMap<String, MarksInfo>();
		this.subjectInfoMap = new HashMap<String, SubjectInfo>();
	}

	public Map<String, SubjectInfo> getSubjectInfoMap() {
		return subjectInfoMap;
	}

	public void setSubjectInfoMap(Map<String, SubjectInfo> subjectInfoMap) {
		this.subjectInfoMap = subjectInfoMap;
	}

	public EvaluationStage getEvaluationStage() {
		return evaluationStage;
	}

	public void setEvaluationStage(EvaluationStage evaluationStage) {
		this.evaluationStage = evaluationStage;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Map<String, MarksInfo> getSubjectMarksInfoMap() {
		return subjectMarksInfoMap;
	}

	public void setSubjectMarksInfoMap(Map<String, MarksInfo> subjectMarksInfoMap) {
		this.subjectMarksInfoMap = subjectMarksInfoMap;
	}

	public Map<String, MarksInfo> getComponentMarksInfoMap() {
		return componentMarksInfoMap;
	}

	public void setComponentMarksInfoMap(Map<String, MarksInfo> componentMarksInfoMap) {
		this.componentMarksInfoMap = componentMarksInfoMap;
	}

	public Map<String, MarksInfo> getSubComponentMarksInfoMap() {
		return subComponentMarksInfoMap;
	}

	public void setSubComponentMarksInfoMap(Map<String, MarksInfo> subComponentMarksInfoMap) {
		this.subComponentMarksInfoMap = subComponentMarksInfoMap;
	}

	public Map<String, MarksInfo> getChildSubComponentMarksInfoMap() {
		return childSubComponentMarksInfoMap;
	}

	public void setChildSubComponentMarksInfoMap(Map<String, MarksInfo> childSubComponentMarksInfoMap) {
		this.childSubComponentMarksInfoMap = childSubComponentMarksInfoMap;
	}

	@Override
	public String toString() {
		return "StageInfo [stageName=" + stageName + ", evaluationStage=" + evaluationStage + ", subjectInfoMap="
				+ subjectInfoMap + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
