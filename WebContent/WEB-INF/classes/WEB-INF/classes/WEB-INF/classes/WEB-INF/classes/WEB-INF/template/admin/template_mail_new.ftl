<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>新建邮件模板</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>新建模版</h1>
		</div>
		<form id="inputForm" class="validate" action="template_mail!save.action" method="post">
			<table class="inputTable">
				<tr>
					<th>
						模版文件名称:
					</th>
					<td>
							<input type="text" name="fileName" class="formText {required: true, paramValue: true}" />
							<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						邮件描述:
					</th>
					<td>
							<input type="text" name="description" class="formText {required: true, paramValue: true}" />
							<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						邮件主题:
					</th>
					<td>
							<input type="text" name="subject" class="formText {required: true, paramValue: true}" />
							<label class="requireField">*</label>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>