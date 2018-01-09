<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>合作商-新增</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<z:init method="com.sinosoft.cms.document.PartnersManage.initAddDialog">
	<form id="form2">
		<table width="350" height="200" align="center" cellpadding="2"
			cellspacing="0">
			<tr>
				<td valign="top">
					<fieldset>
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作商名称：</td>
								<td>
									<input value="" type="text" id="PartnersName" name="PartnersName" class="inputText" size="20" verify="名称|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作商编码：</td>
								<td>
									<input value="" type="text" id="PartnersCode" name="PartnersCode" class="inputText" size="20" verify="编码|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">Cookie有效期(天)：</td>
								<td>
									<input value="30" type="text" id="CookieTime" name="CookieTime" class="inputText" size="20" verify="有效期|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">推送URL：</td>
								<td>
									<input type="text" id="SendUrl" name="SendUrl" class="inputText" size="20"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作状态：</td>
								<td>
									是 <input type="radio" name="State" value="Y" checked="checked"> 否 <input type="radio" name="State" value="N">
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">推送状态：</td>
								<td>
									是 <input type="radio" name="SendState" value="Y" checked="checked"> 否 <input type="radio" name="SendState" value="N">
								</td>
							</tr>
						</table>
					</fieldset></td>
			</tr>
		</table>
	</form>
	</z:init>
</body>
</html>