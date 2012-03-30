<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.netkiller.util.CommonUtil"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%
Map result = (Map)request.getAttribute("result");
String logoutUrl = "";
if(result != null){
	logoutUrl = CommonUtil.getNotNullString((String)result.get("logoutUrl"));
}
%>

<html>

<body>
Permission Error!&nbsp;&nbsp;&nbsp;&nbsp;
<% if( !logoutUrl.equals("") ){ %>
<a href="<%= logoutUrl %>">Logout</a>&nbsp;&nbsp;
<% } %>
</body>
</html>