package com.metacube.ipathshala.core;

public class EmptyFileException extends AppException{

	private static final long serialVersionUID = 1L;
	
	private String propertyName;
	
	public String getPropertyName() {
		return propertyName;
	}

	public EmptyFileException(String arg0) {
		super(arg0);
	}
	
	public EmptyFileException(String arg0, String propName) {
		super(arg0);
		propertyName = propName;
	}
	
	
	public EmptyFileException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}




}
