/**
 * 
 */
package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.Parent;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * @author amit.c
 * 
 */

/*
 * The bean name is set to "ParentMetaData". This name should be used in the
 * 
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See ParentService.java
 */
@Component("ParentMetaData")
public class ParentMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Parent";
	public static final Class<?> entityClass = Parent.class;
	public static final String COL_PARENT_KEY = "key";
	public static final String COL_FATHER_FIRST_NAME = "fatherFirstName";
	public static final String COL_FATHER_FIRST_NAME_UPPER_CASE = "fatherFirstNameUpperCase";
	public static final String COL_ACTIVE = "active";
	public static final String COL_FATHER_MIDDLE_NAME = "fatherMiddleName";
	public static final String COL_FATHER_LAST_NAME = "fatherLastName";
	public static final String COL_MOTHER_FIRST_NAME = "motherFirstName";
	public static final String COL_MOTHER_MIDDLE_NAME = "motherMiddleName";
	public static final String COL_MOTHER_LAST_NAME = "motherLastName";
	public static final String COL_PRESENT_ADDRESS_LINE_1 = "presentAddressLine1";
	public static final String COL_PRESENT_ADDRESS_LINE_2 = "presentAddressLine2";
	public static final String COL_PRESENT_ADDRESS_CITY_KEY = "presentAddressCityKey";
	public static final String COL_PRESENT_ADDRESS_PINCODE = "presentAddressPincode";
	public static final String COL_PRESENT_ADDRESS_STATE_KEY = "presentAddressStateKey";
	public static final String COL_PRESENT_ADDRESS_COUNTRY_KEY = "presentAddressCountryKey";
	public static final String COL_PRESENT_ADDRESS_ALTERNATE_CITY = "presentAddressAlternateCity" ;
	public static final String COL_FATHER_OCCUPATION_KEY = "fatherOccupation" ;
	public static final String COL_MOTHER_OCCUPATION_KEY = "motherOccupation" ;
	
	public static final String COL_FATHER_OFFICE_LANDLINENUMBER_CODE = "fatherOfficeLandlineNumberCode";
	public static final String COL_FATHER_OFFICE_LANDLINENUMBER = "fatherOfficeLandlineNumber";
	public static final String COL_FATHER_CELLNUMBER_CODE = "fatherCellphoneNumberCode";
	public static final String COL_FATHER_CELLNUMBER = "fatherCellphoneNumber";
	public static final String COL_MOTHER_OFFICE_LANDLINENUMBER_CODE = "motherOfficeLandlineNumberCode";
	public static final String COL_MOTHER_OFFICE_LANDLINENUMBER = "motherOfficeLandlineNumber";
	public static final String COL_MOTHER_CELLNUMBER_CODE = "motherCellphoneNumberCode";
	public static final String COL_MOTHER_CELLNUMBER = "motherCellphoneNumber";
	public static final String COL_FATHER_EMAIL_ADDRESS = "fatherEmailAddress";
	public static final String COL_MOTHER_EMAIL_ADDRESS = "motherEmailAddress";
	
	public static final String COL_PERMANENT_ADDRESS_LINE_1 = "permanentAddressLine1";
	public static final String COL_PERMANENT_ADDRESS_LINE_2 = "permanentAddressLine2";
	public static final String COL_PERMANENT_ADDRESS_CITY_KEY = "permanentAddressCityKey";
	public static final String COL_PERMANENT_ADDRESS_PINCODE = "permanentAddressPincode";
	public static final String COL_PERMANENT_ADDRESS_STATE_KEY = "permanentAddressStateKey";
	public static final String COL_PERMANENT_ADDRESS_COUNTRY_KEY = "permanentAddressCountryKey";
	public static final String COL_PERMANENT_ADDRESS_ALTERNATE_CITY = "permanentAddressAlternateCity" ;
	
	public static final String COL_HOME_LANDLINENUMBER_CODE = "homeLandlineNumberCode";
	public static final String COL_HOME_OFFICE_LANDLINENUMBER = "homeLandlineNumber";
	public static final String COL_IS_FATHER_PRIMARY_CONTACT = "isFatherPrimaryContact" ;
	public static final String COL_IS_DELETED = "isDeleted" ;
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_FATHER_DESIGNATION = "fatherDesignation";
	public static final String COL_MOTHER_DESIGNATION = "motherDesignation";
	public static final String COL_USER_DEFINED_STRING_FIELD1 = "userDefinedStringField1";
	public static final String COL_USER_DEFINED_STRING_FIELD2 = "userDefinedStringField2";
	public static final String COL_USER_DEFINED_STRING_FIELD3 = "userDefinedStringField3";
	public static final String COL_USER_DEFINED_DATE_FIELD1 = "userDefinedDateField1";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	
	public ParentMetaData() {
		super(ENTITY_NAME, new String[] { COL_FATHER_FIRST_NAME,
				COL_FATHER_LAST_NAME }, COL_FATHER_FIRST_NAME_UPPER_CASE,   new String[]{COL_FATHER_FIRST_NAME,COL_FATHER_MIDDLE_NAME,COL_FATHER_LAST_NAME}, entityClass, null, getFilterList());
	}
	
	private static List<String> getFilterList()	{
		List<String> filterList = new ArrayList<String>();
	
		filterList.add(COL_ACTIVE);
		
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_PARENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_FATHER_FIRST_NAME,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FATHER_FIRST_NAME_UPPER_CASE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FATHER_MIDDLE_NAME,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FATHER_LAST_NAME,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_FIRST_NAME,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_MIDDLE_NAME,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_LAST_NAME,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FATHER_OFFICE_LANDLINENUMBER_CODE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FATHER_OFFICE_LANDLINENUMBER,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FATHER_CELLNUMBER_CODE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FATHER_CELLNUMBER,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_OFFICE_LANDLINENUMBER_CODE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_OFFICE_LANDLINENUMBER,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_CELLNUMBER_CODE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_CELLNUMBER,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_LINE_1,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_LINE_2,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_CITY_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PRESENT_ADDRESS_PINCODE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_STATE_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PRESENT_ADDRESS_COUNTRY_KEY,
				ColumnMetaData.ColumnType.Key);
		
		addColumnMetaData(COL_PRESENT_ADDRESS_ALTERNATE_CITY, ColumnMetaData.ColumnType.STRING) ;
		addColumnMetaData(COL_PERMANENT_ADDRESS_ALTERNATE_CITY, ColumnMetaData.ColumnType.STRING) ;
		addColumnMetaData(COL_FATHER_EMAIL_ADDRESS, ColumnMetaData.ColumnType.STRING) ;
		addColumnMetaData(COL_MOTHER_EMAIL_ADDRESS, ColumnMetaData.ColumnType.STRING) ;
		addColumnMetaData(COL_FATHER_OCCUPATION_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_MOTHER_OCCUPATION_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_IS_FATHER_PRIMARY_CONTACT,ColumnMetaData.ColumnType.BOOL) ;
		addColumnMetaData(COL_HOME_LANDLINENUMBER_CODE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_HOME_OFFICE_LANDLINENUMBER,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_LINE_1,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_LINE_2,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_CITY_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PERMANENT_ADDRESS_PINCODE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_STATE_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PERMANENT_ADDRESS_COUNTRY_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.ACTIVE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_PARENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_FATHER_DESIGNATION,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MOTHER_DESIGNATION,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD1, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD2, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD3, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_DATE_FIELD1, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
	}
}