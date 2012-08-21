package com.metacube.ipathshala.workflow.impl.task;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;

public class TestWorkflowTask3 extends AbstractWorkflowTask {
	
	public TestWorkflowTask3 () {
		super();
	}
	
	@Override
	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException {
		System.out.println("*****************************");
		System.out.println("Executing Task 3");
		System.out.println("Name Of The Task "+taskName);
		System.out.println("Max Retry Allowed : "+retryCount);
		System.out.println("Current Retry Number : "+actualRetryDone);
		System.out.println("*****************************");
		AppException exception=new AppException("Test Exception");
		throw new WorkflowExecutionException("Throwing Exception",exception);
		//return context;
	}

}
