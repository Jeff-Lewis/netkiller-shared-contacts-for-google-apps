package com.metacube.ipathshala.security;

import java.util.Map;

/**
 * @author dhruvsharma
 *
 */
public class ResourceSecurityConfig {

	private Map<String, String> resourceEntityMap;

	public void setResourceEntityMap(Map<String, String> resourceEntityMap) {
		this.resourceEntityMap = resourceEntityMap;
	}
	
	public String getMappedEntityNameForResource(String resourceName) {
		return resourceEntityMap.get(resourceName);
	}
	
	public boolean doesResourceExist(String resourceName) {
		return resourceEntityMap.containsKey(resourceName);
	}

}
