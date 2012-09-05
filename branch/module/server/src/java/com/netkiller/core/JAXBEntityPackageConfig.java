package com.netkiller.core;

import java.util.HashMap;
import java.util.Map;

public class JAXBEntityPackageConfig {

	Map<EntityType, String> entityTypePackageMap= new HashMap<EntityType,String>();

	public String getEntityPackage(EntityType entityType) {
		return entityTypePackageMap.get(entityType);
	}

	public Map<EntityType, String> getEntityTypePackageMap() {
		return entityTypePackageMap;
	}

	public void setEntityTypePackageMap(Map<EntityType, String> entityTypePackageMap) {
		this.entityTypePackageMap = entityTypePackageMap;
	}

}
