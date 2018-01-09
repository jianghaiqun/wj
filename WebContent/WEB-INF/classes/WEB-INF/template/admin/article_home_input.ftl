<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑文章</title>
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加文章<#else>编辑文章</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>article_home!save.action<#else>article_home!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						栏目名称:
					</th>
					<td>
						<input type="text" name="title" class="formText {required: true}" value="${(articleHome.title)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						文章分类:
					</th>
					<td>
						<select name="articleCategoryId" class="{required: true}">
							<option value="">请选择...</option>
							<#list articleCategoryTreeList as list>
								<option value="${list.id}"<#if (list.id == article.articleCategory.id)!> selected</#if>>
									<#if list.level != 0>
										<#list 1..list.level as i>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</#list>
									</#if>
								${list.name}
								</option>
							</#list>
						</select>
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