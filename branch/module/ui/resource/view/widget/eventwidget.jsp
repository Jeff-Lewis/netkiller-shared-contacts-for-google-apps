<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/js/google/jsapi.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<style type="text/css">
.tableHeader	{
    background:  url("/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
    font-weight: bold;
    text-align: left;
}
.header{
    color: inherit;
    font-size: 1.2em;
    padding-bottom: 0;
    border: 1px solid #CCCCCC;
     background : url("images/cleang1.jpg") repeat-x scroll left bottom transparent ;
}

.colValues {
    list-style-image: none;
    margin-bottom: 5px;
    padding-bottom: 3px;
    padding-right: 3px;
    border-bottom: 1px dotted #CCCCCC;
    width: 100%;
}

.divStyle {
	margin: 5px auto 5px 0pt; text-align: left; clear: both; display: block;
}
</style>
<script type="text/javascript">
      google.load('visualization', '1', {packages: ['table']});
      
    </script>
<script type="text/javascript">
	$(document).ready(function(){
		// Create and populate the data table.
		  var options = {allowHtml: true,cssClassNames:{headerRow: 'tableHeader'},sort:'disable'};
		  var data = new google.visualization.DataTable();
		  var myView = new google.visualization.DataView(data)
		  data.addColumn('number', 'Id');
		  data.addColumn('string', 'Title');		
		  data.addColumn('string', 'Class');
		  data.addColumn('string', 'Teacher');
		  data.addColumn('string', 'From Date');
		  data.addColumn('string', 'To Date');
		  
		  
		  $
		  .ajax({
		  	url : "/event/widgetdata.do?advSearchText=&_search=false&nd=1310370142138&rows=10&page=1&sidx=fromDate&sord=desc",
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
		  															data.setCell(row, col,Number(this),undefined, {style: 'text-align:left;width:14%'});
		  														}
		  														else	{
		  															if(col==4 || col==5)	{
			  															var dt = new Date(this.toString()) ;
			  															var currentDate = new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes());
			  															data.setCell(row, col,this.toString(),undefined, {style: 'text-align:left;width:14%'});
			  														}
		  															else if(col==1){
		  																data.setCell(row, col, this.toString(),undefined, {style: 'text-align:left;width:30%'});
		  															}
		  															else
		  															data.setCell(row, col, this.toString(),undefined, {style: 'text-align:left;width:14%'});
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
	  				
		  		 	 var formatter = new google.visualization.PatternFormat('<a href="javascript:parent.window.location=\'/event/showDetail.do?paramid={1}\'">{0}</a>');
		  		
		  		  	formatter.format(data, [1, 0]); // Apply formatter and set the formatted value of the first column.
		  		  	var view = new google.visualization.DataView(data);
	             	view.setColumns([1,2,3,4,5]);
	             	
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

		 
			}) ;
</script>

<body>
<div id="table"></div>
<div id="attachmentDiv" class="divStyle"></div>
</body>
</html>