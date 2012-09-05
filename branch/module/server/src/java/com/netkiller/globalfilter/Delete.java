package com.netkiller.globalfilter;

import java.io.Serializable;

import com.netkiller.entity.metadata.impl.GlobalFilterType;

public class Delete implements GlobalFilter,Serializable{
	
	private boolean isDeleted;

	public boolean isDeleted() {
		return isDeleted;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isDeleted ? 1231 : 1237);
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
		Delete other = (Delete) obj;
		if (isDeleted != other.isDeleted)
			return false;
		return true;
	}

	public Delete(boolean isDeleted)	{
		this.isDeleted = isDeleted;
	}
	
	@Override
	public GlobalFilterType getGlobalFilterType() {
		return GlobalFilterType.DELETE;	}
	


}
