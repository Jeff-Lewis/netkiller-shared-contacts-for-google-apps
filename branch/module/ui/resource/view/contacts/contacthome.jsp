<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreService,com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
<input type="hidden" id="advSeachParam"
	value="<c:out value="${advSearchText}" />
" />
<!-- <script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script> -->
<script
	src="http://maps.google.com/maps?file=api&v=2.52&key=ABQIAAAAnAZyA34zDlQmFYfWlw6JqBS5ZecFzz0-rytgJXQ0WWFbp2mDUBSm0aq1TjDhjaENKiolezSGMeNzyQ"
	type="text/javascript"></script>

<style type="text/css">
<!--
.student-edit-textbox {
	border: 1px solid #EAEAEA;
	height: 20px;
	width: 185px;
}


.ui-widget input,.ui-widget select,.ui-widget textarea,.ui-widget button
	{
	width: 50px;
}

.ui-jqgrid .ui-pg-selbox {
	font-size: 14px;
}
-->
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//showMap();
		//load();
		//$("#createFormDiv").hide();
	});
</script>

<script type="text/javascript">
	/* var isCmpnyUpdated = false;
	var isAddressUpdated = false;
	var isNotesUpdated = false; */
	var gmarkers = [];
	var map;
	var minLa = 90;
	var minLo = 180;
	var maxLa = 0;
	var maxLo = 0;
	var meanLa = 0;
	var meanLo = 0;
	//var htmls = [];

	// handle clicks from the listing:
	function click(i) {
		gmarkers[i].openInfoWindowHtml(htmls[i]);
	}

	// set up a new marker
	function addMarker(lat, lon) {
		//alert(html);
		var marker = new GMarker(new GLatLng(lat, lon));
		GEvent.addListener(marker, "click", function() {
			//marker.openInfoWindowHtml(html);
		});
		gmarkers.push(marker);
		//htmls.push(html);
		return marker;
	}

	function formatHtml(blurb, address) {
		return '<div class="blurb">' + blurb + '</div>\n<div class="address">'
				+ address + '</div>';
	}

	function showAxis() {
		geocoder = new GClientGeocoder();
		var addressList = getSelectedAddressList();
		var add = [];
		add = addressList.split('$')
		if (geocoder) {
			for ( var i = 0; i < add.length; i++) {
				geocoder.getLatLng(add[i], function(point) {
					if (point) {
						//var html = formatHtml("", add[i]);
						marker = addMarker(point.Vd, point.Ha);
						map.addOverlay(marker);
					}
				});
			}
		}
	}

	//function load(meanLa,meanLo,minLa,maxLa,minLo,maxLo) {
	function load() {
		meanLa = (parseFloat(minLa) + parseFloat(maxLa)) / 2;
		meanLo = (parseFloat(minLo) + parseFloat(maxLo)) / 2
		if (GBrowserIsCompatible()) {
			map = new GMap2(document.getElementById("map_canvas"));
			map.addControl(new GLargeMapControl());
			map.addControl(new GMapTypeControl());
			map.setCenter(new GLatLng(meanLa, meanLo), 0);
			var bounds = new GLatLngBounds(new GLatLng(maxLa, minLo),
					new GLatLng(minLa, maxLo));
			showAxis();
			map.setZoom(map.getBoundsZoomLevel(bounds));
		}
	}

	function showMap() {
		geocoder = new GClientGeocoder();
		var addressList = getSelectedAddressList();
		var add = [];
		add = addressList.split('$')
		if (geocoder) {
			for ( var i = 0; i < add.length; i++) {
				geocoder.getLatLng(add[i], function(point) {
					if (point) {

						if (point.Vd < minLa) {
							minLa = point.Vd
						}
						if (point.Ha < minLo) {
							minLo = point.Ha
						}
						if (point.Ha > maxLo) {
							maxLo = point.Ha
						}
						if (point.Vd > maxLa) {
							maxLa = point.Vd
						}

					}
				});
			}
		}

		load();
	}
</script>
<script src="http://www.google-analytics.com/urchin.js"
	type="text/javascript"></script>

<script type="text/javascript">
	_uacct = "UA-250378-4";
	urchinTracker();
</script>

<script type="text/javascript">
	var grid;
	var prmSearch;
	$(function() {
		
		$('#ImportDialog').hide();
		$("#menu-contaner a").removeClass("selectmenu");
		$("#contacts").addClass("selectmenu");
		//showMap();
		$('#mapDiv').hide();
	});
	$(function() {
		
		$('#ImportDialog').dialog({
			autoOpen: false,
			width: 520,
		});	

		grid = $('#list4');
		jQuery.jgrid.no_legacy_api = true;
		pager:
				'#pnewapi',
				$("#list4")
						.jqGrid(
								{
									datatype : "json",
									url : '/contact/data.do',
									autowidth : true,
									jsonReader : {
										root : "rows"
									},
									pager : '#pnewapi',
									colNames : [ 'Id', 'FirstName', 'LastName',
											'Company', 'Email', 'Phone',
											'Address', 'Action' ],
									colModel : [ {
										name : 'key',
										index : 'key',
										align : 'left',
										search : false,
										sortable : true,
										hidden : false,
										viewable : true
									}, {
										name : 'firstName',
										formatter : editLinkFormatter,
										editrules : {
											edithidden : true,
											required : true
										},
										index : 'firstName',
										align : 'left',
										editable : false,
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										},
									}, {
										name : 'lastName',
										index : 'lastName',
										editable : true,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'cmpnyName',
										index : 'cmpnyName',
										editable : true,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'workEmail',
										index : 'workEmail',
										editable : true,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'workPhone',
										index : 'workPhone',
										editable : true,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'workAddress',
										index : 'workAddress',
										editable : true,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'act',
										index : 'act',
										width : 150,
										sortable : false,
										search : false,
									} ],

									mtype : 'POST',
									caption : "Contacts",
									viewrecords : true,
									multiselect : true,
									rownumbers : true,
									rowNum : 15,
									rowList : [ 5, 10, 15, 30, 50, 100 ],
									sortname : 'firstName',
									sortorder : 'asc',
									beforeRequest : function() {
										var searchParam = $("#advSeachParam")
												.val();
										if (searchParam != null) {
											jQuery("#list4")
													.jqGrid(
															'setGridParam',
															{
																url : '/contact/data.do?advSearchText='
																		+ searchParam
															});
										}
									},

									gridComplete : function() {
										var ids = jQuery("#list4").jqGrid(
												'getDataIDs');
										for ( var i = 0; i < ids.length; i++) {
											var cl = ids[i];
											be = "<input style='height:22px;width:50px;' type='button' value='Edit' onclick=\"jQuery('#list4').editRow('"
													+ cl + "');\" />";
											se = "<input style='height:22px;width:50px;' type='button' value='Save' onclick=\"jQuery('#list4').saveRow('"
													+ cl + "');\" />";
											jQuery("#list4").jqGrid(
													'setRowData', ids[i], {
														act : be + se
													});
										}

										// alert(jQuery("#list4").jqGrid('getGridParam','records'));
										jQuery("#list4").jqGrid(
												'setGridHeight', 'auto');
										if ($('#advSeachParam').val() != '') {
											$('#advSeachParam').val('');
										}
										var myGrid = $("#list4");
										var cm = myGrid[0].p.colModel;
										$
												.each(
														myGrid[0].grid.headers,
														function(index, value) {
															var cmi = cm[index];
															if (!cmi.sortable) {
																$(
																		'div.ui-jqgrid-sortable',
																		value.el)
																		.css(
																				{
																					cursor : "default"
																				});
															}

														});
									},
								/*  editurl: "/contact/update.do",
								 	jsonReader:{
								   	  root: "rows",
								   	  page: "page",
								   	  total: "total",
								   	  records: "records",
								   	  id: "id",
								   	  repeatitems: false
								   	}, */

								});

				prmSearch = {
					multipleSearch : false,
					overlay : false,
					recreateFilter : true,
					beforeShowSearch : function($form) {
						var events = $('.vdata', $form).data('events'), event;
						for (event in events) {
							if (events.hasOwnProperty(event)
									&& event === 'keydown') {
								return;
							}
						}

						$('.vdata', $form).keydown(
								function(e) {
									var key = e.charCode ? e.charCode
											: e.keyCode ? e.keyCode : 0;
									if (e.which == 13) {
										grid[0].SearchFilter.search();

									}

								});

					}
				},
				createSearchDialog = function() {

					grid.searchGrid(prmSearch);
					var searchDialog = $("#fbox_" + grid[0].id);
					searchDialog
							.addClass("ui-jqgrid ui-widget ui-widget-content ui-corner-all");
					searchDialog.css({
						position : "relative",
						"z-index" : "auto",
						float : "left"
					})
					var gbox = $("#gbox_" + grid[0].id);
					gbox.before(searchDialog);
					gbox.css({
						clear : "left"
					});
					$('#searchmodfbox_list4').hide();
					$('#gridFilter').before($('#fbox_list4'));

				};

		jQuery("#list4").jqGrid('navGrid', '#pnewapi', {
			search : false,
			del : false,
			add : false,
			edit : false
		}, {}, {}, {}, {
			closeAfterSearch : true,
			multipleSearch : false,
			closeAfterReset : true
		}, prmSearch);
		createSearchDialog();

	});

	function editLinkFormatter(cellvalue, options, rowObject) {
		return '<a href="/contact/showDetail.do?paramid=' + rowObject[0] + '">'
				+ cellvalue + '</a>';
	}

	function saveGridRowData(rowId) {
		var key = $('#list4').jqGrid('getCell', this, 'key');
		alert(key);
		$.ajax({
			url : '/contacts/saveGridRowData.do',
			data : {
				contactId : key,

			},
			success : function(result) {
				if (result == 'success') {
					alert('saved');
				}
			},
			error : function() {
				alert('error');
			}
		});
	}

	/* if($("#cmpnyCheckBoxId").attr('checked')){
	 alert('hi');	
	} */

	function contactsMassUpdate() {
		var str = "";
		var contactKeyList = getSelectedContactsIdList();
		if (contactKeyList == '') {
			alert('Please select contacts');
		} else {

			if ($("#cmpnyCheckBoxId").attr("checked")) {
				str = '&name=' + $("#cmpnyName").val() + '&dept='
						+ $("#cmpnyDepartment").val();
			}
			if ($("#addressCheckBoxId").attr("checked")) {
				str += '&workAddr=' + $("#workAddress").val() + '&otherAddr='
						+ $("#otherAddress").val();
			}
			if ($("#noteCheckBoxId").attr('checked')) {
				str += '&notes=' + $("#notes").val();
			}
			$.ajax({
				url : 'contact/contactMassUpdate.do?contactIdList='
						+ contactKeyList,
				data : str,
				success : function(result) {
					alert('updated All contacts');
					$("#massUpdateDiv").hide();
					$("#list4").trigger('reloadGrid');
					emptyForm();
				},
				error : function() {
					alert('error');

				}
			});
		}

	}

	function emptyForm() {
		$("#cmpnyCheckBoxId").attr("checked", false);
		$("#addressCheckBoxId").attr("checked", false);
		$("#noteCheckBoxId").attr('checked', false);
		$("#cmpnyName").val('');
		$("#cmpnyDepartment").val('');
		$("#workAddress").val('');
		$("#otherAddress").val('');
		$("#notes").val('');

	}

	function closeForm() {
		$("#massUpdateDiv").hide();
	}

	function performAction() {
		var optionValue = $("#selectBoxId option:selected").val();
		var contactIdList = getSelectedContactsIdList();
		if (optionValue == 'import') {
			//$('#uploadDiv').show();
			$('#ImportDialog').dialog('open');
			return false;
		} else {
			$('#uploadDiv').hide();
			document.getElementById("formId").action = "/contact/"
					+ optionValue + ".do?contactIdList=" + contactIdList;
			document.getElementById("formId").submit();
		}

	}

	function openCreateWindow() {
		$("#createFormDiv").load('/contact/createForm.do').show();
		//document.getElementById("")
		//window.location.href = '/contact/createForm.do';
	}

	function getSelectedContactsIdList() {
		var contactIdList = "";
		var contactKeyList = "";
		jQuery.each(jQuery("#list4").getGridParam('selarrrow'), function() {
			if (!isNaN(this))
				contactIdList += this + ",";
			var myData = $('#list4').jqGrid('getCell', this, 'key');
			contactKeyList += myData + ",";

		});
		contactKeyList = contactKeyList.substring(0, contactKeyList.length - 1);
		document.getElementById("contactIdList").value = contactKeyList;
		//alert(contactKeyList);
		return contactKeyList;
	}

	function massUpdate() {
		var contactKeyList = getSelectedContactsIdList();
		$.ajax({
			url : '/contact/getSelectedContactData.do',
			data : {
				contactIdList : contactKeyList,
			},
			success : function(data) {
				var modelMapList = eval(data);
				var tableCode = "<table style='width:710px'>";
				for ( var i = 0; i < modelMapList.length; i++) {
					var modelMap = modelMapList[i];
					tableCode += "<tr style='margin:10 10 0 0'><td>"
							+ modelMap['fullName'] + "</td><td>"
							+ modelMap['companyName'] + "</td><td>"
							+ modelMap['workEmail'] + "</td><td>"
							+ modelMap['Phone'] + "</td></tr>";
				}
				tableCode += "</table>";
				//alert(tableCode);
				//document.getElementById('iframeId').innerHTML = tableCode;
				$("#iframeId").html(tableCode);
				$("#massUpdateDiv").show();
			},
			error : function() {

			}

		});

	}

	function makeDuplicate() {
		var contactIdList = getSelectedContactsIdList();
		window.location.href = '/contact/duplicate.do?contactIdList='
				+ contactIdList;
	}

	function getSelectedAddressList() {
		var addressArray = "";
		jQuery.each(jQuery("#list4").jqGrid('getGridParam', 'selarrrow'),
				function() {
					var myCellData = jQuery("#list4").jqGrid('getCell', this,
							'workAddress');
					addressArray += myCellData + "$";
				});
		addressArray = addressArray.substring(0, addressArray.length - 1);
		return addressArray;
	}

	function getSelectedContactsData() {
		var contactKeyList = getSelectedContactsIdList();
		$.ajax({
			url : '/contact/getSelectedContactData.do',
			data : {
				contactIdList : contactKeyList,
			},
			success : function(data) {
				var modelMap = eval(data);
			},
			error : function() {
				alert('Error');
			}

		});
	}

	function submitForm() {
		document.getElementById("contactUploadForm").action = '/contact/import.do';
		document.getElementById("contactUploadForm").submit();
	}
	
	function sendMail(){
		window.location.href = '/contact/mailTo.do';
	}
</script>


<%-- <div class="breadcrumb">
	<brc:breadcrumb></brc:breadcrumb>
</div> --%>
<div class="clear"></div>
<div class="grid-block" style="background: none;margin: 0 165px;border-radius:0 0 0 0">

	<div class="grids-title">
		<span class="fl">Contacts</span> <span class="fr expent-icon"></span>
		<div class="clear"></div>
	</div>
	<div class="top_bar_2" id="search">

		<div id="gridFilter" class="search_bar2">
			<input id="fbox_list4_search" type="submit" name="submit"
				value="Search">
		</div>

	</div>
</div>
<div class="grid-result-block" style="margin: 0 165px;border-radius:0 0 0 0">
	<div class="grid-result-top">
		<div class="formDiv">
			<form action="" id="formId">
				<input type="hidden" id="contactIdList" name="contactIdList" />
				<table>
					<tr>
						<td><select id="selectBoxId">
								<option selected="selected" value="import">Import</option>
								<option value="export">Export</option>
								<option value="syncContacts">Sync</option>
								<option value="delete">Delete</option>
						</select></td>
						<td><input type="button" value="Action" name="Action"
							onclick="performAction()" /></td>
					</tr>


				</table>
			</form>
		</div>
		<div class="add-student">

			<a href="#" onClick="massUpdate()"><span class="add-student-icon"></span>Mass
				Update</a>

		</div>

		<div class="add-student" style="width: 100px;">

			<a href="#" onClick="makeDuplicate()"><span
				class="add-student-icon"></span>Duplicate</a>

		</div>
		<div class="add-student" style="width: 70px;">

			<a href="#" onClick="openCreateWindow();"><span
				class="add-student-icon"></span>New</a>

		</div>

		<div class="add-student" style="width: 70px;">

			<a href="#" onClick="showMap();$('#mapDiv').show();"><span
				class="add-student-icon"></span>Map</a>

		</div>

		<div class="add-student" style="width: 90px;">

			<a href="#" onClick="sendMail()"><span class="add-student-icon"></span>Mail
				To</a>

		</div>

		<div class="add-student" style="width: 100px;">

			<a href="#" onClick=""><span class="add-student-icon"></span>Connect</a>

		</div>


		<div class="clear"></div>
	</div>

	<div class="grid-result">
		<table id="list4" style="width: 100%;"></table>
	</div>

	<div id="pnewapi"></div>

	<div class="clear"></div>
	<div id="ImportDialog" title="Import contacts"
		style="font-family: Arial; font-size: 12px;display:none;background:white;">
		<p>
			Open <a href="https://docs.google.com/a/netkiller.com/spreadsheet/ccc?key=0ApQQqEHZz9C9dElmWTNFOHIzc3VDQk5XZE5vUDZiMUE" target="_blank"><b>this</b> </a>  doc and copy it into your google account: Go to File > Make A Copy, and then fill in the document with your contacts. Then, go to File > Download as a CSV file, and upload the file here. Upload time depends on the number of contacts, but normally takes between a few seconds and a few minutes.
			<br /> <br /> Please select an CSV file(.csv) to upload: <br />
		<form id="uploadForm" method="post"
			action="<%= blobstoreService.createUploadUrl("/contact/import.do") %>" enctype="multipart/form-data"
			target="resultFrm">
			<div style="font-size: 12px; width: 480px; text-align: middle;">
				<table width="100%" border="0">
					<tr>
						<td width="50%"><input id="file" type="file" name="file" />
						</td>
						<td align="center"><input id="SubmitFile" type="submit"
							name="Submit" value="Submit" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		
		<iframe id="resultFrm" name="resultFrm" width="0px" height="00px"></iframe>
	</div>
	<div id="uploadDiv" style="display: none;">
		<form id="contactUploadForm" action="/contact/import.do" method="post"
			enctype="multipart/form-data">
			<table style="font-size: 13px">
				<tbody>
					<tr>
						<td><b>Import Contacts from Existing CSV</b>
						</td>
					</tr>
					<tr>
						<td><input type="file" name="contactCreationFile">
						</td>
						<td><input type="submit" value="Import Contacts"
							title="submit" onclick="submitForm()" id="sub">
						</td>
						<td id="error"
							style="color: red; font-size: 12px; vertical-align: middle"><c:if
								test="${FileError != null}">
	${FileError}
	</c:if>
						</td>
					</tr>
				</tbody>
			</table>

		</form>
	</div>

	<div style="display: none; margin-left: 150px; margin-top: 30px;"
		id="mapDiv">
		<form action="#">
			<div id="map_canvas"></div>
		</form>
	</div>

	<div style="display: none; height: 570px; width: 740px;"
		id="massUpdateDiv">
		<div class="fr">
			<span class="student-save"
				onclick="contactsMassUpdate()">Update</span> <span
				class="student-save" onclick="javascript:closeForm()">Cancel</span>
			<div class="clear"></div>
		</div>

		<form:form id="contactForm" action="" modelAttribute="contact"
			method="post">
			<div class="student-info-block"
				style="margin-left: 0px; width: 735px; height: 570px;">
				<div class="nameDiv">
					<div class="student-info">
						<div class="left-div">
							<div class="student-general-info">Target Contacts</div>
							<div style="height: 75px; width: 710px; overflow-y: scroll;"
								id='iframeId'></div>
						</div>

					</div>
				</div>
				<div class="clear"></div>

				<div class="companyDiv" style="margin-top: 20px;">
					<div class="student-info">
						<div class="left-div">
							<div class="student-general-info">Company</div>

							<div class="student-general-info-row mrt10">
								<div>
									<input type="checkbox" name="updateGrpChckBox"
										id="cmpnyCheckBoxId" value="checkBox" /> <span>Update
										Group</span>
								</div>
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



							<div class="clear"></div>
						</div>


						<div class="right-div" style="margin-top: 20px;">
							<div class="student-general-info"></div>
							<div class="student-general-info-row" style="margin-top: 10px;">
								<div class="leftdiv">Division</div>

								<div class="rightdiv">
									<form:input cssClass="student-edit-textbox"
										id="cmpnyDepartment" path="cmpnyDepartment" />
								</div>
								<div class="clear"></div>
								<span class="error"> <form:errors path="cmpnyDepartment"
										cssClass="error" /> </span>
								<div class="clear"></div>
							</div>
						</div>


					</div>


				</div>
				<div class="clear"></div>

				<div class="addressDiv">
					<div class="student-info">
						<div class="left-div">
							<div class="student-general-info">Address</div>
							<div class="student-general-info-row mrt10">
								<div>
									<input type="checkbox" name="updateGrpChckBox"
										id="addressCheckBoxId" value="checkBox" /> <span>Update
										Group</span>
								</div>
								<div class="leftdiv">Work</div>
								<div class="rightdiv">
									<form:input cssClass="student-edit-textbox" id="workAddress"
										path="workAddress" />
									<div class="clear"></div>
									<span class="error"> <form:errors path="workAddress"
											cssClass="error" /> </span>
								</div>
								<div class="clear"></div>
							</div>

						</div>
						<div class="right-div" style="margin-top: 20px;">
							<div class="student-general-info"></div>
							<div class="student-general-info-row" style="margin-top: 10px;">
								<div class="leftdiv">Other</div>
								<div class="rightdiv">

									<form:input cssClass="student-edit-textbox" id="otherAddress"
										path="otherAddress" />
									<div class="clear"></div>
									<span class="error"> <form:errors path="otherAddress"
											cssClass="error" /> </span>
								</div>
								<div class="clear"></div>
							</div>



						</div>

					</div>

				</div>

				<div class="clear"></div>

				<div class="notesDiv">

					<div class="student-info">
						<div class="left-div">
							<div class="student-general-info">Notes</div>
							<div class="student-general-info-row">
								<div>
									<input type="checkbox" name="updateGrpChckBox"
										id="noteCheckBoxId" value="checkBox" /> <span>Update
										Group</span>
								</div>
								<div class="leftdiv"></div>
								<div class="rightdiv">

									<form:textarea cssClass="student-edit-textbox" path="notes"
										id="notes" onkeyup="validateTextAreaMaxLength(this)"
										onkeydown="validateTextAreaMaxLength(this)"
										onblur="validateTextAreaMaxLength(this)"
										style="height:55px;  margin-bottom: 10px;margin-left: 20px;width: 680px;" />
									<div class="clear"></div>
									<span class="error"> <form:errors path="notes"
											cssClass="error" /> </span>
								</div>
								<div class="clear"></div>
							</div>
						</div>


					</div>

					<div class="buttonDiv"></div>

				</div>

			</div>


		</form:form>




	</div>






	<div id="createFormDiv" style="display: none"></div>

</div>
<div class="clear"></div>


<style>
<!--
.formDiv {
	float: left;
	font-size: 13px;
	height: 24px;
	line-height: 24px;
	margin-right: 15px;
	width: 130px;
}

#createFormDiv {
	background: none repeat scroll 0 0 #FFFFFF;
	border: 3px outset;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	height: 900px;
	left: 50%;
	line-height: 21px;
	margin-left: -100px;
	margin-top: -150px;
	position: absolute;
	text-align: left;
	top: 50%;
	width: 711px;
	z-index: 1000;
}

#massUpdateDiv {
	position: absolute;
	border: 1px solid black;
	z-index: 1000;
	background: #ffffff;
	width: 835px;
	height: 915px;
	left: 50%;
	top: 50%;
	margin-left: -600px;
	margin-top: -150px;
	border: 3px outset;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	line-height: 21px;
	text-align: left;
}

#map_canvas {
	position: absolute;
	border: 1px solid black;
	z-index: 1000;
	background: #ffffff;
	width: 600px;
	height: 400px;
	left: 90%;
	top: 85%;
	margin-left: -600px;
	margin-top: -150px;
	border: 3px outset;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	line-height: 21px;
	/* text-align: left; */
}

.student-save{
background: -moz-linear-gradient(center top , #747474 0%, #4D4D4D 100%) repeat scroll 0 0 transparent;
    border: medium none;
    border-radius: 10px 10px 10px 10px;
    box-shadow: 0 0 3px #999999;
    color: #FFFFFF;
    cursor: pointer;
    filter: none;
    font-size: 13px;
    height: 24px;
    line-height: 26px;
    padding-bottom: 5px;
    width: 80px;

}
-->
</style>
