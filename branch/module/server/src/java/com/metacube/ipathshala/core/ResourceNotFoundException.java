/**
 * 
 */
package com.metacube.ipathshala.core;

/**
 * @author vishesh
 *
 */
public class ResourceNotFoundException extends AppException{

	private String resourceName ;
	
	public ResourceNotFoundException(String arg0, Throwable arg1,String propertyName) {
		super(arg0, arg1);
		this.resourceName = propertyName ;
	}
	
	public ResourceNotFoundException(String arg0, String propertyName) {
		super(arg0);
		this.resourceName = propertyName ;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	

}
