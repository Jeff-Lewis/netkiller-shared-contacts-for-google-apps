package com.metacube.ipathshala.service.file;

import com.metacube.ipathshala.core.AppException;

public interface FileService {
	
	public File createFile(File file) throws AppException;
	
	public File readFile(String bucketName,String fileName) throws AppException;
	
	public File updateFile(String filePath, byte[] data);

}
