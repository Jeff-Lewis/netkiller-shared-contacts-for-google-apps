package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.EvaluationComponent;
import com.metacube.ipathshala.entity.EvaluationScheme;
import com.metacube.ipathshala.entity.EvaluationSubComponent;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;


/**
 * @author Jitender
 *
 */
@Component("EvaluationSubComponentMetaData")
public class EvaluationSubComponentMetaData extends AbstractMetaData{

	public static final String ENTITY_NAME = "EvaluationSchemeComponent";
	public static final Class<?> entityClass = EvaluationSubComponent.class;
	public static final String COL_EVALUATION_SUB_COMPONENT_KEY = "key";
	public static final String COL_EVALUATION_COMPONENT_KEY = "evaluationComponentKey";
	public static final String COL_SUB_COMPONENT_NAME = "subComponentName";
	public static final String COL_SUB_COMPONENT_DESCRIPTION = "subComponentDescription";
	public static final String COL_SUB_COMPONENT_REMARKS = "subComponentRemarks";
	public static final String COL_DISPLAY_SEQUENCE = "displaySequence";
	public static final String COL_ACTIVE = "active";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted";
	
	@Autowired
	public EvaluationSubComponentMetaData(EvaluationComponentMetaData evaluationComponentMetaData){
		super(ENTITY_NAME, new String[] { COL_SUB_COMPONENT_NAME },
				COL_EVALUATION_COMPONENT_KEY,null, entityClass, getRelatedEntitiesMap(evaluationComponentMetaData),null);
		}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(EvaluationComponentMetaData evaluationComponentMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(EvaluationComponent.class, evaluationComponentMetaData);
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_EVALUATION_SUB_COMPONENT_KEY , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_EVALUATION_COMPONENT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(EvaluationComponent.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_SUB_COMPONENT_NAME , ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SUB_COMPONENT_DESCRIPTION , ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SUB_COMPONENT_REMARKS , ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DISPLAY_SEQUENCE  , ColumnMetaData.ColumnType.INT);
		addColumnMetaData(COL_ACTIVE  , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		
		
	}



}
