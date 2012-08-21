package com.metacube.ipathshala.workflow;

import com.metacube.ipathshala.util.AppLogger;

/**
 * An abstract class for Workflow Task that can use some template methods for
 * performing general operations like retries etc.
 * 
 * @author prateek
 * 
 */
public abstract class AbstractWorkflowTask implements WorkflowTask {

	protected int retryCount;
	protected int actualRetryDone;
	protected long retryWaitInMillis;
	protected String taskName;
	private static final AppLogger log = AppLogger.getLogger(AbstractWorkflowTask.class);

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public int getActualRetryDone() {
		return actualRetryDone;
	}

	public void setActualRetryDone(int actualRetryDone) {
		this.actualRetryDone = actualRetryDone;
	}

	public long getRetryWaitInMillis() {
		return retryWaitInMillis;
	}

	public void setRetryWaitInMillis(long retryWaitInMillis) {
		this.retryWaitInMillis = retryWaitInMillis;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public AbstractWorkflowTask(int retryCount, int actualRetryDone, long retryWaitInMillis) {
		super();
		this.retryCount = retryCount;
		this.actualRetryDone = actualRetryDone;
		this.retryWaitInMillis = retryWaitInMillis;
	}

	public AbstractWorkflowTask() {
		super();
	}

	/**
	 * Template method for performing retry for the task that has failed and
	 * needs to retry again.
	 * 
	 * @throws WorkflowExecutionException
	 */
	@Override
	public WorkflowContext retry(WorkflowContext context, Exception lastException) throws WorkflowExecutionException {
		WorkflowContext retryContext = context;
		// Checking if retry is possible
		if (actualRetryDone < retryCount) {
			actualRetryDone++;
			try {
				retryContext = execute(context);
				if (log.isDebugEnabled()) {
					log.debug("Task Retried With Context" + context);
				}
			} catch (WorkflowExecutionException wee) {
				retry(context, wee);
			}
			return retryContext;
		} else {
			log.error("Task Cannot be retried - Retry Count Exceeded");
			WorkflowExecutionException limitReachedException = new WorkflowExecutionException("Retry Limit Reached",
					lastException);
			throw limitReachedException;
		}
	}

	public abstract WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException;

}
