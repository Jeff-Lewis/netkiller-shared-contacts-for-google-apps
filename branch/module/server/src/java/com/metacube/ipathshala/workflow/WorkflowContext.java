package com.metacube.ipathshala.workflow;

/**
 * Workflow context contains information that would be used
 * by all the WorkflowTasks of a workflow to do their job
 * and will also serve the workflow execution processors
 * with information to do their job.
 * .
 * 
 * 
 * @author prateek
 *
 */
public interface WorkflowContext{ 
    /**
     * Information about the workflow being executed.
     * @return
     */
    public WorkflowInfo getWorkflowInfo();
    
    public void setWorkflowInfo(WorkflowInfo workflowInfo);
    
    public String toString();
}   
 