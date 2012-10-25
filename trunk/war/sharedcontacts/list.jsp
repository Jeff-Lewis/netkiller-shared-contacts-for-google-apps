<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>


<%!Logger logger = Logger.getLogger(getClass().getName());%>

<%
	request.setCharacterEncoding("UTF-8");
%>

<%
	logger.info("-- list.jsp --");
%>


<%
	Map<String, Object> result = (Map<String, Object>) request
			.getAttribute("result");
	String adminUserName = "";
	String userEmail = "";
	boolean isUserPermitted = (Boolean)result.get("isUserPermitted");
	boolean isAdmin = false;
	if (result != null) {
		adminUserName = (String) result.get("adminUserName");
		userEmail = (String) result.get("userEmail");
		isAdmin = (Boolean)result.get("isUserAdmin");
		/* if (adminUserName.equals(userEmail)) {
			isAdmin = true;
		} */
		logger.info("adminUserName: " + adminUserName + ", userEmail: "
				+ userEmail);
	}
%>


<%
	String page1 = CommonWebUtil.getParameter(request, "page"); // get the requested page
	String rows = CommonWebUtil.getParameter(request, "rows"); // get how many rows we want to have into the grid		
	String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby Sorting criterion. The only supported value is lastmodified.
	String sord = CommonWebUtil.getParameter(request, "sord"); // ascending or descending.
	logger.info("### PARAMS: page: " + page1 + ", " + "rows: " + rows
			+ ", " + "sidx: " + sidx + ", " + "sord: " + sord);

	StringBuffer queryParams = new StringBuffer("");
	String defaultGridOrder = CommonWebUtil.getParameter(request,
			"defaultGridOrder");
	String defaultGridOrderQueryString = "";
	if (!defaultGridOrder.equals("")) {
		defaultGridOrderQueryString = "&defaultGridOrder="
				+ defaultGridOrder;
	}
	if (!page1.equals("")) {
		queryParams.append("page1=");
		queryParams.append(page1);
		//queryParams.append("&rows=");
		//queryParams.append(rows);
		//queryParams.append("&sidx=");
		//queryParams.append(sidx);
		//queryParams.append("&sord=");
		//queryParams.append(sord);		
	}
	String queryString = "";
	if (!queryParams.toString().equals("") && !page1.equals("1")) {
		queryString = "&" + queryParams.toString();
	}
	logger.info("queryString ===> " + queryString);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Netkiller Shared Contacts</title>
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />

<script type="text/javascript">
function waitPreloadPage() { //DOM
if (document.getElementById){
document.getElementById('prepage').style.visibility='hidden';
}else{
if (document.layers){ //NS4
document.prepage.visibility = 'hidden';
}
else { //IE4
document.all.prepage.style.visibility = 'hidden';
}
}
document.getElementById('container').style.display="";
}

</script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='stylesheet' type='text/css'
	href='/css/jquery-ui-1.8.12.custom.css' />
<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css' />
<link rel='stylesheet' type='text/css' href='/css/style.css' />
<link rel="stylesheet" type="text/css" href="/css/fileinput.css" />

<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>

<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/jquery.form.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
<script type="text/javascript" src='/js/jquery.numeric.js'></script>
<script type="text/javascript">
function hasNumbers(t,a)
{
	
var returnValue = /\d/.test(t);
var msg="";
returnValue = !returnValue;
if(!returnValue){
	msg=" cannot contain numbers"
}

return [returnValue,msg];
}

function isEmail(email,a) {
	  var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var returnValue = regex.test(email);
		var msg="";
		
		if(!returnValue){
			msg="is not a valid email"
		}
		
	  return [returnValue,msg];
	}

function beforeEdit(e){
	$(".editable:lt(4)").each(function(){
		console.log($(this));
		$(this).attr('maxlength',32);		
	});
//	$(".editable:eq(5)").numeric({ decimal: false, negative: false });
}
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

function showLoading() {
	$(".ui-dialog-titlebar").hide() ; 
	$("#loading").dialog('open');
	  $("#loading").show();
	}

	function hideLoading() {
	  $("#loading").hide();
	}


$(document).ready(function() {
	
	$('#loading').dialog({
		autoOpen: false,
		width: 520,
		height:400
		//height: 260
		//buttons: {
		//	"Ok": function() {  
		//		alert('hello');
		//		
		//	}
			//"Close": function() { 
			//	$(this).dialog("close"); 
			//}
		//}
	});
	
	document.getElementById('findButton').style.display="";
//	$( "#Sync" ).button();
	$( "#syncButton" ).button();
	$("#syncButton").click(function(){
		showLoading();
		
		$('#SyncDialog').dialog('close');
		var $data = { 
				cmd: 'syncContacts' 
				
			};
		$.ajax({
			url:'/sharedcontacts/main.do',
			type:'post',
			timeout:10000,
			data: $data,
			//success:handleSuccess,
			//error:handleError,
			success:function(xml){
				$("#loading").dialog('close');
				$(".ui-dialog-titlebar").show() ; 
				//alert(xml);
				
				//var xml_text = $(xml).text();
				//alert(xml_text);			

				var code = $(xml).find('code').text();
				var message = $(xml).find('message').text();
				//alert(code);
				if(code == "success"){
					alert(message);
					/* window.location.href = "/sharedcontacts/main.do?cmd=list&defaultGridOrder=lastModifiedDate"; */
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
				$("#loading").dialog('close');
				$(".ui-dialog-titlebar").show() ; 
				alert(" It's being updated on your contacts thus please check your contacts in a minute.");
				
			}
		}); //end ajax		

	
		
	});
	
	

//	$( "#cancelButton" ).button();
	$( "#cancelButton" ).click(function(){
		$('#SyncDialog').dialog('close');
	});
	
	$( "#Sync" ).click(function() {
		//alert("New Contact!");		
		$('#SyncDialog').dialog('open');
		
		return false;
		
	});
	$('#SyncDialog').dialog({
		autoOpen: false,
		width: 520
		//height: 260
		//buttons: {
		//	"Ok": function() {  
		//		alert('hello');
		//		
		//	}
			//"Close": function() { 
			//	$(this).dialog("close"); 
			//}
		//}
	});
	
	
//	$( "#NewContact" ).button();
	$( "#NewContact" ).click(function() {
		//alert("New Contact!");
		window.location.href = "/sharedcontacts/main.do?cmd=create";
	});
	
	// Dialog			
	$('#ImportDialog').dialog({
		autoOpen: false,
		width: 520//,
		//height: 260
		//buttons: {
			//"Ok": function() { 
			//	$(this).dialog("close"); 
			//}, 
			//"Close": function() { 
			//	$(this).dialog("close"); 
			//}
		//}
	});
	
	
	
//	$( "#Import" ).button();
	$( "#Import" ).click(function() {
		$('#ImportDialog').dialog('open');
		return false;
	});
	
	



//	$( "#Delete" ).button();
	$( "#Delete" ).click(function() {

		if(!confirm("Are you sure?")){
			return;
		}
		
		var grid = jQuery("#list2");
	    var ids = grid.jqGrid('getDataIDs');
	    var val = null;
	    var id = null;
	    var idsToDelTmp = "";
	    for (var i = 0; i < ids.length; i++) {
	    	id = ids[i];
	    	val = grid.jqGrid('getCell', id, 'delete');
	    	if(val == "Yes"){
	    		idsToDelTmp += id + "|";
	    	}
	    }
	    if(idsToDelTmp == ""){
	    	return;
	    }
	    else{
			
	    }
	    
	    $idsToDel = idsToDelTmp;
	    //alert(idsToDel);
	    var $data = { cmd: 'actremove', ids_to_del: $idsToDel }; 
		//alert(11);
		$.ajax({
			url:'/sharedcontacts/main.do',
			type:'post',
			timeout:20000,
			data: $data,
			//success:handleSuccess,
			//error:handleError,
			success:function(xml){

				var code = $(xml).find('code').text();
				var message = $(xml).find('message').text();
				
				if(code == "success"){
					alert(message);
					window.location.href = "/sharedcontacts/main.do?cmd=list<%=queryString%>";
				}
				else{
					alert(message);
				}				
			},
			error:function(xhr,status,e){
				alert("Error occured!");
			}
		}); //end ajax			
	});	
	
	//$( "#Download" ).button();
	$( "#Download" ).click(function() {

		
		window.location.href = "/sharedcontacts/main.do?cmd=actdownload";
		
	});	
	
	
	jQuery("#list2").jqGrid({ 
		url:'/sharedcontacts/main.do?cmd=list_data<%=queryString%><%=defaultGridOrderQueryString%>',
		datatype: "json",
		<%if (isAdmin) {%>
		colNames:['id', 'No.', '&nbsp;<input type="checkbox" onclick="checkBox(event)" /> ', 'First Name', 'Last Name', 'Company', 'Email', 'Phone', 'Address', 'Action'],
		<%} else {%>
		colNames:['id', 'No.', '&nbsp;<input type="checkbox" onclick="checkBox(event)" /> ', 'First Name', 'Last Name', 'Company', 'Email', 'Phone', 'Address'],
		<%}%>
		colModel:[                   
		           {name:'id',index:'id', width:0, hidden:true,search:false, hidedlg:true},		           
		          
		           <%if (isAdmin) {%>
		           {name:'no',index:'no', width:32, align:"center",search:false, sortable:false},
                   {name:'delete',index:'delete', sortable:false,search:false, formatter: "checkbox", formatoptions: {disabled : false}, editable: true, edittype:"checkbox", align:"center", width:32},		            
		           {name:'givenname',index:'givenname', searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:true}, editable:true, width:80},
		           {name:'familyname',index:'familyname', searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:true}, editable:true, width:80,  searchoptions:{sopt:['eq','bw']}},
		           {name:'company',index:'company',searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:false}, editable:true, width:100}, 
		           {name:'email',index:'email',searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:true, custom:true,    custom_func:isEmail}, editable:true, width:130, align:"left"}, 
		           {name:'phone',index:'phone',searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:true}, editable:true, width:100, align:"left"}, 
		           {name:'address',index:'address', searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:false}, editable:true, width:225,align:"left",sortable:false},
		           {name:'act',index:'act', width:118,search:false, sortable:false}		           
		           
		           <%} else {%>
		           {name:'no',index:'no', width:32, align:"center",search:false, sortable:false},
                   {name:'delete',index:'delete', sortable:false,search:false, formatter: "checkbox", formatoptions: {disabled : false}, editable: true, edittype:"checkbox", align:"center", width:46},		            
		           {name:'givenname',index:'givenname', searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:true}, editable:true, width:90},
		           {name:'familyname',index:'familyname', searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:true}, editable:true, width:90,  searchoptions:{sopt:['eq','bw']}},
		           {name:'company',index:'company',searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:false}, editable:true, width:100}, 
		           {name:'email',index:'email',searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:false}, editable:true, width:130, align:"left"}, 
		           {name:'phone',index:'phone',searchoptions:{sopt:['eq','bw','cn']}, editrules:{edithidden:true, required:false}, editable:true, width:100, align:"left"}, 
		           {name:'address',index:'address', editrules:{edithidden:true, required:false}, editable:true, width:314,align:"left",sortable:false}
		           <%}%>
		],
		gridComplete: function(){
			
			var ids = jQuery("#list2").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];
				div1 = "<div align='center'>";
				be = "<input class='row_bt' type='button' value='Edit' onclick=\"resetEdit();jQuery('#list2').editRow('"+cl+"',null,beforeEdit);\"  />"; 
				se = "<input class='row_bt' type='button' value='Save' onclick=\"jQuery('#list2').saveRow('"+cl+"');\"  />"; 
				
				//ce = "<input style='height:22px;width:50px;' type='button' value='Cancel' onclick=\"jQuery('#list2').restoreRow('"+cl+"');\" />"; 
				//jQuery("#list2").jqGrid('setRowData',ids[i],{act:be+se+ce});
				div2 = "</div>";
				jQuery("#list2").jqGrid('setRowData',ids[i],{act:div1+be+se+div2});
			}
		},
		editurl: "/sharedcontacts/main.do?cmd=actmodifysimply",
     	jsonReader:{
  	   	  root: "rows",
  	   	  page: "page",
  	   	  total: "total",
  	   	  records: "records",
  	   	  //url: "url",
  	   	  id: "id",
  	   	  repeatitems: false
  	   	},
  	  	mtype:'POST',
		scroll: false,
		scrollrows: true,
	   	forceFit : true, 
		gridview: true,			
	    viewrecords: true,
	   	multiselect: false,
	   	multiboxonly: true,
		shrinkToFit: false,
		   	//autowidth: true,
  	   	rowNum:15, 
		rowList:[5, 10, 15, 30, 50, 100],
		pager: '#pager2', 
		sortname: 'no', 
		viewrecords: true, 
		sortorder: "asc", 
		width: 942,
		height: '100%',
		//cellEdit: true, //if true, onSelectRow event can not be used
		//editurl:"/sharedcontacts/main.do?cmd=list_data",
		//cellurl: "/sharedcontacts/main.do?cmd=actmodifysimply",
		//beforeSelectRow: function (rowid, e) {
		//	alert("beforeSelectRow");
		//},
		/* onSelectRow: function(id){
			document.location.href = "/sharedcontacts/main.do?cmd=details&id="+id;
		}, */
		onCellSelect: function(rowid,
				iCol,
				cellcontent){
			var  row= jQuery("#list2").jqGrid('getRowData',rowid);

				     if(cellcontent== row.address){
				    	/*  $('#map_canvas').dialog('open');
				    	 showAddress(cellcontent);
 */
 						 cellcontent=cellcontent.replace(/ /g,"+"); 
				    	 window.open("http://maps.google.com?q="+cellcontent,"_blank");
				    }         
				     else	{
				    		document.location.href = "/sharedcontacts/main.do?cmd=details&id="+rowid;
				     }
		
		},
		caption:"Contacts List",
		recordtext: "View {0} - {1} of {2}",
	    emptyrecords:"No records to view",
		loadtext:"Loading....",
		pgtext : "Page {0} of {1}"
	});
	//page, rows, sidx, sord
	jQuery("#list2").jqGrid('navGrid','#pager2',
			{refresh:false,add:false,edit:false,del:false,search:false,view:false},
			{}, // edit options
			{}, // add options
			{}, // del options
			{closeAfterSearch : true, multipleSearch:false,beforeShowSearch: function($form) {
				$('select.opsel').hide();
				
        },multipleSearch:false,
}, // search options
			{} // view options
	);
	jQuery("#list2").setGridParam({url:'/sharedcontacts/main.do?cmd=list_data'}).trigger("reloadGrid");
	//$( "#SelectAll" ).button();
	$( "#MailTo" ).click(function() {
		var grid = jQuery("#list2");
	    var ids = grid.jqGrid('getDataIDs');
	    var val = null;
	    var id = null;
	    var idsToDelTmp = "";
	    for (var i = 0; i < ids.length; i++) {
	    	id = ids[i];
	    	val = grid.jqGrid('getCell', id, 'delete');
	    	val2 = grid.jqGrid('getCell', id, 'email');
	    	if(val == "Yes"){
				idsToDelTmp += val2 + ",";
	    		
	    	}
	    }
	    if(idsToDelTmp == ""){
	    	return;
	    }
	    else{
			//alert(idsToDelTmp);
			parent.location='mailto:'+idsToDelTmp;
			
			//document.location.href = "/sharedcontacts/main.do?cmd=details&id="+rowid;
			//window.open("http://maps.google.com?q="+cellcontent,"_blank");
	    }
    			
	});
	
	$( "#file" ).fileinput();
	$( "#SubmitFile" ).button();
	$('#uploadForm').ajaxForm(function() { 
		$('#ImportDialog').dialog('close');
		window.location.reload();
    }); 
	
});
var isSelectedAll = false;
function resetEdit(){
	var grid = jQuery("#list2");
    var ids = grid.jqGrid('getDataIDs');
    var val = null;
    var id = null;
    var idsToDelTmp = "";
    for (var i = 0; i < ids.length; i++) {
    	id = ids[i];
    	jQuery('#list2').restoreRow(id);
    }
}

function searchJqgrid()	{
	var myfilter = { groupOp: $('#groupOperator').val(), rules: []};
	// addFilteritem("invdate", "gt", "2007-09-06");
	myfilter.rules.push({field: $('#field').val(),op:$('#operator').val(),data:$('#fieldData').val().replace(/^\s\s*/, '').replace(/\s\s*$/, '')});

	

	// generate to top postdata filter code
	var grid = $("#list2");
	/* grid.jqGrid({
	    // all prarameters which you need
	    search:true, // if you want to force the searching
	    postData: { filters: JSON.stringify(myfilter)}
	}); */
	grid[0].p.search = myfilter.rules.length>0;
	$.extend(grid[0].p.postData,{filters:JSON.stringify(myfilter)});
	grid.trigger("reloadGrid",[{page:1}]);


}

function checkBox(e) 
{ 
		var ids = jQuery("#list2").jqGrid('getDataIDs');
    	var id = null;
		for(var i=0; i<ids.length; i++){
			id = ids[i];
			if(isSelectedAll){
				jQuery("#list2").setCell(id, 'delete', 'No', {});
			}
			else{
				jQuery("#list2").setCell(id, 'delete', 'Yes', {});
			}
		}
    	if(isSelectedAll){
    		isSelectedAll = false;
    	}
    	else{
    		isSelectedAll = true;
    	}

   e = e||event;/* get IE event ( not passed ) */ 
   e.stopPropagation? e.stopPropagation() : e.cancelBubble = true; 
} 

function getCellValue(rowId, cellId) {
    var cell = jQuery('#' + rowId + '_' + cellId);        
    var val = cell.val();
    return val;
}
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
<body onload="waitPreloadPage()">
<DIV id="prepage" style="position:absolute; font-family:arial; font-size:16; left:0px; top:0px; background-color:white; layer-background-color:white; height:100%; width:100%"> 
<TABLE width=100%><TR><TD><B>Loading ... ... Please wait!</B></TD></TR></TABLE>
</DIV>
<div style="display:none" id="container">


	<div id="confirmSubmitMessage" title=" "></div>
	<table width="950px" align="center" border="0">
		<tr>
			<td align="center"><jsp:include
					page="/sharedcontacts/main.do?cmd=getheader" flush="true" /></td>
		</tr>
		<tr>
			<td align="center">
				<!-- <div style="text-align:left;margin:20px 0px 10px 0px;font-weight:bold;font-family:Arial;color:#35586C;"><img src="/img/title_icon.jpg" align="center">List</div> -->

				<div style="display:none;" class="menu">
			<ul>
				<li><a class="check dropdown" href="#"><input type="checkbox"/><span class="arrow"></span></a>
				<ul class="width-1">
					<li><a href="#">All</a></li>
					<li><a href="#">None</a></li>
					<li><a href="#">Read</a></li>
					<li><a href="#">Unread</a></li>
					<li><a href="#">Starred</a></li>
					<li><a href="#">Unstarred</a></li>
				</ul>
				</li>
				<li><a class="left" href="#">Archive</a>
				</li>
				<li><a class="middle" href="#">Spam</a></li>
				<li><a class="right" href="#">Delete</a>
				</li>
				<li><a class="left dropdown" href="#">Move to<span class="arrow"></span></a>
				<ul class="width-2">
					<li><a href="#">Personal</a></li>
					<li><a href="#">Work</a></li>
					<li><a href="#">Travel</a></li>
					<li><a href="#">Insurance</a></li>
					<li><a href="#">Insurance/Vehicle</a></li>
					<li><a href="#">Receipts</a></li>
					<li><a href="#">Educational</a></li>
					<li><a href="#">Spam</a></li>
				</ul>
				</li>
				<li><a class="right dropdown" href="#">Labels<span class="arrow"></span></a>
				<ul class="width-2">
					<li><a href="#">Personal</a></li>
					<li><a href="#">Work</a></li>
					<li><a href="#">Travel</a></li>
					<li><a href="#">Insurance</a></li>
					<li><a href="#">Insurance/Vehicle</a></li>
					<li><a href="#">Receipts</a></li>
					<li><a href="#">Educational</a></li>
				</ul>
				</li>
				<li><a class="dropdown" href="#">More<span class="arrow"></span></a>
				<ul class="width-3">
					<li><a href="#">Mark all as read</a></li>
					<li><a href="#">Mark as read</a></li>
					<li><a href="#">Mark as unread</a></li>
					<li><a href="#">Add to tasks</a></li>
					<li><a href="#">Add star</a></li>
					<li><a href="#">Remove star</a></li>
					<li><a href="#">Create event</a></li>
					<li><a href="#">Filter messages like these</a></li>
					<li><a href="#">Mute</a></li>
					<li><a href="#">Unmute</a></li>
				</ul>
				</li>
				<li><a class="refresh" href="#">&nbsp;</a></li>
				<li><a class="num"><b>1</b>–<b>50</b> of <b>52</b></a></li>
				<li><a class="previous" href="#">&nbsp;</a></li>
				<li><a class="next" href="#">&nbsp;</a></li>
			</ul>
		</div>



				<div
					style="text-align: left; margin: 10px 0px 2px 0px; float: right;">
					<!-- button id="SelectAll" style="font-family:Arial;font-size:12px;height:30px;width:80px;text-align:center;">Select All</button-->
					<%
						if (isAdmin||isUserPermitted) {
					%>
					<table border="0">
						<tr>
						<td>
								<button id="Sync" >Sync
									</button></td>
							
							<td>
								<button id="MailTo">Mail to</button></td>
							<td>
								<button id="Delete">Delete</button>
							</td>
							<td>
								<button id="NewContact"
									>New
									contact</button></td>
							<td>
								<button id="Import"
									>Import</button>
							</td>
							<td>
								<button id="Download"
									>Download</button>
							</td>
						</tr>
					</table>
					<%
						}else{
					%>
						<table border="0">
						<tr>
						<td>
								<button id="Sync" >Sync
									</button></td>
									</tr>
					</table>
						<%
						}
					%>
				</div>
				<div
					style="float: left; margin: 8px 0px 2px 0px; font-size: 0.6em; overflow: auto;"
					class="searchFilter" id="fbox_list2">
					<form name="searchForm" onsubmit="searchJqgrid();">
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
									<td style="width:103px; float:left;" class="columns ui-widget">
									<div style="display: block;z-index:1000;margin-top:2px" class="menu">
			<ul>
				<li onmouseout="$('#fielddropdownlist').hide();" onmouseover="$('#fielddropdownlist').show();"><a style="width:78px" onclick="$('#fielddropdownlist').show();" id="fielddropdown" class="check dropdown" href="#">All<span class="arrow"></span></a>
				<ul onmouseout="$(this).hide();" onmouseover="$(this).show();" id="fielddropdownlist" class="width-1">
					<li><a style="width:78px" style="width:78px" onclick = "$('#fielddropdownlist').hide();$('#field').val('all');$('#fielddropdown').html('All');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">All</a></li>
<!-- 					<li><a style="width:78px" onclick = "$('#fielddropdownlist').hide();$('#field').val('givenname');$('#fielddropdown').html('First Name');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">First Name</a></li> -->
					<li><a style="width:78px" onclick = "$('#fielddropdownlist').hide();$('#field').val('familyname');$('#fielddropdown').html('Last Name');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">Last Name</a></li>
					<li><a style="width:78px" onclick = "$('#fielddropdownlist').hide();$('#field').val('company');$('#fielddropdown').html('Company');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">Company</a></li>
					<li><a style="width:78px" onclick = "$('#fielddropdownlist').hide();$('#field').val('email');$('#fielddropdown').html('Email');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">Email</a></li>
					<li><a style="width:78px" onclick = "$('#fielddropdownlist').hide();$('#field').val('phone');$('#fielddropdown').html('Phone');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">Phone</a></li>
					<li><a style="width:78px" onclick = "$('#fielddropdownlist').hide();$('#field').val('address');$('#fielddropdown').html('Address');var newSpan=document.createElement('span');newSpan.className='arrow';$('#fielddropdown').append(newSpan);" href="#">Address</a></li>
				
				</ul>
				</li></ul></div>
				
									<div style="display:none">
									<select
										onkeypress="submitForm(event)" id="field">
											<option  value="all">All</option>
											<option value="givenname">First Name</option>
											<option value="familyname">Last Name</option>
											<option value="company">Company</option>
											<option value="email">Email</option>
											<option value="phone">Phone</option>
											<option value="address">Address</option>
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
										onkeypress="submitForm(event)" type="text" id="fieldData"
										style="width: 98%;height:24px;border:solid 1px #CCC;font-size:12px" class="input-elm">
									</td>
									<td class="ui-widget"><input type="button"
										class="delete-rule ui-del" title="Delete rule" value="-"
										style="display: none;">
									</td>
									<td class="EditButton"><div id="findButton" class="submit_bt" style="display:none"><a
										class="fm-button ui-state-default ui-corner-all fm-button-icon-right ui-reset"
										 href="javascript:searchJqgrid()" style="width:45px"><span
											class="ui-icon ui-icon-search"></span>Find</a></div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
<div id="loading" style="text-align:center;display: none;background: none;">
  					<p><img style="line-height:400px;margin-top:150px; width:20px;height:20px" src="/img/loading.gif" /> </p>
				</div>
				
				<div style="clear: both;min-height: 405px; width:944px;" >
				
					<form name="listFrm">
						<table id="list2" style="width:944px; "></table>
						<div id="pager2"></div>
					</form>
				</div></td>
		</tr>
		<tr><td>
		<hr noshade size=1 width="100%"/></td>
		</tr>
		<tr valign="top">
			<td align="center" valign="top"><jsp:include
					page="/sharedcontacts/main.do?cmd=getfooter" flush="true" /></td>
		</tr>
	</table>

	<div id="ImportDialog" title="Import contacts"
		style="font-family: Arial; font-size: 12px;">
		<p>
<!--
			We support importing Excel files(.csv) to be uploaded in Netkiller
			Shared Contacts.<br /> Click <a href="https://docs.google.com/spreadsheet/ccc?key=0ApQQqEHZz9C9dElmWTNFOHIzc3VDQk5XZE5vUDZiMUE" target="_blank"><b>here</b> </a>
			to link sample format and mass upload your shared contacts.<br />
			You can upload Unlimited contacts. (Note: Time of upload contacts is
			depend on number of contacts)<br /> <br /> Please select an CSV
			file(.csv) to upload: <br />


			Click <a href="/sample.csv" target="_blank"><b>here</b> </a> to download a CSV file to upload contacts in bulk.
			For multi-byte languages (Korean, etc.) copy <a href="https://docs.google.com/spreadsheet/ccc?key=0ApQQqEHZz9C9dElmWTNFOHIzc3VDQk5XZE5vUDZiMUE" target="_blank"><b>this doc</b> </a> into your google account,
			fill in your contacts, download it as a CSV file, and upload the file here.
			Upload time depends on the number of contacts.

		
			-->
			Open <a href="https://docs.google.com/a/netkiller.com/spreadsheet/ccc?key=0ApQQqEHZz9C9dElmWTNFOHIzc3VDQk5XZE5vUDZiMUE" target="_blank"><b>this</b> </a>  doc and copy it into your google account: Go to File > Make A Copy, and then fill in the document with your contacts. Then, go to File > Download as a CSV file, and upload the file here. Upload time depends on the number of contacts, but normally takes between a few seconds and a few minutes.
			<br /> <br /> Please select an CSV file(.csv) to upload: <br />
		<form id="uploadForm" method="post"
			action="/sharedcontacts/fileupload.do" enctype="multipart/form-data"
			target="resultFrm">
			<div style="font-size: 12px; width: 480px; text-align: middle;">
				<table width="100%" border="0">
					<tr>
						<td width="50%"><input id="file" type="file" name="file" />
						</td>
						<td align="center"><input id="SubmitFile" type="submit"
							name="Submit" value="Submit" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		
		<iframe id="resultFrm" name="resultFrm" width="0px" height="00px"></iframe>
	</div>
	<div id="SyncDialog" title="Sync contacts"
		style="font-family: Arial; font-size: 12px;">
	<p>
	This operation will import contacts to your google account and replace all. It also consume a lot of traffic. Thus please use it carefully and advise not to edit any contacts in your google account shared group to avoid frequent use of 'sync'. Your daily 'sync' operation can be limited.<br><br> Are you sure you want to continue?
	</p>
	<form id="syncForm" method="post"
			action="/sharedcontacts/main.do?cmd=syncContacts"
			>
			<div style="font-size: 12px; width: 480px; text-align: middle;">
				<table width="100%" border="0">
					<tr>
						<td width="18%"><input id="syncButton" style=" width: 70px;" value="Ok" type="button" name="syncButton" />
						</td>
						<td ><input id="cancelButton" type="button" class="ui-button ui-widget ui-state-default ui-corner-all"
							name="Submit" value="Cancel" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
</body>
</html>
