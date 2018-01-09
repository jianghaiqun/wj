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
<z:init method="com.wangjin.coupon.CouponInfo.init">
	<form id="form2">
	<table width="450" height="120" align="center" cellpadding="2"
		cellspacing="0">
		<tr>
			<td valign="top">
			<fieldset>
			<table height="100%" width="100%">
				<tr>
					<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">邮箱或电话信息：</td>
					<td><input value="" type="text" id="mailMobile"
						name="mailMobile" ztype="String" class="inputText" size="30" width="50%"
						verify="邮箱或电话|NotNull"></td>
				</tr>
			</table>
			<br />
			<br />
			<br />
			</fieldset>
			</td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
