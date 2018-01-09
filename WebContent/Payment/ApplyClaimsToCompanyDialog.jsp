<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	if ($V("kindOfLoss") == 'KOL170') {
		$("claReaTr").show();
	}
});

function changeKindOfLoss(){
	if ($V("kindOfLoss") == 'KOL170') {
		$("claReaTr").show();
	} else {
		$S("claimReason", "");
		$S("flightNo", "");
		$S("flightTime", "");
		$("claReaTr").hide();
	}
}

</script>
</head>
<body class="dialogBody">
	<table width="100%" border="0" cellspacing="0" cellpadding="1"
		class="blockTable">
		<tr>
			<td valign="middle" class="blockTd"><img
				src="../Icons/icon018a6.gif" width="20" height="20" />理赔申请信息</td>
		</tr>
		<tr>
			<td><z:init method="com.sinosoft.cms.payment.PaymentManage.initDialog1">
					<form id="form2">
						<table width="100%" cellpadding="0" cellspacing="0" align="center">
							<tr>

								<td height="25" align="right" class="tdgrey1" width="110px">理赔单号：</td>
								<td height="25"><input name="claimsNo" type="text"
									value="${claimsNo}" id="claimsNo" style="width: 120px" disabled /></td>
								
								<td height="25" align="right" class="tdgrey1" width="80px">订单号：</td>
								<td height="25"><input type="text" id="orderSn"
									name="orderSn" value="${orderSn}" disabled /></td>
								<td height="25" align="right" class="tdgrey1" width="100px">保单号：</td>
								<td height="25"><input name="policyNo" type="text"
									value="${policyNo}" id="policyNo" style="width: 150px" disabled /></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1" width="80px">理赔项目：</td>
								<td height="25"><input type="text" id="claimsItemsName"
									name="claimsItemsName" value="${claimsItemsName}" disabled /></td>
								<td height="25" align="right" class="tdgrey1">损失类型：</td>
								<td height="25">
								  <z:select name="kindOfLoss" id="kindOfLoss" style="width:120px;" value="${kindOfLoss}" onChange="changeKindOfLoss();" verify="损失类型|NotNull">${KindOfLoss1}</z:select>
								</td>
								<td height="25" align="right" class="tdgrey1">出险时间：</td>
								<td height="25">
								    <input name="happenTime" id="happenTime" value="${happenTime}" type="text" size="20" verify="出险时间|NotNull"/>
			       				</td>
								
							</tr>
							<tr>

								<td height="25" align="right" class="tdgrey1">出险地址：</td>
								<td height="25" colspan="5"><input name="happenAddress" type="text"
									value="${happenAddress}" id="happenAddress" style="width: 400px" verify="出险地址|NotNull"/></td>
							</tr>

							<tr id="claReaTr" style="display:none;">
								<td height="25" align="right" class="tdgrey1">保险公司理赔原因：</td>
								<td height="25">
									 <z:select name="claimReason" id="claimReason" style="width:100px;" value="${claimReason}" >${ClaimReason1}</z:select>
								</td>
								
			       				<td height="25" align="right" class="tdgrey1">航班号：</td>
								<td height="25">
								    <input name="flightNo" id="flightNo" value="${flightNo}" type="text" size="20" />
			       				</td>
			       				<td height="25" align="right" class="tdgrey1">计划起飞时间：</td>
								<td height="25">
								    <input name="flightTime" id="flightTime" value="${flightTime}" type="text" size="20" />
			       				</td>
							</tr>
						
						</table>
						<input name="id" type="hidden" value="${id}" id="id" />
					</form>
				</z:init></td>
		</tr>
		
	</table>
</body>
</html>