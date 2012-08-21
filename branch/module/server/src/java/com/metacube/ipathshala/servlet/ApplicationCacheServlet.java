package com.metacube.ipathshala.servlet;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.metacube.ipathshala.ServerCommonConstant;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.service.ValueService;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;


public class ApplicationCacheServlet extends HttpServlet{
	public void init(ServletConfig servletContext) throws ServletException {
		//Register the Cache so that it could be retrieved later using CacheManager
		CacheManager cacheManager = CacheManager.getInstance();
		try {
			Cache valueCache = cacheManager.getCacheFactory().createCache(new HashMap<Object,Value>());
			Cache valueListCache = cacheManager.getCacheFactory().createCache(new HashMap<String, List>());
			
			cacheManager.registerCache(ServerCommonConstant.STR_VALUE_CACHE, valueCache);
			cacheManager.registerCache(ServerCommonConstant.STR_VALUE_LIST_CACHE, valueListCache);
		} catch (CacheException e) {
			e.printStackTrace();
		}
		
	}
}
