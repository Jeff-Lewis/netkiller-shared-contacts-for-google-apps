/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.metacube.ipathshala.view.validators;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Var;
import org.apache.commons.validator.util.ValidatorUtils;

import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.entity.EvaluationScheme;
import com.metacube.ipathshala.util.AppLogger;

/**
 * Contains validation methods for different unit type validations.
 */
public class CustomTypeValidator {

	private static final AppLogger log = AppLogger.getLogger(CustomTypeValidator.class);

	/**
	 * Checks if the field can be successfully converted to a <code>byte</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>byte</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Byte validateByte(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatByte(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>byte</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>byte</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Byte validateByte(Object bean, Field field, Locale locale) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatByte(value, locale);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>short</code>
	 * . EmailValidator
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>short</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Short validateShort(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatShort(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>short</code>
	 * .
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>short</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Short validateShort(Object bean, Field field, Locale locale) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatShort(value, locale);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>int</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>int</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Integer validateInt(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (GenericValidator.isBlankOrNull(value))
			return (Integer) 0;
		return GenericTypeValidator.formatInt(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>int</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>int</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Integer validateInt(Object bean, Field field, Locale locale) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (GenericValidator.isBlankOrNull(value))
			return (Integer) 0;
		return GenericTypeValidator.formatInt(value, locale);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>long</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>long</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Long validateLong(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (GenericValidator.isBlankOrNull(value))
			return (long) 0;
		return GenericTypeValidator.formatLong(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>long</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>long</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Long validateLong(Object bean, Field field, Locale locale) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (GenericValidator.isBlankOrNull(value))
			return (long) 0;
		return GenericTypeValidator.formatLong(value, locale);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>float</code>
	 * .
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>float</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Float validateFloat(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatFloat(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>float</code>
	 * .
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>float</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Float validateFloat(Object bean, Field field, Locale locale) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatFloat(value, locale);
	}

	/**
	 * Checks if the field can be successfully converted to a
	 * <code>double</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>double</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Double validateDouble(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatDouble(value);
	}

	/**
	 * Checks if the field can be successfully converted to a
	 * <code>double</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>double</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Double validateDouble(Object bean, Field field, Locale locale) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatDouble(value, locale);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>date</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>date</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Date validateDate(Object bean, Field field, Locale locale) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return GenericTypeValidator.formatDate(value, locale);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>date</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>date</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static Date validateDate(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		String datePattern = field.getVarValue("datePattern");
		String datePatternStrict = field.getVarValue("datePatternStrict");

		Date result = null;
		if (datePattern != null && datePattern.length() > 0) {
			result = GenericTypeValidator.formatDate(value, datePattern, false);
		} else if (datePatternStrict != null && datePatternStrict.length() > 0) {
			result = GenericTypeValidator.formatDate(value, datePatternStrict, true);
		}

		return result;
	}

	/**
	 * Checks if the field is required.
	 * 
	 * @return boolean If the field isn't <code>null</code> and has a length
	 *         greater than zero, <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateRequired(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return !GenericValidator.isBlankOrNull(value);
	}

	/**
	 * Checks if the field is a valid Email Address or not .
	 * 
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field is valid Email <code>true</code> is
	 *         returned. Otherwise <code>false</code>.
	 */
	public static boolean validateEmail(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		boolean isAddressValid = true;
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)");
		if (!GenericValidator.isBlankOrNull(value)) {
			isAddressValid = pattern.matcher(value).matches();
		}
		return isAddressValid;
	}

	public static boolean validateUserId(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		boolean isValidUserId = false;
		Pattern pattern = Pattern.compile("^[a-z0-9._-]{4,20}$");
		if (!StringUtils.isNumeric(value) && pattern.matcher(value).matches()) {
			isValidUserId = true;
		}
		return isValidUserId;
	}
	public static boolean isAllDayEvent(Object bean, Field field) throws Exception, InvocationTargetException, NoSuchMethodException {
		Boolean isAllDay=Boolean.valueOf((String) PropertyUtils.getProperty(bean, field.getVarValue("isAllDayEvent")));
		Date toDate = (Date) PropertyUtils.getProperty(bean, field.getProperty());
		boolean returnValue = true;
		if(!isAllDay && toDate == null){
				returnValue = false;
		}
		return returnValue;
	}

	/**
	 * Method used to validate AutoComplete Text Box.
	 * 
	 * @param bean
	 *            Java Bean whose properties are to be validated.
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean validateDefaultAcademicYear(Object bean, Field field) {

		log.debug("validating autocomplete");

		Boolean isDefaultAcademicYear = false;
		Boolean isActiveAcademicYear = false;
		Var varActiveAcademicYear = null;

		try {
			// key business key
			isDefaultAcademicYear = (Boolean) PropertyUtils.getProperty(bean, field.getProperty());
			if (field.getVars() != null) {
				Collection<Var> varCollection = field.getVars().values();
				for (Iterator<Var> varCollectionIterator = varCollection.iterator(); varCollectionIterator.hasNext();) {
					varActiveAcademicYear = (Var) varCollectionIterator.next();
					if (varActiveAcademicYear != null) {
						isActiveAcademicYear = (Boolean) PropertyUtils.getProperty(bean,
								varActiveAcademicYear.getValue());
						if (!isActiveAcademicYear && isDefaultAcademicYear) {
							PropertyUtils.setProperty(bean, varActiveAcademicYear.getValue(), !isActiveAcademicYear);
							return false;
						} else {
							return true;
						}
					}
				}
			}
			// check for above use cases

		} catch (IllegalAccessException e) {
			String message = "Unable to validate AcademicYear";
			log.error(message, e);
		} catch (InvocationTargetException e) {
			String message = "Unable to validate AcademicYear";
			log.error(message, e);
		} catch (NoSuchMethodException e) {
			String message = "Unable to validate AcademicYear";
			log.error(message, e);
		}

		return false;
	}
	
	/**
	 * Methos used to validate the working Days
	 * 
	 * @param bean
	 * @param field
	 * @return
	 */
	public static boolean validateWorkingDays(Object bean,Field field){
		Boolean isValidWorkingDays = false;
	    
		
		
		
		return false;
		
	}
	
	

	/**
	 * Method used to validate AutoComplete Text Box.
	 * 
	 * @param bean
	 *            Java Bean whose properties are to be validated.
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean validatePrimaryContact(Object bean, Field field) {

		log.debug("validating autocomplete");

		Boolean isFatherPrimaryContact = false;
		String fatherFirstName = null;
		String fatherLastName = null;
		String motherFirstName = null;
		String motherLastName = null;
		Var currentVariable = null;

		try {
			// key business key
			isFatherPrimaryContact = (Boolean) PropertyUtils.getProperty(bean, field.getProperty());
			if (field.getVars() != null) {
				Collection<Var> varCollection = field.getVars().values();
				for (Iterator<Var> varCollectionIterator = varCollection.iterator(); varCollectionIterator.hasNext();) {
					currentVariable = (Var) varCollectionIterator.next();
					if (currentVariable != null) {
						if (currentVariable.getName().equals("fatherFirstName")) {
							fatherFirstName = (String) PropertyUtils.getProperty(bean, currentVariable.getValue());
						}
						if (currentVariable.getName().equals("fatherLastName")) {
							fatherLastName = (String) PropertyUtils.getProperty(bean, currentVariable.getValue());
						}
						if (currentVariable.getName().equals("motherFirstName")) {
							motherFirstName = (String) PropertyUtils.getProperty(bean, currentVariable.getValue());
						}
						if (currentVariable.getName().equals("motherLastName")) {
							motherLastName = (String) PropertyUtils.getProperty(bean, currentVariable.getValue());
						}
						
					}
				}
				if (isFatherPrimaryContact) {
					if (fatherFirstName != null && !StringUtils.isEmpty(fatherFirstName))
						return true;
					else
						return false;
					/*
					 * PropertyUtils.setProperty(bean,
					 * currentVariable.getValue(),!fatherFirstName);
					 */

				} else {
					if (motherFirstName != null && !StringUtils.isEmpty(motherFirstName))
						return true;
					else
						return false;
					
				}
			}
			// check for above use cases

		} catch (IllegalAccessException e) {
			String message = "Unable to validate AcademicYear";
			log.error(message, e);
		} catch (InvocationTargetException e) {
			String message = "Unable to validate AcademicYear";
			log.error(message, e);
		} catch (NoSuchMethodException e) {
			String message = "Unable to validate AcademicYear";
			log.error(message, e);
		}

		return false;
	}

	
	/**
	 * validate if grading scale is numeric
	 * @param bean
	 * @param field
	 * @return
	 * @throws Exception
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	
	//TODO change constant value according to scale name for grade or numeric scale
	public static boolean isNotNumericScale(Object bean, Field field) throws Exception, InvocationTargetException, NoSuchMethodException {
		EvaluationScheme evaluationScheme = (EvaluationScheme)bean;
		boolean returnValue = true;
		if(evaluationScheme.getSchemeEvalType()!=null){
			if(evaluationScheme.getSchemeEvalType().getValue().equalsIgnoreCase(UICommonConstants.EVALUATION_SCHEME_GRADING) && evaluationScheme.getGradingScale()==null){
				returnValue = false;
			}
		}
		return returnValue;
	}
	
	public static boolean isNumericScale(Object bean, Field field) throws Exception, InvocationTargetException, NoSuchMethodException {
		EvaluationScheme evaluationScheme = (EvaluationScheme)bean;
		boolean returnValue = true;
		
		if(evaluationScheme.getSchemeEvalType()!=null){
			if(evaluationScheme.getSchemeEvalType().getValue().equalsIgnoreCase(UICommonConstants.EVALUATION_SCHEME_NUMERIC) && evaluationScheme.getPassPercentage()==null){
				returnValue = false;
		}}
		return returnValue;
	}	
}