package com.netkiller.workflow	;

import com.netkiller.exception.AppException;

/**
 * Exception to flag any exception in a Workflow execution.
 * @author prateek
 *
 */
public class WorkflowExecutionException extends AppException{

	public WorkflowExecutionException(String arg0, Throwable arg1) {
		super(arg0);
	}

	public WorkflowExecutionException(String arg0) {
		super(arg0);
	}

}
