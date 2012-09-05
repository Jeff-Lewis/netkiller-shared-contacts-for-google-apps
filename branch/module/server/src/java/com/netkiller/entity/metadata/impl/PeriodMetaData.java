/**
 * 
 */
package com.netkiller.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.Period;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author kunal.n
 * 
 */

/*
 * The bean name is set to "PeriodMetaData". This name should be used in the
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See PeriodService.java
 */
@Component("PeriodMetaData")
public class PeriodMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Period";
	public static final Class<?> entityClass = Period.class;
	public static final String COL_PERIOD_KEY = "key";
	public static final String COL_NAME = "name";
	public static final String COL_NAME_UPPERCASE = "nameUpperCase";
	public static final String COL_ST_FROM_HOUR = "stFromHour";
	public static final String COL_ST_FROM_MIN = "stFromMin";
	public static final String COL_ST_TO_HOUR = "stToHour";
	public static final String COL_ST_TO_MIN = "stToMin";
	public static final String COL_WT_FROM_HOUR = "wtFromHour";
	public static final String COL_WT_FROM_MIN = "wtFromMin";
	public static final String COL_WT_TO_HOUR = "wtToHour";
	public static final String COL_WT_TO_MIN = "wtToMin";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_ACTIVE = "active";
	public static final String COL_TYPE_KEY = "typeKey";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";


	@Autowired
	public PeriodMetaData(AcademicYearMetaData academicYearMetaData,ValueMetaData valueMetaData) {
		super(ENTITY_NAME, new String[] { COL_NAME }, COL_NAME_UPPERCASE, new String[] { COL_NAME }, entityClass, getRelatedEntitiesMap(academicYearMetaData,valueMetaData),  getFilterList());
	}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(AcademicYearMetaData academicYearMetaData, ValueMetaData valueMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Value.class,valueMetaData);
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_PERIOD_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_NAME_UPPERCASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ST_FROM_HOUR, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ST_FROM_MIN, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ST_TO_HOUR, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ST_TO_MIN, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WT_FROM_HOUR, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WT_FROM_MIN, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WT_TO_HOUR, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WT_TO_MIN, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DESCRIPTION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TYPE_KEY, ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
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
