package com.metacube.ipathshala.core.userresourcemap;

import java.util.List;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;

public interface UserSiteListGenerator {
	
	public List<SiteInfo> getSiteList(String userId, DataContext dataContext) throws AppException;

}
