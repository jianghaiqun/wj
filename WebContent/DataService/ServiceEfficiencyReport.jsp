<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>客服效率汇总</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function Search(){
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.setParam("dg2","orderChannel",$V("orderChannel"));
	DataGrid.setParam("dg2","conversionType",$V("conversionType"));
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
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
						<input type="button" value="查询" onClick="Search()" id="Submitbutton">
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.CustomerServiceManage.dg3DataBind" page="false" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><b>序号</b></td>
								<td width="10%">客服名</td>
								<td width="20%">总保费(不包含撤单)</td>
								<td width="8%" ><b>保单个数</b></td>
								<td width="13%">&nbsp;</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td>${EmployeeName}</td>
								<td>${payPriceAll}</td>
								<td>${COUNT}</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		  
	</table>
</body>

</html>
