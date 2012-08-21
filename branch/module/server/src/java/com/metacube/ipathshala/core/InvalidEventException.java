package com.metacube.ipathshala.core;

public class InvalidEventException extends Exception{
	public InvalidEventException(String arg0){
		 super(arg0);
	 }
	
	public InvalidEventException(String arg0,Throwable thr1){
		 super(arg0,thr1);
	 }

}