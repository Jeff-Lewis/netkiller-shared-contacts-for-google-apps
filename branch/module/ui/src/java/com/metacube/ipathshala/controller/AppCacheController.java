package com.metacube.ipathshala.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.cache.AppCache;
import com.metacube.ipathshala.cache.AppCacheConfig;
import com.metacube.ipathshala.cache.AppCacheManager;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.security.AppUser;
import com.metacube.ipathshala.util.AppLogger;

/**
 * @author sparakh
 * 
 */
@Controller
public class AppCacheController extends AbstractController {

	private static final AppLogger log = AppLogger.getLogger(AppCacheController.class);

	@Autowired
	private AppCacheManager cacheManger;
	
	@Autowired
	private AppCacheConfig cacheConfig;

	@RequestMapping("/cache/reloadcache.do")
	public String loadCache(Model model, HttpServletRequest request, HttpSession session)
			throws AppException {
		addToNavigationTrail("EvaluationScheme", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW, "admin/cacheHome.jsp");
		String activeEntityList = request.getParameter("newActiveEntitiesList");
		if(!StringUtils.isBlank(activeEntityList))	{
			List<String> newActiveEntityList =Arrays.asList(activeEntityList.split(","));
			Map<String, AppCache> activeEntityCacheMap = new HashMap<String, AppCache>();
			for(String entity:newActiveEntityList)	{
				activeEntityCacheMap.put(entity, cacheConfig.getEntityCacheMap().get(entity));
			}
			cacheConfig.setActiveEntityCacheMap(activeEntityCacheMap);
		}
		else	{
			cacheConfig.setActiveEntityCacheMap(new HashMap<String, AppCache>());
		}
		
		cacheManger.initialize();
		
		model.addAttribute("entities",cacheConfig.getEntityCacheMap().keySet());
		Set<String> activeEntitySet = null;
		String activeEntitySetString = null;
		if(cacheConfig.getActiveEntityCacheMap()!=null)	{
			activeEntitySet =cacheConfig.getActiveEntityCacheMap().keySet() ;
			activeEntitySetString = "";
			for(String entity:activeEntitySet)	{
				activeEntitySetString = activeEntitySetString+ entity+",";
			}
			if(!StringUtils.isBlank(activeEntitySetString))
			activeEntitySetString = activeEntitySetString.substring(0,activeEntitySetString.length()-1);
		}
		
		
	
		model.addAttribute("activeEntities",activeEntitySetString);
		
		
		AppUser user = (AppUser) request.getSession().getAttribute(AppUser.APPUSER_SESSION_VAR);
		if (user == null) {
			throw new AppException("User not logged in, cannot process notes");
		}
		

		return UICommonConstants.VIEW_INDEX;
	}
	
	@RequestMapping("/cache/removecache.do")
	public String clearCache(Model model, HttpServletRequest request, HttpSession session)
			throws AppException {
		addToNavigationTrail("EvaluationScheme", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW, "admin/cacheHome.jsp");
		cacheManger.clearAllCache();
		cacheConfig.setActiveEntityCacheMap(new HashMap<String, AppCache>());
		AppUser user = (AppUser) request.getSession().getAttribute(AppUser.APPUSER_SESSION_VAR);
		if (user == null) {
			throw new AppException("User not logged in, cannot process notes");
		}
		model.addAttribute("entities",cacheConfig.getEntityCacheMap().keySet());
		Set<String> activeEntitySet = null;
		String activeEntitySetString = null;
		if(cacheConfig.getActiveEntityCacheMap()!=null)	{
			activeEntitySet =cacheConfig.getActiveEntityCacheMap().keySet() ;
			activeEntitySetString = "";
			for(String entity:activeEntitySet)	{
				activeEntitySetString = activeEntitySetString+ entity+",";
			}
			if(!StringUtils.isBlank(activeEntitySetString))
			activeEntitySetString = activeEntitySetString.substring(0,activeEntitySetString.length()-1);
		}
		
		
	
		model.addAttribute("activeEntities",activeEntitySetString);

		return UICommonConstants.VIEW_INDEX;
	}
	
	@RequestMapping("/managecache.do")
	public String mangeCache(Model model, HttpServletRequest request, HttpSession session)
			throws AppException {
		addToNavigationTrail("EvaluationScheme", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW, "admin/cacheHome.jsp");
		model.addAttribute("entities",cacheConfig.getEntityCacheMap().keySet());
		Set<String> activeEntitySet = null;
		String activeEntitySetString = null;
		if(cacheConfig.getActiveEntityCacheMap()!=null)	{
			activeEntitySet =cacheConfig.getActiveEntityCacheMap().keySet() ;
			activeEntitySetString = "";
			for(String entity:activeEntitySet)	{
				activeEntitySetString = activeEntitySetString+ entity+",";
			}
			if(!StringUtils.isBlank(activeEntitySetString))
			activeEntitySetString = activeEntitySetString.substring(0,activeEntitySetString.length()-1);
		}
		
		
	
		model.addAttribute("activeEntities",activeEntitySetString);
		
		AppUser user = (AppUser) request.getSession().getAttribute(AppUser.APPUSER_SESSION_VAR);
		if (user == null) {
			throw new AppException("User not logged in, cannot process notes");
		}
		

		return UICommonConstants.VIEW_INDEX;
	}

 
}
