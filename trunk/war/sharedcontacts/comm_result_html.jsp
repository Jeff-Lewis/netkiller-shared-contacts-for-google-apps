<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.netkiller.util.CommonUtil"%>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>

<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%  logger.info("-- comm_result_html.jsp --"); %>

<%
	Map result = (Map)request.getAttribute("result");
	String code = "";
	String message = "";
	if(result != null){
		code = CommonUtil.getNotNullString((String)result.get("code"));
		message = CommonUtil.getNotNullString((String)result.get("message"));
	}
	logger.info("code: " + code);
	logger.info("message: " + message);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>
<script type="text/javascript">
$(document).ready(function() {
	var code = "<%= code %>";
	var message = "<%= message %>";
	if(code != "success"){
		alert(message);
	}
	else{
		alert(message);
		//parent.window.location.href = "/sharedcontacts/main.do?cmd=list";
	}	
});
</script>

<!-- Google Analytics  -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-32320031-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
</head>
<body>
code: <%= code %><br/>
message: <%= message %>
</body>
</html>