<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script>
function exportStaff(){
	DataGrid.toExcel("dg2",1);
}

function init(startDate, endDate, From, Wedo, channelSn) {
	DataGrid.setParam("dg2","startDate",startDate);
	DataGrid.setParam("dg2","endDate",endDate);
	DataGrid.setParam("dg2","From",From);
	DataGrid.setParam("dg2","Wedo",Wedo);
	DataGrid.setParam("dg2","contant",channelSn);
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	//Dialog.wait("查询中...");
	DataGrid.loadData("dg2");
	//Dialog.closeEx();
}
</script>

</head>
<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0" align="center">
				<tr>
                   <td>
                   <z:tbutton onClick="exportStaff()"><img src="../Icons/icon021a3.gif" />导出EXCEL</z:tbutton>
                   </td>
			</tr>
					<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.CustomerAddStatisticNew.dg2DataBind" size="10" lazy="true">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
							<td width="2%" ztype="selector" field="id">&nbsp;</td>
							<td width="10%" style="text-align:center;"><b>订单号</b></td>
							<td width="8%" style="text-align:center;"><b>投/被保人姓名</b></td>
							<td width="8%" style="text-align:center;"><b>投/被保人证件类型</b></td>
							<td width="12%" style="text-align:center;"><b>投/被保人证件号码</b></td>
							<td width="10%" style="text-align:center;"><b>投/被保人移动电话</b></td>
							<td width="10%" style="text-align:center;"><b>投/被保人电子邮箱</b></td>
							<td width="8%" style="text-align:center;"><b>投/被保人创建时间</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td style="text-align:center;"></td>
							<td>&nbsp;</td>
							<td>${ordersn}</td>
							<td>${NAME}</td>
							<td>${IdentityTypeName}</td>
							<td>${IdentityId}</td>
							<td>${Mobile}</td>
							<td>${Mail}</td>
							<td>${createDate}</td>
						</tr>
						<tr ztype="pagebar">
							<td height="25" colspan="11">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
</table>
</form>
</body>
</html>
