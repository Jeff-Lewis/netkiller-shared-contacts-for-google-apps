<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="/js/google/jsapi.js"></script>
<style type="text/css">

.tableHeader	{
    background:  url("/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
    font-weight: bold;
    text-align: left;
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
			  var options = {allowHtml: true,cssClassNames:{headerRow: 'tableHeader'}};
			  var data = new google.visualization.DataTable();			  
			  data.addColumn('string', 'Title');
			  data.addColumn('string', 'Link');
			  data.addColumn('date', 'Date');
			  data.addColumn('string', 'Site Name');
			  data.addColumn('string', 'Description');
			  
			$.ajax({
				url : "announcement/data.do" ,
			
			  success : function(fetchedData) {					
	  			data.addRows(fetchedData.length);
				var entities = fetchedData;
				var currentDate = "";
				var row = 0;
				jQuery.each(entities,function(){					
					var dt = new Date(this.lastUpdatedDate) ;					
					var col = 0 ;									
					data.setCell(row, col,this.title,undefined, {style: 'text-align:left;width:25%'});
					col ++ ;
					data.setCell(row, col,this.link);
					col ++ ;
					data.setCell(row, col,new Date(dt.getFullYear(), dt.getMonth(), dt.getDate()),undefined, {style: 'text-align:left;width:14%'});
					
					col++;
					data.setCell(row, col,this.classSiteName,undefined, {style: 'text-align:left;width:14%'});
					col++ ;
					data.setCell(row, col,this.content,undefined, {style: 'text-align:left;width:47%'});
					row++;					
					}) ;						   
			     visualization = new google.visualization.Table(document.getElementById('table'));
			   
  				var dateFormat = new google.visualization.DateFormat({pattern: "dd/MM/yyyy"});
  				dateFormat.format(data, 2);
  				 var linkFormatter = new google.visualization.PatternFormat('<a href="javascript:parent.window.location=\'{1}\'">{0}</a>');
                linkFormatter.format(data, [0, 1]); // Apply formatter and set the formatted value of the first column.
                 var view = new google.visualization.DataView(data);
                view.setColumns([0,2,3,4]);
             	options['sortColumn'] = 1;
             	options['sortAscending'] = false;
	  			options['page'] = 'enable';
	  	 		options['pageSize'] = 5;
	  	  //		options['pagingSymbols'] = {prev: 'prev', next: 'next'};
	  	  		options['pagingButtonsConfiguration'] = 'auto';
	  		 	visualization.draw(view, options);
	  			$(".google-visualization-table-table").attr('style','width:100%');
				parent.resizeCaller();
				}, 
				error : function() {
				}
			}) ;
		}) ;
</script>

<body>
 <div id="table"></div>
<div id="announcementDiv" class="divStyle"></div>
</body>
</html>
