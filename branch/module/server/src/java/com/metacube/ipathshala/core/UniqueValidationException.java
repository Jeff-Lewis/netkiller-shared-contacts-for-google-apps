package com.metacube.ipathshala.core;

public class UniqueValidationException extends AppException {
	
	private String propertyName;
	private Object propertyValue;
	
	/**
	 * @return the uniquePropertyName
	 */
	public String getUniquePropertyName() {
		return propertyName;
	}

	/**
	 * @return the uniquePropertyValue
	 */
	public Object getUniquePropertyValue() {
		return propertyValue;
	}

	public UniqueValidationException(String arg0) {
		super(arg0);
	}
	
	public UniqueValidationException(String arg0, String propertyName, Object propertyValue) {
		super(arg0);
		this.propertyName  = propertyName;
		this.propertyValue = propertyValue;
		
	}

	public UniqueValidationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
