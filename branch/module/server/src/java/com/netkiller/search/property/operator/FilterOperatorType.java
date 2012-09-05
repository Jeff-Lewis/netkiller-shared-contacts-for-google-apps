package com.netkiller.search.property.operator;

public enum FilterOperatorType {
	EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, STARTS_WITH, ENDS_WITH, GREATER_THAN_EQUAL, LESS_THAN_EQUAL;

	@Override
	public String toString() {
		switch (this) {
		case EQUAL:
			return "==";
		case NOT_EQUAL:
			return "!=";
		case GREATER_THAN:
			return ">";
		case LESS_THAN:
			return "<";
		case STARTS_WITH:
			return "startsWith";
		case ENDS_WITH:
			return "endsWith";
		case GREATER_THAN_EQUAL:
			return ">=";
		case LESS_THAN_EQUAL:
			return "<=";
			
		default:
			return null;
		}
	}

}
