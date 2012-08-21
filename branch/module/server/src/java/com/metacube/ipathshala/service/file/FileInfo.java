package com.metacube.ipathshala.service.file;

public class FileInfo {
	private String fileName;
	private String owner;
	private String fileType;
	private FileACLType acl = FileACLType.PUBLIC_READ;
	private CharacterSet charSet = CharacterSet.UTF8;
	private String bucketName;
	
	public CharacterSet getCharSet() {
		return charSet;
	}
	public void setCharSet(CharacterSet charSet) {
		this.charSet = charSet;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public FileACLType getAcl() {
		return acl;
	}
	public void setAcl(FileACLType acl) {
		this.acl = acl;
	}
	
}
