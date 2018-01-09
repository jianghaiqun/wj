<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>询单转化报表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function Search(){
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.setParam("dg2","orderChannel",$V("orderChannel"));
	DataGrid.setParam("dg2","conversionType",$V("conversionType"));
	DataGrid.setParam("dg2","orderStatus",$V("orderStatus"));
	DataGrid.setParam("dg2","appStatus",$V("appStatus"));
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

</script>
</head>
<body>
	<z:init method="com.sinosoft.cms.dataservice.CustomerServiceManage.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:320px">
						支付时间：从
						<input type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						</span>
						<span>&nbsp;订单渠道: <input name="orderChannel" type="text" id="orderChannel" style="width:110px"></span>
						<span>&nbsp;转换渠道: <input name="conversionType" type="text" id="conversionType" style="width:110px"></span>
						<span>&nbsp;订单状态: <z:select style="width:100px;" name="orderStatus" id="orderStatus">${OrderStatus}</z:select></span>
						<span>&nbsp;保单状态: <z:select style="width:100px;" name="appStatus" id="appStatus">${AppStatus}</z:select></span>
						<input type="button" value="查询" onClick="Search()" id="Submitbutton">
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.CustomerServiceManage.dg2DataBind" page="false" lazy="true" scroll="true" >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><b>序号</b></td>
								<td width="7%">客服人员</td>
								<td width="8%">实际支付保费</td>
								<td width="8%">原价保费</td>
								<td width="5%">订单状态</td>
								<td width="12%">保单状态</td>
								<td width="8%">订单付款时间</td>
								<td width="6%">订单渠道</td>
								<td width="10%">转化渠道</td>
								<td width="12%">订单编号</td>
								<td width="12%">保单编码</td>
								<td width="8%">会员ID</td>
								<td width="12%">保全记录</td>
							</tr>
							<tr>
								<td align="center">&nbsp;</td>
								<td title="${EmployeeName}">${EmployeeName}</td>
								<td title="${payPrice}">${payPrice}</td>
								<td title="${productPrice}">${productPrice}</td>
								<td title="${orderstatusname}">${orderstatusname}</td>
								<td title="${appstatusname}">${appstatusname}</td>
								<td title="${insureDate}">${insureDate}</td>
								<td title="${channelsn}">${channelsn}</td>
								<td title="${ConversionType}">${ConversionType}</td>
								<td title="${ordersn}">${ordersn}</td>
								<td title="${policyNo}">${policyNo}</td>
								<td title="${MID}">${MID}</td>
								<td title="${remark}">${remark}</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</z:init>
</body>

</html>
