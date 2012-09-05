/**
 * 
 */
package com.netkiller.core;

/**
 * @author vishesh
 *
 */
public class UserIdConflictException extends AppException{

	private String propertyName;
	private Object propertyValue;
	
	public UserIdConflictException(String arg0) {
		super(arg0);
	}
	
	public UserIdConflictException(String arg0, String propertyName, Object propertyValue){
		super(arg0);
		this.propertyName  = propertyName;
		this.propertyValue = propertyValue;
	}

	public UserIdConflictException(String arg0,Throwable arg1){
		super(arg0,arg1);
	}
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	

}
