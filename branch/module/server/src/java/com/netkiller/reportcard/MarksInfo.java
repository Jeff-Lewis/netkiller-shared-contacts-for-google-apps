/**
 * 
 */
package com.netkiller.reportcard;

/**
 * @author vishesh
 * 
 */
public class MarksInfo {
	private String grade;
	private Double marks;
	private String remarks;
	private Boolean isNumeric;
	private Double maxMarks;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Double getMarks() {
		return marks;
	}

	public void setMarks(Double marks) {
		this.marks = marks;
	}

	public Double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(Double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getIsNumeric() {
		return isNumeric;
	}

	public void setIsNumeric(Boolean isNumeric) {
		this.isNumeric = isNumeric;
	}

	@Override
	public String toString() {
		return "MarksInfo [grade=" + grade + ", marks=" + marks + ", remarks="
				+ remarks + ", isNumeric=" + isNumeric + ", maxMarks="
				+ maxMarks + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
