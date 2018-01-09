<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body class="dialogBody">
	<input type="hidden" id="comCode" value="<%=request.getParameter("comCode")%>">
	<z:init method="com.sinosoft.cms.resource.ClaimsInfo.init">
		<form id="form2">
			<table width="100%" height="80%" align="center" cellpadding="1" cellspacing="1">
				<tr height="20px">
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td width="110px">保险公司：</td>
					<td><z:select name="supplierCode" id="supplierCode" style="width:150px;" verify="保险公司|NotNull">${supplier}</z:select></td>
				</tr>
				<tr>
					<td></td>
					<td>保险公司分类名称：</td>
					<td><input name="comClassifyName" type="text" id="comClassifyName" value="" style="width:200px" verify="保险公司分类名称|NotNull" /></td>
				</tr>
				<tr>
					<td></td>
					<td>排序：</td>
					<td><input name="orderFlag" type="text" id="orderFlag" value="" style="width:120px" verify="排序|Int"/></td>
				</tr>
				<tr>
					<td></td>
					<td>是否推荐：</td>
					<td><z:select name="recommendFlag" id="recommendFlag" style="width:50px;" >${YesOrNo}</z:select></td>
				</tr>
			</table>
		</form>
	</z:init>
</body>
</html>