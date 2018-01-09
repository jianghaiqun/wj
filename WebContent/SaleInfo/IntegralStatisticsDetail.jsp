<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script>
function dosearch() { 
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.setParam("dg2","month",$V("month"));
	DataGrid.setParam("dg2","source",$V("source"));
	DataGrid.loadData("dg2");
}
</script>
</head>
<body class="dialogBody">
<form id="form2">
<z:init method="com.wangjin.coupon.IntegralStatistics.initDialog">
<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				<tr>
					<td style="padding:8px 10px;">
					</td>
				</tr>
				<tr>
						<td>
							<table cellspacing="0" cellpadding="3">
								<tr>
									<td>积分来源：</td>
									<td><z:select name="source" id="source" style="width:150px;" >${source}</z:select></td>
									<td><z:tbutton onClick="dosearch()" id="dosearch">
											<img src="../Icons/icon021a4.gif" width="20" height="20" />查询
		            	   </z:tbutton>
		            	   <input type="hidden" id="startDate" name="startDate" value="${startDate}" />
		            	   <input type="hidden" id="endDate" name="endDate" value="${endDate}" />
		            	   <input type="hidden" id="month" name="month" value="${month}" />
									</td>
								</tr>
							</table></td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.wangjin.coupon.IntegralStatistics.dg2DataBind" size="10" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0" 
						style="text-align: center; table-layout: fixed;" 
							class="dataTable" fixedHeight="700px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="100" style="text-align:center;"><b>统计时间</b></td>
								<td width="140" style="text-align:center;"><b>会员用户名</b></td>
								<td width="120" style="text-align:center;"><b>商家订单号/订单号</b></td>
								<td width="90" style="text-align:center;"><b>总金额</b></td>
								<td width="90" style="text-align:center;"><b>交易金额</b></td>
								<td width="90" style="text-align:center;"><b>积分来源</b></td>
								<td width="90" style="text-align:center;"><b>积分数量</b></td>
								<td width="90" style="text-align:center;"><b>积分价值</b></td>
							</tr>
							<tr >
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td title="${tjTime}">${tjTime}</td>
								<td title="${mem}">${mem}</td>
								<td title="${businessid}">${businessid}</td>
								<td title="${totalAmount}">${totalAmount}</td>
								<td title="${payPrice}">${payPrice}</td>
								<td title="${source}">${source}</td>
								<td title="${point}">${point}</td>
								<td title="${money}">${money}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="10">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
</z:init>
</form>
</body>
</html>