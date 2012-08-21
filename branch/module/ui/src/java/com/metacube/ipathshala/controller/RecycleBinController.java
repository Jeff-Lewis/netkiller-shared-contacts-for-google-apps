package com.metacube.ipathshala.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterType;
import com.metacube.ipathshala.entity.metadata.util.MetaDataUtil;
import com.metacube.ipathshala.globalfilter.Delete;
import com.metacube.ipathshala.globalfilter.GlobalFilterMap;
import com.metacube.ipathshala.manager.EntityManager;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.search.property.operator.InputFilterGroupOperatorType;
import com.metacube.ipathshala.search.property.operator.InputFilterOperatorType;
import com.metacube.ipathshala.util.KeyUtil;

/**
 * Class to control recycle bin entities
 * 
 * @author Jitender
 * 
 */
@Controller
public class RecycleBinController extends AbstractController {

	@Autowired
	private ApplicationContext applicationContext;

	/*
	 * @Autowired private AcademicYearManager academicYearManager;
	 */

	/**
	 * method to get main page for recycle bin
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/recyclebin/index.do")
	public String viewRecycleBin(Model model, HttpServletRequest request,
			HttpSession session) throws AppException {
		addToNavigationTrail("Recycle Bin", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_RECYCLEB_IN_INDEX);
		return UICommonConstants.VIEW_INDEX;

	}

	/**
	 * method to get list of deleted entities
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws AppException
	 */
	@RequestMapping("/recyclebin/getRecycleBinEntity.do")
	public @ResponseBody
	Map<String, Object> getAllDeletedEntities(HttpServletRequest request,
			HttpSession session) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, AppException {
		Map<String, Object> entityList = null;
		EntityManager entityManager = getEntityManagerFromRequest(request);
		entityList = getEntities(entityManager, session);
		return entityList;

	}

	private EntityManager getEntityManagerFromRequest(HttpServletRequest request) {
		final String ENTITY_PARAM = "entityName";
		String entityName = request.getParameter(ENTITY_PARAM).trim()
				+ "Manager";
		EntityManager entityManager = (EntityManager) applicationContext
				.getBean(entityName);
		return entityManager;
	}

	/**
	 * method to get all entities
	 * 
	 * @param entityManager
	 * @param session
	 * @return
	 * @throws AppException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getEntities(EntityManager entityManager,
			HttpSession session) throws AppException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		DataContext dataContext = getDataContext(session);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.setGroupOp(InputFilterGroupOperatorType.AND);
		List<FilterInfo.Rule> ruleList = new ArrayList<FilterInfo.Rule>();

		FilterInfo.Rule rule = new FilterInfo.Rule();
		rule.setField("isDeleted");
		rule.setData("y");
		rule.setOp(InputFilterOperatorType.EQUAL);
		ruleList.add(rule);
		filterInfo.setRules(ruleList);
		GlobalFilterMap globalFilterMap = dataContext.getGlobalFilterMap();
		globalFilterMap.getGlobalFilterHashMap()
				.remove(GlobalFilterType.DELETE);
		SearchResult searchResult = entityManager.doSearch(filterInfo,
				dataContext);
		globalFilterMap.getGlobalFilterHashMap().put(GlobalFilterType.DELETE,
				new Delete(false));
		List<Object> objectList = searchResult.getResultObjects();

		int counter = 0;
		List<Map<String, Object>> deletedEntities = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<Object> iterator = objectList.iterator(); iterator
				.hasNext();) {
			Object currentObject = (Object) iterator.next();
			String businessKey = MetaDataUtil.getEntityBusinessKeyValue(
					currentObject, entityManager.getEntityMetaData());
			Map<String, Object> innerMap = new HashMap<String, Object>();
			innerMap.put("key", counter + 1);
			List<Object> entityList = new ArrayList<Object>();
			Key key = KeyUtil.geyKeyFromObject(currentObject);
			entityList.add(key.getId());
			entityList.add(businessKey);
			innerMap.put("cell", entityList);
			counter++;
			deletedEntities.add(innerMap);
		}
		map.put("total", ((int) counter / 10) + 1);
		map.put("page", 1);
		map.put("records", counter);
		map.put("rows", deletedEntities);
		return map;

	}

	/**
	 * method to get current data context
	 * 
	 * @param session
	 * @return
	 * @throws AppException
	 */
	private DataContext getDataContext(HttpSession session) throws AppException {
		DataContext dataContext = null;
		/*
		 * if (session.getAttribute(UICommonConstants.DATA_CONTEXT) == null) {
		 * dataContext = DataContextUtil
		 * .getDataContextByAcademicYear(academicYearManager
		 * .getDefaultAcademicYear());
		 * session.setAttribute(UICommonConstants.DATA_CONTEXT, dataContext); }
		 * else { dataContext = (DataContext) session
		 * .getAttribute(UICommonConstants.DATA_CONTEXT); }
		 */
		return dataContext;
	}

	@RequestMapping("/restoreEntity.do")
	public @ResponseBody
	void restoreEntity(HttpServletRequest request, HttpSession session)
			throws AppException, SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final String ENTITY_KEY = "entityKey";
		EntityManager entityManager = getEntityManagerFromRequest(request);
		long longKey = Long.parseLong(request.getParameter(ENTITY_KEY));
		Key key = KeyFactory.createKey(entityManager.getEntityMetaData()
				.getEntityClass().getSimpleName(), longKey);
		Object entity = entityManager.getById(key);
		entityManager.restoreObject(entity);
	}

	@RequestMapping("/restoreAllEntities.do")
	public @ResponseBody
	void restoreAllEntities(HttpServletRequest request, HttpSession session)
			throws AppException, SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final String ENTITY_KEYS = "enitityKeys";
		EntityManager entityManager = getEntityManagerFromRequest(request);
		List<Long> keyLongList = getEntityKeys(request
				.getParameter(ENTITY_KEYS));
		List<Key> keyList = new ArrayList<Key>();
		for (Long longKey : keyLongList) {
			Key key = KeyFactory.createKey(entityManager.getEntityMetaData()
					.getEntityClass().getSimpleName(), longKey);
			Object entity = entityManager.getById(key);
			entityManager.restoreObject(entity);
		}

	}

	private List<Long> getEntityKeys(String keysStr) {
		keysStr = keysStr.substring(10, keysStr.length() - 3);
		List<Long> keyList = new ArrayList<Long>();
		Scanner sc = new Scanner(keysStr).useDelimiter(",");
		while (sc.hasNextLong()) {
			keyList.add(sc.nextLong());
		}

		return keyList;
	}

}