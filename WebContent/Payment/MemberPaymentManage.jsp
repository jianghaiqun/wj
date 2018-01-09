<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员理赔管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/Tabpage.js"></script>

</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td><z:tab>
			<z:childtab id="Basic" selected="true" src="PaymentClaimsInfo.jsp"><img src="../Icons/icon002a1.gif" width="20" height="20" /><b>理赔申请信息</b></z:childtab>
			<!-- 
			<z:childtab id="ClaimsAbnormalInfo" selected="false" src="ClaimsAbnormalInfo.jsp"><img src="../Icons/icon002a1.gif" width="20" height="20" /><b>理赔异常统计</b></z:childtab>
			 -->
		</z:tab></td>
	</tr>
</table>
</body>
</html>