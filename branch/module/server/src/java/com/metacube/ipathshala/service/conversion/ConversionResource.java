package com.metacube.ipathshala.service.conversion;

public class ConversionResource {
	public ConversionResource(String resourceName, String mimeType, byte[] data)	{
		this.resourceName = resourceName;
		this.mimeType = mimeType;
		this.data = data;
	}
	private String resourceName;
	private String mimeType;
	private byte[] data;
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

}
