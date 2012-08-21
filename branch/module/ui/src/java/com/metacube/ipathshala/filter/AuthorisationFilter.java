package com.metacube.ipathshala.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.AppUserEntity;
import com.metacube.ipathshala.entity.DomainAdmin;
import com.metacube.ipathshala.entity.Set;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.manager.DomainAdminManager;
import com.metacube.ipathshala.manager.SetManager;
import com.metacube.ipathshala.manager.ValueManager;
import com.metacube.ipathshala.security.ResourceSecurityConfig;
import com.metacube.ipathshala.service.AppUserEntityService;
import com.metacube.ipathshala.service.ContactsService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CommonWebUtil;

/**
 * Security filter that will take care of authorisation.
 * 
 * TODO: remove hard coding.
 * 
 * @author sabir
 * 
 */
@Component
public class AuthorisationFilter implements Filter {

	private static final AppLogger log = AppLogger
			.getLogger(AuthorisationFilter.class);

	@Autowired
	private AppUserEntityService appUserEntityService;

	@Autowired
	private ContactsService contactsService;

	@Autowired
	private ResourceSecurityConfig resourceSecurityConfig;

	@Autowired
	private SetManager setManager;

	@Autowired
	private ValueManager valueManager;

	@Autowired
	private DomainAdminManager domainAdminManager;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpServletRequest request = (HttpServletRequest) req;
		/*
		 * log.debug("Inside authentication filter..."); boolean requestOk =
		 * true;
		 * 
		 * requestOk = checkAuthorisation((HttpServletRequest) req,
		 * (HttpServletResponse) resp);
		 * 
		 * if (requestOk) { chain.doFilter(req, resp); } else {
		 * HttpServletResponse httpResponse = (HttpServletResponse) resp;
		 * httpResponse
		 * .sendRedirect(UICommonConstants.AUTHORIZATION_ERROR_URL); }
		 */

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			AppUserEntity appUser = appUserEntityService.getAppUserByEmail(user
					.getEmail());

			if (appUser != null) {
				chain.doFilter(req, resp);
				// response.sendRedirect(UICommonConstants.CONTEXT_CONTACTS_HOME);
			} else {
				Boolean isAdmin = contactsService.isAdmin(user.getEmail());
				DomainAdmin domainAdmin = domainAdminManager
						.getDomainAdminByDomainName(CommonWebUtil
								.getDomain(user.getEmail()));
				if (domainAdmin == null) {
					if (isAdmin) {
						AppUserEntity appUserEntity = new AppUserEntity(
								user.getUserId(), user.getEmail(), "", "",
								user.getNickname(),
								CommonWebUtil.getDomain(user.getEmail()));
						appUserEntityService.createAppUser(appUserEntity);
						DomainAdmin newDomainAdmin = new DomainAdmin();
						newDomainAdmin.setAdminEmail(user.getEmail());
						newDomainAdmin.setDomainName(CommonWebUtil
								.getDomain(user.getEmail()));
						newDomainAdmin.setRegisteredDate(new Date());
						newDomainAdmin.setTotalCounts(0);
						newDomainAdmin.setAccountTypeKey(getAccountTypeKey());
						try {
							domainAdminManager
									.createDomainAdmin(newDomainAdmin);
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						response.sendRedirect("/createGroupHome.do");
					} else {
						response.getWriter().print(
								"Admin must login first time");
					}
				} else {
					AppUserEntity appUserEntity = new AppUserEntity(
							user.getUserId(), user.getEmail(), "", "",
							user.getNickname(), CommonWebUtil.getDomain(user
									.getEmail()));
					appUserEntityService.createAppUser(appUserEntity);
					response.sendRedirect("/contacts.do");
				}
			}
		} else {
			response.sendRedirect(userService.createLoginURL(request
					.getRequestURI()));
		}
	}

	private Key getAccountTypeKey() {
		Set set = setManager.getBySetName("AccountType");
		List<Value> valueList = (List<Value>) valueManager
				.getValueBySetKey(set);
		if (valueList != null && !valueList.isEmpty()) {
			for (Value value : valueList) {
				if (value.getValue().equalsIgnoreCase("Free")) {
					return value.getKey();
				}
			}
		}
		return null;
	}

	private boolean checkAuthorisation(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		/*
		 * HttpSession currentSession = httpRequest.getSession(false); if
		 * (currentSession != null) { AppUser currentLoggedInUser = (AppUser)
		 * currentSession .getAttribute(AppUser.APPUSER_SESSION_VAR); String
		 * targetUrl = httpRequest.getRequestURI(); String[] tokenizedUrl =
		 * StringUtils.split(targetUrl, "/"); if (tokenizedUrl.length == 0 ||
		 * tokenizedUrl[0].equals("index.do") || currentLoggedInUser == null) {
		 * return true; } else if (resourceSecurityConfig
		 * .doesResourceExist(tokenizedUrl[0]) &&
		 * (!currentLoggedInUser.hasAccess(resourceSecurityConfig
		 * .getMappedEntityNameForResource(tokenizedUrl[0]),
		 * Permission.PermissionType.READ))) { return false; } } return true;
		 */
		return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
				.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(this, "appUserEntityService");

		autowireCapableBeanFactory
				.configureBean(this, "resourceSecurityConfig");

		autowireCapableBeanFactory.configureBean(this, "contactsService");

		autowireCapableBeanFactory.configureBean(this, "setManager");

		autowireCapableBeanFactory.configureBean(this, "valueManager");
		autowireCapableBeanFactory.configureBean(this, "domainAdminManager");

	}
}