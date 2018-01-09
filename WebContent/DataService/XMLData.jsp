<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
</head>
<body class="dialogBody">
<form action="XMLDataSave.jsp" enctype="multipart/form-data"
	method="post" id="f1" name="f1">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td height="30" colspan="2" align="left"><span class="STYLE1">
		<%
		 if(StringUtil.isNotEmpty(request.getParameter("upStatus")) && (request.getParameter("upStatus")).equals("0")){
	    %> 
			 <script>
		       Dialog.alert("导入失败!");
			</script> 
		<%
		  }
		  else if(StringUtil.isNotEmpty(request.getParameter("upStatus")) && (request.getParameter("upStatus")).equals("1")){
		  %> <script>
		       Dialog.alert("导入成功!");
			</script> 
		  <%}%> &nbsp;</span></td>
	</tr>
	</table>
	<table>
	<tr>
		<td align="right">XML模板文件：</td>

		<td height="30"><input id="DBFile" name="DBFile" type="file"
			size="40"></td>
		<td align="right"><z:tbutton id="b3" onClick="submit()"><img src="../Icons/icon005a6.gif" width="20" height="20" />导入</z:tbutton></td>
	</tr>
</table>
</form>
</body>
</html>