package com.netkiller.entity.metadata.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.netkiller.entity.EntityCounter;
import com.netkiller.entity.metadata.ColumnMetaData;

@Component("EntityCounterMetaData")
public class EntityCounterMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "EntityCounter";
	public static final Class<?> entityClass = EntityCounter.class;
	public static final String COL_ENTITY_COUNTER_KEY = "key";
	public static final String COL_DOMAIN_NAME = "domain";
	public static final String COL_ENTITY_NAME = "entityName";
	public static final String COL_COUNT = "count";

	public EntityCounterMetaData() {
		super(ENTITY_NAME, new String[] { COL_DOMAIN_NAME }, COL_DOMAIN_NAME,
				new String[] { COL_DOMAIN_NAME }, entityClass, new HashMap(),
				null);

	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_ENTITY_COUNTER_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_DOMAIN_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ENTITY_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_COUNT, ColumnMetaData.ColumnType.INT);

	}

}
