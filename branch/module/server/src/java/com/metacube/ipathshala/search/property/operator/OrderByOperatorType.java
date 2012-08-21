package com.metacube.ipathshala.search.property.operator;

public enum OrderByOperatorType {
	ASC, DESC;

	@Override
	public String toString() {
		switch (this) {
		case ASC:
			return "ascending";
		case DESC:
			return "descending";
		default:
			return null;
		}
	}

}
