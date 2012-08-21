/**
 * 
 */
package com.metacube.ipathshala.entity.metadata.impl;


import org.springframework.stereotype.Component;
import com.metacube.ipathshala.entity.School;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * @author sandeep
 *
 */
@Component("SchoolMetaData")
public class SchoolMetaData extends AbstractMetaData{

	public SchoolMetaData() {
		super(ENTITY_NAME, new String[] { COL_SCHOOL_NAME },
				COL_SCHOOL_NAME_UPPER_CASE, new String[]{COL_SCHOOL_NAME_UPPER_CASE}, entityClass, null, null);
	}
	public static final String ENTITY_NAME = "School";
	public static final Class<?> entityClass = School.class;
	public static final String COL_KEY = "key";
	public static final String COL_SCHOOL_NAME = "schoolName";
	public static final String COL_SCHOOL_NAME_UPPER_CASE = "schoolNameUpperCase";
	public static final String COL_ADDRESS = "address";
	public static final String COL_PHONE = "phoneNo";
	public static final String COL_FAX = "faxNo";
	public static final String COL_EMAIL = "email";
	public static final String COL_WEBSITE = "websiteName";
	public static final String COL_AFFILIATIONNO = "affiliationNo";
	public static final String IS_DELETED = "isDeleted";
	@Override
	protected void initialize() {
		addColumnMetaData(COL_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_SCHOOL_NAME,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SCHOOL_NAME_UPPER_CASE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ADDRESS,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_PHONE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_FAX,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_EMAIL,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_WEBSITE,
				ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_AFFILIATIONNO,
				ColumnMetaData.ColumnType.STRING, true);
		addColumnMetaData(IS_DELETED,
				ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
						GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
	}
	
}
