package com.netkiller.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.DomainAdmin;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;

@Component("DomainAdminMetaData")
public class DomainAdminMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "DomainAdmin";
	public static final Class<?> entityClass = DomainAdmin.class;
	public static final String COL_DOMAIN_ADMIN_KEY = "key";
	public static final String COL_ACCOUNT_TYPE_KEY = "accountTypeKey";
	public static final String COL_DOMAIN_NAME = "domainName";
	public static final String COL_ADMIN_EMAIL = "adminEmail";
	public static final String COL_REGISTERED_DATE = "registeredDate";
	public static final String COL_TOTAL_COUNTS = "totalCounts";

	@Autowired
	protected DomainAdminMetaData(ValueMetaData valueMetaData) {
		super(ENTITY_NAME, new String[] { COL_DOMAIN_NAME }, COL_DOMAIN_NAME,
				new String[] { COL_DOMAIN_NAME }, entityClass,
				getRelatedEntitiesMap(valueMetaData), getFilterList());
	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(
			ValueMetaData valueMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Value.class, valueMetaData);
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_DOMAIN_ADMIN_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(
				COL_ACCOUNT_TYPE_KEY,
				ColumnMetaData.ColumnType.Key,
				new ColumnRelationShipMetaData(relatedEntityTypesMap
						.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_DOMAIN_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ADMIN_EMAIL, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_REGISTERED_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TOTAL_COUNTS, ColumnMetaData.ColumnType.INT);

	}

}
