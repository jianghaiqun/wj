<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>产品分类列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	// 删除确认对话框
	$(".deleteAction").click( function() {
		if (confirm("您确定要删除此产品分类吗？") == false) {
			return false;
		}
	});
	
	// 树折叠
	$(".categoryName").click( function() {
		var level = $(this).parent().attr("level");
		var isHide;
		$(this).parent().nextAll("tr").each(function(){
			var thisLevel = $(this).attr("level");
			if(thisLevel <=  level) {
				return false;
			}
			if(isHide == null) {
				if($(this).is(":hidden")){
					isHide = true;
				} else {
					isHide = false;
				}
			}
			if( isHide) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});

})
</script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>产品分类列表&nbsp;<span class="pageInfo">总记录数: ${productCategoryTreeList?size}</span></h1>
		</div>
		<form id="listForm" action="product_category!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='product_category!add.action'" value="添加分类" />
			</div>
			<table class="listTable">
				<tr>
					<th>
						分类名称&nbsp;
					</th>
					<th>
						排序&nbsp;
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list productCategoryTreeList as list>
					<tr level="${(list.level)!}">
						<td class="categoryName">
							<span style="margin-left: ${list.level * 20}px;">
								<#if list.level == 0>
									<img src="${shopStaticPath}/template/admin/images/list_category_first_icon.gif" />
								<#else>
									<img src="${shopStaticPath}/template/admin/images/list_category_icon.gif" />
								</#if>
								${list.name}
							</span>
						</td>
						<td>
							${list.orderList}
						</td>
						<td>
							<a href="${base}/shop/product!list.action?id=${list.id}" target="_blank" title="查看">[查看]</a>
							<#if list.children?size gt 0>
								<span title="无法删除">[删除]</span>
							<#else>
								<a href="product_category!delete.action?id=${list.id}" class="deleteAction" title="删除" >[删除]</a>
							</#if>
							<a href="product_category!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if productCategoryTreeList?size == 0>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>