package com.metacube.ipathshala.core;

public enum ReportCardStatus {
	INITILIAZED, CREATED,INPROGRESS,FAILED;

	@Override
	public String toString() {
		switch (this) {
		case INITILIAZED:
			return "INITILIAZED";
		case CREATED:
			return "CREATED";
		case INPROGRESS:
			return "INPROGRESS";
		case FAILED:
			return "FAILED";
		default:
			return null;
		}
	}
}

