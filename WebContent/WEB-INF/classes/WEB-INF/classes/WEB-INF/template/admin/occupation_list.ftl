<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>职业列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/admin/js/list.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $delete = $(".delete");
	
	// 职业删除
	$delete.click( function() {
		var $this = $(this);
		var id = $this.metadata().id;
		if (confirm("您确定要删除吗？") == true) {
			$.ajax({
				url: "occupation!delete.action",
				data: {"id": id},
				dataType: "json",
				async: false,
				success: function(data) {
					if (data.status == "success") {
						$this.parent().html("&nbsp;");
					}
					$.message(data.status, data.message);
				}
			});
		}
		return false;
	});
	
})
</script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>职业管理&nbsp;<span class="pageInfo">总记录数: ${occupationList?size}</span></h1>
		</div>
		<form id="listForm" action="occupation!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='occupation!add.action<#if parent??>?parentId=${parentId}</#if>'" value="添加职业" />
			</div>
			<table class="listTable">
				<tr>
					<th colspan="5" class="green" style="text-align: center;">
						<#if parent??>上级职业 - [${(parent.name)!}]<#else>顶级职业</#if>
					</th>
				</tr>
				<#list occupationList as list>
					<#if (list_index + 1) == 1>
						<tr>
					</#if>
					<td>
						<a href="occupation!list.action?parentId=${list.id}" title="查看下级地区">${list.name}</a>
						<a href="occupation!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						<a href="#" class="delete {id: '${list.id}'}" title="删除">[删除]</a>
					</td>
					<#if (list_index + 1) % 5 == 0 && list_has_next>
						</tr>
						<tr>
					</#if>
					<#if (list_index + 1) % 5 == 0 && !list_has_next>
						</tr>
					</#if>
					<#if (list_index + 1) % 5 != 0 && !list_has_next>
							<td colspan="${5 - occupationList?size % 5}">&nbsp;</td>
						</tr>
					</#if>
				</#list>
				<#if occupationList?size == 0>
					<tr>
						<td colspan="5" style="text-align: center; color: red;">
							无下级职业! <a href="occupation!add.action<#if parent??>?parentId=${parentId}</#if>" style="color: gray">点击添加</a>
						</td>
					</tr>
				</#if>
			</table>
			<#if parent??>
				<div class="blank"></div>
				<#if (parent.parent)??>
					<input type="button" class="formButton" onclick="location.href='occupation!list.action?parentId=${(parent.parent.id)!}'" value="上级职业" />
				<#else>
					<input type="button" class="formButton" onclick="location.href='occupation!list.action'" value="上级职业" />
				</#if>
			</#if>
		</form>
	</div>
</body>
</html>