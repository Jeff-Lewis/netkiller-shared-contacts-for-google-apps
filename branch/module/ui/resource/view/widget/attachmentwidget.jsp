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
		  var options = {allowHtml: true,cssClassNames:{headerRow: 'tableHeader'}};
		  var data = new google.visualization.DataTable();
		  var myView = new google.visualization.DataView(data)

		  data.addColumn('string', 'Title');		
		  data.addColumn('number', 'Id');
		  data.addColumn('date', 'Submission Date');
		  data.addColumn('string', 'Class');
		  data.addColumn('string', 'Subject');
		  data.addColumn('string', 'Teacher');
		  data.addColumn('string', 'Download');
		  data.addColumn('string', 'Link');
		  
		  
		  $
			$.ajax({
				url : "showattachment/data.do" ,
			
				success : function(fetchedData) {
					
					var entities = fetchedData;
					data.addRows(entities.length);
					var row = 0;
					jQuery.each(entities,function(){				
						var dt = new Date(this.submissionDate) ;
						var month = dt.getMonth() + 1;
						var dateFormat = dt.getDate()+"/"+ month + "/"+dt.getFullYear();
		  			   
						data.setCell(row, 0,this.title,undefined, {style: 'text-align:left;width:30%'});
						 data.setCell(row, 1,Number(this.assignmentId));
						//data.setCell(row, 2,dateFormat);
						data.setCell(row, 2,new Date(dt.getFullYear(), dt.getMonth(), dt.getDate()),undefined, {style: 'text-align:left;width:14%'});
						data.setCell(row, 3,this.className,undefined, {style: 'text-align:left;width:14%'});
						data.setCell(row, 4,this.subjectName,undefined, {style: 'text-align:left;width:14%'});
						data.setCell(row, 5,this.teacherName,undefined, {style: 'text-align:left;width:14%'});
						data.setCell(row, 6,"Download",undefined, {style: 'text-align:left;width:14%'});
						data.setCell(row, 7,this.link,undefined, {style: 'text-align:left;width:14%'});					
					row++;
					}) ;					
					 visualization = new google.visualization.Table(document.getElementById('table'));	
					 var dateFormat = new google.visualization.DateFormat({pattern: "dd/MM/yyyy"});
		  				dateFormat.format(data, 2);
					 var formatter = new google.visualization.PatternFormat('<a href="javascript:parent.window.location=\'/assignment/showDetail.do?paramid={1}\'">{0}</a>');
					 var downloadLinkFormatter = new google.visualization.PatternFormat('<a target="_blank" href=\'{1}\'>Download</a>');
				// var downloadLinkFormatter =new google.visualization.PatternFormat('<a href="javascript:parent.window.location=\'/ashhhhhsignment/showDetail.do?paramid={1}\'">{0}</a>');
					  formatter.format(data, [0, 1]); // Apply formatter and set the formatted value of the first column.
					  downloadLinkFormatter.format(data, [6, 7]);
					  var view = new google.visualization.DataView(data);
					  view.setColumns([0,2,3,4,5,6]); // Create a view with the first column only.

		             	options['sortColumn'] = 1;
		             	options['sortAscending'] = false;
		  			options['page'] = 'enable';
		  	 		options['pageSize'] = 5;
		  	//  		options['pagingSymbols'] = {prev: 'prev', next: 'next'};
		  	  		options['pagingButtonsConfiguration'] = 'auto';
		  		 	visualization.draw( view, options);
		  		 	var dateFormat = new google.visualization.DateFormat({pattern: "dd/MM/yyyy"});
	  				dateFormat.format(data, 2);
		  			$(".google-visualization-table-table").attr('style','width:100%');
		  		 	
		  		/* 	 $(".google-visualization-table-tr-head td").attr('style',' background: url("images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC');
					$(".google-visualization-table-tr-head").attr('style','background: url("images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC');
					$(".google-visualization-table-tr-head-nonstrict").attr('style','background: url("images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC');  */
				/* 	 $(".google-visualization-table-tr-head td").attr('style','text-align:left');
					 $(".google-visualization-table-tr-head").attr('style','text-align:left');
					 $(".google-visualization-table-tr-head-nonstrict").attr('style','text-align:left'); */
			
					parent.resizeCaller();
		  		 	
					
					}, 
					error : function() {
						$('#attachmentDiv').html("<b> Error occured</b>") ;
					}
				}) ;
			}) ;
</script>

<body>
<div id="table"></div>
<div id="attachmentDiv" class="divStyle"></div>
</body>
</html>