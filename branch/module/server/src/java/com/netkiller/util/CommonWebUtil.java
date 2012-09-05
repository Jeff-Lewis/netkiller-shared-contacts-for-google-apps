package com.netkiller.util;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

public class CommonWebUtil {

	public static String getDomain(String emailAddr) {
		return emailAddr.substring(emailAddr.indexOf("@") + 1);
	}

	public static String getUserId(String emailAddr) {
		return emailAddr.substring(0, emailAddr.indexOf("@"));
	}

	public static String getParameter(HttpServletRequest request,
			String paramName) {
		String value = request.getParameter(paramName);
		value = CommonUtil.getNotNullString(value);
		return value;
	}

	public static String getParameter(HttpServletRequest request,
			String paramName, String alternative) {
		String value = request.getParameter(paramName);
		value = CommonUtil.getNotNullString(value);
		if (value.equals("")) {
			value = alternative;
		}
		return value;
	}

	public static int getParameterInInteger(HttpServletRequest request,
			String paramName) {
		String value = request.getParameter(paramName);
		return Integer.parseInt(value);
	}

	public static double getParameterInDouble(HttpServletRequest request,
			String paramName) {
		String value = request.getParameter(paramName);
		return Double.parseDouble(value);
	}

	public static String getAttribute(HttpServletRequest request,
			String paramName) {
		String value = (String) request.getAttribute(paramName);
		value = CommonUtil.getNotNullString(value);
		return value;
	}

	public static JSONArray convetArraryListToJSONArray(List<String> arrayList) {
		JSONArray jsonArray = new JSONArray();
		for (String currentValue : arrayList) {
			((List<String>) jsonArray).add(currentValue);
		}
		return jsonArray;
	}

}
