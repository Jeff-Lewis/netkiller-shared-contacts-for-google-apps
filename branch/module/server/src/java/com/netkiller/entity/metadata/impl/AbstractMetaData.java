package com.netkiller.entity.metadata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.netkiller.core.AppException;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;

public abstract class AbstractMetaData implements EntityMetaData {

	private String entityName;
	private Class<?> entityClass;
	protected Map<String, ColumnMetaData> columnMap;
	protected Map<String, ColumnMetaData> uniqueColumnMap;
	protected Map<String, ColumnRelationShipMetaData> relatedEntitiesColumnMetaDataMap;
	protected Map<Class, EntityMetaData> relatedEntityTypesMap;
	protected List<String> filterColumnNameList;
	protected Map<String, FilterMetaData> filterEntitiesColumnMetaDataMap;
	private String[] labelColumns;
	
	private String[] businessKeyColumns;

	private String defaultSearchKeyColumnName;
	protected Map<String,SystemMetaData> systemPropertiesColumnMetaDataMap;

	public Map<String, SystemMetaData> getSystemPropertiesColumnMetaDataMap() {
		return systemPropertiesColumnMetaDataMap;
	}


	protected abstract void initialize();


	protected AbstractMetaData(String entityName,String[] businessKeyColumns,String defaultSearchKeyColumnName, String[] labelColumns, Class<?> entityClass, Map relatedEntitiesTypeMap, List<String> filterColumnNameList) {

		this.entityName = entityName;
		this.businessKeyColumns = businessKeyColumns;
		this.defaultSearchKeyColumnName = defaultSearchKeyColumnName;
		this.labelColumns = labelColumns;
		this.columnMap = new HashMap<String, ColumnMetaData>();
		this.entityClass = entityClass;
		this.relatedEntitiesColumnMetaDataMap = new HashMap<String, ColumnRelationShipMetaData>();
		this.filterEntitiesColumnMetaDataMap = new HashMap<String, FilterMetaData>();
		this.relatedEntityTypesMap = relatedEntitiesTypeMap; 
		this.filterColumnNameList = filterColumnNameList;
		this.uniqueColumnMap = new HashMap<String, ColumnMetaData>();
		this.systemPropertiesColumnMetaDataMap = new HashMap<String,SystemMetaData>();
		
		initialize();
	}
	
	

	/**
	 * adds column meta data definition in the column mappings
	 * 
	 * @param columnName
	 * @param columnType
	 */
	protected void addColumnMetaData(String columnName, ColumnType columnType) {
		ColumnMetaData cmd = new ColumnMetaData(columnName, columnType);
		columnMap.put(columnName, cmd);
	}
	
	/**
	 * adds column meta data definition in the column mappings
	 * 
	 * @param columnName
	 * @param columnType
	 */
	protected void addColumnMetaData(String columnName, ColumnType columnType, Boolean unique) {
		ColumnMetaData cmd = new ColumnMetaData(columnName, columnType);
		columnMap.put(columnName, cmd);
		if(unique)	{
			uniqueColumnMap.put(columnName, cmd);
		}
	}

	/**
	 * adds column meta data definition in the column mappings
	 * 
	 * @param columnName
	 * @param columnType
	 */
	protected void addColumnMetaData(String columnName, ColumnType columnType, ColumnRelationShipMetaData relationMetaData) {
		ColumnMetaData cmd = new ColumnMetaData(columnName, columnType,relationMetaData);
		columnMap.put(columnName, cmd);
		relatedEntitiesColumnMetaDataMap.put(columnName, relationMetaData);

	}
	
	/**
	 * adds column meta data definition in the column mappings
	 * 
	 * @param columnName
	 * @param columnType
	 */
	protected void addColumnMetaData(String columnName, ColumnType columnType, ColumnRelationShipMetaData relationMetaData, FilterMetaData filterMetaData) {
		ColumnMetaData cmd = new ColumnMetaData(columnName, columnType,relationMetaData, filterMetaData);
		columnMap.put(columnName, cmd);
		relatedEntitiesColumnMetaDataMap.put(columnName, relationMetaData);
		filterEntitiesColumnMetaDataMap.put(columnName, filterMetaData);

	}
	
	/**
	 * adds column meta data definition in the column mappings
	 * 
	 * @param columnName
	 * @param columnType
	 */
	protected void addColumnMetaData(String columnName, ColumnType columnType, ColumnRelationShipMetaData relationMetaData, FilterMetaData filterMetaData, Boolean unique) {
		ColumnMetaData cmd = new ColumnMetaData(columnName, columnType,relationMetaData, filterMetaData, unique);
		columnMap.put(columnName, cmd);
		relatedEntitiesColumnMetaDataMap.put(columnName, relationMetaData);
		filterEntitiesColumnMetaDataMap.put(columnName, filterMetaData);
		if(unique)	{
			uniqueColumnMap.put(columnName, cmd);
		}

	}
	/**
	 * adds column meta data definition in the column mappings
	 * 
	 * @param columnName
	 * @param columnType
	 */
	protected void addColumnMetaData(String columnName, ColumnType columnType, FilterMetaData filterMetaData) {
		ColumnMetaData cmd = new ColumnMetaData(columnName, columnType, filterMetaData);
		columnMap.put(columnName, cmd);
		filterEntitiesColumnMetaDataMap.put(columnName, filterMetaData);

	}
	
	/**
	 * adds column meta data definition in the column mappings
	 */
	protected void addColumnMetaData(String columnName, ColumnType columnType, SystemMetaData systemMetaData) {
		ColumnMetaData cmd = new ColumnMetaData(columnName, columnType, systemMetaData);
		columnMap.put(columnName, cmd);
		systemPropertiesColumnMetaDataMap.put(columnName, systemMetaData);

	}
	
	

	@Override
	public String getEntityName() {
		return entityName;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return entityClass;
	}
	


	@Override
	public Set<Entry<String, ColumnMetaData>> getAllColumns() {
		return columnMap.entrySet();
	}

	@Override
	public ColumnMetaData getColumnMetaData(String columnName) throws AppException {
		if (columnMap.containsKey(columnName)) {
			return columnMap.get(columnName);
		}
		throw new AppException("Column " + columnName + " not found in metadata for entity " + this.entityName);
	}

	@Override
	public ColumnMetaData[] getBusinessKey() {
		ColumnMetaData[] columMetaDataColumns = null;
		if (businessKeyColumns != null) {
			columMetaDataColumns = new ColumnMetaData[businessKeyColumns.length];
			for (int i = 0; i < businessKeyColumns.length; i++) {
				columMetaDataColumns[i] = columnMap.get(businessKeyColumns[i]);
			}
		}
		return columMetaDataColumns;
	}
	
	@Override
	public ColumnMetaData[] getLabelColumns()	{
		ColumnMetaData[] columMetaDataColumns = null;
		if (labelColumns != null) {
			columMetaDataColumns = new ColumnMetaData[labelColumns.length];
			for (int i = 0; i < labelColumns.length; i++) {
				columMetaDataColumns[i] = columnMap.get(labelColumns[i]);
			}
		}
		return columMetaDataColumns;
	}

	@Override
	public ColumnMetaData getDefaultSearchColumn() {
		return columnMap.get(defaultSearchKeyColumnName);
	}

	@Override
	public Set<ColumnRelationShipMetaData> getAllRelationsMetaData() {
		return (Set<ColumnRelationShipMetaData>)relatedEntitiesColumnMetaDataMap.values();
	}

	/**
	 * @return the filterEntitiesColumnMetaDataMap
	 */
	@Override
	public Map<String, FilterMetaData> getFilterEntitiesColumnMetaDataMap() {
		return filterEntitiesColumnMetaDataMap;
	}


	@Override
	public Set<FilterMetaData> getAllFiltersMetaData() {
		return (Set<FilterMetaData>)filterEntitiesColumnMetaDataMap.values();
	}
	
	@Override
	public Map<String, ColumnMetaData> getUniqueColumnMetaDataMap() {
		return uniqueColumnMap;
	}
}