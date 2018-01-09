<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.document.WxAutoReply.initSubDialog">
<form id="form2">
	<table width="800" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="100" height="10"></td>
			<td width="680">
			<input id="ID" name="ID" type="hidden" value="${ID}"/>
			</td>
		</tr>
	 	<tr id="TextContentTR">
            <td align="right">回复内容：</td>
            <td height="30">
                <textarea name="TextContent" id="TextContent" style="width:680px;height:160px">${TextContent}</textarea>
            </td>
       	</tr>
	</table>
	</form>
</z:init>
</body>
</html>