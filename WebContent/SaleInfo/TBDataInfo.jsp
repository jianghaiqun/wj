<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.OrderInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>日报信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function onCheck(ele,treeID,attName){
	ele = $(ele);
	var checked = ele.checked;
	var newPID = ele.getParent("P").$A("parentid");
	if(!checked){
		if($(attName+"_"+newPID)!=null){
			$(attName+"_"+newPID).checked = false;
		}
	}
	var p = ele.getParent("P");
	var level = p.$A("level");
	var arr = $(treeID).$T("P");
	var flag = true;
	for(var i=0;i<arr.length;i++){
		var c = arr[i];
		var cid = c.$A("cid");
		if(cid){
			if(cid!="-1"&&ele.value=="-1"){
				if(checked){
					$(attName+"_"+cid).disable();
				}else{
					$(attName+"_"+cid).enable();
				}
			}else{
				if(c!=p&&flag){
					continue;
				}
				if(c==p){
					flag = false;
					continue;
				}
				if(c.$A("level")>level){
					$(attName+"_"+cid).checked = checked;
				}else{
					break;
				}
			}
		}
	}
}

function startStaff() {
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","company",$NV("company"));
	DataGrid.setParam("dg1","insType",$NV("insType"));
	DataGrid.loadData("dg1");
}

function exportStaff() {
	DataGrid.toExcel("dg1",1);
}
</script>
</head>
<body>
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
		<td>
		<table width="20" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding: 6px;" class="blockTd">
					<z:tree id="tree1" style="height:440px;width:120px;" method="com.sinosoft.platform.TBDataCount.treeDataBind" 
					level="3" lazy="true" resizeable="true">
					<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
					<p cid="${ID}" level="${TreeLevel}">
						<input type="checkbox" name="company" id="company_${ID}" value="${ID}" onClick="onCheck(this,'tree1','company');">
						<label for="company_${ID}"><span id="span_${ID}">${ShopName}</span></label>
					</p>
					</z:tree>
				</td>
			</tr>
			<tr>
				<td style="padding: 6px;" class="blockTd">
					<z:tree id="tree2" style="height:440px;width:120px;" method="com.sinosoft.platform.TBDataCount.treeDataBind2" 
					level="3" lazy="true" resizeable="true">
					<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
					<p cid="${ID}" level="${TreeLevel}">
						<input type="checkbox" name="insType" id="insType_${ID}" value="${ID}" onClick="onCheck(this,'tree2','insType');">
						<label for="insType_${ID}"><span id="span_${ID}">${Name}</span></label>
					</p>
					</z:tree>
				</td>
			</tr>
		</table>
		</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:700px">
							统计时间：从
							<input value="${yesterday}" type="text" id="startDate" name="startDate" ztype="Date"  class="inputText" size="14" >
							到
							<input value="${yesterday}" type="text" id="endDate" name="endDate" ztype="Date"  class="inputText" size="14" >
						</span>
						<z:tbutton onClick="startStaff()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
						<z:datagrid id="dg1" method="com.sinosoft.platform.TBDataCount.dg1DataBind" lazy="true">
						<table cellpadding="2" cellspacing="0" style="width:1500px;  overflow: auto;"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo"><b>序号</b></td>
								<td width="25%">商品名称</td>
								<td width="8%">天猫宝贝ID</td>
								<td width="15%">所属店铺</td>
								<td width="5%">险种</td>
								<td width="6%">当日销量</td>
								<td width="7%">累计销量</td>
								<td width="11%">价格区间</td>
								<td width="10%">统计日期</td>
							</tr>
							<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
								<td align="center">&nbsp;</td>
								<td style="text-align:left;">${ItemName}</td>
								<td>${ItemID}</td>
								<td>${ShopName}</td>
								<td>${InsTypeName}</td>
								<td style="text-align:right;">${TSellCount}</td>
								<td style="text-align:right;">${SellCount}</td>
								<td style="text-align:right;">${Price}</td>
								<td>${ModifyDate}</td>
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