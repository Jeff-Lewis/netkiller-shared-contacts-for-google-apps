package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.GradingScale;
import com.metacube.ipathshala.entity.GradingScaleSteps;
import com.metacube.ipathshala.entity.MyClass;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * @author Jitender
 *
 */
@Component("GradingScaleStepsMetaData")
public class GradingScaleStepsMetaData extends AbstractMetaData{
	
	public static final String ENTITY_NAME = "GradingScaleSteps";
	public static final Class<?> entityClass = GradingScaleSteps.class;
	public static final String COL_GRADING_SCALE_STEPS_KEY = "key";
	public static final String COL_GRADING_SCALE_KEY = "key";
	public static final String COL_MINIMUM = "minimum";
	public static final String COL_MAXIMUM = "maximum";
	public static final String COL_COLOR_KEY = "colorKey";
	public static final String STEP_DISPLAY = "stepDisplay";
	public static final String COL_NUMERIC_DISPLAY = "numericDisplay";
	public static final String STEP_WEIGHT = "stepWeight";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted";
	
	@Autowired
	public GradingScaleStepsMetaData(GradingScaleMetaData gradingScaleMetaData,ValueMetaData valueMetaData){
		super(ENTITY_NAME, new String[] { STEP_DISPLAY },
				COL_GRADING_SCALE_KEY,null, entityClass, getRelatedEntitiesMap(gradingScaleMetaData,valueMetaData),null);
		}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(GradingScaleMetaData gradingScaleMetaData,ValueMetaData valueMetaData
			) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(GradingScale.class, gradingScaleMetaData);
		relatedEntitiesMap.put(Value.class, valueMetaData);
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_GRADING_SCALE_STEPS_KEY , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_GRADING_SCALE_KEY , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_MINIMUM, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_COLOR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_NUMERIC_DISPLAY, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MINIMUM, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(STEP_DISPLAY , ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(STEP_WEIGHT , ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
GlobalFilterType.DELETE,
						GlobalFilterValueType.EXACT_MATCH));
	
		
		
	}
	
	
}
