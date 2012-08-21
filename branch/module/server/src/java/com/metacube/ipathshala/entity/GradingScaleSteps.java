package com.metacube.ipathshala.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;

/**
 * @author Jitender
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class GradingScaleSteps implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key gradingScaleKey;

	@Persistent
	private String stepDisplay;
	
	@Persistent
	private String numericDisplay;

	@Persistent
	private Double stepWeight;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	@Persistent
	private String minimum;

	@Persistent
	private String maximum;

	@Persistent
	private Boolean isDeleted = false;
	
	@Persistent
	private Key colorKey;
	
	@NotPersistent
	private Value colorValue;
	
	@NotPersistent
	private String colorBusinessKey;
	
	@NotPersistent
	private String colorKeyString;
	

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	
	public String getNumericDisplay() {
		return numericDisplay;
	}

	public void setNumericDisplay(String numericDisplay) {
		this.numericDisplay = numericDisplay;
	}
	
	

	public Key getColorKey() {
		return colorKey;
	}

	public void setColorKey(Key colorKey) {
		this.colorKey = colorKey;
	}

	public Value getColorValue() {
		return colorValue;
	}

	public void setColorValue(Value colorValue) {
		this.colorValue = colorValue;
	}

	public String getColorBusinessKey() {
		return colorBusinessKey;
	}

	public void setColorBusinessKey(String colorBusinessKey) {
		this.colorBusinessKey = colorBusinessKey;
	}

	public String getColorKeyString() {
		return colorKeyString;
	}

	public void setColorKeyString(String colorKeyString) {
		this.colorKeyString = colorKeyString;
	}

	

	@Override
	public String toString() {
		return "GradingScaleSteps [key=" + key + ", gradingScaleKey="
				+ gradingScaleKey + ", stepDisplay=" + stepDisplay
				+ ", numericDisplay=" + numericDisplay + ", stepWeight="
				+ stepWeight + ", lastModifiedDate=" + lastModifiedDate
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + ", minimum=" + minimum
				+ ", maximum=" + maximum + ", isDeleted=" + isDeleted
				+ ", colorKey=" + colorKey + ", colorValue=" + colorValue
				+ ", colorBusinessKey=" + colorBusinessKey
				+ ", colorKeyString=" + colorKeyString + "]";
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getGradingScaleKey() {
		return gradingScaleKey;
	}

	public void setGradingScaleKey(Key gradingScaleKey) {
		this.gradingScaleKey = gradingScaleKey;
	}

	public String getStepDisplay() {
		return stepDisplay;
	}

	public void setStepDisplay(String stepDisplay) {
		this.stepDisplay = stepDisplay;
	}

	public Double getStepWeight() {
		return stepWeight;
	}

	public void setStepWeight(Double stepWeight) {
		this.stepWeight = stepWeight;
	}

	@Override
	public void jdoPreStore() {
		// TODO Auto-generated method stub

	}

}
