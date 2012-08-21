package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.AcademicYearStructure;
import com.metacube.ipathshala.entity.Term;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

@Component("TermMetaData")
public class TermMetaData extends AbstractMetaData {
    public static final String ENTITY_NAME = "Term";
	public static final Class<?> entityClass = Term.class;
	public static final String COL_TERM_KEY = "key";
	public static final String COL_TERM_NAME = "name";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_ACADEMIC_YEAR_STRUCTURE_KEY = "academicYearStructureKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_TERM_END_EXAM = "termEndExam";
	
	@Autowired
	public TermMetaData(ValueMetaData valueMetaData,AcademicYearMetaData academicYearMetaData, AcademicYearStructureMetaData academicYearStructureMetaData) {
		super(ENTITY_NAME, new String[]{COL_TERM_NAME}, COL_TERM_NAME, new String[]{COL_TERM_NAME},
				entityClass, getRelatedEntitiesMap(valueMetaData,academicYearMetaData,academicYearStructureMetaData), null);
	}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(ValueMetaData valueMetaData,
			AcademicYearMetaData academicYearMetaData, AcademicYearStructureMetaData academicYearStructureMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Value.class, valueMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(AcademicYearStructure.class, academicYearStructureMetaData);
		return relatedEntitiesMap;
	}

	
	@Override
	protected void initialize() {
		addColumnMetaData(COL_TERM_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_TERM_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_ACADEMIC_YEAR_STRUCTURE_KEY, ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(relatedEntityTypesMap.get(AcademicYearStructure.class), RelationshipType.ONE_TO_MANY));
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_TERM_END_EXAM, ColumnMetaData.ColumnType.BOOL);
   }
	

}
