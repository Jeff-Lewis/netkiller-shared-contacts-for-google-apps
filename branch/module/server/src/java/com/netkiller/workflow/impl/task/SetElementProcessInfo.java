package com.netkiller.workflow.impl.task;

import com.netkiller.core.jaxb.set.SetElement;

public class SetElementProcessInfo {

	SetElement setElement;

	boolean isProcessed;

	public SetElement getSetElement() {
		return setElement;
	}

	public void setSetElement(SetElement setElement) {
		this.setElement = setElement;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public SetElementProcessInfo(SetElement setElement, boolean isProcessed) {
		super();
		this.setElement = setElement;
		this.isProcessed = isProcessed;
	}

}
