<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/form.css" rel="stylesheet" type="text/css" />
<script src="js/custom-form-elements.js" type="text/javascript"></script>
<script src="js/jquery-1.7.1.js" type="text/javascript"></script>
<script src="SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
<link href="SpryAssets/SpryMenuBarVertical.css" rel="stylesheet"
	type="text/css">

<style type="text/css">
.relatedLinkSelected {
	font-weight: bold;
	color: #BB4949;
	font-size: 18px;
	margin-bottom: 10px;
}
</style>

 <script type="text/javascript">
	$(function() {
		$("#loading").hide();
	});
</script> 
<!-- Start page content -->
 <div class="breadcrumb">

	<%-- <brc:breadcrumb></brc:breadcrumb> --%>
</div> 
<form:form id="contactForm" action="/contact/editForm.do"
	modelAttribute="contact" method="post">
	<input type="hidden" name="paramid"
		value="<c:out
					value="${contact.key.id}" />" />

	<div class="clear"></div>
	<div class="entity-detail">
		<!-- <div class="entity-edit" onclick="editContact()">
			<a href="#"><span class="entity-edit-icon">Edit</span> </a>
		</div>

		<div class="entity-delete" onclick="deleteContact()">
			<a href="#"><span class="entity-delete-icon"> Delete</span> </a>
		</div> -->

		<div class="entity-close" onclick="closeForm()">
			<a href="#"><span class="entity-close-icon"> Close</span> </a>
		</div>
		<div class="clear"></div>


	</div>

	<div class="entity-grid-block" style="width:1150px;">
		<div class="entity-title">
			<span class="fl"><c:out value="${student.fullName}" />&nbsp;
			</span>
			<div class="clear"></div>
		</div>
	</div>

	<div class="entity-info-block" style="width:1150px;">
		<div class="entity-info">
			<div class="left-div">
				<div class="entity-general-info">General Information</div>
				<div class="entity-general-info-row mrt10">
					<div class="leftdiv">First Name</div>
					<div class="rightdiv">
						<c:out value="${contact.firstName}" />
					</div>
					<div class="clear"></div>
				</div>
				<div class="entity-general-info-row">
					<div class="leftdiv">Last Name</div>
					<div class="rightdiv">
						<c:out value="${contact.lastName}" />
					</div>
					<div class="clear"></div>
				</div>
				<div class="entity-general-info-row">
					<div class="leftdiv">Full Name</div>
					<div class="rightdiv">
						<c:out value="${contact.fullName}" />
					</div>
					<div class="clear"></div>
				</div>
				<div class="entity-general-info-row">
					<div class="leftdiv">Company Name</div>
					<div class="rightdiv">
						<c:out value="${contact.cmpnyName}" />
					</div>
					<div class="clear"></div>
				</div>
				<div class="entity-general-info-row">
					<div class="leftdiv">Company DepartMent</div>
					<div class="rightdiv">
						<c:out value="${contact.cmpnyDepartment}" />
					</div>
					<div class="clear"></div>
				</div>

				<div class="entity-general-info-row">
					<div class="leftdiv">Company Title</div>
					<div class="rightdiv">
						<c:out value="${contact.cmpnyTitle}" />
					</div>
					<div class="clear"></div>
				</div>

				<div class="entity-general-info-row">
					<div class="leftdiv">Work Email</div>
					<div class="rightdiv">
						<c:out value="${contact.workEmail}" />
					</div>
					<div class="clear"></div>
				</div>

				<div class="entity-general-info-row">
					<div class="leftdiv">Home Email</div>
					<div class="rightdiv">
						<c:out value="${contact.homeEmail}" />
					</div>
					<div class="clear"></div>
				</div>


				<div class="entity-general-info-row">
					<div class="leftdiv">Other Email</div>
					<div class="rightdiv">
						<c:out value="${contact.otherEmail}" />
					</div>
					<div class="clear"></div>
				</div>

			
			</div>

			<div class="right-div" style="margin-top: 20px;">
			
			
				<div class="entity-general-info "></div>
					<div class="entity-general-info-row mrt10">
					<div class="leftdiv">Work Phone</div>
					<div class="rightdiv">
						<c:out value="${contact.workPhone}" />
					</div>
					<div class="clear"></div>
				</div>

				<div class="entity-general-info-row">
					<div class="leftdiv">Home Phone</div>
					<div class="rightdiv">
						<c:out value="${contact.homePhone}" />
					</div>
					<div class="clear"></div>
				</div>

				<div class="entity-general-info-row">
					<div class="leftdiv">Mobile Number</div>
					<div class="rightdiv">
						<c:out value="${contact.mobileNumber}" />
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="entity-general-info-row ">
					<div class="leftdiv">Work Address</div>
					<div class="rightdiv">
						<c:out value="${student.workAddress}" />
					</div>
					<div class="clear"></div>
				</div>
				<div class="entity-general-info-row">
					<div class="leftdiv">Home Address</div>
					<div class="rightdiv">
						<c:out value="${student.homeAddress}" />
					</div>
					<div class="clear"></div>
				</div>

				<div class="entity-general-info-row">
					<div class="leftdiv">Other Address</div>
					<div class="rightdiv">
						<c:out value="${student.otherAddress}" />
					</div>
					<div class="clear"></div>
				</div>


				<div class="entity-general-info-row">
					<div class="leftdiv">Notes</div>
					<div class="rightdiv">
						<c:out value="${student.notes}" />
					</div>
					<div class="clear"></div>
				</div>





			</div>




		</div>
	</div>










</form:form>

<script type="text/javascript">
	jQuery('#mysite-dropdown').bind('click', function() {
		jQuery('#hdr-popup').toggle();
		jQuery('#hdr-popup').css({
			'margin-left' : 0
		});
		jQuery(this).toggleClass('edu-username-red-arrow');
		jQuery(this).toggleClass('edu-username-white-arrow');
		return false;
	});
	jQuery('#hdr-popup').bind('click', function(e) {
		e.stopPropagation();
	});
	jQuery(document).bind('click', function() {
		jQuery('#hdr-popup').hide();
		jQuery('#mysite-dropdown').removeClass('edu-username-red-arrow');
		jQuery('#mysite-dropdown').addClass('edu-username-white-arrow');
	});
	var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {
		imgRight : "SpryAssets/SpryMenuBarRightHover.gif"
	});
</script>
<script type="text/javascript">
<!--
	function closeForm() {
		window.location = "/contact/close.do";
	}

	function updateContact() {
		document.getElementById('contactForm').action = "/contact/update.do";
		document.getElementById('contactForm').submit();
	}

	function editContact() {
		document.getElementById('contactForm').action = "/contact/editForm.do";
		document.getElementById('contactForm').submit();
	}
	function deleteContact() {
		var response = confirm('Are you sure?');
		if (response) {
			document.getElementById('contactForm').action = "/contact/delete.do";
			document.getElementById('contactForm').submit();
		}
	}
//-->
</script>
<!-- Body events -->
<!-- End page content -->