package com.netkiller.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import com.netkiller.core.AppException;

public class GlobalFilterUtil {
	private static final AppLogger log = AppLogger.getLogger(GlobalFilterUtil.class);
	
	public static Date getStaticFutureDateForGlobalFilter() throws AppException {
		
		Date toDate = null ;
		
		InputStream googleSitesPropertiesInputStream = GlobalFilterUtil.class.getClassLoader()
		.getResourceAsStream("ipathshala.properties");
		Properties appProperties = new Properties();
		try {
			appProperties.load(googleSitesPropertiesInputStream);
		}catch (IOException e) {
			String message = "No FileCabinet Title found to place Assignments. ";
			log.error(message + e.getMessage(), e);
			throw new AppException(message + e.getMessage(), e);
		}
		String toDateString = appProperties.getProperty("gloabalfilter.future.toDate");
		toDate = LocalizationUtil.parseDate(toDateString, null);
		return toDate;
	
}

}
