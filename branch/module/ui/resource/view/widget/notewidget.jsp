<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.tableHeader	{
    background:  url("/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
    font-weight: bold;
    text-align: left;
}
</style>
<input type="hidden" id="advSeachParam" value="<c:out
					value="${advSearchText}" />"/>
<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_parent'}">
		
		<input type="hidden" id="sender" name="sender"
			value="teacher" />
</c:if>
<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher'}">
		<input type="hidden" id="sender" name="sender"
			value="parent" />
	</c:if>
	
<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_student'}">
		<input type="hidden" id="sender" name="sender"
			value="teacher" />
	</c:if>	
	

<link rel="stylesheet" type="text/css" media="screen"
	href="/css/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/main.css" />
<link type="text/css" href="/css/themes/redmond/jquery.ui.theme.css"
	rel="stylesheet" />

<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<script src="/js/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="/js/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="/js/ui/i18n/jquery-ui-i18n.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="/js/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/ui/i18n/jquery.ui.datepicker-en-GB.js"></script>
<script type="text/javascript" src="/js/google/jsapi.js"></script>

    <script type="text/javascript">
      google.load('visualization', '1', {packages: ['table']});
      
    </script>
<script type="text/javascript">



$(function() {
	  // Create and populate the data table.
	  var options = {allowHtml: true,cssClassNames:{headerRow: 'tableHeader'}};
	  var data = new google.visualization.DataTable();
	  data.addColumn('number', 'Id');
	  data.addColumn('date', 'Date');
	  data.addColumn('string', 'Sender');
	  data.addColumn('string', 'Class');
	  data.addColumn('string', 'Subject');
	  
	  $
	  .ajax({
	  	url : "/notewidgetdata.do?advSearchText=&_search=false&nd=1310370142138&rows=20&page=1&sidx=&sord=asc",
	  	success : function(tableData) {
	  		var html = "";
	  		var p = tableData;
	  		for (var key in p) {
	  			  if (p.hasOwnProperty("rows")&& key == "rows" ) {
	  				var row = 0;
	  				
	  				var length= p[key].length;
	  				data.addRows(length);
	  			 	  jQuery
	  					.each(
	  							p[key],
	  							function() {
	  								
	  								 var innerList = this;
	  								for (var innerKey in innerList) {
	  									var col=0;
	  									  if (innerList.hasOwnProperty("cell")&& innerKey == "cell" ) {
	  										  jQuery
	  											.each(
	  													innerList[innerKey],
	  													function() {
	  														if(col==0)	{
	  															data.setCell(row, col,Number(this),undefined, {style: 'text-align:left;width:10%'});
	  														}
	  														else	{
	  															if(col==1)	{
		  															var dt = new Date(this.toString()) ;
		  															data.setCell(row, col,new Date(dt.getFullYear(), dt.getMonth(), dt.getDate()),undefined, {style: 'text-align:left;width:16%'});
		  														}
	  															else if(col==4)	{
	  																data.setCell(row, col, this.toString(),undefined, {style: 'text-align:left;width:42%'});
	  															}
	  															else
	  															data.setCell(row, col, this.toString(),undefined, {style: 'text-align:left;width:16%'});
	  														}
	  														
	  															col++;
	  													});
	  										  row++
	  									  }
	  									} 
	  							}); 
	  			  }
	  			}
	  			 visualization = new google.visualization.Table(document.getElementById('table'));
	  			var dateFormat = new google.visualization.DateFormat({pattern: "dd/MM/yyyy"});
  				dateFormat.format(data, 1);
	  		 	 var formatter = new google.visualization.PatternFormat('<a href="javascript:parent.window.location=\'/note/showDetail.do?paramid={0}\'">{0}</a>');
	  		
	  		  	formatter.format(data, [0, 1]); // Apply formatter and set the formatted value of the first column.
	  		  	var view = new google.visualization.DataView(data);
             	view.setColumns([0,1,2,3,4]);
             	options['sortColumn'] = 1;
             	options['sortAscending'] = false;
	  			options['page'] = 'enable';
	  	 		options['pageSize'] = 5;
	  	 // 		options['pagingSymbols'] = {prev: 'prev', next: 'next'};
	  	  		options['pagingButtonsConfiguration'] = 'auto';
	  		 	visualization.draw(view, options);	  		 	
 	  			
				$(".google-visualization-table-table").attr('style','width:100%');
				parent.resizeCaller();
	  		
	  	},
	  	error : function() {
	  		
	  	}
	  //dataType: dataType
	  });

	 

	  // Create and draw the visualization.
	 
	});

$(function() {

	// attache selected tab class with the tab:  class="currentTab primaryPalette"
	$('#noteTab').addClass('currentTab');
	$('#noteTab').addClass('primaryPalette');
});

//-->
</script>
<head>
</head>
<body>
 <div id="table"></div>
<table id="list4" style="width: 100%;"></table>
</body>
</html>