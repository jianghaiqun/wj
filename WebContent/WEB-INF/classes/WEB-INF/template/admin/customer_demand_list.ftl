<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户需求列表</title>
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
			<h1><span class="icon">&nbsp;</span>会员列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="customerDemand!list.action" method="post">
			<div class="operateBar">
		    <input type="button" class="addButton" onclick="location.href='customer_demand!add.action'" value="添加需求" />
				<label>查找:</label>
				<select name="pager.property">
					<option value="customername" <#if pager.property == "customername">selected="selected" </#if>>
						客户姓名
					</option>
					<option value="email" <#if pager.property == "email">selected="selected" </#if>>
						E-mail
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
						<span class="sort" name="customername">客户姓名</span>
					</th>
					<th>
						<span class="sort" name="phone">联系电话</span>
					</th>
					<th>
						<span class="sort" name="email">E-mail</span>
					</th>
					<th>
						<span class="sort" name="insuredname">为谁投保</span>
					</th>
					<th>
						<span class="sort" name="insuredage">被保人年龄</span>
					</th>
					<th>
						<span class="sort" name="insurancetype">险种类型</span>
					</th>
					<th>
						<span class="sort" name="guaranteeperiod">保障期限</span>
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${(list.id)!}" />
						</td>
						<td>
							${list.customername}
						</td>
						<td>
							${list.phone}
						</td>
						<td>
							${list.email}
						</td>
						<td>
							${list.insuredname}
						</td>
						<td>
							${list.insuredage}
						</td>
						<td>
							${list.insurancetype}
						</td>
						<td>
							${list.guaranteeperiod}
						</td>
						<td>
							<a href="customer_demand!edit.action?id=${list.id}" title="[编辑]">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="customer_demand!delete.action" value="删 除" disabled hidefocus="true" />
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