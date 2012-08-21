package com.metacube.ipathshala.core;

import java.util.HashMap;
import java.util.Map;

public class ImportEntityWorkflowConfig {

	Map<EntityType, String> entityTypeWorkflowNameMap = new HashMap<EntityType, String>();

	public String getWntityWorkflow(EntityType entityType) {
		return entityTypeWorkflowNameMap.get(entityType);
	}

	public Map<EntityType, String> getEntityTypeWorkflowNameMap() {
		return entityTypeWorkflowNameMap;
	}

	public void setEntityTypeWorkflowNameMap(
			Map<EntityType, String> entityTypeWorkflowNameMap) {
		this.entityTypeWorkflowNameMap = entityTypeWorkflowNameMap;
	}

}
