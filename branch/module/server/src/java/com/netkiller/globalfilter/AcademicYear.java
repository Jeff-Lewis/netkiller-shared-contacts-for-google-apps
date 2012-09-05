package com.netkiller.globalfilter;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.entity.metadata.impl.GlobalFilterType;

/**
 * Global Academic Year entity.
 * 
 * @author sparakh
 * 
 */

public class AcademicYear implements GlobalFilter,Serializable {



	private Key entityKey;
	private Date fromDate;
	private Date toDate;
	private Boolean isActive;

	/**
	 * 
	 * @param entityKey AcademicYear Key
	 * @param fromDate From Date
	 * @param toDate To Date
	 * @throws AppException
	 */
	public AcademicYear(Key entityKey,Date fromDate, Date toDate, Boolean isActive) throws AppException {
		this.entityKey = entityKey;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.isActive = isActive;
		}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param entityKey
	 *            the entityKey to set
	 */
	public void setEntityKey(Key entityKey) {
		this.entityKey = entityKey;
	}

	@Override
	public GlobalFilterType getGlobalFilterType() {
		return GlobalFilterType.GLOBAL_ACADEMIC_YEAR;
	}

	public Key getEntityKey() {
		return this.entityKey;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityKey == null) ? 0 : entityKey.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcademicYear other = (AcademicYear) obj;
		if (entityKey == null) {
			if (other.entityKey != null)
				return false;
		} else if (!entityKey.equals(other.entityKey))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		return true;
	}
}
