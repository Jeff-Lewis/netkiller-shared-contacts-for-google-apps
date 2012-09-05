package com.netkiller.util;

public class NumberUtil {
	
	public static String formatLongToTwoDigits(Long number)	{
		  
		java.text.DecimalFormat numberFormatter = new  java.text.DecimalFormat("#00.###");  
		numberFormatter.setDecimalSeparatorAlwaysShown(false); 	
		return numberFormatter.format(number);
	}
	
	public static String formatIntToTwoDigits(int number)	{
		  
		java.text.DecimalFormat numberFormatter = new  java.text.DecimalFormat("#00.###");  
		numberFormatter.setDecimalSeparatorAlwaysShown(false); 	
		return numberFormatter.format(number);
	}

}
