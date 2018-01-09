<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>标题菜单-添加</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<z:init method="com.sinosoft.cms.document.WapSiteBidPageManage.initAddDialog">
	<form id="form2">
		<table width="350" height="200" align="center" cellpadding="2"
			cellspacing="0">
			<tr>
				<td valign="top">
					<fieldset>
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">菜单姓名：</td>
								<td>
									<input value="" type="text" id="MenuName" name="MenuName" class="inputText" size="20" verify="菜单名称|NotNull" maxlength="5" />
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">顺序：</td>
								<td>
									<input type="text" id="OrderFlag" name="OrderFlag" class="inputText" size="20" verify="顺序|NotNull&&Number" maxlength="10"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">备注：</td>
								<td>
									<textarea id="Description" rows="5" cols="25"></textarea>
								</td>
							</tr>
						</table>
					</fieldset></td>
			</tr>
		</table>
		<input name="DocumentId" type="hidden" value="${DocumentId}" id="DocumentId" />
	</form>
	</z:init>
</body>
</html>