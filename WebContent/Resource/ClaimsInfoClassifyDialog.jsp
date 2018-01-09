<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<z:init method="com.sinosoft.cms.resource.ClaimsInfo.initClassify" >
<table>
	<tr>
		<td height="35" align="right" class="tdgrey1" bordercolor="#eeeeee">二级分类：</td>
		<td>${ClassifyType}</td>
	</tr>
</table>
<input type="hidden" name="comCode" value='${comCode}'>
</z:init>
</body>
</html>