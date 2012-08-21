package com.metacube.ipathshala.core;

public enum UserRoleType {
	ADMIN, TEACHER, STUDENT, PARENT , DOMAIN;

	@Override
	public String toString() {
		switch (this) {
		case ADMIN:
			return "ipath_app_group_admin";
		case STUDENT:
			return "ipath_app_group_student";
		case PARENT:
			return "ipath_app_group_parent";
		case TEACHER:
			return "ipath_app_group_teacher";
		case DOMAIN:
			return "ipath_app_group_domain";

		default:
			return null;
		}
	}
	
	public static UserRoleType get(String role){
		UserRoleType userRoleType = null;
	    for(UserRoleType userRole : values()){
	        if( userRole.toString().equals(role)){
	        	userRoleType =  userRole;
	        }
	    }
	    return userRoleType;
	}



}

