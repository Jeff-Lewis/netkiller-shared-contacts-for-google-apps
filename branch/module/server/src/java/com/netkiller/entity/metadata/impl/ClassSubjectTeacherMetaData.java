package com.netkiller.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.ClassSubjectTeacher;
import com.netkiller.entity.EvaluationScheme;
import com.netkiller.entity.Location;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.Subject;
import com.netkiller.entity.Teacher;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;

/**
 * 
 * @author sabir
 *
 */
@Component("ClassSubjectTeacherMetaData")
public class ClassSubjectTeacherMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "ClassSubjectTeacherMetaData";
	
	public static final String COL_CLASS_SUBJECT_TEACHER_KEY = "key";
	public static final Class<?> entityClass = ClassSubjectTeacher.class;
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_CLASS_KEY = "classKey";
	public static final String COL_SUBJECT_KEY = "subjectKey";
	public static final String COL_TEACHER_KEY = "teacherKey";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_LOCATION_KEY = "locationKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_EVAL_SCHEME_KEY = "evalSchKey";
	public static final String COL_IS_EVAL_SCHEME_REMOVABLE = "isEvalSchemeRemovable";
	public static final String COL_INSTRUCTOR_INCHARGE = "instructorIncharge";
	public static final String COL_IS_SUBJECT = "isSubject";
	
	@Autowired
	public ClassSubjectTeacherMetaData(MyClassMetaData classMetaData, SubjectMetaData subjectMetaData, TeacherMetaData teacherMetaData,LocationMetaData locationMetaData, EvaluationSchemeMetaData evaluationSchemeMetaData) {
		super(ENTITY_NAME, new String[] {COL_CLASS_KEY, COL_SUBJECT_KEY, COL_LOCATION_KEY}, COL_TEACHER_KEY,null, entityClass, getRelatedEntitiesMap(classMetaData,subjectMetaData, teacherMetaData,locationMetaData,evaluationSchemeMetaData), getFilterList());
	}

	@Autowired
	private static Map<Class,EntityMetaData> getRelatedEntitiesMap(MyClassMetaData classMetaData, SubjectMetaData subjectMetaData, TeacherMetaData teacherMetaData,LocationMetaData locationMetaData, EvaluationSchemeMetaData evaluationSchemeMetaData)	{
		Map<Class,EntityMetaData> relatedEntitiesMap= new HashMap();
		relatedEntitiesMap.put(MyClass.class, classMetaData);
		relatedEntitiesMap.put(Subject.class, subjectMetaData);
		relatedEntitiesMap.put(Teacher.class, teacherMetaData);
		relatedEntitiesMap.put(Location.class, locationMetaData);
		relatedEntitiesMap.put(EvaluationScheme.class, evaluationSchemeMetaData);
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
		addColumnMetaData(COL_CLASS_SUBJECT_TEACHER_KEY, ColumnType.Key);
		addColumnMetaData(COL_SUBJECT_KEY, ColumnType.Key);
		addColumnMetaData(COL_TEACHER_KEY, ColumnType.Key);
		addColumnMetaData(COL_DESCRIPTION, ColumnType.STRING);
		addColumnMetaData(COL_CLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(MyClass.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_SUBJECT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(Subject.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TEACHER_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(Teacher.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_LOCATION_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(Location.class),
				RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_EVAL_SCHEME_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(relatedEntityTypesMap.get(EvaluationScheme.class),
				RelationshipType.MANY_TO_ONE));
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_IS_EVAL_SCHEME_REMOVABLE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_INSTRUCTOR_INCHARGE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_IS_SUBJECT, ColumnMetaData.ColumnType.BOOL);
	}

}
