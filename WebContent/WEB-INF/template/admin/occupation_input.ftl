<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑职业</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加职业<#else>编辑职业</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>occupation!save.action<#else>occupation!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="parentId" value="${parentId}" />
			<table class="inputTable">
				<tr>
					<th>
						上级职业:
					</th>
					<td>
						<#if parent??>${(parent.name)!}<#else>一级职业</#if>
					</td>
				</tr>
				<tr>
					<th>
						职业编码:
					</th>
					<td>
						<input type="text" name="occupation.code" class="formText {required: true, remote: 'occupation!checkCode.action?oldValue=${(occupation.code?url)!}&parentId=${(parent.id)!}', messages: {remote: '此职业编码已存在!'}}" value="${(occupation.code)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						职业名称:
					</th>
					<td>
						<input type="text" name="occupation.name" class="formText {required: true, remote: 'occupation!checkName.action?oldValue=${(occupation.name?url)!}&parentId=${(parent.id)!}', messages: {remote: '此职业名称已存在!'}}" value="${(occupation.name)!}" />
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