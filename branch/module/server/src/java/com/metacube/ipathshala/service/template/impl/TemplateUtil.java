package com.metacube.ipathshala.service.template.impl;

import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.MailUtil;

public class TemplateUtil {

	private static final AppLogger log = AppLogger.getLogger(MailUtil.class);
	
	public static String getQualifiedName(String templateName, String path) {
		return path + templateName;
	}
}
