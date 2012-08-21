package com.metacube.ipathshala.entity.metadata.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;

/**
 * Helper functions on metadata.
 * 
 * @author dhruvsharma
 * 
 */
public class MetaDataUtil {

	public static String getEntityDefaultSearchColumnValue(Object entity, EntityMetaData metaData)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (entity != null) {
			return BeanUtils.getProperty(entity, metaData.getDefaultSearchColumn().getColumnName());
		}
		return null;
	}

	public static String getEntityBusinessKeyValue(Object entity, EntityMetaData metaData)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (entity != null) {
			ColumnMetaData[] businessKeyMetadata = metaData.getBusinessKey();
			StringBuilder businessKeyValue = new StringBuilder();
			for (int businessKeyMetadataIndex = 0; businessKeyMetadataIndex < businessKeyMetadata.length; businessKeyMetadataIndex++) {
				String columnName = businessKeyMetadata[businessKeyMetadataIndex].getColumnName();
				String businessKeyColumnValue = BeanUtils.getProperty(entity, columnName);
				businessKeyValue.append(businessKeyColumnValue);
				if (businessKeyMetadataIndex != (businessKeyMetadata.length - 1)) {
					businessKeyValue.append(" ");
				}
			}
			return businessKeyValue.toString();
		}
		return null;
	}

	public static String getEntityLabelValue(Object entity, EntityMetaData metaData) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if (entity != null) {
			ColumnMetaData[] labelColumnMetaData = metaData.getLabelColumns();
			StringBuilder labelValue = new StringBuilder();
			for (int labelColumnMetaDataIndex = 0; labelColumnMetaDataIndex < labelColumnMetaData.length; labelColumnMetaDataIndex++) {
				String columnName = labelColumnMetaData[labelColumnMetaDataIndex].getColumnName();
				String labelColumnValue = BeanUtils.getProperty(entity, columnName);
				if (!StringUtils.isBlank(labelColumnValue)) {
					labelValue.append(labelColumnValue);
				} else {
					labelValue.append("");
				}
				if (labelColumnMetaDataIndex != (labelColumnMetaData.length - 1)) {
					labelValue.append(" ");
				}
			}
			return labelValue.toString();
		}
		return null;
	}
}
