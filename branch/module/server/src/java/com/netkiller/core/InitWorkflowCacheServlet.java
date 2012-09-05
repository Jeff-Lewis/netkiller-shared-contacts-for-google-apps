package com.netkiller.core;


import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.netkiller.workflow.WorkflowContext;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

/**
 * Servlet loaded on startup to register Cache
 * 
 * @author dhruvsharma
 *
 */
public class InitWorkflowCacheServlet extends HttpServlet {
	
	@Override
	public void init(ServletConfig servletContext) throws ServletException {
		//Register the Cache so that it could be retrieved later using CacheManager
		CacheManager cacheManager = CacheManager.getInstance();
		try {
			Cache newcache = cacheManager.getCacheFactory().createCache(new HashMap<String,WorkflowContext>());
			cacheManager.registerCache("WorkflowContextCache",newcache);
		} catch (CacheException e) {
			e.printStackTrace();
		}
		
	}

}
