package com.metacube.ipathshala.workflow.impl.task;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.metacube.ipathshala.cache.AppCache;
import com.metacube.ipathshala.cache.AppCacheConfig;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.manager.EntityManager;
import com.metacube.ipathshala.search.SearchCriteria;
import com.metacube.ipathshala.search.SearchRequest;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.search.SearchService;
import com.metacube.ipathshala.search.property.operator.FilterGroupOperatorType;
import com.metacube.ipathshala.util.KeyUtil;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.LoadCacheWorkflowContext;

public class LoadCacheTask extends AbstractWorkflowTask {

	@Autowired
	private SearchService searchService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AppCacheConfig cacheConfig;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		LoadCacheWorkflowContext workflowContext = ((LoadCacheWorkflowContext) context);
		Map<String, AppCache> entityCacheMap = cacheConfig.getActiveEntityCacheMap();
		AppCache keyCache = cacheConfig.getKeyCache();
		Map<String, SearchRequest> entitySearchRequestMap = new HashMap<String, SearchRequest>();
		for (String entityName : entityCacheMap.keySet()) {
			entitySearchRequestMap.put(entityName,
					createEntitySearchRequest(entityName));
		}
		for (String entityName : entityCacheMap.keySet()) {
			loadCache(entityName, entityCacheMap, keyCache,
					entitySearchRequestMap);
		}
		cacheConfig.setCacheEnabled(true);
		return context;
	}

	private void loadCache(String entityName,
			Map<String, AppCache> entityCacheMap, AppCache keyCache,
			Map<String, SearchRequest> entitySearchRequestMap) {
		AppCache entityCache = entityCacheMap.get(entityName);

		SearchResult result;
		try {
			result = searchService.doSearch(entitySearchRequestMap
					.get(entityName));

			List<Object> results = result.getResultObjects();
			List<Key> keyList = new ArrayList<Key>();
			for (Object record : results) {
				Key key = KeyUtil.geyKeyFromObject(record);
				if (key != null) {
					keyList.add(key);
				}
				entityCache.addCacheValue(record);
			}
			Map.Entry<String, List<Key>> mapEntry = new AbstractMap.SimpleEntry<String, List<Key>>(
					entityName, keyList);
			keyCache.addCacheValue(mapEntry);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private SearchRequest createEntitySearchRequest(String entityName) {
		String newEntityName = Character.toString(Character
				.toLowerCase(entityName.charAt(0)))
				+ entityName.substring(1, entityName.length());

		EntityManager entityManager = (EntityManager) applicationContext
				.getBean(newEntityName + "Manager");
		EntityMetaData entityMetaData = entityManager.getEntityMetaData();
		SearchCriteria searchCriteria = new SearchCriteria(null,
				entityMetaData.getEntityClass());
		SearchRequest request = new SearchRequest(searchCriteria, null, null,
				FilterGroupOperatorType.AND, null,SearchRequest.ResultType.Entity);
		return request;
	}

}
