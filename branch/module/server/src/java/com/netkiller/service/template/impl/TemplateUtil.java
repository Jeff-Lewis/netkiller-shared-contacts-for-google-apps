package com.netkiller.service.template.impl;

import com.netkiller.util.AppLogger;
import com.netkiller.util.MailUtil;

public class TemplateUtil {

	private static final AppLogger log = AppLogger.getLogger(MailUtil.class);
	
	public static String getQualifiedName(String templateName, String path) {
		return path + templateName;
	}
}
