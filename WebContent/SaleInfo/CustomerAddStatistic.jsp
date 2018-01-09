<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.TotalInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>客户增量统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript">
function statStaff(){
	DataGrid.setParam("dg5","startDate",$V("startDate"));
	DataGrid.setParam("dg5","endDate",$V("endDate"));
	DataGrid.setParam("dg5","From",$V("From"));
	DataGrid.setParam("dg5","Wedo",$V("Wedo"));
	DataGrid.setParam("dg5","contant",$NV("contant"));
	DataGrid.setParam("dg5",Constant.PageIndex,0);
	DataGrid.loadData("dg5");
}
function exportStaff(){
	var arr = $NV("contant");
	for ( var i = 0; i < arr.length; i++) {
		var id = arr[i];
		if(id.startWith("b2b")){
			Dialog.alert("暂不支持该渠道订单的详情查询！");
			return;
		}
	}
	DataGrid.toExcel("dg5",1);
}
//显示当天注册的用户列表
function showRegistDetail(channelSn){
	var startDate = $V("startDate");
	var endDate = $V("endDate");
	var From = $V("From");
	var Wedo = $V("Wedo");
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 350;
	diag.Title = "投/被保人列表查看";
	diag.URL = "SaleInfo/CustomerAddDetailDialog.jsp?contant="+channelSn+"&startDate="+startDate+"&endDate="+endDate+"&From="+From+"&Wedo="+Wedo;
		diag.onLoad = function() {
			$DW.init(startDate, endDate, From, Wedo, channelSn);
		};
		diag.ShowMessageRow = true;
		diag.MessageTitle = "查看投/被保人信息";
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
						<table>
							<tr>
								<td>
									<span style="float: left;width:500px">
									统计时间：从
									<input value="${yesterday}" type="text" id="startDate" 
										name="startDate" ztype="Date"  class="inputText" size="14" >
									到<input value="${today}" type="text" id="endDate" 
										name="endDate" ztype="Date"  class="inputText" size="14" >
									</span>
								</td>
								<td>
									对象选择：
									<z:select name="From" id="From" style="width:100px">
										<span value="1">投保人</span>
										<span value="2">被保人</span>
										<span value="3">投/被保人</span>
									</z:select>
									统计维度：
									<z:select name="Wedo" id="Wedo" style="width:100px">
										<span value="id">证件号</span>
										<span value="phone">手机号</span>
										<span value="email">email</span>
									</z:select>
								</td>
								<td>
									<span>
										<z:tbutton onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
									</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg5" method="com.sinosoft.cms.dataservice.CustomerAddStatisticNew.dg1DataBind" lazy="true">
						<table width="80%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="10%">渠道</td>
								<td width="10%">客户新增数量</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="left">${channalName}</td>
								<td align="right"><a style="cursor: hand;" href="javascript:void(0);" onClick="showRegistDetail('${channelsn}')">${count}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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