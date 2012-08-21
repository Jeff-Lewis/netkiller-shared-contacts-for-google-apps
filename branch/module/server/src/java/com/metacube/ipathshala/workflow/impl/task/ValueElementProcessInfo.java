package com.metacube.ipathshala.workflow.impl.task;

import com.metacube.ipathshala.core.jaxb.value.ValueElement;

public class ValueElementProcessInfo {

	ValueElement valueElement;

	boolean isProcessed;

	public ValueElement getValueElement() {
		return valueElement;
	}

	public void setValueElement(ValueElement valueElement) {
		this.valueElement = valueElement;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public ValueElementProcessInfo(ValueElement valueElement,
			boolean isProcessed) {
		super();
		this.valueElement = valueElement;
		this.isProcessed = isProcessed;
	}

}
