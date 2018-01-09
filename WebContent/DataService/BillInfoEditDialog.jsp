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
	<table width="100%" border="0" cellspacing="0" cellpadding="3"
		class="blockTable">
		<tr>
			<td valign="middle" class="blockTd"><img
				src="../Icons/icon018a6.gif" width="20" height="20" /> 订单信息</td>
		</tr>
		<tr>
			<td><z:init
					method="com.sinosoft.cms.dataservice.BillInfoManage.initDialog">
					<form id="form2">
						<table width="100%" cellpadding="2" cellspacing="0" align="center">
							<tr>

								<td height="25" align="right" class="tdgrey1">发票类型：</td>
								<td height="25"><input name="BillTypeName" type="text"
									value="${BillTypeName}" id="BillTypeName" disabled /></td>
								<td height="25" align="right" class="tdgrey1">发票抬头：</td>
								<td height="25"><input type="text" id="BillTitle"
									name="BillTitle" value="${BillTitle}" disabled /></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">发票金额：</td>
								<td height="25"><input name="BillAmount" type="text"
									value="${BillAmount}" id="BillAmount" disabled /></td>
								<td height="25" align="right" class="tdgrey1">收件人姓名：</td>
								<td height="25"><input name="DeliverName" type="text"
									value="${DeliverName}" id="DeliverName" disabled /></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">收件人电话：</td>
								<td height="25"><input name="DeliverTel" type="text"
									value="${DeliverTel}" id="DeliverTel" disabled /></td>
								<td height="25" align="right" class="tdgrey1">邮编：</td>
								<td height="25"><input name="DeliverZipCode" type="text"
									value="${DeliverZipCode}" id="DeliverZipCode" disabled /></td>

							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">邮寄地址：</td>
								<td height="25" colspan="3"><input name="DeliverAddr" style="width:510px"
									type="text" value="${DeliverAddr}" id="DeliverAddr" disabled />
								</td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">物流公司：</td>
								<td height="25"><z:select id="Logistics" name="Logistics"
										style="width:150px" listWidth="150" verify="物流公司|NotNull"> ${Logistics}</z:select></td>
								<td height="25" align="right" class="tdgrey1">运单号：</td>
								<td height="25"><input name="WayBillNo" type="text"
									value="${WayBillNo}" style="width: 150px" class="inputText"
									id="WayBillNo" verify="运单号|NotNull"/></td>
							</tr>

						</table>
						<input name="id" type="hidden" value="${id}" id="id" />
					</form>
				</z:init></td>
		</tr>
		<tr>
			<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
							<z:datagrid id="dg1"
								method="com.sinosoft.cms.dataservice.BillInfoManage.billRelateOrder"
								size="10" scroll="true">
								<table width="100%" cellpadding="2" cellspacing="0"
									class="dataTable"
									style="text-align: center; table-layout: fixed;"
									fixedHeight="250px">
									<tr ztype="head" class="dataTableHead">
										<td width="30" ztype="RowNo" style="text-align: center;"><strong>序号</strong></td>
										<td width="120" style="text-align:center;"><b>订单号</b></td>
										<td width="180" style="text-align:center;"><b>产品名称</b></td>
										<td width="60" style="text-align:center;"><b>订单状态</b></td>
										<td width="60" style="text-align:center;"><b>订单保费</b></td>
										<td width="80" style="text-align:center;"><b>订单支付金额</b></td>
										<td width="150" style="text-align:center;"><b>保单号</b></td>
									</tr>
									<tr style="background-color: #F9FBFC">
										<td width="3%">&nbsp;</td>
										<td style="text-align: left;" title="${orderSn}">${orderSn}</td>
										<td style="text-align: left;" title="${productName}">${productName}</td>
										<td style="text-align: left;" title="${orderStatusName}">${orderStatusName}</td>
										<td style="text-align: left;" title="${totalAmount}">${totalAmount}</td>
										<td style="text-align: left;" title="${payPrice}">${payPrice}</td>
										<td style="text-align: left;" title="${policyNo}">${policyNo}</td>
									</tr>
									<tr ztype="pagebar">
										<td height="25" colspan="6">${PageBar}</td>
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