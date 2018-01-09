<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>vip成本支出</title>
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
	DataGrid.loadData("dg1");
}

//显示订单详情
function showOrderDetail(channelsn, type){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 580;
	diag.Title = "VIP成本信息列表查看";
	diag.URL = "MemberReport/VIPCostOrderDialog.jsp?type="+type+"&startDate="+startDate+"&endDate="+endDate+"&channelsn="+channelsn;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看订单信息";
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />vip成本支出信息</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.memberreport.VIPCostReport.init">
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
				</z:init>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.VIPCostReport.dg1DataBind" lazy="true">
				
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="15%">订单渠道</td>
								<td width="10%">vip订单总数</td>
								<td width="8%">保费</td>
								<td width="8%">已付费</td>
								<td width="8%">使用优惠券订单数</td>
								<td width="8%">优惠券累计金额</td>
								<td width="8%">活动优惠订单数</td>
								<td width="8%">活动优惠金额</td>
								<td width="8%">积分抵值订单数</td>
								<td width="8%">积分抵值金额</td>
								<td width="8%">赠送的积分数</td>
								<td width="8%">赠送的积分金额</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="left">${channelname}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${channelsn}', 'all')">${ordersncount}</a></td>
								<td align="right">${premTotal}</td>
								<td align="right">${payTotal}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${channelsn}', 'coupon')">${couponcount}</a></td>
								<td align="right">${orderCouponsum}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${channelsn}', 'activity')">${activitycount}</a></td>
								<td align="right">${orderActivitysum}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${channelsn}', 'Integral')">${orderIntegralcount}</a></td>
								<td align="right">${orderIntegralsum}</td>
								<td align="right">${Integralsum}</td>
								<td align="right">${IntegralTotal}</td>
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