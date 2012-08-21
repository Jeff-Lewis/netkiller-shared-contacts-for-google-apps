package com.metacube.ipathshala.entity.metadata;

import com.metacube.ipathshala.entity.metadata.impl.SystemMetaData;

/**
 * 
 * @author amit.c
 * 
 */
public class ColumnMetaData {

	public enum ColumnType {
		INT, DOUBLE, STRING, BOOL, DATE, Key, BLOB, DATETIME
	};

	private String columnName;
	private ColumnType columnType;
	private ColumnRelationShipMetaData relationShip;
	private FilterMetaData filter;
	private Boolean unique = false;
	private SystemMetaData systemMetaData;
	
	/**
	 * @param columnName
	 * @param columnType
	 */
	public ColumnMetaData(String columnName, ColumnType columnType) {
		this.columnName = columnName;
		this.columnType = columnType;
	}
	
	/**
	 * 
	 * @return
	 */
	public ColumnMetaData(String columnName, ColumnType columnType,SystemMetaData systemMetaData){
		this.columnName = columnName;
	    this.columnType = columnType;
	    this.systemMetaData = systemMetaData;
	}
	
	public ColumnRelationShipMetaData getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(ColumnRelationShipMetaData relationShip) {
		this.relationShip = relationShip;
	}

	/**
	 * @param columnName
	 * @param columnType
	 */
	public ColumnMetaData(String columnName, ColumnType columnType,ColumnRelationShipMetaData relationShip) {
		this.columnName = columnName;
		this.columnType = columnType;
		this.relationShip=relationShip;
	}
	
	/**
	 * @param columnName
	 * @param columnType
	 */
	public ColumnMetaData(String columnName, ColumnType columnType,ColumnRelationShipMetaData relationShip, FilterMetaData filter, Boolean unique) {
		this.columnName = columnName;
		this.columnType = columnType;
		this.relationShip=relationShip;
		this.filter = filter;
		this.unique = unique;
	}
	
	/**
	 * @param columnName
	 * @param columnType
	 */
	public ColumnMetaData(String columnName, ColumnType columnType,ColumnRelationShipMetaData relationShip, FilterMetaData filter) {
		this.columnName = columnName;
		this.columnType = columnType;
		this.relationShip=relationShip;
		this.filter = filter;
	}
	
	/**
	 * @param columnName
	 * @param columnType
	 */
	public ColumnMetaData(String columnName, ColumnType columnType, FilterMetaData filter) {
		this.columnName = columnName;
		this.columnType = columnType;
		this.filter = filter;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the columnType
	 */
	public ColumnType getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType
	 *            the columnType to set
	 */
	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
	
	
}
