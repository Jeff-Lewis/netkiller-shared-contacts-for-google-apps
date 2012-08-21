package com.metacube.ipathshala.entity.metadata.impl;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.Workflow;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;

@Component("WorkflowMetaData")
public class WorkflowMetaData extends AbstractMetaData {
	
	public static final String ENTITY_NAME = "Workflow";
	public static final Class<?> entityClass = Workflow.class;
	public static final String COL_WORKFLOW_KEY = "key";
	public static final String COL_WORKFLOW_NAME = "workflowName";
	public static final String COL_WORKFLOW_INSTANCE_ID = "workflowInstanceId";
	public static final String COL_WORKFLOW_CONTEXT = "workflowContext";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	public WorkflowMetaData() {
		super(ENTITY_NAME,new String[]{COL_WORKFLOW_NAME},COL_WORKFLOW_NAME,null, entityClass, null, null);
	}
	
	@Override
	protected void initialize() {
		addColumnMetaData(COL_WORKFLOW_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_WORKFLOW_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WORKFLOW_INSTANCE_ID, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WORKFLOW_CONTEXT, ColumnMetaData.ColumnType.BLOB);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
	
	}
}
