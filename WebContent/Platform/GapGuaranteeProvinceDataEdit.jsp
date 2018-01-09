<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<z:init method="com.sinosoft.platform.GapGuarantee.dropdownMenu5">
		<form id="form5">
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td width="40%" height="10" align="right"></td>
					<td height="10"></td>
				</tr>
				<br></br>
				<tr>
					<td align="right">居住省份</td>
					<td><z:select id="CodeValue" name="CodeValue" style="width:117px"
							verify="居住省份|NotNull">${Province}</z:select></td>
				</tr>
				<tr>
					<td align="right">居民人均可支配收入</td>
					<td><input id="prop1" name="prop1" type="text" class="input1"
						 verify="居民人均可支配收入|NotNull" size=20 /></td>
				</tr>
				<tr>
					<td align="right">居民人均消费支出</td>
					<td><input id="prop2" name="prop2" type="text" class="input1"
						verify="居民人均消费支出|NotNull" size=20 /></td>
				</tr>
				<tr>
					<td align="right">在岗职工平均工资</td>
					<td><input id="prop3" name="prop3" type="text" class="input1"
						 verify="在岗职工平均工资|NotNull" size=20 /></td>
				</tr>
				<input name="CodeType" id="CodeType" type="hidden" />
				<input name="CodeValueOld" type="hidden" id="CodeValueOld" />
				<input name="CodeNameOld" type="hidden" id="CodeNameOld" />
				<input name="prop1Old" type="hidden" id="prop1Old" />
				<input name="prop2Old" type="hidden" id="prop2Old" />
				<input name="prop3Old" type="hidden" id="prop3Old" />
			</table>
		</form>
	</z:init>
</body>
</html>