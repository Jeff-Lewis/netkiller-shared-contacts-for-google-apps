<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.netkiller.util.CommonUtil"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="java.util.logging.Logger"%>

<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%

	String userEmail = "";
	String adminUserName = "";
	String logoutUrl = "";

	Map<String, Object> result = (Map<String, Object>)request.getAttribute("result");
	
	if(result != null){
		adminUserName = CommonUtil.getNotNullString((String)result.get("adminUserName"));
		userEmail = CommonUtil.getNotNullString((String)result.get("userEmail"));
		logoutUrl = CommonUtil.getNotNullString((String)result.get("logoutUrl"));
	}
	
	logger.info("logoutUrl ===> " + logoutUrl);
	
	boolean isAdmin = false;
	if(result!=null){
		 isAdmin = (Boolean)result.get("isUserAdmin");;
	}
%>



<%
String page1 = CommonWebUtil.getParameter(request, "page"); // get the requested page
String rows = CommonWebUtil.getParameter(request, "rows"); // get how many rows we want to have into the grid		
String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby Sorting criterion. The only supported value is lastmodified.
String sord = CommonWebUtil.getParameter(request, "sord"); // ascending or descending.
logger.info("### PARAMS: page: " + page1 + ", " + "rows: " + rows + ", " + "sidx: " + sidx + ", " + "sord: " + sord);

StringBuffer queryParams = new StringBuffer("");

if( !page1.equals("") || !rows.equals("") || !sidx.equals("") || !sord.equals("") ){
	queryParams.append("page=");
	queryParams.append(page1);
	queryParams.append("&rows=");
	queryParams.append(rows);
	queryParams.append("&sidx=");
	queryParams.append(sidx);
	queryParams.append("&sord=");
	queryParams.append(sord);		
}

String queryString = "";
if(!queryParams.toString().equals("")){
	queryString = "&" + queryParams.toString();
}
%>


<div style="text-align:center;font-size:20px;font-family:Arial;font-weight:bold;color:#3B4990" width="1024px">
<table width="100%" border="0">
<tr>
<td align="left">
<a href="/sharedcontacts/main.do?cmd=list"><img src="/img/sharedcontacts_sm.jpg"/></a>
</td>
<td align="right" valign="bottom" style="font-size:13px">
<%= userEmail %>  
&nbsp;|&nbsp;
<%if(userEmail.equals("swjung@netkiller.com")
		|| userEmail.equals("harryj@netkiller.com")
		|| userEmail.equals("mjkim@netkiller.com")
		|| userEmail.equals("ys.kim@netkiller.com")
		|| userEmail.equals("ashley@netkiller.com")
		|| userEmail.equals("jszin@netkiller.com")
		
	) {%>
<a href="/sharedcontacts/main.do?cmd=customers" style="text-decoration: none;">Customers</a>
&nbsp;|&nbsp;
<%} %>
 <% if(isAdmin){ %>
 <a style="text-decoration: none;" href="/sharedcontacts/main.do?cmd=authorizeForm">Manage Users</a>
&nbsp;|&nbsp;
 <a style="text-decoration: none;" href="javascript:deleteDuplicate()">Delete Duplicate Groups</a>
&nbsp;|&nbsp;
 <%} %>
<% if( !logoutUrl.equals("") ){ %>
<a href="<%= logoutUrl %>" style="text-decoration: none;">Sign out</a>&nbsp;&nbsp;
<% } %>
</td>
</tr>
</table>
</div>
<!-- 
<div style="text-align:left;font-size:12px;font-family:Arial;background-color:#d0e4fe;">
<table width="100%" border="0">
<tr>
<td>
&nbsp;&nbsp;<a href="/sharedcontacts/main.do?cmd=list" style="text-decoration: none;">LIST</a>
<% if(isAdmin){ %>
 &nbsp; | &nbsp;
<a href="/sharedcontacts/main.do?cmd=create" style="text-decoration: none;">CREATE</a> &nbsp; | &nbsp;
<a href="/sharedcontacts/main.do?cmd=multicreate" style="text-decoration: none;">MULTIPLE CREATE</a>
<% } %>
</td>
<td align="right">
</td>
</table>
</div>
-->