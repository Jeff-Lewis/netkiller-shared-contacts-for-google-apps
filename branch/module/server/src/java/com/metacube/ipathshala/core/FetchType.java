package com.metacube.ipathshala.core;

public enum FetchType {
	ACTIVE, ALL;
	

	@Override
	public String toString() {
		switch (this) {
		case ACTIVE:
			return "active";
		case ALL:
			return "all";
		default:
			return null;
		}
	}
}
