package com.netkiller.dataupload.entityprocessor;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.dataupload.DataUploadEntityProcessor;
import com.netkiller.entity.Set;
import com.netkiller.entity.Value;
import com.netkiller.manager.SetManager;
import com.netkiller.manager.ValueManager;
import com.netkiller.security.impl.GoogleUserService;
import com.netkiller.util.AppLogger;
import com.netkiller.util.LocalizationUtil;
import com.netkiller.util.UserUtil;

public abstract class AbstractDataUploadEntityProcessor implements DataUploadEntityProcessor{
	
	private static final AppLogger log = AppLogger.getLogger(AbstractDataUploadEntityProcessor.class);
	
	@Autowired
	private GoogleUserService userService;
	
	@Autowired
	private SetManager setManager;
	
	@Autowired
	private ValueManager valueManager;
	
	private List<String> getAvailableUserIds(List<String> userIds, int numberOfUserIds) throws AppException {
		List<String> availableUserIds = new ArrayList<String>();
		for (int i = 0; availableUserIds.size() < numberOfUserIds && i < userIds.size(); i++) {
			String user = userIds.get(i);
			if (UserUtil.isValidUserId(user) && !userService.isAppUser(user)) {
				availableUserIds.add(user);
			}
		}
		return availableUserIds;
	}
	/**
	 * Get User ID to be assigned
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws AppException 
	 */
	protected String getUserId(String firstName, String lastName) throws AppException{		
		int numberOfUserIds = 1;
		String userId = "";
		List<String> userIds = new ArrayList<String>();
		java.util.Set<String> availableUserIds = new HashSet<String>();
		if (!(StringUtils.isBlank(firstName) )) {
			userIds.addAll(UserUtil.getRelatedUserIdSuggestions(firstName, lastName));
		}
		availableUserIds.addAll(getAvailableUserIds(userIds, numberOfUserIds));
		while (availableUserIds.size() != numberOfUserIds) {
			userIds = (List<String>) UserUtil.getRandomUserIdSuggestions(firstName, lastName, userId);
			availableUserIds.addAll(getAvailableUserIds(userIds, numberOfUserIds - availableUserIds.size()));
		}
		try{
			for(String userid: availableUserIds){			
					return userid;
			}	
		}catch(Exception ex){
			log.error("User Id not Found", ex);
		}
		return null;
		
	}
	

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws AppException
	 */
	protected boolean isUserAvailable(String userId) throws AppException{		
		return userService.isAppUser(userId);
	}
	
	/**
	 * utility method to get date from cell value
	 * @param value
	 * @return
	 */
	protected Date getDate(Object value){
		Date date = null;
		
		if(value==null)
			return null;
		
	
		if(value instanceof Date)
			date = (Date)value;
			else
			date = LocalizationUtil.parseDate(value.toString(),null);
		
		return date;
	}
	
	/**
	 * utility method to get boolean from cell value
	 * @param value
	 * @return
	 */
	protected Boolean getBoolean(Object value){
		Boolean bool = Boolean.FALSE;
		
		if(value==null)
			return bool;
		
		if(value instanceof Boolean)		
			return (Boolean)value;
		
		bool = Boolean.parseBoolean(value.toString());
		
		return bool;
		
	}
	
	/**
	 * utility method to get double from cell value
	 * @param value
	 * @return
	 */
	private Double getDouble(Object value){
		Double val = null;
		
		if(value==null)
			return null;
		
		if(value instanceof Double)		
			return (Double)value;
		try{
		val = Double.parseDouble(value.toString());
		}catch(NumberFormatException pe){
			
		}
		
		return val;
		
	}
	
	/**
	 * utility method to get Integer from cell value
	 * @param value
	 * @return
	 */
	protected Long getLong(Object value){
		Long val = null;
		
		if(value==null)
			return null;
		if(value instanceof Double){
			return ((Double) value).longValue();
		}
		try{
			val = Long.parseLong(value.toString());
		}catch(NumberFormatException pe){
			
		}
		
		return val;
		
	}
	
	/**
	 * 
	 * @param setName
	 * @param valueRetrieved
	 * @return
	 */
	protected Key getSetValueKey(String setName, String valueRetrieved){
		Key key = null;
		
		if(valueRetrieved==null || valueRetrieved.trim().equals("")){
			return key;}
		if(valueRetrieved.equalsIgnoreCase("M")){
			valueRetrieved = "Male";
		}else if(valueRetrieved.equalsIgnoreCase("F")){
			valueRetrieved = "Female";
		}
		Set set = setManager.getBySetName(setName);
		if(set!=null){
			Collection<Value> values = valueManager.getValueBySetKey(set);
			if(values!=null && !values.isEmpty()){
			for(Value value : values){
				if(value.getValue()!=null && value.getValue().equalsIgnoreCase(valueRetrieved.trim())){
					key = value.getKey(); break;
				}
			}
				
		  }
			
		}		
		
		return key;
	}
	
	/**
	 * get State value
	 * @param valueRetrieved
	 * @return
	 */
	protected Key getState(String valueRetrieved){
		return getSetValueKey("State",valueRetrieved);
	}
	
	/**
	 * get City value
	 * @param valueRetrieved
	 * @return
	 */
	protected Key getCity(String valueRetrieved){
		return getSetValueKey("City",valueRetrieved);
	}
	
	/**
	 * get Blood Group Key
	 * @param valueRetrieved
	 * @return
	 */
	
	protected Key getBloodGroup(String valueRetrieved){
		return getSetValueKey("BloodGroup",valueRetrieved);
	}
	
	
	/**
	 * get Class Level value
	 * @param valueRetrieved
	 * @return
	 */
	protected Key getClassLevel(String valueRetrieved){
		return getSetValueKey("ClassLevel",valueRetrieved);
	}
	
	/**
	 * get Gender value
	 * @param valueRetrieved
	 * @return
	 */
	protected Key getGender(String valueRetrieved){
		return getSetValueKey("Gender",valueRetrieved);
	}

}
