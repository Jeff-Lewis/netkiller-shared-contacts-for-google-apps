/**
 * 
 */
package com.netkiller.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.Teacher;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author kunal.n
 * 
 */

/*
 * The bean name is set to "TeacherMetaData". This name should be used in the
 * 
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See TeacherService.java
 */
@Component("TeacherMetaData")
public class TeacherMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Teacher";
	public static final Class<?> entityClass = Teacher.class;
	public static final String COL_TEACHER_KEY = "key";
	public static final String COL_FIRST_NAME = "firstName";
	public static final String COL_FIRST_NAME_UPPER_CASE = "firstNameUpperCase";
	public static final String COL_MIDDLE_NAME = "middleName";
	public static final String COL_LAST_NAME = "lastName";
	public static final String COL_QUALIFICATION = "qualification";
	public static final String COL_LANDLINENUMBER_CODE = "landlineNumberCode";
	public static final String COL_LANDLINENUMBER = "landlineNumber";
	public static final String COL_CELLNUMBER_CODE = "cellNumberCode";
	public static final String COL_CELLNUMBER = "cellNumber";
	public static final String COL_LEVEL_KEY = "teachingLevelKey";
	public static final String COL_PRESENT_ADDRESS_LINE_1 = "presentAddressLine1";
	public static final String COL_PRESENT_ADDRESS_LINE_2 = "presentAddressLine2";
	public static final String COL_PRESENT_ADDRESS_CITY_KEY = "presentAddressCityKey";
	public static final String COL_PRESENT_ADDRESS_PINCODE = "presentAddressPincode";
	public static final String COL_PRESENT_ADDRESS_STATE_KEY = "presentAddressStateKey";
	public static final String COL_PRESENT_ADDRESS_COUNTRY_KEY = "presentAddressCountryKey";
	public static final String COL_PERMANENT_ADDRESS_LINE_1 = "permanentAddressLine1";
	public static final String COL_PERMANENT_ADDRESS_LINE_2 = "permanentAddressLine2";
	public static final String COL_PERMANENT_ADDRESS_CITY_KEY = "permanentAddressCityKey";
	public static final String COL_PERMANENT_ADDRESS_PINCODE = "permanentAddressPincode";
	public static final String COL_PERMANENT_ADDRESS_STATE_KEY = "permanentAddressStateKey";
	public static final String COL_PERMANENT_ADDRESS_COUNTRY_KEY = "permanentAddressCountryKey";
	public static final String COL_PRESENT_ADDRESS_ALTERNATE_CITY = "presentAddressAlternateCity" ;
	public static final String COL_PERMANENT_ADDRESS_ALTERNATE_CITY = "permanentAddressAlternateCity" ;
	public static final String COL_ACTIVE = "active";
	public static final String COL_DATE_JOINED = "dateJoined";
	public static final String COL_DATE_RELIEVED = "dateRelieved" ;
	public static final String COL_IS_DELETED = "isDeleted" ;
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_TITLE_KEY = "titleKey";
	public static final String COL_DESIGNATION = "designation";
	public static final String COL_PAN = "pan";
	public static final String COL_BLOOD_GROUP_KEY = "bloodGroupKey";
	public static final String COL_DATE_OF_BIRTH = "dateOfBirth";
	public static final String COL_USER_DEFINED_STRING_FIELD1 = "userDefinedStringField1";
	public static final String COL_USER_DEFINED_STRING_FIELD2 = "userDefinedStringField2";
	public static final String COL_USER_DEFINED_STRING_FIELD3 = "userDefinedStringField3";
	public static final String COL_USER_DEFINED_DATE_FIELD1 = "userDefinedDateField1";
	
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_EMAIL_ID = "emailId";
	
	
	@Autowired
	public TeacherMetaData(ValueMetaData valueMetaData) {
		super(ENTITY_NAME, new String[] { COL_FIRST_NAME, COL_CELLNUMBER_CODE, COL_CELLNUMBER },
				COL_FIRST_NAME_UPPER_CASE,  new String[]{COL_FIRST_NAME,COL_MIDDLE_NAME,COL_LAST_NAME}, entityClass, getRelatedEntitiesMap(valueMetaData), getFilterList());
	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(ValueMetaData valueMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Value.class, valueMetaData);
		return relatedEntitiesMap;
	}
	
	private static List<String> getFilterList()	{
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_FROM_DATE);
		filterList.add(COL_TO_DATE);
		return filterList;
	}
	
	@Override
	protected void initialize() {
		addColumnMetaData(COL_TEACHER_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_FIRST_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FIRST_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MIDDLE_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LAST_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_QUALIFICATION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LANDLINENUMBER_CODE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LANDLINENUMBER, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_CELLNUMBER_CODE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_CELLNUMBER, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE, new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_FROM));
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE, new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_TO));

		addColumnMetaData(COL_LEVEL_KEY, ColumnMetaData.ColumnType.Key);
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
		addColumnMetaData(COL_PERMANENT_ADDRESS_LINE_1,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_LINE_2,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_CITY_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PERMANENT_ADDRESS_PINCODE,ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_ALTERNATE_CITY, ColumnMetaData.ColumnType.STRING) ;
		addColumnMetaData(COL_PRESENT_ADDRESS_ALTERNATE_CITY, ColumnMetaData.ColumnType.STRING) ;
		
		addColumnMetaData(COL_PERMANENT_ADDRESS_STATE_KEY,
				ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PERMANENT_ADDRESS_COUNTRY_KEY,
				ColumnMetaData.ColumnType.Key);

		addColumnMetaData(COL_LEVEL_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_PRESENT_ADDRESS_LINE_1, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_LINE_2, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_CITY_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PRESENT_ADDRESS_PINCODE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_STATE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PRESENT_ADDRESS_COUNTRY_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PERMANENT_ADDRESS_LINE_1, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_LINE_2, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_CITY_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PERMANENT_ADDRESS_PINCODE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PERMANENT_ADDRESS_STATE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_PERMANENT_ADDRESS_COUNTRY_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_DATE_RELIEVED,ColumnMetaData.ColumnType.DATE );
		addColumnMetaData(COL_DATE_JOINED,ColumnMetaData.ColumnType.DATE );
		addColumnMetaData(COL_TITLE_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_DESIGNATION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PAN, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_BLOOD_GROUP_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_DATE_OF_BIRTH, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD1, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD2, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD3, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_DATE_FIELD1, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_EMAIL_ID, ColumnMetaData.ColumnType.STRING);
	
		
	}
}