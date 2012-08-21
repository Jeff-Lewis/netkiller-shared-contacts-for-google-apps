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
import com.metacube.ipathshala.entity.MyClass;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * @author kunal.n
 * 
 */

/*
 * The bean name is set to "MyClassMetaData". This name should be used in the
 * 
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See MyClassService.java
 */
@Component("MyClassMetaData")
public class MyClassMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "MyClass";
	public static final Class<?> entityClass = MyClass.class;
	public static final String COL_MYCLASS_KEY = "key";
	public static final String COL_NAME = "name";
	public static final String COL_NAME_UPPER_CASE = "nameUpperCase";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_LEVEL_KEY = "levelKey";
	public static final String COL_TYPE_KEY = "classTypeKey";
	public static final String COL_ACTIVE = "active";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_CLASS_SITE_NAME = "classSiteName";
	public static final String COL_ASSIGNMENT_CALENDAR_ID = "assignmentCalendarId";
	public static final String COL_EVENT_CALENDAR_ID = "eventCalendarId";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	

	@Autowired
	public MyClassMetaData(ValueMetaData valueMetaData, AcademicYearMetaData academicYearMetaData) {
		super(ENTITY_NAME, new String[] { COL_NAME }, COL_NAME_UPPER_CASE,  new String[] { COL_NAME }, entityClass, getRelatedEntitiesMap(
						valueMetaData, academicYearMetaData), getFilterList());
	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(ValueMetaData valueMetaData,
			AcademicYearMetaData academicYearMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Value.class, valueMetaData);
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
		addColumnMetaData(COL_MYCLASS_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING,true);
		addColumnMetaData(COL_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DESCRIPTION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LEVEL_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TYPE_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_CLASS_SITE_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ASSIGNMENT_CALENDAR_ID, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_EVENT_CALENDAR_ID, ColumnMetaData.ColumnType.STRING);
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
