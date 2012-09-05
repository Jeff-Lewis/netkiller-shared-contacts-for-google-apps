package com.netkiller.core;

public class InvalidTermException extends Exception{
		 
	public InvalidTermException(String arg0){
		 super(arg0);
	 }
	
	public InvalidTermException(String arg0,Throwable thr1){
		 super(arg0,thr1);
	 }
}
