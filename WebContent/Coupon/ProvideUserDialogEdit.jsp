<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	function sear() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "realName", $V("realName"));
		DataGrid.loadData("dg1");
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
<input type="hidden" id="provideuserparam" name="provideuserparam" value=${provideuserparam}>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />后台用户列表</td>
			</tr>
			<tr>
				用户姓名：
				<input name="realName" type="text" id="realName" value="" style="width:90px">
                <input type="button"  value="查询" onClick="sear()">
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
						<z:datagrid id="dg2" method="com.wangjin.coupon.CouponInfo.dg1DataBindProvideUser"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" afterdrag="afterRowDragEnd"
								style="table-layout: fixed" fixedHeight="190px">
								<tr ztype="head" class="dataTableHead">
									<td width="10%" height="30" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="10%" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td width="40%"><strong>用户名</strong></td>
									<td width="40%"><strong>用户姓名</strong></td>
								</tr>
								<tr style="background-color: #F9FBFC">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>${id}</td>
									<td>${realName}</td>
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
<script type="text/javascript">
		<%String provideuserparam = request.getParameter("provideuserparam");%>
		var provideuser='<%=provideuserparam%>';
		var provideArray=provideuser.split(",");
		for(var i=0;i<provideArray.length;i++){
			DataGrid.select($("dg2"),provideArray[i]);
		}
</script>
</html>