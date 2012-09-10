/**
 *
 */
package com.netkiller.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netkiller.UICommonConstants;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.metadata.impl.GlobalFilterType;
import com.netkiller.gadget.Gadget;
import com.netkiller.gadget.GadgetDashboardModel;
import com.netkiller.gadget.GadgetMapping;
import com.netkiller.globalfilter.GlobalFilter;
import com.netkiller.security.AppUser;
import com.netkiller.security.AppUserService;
import com.netkiller.service.googleservice.SiteService;
import com.netkiller.sites.Site;
import com.netkiller.util.AppLogger;

/**
 * 
 */
@Controller
public class HomePageController extends AbstractController {

	private static final AppLogger log = AppLogger.getLogger(HomePageController.class);

	@Autowired
	private ApplicationContext applicationContext;

/*	@Autowired
	private AcademicYearManager academicYearManager;*/

	@Autowired
	@Qualifier("GoogleUserService")
	private AppUserService userService;

	/*@Autowired
	private StudentManager studentManager;*/

	

	/**
	 * Show dashboard to student group.
	 * 
	 * @param model
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping("/index.do")
	public String showDashboard(HttpSession session, Model model, HttpServletRequest request) {
		AppUser appUser = (AppUser) session.getAttribute(AppUser.APPUSER_SESSION_VAR);
		String groupName = null;
		if (appUser != null && appUser.getDefaultAppGroup() != null) {
			groupName = appUser.getDefaultAppGroup().getGroupName();
		}
		log.debug("Group Name " + groupName);
		String currentSelectTab = request.getParameter("currentSelectTab");
		String currentSelectTabVal = request.getParameter("currentSelectTabVal");
		session.setAttribute("currentSelectTab", currentSelectTab);
		session.setAttribute("currentSelectTabVal", currentSelectTabVal);
		// applicationContext.getBeansOfType(Class) doesn't works, as it is not
		// checking in the parent context
		// So we have to use this one via BeanFactoryUtils to go to the parent
		// Application Context
		Map<String, GadgetMapping> mappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
				GadgetMapping.class);
		log.debug("Mappings " + mappings);

		if (mappings != null && !mappings.isEmpty()) {
			for (GadgetMapping mapping : mappings.values()) {
				if (mapping.getRole().equalsIgnoreCase(groupName)) {
					List<Gadget> availableGadgetsForRole = mapping.getGadgets();
					model.addAttribute("gadgetModel", new GadgetDashboardModel(availableGadgetsForRole));
					break;
				}
			}
		}

		// Add Gadget Container to the view index
		// commented to check breadcrumb problem
		addToNavigationTrail("Home", true, request, false, false);
		removeFromNavigationTrail(request);
		/*
		 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
		 * UICommonConstants.CONTEXT_GADGET_CONTAINER);
		 */
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW, UICommonConstants.CONTEXT_LATEST_ACTIVITY_WIDGET);

		return UICommonConstants.VIEW_INDEX;
	}

	//@RequestMapping("/logout.do")
	public void doLogout(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		session.setAttribute("logoutAction", new Boolean(true));
		String logoutUrl = userService.createLogoutURL("/");
		session.invalidate();
		try {
			((HttpServletResponse) response).sendRedirect(logoutUrl);

		} catch (IOException ioe) {
			log.error("Unable to logout", ioe);
			throw new AppException("Unable to logout", ioe);
		}
	}

	@RequestMapping("/authorisationerror.do")
	public String showAuthorisationError() {
		return UICommonConstants.VIEW_AUTHORIZATION_ERROR;
	}

	@RequestMapping("/resourcenotfounderror.do")
	public String showResourceNotFoundError() {
		return UICommonConstants.VIEW_RESOURCE_MISSING_ERROR;
	}

	/*@RequestMapping("/gotoclasssite.do")
	public void goToGoogleSite(HttpServletRequest request, HttpServletResponse response) throws AppException,
			ServletException, IOException {
		String siteName = request.getParameter("selectedSite");
		try {
			//Site classSite = siteService.getSiteFromSiteName(siteName);
			//response.sendRedirect(classSite.getSiteHref());
		} catch (AppException siteNotFoundException) {
			log.error("Class Site Does not Exist. Workflow Might not have been executed.", siteNotFoundException);
			response.sendRedirect("resourcenotfounderror.do");
		}
	}*/

	@RequestMapping("/setDataContext.do")
	public void setSession(HttpServletRequest request, Model model, HttpSession session, HttpServletResponse response)
			throws AppException {
		
		DataContext dataContext = null;
		if (((DataContext) session.getAttribute(UICommonConstants.DATA_CONTEXT)) == null) {
			dataContext = new DataContext();
		} else {
			dataContext = (DataContext) session.getAttribute(UICommonConstants.DATA_CONTEXT);
		}
		String acadYear = request.getParameter(UICommonConstants.GLOBAL_ACADEMIC_YEAR_KEY);
		
		Map<GlobalFilterType, GlobalFilter> globalFilterHashMap = null;
		if (dataContext.getGlobalFilterMap() != null
				&& dataContext.getGlobalFilterMap().getGlobalFilterHashMap() != null) {
			globalFilterHashMap = dataContext.getGlobalFilterMap().getGlobalFilterHashMap();
		} else {
			globalFilterHashMap = new HashMap<GlobalFilterType, GlobalFilter>();
		}
		/*com.metacube.ipathshala.entity.AcademicYear selectedAcademicYear = (com.metacube.ipathshala.entity.AcademicYear) academicYearManager
				.getById(KeyFactory.createKey(com.metacube.ipathshala.entity.AcademicYear.class.getSimpleName(),
						Long.parseLong(acadYear)));*/
		/*globalFilterHashMap
				.put(GlobalFilterType.GLOBAL_ACADEMIC_YEAR,
						new AcademicYear(selectedAcademicYear.getKey(), selectedAcademicYear.getFromDate(),
								selectedAcademicYear.getToDate(), selectedAcademicYear.getActive()));
		GlobalFilterMap globalFilterMap = new GlobalFilterMap(globalFilterHashMap);
		dataContext.setGlobalFilterMap(globalFilterMap);*/
		session.setAttribute(UICommonConstants.DATA_CONTEXT, dataContext);
		String dashboardUrl = "/index.do";
		try {
			request.getRequestDispatcher(dashboardUrl).forward(request, response);
		} catch (ServletException servletException) {
			String message = dashboardUrl + "not found";
			throw new AppException(message, servletException);
		} catch (IOException ioException) {
			String message = dashboardUrl + "not found";
			throw new AppException(message, ioException);
		}

	}

/*	@Autowired
	private ClassStudentManager classStudentManager;
	
	@Autowired
	private MyClassManager myClassManager;*/
	
	@RequestMapping("/setGlobalFilterStudent.do")
	public void setGlobalFilterStudent(HttpServletRequest request, Model model, HttpSession session,
			HttpServletResponse response) throws AppException {

		/*
		 * DataContext dataContext = null; if (((DataContext)
		 * session.getAttribute(UICommonConstants.DATA_CONTEXT)) == null) {
		 * dataContext = new DataContext(); } else { dataContext = (DataContext)
		 * session.getAttribute(UICommonConstants.DATA_CONTEXT); } String
		 * studentKey = request.getParameter("stud"); Map<GlobalFilterType,
		 * GlobalFilter> globalFilterHashMap = null; if
		 * (dataContext.getGlobalFilterMap() != null &&
		 * dataContext.getGlobalFilterMap().getGlobalFilterHashMap() != null) {
		 * globalFilterHashMap =
		 * dataContext.getGlobalFilterMap().getGlobalFilterHashMap(); } else {
		 * globalFilterHashMap = new HashMap<GlobalFilterType, GlobalFilter>();
		 * } if (!StringUtils.isBlank(studentKey)) { if
		 * (!studentKey.equalsIgnoreCase("all")) {
		 * com.metacube.ipathshala.entity.Student currentSelectedStudent =
		 * (com.metacube.ipathshala.entity.Student) studentManager
		 * .getById(KeyFactory
		 * .createKey(com.metacube.ipathshala.entity.Student.class
		 * .getSimpleName(), Long.parseLong(studentKey)));
		 * 
		 * List<Key> myClasses =
		 * classStudentManager.getClassKeyListUsingStudentKey
		 * (currentSelectedStudent.getKey(), dataContext); Key classKey = null;
		 * if(myClasses!=null){ List<MyClass> myClassList = (List<MyClass>)
		 * myClassManager.getByKeys(myClasses); for(MyClass myClass :
		 * myClassList){
		 * if(myClass.getClassTypeValue().getValueUpperCase().equals("CLASS")){
		 * classKey = myClass.getKey(); break; } } }
		 * globalFilterHashMap.put(GlobalFilterType.STUDENT, new
		 * Student(currentSelectedStudent.getKey(),
		 * currentSelectedStudent.getUserId(),classKey)); } else { if
		 * (globalFilterHashMap.get(GlobalFilterType.STUDENT) != null) {
		 * globalFilterHashMap.remove(GlobalFilterType.STUDENT); } }
		 * 
		 * }
		 * 
		 * GlobalFilterMap globalFilterMap = new
		 * GlobalFilterMap(globalFilterHashMap);
		 * dataContext.setGlobalFilterMap(globalFilterMap);
		 * session.setAttribute(UICommonConstants.DATA_CONTEXT, dataContext);
		 * String dashboardUrl = "/index.do"; try {
		 * request.getRequestDispatcher(dashboardUrl).forward(request,
		 * response); } catch (ServletException servletException) { String
		 * message = dashboardUrl + "not found"; throw new AppException(message,
		 * servletException); } catch (IOException ioException) { String message
		 * = dashboardUrl + "not found"; throw new AppException(message,
		 * ioException); }
		 */
	}

	@RequestMapping("/getHeaderTitle.do")
	public @ResponseBody
	String getHeaderTitle() {
		String title = null;
		InputStream loginProperties = this.getClass().getClassLoader().getResourceAsStream("ipathshala.properties");
		Properties props = new Properties();
		try {
			props.load(loginProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		title = props.getProperty("metacampus.header.title");
		return title;
	}

	@RequestMapping("/getSchoolSite.do")
	public @ResponseBody
	String getSchoolStie() {
		String title = null;
		InputStream loginProperties = this.getClass().getClassLoader().getResourceAsStream("ipathshala.properties");
		Properties props = new Properties();
		try {
			props.load(loginProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		title = props.getProperty("schoolSiteName");
		return title;
	}

}
