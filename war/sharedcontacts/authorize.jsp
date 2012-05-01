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
	String domain = (String)result.get("domainName");
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

<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css' />
<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/jquery.form.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var domainName = '<%=domain%>';

function submitForm(e)
{


    // Get the ASCII value of the key that the user entered
	if(window.event)
 		var key = window.event.keyCode; 
	else
		var key = e.which;


   

    // Verify if the key entered was a numeric character (0-9) or a backspace (.)
    if(key != 13 )
	{
	
        // If it was, then allow the entry to continue
        return;
		}
    else
	{
	    searchJqgrid();
	        // If it was not, then dispose the key and continue with entry
	
		if(window.event)
			window.event.returnValue = null; // IE hack
		else
			e.preventDefault(); // standard method
	
	}
}

$(function(){
	
	
	
	if(eval(<%=success %>))	{
		alert("Settings saved Successfully.");
		backToContacts();
		
	}
	if(eval(<%=onlyAdminPermitted %>))	{
		$('#onlyAdminPermitted').attr('checked', true);
		$('#UserPermissionSelectDiv').hide();
		$('#messageDiv').html("Only Super Admin(s) can edit shared contacts.");
	} else
	if(eval(<%=allUsersPermitted %>))	{
		$('#allUsersPermitted').attr('checked',true);
		$('#messageDiv').html("All users can edit shared contacts.");
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
 var searchJqgrid= function(){
		var operator=getOperator();
		var field= getField();
$('input[type="checkbox"]').each(function(){
			
			$(this).parent().parent().attr('style', 'background:none');
			$(this).attr('checked', false);
			
		});
		var searchText=$('#searchText').val();
if($('#searchText').val().indexOf("@"+domainName)!=-1)	{
	searchText = searchText.substring(0,$('#searchText').val().indexOf("@"+domainName));
}
$(field+' input[type="checkbox"][value'+operator+'"'+searchText+'"]').each(function(){
	
	$(this).parent().parent().attr('style', 'background:url("/css/images/heading.gif") repeat scroll 0 0 transparent')
	$(this).attr('checked',true);
});


	};
$(function(){
	$('#search').click(searchJqgrid);
		
	
});

function getOperator()	{
	var operator="*=";
	var operatorValue = $('#operator').val();

	if(operatorValue == 'bw')	{
		operator = "^=";
	}
	if(operatorValue == 'eq')	{
		operator = "=";
	}
	return operator;
}

function getField()	{
	
	var field="";
	var fieldValue = $('#field').val();
	if(fieldValue=='right')	{
		field = '#updateUserDiv';
	}
	if(fieldValue=='left')	{
		field = '#readOnlyUserDiv';
	}
		return field;
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

		<div style="width:100%;font-family:Arial;font-size:17px;text-align:center;display:block">
		
			<div
					style="text-align: left; margin: 10px 0px 2px 0px; float: left;">
					<!-- button id="SelectAll" style="font-family:Arial;font-size:12px;height:30px;width:80px;text-align:center;">Select All</button-->
				
					<table border="0">
						<tr><td style="font-size:12px; font-weight:bold; font-family:Arial">Managing Users Previliges:</td>
							<td><input type="button" class="def_bt"   onclick='window.location.href="/sharedcontacts/main.do?cmd=authorizeForm";' value="User Editing" ></td>
								<td><input type="button" class="def_bt" onclick='window.location.href="/sharedcontacts/main.do?cmd=unauthorizeForm"' value="User Access" ></td>
						
						
							
						</tr>
					</table>
					
				</div>
							
		<div
					style="float: left; margin: 8px 0px 2px 0px; font-size: 0.6em; overflow: auto;"
					class="searchFilter" id="fbox_list2">
					
						<table style="border: 0px none;"
							class="group ui-widget ui-widget-content">

							<tbody>
								<tr style="display: none;" class="error">
									<th align="left" class="ui-state-error" colspan="5"></th>
								</tr>
								<tr>
									<th align="left" class="ui-widget" colspan="5"><select
										id="groupOperator" class="opsel" style="display: none;"><option
												selected="selected" value="AND">AND</option>
											<option value="OR">OR</option>
									</select><span></span><input type="button" class="add-rule ui-add"
										title="Add rule" value="+" style="display: none;">
									</th>
								</tr>
								<tr>
									<td class="first ui-widget"></td>
									<td style="width:70px; float:left;" class="columns ui-widget">
									<div style="display: block;z-index:1000;margin-top:2px" class="menu">
			<ul>
				<li onmouseout="$('#fielddropdownlist').hide();" onmouseover="$('#fielddropdownlist').show();"><a style="width:40px" onclick="$('#fielddropdownlist').show();" id="fielddropdown" class="check dropdown" href="#">All<span class="arrow"></span></a>
				<ul onmouseout="$(this).hide();" onmouseover="$(this).show();" id="fielddropdownlist" class="width-1">
					<li><a  style="width:40px" onclick = "$('#fielddropdownlist').hide();$('#field').val('all');$('#fielddropdown').html('All');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">All</a></li>
					<li><a  style="width:40px" onclick = "$('#fielddropdownlist').hide();$('#field').val('left');$('#fielddropdown').html('Left');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">Left</a></li>
					<li><a style="width:40px" onclick = "$('#fielddropdownlist').hide();$('#field').val('right');$('#fielddropdown').html('Right');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">Right</a></li>

				
				</ul>
				</li></ul></div>
				
									<div style="display:none">
									<select
										onkeypress="submitForm(event)" id="field">
											<option  value="all">All</option>
											<option value="left">Left</option>
											<option value="right">Right</option>
											
									</select></div>
									</td>
									<td style="width:103px; float:left;" class="operators ui-widget">
								
									<div style="display: block;z-index:1000;margin-top:2px" class="menu">
			<ul>
				<li onmouseout="$('#operatordropdownlist').hide();" onmouseover="$('#operatordropdownlist').show();" ><a onclick="$('#operatordropdownlist').show();" style="width:80px" id="operatordropdown" class="check dropdown" href="#">contains<span class="arrow"></span></a>
				<ul onmouseout="$(this).hide();" onmouseover="$(this).show();" id="operatordropdownlist" class="width-1">
					<li><a style="width:80px" onclick = "$('#operatordropdownlist').hide();$('#operator').val('cn');$('#operatordropdown').html('contains');var newSpan=document.createElement('span');newSpan.className='arrow';$('#operatordropdown').append(newSpan);" href="#">contains</a></li>
					<li><a style="width:80px" onclick = "$('#operatordropdownlist').hide();$('#operator').val('bw');$('#operatordropdown').html('begins with');var newSpan=document.createElement('span');newSpan.className='arrow';$('#operatordropdown').append(newSpan);" href="#">begins with</a></li>
					<li><a style="width:80px" onclick = "$('#operatordropdownlist').hide();$('#operator').val('eq');$('#operatordropdown').html('equal');var newSpan=document.createElement('span');newSpan.className='arrow';$('#operatordropdown').append(newSpan);" href="#">equal</a></li>
				</ul>
				</li></ul></div>
				
									
									
									
									<div style="display:none;">
									<select
										onkeypress="submitForm(event)" id="operator"
										class="selectopts"><option value="eq">equal</option>
											<option value="bw">begins with</option>
											<option value="cn" selected="selected">contains</option>
									</select>
									</div>
									</td>
									<td class="data ui-widget"><input
										onkeypress="submitForm(event)" type="text" id="searchText" name="searchText"
										style="width: 98%;height:24px;border:solid 1px #CCC;font-size:12px" class="input-elm">
									</td>
									<td class="ui-widget"><input type="button"
										class="delete-rule ui-del" title="Delete rule" value="-"
										style="display: none;">
									</td>
									<td class="EditButton"><div id="findButton" class="submit_bt" style=""><a id="search"
										class="fm-button ui-state-default ui-corner-all fm-button-icon-right ui-reset"
										 href="javascript:searchJqgrid()" style="width:45px"><span
											class="ui-icon ui-icon-search"></span>Find</a>
											<div style="display:none"><input type="submit"   /> </div>
											</div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
		
								
								
								<div
					style="text-align: left; margin: 10px 0px 2px 0px; float: right;">
					<!-- button id="SelectAll" style="font-family:Arial;font-size:12px;height:30px;width:80px;text-align:center;">Select All</button-->
					
					<table border="0">
						<tr>
							<td><button id="Save" onclick="populateFinalList()" >Save</button></td>
								<td><button id="Cancel" onclick="backToContacts()" >Cancel</button></td>
						
						
							
						</tr>
					</table>
					
				</div>
								
								</div>
		
<div class="panel">
<div class="pnl_img"><img src="/css/images/top-hdng.gif"></div>
	<div class="panel_top">
	<div class="panel_top_left2">Choose to Allow:</div>
		<div class="panel_top_left"><input id="allUsersPermitted" name="domainSettings" type="radio" value="allUsersPermitted" /> All Users</div>
		<div class="panel_top_left"><input id="onlyAdminPermitted" name="domainSettings" type="radio" value="onlyAdminPermitted" /> Super Admin(s) only</div>
		<div class="panel_top_left" style="float:right;"><input id="selectUserPermitted" name="domainSettings" checked type="radio" value="selectUserPermitted" />Admin & Selected User</div>
	
	
	<div id="messageDiv" align="center" style="color:blue; font-family:Arial;display:block;clear:both;padding-top:10px; width:100%">Only Admin and selected (enabled) users can edit shared contacts.</div>
	
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
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