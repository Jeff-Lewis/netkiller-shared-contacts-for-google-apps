package com.netkiller.util;

import java.util.Date;
import java.util.TimeZone;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CommonUtil {
	
	public static String convertEncodingType(String orgStr, String orgStrType, String newStrType) throws UnsupportedEncodingException{
		return new String(orgStr.getBytes(orgStrType), newStrType);
	}
	
	public static String convertEncodingType(String orgStr) throws UnsupportedEncodingException{
		return convertEncodingType(orgStr, "8859_1", "UTF-8");
	}
	
	public static String getNotNullString(String param){
		if(param == null){ param = ""; }
		return param;
	}
	
	public static String getNotNullValue(Object value){
		if(value == null){ value = ""; }
		return value.toString();
	}
	
	public static String getString(String param, String replace){
		if(param == null || param.equals("")){ 
			param = replace; 
		}
		return param;
	}
	
	public static String getStringWithSpace(String param){
		return getString(param, " ");
	}
	
	
	public static String getCurrentDate() {
		
		//String DATE_FORMAT = "MMM dd, yyyy HH:mm:ss z";
		String DATE_FORMAT = "MMM dd, yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone( TimeZone.getTimeZone("GMT-4:00"));

		return sdf.format(new Date()) ;		
		
		
		//TimeZone theTz = TimeZone.getTimeZone("GMT-4:00");
		//DateFormat df = DateFormat.getDateTimeInstance( DateFormat.MEDIUM, DateFormat.MEDIUM );
		//df.setTimeZone( theTz );
		//return df.format( new Date()) ;

		//Date date = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat( format );
		//return sdf.format( date );
	}
	
	public static String toWon(Long param){
		//NumberFormat nf = new DecimalFormat("#,###");
		NumberFormat nf = new DecimalFormat("#,##0");
		return nf.format(param);
	}

	public static String getEmailLink(String email){
		if(email.trim().isEmpty()){
			return email;
		}
		return "<a style='text-decoration:none;' href='https://mail.google.com/mail/?view=cm&fs=1&tf=1&to="+email+"' target='_blank'>"+email+"</a>";
	}
	
	public static String getMapLink(String address){
		if(address.trim().isEmpty()){
			return address;
		}
		String linkAddress = address.replaceAll(" ", "+");
		return "<a style='text-decoration:none;' href='http://maps.google.com/maps?q="+linkAddress+"' target='_blank'>"+address+"</a>";
	}
	
//	Kentucky,+United+States
	
}
