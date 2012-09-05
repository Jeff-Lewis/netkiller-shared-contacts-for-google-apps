package com.netkiller.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.Student;
import com.netkiller.entity.StudentMarks;
import com.netkiller.entity.SubjectEvaluationEvent;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;
@Component("StudentMarksMetaData")
public class StudentMarksMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "StudentMarks";
	public static final Class<?> entityClass = StudentMarks.class;
	public static final String COL_STUDENT_MARKS_KEY = "key";
	public static final String COL_SUBJECT_EVALUATION_EVENT_KEY = "subjectEvaluationEventKey";
	public static final String COL_STUDENT_KEY = "studentKey";
	public static final String COL_MARKS = "marks";
	public static final String COL_GRADE = "grade";
	public static final String COL_COMPONENTS = "components";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_IS_DELETED = "isDeleted";
	
	
	
	@Autowired
	public StudentMarksMetaData(SubjectEvaluationEventMetaData subjectEvaluationEventMetaData, StudentMetaData studentMetaData){
		super(ENTITY_NAME, new String[] { COL_MARKS,COL_GRADE }, COL_SUBJECT_EVALUATION_EVENT_KEY,
				 null, entityClass, getRelatedEntitiesMap(subjectEvaluationEventMetaData,
						 studentMetaData), null);
	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(SubjectEvaluationEventMetaData subjectEvaluationEventMetaData, StudentMetaData studentMetaData){
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(SubjectEvaluationEvent.class, subjectEvaluationEventMetaData);
		relatedEntitiesMap.put(Student.class, studentMetaData);
		return relatedEntitiesMap;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_STUDENT_MARKS_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_SUBJECT_EVALUATION_EVENT_KEY, ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(SubjectEvaluationEvent.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_STUDENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_MARKS, ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_GRADE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_COMPONENTS, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		
		
	}

}
