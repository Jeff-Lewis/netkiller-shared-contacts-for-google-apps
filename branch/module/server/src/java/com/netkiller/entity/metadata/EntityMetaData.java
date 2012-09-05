/**
 * 
 */
package com.netkiller.entity.metadata;

import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

import com.netkiller.core.AppException;
import com.netkiller.entity.metadata.impl.SystemMetaData;

/**
 * 
 * @author amit
 * 
 */
public interface EntityMetaData {

	/**
	 * @return the simple name of the entity
	 */
	String getEntityName();
	
	/**
	 * @return the class of the entity
	 */
	Class<?> getEntityClass();

	/**
	 * @return a set of all column values for this entity
	 */
	Set<Entry<String, ColumnMetaData>> getAllColumns();

	ColumnMetaData getColumnMetaData(String columnName) throws AppException;

	/**
	 * Business key column.
	 * 
	 * @return
	 */
	ColumnMetaData[] getBusinessKey();

	/**
	 * s Column on which default searches can happen.
	 * 
	 * @return
	 */
	ColumnMetaData getDefaultSearchColumn();

	/**
	 * Gives relationship metadata for all columns that are related to this
	 * entity.Returns null if there aren't any;
	 * 
	 * @return
	 */
	Set<ColumnRelationShipMetaData> getAllRelationsMetaData();
	
	/**
	 * Gives Filter metadata for all columns that are related to this
	 * entity.Returns null if there aren't any;
	 * 
	 * @return
	 */
	Set<FilterMetaData> getAllFiltersMetaData();
	
	/**
	 * Gives Filter metadata Map for all columns that are related to this
	 * entity.Returns null if there aren't any;
	 * 
	 * @return
	 */
	Map<String, FilterMetaData> getFilterEntitiesColumnMetaDataMap();

	Map<String, ColumnMetaData> getUniqueColumnMetaDataMap();
	
	Map<String, SystemMetaData> getSystemPropertiesColumnMetaDataMap();
	
	ColumnMetaData[] getLabelColumns();

}
