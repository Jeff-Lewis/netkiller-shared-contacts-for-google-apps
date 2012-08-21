package com.metacube.ipathshala.view.validators;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.Field;

import com.google.gdata.data.analytics.Property;
import com.metacube.ipathshala.util.AppLogger;

/**
 * 
 * @author saurabh
 *
 */
public class DateValidatorForEvent {
	private static final long serialVersionUID = 7365451386579758836L;

	private static final AppLogger log = AppLogger.getLogger(DateValidator.class);

	private static final String FROM_DATE_FIELD_NAME = "fromDateField";
	
	private static final String TO_DATE_FIELD_NAME = "toDateField";
	
	public static boolean validateEndDateForEvent(Object bean, Field field) {
		log.debug("Validating (Comparing) dates.");
		// Fetch the end date value.

		Date beginDate = null;
		Date endDate = null;
		boolean validationResult=true;
		try{
			beginDate  = (Date)PropertyUtils.getProperty(bean, field.getVarValue(FROM_DATE_FIELD_NAME));
			endDate = (Date) PropertyUtils.getProperty(bean, field.getVarValue(TO_DATE_FIELD_NAME));
		}catch (IllegalAccessException e) {
			log.error("Error accessing date properties", e);
		} catch (InvocationTargetException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error("No Date property found in the bean", e);
		} catch(ClassCastException ex)	{
			log.error("Property is not of type Date", ex);
		}
		
		if (beginDate == null || endDate == null) {
			return true;
		}
		
		if((beginDate.compareTo(endDate)==0)){
			validationResult = isTimeSame(beginDate, endDate);
		}
		
		return validationResult;
	}
	

	@SuppressWarnings("deprecation")
	private static boolean isTimeSame(Date beginDate,Date endDate){
		if(beginDate.getHours()==18 && beginDate.getMinutes()==30 && beginDate.getSeconds()==0){
			return true;
		}else if(beginDate.getHours()==endDate.getHours() && beginDate.getMinutes()==endDate.getMinutes()
				&& beginDate.getSeconds()==endDate.getSeconds()){
			return false;
		}else{
			return true;
		}
	}
}
