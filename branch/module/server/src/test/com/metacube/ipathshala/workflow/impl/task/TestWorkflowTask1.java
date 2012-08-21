package com.metacube.ipathshala.workflow.impl.task;

import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.WorkflowContext;

public class TestWorkflowTask1 extends AbstractWorkflowTask  {

	public TestWorkflowTask1 (int retryCount){
		super(retryCount,0,0);
	}
	
	public TestWorkflowTask1 () {
		super();
	}
	
	@Override
	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException {
		System.out.println("*****************************");
		System.out.println("Executing Task 1");
		System.out.println("Name Of The Task "+taskName);
		System.out.println("*****************************");
		return context;
	}
}
