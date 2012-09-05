package com.netkiller.security;

import com.netkiller.core.AppException;

public interface AppGroupService {

	public AppGroup getGroup(String id) throws AppException; 
}
