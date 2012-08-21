package com.metacube.ipathshala.workflow.impl.task;

import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.WorkflowContext;

public class TestWorkflowTask2 extends AbstractWorkflowTask {
	
	public TestWorkflowTask2 (int retryCount,int retryDone){
		super(retryCount,retryDone,0);
	}
	
	public TestWorkflowTask2 () {
		super();
	}
	
	@Override
	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException {
		System.out.println("*****************************");
		System.out.println("Executing Task 2");
		System.out.println("Name Of The Task "+taskName);
		System.out.println("*****************************");
		return context;
	}
}