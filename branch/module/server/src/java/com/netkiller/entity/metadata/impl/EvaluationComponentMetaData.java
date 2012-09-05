package com.netkiller.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.EvaluationComponent;
import com.netkiller.entity.EvaluationScheme;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;



/**
 * 
 * @author Jitender
 *
 */

@Component("EvaluationComponentMetaData")
public class EvaluationComponentMetaData extends AbstractMetaData {
	
	public static final String ENTITY_NAME = "EvaluationComponent";
	public static final Class<?> entityClass = EvaluationComponent.class;
	public static final String COL_EVALUATION_COMPONENT_KEY = "key";
	public static final String COL_EVALUATION_SCHEME_KEY = "evalSchemeKey";
	public static final String COL_COMPONENT_NAME = "componentName";
	public static final String COL_COMPONENT_TYPE_KEY = "componentTypeKey";
	public static final String COL_COMPONENT_CATEGORY_KEY = "componentCategoryKey";
	public static final String COL_COMPONENT_STRUCTURE_KEY = "componentStructureKey";
	public static final String COL_COMPONENT_CALCULATION_METHOD_KEY = "componentCalcMethodKey";
	public static final String COL_DISPLAY_SEQUENCE = "displaySequence";
	public static final String COL_APPLY_MINIMUM = "applyMinimum";
	public static final String COL_TERM_ONE_AVAILABILITY = "termOneAvailability";
	public static final String COL_TERM_TWO_AVAILABILITY = "termTwoAvailability";
	public static final String COL_TERM_THREE_AVAILABILITY = "termThreeAvailability";
	public static final String COL_TERM_FOUR_AVAILABILITY = "termFourAvailability";
	public static final String COL_TERM_ONE_MAX_MARKS = "termOneMaxMarks";
	public static final String COL_TERM_TWO_MAX_MARKS = "termTwoMaxMarks";
	public static final String COL_TERM_THREE_MAX_MARKS = "termThreeMaxMarks";
	public static final String COL_TERM_FOUR_MAX_MARKS = "termFourMaxMarks";
	public static final String COL_ACTIVE = "active";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	@Autowired
	public EvaluationComponentMetaData(EvaluationSchemeMetaData evaluationSchemeMetaData) {		
		super(ENTITY_NAME, new String[] { COL_COMPONENT_NAME },
				COL_EVALUATION_SCHEME_KEY,null, entityClass, getRelatedEntitiesMap(evaluationSchemeMetaData),null);
		}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(EvaluationSchemeMetaData evaluationSchemeMetaData
			) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(EvaluationScheme.class, evaluationSchemeMetaData);
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_EVALUATION_COMPONENT_KEY , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_EVALUATION_SCHEME_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(EvaluationScheme.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_COMPONENT_NAME , ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_COMPONENT_TYPE_KEY  ,  ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_COMPONENT_STRUCTURE_KEY  , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_COMPONENT_CATEGORY_KEY  , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_COMPONENT_CALCULATION_METHOD_KEY  , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_DISPLAY_SEQUENCE  , ColumnMetaData.ColumnType.INT);
		addColumnMetaData(COL_APPLY_MINIMUM , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TERM_ONE_AVAILABILITY  , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TERM_TWO_AVAILABILITY  , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TERM_THREE_AVAILABILITY  , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TERM_FOUR_AVAILABILITY  , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TERM_ONE_MAX_MARKS , ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_TERM_TWO_MAX_MARKS  , ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_TERM_THREE_MAX_MARKS   , ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_TERM_FOUR_MAX_MARKS  , ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_ACTIVE  , ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.ACTIVE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
	}

}
