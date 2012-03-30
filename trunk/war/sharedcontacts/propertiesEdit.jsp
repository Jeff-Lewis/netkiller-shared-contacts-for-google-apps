<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="com.netkiller.common.jdo.Property"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%
	Map<String, Object> result = (Map<String, Object>)request.getAttribute("result");
	List<Property> properties = (List<Property>) result.get("properties");
	String cmd = (String) result.get("cmd");
	
	boolean e = false;
	if("edit".equals(cmd)){
		e = true;
	}
%>    
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>


<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type='text/css' href='/css/main.css' />
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
<link rel='stylesheet' type='text/css' href='/css/jquery-ui-min.css'/>
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>
<script type="text/javascript" src='/js/jquery.ui.dialog.js'></script>
<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	$("#btnEdit").button().click(function() {
		$("#frmUser")
		.attr("action", "/sharedcontacts/prop.do?cmd=edit")
		.submit();
	});
	$("#btnSave").button().click(function() {
		$("#frmUser")
		.attr("action", "/sharedcontacts/prop.do?cmd=save")
		.submit();
	});
	$("#btnCancel").button().click(function() {
		document.location.href="/sharedcontacts/prop.do?cmd=view";
	});
});
</script>
<title>Admin Info. Edit (<%=properties.size()%>)</title>
</head>
<body>
<table width="850px" align="center">
	<tr>
		<td align="center">
			<div id="users-contain" class="ui-widget">
				<form id="frmUser" method="post">
				<table id="tblUser" class="ui-widget ui-widget-content" width="600px">
					<thead>
						<tr class="ui-widget-header ">
							<th colspan="2">관리자 정보 수정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="formLabel">sharedContactsGroupName</td>
							<td>
								<% if(!e){%><%=getValue(properties,"sharedContactsGroupName")%><%}%>
								<% if(e){%><input type="text" size="50" id="sharedContactsGroupName" name="sharedContactsGroupName" value="<%=getValue(properties,"sharedContactsGroupName")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">adminDomain</td>
							<td>
								<% if(!e){%><%=getValue(properties,"adminDomain")%><%}%>
								<% if(e){%><input type="text" size="50" id="adminDomain" name="adminDomain" value="<%=getValue(properties,"adminDomain")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">domainCheck</td>
							<td>
								<% if(!e){%><%=getValue(properties,"domainCheck")%><%}%>
								<% if(e){%><input type="text" size="50" id="domainCheck" name="domainCheck" value="<%=getValue(properties,"domainCheck")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">isUseForSharedContacts</td>
							<td>
								<% if(!e){%><%=getValue(properties,"isUseForSharedContacts")%><%}%>
								<% if(e){%><input type="text" size="50" id="isUseForSharedContacts" name="isUseForSharedContacts" value="<%=getValue(properties,"isUseForSharedContacts")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">username</td>
							<td>
								<% if(!e){%><%=getValue(properties,"username")%><%}%>
								<% if(e){%><input type="text" size="50" id="username" name="username" value="<%=getValue(properties,"username")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">password</td>
							<td>
								<% if(!e){%><%=getValue(properties,"password")%><%}%>
								<% if(e){%><input type="text" size="50" id="password" name="password" value="<%=getValue(properties,"password")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">feedurl</td>
							<td>
								<% if(!e){%><%=getValue(properties,"feedurl")%><%}%>
								<% if(e){%><input type="text" size="50" id="feedurl" name="feedurl" value="<%=getValue(properties,"feedurl")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">groupFeedUrl</td>
							<td>
								<% if(!e){%><%=getValue(properties,"groupFeedUrl")%><%}%>
								<% if(e){%><input type="text" size="50" id="groupFeedUrl" name="groupFeedUrl" value="<%=getValue(properties,"groupFeedUrl")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td class="formLabel">isSortingSupported</td>
							<td>
								<% if(!e){%><%=getValue(properties,"isSortingSupported")%><%}%>
								<% if(e){%><input type="text" size="50" id="isSortingSupported" name="isSortingSupported" value="<%=getValue(properties,"isSortingSupported")%>" /><%}%>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<% if(!e){%><input type="button" id="btnEdit" value="수정"/><%}%>
								<% if(e){%><input type="button" id="btnSave" value="저장"/><%}%>
								<% if(e){%><input type="button" id="btnCancel" value="취소"/><%}%>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
		</td>
	</tr>
	</table>
</body>
</html>
<%!
	public static String getValue(List<Property> properties, String properId){
		if(properties == null){
			return "";
		}

		for(int i=0; i<properties.size(); i++){
			if( properties.get(i).getPropId() != null && properties.get(i).getPropId().equals(properId)){
				return properties.get(i).getPropValue()==null?"":properties.get(i).getPropValue();
			}
		}		

		return "";
	}
%>    