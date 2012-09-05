package com.netkiller.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class MyImage implements Serializable {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent
    private String name;

    @NotPersistent
	private byte[] fileByteArray;

	@NotPersistent
	private MultipartFile file;
	
   
	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getFileByteArray() {
		return fileByteArray;
	}
	public void setFileByteArray(byte[] fileByteArray) {
		this.fileByteArray = fileByteArray;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	@Persistent
	private BlobKey blobKey;
	
	public BlobKey getBlobKey() {
		return blobKey;
	}
	public void setBlobKey(BlobKey blobKey) {
		this.blobKey = blobKey;
	}

	@NotPersistent
    Blob image;

    public MyImage() { }
    public MyImage(String name, Blob image) {
        this.name = name; 
        this.image = image;
    }

    // JPA getters and setters and empty contructor
    // ...
    public Blob getImage()              { return image; }
    public void setImage(Blob image)    { this.image = image; }
}
