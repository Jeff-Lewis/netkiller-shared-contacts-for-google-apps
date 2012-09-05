package com.netkiller.service.conversion;


import com.netkiller.core.AppException;
import com.netkiller.util.PropertyUtil;

public class ConversionUtil {
	
	public static String conversionTypeString = "google";
	
	public static String getMimeType(ConversionType type)	{
		String mimeType = null;
		switch(type)	{
		
		case HTML:
			mimeType = "text/html";
			break;
		case PDF:
			mimeType = "application/pdf";
			break;
		}
		return mimeType;
	}

	public static ConversionServiceType getConversionType() throws AppException {
	//	String conversionServiceTypeString = PropertyUtil.getProperty("coversion.service");
		String conversionServiceTypeString = conversionTypeString;
		return ConversionServiceType.get(conversionServiceTypeString);
		
		
	}

}
