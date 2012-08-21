function getNextEditableColNum(editableColArray,currentColNum){
	for(var i=0;i<editableColArray.length;i++){
		if(editableColArray[i]==currentColNum && eval(i+1)!=editableColArray.length){
			return editableColArray[++i];
		}	
	}
	return -1;
}

function getPrevEditableColNum(editableColArray,currentColNum){
	for(var i=0;i<editableColArray.length;i++){
		if(editableColArray[i]==currentColNum && eval(i-1)>=0){
			return editableColArray[--i];
		}	
	}
	return -1;
}

function getFirstNonHiddenEditableColNum(grid, nonHiddenEditableColNameArray){
	var columnModel = grid.jqGrid('getGridParam','colModel');	
	var firstNonHiddenEditableColName = nonHiddenEditableColNameArray[0];
	for(var j=0;j<columnModel.length;j++) {
		if(columnModel[j].name == firstNonHiddenEditableColName){
			return j;
		}
	}
	return -1;
}

function getLastNonHiddenEditableColNum(grid,nonHiddenEditableColNameArray){
	var columnModel = grid.jqGrid('getGridParam','colModel');	
	var lastNonHiddenEditableColName = nonHiddenEditableColNameArray[nonHiddenEditableColNameArray.length-1];
	for(var j=0;j<columnModel.length;j++) {
		if(columnModel[j].name == lastNonHiddenEditableColName){
			return j;
		}
	}
	return -1;
}

function getNonHiddenColNumArray(grid){
	var columnModel = grid.jqGrid('getGridParam','colModel');	
	var nonHiddenColNumArray = new Array();
	for(var i=0;i<columnModel.length;i++){
		if(!columnModel[i].hidden){
			nonHiddenColNumArray.push(i);
		}
	}
	return nonHiddenColNumArray;
}

function getNextNonHiddenEditableColName(grid,currentColName){
	var columnModel = grid.jqGrid('getGridParam','colModel');	
	var flag = false;
	for(var i=0;i<columnModel.length;i++){
		if(columnModel[i].editable && !columnModel[i].hidden){
			if(flag) {
				return columnModel[i].name;
			}
			if(columnModel[i].name == currentColName){
				flag = true;
			}
		}
		
	}
}

function  disableEditPropertyOfGridColumn(grid,gridEditableColNames){
	for(var i=0;i<gridEditableColNames.length;i++){
		grid.jqGrid('setColProp', gridEditableColNames[i], {editable:false});
	}
}

function enableEditPropertyOfGridColumn(grid,gridEditableColNames){
	for(var i=0;i<gridEditableColNames.length;i++){
		grid.jqGrid('setColProp', gridEditableColNames[i], {editable:true});
	}
}

function getFirstEditableNonHiddenColName(grid){
	var columnModel = grid.jqGrid('getGridParam','colModel');	
	for(var j=0;j<columnModel.length;j++) {
		if(columnModel[j].editable && !columnModel[j].hidden){
			return columnModel[j].name;
		}
	}
}

function getLastEditableNonHiddenColName(grid){
	var columnModel = grid.jqGrid('getGridParam','colModel');	
	var lastColName ;
	for(var j=0;j<columnModel.length;j++) {
		if(columnModel[j].editable && !columnModel[j].hidden){
			lastColName = columnModel[j].name;
		}
	}
	return lastColName;
}

function getNonHiddenEditableGridColumnNames(grid){
	var columnModel = grid.jqGrid('getGridParam','colModel');	
	var nonHiddenEditableElementArrray = new Array();
	for(var j=0;j<columnModel.length;j++) {
		if(columnModel[j].editable && !columnModel[j].hidden){
			nonHiddenEditableElementArrray.push(columnModel[j].name);
		}
	}
	return nonHiddenEditableElementArrray;
}

function getEditableColumnNameArray(grid) {
	var columnModel = grid.jqGrid('getGridParam','colModel');
	var colNameArray=new Array(); 	
	for(var j=0;j<columnModel.length;j++) {
		if(columnModel[j].editable ){				
			colNameArray.push(columnModel[j].name);
		}
	}
	return colNameArray;
}

function getRowNumWhichIsInEditMode(grid){
	var numberOfRecords = grid.getGridParam("records");	
	for(var i=0;i<numberOfRecords;i++){
		var id = i+1;
		if($(grid.jqGrid("getInd",id,true)).attr("editable") === "1"){
			return id;
		}
	}
	return -1;
}

function getInEditModeRowNumberArray(grid){
	var numberOfRecords = grid.getGridParam("records");	
	var rowNumArray = new Array();
	for(var i=0;i<numberOfRecords;i++){
		var id = i+1;
		if($(grid.jqGrid("getInd",id,true)).attr("editable") === "1"){
			rowNumArray.push(id);
		}
	}
	return rowNumArray;
}

function getNextEditableNonHiddenColNum(grid,iCol){
	var columnModel = grid.jqGrid('getGridParam','colModel');
	var nextEditableNonHiddenColNum = -1;
	for(var j=0;j<columnModel.length;j++) {
		if(columnModel[j].editable && !columnModel[j].hidden){
			if(j>iCol){
				nextEditableNonHiddenColNum = j;
				break;
			}
		}
	}
	
	return nextEditableNonHiddenColNum;
}