<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
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
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.wangjin.coupon.IntegralRollBack.dg2DataBind" size="10">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="700px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" ><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="120" ><b>统计时间</b></td>
								<td width="160" ><b>商家订单号</b></td>
								<td width="120" ><b>订单号</b></td>
								<td width="120" ><b>冻结积分回冲数量</b></td>
								<td width="120" ><b>冻结积分回冲额度</b></td>
								
							</tr>
							<tr >
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td title="${tjTime}">${tjTime}</td>
								<td title="${paySn}">${paySn}</td>
								<td title="${orderSn}">${orderSn}</td>
								<td title="${freezeNum}">${freezeNum}</td>
								<td title="${freezeMoney}">${freezeMoney}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="7">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
								<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg3" method="com.wangjin.coupon.IntegralRollBack.dg3DataBind" size="10">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="700px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" ><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="120" ><b>统计时间</b></td>
								<td width="160" ><b>商家订单号</b></td>
								<td width="120" ><b>积分抵值回冲数量</b></td>
								<td width="120" ><b>积分抵值回冲额度</b></td>
								
							</tr>
							<tr >
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td title="${tjTime}">${tjTime}</td>
								<td title="${paySn}">${paySn}</td>
								<td title="${offsetPoint}">${offsetPoint}</td>
								<td title="${orderIntegral}">${orderIntegral}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="6">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
</z:init>
</form>
</body>
</html>