package com.netkiller.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.ConnectContact;
import com.netkiller.entity.Contact;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.EntityMetaData;

@Component("ConnectContactMetaData")
public class ConnectContactMetaData extends AbstractMetaData{
	
	public static final String ENTITY_NAME = "ConnectContact";
	public static final Class<?> entityClass = ConnectContact.class;
	public static final String COL_KEY = "key";
	public static final String COL_CONTACT_KEY= "contactKey";
	public static final String COL_RANDOM_URL = "randomUrl";
	
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	@Autowired 
	public ConnectContactMetaData(ContactsMetaData contactsMetaData) {
		super(ENTITY_NAME, new String[] {  }, COL_KEY,
				new String[] { }, entityClass, getRelatedEntitiesMap(contactsMetaData),
				null);
	}
	
	private static Map<Class,EntityMetaData> getRelatedEntitiesMap(ContactsMetaData contactsMetaData){
		Map<Class,EntityMetaData> relatedEntitiesMap= new HashMap();
		relatedEntitiesMap.put(Contact.class, contactsMetaData);
		return relatedEntitiesMap;
	}

	protected void initialize() {
		addColumnMetaData(COL_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_CONTACT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_RANDOM_URL, ColumnMetaData.ColumnType.STRING);
		
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
