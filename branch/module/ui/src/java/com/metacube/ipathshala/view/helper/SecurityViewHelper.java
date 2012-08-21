package com.metacube.ipathshala.view.helper;

import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.security.AppUser;
import com.metacube.ipathshala.security.acl.Permission;

public class SecurityViewHelper {

	public static boolean hasReadPermission(AppUser currentAppUser, String resourceName, DataContext dataContext) {
		if (currentAppUser != null && resourceName != null) {
			if (currentAppUser.hasAccess(resourceName, Permission.PermissionType.READ)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasWritePermission(AppUser currentAppUser, String resourceName, DataContext dataContext) {
		if (currentAppUser != null && resourceName != null) {
			if (dataContext!=null && dataContext.getCurrentSelectedAcademicYear() != null
					&& dataContext.getCurrentSelectedAcademicYear().getIsActive()) {
				if (currentAppUser.hasAccess(resourceName, Permission.PermissionType.WRITE)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean hasDeletePermission(AppUser currentAppUser, String resourceName, DataContext dataContext) {
		if (currentAppUser != null && resourceName != null) {
			if (dataContext!=null && dataContext.getCurrentSelectedAcademicYear() != null
					&& dataContext.getCurrentSelectedAcademicYear().getIsActive()) {
				if (currentAppUser.hasAccess(resourceName, Permission.PermissionType.DELETE)
						&& dataContext.getCurrentSelectedAcademicYear().getIsActive()) {
					return true;
				}
			}
		}
		return false;
	}

}
