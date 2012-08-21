package com.metacube.ipathshala.workflow.impl.context;

import java.io.Serializable;

import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;

public class TestWorkflowContext implements WorkflowContext,Serializable {
     
	WorkflowInfo workflowInfo;
	
	
	public TestWorkflowContext() {
		super();
	}

	public WorkflowInfo getWorkflowInfo () {
		return workflowInfo;
	}
	 
	 public void setWorkflowInfo (WorkflowInfo workflowInfo) {
		this.workflowInfo=workflowInfo; 
	 }
	 
	 @Override
	 public String toString() {
	    return "workflowName : "+workflowInfo.getWorkflowName()+" workflowInstance : "+workflowInfo.getWorkflowInstance()+" lastFailedTaskName : "+workflowInfo.getLastFailedTaskName();
	 }
	 
}
