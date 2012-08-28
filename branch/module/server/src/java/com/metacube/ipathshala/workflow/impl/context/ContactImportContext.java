package com.metacube.ipathshala.workflow.impl.context;

import java.io.Serializable;

import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;

public class ContactImportContext implements WorkflowContext,
Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	WorkflowInfo workflowInfo;
	
	String blobKeyStr;
	
	String email;
	
	@Override
	public WorkflowInfo getWorkflowInfo() {
		// TODO Auto-generated method stub
		return workflowInfo;
	}


	public String getBlobKeyStr() {
		return blobKeyStr;
	}


	public void setBlobKeyStr(String blobKeyStr) {
		this.blobKeyStr = blobKeyStr;
	}


	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
		
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}





}
