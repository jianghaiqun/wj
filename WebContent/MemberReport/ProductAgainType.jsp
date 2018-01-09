<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%> 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员复购险种统计</title>
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
	if($NV("contant") == null || $NV("contant") == "" ){
		Dialog.alert("请选择渠道！");
		return;
	}
	// 会员渠道
	DataGrid.setParam("dg1","contant",$NV("contant"));
	DataGrid.loadData("dg1");
}

function showchannel(){
	var check_flag=jQuery('#showchannel').is(':checked');
	if(check_flag==true){
		jQuery("#channeltree").show();
	}else{
		jQuery("#channeltree").hide();
	}
}
//显示复购的订单详情
function showOrderDetail(channelsn, productType){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var contant = $NV("contant");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 580;
	diag.Title = "复购 订单列表查看";
	
	diag.URL = "MemberReport/AgainOrderDialog.jsp?channelsn="+channelsn+"&productType="+productType+"&startDate="+startDate+"&endDate="+endDate+"&contant="+contant;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看订单信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
//显示复购的保单详情
function showRiskTypeDetail(channelsn, productType){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var contant = $NV("contant");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 580;
	diag.Title = "复购保单信息列表";
	diag.URL = "MemberReport/AgainRiskTypeDialog.jsp?channelsn="+channelsn+"&productType="+productType+"&startDate="+startDate+"&endDate="+endDate+"&contant="+contant;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看保单信息";
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
			<td id="channeltree" style="display: none">
				<table width="50" border="0" cellspacing="0" cellpadding="6"
					class="blockTable">
					<tr>
						<td style="padding: 6px;"  width="50" class="blockTd"><z:tree id="tree1"
							style="height:440px;width:120px;"
							method="com.sinosoft.platform.Channel.treeDataBind"
							level="3" lazy="true" resizeable="true">
							<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
							<p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
								name="contant" id='contant_${ID}' value='${ChannelCode}'
								onClick="onCheck(this);"><label for="contant_${ID}"><span id="span_${ID}">${Name}</span></label></p>
						</z:tree></td>
					</tr>
				</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员复购险种统计</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.memberreport.AgainProductTypeReport.init">
				<table>
					<tr>	
							<td>统计时间：</td>
						<td><input name="startDate" type="text" id="startDate" value="${startDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
							<td>到：</td>
						<td><input name="endDate" type="text" id="endDate" value="${endDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
						<td><input type="checkbox" onclick="showchannel()" id="showchannel" name="showchannel"/>订单渠道</td>
					</tr>
					<tr>
               			<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
            		</tr>
				</table>
				</z:init>
				</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.AgainProductTypeReport.dg1DataBind" lazy="true">
						<table width="80%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="10%">渠道</td>
								<td width="10%">险种</td>
								<td width="10%">保单数</td>
								<td width="10%">订单数</td>
								<td width="15%">保费</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="left">${channelname}</td>
								<td align="left">${ProductGenera}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showRiskTypeDetail('${channelsn}', '${ProductType}')">${policycount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${channelsn}', '${ProductType}')">${ordercount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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