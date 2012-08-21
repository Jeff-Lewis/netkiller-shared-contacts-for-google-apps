<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
function makeActiveEntitiesList(url)	{
	var list="";
	$('#entityList input:checked').each(function(){
		
		list = list+$(this).val()+",";
	})
	list = list.substring(0,list.length-1);
	$('#newActiveEntitiesList').val(list);
	document.cacheForm.action = url;
	document.cacheForm.submit();
}


</script>
<c:if test="${activeEntities!=null}">
<script type="text/javascript">
$(function(){
	var activeEntities = "${activeEntities}".split(',');
	
	for(var i=0;i<activeEntities.length;i++)	{
		$("#"+activeEntities[i]).attr('checked',true);
	}
	
});</script>
</c:if>
<title>Insert title here</title>
</head>
<body>
<form name="cacheForm" method="post" action="#">
<div id="entityList">

<input type="hidden" name ="newActiveEntitiesList" id ="newActiveEntitiesList">
<c:forEach var="entity" items="${entities}">
<input type="checkbox" id="${entity}" name = "${entity}" value="${entity}"/>${entity}<br>
</c:forEach>
</div>
<input type="button" value="Load Cache" onclick = "makeActiveEntitiesList('/cache/reloadcache.do');">
<input type="button" onclick = "makeActiveEntitiesList('/cache/removecache.do');" value="Remove All Cache" >
</form>
</body>
</html>