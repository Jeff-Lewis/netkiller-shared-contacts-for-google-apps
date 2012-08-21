<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>

<script type="text/javascript">
	$(function() {
		$("#menu-contaner a").removeClass("selectmenu");
		$("#valueTab").addClass("selectmenu");
		tabAlreadySelected('value','Value');
	});
</script>

<!-- Start page content -->
<div class="breadcrumb">

	<brc:breadcrumb></brc:breadcrumb>
</div>

<form:form id="valueForm" action="/value/editForm.do"
	modelAttribute="value" method="post">

	<input type="hidden" name="paramid"
		value="<c:out
					value="${value.key.id}" />" />
	

			<div class="clear"></div>
			<div class="entity-detail">
				<c:choose>
					<c:when
						test="${sec:hasWritePermission(appUser,'Value',dataContext)}">
						<div class="entity-edit" onclick="editValue()">
							<a href="#"><span class="entity-edit-icon">Edit</span> </a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="entity-edit">
							<a href="#"><span class="entity-edit-icon">Edit</span> </a>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="entity-close" onclick="closeForm()">
							<a href="#"><span class="entity-close-icon"> Close</span>
							</a>
						</div>
				<div class="clear"></div>
			</div>
			<div class="entity-grid-block">
				<div class="entity-title">
					<span class="fl"><c:out value="${value.value}" />
					</span>
					<div class="clear"></div>
				</div>
			</div>
			<div class="entity-info-block">
				<div class="entity-info">
					<div class="left-div" style="width:100%;border-right:0;">
						<div class="entity-general-info">General Information</div>
						<div class="entity-general-info-row mrt10">
							<div class="leftdiv">Value</div>
							<div class="rightdiv">
								<c:out value="${value.value}" />
							</div>
							<div class="clear"></div>
						</div>
						<div class="entity-general-info-row">
							<div class="leftdiv">Order Index</div>
							<div class="rightdiv">
								<c:out value="${value.orderIndex}" />
							</div>
							<div class="clear"></div>
						</div>
						<div class="entity-general-info-row">
							<div class="leftdiv">Parent Set</div>
							<div class="rightdiv">
								<c:choose>
					<c:when test="${sec:hasReadPermission(appUser,'Set',dataContext)}">
						<a
							href="/set/showDetail.do?paramid=<c:out
					value='${value.setKey.id}' />"><c:out
							value='${value.setBusinessKey}' /></a>
					</c:when>
					<c:otherwise>
						<c:out value='${value.setBusinessKey}' />
					</c:otherwise>
				</c:choose>
							</div>
							<div class="clear"></div>
						</div>
						<div class="entity-general-info-row">
							<div class="leftdiv">Parent Value</div>
							<div class="rightdiv">
								<c:choose>
					<c:when test="${sec:hasReadPermission(appUser,'Value',dataContext)}">
						<a
							href="/value/showDetail.do?paramid=<c:out
					value='${value.parentValueKey.id}' />"><c:out
							value='${value.parentValueBusinessKey}' /></a>
					</c:when>
					<c:otherwise>
						<c:out value='${value.parentValueBusinessKey}' />
					</c:otherwise>
				</c:choose>
							</div>
							<div class="clear"></div>
						</div>
						
					</div>
					</div>

</div>
		</form:form>

<script type="text/javascript">
<!--
	function closeForm() {
		window.location = "/value/close.do";
	}
function editValue() {
	document.getElementById('valueForm').action="/value/editForm.do";
	document.getElementById('valueForm').submit();
}

//-->
</script>
<!-- Body events -->
<!-- End page content -->