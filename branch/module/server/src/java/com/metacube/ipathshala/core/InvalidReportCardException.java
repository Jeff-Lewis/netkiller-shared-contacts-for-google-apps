package com.metacube.ipathshala.core;

public class InvalidReportCardException extends Exception{
	
	public InvalidReportCardException(String arg0){
		 super(arg0);
	 }
	
	public InvalidReportCardException(String arg0,Throwable thr1){
		 super(arg0,thr1);
	 }
}
