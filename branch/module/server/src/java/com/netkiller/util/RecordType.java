package com.netkiller.util;

public enum RecordType {
	GENERAL, HEALTH_RECORD, SELF_AWARENESS;

	public String toString() {
		switch (this) {
		case GENERAL:
			return "General";
		case HEALTH_RECORD:
			return "Health";
		case SELF_AWARENESS:
			return "Awareness";
		default:
			return null;
		}

	}
}
