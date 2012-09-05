package com.netkiller.core;

public class InvalidStageException extends Exception{
	public InvalidStageException(String arg0){
		 super(arg0);
	 }
	
	public InvalidStageException(String arg0,Throwable thr1){
		 super(arg0,thr1);
	 }

}
