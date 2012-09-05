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

import com.netkiller.entity.ClassGroup;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author vishesh
 *
 */
@Component("ClassGroupMetaData")
public class ClassGroupMetaData extends AbstractMetaData{
	
	public static final String ENTITY_NAME = "ClassGroup";
	public static final Class<?> entityClass = ClassGroup.class;
	public static final String COL_KEY = "key";
	public static final String COL_CLASS_KEY = "classKey";
	public static final String COL_GROUP_KEY = "groupKey";
	public static final String COL_ACTIVE = "active";
	public static final String COL_IS_DELETED = "isDeleted";
	
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	@Autowired
	public ClassGroupMetaData(MyClassMetaData classMetaData) {		
		super(ENTITY_NAME, new String[] {  },
				null, new String[]{}, entityClass, getRelatedEntitiesMap(classMetaData), getFilterList());
		
	}
	
	private static List<String> getFilterList()	{
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACTIVE);
		return filterList;
	}
	
	private static Map<Class,EntityMetaData> getRelatedEntitiesMap(MyClassMetaData classMetaData){
		Map<Class,EntityMetaData> relatedEntitiesMap= new HashMap();		
		relatedEntitiesMap.put(MyClass.class, classMetaData);
		return relatedEntitiesMap;
	}
	
	@Override
	protected void initialize() {
		
		addColumnMetaData(COL_CLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(MyClass.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_GROUP_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(MyClass.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.ACTIVE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		
	}

}
