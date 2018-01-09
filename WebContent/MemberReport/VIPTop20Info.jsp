<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>VIPTop20产品</title>
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
	DataGrid.setParam("dg1","productType",$V("productType"));
	DataGrid.loadData("dg1");
}

//显示的用户列表
function showMemberDetail(productid){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1200; 
	diag.Height = 550; 
	diag.Title = "VIP会员信息列表查看";
	diag.URL = "MemberReport/VIPTop20MemberDialog.jsp?startDate="+startDate+"&endDate="+endDate+"&productid="+productid;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
//显示回购的订单详情
function showOrderDetail(productid){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 550;
	diag.Title = "VIP购买订单信息列表查看";
	diag.URL = "MemberReport/VIPTop20OrderDialog.jsp?startDate="+startDate+"&endDate="+endDate+"&productid="+productid;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看订单信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
//显示回购的保单详情
function showRiskTypeDetail(productid){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 550;
	diag.Title = "VIP购买保单信息列表查看";
	diag.URL = "MemberReport/VIPTop20RiskTypeDialog.jsp?startDate="+startDate+"&endDate="+endDate+"&productid="+productid;
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
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />VIPTop20产品</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.memberreport.VIPTop20Report.init">
				<table>
					<tr>	
							<td>统计时间：</td>
						<td><input name="startDate" type="text" id="startDate" value="${startDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
							<td>到：</td>
						<td><input name="endDate" type="text" id="endDate" value="${endDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
							<td>险种：</td>
						<td><z:select style="width:100px;" name="productType" id="productType">${erisktype}</z:select></td>
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
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.VIPTop20Report.dg1DataBind" lazy="true">
						<table width="80%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="20%">产品名称</td>
								<td width="10%">产品编号</td>
								<td width="10%">会员数</td>
								<td width="10%">订单数</td>
								<td width="10%">保单数</td>
								<td width="15%">保费</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="left" title="${productname}">${productname}</td>
								<td align="left">${productid}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showMemberDetail('${productid}')">${membercount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${productid}')">${ordersncount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: pointer;" onClick="showRiskTypeDetail('${productid}')">${policycount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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