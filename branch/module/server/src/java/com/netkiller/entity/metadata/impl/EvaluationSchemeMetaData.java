package com.netkiller.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.EvaluationScheme;
import com.netkiller.entity.Set;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

@Component("EvaluationSchemeMetaData")
public class EvaluationSchemeMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "EvaluationScheme";
	public static final Class<?> entityClass = EvaluationScheme.class;
	public static final String COL_EVALUATION_SCHEME_KEY = "key";
	public static final String COL_NAME = "name";
	public static final String COL_NAME_UPPER_CASE = "nameUpperCase";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_SCHEME_EVALUATION_TYPE_KEY = "schemeEvalTypeKey";
	public static final String COL_GRADING_SCALE_KEY = "gradingScaleKey";
	public static final String COL_TEMPLATE = "template";
	public static final String COL_ACTIVE = "active";
	public static final String COL_VALID = "valid";
	public static final String COL_PASS_PERCENTAGE = "passPercentage";
	public static final String COL_TERMS = "terms";
	public static final String COL_TERM_ONE_MAX_MARKS = "termOneMaxMarks";
	public static final String COL_TERM_TWO_MAX_MARKS = "termTwoMaxMarks";
	public static final String COL_TERM_THREE_MAX_MARKS = "termThreeMaxMarks";
	public static final String COL_TERM_FOUR_MAX_MARKS = "termFourMaxMarks";
	public static final String STAGES = "stages";
	public static final String COL_IS_DELETED = "isDeleted";
	
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	//TODO add grading scale
	@Autowired
	public EvaluationSchemeMetaData( SetMetaData setMetadata, GradingScaleMetaData gradingScaleMetaData ) {
		super(ENTITY_NAME, new String[] { COL_NAME }, COL_NAME_UPPER_CASE, new String[] { COL_NAME }, entityClass,
				getRelatedEntitiesMap(setMetadata, gradingScaleMetaData), null);

	}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(  SetMetaData setMetadata, GradingScaleMetaData gradingScaleMetaData
	) {
Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
relatedEntitiesMap.put(Set.class, setMetadata);
relatedEntitiesMap.put(GradingScaleMetaData.class, gradingScaleMetaData);
return relatedEntitiesMap;
}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_EVALUATION_SCHEME_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING, true);
		addColumnMetaData(COL_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_SCHEME_EVALUATION_TYPE_KEY, ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Set.class), RelationshipType.ONE_TO_ONE_UNOWNED));
		addColumnMetaData(COL_GRADING_SCALE_KEY, ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(GradingScaleMetaData.class), RelationshipType.ONE_TO_ONE_UNOWNED));
		addColumnMetaData(COL_TEMPLATE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_VALID, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TERMS, ColumnMetaData.ColumnType.INT);
		addColumnMetaData(COL_PASS_PERCENTAGE, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_TERM_ONE_MAX_MARKS, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_TERM_TWO_MAX_MARKS, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_TERM_THREE_MAX_MARKS, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_TERM_FOUR_MAX_MARKS, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(STAGES, ColumnMetaData.ColumnType.INT);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));

	}

}
