<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.module.Module.initModuleList">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body class="dialogBody">
	<form id="form2">
	<table width="100%" height="100%" border="0">
		<tr>
			<td valign="middle">
			<table width="600" height="197" align="center" cellpadding="2"
				cellspacing="0" border="0">
				<tr>
					<td width="80" height="10"></td>
					<td></td>
				</tr>
				<tr>
					<td align="center">元素名称：</td>
					<td  ><input name="ElementName" verify="元素名称|NotNull&&Length<20"
						type="text" value=""   class="input1" id="ElementName"
						size="50" /> <input name="ID" type="hidden" id="ID" /></td>
				</tr>
				<tr>
					<td align="center">元素类型：</td>
					<td>
					<z:select id="ElementType" style="width:100px;" verify="元素类型|NotNull">${ElementTypeList}</z:select>
					</td>
				</tr>
				<tr>
					<td align="center" valign="top">元素内容：</td>
					<td> 
						<textarea name="ElementContent" cols="25" rows="3" id="ElementContent" style="width: 410px;height: 200px;" verify="元素内容|NotNull"></textarea>
					</td>
				</tr>
				<tr>
					<td align="center">备注：</td>
					<td><input name="Memo" type="text" class="input1" id="Memo"
						value="" size="30" verify="备注|Length<50"/></td>
				</tr>
				<tr>
					<td colspan="3" align="center" height="10"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
	</body>
	</html>
</z:init>
