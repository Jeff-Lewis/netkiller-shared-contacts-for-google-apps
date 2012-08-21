package com.metacube.ipathshala.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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


import com.metacube.ipathshala.GridRequest;
import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.EntityType;
import com.metacube.ipathshala.core.jaxb.set.SetElement;
import com.metacube.ipathshala.core.jaxb.set.SetList;
import com.metacube.ipathshala.entity.Set;
import com.metacube.ipathshala.entity.metadata.util.MetaDataUtil;
import com.metacube.ipathshala.manager.GlobalImportExportManager;
import com.metacube.ipathshala.manager.SetManager;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.GridRequestParser;
import com.metacube.ipathshala.view.validators.EntityValidator;
import com.metacube.ipathshala.vo.ImportVO;

@Controller
public class SetController extends AbstractController {

	private static final AppLogger log = AppLogger
			.getLogger(SetController.class);

	@Autowired
	private SetManager SetManager;

	@Autowired
	private EntityValidator validator;

	@Resource
	GridRequestParser gridRequestParser;

	@Autowired
	private GlobalImportExportManager globalImportExportManager;

	/**
	 * Show Set tab.
	 * 
	 * @return
	 */
	@RequestMapping("/set.do")
	public String showSetTab(Model model, HttpServletRequest request) {
		log.debug("Presenting Set view.");
		addToNavigationTrail("Set", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_SET_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Show Set search form.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/set/searchForm.do")
	public String getSearchForm(Model model, HttpServletRequest request) {
		log.debug("Presenting Set Form view.");
		addToNavigationTrail("Search", false, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_SET_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Show the create Set form.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/set/createForm.do")
	public String getCreateForm(Model model, HttpServletRequest request) {
		log.debug("Presenting create-Set form view.");
		addToNavigationTrail("Create", false, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_SET, new Set());
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_CREATE);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CREATE_SET);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Show Set detail.
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws AppException
	 * @throws NumberFormatException
	 */
	@RequestMapping("/set/showDetail.do")
	public String showDetail(HttpServletRequest request, Model model)
			throws NumberFormatException, AppException {
		String SetId = request.getParameter(UICommonConstants.PARAM_ENTITY_ID);
		Key key = KeyFactory.createKey(Set.class.getSimpleName(), Long
				.parseLong(SetId));
		Set currentSet = (Set) SetManager.getById(key);
		populateTransientFields(currentSet);
		model.addAttribute(UICommonConstants.ATTRIB_SET, currentSet);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_SET_DETAIL);
		log.debug("Presenting Set view.");
		addToNavigationTrail(currentSet.getSetName(), false,
				request, false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * Populate the Set data for editing and show the update form.
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws AppException
	 * @throws NumberFormatException
	 */
	@RequestMapping("/set/editForm.do")
	public String getEditForm(HttpServletRequest request, Model model)
			throws NumberFormatException, AppException {

		String SetId = request.getParameter(UICommonConstants.PARAM_ENTITY_ID);
		Key k = KeyFactory.createKey(Set.class.getSimpleName(), Long
				.parseLong(SetId));
		Set currentSet = (Set) SetManager.getById(k);
		populateTransientFields(currentSet);
		model.addAttribute(UICommonConstants.ATTRIB_SET, currentSet);
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_EDIT);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CREATE_SET);
		log.debug("Presenting edit-Set form view.");
		addToNavigationTrail("Edit", false,
				request, false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * This will sends a request to update Set.
	 * 
	 * @param Set
	 * @param result
	 * @param model
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/set/update.do")
	public String update(
			@ModelAttribute(value = UICommonConstants.ATTRIB_SET) Set set,
			BindingResult result, Model model, HttpServletRequest request)
			throws AppException {
		validator.validate(set, result, UICommonConstants.FORM_SET);

		if (result.hasErrors()) {
			log
					.debug("Could not validate update Set form becuase of form errors.");
			model.addAttribute(UICommonConstants.ATTRIB_OPER,
					UICommonConstants.OPER_EDIT);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_CREATE_SET);
		} else {
			log.debug("Update Set data...");
			SetManager.updateSet(set);
			model.addAttribute(UICommonConstants.ATTRIB_SET, set);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_SET_DETAIL);
			removeFromNavigationTrail(request);
		}

		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * This will sends a request to delete Set.
	 * 
	 * @param request
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/set/delete.do")
	public void delete(
			@ModelAttribute(value = UICommonConstants.ATTRIB_SET) Set Set,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		log.debug("Processing detete Set request.");
		SetManager.deleteSet(Set);
		removeFromNavigationTrail(request);
		redirectToPreviousBreadcrumb(request, response);
	}

	@RequestMapping("/set/close.do")
	public void close(HttpServletRequest request, HttpServletResponse response) {
		removeFromNavigationTrail(request);
		redirectToPreviousBreadcrumb(request, response);
	}
	
	@RequestMapping("/set/getsetxml.do")
	public String getSetDefinitions(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment;filename=\""+"setdefinitions.xml");
		try{
		OutputStream os = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		JAXBContext jaxbcontext;
		jaxbcontext = JAXBContext.newInstance(SetList.class);		
		Collection<Set> allSets = SetManager.getAllSets();
		HashMap<Key,String> setnameMap = new HashMap<Key,String>();
		for(Set set : allSets){
			setnameMap.put(set.getKey(), set.getSetName());
		}
		
		SetList listSet = new SetList();
		SetElement element = null;
		for(Set set : allSets){
			element = new SetElement();
			element.setSetName(set.getSetName());
			element.setSetOrder(set.getSetOrder());
			element.setParentSetName(setnameMap.get(set.getParentSetKey()));
			listSet.getSetElement().add(element);
		}
		Marshaller m = jaxbcontext.createMarshaller();
		m.marshal(listSet, bos);		
		bos.flush();
		bos.close();
		}catch(Exception ex){
			log.error(ex);
		}
		return null;
	}


	/**
	 * This will sends a request to create Set.
	 * 
	 * @param Set
	 * @param result
	 * @return
	 * @throws AppEx
	 * @RequestMappingception
	 */
	@RequestMapping("/set/create.do")
	public String create(
			@ModelAttribute(value = UICommonConstants.ATTRIB_SET) Set set,
			BindingResult result, Model model, HttpServletRequest request)
			throws AppException {
		validator.validate(set, result, "SetForm");

		if (result.hasErrors()) {
			log.warn("Unable to validate Set data for creation of new Set.");
			model.addAttribute(UICommonConstants.ATTRIB_OPER,
					UICommonConstants.OPER_CREATE);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_CREATE_SET);
		} else {
			log.debug("New Set creation request processing.");
			SetManager.createSet(set);
			model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
					UICommonConstants.CONTEXT_SET_INDEX);
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
	@RequestMapping("/set/data.do")
	public @ResponseBody
	Map<String, Object> searchSetsData(HttpServletRequest request)
			throws AppException {
		Map<String, Object> modalMap = new HashMap<String, Object>();

		GridRequest dataCriteria = gridRequestParser.parseDataCriteria(request);
		SearchResult searchResult = SetManager.doSearch(dataCriteria);

		List<Object> SetList = searchResult.getResultObjects();

		Long totalRecordsLong = (Long) searchResult.getTotalRecordSize();
		int totalRecords = totalRecordsLong.intValue();

		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		int id = 0;

		for (Iterator<Object> iterator = SetList.iterator(); iterator.hasNext();) {
			Set set = (Set) iterator.next();

			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", ++id);

			ArrayList<Object> row = new ArrayList<Object>();
			row.add(set.getKey().getId());
			row.add(set.getSetName());
			row.add(set.getSetOrder());
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

	@RequestMapping("/set/advancedsearch.do")
	public String doAdvancedSearchForSetData(HttpServletRequest request,
			Model model) throws AppException {

		String searchText = request
				.getParameter(UICommonConstants.ADV_SEARCH_TEXT_PARAM);
		addToNavigationTrail("Set", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_ADV_SEARCH_TXT, searchText);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_SET_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}

	/**
	 * @return SetManager
	 */
	public SetManager getSetManager() {
		return SetManager;
	}

	/**
	 * SetManager to set
	 * 
	 * @param SetManager
	 */
	public void setSetManager(SetManager setManager) {
		this.SetManager = setManager;
	}

	/**
	 * @return validator EntityValidator handle
	 */
	public EntityValidator getValidator() {
		return validator;
	}

	/**
	 * @param validator
	 *            EntityValidator to set
	 */
	public void setValidator(EntityValidator validator) {
		this.validator = validator;
	}

	@ModelAttribute
	public void preprocessSet(
			@ModelAttribute(value = UICommonConstants.ATTRIB_SET) Set set,
			HttpServletRequest request) {
		Key key = null;
		String parentSetKeyString = set.getParentSetKeyString();
		if (request.getParameterMap().containsKey(
				UICommonConstants.PARAM_ENTITY_ID)
				&& set != null) {
			key = KeyFactory.createKey(Set.class.getSimpleName(), Long
					.parseLong(request
							.getParameter(UICommonConstants.PARAM_ENTITY_ID)));
			set.setKey(key);
		}
		if (parentSetKeyString != null && parentSetKeyString != "") {
			set.setParentSetKey(KeyFactory.createKey(Set.class.getSimpleName(),
					Long.parseLong(parentSetKeyString)));
		}
	}

	@InitBinder
	public void allowEmptyDateBinding(WebDataBinder binder) {
		// TODO as per prateek need to have a localization utility to centralize
		// the
		// formatting related functionality(locale based).
		// Allow for null values in date fields.
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
	}

	private void populateTransientFields(Set currentSet) throws AppException {
		try {
			Set parentSet = currentSet.getParentSet();
			if (parentSet != null) {
				currentSet.setParentSetBusinessKey(MetaDataUtil
						.getEntityDefaultSearchColumnValue(parentSet,
								SetManager.getEntityMetaData()));
				currentSet.setParentSetKeyString(Long.toString(parentSet
						.getKey().getId()));
			}
		} catch (DataAccessException dae) {
			String message = "unable to pupolate transient fields of set"
					+ currentSet;
			log.error(message, dae);
			throw new AppException(message, dae);
		} catch (IllegalAccessException dae) {
			String message = "unable to pupolate transient fields of set"
					+ currentSet;
			log.error(message, dae);
			throw new AppException(message, dae);
		} catch (InvocationTargetException dae) {
			String message = "unable to pupolate transient fields of set"
					+ currentSet;
			log.error(message, dae);
			throw new AppException(message, dae);
		} catch (NoSuchMethodException dae) {
			String message = "unable to pupolate transient fields of set"
					+ currentSet;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	@RequestMapping("/set/setlist.do")
	public String createSetList(HttpServletRequest request, Model model, @RequestParam("setCreationFile") MultipartFile file)
			throws AppException, JAXBException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			InputStream stream = null;
			try	{
				stream = new ByteArrayInputStream(file.getBytes());
			}
			catch(UnsupportedOperationException e)	{
				String msg = "cannot obtain the stream" ;
				throw new AppException(msg, e) ;
			}
					ImportVO importvo = new ImportVO();
					importvo.setEntityType(EntityType.SET);
					importvo.setStream(stream);
					
					globalImportExportManager.importFile(importvo);
		} catch (IOException ioexception) {
			String msg = "Error in IO of file";
			log.error(msg);
			throw new AppException(msg, ioexception);
		} catch (AppException ioexception) {
			model.addAttribute(UICommonConstants.ATTRIB_FILE_ERROR,
					UICommonConstants.MSG_FILE_ERROR);
		}
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_SET_INDEX);
		return UICommonConstants.VIEW_INDEX;

	}

}
