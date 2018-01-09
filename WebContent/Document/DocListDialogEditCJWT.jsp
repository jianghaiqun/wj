<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">


</script>
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.document.ArticleListCJWT.init">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td height="25" align="right" class="tdgrey1">常见问题：</td>
			<td height="25">
			<input type="text" id="FAQName" name="FAQName" style="width:150px" verify="常见问题|NotNull" value="${FAQName}" />
			</td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">常见问题答案：</td>
			<td height="25">
			<textarea id="FAQContent" name="FAQContent" cols="25"
				rows="5" verify="常见问题答案|NotNull">${FAQContent}</textarea>
		</tr>
	</table>
	 <input name="RelaId" type="hidden" value="${RelaId}" id="RelaId" />
	  <input name="Id" type="hidden" value="${Id}" id="Id" />
	</form>
</z:init>
</body>
</html>
