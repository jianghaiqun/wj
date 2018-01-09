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
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
var KID = "<%=KID%>";
//订单查询
function doSearch(){ 
	var sd = $V("createDate");
	var ed = $V("endCreateDate");
	var sc = $V("screateDate");
	var se = $V("sendCreateDate");
	var tT = $V("tbTradeSeriNo");
	var od = $V("orderSn");
	var pd = $V("productName");
	var os = $V("OrderStatus");
	var an = $V("applicantName");
	var io = $V("idNO");
	var ch = $V("channelSn");

	var all=sd+ed+tT+od+pd+os+io+sc+se+ch+an;
	if(all == null || all == ''){
		Dialog.alert("查询条件不能为空,至少要有一个查询条件！");
		return;
	}
	if(ed < sd || se < sc){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
	DataGrid.setParam("dg1","screateDate",$V("screateDate"));
	DataGrid.setParam("dg1","sendCreateDate",$V("sendCreateDate"));
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","tbTradeSeriNo",$V("tbTradeSeriNo"));
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","idNO",$V("idNO"));
	DataGrid.setParam("dg1","OrderStatus",$V("OrderStatus"));
	DataGrid.setParam("dg1","applicantName",$V("applicantName"));
	DataGrid.setParam("dg1","channelSn",$V("channelSn"));
	DataGrid.loadData("dg1");
}

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 订单信息</td>
		          </tr>
				<tr>
				<td>
				<z:init method="com.sinosoft.platform.TBSDInsureds.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>渠道订单号：</td>
							<td> <input name="tbTradeSeriNo" type="text" id="tbTradeSeriNo" value="" style="width:100px"></td>
							<td>订单号：</td>
							<td> <input name="orderSn" type="text" id="orderSn" value="" style="width:100px"></td>
							<td>产品名称：</td>
							<td> <input name="productName" type="text" id="productName" value="" style="width:100px"></td>
							<td>被保人：</td>
							<td> <input name="applicantName" type="text" id="applicantName" value="" style="width:100px" /></td>
							<td>证件号码：</td>
							<td> <input name="idNO" type="text" id="idNO" value="" style="width:100px" /></td>
							<td>订单状态</td>
							<td><z:select style="width:100px;" name="OrderStatus" id="OrderStatus">${OrderStatus}</z:select></td>
						</tr>
						<tr>
							<td>订单支付时间 从：</td>
		                	<td> <input name="createDate" type="text" id="createDate" value="${createDate}" style="width:100px" ztype="date"></td>
							<td>至：</td>
							<td><input name="endCreateDate" type="text" id="endCreateDate" value="${endCreateDate}"style="width:100px" ztype="date"></td>
							<td>订单生效时间 从：</td>
		                	<td> <input name="screateDate" type="text" id="screateDate" value="${createDate}" style="width:100px" ztype="date"></td>
							<td>至：</td>
							<td><input name="sendCreateDate" type="text" id="sendCreateDate" value="${endCreateDate}"style="width:100px" ztype="date"></td>
							<td>订单渠道</td>
							<td>
								<z:select style="width:100px;" name="channelSn" id="channelSn">${channelSn}</z:select>
							</td>
						</tr>
						<tr>
						   <td  colspan="8" nowrap>
			            	   
						<input type="button" value="查询" onclick="doSearch();">
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
					<z:datagrid id="dg1" method="com.sinosoft.platform.TBSDInsureds.dg1DataBind" size="20" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="140" style="text-align:center;"><b>订单编号</b></td>
						        <td width="140" style="text-align:center;"><b>渠道订单号</b></td>
						        <td width="100" style="text-align:center;"><b>支付时间</b></td>
						        <td width="100" style="text-align:center;"><b>生效日期</b></td>
						        <td width="140" style="text-align:center;"><b>产品名称</b></td>
						        <td width="80" style="text-align:center;"><b>保费</b></td>
						        <td width="80" style="text-align:center;"><b>已付费</b></td>
								<td width="80" style="text-align:center;"><b>保单状态</b></td>
								<td width="80" style="text-align:center;"><b>被保险人姓名</b></td>
								<td width="145" style="text-align:center;"><b>被保险人证件号码</b></td>
								<td width="80" style="text-align:center;"><b>渠道</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td style="text-align:center;">&nbsp;</td>
								<td style="text-align:center;" title="${orderSn}"><a  style="cursor: hand;" onClick="showOrderDetail('${OrderSn}')">${orderSn}</a></td>
								<td title="${tbTradeSeriNo}">${tbTradeSeriNo}</td>
								<td title="${receiveDate}">${receiveDate}</td>
								<td style="text-align:center;" title="${svalidate}">${svalidate}</td>
								<td style="text-align:center;" title="${ProductName}">${ProductName}</td>
								<td style="text-align:center;" title="${timePrem}">${timePrem}</td>
								<td style="text-align:center;" title="${PayPrice}">${PayPrice}</td>
								<td style="text-align:center;" title="${appstatus}">${appstatus}</td>
								<td title="${recognizeeName}">${recognizeeName}</td>
								<td title="${recognizeeIdentityId}">${recognizeeIdentityId}</td>
								<td style="text-align:right;" title="${channelsn}">${channelsn}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
								<input name="id" type="hidden" id="id" value="${id}" style="width:150px">
								<input name="insuredid" type="hidden" id="insuredid" value="${insuredid}" style="width:150px">
								<input name="OrderSn" type="hidden" id="OrderSn" value="${OrderSn}" style="width:150px">
								<input name="insuranceCompany" type="hidden" id="insuranceCompany" value="${insuranceCompany}" />
								
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
</body>
</html>
