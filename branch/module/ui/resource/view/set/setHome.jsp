<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>

<input type="hidden" id="advSeachParam"
	value="<c:out
					value="${advSearchText}" />" />
<script type="text/javascript">
<!--
	$(function() {

		$('#setTab').addClass('currentTab');
		$('#setTab').addClass('primaryPalette');

	});
	$(function() {
		jQuery.jgrid.no_legacy_api = true;
		$("#list4").jqGrid(
				{
					datatype : "json",
					url : '/set/data.do',
					autowidth : true,
					pager : '#pnewapi',
					colNames : [ 'Id', 'Name', 'Order' ],
					colModel : [ {
						name : 'key',
						index : 'key',
						align : 'left',
						formatter : editLinkFormatter,
						search : false,
						sortable:false
					}, {
						name : 'setName',
						index : 'setName',
						width : 110,
						align : 'left',
						searchoptions : {
							sopt : [ 'eq', 'bw' ]
						}
					}, {
						name : 'setOrder',
						index : 'setOrder',
						width : 110,
						align : 'left',
						search : false,
						sortable:false
					}, ],
					caption : "Sets",
					viewrecords : true,
					beforeRequest : function() {
						var searchParam = $("#advSeachParam").val();
						if (searchParam != null) {
							jQuery("#list4").jqGrid(
									'setGridParam',
									{
										url : '/set/data.do?advSearchText='
												+ searchParam
									});
						}
					},
					gridComplete : function() {
						// alert(jQuery("#list4").jqGrid('getGridParam','records'));
						jQuery("#list4").jqGrid(
								'setGridHeight',
								'auto');
						if($('#advSeachParam').val()!=''){
				  			$('#advSeachParam').val('');
				  		}
						var myGrid = $("#list4");
				  		var cm = myGrid[0].p.colModel;
				  		$.each(myGrid[0].grid.headers, function(index, value) {
				  		    var cmi = cm[index];
				  		    if(!cmi.sortable) {
				  		        $('div.ui-jqgrid-sortable',value.el).css({cursor:"default"});
				  		    }
				  		   
				  		});
					}
				});

		jQuery("#list4").jqGrid('navGrid', '#pnewapi', {
			del : false,
			add : false,
			edit : false,
			search: false
		}, {}, {}, {}, {
			closeAfterSearch : true,
			multipleSearch : false,
			recreateFilter:true
		});
	});

	function editLinkFormatter(cellvalue, options, rowObject) {
		return '<a href="/set/showDetail.do?paramid=' + cellvalue + '">'
				+ cellvalue + '</a>';
	}
//-->
</script>

<!-- Start page content -->
<a name="skiplink"><img width="1" height="1"
	title="Content Starts Here" class="skiplink" alt="Content Starts Here"
	src="/images/s.gif"></a>
<div class="bPageTitle">
<div align="left" class="ptBreadcrumb"><brc:breadcrumb></brc:breadcrumb></div>
<!-- <div id="ptBody" class="ptBody secondaryPalette"><a
	href="/_ui/core/userprofile/UserProfilePage" linkindex="116"><img
	width="45" height="45" title="Amit C" class="thumbnail_visible"
	alt="Amit C" src="/images/T.png"> </a>
<div class="greeting">
<div class="content"
	style="top: -45px; padding-left: 50px; margin-bottom: -30px;"><span
	class="pageType">
<h1 class="currentStatusUserName"><a
	href="/_ui/core/userprofile/UserProfilePage" linkindex="117">Amit C</a></h1>
	<jsp:useBean id="now" class="java.util.Date" scope="page" />
<span class="statusContainer"><span class="currentStatus"><span
	title="this is yogesh's account" class="myCurrentStatus"></span><span style="display: none;"
	class="feeddata currentStatusUserId">005Q0000000SUiO</span> </span></span></span><span
	class="pageDescription"><a href="#" linkindex="118"><fmt:formatDate type="date" value="${now}" dateStyle="full"/></a></span></div>
</div>
</div> --></div>

<!-- Begin RelatedListElement -->
<div class="bRelatedList"><!-- Begin ListElement -->


<div class="bMyTask">
<div class="bPageBlock secondaryPalette">
<div class="pbHeader">
<table cellspacing="0" cellpadding="0" border="0">
	<tbody>
		<tr>
			<td class="pbTitle"><img width="1" height="1" title=""
				class="minWidth" alt="" src="/images/s.gif">
			<h3>Sets</h3>
			</td>
			<c:choose>
				<c:when test="${sec:hasWritePermission(appUser,'Set',dataContext)}">
					<td class="pbButton" width="33%" align="center"><input type="button" title="New Task"
						onClick="parent.location='/set/createForm.do'" name="newTask"
						class="btn" value=" New "></td>
				</c:when>
				<c:otherwise>
					<td class="pbButton" width="33%" align="center"><input type="button" title="New Task"
						onClick="parent.location='/set/createForm.do'" name="newTask"
						class="btn" value=" New " disabled="disabled"></td>
				</c:otherwise>
			</c:choose>
			<td class="pbHelp"><!-- Select Deleted --></td>
		</tr>
	</tbody>
</table>
</div>
<div class="pbBody"><%-- Period Grid here. --%>

<table id="list4" style="width: 100%;"></table>
<div id="pnewapi"></div>

<form id="assignmentForm" action="/set/setlist.do" method="post"
	enctype="multipart/form-data">
<table style="font-size: 13px">
	<tbody>
		<tr>
			<td><b>Import Sets from Existing XML</b></td>
		</tr>
		<tr>
			<td><input type="file" name="setCreationFile"></td>
			<td><input type="submit" value="Import Sets" title="submit"
				onclick="submitForm" id="sub"></td>
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
<div class="pbFooter secondaryPalette">
<div class="bg"></div>
</div>
</div>
</div>
<div class="listElementBottomNav"></div>
<!-- End ListElement --></div>
<!-- End RelatedListElement -->
<!-- Body events -->
