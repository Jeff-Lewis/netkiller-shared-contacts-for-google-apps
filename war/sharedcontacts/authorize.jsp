<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="org.json.simple.JSONObject"%>
<%@ page import="java.util.List"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>

<%
	Map result = (Map)request.getAttribute("result");
	List<String> usersWithReadPermission = (List<String>)result.get("usersWithReadPermission");
	JSONArray usersWithReadPermissionJsonArray = CommonWebUtil.convetArraryListToJSONArray(usersWithReadPermission);
	List<String> usersWithWritePermission = (List<String>)result.get("usersWithWritePermission");
	JSONArray usersWithWritePermissionJsonArray = CommonWebUtil.convetArraryListToJSONArray(usersWithWritePermission);
	JSONObject jsonObj = new JSONObject();
	String usersWithReadPermissionJsonString = usersWithReadPermissionJsonArray.toJSONString().trim();
	String usersWithWritePermissionJsonString = usersWithWritePermissionJsonArray.toJSONString().trim();
	boolean onlyAdminPermitted = (Boolean)result.get("onlyAdminPermitted");
	boolean allUsersPermitted = (Boolean)result.get("allUsersPermitted");
	boolean success = false;
	if(result.get("success")!=null)
		 success = (Boolean)result.get("success");
	System.out.println("success:"+success);
	%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
<link rel='stylesheet' type='text/css'
	href='/css/jquery-ui-1.8.12.custom.css' />


<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>

<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/jquery.form.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(function(){
	
	
	
	if(eval(<%=success %>))	{
		alert("Settings saved Successfully.");
		backToContacts();
		
	}
	if(eval(<%=onlyAdminPermitted %>))	{
		$('#onlyAdminPermitted').attr('checked', true);
		$('#UserPermissionSelectDiv').hide();
	} else
	if(eval(<%=allUsersPermitted %>))	{
		$('#allUsersPermitted').attr('checked',true);
		$('#UserPermissionSelectDiv').hide();
	} else	{
		$('#UserPermissionSelectDiv').show();
	}
	$('#selectUserPermitted').click(function(){
		$('#UserPermissionSelectDiv').show();
		$('#messageDiv').html("Only Admin and selected (enabled) users can edit shared contacts.");
		});
	$('#allUsersPermitted').click(function(){
		$('#UserPermissionSelectDiv').hide();
		$('#messageDiv').html("All users can edit shared contacts.");
		});
	$('#onlyAdminPermitted').click(function(){
		$('#UserPermissionSelectDiv').hide();
		$('#messageDiv').html("Only Super Admin(s) can edit shared contacts.");
		});	
});
function populateFinalList()	{
	var readOnlyUsers = getAllUsersFromDiv('readOnlyUserDiv');
	var updateUsers = 	getAllUsersFromDiv('updateUserDiv');
	$('#readOnlyUserList').val(readOnlyUsers);
	$('#updateUserList').val(updateUsers);
	document.userPermissionForm.submit();
}

function getAllUsersFromDiv(divId)	{
	var users = "";
	$('#'+divId+' input').each(function()	{
		users = users+$(this).val()+",";
	});
	users = users.substring(0,users.length-1);
	return users;
}
$(function(){
var readOnlyUsers = eval('<%=usersWithReadPermissionJsonString%>');
var updateUsers = eval('<%=usersWithWritePermissionJsonString%>');
readOnlyUsersSize = readOnlyUsers.length;
updateUsersSize = updateUsers.length;
for(var user in readOnlyUsers)	{
	if(readOnlyUsers[user]!='')	{
		appendNewCheckBoxWithinNode('readOnlyUserDiv',readOnlyUsers[user],readOnlyUsers[user],readOnlyUsers[user]);
	}//document.write(users[user]);
}
for(var user in updateUsers)	{
	if(updateUsers[user]!='')	{
		appendNewCheckBoxWithinNode('updateUserDiv',updateUsers[user],updateUsers[user],updateUsers[user]);
	}
	//document.write(users[user]);
}

$("#readOnlyUserDiv input").live('click',function(){
	$('#selectAllreadOnly').attr('checked',false);
	
});

$("#updateUserDiv input").live('click',function(){
	$('#selectAllUpdate').attr('checked',false);
	
});

$("#readOnlyUserDiv").live('dblclick',function(){
	move('readOnlyUserDiv','updateUserDiv');
	
});

$("#updateUserDiv").live('dblclick',function(){
	move('updateUserDiv','readOnlyUserDiv');
	
});

});

function appendNewCheckBoxWithinNode(nodeId,checkboxValue,checboxName,checkboxLabel)	{

	var userCheckboxDiv = document.createElement('div');
	userCheckboxDiv.className="mdl";
	var userCheckboxInnerDiv = document.createElement('div');
	userCheckboxInnerDiv.className="left_panel_btm_left";
	var userCheckbox = document.createElement('input');
	userCheckbox.type="checkbox";
	userCheckbox.name=checboxName;
	userCheckbox.value=checkboxValue;
	var userCheckboxLabel = document.createElement('label');
	userCheckboxLabel.setAttribute('for',checboxName);
	userCheckboxLabel.innerHTML=checkboxLabel;
	userCheckboxInnerDiv.appendChild(userCheckbox);
	userCheckboxDiv.appendChild(userCheckboxInnerDiv);
	userCheckboxDiv.appendChild(userCheckboxLabel);
	$('#'+nodeId).append(userCheckboxDiv);
}

function backToContacts(){
	window.location.href = "/sharedcontacts/main.do?cmd=list";
}

function move(fromNodeID, toNodeID)	{
	
	$("#"+fromNodeID+" input:checked").each( function() {
		appendNewCheckBoxWithinNode(toNodeID,$(this).attr('value'),$(this).attr('name'),$(this).val());	
		$("#"+fromNodeID+" input[value='"+$(this).attr('value')+"']").parent().parent().remove();
		});
	
}

function toggleChecked(status, nodeId) {
	$("#"+nodeId+" input").each( function() {
	$(this).attr("checked",status);
	})
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Netkiller Shared Contacts</title>
</head>
<body>

<form name="userPermissionForm" method="post" action="/sharedcontacts/main.do?cmd=authorize" >
<table width="950px" align="center" border="0">
		<tr>
			<td colspan="3" align="center"><jsp:include
					page="/sharedcontacts/main.do?cmd=getheader" flush="true" /></td>
		</tr>
<tr>
<td>
<div style="margin:10px 0px 10px 0px;">
			<!-- button id="SelectAll" style="font-family:Arial;font-size:12px;height:30px;width:80px;text-align:center;">Select All</button-->
			<table border="0" width="100%">
				<tr>
					<td valign="middle" style="font-family:Arial;font-size:13px;">
						<a href="javascript:backToContacts();" style="color:#3B5323;text-decoration: none;"><< &nbsp; Back to contacts</a>
					</td>
					<td align="right">
						<table border="0">
							<tr>
								<td><buton id="Save" onclick="populateFinalList()" style="cursor:pointer;  background: url('images/ui-bg_glass_85_dfeffc_1x400.png') repeat-x scroll 50% 50% #DFEFFC;border: 1px solid #C5DBEC;color: #2E6E9E;font-weight: bold;font-family:Arial;font-size:11px;height:20px;width:75px;border-radius: 5px 5px 5px 5px;text-align:center;padding:3px 8px 3px 8px;margin:0 5px 0 0">Save now</button></td>
								<td><buton id="Cancel" onclick="backToContacts()" style="cursor:pointer;background: url('images/ui-bg_glass_85_dfeffc_1x400.png') repeat-x scroll 50% 50% #DFEFFC;border: 1px solid #C5DBEC;color: #2E6E9E;font-weight: bold;font-family:Arial;font-size:11px;height:20px;width:75px;border-radius: 5px 5px 5px 5px;text-align:center;padding:3px 8px 3px 8px;margin:0 5px 0 0">Cancel</button></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div style="width:100%;font-family:Arial;font-size:17px;text-align:center;display:block">Netkiller Shared Contacts Access Privileges</div>
		<div class="hdng_lnk">
		<a style="text-decoration: none;color:blue" href="/sharedcontacts/main.do?cmd=authorizeForm">Enabled Users</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a style="text-decoration: none;color:#000" href="/sharedcontacts/main.do?cmd=unauthorizeForm">Restricted Users</a>
		</div>
<div class="panel">
<div class="pnl_img"><img src="/css/images/top-hdng.gif"></div>
	<div class="panel_top">
	<div class="panel_top_left2">Choose to Allow:</div>
		<div class="panel_top_left"><input id="allUsersPermitted" name="domainSettings" type="radio" value="allUsersPermitted" /> All Users</div>
		<div class="panel_top_left"><input id="onlyAdminPermitted" name="domainSettings" type="radio" value="onlyAdminPermitted" /> Super Admin(s) only</div>
		<div class="panel_top_left" style="float:right;"><input id="selectUserPermitted" name="domainSettings" checked type="radio" value="selectUserPermitted" />Admin & Selected User</div>
	</div>
	
	
	<div id="messageDiv" align="center" style="color:blue; font-family:Arial">Only Admin and selected (enabled) users can edit shared contacts.</div>
	
	<div id="UserPermissionSelectDiv">
	
	<div style="width:220px;height:360px;overflow:auto"  class="left_panel">
		<div  class="left_panel_top">
			<div class="left_panel_top_left">
				<input id="selectAllreadOnly" name="selectAllreadOnly"
				onclick="toggleChecked(this.checked, 'readOnlyUserDiv')" type="checkbox" value="select All" />
			</div>
			<span>Select All</span>
		</div>
		<div id="readOnlyUserDiv" class="left_panel_btm">
		</div>
	</div>
	<div class="mdl_panel">
		<span><img src="/css/images/add.gif">&nbsp;&nbsp;Add</span>
		<p><a href="#" onclick="move('readOnlyUserDiv','updateUserDiv')" id="moveRight"><img src="/css/images/arrow-top.gif" /></a><br />
		</p><p><a href="#" id="moveLeft"
						onclick="move('updateUserDiv','readOnlyUserDiv')"><img src="/css/images/arrow-bottom.gif" /></a><br />
		</p><span><img src="/css/images/delete.gif">&nbsp;&nbsp;Delete</span>
	</div> 
	<div style="width:220px;height:360px;overflow:auto" class="right_panel">
	<div style="font:normal 12px Arial;text-align:center;padding-bottom:5px;color:#00f;">Enabled Users</div>
		<div class="left_panel_top">
			<div class="left_panel_top_left">
				<input id="selectAllUpdate" name="selectAllUpdate"
				onclick="toggleChecked(this.checked, 'updateUserDiv')"
				value="Select All" type="checkbox"/>
			</div>
			<span>Select All</span>
		</div>
		<div id="updateUserDiv" class="left_panel_btm">
			
		</div>
	</div>
	</div>
	<div class="send_btn"><input type="hidden" name="readOnlyUserList"
				id="readOnlyUserList"> <input type="hidden"
				name="updateUserList" id="updateUserList"> </div>
</div>
</td>
</tr>
<tr><td>
<hr noshade size=1 width="100%"/></td>
</tr>
<tr valign="top">
			<td align="center" valign="top"><jsp:include
					page="/sharedcontacts/main.do?cmd=getfooter" flush="true" /></td>
		</tr>
</table>

</form>

















</body>
</html>