package com.metacube.ipathshala.mail;

public class MailAttachment {
	
	private Byte[] file;
	
	private String filename;

	/**
	 * @return the file
	 */
	public Byte[] getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(Byte[] file) {
		this.file = file;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

}
