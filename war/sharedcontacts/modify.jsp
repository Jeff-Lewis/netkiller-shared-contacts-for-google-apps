<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.google.gdata.data.contacts.ContactEntry" %>
<%@ page import="com.google.gdata.data.extensions.*" %>
<%@ page import="com.netkiller.util.SharedContactsUtil" %>
<%@ page import="com.netkiller.util.CommonUtil"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>

<%  logger.info("-- modify.jsp --"); %>

<%
	Map result = (Map)request.getAttribute("result");
	ContactEntry contact = null;
	if(result != null){
		contact = (ContactEntry)result.get("contact");
	}
%>

<%

String page1 = "";
String rows = "";
String sidx = "";
String sord = "";

StringBuffer queryParams = new StringBuffer("");

if(result != null){
	page1 = CommonUtil.getNotNullString((String)result.get("page"));
	rows = CommonUtil.getNotNullString((String)result.get("rows"));
	sidx = CommonUtil.getNotNullString((String)result.get("sidx"));
	sord = CommonUtil.getNotNullString((String)result.get("sord"));
	if( !page1.equals("") || !rows.equals("") || !sidx.equals("") || !sord.equals("") ){
		queryParams.append("page=");
		queryParams.append(page1);
		queryParams.append("&rows=");
		queryParams.append(rows);
		queryParams.append("&sidx=");
		queryParams.append(sidx);
		queryParams.append("&sord=");
		queryParams.append(sord);		
	}
}
%>

<%
SharedContactsUtil util = SharedContactsUtil.getInstance();

String id = contact.getEditLink().getHref();

String fullName = util.getFullName(contact).equals("-")?" ":util.getFullName(contact);
String familyName = util.getFamilyName(contact).equals("-")?" ":util.getFamilyName(contact);
String givenName = util.getGivenName(contact).equals("-")?" ":util.getGivenName(contact);
String orgName = util.getOrganization(contact, "work").equals("-")?" ":util.getOrganization(contact, "work");
String orgDept = util.getOrganizationDept(contact, "work").equals("-")?" ":util.getOrganizationDept(contact, "work");
String orgTitle = util.getOrganizationTitle(contact, "work").equals("-")?" ":util.getOrganizationTitle(contact, "work");
String workEmail = util.getEmail(contact, "work").equals("-")?" ":util.getEmail(contact, "work");
String homeEmail = util.getEmail(contact, "home").equals("-")?" ":util.getEmail(contact, "home");
String otherEmail = util.getEmail(contact, "other").equals("-")?" ":util.getEmail(contact, "other");
String workPhoneNumber = util.getPhoneNumber(contact, "work").equals("-")?" ":util.getPhoneNumber(contact, "work");
String homePhoneNumber = util.getPhoneNumber(contact, "home").equals("-")?" ":util.getPhoneNumber(contact, "home");
String mobilePhoneNumber = util.getPhoneNumber(contact, "mobile").equals("-")?" ":util.getPhoneNumber(contact, "mobile");
//String workAddress = util.getAddress(contact, "work");
//String homeAddress = util.getAddress(contact, "home");
//String otherAddress = util.getAddress(contact, "other");
//String workAddress = util.getExtendedProperty(contact, "workAddress");
//String homeAddress = util.getExtendedProperty(contact, "homeAddress");
//String otherAddress = util.getExtendedProperty(contact, "otherAddress");

String notes = util.getNotes(contact).equals("-")?" ":util.getNotes(contact);

String homeRel = "http://schemas.google.com/g/2005#home";
String workRel = "http://schemas.google.com/g/2005#work";
String otherRel = "http://schemas.google.com/g/2005#other";
String mobileRel = "http://schemas.google.com/g/2005#mobile";

String workAddress = util.getFormattedAddress(contact, workRel);
String homeAddress = util.getFormattedAddress(contact, homeRel);
String otherAddress = util.getFormattedAddress(contact, otherRel);


String queryString = "";
if(!queryParams.toString().equals("")){
	queryString = "&" + queryParams.toString() + "&id=" + id;
}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
span.required{
color:red;
font-size: 15px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
<link rel='stylesheet' type='text/css' href='/css/main.css'/>
<link rel='stylesheet' type='text/css' href='/css/jquery-ui-1.8.12.custom.css'/>
<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css'/>
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>
<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src='/js/jquery.numeric.js'></script>
<script type="text/javascript">
function isEmail(email) {
	  var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return regex.test(email);
	}
	
function hasNumbers(t)
{
return /\d/.test(t);
}
$(document).ready ( function () {
	//$("#workphone,#homephone,#mobilephone").numeric({ decimal: false, negative: false });
	$('#givenname').change(function(){
		$('#fullname').val($('#givenname').val()+" "+$('#familyname').val());
		});
	$('#familyname').change(function(){
		$('#fullname').val($('#givenname').val()+" "+$('#familyname').val());
		
	});
	$( "#Save" ).button();
	$( "#Save" ).click(function() {
		//alert("SaveNow");
		var $id = $("#id").val();
		var $fullname = $("#fullname").val();
		var $givenname = $("#givenname").val();
		var $familyname = $("#familyname").val();
		var $companyname = $("#companyname").val();
		var $companydept = $("#companydept").val();
		var $companytitle = $("#companytitle").val();
		var $workemail = $("#workemail").val();
		var $homeemail = $("#homeemail").val();
		var $otheremail = $("#otheremail").val();
		
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
		if($givenname.length > 32){
			alert("First Name cannot be more than 32 characters!");
			$("#givenname").focus();
			return;
		}
		/* if(hasNumbers($givenname)){
			alert("First Name cannot contain numbers!");
			$("#givenname").focus();
			return;
		} */
		
		if($familyname == ""){
			alert("Last Name is necessary!");
			$("#familyname").focus();
			return;
		}
		if($familyname.length > 32){
			alert("Last Name cannot be more than 32 characters!");
			$("#familyname").focus();
			return;
		}
		/* if(hasNumbers($familyname)){
			alert("Last Name cannot contain numbers!");
			$("#familyname").focus();
			return;
		}
 */		if($companyname && $companyname.length > 32){
			alert("Company Name cannot be more than 32 characters!");
			$("#companyname").focus();
			return;
		}
		
		if($otheremail == "" && $homeemail =="" && $workemail == ""){
			alert("Email is necessary!");
			$("#workemail").focus();
			return;
		}
		
		if($otheremail && !isEmail($otheremail) ){
			alert("Please enter valid email!");
			$("#otheremail").focus();
			return;
		}
		if($homeemail &&  !isEmail($homeemail) ){
			alert("Please enter valid email!");
			$("#homeemail").focus();
			return;
		}
		
		if($workemail && !isEmail($workemail) ){
			alert("Please enter valid email!");
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
		
		if($workphone == "" && $homephone =="" && $mobilephone == ""){
			alert("Phone Number is necessary!");
			$("#workphone").focus();
			return;
		}
		
		
		var $data = { 
				cmd: 'actmodify', 
				id: $id, 
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
			timeout:30000,
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
					//window.location.href = "/sharedcontacts/main.do?cmd=list" + "<%=queryString%>";
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
		});
	});
	
	$( "#Edit" ).button({ disabled: true });
	//$( "#Edit" ).button();

	
	$( "#Cancel" ).button();
	$( "#Cancel" ).click(function() {
		//alert("Cancel");
		window.location.href = "/sharedcontacts/main.do?cmd=details" + "<%=queryString%>";
	});
	
	
	$( "#Back" ).button();
	$( "#Back" ).click(function() {
		window.location.href = "/sharedcontacts/main.do?cmd=list" + "<%=queryString%>";
	});
	
	$( "#Sbmt" ).button();
	$( "#Sbmt" ).click(function() {
		var $id = $("#id").val();
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
				cmd: 'actmodify', 
				id: $id, 
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
					//window.location.href = "/sharedcontacts/main.do?cmd=list" + "<%=queryString%>";
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
		});
	});	
	
});
$(function(){
	$("input").each(function(){
		var thisVal = $(this).val();
		if(thisVal=="-"){
			$(this).val("");
		}
	});
});
function backToContacts(){
	window.location.href = "/sharedcontacts/main.do?cmd=list" + "<%=queryString%>";
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
<input type="hidden" name="id" id="id" value="<%= id %>">
<table width="900px" align="center">
	<tr>
		<td align="center">
			<jsp:include page="/sharedcontacts/main.do?cmd=getheader" flush="true" />
		</td>
	</tr>
	<tr>
		<td align="left">
		
		<!-- div style="text-align:left;margin:20px 0px 10px 0px;font-weight:bold;font-family:Arial;color:#35586C;"><img src="/img/title_icon.jpg" align="center">Modify</div -->	
		<div style="text-align:left;margin:10px 0px 10px 0px;">
			<!-- button id="SelectAll" style="font-family:Arial;font-size:12px;height:30px;width:80px;text-align:center;">Select All</button-->
			<table border="0" width="100%">
			<tr><td><span class="required" style="margin-left:30px;">* </span><span style="color:red;font-size:14px;">Required Field</span></td></tr>
			<tr>
			<td valign="middle" style="font-family:Arial;font-size:13px;">
				<a href="javascript:backToContacts();" style="text-decoration: none;font-weight: bold;"><< &nbsp; Back to contacts</a>
			</td>
			<td align="right">
				<table border="0">
				<tr><td>
				<buton id="Save" style="font-family:Arial;font-size:11px;height:20px;width:75px;text-align:center;padding:0px 0px 0px 0px;">Save now</button>			
				</td>
				<td>
				<buton id="Edit" style="font-family:Arial;font-size:11px;height:20px;width:72px;text-align:center;padding:0px 0px 0px 0px;">Edit</button>
				</td>
				<td>
				<buton id="Cancel" style="font-family:Arial;font-size:11px;height:20px;width:72px;text-align:center;padding:0px 0px 0px 0px;">Cancel</button>
				</td>
				</tr>
				</table>
			</td>
			</tr>
			</table>
		</div>

		<div>
			<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
				<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Name</h5>
 						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
 							<tr>
 								
 								<td width="33%">First Name<span class="required">*&nbsp</span>:&nbsp;<input id="givenname" type="text" class="txtBox" value="<%= givenName %>" style="margin-left:5px;"/></td>
 								<td width="33%">Last Name<span class="required">*&nbsp</span>: <input id="familyname" type="text" class="txtBox"  value="<%= familyName %>"/></td>
 								<td width="33%">Full Name: <input id="fullname" type="text" class="txtBox" style="margin-left:7px;" value="<%= fullName %>"/></td>
 							</tr>
 						</table>
			</div>
			<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
				<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Company</h5>
 						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
 							<tr>
 								<td width="33%" >Name: <input id="companyname" type="text" class="txtBox" value="<%= orgName %>" style="margin-left:41px" /></td>
 								<td width="33%" >Department: <input id="companydept" type="text" class="txtBox" style="margin-left:4px;" value="<%= orgDept %>"/></td>
 								<td width="33%" >Title: <input id="companytitle" type="text" class="txtBox" value="<%= orgTitle %>" style="margin-left:39px" /></td>
 							</tr>
 						</table>
			</div>
			<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
				<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Email</h5>
 						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
 							<tr>
 								<td width="33%">Work<span class="required">*&nbsp</span>: <input id="workemail" type="text" class="txtBox" value="<%= workEmail %>" style="margin-left:36px"/></td>
 								<td width="33%">Home: <input id="homeemail" type="text" class="txtBox" value="<%= homeEmail %>" style="margin-left:37px"/></td>
 								<td width="33%">Other: <input id="otheremail" type="text" class="txtBox" value="<%= otherEmail %>" style="margin-left:32px"/></td>
 							</tr>
 						</table>
			</div>
			<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
				<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Phone</h5>
 						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
 							<tr>
 								<td width="33%">Work<span class="required">*&nbsp</span>: <input id="workphone" type="text" class="txtBox" value="<%= workPhoneNumber %>" style="margin-left:36px"/></td>
 								<td width="33%">Home: <input id="homephone" type="text" class="txtBox" value="<%= homePhoneNumber %>" style="margin-left:37px"/></td>
 								<td width="33%">Mobile: <input id="mobilephone" type="text" class="txtBox" value="<%= mobilePhoneNumber %>" style="margin-left:28px"/></td>
 							</tr>
 						</table>
			</div>
			<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px; width:900px;">
				<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px">Address</h5>
 						<table width="100%" height="15px" style="margin:2px 0px 0px 0px">
 							<tr>
 								<td width="40px" height="25px" >Work: </td><td align="left"><input id="workaddress" type="text" class="txtBox" value="<%= workAddress %>" size="100" style="margin-left:41px" /></td>
 							</tr>
 							<tr>
 								<td height="25px">Home: </td><td align="left"><input id="homeaddress" type="text" class="txtBox" value="<%= homeAddress %>" size="100" style="margin-left:41px"/></td>
 							</tr>
 							<tr>
 								<td height="25px">Other: </td><td align="left"><input id="otheraddress" type="text" class="txtBox" value="<%= otherAddress %>" size="100" style="margin-left:41px"/></td>
 							</tr>
 						</table>
			</div>
			<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
				<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;">Notes</h5>
 						<table width="100%" height="15px" style="margin:5px 0px 0px 0px">
 							<tr>
 								<td width="40px" >&nbsp;</td><td align="left"><textarea name="notes" id="notes" style="width:83%; height:50px; font-family:Arial;margin-left:22px;"><%= notes %></textarea></td>
 							</tr>
 						</table>
			</div>	
		</div>
		
		<!-- 
		<div class="btn" align="center" style="margin:10px 0px 0px 0px;">				
			<button id="Sbmt" style="font-family:Arial;font-size:12px;height:30px;width:70px;text-align:center;">Submit</button>&nbsp;<button id="Back" style="font-family:Arial;font-size:12px;height:30px;width:70px;text-align:center;">Back</button>
		</div>
		 -->
				
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