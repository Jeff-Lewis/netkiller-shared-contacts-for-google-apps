package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.Subject;
import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.Assignment;
import com.metacube.ipathshala.entity.MyClass;
import com.metacube.ipathshala.entity.Teacher;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

@Component("AssignmentMetaData")
public class AssignmentMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Assignment";
	public static final Class<?> entityClass = Assignment.class;
	public static final String COL_ASSIGNMENT_KEY = "key";
	public static final String COL_TITLE = "title";
	public static final String COL_TITLE_UPPER_CASE = "titleUpperCase";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_SUBMISSION_DATE = "submissionDate";
	public static final String COL_POSTED_DATE = "postedDate";
	public static final String COL_ACTIVE = "active";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_ATTACHMENT_ID = "attachmentId";
	public static final String COL_CALENDAR_EVENT_ID = "calendarEventId";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_MYCLASS_KEY = "myclassKey";
	public static final String COL_TEACHER_KEY = "teacherKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	@Autowired
	public AssignmentMetaData(MyClassMetaData myClassMetaData,
			SubjectMetaData subjectMetaData, TeacherMetaData teacherMetaData, AcademicYearMetaData academicYearMetaData) {
		super(ENTITY_NAME, new String[] { COL_TITLE }, COL_TITLE_UPPER_CASE,
				 new String[] { COL_TITLE }, entityClass, getRelatedEntitiesMap(myClassMetaData,
								subjectMetaData, teacherMetaData, academicYearMetaData), getFilterList());
	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(
			MyClassMetaData myClassMetaData, SubjectMetaData subjectMetaData,
			TeacherMetaData teacherMetaData, AcademicYearMetaData academicYearMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Teacher.class, teacherMetaData);
		relatedEntitiesMap.put(MyClass.class, myClassMetaData);
		relatedEntitiesMap.put(Subject.class, subjectMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		return relatedEntitiesMap;
	}
	
	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_ASSIGNMENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_TITLE, ColumnMetaData.ColumnType.STRING,true);
		addColumnMetaData(COL_TITLE_UPPER_CASE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DESCRIPTION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SUBMISSION_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_POSTED_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_ATTACHMENT_ID, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_CALENDAR_EVENT_ID,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_MYCLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(MyClass.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TEACHER_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Teacher.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));

	}

}
