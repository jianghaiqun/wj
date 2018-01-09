<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员回购统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	//DataGrid.setParam("dg1","oldFlag","1");
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

//显示用户列表
function showMemberDetail(type){
	if(type=='2'){
		Dialog.alert("'回购率'不支持会员信息查询！");
		return;
	}
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var contant = $NV("contant");
	var diag = new Dialog("Diag1");
	diag.Width = 1200; 
	diag.Height = 580; 
	if ('0' == type) {
		diag.Title = "回购信息列表查看";
	} else {
		diag.Title = "总数信息列表查看";
	}
	diag.URL = "MemberReport/BuyBackMemberDialog.jsp?type="+type+"&startDate="+startDate+"&endDate="+endDate+"&contant="+contant;
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
function showOrderDetail(type){
	if(type=='2'){
		Dialog.alert("'回购率'不支持订单信息查询！");
		return;
	}
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var contant = $NV("contant");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 580;
	if ('0' == type) {
		diag.Title = "回购信息列表查看";
	} else {
		diag.Title = "总数信息列表查看";
	}
	
	diag.URL = "MemberReport/BuyBackOrderDialog.jsp?type="+type+"&startDate="+startDate+"&endDate="+endDate+"&contant="+contant;
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
function showRiskTypeDetail(type){
	if(type=='2'){
		Dialog.alert("'回购率'不支持订单信息查询！");
		return;
	}
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var contant = $NV("contant");
	var diag = new Dialog("Diag1");
	diag.Width = 1300;
	diag.Height = 580;
	if ('0' == type) {
		diag.Title = "回购信息列表查看";
	} else {
		diag.Title = "总数信息列表查看";
	}
	
	diag.URL = "MemberReport/BuyBackRiskTypeDialog.jsp?type="+type+"&startDate="+startDate+"&endDate="+endDate+"&contant="+contant;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看保单信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}

//显示回购的会员来源合计
function showSourceDetail(type){
	if(type=='2'){
		Dialog.alert("'回购率'不支持订单信息查询！");
		return;
	}
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var contant = $NV("contant");
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 250;
	if ('0' == type) {
		diag.Title = "回购信息列表查看";
	} else {
		diag.Title = "总数信息列表查看";
	}
	
	diag.URL = "MemberReport/BuyBackSourceDialog.jsp?type="+type+"&startDate="+startDate+"&endDate="+endDate+"&contant="+contant;
	diag.onLoad = function() {
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员来源信息";
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员回购统计</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.memberreport.BuyBackReport.init">
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
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.BuyBackReport.dg1DataBind" lazy="true">
						<table width="80%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="10%">类别</td>
								<td width="10%">会员数</td>
								<td width="10%">订单数</td>
								<td width="15%">保费</td>
								<td width="10%">保单</td>
								<td width="10%">会员来源</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="left">${_Columns_0}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showMemberDetail('${_Columns_6}')">${_Columns_1}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: pointer;" onClick="showOrderDetail('${_Columns_6}')">${_Columns_2}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right">${_Columns_3}</td>
								<td align="right"><a style="cursor: pointer;" onClick="showRiskTypeDetail('${_Columns_6}')">${_Columns_4}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: pointer;" onClick="showSourceDetail('${_Columns_6}')">${_Columns_5}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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