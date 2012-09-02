package com.metacube.ipathshala.workflow.impl.context;

import java.io.Serializable;

import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;

public class ConnectContactContext implements WorkflowContext,
Serializable {
	
	WorkflowInfo workflowInfo;
	
	String contactKeysCSV;
	
	String ownerEmail;
	
	String toName;
	
	String toEmail;
	
	@Override
	public WorkflowInfo getWorkflowInfo() {
		// TODO Auto-generated method stub
		return workflowInfo;
	}
	
	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
		
	}

	public String getContactKeysCSV() {
		return contactKeysCSV;
	}

	public void setContactKeysCSV(String contactKeysCSV) {
		this.contactKeysCSV = contactKeysCSV;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	
	
}
