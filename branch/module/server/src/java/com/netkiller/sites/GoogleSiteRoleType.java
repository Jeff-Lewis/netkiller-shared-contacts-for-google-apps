/**
 * 
 */
package com.netkiller.sites;

/**
 * @author administrator
 * 
 */
public enum GoogleSiteRoleType {
	NONE, OWNER, PEEKER, READER, WRITER;

	@Override
	public String toString() {
		switch (this) {
		case NONE:
			return "none";
		case OWNER:
			return "owner";
		case PEEKER:
			return "peeker";
		case READER:
			return "reader";
		case WRITER:
			return "writer";
		default:
			return null;
		}
	}
}
