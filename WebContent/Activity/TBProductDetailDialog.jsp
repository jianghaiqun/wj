<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	DataGrid.onRowClick = function(ele,evt){
		return false;
	};
</script>
<style>
#hotarea {
	width: 160px;
	height: 120px;
	border: #147 1px solid;
	background: #ca6 url(../Platform/Images/picture.jpg) no-repeat;
	position: relative
}

#hotarea  a {
	position: absolute;
	display: block;
	width: 35px;
	height: 25px;
	border: #fff 1px dashed;
	text-align: center;
	line-height: 24px;
	color: #fff;
}

#hotarea  a:hover {
	text-decoration: none;
	border: #fff 1px solid;
	color: #fff
}
</style>
</head>
<body>
<input type="hidden" id="provideuserparam" name="provideuserparam" value=${provideuserparam}>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />产品列表</td>
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
						<z:datagrid id="dg2" method="com.wangjin.activity.ActivityInfo.dg2DataBindProduct"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" afterdrag="afterRowDragEnd"
								style="table-layout: fixed" fixedHeight="190px">
								<tr ztype="head" class="dataTableHead">
									<td width="20px" height="30" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="20px" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td width="70px"><strong>产品代码</strong></td>
									<td width="120px"><strong>产品名称</strong></td>
									<td width="60px"><strong>计划编码</strong></td>
									<td width="60px"><strong>保险期限</strong></td>
									<td width="60px"><strong>投保年龄</strong></td>
									<td width="60px"><strong>职业等级</strong></td>
									<td width="40px"><strong>性别</strong></td>
									<td width="60px"><strong>缴费方式</strong></td>
								</tr>
								<tr style="background-color: #F9FBFC">
									<td>&nbsp;</td>
									<td disabled="true">&nbsp;</td>
									<td title="${id}">${id}</td>
									<td title="${ProductName}">${ProductName}</td>
									<td title="${PlanCode}">${PlanCode}</td>
									<td title="${CoverageYear}">${CoverageYear}</td>
									<td title="${AgeIssue}">${AgeIssue}</td>
									<td title="${Occupation}">${Occupation}</td>
									<td title="${Gender}">${Gender}</td>
									<td title="${PaymentMethods}">${PaymentMethods}</td>
								</tr>
							</table>
						</z:datagrid></td>
					</tr>
				</table>
				</td>
			<tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>