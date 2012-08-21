package com.metacube.ipathshala.dataupload;

import com.metacube.ipathshala.core.DataContext;

public class PathshalaDataUploadContext extends DataUploadContext{

	DataContext dataContext;

	public DataContext getDataContext() {
		return dataContext;
	}

	public void setDataContext(DataContext dataContext) {
		this.dataContext = dataContext;
	}	
	
}
