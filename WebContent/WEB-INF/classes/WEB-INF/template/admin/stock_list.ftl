<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>库存分类列表</title>
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
			<h1><span class="icon">&nbsp;</span>库存分类列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="stock!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="formButtonL" onclick="location.href='stock!add.action'" value="添加库存分类" />
			</div>
			<table class="listTable">
				<tr>
					<th>
						分类名称&nbsp;
					</th>
					<th>
						库存剩余量&nbsp;
					</th>
					<th>
						库存支出量&nbsp;
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list  pager.list as list>
					<tr level="${(list.level)!}">
						<td  >
						    ${list.name}
						</td>
						
						<td>
							${list.residue}
						</td>
						
						<td>
							${list.betake}
						</td>
						
						<td>
						    <a href="gift!list.action?id=${list.id}" title="查看">[查看]</a>
							<a href="stock!edit.action?id=${list.id}" title="编辑">[编辑]</a>
							<!--
							<a href="stock!delete.action?id=${list.id}" title="删除" id="deleteAction" onclick="return confirm('您确定要删除该库存分类吗？');">[删除]</a>
							-->
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size <= 0)>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			<#else>
				<#include "/WEB-INF/template/admin/pager.ftl" />
			</#if>
		</form>
	</div>
</body>
</html>