package com.netkiller.entity.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.EvaluationComponent;
import com.netkiller.entity.EvaluationStage;
import com.netkiller.entity.EvaluationSubComponent;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.Subject;
import com.netkiller.entity.SubjectEvaluationEvent;
import com.netkiller.entity.Teacher;
import com.netkiller.entity.Term;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;

@Component("SubjectEvaluationEventMetaData")
public class SubjectEvaluationEventMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "SubjectEvaluationEvent";
	public static final Class<?> entityClass = SubjectEvaluationEvent.class;
	public static final String COL_SUBJECT_EVALUATION_EVENT_KEY = "key";
	public static final String COL_CLASS_KEY = "classKey";
	public static final String COL_SUBJECT_KEY = "subjectKey";
	public static final String COL_TEACHER_KEY = "teacherKey";
	public static final String COL_SEQUENCE_NUMBER = "sequenceNumber";
	public static final String COL_EVALUATION_COMPONENT_KEY = "evaluationComponentKey";
	public static final String COL_SUB_EVALUATION_COMPONENT_KEY = "evaluationSubComponentKey";
	public static final String COL_EVENT_NAME = "eventName";
	public static final String COL_WEIGHTAGE = "weightage";
	public static final String COL_DATETIME = "datetime";
	public static final String COL_MAX_MARKS = "maxMarks";
	public static final String COL_COMPONENT_CALCULATION_METHOD_KEY = "componentCalcMethodKey";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String COL_TERM_KEY = "termKey";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_STAGE_KEY = "stageKey";
	public static final String COL_IS_ENABLED = "isEnabled";
	public static final String COL_IS_TEMPLATE_GENERATED = "isTemplateGenerated";
	public static final String COL_IS_VISIBLE = "isVisible";
	public static final String COL_IS_SUBMIT_MARKS = "isSubmitMarks";
	public static final String COL_CALCULATION_METHOD = "calculationMethod";

	@Autowired
	public SubjectEvaluationEventMetaData(
			MyClassMetaData classMetaData,
			SubjectMetaData subjectMetaData,
			TeacherMetaData teacherMetaData,
			EvaluationComponentMetaData componentMetaData,
			EvaluationSubComponentMetaData evaluationSubComponentMetaData, TermMetaData termMetaData, EvaluationStageMetaData evaluationStageMetaData) {
		
		super(ENTITY_NAME, new String[] { COL_EVENT_NAME },
				COL_SUBJECT_EVALUATION_EVENT_KEY,null, entityClass, getRelatedEntitiesMap(classMetaData,subjectMetaData,teacherMetaData,componentMetaData,evaluationSubComponentMetaData,termMetaData,evaluationStageMetaData),null);
		}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(MyClassMetaData myClassMetaData, SubjectMetaData subjectMetaData, TeacherMetaData teacherMetaData,
			EvaluationComponentMetaData componentMetaData,
			EvaluationSubComponentMetaData evaluationSubComponentMetaData, TermMetaData termMetaData, EvaluationStageMetaData evaluationStageMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(MyClass.class, myClassMetaData);
		relatedEntitiesMap.put(Subject.class, subjectMetaData);
		relatedEntitiesMap.put(Teacher.class, teacherMetaData);
		relatedEntitiesMap.put(EvaluationComponent.class, componentMetaData);
		relatedEntitiesMap.put(EvaluationSubComponent.class, evaluationSubComponentMetaData);
		relatedEntitiesMap.put(Term.class, termMetaData);
		relatedEntitiesMap.put(EvaluationStage.class, evaluationStageMetaData);
		return relatedEntitiesMap;
	}


	@Override
	protected void initialize() {
		addColumnMetaData(COL_SUBJECT_EVALUATION_EVENT_KEY , ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_CLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(MyClass.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_SUBJECT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Subject.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TERM_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Term.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TEACHER_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Teacher.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_EVALUATION_COMPONENT_KEY , ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(EvaluationComponent.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_SUB_EVALUATION_COMPONENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_EVENT_NAME , ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_SEQUENCE_NUMBER , ColumnMetaData.ColumnType.INT);
		addColumnMetaData(COL_DATETIME , ColumnMetaData.ColumnType.DATETIME);
		addColumnMetaData(COL_MAX_MARKS , ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_WEIGHTAGE , ColumnMetaData.ColumnType.DOUBLE);
		addColumnMetaData(COL_COMPONENT_CALCULATION_METHOD_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_STAGE_KEY , ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(EvaluationStage.class),RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_IS_ENABLED , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_IS_TEMPLATE_GENERATED , ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_IS_VISIBLE, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_IS_SUBMIT_MARKS, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_CALCULATION_METHOD, ColumnMetaData.ColumnType.STRING);
	}

}
