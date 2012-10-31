package com.netkiller.workflow.impl.context;

import java.io.Serializable;

import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class ContactImportContext implements WorkflowContext, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	WorkflowInfo workflowInfo;

	String blobKeyStr;

	String email;

	int startLimit;

	int endLimit;

	public int getStartLimit() {
		return startLimit;
	}

	public void setStartLimit(int startLimit) {
		this.startLimit = startLimit;
	}

	public int getEndLimit() {
		return endLimit;
	}

	public void setEndLimit(int endLimit) {
		this.endLimit = endLimit;
	}

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

	@Override
	public String toString() {
		return "ContactImportContext [workflowInfo=" + workflowInfo
				+ ", blobKeyStr=" + blobKeyStr + ", email=" + email
				+ ", startLimit=" + startLimit + ", endLimit=" + endLimit + "]";
	}

}
