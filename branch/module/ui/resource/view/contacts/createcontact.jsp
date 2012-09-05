<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/listofvalues-taglib.tld" prefix="lov"%>
<%@ taglib uri="/tld/urlgenerator-taglib.tld" prefix="genurl"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>




<!-- <div class="breadcrumb"></div>
 -->
<div class="clear"></div>
<form:form id="contactCreateForm" action="/connect/create.do"
	modelAttribute="contact" method="post">


	<div class="student-info-block"
		style="width: 640px; margin: 0 0 0 0; border: none;overflow: auto;">

		<div class="nameDiv">
			<div class="student-info">
				<div class="left-div">
					<div class="student-general-info">Name</div>
					<div class="student-general-info-row mrt10">
						<div class="leftdiv">
							First Name
							<div class="requiredBlock">&nbsp;</div>
						</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="firstName"
								id="firstName" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="firstName"
									cssClass="error" /> </span>
						</div>


						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<div class="student-general-info-row">
						<div class="leftdiv">Last Name</div>
						<div class="rightdiv">
							<div class="bPageBlock"></div>
							<form:input cssClass="student-edit-textbox" id="lastName"
								path="lastName" />
						</div>
						<div class="clear"></div>
						<div class="clear"></div>
					</div>

				</div>

				<div class="right-div" style="margin-top: 29px;">
					<div class="student-general-info"></div>
					<div class="student-general-info-row">
						<div class="leftdiv">
							Full Name
							<div class="requiredBlock">&nbsp;</div>
						</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" id="fullName"
								readonly="true" path="fullName" />
						</div>
						<div class="clear"></div>
						<span class="error"> <form:errors path="fullName"
								cssClass="error" /> </span>
					</div>

				</div>

			</div>
		</div>
		<div class="clear"></div>

		<div id="companyDiv" style="margin-top: 20px;">
			<div class="student-info">
				<div class="left-div">
					<div class="student-general-info">Company</div>
					<div class="student-general-info-row">

						<div class="leftdiv">Name</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" id="cmpnyName"
								path="cmpnyName" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="cmpnyName"
									cssClass="error" /> </span>
						</div>

						<div class="clear"></div>
					</div>
					<div class="clear"></div>

					<div class="student-general-info-row">
						<div class="leftdiv">Title</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" id="cmpnyTitle"
								path="cmpnyTitle" />
						</div>
						<div class="clear"></div>
						<span class="error"> <form:errors path="cmpnyTitle"
								cssClass="error" /> </span>
						<div class="clear"></div>
					</div>


				</div>
				<div class="right-div" style="margin-top: 20px;">
					<div class="student-general-info"></div>
					<div class="student-general-info-row">
						<div class="leftdiv">Department</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" id="cmpnyDepartment"
								path="cmpnyDepartment" />
						</div>
						<div class="clear"></div>
						<span class="error"> <form:errors path="cmpnyDepartment"
								cssClass="error" /> </span>
						<div class="clear"></div>
					</div>

					<div class="clear"></div>
					<div class="student-general-info-row">
						<input type="checkbox" value="CheckBox" id="companyDataCheckBoxId"><span>Keep
							Text</span>
					</div>
				</div>



			</div>




		</div>
		<div class="clear"></div>


		<div id="emailDiv">
			<div class="student-info">
				<div class="left-div">
					<div class="student-general-info">Email</div>
					<div class="student-general-info-row">
						<div class="leftdiv">Work</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="workEmail"
								id="workEmail" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="workEmail" /> </span>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>

					<div class="student-general-info-row">
						<div class="leftdiv">Personal</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="homeEmail"
								id="homeEmail" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="homeEmail" /> </span>
						</div>
						<div class="clear"></div>
					</div>



				</div>
				<div class="right-div" style="margin-top: 20px;">
					<div class="student-general-info"></div>
					<div class="student-general-info-row">
						<div class="leftdiv">Other Email</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="otherEmail"
								id="otherEmail" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="otherEmail" /> </span>
						</div>
						<div class="clear"></div>
					</div>


				</div>

			</div>

		</div>
		<div class="clear"></div>
		<div id="phoneDiv">
			<div class="student-info">
				<div class="left-div">
					<div class="student-general-info">Phone</div>

					<div class="student-general-info-row">
						<div class="leftdiv">Work</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="workPhone"
								id="workPhone" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="workPhone"
									cssClass="error" /> </span>
						</div>
						<div class="clear"></div>

					</div>
					<div class="clear"></div>

					<div class="student-general-info-row">
						<div class="leftdiv">Mobile</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="mobileNumber"
								id="mobileNumber" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="mobileNumber"
									cssClass="error" /> </span>
						</div>
						<div class="clear"></div>

					</div>


				</div>
				<div class="right-div" style="margin-top: 20px;">
					<div class="student-general-info"></div>
					<div class="student-general-info-row">
						<div class="leftdiv">Home</div>

						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="homePhone"
								id="homePhone" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="homePhone"
									cssClass="error" /> </span>
						</div>
						<div class="clear"></div>

					</div>
				</div>


			</div>


		</div>

		<div class="clear"></div>
		<div id="addressDiv">
			<div class="student-info">
				<div class="left-div">
					<div class="student-general-info">Address</div>
					<div class="student-general-info-row">
						<div class="leftdiv">Work</div>
						<div class="rightdiv">
							<form:input cssClass="student-edit-textbox" path="workAddress"
								id="workAddress" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="workAddress"
									cssClass="error" /> </span>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<div class="student-general-info-row">
						<div class="leftdiv">Home</div>
						<div class="rightdiv">

							<form:input cssClass="student-edit-textbox" path="homeAddress"
								id="homeAddress" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="homeAddress"
									cssClass="error" /> </span>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>

				<div class="right-div" style="margin-top: 20px;">
					<div class="student-general-info"></div>
					<div class="student-general-info-row">
						<div class="leftdiv">Other</div>
						<div class="rightdiv">

							<form:input cssClass="student-edit-textbox" path="otherAddress"
								id="otherAddress" />
							<div class="clear"></div>
							<span class="error"> <form:errors path="otherAddress"
									cssClass="error" /> </span>
						</div>
						<div class="clear"></div>
					</div>

					<div class="clear"></div>
					<div class="student-general-info-row">
						<input type="checkbox" value="CheckBox" id="addressDataCheckBoxId"><span>Keep
							Text</span>
					</div>

				</div>

			</div>
		</div>

		<div class="clear"></div>
		<div id="notesDiv">
			<div class="student-info">
				<div class="left-div">
					<div class="student-general-info">Note</div>
					<form:textarea cssClass="student-edit-textbox" path="notes"
						id="notes" onkeyup="validateTextAreaMaxLength(this)"
						onkeydown="validateTextAreaMaxLength(this)"
						onblur="validateTextAreaMaxLength(this)"
						style="height:60px;width:590px" />
					<div class="clear"></div>
					<span class="error"> <form:errors path="notes"
							cssClass="error" /> </span>
				</div>
			</div>

		</div>
		<div class="clear"></div>
		<div class="buttonDiv" style="width:410px;margin-left:200px;">
			<div class="fr">

				<input type="checkbox" /> <span>Agreed to get news letter</span> <input
					type="button" value="Submit" name="Submit"
					onclick="javascript:createContact(false,this)" /> <input
					type="button" value="Add More" name="Add More"
					onclick="javascript:createContact(true,this)"> <input
					type="button" value="Cancel" onclick="javascript:closeForm()" />
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div>

	</div>





	</div>
	<div class="clear"></div>
</form:form>

<script type="text/javascript">
	$(document).ready(
			function() {
				$('#firstName').change(
						function() {
							$('#fullName').val(
									$('#firstName').val() + " "
											+ $('#lastName').val());
						});
				$('#lastName').change(
						function() {
							$('#fullName').val(
									$('#firstName').val() + " "
											+ $('#lastName').val());

						});
			});

	/* UNCOMMENT IF NEEDED 
	function updateContact(generatedUrl, elm) {
		singleClickDisabled(elm);
		$.ajax({
			url : generatedUrl,
			data : $('#contactCreateForm').serialize(),
			success : function(result) {
				if (result == 'success') {
					alert('Updated');
					$("#createFormDiv").empty().hide();
					$('#list4').trigger('reloadGrid');
				} else {
					$("#createFormDiv").html(result);
				}
			},
			error : function() {
				alert('error');
			}
		});

	} */

	function createContact(isSaveAndNew, elm) {
		singleClickDisabled(elm);
		$.ajax({
			url : '/connect/create.do?isSaveAndNew=' + isSaveAndNew,
			data : $('#contactCreateForm').serialize(),
			success : function(result) {
				if (result == 'success') {
					alert('Saved');
					if (!isSaveAndNew) {
						$("#createFormDiv").empty().hide();
						$('#list4').trigger('reloadGrid');
					} else {
						emptyFormData();
						$('#list4').trigger('reloadGrid');
					}

				} else {
					$("#createFormDiv").html(result);
				}
			},
			error : function() {
				alert('error');
			}
		});
	}

	function emptyFormData() {
		$("#firstName").val('');
		$("#lastName").val('');
		$("#fullName").val('');
		if (!$("#companyDataCheckBoxId").attr('checked')) {
			$("#contactCreateForm #cmpnyName").val('');
			$("#contactCreateForm #cmpnyTitle").val('');
			$("#contactCreateForm #cmpnyDepartment").val('');
		}

		$("#workEmail").val('');
		$("#homeEmail").val('');
		$("#otherEmail").val('');
		$("#workPhone").val('');
		$("#mobileNumber").val('');
		$("#homePhone").val('');
		if (!$("#addressDataCheckBoxId").attr('checked')) {

			$("#contactCreateForm  #workAddress").val('');

			$("#contactCreateForm #homeAddress").val('');
			$("#contactCreateForm #otherAddress").val('');
		}
		$("#notes").val('');
	}

	function closeForm() {
		$("#createFormDiv").empty().hide();
	}
</script>

