package com.metacube.ipathshala.vo;

import java.io.File;
import java.io.InputStream;

import com.metacube.ipathshala.core.EntityType;

public class ImportVO {

	private InputStream stream;

	private EntityType entityType;

	/**
	 * @return the stream
	 */
	public InputStream getStream() {
		return stream;
	}

	/**
	 * @param stream
	 *            the stream to set
	 */
	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportVO [entityType=" + entityType + ", stream=" + stream + ", getEntityType()=" + getEntityType()
				+ ", getStream()=" + getStream() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public ImportVO() {
		super();
	}

}
