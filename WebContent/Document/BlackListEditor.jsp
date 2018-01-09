<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>黑名单-修改</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<z:init method="com.sinosoft.cms.document.BlackListManage.initDialogEditor">
	<form id="BlackListEditorForm">
	<input type="hidden" id="ID" value="${ID}"></input>
		<table width="350" height="200" align="center" cellpadding="2"
			cellspacing="0">
			<tr>
				<td valign="top">
					<fieldset>
							<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">姓名：</td>
								<td>
									<input type="text" id="RecognizeeName" name="RecognizeeName" class="inputText" size="20" value="${recognizeeName}" verify="名称|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">保险公司：</td>
								<td>
									<z:select id="InsuredCompanySn" name="InsuredCompanySn" style="width:135px" listWidth="125"> ${SupplierCode}</z:select>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">证件类型：</td>
								<td>
									<select id="InsIDType" disabled="disabled">
										<option value="ALL">全部证件</option>
									</select>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">证件号：</td>
								<td>
									<input type="text" id="InsIDNo" name="InsIDNo" class="inputText" value="${insIDNo}" size="20" verify="证件号|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">备注(原因)：</td>
								<td>
									<textarea id="Remark" rows="5" cols="25">${remark}</textarea>
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