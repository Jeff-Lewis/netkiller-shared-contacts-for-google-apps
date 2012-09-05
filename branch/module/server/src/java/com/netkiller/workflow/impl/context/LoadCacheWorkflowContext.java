package com.netkiller.workflow.impl.context;

import java.io.Serializable;

import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class LoadCacheWorkflowContext implements WorkflowContext, Serializable {

	private WorkflowInfo workflowInfo;








	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}



}
