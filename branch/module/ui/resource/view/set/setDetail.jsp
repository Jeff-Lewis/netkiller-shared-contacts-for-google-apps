<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>

<script type="text/javascript">
	$(function() {
		$("#menu-contaner a").removeClass("selectmenu");
		$("#termTab").addClass("selectmenu");
		tabAlreadySelected('set','Set');
	});
</script>

<!-- Start page content -->
<div class="breadcrumb">

	<brc:breadcrumb></brc:breadcrumb>
</div>



<form:form id="setForm" action="/set/editForm.do" modelAttribute="set"
	method="post">

	<input type="hidden" name="paramid"
		value="<c:out
					value="${set.key.id}" />" />

			<div class="clear"></div>
			<div class="entity-detail">
				<c:choose>
					<c:when
						test="${sec:hasWritePermission(appUser,'Set',dataContext)}">
						<div class="entity-edit" onclick="editSet()">
							<a href="#"><span class="entity-edit-icon">Edit</span> </a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="entity-edit">
							<a href="#"><span class="entity-edit-icon">Edit</span> </a>
						</div>
					</c:otherwise>
				</c:choose>
			<%-- 	<c:choose>
					<c:when
						test="${sec:hasDeletePermission(appUser,'Set',dataContext)}">
						<div class="entity-delete" onclick="deleteSet()">
							<a href="#"><span class="entity-delete-icon"> Delete</span> </a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="entity-delete">
							<a href="#"><span class="entity-delete-icon"> Delete</span> </a>
						</div>
					</c:otherwise>
				</c:choose> --%>
				<div class="entity-close" onclick="closeForm()">
							<a href="#"><span class="entity-close-icon"> Close</span>
							</a>
						</div>
				<div class="clear"></div>
			</div>
			<div class="entity-grid-block">
				<div class="entity-title">
					<span class="fl"><c:out value="${set.setName}" />
					</span>
					<div class="clear"></div>
				</div>
			</div>
			<div class="entity-info-block">
				<div class="entity-info">
					<div class="left-div" style="width:100%;border-right:0;">
						<div class="entity-general-info">General Information</div>
						<div class="entity-general-info-row mrt10">
							<div class="leftdiv">Set Name</div>
							<div class="rightdiv">
								<c:out value="${set.setName}" />
							</div>
							<div class="clear"></div>
						</div>
						<div class="entity-general-info-row">
							<div class="leftdiv">Set Order</div>
							<div class="rightdiv">
								<c:out value="${set.setOrder}" />
							</div>
							<div class="clear"></div>
						</div>
						<div class="entity-general-info-row">
							<div class="leftdiv"> Parent Set</div>
							<div class="rightdiv">
								<c:choose>
					<c:when test="${sec:hasReadPermission(appUser,'Set',dataContext)}">
						<a
							href="/set/showDetail.do?paramid=<c:out
					value='${set.parentSetKeyString}' />"><c:out
							value='${set.parentSetBusinessKey}' /></a>
					</c:when>
					<c:otherwise>
						<c:out value='${set.parentSetBusinessKey}' />
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
		window.location = "/set/close.do";
	}
function editSet() {
	document.getElementById('setForm').action="/set/editForm.do";
	document.getElementById('setForm').submit();
}

//-->
</script>
<!-- Body events -->
<!-- End page content -->