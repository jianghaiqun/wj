<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>

<z:init method="com.sinosoft.cms.memberreport.MemberReport.init2Dialog">
<script>
Page.onLoad(function(){
	doSearch();
});
function doSearch(){
	DataGrid.setParam("dg1", Constant.PageIndex, 0);
	DataGrid.setParam("dg1", "type", '${type}');
	DataGrid.setParam("dg1", "startDate", '${startDate}');
	DataGrid.setParam("dg1", "endDate", '${endDate}');
	// 会员渠道
	DataGrid.setParam("dg1", "channelsn", '${channelsn}');
	DataGrid.setParam("dg1", "Search","search");
	DataGrid.loadData("dg1");
}

//显示订单详细信息
function showOrderDetail(orderSn) {
	var queryID = orderSn;
	//alert(KID);
	var KKID = '';
	var dc = new DataCollection();
	dc.add("KID", KID + queryID);
	var method = "cn.com.sinosoft.util.StringUtilC.md5Hex";

	Server.sendRequest(method, dc, function(response) {
		//alert(response.get("KID"));
		KKID = response.get("KID");
		var win = window.open(
				'../shop/order_config_new!linkOrderDetails.action?orderSn='
						+ queryID + "&KID=" + KKID, '_blank');
		win.focus();
	});

}
</script>
</z:init>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" /订单列表</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.MemberReport.dg2DataBind_dialog" lazy="true" size="20">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
						        <td width="120" style="text-align:center;"><b>订单编号</b></td>
						        <td width="120" style="text-align:center;"><b>渠道订单号</b></td>
						        <td width="145" style="text-align:center;"><b>保单号</b></td>
						        <td width="55" style="text-align:center;"><b>保全记录</b></td>
						        <td width="50" style="text-align:center;"><b>投保人</b></td>
						        <td width="70" style="text-align:center;"><b>被保险人数</b></td>
						        <td width="120" style="text-align:center;"><b>起保日期</b></td>
						        <td width="120" style="text-align:center;"><b>修改时间</b></td>
								<td width="150" style="text-align:center;"><b>产品名称</b></td>
								<td width="55" style="text-align:center;"><b>产品计划</b></td>
								<td width="50" style="text-align:center;"><b>保费</b></td>
								<td width="50" style="text-align:center;"><b>已付费</b></td>
								<td width="110" style="text-align:center;"><b>会员ID</b></td>
								<td width="55" style="text-align:center;"><b>订单状态</b></td>
								<td width="145" style="text-align:center;"><b>优惠券号</b></td>
								<td width="100" style="text-align:center;"><b>优惠券优惠金额</b></td>
								<td width="145" style="text-align:center;"><b>活动号</b></td>
								<td width="80" style="text-align:center;"><b>活动优惠金额</b></td>
								<td width="90" style="text-align:center;"><b>积分抵值金额</b></td>
								<td width="80" style="text-align:center;"><b>是否报案</b></td>
								<td width="80" style="text-align:center;"><b>渠道</b></td>
								<td width="150" style="text-align:center;"><b>自定义活动描述</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td style="text-align:center;" title="${orderSn}">${orderSn}</td>
								<td title="${tbTradeSeriNo}">${tbTradeSeriNo}</td>
								<td title="${policyNo}">${policyNo}</td>
								<td style="text-align:center;" title="${remark}">${remark}</td>
								<td style="text-align:center;" title="${ApplicantName}">${ApplicantName}</td>
								<td style="text-align:center;" title="${recognizeeNu}">${recognizeeNu}</td>
								<td style="text-align:center;" title="${svalidate}">${svalidate}</td>
								<td style="text-align:center;" title="${ModifyDate}">${ModifyDate}</td>
								<td title="${ProductName}">${ProductName}</td>
								<td title="${planName}">${planName}</td>
								<td style="text-align:right;" title="${totalAmount}">${totalAmount}</td>
								<td style="text-align:right;" title="${PayPrice}">${PayPrice}</td>
								<td style="text-align:right;" title="${mid}">${mid}</td>
								<td style="text-align:center;" title="${orderstatusname}">${orderstatusname}</td>
								<td style="text-align:right;" title="${couponSn}">${couponSn}</td>
								<td style="text-align:right;" title="${parValue}">${parValue}</td>
								<td style="text-align:right;" title="${ActivitySn}">${ActivitySn}</td>
								<td style="text-align:right;" title="${orderActivity}">${orderActivity}</td>
								<td style="text-align:right;" title="${offsetPoint}">${offsetPoint}</td>
								<td style="text-align:center;" title="${paymentReport}">${paymentReport}</td>
								<td style="text-align:center;" title="${channelsn}">${channelsn}</td>
								<td style="text-align:center;" title="${diyActivityDescription}">${diyActivityDescription}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>