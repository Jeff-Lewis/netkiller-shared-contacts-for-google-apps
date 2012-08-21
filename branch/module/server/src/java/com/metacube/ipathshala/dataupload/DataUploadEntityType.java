package com.metacube.ipathshala.dataupload;

public class DataUploadEntityType {

	public static final int ACADEMIC_YEAR_ENTITY = 1;
	public static final int MYCLASS_ENTITY = 2;
	public static final int STUDENT_ENTITY = 3;
	public static final int PARENT_ENTITY = 4;
	public static final int SUBJECT_ENTITY = 5;
	public static final int TEACHER_ENTITY = 6;
	public static final int CLASSSTUDENT_ENTITY = 7;
	public static final int CLASSSUBJECTTEACHER_ENTITY = 8;
	public static final int TIME_TABLE_ENTRY_ENTITY = 9;
	public static final int PERIOD_ENTITY = 10;
	public static final int WEEKDAY_ENTITY = 11;
	public static final int STUDENT_UPDATE_ENTITY = 12;
	
	public static final String S_ACADEMIC_YEAR_ENTITY = "AcademicYear";
	public static final String S_MYCLASS_ENTITY = "Class";
	public static final String S_STUDENT_ENTITY = "Student";
	public static final String S_PARENT_ENTITY = "Parent";
	public static final String S_SUBJECT_ENTITY = "Subject";
	public static final String S_TEACHER_ENTITY = "Teacher";
	public static final String S_CLASSSTUDENT_ENTITY = "ClassStudent";
	public static final String S_CLASSSUBJECTTEACHER_ENTITY = "ClassSubjectTeacher";
	public static final String S_TIME_TABLE_ENTRY_ENTITY = "TimetableEntry";
	public static final String S_PERIOD_ENTITY = "Period";
	public static final String S_WEEKDAY_ENTITY = "Weekday";
	public static final String S_STUDENT_UPDATE_ENTITY = "StudentUpdate";
	
	public static int toInt(String entityType){		
		if(entityType==null)
			return 0;
		
		if(entityType.equalsIgnoreCase(S_ACADEMIC_YEAR_ENTITY)){
			return ACADEMIC_YEAR_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_MYCLASS_ENTITY)){
			return MYCLASS_ENTITY;
		} else if(entityType.equalsIgnoreCase(S_STUDENT_ENTITY)){
			return STUDENT_ENTITY;
		} else if(entityType.equalsIgnoreCase(S_PARENT_ENTITY)){
			return PARENT_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_SUBJECT_ENTITY)){
			return SUBJECT_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_TEACHER_ENTITY)){
			return TEACHER_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_CLASSSTUDENT_ENTITY)){
			return CLASSSTUDENT_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_CLASSSUBJECTTEACHER_ENTITY)){
			return CLASSSUBJECTTEACHER_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_TIME_TABLE_ENTRY_ENTITY)){
			return TIME_TABLE_ENTRY_ENTITY;
		}
		else if(entityType.equalsIgnoreCase(S_PERIOD_ENTITY)){
				return PERIOD_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_WEEKDAY_ENTITY)){
			return WEEKDAY_ENTITY;
		}else if(entityType.equalsIgnoreCase(S_STUDENT_UPDATE_ENTITY)){
			return STUDENT_UPDATE_ENTITY;
		}
		return 0;
	}
	
	public static String toString(int entityType){		
		if(entityType==0)
			return null;
		
		if(entityType==ACADEMIC_YEAR_ENTITY){
			return S_ACADEMIC_YEAR_ENTITY;
		}else if(entityType==MYCLASS_ENTITY){
			return S_MYCLASS_ENTITY;
		} else if(entityType==STUDENT_ENTITY){
			return S_STUDENT_ENTITY;
		} else if(entityType==PARENT_ENTITY){
			return S_PARENT_ENTITY;
		}else if(entityType==SUBJECT_ENTITY){
			return S_SUBJECT_ENTITY;
		}else if(entityType==TEACHER_ENTITY){
			return S_TEACHER_ENTITY;
		}else if(entityType==CLASSSTUDENT_ENTITY){
			return S_CLASSSTUDENT_ENTITY;
		}else if(entityType==CLASSSUBJECTTEACHER_ENTITY){
			return S_CLASSSUBJECTTEACHER_ENTITY;
		}else if(entityType==TIME_TABLE_ENTRY_ENTITY){
			return S_TIME_TABLE_ENTRY_ENTITY;
		}else if(entityType == PERIOD_ENTITY){
			return S_PERIOD_ENTITY;
		}else if(entityType == WEEKDAY_ENTITY){
			return S_WEEKDAY_ENTITY;
		}else if(entityType==STUDENT_UPDATE_ENTITY){
			return S_STUDENT_UPDATE_ENTITY;
		}
		   
		return null;
	}
	
}
