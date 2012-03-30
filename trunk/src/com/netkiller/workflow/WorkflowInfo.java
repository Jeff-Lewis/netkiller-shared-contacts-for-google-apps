package com.netkiller.workflow;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * An info class containing general information about a workflow.
 * 
 */
public class WorkflowInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132113973897652371L;
	private String workflowName;
	private String workflowInstance;
	private boolean isNewWorkflow;
	private String lastFailedTaskName;

	public WorkflowInfo(String workflowName) {
		super();
		this.workflowName = workflowName;
		Date date = new Date();
		Timestamp currentTimeStamp = new Timestamp(date.getTime());
		this.workflowInstance = workflowName + currentTimeStamp;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(String workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	public boolean getIsNewWorkflow() {
		return isNewWorkflow;
	}

	public void setIsNewWorkflow(boolean isNewWorkflow) {
		this.isNewWorkflow = isNewWorkflow;
	}

	public String getLastFailedTaskName() {
		return lastFailedTaskName;
	}

	public void setLastFailedTaskName(String lastFailedTaskName) {
		this.lastFailedTaskName = lastFailedTaskName;
	}

}
