package com.netkiller.filter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.netkiller.UICommonConstants;
import com.netkiller.core.DataContext;
import com.netkiller.core.UserRoleType;
import com.netkiller.security.AppUser;
import com.netkiller.util.AppLogger;

@Component
public class InitializationFilter implements Filter {

/*	@Autowired
	private UserSiteListGeneratorFactory siteListFactory;*/

	/*
	 * @Autowired private AcademicYearManager academicYearManager;
	 * 
	 * @Autowired private ClassStudentManager classStudentManager;
	 * 
	 * @Autowired private MyClassManager myClassManager;
	 * 
	 * @Autowired private TeacherManager teacherManager;
	 * 
	 * @Autowired private StudentManager studentManager;
	 * 
	 * @Autowired private ParentManager parentManager;
	 */

	private static final AppLogger log = AppLogger
			.getLogger(InitializationFilter.class);

	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) req;
		HttpSession currentSession = httprequest.getSession(false);

		if (currentSession != null) {

			AppUser currentUser = (AppUser) currentSession
					.getAttribute(AppUser.APPUSER_SESSION_VAR);
			//List<com.netkiller.entity.AcademicYear> userAcademicYearList = null;
			/*
			 * if (currentSession
			 * .getAttribute(UICommonConstants.USER_ACADEMIC_YEAR_LIST) == null)
			 * { if (currentUser != null) { if (currentUser.getDefaultAppGroup()
			 * != null) { userAcademicYearList =
			 * getAcademicYearListByUser(currentUser);
			 * currentSession.setAttribute(
			 * UICommonConstants.USER_ACADEMIC_YEAR_LIST, userAcademicYearList);
			 * } } }
			 */
			// System.out.println("My Academic Year List size:"+userAcademicYearList.size());
			/*
			 * If dataContext is null then the datacontext is to be set with the
			 * default AcademicYear
			 */
			// try {
			DataContext dataContext = null;

			if (currentSession.getAttribute(UICommonConstants.DATA_CONTEXT) == null
			/* && getAcademicYearListByUser(currentUser) != null */) {
				/*
				 * com.metacube.ipathshala.entity.AcademicYear
				 * defaultAcademicYear = academicYearManager
				 * .getDefaultAcademicYear(); List<Key> userAcademicYearKeyList
				 * = new ArrayList<Key>(); dataContext = new DataContext();
				 * userAcademicYearList =
				 * getAcademicYearListByUser(currentUser); if
				 * (userAcademicYearList != null) { for
				 * (com.metacube.ipathshala.entity.AcademicYear academicYear :
				 * userAcademicYearList) {
				 * userAcademicYearKeyList.add(academicYear.getKey()); } } if
				 * (defaultAcademicYear != null && userAcademicYearKeyList
				 * .contains(defaultAcademicYear.getKey())) {
				 * Map<GlobalFilterType, GlobalFilter> globalFilterHashMap = new
				 * HashMap<GlobalFilterType, GlobalFilter>();
				 * 
				 * globalFilterHashMap.put(
				 * GlobalFilterType.GLOBAL_ACADEMIC_YEAR, new
				 * AcademicYear(defaultAcademicYear.getKey(),
				 * defaultAcademicYear.getFromDate(),
				 * defaultAcademicYear.getToDate(),
				 * defaultAcademicYear.getActive()));
				 * globalFilterHashMap.put(GlobalFilterType.DELETE, new
				 * Delete(false));
				 */
				/*
				 * if (UserRoleType .get(currentUser.getDefaultAppGroup()
				 * .getGroupName()).equals( UserRoleType.PARENT)) { String
				 * userId = currentUser.getUserId(); Parent parent =
				 * parentManager.getByUserId(userId); if (parent != null) {
				 * List<Student> students = (List<Student>) studentManager
				 * .getStudentsByParentKey(parent.getKey());
				 * 
				 * if (students != null && students.size() == 1) { Student
				 * currentSelectedStudent = students .get(0); List<Key>
				 * myClasses = classStudentManager
				 * .getClassKeyListUsingStudentKey( currentSelectedStudent
				 * .getKey(), dataContext); Key classKey = null; if (myClasses
				 * != null) { List<MyClass> myClassList = (List<MyClass>)
				 * myClassManager .getByKeys(myClasses); for (MyClass myClass :
				 * myClassList) { if (myClass.getClassTypeValue()
				 * .getValueUpperCase() .equals("CLASS")) { classKey =
				 * myClass.getKey(); break; } } } globalFilterHashMap
				 * .put(GlobalFilterType.STUDENT, new
				 * com.metacube.ipathshala.globalfilter.Student(
				 * currentSelectedStudent .getKey(), currentSelectedStudent
				 * .getUserId(), classKey)); } currentSession.setAttribute(
				 * UICommonConstants.STUDENT_LIST, students); //
				 * globalFilterHashMap.put(GlobalFilterType.STUDENT, // new //
				 * com
				 * .metacube.ipathshala.globalfilter.Student(student.getKey(),
				 * student.getUserId())); } } GlobalFilterMap globalFilterMap =
				 * new GlobalFilterMap( globalFilterHashMap);
				 * dataContext.setGlobalFilterMap(globalFilterMap);
				 * dataContext.setDefaultAcademicYear(new AcademicYear(
				 * defaultAcademicYear.getKey(),
				 * defaultAcademicYear.getFromDate(),
				 * defaultAcademicYear.getToDate(),
				 * defaultAcademicYear.getActive())); } } else {
				 */dataContext = (DataContext) currentSession
						.getAttribute(UICommonConstants.DATA_CONTEXT);
				// }

				currentSession.setAttribute(UICommonConstants.DATA_CONTEXT,
						dataContext);
				// } catch (AppException e) {
				// log.error("Incorrect Number Format  :" + e);
				// }

				if (currentSession
						.getAttribute(UICommonConstants.VAR_SCHOOL_SITE) == null) {
					try {
						InputStream googleSitesPropertiesInputStream = this
								.getClass().getClassLoader()
								.getResourceAsStream("googlesites.properties");
						if (googleSitesPropertiesInputStream == null) {
							log.error("School Site Cannot be initialized- Properties file not readable");
						}
						Properties googleSitesProperties = new Properties();
						googleSitesProperties
								.load(googleSitesPropertiesInputStream);
						String schoolSiteName = googleSitesProperties
								.getProperty("schoolinternalsitename");
						currentSession.setAttribute(
								UICommonConstants.VAR_SCHOOL_SITE,
								schoolSiteName);
					} catch (FileNotFoundException e) {
						log.error(
								"School Site Cannot Be Found " + e.getMessage(),
								e);
					} catch (IOException e) {
						log.error(
								"School Site Cannot Be Found " + e.getMessage(),
								e);
					}

				}

				if (currentSession
						.getAttribute(UICommonConstants.USER_SITE_LIST) == null) {
					if (currentUser != null) {
						/*
						 * if (currentUser.getDefaultAppGroup() != null) {
						 * UserRoleType roleType = getUserRoleType(currentUser
						 * .getDefaultAppGroup().getGroupName()); String userId
						 * = currentUser.getUserId(); List<SiteInfo>
						 * siteInfoList = new ArrayList<SiteInfo>();
						 * List<SiteInfo> adminSiteInfoList = new
						 * ArrayList<SiteInfo>(); UserSiteListGenerator
						 * userSiteListGenerator = siteListFactory
						 * .getSiteListGenerator(roleType);
						 * UserSiteListGenerator adminSiteListGenerator =
						 * siteListFactory
						 * .getSiteListGenerator(UserRoleType.ADMIN); try {
						 * siteInfoList = userSiteListGenerator .getSiteList(
						 * userId, (DataContext) currentSession
						 * .getAttribute(UICommonConstants.DATA_CONTEXT));
						 * adminSiteInfoList = adminSiteListGenerator
						 * .getSiteList( userId, (DataContext) currentSession
						 * .getAttribute(UICommonConstants.DATA_CONTEXT));
						 * 
						 * } catch (AppException e) { log.error(
						 * "Unable to fetch Class Site List for user :" +
						 * userId, e);
						 * 
						 * } if (!siteInfoList.isEmpty()) {
						 * currentSession.setAttribute(
						 * UICommonConstants.USER_SITE_LIST, siteInfoList); } if
						 * (!siteInfoList.isEmpty()) {
						 * currentSession.setAttribute(
						 * UICommonConstants.ALL_SITE_LIST, adminSiteInfoList);
						 * }
						 */
						// }
					}
				}

			}
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig config) throws ServletException {

	}

	/*
	 * private List<com.metacube.ipathshala.entity.AcademicYear>
	 * getAcademicYearListByUser( AppUser currentUser) {
	 * List<com.metacube.ipathshala.entity.AcademicYear> userAcademicYearList =
	 * null; if (currentUser != null) { String userId = currentUser.getUserId();
	 * if (currentUser.getDefaultAppGroup() != null) { try {
	 * com.metacube.ipathshala.entity.AcademicYear defaultAcademicYear =
	 * academicYearManager .getDefaultAcademicYear(); switch
	 * (UserRoleType.get(currentUser.getDefaultAppGroup() .getGroupName())) {
	 * case ADMIN: userAcademicYearList =
	 * (List<com.metacube.ipathshala.entity.AcademicYear>) academicYearManager
	 * .getAllAcademicYears(); break; case PARENT: Parent parent =
	 * parentManager.getByUserId(userId); if (parent != null) { if
	 * (defaultAcademicYear != null) { userAcademicYearList =
	 * academicYearManager .getAcademicYearsBetweenDateRange(
	 * parent.getFromDate(), defaultAcademicYear.getToDate()); } } break; case
	 * TEACHER:
	 * 
	 * Teacher can see all the academic years whether they come within his range
	 * or not
	 * 
	 * 
	 * Teacher teacher = teacherManager.getByUserId(userId); if (teacher !=
	 * null) { userAcademicYearList =
	 * (List<com.metacube.ipathshala.entity.AcademicYear>) academicYearManager
	 * .getAllAcademicYears(); } break; case STUDENT: Student student =
	 * studentManager.getByUserId(userId); if (student != null) { if
	 * (defaultAcademicYear != null) { userAcademicYearList =
	 * academicYearManager .getAcademicYearsBetweenDateRange(
	 * student.getFromDate(), defaultAcademicYear.getToDate()); } } break; case
	 * DOMAIN: userAcademicYearList =
	 * (List<com.metacube.ipathshala.entity.AcademicYear>) academicYearManager
	 * .getAllAcademicYears(); break; }
	 * 
	 * } catch (AppException e) {
	 * log.error("Unable to fetch Academic Year List for user :" + userId, e); }
	 * } }
	 * 
	 * // sort the list if (userAcademicYearList != null &&
	 * !userAcademicYearList.isEmpty()) Collections .sort(userAcademicYearList,
	 * new AcademicYearComparator());
	 * 
	 * return userAcademicYearList; }
	 */

	private UserRoleType getUserRoleType(String stringValue) {
		UserRoleType[] type = UserRoleType.values();
		for (int i = 0; i < type.length; i++) {
			if (type[i].toString().equals(stringValue)) {
				return type[i];
			}
		}
		return null;
	}

}
