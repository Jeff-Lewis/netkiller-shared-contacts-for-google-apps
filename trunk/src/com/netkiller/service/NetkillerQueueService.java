package com.netkiller.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.netkiller.workflow.WorkflowContext;

@Service
public class NetkillerQueueService {
	/**
	 * This will send a request to google apps task queue service to create user
	 * in app domain. Google app engine will initiate the task in background.
	 * 
	 * @param params
	 *            the parameters required by the task handler.
	 */
	public void requestCreateUserInAppDomain(Map<String, String> params) {
		// Getting the required queue that will manage the required task.
		Queue queue = QueueFactory.getQueue("call-domain-service");

		// TODO: need to find the way to secure this call, else normal user can
		// initiate this task through simple URL hit.
		TaskOptions options = TaskOptions.Builder.withUrl("/async/task/createUserInAppDomain.do");

		// Setting parameters for the task handler
		for (Map.Entry<String, String> entry : params.entrySet()) {
			options.param(entry.getKey(), entry.getValue());
		}

		// adding the handler information to the queue, after this app engine
		// will initiate the task asynchronously
		queue.add(options);
	}
	
	public void triggerWorkflow(WorkflowContext context) {
		Queue queue = QueueFactory.getQueue("call-workflow");
		TaskOptions options = TaskOptions.Builder.withUrl("/async/task/performworkflowtasks.do");
		CacheManager cmanager=CacheManager.getInstance();
		Cache cache=cmanager.getCache("WorkflowContextCache");
		cache.put(context.getWorkflowInfo().getWorkflowInstance(),context);
		options.param("workflowinstanceid", context.getWorkflowInfo().getWorkflowInstance()).header("Host", BackendServiceFactory.getBackendService().getBackendAddress("worker"));
		queue.add(options);
		}  
	
	public void triggerContactsSyncWorkflow(WorkflowContext context) {
		Queue queue = QueueFactory.getQueue("sync-contacts");
		TaskOptions options = TaskOptions.Builder.withUrl("/async/task/performworkflowtasks.do");
		CacheManager cmanager=CacheManager.getInstance();
		Cache cache=cmanager.getCache("WorkflowContextCache");
		cache.put(context.getWorkflowInfo().getWorkflowInstance(),context);
		options.param("workflowinstanceid", context.getWorkflowInfo().getWorkflowInstance()).header("Host", BackendServiceFactory.getBackendService().getBackendAddress("worker"));
		queue.add(options);
		}  

}
