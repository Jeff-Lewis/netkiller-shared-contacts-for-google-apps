package com.netkiller.dao;

import java.util.Collection;

import com.netkiller.entity.Workflow;

public interface WorkflowDao {

	public Workflow create(Workflow object);
	
	public Workflow get(Object id);
	
	public Collection<Workflow> getAll();
	
	public Workflow update(Workflow workflow);
	
	public void remove (Object id);
	
	public Workflow getByWorkflowInstanceId(String workflowInstanceId);
}
