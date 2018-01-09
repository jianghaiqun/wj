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
<title>营销管理-明细</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
</head>
<script type="text/javascript">
Page.onLoad(function(){
	DataGrid.setParam("dg1","MemberID",$V("MemberID"));
	DataGrid.setParam("dg1","BeginDate",$V("BeginDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
});
</script>

<body>
	<input type="hidden" id="MemberID" value="<%=request.getParameter("MemberID") %>">
	<input type="hidden" id="BeginDate" value="<%=request.getParameter("BeginDate") %>">
	<input type="hidden" id="EndDate" value="<%=request.getParameter("EndDate") %>">
	
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 订单信息</td>
		          </tr>
				<tr>
			</tr>
			  <tr>
				<td style="padding: 0px 5px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.MemberMarketing.detail" size="20" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="4%" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="8%" style="text-align:center;"><b>会员名</b></td>
						        <td width="16%" style="text-align:center;"><b>订单编号</b></td>
						        <td width="28%" style="text-align:center;"><b>产品名称</b></td>
						        <td width="9%" style="text-align:center;"><b>订单金额</b></td>
						        <td width="9%" style="text-align:center;"><b>实付金额</b></td>
						        <td width="7%" style="text-align:center;"><b>订单状态</b></td>
						        <td width="15%" style="text-align:center;"><b>支付时间</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td >&nbsp;</td>
								<td >&nbsp;</td>
								<td title="${UserName}">${UserName}</td>
								<td title="${OrderSn}">${OrderSn}</td>
								<td title="${ProductName}">${ProductName}</td>
								<td title="${totalAmount}">&nbsp;&nbsp;${totalAmount}</td>
								<td title="${payAmount}">&nbsp;&nbsp;${payAmount}</td>
								<td title="${orderStatusName}">&nbsp;&nbsp;${orderStatusName}</td>
								<td title="${tModifyDate}">&nbsp;&nbsp;${tModifyDate}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="7">${PageBar}</td>
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
