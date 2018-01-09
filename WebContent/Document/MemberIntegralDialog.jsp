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
</head>
<script>

</script>
<body class="dialogBody">
<z:init method="com.sinosoft.message.MemberIntegral.initDialog">
<form id="form2">
	<table width="500" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="150" height="10"></td>
			<td width="376"><input id="ID" name="ID" type="hidden" value="${ID}"/></td>
		</tr>
		<tr>
			<td align="right">会员名：</td>
			<td height="30">${username}</td>
		</tr>
		
		<tr>
			<td align="right">积分：</td>
			<td height="30"><input id="Integral" name="Integral" type="text"
				value="${Integral}" class="input1" verify="积分|NotNull" /></td>
		</tr>
		<tr>
			<td align="right">积分来源：</td>
			<td><z:select  id="Source" style="width:150px;" >
				${Source}
			</z:select>
			</td>
		</tr>
				
		<tr>
			<td align="right">积分状态：</td>
			<td><z:select style="width:150px;">
				<select name="Status" id="Status">
                  ${statusSelectTag}
                </select>
				</z:select>
		</tr>
		<tr>
			<td align="right">积分收入/支出：</td>
			<td><z:select style="width:150px;"  disabled="true">
				<select name="Manner" id="Manner" >
					${mannerSelectTag}
                </select>
				</z:select>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td>
				<textarea id="description" verify="备注|NotNull">${description}</textarea>
			</td>
		</tr>
		
	</table>
	</form>
</z:init>
</body>
</html>