package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

@Component("ContactsMetaData")
public class ContactsMetaData extends AbstractMetaData {
	public static final String ENTITY_NAME = "Contacts";
	public static final Class<?> entityClass = Contacts.class;
	public static final String COL_CONTACTS_KEY = "key";
	public static final String COL_FIRST_NAME = "firstName";
	public static final String COL_LAST_NAME = "lastName";
	public static final String COL_FULL_NAME = "fullName";
	public static final String COL_COMPANY_NAME = "cmpnyName";
	public static final String COL_COMPANY_TITLE = "cmpnyTitle";
	public static final String COL_COMAPANY_DEPARTMENT = "cmpnyDepartment";
	public static final String COL_WORK_EMAIL = "workEmail";
	public static final String COL_HOME_EMAIL = "homeEmail";
	public static final String COL_OTHER_EMAIL = "otherEmail";
	public static final String COL_WORK_PHONE = "workPhone";
	public static final String COL_HOME_PHONE = "homePhone";
	public static final String COL_MOBILE_NUMBER = "mobileNumber";
	public static final String COL_WORK_ADDRESS = "workAddress";
	public static final String COL_HOME_ADDRESS = "homeAddress";
	public static final String COL_OTHER_ADDRESS = "otherAddress";
	public static final String COL_NOTES = "notes";

	public static final String COL_ACTIVE = "active";
	public static final String COL_IS_DELETED = "isDeleted";

	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	public ContactsMetaData() {
		super(ENTITY_NAME, new String[] { COL_FIRST_NAME }, COL_FIRST_NAME,
				new String[] { COL_FIRST_NAME }, entityClass, new HashMap(),
				null);

	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_CONTACTS_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_FIRST_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LAST_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FULL_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_COMPANY_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_COMPANY_TITLE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_COMAPANY_DEPARTMENT,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WORK_EMAIL, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_HOME_EMAIL, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_OTHER_EMAIL, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WORK_PHONE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_HOME_PHONE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOBILE_NUMBER, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WORK_ADDRESS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_HOME_ADDRESS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_OTHER_ADDRESS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_NOTES, ColumnMetaData.ColumnType.STRING);

		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL,
				new FilterMetaData(GlobalFilterType.DELETE,
						GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL,
				new FilterMetaData(GlobalFilterType.ACTIVE,
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
