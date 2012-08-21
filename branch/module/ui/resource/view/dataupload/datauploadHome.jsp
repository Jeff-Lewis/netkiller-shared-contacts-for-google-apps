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

		$('#datauploadTab').addClass('currentTab');
		$('#datauploadTab').addClass('primaryPalette');

	});
/*
	$(function() {
		jQuery.jgrid.no_legacy_api = true;
		$("#list4").jqGrid(
				{
					datatype : "json",
					url : '/dataupload/data.do',
					autowidth : true,
					pager : '#pnewapi',
					colNames : [ 'Id', 'File', 'Upload Time', 'Upload Status' ],
					colModel : [ {
						name : 'key',
						index : 'key',
						align : 'center',
						formatter : editLinkFormatter,
						search : false
					}, {
						name : 'dataFile',
						index : 'dataFile',
						width : 110,
						align : 'center',
						searchoptions : {
							sopt : [ 'eq', 'bw' ]
						}
					}, {
						name : 'uploadTime',
						index : 'uploadTime',
						width : 110,
						align : 'center',
						search : false
					}, {
						name : 'uploadStatus',
						index : 'setStatus',
						width : 110,
						align : 'center',
						search : false
					}, ],
					caption : "DataUpload",
					viewrecords : true,
					beforeRequest : function() {
						var searchParam = $("#advSeachParam").val();
						if (searchParam != null) {
							jQuery("#list4").jqGrid(
									'setGridParam',
									{
										url : '/dataupload/data.do?advSearchText='
												+ searchParam
									});
						}
					},
					gridComplete : function() {
						// alert(jQuery("#list4").jqGrid('getGridParam','records'));
						jQuery("#list4").jqGrid(
								'setGridHeight',
								'auto');

					}
				});

		jQuery("#list4").jqGrid('navGrid', '#pnewapi', {
			del : false,
			add : false,
			edit : false
		}, {}, {}, {}, {
			closeAfterSearch : true,
			multipleSearch : true
		});
	});

	function editLinkFormatter(cellvalue, options, rowObject) {
		return '<a href="/dataupload/showDetail.do?paramid=' + cellvalue + '">'
				+ cellvalue + '</a>';
	}
	*/
-->
</script>

<!-- Start page content -->
<a name="skiplink"><img width="1" height="1"
	title="Content Starts Here" class="skiplink" alt="Content Starts Here"
	src="/images/s.gif"></a>
<div class="bPageTitle">
<div align="left" class="ptBreadcrumb"><brc:breadcrumb></brc:breadcrumb></div>
</div>

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
			<h3>DataUpload</h3>
			</td>			
			<td class="pbHelp"><!-- Select Deleted --></td>
		</tr>
	</tbody>
</table>
</div>
<div class="pbBody"><%-- Period Grid here. --%>

<table id="list4" style="width: 100%;"></table>
<div id="pnewapi"></div>

<form id="assignmentForm" action="/dataupload/filelist.do" method="post"
	enctype="multipart/form-data">
<table style="font-size: 13px">
	<tbody>
		<tr>
			<td><b>Import Data from Existing XLS</b></td>
		</tr>
		<tr>
			<td><input type="file" name="datauploadCreationFile"></td>
			<td><input type="submit" value="Import Data" title="submit"
				onclick="submitForm" id="sub"></td>
			<td id="error"
				style="color: red; font-size: 12px; vertical-align: middle"><c:if
				test="${FileError != null}">
	${FileError}
	</c:if></td>
	<td id="message"
				style="color: black; font-size: 12px; vertical-align: middle"><c:if
				test="${DataUploadMsg != null}">
	${DataUploadMsg}
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
