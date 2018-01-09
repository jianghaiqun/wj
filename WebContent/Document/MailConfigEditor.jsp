<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function setCatalog(emailType){
	var diag = new Dialog("Diag2");
	diag.Width = 800;
	diag.Height = 500;
	diag.Title = "设置品类";
	diag.URL = "Document/MailConfigCategory.jsp?emailType="+emailType;
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	$D.close();
}
</script>

<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<z:init method="com.sinosoft.cms.document.MailConfig.initDialogEditor">
		<form id="MailConfigEditorForm">
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
									${MailConfigName}
									<input type="hidden" name="EmailType" id="EmailType" value="${EmailType}">
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
									class="tdgrey1">关联品类：</td>
								<td>
									<input type="button" value="设置" onclick="setCatalog('${EmailType}')"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">推荐活动：</td>
								<td>
									启用 <input type="radio" name="activityFlag" value="Y" ${IsNewViewYCheck}> 禁止 <input type="radio" name="activityFlag" value="N" ${IsNewViewNCheck}>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">推荐产品：</td>
								<td>
									启用 <input type="radio" name="productFlag" value="Y" ${IsVisiableYCheck}> 禁止 <input type="radio" name="productFlag" value="N" ${IsVisiableNCheck}>
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