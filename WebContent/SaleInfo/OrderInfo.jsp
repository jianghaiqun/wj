<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.OrderInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>日报信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript">
function statStaff(cancelFlag){

	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","allDate","0");
	DataGrid.setParam("dg1","Markingkg",$V("Markingkg"));
	DataGrid.setParam("dg1","cancelFlag",cancelFlag);
	DataGrid.setParam("dg1","contant",$NV("contant"));
	DataGrid.setParam("dg1","sdChannel",$V("sdChannel"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function statAll(cancelFlag){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1","allDate","1");
	DataGrid.setParam("dg1","Markingkg",$V("Markingkg"));
	DataGrid.setParam("dg1","cancelFlag",cancelFlag);
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
//显示订单详细信息
function showOrderDetail(orderSn,KID,channelSn){
    /* 	var arr = $NV("contant");
	for ( var i = 0; i < arr.length; i++) {
		var id = arr[i];
		if(orderSn.startWith("2015")){
			Dialog.alert("暂不支持该渠道订单的详情查询！");
			return;
		}
	} */
    var WjRoleFlag = $V("WjRoleFlag");
    var channelCode = $V("channelCode");
    var channelCodeArray = channelCode.split(",");

    if(WjRoleFlag!="1" && channelCodeArray.indexOf(channelSn) != "-1" ){
        Dialog.alert("暂不支持该渠道订单的详情查询！");
        return;
    }

	if(channelSn.startWith("b2b")){
		Dialog.alert("暂不支持该渠道订单的详情查询！");
		return;
	}
	window.open('../shop/order_config_new!linkOrderDetails.action?orderSn='+orderSn+"&KID="+KID,'_blank');
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
						<span style="float: left;width:700px">
						统计时间：从
						<input value="${yesterday}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="${yesterday}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						订单所属险种：
						<z:select id="Markingkg">${Markingkg}</z:select>
						SD渠道：
						<z:select id="sdChannel">${sdChannel}</z:select>
						</span>
						<z:tbutton onClick="statStaff(0)"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<%-- <z:tbutton onClick="statAll(0)"> <img src="../Icons/icon031a15.gif" />全部统计</z:tbutton> --%>
						<z:tbutton onClick="statStaff(1)"> <img src="../Icons/icon031a1.gif" />撤单统计</z:tbutton>
						<z:tbutton onClick="statStaff(2)"> <img src="../Icons/icon031a1.gif" />对冲统计</z:tbutton>
						<%-- <z:tbutton onClick="statAll(1)"> <img src="../Icons/icon031a15.gif" />全部撤单统计</z:tbutton> --%>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.OrderInfo.dg1DataBind"
					 lazy="true">
						<table cellpadding="2" cellspacing="0" style="width:1500px;  overflow: auto;"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo"><b>序号</b></td>
								<td width="13%" ><b>保单号</b></td>
								<td width="11%">订单号</td>
								<td width="16%">产品</td>
								<td width="5%" ><b>保费</b></td>
								<td width="7%" ><b>手续费</b></td>
								<td width="11%" ><b>支付日期</b></td>
								<td width="8%" ><b>生效日期</b></td>
								<td width="11%" ><b>撤单日期</b></td>
								<td width="5%" ><b>所属险种</b></td>
								<td width="5%" ><b>所属公司</b></td>
								<td width="5%" ><b>被保人证件号</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td style="text-align:left;">${policyNo}</td>
								<td><a  style="cursor: hand;" onClick="showOrderDetail('${ordersn}','${KID}','${ChannelSn}')">${ordersn}</a></td>
								<td style="text-align:left;">${productName}</td>
								<td style="text-align:right;">${totalAmount}</td>
								<td style="text-align:right;">${charge}</td>
								<td>${modifyDate}</td>
								<td>${startDate}</td>
								<td>${cancelDate}</td>
								<td>${ProductGenera}</td>
								<td>${insureName}</td>
								<td>${recognizeeIdentityId}</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
		<input type="hidden" id="WjRoleFlag" name="WjRoleFlag" value="${WjRoleFlag}"/>
		<input type="hidden" id="channelCode" name="channelCode" value="${channelCode}"/></td>
	</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</body>
</html>
</z:init>