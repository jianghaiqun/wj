<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>礼品列表</title>
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
			<h1><span class="icon">&nbsp;</span>礼品列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="present!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='present!add.action'" value="添加礼品" />
				<label>查找:</label>
				<select name="pager.property">
					<option value="name" <#if pager.property == "name">selected="selected" </#if>>
						礼品名称
					</option>
					<!-- <option value="point" <#if pager.property == "point">selected="selected" </#if>>
						兑换积分
					</option> -->
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
						<span class="sort" name="name">礼品名称</span>
					</th>
					<th>
						<span class="sort" name="presentCategory">分类</span>
					</th>
				<!--	<th>
						<span class="sort" name="isMarketable">上架</span>
					</th>
					<th>
						<span class="sort" name="isBest">精品</span>
					</th>
					<th>
						<span class="sort" name="isNew">新品</span>
					</th>
					<th>
						<span class="sort" name="isHot">热销</span>
					</th> -->
					
					<th>
						<span class="sort" name="point">积分</span>
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
							<#if (list.name?length <= 20)!>
								<span title="${list.name}">${list.name}</span>
							<#else>
								<span title="${list.name}">${list.name[0..20]}...</span>
							</#if>
						</td>
						<td>
							${list.presentCategory.name}
						</td>
					<!--	<td>
							<#if list.isMarketable == true>
								<img src="${shopStaticPath}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${shopStaticPath}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isBest == true>
								<img src="${shopStaticPath}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${shopStaticPath}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isNew == true>
								<img src="${shopStaticPath}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${shopStaticPath}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isHot == true>
								<img src="${shopStaticPath}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${shopStaticPath}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td> -->
						<td>
							${list.point}
						</td>
						<td>
							<a href="present!edit.action?id=${list.id}" title="编辑">[编辑]</a>
							<!--<#if list.isMarketable == true>
								<a href="${base}${list.htmlFilePath}" target="_blank" title="浏览">[浏览]</a>
							<#else>
								<span title="未上架">[未上架]</span>
							</#if>-->
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="present!delete.action" value="删 除" disabled hidefocus="true" />
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