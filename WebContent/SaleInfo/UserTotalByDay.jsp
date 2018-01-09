<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.TotalInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>会员消费次数</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript">
function statStaff(){
	var arr = $NV("contant");
	if(arr==null){
		Dialog.alert("暂不支持旅行保渠道订单的详情查询！");
		return;
	}
	for ( var i = 0; i < arr.length; i++) {
		var id = arr[i];
		if(id.startWith("b2b")){
			Dialog.alert("暂不支持该渠道订单的详情查询！");
			return;
		}
	}
	DataGrid.setParam("dg5","startDate",$V("startDate"));
	DataGrid.setParam("dg5","endDate",$V("endDate"));
	document.getElementById("allDate").value = '0';
	DataGrid.setParam("dg5","allDate","0");
	DataGrid.setParam("dg5",Constant.PageIndex,0);
	DataGrid.setParam("dg5","contant",$NV("contant"));
	DataGrid.loadData("dg5");
}
function statAll(){
	DataGrid.setParam("dg5","startDate","");
	DataGrid.setParam("dg5","endDate","");
	document.getElementById("allDate").value = '1';
	DataGrid.setParam("dg5","allDate","1");
	DataGrid.setParam("dg5",Constant.PageIndex,0);
	DataGrid.loadData("dg5");
}
function exportStaff(){
	var arr = $NV("contant");
	if(arr==null){
		Dialog.alert("暂不支持旅行保渠道订单的详情查询！");
		return;
	}
	for ( var i = 0; i < arr.length; i++) {
		var id = arr[i];
		if(id.startWith("b2b")){
			Dialog.alert("暂不支持该渠道订单的详情查询！");
			return;
		}
	}
	DataGrid.toExcel("dg5",1);
}
//显示订单详细信息
function showOrderDetail(orderSn,KID){
	var queryID=orderSn;
	var keyID=KID;
	window.open('../admin/order!linkOrderDetails.action?orderSn='+queryID+"&KID="+keyID);
}
//显示用户列表
function showMemberDetail(type){
	var startDate;
	var endDate;
	var allDate = document.getElementById("allDate").value;
	if(allDate == '1'){
		startDate = '';
		endDate	= '';
	}else{
		startDate = document.getElementById("startDate").value; 
		endDate = document.getElementById("endDate").value; 
	}
    var arr = DataGrid.getSelectedValue("dg5");
	if(type&&type!=""){
		arr = new Array();
		arr[0] = type;
		arr[1] = startDate;
		arr[2] = endDate;
		arr[3] = allDate;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 350;
	diag.Title = "当天注册用户列表查看";
	diag.URL = "SaleInfo/UserListDialog.jsp?type="+arr[0]+"&startDate="+startDate+"&endDate="+endDate+"&allDate="+allDate;
		diag.onLoad = function() {
			$DW.init();
		};
		diag.ShowMessageRow = true;
		diag.MessageTitle = "查看会员信息";
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value = "关闭";
	}
</script>
</head>
<body>
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
	    <td>
		<table width="20" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding: 6px;" class="blockTd"><z:tree id="tree1"
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
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:500px">
						统计时间：从
						<input value="${yesterday}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						<input type = "hidden" id = "allDate" value="${allDate}">
						<%-- 统计范围：
						<z:select id="contant" name="contant" style="width:90px" >${contant}</z:select> --%>
						</span>
						<z:tbutton onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<%-- <z:tbutton onClick="statAll()"> <img src="../Icons/icon031a15.gif" />全部统计</z:tbutton> --%>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg5" method="com.wangjin.infoseeker.TotalInfo.dg5DataBind" lazy="true">
						<table width="80%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><b>序号</b></td>
								<td width="10%">回购次数</td>
								<td width="10%">会员数量</td>
								<td width="10%">比例</td>
								<td width="15%">订单</td>
								<td width="20%">保费</td>
								<td width="15%">手续费</td>
								<td width="15%">单均利润</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td>${type}</td>
								<td align="right">${memberCount}</td>
								<td>${scale}</td>
								<td>${orderCount}</td>
								<td>${totalAmount}</td>
								<td>${charge}</td>
								<td>${avg}</td>
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