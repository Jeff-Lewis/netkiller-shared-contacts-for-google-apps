package com.netkiller.service.file;

import com.netkiller.core.AppException;

public interface FileService {
	
	public File createFile(File file) throws AppException;
	
	public File readFile(String bucketName,String fileName) throws AppException;
	
	public File updateFile(String filePath, byte[] data);

}
