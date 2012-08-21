package com.metacube.ipathshala.workflow.impl.context;

import java.io.Serializable;

import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;

public class ExImpWorkflowContext implements WorkflowContext, Serializable {

	WorkflowInfo workflowInfo;

	Object xmlObject;

	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	public Object getXmlObject() {
		return xmlObject;
	}

	public void setXmlObject(Object xmlObject) {
		this.xmlObject = xmlObject;
	}

	@Override
	public String toString() {
		return "ExImpWorkflowContext [workflowInfo=" + workflowInfo + ", xmlObject=" + xmlObject
				+ ", getWorkflowInfo()=" + getWorkflowInfo() + ", getXmlObject()=" + getXmlObject() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
