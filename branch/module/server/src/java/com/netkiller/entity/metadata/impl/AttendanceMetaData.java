package com.netkiller.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.Attendance;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.Period;
import com.netkiller.entity.Student;
import com.netkiller.entity.Term;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author Jitender
 * 
 */
@Component("AttendanceMetaData")
public class AttendanceMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Attendance";
	public static final Class<?> entityClass = Attendance.class;
	public static final String COL_Attendance_KEY = "key";
	public static final String COL_CLASS_KEY = "myClassKey";
	public static final String COL_STUDENT_KEY = "studentKey";
	public static final String COL_TERM_KEY = "termKey";
	public static final String COL_PERIOD_KEY = "periodKey";
	public static final String COL_TERM_ATTENDANCE = "termAttendance";
	public static final String COL_ATTENDANCE_DATE = "attendanceDate";
	public static final String COL_IS_PRESENT = "isPresent";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	@Autowired
	public AttendanceMetaData(MyClassMetaData myClassMetaData,
			StudentMetaData studentMetaData, PeriodMetaData periodMetaData, TermMetaData termMetaData) {
		super(ENTITY_NAME, new String[] { COL_Attendance_KEY },
				COL_Attendance_KEY, new String[] { COL_Attendance_KEY },
				entityClass, getRelatedEntitiesMap(myClassMetaData,
						studentMetaData, periodMetaData,termMetaData), getFilterList());
	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(
			MyClassMetaData myClassMetaData, StudentMetaData studentMetaData,
			PeriodMetaData periodMetaData, TermMetaData termMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(MyClass.class, myClassMetaData);
		relatedEntitiesMap.put(Student.class, studentMetaData);
		relatedEntitiesMap.put(Period.class, periodMetaData);
		relatedEntitiesMap.put(TermMetaData.class, termMetaData);
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();

		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_Attendance_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(
				COL_CLASS_KEY,
				ColumnMetaData.ColumnType.Key,
				new ColumnRelationShipMetaData(relatedEntityTypesMap
						.get(MyClass.class), RelationshipType.ONE_TO_ONE_OWNED));
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
		addColumnMetaData(
				COL_PERIOD_KEY,
				ColumnMetaData.ColumnType.Key,
				new ColumnRelationShipMetaData(relatedEntityTypesMap
						.get(Period.class), RelationshipType.ONE_TO_ONE_OWNED));

		addColumnMetaData(COL_TERM_ATTENDANCE, ColumnMetaData.ColumnType.INT);
		addColumnMetaData(COL_ATTENDANCE_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_IS_PRESENT, ColumnMetaData.ColumnType.BOOL);
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
