<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.KXBTotalInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>各险种销售情况</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statStaff(cancelFlag){
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","allDate","0");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","cancelFlag",cancelFlag);
	DataGrid.setParam("dg1","contant",$NV("contant"));
	DataGrid.setParam("dg1","sdChannel",$V("sdChannel"));
	DataGrid.loadData("dg1");
}
function statAll(){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1","allDate","1");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
function onCheck(ele){
	ele = $(ele);
	var checked = ele.checked;
	var newPID = ele.getParent("P").$A("parentid");
	if(!checked){
		if($("contant_"+newPID)!=null){
			$("contant_"+newPID).checked = false;
		}
	}
	if(ele.value=="-1"){
	
	}
	var p = ele.getParent("P");
	var level = p.$A("level");
	var arr = $("tree1").$T("P");
	var flag = true;
	for(var i=0;i<arr.length;i++){
		var c = arr[i];
		var cid = c.$A("cid");
		if(cid){
			if(cid!="-1"&&ele.value=="-1"){
				if(checked){
					$("contant_"+cid).disable();
				}else{
					$("contant_"+cid).enable();
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
					$("contant_"+cid).checked = checked;
				}else{
					break;
				}
			}
		}
	}
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
				<td style="padding: 6px;" class="blockTd"><z:tree id="tree1"
					style="height:440px;width:160px;"
					method="com.sinosoft.platform.KXBChannel.treeDataBind"
					level="3" lazy="true" resizeable="true">
					<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
					<p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
						name="contant" id='contant_${ID}' value='${ChannelCode}'
						onClick="onCheck(this);"><label for="contant_${ID}"><span id="span_${ID}">${Name}</span></label></p>
				</z:tree></td>
			</tr>
		</table>
		</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:500px">
						统计时间：从
						<input value="${yesterday}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						SD渠道：
						<z:select id="sdChannel">${sdChannel}</z:select>
						<%-- 统计范围：
						<z:select id="contant" name="contant" style="width:90px" >${contant}</z:select> --%>
						</span>
						<z:tbutton onClick="statStaff(0)"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<z:tbutton onClick="statStaff(1)"> <img src="../Icons/icon031a1.gif" />撤单统计</z:tbutton>
						<z:tbutton onClick="statStaff(2)"> <img src="../Icons/icon031a1.gif" />对冲统计</z:tbutton>
						<%-- <z:tbutton onClick="statAll()"> <img src="../Icons/icon031a15.gif" />全部统计</z:tbutton> --%>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.KXBTotalInfo.dg1DataBind" lazy="true">
						<table width="50%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><b>序号</b></td>
								<td width="20%">险种</td>
								<td width="15%">订单数</td>
								<td width="20%" ><b>保费收入</b></td>
								<td width="20%" ><b>手续费收入</b></td>
								<td width="20%" ><b>单均收入</b></td>	
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td>${productGenera}</td>
								<td style="text-align:right;">${count}</td>
								<td style="text-align:right;">${totalAmount}</td>
								<td style="text-align:right;">${charge}</td>
								<td style="text-align:right;">${avg}</td>
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