package com.netkiller.cache;



import java.util.Comparator;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;
import com.netkiller.search.property.OrderByProperty;
import com.netkiller.search.property.operator.OrderByOperatorType;
import com.netkiller.util.AppLogger;

public class EntityCacheValueComparator implements Comparator<EntityCacheValue> {

	private static final AppLogger log = AppLogger.getLogger(EntityCacheValueComparator.class);

	private OrderByProperty orderByProperty;
	private Class<?> entityType;
	private ColumnType columnType;

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

	public EntityCacheValueComparator(OrderByProperty orderByProperty, Class<?> entityType,ColumnType columnType) {
		this.entityType = entityType;
		this.orderByProperty = orderByProperty;
		this.columnType = columnType;
	}

	/**
	 * @return the log
	 */
	public static AppLogger getLog() {
		return log;
	}

	@Override
	public int compare(EntityCacheValue object1, EntityCacheValue object2) {
		int result = 0;
		if (orderByProperty != null) {
		
				result = compareObjectByProperty(object1, object2, orderByProperty,
						columnType);
			
			}
		
		return result;
	}

	private int compareObjectByProperty(EntityCacheValue object1, EntityCacheValue object2, OrderByProperty orderByProperty,
			ColumnType columnType) {
		int result = 0;
		try {
			Object propertyValue1 = (Object) object1.getProperty(orderByProperty.getOrderByPropertyName());
			Object propertyValue2 = (Object) object2.getProperty(orderByProperty.getOrderByPropertyName());
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
		}  catch (IllegalArgumentException e) {
			String message = "Unable to fetch property with name " + orderByProperty.getOrderByPropertyName();
			log.error(message);
		}
		return result;
	}

	@Override
	public String toString() {
		return "EntityCacheValueComparator [orderByProperty=" + orderByProperty + ", entityType=" + entityType
				+ ", columnType=" + columnType + "]";
	}

	



}
