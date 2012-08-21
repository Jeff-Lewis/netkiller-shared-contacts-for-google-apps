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
<style type="text/css">

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
	$(document).ready(function(){
			
			$.ajax({
				url : "showDueAssignment.do" ,
			
			  success : function(fetchedData) {
				
				var html = "<table class=\"detailList\" style=\" border: 1px solid #CCCCCC; width:100%;\">";
				var entities = fetchedData;

				jQuery.each(entities,function(){
					var dt = new Date(this.submissionDate) ;
					var month = dt.getMonth() + 1;
					var dateFormat = dt.getDate()+"/"+ month + "/"+dt.getFullYear();
					html += "<tr><td class=\"colValues\"><a href=\'javascript:parent.window.location=\"/assignment/showDetail.do?paramid="+this.assignmentId + "\"\'\"style=\"color: #0033CC;\">"+this.title+"</a>" ;
					if(this.link!=null) {
						html+= "&nbsp;(<a href=\'"+this.link+"\'>Download</a>)";
					}
						html+="&nbsp;"+dateFormat+"&nbsp;Teacher :"+this.teacherName+"&nbsp;Class :"+this.className+"&nbsp;Subject :"+this.subjectName+"<br/>"+this.content+"</td></tr>" ;
					}) ;
					
				html+="</table>" ;
			    $('#dueAssignmentDiv').html(html) ;
				}, 
				error : function() {
					$('#dueAssignmentDiv').html("<b> Error occured</b>") ;
				}
			}) ;
		}) ;
</script>

<body>
<div id="dueAssignmentDiv" class="divStyle"></div>
</body>
</html>