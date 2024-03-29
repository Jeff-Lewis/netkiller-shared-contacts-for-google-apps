<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>


<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>

<%  logger.info("-- customers-list.jsp --"); %>


<%
	Map<String, String> result = (Map<String, String>)request.getAttribute("result");
String domainStatus = null;
String domain = null;
if(result !=null)	{
	domainStatus = result.get("domainStatus");
	domain = result.get("domainToBeDeleted");
	
}
%>


<%
String page1 = CommonWebUtil.getParameter(request, "page"); // get the requested page
String rows = CommonWebUtil.getParameter(request, "rows"); // get how many rows we want to have into the grid		
String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby Sorting criterion. The only supported value is lastmodified.
String sord = CommonWebUtil.getParameter(request, "sord"); // ascending or descending.

StringBuffer queryParams = new StringBuffer("");

if( !page1.equals("") ){
	queryParams.append("page1=");
	queryParams.append(page1);
}
String queryString = "";
if( !queryParams.toString().equals("") && !page1.equals("1") ){
	queryString = "&" + queryParams.toString();
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='stylesheet' type='text/css' href='/css/style.css' />
<link rel='stylesheet' type='text/css' href='/css/jquery-ui-1.8.12.custom.css'/>
<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css'/>
<link rel="stylesheet" type="text/css" href="/css/fileinput.css" />	

<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>

<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/jquery.form.js'></script>
<script>

if(<%=(domainStatus!=null&&domainStatus.equals("inUse")) %>)
	alert("Domain in Working State");
else if(<%=(domainStatus!=null&&domainStatus.equals("notInUse")) %>)	{
	var $domain = "<%=domain%>";
	if(confirm("Domain Uninstalled. Remove customer data?"))	{
		var $data = { 
				cmd: 'actcustomerremove', 
				domainToBeDeleted: $domain
			};
		$.ajax({
			url:'/sharedcontacts/main.do',
			type:'post',
			timeout:10000,
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
				alert("Error occured");
				
			}
		}); //end ajax		

	
	}
	
}
	</script>
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />

<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	jQuery("#list2").jqGrid({ 
		url:'/sharedcontacts/main.do?cmd=customers-list-data<%=queryString%>',
		datatype: "json",
		colNames:['id', 'Domain', 'Admin Email', 'Type', 'Install<br/>Date', 'Upgrade<br/>Date','End<br/>Date', 'Total<br/>Contacts', 'Apps<br/>Users','Synced<br/>Users','NSC<br/>Users','Actions','Domain<br/>Status'],
		colModel:[
		          
		           {name:'id',index:'id', width:0, hidden:true, hidedlg:true,width:200},		           
		           {name:'domain',index:'domain', width:130, align:"left", sortable:true,width:170},
                   {name:'email',index:'email', sortable:true, align:"left", width:310},		            
		           {name:'accountType',index:'accountType', align:"center", width:70, edittype:'select',editrules:{edithidden:true, required:false}, editable:true,editoptions: { value: "Paid:Paid; Free:Free" } },
		           {name:'registeredDate',index:'registeredDate', align:"center", width:100},
		           {name:'upgradedDate',index:'upgradedDate', align:"center", width:100}, 
		           {name:'endDate',index:'endDate', align:"center", width:100},
		           {name:'totalContacts',index:'totalContacts', width:100, align:"right",sorttype: 'int'},
		           {name:'totalUsers',index:'totalUsers', align:"center", width:100,sortable:false,formatter:totalFormatter},
		           {name:'syncedUsers',index:'syncedUsers', align:"center", width:100,sortable:false,formatter:syncedFormatter,hidden:true},
		           {name:'nscUsers',index:'nscUsers', align:"center", width:100,sortable:false,formatter:userFormatter},
		           {name:'act',index:'act', width:290,sortable:false},
		           {name:'checkStatus',index:'checkStatus', align:"center", width:125,sortable:false,formatter:editLinkFormatter,hidden:true},		           
		           
		],
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
	   	shrinkToFit: true,
	   	autowidth: true,
  	   	rowNum:15, 
		rowList:[5, 10, 15, 30, 50, 100],
		pager: '#pager2', 
		sortname: 'domain', 
		viewrecords: true, 
		sortorder: "asc", 
		width: 1024,
		height: '100%',
		gridComplete: function(){
			var ids = jQuery("#list2").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){ var cl = ids[i]; 
			be = "<input class='row_bt' type='button' title='Edit' value='Edit' onclick=\"jQuery('#list2').editRow('"+cl+"');\" />"; 
			se = "<input class='row_bt'  type='button' title='Save' value='Save' onclick=\"jQuery('#list2').saveRow('"+cl+"');\" />"; 
			ce = "<input class='row_bt'  type='button' title='Cancel' value='Cancel' onclick=\"jQuery('#list2').restoreRow('"+cl+"');\" />"; 
			jQuery("#list2").jqGrid('setRowData',ids[i],{act:be+se+ce}); } },
			editurl: "/sharedcontacts/main.do?cmd=updateStatus",
		//cellEdit: true, //if true, onSelectRow event can not be used
		//editurl:"/sharedcontacts/main.do?cmd=list_data",
		//cellurl: "/sharedcontacts/main.do?cmd=actmodifysimply",
		//beforeSelectRow: function (rowid, e) {
		//	alert("beforeSelectRow");
		//},
		//onSelectRow: function(id){
		//	document.location.href = "/sharedcontacts/main.do?cmd=details&id="+id;
		//},
		caption:"Registered Customers",
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
			{}, // search options
			{} // view options
	);
	jQuery("#list2").setGridParam({url:'/sharedcontacts/main.do?cmd=customers-list-data'}).trigger("reloadGrid");
	
	
});
function editLinkFormatter (cellvalue, options, rowObject)
{
	
   return '<a href="/sharedcontacts/main.do?cmd=customers&domain=' + rowObject.domain + '">Check Status </a>';
}
function totalFormatter (cellvalue, options, rowObject)
{
	
   return '<span style="text-decoration:underline;" onclick="showTotalUsers(\'' + rowObject.domain + '\')">'+ cellvalue+'</a>';
}
function syncedFormatter (cellvalue, options, rowObject)
{
	 return '<span style="text-decoration:underline;" onclick="showSyncUsers(\'' + rowObject.domain + '\')">'+ cellvalue+'</a>';
}
function userFormatter (cellvalue, options, rowObject)
{
	return '<span style="text-decoration:underline;" onclick="showActiveUsers(\'' + rowObject.domain + '\')">'+ cellvalue+'</a>';
}

function showTotalUsers(domain){
	makeAjax("/getTotalUsers.do",domain,"All Domain Users");
}

function showSyncUsers(domain){
	makeAjax("/getSyncUsers.do",domain,"Synced Users");
}

function showActiveUsers(domain){
	makeAjax("/getNscUsers.do",domain,"Active Users");
}

function makeAjax(callUrl,domain,title){
	$("#formTitleBar .title").html(title);
	$("#loadingImg").show();
	$.ajax({
		url : callUrl,
		data:{"domainName":domain},
		success:function(result){
			$("#loadingImg").hide();
			if(!result){
				alert("No records fetched")
			}else{
			//var arr = result.split(",")
			var tbl ="";
			var arr = result.split(",")
			var len = arr.length-1;
			if(len>1){
				for( var s in arr){
					if(s!=len){
					tbl += "<li>" + arr[s] +"</li>" ;
					}
				}
				tbl += "<p>Total Users : " + len +"</p>" ;
			}	else if(len==1){
				tbl += "<p>" + arr[0] +"</p>" ;
			}		
			tbl += "<p><b>" + arr[len] +"</b></p>" ;	
				$("#userList ul").html(tbl);
				$("#userList").show();
			}
		},
		error:function(result){
			$("#loadingImg").hide();
			alert("Error while retreiving list. Please try again later")
		}
	});
	
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
<body>
<div id="confirmSubmitMessage" title=" "></div>
<table width="950px" align="center" border="0">
	<tr>
		<td align="center">
			<jsp:include page="/sharedcontacts/main.do?cmd=getheader" flush="true" />
		</td>
	</tr>
	<tr>
		<td align="center">
		<div style="text-align:left;margin:10px 0px 2px 0px;float:right;">
		</div>	
		<div style="clear:both;min-height: 405px;">
			<form name="listFrm">
				<table id="list2"></table> 
				<div id="pager2"></div>
			</form>
		</div>
		</td>
	</tr>
	<tr><td>
<hr noshade size=1 width="100%"/></td>
</tr>
	<tr valign="top">
		<td align="center" valign="top">
			<jsp:include page="/sharedcontacts/main.do?cmd=getfooter" flush="true" />
		</td>
	</tr>	
</table>
<div id="userList">
<div id="formTitleBar">
<div class='title'>abcd</div>
<div onclick="$('#userList').hide();" class='windowButtons'>X</div>
</div>
<ul></ul>
</div>
<img id="loadingImg" src="/img/319.gif" style="display:none;left: 47%;position: absolute;top: 30%;" />

</body>
</html>
<style>
#userList{
display:none;
		   background: none repeat scroll 0 0 white;
    border: 3px outset gray;
    left: 40%;
    padding: 5px;
    position: absolute;
    top: 25%;
    width: 250px;       
    }
  #userList ul{  
     overflow-x: hidden;
    overflow-y: scroll;
    height:300px;
    }
    #formTitleBar{
border:2px solid black;
 height: 22px;
 padding:0 3px; 
}

#formTitleBar .title {
float:left;
}
#formTitleBar .windowButtons {
float:right;
padding: 0 3px;
 border: 2px solid black;
 line-height: 15px;
 margin-top: 1px;
 cursor:pointer;
}
</style>