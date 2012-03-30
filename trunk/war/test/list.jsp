<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
$(document).ready(function() {
	jQuery("#list2").jqGrid({ 
		url:'list_contacts.jsp', 
		datatype: "json", 
		colNames:['No.', 'Delete', 'Name', 'Company', 'Email', 'Phone', 'Address', 'Notes'], 
		colModel:[ 
                   {name:'id',index:'id', width:35, align:"center"},
                   {name:'delete',index:'chkbox', sortable:false, formatter: "checkbox", formatoptions: {disabled : false}, editable: true, edittype:"checkbox", align:"center", width:35},		            
		           {name:'name',index:'name asc', width:60}, 
		           {name:'company',index:'company', width:60}, 
		           {name:'email',index:'email', width:90, align:"left"}, 
		           {name:'phone',index:'phone', width:90, align:"left"}, 
		           {name:'address',index:'address', width:150,align:"left"}, 
		           {name:'notes',index:'notes', width:150, sortable:false, align:"left"} 
		         ], 
		rowNum:10, 
		rowList:[10,20,30], 
		pager: '#pager2', 
		sortname: 'id', 
		viewrecords: true, 
		sortorder: "desc", 
		width: 1024,
		height: 350,
		caption:"JSON Example" 	
	}); 
	jQuery("#list2").jqGrid('navGrid','#pager2',
			{refresh:false,add:false,edit:false,del:false,search:false,view:false},
			{}, // edit options
			{}, // add options
			{}, // del options
			{}, // search options
			{} // view options
	);	

	jQuery("#list2").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true});
	jQuery("#list2").jqGrid('navButtonAdd',"#pager2",{caption:"새로고침",title:"목록을 새로고침 합니다.",buttonicon :'ui-icon-refresh',
		onClickButton:function(){
			jQuery("#list2").trigger('reloadGrid');
		} 
	});
	jQuery("#list2").jqGrid('navButtonAdd',"#pager2",{caption:"검색하기",title:"결재 문서를 검색합니다.",buttonicon :'ui-icon-search',
		onClickButton:function(){
			var searchWindow = window.open("/jsp/search.jsp", "searchWindow", "location=1,status=1,scrollbars=1,width=1010,height=750");
			searchWindow.focus();
		} 
	});	
	
});
</script>
<title>List</title>
</head>
<body>
<table width="1000px" align="center">	
	<tr>
		<td align="center">
			<table id="list2"></table> 
			<div id="pager2"></div> 
		</td>
	</tr>
</table>
</body>
</html>