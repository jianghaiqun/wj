<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>动态模板列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/admin/js/list.js"></script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1>
				<span class="icon">&nbsp;</span>动态模板列表
			</h1>
		</div>
		<div class="blank"></div>
		<table class="listTable">
			<tr>
				<th>
					模板名称
				</th>
				<th>
					描述
				</th>
				<th>
					模板文件路径
				</th>
				<th>
					操作
				</th>
			</tr>
			<#list dynamicConfigList as list>
				<tr>
					<td>
						${list.name}
					</td>
					<td>
						${list.description}
					</td>
					<td>
						${list.templateFilePath}
					</td>
					<td>
						<a href="template_dynamic!edit.action?dynamicConfig.name=${list.name}" title="[编辑]">[编辑]</a>
					</td>
				</tr>
			</#list>
		</table>
		<div class="blank"></div>
	</div>
</body>
</html>