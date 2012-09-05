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
import com.netkiller.entity.EvaluationStage;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.ReportCardEvent;
import com.netkiller.entity.Subject;
import com.netkiller.entity.Term;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author ankit gupta
 * 
 */

/*
 * The bean name is set to "ReportCardEventMetaData". This name should be used in
 * the
 * 
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See ReportCardEventService.java
 */
@Component("ReportCardEventMetaData")
public class ReportCardEventMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "ReportCardEvent";
	public static final Class<?> entityClass = ReportCardEvent.class;
	public static final String COL_KEY = "key";
	public static final String COL_CLASS_KEY = "classKey";
	public static final String COL_TERM_KEY = "termKey";
	public static final String COL_NAME = "name";
	public static final String COL_STAGE_KEY = "stageKey";
	public static final String COL_REPORT_CARD_TYPE_KEY = "reportCardTypeKey";
	public static final String COL_STATUS = "status";
	public static final String COL_IS_PUBLISHED = "isPublished";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	
	public static final String COL_NAME_UPPER_CASE = "nameUpperCase";
	
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	@Autowired
	public ReportCardEventMetaData(MyClassMetaData myClassMetaData, ValueMetaData valueMetaData, AcademicYearMetaData academicYearMetaData ,
			TermMetaData termMetaData , EvaluationStageMetaData evaluationStageMetaData) {
		super(ENTITY_NAME, new String[]{}, COL_NAME_UPPER_CASE, null, entityClass,
				getRelatedEntitiesMap(myClassMetaData, valueMetaData , academicYearMetaData , termMetaData, evaluationStageMetaData),
				getFilterList());

	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(
			MyClassMetaData myClassMetaData, ValueMetaData valueMetaData, AcademicYearMetaData academicYearMetaData ,
			TermMetaData termMetaData , EvaluationStageMetaData evaluationStageMetaData) {
		
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		
		relatedEntitiesMap.put(MyClass.class, myClassMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Value.class, valueMetaData);
		relatedEntitiesMap.put(Term.class,termMetaData);
		relatedEntitiesMap.put(EvaluationStage.class, evaluationStageMetaData);
		
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		filterList.add(COL_IS_DELETED);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_KEY, ColumnMetaData.ColumnType.Key);
		
		addColumnMetaData(COL_STATUS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_IS_PUBLISHED, ColumnMetaData.ColumnType.BOOL);
		
		addColumnMetaData(COL_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING);

		addColumnMetaData(COL_CLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(MyClass.class), RelationshipType.ONE_TO_ONE_OWNED));
		
		addColumnMetaData(COL_TERM_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Term.class), RelationshipType.ONE_TO_ONE_OWNED));
		
		addColumnMetaData(COL_STAGE_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(EvaluationStage.class), RelationshipType.ONE_TO_ONE_OWNED));
		
		
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		
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
