<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>理赔资料管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/Tabpage.js"></script>

</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td><z:tab>
			<z:childtab id="Basic" selected="true" src="ClaimsInfoCompany.jsp"><img src="../Icons/icon002a1.gif" width="20" height="20" /><b>保险公司分类配置</b></z:childtab>
			<z:childtab id="ClaimsInfo" selected="false" src="ClaimsInfo.jsp"><img src="../Icons/icon002a1.gif" width="20" height="20" /><b>理赔资料配置</b></z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>