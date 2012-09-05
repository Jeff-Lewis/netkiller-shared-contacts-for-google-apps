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
import com.netkiller.entity.Subject;
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
 * The bean name is set to "SubjectMetaData". This name should be used in the @Qualifier with @Autowired.
 * If you want to use @Resource annotation simple use this in name attribute. But please follow @Autowired 
 * with @Qualifier for consistency. See SubjectService.java 
 */
@Component("SubjectMetaData")
public class SubjectMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Subject";
	public static final Class<?> entityClass = Subject.class;
	public static final String COL_SUBJECT_KEY = "key";
	public static final String COL_NAME = "subjectName";
	public static final String COL_NAME_UPPER_CASE = "subjectNameUpperCase";
	public static final String COL_DESCRIPTION = "subjectDescription";
	public static final String COL_GROUP = "subjectGroupKey";
	public static final String COL_LEVEL = "subjectLevelKey";
	public static final String COL_ACTIVE = "active";
	public static final String COL_TYPE = "type";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	@Autowired
	public SubjectMetaData(ValueMetaData valueMetaData) {
		super(ENTITY_NAME,new String[]{COL_NAME},COL_NAME_UPPER_CASE, new String[]{COL_NAME}, entityClass, getRelatedEntitiesMap(valueMetaData), getFilterList());
	}
	
	private static List<String> getFilterList()	{
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_FROM_DATE);
		filterList.add(COL_TO_DATE);
		return filterList;
	}
	

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(ValueMetaData valueMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Value.class, valueMetaData);
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_SUBJECT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING,true);
		addColumnMetaData(COL_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DESCRIPTION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LEVEL, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_GROUP, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TYPE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE, new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_FROM));
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE, new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_TO));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
	}
}
