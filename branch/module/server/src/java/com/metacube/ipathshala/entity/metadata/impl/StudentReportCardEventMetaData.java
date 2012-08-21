/**
 * 
 */
package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.ReportCardEvent;
import com.metacube.ipathshala.entity.Student;
import com.metacube.ipathshala.entity.StudentReportCardEvent;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * @author ankit gupta
 * 
 */

/*
 * The bean name is set to "StudentReportCardEventMetaData". This name should be used in
 * the
 * 
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See StudentReportCardEventService.java
 */
@Component("StudentReportCardEventMetaData")
public class StudentReportCardEventMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "StudentReportCardEvent";
	public static final Class<?> entityClass = StudentReportCardEvent.class;
	public static final String COL_KEY = "key";
	public static final String COL_STUDENT_KEY = "studentKey";
	public static final String COL_REPORTCARDEVENT_KEY = "reportCardEventKey";
	public static final String COL_STATUS = "status";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_PATH = "reportCardPath";
	public static final String COL_REMARKS = "remarks";
	public static final String COL_STATUS_UPPER_CASE = "statusUpperCase";
	
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	@Autowired
	public StudentReportCardEventMetaData(StudentMetaData studentMetaData,AcademicYearMetaData academicYearMetaData ,
			ReportCardEventMetaData reportCardEventMetaData) {
		super(ENTITY_NAME, new String[]{}, COL_STATUS_UPPER_CASE, null, entityClass,
				getRelatedEntitiesMap(studentMetaData, academicYearMetaData ,reportCardEventMetaData),
				getFilterList());

	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(
			StudentMetaData studentMetaData,AcademicYearMetaData academicYearMetaData ,
			ReportCardEventMetaData reportCardEventMetaData) {
		
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		
		relatedEntitiesMap.put(ReportCardEvent.class, reportCardEventMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Student.class, studentMetaData);
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		filterList.add(COL_IS_DELETED);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_KEY, ColumnMetaData.ColumnType.Key);
		
		addColumnMetaData(COL_STATUS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_STATUS_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PATH, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_REMARKS, ColumnMetaData.ColumnType.STRING);

		addColumnMetaData(COL_STUDENT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Student.class), RelationshipType.ONE_TO_ONE_OWNED));
		
		addColumnMetaData(COL_REPORTCARDEVENT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(ReportCardEvent.class), RelationshipType.ONE_TO_ONE_OWNED));
		
		
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		
		addColumnMetaData(COL_LAST_MODIFIED_DATE,
				ColumnMetaData.ColumnType.DATE, new SystemMetaData(
						OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE,
				new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,
				ColumnMetaData.ColumnType.STRING, new SystemMetaData(
						OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING,
				new SystemMetaData(OperationType.CREATE));

	}
}
