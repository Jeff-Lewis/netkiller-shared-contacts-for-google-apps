package com.metacube.ipathshala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.Workflow;
import com.metacube.ipathshala.manager.WorkflowManager;
import com.metacube.ipathshala.service.IPathshalaQueueService;
import com.metacube.ipathshala.workflow.WorkflowInfo;
import com.metacube.ipathshala.workflow.impl.context.TestWorkflowContext;
import com.metacube.ipathshala.workflow.impl.processor.WorkflowStatusType;
import com.metacube.ipathshala.workflow.util.WorkflowUtil;

/**
 * @author dhruvsharma
 * 
 *         A Test controller, to trigger the execution of a work flow in the dev
 *         environment
 * 
 */
@Controller
public class TestWrokflowTriggerController {

	@Autowired
	IPathshalaQueueService workflowQueueService;

	@Autowired
	WorkflowManager workflowManager;

	@RequestMapping("/testworkflow.do")
	public String postWorkflow() {
		WorkflowInfo info = new WorkflowInfo("workflowinfo");
		info.setIsNewWorkflow(true);
		TestWorkflowContext workflowcontext = new TestWorkflowContext();
		workflowcontext.setWorkflowInfo(info);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName(info.getWorkflowName());
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow.setContext(workflowcontext);
		try {
			workflowManager.createWorkflow(workflow);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create a new ccontext or load the context from the datastore
		// workflowQueueService.triggerWorkflow(workflowcontext);
		return UICommonConstants.VIEW_INDEX;
	}

}
