package com.netkiller.mail;



public class Recipient {
	
	private MailAddress mailAddress;
	
	private RecipientType recipientType;

	/**
	 * @return the mailAddress
	 */
	public MailAddress getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(MailAddress mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the recipientType
	 */
	public RecipientType getRecipientType() {
		return recipientType;
	}

	/**
	 * @param recipientType the recipientType to set
	 */
	public void setRecipientType(RecipientType recipientType) {
		this.recipientType = recipientType;
	}
	
	
	
	

}
