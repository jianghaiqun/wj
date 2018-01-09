<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑保险公司支付方式</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	var $allCheck = $(".input input.allCheck");// 全选复选框
	var $idsCheck = $(".input input[name='ids']");// ID复选框
	var $deleteButton = $(".input input.deleteButton");// 删除按钮
	
	// 全选
	$allCheck.click( function() {
		if ($(this).attr("checked") == true) {
			$idsCheck.attr("checked", true);
			$deleteButton.attr("disabled", false);
		} else {
			$idsCheck.attr("checked", false);
			$deleteButton.attr("disabled", true);
		}
	});
	
	// 无复选框被选中时,删除按钮不可用
	$idsCheck.click( function() {
		var $idsChecked = $(".input input[name='ids']:checked");
		if ($idsChecked.size() > 0) {
			$deleteButton.attr("disabled", false);
		} else {
			$deleteButton.attr("disabled", true)
		}
	});
	
	// 批量删除
	$deleteButton.click( function() {
		var url = $(this).attr("url");
		var $idsCheckedCheck = $(".input input[name='ids']:checked");
		if (confirm("您确定要删除吗？") == true) {
			$.ajax({
				url: url,
				data: $idsCheckedCheck.serialize(),
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$deleteButton.attr("disabled", true)
				},
				success: function(data) {
					$deleteButton.attr("disabled", false)
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().remove();
					}
					$.message(data.status, data.message);
				}
			});
		}
	});	
});
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加保险公司支付方式<#else>编辑保险公司支付方式</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>payment_company!save.action<#else>payment_company!update.action</#if>"  method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						保险公司名称:
					</th>
					<td>
					<#if isAdd??>
						<select name="paymentCompany.brand.id">
							<option value="">请选择...</option>
							<#list allBrand as list>
								<option value="${list.id}"<#if (list.id == brand.id)!> selected </#if>>
									${list.name}
								</option>
							</#list>
						</select>
					<#else>
					${(brand.name)!}
					<input type="hidden" name="paymentCompany.brand.id" value="${(brand.name)!}" />
				    </#if>	
					</td>
				</tr>
				<#if isAdd??>
				<tr>
					<th>
					 支付银行:
					</th>
					<td>
						<select name="paymentCompany.paymentConfig.id" >  
							<option value="">请选择...</option>
							<#list allBankPaymentConfig as list>
								<option value="${list.id}" <#if (list.id == paymentConfig.id)!> selected </#if>>
									${list.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<#else>
					<tr>
					<th>
					默认支付银行:
					</th>
					<td>
						<select name="paymentCompany.paymentConfig.id" >  
							<option value="">请选择...</option>
							<#list paymentCompanys as list>
								<option value="${list.id}" <#if (list.paymentConfig.id == paymentConfig.id)!> selected </#if>>
									${list.paymentConfig.name}
								</option>
							</#list>
						</select>
					</td>
				  </tr>
					</#if>
				<#if isAdd??>
				<#else>
				<tr>
					<th>
						支付银行列表:
					</th>
							<td>
							<#list paymentCompanys as list>
							
								<input type="checkbox" name="ids" value="${list.id}" />
							    <img src="${staticPath}${list.paymentConfig.logo}" >
							</#list>
							   </td>
				</tr>
				</#if>
				<#if isAdd??>
				<tr>
					<th>
						是否默认:
					</th>
					<td>
						<label><input type="radio" name="paymentCompany.isDefault" value="true"<#if (paymentCompany.isDefault == true)!> checked</#if> />是</label>
						<label><input type="radio" name="paymentCompany.isDefault" value="false"<#if (isAdd || paymentCompany.isDefault == false)!> checked</#if> />否</label>
					</td>

				</tr>
				</#if>
			</table>
			<div class="buttonArea">
				<#if isAdd??>
				<#else>
				<input type="button" class="deleteButton" url="payment_company!deletepaymentconfig.action" value="删 除" disabled hidefocus="true" />
				</#if>
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>