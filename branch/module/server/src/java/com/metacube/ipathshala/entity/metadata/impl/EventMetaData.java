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

import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;
import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.Event;
import com.metacube.ipathshala.entity.MyClass;
import com.metacube.ipathshala.entity.Teacher;

/**
 * @author kunal.n
 * 
 */

/*
 * The bean name is set to "EventMetaData". This name should be used in the
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See EventService.java
 */
@Component("EventMetaData")
public class EventMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Event";
	public static final Class<?> entityClass = Event.class;
	public static final String COL_EVENT_KEY = "key";
	public static final String COL_TITLE = "title";
	public static final String COL_TITLE_UPPER_CASE = "titleUpperCase";
	public static final String COL_CLASS_KEY = "myclassKey";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_TEACHER_KEY = "teacherKey";
	public static final String COL_ACTIVE = "active";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";


	@Autowired
	public EventMetaData(AcademicYearMetaData academicYearMetaData, TeacherMetaData teacherMetaData,MyClassMetaData myClassMetaData) {
		super(ENTITY_NAME, new String[] { COL_TITLE }, COL_TITLE_UPPER_CASE,new String[] { COL_TITLE }, entityClass, getRelatedEntitiesMap(academicYearMetaData,teacherMetaData,myClassMetaData), getFilterList());
	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(
			AcademicYearMetaData academicYearMetaData, TeacherMetaData teacherMetaData,MyClassMetaData myClassMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Teacher.class, teacherMetaData);
		relatedEntitiesMap.put(MyClass.class, myClassMetaData);
		return relatedEntitiesMap;
	}
	
	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		return filterList;
	}
	
	@Override
	protected void initialize() {
		addColumnMetaData(COL_EVENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_TITLE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_TITLE_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_CLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(MyClass.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATETIME);
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATETIME);
		addColumnMetaData(COL_DESCRIPTION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_TEACHER_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Teacher.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
	}
}
