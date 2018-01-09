<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.GlobalCount.initStaff">
<html> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>单一产品总保费及件数统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
function count(){ 
	jQuery("#countflag").val("count");
	DataGrid.setParam("dg1","year",$V("year"));
	DataGrid.setParam("dg1","countflag",$V("countflag"));
	DataGrid.setParam("dg1","contant",$V("contant")); 
	DataGrid.loadData("dg1");
	var timename=setInterval(
			function loadtitle() {
				if(DataGrid.isLoading==false){
					var year=$V("year");
					var array=jQuery("td[name='yearmonth']")
					for(var i=0;i<array.length;i++){
						array[i].innerHTML=year+"-"+(i+1);
					}
					jQuery("#project").html("产品(件数)");
					clearInterval(timename);
				}
			}
			,100);
	//var t=setTimeout("load()",600);
		
}
DataGrid._onContextMenu = function(tr,evt){}
function countAmnt(){
	jQuery("#countflag").val("amnt");
	DataGrid.setParam("dg1","year",$V("year"));
	DataGrid.setParam("dg1","countflag",$V("countflag"));
	DataGrid.setParam("dg1","contant",$V("contant"));
	DataGrid.loadData("dg1");
	var timename=setInterval(
			function loadtitle() {
				if(DataGrid.isLoading==false){
					var year=$V("year");
					var array=jQuery("td[name='yearmonth']")
					for(var i=0;i<array.length;i++){
						array[i].innerHTML=year+"-"+(i+1);
					}
					jQuery("#project").html("产品(保费)");
					clearInterval(timename);
				}
			}
			,100);
}
function exportExel(){
	var years=$V("year");
	var titles=[jQuery("#project").html(),years+'-1',years+'-2',years+'-3',years+'-4',years+'-5',years+'-6',years+'-7',years+'-8',years+'-9',years+'-10',years+'-11',years+'-12','总计'];
	DataGrid.globalToExcel("dg1",1,titles);
}
DataGrid.globalToExcel = function(ele,toExcelPageFlag,titles){
	ele = $(ele);
	var diag = new Dialog("选择要导出的列","Framework/Controls/DataGridToExcelDialog.html",500,150);
	diag.OKEvent = function(){
		var columns = $DW.$N("Column");
		var columnIndexes = [],columnWidths = [];
		for(var i=0;i<columns.length;i++){
			if(columns[i].checked){
				columnIndexes.push(columns[i].value);
				columnWidths.push($(columns[i]).$A("columnWidth"));
			}
		}
		$D.close();
		DataGrid.globalToExcelSubmit(ele,toExcelPageFlag,columnIndexes,columnWidths,titles);
	}
	diag.addParam("DataGridID",ele.id);
	diag.show();
}
DataGrid.globalToExcelSubmit = function(ele,toExcelPageFlag,columnIndexes,columnWidths,titles){
	ele = $(ele);
	var xls = "_Excel_";
	var doc = window.document.body;
	var f = $(xls+"_Form");
	if(f){
		f.outerHTML = "";//要清空上次导出设置
	}
	f  = document.createElement("form");
	doc.appendChild(f);
	f.id = xls+"_Form";
	f.method="post";
	f.action = Server.ContextPath+"Framework/Controls/GlobalDataGridToExcel.jsp";
	var inputs = ele.Params.keys;
	for(var i = 0;i<inputs.length;i++){
		var input = $(xls+inputs[i]);
		if(!input){
			input  = document.createElement("input");
			
		}
		input.type="hidden";
		input.id =xls+inputs[i];
		input.name = xls+inputs[i];
		input.value=DataGrid.getParam(ele,inputs[i]);
		f.appendChild(input);
	}
	
	var input = $(xls+"_ZVING_ToExcelPageFlag");
	if(!input){
		input  = document.createElement("input");
		
	}
	input.type = "hidden";
	input.id =xls+"_ZVING_ToExcelPageFlag";
	input.name = xls+"_ZVING_ToExcelPageFlag";
	input.value= toExcelPageFlag? "1":"0";
	f.appendChild(input);

	input = $(xls+"_ZVING_Widths");
	if(!input){
		input  = document.createElement("input");
	}
	input.type="hidden";
	input.id =xls+"_ZVING_Widths";
	input.name = xls+"_ZVING_Widths";
	input.value = columnWidths.join();
	f.appendChild(input);
	
	input = $(xls+"_ZVING_Indexes");
	if(!input){
		input  = document.createElement("input");
	}
	input.type="hidden";
	input.id =xls+"_ZVING_Indexes";
	input.name = xls+"_ZVING_Indexes";
	input.value = columnIndexes.join();
	f.appendChild(input);

	input  = document.createElement("input");
	input.type="hidden";
	input.id =xls+"_ZVING_Titles";
	input.name = xls+"_ZVING_Titles";
	input.value = titles;
	f.appendChild(input);
	f.submit();
}
</script>
<script type="text/javascript" for="window" event="onload">
if(document.readyState=="complete"){  
    load();  
} 
function load() {
	jQuery("#project").html("产品(件数)");
	jQuery("#countflag").val("amnt");
	var year=$V("year");
	var array=jQuery("td[name='yearmonth']")
	for(var i=0;i<array.length;i++){
		array[i].innerHTML="<b>"+year+"-"+(i+1)+"</b>";
	}
}
</script>
</head>
<body onload="load()">
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<input type="hidden" value="" id="countflag" name="countflag">
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:950px">
						统计年份：
						<z:select id="year" name="year" style="width:90px" >${year}</z:select>
						统计范围：
						<z:select id="contant" name="contant" style="width:90px" >${contant}</z:select>
						</span>
						<z:tbutton onClick="count()"> <img src="../Icons/icon031a1.gif"/>总件数统计</z:tbutton>
						<z:tbutton onClick="countAmnt()"> <img src="../Icons/icon031a1.gif"/>总保费统计</z:tbutton>
						<z:tbutton onClick="exportExel()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.SingleProductMoney.dg7DataBind" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo"><b>序号</b></td>
								<td width="15%" id="project"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="6%" name="yearmonth"></td>
								<td width="15%" ><b>总计</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td style="text-align:left;">${project}</td>
								<td style="text-align:right;">${one}</td>
								<td style="text-align:right;">${two}</td>
								<td style="text-align:right;">${three}</td>
								<td style="text-align:right;">${four}</td>
								<td style="text-align:right;">${five}</td>
								<td style="text-align:right;">${six}</td>
								<td style="text-align:right;">${seven}</td>
								<td style="text-align:right;">${eight}</td>
								<td style="text-align:right;">${night}</td>
								<td style="text-align:right;">${ten}</td>
								<td style="text-align:right;">${eleven}</td>
								<td style="text-align:right;">${twele}</td>
								<td style="text-align:right;">${total}</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</body>
</html>
</z:init>