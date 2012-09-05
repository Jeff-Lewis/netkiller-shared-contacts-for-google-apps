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
import com.netkiller.entity.Announcement;
import com.netkiller.entity.MyClass;
import com.netkiller.entity.Subject;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;

/**
 * @author ankit gupta
 * 
 */

/*
 * The bean name is set to "AnnouncementMetaData". This name should be used in
 * the
 * 
 * @Qualifier with @Autowired. If you want to use @Resource annotation simple
 * use this in name attribute. But please follow @Autowired with @Qualifier for
 * consistency. See AnnouncementService.java
 */
@Component("AnnouncementMetaData")
public class AnnouncementMetaData extends AbstractMetaData {

	public static final String ENTITY_NAME = "Announcement";
	public static final Class<?> entityClass = Announcement.class;
	public static final String COL_ANNOUNCEMENT_KEY = "key";
	public static final String COL_CLASS_KEY = "classKey";
	public static final String COL_SUBJECT_KEY = "subjectKey";
	public static final String COL_TYPE = "type";
	public static final String COL_ACADEMIC_YEAR_KEY = "academicYearKey";
	public static final String COL_TITLE = "title";
	public static final String COL_DETAIL = "detail";
	public static final String COL_IS_DELETED = "isDeleted";

	

	public static final String COL_ANNOUNCEMENT_ENTRY_ID = "announcementEntryId";

	public static final String COL_TITLE_UPPER_CASE = "titleUpperCase";
	public static final String COL_LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_LAST_MODIFIED_BY = "lastModifiedBy";

	@Autowired
	public AnnouncementMetaData(MyClassMetaData myClassMetaData, SubjectMetaData subjectMetaData,AcademicYearMetaData academicYearMetaData , ValueMetaData valueMetaData) {
		super(ENTITY_NAME, new String[]{}, COL_TITLE_UPPER_CASE, null, entityClass,
				getRelatedEntitiesMap(myClassMetaData, subjectMetaData,academicYearMetaData,valueMetaData),
				getFilterList());

	}

	private static Map<Class, EntityMetaData> getRelatedEntitiesMap(
			MyClassMetaData myClassMetaData, SubjectMetaData subjectMetaData,AcademicYearMetaData academicYearMetaData , ValueMetaData valueMetaData) {
		Map<Class, EntityMetaData> relatedEntitiesMap = new HashMap();
		relatedEntitiesMap.put(MyClass.class, myClassMetaData);
		relatedEntitiesMap.put(Subject.class, subjectMetaData);
		relatedEntitiesMap.put(AcademicYear.class, academicYearMetaData);
		relatedEntitiesMap.put(Value.class, valueMetaData);
		return relatedEntitiesMap;
	}

	private static List<String> getFilterList() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(COL_ACADEMIC_YEAR_KEY);
		filterList.add(COL_IS_DELETED);
		return filterList;
	}

	@Override
	protected void initialize() {
		addColumnMetaData(COL_ANNOUNCEMENT_KEY, ColumnMetaData.ColumnType.Key);
		addColumnMetaData(COL_TYPE, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Value.class), RelationshipType.ONE_TO_ONE_OWNED));
		addColumnMetaData(COL_TITLE, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_DETAIL, ColumnMetaData.ColumnType.STRING);
		addColumnMetaData(COL_TITLE_UPPER_CASE, ColumnMetaData.ColumnType.STRING);

		addColumnMetaData(COL_ANNOUNCEMENT_ENTRY_ID, ColumnMetaData.ColumnType.STRING);
		
		addColumnMetaData(COL_CLASS_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(MyClass.class), RelationshipType.ONE_TO_ONE_OWNED));
		
		addColumnMetaData(COL_SUBJECT_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(Subject.class), RelationshipType.ONE_TO_ONE_OWNED));
		
		addColumnMetaData(COL_ACADEMIC_YEAR_KEY, ColumnMetaData.ColumnType.Key, new ColumnRelationShipMetaData(
				relatedEntityTypesMap.get(AcademicYear.class), RelationshipType.ONE_TO_ONE_OWNED), new FilterMetaData(
				GlobalFilterType.GLOBAL_ACADEMIC_YEAR, GlobalFilterValueType.EXACT_MATCH));
		
		addColumnMetaData(COL_IS_DELETED, ColumnMetaData.ColumnType.BOOL, new FilterMetaData(
				GlobalFilterType.DELETE, GlobalFilterValueType.EXACT_MATCH));
		
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
