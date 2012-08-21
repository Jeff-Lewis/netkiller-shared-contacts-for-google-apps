package com.metacube.ipathshala.workflow.impl.context;

import java.io.Serializable;

import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;

public class LoadCacheWorkflowContext implements WorkflowContext, Serializable {

	private WorkflowInfo workflowInfo;








	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}



}
