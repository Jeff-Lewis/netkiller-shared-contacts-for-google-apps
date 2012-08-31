package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.entity.UserContact;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;

@Component("UserContactMetaData")
public class UserContactMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "UserContact";
	public static final Class<?> entityClass = UserContact.class;
	public static final String COL_USER_CONTACT_KEY = "key";
	public static final String COL_USER_EMAIL = "userEmail";
	public static final String COL_CONTACT_KEY = "contactKey";
	public static final String COL_GOOGLE_CONTACT_ID = "contactId";
	public static final String COL_GROUP_NAME = "groupName";
	public static final String COL_DOMAIN_NAME = "domainName";

	public UserContactMetaData() {
		super(ENTITY_NAME, new String[] { COL_USER_EMAIL }, COL_USER_EMAIL,
				new String[] { COL_USER_EMAIL }, entityClass, new HashMap(),
				null);
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_USER_CONTACT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_USER_EMAIL, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(
				COL_CONTACT_KEY,
				ColumnMetaData.ColumnType.Key,
				new ColumnRelationShipMetaData(relatedEntityTypesMap
						.get(Contacts.class), RelationshipType.MANY_TO_MANY));
		addColumnMetaData(COL_GOOGLE_CONTACT_ID,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_GROUP_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DOMAIN_NAME, ColumnMetaData.ColumnType.STRING);

	}
}
