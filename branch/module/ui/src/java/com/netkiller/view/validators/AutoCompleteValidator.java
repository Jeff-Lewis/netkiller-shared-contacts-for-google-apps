package com.netkiller.view.validators;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Var;

import com.netkiller.util.AppLogger;

/**
 * Class to perform AutoComplete validations. For every value chosen from the
 * AutoComplete, two fields are populated. 1) Text field containing Business key
 * for that entity. 2) Hidden field containing Primary key for that entity. This
 * Validator validates the following Use-Cases : 1) Both values are NULL :
 * Accept 2) Either of the value is NULL : Reject Remember that if the User
 * selects a value from the AutoComplete and then rubs it back, it will be
 * REJECTED.
 * 
 * @author kunal
 * 
 */
public class AutoCompleteValidator implements Serializable {

	private static final long serialVersionUID = 7365451386579758836L;

	private static final AppLogger log = AppLogger
			.getLogger(DateValidator.class);

	/**
	 * Method used to validate AutoComplete Text Box.
	 * 
	 * @param bean
	 *            Java Bean whose properties are to be validated.
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean validateAutoComplete(Object bean, Field field) {

		log.debug("validating autocomplete");

		String businessKey = null;
		String primaryKey = null;
		Var varPrimaryKey = null;

		try {
			// key business key
			businessKey = (String) PropertyUtils.getProperty(bean, field
					.getProperty());
			if (field.getVars() != null) {
				Collection<Var> varCollection = field.getVars().values();
				for (Iterator<Var> varCollectionIterator = varCollection
						.iterator(); varCollectionIterator.hasNext();) {
					varPrimaryKey = (Var) varCollectionIterator.next();
					if (varPrimaryKey != null) {
						primaryKey = (String) PropertyUtils.getProperty(bean,
								varPrimaryKey.getValue());
					}
				}
			}
			// check for above use cases
			if (!(GenericValidator.isBlankOrNull(primaryKey))
					&& (GenericValidator.isBlankOrNull(businessKey)))
				return false;
			else if ((GenericValidator.isBlankOrNull(primaryKey))
					&& !(GenericValidator.isBlankOrNull(businessKey)))
				return false;
			else
				return true;
		} catch (IllegalAccessException e) {
			String message = "Unable to validate autocomplete";
			log.error(message, e);
		} catch (InvocationTargetException e) {
			String message = "Unable to validate autosomplete";
			log.error(message, e);
		} catch (NoSuchMethodException e) {
			String message = "Unable to validate autocomplete";
			log.error(message, e);
		} catch (ClassCastException ex) {
			String message = "Unable to validate autocomplete";
			log.error(message, ex);
		}

		return false;
	}

}
