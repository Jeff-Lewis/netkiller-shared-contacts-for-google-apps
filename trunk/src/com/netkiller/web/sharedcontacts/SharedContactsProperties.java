package com.netkiller.web.sharedcontacts;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.netkiller.common.jdo.PMF;
import com.netkiller.common.jdo.Property;
import com.netkiller.util.CommonWebUtil;

public class SharedContactsProperties implements Controller{

	protected final Logger logger = Logger.getLogger(getClass().getName());
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 웹브라우져 URL 요청시 프레임워크에 의해 자동 실행, 각종 요청을 처리
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		ModelAndView mnv = null;		
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    if (user == null) {
	    	String loginURL = userService.createLoginURL(request.getRequestURI());
	    	response.sendRedirect(loginURL);  //로그인 안되어 있을 경우 로그인 페이지로 리다이렉트
	    }
	    else{
	    	String cmd = CommonWebUtil.getParameter(request, "cmd");
			logger.info("cmd:" + cmd);
			
			if( cmd.equals("") ){
				cmd = "view";
			}
						
			if( cmd.equals("view") || cmd.equals("edit") ){
				mnv = view(request, response, cmd);
				
			}else if( cmd.equals("save") ) {
				Enumeration enu = request.getParameterNames();
				while(enu.hasMoreElements()){	
					String name = (String)enu.nextElement();
					save(name, request.getParameter(name));
				}
				
				mnv = view(request, response, "view");
			}
	    }
	    
	    return mnv;
	}
	
	private ModelAndView view(HttpServletRequest request, HttpServletResponse response, String cmd) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from "+ Property.class.getName()+" order by seq asc";
	    javax.jdo.Query nQuery = pm.newQuery(query);
	    
	    List<Property> properties = (List<Property>) nQuery.execute();
	    if (properties.isEmpty() || properties.size() < Property.propertyId.length){
	    	initProperty(properties);
	    	properties = (List<Property>) nQuery.execute();
	    }
	    
	    Map<String, Object> result = new HashMap<String, Object>();
		result.put("properties", properties);
		result.put("cmd", cmd);
	    
		ModelAndView mnv = new ModelAndView("/sharedcontacts/propertiesEdit", "result", result);
		return mnv;
	}
	
	private void save(String id, String value) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.currentTransaction().begin();
		try {
			Property p = pm.getObjectById(Property.class, id);
			p.setPropValue(value);
			pm.makePersistent(p);
			pm.currentTransaction().commit();
		} catch (Exception e) {
			pm.currentTransaction().rollback();
		} 
		finally {
			pm.close();
		}
	}
	
	private void initProperty(List<Property> properties) {
		Property property = null;
		PersistenceManager pm = null;
		for(int i=0; i<Property.propertyId.length; i++){
			if( properties==null || !existProperty(properties, Property.propertyId[i]) ){
				pm = PMF.get().getPersistenceManager();
		        try {
		        	property = new Property();
		        	property.setPropId(Property.propertyId[i]);
		        	property.setPropValue("");
		        	property.setSeq((i+1)+"");
		            pm.makePersistent(property);
		        } finally {
		            pm.close();
		        }				
			}
		}
	}

	private boolean existProperty(List<Property> properties, String properId){
		for(int i=0; i<properties.size(); i++){
			if( properties.get(i).getPropId().equals(properId) ){
				return true;
			}
		}
		
		return false;
	}

}
