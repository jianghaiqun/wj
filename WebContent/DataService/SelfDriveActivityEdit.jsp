<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
</script>
</head>
<body class="dialogBody">
	<z:init method="com.sinosoft.cms.document.SelfDriveActivity.dg2DataBind">
		<form id="form2">
			<input name="id" type="hidden" id="id" value="${id}">
			<input id="createdate" name="createdate" type="hidden" value="${createdate}" />
			<table cellpadding="2" cellspacing="0" align="center">
				<tr>
					<td height="25" align="right" class="tdgrey1">姓名：</td>
					<td height="25"><input name="realname" type="text" id="realname"
						value="${realname}" style="width: 150px" verify="姓名|NotNull"></td>
				</tr>
				<tr>
					<td height="25" align="right" class="tdgrey1">手机号：</td>
					<td height="25"><input name="mobileno" type="text" id="mobileno"
						value="${mobileno}" style="width: 150px" verify="手机号|NotNull"></td>
				</tr>
				<tr>
					<td height="25" align="right" class="tdgrey1">电子邮箱：</td>
					<td height="25"><input name="email" type="text" id="email"
						value="${email}" style="width: 150px" verify="电子邮箱|NotNull"></td>
				</tr>
				<tr>
					<td height="25" align="right" class="tdgrey1">身份证号：</td>
					<td height="25"><input name="idcode" type="text" id="idcode"
						value="${idcode}" style="width: 150px" verify="身份证号|NotNull"></td>
				</tr>
			</table>
		</form>
	</z:init>
</body>
</html>