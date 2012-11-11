package com.netkiller.workflow.impl.context;

import java.io.Serializable;

import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class DomainUpdateContext implements WorkflowContext, Serializable{
	
	private static final long serialVersionUID = 7007353076319985798L;

	private WorkflowInfo workflowInfo;
	
	@Override
	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;

	}

}
