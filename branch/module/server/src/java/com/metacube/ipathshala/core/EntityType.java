package com.metacube.ipathshala.core;

public enum EntityType {
	SET, VALUE , DATA,CONTACT;

	@Override
	public String toString() {
		switch (this) {
		case SET:
			return "Set";
		case VALUE:
			return "Value";
		case DATA:
			return "Data";
		case CONTACT:
			return "Contact";

		default:
			return null;
		}
	}

}
