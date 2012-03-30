package com.netkiller.mail;



import java.util.ArrayList;
import java.util.List;


/**
 * This class represents an Email.
 * @author saurab
 * 
 */

public class MailMessage {

	private String subject;
	
	private String textMessage;
	
	private List<Recipient> recipients;
	
	private MailAddress sender;
	
	private String htmlBody;
	

	
	private MailAddress replyAddress;
	
	
	/**
	 * @return the replyAddress
	 */
	public MailAddress getReplyAddress() {
		return replyAddress;
	}

	/**
	 * @param replyAddress the replyAddress to set
	 */
	public void setReplyAddress(MailAddress replyAddress) {
		this.replyAddress = replyAddress;
	}

	/**
	 * @return the recipients
	 */
	public List<Recipient> getRecipients() {
			
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}



	/**
	 * @return the sender
	 */
	public MailAddress getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(MailAddress sender) {
		this.sender = sender;
	}

	/**
	 * @return the htmlBody
	 */
	public String getHtmlBody() {
		return htmlBody;
	}

	/**
	 * @param htmlBody the htmlBody to set
	 */
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}


	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the textMessage
	 */
	public String getTextMessage() {
		return textMessage;
	}

	/**
	 * @param textMessage the textMessage to set
	 */
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public void addRecipient(Recipient recipient)	{
		if(this.recipients == null)	{
			this.recipients = new ArrayList<Recipient>();
		}
		this.recipients.add(recipient);
	}
	



	

}
