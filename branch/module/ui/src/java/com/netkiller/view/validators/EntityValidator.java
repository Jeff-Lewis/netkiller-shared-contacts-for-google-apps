/**
 *
 */
package com.netkiller.view.validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.Errors;
import org.xml.sax.SAXException;

import com.netkiller.util.AppLogger;

/**
 * A validator class to validate student object based on the validation rules
 * defined in a resource XML.
 *
 * @author vnarang
 */
public final class EntityValidator {

	private InputStream in = null;
	private ValidatorResources resources = null;

	private static final AppLogger log = AppLogger.getLogger(EntityValidator.class);

	public EntityValidator(final String xmlDefPath) throws IOException, SAXException {
		Resource resource = new ClassPathResource(xmlDefPath);

		try {
			// Create a new instance of a ValidatorResource, then get a stream
			// handle on the XML file with the actions in it, and initialize the
			// resources from it.
			in = resource.getInputStream();
			resources = new ValidatorResources(in);

		} catch (IOException ioex) {
			log.error("No resource named validator-definition.xml could be found in the classpath.",
							ioex);
			// errors.reject("error.general");
		} finally {
			// Make sure we close the input stream.
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * Method validates the entity with respect to the XML definition present in
	 * the class path.
	 *
	 * @param target
	 * @param errors
	 * @param formName
	 * @throws ValidatorException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void doValidate(Object target, Errors errors, String formName)
			throws ValidatorException {

		log.debug("---   Starting doValidate method.");

		Validator validator = new Validator(resources, formName);
		validator.setParameter(Validator.BEAN_PARAM, target);

		ValidatorResults results = validator.validate();

		populateErrors(errors, resources, target, results, formName);

		log.debug("---   Inside doValidate method.");
	}

	/**
	 * Method validates the target object against the form defined as per
	 * resource XML.
	 *
	 * @param target
	 * @param errors
	 * @param formName
	 */
	public void validate(Object target, Errors errors, String formName) {
		log.debug("Inside validate method.");
		try {
			doValidate(target, errors, formName);
		} catch (ValidatorException e) {
			log.error("Error valiating the form: " + formName, e);
			errors.reject("error.general");
		}
		log.debug("Validation completed.");
	}

	/**
	 * Method populates errors in the Errors object passed (by reference)
	 *
	 * @param errors
	 *            object to be populated with errors.
	 * @param resources
	 *            Resource constructed using the validation XML.
	 * @param bean
	 *            target bean to validate.
	 * @param results
	 *            validation results.
	 * @param formName
	 *            form name in resource XML against which validations are
	 *            performed.
	 */
	private void populateErrors(Errors errors, ValidatorResources resources,
			Object bean, ValidatorResults results, String formName) {

		log.debug("\n\nValidating:");
		log.debug(bean.toString());

		// Start by getting the form for the current locale and Bean.
		Form form = resources.getForm(Locale.getDefault(), formName);
		Field field = null;
		// Iterate over each of the properties of the Bean which had messages.
		Iterator propertyNames = results.getPropertyNames().iterator();
		while (propertyNames.hasNext()) {
			String propertyName = (String) propertyNames.next();
			field = form.getField(propertyName);
			
			log.debug("Iterating on property name:" + propertyName);
			// Get the result of validating the property.
			ValidatorResult result = results.getValidatorResult(propertyName);
			// Get all the actions run against the property, and iterate over
			// their names.
			Iterator<String> keys = result.getActions();
			while (keys.hasNext()) {
				String actName = keys.next();
				ValidatorAction action = resources.getValidatorAction(actName);
				log.debug("\nIterating on keys:" + action);
				if (!result.isValid(actName)) {
					if(!errors.hasFieldErrors(propertyName))
					errors.rejectValue(propertyName, field.getMsg(actName));
					log.debug("propertyName: " + propertyName + ",  -- msg: "
							+ field.getMsg(actName));
				}
			}
		}
	}
}
