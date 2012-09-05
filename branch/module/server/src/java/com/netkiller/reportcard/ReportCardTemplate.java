package com.netkiller.reportcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.EvaluationScheme;
import com.netkiller.entity.GradingScaleSteps;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.Student;
import com.netkiller.entity.StudentMiscellaneous;
import com.netkiller.util.AppLogger;

public class ReportCardTemplate {

	private MyClass myClass;
	private String studentImageUrl;
	private String schoolLogoImageUrl;
	private String overallRemarks;
	private String overallGrade;
	private String classMentor;
	private String principal;
	private boolean isAcademicYearLevel;
	private StudentMiscellaneous studentMiscellaneous;
	private Map<String, EvaluationScheme> subjectSchemeMap;
	private AcademicYear academicYearSession;

	private Map<String, SubjectInfo> subjectInfoMap;
	private Map<Key, List<GradingScaleSteps>> gradingScaleStepMap;
	// map of term name and termInfo
	private Map<String, TermInfo> termInfoMap;

	private final String NOT_FOUND_VALUE = "NF";
	private final String INVALID_VALUE = "INVALID";
	public final static String ATTRIB_THEORY = "_THEORY";
	public final static String ATTRIB_PRACTICAL = "_PRACTICAL";

	public enum Operator {

		PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/");

		Operator(String value) {
			this.value = value;
		}

		private final String value;

		public String value() {
			return value;
		}

	};

	public ReportCardTemplate() {
		this.subjectInfoMap = new HashMap<String, SubjectInfo>();
		this.termInfoMap = new HashMap<String, TermInfo>();
	}

	public String getSchoolLogoImageUrl() {
		return schoolLogoImageUrl;
	}

	public void setSchoolLogoImageUrl(String schoolLogoImageUrl) {
		this.schoolLogoImageUrl = schoolLogoImageUrl;
	}

	public String getOverallRemarks() {
		return overallRemarks;
	}

	public void setOverallRemarks(String overallRemarks) {
		this.overallRemarks = overallRemarks;
	}

	public String getOverallGrade() {
		return overallGrade;
	}

	public void setOverallGrade(String overallGrade) {
		this.overallGrade = overallGrade;
	}

	public String getClassMentor() {
		return classMentor;
	}

	public void setClassMentor(String classMentor) {
		this.classMentor = classMentor;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Map<Key, List<GradingScaleSteps>> getGradingScaleStepMap() {
		return gradingScaleStepMap;
	}

	public void setGradingScaleStepMap(
			Map<Key, List<GradingScaleSteps>> gradingScaleStepMap) {
		this.gradingScaleStepMap = gradingScaleStepMap;
	}

	public String getINVALID_VALUE() {
		return INVALID_VALUE;
	}

	public String getNOT_FOUND_VALUE() {
		return NOT_FOUND_VALUE;
	}

	public static String getAttribTheory() {
		return ATTRIB_THEORY;
	}

	public static String getAttribPractical() {
		return ATTRIB_PRACTICAL;
	}

	public Map<String, TermInfo> getTermInfoMap() {
		return termInfoMap;
	}

	public void setTermInfoMap(Map<String, TermInfo> termInfoMap) {
		this.termInfoMap = termInfoMap;
	}

	public Map<String, SubjectInfo> getSubjectInfoMap() {
		return subjectInfoMap;
	}

	public void setSubjectInfoMap(Map<String, SubjectInfo> subjectInfoMap) {
		this.subjectInfoMap = subjectInfoMap;
	}

	public AcademicYear getAcademicYearSession() {
		return academicYearSession;
	}

	public void setAcademicYearSession(AcademicYear academicYearSession) {
		this.academicYearSession = academicYearSession;
	}

	public MyClass getMyClass() {
		return myClass;
	}

	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}

	public boolean isAcademicYearLevel() {
		return isAcademicYearLevel;
	}

	public void setAcademicYearLevel(boolean isAcademicYearLevel) {
		this.isAcademicYearLevel = isAcademicYearLevel;
	}

	private Student student;

	public String getStudentImageUrl() {
		return studentImageUrl;
	}

	public void setStudentImageUrl(String studentImageUrl) {
		this.studentImageUrl = studentImageUrl;
	}

	public Map<String, EvaluationScheme> getSubjectSchemeMap() {
		return subjectSchemeMap;
	}

	public void setSubjectSchemeMap(
			Map<String, EvaluationScheme> subjectSchemeMap) {
		this.subjectSchemeMap = subjectSchemeMap;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public StudentMiscellaneous getStudentMiscellaneous() {
		return studentMiscellaneous;
	}

	public void setStudentMiscellaneous(
			StudentMiscellaneous studentMiscellaneous) {
		this.studentMiscellaneous = studentMiscellaneous;
	}

	private static final AppLogger log = AppLogger
			.getLogger(ReportCardTemplate.class);

	public String getStageLevelMarks(String termName, String stageName,
			String subjectName) {

		// TODO : Need to add support for other operators such as multiply,
		// subtract, divide etc..

		if (!termName.contains("+") && !stageName.contains("+")) {
			return getMarksByTermAndStage(termName, stageName, subjectName);
		} else if (!termName.contains("+") && stageName.contains("+")) {
			String stageArr[] = stageName.split("\\+");
			List<String> obtainedMarksArr = new ArrayList<String>(
					stageArr.length);
			for (String subStageName : stageArr) {
				obtainedMarksArr.add(getMarksByTermAndStage(termName,
						subStageName, subjectName));
			}
			return calculateOverallMarks(obtainedMarksArr);
		} else if (termName.contains("+") && stageName.contains("+")) {
			String stageArr[] = stageName.split("\\+");
			List<String> obtainedMarksArr = new ArrayList<String>(
					stageArr.length);

			for (String subStage : stageArr) {
				// Here the sub stage name should be in the format
				// TermName_StageName
				if (subStage.contains("_")) {
					String termStageArr[] = subStage.split("_");
					String subTermName = termStageArr[0];
					String subStageName = termStageArr[1];
					obtainedMarksArr.add(getMarksByTermAndStage(subTermName,
							subStageName, subjectName));
				}
			}
			return calculateOverallMarks(obtainedMarksArr);
		}

		// Code should not reach here..
		return INVALID_VALUE;

	}

	private String calculateOverallMarks(List<String> obtainedMarksArr) {

		double overallMarks = 0.0;
		boolean isFound = false;
		for (String marks : obtainedMarksArr) {
			if (!marks.equals(NOT_FOUND_VALUE) && !StringUtils.isEmpty(marks)) {
				overallMarks += Double.valueOf(marks);
				isFound = true;
			}
		}

		if (isFound) {
			return String.valueOf(overallMarks);
		} else {
			return "";
		}
	}

	private String getMarksByTermAndStage(String termName, String stageName,
			String subjectName) {
		String toBeReturnedValue = NOT_FOUND_VALUE;
		termName = termName.trim();
		stageName = stageName.trim();
		// log.error("\n\nSubject name is  : " + subjectName);
		TermInfo termInfo = this.getTermInfoMap().get(termName);
		if (termInfo != null) {
			// log.error("Term name is  : " + termName);
			StageInfo stageInfo = termInfo.getStageInfoMap().get(stageName);
			if (stageInfo != null) {
				// log.error("Stage name is  : " + stageName);
				SubjectInfo subjectInfo = stageInfo.getSubjectInfoMap().get(
						subjectName);
				if (subjectInfo != null) {
					MarksInfo marksInfo = subjectInfo.getMarksInfo();
					// log.error("Marks is  : " + marksInfo.toString());
					if (marksInfo != null) {
						if (marksInfo.getMarks() != null) {
							toBeReturnedValue = String.valueOf(marksInfo
									.getMarks());
						}
					}
				}
			}
		}
		// log.error("\n\n");
		if (toBeReturnedValue == null) {
			toBeReturnedValue = "";
		}
		return toBeReturnedValue;
	}

	public String getTermLevelMarks(String termName, String subjectName) {
		String toBeReturnedValue = NOT_FOUND_VALUE;
		if (this.getTermInfoMap() != null) {
			TermInfo termInfo = this.getTermInfoMap().get(termName);
			if (termInfo != null) {
				SubjectInfo subjectInfo = termInfo.getSubjectInfoMap().get(
						subjectName);
				if (subjectInfo != null) {
					MarksInfo marksInfo = subjectInfo.getMarksInfo();
					if (marksInfo != null) {
						if (marksInfo.getMarks() != null) {
							toBeReturnedValue = String.valueOf(marksInfo
									.getMarks());
						}
					}
				}
			}
		}
		if (toBeReturnedValue == null) {
			toBeReturnedValue = "";
		}
		return toBeReturnedValue;

	}

	public String getAcademicYearLevelMarks(String subjectName) {
		String toBeReturnedValue = NOT_FOUND_VALUE;
		if (this.getSubjectInfoMap() != null) {
			SubjectInfo subjectInfo = this.getSubjectInfoMap().get(subjectName);
			if (subjectInfo != null) {
				MarksInfo marksInfo = subjectInfo.getMarksInfo();
				if (marksInfo != null) {
					if (marksInfo.getMarks() != null) {
						toBeReturnedValue = String
								.valueOf(marksInfo.getMarks());
					}
				}
			}
		}
		if (toBeReturnedValue == null) {
			toBeReturnedValue = "";
		}
		return toBeReturnedValue;
	}

	public Map<String, ComponentInfo> getCoScolasticMapForSubject(
			String subjectName) {
		SubjectInfo subjectInfo = this.getSubjectInfoMap().get(subjectName);
		if (subjectInfo != null) {
			return subjectInfo.getComponentMap();
		}
		return null;
	}

	public void getNumericSchemeGradeInfo() {
		if (this.getSubjectSchemeMap() != null) {
			for (String subject : this.getSubjectSchemeMap().keySet()) {
				EvaluationScheme sch = this.getSubjectSchemeMap().get(subject);
				if (sch.getSchemeEvalType().getValueUpperCase()
						.equals("NUMERIC")) {
					sch.getGradingScale();
				}
			}
		}
	}

	public String getTotalAttendanceForTerm(String termName) {
		String toBeReturnedValue = NOT_FOUND_VALUE;
		if (this.getTermInfoMap() != null) {
			TermInfo termInfo = this.getTermInfoMap().get(termName);
			if (termInfo != null) {
				toBeReturnedValue = termInfo.getTotalAttendance();
			}
		}
		if (toBeReturnedValue == null) {
			toBeReturnedValue = "";
		}
		return toBeReturnedValue;
	}

	public String getTotalWorkingDaysForTerm(String termName) {
		String toBeReturnedValue = NOT_FOUND_VALUE;
		if (this.getTermInfoMap() != null) {
			TermInfo termInfo = this.getTermInfoMap().get(termName);
			if (termInfo != null) {
				toBeReturnedValue = termInfo.getNoOfWorkingDays();
			}
		}
		if (toBeReturnedValue == null) {
			toBeReturnedValue = "";
		}
		return toBeReturnedValue;
	}

	public String getFormattedStageName(String stageName) {
		if (!stageName.contains("_")) {
			return stageName;
		} else {
			// As per the converntion, stage name should be either
			// "termName_stageName" or "termName_StageName + termName_StageName"
			if (stageName.contains("+")) {
				String tempArr[] = stageName.split("\\+");
				String formattedString = "";
				for (String subStageName : tempArr) {
					formattedString += subStageName.split("_")[1] + "+";
				}
				formattedString = formattedString.substring(0,
						formattedString.lastIndexOf("+"));
				return formattedString;
			} else {
				return stageName.split("_")[1];
			}
		}
	}

	public boolean isSubjectTheory(String subjectName) {
		if (subjectName.contains(ATTRIB_THEORY)) {
			return true;
		}
		return false;
	}

	public boolean isSubjectPractical(String subjectName) {
		if (subjectName.contains(ATTRIB_PRACTICAL)) {
			return true;
		} else {
			return false;
		}
	}

	public Date getTermEndDate(String termName) {
		Date toBeReturnedValue = null;
		if (this.getTermInfoMap() != null) {
			TermInfo termInfo = this.getTermInfoMap().get(termName);
			if (termInfo != null) {
				toBeReturnedValue = termInfo.getTermEndDate();
			}
		}
		return toBeReturnedValue;
	}

}
