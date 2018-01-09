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
		DataGrid.setParam("dg2", Constant.PageIndex, 0);
		DataGrid.setParam("dg2", "codename", $V("codename"));
		DataGrid.loadData("dg2");
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
				险&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;种：&nbsp;&nbsp;<input id="riskCode2other" name="riskCode2other" value="" style="width:350px" readonly="readonly" disabled="disabled"></input>
				<input id="riskCode2" name="riskCode2" value="" style="width:350px" readonly="readonly" disabled="disabled" type="hidden"></input>
			</tr>
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />险种列表</td>
			</tr>
			<tr>
				险种名称：
				<input name="codename" type="text" id="codename" value="" style="width:90px">
                <input type="button"  value="查询" onClick="sear()">
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
						<z:datagrid id="dg2" method="com.wangjin.coupon.CouponInfo.dg1DataBindRiskCode"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" afterdrag="afterRowDragEnd"
								style="table-layout: fixed" fixedHeight="190px">
								<tr ztype="head" class="dataTableHead">
									<td width="10%" height="30" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="10%" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td width="40%"><strong>险种代码</strong></td>
									<td width="40%"><strong>险种名称</strong></td>
								</tr>
								<tr style="background-color: #F9FBFC">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>${id}</td>
									<td>${codename}</td>
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
		<%
			String riskCodeother = request.getParameter("riskCodeother");
			String riskCode = request.getParameter("riskCode");
		%>
		var riskcode='<%=riskCode%>';
		var riskcodeArray=riskcode.split(",");
		for(var i=0;i<riskcodeArray.length;i++){
			DataGrid.select($("dg2"),riskcodeArray[i]);
		}
		jQuery("#riskCode2other").val('<%=riskCodeother%>');
		jQuery("#riskCode2").val('<%=riskCode%>');
</script>
</html>