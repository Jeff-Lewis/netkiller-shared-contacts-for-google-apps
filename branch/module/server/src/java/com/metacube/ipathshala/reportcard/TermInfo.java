/**
 * 
 */
package com.metacube.ipathshala.reportcard;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.metacube.ipathshala.entity.Term;

/**
 * @author vishesh
 * 
 */
public class TermInfo {

	// Optionally we can add here the term object, which might come in use

	private String name;
	private Map<String, StageInfo> stageInfoMap;
	private String noOfWorkingDays;
	private String totalAttendance;
	private Term term;
	private Date termEndDate;

	/**
	 * If the record if for term year level
	 */
	@Deprecated
	private Map<String, MarksInfo> subjectMarksInfoMap;
	@Deprecated
	private Map<String, MarksInfo> componentMarksInfoMap;
	@Deprecated
	private Map<String, MarksInfo> subComponentMarksInfoMap;
	@Deprecated
	private Map<String, MarksInfo> childSubComponentMarksInfoMap;

	private Map<String, SubjectInfo> subjectInfoMap;

	public TermInfo() {
		this.subjectMarksInfoMap = new HashMap<String, MarksInfo>();
		this.componentMarksInfoMap = new HashMap<String, MarksInfo>();
		this.subComponentMarksInfoMap = new HashMap<String, MarksInfo>();
		this.childSubComponentMarksInfoMap = new HashMap<String, MarksInfo>();
		this.stageInfoMap = new HashMap<String, StageInfo>();
		this.subjectInfoMap = new HashMap<String, SubjectInfo>();
	}

	public Date getTermEndDate() {
		return termEndDate;
	}

	public void setTermEndDate(Date termEndDate) {
		this.termEndDate = termEndDate;
	}

	public Map<String, SubjectInfo> getSubjectInfoMap() {
		return subjectInfoMap;
	}

	public void setSubjectInfoMap(Map<String, SubjectInfo> subjectInfoMap) {
		this.subjectInfoMap = subjectInfoMap;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
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

	public boolean isTermLevel() {
		return isTermLevel;
	}

	public void setTermLevel(boolean isTermLevel) {
		this.isTermLevel = isTermLevel;
	}

	private boolean isTermLevel;

	public String getNoOfWorkingDays() {
		return noOfWorkingDays;
	}

	public void setNoOfWorkingDays(String noOfWorkingDays) {
		this.noOfWorkingDays = noOfWorkingDays;
	}

	public String getTotalAttendance() {
		return totalAttendance;
	}

	public void setTotalAttendance(String totalAttendance) {
		this.totalAttendance = totalAttendance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, StageInfo> getStageInfoMap() {
		return stageInfoMap;
	}

	public void setStageInfoMap(Map<String, StageInfo> stageInfoMap) {
		this.stageInfoMap = stageInfoMap;
	}

	@Override
	public String toString() {
		return "TermInfo [name=" + name + ", stageInfoMap=" + stageInfoMap + ", noOfWorkingDays=" + noOfWorkingDays
				+ ", totalAttendance=" + totalAttendance + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
