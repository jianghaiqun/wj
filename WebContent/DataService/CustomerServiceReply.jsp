<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.Comment.initReplyContent">
	<form name="fm_reply" id="fm_reply">
		<table width="100%" cellpadding="2" cellspacing="0" class="blockTable">
			<tr height="200px">
				<td align="center">
					<textarea id="ReplyContent" style="width:300px;height:100px">${ReplyContent}</textarea>
				</td>
				<td><input type="hidden" id="ID" value="${ID}"/></td>
			</tr>
		</table>
	</form>
</z:init>
</body>
</html>