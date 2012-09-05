package com.netkiller.globalfilter;

import java.io.Serializable;

import com.netkiller.entity.metadata.impl.GlobalFilterType;

public class Active implements GlobalFilter,Serializable {
	
	private boolean isActive;

	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isActive ? 1231 : 1237);
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
		Active other = (Active) obj;
		if (isActive != other.isActive)
			return false;
		return true;
	}

	public Active(boolean isActive)	{
		this.isActive = isActive;
	}
	
	@Override
	public GlobalFilterType getGlobalFilterType() {
		return GlobalFilterType.ACTIVE;	}
	

}