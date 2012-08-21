<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.tableHeader	{
    background:  url("/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
    font-weight: bold;
    text-align: left;
}

#table td{
white-space:pre-wrap;
}
</style>
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/jqgrid/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet"
	media="handheld,print,projection,screen,tty,tv"
	href="/css/themes/common.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/main.css" />
<link type="text/css" href="/css/themes/redmond/jquery.ui.theme.css"
	rel="stylesheet" />
<link type="text/css" href="/css/themes/redmond/jquery.ui.autocomplete.css"
	rel="stylesheet" />

<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<script src="/js/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="/js/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="/js/ui/i18n/jquery-ui-i18n.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="/js/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/ui/i18n/jquery.ui.datepicker-en-GB.js"></script>
<script type="text/javascript" src="/js/google/jsapi.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.position.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="/js/util/autocompleteutil.js"></script>
<style>
.tableHeader	{
    background:  url("/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
    font-weight: bold;
    text-align: left;
}
</style>
<script type="text/javascript">
		$(document).ready(function(){
			 $("#teacherList").attr("disabled","disabled");
			 $("#subjectList").attr("disabled","disabled");
			 drawTimeTable("Primary1");
		 });
      google.load('visualization', '1', {packages: ['table']});
      
</script>

<script type="text/javascript">

	function getAllClasses(){
		var classList ='<option selected="selected">--Select--</option>' + 
		'<option value="Primary1">Primary1</option> ' +
		'<option value="Primary2">Primary2</option>' +
		'<option value="Secondary1">Secondary1</option>' +
		'<option value="Secondary2">Secondary2</option>' +
		'<option value="Senior Secondary">Senior Secondary</option>' 
		$('#classLevel').append(classList);
	}
     function setClassLevel(classLevel) {
    	
	     if (classLevel == '0'){
	     	//$("#pageBody").height(0);
			document.getElementById('table').innerHTML='';
			parent.resizeCaller();
		}else {
		//$("#pageBody").height(0);
		drawTimeTable(classLevel);
     	}
     }
      
     function addColumns(data){   
		var colN;
			$.ajax(
			    {
			       type: "POST",
			       url: "/gadget/getTimeTableColumnsForTodaysView.do",
			       data: "",
			       dataType: "json",
			       async: false,
			       success: function(result)
			       {	
			    	  colN = result.colNames;			       
			       },
			       error: function(x, e)
			       {
			          //  alert(x.readyState + " "+ x.status +" "+ e.msg);   
			       }
			    });	
		return colN;
	} 
      
     function drawTimeTable(classLevel) {
     	 var options = {allowHtml: true, sort: 'disable', cssClassNames:{headerRow: 'tableHeader'}};
	  var data = new google.visualization.DataTable();
	  var colN = addColumns(data);
      for ( name in colN ) {		    		
   	   if (colN.hasOwnProperty(name)) { 
   		   data.addColumn('string',colN[name]);
   		   }

      }
     	$.ajax ({
     		type : "POST",
     		url : '/gadget/todaysScheduleView.do?classLevel='+classLevel ,
     		data : "",
     		success : function(tableData) {
     			var html = "";
	  				var p = eval(tableData);
	  				var length= p.length;
	  				data.addRows(length);
	  				var row = 0 ;
	  				for (var key in p) {
	  					var col = 0 ;
	  					var map = eval(p[key]);
	  					var className = map.className ;	  					
	  					var cell = eval(map.cell) ;
	  					
	  					data.setCell(row, col,className);
	  					col ++ ;
	  					jQuery.each(cell,function(){		  						
	  						data.setCell(row, col,this.toString());
	  						col ++ ;
	  					});
	  					row ++ ;
	 				}  					
	  			visualization = new google.visualization.Table(document.getElementById('table'));
	  		 	visualization.draw(data, options);
	  		 	$(".google-visualization-table-table").attr('style','width:100%');
	  		 	parent.resizeCaller();
     		},
     		error: function(x,e) {
     		}
     	});
     } 
        
     function getAllTeachers() {
    	 
    	 $("#teacherList").removeAttr('disabled');
    	 $("#classLevel").attr("disabled","disabled");
		 $("#subjectList").attr("disabled","disabled");
    	 
     	$.ajax({
     		type : "POST",
     		url : '/gadget/getAllGlobalFilteredTeachers.do' ,
     		data : "",
     		success : function(teachers) {
     			document.getElementById("teacherList").options.length = 0;
				$("#teacherList").html($("#teacherList").html()+'<option value="">--Select--</option>');
     			var teacherList = eval(teachers);
     			for(var i in teacherList) {
     				var data = teacherList[i];
     				var name = data.name;
     				var id = data.id;
     				$("#teacherList").html($("#teacherList").html()+"<option value=\""+id+"\">"+name+"</option>");
     			}
     		},
     		error: function(x,e) {
     		}
     	});
     }   
        
     function getTeacherTodaysView(teacherId){
     
	    var options = {allowHtml: true, sort: 'disable', cssClassNames:{headerRow: 'tableHeader'}};
		  var data = new google.visualization.DataTable();
		  var colN = addColumns(data);
	      for ( name in colN ) {		    		
	   	   if (colN.hasOwnProperty(name)) { 
	   		   data.addColumn('string',colN[name]);
	   		   }
	
	      }
	      
     	$.ajax({
     		type : "POST",
     		url : '/gadget/todaysScheduleTeacherView.do?teacherId='+teacherId ,
     		data : "",
     		success : function(tableData) {
     			var html = "";
	  				var p = eval(tableData);
	  				var length= p.length;
	  				data.addRows(length);
	  				var row = 0 ;
	  				for (var key in p) {
	  					var col = 0 ;
	  					var map = eval(p[key]);
	  					var className = map.className ;	 
	  					data.setCell(row, col,className);
	  					col ++ ;
	  					
	  					var cell = eval(map.cell) ;
	  					for(var i in cell) {
	  						var colNumber  = cell[i].periodColumnNumer;
	  						var cellValue = cell[i].rowValue;
	  						data.setCell(row, colNumber,cellValue);
	  					}
	  					row ++ ;
	 				}  					
	  			visualization = new google.visualization.Table(document.getElementById('table'));
	  		 	visualization.draw(data, options);
	  		 	$(".google-visualization-table-table").attr('style','width:100%');
	  		 	parent.resizeCaller();
     		},
     		error: function(x,e) {
     		}
     	});
     }
     
     function getAllSubjects() {
    	
    	 $("#subjectList").removeAttr('disabled');
	   	$("#classLevel").attr("disabled","disabled");
		$("#teacherList").attr("disabled","disabled");
    	 
      	$.ajax({
      		type : "POST",
      		url : '/gadget/getAllGlobalFilteredSubjects.do' ,
      		data : "",
      		success : function(subjects) {
      			document.getElementById("subjectList").options.length = 0;
 				$("#subjectList").html($("#subjectList").html()+'<option value="">--Select--</option>');
      			var subjectList = eval(subjects);
      			for(var i in subjectList) {
      				var data = subjectList[i];
      				var name = data.name;
      				var id = data.id;
      				$("#subjectList").html($("#subjectList").html()+"<option value=\""+id+"\">"+name+"</option>");
      			}
      		},
      		error: function(x,e) {
      		}
      	});
      }  
     
     
     function getSubjectTodaysView(subjectId){
         
 	    var options = {allowHtml: true, sort: 'disable', cssClassNames:{headerRow: 'tableHeader'}};
 		  var data = new google.visualization.DataTable();
 		  var colN = addColumns(data);
 	      for ( name in colN ) {		    		
 	   	   if (colN.hasOwnProperty(name)) { 
 	   		   data.addColumn('string',colN[name]);
 	   		   }
 	
 	      }
 	      
      	$.ajax({
      		type : "POST",
      		url : '/gadget/todaysScheduleSubjectView.do?subjectId='+subjectId ,
      		data : "",
      		success : function(tableData) {
      			var html = "";
 	  				var p = eval(tableData);
 	  				var length= p.length;
 	  				data.addRows(length);
 	  				var row = 0 ;
 	  				for (var key in p) {
 	  					var col = 0 ;
 	  					var map = eval(p[key]);
 	  					var className = map.className ;	 
 	  					data.setCell(row, col,className);
 	  					col ++ ;
 	  					
 	  					var cell = eval(map.cell) ;
 	  					for(var i in cell) {
 	  						var colNumber  = cell[i].periodColumnNumer;
 	  						var cellValue = cell[i].rowValue;
 	  						data.setCell(row, colNumber,cellValue);
 	  					}
 	  					row ++ ;
 	 				}  					
 	  			visualization = new google.visualization.Table(document.getElementById('table'));
 	  		 	visualization.draw(data, options);
 	  		 	$(".google-visualization-table-table").attr('style','width:100%');
 	  		 	parent.resizeCaller();
      		},
      		error: function(x,e) {
      		}
      	});
      }
     
</script>
<head>
<title></title>
</head>


<body id="pageBody">

<div  id="widget" class="ui-widget">	
			
	<input type="radio" name="ViewType" value="class" checked onClick="$('#classLevel').removeAttr('disabled');$('#subjectList').attr('disabled','disabled');$('#teacherList').attr('disabled','disabled');$('#subjectList').empty();$('#teacherList').empty();getAllClasses();"/> Class
	
	<select name="classLevel" id="classLevel" onchange="setClassLevel(this.value)" style="font-family: Arial,Helvetica,sans-serif; font-size: 85%;">
		<option value="Primary1" selected="selected">Primary1</option>
		<option value="Primary2">Primary2</option>
		<option value="Secondary1">Secondary1</option>
		<option value="Secondary2">Secondary2</option>
		<option value="Senior Secondary">Senior Secondary</option>
	</select>
	<input type="radio" name="ViewType" value="teacher" onClick="$('#subjectList').empty();$('#classLevel').empty();getAllTeachers()" /> Teacher
	<select name="teacherList" id="teacherList" onChange="getTeacherTodaysView(this.value)" style="font-family: Arial,Helvetica,sans-serif; font-size: 85%;">
	</select>
	<input type="radio" name="ViewType" value="subject" onClick="$('#classLevel').empty();$('#teacherList').empty();getAllSubjects()"/> Subject	
	<select name="subjectList" id="subjectList" onChange="getSubjectTodaysView(this.value)" style="font-family: Arial,Helvetica,sans-serif; font-size: 85%;">
	</select><br/>
</div>
<div id="table"></div>
</body>
</html>
