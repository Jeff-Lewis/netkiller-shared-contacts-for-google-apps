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

import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.Parent;
import com.netkiller.entity.Student;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author amit.c
 * 
 */

/*
 * The bean name is set to "StudentMetaData". This name should be used in the
 * 
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See StudentService.java
 */
@Component("StudentMetaData")
public class StudentMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Student";
	public static final Class<?> entityClass = Student.class;
	public static final String COL_STUDENT_KEY = "key";
	public static final String COL_ENROLLMENT_NUMBER = "enrollmentNumber";
	public static final String COL_ENROLLMENT_DATE = "enrollmentDate";
	public static final String COL_FIRST_NAME = "firstName";
	public static final String COL_FIRST_NAME_UPPER_CASE = "firstNameUpperCase";
	public static final String COL_MIDDLE_NAME = "middleName";
	public static final String COL_LAST_NAME = "lastName";
	public static final String COL_GENDER_KEY = "gender";
	public static final String COL_PRIMARY_LANDLINENUMBER_CODE = "primaryLandlineNumberCode";
	public static final String COL_PRIMARY_LANDLINENUMBER = "primaryLandlineNumber";
	public static final String COL_PRIMARY_CELLNUMBER_CODE = "primaryCellNumberCode";
	public static final String COL_PRIMARY_CELLNUMBER = "primaryCellNumber";
	public static final String COL_USER_ID = "userId";
	public static final String COL_PASSWORD = "password";
	public static final String COL_PRESENT_ADDRESS_LINE_1 = "presentAddressLine1";
	public static final String COL_PRESENT_ADDRESS_LINE_2 = "presentAddressLine2";
	public static final String COL_PRESENT_ADDRESS_CITY_KEY = "presentAddressCityKey";
	public static final String COL_PRESENT_ADDRESS_PINCODE = "presentAddressPincode";
	public static final String COL_PRESENT_ADDRESS_STATE_KEY = "presentAddressStateKey";
	public static final String COL_PRESENT_ADDRESS_COUNTRY_KEY = "presentAddressCountryKey";
	public static final String COL_PARENT_KEY = "parentKey";
	public static final String COL_ACTIVE = "active";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_DATE_RELIEVED = "dateRelieved";
	public static final String COL_LIVING_STATUS_KEY = "livingStatusKey" ;
	public static final String COL_IS_DELETED = "isDeleted" ;
	public static final String COL_PRESENT_ADDRESS_ALTERNATE_CITY = "presentAddressAlternateCity" ;
	public static final String COL_FROM_ACADEMIC_YEAR_KEY = "fromAcademicYearKey";
	public static final String COL_TO_ACADEMIC_YEAR_KEY = "toAcademicYearKey";
	public static final String COL_HOUSE = "house";
	public static final String COL_BLOOD_GROUP_KEY = "bloodGroupKey";
	public static final String COL_BLOOD_GROUP = "bloodGroup";
	public static final String COL_DATE_OF_BIRTH = "dateOfBirth";
	public static final String COL_USER_DEFINED_STRING_FIELD1 = "userDefinedStringField1";
	public static final String COL_USER_DEFINED_STRING_FIELD2 = "userDefinedStringField2";
	public static final String COL_USER_DEFINED_STRING_FIELD3 = "userDefinedStringField3";
	public static final String COL_USER_DEFINED_DATE_FIELD1 = "userDefinedDateField1";
	
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	
	@Autowired
	public StudentMetaData(ParentMetaData parentMetaData,MyClassMetaData classMetaData, ValueMetaData valueMetaData, AcademicYearMetaData academicYearMetaData) {		
		super(ENTITY_NAME, new String[] { COL_ENROLLMENT_NUMBER },
				COL_FIRST_NAME_UPPER_CASE, new String[]{COL_FIRST_NAME,COL_MIDDLE_NAME,COL_LAST_NAME}, entityClass, getRelatedEntitiesMap(parentMetaData,classMetaData, valueMetaData, academicYearMetaData), getFilterList());
		
	}
	
	private static Map<Class,EntityMetaData> getRelatedEntitiesMap(ParentMetaData parentMetaData,MyClassMetaData classMetaData, ValueMetaData valueMetaData, AcademicYearMetaData academicYearMetaData){
		Map<Class,EntityMetaData> relatedEntitiesMap= new HashMap();
		relatedEntitiesMap.put(Parent.class, parentMetaData);
		relatedEntitiesMap.put(MyClass.class, classMetaData);
		relatedEntitiesMap.put(Value.class, valueMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		return relatedEntitiesMap;
	}
	
	private static List<String> getFilterList()	{
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACTIVE);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_STUDENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_ENROLLMENT_NUMBER, ColumnMetaData.ColumnType.STRING, true);
		addColumnMetaData(COL_ENROLLMENT_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_FIRST_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FIRST_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MIDDLE_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LAST_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_GENDER_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_PRIMARY_LANDLINENUMBER_CODE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRIMARY_LANDLINENUMBER, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRIMARY_CELLNUMBER_CODE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRIMARY_CELLNUMBER, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_ID, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PASSWORD, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_LINE_1, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_LINE_2, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_PRESENT_ADDRESS_CITY_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_PRESENT_ADDRESS_PINCODE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PRESENT_ADDRESS_STATE_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_PRESENT_ADDRESS_COUNTRY_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_PARENT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(Parent.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.ACTIVE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LIVING_STATUS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_DATE_RELIEVED, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_HOUSE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_BLOOD_GROUP_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_DATE_OF_BIRTH, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_BLOOD_GROUP, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD1, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD2, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_STRING_FIELD3, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_USER_DEFINED_DATE_FIELD1, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		
		
		/*addColumnMetaData(COL_FROM_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_FROM));
		addColumnMetaData(COL_TO_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_TO));*/
	}
}
