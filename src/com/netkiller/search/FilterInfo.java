package com.netkiller.search;



import java.util.ArrayList;
import java.util.List;

/**
 * A class whose object hold the filter data from a UI Component/UI Layer.
 * 
 * @author raj
 * 
 */
public class FilterInfo {

	private String groupOp;

	private List<Rule> rules = new ArrayList<Rule>();

	public static class Rule {

		private String field;

		private String op;

		private String data;

		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}

		/**
		 * @param field
		 *            the field to set
		 */
		public void setField(String field) {
			this.field = field;
		}

		/**
		 * @return the op
		 */
		public String getOp() {
			return op;
		}

		/**
		 * @param op
		 *            the op to set
		 */
		public void setOp(String op) {
			this.op = op;
		}

		/**
		 * @return the data
		 */
		public String getData() {
			return data;
		}

		/**
		 * @param data
		 *            the data to set
		 */
		public void setData(String data) {
			this.data = data;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Rule [data=" + data + ", field=" + field + ", op=" + op
					+ "]";
		}
	}

	/**
	 * @return the groupOp
	 */
	public String getGroupOp() {
		return groupOp;
	}

	/**
	 * @param groupOp
	 *            the groupOp to set
	 */
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	/**
	 * @return the rules
	 */
	public List<Rule> getRules() {
		return rules;
	}

	/**
	 * @param rules
	 *            the rules to set
	 */
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchCriteria [groupOp=" + groupOp + ", rules=" + rules + "]";
	}

}
