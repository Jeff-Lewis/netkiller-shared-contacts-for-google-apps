<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.google.gdata.data.contacts.ContactEntry" %>
<%@ page import="com.google.gdata.data.extensions.*" %>
<%@ page import="com.netkiller.util.SharedContactsUtil" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>

<%  logger.info("-- create.jsp --"); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Netkiller Shared Contacts</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='stylesheet' type='text/css' href='/css/main.css'/>
<link rel='stylesheet' type='text/css' href='/css/jquery-ui-1.8.12.custom.css'/>
<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css'/>
<link rel='stylesheet' type='text/css' href='/css/style.css' />
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>
<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>

<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
<script type="text/javascript">
function checkDuplicateEmail()	{
	var $workemail = $("#workemail").val();
	var $homeemail = $("#homeemail").val();
	var $otheremail = $("#otheremail").val();
	
	if(isDuplicateEmail($workemail)||isDuplicateEmail($homeemail)||isDuplicateEmail($otheremail))	{
		alert('Duplicate email found');
	} else	{
		alert('No duplicate email found');
	}
	
}
function isDuplicateEmail(email){
	var result = false;
	var $duplicateCheckData = { 
			cmd:'list_data', 
			_search:'true',
			rows:'15',
			page:'1',
			sidx:'no',
			sord:'asc',
			filters:'{"groupOp":"AND","rules":[{"field":"email","op":"eq","data":"'+email+'"}]}'
			};
	
	$.ajax({
		url:'/sharedcontacts/main.do',
		type:'post',
		async:false,
		
		data: $duplicateCheckData,
		//success:handleSuccess,
		//error:handleError,
		success:function(data){
			//alert(xml);
			
			//var xml_text = $(xml).text();
			//alert(xml_text);			

			if(data["rows"].length!=undefined&&data["rows"].length!=null &&data["rows"].length>0)	{
				
				result =  true;
			} else{
				result = false;
			}
			
			//alert(message);
			
			//$(xml).find('cat').each(function(){
			//	var item_text=$(this).text();
			//	alert(item_text);
			//});
		},
		error:function(xhr,status,e){
			alert("Error occured");
			
		}
	}); //end ajax		
	return result;
}
$(document).ready ( function () {
	
	$('#givenname').change(function(){
		$('#fullname').val($('#givenname').val()+" "+$('#familyname').val());
		});
	$('#familyname').change(function(){
		$('#fullname').val($('#givenname').val()+" "+$('#familyname').val());
		
	});
	
	
	$( "#Save" ).button();
	$( "#Save" ).click(function() {
		//alert("SaveNow");
		//window.location.href = "/sharedcontacts/main.do?cmd=create";
		var $fullname = $("#fullname").val();
		var $givenname = $("#givenname").val();
		var $familyname = $("#familyname").val();
		
		if($fullname == ""){
			alert("Full Name is necessary!");
			$("#fullname").focus();
			return;
		}
		
		if($givenname == ""){
			alert("First Name is necessary!");
			$("#givenname").focus();
			return;
		}
		
		if($familyname == ""){
			alert("Last Name is necessary!");
			$("#familyname").focus();
			return;
		}
		
		
		
		
		var $companyname = $("#companyname").val();
		var $companydept = $("#companydept").val();
		var $companytitle = $("#companytitle").val();
		var $workemail = $("#workemail").val();
		var $homeemail = $("#homeemail").val();
		var $otheremail = $("#otheremail").val();
		if($otheremail == "" && $homeemail =="" && $workemail == ""){
			alert("Email is necessary!");
			$("#workemail").focus();
			return;
		}
		var $workphone = $("#workphone").val();
		var $homephone = $("#homephone").val();
		var $mobilephone = $("#mobilephone").val();
		var $workaddress = $("#workaddress").val();
		var $homeaddress = $("#homeaddress").val();
		var $otheraddress = $("#otheraddress").val();
		var $notes = $("#notes").val();
		var $data = { 
				cmd: 'actcreate', 
				fullname: $fullname, 
				givenname: $givenname, 
				familyname: $familyname,
				companyname: $companyname,
				companydept: $companydept,
				companytitle: $companytitle,				
				workemail: $workemail,
				homeemail: $homeemail,
				otheremail: $otheremail,
				workphone: $workphone,
				homephone: $homephone,
				mobilephone: $mobilephone,
				workaddress: $workaddress,
				homeaddress: $homeaddress,
				otheraddress: $otheraddress,
				notes: $notes,
			};
		$.ajax({
			url:'/sharedcontacts/main.do',
			type:'post',
			timeout:3000,
			data: $data,
			//success:handleSuccess,
			//error:handleError,
			success:function(xml){
				//alert(xml);
				
				//var xml_text = $(xml).text();
				//alert(xml_text);			

				var code = $(xml).find('code').text();
				var message = $(xml).find('message').text();
				//alert(code);
				if(code == "success"){
					alert(message);
					window.location.href = "/sharedcontacts/main.do?cmd=list&defaultGridOrder=lastModifiedDate";
				}
				else{
					alert(message);
				}
				
				//alert(message);
				
				//$(xml).find('cat').each(function(){
				//	var item_text=$(this).text();
				//	alert(item_text);
				//});
			},
			error:function(xhr,status,e){
				alert("Successfully created. Contacts will appear in a while");
				window.location.href = "/sharedcontacts/main.do?cmd=list&defaultGridOrder=lastModifiedDate";
			}
		}); //end ajax		
	});
	
	
	//$( "#Edit" ).button();
	$( "#Edit" ).button({ disabled: true });
	
	$( "#Cancel" ).button();
	$( "#Cancel" ).click(function() {
		//alert("Cancel");
		$("#fullname").val("");
		$("#givenname").val("");
		$("#familyname").val("");
		$("#companyname").val("");
		$("#companydept").val("");
		$("#companytitle").val("");
		$("#workemail").val("");
		$("#homeemail").val("");
		$("#otheremail").val("");
		$("#workphone").val("");
		$("#homephone").val("");
		$("#mobilephone").val("");
		$("#workaddress").val("");
		$("#homeaddress").val("");
		$("#otheraddress").val("");
		$("#notes").val("");
		$("#fullname").focus();
	});	
	
	
	
	$( "#Sbmt" ).button();
	$( "#Sbmt" ).click(function() {
		var $fullname = $("#fullname").val();
		var $givenname = $("#givenname").val();
		var $familyname = $("#familyname").val();
		var $companyname = $("#companyname").val();
		var $companydept = $("#companydept").val();
		var $companytitle = $("#companytitle").val();
		var $workemail = $("#workemail").val();
		var $homeemail = $("#homeemail").val();
		var $otheremail = $("#otheremail").val();
		var $workphone = $("#workphone").val();
		var $homephone = $("#homephone").val();
		var $mobilephone = $("#mobilephone").val();
		var $workaddress = $("#workaddress").val();
		var $homeaddress = $("#homeaddress").val();
		var $otheraddress = $("#otheraddress").val();
		var $notes = $("#notes").val();
		var $data = { 
				cmd: 'actcreate', 
				fullname: $fullname, 
				givenname: $givenname, 
				familyname: $familyname,
				companyname: $companyname,
				companydept: $companydept,
				companytitle: $companytitle,				
				workemail: $workemail,
				homeemail: $homeemail,
				otheremail: $otheremail,
				workphone: $workphone,
				homephone: $homephone,
				mobilephone: $mobilephone,
				workaddress: $workaddress,
				homeaddress: $homeaddress,
				otheraddress: $otheraddress,
				notes: $notes,
			};
		$.ajax({
			url:'/sharedcontacts/main.do',
			type:'post',
			timeout:3000,
			data: $data,
			//success:handleSuccess,
			//error:handleError,
			success:function(xml){
				//alert(xml);
				
				//var xml_text = $(xml).text();
				//alert(xml_text);			

				var code = $(xml).find('code').text();
				var message = $(xml).find('message').text();
				//alert(code);
				if(code == "success"){
					alert(message);
					window.location.href = "/sharedcontacts/main.do?cmd=list&defaultGridOrder=lastModifiedDate";
				}
				else{
					alert(message);
				}
				
				//alert(message);
				
				//$(xml).find('cat').each(function(){
				//	var item_text=$(this).text();
				//	alert(item_text);
				//});
			},
			error:function(xhr,status,e){
				alert("Error occured!");
			}
		}); //end ajax
	});
});

function backToContacts(){
	window.location.href = "/sharedcontacts/main.do?cmd=list";
}

</script>
<style>
	#subbox { width: 900px; min-height: 50px; padding: 0.5em; }
	#subbox h3 { text-align: center; margin: 0; }
	#subbox h5 { text-align: center; margin: 0; }
	#subbox table td {font-size:12px;}
	#subbox table td input {font-size:12px;}
	
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
	
	
	<tr>
		<td>
		<!-- div style="text-align:left;margin:20px 0px 10px 0px;font-weight:bold;font-family:Arial;color:#35586C;"><img src="/img/title_icon.jpg" align="center">Create</div -->
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
								<td><buton id="Save" style="font-family:Arial;font-size:11px;height:20px;width:75px;text-align:center;padding:0px 0px 0px 0px;">Save now</button></td>
								<td><buton id="Edit" style="font-family:Arial;font-size:11px;height:20px;width:72px;text-align:center;padding:0px 0px 0px 0px;">Edit</button></td>
								<td><buton id="Cancel" style="font-family:Arial;font-size:11px;height:20px;width:72px;text-align:center;padding:0px 0px 0px 0px;">Cancel</button></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
			
			<!-- <div id="accordion">  -->
				<!-- <h3><a href="#">Details</a></h3>  -->
				<div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Name</h5>
	  						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
	  							<tr>
	  								
	  								<td width="33%"><b>First Name:</b> <input id="givenname"  type="text" class="txtBox" value="" style="margin-left:5px;"/></td>
	  								<td width="33%"><b>Last Name:</b> <input id="familyname" type="text" class="txtBox" value=""/></td>
									<td width="33%"><b>Full Name:</b> <input readonly="readonly" id="fullname" type="text" class="txtBox" value=""/></td>	  						
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Company</h5>
	  						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
	  							<tr>
	  								<td width="33%"><b>Name:</b> <input id="companyname" type="text" class="txtBox" value="" style="margin-left:20px" /></td>
	  								<td width="33%"><b>Department:</b> <input id="companydept" type="text" class="txtBox" value=""/></td>
	  								<td width="33%"><b>Title:</b> <input id="companytitle" type="text" class="txtBox" value="" style="margin-left:39px;"/></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Email</h5>
	  						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
	  							<tr>
	  								<td width="33%"><b>Work:</b> <input id="workemail" type="text" class="txtBox" value="" style="margin-left:25px"/></td>
	  								<td width="33%"><b>Home:</b> <input id="homeemail" type="text" class="txtBox" value="" style="margin-left:32px;"/></td>
	  								<td width="33%"><b>Other:</b> <input id="otheremail" type="text" class="txtBox" value="" style="margin-left:32px;"/><input class='row_bt' type='button' value='Duplicate' onclick='checkDuplicateEmail();'  style="margin-left:32px;"/></td>
	  								
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Phone</h5>
	  						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
	  							<tr>
	  								<td width="33%"><b>Work:</b> <input id="workphone" type="text" class="txtBox" value="" style="margin-left:25px"/></td>
	  								<td width="33%"><b>Home:</b> <input id="homephone" type="text" class="txtBox" value="" style="margin-left:32px;"/></td>
	  								<td width="33%"><b>Mobile:</b> <input id="mobilephone" type="text" class="txtBox" value="" style="margin-left:28px;"/></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px; width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Address</h5>
	  						<table width="100%" style="margin:2px 0px 0px 0px">
	  							<tr>
	  								<td width="40px" height="25px"><b>Work:</b> </td><td align="left"><input id="workaddress" type="text" class="txtBox" value="" size="100" style="margin-left:22px"/></td>
	  							</tr>
	  							<tr>
	  								<td height="25px"><b>Home:</b> </td><td align="left"><input id="homeaddress" type="text" class="txtBox" value="" size="100" style="margin-left:22px"/></td>
	  							</tr>
	  							<tr>
	  								<td height="25px"><b>Other:</b> </td><td align="left"><input id="otheraddress" type="text" class="txtBox" value="" size="100" style="margin-left:22px"/></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;">Notes</h5>
	  						<table width="100%" style="margin:5px 0px 0px 0px">
	  							<tr>
	  								<td width="40px">&nbsp;</td><td align="left"><textarea name="notes" id="notes" style="width:83%; height:50px; font-family:Arial;margin-left:25px;"></textarea></td>
	  							</tr>
	  						</table>	  					
					</div>
					<!-- 
					<table>
					<tr>
					<td>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 0px;width:430px;height:80px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;">Notes</h3>
	  					<textarea name="notes" id="notes" style="width:95%; height:50px; margin:5px 5px 5px 5px;"></textarea>
					</div>
					</td>
					<td>					
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 20px;width:430px;height:80px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;">Others</h3>
	  						<table width="40%" height="15px" style="margin:5px 0px 0px 0px">
	  							
	  						</table>
					</div>
					</td>
					</tr>
					</table>
					-->
				</div>
				<!-- 
				<div class="btn" align="center" style="margin:10px 0px 0px 0px;">				
					<button id="Sbmt" style="font-family:Arial;font-size:12px;height:30px;width:70px;text-align:center;">Submit</button>
				</div>
				 -->
				
			<!--  </div>  -->
		</td>
	</tr>
	<tr>
		<td align="center">
			<jsp:include page="/sharedcontacts/main.do?cmd=getfooter" flush="true" />
		</td>
	</tr>
</table>
</body>
</html>