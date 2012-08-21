package com.metacube.ipathshala.exception.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.metacube.ipathshala.util.AppLogger;

/**
 * This exception resolver extends the spring SimpleMappingExceptionResolver to
 * override the following functionality:
 * 
 * 1. To log exception as error.
 * 
 * @author sabir
 * 
 */
public class IpathshalaExceptionResolver extends SimpleMappingExceptionResolver {

	private AppLogger errorLogger = AppLogger
			.getLogger(IpathshalaExceptionResolver.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver
	 * #logException(java.lang.Exception, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		if (this.errorLogger != null) {
			this.errorLogger.error(buildLogMessage(ex, request), ex);
		}
	}

	/**
	 * Set the log category for error logging.
	 * 
	 * @see AbstractHandlerExceptionResolver#setWarnLogCategory(String)
	 * @param loggerName
	 */
	public void setErrorLogCategory(String loggerName) {
		this.errorLogger = AppLogger.getLogger(loggerName);
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if(ex!=null && ex.getCause()!=null) {
			
			// If the exception is due to FileUpload, that is for the case when the size of the 
			// file is greater then the permissible limit, then redirect it to the request from where
			// it was coming with a flag indicating the same.
			if(ex.getCause().getClass().getSimpleName().equals("FileUploadIOException")){
				try {
					
					// if the exception is coming from the edit page, then entryEntityIdValue will contain the value of the id
					String entryEntityIdValue = "";
					if(request.getParameterMap().containsKey("entityId") && !StringUtils.isEmpty(request.getParameter("entityId"))){
						entryEntityIdValue = request.getParameter("entityId");
					}
					response.sendRedirect(request.getRequestURI()+"?FileUploadException=true&entityId="+entryEntityIdValue);
					return null ;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return super.doResolveException(request, response, handler, ex);
	}
}