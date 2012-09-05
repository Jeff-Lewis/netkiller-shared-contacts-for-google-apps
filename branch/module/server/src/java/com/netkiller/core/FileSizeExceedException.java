/**
 * 
 */
package com.netkiller.core;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author vishesh
 * 
 */
public class FileSizeExceedException extends AppException {

	private String fileName;
	private String propertyName;

	public FileSizeExceedException(String arg0, Throwable arg1,
			String propertyName, String fileName) {
		super(arg0, arg1);
		this.propertyName = propertyName;
		this.fileName = fileName;
	}

	public FileSizeExceedException(String arg0, String propertyName,
			String fileName) {
		super(arg0);
		this.propertyName = propertyName;
		this.fileName = fileName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	
	

}
