<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>邮件模板-新增</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<z:init method="com.sinosoft.cms.document.MailConfig.initAddDialog">
	<form id="form2">
		<table width="350" height="200" align="center" cellpadding="2"
			cellspacing="0">
			<tr>
				<td valign="top">
					<fieldset>
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">邮件模板名称：</td>
								<td>
									<z:select id="MailTemplate" name="MailTemplate" style="width:215px" listWidth="222"> ${MailTemplate}</z:select>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">推荐产品类别：</td>
								<td>
									<z:select id="MailConfigRiskType" name="MailConfigRiskType" style="width:150px" listWidth="157"> ${MailConfigRiskType}</z:select>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">推荐活动：</td>
								<td>
									启用 <input type="radio" name="activityFlag" value="Y" checked="checked"> 禁止 <input type="radio" name="activityFlag" value="N">
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">推荐产品：</td>
								<td>
									启用 <input type="radio" name="productFlag" value="Y" checked="checked"> 禁止 <input type="radio" name="productFlag" value="N">
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