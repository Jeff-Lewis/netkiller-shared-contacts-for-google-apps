package com.metacube.ipathshala.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String getUserNameOutOfEmail(String email) {
		return StringUtils.split(email, "@")[0];
	}
}
