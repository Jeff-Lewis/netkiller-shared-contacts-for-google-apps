package com.metacube.ipathshala.workflow;

/**
 * A task interface to be implementing by classes
 * which have the actual logic for doing a particular
 * task in a workflow.
 * @author prateek
 *
 */
public interface WorkflowTask {

	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException;
	
	public String getTaskName();
	
	public void setTaskName(String taskName);
	
	public WorkflowContext retry(WorkflowContext context, Exception executionException) throws WorkflowExecutionException;
}
