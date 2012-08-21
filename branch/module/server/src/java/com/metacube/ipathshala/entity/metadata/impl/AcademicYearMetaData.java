/**
 * 
 */
package com.metacube.ipathshala.entity.metadata.impl;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * @author kunal.n
 * 
 */

/*
 * The bean name is set to "AcademicYearMetaData". This name should be used in
 * the @Qualifier with @Autowired. If you want to use @Resource annotation
 * simple use this in name attribute. But please follow @Autowired with
 * @Qualifier for consistency. See AcademicYearService.java
 */
@Component("AcademicYearMetaData")
public class AcademicYearMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "AcademicYear";
	public static final Class<?> entityClass = AcademicYear.class;
	public static final String COL_ACADEMICYEAR_KEY = "key";
	public static final String COL_NAME = "name";
	public static final String COL_NAME_UPPER_CASE = "nameUpperCase";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_ACTIVE = "active";
	public static final String COL_TYPE_KEY = "typeKey";
	public static final String COL_SESSION_ACCOUNT = "sessionAccount";
	public static final String COL_IS_DELETED = "isDeleted" ;
	public static final String COL_IS_DEFAULT_ACADEMIC_YEAR = "isDefaultAcademicYear" ; 
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	public AcademicYearMetaData() {
		super(ENTITY_NAME, new String[] { COL_NAME }, COL_NAME_UPPER_CASE, new String[] { COL_NAME }, entityClass, null, null);
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_ACADEMICYEAR_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_NAME_UPPER_CASE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_DESCRIPTION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ACTIVE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_TYPE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_SESSION_ACCOUNT, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_IS_DEFAULT_ACADEMIC_YEAR, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
	}


}
