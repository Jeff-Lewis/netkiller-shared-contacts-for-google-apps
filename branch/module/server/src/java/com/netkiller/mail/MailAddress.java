package com.netkiller.mail;

public class MailAddress {
	
	private String name;
	
	private String email;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public MailAddress(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
