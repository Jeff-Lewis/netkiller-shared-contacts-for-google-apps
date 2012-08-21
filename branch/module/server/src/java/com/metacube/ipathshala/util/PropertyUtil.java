package com.metacube.ipathshala.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.metacube.ipathshala.ServerCommonConstant;
import com.metacube.ipathshala.core.AppException;

public class PropertyUtil {

	public static String getProperty(String key) throws AppException {
		InputStream ipathshalaPropertiesLoadStream = PropertyUtil.class.getClassLoader().getResourceAsStream(
				ServerCommonConstant.IPATHSHALA_PROPERTY_FILE);
		if (ipathshalaPropertiesLoadStream == null) {
			throw new AppException("Cannot read from Ipathshala properties file");
		}
		Properties ipathshalaProperties = new Properties();
		try {
			ipathshalaProperties.load(ipathshalaPropertiesLoadStream);
		} catch (IOException e) {
			throw new AppException("Cannot read from Ipathshala properties file");
		}
		String propertyValue = ipathshalaProperties.getProperty(key);
		return propertyValue;
	}
	
	public static String getGadgetProperty(String key) throws AppException {
		InputStream gadgetPropertiesLoadStream = PropertyUtil.class.getClassLoader().getResourceAsStream(
				ServerCommonConstant.GADGET_PROPERTY_FILE);
		if (gadgetPropertiesLoadStream == null) {
			throw new AppException("Cannot read from Ipathshala properties file");
		}
		Properties gadgetProperties = new Properties();
		try {
			gadgetProperties.load(gadgetPropertiesLoadStream);
		} catch (IOException e) {
			throw new AppException("Cannot read from Ipathshala properties file");
		}
		String propertyValue = gadgetProperties.getProperty(key);
		return propertyValue;
	}
	
	public static String getGoogleSitesProperty(String key) throws AppException {
		InputStream googleSitesPropertiesLoadStream = PropertyUtil.class.getClassLoader().getResourceAsStream(
				ServerCommonConstant.GOOGLE_SITE_PROPERTY_FILE);
		if (googleSitesPropertiesLoadStream == null) {
			throw new AppException("Cannot read from Google Site properties file");
		}
		Properties googleSitesProperties = new Properties();
		try {
			googleSitesProperties.load(googleSitesPropertiesLoadStream);
		} catch (IOException e) {
			throw new AppException("Cannot read from Google Site properties file");
		}
		String propertyValue = googleSitesProperties.getProperty(key);
		return propertyValue;
	}
	
	/*public static String getMessageProperty(String key) throws AppException{
		InputStream messagePropertiesLoadStream = PropertyUtil.class.getClassLoader().getResourceAsStream(
				ServerCommonConstant.MESSAGE_PROPERTY_FILE);
		if (messagePropertiesLoadStream == null) {
			throw new AppException("Cannot read from Message properties file");
		}
		Properties messageProperty = new Properties();
		try {
			messageProperty.load(messagePropertiesLoadStream);
		} catch (IOException e) {
			throw new AppException("Cannot read from Message properties file");
		}
		String propertyValue = messageProperty.getProperty(key);
		return propertyValue;
	}*/
	
	

}
