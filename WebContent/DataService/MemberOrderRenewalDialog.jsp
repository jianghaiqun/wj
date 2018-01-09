<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%String serverContext = Config.getServerContext();%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
</head>
<body class="dialogBody">
<z:init>
<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
		<tr>
		    <td height="25" align="right" class="tdgrey1">投保人邮箱：</td>
			<td height="25">
				<input name="applicantMail" type="text" value="${applicantMail}" style="width:250px"  id="applicantMail" verify="投保人邮箱|NotNull&&Email"/>
			</td>
			
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">推送续保链接：</td>
			<td height="25">
				<textarea id="renewalUrl" name="renewalUrl" style="width:350px" readonly><%=serverContext %>/shop/order_config_new!keepInput.action?orderSn=${orderSn}&KID=${KID}</textarea>
			</td>
		</tr>
	</table>
</form>
</z:init>
</body>
</html>