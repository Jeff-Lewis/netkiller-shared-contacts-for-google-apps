package com.netkiller.servlets;

import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.netkiller.workflow.WorkflowContext;

public class InitWorkflowCacheServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8561544823275649710L;

	@Override
	public void init(ServletConfig servletContext) throws ServletException {
		//Register the Cache so that it could be retrieved later using CacheManager
		CacheManager cacheManager = CacheManager.getInstance();
		
			Cache newcache = null;
			try {
				newcache = cacheManager.getCacheFactory().createCache(new HashMap<String,WorkflowContext>());
			} catch (CacheException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& + getting cahce initialized******************");
			cacheManager.registerCache("WorkflowContextCache",newcache);
		
	}

}
