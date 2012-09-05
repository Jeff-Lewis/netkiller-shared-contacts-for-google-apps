package com.netkiller.view.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.netkiller.UICommonConstants;
import com.netkiller.entity.Set;
import com.netkiller.entity.Value;
import com.netkiller.manager.SetManager;
import com.netkiller.manager.ValueManager;
import com.netkiller.util.AppLogger;

/**
 * @author dhruvsharma
 * 
 */
public class ListOfValuesCustomTag extends TagSupport {

	private static final AppLogger log = AppLogger
			.getLogger(ListOfValuesCustomTag.class);

	private String valueSetName;

	private String name;
	
	private String className;
	
	private String style;
	
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	private String selectedValueId;

	private WebApplicationContext webApplicationContext;

	public String getValueSetName() {
		return valueSetName;
	}

	public void setValueSetName(String valueSetName) {
		this.valueSetName = valueSetName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelectedValueId() {
		return selectedValueId;
	}

	public void setSelectedValueId(String selectedValueId) {
		this.selectedValueId = selectedValueId;
	}

	public int doStartTag() throws JspException {
		// Setting WebApplicationContext
		webApplicationContext = RequestContextUtils.getWebApplicationContext(
				pageContext.getRequest(), pageContext.getServletContext());
		// Fetching the SetService Bean from the WebApplicationContext manually
		SetManager setManager = (SetManager) webApplicationContext
				.getBean("setManager");
		// Fetching ValueService Bean from the WebApplicationContext manually
		ValueManager valueManager = (ValueManager) webApplicationContext
				.getBean("valueManager");
		JspWriter out = pageContext.getOut();
		Set set = setManager.getBySetName(valueSetName);
		// checking if the set exists, otherwise display proper message in the
		// dropdown
		if (set != null) {
			List<Value> valueList = (List<Value>) valueManager
					.getValueBySetKey(set);
			// checking if values exist in the particular set otherwise display
			// proper message
			if (valueList != null && valueList.size() != 0) {
				Key selectedValueKey = null;
				if (selectedValueId != null && selectedValueId != "") {
					selectedValueKey = KeyFactory.createKey(Value.class
							.getSimpleName(), Long.parseLong(selectedValueId));
				}
				try {
					out.print("<select id='" + name + "' name='" + name + "' class='" + className +"' style='" +style+ "'>");
					out.print("<option value='"
							+ UICommonConstants.LOV_NULL_VALUE
							+ "'>Not Selected</option>");
					for (Iterator<Value> valueListIterator = valueList
							.iterator(); valueListIterator.hasNext();) {
						Value currentValue = (Value) valueListIterator.next();
						Key currentValueKey = currentValue.getKey();
						if (selectedValueKey != null
								&& currentValueKey.equals(selectedValueKey)) {
							out.print("<option selected='selected' value='"
									+ currentValueKey.getId() + "'>"
									+ currentValue.getValue() + "</option>");
						} else {
							out.print("<option value='"
									+ currentValueKey.getId() + "'>"
									+ currentValue.getValue() + "</option>");
						}
					}
					out.print("</select>");
				} catch (IOException e) {
					String message = "Unable to processs custom JSP tag";
					log.error(message);
					JspException jspexception = new JspException(e);
					throw jspexception;
				}
			} else {
				try {
					out.print("<select id='" + name + "' name='" + name + "'>");
					out.print("<option value='"
							+ UICommonConstants.LOV_NULL_VALUE
							+ "'>No Values Exist</option></select>");
				} catch (IOException e) {
					String message = "Unable to processs custom JSP tag-No Values Exist For particular Set";
					log.error(message);
					JspException jspexception = new JspException(e);
					throw jspexception;
				}
			}
		} else {
			try {
				out.print("<select id='" + name + "' name='" + name + "'>");
				out.print("<option value='"
						+ UICommonConstants.LOV_NULL_VALUE
						+ "'>No Set Exists</option></select>");
			} catch (IOException e) {
				String message = "Unable to processs custom JSP tag-No Set Exists For Particular valueSetName";
				log.error(message);
				JspException jspexception = new JspException(e);
				throw jspexception;
			}
		}
		return (SKIP_BODY);
	}

}
