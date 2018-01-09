<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑字典项</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加字典<#else>编辑字典</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>code!save.action<#else>code!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr id="DictionaryItemTypes">
					<th>
						字典项类型:
					</th>
					<td>
					   <select id="paramENDescription" name="code.paramENDescription" class="{required: true}">
					   <#if isAdd??>
							<option value="" select>请选择...</option>
							<option value="forWhomInsurance" >为谁投保</option>
							<option value="insuranceType" >险种类型</option>
							<option value="protectionPeriod" >保障期限</option>
						</select>
						<#else>
							<#list allDictionaryItemTypes as list>
								<option value="${code.paramENDescription}"<#if (list==(code.paramZHDescription))!> selected</#if>>
									${action.getText(list)}
								</option>
							</#list>
						</#if>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						字典项值:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" name="code.paramValue" class="formText {required: true, paramValue: true}" />
							<label class="requireField">*</label>
						<#else>
							<input type="text" name="code.paramValue" value="${(code.paramValue)!}" />
						</#if>
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