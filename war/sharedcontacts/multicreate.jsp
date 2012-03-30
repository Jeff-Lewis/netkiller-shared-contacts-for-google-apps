<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.google.gdata.data.contacts.ContactEntry" %>
<%@ page import="com.google.gdata.data.extensions.*" %>
<%@ page import="com.netkiller.util.SharedContactsUtil" %>

<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>

<%  logger.info("-- multicreate.jsp --"); %>


<%
	Map result = (Map)request.getAttribute("result");
	ContactEntry contact = null;
	if(result != null){
		contact = (ContactEntry)result.get("contact");
	}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
<link rel='stylesheet' type='text/css' href='/css/main.css'/>
<link rel='stylesheet' type='text/css' href='/css/jquery-ui-1.8.12.custom.css'/>
<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css'/>
<link rel="stylesheet" type="text/css" href="/css/fileinput.css" />	
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>
<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
<script type="text/javascript">
$(document).ready ( function () {	
	$( "#file" ).fileinput();
	//$('#file').fileinput({
	//    buttonText: 'Browse Files...',
	//    inputText: 'Please press the button'
	//});
	$( "#Submit" ).button();
});

function reset(){
	//$( "#file" ).fileinput();
}
</script>
<style>
	#subbox { width: 900px; height: 50px; padding: 0.5em; }
	#subbox h3 { text-align: center; margin: 0; }
	#subbox h5 { text-align: center; margin: 0; }
	
	#div1 { width: 200px; height: 30px; background-color: #a9a9a9; }
	#txtBox { width: 180px; height: 18px; background-color: #EDFAEE; position: relative; top: 0px; left: 0px; border-style: none; font-family:Arial;font-size:15px;}
	</style>
</head>
<body>
<table width="900px" align="center">
	<tr>
		<td align="center">
			<jsp:include page="/sharedcontacts/main.do?cmd=getheader" flush="true" />
		</td>
	</tr>
	<tr height="400px">
		<td align="left">
		<div style="text-align:left;margin:20px 0px 10px 0px;font-weight:bold;font-family:Arial;color:#35586C"><img src="/img/title_icon.jpg" align="center">Multiple Create</div>
			<div style="height:400px;align:center;">
				<br/><br/>       			 
       			<form method="post" action="/sharedcontacts/fileupload.do" enctype="multipart/form-data" target="resultFrm">
           		<div style="font-size:12px;height:30px;width:300px;text-align:middle;">
           			<input id="file" type="file" name="file"/><br/><br/><br/><input id="Submit" type="submit" name="Submit" value="Submit"/><input id="Cancel" type="cancel" name="Cancel" value="Cancel"/><br/><br/><br/>
       				<font size="3"><a href="/list.xls">Click here to download a sample file.</a></font>
       			</div>
       			</form>
       			       						
			</div>
			
		</td>
	</tr>
	<tr>
		<td align="center">
			<jsp:include page="/sharedcontacts/main.do?cmd=getfooter" flush="true" />
		</td>
	</tr>
</table>
<iframe id="resultFrm" name="resultFrm" width="0px" height="0px"></iframe>
</body>
</html>