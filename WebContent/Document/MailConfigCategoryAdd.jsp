<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>邮件模板-新增品类</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<z:init method="com.sinosoft.cms.document.MailConfigCategory.initAddDialog">
	<form id="form2">
		<table width="350" height="200" align="center" cellpadding="2"
			cellspacing="0">
			<tr>
				<td valign="top">
					<fieldset>
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">产品种类：</td>
								<td>
									<input type="hidden" name="EmailType" id="emailType" value="${emailType}">
									<input type="hidden" id="ID" name="ID" value="${ID}"/>
									<input type="hidden" id="oldProductType" name="oldProductType" value="${ProductType}"/>
									<z:select id="ProductType" name="ProductType" style="width:150px" listWidth="157" value="${ProductType}"> ${MailConfigRiskType}</z:select>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">产品品类：</td>
								<td>
									<z:select id="ProductCategory" name="ProductCategory" style="width:150px" listWidth="157" value="${ProductCategory}"> ${categorys}</z:select>
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