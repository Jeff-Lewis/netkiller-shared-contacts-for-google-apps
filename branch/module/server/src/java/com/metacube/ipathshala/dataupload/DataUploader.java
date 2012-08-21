package com.metacube.ipathshala.dataupload;

import java.io.InputStream;

import com.metacube.ipathshala.core.AppException;

public interface DataUploader {
	
	public void uploadData(InputStream stream, DataUploadContext context) throws AppException;

}
