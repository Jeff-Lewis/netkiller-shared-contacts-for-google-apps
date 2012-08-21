package com.metacube.ipathshala.filter;

import java.io.IOException;
import java.util.Date;

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
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.security.AppUser;
import com.metacube.ipathshala.security.AppUserService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.PropertyUtil;

/**
 * Security filter that will take care of authentication.
 * 
 * @author sabir
 * 
 */
@Component
public class AuthenticationFilter implements Filter {

	private static final AppLogger log = AppLogger.getLogger(AuthenticationFilter.class);

	private FilterConfig filterConfig;

	@Autowired
	@Qualifier("GoogleUserService")
	private AppUserService appUserService;

	/**
	 * skip resources will tell filter to skip security check process on certain
	 * resoruces, this is required to avoid cyclic redirect in case of some
	 * unauthorised access.
	 */
	private String[] skipResources;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		log.debug("Inside authentication filter...");
		boolean requestOk = true;
		String LAST_ACCESED_TIME="LAST_ACCESED_TIME";

		/*
		 * Process required only if request is a type of http request.
		 */
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) req;
			String requestURI = request.getRequestURI();
			HttpSession session = request.getSession();
			Date lastAccessedTime = new Date();
			if(session.getAttribute(LAST_ACCESED_TIME)!=null)	{
			lastAccessedTime = (Date)session.getAttribute(LAST_ACCESED_TIME);
			}
			session.setAttribute(LAST_ACCESED_TIME, new Date());
			if (appUserService.isUserLoggedIn()) {
				if (!isDevLogoutAction(request)) {
					Long timeOut= getSessionInactiveTimeOut();
					AppUser user = (AppUser) session.getAttribute(AppUser.APPUSER_SESSION_VAR);
					//System.out.println("DIFFERENCE"+((new Date()).getTime() - session.getLastAccessedTime())+" Timeout:"+timeOut+" current time:"+(new Date()).getTime()+" last access:"+session.getLastAccessedTime());
					//log.error("DIFFERENCE"+(((new Date()).getTime() - lastAccessedTime.getTime()+" Timeout:"+timeOut+" current time:"+(new Date()).getTime()+" last access:"+lastAccessedTime+"is session new:"+session.isNew())));
						if ((new Date()).getTime() - lastAccessedTime.getTime() >= timeOut * 1000 && lastAccessedTime.getTime()!=0) {
							String logoutUrl = appUserService.createLogoutURL(requestURI);
							requestOk = false;
							((HttpServletResponse) resp).sendRedirect(logoutUrl);
						} 
					if (user == null) {
							try {
								session.setAttribute(AppUser.APPUSER_SESSION_VAR, appUserService.getCurrentUser());
							//	session.setMaxInactiveInterval(timeOut.intValue());
							} catch (AppException e) {
								String logoutUrl = appUserService.createLogoutURL(requestURI);
								requestOk = false;
								((HttpServletResponse) resp).sendRedirect(logoutUrl);
							}
					}
				} else {
					// logout in-process
					session.invalidate();
				}

			} else {
				if (StringUtils.indexOfAny(requestURI, skipResources) < 0) {
					log.debug("User is not logged in.");
					String loginURL = appUserService.createLoginURL(requestURI);
					requestOk = false;
					((HttpServletResponse) resp).sendRedirect(loginURL);
				}
			}
		}
		if (requestOk) {
			chain.doFilter(req, resp);
		}
	}

	private Long getSessionInactiveTimeOut() {
		String timeOutString = null;
		try {
			timeOutString = PropertyUtil.getProperty("google.app.timeout");
		} catch (AppException e) {
			log.warn("Inactive Timeout not set");
		}
		Long timeOut = 1800L;
		if(!StringUtils.isBlank(timeOutString))	{
			timeOut = Long.valueOf(timeOutString);
		}
		return timeOut;
	}

	private boolean isDevLogoutAction(HttpServletRequest request) {
		String[] tokenizedURI = StringUtils.split(request.getRequestURI(), "/");
		Integer length = tokenizedURI.length;
		if (length > 1) {
			for (int index = 0; index < tokenizedURI.length; index++) {
				if (tokenizedURI[index].equals("logout") && tokenizedURI[index - 1].equals("_ah")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if user is logged in or not.
	 * 
	 * @param userService
	 *            the service to get user information.
	 * @return 'true' if user is logged in, else 'false'.
	 */

	@Override
	public void init(FilterConfig config) throws ServletException {
		filterConfig = config;
		skipResources = StringUtils.split(filterConfig.getInitParameter("skipResources"), ",");
		ServletContext servletContext = filterConfig.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(this, "GoogleUserService");
	}

}
