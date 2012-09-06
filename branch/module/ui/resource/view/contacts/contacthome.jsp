<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<%@page
	import="com.google.appengine.api.blobstore.BlobstoreService,com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
%>
<input type="hidden" id="advSeachParam"
	value="<c:out value="${advSearchText}" />
" />
<!-- <script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script> -->
<!-- <script
	src="http://maps.google.com/maps?file=api&v=2.52&key=ABQIAAAAnAZyA34zDlQmFYfWlw6JqBS5ZecFzz0-rytgJXQ0WWFbp2mDUBSm0aq1TjDhjaENKiolezSGMeNzyQ"
	type="text/javascript"></script> -->

<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>


<style type="text/css">
<!--
.student-edit-textbox {
	border: 1px solid #EAEAEA;
	height: 20px;
	width: 185px;
}

.button-input {
	background-color: grey;
	color: white;
	height: 22px;
	border: none;
	font-size: 11px;
	font-weight: 600;
	padding-top: 3px;
	padding-bottom: 20px;
	margin-top: 5px;
}
/* .selectopts{
	display: none;	
	} */
.search-button {
	background-color: green;
	color: white;
	height: 22px;
	border: none;
	font-size: 11px;
	font-weight: 600;
	padding-top: 3px;
	padding-bottom: 20px;
	margin-top: 5px;
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
	/* var isCmpnyUpdated = false;
	var isAddressUpdated = false;
	var isNotesUpdated = false; */
	/* var gmarkers = [];
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
	} */

	// set up a new marker
	/* 	function addMarker(lat, lon) {
	 //alert(html);
	 var marker = new GMarker(new GLatLng(lat, lon));
	 GEvent.addListener(marker, "click", function() {
	 //marker.openInfoWindowHtml(html);
	 });
	 gmarkers.push(marker);
	 //htmls.push(html);
	 return marker;
	 } */

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

	var myLatlng = new google.maps.LatLng(-25.363882, 131.044922);
	// Check for geolocation support

	var markers = [];

	function showAddressOnMap(lastElemIndex) {
		clearOverlays();
		markers = new Array();
		console.log(lastElemIndex)
		if (geocoder) {

			$(".cbox:checked")
					.each(
							function() {
								var elemIndex = parseInt($("#list4 input")
										.index($(this))) / 3 + 1;
								var address = $('#list4').getCell(elemIndex,
										'workAddress');
								var name = $('#list4').getCell(elemIndex,
										'firstName');

								if (lastElemIndex == elemIndex) {

									console
											.log("yellow marker for :"
													+ address)
									geocoder
											.geocode(
													{
														'address' : address
													},
													function(results, status) {
														if (status == google.maps.GeocoderStatus.OK) {
															var latlng = results[0].geometry.location;
															addYellowMarker(
																	latlng
																			.lat(),
																	latlng
																			.lng(),
																	name);
														}
													});
								} else {
									console.log("normal  marker for :"
											+ address)

									geocoder
											.geocode(
													{
														'address' : address
													},
													function(results, status) {
														if (status == google.maps.GeocoderStatus.OK) {
															var latlng = results[0].geometry.location;
															addMarkers(
																	latlng
																			.lat(),
																	latlng
																			.lng(),
																	name);
														}
													});
								}
							});

		}
	}

	function addMarkers(x, y, placeTitle) {
		var latlng = new google.maps.LatLng(x, y);
		var m1 = new google.maps.Marker({
			position : latlng,
			title : placeTitle,
			map : map
		});
		markers.push(m1);
		autoCenter(map, markers);
	}

	function addYellowMarker(x, y, placeTitle) {
		var latlng = new google.maps.LatLng(x, y);
		var m1 = new google.maps.Marker(
				{
					position : latlng,
					title : placeTitle,
					icon : new google.maps.MarkerImage(
							'http://www.gettyicons.com/free-icons/108/gis-gps/png/24/needle_left_yellow_2_24.png',
							new google.maps.Size(24, 24),
							new google.maps.Point(0, 0), new google.maps.Point(
									0, 24)),
					map : map
				});
		markers.push(m1);
		for ( var i = 0; i < markers.length - 1; i++) {
			console.log(markers[i]['icon'])
			delete markers[i]['icon'];
			console.log(markers[i]['icon'])
		}
		autoCenter(map, markers);
	}
	function clearOverlays() {
		if (markers) {
			for ( var i = 0; i < markers.length; i++) {
				markers[i].setMap(null);
			}
		}
	}

	function sendCSVToMail(){
		$.ajax({
		url:'/contact/triggercsvmail.do',
		success:function(result){
			alert("CSV will be mailed to " + result);
		},
		error:function(){
			alert("Error occured." );
		}
		});
	}
	
	function autoCenter(map, markers) {
		//  Create a new viewpoint bound
		var bounds = new google.maps.LatLngBounds();
		//  Go through each marker...
		for ( var marker in markers) {
			bounds.extend(markers[marker].getPosition());
		}
		//  Fit these bounds to the map

		map.fitBounds(bounds);
		map.setZoom(8);
	}
	var geocoder;
	var map;

	function initListCheckBox() {
		$(".cbox").click(
				function() {

					if ($(this).is(':checked')) {
						var elemIndex = parseInt($("#list4 input").index(
								$(this))) / 3 + 1;
						showAddressOnMap(elemIndex);
					}
				});
	}

	function showMap() {

		if ($("#canvas_map").is(':hidden')) {
			initListCheckBox();
			$("#showMapButton").html(
					"<span class='add-student-icon'></span>Hide");
			$('#list4').jqGrid('hideCol', 'workPhone').jqGrid('hideCol',
					'workAddress').jqGrid('hideCol', 'act');
			$("#gbox_list4").css('float', 'left')
			$(".grid-result").append($("#canvas_map"))
			$("#canvas_map").show();

			var myOptions = {
				zoom : 12,
				center : myLatlng,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
			map = new google.maps.Map(document.getElementById('canvas_map'),
					myOptions);
		} else {
			$("#showMapButton").html(
					"<span class='add-student-icon'></span>Map");
			$("body").append($("#canvas_map"));
			$("#canvas_map").hide();
			$('#list4').jqGrid('showCol', 'workPhone').jqGrid('showCol',
					'workAddress').jqGrid('showCol', 'act');
		}

		/*geocoder = new GClientGeocoder();
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

			load(); */
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

		geocoder = new google.maps.Geocoder();

		$('#ImportDialog').dialog({
			autoOpen : false,
			width : 520,
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
									colNames : [ 'Id', 'key', 'FirstName',
											'LastName', 'Company', 'Email',
											'Phone', 'Address', 'Action' ],
									colModel : [ {
										name : 'key',
										index : 'key',
										align : 'left',
										search : false,
										sortable : true,
										hidden : true,
										viewable : false
									}, {
										name : 'keyLong',
										index : 'keyLong',
										align : 'left',
										search : false,
										sortable : false,
										hidden : true,
										editable : true,
									}, {
										name : 'firstName',
										//formatter : editLinkFormatter,
										editrules : {
											edithidden : true,
											required : true
										},
										index : 'firstName',
										align : 'left',
										width: 130,
										editable : true,
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										},
									}, {
										name : 'lastName',
										index : 'lastName',
										editable : true,
										width: 130,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'cmpnyName',
										index : 'cmpnyName',
										editable : true,
										width: 130,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'workEmail',
										index : 'workEmail',
										editable : true,
										width: 100,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'workPhone',
										index : 'workPhone',
										editable : true,
										width: 90,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'workAddress',
										index : 'workAddress',
										editable : true,
										width: 200,
										align : 'left',
										searchoptions : {
											sopt : [ 'bw', 'eq' ]
										}
									}, {
										name : 'act',
										index : 'act',
										width : 130,
										sortable : false,
										search : false,
									}

									],

									mtype : 'POST',
									caption : "Contacts",
									viewrecords : true,
									multiselect : true,
									rownumbers : true,
									rowNum : 15,
									rowList : [ 5, 10, 15, 30, 50, 100 ],
									sortname : 'firstName',
									sortorder : 'asc',
									onCellSelect: function(rowid,
											iCol,
											cellcontent){
										var  row= jQuery("#list4").jqGrid('getRowData',rowid);
												//alert('hi' + cellcontent )
											     if(cellcontent== row.workAddress){
											    	/*  $('#map_canvas').dialog('open');
											    	 showAddress(cellcontent);
							 */
							 						 cellcontent=cellcontent.replace(/ /g,"+"); 
											    	 window.open("http://maps.google.com?q="+cellcontent,"_blank");
											    }         
											     else	{
											    	 var paramId = $('#list4').jqGrid('getCell', rowid, 'key');
											 
											    		document.location.href = "/contact/showDetail.do?paramid="+paramId;
											     }
									
									},
									beforeRequest : function() {
										$('.cbox').unbind('click');

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
										if ($("#canvas_map").is(':visible')) {

											initListCheckBox();

										}
									},
									editurl : "/contact/gridUpdate.do",
								/* 	jsonReader:{
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

	function connectContacts() {
		var selectedContacts = "";
		var email =  $("#connectEmail").val();
		if(!email){
			alert("Email is required");
			return false;
		}
		var name =  $("#connectName").val().trim()?$("#connectName").val():email;

		$(".cbox:checked").each(function() {
			var elemIndex = parseInt($("#list4 input").index($(this))) / 3 + 1;
			selectedContacts += $('#list4').getCell(elemIndex, 'key') + ",";
		});
		if (selectedContacts && email) {
			selectedContacts = selectedContacts.substring(0,
					selectedContacts.length - 1);
			$('#connectPopUp').hide();
			$.ajax({
				url : "/contacts/connect.do?",
				data:{'contacts':selectedContacts,'toName':name,'toEmail':email},
				type:'POST',
				success : function() {
					alert("Connect process triggered");
				},
				error : function() {
					alert("Connect ProcessFailed");
				}
			});
		} else {
			alert("Please choose contacts to connect")
		}

		//	alert(selectedContacts)
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
		if (contactKeyList == '') {
			alert('Selcet contact to update');
		} else {
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

	function sendMail() {
		window.location.href = '/contact/mailTo.do';
	}
</script>


<%-- <div class="breadcrumb">
	<brc:breadcrumb></brc:breadcrumb>
</div> --%>
<div class="clear"></div>
<div class="grid-block"
	style="background: none; margin: 0 0px; border-radius: 0 0 0 0">

	<div class="grids-title">
		<span class="fl">Contacts</span>
		<div class="clear"></div>
	</div>

</div>
<div class="grid-result-block"
	style="margin: 0 0px; border-radius: 0 0 0 0">
	<div class="grid-result-top">
		<div class="top_bar_2" id="search">

			<div id="gridFilter" class="search_bar2">
				<input id="fbox_list4_search" type="submit" name="submit"
					class="search-button" value="Search" ">
			</div>

		</div>

		<div class="clear"></div>
		<div class="formDiv" style="margin-left: 660px">
			<form action="" id="formId">
				<input type="hidden" id="contactIdList" name="contactIdList" />
				<table>
					<tr>
						<td><select id="selectBoxId">
								<option selected="selected" value="import">Import</option>
								<option value="export">Export</option>
								<option value="syncContacts">Sync</option>
								<option value="delete">Delete</option>
						</select>
						</td>
						<td><input type="button" value="Action" name="Action"
							class="button-input" onclick="performAction()" />
						</td>
					</tr>


				</table>
			</form>
		</div>
		<div style="float: right">
			<input type="button" id="massUpdateButton" class="button-input"
				value="Mass Update" name="Mass Update" onclick="massUpdate()"
				style="margin-left: 0px;"></input> <input type="button"
				id="duplicateButton" class="button-input" value="Duplicate"
				name="Duplicate" onclick="makeDuplicate()" style="margin-left: 0px;"></input>
			<input type="button" id="newButton" class="button-input" value="New"
				name="New" onclick="openCreateWindow()" style="margin-left: 0px;"></input>
			<input type="button" id="mapButton" class="button-input" value="Map"
				name="Map" onclick="showMap()" style="margin-left: 0px;"></input> <input
				type="button" id="mailToButton" class="button-input" value="Mail To"
				name="Mail To" onclick="sendCSVToMail()" style="margin-left: 0px;"></input>
			<input type="button" id="connectButton" class="button-input"
				value="Connect" name="Connect"  onclick="$('#connectPopUp').show();"
				style="margin-left: 0px;"></input>
		</div>
		<!-- <div class="add-student" style="width: 100px;">

			<a href="#" onClick="makeDuplicate()"><span
				class="add-student-icon"></span>Duplicate</a>

		</div> -->

		<!-- <img src="/images/new.jpg" onclick = "openCreateWindow()"  /> -->


		<!-- <div class="add-student" style="width: 70px;">

			<a href="#" onClick="showMap();" id="showMapButton"><span
				class="add-student-icon"></span>Map</a>

		</div> -->

		<!-- <div class="add-student" style="width: 90px;">

			<a href="#" onClick="sendMail()"><span class="add-student-icon"></span>Mail
				To</a>

		</div> -->

		<!-- <div class="add-student" style="width: 100px;">

			<a href="#" onClick="connectContacts()"><span class="add-student-icon"></span>Connect</a>

		</div> -->


		<div class="clear"></div>
	</div>

	<div class="grid-result">
		<table id="list4" style="width: 100%;"></table>
	</div>

	<div id="pnewapi"></div>

	<div class="clear"></div>
	<div id="ImportDialog" title="Import contacts"
		style="font-family: Arial; font-size: 12px; display: none; background: white;">
		<p>
			Open <a
				href="https://docs.google.com/a/netkiller.com/spreadsheet/ccc?key=0ApQQqEHZz9C9dElmWTNFOHIzc3VDQk5XZE5vUDZiMUE"
				target="_blank"><b>this</b> </a> doc and copy it into your google
			account: Go to File > Make A Copy, and then fill in the document with
			your contacts. Then, go to File > Download as a CSV file, and upload
			the file here. Upload time depends on the number of contacts, but
			normally takes between a few seconds and a few minutes. <br /> <br />
			Please select an CSV file(.csv) to upload: <br />
		<form id="uploadForm" method="post"
			action="<%= blobstoreService.createUploadUrl("/contact/import.do") %>"
			enctype="multipart/form-data" target="resultFrm">
			<div style="font-size: 12px; width: 480px; text-align: middle;">
				<table width="100%" border="0">
					<tr>
						<td width="50%"><input id="file" type="file" name="file" />
						</td>
						<td align="center"><input id="SubmitFile" type="submit"
							name="Submit" value="Submit" /></td>
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
						<td><b>Import Contacts from Existing CSV</b></td>
					</tr>
					<tr>
						<td><input type="file" name="contactCreationFile"></td>
						<td><input type="submit" value="Import Contacts"
							title="submit" onclick="submitForm()" id="sub"></td>
						<td id="error"
							style="color: red; font-size: 12px; vertical-align: middle"><c:if
								test="${FileError != null}">
	${FileError}
	</c:if></td>
					</tr>
				</tbody>
			</table>

		</form>
	</div>

	<%-- 	<div style="display: none; margin-left: 150px; margin-top: 30px;"
		id="mapDiv">
		<form action="#">
			<div id="map_canvas"></div>
		</form>
	</div> --%>

	<div style="display: none; height: auto; width: 640px;"
		id="massUpdateDiv">
		<div id="buttonDiv" style="float: right;">
			<input type="button" name="Update" value="Update"
				onclick="contactsMassUpdate()" /> <input type="button"
				name="Cancel" value="Cancel" onclick="closeForm()" />
		</div>
		<!-- <div class="fr">
			<span class="student-save"
				onclick="contactsMassUpdate()">Update</span> <span
				class="student-save" onclick="javascript:closeForm()">Cancel</span>
			<div class="clear"></div>
		</div> -->

		<form:form id="contactForm" action="" modelAttribute="contact"
			method="post">
			<div class="student-info-block"
				style="margin-left: 0px; width: 640px; height: auto;">
				<div class="nameDiv">
					<div class="student-info">
						<div class="left-div">
							<div class="student-general-info">Target Contacts</div>
							<div style="height: 75px; width: 620px; overflow-y: scroll;"
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
										id="cmpnyCheckBoxId" value="checkBox"
										style="margin-bottom: 10px" /> <span>Update Group</span>
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


						<div class="right-div" style="margin-top: 40px;">
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
										id="addressCheckBoxId" value="checkBox"
										style="margin-bottom: 10px" /> <span>Update Group</span>
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
						<div class="right-div" style="margin-top: 40px;">
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
										style="height:55px;  margin-bottom: 10px;margin-left: 20px;width: 585px;" />
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
<div id="canvas_map"
	style="display: none; width: 400px; margin: 0 auto; margin-left: 5px; height: 469px; float: left;"></div>

<div style="display: none;clear:both;width:250px;border:3px gray inset;position:absolute;left:50%;top:35%;margin-left:-125px;background:white;" id="connectPopUp">
<div style="border-bottom:2px solid black;overflow:hidden;">
<div style="float:left;">Connect Contacts</div><div style="float:right;padding:0 2px;border:1px solid black;cursor:pointer;" onclick="$('#connectPopUp').hide();">x</div>
</div>
<div style="clear:both;">
<br>
Name : <input type='text' id="connectName" /><br><br>

Email : <input type='text'  id="connectEmail" /><br>
</div>
<br>
<center>
<input type="button" value="Connect" onclick="connectContacts()" />
</center>
<br>
</div>

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
	height: auto;
	left: 50%;
	line-height: 21px;
	margin-left: -100px;
	margin-top: -150px;
	position: absolute;
	text-align: left;
	top: 50%;
	width: 620px;
	z-index: 1000;
}

#massUpdateDiv {
	position: absolute;
	border: 1px solid black;
	z-index: 1000;
	background: #ffffff;
	width: 620px;
	height: auto;
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
	background: #ffffff;
	width: 100%;
	height: 100%;
	position: static;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;

	/* text-align: left; */
}

.student-save {
	background: -moz-linear-gradient(center top, #747474 0%, #4D4D4D 100%)
		repeat scroll 0 0 transparent;
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

 