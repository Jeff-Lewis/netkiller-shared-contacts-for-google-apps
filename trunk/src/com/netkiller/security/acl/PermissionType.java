package com.netkiller.security.acl;

public enum PermissionType {
	NONE(0), READ(1), WRITE(2), DELETE(3);

	private int weightage;

	PermissionType(int weightage) {
		this.weightage = weightage;
	}

	public int weightage() {
		return weightage;
	}

	@Override
	public String toString() {
		String returnString = null;
		switch (this) {
		case NONE:
			returnString = "none";
			break;
		case READ:
			returnString = "read";
			break;
		case WRITE:
			returnString = "write";
			break;
		case DELETE:
			returnString = "delete";
			break;
		}

		return returnString;
	}
	
	public static PermissionType getPermissionType(String value)	{
		PermissionType permissionType = null;
		for(PermissionType pType:PermissionType.values())	{
			if(pType.toString().equalsIgnoreCase(value))
				permissionType =  pType;
		}
		return permissionType;
	}
}
