package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.Parent;
import com.metacube.ipathshala.entity.Set;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

@Component("ValueMetaData")
public class ValueMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Value";
	public static final Class<?> entityClass = Value.class;
	public static final String COL_VALUE_KEY = "key";
	public static final String COL_SET_KEY = "setKey";
	public static final String COL_VALUE = "value";
	public static final String COL_VALUE_UPPER_CASE = "valueUpperCase";
	public static final String COL_ORDER_INDEX = "orderIndex";
	public static final String COL_PARENT_VALUE_KEY = "parentValueKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted";

	@Autowired
	public ValueMetaData(SetMetaData setMetaData) {
		super(ENTITY_NAME, new String[] { COL_VALUE }, COL_VALUE_UPPER_CASE, new String[] { COL_VALUE }, entityClass, getRelatedEntitiesMap(setMetaData), null);
	}
	
	private static Map<Class,EntityMetaData> getRelatedEntitiesMap(SetMetaData setMetaData){
		Map<Class,EntityMetaData> relatedEntitiesMap= new HashMap();
		relatedEntitiesMap.put(Set.class, setMetaData);
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_VALUE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_SET_KEY, ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(relatedEntityTypesMap.get(Set.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_VALUE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_VALUE_UPPER_CASE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ORDER_INDEX, ColumnMetaData.ColumnType.INT);
		addColumnMetaData(COL_PARENT_VALUE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
	}
}
