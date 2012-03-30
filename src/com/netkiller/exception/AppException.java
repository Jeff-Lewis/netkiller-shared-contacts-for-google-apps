package com.netkiller.exception;

public class AppException extends Exception{

	private String customMessage;
	
	
	public AppException(){}
	public AppException(String message){
		setCustomMessage(message);
	}
	
	public AppException(String messString, Throwable arg){
		
	}
	public String getCustomMessage() {
		return customMessage;
	}
	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}
	
}

