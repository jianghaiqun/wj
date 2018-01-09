<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>团险信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.TeamReservation.orderInquery" size="15" scroll="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="24%"><b>企业名称</b></td>
								<td width="15%"><b>参保人数</b></td>
								<td width="15%"><b>联系时间</b></td>
								<td width="15%"><b>联系人姓名</b></td>
								<td width="15%"><b>联系电话</b></td>
								<td width="16%"><b>创建日期</b></td>
							</tr>
							<tr   style="background-color:#F9FBFC">
								<td>${companyname}</td>
								<td>${peoplenum}</td>
								<td>${connecttime}</td>
								<td>${connectname}</td>
								<td>${connectiphone}</td>
								<td>${submitDate}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid>
</body>
</html>