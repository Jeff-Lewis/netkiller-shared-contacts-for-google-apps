package com.metacube.ipathshala.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.ClassStudent;
import com.metacube.ipathshala.entity.MyClass;
import com.metacube.ipathshala.entity.Student;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData.ColumnType;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;

/**
 * 
 * @author sparakh
 * 
 */
@Component("ClassStudentMetaData")
public class ClassStudentMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "ClassStudentMetaData";

	public static final String COL_CLASS_STUDENT_KEY = "key";
	public static final Class<?> entityClass = ClassStudent.class;
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_CLASS_KEY = "classKey";
	public static final String COL_STUDENT_KEY = "studentKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted";

	@Autowired
	public ClassStudentMetaData(MyClassMetaData classMetaData, StudentMetaData studentMetaData) {
		super(ENTITY_NAME, new String[] { COL_CLASS_KEY, COL_STUDENT_KEY }, COL_CLASS_KEY, null,
				entityClass, getRelatedEntitiesMap(classMetaData, studentMetaData), getFilterList());
	}

	@Autowired
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(MyClassMetaData classMetaData,
			StudentMetaData studentMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(MyClass.class, classMetaData);
		relatedEntitiesMap.put(Student.class, studentMetaData);
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_CLASS_KEY, ColumnType.Key);
		addColumnMetaData(COL_CLASS_STUDENT_KEY, ColumnType.Key);
		addColumnMetaData(COL_STUDENT_KEY, ColumnType.Key);
		addColumnMetaData(COL_CLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(MyClass.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_STUDENT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Student.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
	}

}
