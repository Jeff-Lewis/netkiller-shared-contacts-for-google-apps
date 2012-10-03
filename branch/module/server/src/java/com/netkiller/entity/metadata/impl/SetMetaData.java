package com.netkiller.entity.metadata.impl;

import org.springframework.stereotype.Component;

import com.netkiller.entity.Set;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

@Component("SetMetaData")
public class SetMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Set";
	public static final Class<?> entityClass = Set.class;
	public static final String COL_SET_KEY = "key";
	public static final String COL_SET_NAME = "setName";
	public static final String COL_SET_NAME_UPPER_CASE = "setNameUpperCase";
	public static final String COL_SET_ORDER = "setOrder";
	public static final String COL_PARENT_SET_KEY = "parentSetKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted";


	public SetMetaData() {
		super(ENTITY_NAME, new String[] { COL_SET_NAME },
				COL_SET_NAME_UPPER_CASE,  new String[] { COL_SET_NAME }, entityClass, null, null);
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_SET_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_SET_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SET_NAME_UPPER_CASE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SET_ORDER, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PARENT_SET_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
	}

}
