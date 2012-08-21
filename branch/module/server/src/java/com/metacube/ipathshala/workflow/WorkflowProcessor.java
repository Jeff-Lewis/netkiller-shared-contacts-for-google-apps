package com.metacube.ipathshala.workflow;

import java.util.List;

public interface WorkflowProcessor {

	/**
	 * Abstract method used to kickoff the processing of work flow activities.
	 * Each processor implementation should implement doActivities as a means of
	 * carrying out the activities wired to the workflow process.
	 */
	public void doTasks();

	/**
	 * Sets the collection of Activities to be executed by the Workflow Process
	 * 
	 * @param activities
	 *            ordered collection (List) of activities to be executed by the
	 *            processor
	 */
	public void setWorkflowTasks(List<WorkflowTask> activities);

	/**
	 * Error handler that the Workflow processor would use to perform error
	 * hadnling
	 * 
	 * @param defaultErrorHandler
	 */
	public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler);

	public void setContext(WorkflowContext context);
}
