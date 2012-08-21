package com.metacube.ipathshala.core;

/**
 * A generic Application Exception class.
 * 
 * @author prateek
 * 
 */
public class AppException extends Exception {

	public AppException(String arg0) {
		super(arg0);
	}

	public AppException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
