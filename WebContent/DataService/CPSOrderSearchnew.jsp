<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>订单信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//订单查询
function doSearch(){ 
	var obj=document.getElementsByName('checkItem');
	var s=''; 
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) s+=obj[i].value+',';
	} 
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","channel",s);
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","Markingkg",$V("Markingkg"));
	DataGrid.setParam("dg1","IntermediaryMember",$NV("IntermediaryMember"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

function statStaff(){
	var obj=document.getElementsByName('checkItem');
	var s=''; 
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) s+=obj[i].value+',';
	} 
	var dc = new DataCollection();
	dc.add("startDate", $V("startDate"));
	dc.add("channel", s);
	dc.add("endDate", $V("endDate"));
	dc.add("Markingkg", $V("Markingkg"));
	dc.add("IntermediaryMember", $V("IntermediaryMember"));
	Server.sendRequest("com.wangjin.cms.orders.CPSQueryOrders.orderCountNew",
			dc, function() {
				var response = Server.getResponse();
				var strs= new Array(); //定义一数组 
				strs=response.Message.split("&");
				jQuery("#totalAll").text(strs[0]);
				jQuery("#chargeAll").text(strs[1]);
			});
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" />分销订单信息</td>
		          </tr>
				<tr>
				<td>
				<z:init method="com.wangjin.cms.orders.CPSQueryOrders.init">
				<input type="hidden" id="mediamanager" name="mediamanager" value="${mediamanager}">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>业务平台：</td>
							<td colspan="8"> 
								<input id="b2b_radio" class="inputRadio" type="radio" value="b2b" name="checkItem" checked>
								<label for="b2b_radio">美行保</label>
								<input id="cps_radio" class="inputRadio" type="radio" value="cps" name="checkItem" >
								<label for="cps_radio">网站联盟</label>
								<!-- <input id="agent_radio" class="inputRadio" type="checkbox" value="agent" name="checkItem" disabled="disabled">
								<label for="agent_radio">网金所</label> -->
							</td>
							<td>&nbsp;&nbsp;保费&nbsp;&nbsp;&nbsp;总额：<em id="totalAll">0.00</em></td>
						</tr>
						<tr><td></td></tr>
						<tr>
							<td>媒介经理：</td>
							<td> <input name="IntermediaryMember" type="text" id="IntermediaryMember" value="" style="width:100px" />
							订单所属险种：
							<z:select id="Markingkg">${Markingkg}</z:select></td>
		                	<td>统计时间 从：</td>
		                	<td> <input name="startDate" type="text" id="startDate" value="${yesterday}" style="width:100px" ztype="date"></td>
							<td>至：</td>
							<td><input name="endDate" type="text" id="endDate" value="${yesterday}"style="width:100px" ztype="date"></td>
							<td>
			            	   <z:tbutton onClick="doSearch();" id="doSearch">
												<img src="../Icons/icon021a4.gif" width="20" height="20" />查询
		            	   		</z:tbutton>
		            	   	</td>
		            	   	<td>|&nbsp;</td>
		            	   	<td>	
								<z:tbutton onClick="statStaff();" id="statStaff">
												<img src="../Icons/icon021a4.gif" width="20" height="20" />统计
		            	   		</z:tbutton>
		            	   	</td>
		            	   	<td>
		            	   		&nbsp;&nbsp;手续费总额：<em id="chargeAll">0.00</em>
			            	</td>
						</tr>
		             </table>
		             </z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 5px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.cms.orders.CPSQueryOrders.orderInqueryNew" size="20" scroll="true" lazy="true">
						<table width="130%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="20px" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15px" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="110px" style="text-align:center;"><b>保单号</b></td>
						        <td width="100px" style="text-align:center;"><b>订单号</b></td>
						        <td width="150px" style="text-align:center;"><b>产品</b></td>
						        <td width="50px" style="text-align:center;"><b>保费</b></td>
						        <td width="50px" style="text-align:center;"><b>手续费</b></td>
						        <td width="80px" style="text-align:center;"><b>支付日期</b></td>
						        <td width="80px" style="text-align:center;"><b>生效日期</b></td>
						        <td width="50px" style="text-align:center;"><b>所属险种</b></td>
						        <td width="50px" style="text-align:center;"><b>所属公司</b></td>
						        <td width="40px" style="text-align:center;"><b>业务平台</b></td>
						        <td width="40px" style="text-align:center;"><b>媒介经理</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td title="${policyno}">${policyno}</td>
								<td title="${ordersn}">${ordersn}</td>
								<td title="${productname}">${productname}</td>
								<td title="${totalamount}">${totalamount}</td>
								<td title="${charge}">${charge}</td>
								<td title="${paydate}">${paydate}</td>
								<td title="${svaliDate}">${svaliDate}</td>
								<td title="${ProductGenera}">${ProductGenera}</td>
								<td title="${InsureName}">${InsureName}</td>
								<td title="${channelSn}">${channelSn}</td>
								<td title="${IntermediaryMember}">${IntermediaryMember}</td>
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
			</td>
		</tr>
	</table>
	<script type="text/javascript">
	var mediamanager = document.getElementById("mediamanager").value;
	if("Y" == mediamanager){
		document.getElementById("IntermediaryMember").disabled = true;
	}
	
	</script>
</body>
</html>
