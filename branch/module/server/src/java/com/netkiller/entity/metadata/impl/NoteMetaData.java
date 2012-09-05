/**
 * 
 */
package com.netkiller.entity.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.Note;
import com.netkiller.entity.Parent;
import com.netkiller.entity.Student;
import com.netkiller.entity.Teacher;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author sparakh
 * 
 */

/*
 * The bean name is set to "NoteMetaData". This name should be used in the @Qualifier with @Autowired.
 * If you want to use @Resource annotation simple use this in name attribute. But please follow @Autowired 
 * with @Qualifier for consistency. See NoteService.java 
 */
@Component("NoteMetaData")
public class NoteMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Note";
	public static final Class<?> entityClass = Note.class;
	public static final String COL_NOTE_KEY = "key";
	public static final String COL_NOTE_ACADEMIC_SESSION_ID = "academicSessionId";
	public static final String COL_STUDENT_ID = "studentId";
	public static final String COL_NOTE_DATE = "noteDate";
	public static final String COL_NOTE_SUBJECT = "noteSubject";
	public static final String COL_NOTE_DESCRIPTION = "noteDescription";
	public static final String COL_NOTE_PRIORITY_KEY = "notePriorityKey";
	public static final String COL_NOTE_SUBJECT_UPPERCASE = "noteSubjectUpperCase";
	public static final String COL_NOTE_SENDER_KEY = "senderKey";
	public static final String COL_NOTE_SENDER = "sender";
	public static final String COL_NOTE_STATUS_KEY = "noteStatusKey";
	public static final String COL_NOTE_TEACHER_ID = "teacherId";
	public static final String COL_NOTE_VISIBLE_TO_STUDENT = "visibleToStudent";
	public static final String COL_NOTE_MUST_RESPOND = "mustRespond";
	public static final String COL_NOTE_DUE_DATE = "dueDate";
	public static final String COL_NOTE_PARENT_ID = "parentId";
	public static final String COL_IS_DELETED = "isDeleted";
	public static final String COL_NOTE_SEND_SMS = "sendSMS";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";
	
	@Autowired
	public NoteMetaData(AcademicYearMetaData academicYearMetaData, TeacherMetaData teacherMetaData, ParentMetaData parentMetaData, StudentMetaData studentMetadata,ValueMetaData valueMetaData) {
		super(ENTITY_NAME,new String[]{COL_NOTE_SUBJECT},COL_NOTE_SUBJECT_UPPERCASE,new String[]{COL_NOTE_SUBJECT}, entityClass, getRelatedEntitiesMap(academicYearMetaData,teacherMetaData,parentMetaData,studentMetadata,valueMetaData), getFilterList());
	}
	
	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(AcademicYearMetaData academicYearMetaData, TeacherMetaData teacherMetaData, ParentMetaData parentMetaData, StudentMetaData studentMetadata,ValueMetaData valueMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Teacher.class, teacherMetaData);
		relatedEntitiesMap.put(Student.class, studentMetadata);
		relatedEntitiesMap.put(Parent.class, parentMetaData);
		relatedEntitiesMap.put(Value.class, valueMetaData);
		return relatedEntitiesMap;
	}
	
	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_NOTE_ACADEMIC_SESSION_ID);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_NOTE_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NOTE_ACADEMIC_SESSION_ID,  ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_STUDENT_ID, ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Student.class), RelationshipType.ONE_TO_ONE_OWNED),new FilterMetaData(
						GlobalFilterType.STUDENT, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_NOTE_DATE, ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_NOTE_SUBJECT, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_NOTE_SENDER, ColumnMetaData.ColumnType.STRING);
		
		addColumnMetaData(COL_NOTE_DESCRIPTION,  ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_NOTE_PRIORITY_KEY,  ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_NOTE_SUBJECT_UPPERCASE,  ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_NOTE_SENDER_KEY,  ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_NOTE_STATUS_KEY,  ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_NOTE_TEACHER_ID,  ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Teacher.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_NOTE_VISIBLE_TO_STUDENT,  ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_NOTE_MUST_RESPOND, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_NOTE_DUE_DATE,  ColumnMetaData.ColumnType.DATE);
		addColumnMetaData(COL_NOTE_PARENT_ID,  ColumnMetaData.ColumnType.Key,new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Parent.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_NOTE_ACADEMIC_SESSION_ID, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		addColumnMetaData(COL_NOTE_SEND_SMS, ColumnMetaData.ColumnType.BOOL);
		addColumnMetaData(COL_LAST_MODIFIED_DATE,ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_DATE, ColumnMetaData.ColumnType.DATE, new SystemMetaData(OperationType.CREATE));
		addColumnMetaData(COL_LAST_MODIFIED_BY,ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.MODIFY));
		addColumnMetaData(COL_CREATED_BY, ColumnMetaData.ColumnType.STRING, new SystemMetaData(OperationType.CREATE));
	
	}
}
