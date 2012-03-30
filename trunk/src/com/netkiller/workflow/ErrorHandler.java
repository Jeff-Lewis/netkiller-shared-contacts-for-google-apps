
package com.netkiller.workflow;

/**
 * ErrorHandler that defines what the Workflow framework
 * should do whenever an exception occurs during
 * execution of a task. 
 * @author prateek
 *
 */
public interface ErrorHandler{
    
    public void handleError(WorkflowContext context, Throwable th);

}
