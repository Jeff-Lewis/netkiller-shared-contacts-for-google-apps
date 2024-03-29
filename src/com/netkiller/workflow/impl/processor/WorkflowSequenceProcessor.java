package com.netkiller.workflow.impl.processor;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.Workflow;
import com.netkiller.exception.AppException;
import com.netkiller.manager.WorkflowManager;
import com.netkiller.workflow.AbstractWorkflowProcessor;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.WorkflowTask;

/**
 * A workflow processor implementation that executes WorkflowTasks in sequence.
 * 
 * @author prateek
 * 
 */
@Component
public class WorkflowSequenceProcessor extends AbstractWorkflowProcessor {

	@Autowired
	private WorkflowManager workflowManager;
	
	protected final Logger log = Logger.getLogger(getClass().getName());

	public WorkflowSequenceProcessor(WorkflowContext context) {
		super(context);
	}

	public WorkflowSequenceProcessor() {
		super();
	}

	@Override
	public void doTasks() {
		WorkflowContext currentContext = context;
		WorkflowInfo currentInfo = context.getWorkflowInfo();
		
		
		// Currently we are not saving a workflow entity
		// Ideally we should keep a track of how many workflows are created, and therefore should save them
		Workflow workflow = null;
		/*try {
			workflow = workflowManager.getByWorkflowInstanceId(currentInfo.getWorkflowInstance());
		} catch (AppException e2) {
			log.log(Level.SEVERE,"Cannot Convert from Bolb to WorkflowContext : " + e2.getMessage());
		}*/
		Boolean isSuccessful = true;
		Boolean isNewWorkflow = currentInfo.getIsNewWorkflow();
		int startingIndex = getStartingTaskIndex();
		currentInfo.setIsNewWorkflow(false);
		for (int loopIndex = startingIndex; loopIndex < workflowTasks.size(); loopIndex++) {
			WorkflowTask currentTask = (WorkflowTask) workflowTasks.get(loopIndex);
			try {
				currentContext = currentTask.execute(currentContext);
			} catch (WorkflowExecutionException e) {
				try {
					currentTask.retry(currentContext, e);
				} catch (WorkflowExecutionException e1) {
					log.log(Level.SEVERE,"Retry Count Exceeded for Workflow" + currentInfo.getWorkflowInstance());
					currentInfo.setLastFailedTaskName(currentTask.getTaskName());
					currentContext.setWorkflowInfo(currentInfo);
					isSuccessful = false;
					break;
				}
			}
		}
		/*try {
			saveWorkflowInstance(workflow, currentContext, isNewWorkflow, isSuccessful);
		} catch (AppException e) {
			log.log(Level.SEVERE,"falied to update Workflow in DataStore " + workflow, e);
		}*/
	}

	private void saveWorkflowInstance(Workflow workflow, WorkflowContext currentContext, boolean createNew,
			boolean isSuccessful) throws AppException {
		if (isSuccessful) {
			workflow.setWorkflowStatus(WorkflowStatusType.SUCCESSFUL.toString());
			currentContext.getWorkflowInfo().setLastFailedTaskName(null);
		} else {
			workflow.setWorkflowStatus(WorkflowStatusType.FAILED.toString());
		}
		//workflow.setWorkflowName(currentContext.getWorkflowInfo().getWorkflowName()); workflow name already exists on workflow
		workflow.setWorkflowInstanceId(currentContext.getWorkflowInfo().getWorkflowInstance());
		workflow.setContext(currentContext);
		if (createNew) {
			try {
				workflowManager.createWorkflow(workflow);
			} catch (AppException e) {
				log.log(Level.SEVERE,"falied to create Workflow in DataStore");
			}
		} else {
			try {
				workflowManager.updateWorkflow(workflow);
			} catch (AppException exception) {
				log.log(Level.SEVERE,"falied to update Workflow in DataStore " + workflow, exception);
			}
		}
	}

	private int getStartingTaskIndex() {
		WorkflowInfo currentInfo = context.getWorkflowInfo();
		int startingIndex = 0;
		if (currentInfo.getIsNewWorkflow()) {
			return startingIndex;
		} else {
			for (Iterator<WorkflowTask> taskIterator = workflowTasks.iterator(); taskIterator.hasNext();) {
				AbstractWorkflowTask currentTask = (AbstractWorkflowTask) taskIterator.next();
				if (currentTask.getTaskName().equals(currentInfo.getLastFailedTaskName())) {
					break;
				}
				startingIndex++;
			}
			return startingIndex;
		}
	}

}