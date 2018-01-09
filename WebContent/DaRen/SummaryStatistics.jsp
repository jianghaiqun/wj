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
<title>汇总统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//统计
function count(){ 
	var sd = $V("statisticalTime");
	var ed = $V("endStatisticalTime");
	if (Verify.hasError()) {
		return;
	}
	if(ed < sd){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","statisticalTime",sd);
	DataGrid.setParam("dg1","endStatisticalTime",ed);
	DataGrid.loadData("dg1");
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
				<z:init method="com.wangjin.daren.AuthorDetailInfo.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>统计时间 从：</td>
		                	<td> <input name="statisticalTime" type="text" id="statisticalTime" value="" style="width:100px" ztype="date" verify="统计开始时间|NotNull"></td>
							<td>至：</td>
							<td><input name="endStatisticalTime" type="text" id="endStatisticalTime" value="" style="width:100px" ztype="date" verify="统计结束时间|NotNull"></td>
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
					<z:datagrid id="dg1" method="com.wangjin.daren.SummaryStatistics.dg1DataBind" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;"  >
							<tr ztype="head" class="dataTableHead">
						        <td width="100" style="text-align:center;"><b>姓名</b></td>
						        <td width="100" style="text-align:center;"><b>订单量</b></td>
						        <td width="100" style="text-align:center;"><b>保费（元）</b></td>
						        <td width="100" style="text-align:center;"><b>成本（元）</b></td>
						        <td width="100" style="text-align:center;"><b>投产比</b></td>
						        <td width="80" style="text-align:center;"><b>流量</b></td>
						        <td width="80" style="text-align:center;"><b>作者量</b></td>
								<td width="80" style="text-align:center;"><b>文章量</b></td>
								<td width="80" style="text-align:center;"><b>转化率</b></td>
								<td width="80" style="text-align:center;"><b>单文订单</b></td>
								<td width="80" style="text-align:center;"><b>单文保费</b></td>
								<td width="100" style="text-align:center;"><b>单均</b></td>
								<td width="100" style="text-align:center;"><b>辅助转化数</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td title="${realName}">${realName}</td>
								<td title="${orderNum}">${orderNum}</td>
								<td title="${sumPrem}">${sumPrem}</td>
								<td title="${cost}">${cost}</td>
								<td title="${production}">${production}</td>
								<td title="${flow}">${flow}</td>
								<td title="${authorNum}">${authorNum}</td>
								<td title="${articleNum}">${articleNum}</td>
								<td title="${conversionRate}">${conversionRate}</td>
								<td title="${orderNu}">${orderNu}</td>
								<td title="${prem}">${prem}</td>
								<td title="${average}">${average}</td>
								<td title="${convertNum}">${convertNum}</td>
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
