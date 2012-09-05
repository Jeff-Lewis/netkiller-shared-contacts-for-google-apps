package com.netkiller.workflow.impl.context;

import java.io.Serializable;

import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class DataUploadWorkflowContext implements WorkflowContext,Serializable {
	
	WorkflowInfo workflowInfo;
	
	private byte[] xlsObject;
	
	/**
	 * @return the stream
	 */
	public byte[] getXLSObject() {
		return xlsObject;
	}

	/**
	 * @param stream
	 *            the stream to set
	 */
	public void setXLSObject(byte[] stream) {
		this.xlsObject = stream;
	}

	@Override
	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

}
