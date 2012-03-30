<%@ page language="java" contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%
java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%><%
java.util.Map result = (java.util.Map)request.getAttribute("result");
String code = "";
String message = "";
if(result != null){
	code = com.netkiller.util.CommonUtil.getNotNullString((String)result.get("code"));
	message = com.netkiller.util.CommonUtil.getNotNullString((String)result.get("message"));
}
logger.info("code: " + code);
logger.info("message: " + message);
%><?xml version="1.0" encoding="UTF-8"?><response><code><%=code%></code><message><%=message%></message></response>