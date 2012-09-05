package com.netkiller.dataupload;

import java.util.ArrayList;

import com.netkiller.core.AppException;

public interface DataUploadEntityProcessor {

	public void populateExistingEntitiesinContext(DataUploadContext context);
	public void uploadEntity(ArrayList data,DataUploadContext context) throws AppException;
	public void rollbackEntity(DataUploadContext context);
	
}
