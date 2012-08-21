package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.EvaluationStage;
import com.metacube.ipathshala.entity.ReportCard;
import com.metacube.ipathshala.entity.Term;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

@Component("ReportCardMetaData")
public class ReportCardMetaData extends AbstractMetaData {
	
	public static final String ENTITY_NAME = "ReportCard";
	public static final Class<?> entityClass = ReportCard.class;
	public static final String COL_REPORT_CARD_KEY = "key";
	public static final String COL_NAME = "name";
	public static final String COL_TEMPLATE_NAME = "templateName";
	public static final String COL_FROM_LEVEL_KEY = "fromLevelKey";
	public static final String COL_TO_LEVEL_KEY = "toLevelKey";
	public static final String COL_ACADEMIC_YEAR_KEY ="academicYearKey";
	public static final String COL_REPORT_CARD_TYPE_KEY = "reportCardTypeKey";
	public static final String COL_TERM_KEY = "termKey";
	public static final String COL_STAGE_KEY ="stageKey";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	@Autowired
	public ReportCardMetaData(ValueMetaData valueMetaData, AcademicYearMetaData academicYearMetaData,TermMetaData termMetaData,EvaluationStageMetaData stageMetaData) {
		super(ENTITY_NAME, new String[] {  }, null,  new String[] { COL_NAME }, entityClass, getRelatedEntitiesMap(
						valueMetaData, academicYearMetaData,termMetaData,stageMetaData), null);
	}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(ValueMetaData valueMetaData,
			AcademicYearMetaData academicYearMetaData,TermMetaData termMetaData,EvaluationStageMetaData stageMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Value.class, valueMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Term.class, termMetaData);
		relatedEntitiesMap.put(EvaluationStage.class, stageMetaData);
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize(){
		addColumnMetaData(COL_REPORT_CARD_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING,true);
		addColumnMetaData(COL_TEMPLATE_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FROM_LEVEL_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TO_LEVEL_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TERM_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Term.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_STAGE_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(EvaluationStage.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_REPORT_CARD_TYPE_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
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
