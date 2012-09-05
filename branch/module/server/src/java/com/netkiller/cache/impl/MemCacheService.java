package com.netkiller.cache.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.Key;
import com.netkiller.ServerCommonConstant;
import com.netkiller.cache.AppCache;
import com.netkiller.cache.AppCacheConfig;
import com.netkiller.cache.AppCacheService;
import com.netkiller.cache.EntityCacheValue;
import com.netkiller.core.AppException;
import com.netkiller.entity.Value;
import com.netkiller.entity.Workflow;
import com.netkiller.search.SearchService;
import com.netkiller.service.WorkflowService;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CacheUtil;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.impl.context.LoadCacheWorkflowContext;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

@Component("MemCacheService")
public class MemCacheService implements AppCacheService {

	private static final AppLogger log = AppLogger.getLogger(MemCacheService.class);

	@Autowired
	private AppCacheConfig appCacheConfig;

	@Autowired
	private WorkflowService workflowService;

	@Override
	public AppCache getEntityCache(String cacheName) {
		// return MemCache.getInstance(cacheName);
		return appCacheConfig.getActiveEntityCacheMap().get(cacheName);
	}

	@Override
	public AppCache getKeyCache() {
		// return MemCache.getInstance(cacheName);
		return appCacheConfig.getKeyCache();
	}

	@Override
	public void registerKeyCache() throws AppException {
		CacheManager cacheManager = CacheUtil.getCacheManger();
		try {
			Cache cache = cacheManager.getCacheFactory().createCache(new HashMap<Key, EntityCacheValue>());
			cacheManager.registerCache("Key", cache);
			appCacheConfig.getKeyCache().initializeCache();
		} catch (CacheException cacheException) {
			String message = "Unable to register Cache";
			log.error(message);
			throw new AppException(message, cacheException);
		}
	}

	@Override
	public void registerCache(String cacheName) throws AppException {
		CacheManager cacheManager = CacheUtil.getCacheManger();
		try {
			Cache cache = cacheManager.getCacheFactory().createCache(new HashMap<Key, EntityCacheValue>());
			cacheManager.registerCache(cacheName, cache);
			appCacheConfig.getActiveEntityCacheMap().get(cacheName).initializeCache();
		} catch (CacheException cacheException) {
			String message = "Unable to register Cache";
			log.error(message);
			throw new AppException(message, cacheException);
		}

	}

	@Override
	public void clearCache(String cacheName) {
		AppCache cache = appCacheConfig.getActiveEntityCacheMap().get(cacheName);
		if (cache != null && cache.isCacheIntialized())
			cache.clear();
	}

	@Override
	public void initialize() throws AppException {

		this.registerKeyCache();
		if (appCacheConfig.getActiveEntityCacheMap() == null) {
			appCacheConfig.setActiveEntityCacheMap(appCacheConfig.getEntityCacheMap());
		}
		Map<String, AppCache> entityCacheMap = appCacheConfig.getActiveEntityCacheMap();

		for (String entityName : entityCacheMap.keySet()) {
			this.registerCache(entityName);
		}
		Workflow workflow = createLoadCacheWorkflow();
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}

	}

	@Override
	public void clearAllCache() {
		System.out.println("Cache is removed");
		appCacheConfig.setCacheEnabled(false);
		if (appCacheConfig.getActiveEntityCacheMap() != null) {
			for (String entityName : appCacheConfig.getActiveEntityCacheMap().keySet()) {
				this.clearCache(entityName);
			}
			AppCache keyCache = this.getKeyCache();
			if (keyCache != null && keyCache.isCacheIntialized()) {
				keyCache.clear();
			}

		}
	}

	private Workflow createLoadCacheWorkflow() throws AppException {
		LoadCacheWorkflowContext workflowContext = new LoadCacheWorkflowContext();
		WorkflowInfo info = new WorkflowInfo("loadCacheWorkflowProcessor");
		info.setIsNewWorkflow(true);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName(/* info.getWorkflowName() */"Load all Entity Cache and Key Cache");
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow.setContext(workflowContext);
		workflowContext.setWorkflowInfo(info);
		workflow = workflowService.createWorkflow(workflow);
		return workflow;
	}

}
