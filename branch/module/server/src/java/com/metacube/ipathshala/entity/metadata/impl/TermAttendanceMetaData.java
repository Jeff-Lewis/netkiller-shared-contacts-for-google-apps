package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.Student;
import com.metacube.ipathshala.entity.Term;
import com.metacube.ipathshala.entity.TermAttendance;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

@Component("TermAttendanceMetaData")
public class TermAttendanceMetaData extends AbstractMetaData {
	
	public static final String ENTITY_NAME = "TermAttendance";
	public static final Class<?> entityClass = TermAttendance.class;
	public static final String COL_TERM_ATTENDACE_KEY = "key";
	public static final String COL_STUDENT_KEY = "studentKey";
	public static final String COL_TERM_KEY = "termKey";
	public static final String COL_TERM_ATTENDANCE = "termAttendance";
	public static final String COL_WORKING_DAYS = "workingDays";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	@Autowired
	public TermAttendanceMetaData(StudentMetaData studentMetaData,TermMetaData termMetaData) {
		super(ENTITY_NAME, new String[] { COL_TERM_ATTENDACE_KEY },
				COL_TERM_ATTENDACE_KEY, new String[] { COL_TERM_ATTENDACE_KEY },
				entityClass, getRelatedEntitiesMap(studentMetaData, termMetaData), getFilterList());
	}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(StudentMetaData studentMetaData,TermMetaData termMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(Student.class, studentMetaData);
		relatedEntitiesMap.put(TermMetaData.class, termMetaData);
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();

		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_TERM_ATTENDACE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(
				COL_TERM_KEY,
				ColumnMetaData.ColumnType.Key,
				new ColumnRelationShipMetaData(relatedEntityTypesMap
						.get(Term.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(
				COL_STUDENT_KEY,
				ColumnMetaData.ColumnType.Key,
				new ColumnRelationShipMetaData(relatedEntityTypesMap
						.get(Student.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TERM_ATTENDANCE, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_WORKING_DAYS, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL,
				new FilterMetaData(GlobalFilterType.DELETE,
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
