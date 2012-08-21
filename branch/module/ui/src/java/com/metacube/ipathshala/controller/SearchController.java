package com.metacube.ipathshala.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData.ColumnType;
import com.metacube.ipathshala.entity.metadata.ColumnRelationShipMetaData;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.util.MetaDataUtil;
import com.metacube.ipathshala.manager.EntityManager;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.search.property.operator.InputFilterGroupOperatorType;
import com.metacube.ipathshala.search.property.operator.InputFilterOperatorType;
import com.metacube.ipathshala.security.AppUser;
import com.metacube.ipathshala.security.impl.GoogleUserService;
import com.metacube.ipathshala.service.googleservice.GoogleSitesService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.UserUtil;

/**
 * @author sparakh
 * 
 */

@Controller
public class SearchController {
	private static ObjectMapper mapper = new ObjectMapper();
	private static final AppLogger log = AppLogger
			.getLogger(SearchController.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private GoogleUserService userService;

	/*@Autowired
	private AcademicYearManager academicYearManager;*/

	@Autowired
	private GoogleSitesService siteService;
	
	/*@Autowired
	private TeacherManager teacherManager;*/
	
	/*@Autowired
	private ClassSubjectTeacherManager classSubjectTeacherManager;*/

	/*@Autowired
	private MyClassManager myClassManager;*/

	@RequestMapping("/getSearchedEntities.do")
	public @ResponseBody
	List<Map<String, String>> searchEntities(HttpServletRequest request,
			HttpSession session) throws AppException {
		try {
			String searchTerm = request.getParameter("term");
			String Entity = request.getParameter("Entity");
			EntityManager entityManager = (EntityManager) applicationContext
					.getBean(Entity + "Manager");
			EntityMetaData entityMetaData = entityManager.getEntityMetaData();
			FilterInfo filterInfo = new FilterInfo();
			List<FilterInfo.Rule> ruleList = new ArrayList<FilterInfo.Rule>();
			FilterInfo.Rule termFilterRule = null;
			List<String> keyList = new ArrayList<String>();

			if (StringUtils.isWhitespace(searchTerm)) {
				// filter nothing return all results
				searchTerm = "";
			} else if (searchTerm.charAt(0) == ' ') {
				int index = 0;
				while (searchTerm.charAt(index) == ' ') {
					index++;
				}
				searchTerm = searchTerm.substring(index);
			}
			if (searchTerm != "" && searchTerm != null) {
				termFilterRule = new FilterInfo.Rule();
				termFilterRule.setField(entityMetaData.getDefaultSearchColumn()
						.getColumnName());
				termFilterRule.setData(searchTerm);
				termFilterRule.setOp(InputFilterOperatorType.STARTS_WITH);
				ruleList.add(termFilterRule);
			}

			String jsonSearchCriteriaString = request.getParameter("filters");

			List<FilterInfo.Rule> ruleListwithFilters = parseFilterRulesJSON(jsonSearchCriteriaString);
			if (termFilterRule != null)
				ruleListwithFilters.add(termFilterRule);
			filterInfo.setRules(ruleListwithFilters);
			filterInfo.setGroupOp(InputFilterGroupOperatorType.AND);
			SearchResult searchResult = entityManager.doSearch(filterInfo,
					(DataContext) session.getAttribute("dataContext"));
			List<Object> searchedObjects = searchResult.getResultObjects();

			List<Map<String, String>> values = new ArrayList<Map<String, String>>();

			for (Iterator<Object> iterator = searchedObjects.iterator(); iterator
					.hasNext();) {

				Map<String, String> valueMap = new LinkedHashMap<String, String>();
				Object object = (Object) iterator.next();
				String propertyValue = null;
				try {
					propertyValue = BeanUtils.getProperty(object, "active");
				} catch (NoSuchMethodException e) {
					// log.error("Active property not found");
				}
				if ((propertyValue != null && propertyValue.equals("true"))
						|| propertyValue == null) {
					String key = "{" + BeanUtils.getProperty(object, "key.id")
							+ "}";
					if (!keyList.contains(key)) {
						keyList.add(key);
						String businessKeyValue = MetaDataUtil
								.getEntityBusinessKeyValue(object,
										entityMetaData);
						String label = MetaDataUtil.getEntityLabelValue(object,
								entityMetaData);
						valueMap.put("value", label);
						valueMap.put("label", label);
						valueMap.put("desc", businessKeyValue + key);
						values.add(valueMap);
					}
				}
			}
			log.debug("Searched Entities By Term:" + searchTerm);
			return values;
		} catch (IllegalAccessException e) {
			String message = "Illegal State In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (InvocationTargetException e) {
			String message = "Invalid Invocation Target Exception In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (NoSuchMethodException e) {
			String message = "No Such Method Exception In Edit SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		}

	}

	@RequestMapping("/getEntityFieldValuesByKey.do")
	public @ResponseBody
	String entityFieldValuesByKey(HttpServletRequest request,
			HttpSession session) throws AppException {
		try {
			String fieldName = null;
			String studentKeyLong = null;
			String entity = null;
			if (request.getParameterMap().containsKey("field"))
				fieldName = request.getParameter("field");
			if (request.getParameterMap().containsKey("key"))
				studentKeyLong = request.getParameter("key");
			if (request.getParameterMap().containsKey("Entity"))
				entity = request.getParameter("Entity");
			EntityManager entityManager = (EntityManager) applicationContext
					.getBean(entity + "Manager");
			FilterInfo filterInfo = new FilterInfo();
			List<FilterInfo.Rule> ruleList = new ArrayList<FilterInfo.Rule>();

			FilterInfo.Rule rule = new FilterInfo.Rule();

			rule.setField("key");
			rule.setData(studentKeyLong);
			rule.setOp(InputFilterOperatorType.EQUAL);
			ruleList.add(rule);

			filterInfo.setRules(ruleList);
			filterInfo.setGroupOp(InputFilterGroupOperatorType.AND);

			SearchResult searchResult = entityManager.doSearch(filterInfo,
					(DataContext) session.getAttribute("dataContext"));

			List<Object> searchedObjects = searchResult.getResultObjects();
			String fieldvalue = BeanUtils.getProperty(searchedObjects.get(0),
					"classKey.id");
			return fieldvalue;
		} catch (IllegalAccessException e) {
			String message = "Illegal State In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (InvocationTargetException e) {
			String message = "Invalid Invocation Target Exception In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (NoSuchMethodException e) {
			String message = "No Such Method Exception In Edit SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		}

	}

	@RequestMapping("/getSearchedEntitiesByFieldsCombobox.do")
	public @ResponseBody
	List<Map<String, String>> searchEntitiesByFieldsCombobox(
			HttpServletRequest request, HttpSession session)
			throws AppException {

		String Entity = request.getParameter("Entity");
		String searchTerm = request.getParameter("term");
		if (StringUtils.isWhitespace(searchTerm)) {
			// filter nothing return all results
			searchTerm = "";
		} else if (searchTerm != null && searchTerm.charAt(0) == ' ') {
			int index = 0;
			while (searchTerm.charAt(index) == ' ') {
				index++;
			}
			searchTerm = searchTerm.substring(index);
		}
		String field = request.getParameter("field");
		EntityManager entityManager = (EntityManager) applicationContext
				.getBean(Entity + "Manager");
		EntityMetaData entityMetaData = entityManager.getEntityMetaData();
		FilterInfo filterInfo = new FilterInfo();
		String jsonSearchCriteriaString = request.getParameter("filters");
		List<FilterInfo.Rule> ruleListwithFilters = parseFilterRulesJSON(jsonSearchCriteriaString);
		filterInfo.setRules(ruleListwithFilters);
		filterInfo.setGroupOp(InputFilterGroupOperatorType.AND);
		SearchResult searchResult = entityManager.doSearch(filterInfo,
				(DataContext) session.getAttribute("dataContext"));
		List<Object> results = searchResult.getResultObjects();

		List<Map<String, String>> searchedEntitiesList = new ArrayList<Map<String, String>>();
		Set<Key> relationshipKeySet = new HashSet<Key>();
		for (Iterator<Object> iterator = results.iterator(); iterator.hasNext();) {

			Object object = (Object) iterator.next();

			String valueMapKey = null;
			String valueMapValue = null;
			String label = null;
			Map<String, String> entitiesMap = new HashMap<String, String>();
			try {
				String longkey = null;
				String businessKeyValue = null;

				ColumnMetaData columnMetaData = entityMetaData
						.getColumnMetaData(field);
				ColumnType columnType = columnMetaData.getColumnType();
				if (columnType == ColumnType.Key) {
					try {
						longkey = BeanUtils.getProperty(object, field + ".id");
					} catch (Exception e) {
						String message = "Cannot find property";
						log.debug(message);
						continue;
					}
					Key key = null;
					ColumnRelationShipMetaData relationShip = columnMetaData
							.getRelationShip();
					if (relationShip != null) {
						// for relationship keys.
						String relatedEntityName = relationShip
								.getRelatedEntityMetaData().getEntityName();
						relatedEntityName = Character.toString(Character
								.toLowerCase(relatedEntityName.charAt(0)))
								+ relatedEntityName.substring(1,
										relatedEntityName.length());
						EntityManager relatedEntityManager = (EntityManager) applicationContext
								.getBean(relatedEntityName + "Manager");
						key = KeyFactory.createKey(relationShip
								.getRelatedEntityMetaData().getEntityName(),
								Long.parseLong(longkey));
						if (!relationshipKeySet.contains(key)) {
							Object relatedEntity = relatedEntityManager
									.getById(key);
							String propertyValue = null;
							try {
								propertyValue = BeanUtils.getProperty(
										relatedEntity, "active");
							} catch (NoSuchMethodException e) {
								// log.error("Active property not found");
								System.out
										.println("1. Active property not found");

							}
							if (propertyValue != null
									&& propertyValue.equals("false")) {
								continue;
							}
							businessKeyValue = MetaDataUtil
									.getEntityLabelValue(relatedEntity,
											relationShip
													.getRelatedEntityMetaData());
							label = MetaDataUtil
									.getEntityDefaultSearchColumnValue(
											relatedEntity, relationShip
													.getRelatedEntityMetaData());
						} else
							continue;
					} else {
						String propertyValue = null;
						try {
							propertyValue = BeanUtils.getProperty(object,
									"active");
						} catch (NoSuchMethodException e) {
							// log.error("Active property not found");
							System.out.println("2. Active property not found");

						}
						if (propertyValue != null
								&& propertyValue.equals("false")) {
							continue;
						}

						key = KeyFactory.createKey(
								entityMetaData.getEntityName(),
								Long.parseLong(longkey));
						businessKeyValue = MetaDataUtil.getEntityLabelValue(
								object, entityMetaData);
						label = MetaDataUtil.getEntityDefaultSearchColumnValue(
								object, entityMetaData);
					}

					valueMapKey = longkey;
					valueMapValue = businessKeyValue;
				} else {
					String propertyValue = null;
					try {
						propertyValue = BeanUtils.getProperty(object, "active");
					} catch (NoSuchMethodException e) {
						// log.error("Active property not found");
						System.out.println("3. Active property not found");
					}
					if (propertyValue != null && propertyValue.equals("false")) {
						continue;
					}

					valueMapKey = BeanUtils.getProperty(object, field);
					valueMapValue = valueMapKey;
					label = valueMapKey;
				}
			} catch (IllegalAccessException e) {
				String message = "Illegal State In SearchController";
				log.error(message, e);
				throw new AppException(message, e);
			} catch (InvocationTargetException e) {
				String message = "Invalid Invocation Target Exception In SearchController";
				log.error(message, e);
				throw new AppException(message, e);
			} catch (NoSuchMethodException e) {
				String message = "No Such Method Exception In Edit SearchController";
				log.error(message, e);
				throw new AppException(message, e);
			}
			String key = "{" + valueMapKey + "}";

			if ((searchTerm != null && label.startsWith(searchTerm
					.toUpperCase())) || searchTerm == null || searchTerm == "") {

				entitiesMap.put("value", label);
				entitiesMap.put("label", label);
				entitiesMap.put("desc", valueMapValue + key);
				entitiesMap.put("key", valueMapKey);
				if (!searchedEntitiesList.contains(entitiesMap)) {
					searchedEntitiesList.add(entitiesMap);
				}

			}
		}
		return searchedEntitiesList;
	}

	@RequestMapping("/isUserIdAvailable.do")
	public @ResponseBody
	boolean isUserIdAvailable(HttpServletRequest request) throws AppException {
		String userId = request.getParameter("userId");
		return userService.isAppUser(userId);
	}

	@RequestMapping("/getUserIdSuggestions.do")
	public @ResponseBody
	Set<String> getUserIdSuggestions(HttpServletRequest request)
			throws AppException {
		int numberOfUserIds = 5;
		String userId = request.getParameter("userId").toLowerCase();
		String firstName = request.getParameter("firstName").toLowerCase();
		String lastName = request.getParameter("lastName").toLowerCase();

		List<String> userIds = new ArrayList<String>();
		Set<String> availableUserIds = new HashSet<String>();
		userIds.add(userId);
		if (!(StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName))) {
			userIds.addAll(UserUtil.getRelatedUserIdSuggestions(firstName,
					lastName));
		}
		availableUserIds.addAll(getAvailableUserIds(userIds, numberOfUserIds));
		while (availableUserIds.size() != numberOfUserIds) {
			userIds = (List<String>) UserUtil.getRandomUserIdSuggestions(
					firstName, lastName, userId);
			availableUserIds.addAll(getAvailableUserIds(userIds,
					numberOfUserIds - availableUserIds.size()));
		}
		return availableUserIds;
	}

	@RequestMapping("/getListOfValues.do")
	public String getListOfValues(HttpServletRequest request, Model model) {
		model.addAttribute("setName", request.getParameter("setName"));
		model.addAttribute("name", request.getParameter("name"));
		return UICommonConstants.VIEW_LIST_OF_VALUES;
	}

	List<FilterInfo.Rule> parseFilterRulesJSON(String filtersJSON)
			throws AppException {
		FilterInfo fieldFilter = new FilterInfo();
		if (filtersJSON != null && !filtersJSON.isEmpty()) {
			try {
				fieldFilter = mapper.readValue(filtersJSON, FilterInfo.class);

			} catch (JsonMappingException e) {
				String message = "Illegal JSON mapping";
				log.error(message, e);
				throw new AppException(message);
			} catch (JsonParseException e) {
				String message = "Unable to parse JSON String";
				log.error(message, e);
				throw new AppException(message);
			} catch (IOException e) {
				String message = "Unable to read JSON String";
				log.error(message, e);
				throw new AppException(message);
			}
		}
		return fieldFilter.getRules();

	}

	private List<String> getAvailableUserIds(List<String> userIds,
			int numberOfUserIds) throws AppException {
		List<String> availableUserIds = new ArrayList<String>();
		for (int i = 0; availableUserIds.size() < numberOfUserIds
				&& i < userIds.size(); i++) {
			String user = userIds.get(i);
			if (UserUtil.isValidUserId(user) && !userService.isAppUser(user)) {
				availableUserIds.add(user);
			}
		}
		return availableUserIds;
	}

	@RequestMapping("assignDomainPermission.do")
	@ResponseBody
	public boolean assignPermissionsToClass() throws AppException {
		boolean success = true;
		/*DataContext dataContext = DataContextUtil
				.getDataContextByAcademicYear(academicYearManager
						.getDefaultAcademicYear());
		List<MyClass> classes = (List<MyClass>) myClassManager
				.getAllGlobalFilteredClasses(dataContext);
		for (MyClass myclass : classes) {
			if (!StringUtils.isBlank(myclass.getClassSiteName())) {
				siteService.addDomainRole(GoogleSiteRoleType.READER,
						myclass.getClassSiteName());
			}
		}*/
		return success;
	}
	
	@RequestMapping("/getSearchedMyClassEntities.do")
	public @ResponseBody
	List<Map<String, String>> searchMyClassEntities(HttpServletRequest request,
			HttpSession session) throws AppException {
		return null;
		/*try {
			String searchTerm = request.getParameter("term");
			String Entity = request.getParameter("Entity");
			AppUser user = (AppUser) request.getSession().getAttribute(
					AppUser.APPUSER_SESSION_VAR);
			EntityManager entityManager = (EntityManager) applicationContext
					.getBean(Entity + "Manager");
			EntityMetaData entityMetaData = entityManager.getEntityMetaData();
			FilterInfo filterInfo = new FilterInfo();
			List<FilterInfo.Rule> ruleList = new ArrayList<FilterInfo.Rule>();
			FilterInfo.Rule termFilterRule = null;
			List<String> keyList = new ArrayList<String>();

			if (StringUtils.isWhitespace(searchTerm)) {
				// filter nothing return all results
				searchTerm = "";
			} else if (searchTerm.charAt(0) == ' ') {
				int index = 0;
				while (searchTerm.charAt(index) == ' ') {
					index++;
				}
				searchTerm = searchTerm.substring(index);
			}
			if (searchTerm != "" && searchTerm != null) {
				termFilterRule = new FilterInfo.Rule();
				termFilterRule.setField(entityMetaData.getDefaultSearchColumn()
						.getColumnName());
				termFilterRule.setData(searchTerm);
				termFilterRule.setOp(InputFilterOperatorType.STARTS_WITH);
				ruleList.add(termFilterRule);
			}

			String jsonSearchCriteriaString = request.getParameter("filters");

			List<FilterInfo.Rule> ruleListwithFilters = parseFilterRulesJSON(jsonSearchCriteriaString);
			if (termFilterRule != null)
				ruleListwithFilters.add(termFilterRule);
			filterInfo.setRules(ruleListwithFilters);
			filterInfo.setGroupOp(InputFilterGroupOperatorType.AND);
			SearchResult searchResult = entityManager.doSearch(filterInfo,
					(DataContext) session.getAttribute("dataContext"));
			List<Object> searchedObjects = searchResult.getResultObjects();
			List<Object> classes = new ArrayList<Object>();
			Teacher teacher=teacherManager.getByUserId(user.getUserId());
			Collection classKeys=classSubjectTeacherManager.getClassKey(teacher.getKey());
			List<Long> classIds=new ArrayList<Long>();
			
			//filter for teachers classes
			if(user.getDefaultAppGroup().getGroupName().equalsIgnoreCase("ipath_app_group_teacher")){
				for(Object key:classKeys){
					classIds.add(((Key)key).getId());
				}
				for(Object object:searchedObjects){
					if(classIds.contains(((MyClass)object).getKey().getId())){
						classes.add(object);
					}
				}
			}
			
			List<Map<String, String>> values = new ArrayList<Map<String, String>>();

			for (Iterator<Object> iterator = classes.iterator(); iterator
					.hasNext();) {

				Map<String, String> valueMap = new LinkedHashMap<String, String>();
				Object object = (Object) iterator.next();
				String propertyValue = null;
				try {
					propertyValue = BeanUtils.getProperty(object, "active");
				} catch (NoSuchMethodException e) {
					// log.error("Active property not found");
				}
				if ((propertyValue != null && propertyValue.equals("true"))
						|| propertyValue == null) {
					String key = "{" + BeanUtils.getProperty(object, "key.id")
							+ "}";
					if (!keyList.contains(key)) {
						keyList.add(key);
						String businessKeyValue = MetaDataUtil
								.getEntityBusinessKeyValue(object,
										entityMetaData);
						String label = MetaDataUtil.getEntityLabelValue(object,
								entityMetaData);
						valueMap.put("value", label);
						valueMap.put("label", label);
						valueMap.put("desc", businessKeyValue + key);
						values.add(valueMap);
					}
				}
			}
			log.debug("Searched Entities By Term:" + searchTerm);
			return values;
		} catch (IllegalAccessException e) {
			String message = "Illegal State In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (InvocationTargetException e) {
			String message = "Invalid Invocation Target Exception In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (NoSuchMethodException e) {
			String message = "No Such Method Exception In Edit SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		}
*/
	}
	
	@RequestMapping("/getSearchedValueEntities.do")
	public @ResponseBody
	List<Map<String, String>> searchValueEntities(HttpServletRequest request,
			HttpSession session) throws AppException {
		try {
			String searchTerm = request.getParameter("term");
			String Entity = request.getParameter("Entity");
			AppUser user = (AppUser) request.getSession().getAttribute(
					AppUser.APPUSER_SESSION_VAR);
			EntityManager entityManager = (EntityManager) applicationContext
					.getBean(Entity + "Manager");
			EntityMetaData entityMetaData = entityManager.getEntityMetaData();
			FilterInfo filterInfo = new FilterInfo();
			List<FilterInfo.Rule> ruleList = new ArrayList<FilterInfo.Rule>();
			FilterInfo.Rule termFilterRule = null;
			List<String> keyList = new ArrayList<String>();

			if (StringUtils.isWhitespace(searchTerm)) {
				// filter nothing return all results
				searchTerm = "";
			} else if (searchTerm.charAt(0) == ' ') {
				int index = 0;
				while (searchTerm.charAt(index) == ' ') {
					index++;
				}
				searchTerm = searchTerm.substring(index);
			}
			if (searchTerm != "" && searchTerm != null) {
				termFilterRule = new FilterInfo.Rule();
				termFilterRule.setField(entityMetaData.getDefaultSearchColumn()
						.getColumnName());
				termFilterRule.setData(searchTerm);
				termFilterRule.setOp(InputFilterOperatorType.STARTS_WITH);
				ruleList.add(termFilterRule);
			}

			String jsonSearchCriteriaString = request.getParameter("filters");

			List<FilterInfo.Rule> ruleListwithFilters = parseFilterRulesJSON(jsonSearchCriteriaString);
			if (termFilterRule != null)
				ruleListwithFilters.add(termFilterRule);
			filterInfo.setRules(ruleListwithFilters);
			filterInfo.setGroupOp(InputFilterGroupOperatorType.AND);
			SearchResult searchResult = entityManager.doSearch(filterInfo,
					(DataContext) session.getAttribute("dataContext"));
			List<Object> searchedObjects = searchResult.getResultObjects();
			//filter for report type values
			List<Object> recordTypeValues = new ArrayList<Object>();
			for(Object object : searchedObjects){
				Value value = (Value) object;
				if(value.getSet().getSetName().equalsIgnoreCase("ReportCardType")){
					recordTypeValues.add(value);
				}
			}
			
			List<Map<String, String>> values = new ArrayList<Map<String, String>>();

			for (Iterator<Object> iterator = recordTypeValues.iterator(); iterator
					.hasNext();) {

				Map<String, String> valueMap = new LinkedHashMap<String, String>();
				Object object = (Object) iterator.next();
				String propertyValue = null;
				try {
					propertyValue = BeanUtils.getProperty(object, "active");
				} catch (NoSuchMethodException e) {
					// log.error("Active property not found");
				}
				if ((propertyValue != null && propertyValue.equals("true"))
						|| propertyValue == null) {
					String key = "{" + BeanUtils.getProperty(object, "key.id")
							+ "}";
					if (!keyList.contains(key)) {
						keyList.add(key);
						String businessKeyValue = MetaDataUtil
								.getEntityBusinessKeyValue(object,
										entityMetaData);
						String label = MetaDataUtil.getEntityLabelValue(object,
								entityMetaData);
						valueMap.put("value", label);
						valueMap.put("label", label);
						valueMap.put("desc", businessKeyValue + key);
						values.add(valueMap);
					}
				}
			}
			log.debug("Searched Entities By Term:" + searchTerm);
			return values;
		} catch (IllegalAccessException e) {
			String message = "Illegal State In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (InvocationTargetException e) {
			String message = "Invalid Invocation Target Exception In SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (NoSuchMethodException e) {
			String message = "No Such Method Exception In Edit SearchController";
			log.error(message, e);
			throw new AppException(message, e);
		}

	}
}
