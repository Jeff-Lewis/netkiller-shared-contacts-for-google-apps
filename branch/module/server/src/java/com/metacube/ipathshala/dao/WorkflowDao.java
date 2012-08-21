package com.metacube.ipathshala.dao;

import java.util.Collection;

import com.metacube.ipathshala.entity.Workflow;

public interface WorkflowDao {

	public Workflow create(Workflow object);
	
	public Workflow get(Object id);
	
	public Collection<Workflow> getAll();
	
	public Workflow update(Workflow workflow);
	
	public void remove (Object id);
	
	public Workflow getByWorkflowInstanceId(String workflowInstanceId);
}
