package com.netkiller.vo;

public class SendSMSVO {
	
	public static final String SENDTO_PARENT = "Parents";
	public static final String SENDTO_TEACHER = "Teachers";
	public static final String SENDTO_ALL = "All";
	
	
	private String classBusinessKey;
	private String sendTo;
	private String messageText;
	
	public String getClassBusinessKey() {
		return classBusinessKey;
	}
	public void setClassBusinessKey(String classBusinessKey) {
		this.classBusinessKey = classBusinessKey;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

}
