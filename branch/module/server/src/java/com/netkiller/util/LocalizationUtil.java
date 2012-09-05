package com.netkiller.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.netkiller.core.AppException;

/**
 * A util for all localization related activities.
 * 
 * @author prateek
 * 
 */
public class LocalizationUtil {

	private static String defaultDatePattern = "dd/MM/yyyy";
	private static String defaultDateTimePattern = "dd/MM/yyyy HH:mm:ss";
	private static TimeZone defaultTimeZone = TimeZone.getTimeZone("GMT+05:30");

	private static DateFormat defaultDateFormatter;
	private static DateFormat defaultDateTimeFormatter;

	static {
		defaultDateFormatter = new SimpleDateFormat(defaultDatePattern);
		defaultDateFormatter.setTimeZone(defaultTimeZone);
		defaultDateTimeFormatter = new SimpleDateFormat(defaultDateTimePattern);
		defaultDateTimeFormatter.setTimeZone(defaultTimeZone);
	}

	public static DateFormat getDefaultDateFormatter() {
		return defaultDateFormatter;
	}

	public static DateFormat getDefaultDateTimeFormatter() {
		return defaultDateTimeFormatter;
	}

	public static Date parseDate(String date, String pattern) {
		Date parsedDate = null;
		DateFormat dateFormat = null;
		try {
			if (pattern != null) {
				dateFormat = new SimpleDateFormat(pattern);
				parsedDate = dateFormat.parse(date);

			} else {
				parsedDate = defaultDateFormatter.parse(date);
			}
		} catch (ParseException e) {
			new AppException("Invalid input date:" + date);
		}
		return parsedDate;
	}

	public static String formatDate(Date date, String pattern) {
		String parsedDate = null;
		DateFormat dateFormat = null;
		if (pattern != null) {
			dateFormat = new SimpleDateFormat(pattern);
			parsedDate = dateFormat.format(date);

		} else {
			parsedDate = defaultDateFormatter.format(date);

		}
		return parsedDate;
	}
	
	public static Date parseDateTime(String date, String pattern) {
		Date parsedDate = null;
		DateFormat dateFormat = null;
		try {
			if (pattern != null) {
				dateFormat = new SimpleDateFormat(pattern);

				parsedDate = dateFormat.parse(date);

			} else {
				parsedDate = defaultDateTimeFormatter.parse(date);
			}
		} catch (ParseException e) {
			new AppException("Invalid input date:" + date);
		}
		return parsedDate;
	}
	
	public static String formatDateTime(Date date, String pattern) {
		String parsedDate = null;
		DateFormat dateFormat = null;
		if (pattern != null) {
			dateFormat = new SimpleDateFormat(pattern);
			parsedDate = dateFormat.format(date);

		} else {
			parsedDate = defaultDateTimeFormatter.format(date);

		}
		return parsedDate;
	}
	
	public static Date parseDate(String date, String pattern, TimeZone timeZone) {
		Date parsedDate = null;
		DateFormat dateFormat = null;
		try {
			if (pattern != null) {
				dateFormat = new SimpleDateFormat(pattern);
				dateFormat.setTimeZone(timeZone);
				parsedDate = dateFormat.parse(date);

			} else {
				parsedDate = defaultDateFormatter.parse(date);
			}
		} catch (ParseException e) {
			new AppException("Invalid input date:" + date);
		}
		return parsedDate;
	}

	public static String formatDate(Date date, String pattern, TimeZone timeZone) {
		String parsedDate = null;
		DateFormat dateFormat = null;
		if (pattern != null) {
			dateFormat = new SimpleDateFormat(pattern);
			dateFormat.setTimeZone(timeZone);
			parsedDate = dateFormat.format(date);

		} else {
			parsedDate = defaultDateFormatter.format(date);

		}
		return parsedDate;
	}
	
	public static Date parseDateTime(String date, String pattern, TimeZone timeZone) {
		Date parsedDate = null;
		DateFormat dateFormat = null;
		try {
			if (pattern != null) {
				dateFormat = new SimpleDateFormat(pattern);
				dateFormat.setTimeZone(timeZone);
				parsedDate = dateFormat.parse(date);

			} else {
				parsedDate = defaultDateTimeFormatter.parse(date);
			}
		} catch (ParseException e) {
			new AppException("Invalid input date:" + date);
		}
		return parsedDate;
	}
	
	public static String formatDateTime(Date date, String pattern, TimeZone timeZone) {
		String parsedDate = null;
		DateFormat dateFormat = null;
		if (pattern != null) {
			dateFormat = new SimpleDateFormat(pattern);
			dateFormat.setTimeZone(timeZone);
			parsedDate = dateFormat.format(date);

		} else {
			parsedDate = defaultDateTimeFormatter.format(date);

		}
		return parsedDate;
	}
}
