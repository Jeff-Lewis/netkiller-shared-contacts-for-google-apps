package com.metacube.ipathshala.service.conversion;

public enum ConversionServiceType {
	GOOGLE,ITEXT,DOCUMENT;
	
	@Override
	public String toString()	{
		String returnString = null;
		switch (this) {
		case GOOGLE:
			returnString =  "google";
			break;
		case ITEXT:
			returnString =  "itext";
			break;
		case DOCUMENT:
			returnString =  "document";
			break;
		default:
			returnString = super.toString();
		}
		return returnString;
		
	}
	
	public static ConversionServiceType get(String conversionServiceTypeString)	{
		for(ConversionServiceType conversionServiceType:ConversionServiceType.values())	{
			if(conversionServiceType.toString().equalsIgnoreCase(conversionServiceTypeString))	{
				return conversionServiceType;
			}
		}
		return ConversionServiceType.ITEXT;
	}
}
