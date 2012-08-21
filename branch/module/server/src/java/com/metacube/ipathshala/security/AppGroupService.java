package com.metacube.ipathshala.security;

import com.metacube.ipathshala.core.AppException;

public interface AppGroupService {

	public AppGroup getGroup(String id) throws AppException; 
}
