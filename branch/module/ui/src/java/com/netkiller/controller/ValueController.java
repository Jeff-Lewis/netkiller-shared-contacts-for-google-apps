package com.netkiller.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.netkiller.GridRequest;
import com.netkiller.UICommonConstants;
import com.netkiller.core.AppException;
import com.netkiller.core.EntityType;
import com.netkiller.core.jaxb.value.ValueElement;
import com.netkiller.core.jaxb.value.ValueList;
import com.netkiller.entity.Set;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.util.MetaDataUtil;
import com.netkiller.manager.GlobalImportExportManager;
import com.netkiller.manager.SetManager;
import com.netkiller.manager.ValueManager;
import com.netkiller.search.SearchResult;
import com.netkiller.util.AppLogger;
import com.netkiller.util.GridRequestParser;
import com.netkiller.view.validators.EntityValidator;
import com.netkiller.vo.ImportVO;


@Controller
public class ValueController extends AbstractController {

	private static final AppLogger log = AppLogger
			.getLogger(ValueController.class);

	@Autowired
	private ValueManager valueManager;
	
	@Autowired
	private SetManager setManager;

	@Autowired
	private GlobalImportExportManager globalImportExportManager;

	@Autowired
	private EntityValidator validator;

	@Resource
	GridRequestParser gridRequestParser;

	/**
	 * Show Value tab.
	 * 
	 * @return
	 * @throws AppException 
	 */
	@RequestMapping("/value.do")
	public String showValueTab(Model model, HttpServletRequest request) throws AppException {
		log.debug("Presenting Value view.");
		addToNavigationTrail("Value", true, request, false, false);
		String setMap = "";
		Collection<Set> allSets = setManager.getAllSets();
		if(allSets!=null){
			for(Set set:allSets){
				setMap = setMap + set.getKey().getId() + ":" + set.getSetName() + ";";
			}
			if(!StringUtils.isEmpty(setMap)){	
				setMap = setMap.substring(0, setMap.length()-1);
				}
		}
		model.addAttribute("setMap",setMap);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_VALUE_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Show Value search form.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/value/searchForm.do")
	public String getSearchForm(Model model, HttpServletRequest request) {
		log.debug("Presenting Value Form view.");
		addToNavigationTrail("Search", false, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_VALUE_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Show the create Value form.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/value/createForm.do")
	public String getCreateForm(Model model, HttpServletRequest request) {
		log.debug("Presenting create-Value form view.");
		addToNavigationTrail("Create", false, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_VALUE, new Value());
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_CREATE);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CREATE_VALUE);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Show Value detail.
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws AppException
	 * @throws NumberFormatException
	 */
	@RequestMapping("/value/showDetail.do")
	public String showDetail(HttpServletRequest request, Model model)
			throws NumberFormatException, AppException {
		String valueId = request
				.getParameter(UICommonConstants.PARAM_ENTITY_ID);
		Key key = KeyFactory.createKey(Value.class.getSimpleName(), Long
				.parseLong(valueId));
		Value currentValue = (Value) valueManager.getById(key);
		populateTransientFields(currentValue);
		model.addAttribute(UICommonConstants.ATTRIB_VALUE, currentValue);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_VALUE_DETAIL);
		log.debug("Presenting Value view.");
		addToNavigationTrail(currentValue.getValue(), false,
				request, false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Populate the Value data for editing and show the update form.
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws AppException
	 * @throws NumberFormatException
	 */
	@RequestMapping("/value/editForm.do")
	public String getEditForm(HttpServletRequest request, Model model)
			throws NumberFormatException, AppException {

		String valueId = request
				.getParameter(UICommonConstants.PARAM_ENTITY_ID);
		Key k = KeyFactory.createKey(Value.class.getSimpleName(), Long
				.parseLong(valueId));
		Value currentValue = (Value) valueManager.getById(k);
		populateTransientFields(currentValue);
		model.addAttribute(UICommonConstants.ATTRIB_VALUE, currentValue);
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_EDIT);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CREATE_VALUE);
		log.debug("Presenting edit-Value form view.");
		addToNavigationTrail("Edit", false,
				request, false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * This will sends a request to update Value.
	 * 
	 * @param Value
	 * @param result
	 * @param model
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/value/update.do")
	public String update(
			@ModelAttribute(value = UICommonConstants.ATTRIB_VALUE) Value value,
			BindingResult result, Model model, HttpServletRequest request)
			throws AppException {
		validator.validate(value, result, UICommonConstants.FORM_VALUE);

		if (result.hasErrors()) {
			log
					.debug("Could not validate update Value form becuase of form errors.");
			model.addAttribute(UICommonConstants.ATTRIB_OPER,
					UICommonConstants.OPER_EDIT);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_CREATE_VALUE);
		} else {
			log.debug("Update Value data...");
			valueManager.updateValue(value);
			model.addAttribute(UICommonConstants.ATTRIB_VALUE, value);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_VALUE_DETAIL);
			removeFromNavigationTrail(request);
		}

		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * This will sends a request to delete Value.
	 * 
	 * @param request
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/value/delete.do")
	public void delete(
			@ModelAttribute(value = UICommonConstants.ATTRIB_VALUE) Value value,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		log.debug("Processing detete Value request.");
		valueManager.deleteValue(value);
		removeFromNavigationTrail(request);
		redirectToPreviousBreadcrumb(request, response);
	}

	@RequestMapping("/value/close.do")
	public void close(HttpServletRequest request, HttpServletResponse response) {
		removeFromNavigationTrail(request);
		redirectToPreviousBreadcrumb(request, response);
	}

	/**
	 * This will sends a request to create Value.
	 * 
	 * @param Value
	 * @param result
	 * @return
	 * @throws AppEx
	 * @RequestMappingception
	 */
	@RequestMapping("/value/create.do")
	public String create(
			@ModelAttribute(value = UICommonConstants.ATTRIB_VALUE) Value value,
			BindingResult result, Model model, HttpServletRequest request)
			throws AppException {
		validator.validate(value, result, "ValueForm");

		if (result.hasErrors()) {
			log
					.warn("Unable to validate Value data for creation of new Value.");
			model.addAttribute(UICommonConstants.ATTRIB_OPER,
					UICommonConstants.OPER_CREATE);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_CREATE_VALUE);
		} else {
			log.debug("New Value creation request processing.");
			valueManager.createValue(value);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_VALUE_INDEX);
			removeFromNavigationTrail(request);
		}

		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Method handles calls respective to the data needs of <i>Student</i>
	 * entity.
	 * 
	 * @param request
	 *            HttpServletRequest object
	 * @return returns the data needed in the format jqGrid understands.
	 */
	@RequestMapping("/value/data.do")
	public @ResponseBody
	Map<String, Object> searchValuesData(HttpServletRequest request)
			throws AppException {
		Map<String, Object> modalMap = new HashMap<String, Object>();

		GridRequest dataCriteria = gridRequestParser.parseDataCriteria(request);
		SearchResult searchResult = valueManager.doSearch(dataCriteria);

		List<Object> valueList = searchResult.getResultObjects();

		Long totalRecordsLong = (Long) searchResult.getTotalRecordSize();
		int totalRecords = totalRecordsLong.intValue();

		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		int id = 0;

		for (Iterator<Object> iterator = valueList.iterator(); iterator
				.hasNext();) {
			Value value = (Value) iterator.next();

			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", ++id);

			ArrayList<Object> row = new ArrayList<Object>();
			row.add(value.getKey().getId());
			row.add(value.getValue());
			row.add(value.getOrderIndex());
			row.add(value.getSet().getSetName());
			data.put("cell", row);
			rows.add(data);
		}

		int page = dataCriteria.getPaginationInfo().getPageNumber();
		int recordsPerPage = dataCriteria.getPaginationInfo()
				.getRecordsPerPage();

		modalMap.put("rows", rows);
		modalMap.put("page", rows.size() == 0 ? 0 : page);
		modalMap.put("total", (int) Math.ceil(totalRecords
				/ (recordsPerPage + 0d)));

		modalMap.put("records", totalRecords);

		return modalMap;
	}

	@RequestMapping("/value/advancedsearch.do")
	public String doAdvancedSearchForValueData(HttpServletRequest request,
			Model model) throws AppException {

		String searchText = request
				.getParameter(UICommonConstants.ADV_SEARCH_TEXT_PARAM);
		addToNavigationTrail("Value", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_ADV_SEARCH_TXT, searchText);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_VALUE_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}
	
	@RequestMapping("/value/parentsearch.do")
	@ResponseBody
	public Map<String, Object> doparentSearchForValueData(HttpServletRequest request,
			Model model) throws AppException {
		Map<String, Object> modalMap = new LinkedHashMap<String, Object>();
		String parentValueKey = request.getParameter(UICommonConstants.PARAM_ENTRY_ENTITY_ID);
		String setName = request.getParameter(UICommonConstants.PARAM_VALUE_SET_NAME);
		Set set = setManager.getBySetName(setName);
		if(set!=null && parentValueKey!=null && !parentValueKey.equalsIgnoreCase("none")){
			Key key = KeyFactory.createKey(Value.class.getSimpleName(),	Long.parseLong(parentValueKey));		
			Collection<Value> values = valueManager.getValueBySetKeyandParentValuekey(set, key);			
			for(Value value : values){
				modalMap.put(String.valueOf(value.getKey().getId()), value.getValue());
			}				
		}else if(set!=null){
			Collection<Value> values = valueManager.getValueBySetKey(set);			
			for(Value value : values){
				modalMap.put(String.valueOf(value.getKey().getId()), value.getValue());
			}	
		}
		
		return modalMap;
	}
	
	@RequestMapping("/admin/parentvaluesearch.do")
	@ResponseBody
	public Map<String, Object> doparentSearchForValueDataForAdmin(HttpServletRequest request,
			Model model) throws AppException {
		Map<String, Object> modalMap = new LinkedHashMap<String, Object>();
		String parentValueKey = request.getParameter(UICommonConstants.PARAM_ENTRY_ENTITY_ID);
		String setName = request.getParameter(UICommonConstants.PARAM_VALUE_SET_NAME);
		Set set = setManager.getBySetName(setName);
		if(set!=null && parentValueKey!=null && !parentValueKey.equalsIgnoreCase("none")){
			Key key = KeyFactory.createKey(Value.class.getSimpleName(),	Long.parseLong(parentValueKey));		
			Collection<Value> values = valueManager.getValueBySetKeyandParentValuekey(set, key);			
			for(Value value : values){
				modalMap.put(String.valueOf(value.getKey().getId()), value.getValue());
			}				
		}else if(set!=null){
			Collection<Value> values = valueManager.getValueBySetKey(set);			
			for(Value value : values){
				modalMap.put(String.valueOf(value.getKey().getId()), value.getValue());
			}	
		}
		
		return modalMap;
	}

	/**
	 * @return ValueManager
	 */
	public ValueManager getValueManager() {
		return valueManager;
	}

	/**
	 * ValueManager to Value
	 * 
	 * @param ValueManager
	 */
	public void setValueManager(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	/**
	 * @return validator EntityValidator handle
	 */
	public EntityValidator getValidator() {
		return validator;
	}

	/**
	 * @param validator
	 *            EntityValidator to Value
	 */
	public void setValidator(EntityValidator validator) {
		this.validator = validator;
	}

	@ModelAttribute
	public void preprocessValue(
			@ModelAttribute(value = UICommonConstants.ATTRIB_VALUE) Value value,
			HttpServletRequest request) {
		Key key = null;
		String setKeyString = value.getSetKeyString();
		String parentValueKeyString = value.getParentValueKeyString();
		if (value != null) {
			if (request.getParameterMap().containsKey(
					UICommonConstants.PARAM_ENTITY_ID)
					&& value != null) {
				key = KeyFactory
						.createKey(
								Value.class.getSimpleName(),
								Long
										.parseLong(request
												.getParameter(UICommonConstants.PARAM_ENTITY_ID)));
				value.setKey(key);
			}
			if (setKeyString != null && setKeyString != "") {
				value.setSetKey(KeyFactory.createKey(Set.class.getSimpleName(),
						Long.parseLong(setKeyString)));
			}
			if (parentValueKeyString != null && parentValueKeyString != "") {
				value
						.setParentValueKey(KeyFactory.createKey(Value.class
								.getSimpleName(), Long
								.parseLong(parentValueKeyString)));
			}
		}
	}

	private void populateTransientFields(Value currentValue)
			throws AppException {
		try {
			Value parentValue = currentValue.getParentValue();
			Set parentSet = currentValue.getSet();
			if (parentValue != null) {
				currentValue.setParentValueBusinessKey(MetaDataUtil
						.getEntityDefaultSearchColumnValue(parentValue,
								valueManager.getEntityMetaData()));
				currentValue.setParentValueKeyString(Long.toString(parentValue
						.getKey().getId()));
			}
			if (parentSet != null) {
				currentValue.setSetBusinessKey(MetaDataUtil
						.getEntityDefaultSearchColumnValue(parentSet,
								setManager.getEntityMetaData()));
				currentValue.setSetKeyString(Long.toString(parentSet.getKey()
						.getId()));
			}
		} catch (DataAccessException dae) {
			String message = "unable to pupolate transient fields of value"
					+ currentValue;
			log.error(message, dae);
			throw new AppException(message, dae);
		} catch (IllegalAccessException dae) {
			String message = "unable to pupolate transient fields of value"
					+ currentValue;
			log.error(message, dae);
			throw new AppException(message, dae);
		} catch (InvocationTargetException dae) {
			String message = "unable to pupolate transient fields of value"
					+ currentValue;
			log.error(message, dae);
			throw new AppException(message, dae);
		} catch (NoSuchMethodException dae) {
			String message = "unable to pupolate transient fields of value"
					+ currentValue;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	@InitBinder
	public void allowEmptyDateBinding(WebDataBinder binder) {
		// TODO as per prateek need to have a localization utility to centralize
		// the
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));

	}

	@RequestMapping("/value/valuelist.do")
	public String createSetList(HttpServletRequest request, Model model, @RequestParam("valueCreationFile") MultipartFile file)
			throws AppException, JAXBException {
		try {
				InputStream stream =new ByteArrayInputStream(file.getBytes());
					ImportVO importvo = new ImportVO();
					importvo.setEntityType(EntityType.VALUE);
					importvo.setStream(stream);
					globalImportExportManager.importFile(importvo);
		} catch (IOException ioexception) {
			String msg = "Error in IO of file";
			log.error(msg);
			throw new AppException(msg, ioexception);
		}  catch (AppException ioexception) {
			model.addAttribute(UICommonConstants.ATTRIB_FILE_ERROR,
					UICommonConstants.MSG_FILE_ERROR);
		}
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_VALUE_INDEX);
		return UICommonConstants.VIEW_INDEX;

	}
	
	@RequestMapping("/value/getvaluexml.do")
	public String getValueDefinitions(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment;filename=\""+"valuedefinitions.xml");
		try{
		OutputStream os = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		JAXBContext jaxbcontext;
		jaxbcontext = JAXBContext.newInstance(ValueList.class);	
		Collection<Set> allSets = setManager.getAllSets();
		HashMap<Key,String> setnameMap = new HashMap<Key,String>();
		for(Set set : allSets){
			setnameMap.put(set.getKey(), set.getSetName());
		}		
		Collection<Value> allValues = valueManager.getAllValues();
		HashMap<Key,String> valuenameMap = new HashMap<Key,String>();
		for(Value value : allValues){
			valuenameMap.put(value.getKey(), value.getValue());
		}
		
		ValueList listValue = new ValueList();
		ValueElement element = null;
		for(Value value : allValues){
			element = new ValueElement();
			element.setVal(value.getValue());
			element.setSet(setnameMap.get(value.getSetKey()));
			element.setParentValue(valuenameMap.get(value.getParentValueKey()));
			element.setOrderIndex(BigInteger.valueOf(value.getOrderIndex()));
			listValue.getValueElement().add(element);
		}
		Marshaller m = jaxbcontext.createMarshaller();
		m.marshal(listValue, bos);		
		bos.flush();
		bos.close();
		}catch(Exception ex){
			log.error(ex);
		}
		return null;
	}

}
