package com.netkiller.view.validators;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Field;

import com.netkiller.util.AppLogger;

public class WorkingDaysValidator {
	private static final AppLogger log = AppLogger
			.getLogger(DateValidator.class);
	private static final String FROM_DATE_FIELD_NAME = "fromDateField";

	private static final String TO_DATE_FIELD_NAME = "toDateField";

	private static final String WORKING_DAYS_NAME = "workingDaysField";

	public static boolean validateWorkingDaysForEvent(Object bean, Field field) {
		Boolean isValidWorkingDays = false;
		log.debug("Validating Working Days.");
		// Fetch the end date value.

		Date fromDate = null;
		Date toDate = null;
		int difference = 0;
		int workingDays=0;
		try {
			fromDate = (Date) PropertyUtils.getProperty(bean,
					field.getVarValue(FROM_DATE_FIELD_NAME));
			toDate = (Date) PropertyUtils.getProperty(bean,
					field.getVarValue(TO_DATE_FIELD_NAME));
			if(fromDate != null && toDate != null){
			   difference = (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
			}else{
				isValidWorkingDays = true;
				return isValidWorkingDays;
            }
			String str = (String) PropertyUtils.getProperty(bean,
					field.getVarValue(WORKING_DAYS_NAME));
			if(!StringUtils.isBlank(str)){
			   workingDays = Integer.parseInt(str);
			}
			if (workingDays <= difference || StringUtils.isBlank(str)) {
				isValidWorkingDays = true;
				return isValidWorkingDays;
			}

		} catch (IllegalAccessException e) {
			log.error("Error accessing date properties", e);
		} catch (InvocationTargetException e) {
			log.error(e);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			log.error("No Date property found in the bean", e);
		}

		return isValidWorkingDays;

	}
}
