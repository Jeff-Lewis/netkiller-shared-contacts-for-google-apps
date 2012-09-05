package com.netkiller.entity.metadata.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.GradingScale;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

@Component("GradingScaleMetaData")
public class GradingScaleMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "GradingScale";
	public static final Class<?> entityClass = GradingScale.class;
	public static final String COL_GRADING_SCALE_KEY = "key";
	public static final String COL_NAME = "name";

	public static final String COL_NAME_UPPER_CASE = "nameUpperCase";
	public static final String COL_GRADE_SCALE_STEPS = "gradeScaleSteps";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	public GradingScaleMetaData() {
		super(ENTITY_NAME, new String[] { COL_NAME }, COL_NAME_UPPER_CASE,
				new String[] { COL_NAME }, entityClass, null, null);
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_GRADING_SCALE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING);
	
		addColumnMetaData(COL_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_GRADE_SCALE_STEPS,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL,
				new FilterMetaData(GlobalFilterType.DELETE,
						GlobalFilterValueType.EXACT_MATCH));
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
