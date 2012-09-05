package com.netkiller.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.AcademicYearStructure;
import com.netkiller.entity.Term;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

@Component("AcademicYearStructureMetaData") 
public class AcademicYearStructureMetaData extends AbstractMetaData{

	
	public static final String ENTITY_NAME = "AcademicYearStructure";
	public static final Class<?> entityClass = AcademicYearStructure.class;
	public static final String COL_ACADEMIC_YEAR_STRUCTURE_KEY = "key";
	public static final String COL_NAME = "name";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_FROM_LEVEL_KEY = "fromLevelKey";
	public static final String COL_TO_LEVEL_KEY = "toLevelKey";
	public static final String COL_TERMS = "terms";
	public static final String COL_STAGES = "stages";
	
	
	@Autowired
	protected AcademicYearStructureMetaData(ValueMetaData valueMetaData,AcademicYearMetaData academicYearMetaData) {
		super(ENTITY_NAME, new String[]{COL_NAME}, COL_NAME, new String[]{COL_NAME},
				entityClass, getRelatedEntitiesMap(valueMetaData,academicYearMetaData), getFilterList());
		
	}
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(ValueMetaData valueMetaData,AcademicYearMetaData academicYearMetaData) {
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
		addColumnMetaData(COL_ACADEMIC_YEAR_STRUCTURE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_FROM_LEVEL_KEY,ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TO_LEVEL_KEY,ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TERMS, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Term.class), RelationshipType.ONE_TO_ONE_OWNED));
	}
	
		
}
