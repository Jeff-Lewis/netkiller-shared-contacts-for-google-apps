package com.netkiller.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.UrlValidator;

import com.netkiller.UICommonConstants;
import com.netkiller.util.AppLogger;
import com.netkiller.view.navigation.BreadcrumbNode;

/**
 * @author dhruvsharma
 * 
 *         Abstract controller class, has a method which can be used to add
 *         bread crumbs
 * 
 */
public class AbstractController {

	private static final AppLogger log = AppLogger.getLogger(AbstractController.class);

	@SuppressWarnings("unchecked")
	public void addToNavigationTrail(String displayValue, boolean refresh, HttpServletRequest request,
			boolean includePostParameters, boolean overwriteExistingWithDiffrentQuery) {

		HttpSession currentSession = request.getSession(false);
		String linkValue = null;
		/* String queryString = request.getQueryString(); */

		// constructing the URL
		linkValue = constructRequestUri(includePostParameters, request, request.getRequestURI());
		/*
		 * if (queryString != null) { linkValue = request.getRequestURI() + "?"
		 * + queryString; } else { linkValue = request.getRequestURI(); }
		 */
		// checking if the session in null
		if (currentSession != null) {
			List<BreadcrumbNode> breadcrumblist = (LinkedList<BreadcrumbNode>) currentSession
					.getAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST);

			BreadcrumbNode newNode = new BreadcrumbNode(displayValue, linkValue);
			if (refresh) {
				// refreshing the old value from the session if a refresh is
				// requested
				List<BreadcrumbNode> list = new LinkedList<BreadcrumbNode>();
				BreadcrumbNode homeNode = getHomeNode(request);
				list.add(homeNode);
				list.add(newNode);
				currentSession.setAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST, list);
			} else {
				// refresh not requested
				// checking if the node which is to be added now already exists
				// in the linkedlist
				// if it does we will flush all the list ahead of this node
				int indx = -1;
				if (breadcrumblist != null) {
					if (!overwriteExistingWithDiffrentQuery) {
						if (breadcrumblist.contains(newNode)) {
							List<BreadcrumbNode> toRemove = new LinkedList<BreadcrumbNode>();
							for (int index = (breadcrumblist.indexOf(newNode) + 1); index < breadcrumblist.size(); index++) {
								toRemove.add(breadcrumblist.get(index));
							}
							breadcrumblist.removeAll(toRemove);
						} else {
							breadcrumblist.add(newNode);
						}
					} else {
						if ((indx = indexOfEquivalentUrlExcludingParameters(newNode, breadcrumblist)) != -1) {
							List<BreadcrumbNode> toRemove = new LinkedList<BreadcrumbNode>();
							for (int index = indx; index < breadcrumblist.size(); index++) {
								toRemove.add(breadcrumblist.get(index));
							}
							breadcrumblist.removeAll(toRemove);
							breadcrumblist.add(newNode);
						} else {
							breadcrumblist.add(newNode);
						}
					}

				} else {

					List<BreadcrumbNode> list = new LinkedList<BreadcrumbNode>();
					BreadcrumbNode homeNode = getHomeNode(request);
					list.add(homeNode);
					list.add(newNode);
					breadcrumblist = list;
					currentSession.setAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST, list);
				}

				// setting the attribute in the session apparently this is
				// nessecary for GAE otherwise
				// a change in breadcrumblist would automatically reflect in the
				// session as breadcrumblist
				// is nothing but a reference.
				currentSession.setAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST, breadcrumblist);
			}

		}
	}

	@SuppressWarnings("unchecked")
	public void removeFromNavigationTrail(HttpServletRequest request) {
		HttpSession currentSession = request.getSession(false);
		if (currentSession != null) {
			List<BreadcrumbNode> breadcrumblist = (LinkedList<BreadcrumbNode>) currentSession
					.getAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST);
			if (breadcrumblist != null) {
				if (breadcrumblist.size() > 1) {
					breadcrumblist.remove(breadcrumblist.size() - 1);
				}
				currentSession.setAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST, breadcrumblist);
			} else {
				breadcrumblist = new LinkedList<BreadcrumbNode>();
				breadcrumblist.add(getHomeNode(request));
				currentSession.setAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST, breadcrumblist);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void removeFromNavigationTrail(HttpServletRequest request, int numberToRemove) {
		HttpSession currentSession = request.getSession(false);
		if (currentSession != null) {
			List<BreadcrumbNode> breadcrumblist = (LinkedList<BreadcrumbNode>) currentSession
					.getAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST);
			if (breadcrumblist != null) {
				for (int index = 0; index < numberToRemove; index++) {
					breadcrumblist.remove(breadcrumblist.size() - 1);
				}
				currentSession.setAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST, breadcrumblist);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void redirectToPreviousBreadcrumb(HttpServletRequest request, HttpServletResponse response) {
		HttpSession currentSession = request.getSession(false);
		if (currentSession != null) {
			List<BreadcrumbNode> crumblist = (List<BreadcrumbNode>) currentSession
					.getAttribute(UICommonConstants.ATTRIB_BREADCRUMB_LIST);
			if (crumblist != null && crumblist.size() >= 2) {
				try {
					request.getRequestDispatcher(crumblist.get(crumblist.size() - 1).getLinkValue()).forward(request,
							response);
				} catch (ServletException e) {
					log.error("Cannot redirect from abstract controller", e);
				} catch (IOException e) {
					log.error("Cannot redirect from abstract controller", e);
				}
			} else if (crumblist.size() == 1) {
				try {
					response.sendRedirect("/index.do");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private BreadcrumbNode getHomeNode(HttpServletRequest request) {
		String homeLinkDisplayValue = "Home";
		int indexValue = request.getRequestURI().indexOf("/");
		String homeLinkValue = request.getRequestURI().substring(0, indexValue + 1) + "index.do";
		BreadcrumbNode homeNode = new BreadcrumbNode(homeLinkDisplayValue, homeLinkValue);
		return homeNode;
	}

	public String constructRequestUri(boolean includePostParameters, HttpServletRequest request, String requestUri) {
		String constructedURLWithParams;
		StringBuffer sb = new StringBuffer(requestUri);
		if (includePostParameters) {
			Enumeration en = request.getParameterNames();
			if (en.hasMoreElements()) {
				sb.append("?");
			}
			while (en.hasMoreElements()) {
				String paramName = (String) en.nextElement();
				sb.append(paramName);
				sb.append("=");
				sb.append(request.getParameter(paramName));
				if (en.hasMoreElements())
					sb.append("&");

			}
			constructedURLWithParams = sb.toString();
		} else {
			String queryString = request.getQueryString();
			// constructing the URL
			if (queryString != null) {
				constructedURLWithParams = request.getRequestURI() + "?" + queryString;
			} else {
				constructedURLWithParams = request.getRequestURI();
			}
		}

		return constructedURLWithParams;

	}

	private int indexOfEquivalentUrlExcludingParameters(BreadcrumbNode node, List<BreadcrumbNode> breadcrumbNodeList) {
		for (BreadcrumbNode currentNode : breadcrumbNodeList) {
			if (node != null) {
				String nodePath = getPathFromLink(node.getLinkValue());
				String currentNodePath = getPathFromLink(currentNode.getLinkValue());
				if (!StringUtils.isBlank(nodePath) && !StringUtils.isBlank(currentNodePath)) {
					if (nodePath.equals(currentNodePath)) {
						return breadcrumbNodeList.indexOf(currentNode);
					}
				}

			}

		}

		return -1;
	}

	private String getPathFromLink(String linkValue) {
		String path = null;
		if (!StringUtils.isBlank(linkValue)) {
			if (linkValue.indexOf('?') != -1) {
				path = linkValue.substring(0, linkValue.indexOf('?'));
			} else {
				path = linkValue;
			}
		}
		return path;
	}
}
