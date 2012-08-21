package com.metacube.ipathshala.globalfilter;
import java.io.Serializable;
import java.util.Map;

import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterType;

public class GlobalFilterMap implements Serializable {
	


	public GlobalFilterMap() {
		// Default Constructor
	}
	
	public GlobalFilterMap(Map<GlobalFilterType,GlobalFilter> globalFilterHashMap) {
		this.globalFilterHashMap = globalFilterHashMap;
	}
	
	private Map<GlobalFilterType,GlobalFilter> globalFilterHashMap;

	/**
	 * @return the globalFilterMap
	 */
	public Map<GlobalFilterType, GlobalFilter> getGlobalFilterHashMap() {
		return globalFilterHashMap;
	}

	/**
	 * @param globalFilterMap the globalFilterMap to set
	 */
	public void setGlobalFilterHashMap(
			Map<GlobalFilterType, GlobalFilter> globalFilterMap) {
		this.globalFilterHashMap = globalFilterMap;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((globalFilterHashMap == null) ? 0 : globalFilterHashMap.hashCode());
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
		GlobalFilterMap other = (GlobalFilterMap) obj;
		if (globalFilterHashMap == null) {
			if (other.globalFilterHashMap != null)
				return false;
		} else if (!globalFilterHashMap.equals(other.globalFilterHashMap))
			return false;
		return true;
	}

}
