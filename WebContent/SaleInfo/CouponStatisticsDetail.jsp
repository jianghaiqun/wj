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
	DataGrid.setParam("dg2","status",$V("status"));
	DataGrid.loadData("dg2");
}
</script>
</head>
<body class="dialogBody">
<form id="form2">
<z:init>
<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				<tr>
					<td style="padding:8px 10px;">
					</td>
				</tr>
				<tr>
						<td>
							<table cellspacing="0" cellpadding="3">
								<tr>
									<td>使用状态：</td>
									<td><z:select style="width:100px;"><select name="status" id="status" > 
							<option value=""></option> 
		                 	<option value="1">已使用 </option>
		                 	<option value="2">已发放</option>
		                 	<option value="5">已过期 </option>
		                	</select></z:select></td>
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
					<z:datagrid id="dg2" method="com.wangjin.coupon.CouponStatistics.dg2DataBind" size="10" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0" 
						style="text-align: center; table-layout: fixed;" 
							class="dataTable" fixedHeight="700px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="100" style="text-align:center;"><b>统计时间</b></td>
								<td width="140" style="text-align:center;"><b>优惠券码</b></td>
								<td width="50" style="text-align:center;"><b>申请人</b></td>
								<td width="50" style="text-align:center;"><b>发放人</b></td>
								<td width="65" style="text-align:center;"><b>优惠券面值</b></td>
								<td width="55" style="text-align:center;"><b>使用状态</b></td>
								<td width="120" style="text-align:center;"><b>商家订单号</b></td>
								<td width="95" style="text-align:center;"><b>商家订单总金额</b></td>
								<td width="105" style="text-align:center;"><b>商家订单交易金额</b></td>
								<td width="55" style="text-align:center;"><b>优惠额度</b></td>
							</tr>
							<tr >
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td title="${tjTime}">${tjTime}</td>
								<td title="${couponSn}">${couponSn}</td>
								<td title="${createUser}">${createUser}</td>
								<td title="${realProvideUser}">${realProvideUser}</td>
								<td title="${parValue}">${parValue}</td>
								<td title="${statusName}">${statusName}</td>
								<td title="${PaySn}">${PaySn}</td>
								<td title="${TotalAmount}">${TotalAmount}</td>
								<td title="${TradeAmount}">${TradeAmount}</td>
								<td title="${Sumcoupon}">${Sumcoupon}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="12">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
</z:init>
</form>
</body>
</html>