package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.EvaluationStage;
import com.metacube.ipathshala.entity.Term;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

@Component("EvaluationStageMetaData") 
public class EvaluationStageMetaData extends AbstractMetaData{

	
	public static final String ENTITY_NAME = "EvaluationStage";
	public static final Class<?> entityClass = EvaluationStage.class;
	public static final String COL_EVALUATION_STAGE_KEY = "key";
	public static final String COL_STAGE_NAME = "name";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_START_DATE = "startDate";
	public static final String COL_END_DATE = "endDate";
	public static final String COL_TERM_KEY = "termKey";
	public static final String COL_EXAM_START_DATE = "examStartDate";
	public static final String COL_EXAM_END_DATE = "examEndDate";
	public static final String COL_SUBMIT_RESULT_BY = "submitResultBy";
	public static final String COL_PUBLISH_RESULT_EXAM = "publishResultBy";
	
	
	
	@Autowired
	protected EvaluationStageMetaData(ValueMetaData valueMetaData,AcademicYearMetaData academicYearMetaData,TermMetaData termMetaData) {
		super(ENTITY_NAME, new String[]{COL_STAGE_NAME}, COL_STAGE_NAME, new String[]{COL_STAGE_NAME},
				entityClass, getRelatedEntitiesMap(valueMetaData,academicYearMetaData,termMetaData), getFilterList());
		
	}
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(ValueMetaData valueMetaData,AcademicYearMetaData academicYearMetaData,TermMetaData termMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Value.class, valueMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Term.class, termMetaData);
		return relatedEntitiesMap;
	}
	
	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		return filterList;
	}
	@Override
	protected void initialize() {
		addColumnMetaData(COL_EVALUATION_STAGE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_STAGE_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_START_DATE,ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_END_DATE,ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TERM_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Term.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_EXAM_START_DATE,ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_EXAM_END_DATE,ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_SUBMIT_RESULT_BY,ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_PUBLISH_RESULT_EXAM,ColumnMetaData.ColumnType.DATE);
	}
	
		
}
