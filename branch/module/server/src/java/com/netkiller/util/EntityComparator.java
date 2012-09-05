package com.netkiller.util;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;
import com.netkiller.search.property.OrderByProperty;
import com.netkiller.search.property.operator.OrderByOperatorType;

public class EntityComparator implements Comparator<Object> {

	private static final AppLogger log = AppLogger.getLogger(EntityComparator.class);

	private Map<OrderByProperty, ColumnType> orderByPropertyTypeMap;
	private Class<?> entityType;

	/**
	 * @return the enityType
	 */
	public Class<?> getEntityType() {
		return entityType;
	}

	/**
	 * @param enityType
	 *            the enityType to set
	 */
	public void setEntityType(Class<?> entityType) {
		this.entityType = entityType;
	}

	public EntityComparator(Map<OrderByProperty, ColumnType> orderByPropertyTypeMap, Class<?> entityType) {
		this.entityType = entityType;
		this.orderByPropertyTypeMap = orderByPropertyTypeMap;
	}

	/**
	 * @return the orderByProperty
	 */
	public Map<OrderByProperty, ColumnType> getOrderByPropertyList() {
		return orderByPropertyTypeMap;
	}

	/**
	 * @param orderByProperty
	 *            the orderByProperty to set
	 */
	public void setOrderByProperty(Map<OrderByProperty, ColumnType> orderByPropertyList) {
		this.orderByPropertyTypeMap = orderByPropertyList;
	}

	/**
	 * @return the log
	 */
	public static AppLogger getLog() {
		return log;
	}

	@Override
	public int compare(Object object1, Object object2) {
		int result = 0;
		if (orderByPropertyTypeMap != null) {
			for (OrderByProperty orderByProperty : orderByPropertyTypeMap.keySet()) {
				result = compareObjectByProperty(object1, object2, orderByProperty,
						orderByPropertyTypeMap.get(orderByProperty));
				if (result != 0) {
					break;
				}
			}
		}
		return result;
	}

	private int compareObjectByProperty(Object object1, Object object2, OrderByProperty orderByProperty,
			ColumnType columnType) {
		int result = 0;
		try {

			Field propertyField = this.entityType.getDeclaredField(orderByProperty.getOrderByPropertyName());
			propertyField.setAccessible(true);
			Object propertyValue1 = (Object) propertyField.get(object1);
			Object propertyValue2 = (Object) propertyField.get(object2);
			if (propertyValue1 == null) {
				return -1;         
			} else if (propertyValue2 == null) {
				return 1;
			} else if (propertyValue2 == propertyValue1) {
				return 0;
			} else if (columnType.equals(ColumnType.STRING)) {

				result = ((String) (propertyValue1)).toLowerCase().compareTo(((String) propertyValue2).toLowerCase());

			} else if (columnType == ColumnType.DATE || columnType == ColumnType.DATETIME) {
				result = ((Date) (propertyValue1)).compareTo((Date) propertyValue2);
			} else if (columnType == ColumnType.DOUBLE) {
				result = ((Double) (propertyValue1)).compareTo((Double) propertyValue2);
			} else if (columnType == ColumnType.INT) {
				result = ((Integer) (propertyValue1)).compareTo((Integer) propertyValue2);
			} else if (columnType == ColumnType.BOOL) {
				result = ((Boolean) (propertyValue1)).compareTo((Boolean) propertyValue2);
			}

			else if (columnType.equals(ColumnType.Key)) {
				if (((Key) (propertyValue1)).getId() < ((Key) propertyValue2).getId()) {
					result = -1;
				} else if (((Key) (propertyValue1)).getId() == ((Key) propertyValue2).getId()) {
					result = 0;
				} else if (((Key) (propertyValue1)).getId() > ((Key) propertyValue2).getId()) {
					result = 1;
				}
			} else {
				log.error("Invalid Type from " + orderByProperty.getOrderByPropertyName() + " ");
			}
			if (orderByProperty.getOrderBy().equals(OrderByOperatorType.DESC)) {
				result = result * -1;
			}
		} catch (SecurityException e) {
			String message = "Unable to fetch property with name " + orderByProperty.getOrderByPropertyName();
			log.error(message);
		} catch (NoSuchFieldException e) {
			String message = "Unable to fetch property with name " + orderByProperty.getOrderByPropertyName();
			log.error(message);
		} catch (IllegalArgumentException e) {
			String message = "Unable to fetch property with name " + orderByProperty.getOrderByPropertyName();
			log.error(message);
		} catch (IllegalAccessException e) {
			String message = "Unable to fetch property with name " + orderByProperty.getOrderByPropertyName();
			log.error(message);
		}
		return result;
	}

	/**
	 * @return the orderByPropertyTypeMap
	 */
	public Map<OrderByProperty, ColumnType> getOrderByPropertyTypeMap() {
		return orderByPropertyTypeMap;
	}

	/**
	 * @param orderByPropertyTypeMap
	 *            the orderByPropertyTypeMap to set
	 */
	public void setOrderByPropertyTypeMap(Map<OrderByProperty, ColumnType> orderByPropertyTypeMap) {
		this.orderByPropertyTypeMap = orderByPropertyTypeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntityComparator [orderByPropertyTypeMap=" + orderByPropertyTypeMap + ", entityType=" + entityType
				+ "]";
	}
}
