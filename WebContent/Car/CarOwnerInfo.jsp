<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>车主信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function sear() {
		DataGrid.setParam("dg_car", Constant.PageIndex, 0);
		DataGrid.setParam("dg_car", "prop1", $V("prop1"));
		DataGrid.setParam("dg_car", "CarOwner", $V("CarOwner"));
		DataGrid.setParam("dg_car", "ContactPhone", $V("ContactPhone"));
		DataGrid.setParam("dg_car", "ContactEmail", $V("ContactEmail"));
		DataGrid.setParam("dg_car", "prop2", $V("prop2"));
		DataGrid.setParam("dg_car", "Address", $V("Address"));
		DataGrid.setParam("dg_car", "startdate", $V("startdate"));
		DataGrid.setParam("dg_car", "starttime", $V("starttime"));
		DataGrid.setParam("dg_car", "enddate", $V("enddate"));
		DataGrid.setParam("dg_car", "endtime", $V("endtime"));
		DataGrid.loadData("dg_car");
	}
</script>
</head>
<body>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">
			<tr>
				<td valign="top">
			<fieldset>
				<legend> <label>车主信息</label> </legend>
				<z:init method="com.wangjin.cms.car.QueryCarOwnerInfo.init">
				<form id="form1">
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">保险公司：</td>
							<td>
								<z:select id="prop1" style="width:117px">${prop1Init}</z:select>
							</td>
							<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">车主姓名：</td>
							<td>
								<input value="" type="text" id="CarOwner" name="CarOwner" ztype="String" class="inputText" size="20">
							</td>
							<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">联系电话：</td>
							<td>
								<input value="" type="text" id="ContactPhone" name="ContactPhone" ztype="String" class="inputText" size="20">
							</td>
							<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">邮箱：</td>
							<td>
								<input value="" type="text" id="ContactEmail" name="ContactEmail" ztype="String" class="inputText" size="20">
							</td>
							<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">省份：</td>
							<td>
								<input value="" type="text" id="prop2" name="prop2" ztype="String" class="inputText" size="20">
							</td>
							<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">城市：</td>
							<td>
								<input value="" type="text" id="Address" name="Address" ztype="String" class="inputText" size="20">
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">操作开始时间：</td>
							<td><input name="startdate" id="startdate" value=""
								type="text" size="14" ztype="Date" /></td>
							<td>&nbsp;</td>
							<td><input name="starttime" id="starttime" value=""
								type="text" size="14" ztype="Time" /></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">操作结束时间：</td>
							<td><input name="enddate" id="enddate" value=""
								type="text" size="14" ztype="Date" /></td>
							<td>&nbsp;</td>
							<td><input name="endtime" id="endtime" value=""
								type="text" size="14" ztype="Time" /></td>
						</tr>
					</table>
				</form>
				</z:init>
			</fieldset>
			<table>
					<tr>
						<td>&nbsp;</td>
					</tr>
			</table>
			<z:tbutton onClick="sear()"><img src="../Icons/icon022a16.gif" width="20" height="20" />车主信息查询</z:tbutton>
			<tr>
				<td>&nbsp;</td>
			</tr> 
				<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
				<tr>
					<td>
					<z:datagrid id="dg_car" method="com.wangjin.cms.car.QueryCarOwnerInfo.ownerInfoInquery" size="20" scroll="true" 
					autoFill="true"  lazy="false" multiSelect="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" afterdrag="afterRowDragEnd" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="100" style="text-align:center;"><b>姓名</b></td>
						        <td width="120" style="text-align:center;"><b>电话</b></td>
						        <td width="100" style="text-align:center;"><b>行驶省份</b></td>
						        <td width="100" style="text-align:center;"><b>行驶城市</b></td>
						        <td width="100" style="text-align:center;"><b>车牌号</b></td>
						        <td width="100" style="text-align:center;"><b>提车时间</b></td>
						        <td width="150" style="text-align:center;"><b>邮箱</b></td>
						        <td width="120" style="text-align:center;"><b>操作时间</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td>&nbsp;</td>
								<td >${CarOwner}</td>
								<td title="${ContactPhone}">${ContactPhone}</td>
								<td title="${Prop2}">${Prop2}</td>
								<td title="${Address}">${Address}</td>
								<td title="${PlateNo}">${PlateNo}</td>
								<td title="${BuyDate}">${BuyDate}</td>
								<td title="${ContactEmail}">${ContactEmail}</td>
								<td title="${CreateDate}">${CreateDate}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
								<input name="id" type="hidden" id="id" value="${id}" style="width:150px">
							</tr>
						</table>
					</z:datagrid>
					</td>
					</tr>
				</table>
					</td>
				<tr>
			</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
