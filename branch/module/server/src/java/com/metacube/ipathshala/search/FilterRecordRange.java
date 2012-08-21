package com.metacube.ipathshala.search;

public class FilterRecordRange {

	int startRange;
	int endRange;
	
	public FilterRecordRange(int startRange, int endRange) {
		super();
		this.startRange = startRange;
		this.endRange = endRange;
	}
	public int getStartRange() {
		return startRange;
	}
	public void setStartRange(int startRange) {
		this.startRange = startRange;
	}
	public int getEndRange() {
		return endRange;
	}
	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}
}
