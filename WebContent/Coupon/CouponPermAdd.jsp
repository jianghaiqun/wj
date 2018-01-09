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
<body>
	<form id="form2">
		<table width="850" height="350" align="center" cellpadding="2"
			cellspacing="0">
			<tr>
				<td valign="top">
					<fieldset>
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">用户名：</td>
								<td><input value="" type="text" id="username"
									name="username" ztype="String" class="inputText" size="20"
									verify="用户名|NotNull">
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">优惠券面值折扣：</td>
								<td width="150px"><input value="" type="text"
									id="couponVarPerm" name="couponVarPerm" ztype="String"
									class="inputText" size="20" verify="优惠券面值折扣|NotNull">
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">备注：</td>
								<td width="150px"><input value="" type="text"
									id="memo" name="memo" ztype="String"
									class="inputText" size="20">
								</td>
							</tr>
						</table>
					</fieldset></td>
			</tr>
		</table>
	</form>
</body>
</html>