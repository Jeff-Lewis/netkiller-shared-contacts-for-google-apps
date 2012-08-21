package com.metacube.ipathshala.service.template;

import java.util.Map;

import com.metacube.ipathshala.core.AppException;

public interface TemplateService {
	
	public String generateHTML(String templateName, String path, Map<String, Object> data);
	
	public String generateHTML(byte[] byteArr, Map<String, Object> modalMap) throws AppException;
	
}
