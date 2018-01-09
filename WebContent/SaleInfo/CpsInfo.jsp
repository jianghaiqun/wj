<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.CPSInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>CPS订单查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript">
function statStaff(){
	var sd = $V("startDate");
	var ed = $V("endDate")
	var svd = $V("svaliDate");
	var evd = $V("evaliDate");
	var all=sd+ed+svd+evd;
	if(all == null || all == ''){
		Dialog.alert("统计时间和生效日期查询条件,至少必填一个！");
		return;
	}
	if(ed < sd){ 
	    Dialog.alert("统计时间的结束日期不能小于开始日期！");
		return;
	}
	
	if(evd < svd){
	    Dialog.alert("生效日期的结束日期不能小于开始日期！");
		return;
	}
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","svaliDate",$V("svaliDate"));
	DataGrid.setParam("dg1","evaliDate",$V("evaliDate"));
	DataGrid.setParam("dg1","OrderStatus",$V("OrderStatus"));
	DataGrid.setParam("dg1","orderOriginFlag",$NV("contant"));
	DataGrid.setParam("dg1","allDate","0");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function statAll(){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1","svaliDate","");
	DataGrid.setParam("dg1","evaliDate","");
	DataGrid.setParam("dg1","allDate","1");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
//显示订单详细信息
function showOrderDetail(orderSn,KID){
	window.open('../shop/order_config_new!linkOrderDetails.action?orderSn='+orderSn+"&KID="+KID,'_blank');
}
</script>
</head>
<body>
	<div id="StaffStat">
	<form id="cps_1">
	<input name="ParentID" type="hidden" id="ParentID" value="cps" style="width:150px">
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
						<span style="float: left;width:1000px">
						统计时间：从
						<input value="" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						生效日期：从
						<input value="" type="text" id="svaliDate" 
							name="svaliDate" ztype="Date"  class="inputText" size="14" >
						到<input value="" type="text" id="evaliDate" 
							name="evaliDate" ztype="Date"  class="inputText" size="14" >
						订单状态：
						<z:select style="width:100px;" name="OrderStatus" id="OrderStatus">${OrderStatus}</z:select>	
						<%-- 订单来源：
						<z:select id="orderOriginFlag" name="orderOriginFlag" style="width:100px">${orderOriginFlag}</z:select>	 --%>		
						</span>
						<z:tbutton onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<%-- <z:tbutton onClick="statAll()"> <img src="../Icons/icon031a15.gif" />全部统计</z:tbutton> --%>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.CPSInfo.dg1DataBind" 
					autoFill="true">
						<table width="100%" cellpadding="2" cellspacing="0" 
							class="dataTable" lazy="false" style="table-layout : fixed" fixedHeight="363px"  afterdrag="afterRowDragEnd">
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo"><b>序号</b></td>
								<td width="120">订单号</td>
								<td width="250">产品</td>
								<td width="60" ><b>保费</b></td>
								<td width="60" ><b>支付金额</b></td>
								<td width="60" ><b>手续费</b></td>
								<td width="50" ><b>状态</b></td>
								<td width="130" ><b>订单日期</b></td>
								<td width="130" ><b>支付日期</b></td>
								<td width="130" ><b>生效日期</b></td>
								<td width="100" ><b>订单来源</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td><a  style="cursor: hand;" onClick="showOrderDetail('${ordersn}','${KID}')">${ordersn}</a></td>
								<td style="text-align:left;">${productName}</td>
								<td style="text-align:right;">${totalAmount}</td> 
								<td style="text-align:right;">${payamount}</td>
								<td style="text-align:right;">${charge}</td>    
								<td>${orderstatusname}</td>   
								<td>${orderdate}</td>
								<td>${paydate}</td>
								<td>${svaliDate}</td>
								<td style="text-align:right;">${ordersOrigin}</td>
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
		</form>
</body>
</html>
</z:init>