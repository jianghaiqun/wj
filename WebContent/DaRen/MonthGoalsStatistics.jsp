<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>月计划完成统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//统计
function count(){ 
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","year",$V("year"));
	DataGrid.setParam("dg2","month",$V("month"));
	DataGrid.loadData("dg2");
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
				<td>
				<z:init method="com.wangjin.daren.MonthGoalsInput.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>月份：</td>
		                	<td><z:select id="year" name="year" style="width:50px" value="${yearValue}" verify="年|NotNull">${year}</z:select>&nbsp;年&nbsp;&nbsp;<z:select id="month" name="month" style="width:50px" verify="月|NotNull" value="${monthValue}">${month}</z:select>&nbsp;月</td>
							<td><z:tbutton onClick="count()"> <img src="../Icons/icon031a1.gif"/>统计</z:tbutton></td>
						</tr>
		             </table>
		             </z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 5px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.wangjin.daren.MonthGoalsInput.dg2DataBind" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" >
							<tr ztype="head" class="dataTableHead">
								<td width="40" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
						        <td width="70" style="text-align:center;"><b>姓名</b></td>
						        <td width="120" style="text-align:center;"><b>总计划保费（元）</b></td>
						        <td width="120" style="text-align:center;"><b>已完成保费（元）</b></td>
						        <td width="100" style="text-align:center;"><b>完成比例</b></td>
						        <td width="100" style="text-align:center;"><b>剩余保费</b></td>
						        <td width="80" style="text-align:center;"><b>剩余日均</b></td>
						        <td width="80" style="text-align:center;"><b>计划日均</b></td>
								<td width="80" style="text-align:center;"><b>订单量</b></td>
								<td width="80" style="text-align:center;"><b>剩余天数</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td title="${createUser}">${createUser}</td>
								<td title="${goalsPrem}">${goalsPrem}</td>
								<td title="${completePrem}">${completePrem}</td>
								<td title="${completeRates}">${completeRates}</td>
								<td title="${surplusPrem}">${surplusPrem}</td>
								<td title="${surplusUnit}">${surplusUnit}</td>
								<td title="${goalsUnit}">${goalsUnit}</td>
								<td title="${orderNum}">${orderNum}</td>
								<td title="${surplusDay}">${surplusDay}</td>
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>
