package com.metacube.ipathshala.workflow.impl.processor;

public enum WorkflowStatusType {
	QUEUED, SUCCESSFUL, FAILED, INPROGRESS;

	@Override
	public String toString() {
		switch (this) {
		case QUEUED:
			return "Queued";
		case SUCCESSFUL:
			return "Successful";
		case FAILED:
			return "Failed";
		case INPROGRESS:
			return "In Progress";
		default:
			return null;
		}
	}

}
