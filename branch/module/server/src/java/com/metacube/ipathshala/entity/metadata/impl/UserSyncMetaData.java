package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.UserSync;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;

@Component("UserSyncMetaData")
public class UserSyncMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "UserSync";
	public static final Class<?> entityClass = UserSync.class;
	public static final String COL_ENTITY_KEY = "key";
	public static final String COL_DOMAIN_NAME = "domainName";
	public static final String COL_USER_EMAIL = "userEmail";
	public static final String COL_SYNC_DATE = "syncDate";

	protected UserSyncMetaData() {
		super(ENTITY_NAME, new String[] { COL_DOMAIN_NAME }, COL_DOMAIN_NAME,
				new String[] { COL_DOMAIN_NAME }, entityClass, new HashMap(),
				null);

	}

	@Override
	protected void initialize() {

		addColumnMetaData(COL_ENTITY_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_DOMAIN_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_EMAIL, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SYNC_DATE, ColumnMetaData.ColumnType.DATE);

	}

}
