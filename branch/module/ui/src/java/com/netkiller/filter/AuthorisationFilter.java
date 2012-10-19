package com.netkiller.filter;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.netkiller.core.AppException;
import com.netkiller.entity.AppUserEntity;
import com.netkiller.entity.DomainAdmin;
import com.netkiller.entity.Set;
import com.netkiller.entity.Value;
import com.netkiller.manager.DomainAdminManager;
import com.netkiller.manager.SetManager;
import com.netkiller.manager.ValueManager;
import com.netkiller.service.AppUserEntityService;
import com.netkiller.service.ContactsService;
import com.netkiller.util.AccountType;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonWebUtil;

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
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (StringUtils.indexOfAny(req.getRequestURI(), skipResources) >= 0) {
			chain.doFilter(request, response);
		} else if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		} else {
			HttpSession session = req.getSession();
			AppUserEntity appUser = null;
			if (session.getAttribute("appUser") != null) {
				appUser = (AppUserEntity) session.getAttribute("appUser");
			} else {
				appUser = appUserEntityService.getAppUserByEmail(user
						.getEmail());
				session.setAttribute("appUser", appUser);
			}

			if (appUser == null) {
				DomainAdmin domainAdmin = domainAdminManager
						.getDomainAdminByDomainName(CommonWebUtil
								.getDomain(user.getEmail()));
				if (domainAdmin == null) {
					Boolean isAdmin = contactsService.isAdmin(user.getEmail());
					if (isAdmin) {
						AppUserEntity appUserEntity = new AppUserEntity(
								user.getUserId(), user.getEmail(), "", "",
								user.getNickname(),
								CommonWebUtil.getDomain(user.getEmail()));
						appUserEntityService.createAppUser(appUserEntity);
						createDomainAdmin(user.getEmail());

					} else {
						String adminEmail = contactsService
								.getAdminEmailForFirstTimeByLoginWithAnyUserOfDomain(user
										.getEmail());
						System.out
								.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Admin Email is : "
										+ adminEmail);
						AppUserEntity appUserEntity = new AppUserEntity(
								CommonWebUtil.getUserId(adminEmail),
								adminEmail, "", "", "",
								CommonWebUtil.getDomain(user.getEmail()));
						appUserEntityService.createAppUser(appUserEntity);
						createDomainAdmin(adminEmail);

						/*
						 * resp.getWriter() .print(
						 * "An admin must log in the first time from a new domain"
						 * );
						 */
					}
					resp.sendRedirect("/resource/createGroupHome.do");

				} else {
					AppUserEntity appUserEntity = new AppUserEntity(
							user.getUserId(), user.getEmail(), "", "",
							user.getNickname(), CommonWebUtil.getDomain(user
									.getEmail()));
					appUserEntityService.createAppUser(appUserEntity);
					chain.doFilter(request, response);
				}

			} else {
				chain.doFilter(request, response);
			}

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

	private void createDomainAdmin(String adminEmail) {
		DomainAdmin domainAdmin = new DomainAdmin();
		domainAdmin.setAdminEmail(adminEmail);
		domainAdmin.setDomainName(CommonWebUtil.getDomain(adminEmail));
		domainAdmin.setRegisteredDate(new Date());
		domainAdmin.setTotalCounts(0);
		domainAdmin.setAccountType(AccountType.FREE.toString());
		try {
			domainAdminManager.createDomainAdmin(domainAdmin);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private FilterConfig filterConfig;

	private String[] skipResources;

	@Override
	public void init(FilterConfig config) throws ServletException {
		filterConfig = config;
		skipResources = StringUtils.split(
				filterConfig.getInitParameter("skipResources"), ",");
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
				.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(this, "appUserEntityService");

		autowireCapableBeanFactory.configureBean(this, "contactsService");

		autowireCapableBeanFactory.configureBean(this, "setManager");

		autowireCapableBeanFactory.configureBean(this, "valueManager");
		autowireCapableBeanFactory.configureBean(this, "domainAdminManager");

	}

}
