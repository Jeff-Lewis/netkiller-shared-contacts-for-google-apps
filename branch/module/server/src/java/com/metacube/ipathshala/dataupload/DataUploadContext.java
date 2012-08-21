package com.metacube.ipathshala.dataupload;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DataUploadContext implements Serializable{

	private DataUploadReader reader;
	
	private LinkedList<String> entityProcessingQueue = new LinkedList<String>();

	private Map<String,Map> entityDependencyMap = new HashMap<String, Map>();

	public DataUploadReader getReader() {
		return reader;
	}

	public void setReader(DataUploadReader reader) {
		this.reader = reader;
	}
	
	public Map<String, Map> getEntityDependencyMap() {
		return entityDependencyMap;
	}

	public LinkedList<String> getEntityProcessingQueue() {
		return entityProcessingQueue;
	}	
	
	
}
