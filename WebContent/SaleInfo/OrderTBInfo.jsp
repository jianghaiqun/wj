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
function statStaff(){
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","allDate","0");
	DataGrid.setParam("dg1","Markingkg",$V("Markingkg"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function statAll(){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1","allDate","1");
	DataGrid.setParam("dg1","Markingkg",$V("Markingkg"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
//显示订单详细信息
function showOrderDetail(orderSn,KID){
	window.open('../shop/order_config_new!linkOrderDetails.action?orderSn='+orderSn+"&KID="+KID,'_blank');
}
</script>
</head>
<body>
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:520px">
						统计时间：从
						<input value="${yesterday}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						订单所属险种：
						<z:select id="Markingkg">${Markingkg}</z:select>
						</span>
						<z:tbutton onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<z:tbutton onClick="statAll()"> <img src="../Icons/icon031a15.gif" />全部统计</z:tbutton>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.OrderInfo.dg2DataBind">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo"><b>序号</b></td>
								<td width="11%">订单号</td>
								<td width="20%">产品</td>
								<td width="5%" ><b>投保人</b></td>
								<td width="5%" ><b>保单数</b></td>
								<td width="4%" ><b>保费</b></td>
								<td width="4%" ><b>手续费</b></td>
								<td width="11%" ><b>日期</b></td>
								<td width="5%" ><b>所属险种</b></td>
								<td width="7%" ><b>所属公司</b></td>
								<td width="3%" ><b>会员</b></td>	
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td><a  style="cursor: hand;" onClick="showOrderDetail('${ordersn}','${KID}')">${ordersn}</a></td>
								<td style="text-align:left;">${productName}</td>
								<td style="text-align:right;">${applicantName}</td>
								<td style="text-align:right;">${pocount}</td>
								<td style="text-align:right;">${totalAmount}</td>
								<td style="text-align:right;">${charge}</td>
								<td>${modifyDate}</td>
								<td>${ProductGenera}</td>
								<td>${insureName}</td>
								<td>${isMember}</td>
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