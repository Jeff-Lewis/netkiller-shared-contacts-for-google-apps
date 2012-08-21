package com.metacube.ipathshala.view.taglib;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.view.navigation.BreadcrumbNode;

/**
 * @author dhruvsharma
 *
 */
public class BreadcrumbCustomTag extends TagSupport {

	private static final AppLogger log = AppLogger
	.getLogger(BreadcrumbCustomTag.class);
	
	@SuppressWarnings("unchecked")
	public int doStartTag () throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession currentSession=request.getSession(false);
		JspWriter out = pageContext.getOut();
		if (currentSession != null) {
			List<BreadcrumbNode> breadcrumblist = (LinkedList<BreadcrumbNode>) currentSession
					.getAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST);
			if (breadcrumblist != null) {
			try {
			for (int index=0;index<breadcrumblist.size();index++) {
				if (index==(breadcrumblist.size()-1)) {
					out.println("<b>"+breadcrumblist.get(index).getDisplayValue()+"</b>");
				} else {
				    out.println("<a href='"+breadcrumblist.get(index).getLinkValue()+"'><span class='blue-color breadcrumb-arrow'>"+breadcrumblist.get(index).getDisplayValue()+"</a></span>");	
				}
			}
			} catch (IOException e) {
				log.error("Failed in creating a breadcrumb tag");
				throw (new JspException(e));
			}
			}
		}
        return (SKIP_BODY);
	}
}
