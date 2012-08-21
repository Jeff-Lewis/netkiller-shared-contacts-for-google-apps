package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.DomainGroup;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;

@Component("DomainGroupMetaData")
public class DomainGroupMetaData extends AbstractMetaData {
	public static final String ENTITY_NAME = "DomainGroup";
	public static final Class<?> entityClass = DomainGroup.class;
	public static final String COL_DOMAIN_GROUP_KEY = "";
	public static final String COL_DOMAIN_NAME = "domainName";
	public static final String COL_GROUP_NAME = "groupName";

	protected DomainGroupMetaData() {
		super(ENTITY_NAME, new String[] { COL_DOMAIN_NAME }, COL_DOMAIN_NAME,
				new String[] { COL_DOMAIN_NAME }, entityClass, new HashMap(),
				null);

	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_DOMAIN_GROUP_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_DOMAIN_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_GROUP_NAME, ColumnMetaData.ColumnType.STRING);
	}

}
