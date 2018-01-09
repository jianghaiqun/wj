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
DataGrid.getSelectedValueByColumns = function(ele, column) {
	ele = $(ele);
	var ds = ele.DataSource;
	var str="";
	for ( var i = 0; i < ds.Columns.length; i++) {
		if (ds.Columns[i].Name == column.toLowerCase()) {
			for ( var k = 1; k < ele.rows.length; k++) {
				if (ele.rows[k].Selected) {
					str+=ds.Values[k - 1][i]+",";
				}
			}
		}
	}
	return str.substring(0,str.length-1);
}
	function sear() {
		DataGrid.setParam("dg4", Constant.PageIndex, 0);
		DataGrid.setParam("dg4", "productcode", $V("productcode"));
		DataGrid.loadData("dg4");
	}
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
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">
			<tr>
				产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品：&nbsp;&nbsp;<input id="product2other" name="product2other" value="" style="width:350px" readonly="readonly" disabled="disabled"></input>
				<input id="product2" name="product2" value="" style="width:350px" readonly="readonly" disabled="disabled" type="hidden"></input>
			
			</tr>
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />产品列表</td>
			</tr>
			<tr>
			
				产品代码：
				<input name="productcode" type="text" id="productcode" value="" style="width:90px">
                <input type="button"  value="查询" onClick="sear()">
                
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
						<z:datagrid id="dg4" method="com.wangjin.activity.ActivityInfo.dg2DataBindProduct"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
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
									<td>&nbsp;</td>
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