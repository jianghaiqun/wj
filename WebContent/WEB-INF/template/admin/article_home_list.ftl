<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>文章列表</title>
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
			<h1><span class="icon">&nbsp;</span>文章列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="article_home!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='article_home!add.action'" value="添加文章" />
				<label>查找:</label>
				<select name="pager.property">
					<option value="title" <#if pager.property == "title">selected="selected" </#if>>
						标题
					</option>
				</select>
				<label class="searchText"><input type="text" name="pager.keyword" value="${pager.keyword!}" /></label><input type="button" id="searchButton" class="searchButton" value="" />
				<label>每页显示:</label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10" <#if pager.pageSize == 10>selected="selected" </#if>>
						10
					</option>
					<option value="20" <#if pager.pageSize == 20>selected="selected" </#if>>
						20
					</option>
					<option value="50" <#if pager.pageSize == 50>selected="selected" </#if>>
						50
					</option>
					<option value="100" <#if pager.pageSize == 100>selected="selected" </#if>>
						100
					</option>
				</select>
			</div>
			<table class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="title">标题</span>
					</th>
					<th>
						<span class="sort" name="articleCategoryName">所选文章分类名称</span>
					</th>
					<th>
						<span class="sort" name="homeSn">首页布局编号</span>
					</th>
					<th>
						<span class="sort" name="createDate">添加时间</span>
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${list.id}" />
						</td>
						<td>
							<#if list.title?length lt 30>${list.title}<#else>${list.title[0..30]}...</#if>
						</td>
						<td>
							${list.articleCategoryId}
						</td>
						<td>
							${list.homeSn}
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
						<td>
							<a href="article_home!edit.action?id=${list.id}" title="编辑">[编辑]</a>
							<#if list.isPublication == true>
								<a href="${base}${list.htmlFilePath}" target="_blank" title="浏览">[浏览]</a>
							<#else>
								<span title="未发布">[未发布]</span>
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="article_home!delete.action" value="删 除" disabled hidefocus="true" />
					<#include "/WEB-INF/template/admin/pager.ftl" />
				</div>
			<#else>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>