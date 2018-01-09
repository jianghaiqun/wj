<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑产品属性</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	var $productInsAttributeType = $("#productInsAttributeType");
	var $productInsAttributeTypeTr = $("#productInsAttributeTypeTr");
	var $addAndRemoveTr = $("#addAndRemoveTr");

	// 显示选项内容
	$productInsAttributeType.change(function() {
		productInsAttributeChange();
	})
	
	// 增加选项内容输入框
	$("#addImage").click( function() {
		addAttributeOptionTr();
	})
	
	// 减少选项内容输入框
	$("#removeImage").click( function() {
		removeAttributeOptionTr();
	})
	
	// 删除选项内容输入框
	$(".deleteImage").livequery("click", function() {
		if($(".attributeOptionTr").length > 1) {
			$(this).parent().parent().remove();
		} else {
			alert("请至少保留一个选项!");
		}
	});

	function productInsAttributeChange() {
		$addAndRemoveTr.hide();
		$(".attributeOptionTr").remove();
		if($productInsAttributeType.val() == "select" || $productInsAttributeType.val() == "checkbox") {
			addAttributeOptionTr();
			$addAndRemoveTr.show();
		}
	}
	
	function addAttributeOptionTr() {
		var attributeOptionTrHtml = '<tr class="attributeOptionTr"><th>选项内容:</th><td><input type="text" name="attributeOptionList" class="formText attributeOption {required: true}" />&nbsp;<img src="${shopStaticPath}/template/admin/images/input_delete_icon.gif" class="deleteImage" alt="删除" /></td></tr>';
		if($(".attributeOptionTr").length > 0) {
			$(".attributeOptionTr:last").after(attributeOptionTrHtml);
		} else {
			$productInsAttributeTypeTr.after(attributeOptionTrHtml);
		}
	}

	function removeAttributeOptionTr() {
		if($(".attributeOptionTr").length > 1) {
			$(".attributeOptionTr:last").remove();
		} else {
			alert("请至少保留一个选项!");
		}
	}

})
</script>
<style type="text/css">
<!--

.deleteImage, #addImage, #removeImage {
	cursor: pointer;
}

-->
</style>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加产品属性<#else>编辑产品属性</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>product_ins_attribute!save.action<#else>product_ins_attribute!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="productInsTypeId" value="${productInsTypeId}" />
			<table class="inputTable">
				<tr>
					<th>
						所属产品类型:
					</th>
					<td>
						<select name="productInsAttribute.productInsType.id" id="productInsTypeId" class="{required: true}">
							<option value="">请选择...</option>
							<#list allProductInsType as list>
								<option value="${list.id}"<#if (list.id == productInsTypeId || list.id == productInsAttribute.productInsType.id)!> selected </#if>>${list.name}</option>
							</#list>
						</select>
						<lable class="requireField">*</lable>
					</td>
				</tr>
				<tr>
					<th>
						产品属性名称:
					</th>
					<td>
						<input type="text" name="productInsAttribute.name" id="productInsAttributeName" class="formText {required: true}" value="${(productInsAttribute.name)!}" />
						<lable class="requireField">*</lable>
					</td>
				</tr>
				<tr id="productInsAttributeTypeTr">
					<th>
						产品属性类型:
					</th>
					<td>
						<select id="productInsAttributeType" name="productInsAttribute.attributeInsType" class="{required: true}">
							<option value="">请选择...</option>
							<#list allAttributeInsType as list>
								<option value="${list}"<#if (list == productInsAttribute.attributeInsType)!> selected </#if>>
								${action.getText("AttributeInsType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if (productInsAttribute.attributeInsType == "select" || productInsAttribute.attributeInsType == "checkbox")!>
					<#list productInsAttribute.attributeOptionList as list>
						<tr class="attributeOptionTr">
							<th>选项内容:</th>
							<td>
								<input type="text" name="attributeOptionList" class="formText attributeOption {required: true}" value="${list}" />
								&nbsp;<img src="${shopStaticPath}/template/admin/images/input_delete_icon.gif" class="deleteImage" alt="删除" />
							</td>
						</tr>
					</#list>
					<tr id="addAndRemoveTr">
				<#else>
					<tr id="addAndRemoveTr" style="display: none;">
				</#if>
					<td class="label">
						&nbsp;
					</td>
					<td>
						<img src="${shopStaticPath}/template/admin/images/input_add_icon.gif" id="addImage" alt="增加选项" />&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${shopStaticPath}/template/admin/images/input_remove_icon.gif" id="removeImage" alt="减少选项" />
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="productInsAttribute.orderList" class="formText {digits: true, required:true}" value="${(productInsAttribute.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						是否必填:
					</th>
					<td>
						<label><input type="radio" name="productInsAttribute.isRequired" value="true"<#if (productInsAttribute.isRequired == true)!> checked</#if> />是</label>
						<label><input type="radio" name="productInsAttribute.isRequired" value="false"<#if (isAdd || productInsAttribute.isRequired == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否启用:
					</th>
					<td>
						<label><input type="radio" name="productInsAttribute.isEnabled" value="true"<#if (isAdd || productInsAttribute.isEnabled == true)!> checked</#if> />是</label>
						<label><input type="radio" name="productInsAttribute.isEnabled" value="false"<#if (productInsAttribute.isEnabled == false)!> checked</#if> />否</label>
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