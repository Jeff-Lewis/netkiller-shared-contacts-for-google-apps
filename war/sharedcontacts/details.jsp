<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.google.gdata.data.contacts.ContactEntry" %>
<%@ page import="com.google.gdata.data.extensions.*" %>
<%@ page import="com.netkiller.util.SharedContactsUtil" %>
<%@ page import="com.netkiller.util.CommonUtil"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>

<%  logger.info("-- details.jsp --"); %>


<%
	String adminUserName = "";
	String userEmail = "";
	ContactEntry contact = null;
	
	Map result = (Map)request.getAttribute("result");
		
	if(result != null){
		contact = (ContactEntry)result.get("contact");
		adminUserName = CommonUtil.getNotNullString((String)result.get("adminUserName"));
		userEmail = CommonUtil.getNotNullString((String)result.get("userEmail"));
	}
%>




<%	
	boolean isAdmin = false;
	if(userEmail.equals(adminUserName)){
		isAdmin = true;
	}
%>


<%  
	logger.info("userEmail: " + userEmail);
	logger.info("adminUserName: " + adminUserName);
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
	
	logger.info("page: " + page1 + ", rows: " + rows + ", sidx: " + sidx + ", sord: " + sord);
	
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

String queryString = "";
if(!queryParams.toString().equals("")){
	queryString = "&" + queryParams.toString();
}
%>

<%
SharedContactsUtil util = SharedContactsUtil.getInstance();

String id = contact.getEditLink().getHref();

String fullName = util.getFullName(contact);
String familyName = util.getFamilyName(contact);
String givenName = util.getGivenName(contact);
String orgName = util.getOrganization(contact, "work");
String orgDept = util.getOrganizationDept(contact, "work");
String orgTitle = util.getOrganizationTitle(contact, "work");
String workEmail = util.getEmail(contact, "work");
String homeEmail = util.getEmail(contact, "home");
String otherEmail = util.getEmail(contact, "other");
String workPhoneNumber = util.getPhoneNumber(contact, "work");
String homePhoneNumber = util.getPhoneNumber(contact, "home");
String mobilePhoneNumber = util.getPhoneNumber(contact, "mobile");
//String workAddress = util.getAddress(contact, "work");
//String homeAddress = util.getAddress(contact, "home");
//String otherAddress = util.getAddress(contact, "other");
//String workAddress = util.getExtendedProperty(contact, "workAddress");
//String homeAddress = util.getExtendedProperty(contact, "homeAddress");
//String otherAddress = util.getExtendedProperty(contact, "otherAddress");
String notes = util.getNotes(contact);

String homeRel = "http://schemas.google.com/g/2005#home";
String workRel = "http://schemas.google.com/g/2005#work";
String otherRel = "http://schemas.google.com/g/2005#other";
String mobileRel = "http://schemas.google.com/g/2005#mobile";

String workAddress = util.getFormattedAddress(contact, workRel);
String homeAddress = util.getFormattedAddress(contact, homeRel);
String otherAddress = util.getFormattedAddress(contact, otherRel);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='stylesheet' type='text/css' href='/css/main.css'/>
<link rel='stylesheet' type='text/css' href='/css/jquery-ui-1.8.12.custom.css'/>
<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css'/>
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>
<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript">
$(document).ready ( function () {
	
	// $('input[type="submit"]').attr('disabled','disabled');
	
	//$( ".selector" ).button({ disabled: true });
	$( "#Save" ).button({ disabled: true });

	
	
	$( "#Edit" ).button();
	$( "#Edit" ).click(function() {
		//alert("Edit");
		var $id = "<%= id %>";
		window.location.href = "/sharedcontacts/main.do?cmd=modify&id=" + $id + "<%=queryString%>";
	});
	
	$( "#Cancel" ).button({ disabled: true });
	//$( "#Cancel" ).button();

	//$("#Cancel").attr("disabled", true);
	// To disable   $('.someElement').attr('disabled', 'disabled');
	// To enable    $('.someElement').removeAttr('disabled');
    // OR you can set attr to ""   $('.someElement').attr('disabled', '');
	
	
	$( "#Back" ).button();
	$( "#Back" ).click(function() {
		window.location.href = "/sharedcontacts/main.do?cmd=list" + "<%=queryString%>";
	});	
	
	$( "#Modify" ).button();
	$( "#Modify" ).click(function() {
		var $id = "<%= id %>";
		window.location.href = "/sharedcontacts/main.do?cmd=modify&id=" + $id + "<%=queryString%>";
	});
	
	
});
$(function(){
	//$( "#accordion" ).accordion();
	//$("#div1").corner("round");
});

function backToContacts(){
	window.location.href = "/sharedcontacts/main.do?cmd=list" + "<%=queryString%>";
}
</script>
<style>
	#subbox { width: 900px; min-height: 20px; padding: 0.5em; }
	#subbox h3 { text-align: center; margin: 0; }
	#subbox h5 { text-align: center; margin: 0; }
	#subbox table td {font-size:12px;}
	
	#div1 { width: 200px; height: 30px; background-color: #a9a9a9; }
	#txtBox { width: 180px; height: 18px; background-color: #EDFAEE; position: relative; top: 0px; left: 0px; border-style: none; font-family:Arial;font-size:15px;}
	</style>
	<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<table width="900px" align="center">
	<tr>
		<td align="center">
		<%	if(!queryParams.toString().equals("")){ %>
			<jsp:include page="/sharedcontacts/main.do?cmd=getheader&<%= queryParams.toString() %>" flush="true" />
		<%	}else{ %>
			<jsp:include page="/sharedcontacts/main.do?cmd=getheader" flush="true" />
		<%	} %>
		</td>
	</tr>
	<tr>
		<td align="left">
		<!-- div style="text-align:left;margin:20px 0px 10px 0px;font-weight:bold;font-family:Arial;color:#35586C;"><img src="/img/title_icon.jpg" align="center">Details</div> -->
		<div style="text-align:left;margin:10px 0px 10px 0px;">
			<!-- button id="SelectAll" style="font-family:Arial;font-size:12px;height:30px;width:80px;text-align:center;">Select All</button-->			
			<table border="0" width="100%">
			<tr>			
			<td valign="middle" style="font-family:Arial;font-size:13px;">
				<a href="javascript:backToContacts();" style="color:#3B5323;text-decoration: none;"><< &nbsp; Back to contacts</a>
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

		
			<!-- <div id="accordion">  -->
				<!-- <h3><a href="#">Details</a></h3>  -->
				<div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;float:left;">Name</h5>
	  						<table width="86%">
	  							<tr>
	  								
	  								<td width="33%" style="padding-left:15px;"><b>First Name:</b>&nbsp;<%= givenName %></td>
	  								<td width="33%"><b>Last Name:</b>&nbsp;<%= familyName %></td>
	  								<td width="33%" ><b>Full Name:</b>&nbsp;<%= fullName %></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;float:left;">Company</h5>
	  						<table width="86%">
	  							<tr>
	  								<td width="33%" style="padding-left:15px;"><b>Name:</b>&nbsp;<%= orgName %></td>
	  								<td width="33%"><b>Department:</b>&nbsp;<%= orgDept %></td>
	  								<td width="33%"><b>Title:</b>&nbsp;<%= orgTitle %></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;float:left;">Email</h5>
	  						<table width="86%">
	  							<tr>
	  								<td width="33%" style="padding-left:15px;"><b>Work:</b>&nbsp;<%= CommonUtil.getEmailLink(workEmail) %></td>
	  								<td width="33%"><b>Home:</b>&nbsp;<%= CommonUtil.getEmailLink(homeEmail) %></td>
	  								<td width="33%"><b>Other:</b>&nbsp;<%= CommonUtil.getEmailLink(otherEmail) %></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;float:left;">Phone</h5>
	  						<table width="86%">
	  							<tr>
	  								<td width="33%" style="padding-left:15px;"><b>Work:</b>&nbsp;<%= workPhoneNumber %></td>
	  								<td width="33%"><b>Home:</b>&nbsp;<%= homePhoneNumber %></td>
	  								<td width="33%"><b>Mobile:</b>&nbsp;<%= mobilePhoneNumber %></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px; width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;float:left;">Address</h5>
	  						<table width="86%">
	  							<tr>
	  								<td width="36px" style="padding-left:15px;float:left;font-weight:bold;">Work:&nbsp;</td>
	  								<td style="float:left;"><%= CommonUtil.getMapLink(workAddress) %></td>
	  							</tr>
	  						</table>
	  						<table width="100%">
	  							<tr>
	  								<td width="174px" style="float:left;text-align: right;font-weight:bold;">Home:&nbsp;</td>
	  								<td><%= CommonUtil.getMapLink(homeAddress) %></td>
	  							</tr>
	  							<tr>
	  								<td width="174px" style="float:left;text-align: right;font-weight:bold;">Other:&nbsp;</td>
	  								<td><%= CommonUtil.getMapLink(otherAddress) %></td>
	  							</tr>
	  						</table>
					</div>
					<div id=subbox class="ui-widget-content" style="font-family:Arial;font-size:15px; margin:7px 0px 0px 3px;width:900px;">
						<h5 class="ui-widget-header" style="font-family:Arial;text-align:center;width:120px;font-size:17px;float:left">Notes</h5>
	  						<table width="86%" style=";font-size:12px;">
	  							<tr>
	  								<td width="20px" align="right">&nbsp;</td><td align="left"><%= notes %></td>
	  							</tr>
	  						</table>	  					
					</div>			
					

				</div>
				
				<!-- 
				<div class="btn" align="center" style="margin:10px 0px 0px 0px;">
				<%	if(isAdmin){ %>				
					<button id="Modify" style="font-family:Arial;font-size:12px;height:30px;width:70px;text-align:center;">Modify</button>&nbsp;
				<%	} 			 %>
					<button id="Back" style="font-family:Arial;font-size:12px;height:30px;width:70px;text-align:center;">Back</button>
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