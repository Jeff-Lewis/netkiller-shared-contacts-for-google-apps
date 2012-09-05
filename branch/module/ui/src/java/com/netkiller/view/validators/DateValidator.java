/**
 *
 */
package com.netkiller.view.validators;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.Field;

import com.netkiller.util.AppLogger;

/**
 * Custom Date Validator utility class compares two dates and check if begin date comes before
 * endDate. The validator assumes that the fields have already been validated for being a date
 * type, so, this is not done again.
 *
 * @author vnarang
 */
public class DateValidator implements Serializable {
	private static final long serialVersionUID = 7365451386579758836L;

	private static final AppLogger log = AppLogger.getLogger(DateValidator.class);

	private static final String BEGIN_DATE_FIELD_NAME = "beginDateField";

	/**
	 * Method is used by Commons Validator plug-in to compare dates.
	 *
	 * @param bean Java Bean whose date properties are to be validated.
	 * @param field
	 * @return
	 */
	public static boolean validateDates(Object bean, Field field) {
		log.debug("Validating (Comparing) dates.");
		// Fetch the end date value.

		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = (Date) PropertyUtils.getProperty(bean, field.getProperty());
			endDate = (Date)PropertyUtils.getProperty(bean, field.getVarValue(BEGIN_DATE_FIELD_NAME));
			log.debug("beginDate: " + beginDate);
			log.debug("endDate:" + endDate);
		} catch (IllegalAccessException e) {
			log.error("Error accessing date properties", e);
		} catch (InvocationTargetException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error("No Date property found in the bean", e);
		} catch(ClassCastException ex)	{
			log.error("Property is not of type Date", ex);
		}

		// If either of the dates is blank pass the validation
		if (beginDate ==null || endDate == null) {
			return true;
		}
		
		// If both the dates are same, then show error
		if(beginDate.equals(endDate)) {
			return true;
		}
		
		log.debug("beginDate is before endDate? :" + beginDate.before(endDate));
		return (beginDate.before(endDate));
	}
		
	/**
	 * This method checks that the end date is always greater then the begin date
	 * @param bean
	 * @param field
	 * @return
	 */
	public static boolean validateEndDate(Object bean, Field field) {
		log.debug("Validating (Comparing) dates.");
		// Fetch the end date value.
		
		Date beginDate = null;
		Date endDate = null;

		try {
			endDate = (Date) PropertyUtils.getProperty(bean, field.getProperty());
			beginDate = (Date)PropertyUtils.getProperty(bean, field.getVarValue(BEGIN_DATE_FIELD_NAME));
			log.debug("beginDate: " + beginDate);
			log.debug("endDate:" + endDate);
		} catch (IllegalAccessException e) {
			log.error("Error accessing date properties", e);
		} catch (InvocationTargetException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error("No Date property found in the bean", e);
		} catch(ClassCastException ex)	{
			log.error("Property is not of type Date", ex);
		}
		
		
		// If either of the dates is blank pass the validation
		if (beginDate ==null || endDate == null) {
			return true;
		}
		
		// If both the dates are same, then show error
		if(beginDate.equals(endDate)) {
			return false;
		}
		
		log.debug("beginDate is before endDate? :" + beginDate.before(endDate));
		return (beginDate.before(endDate));
	}
}
