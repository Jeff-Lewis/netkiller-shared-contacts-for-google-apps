/**
 * 
 */
package com.metacube.ipathshala.entity.metadata.impl;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.Location;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * @author kunal.n
 * 
 */

/*
 * The bean name is set to "LocationMetaData". This name should be used in the @Qualifier with @Autowired.
 * If you want to use @Resource annotation simple use this in name attribute. But please follow @Autowired 
 * with @Qualifier for consistency. See LocationService.java 
 */
@Component("LocationMetaData")
public class LocationMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Location";
	public static final Class<?> entityClass = Location.class;
	public static final String COL_LOCATION_KEY = "key";
	public static final String COL_LOCATION_NAME = "locationName";
	public static final String COL_LOCATION_NAME_UPPER_CASE = "locationNameUpperCase";
	public static final String COL_LOCATION_DESCRIPTION = "locationDescription";
	public static final String COL_LOCATION_CAPACITY = "locationCapacity";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	public LocationMetaData() {
		super(ENTITY_NAME,new String[]{COL_LOCATION_NAME},COL_LOCATION_NAME_UPPER_CASE,new String[]{COL_LOCATION_NAME}, entityClass, null, null);
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_LOCATION_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_LOCATION_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LOCATION_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LOCATION_DESCRIPTION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LOCATION_CAPACITY, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE,new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_FROM));
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE,new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.RANGE_TO));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
	}
}
