<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>vip消费</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	if( $V("startDate") == null || $V("startDate") == "" ){
		Dialog.alert("请填写统计开始时间！");
		return;
	}
	if($V("endDate") == null || $V("endDate") == "" ){
		Dialog.alert("请填写统计结束时间！");
		return;
	}
	DataGrid.loadData("dg1", vipbuycount);
}
function vipbuycount() {
	var arrs = DataGrid.getAllData("dg1");
	var drs = arrs.Rows;
	var dr = drs[0];
	if (drs[0] != "" && drs[0] != null) {
		document.getElementById("BUYSpan").innerHTML = dr.get("vipbuycount");
	} 
}

//显示的用户列表
function showHeadMemberDetail(type){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1200; 
	diag.Height = 550; 
	diag.Title = "VIP会员列表查看";
	diag.URL = "MemberReport/VIPBuyHeadMemberDialog.jsp?type="+type+"&startDate="+startDate+"&endDate="+endDate;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
function showMemberDetail(channelsn, productType){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1200; 
	diag.Height = 550; 
	diag.Title = "VIP会员列表查看";
	diag.URL = "MemberReport/VIPBuyMemberDialog.jsp?productType="+productType+"&startDate="+startDate+"&endDate="+endDate+"&channelsn="+channelsn;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
//显示订单详情
function showOrderDetail(channelsn, productType){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 550;
	diag.Title = "信息列表查看";
	diag.URL = "MemberReport/VIPBuyOrderDialog.jsp?productType="+productType+"&startDate="+startDate+"&endDate="+endDate+"&channelsn="+channelsn;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "VIP会员购买订单信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
//显示保单详情
function showRiskTypeDetail(channelsn, productType){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 550;
	diag.Title = "信息列表查看";
	diag.URL = "MemberReport/VIPBuyRiskTypeDialog.jsp?productType="+productType+"&startDate="+startDate+"&endDate="+endDate+"&channelsn="+channelsn;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "VIP会员购买保单信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
</script>

</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />vip消费信息</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.memberreport.VIPBuyReport.init">
				<table>
					<tr>	
							<td>统计时间：</td>
						<td><input name="startDate" type="text" id="startDate" value="${startDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
							<td>到：</td>
						<td><input name="endDate" type="text" id="endDate" value="${endDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
					</tr>
					<tr>
               			<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
            		</tr>
				</table>
				</td>
				</tr>
				<tr>
					<td colspan="11">
						<span style="font-size:14px;font-weight:900;" id="ALLSpan">vip会员数量：<a style="cursor: pointer;" onClick="showHeadMemberDetail('ALL')">${vipallcount}</a></span>
						<span style="width: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span style="font-size:14px;font-weight:900;">购买vip数量：<a style="cursor: pointer;" onClick="showHeadMemberDetail('BUY')"><span id="BUYSpan">${vipbuycount}</span></a></span>
					</td>
				</tr>
				</z:init>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.VIPBuyReport.dg1DataBind" lazy="true">
				
						<table width="80%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="10%">订单渠道</td>
								<td width="10%">险种</td>
								<td width="10%">会员数</td>
								<td width="10%">订单数</td>
								<td width="10%">保单数</td>
								<td width="15%">保费</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="left">${channelname}</td>
								<td align="left">${ProductGenera}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showMemberDetail('${channelsn}','${productType}')">${membercount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${channelsn}','${productType}')">${ordercount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: pointer;" onClick="showRiskTypeDetail('${channelsn}','${productType}')">${policycount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right">${totalAmount}</td>
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