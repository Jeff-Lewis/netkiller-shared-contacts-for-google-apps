package com.metacube.ipathshala.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.ClassSubjectTeacher;
import com.metacube.ipathshala.entity.MyClass;
import com.metacube.ipathshala.entity.Parent;
import com.metacube.ipathshala.entity.Period;
import com.metacube.ipathshala.entity.TimetableEntry;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData.ColumnType;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * 
 * @author sabir
 * 
 */
@Component("TimetableEntryMetaData")
public class TimetableEntryMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "TimetableEntry";
	public static final Class<?> entityClass = TimetableEntry.class;
	public static final String COL_TIMETABLE_KEY = "key";
	public static final String COL_CLASS_KEY = "classKey";
	public static final String COL_PERIOD_KEY = "periodKey";
	public static final String COL_CLASSSUBJECTTEACHER_KEY = "classSubjectTeacherKey";
	public static final String COL_WEEKDAY = "weekDay";
	public static final String COL_REPEAT_OPTION = "repeatOption";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted"; 
		
	@Autowired
	public TimetableEntryMetaData(MyClassMetaData myClassMetaData, PeriodMetaData periodMetaData, ClassSubjectTeacherMetaData classSubjectTeacherMetaData) {
		super(ENTITY_NAME, new String[] {COL_CLASS_KEY, COL_CLASSSUBJECTTEACHER_KEY, COL_PERIOD_KEY}, COL_CLASS_KEY,null,entityClass, getRelatedEntityMap(myClassMetaData,periodMetaData,classSubjectTeacherMetaData), null);
	}

	private static Map<Class,EntityMetaData> getRelatedEntityMap(MyClassMetaData myClassMetaData,
			PeriodMetaData periodMetaData,
			ClassSubjectTeacherMetaData classSubjectTeacherMetaData) {
		
		Map<Class,EntityMetaData> relatedEntitiesMap= new HashMap();
		relatedEntitiesMap.put(MyClass.class, myClassMetaData);
		relatedEntitiesMap.put(Period.class, periodMetaData);
		relatedEntitiesMap.put(ClassSubjectTeacher.class,classSubjectTeacherMetaData);
		
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_CLASS_KEY, ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(MyClass.class),
				RelationshipType.ONE_TO_ONE_OWNED));		
		addColumnMetaData(COL_PERIOD_KEY, ColumnType.Key,new ColumnRelationShipMetaData(relatedEntityTypesMap.get(Period.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_REPEAT_OPTION, ColumnType.INT);
		addColumnMetaData(COL_CLASSSUBJECTTEACHER_KEY, ColumnType.Key,new ColumnRelationShipMetaData(relatedEntityTypesMap.get(ClassSubjectTeacher.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TIMETABLE_KEY, ColumnType.Key);		
		addColumnMetaData(COL_WEEKDAY, ColumnType.STRING);
		addColumnMetaData(COL_FROM_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_TO_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
	}

}
