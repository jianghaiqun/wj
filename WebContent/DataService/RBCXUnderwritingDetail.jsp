<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	function checkAndDealAction() {
		var orderSn = document.getElementById("orderSn").value;
		var insuredSn = document.getElementById("insuredSn").value;
		var policyNo = document.getElementById("policyNo").value;
		var applyPolicyNo = document.getElementById("applyPolicyNo").value;
		var policyFile = document.getElementById("policyFile").value;
		var receiveDate = document.getElementById("receiveDate").value;
		var channel = document.getElementById("channel").value;
		
		if (policyNo.indexOf("PJBS") == -1) {
			Dialog.alert("航延险保单号格式不正确！");
			return false;
		}
		if (applyPolicyNo.indexOf("PEJQ") == -1) {
			Dialog.alert("航意险保单号格式不正确！");
			return false;
		}
		if (policyFile == "") {
			Dialog.alert("请上传电子保单！");
			return false;
		}
		
		var form2 = document.getElementById("form2"); 
		form2.setAttribute("action", "RBCXPolicyUpload.jsp?orderSn=" + orderSn + "&insuredSn=" + insuredSn + "&policyNo=" + policyNo 
				+ "&applyPolicyNo=" + applyPolicyNo + "&receiveDate=" + receiveDate + "&channel=" + channel);
		document.getElementById("button").setAttribute("disabled", "disabled");
		return true;
	}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<input id="orderSn" type="hidden" name="orderSn" value="<%=request.getParameter("orderSn")%>">
	<input id="insuredSn" type="hidden" name="insuredSn" value="<%=request.getParameter("insuredSn")%>">
	<input id="receiveDate" type="hidden" name="receiveDate" value="<%=request.getParameter("receiveDate")%>">
	<input id="channel" type="hidden" name="channel" value="<%=request.getParameter("channel")%>">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="20%" align="right" style="font-weight:bold;">人工承保说明：</td>
			<td width="80%" colspan="2" style="font-weight:bold;">
				请将航延、航意保单号填入相应区域并上传对应电子保单。 <br />
				请仔细核对保单号及电子保单的正确性，承保后电子保单将被发送给客户。<br />
				点击“确定”后，开始进行承保数据操作和电子保单发送，请等待页面响应，不要反复点击“确认”按钮。
			</td>
		</tr>
		<tr>
			<td align="right">订单号：</td>
			<td colspan="2"><%=request.getParameter("orderSn")%></td>
		</tr>
		<tr>
			<td align="right">被保人编号：</td>
			<td colspan="2"><%=request.getParameter("insuredSn")%></td>
		</tr>
		<tr>
			<td align="right">产品名称：</td>
			<td colspan="2"><%=request.getParameter("productName")%></td>
		</tr>
		<tr>
			<td align="right">保费：</td>
			<td colspan="2"><%=request.getParameter("timePrem")%></td>
		</tr>
		<tr>
			<td align="right">被保人：</td>
			<td colspan="2"><%=request.getParameter("recognizeeName")%></td>
		</tr>
		<tr>
			<td align="right">证件类型：</td>
			<td colspan="2"><%=request.getParameter("recognizeeIdentityTypeName")%></td>
		</tr>
		<tr>
			<td align="right">证件号：</td>
			<td colspan="2"><%=request.getParameter("recognizeeIdentityId")%></td>
		</tr>
		<tr>
			<td align="right">性别：</td>
			<td colspan="2"><%=request.getParameter("recognizeeSexName")%></td>
		</tr>
		<tr>
			<td align="right">出生日期：</td>
			<td colspan="2"><%=request.getParameter("recognizeeBirthday")%></td>
		</tr>
		<tr>
			<td align="right">航延险保单号：</td>
			<td><input name="policyNo" type="text" class="input1" id="policyNo" value="" size="30" verify="航延险保单号|NotNull" /></td>
		</tr>
		<tr>
			<td align="right">航意险保单号：</td>
			<td><input name="applyPolicyNo" type="text" class="input1" id="applyPolicyNo" value="" size="30" verify="航意险保单号|NotNull" /></td>
		</tr>
	</table>
	<form id="form2" enctype="multipart/form-data" method="post" onSubmit="return checkAndDealAction();">
		<table width="100%" cellpadding="2" cellspacing="0">
			<tr>
				<td width="20%" align="right" align="right">电子保单：</td>
				<td width="80%" colspan="2"><input name="policyFile" type="file" id="policyFile" accept="application/pdf"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<br />
					<input name="button" type="submit" class="inputButton" id="button" value=" 确 定 " /> &nbsp;
					<input name="button2" type="button" class="inputButton" onClick="Dialog.close();" value=" 取 消 " />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
