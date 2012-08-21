<%@ page import="com.metacube.ipathshala.util.AppLogger" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%--
	Part of Spring error handler for UI.
	Spring error handler framework will redirect any default error to this page, 
	this will handle all exception for which mapping is not defined (please see ipathshala-servlet.xml --> SimpleMappingExceptionResolver).
 --%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
<link type="text/css" rel="stylesheet" media="handheld,print,projection,screen,tty,tv"
	href="/css/themes/common.css">
<link rel="stylesheet" type="text/css" media="screen" href="/css/main.css" />
</head>
<body>
<% AppLogger log = AppLogger.getLogger("CommonErrorPage");
java.lang.Exception ex = (Exception)request.getAttribute("exception") ;
log.error(ex);%>
<!-- <%= request.getAttribute("exception")!=null?request.getAttribute("exception").toString():request.getAttribute("exception")%> -->
<div class="errorMessage">
<table>
	<tr>
		<td><img src="/images/icons/messages/error_icon.png" /></td>
		<td class="errorMessage">Some error occurred while processing the
		request, please report this incident to system administrator.</td>
	</tr>
</table>
</div>

<%--
	'exception' model attribute placed by spring.  
--%>
 
<form:form modelAttribute="exception">
	<!-- form:input path="message"/ -->
</form:form>
</body>
</html>