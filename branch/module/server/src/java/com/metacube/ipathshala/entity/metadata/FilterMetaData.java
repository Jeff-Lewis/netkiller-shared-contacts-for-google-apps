package com.metacube.ipathshala.entity.metadata;

import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterType;
import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterValueType;

public class FilterMetaData {

	
	private GlobalFilterType type;
	private GlobalFilterValueType valueType;

	public FilterMetaData(GlobalFilterType type,GlobalFilterValueType valueType)	{
		this.type = type;
		this.valueType = valueType;
	}
	
	/**
	 * @return the type
	 */
	public GlobalFilterType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(GlobalFilterType type) {
		this.type = type;
	}

	/**
	 * @return the valueType
	 */
	public GlobalFilterValueType getValueType() {
		return valueType;
	}

	/**
	 * @param valueType
	 *            the valueType to set
	 */
	public void setValueType(GlobalFilterValueType valueType) {
		this.valueType = valueType;
	}
}
