package com.metacube.ipathshala.security.acl;

public interface Permission {

	public enum PermissionType {
		NONE(0), READ(1), WRITE(2), DELETE(3);

		private int weightage;

		PermissionType(int weightage) {
			this.weightage = weightage;
		}

		public int weightage() {
			return weightage;
		}
	}

	public  PermissionType getPermissionType();
}
