package com.netkiller;

import java.util.ArrayList;
import java.util.List;

import com.netkiller.search.property.operator.InputFilterGroupOperatorType;

/**
 * A class whose object hold the filter data from a UI Component/UI Layer.
 * 
 * @author prateek
 * 
 */
public class FilterInfo {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupOp == null) ? 0 : groupOp.hashCode());
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilterInfo other = (FilterInfo) obj;
		if (groupOp == null) {
			if (other.groupOp != null)
				return false;
		} else if (!groupOp.equals(other.groupOp))
			return false;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}

	private String groupOp = InputFilterGroupOperatorType.AND;

	private List<Rule> rules = new ArrayList<Rule>();

	public static class Rule {

		private String field;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((data == null) ? 0 : data.hashCode());
			result = prime * result + ((field == null) ? 0 : field.hashCode());
			result = prime * result + ((op == null) ? 0 : op.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Rule other = (Rule) obj;
			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			if (field == null) {
				if (other.field != null)
					return false;
			} else if (!field.equals(other.field))
				return false;
			if (op == null) {
				if (other.op != null)
					return false;
			} else if (!op.equals(other.op))
				return false;
			return true;
		}

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
