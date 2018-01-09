<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	function changeEffSum() {
		var tradeSum = document.getElementById("tradeSum").value;
		var useSum = document.getElementById("useSum").value;
		if (!isNaN(tradeSum) && !isNaN(useSum)) {
			document.getElementById('effSum').innerHTML = tradeSum - useSum;
		}
	}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<form id="form2">
		<input id="recordId" type="hidden" name="recordId" value="<%=request.getParameter("id")%>">
		<table width="100%" cellpadding="2" cellspacing="0">
			<tr>
				<td width="25%" align="right" style="font-weight:bold;">活动消费信息说明：</td>
				<td width="75%" colspan="2" style="font-weight:bold;">
					只能修改当日消费金额和使用金额，有效金额=消费金额-使用金额。 <br />
				</td>
			</tr>
			<tr>
				<td align="right">投保人：</td>
				<td colspan="2"><%=request.getParameter("appntName")%></td>
			</tr>
			<tr>
				<td align="right">证件类型：</td>
				<td colspan="2"><%=request.getParameter("certificateTypeName")%></td>
			</tr>
			<tr>
				<td align="right">证件号：</td>
				<td colspan="2"><%=request.getParameter("certificateId")%></td>
			</tr>
			<tr>
				<td align="right">消费时间：</td>
				<td colspan="2"><%=request.getParameter("tradeDate")%></td>
			</tr>
			<tr>
				<td align="right">消费金额：</td>
				<td><input name="tradeSum" type="text" class="input1" id="tradeSum" value="<%=request.getParameter("tradeSum")%>" size="30" verify="消费金额|PositiveNumber" onchange="changeEffSum()"/></td>
			</tr>
			<tr>
				<td align="right">使用金额：</td>
				<td><input name="useSum" type="text" class="input1" id="useSum" value="<%=request.getParameter("useSum")%>" size="30" verify="使用金额|PositiveNumber" onchange="changeEffSum()"/></td>
			</tr>
			<tr>
				<td align="right">有效金额：</td>
				<td id="effSum" colspan="2"><%=request.getParameter("effSum")%></td>
			</tr>
		</table>
	</form>
</body>
</html>
