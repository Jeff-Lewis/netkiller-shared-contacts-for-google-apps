package com.netkiller.dataupload;

import java.io.InputStream;

import com.netkiller.core.AppException;

public interface DataUploader {
	
	public void uploadData(InputStream stream, DataUploadContext context) throws AppException;

}
