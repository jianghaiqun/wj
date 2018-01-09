<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.platform.Channel.dg2DataBind">
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
			<table width="400" height="197" align="center" cellpadding="2"
				cellspacing="0">
				<tr>
					<td align="right">渠道编码：</td>
					<td>&nbsp;</td>
					<td width="260"><input name="Code"
						type="hidden" value="${Code}" style="width:100px" class="input1" id="Code"
						size=15 />${Code}</td>
				</tr>
				<tr>
					<td align="right">渠道名称：</td>
					<td>&nbsp;</td>
					<td width="260"><input name="Name" verify="名称|NotNull"
						type="text" value="${Name}" style="width:100px" class="input1" id="Name"
						size=15 /></td>
				</tr>
				<tr>
					<td align="right">是否显示：</td>
					<td>&nbsp;</td>
					<td width="260"><z:select id="IsShow"
						style="width:100px;"><span
						value="0"></span> ${IsShow}</z:select></td>
				</tr>
				<tr>
					<td align="right">是否可以发放优惠券：</td>
					<td>&nbsp;</td>
					<td width="260"><z:select id="IsCoupon" verify="发放优惠券|NotNull"
						style="width:100px;"><span
						value="0"></span> ${IsCoupon}</z:select></td>
				</tr>
				<tr>
					<td align="right">是否可制定活动：</td>
					<td>&nbsp;</td>
					<td width="260"><z:select id="IsActivity" verify="制定活动|NotNull"
						style="width:100px;"><span
						value="0"></span> ${IsActivity}</z:select></td>
				</tr>
				<tr>
					<td align="right">上级菜单：</td>
					<td>&nbsp;</td>
					<td>
					<z:select id="ParentID"
						style="width:100px;"><span
						value="0"></span> ${ParentMenu}</z:select>
					</td>
				</tr>
				<tr>
					<td align="right">排序等级：</td>
					<td>&nbsp;</td>
					<td width="260"><input name="OrderFlag"
						type="text" value="${OrderFlag}" style="width:100px" class="input1" id="OrderFlag"
						size=15 /></td>
				</tr>
				<tr>
					<td colspan="7" align="center" height="10"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
	</body>
	</html>
</z:init>
