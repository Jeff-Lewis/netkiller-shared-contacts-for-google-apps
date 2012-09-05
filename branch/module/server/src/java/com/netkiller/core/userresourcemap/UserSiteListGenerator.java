package com.netkiller.core.userresourcemap;

import java.util.List;

import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;

public interface UserSiteListGenerator {
	
	public List<SiteInfo> getSiteList(String userId, DataContext dataContext) throws AppException;

}
