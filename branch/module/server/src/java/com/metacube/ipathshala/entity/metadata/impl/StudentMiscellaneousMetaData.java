package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.AcademicYearStructure;
import com.metacube.ipathshala.entity.MyClass;
import com.metacube.ipathshala.entity.Parent;
import com.metacube.ipathshala.entity.StudentMiscellaneous;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData.ColumnType;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;

@Component("StudentMiscellaneousMetaData")
public class StudentMiscellaneousMetaData extends AbstractMetaData{
	public static final String ENTITY_NAME = "StudentMiscellaneous";
	public static final String STUDENT_MISCELLANEOUS_KEY = "key";
	public static final Class<?> entityClass = StudentMiscellaneous.class;
	public static final String COL_ROLL_NUMBER = "rollNumber";
	public static final String COL_HEIGHT = "height";
	public static final String COL_WEIGHT = "weight";
	public static final String COL_LEFT_VISION = "leftVision";
	public static final String COL_RIGHT_VISION = "rightVision";
	public static final String COL_DENTAL_HYGIENE = "dentalHygiene";
	public static final String COL_MY_GOALS = "myGoals";
	public static final String COL_STRENGHTS = "strenghts";
	public static final String COL_INTEREST_AND_HOBBIES = "interestAndHobbies";
	public static final String COL_EXCEPTIONAL_ACHIEVEMENTS = "exceptionalAchievements";
	public static final String COL_STUDENT_KEY = "studentKey";
	public static final String COL_ACADEMICYEAR_KEY = "academicYearKey";
	
	
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	

	@Autowired
	protected StudentMiscellaneousMetaData(AcademicYearMetaData academicYearMetaData) {
		super(ENTITY_NAME, null, null, null,entityClass, getRelatedEntitiesMap(academicYearMetaData), getFilterList());
		// TODO Auto-generated constructor stub
	}
	
	private static Map<Class,EntityMetaData> getRelatedEntitiesMap(AcademicYearMetaData academicYearMetaData){
		Map<Class,EntityMetaData> relatedEntitiesMap= new HashMap();
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		return relatedEntitiesMap;
	}
	
	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMICYEAR_KEY);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(ENTITY_NAME, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(STUDENT_MISCELLANEOUS_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_ROLL_NUMBER, ColumnMetaData.ColumnType.STRING,true);
		addColumnMetaData(COL_HEIGHT, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_WEIGHT, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_LEFT_VISION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_RIGHT_VISION, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DENTAL_HYGIENE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_MY_GOALS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_STRENGHTS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_INTEREST_AND_HOBBIES, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_EXCEPTIONAL_ACHIEVEMENTS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_ACADEMICYEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		
		
	}

}
