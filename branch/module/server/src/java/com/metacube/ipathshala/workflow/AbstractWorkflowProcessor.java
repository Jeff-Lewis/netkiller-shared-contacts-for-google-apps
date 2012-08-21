package com.metacube.ipathshala.workflow;

import java.util.List;

/**
 * 
 * @author prateek
 * 
 */
public abstract class AbstractWorkflowProcessor implements WorkflowProcessor {
	protected List<WorkflowTask> workflowTasks;
	protected ErrorHandler defaultErrorHandler;
	protected WorkflowContext context;

	public AbstractWorkflowProcessor(WorkflowContext context) {
		this.context = context;
	}
	
	public AbstractWorkflowProcessor() {
		
	}

	public ErrorHandler getDefaultErrorHandler() {
		return defaultErrorHandler;
	}

	public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler) {
		this.defaultErrorHandler = defaultErrorHandler;
	}

	public WorkflowContext getContext() {
		return context;
	}

	public void setContext(WorkflowContext context) {
		this.context = context;
	}

	@Override
	public abstract void doTasks();

	public List<WorkflowTask> getWorkflowTasks() {
		return workflowTasks;
	}

	public void setWorkflowTasks(List<WorkflowTask> workflowTasks) {
		this.workflowTasks = workflowTasks;
	}

}
