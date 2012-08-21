<%@ page language="java" contentType="text/html; charset=UTF-8"
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

<div class="errorMessage">
<table>
	<tr>
		<td><img src="/images/icons/messages/error_icon.png" /></td>
		<td class="errorMessage">You are not authorized to access this resource</td>
	</tr>
	<tr>
	<td></td>
	<td>
	    <a href="/index.do"><b>Go To Dashboard</b></a>
	</td>
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