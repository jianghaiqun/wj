<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑投保</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {
	
	var productTotalPrice = parseFloat("${order.productTotalPrice}");// 产品总价格
	var deliveryFee = parseFloat("${order.deliveryFee}");// 配送费用
	var paymentFee = parseFloat("${order.paymentFee}");// 支付费用
	
	var $areaSelect = $(".areaSelect");
	var $productTotalPrice = $("#productTotalPrice");
	var $totalAmount = $("#totalAmount");
	var $deliveryFeeInput = $("input[name='order.deliveryFee']");
	var $paymentFeeInput = $("input[name='order.paymentFee']");
	var $productPriceInput = $("input.productPrice");
	var $productQuantityInput = $("input.productQuantity");
	var $coutNoInput = $("input.coutNo");
	var $deleteOrderItem = $(".deleteOrderItem");
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "area!ajaxChildrenArea.action"// Json数据获取url
	});
	
	// 修改产品总价格
	function modifyProductTotalPrice() {
		productTotalPrice = 0.0;
		$productPriceInput.each(function(){
			var productPrice = $(this).val();
			var productQuantity = $(this).parent().parent().find("input.productQuantity").val();
			productTotalPrice = floatAdd(productTotalPrice, floatMul(productPrice, productQuantity));
		});
		$productTotalPrice.text(priceUnitCurrencyFormat(productTotalPrice));
	}
	
	// 修改投保总金额
	function modifyTotalAmount() {
		var totalAmount = floatAdd(floatAdd(productTotalPrice, deliveryFee), paymentFee);
		$totalAmount.text(priceUnitCurrencyFormat(totalAmount));
	}
	
	// 根据配送费用修改投保总金额
	$deliveryFeeInput.change( function() {
		$this = $(this);
		var deliveryFeeValue = $this.val();
		var reg = /^(([0-9]+\.?[0-9]+)|[0-9])$/;
		if (!reg.test(deliveryFeeValue)) {
			$this.val(deliveryFee);
			$.tip("配送费用输入有误！");
		} else {
			deliveryFee = deliveryFeeValue;
			modifyTotalAmount();
		}
	});
	
	// 根据支付费用修改投保总金额
	$paymentFeeInput.change( function() {
		$this = $(this);
		var paymentFeeValue = $this.val();
		var reg = /^(([0-9]+\.?[0-9]+)|[0-9])$/;
		if (!reg.test(paymentFeeValue)) {
			$this.val(paymentFee);
			$.tip("支付手续费输入有误！");
		} else {
			paymentFee = paymentFeeValue;
			modifyTotalAmount();
		}
	});
	
	// 记录初始产品价格
	$productPriceInput.each(function(){
		$this = $(this);
		$this.data("previousProductPrice", $this.val());
	});
	
	// 记录初始保单号
	$coutNoInput.each(function(){
		$this = $(this);
		$this.data("previousCoutNo", $this.val());
	});
	
	// 记录初始产品购买数
	$coutNoInput.each(function(){
		$this = $(this);
		$this.data("previousProductQuantity", $this.val());
	});
	
	// 根据产品价格修改产品总价格、投保总金额
	$productPriceInput.change( function() {
		$this = $(this);
		var productPriceValue = $this.val();
		var reg = /^(([0-9]+\.?[0-9]+)|[0-9])$/;
		if (!reg.test(productPriceValue)) {
			var previousProductPrice = $this.data("previousProductPrice");
			$this.val(previousProductPrice);
			$.tip("产品价格输入有误！");
		} else {
			$this.data("previousProductPrice", productPriceValue);
			modifyProductTotalPrice();
			modifyTotalAmount();
		}
	});
	
	// 根据产品数量修改产品总价格、投保总金额
	$productQuantityInput.change( function() {
		$this = $(this);
		var productQuantityValue = $this.val();
		var availableStore = $this.metadata().availableStore;
		var reg = /^[0-9]*[1-9][0-9]*$/;
		if (!reg.test(productQuantityValue)) {
			var previousProductQuantity = $this.data("previousProductQuantity");
			$this.val(previousProductQuantity);
			$.tip("产品数量输入有误！");
		} else {
			if (availableStore != null && parseInt(productQuantityValue) > parseInt(availableStore)) {
				var previousProductQuantity = $this.data("previousProductQuantity");
				$this.val(previousProductQuantity);
				$.tip("产品数量超出可用库存数！");
				return false;
			}
			$this.data("previousProductQuantity", productQuantityValue);
			modifyProductTotalPrice();
			modifyTotalAmount();
		}
	});
	
	// 删除投保项
	$deleteOrderItem.click( function() {
		$this = $(this);
		if ($productPriceInput.length == 1) {
			$.tip("请保留至少一个产品！");
			return false;
		}
		if (confirm("您确定要移除此产品吗？") == true) {
			$this.parent().parent().remove();
			$productPriceInput = $("input.productPrice");
			$productQuantityInput = $("input.productQuantity");
			modifyProductTotalPrice();
			modifyTotalAmount();
		}
		return false;
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
			<h1><span class="icon">&nbsp;</span>编辑投保</h1>
		</div>
		<form id="inputForm" class="validate" action="order!update.action" method="post">
			<input type="hidden" name="order.id" value="${order.id}" />
			<div class="blank"></div>
			<ul class="tab">
				<li>
					<input type="button" value="投保信息" hidefocus="true" />
				</li>
				<li>
					<input type="button" value="产品信息" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						投保编号:
					</th>
					<td>
						${order.orderSn}
					</td>
					<th>
						下单时间:
					</th>
					<td>
						${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
					</td>
				</tr>
				<tr>
					<th>
						产品总金额:
					</th>
					<td>
						<span id="productTotalPrice" class="red">${order.productTotalPrice?string(orderUnitCurrencyFormat)}</span>
					</td>
					<th>
						投保总金额:
					</th>
					<td>
						<span id="totalAmount" class="red">${order.totalAmount?string(orderUnitCurrencyFormat)}</span>
					</td>
				</tr>
				<tr>
					<th>
						配送方式:
					</th>
					<td>
							
						<select name="order.deliveryType.id">
							<#list allDeliveryType as list>
								<option value="${list.id}"<#if (list == order.deliveryType)!> selected</#if>>
							
									电子保单
								
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
							
					</td>
					<th>
						支付方式:
					</th>
					<td>
						<select name="order.paymentConfig.id">
							<#list allPaymentConfig as list>
								<option value="${list.id}"<#if (list == order.paymentConfig)!> selected</#if>>
									支付宝
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
						
					</td>
				</tr>
				<input type="hidden" name="order.deliveryFee" value="${order.deliveryFee}" />
				<input type="hidden" name="order.paymentFee" value="${order.paymentFee}" />
				<input type="hidden" name="order.productWeight" value="${order.productWeight}" />
			<!--
				<tr>
					<th>
						配送费用:
					</th>
					<td>
						<input type="text" name="order.deliveryFee" class="formText {required: true, min: 0}" value="${order.deliveryFee}" />
						<label class="requireField">*</label>
					</td>
					<th>
						支付手续费:
					</th>
					<td>
						<input type="text" name="order.paymentFee" class="formText {required: true, min: 0}" value="${order.paymentFee}" />
						<label class="requireField">*</label>
					</td>
				</tr>
			-->
			<!--
				<tr>
					<th>
						产品保额(万元):
					</th>
					<td>
						<input type="text" name="order.productWeight" class="formText {required: true, min: 0, messagePosition: '#productWeightMessagePosition'}" value="${order.productWeight}" />
					</td>
					<th>
						附言:
					</th>
					<td>
						${(order.memo)!"&nbsp;"}
					</td>
				</tr>
			-->
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<!--原先receiver全部设成隐藏属性-->
				<input type="hidden" name="order.shipAreaPath" value="${order.shipAreaPath}" />
				<!--
				<input type="hidden" name="order.shipName" value="${order.shipName}" />
				<input type="hidden" name="order.shipAddress" value="${order.shipAddress}" />
				<input type="hidden" name="order.shipZipCode" value="${order.shipZipCode}" />
				<input type="hidden" name="order.shipPhone" value="${order.shipPhone}" />
				<input type="hidden" name="shipMobile" value="${shipMobile}" />
				<input type="hidden" name="order.shipName" value="${order.shipName}" />
				<input type="hidden" name="order.shipName" value="${order.shipName}" />
				-->
				<!--原先receiver全部设成隐藏属性-->
			<!--暂时隐藏receiver 开始
				<tr>
					<th>
						收货人姓名:
					</th>
					<td>
						<input type="text" name="order.shipName" class="formText {required: true}" value="${order.shipName}" />
					</td>
					<th>
						收货地区:
					</th>
					<td>
						<input type="text" name="order.shipAreaPath" class="areaSelect hidden {required: true, messagePosition: '#areaMessagePosition'}" value="${(order.shipAreaPath)!}" />
						<span id="areaMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						收货地址:
					</th>
					<td>
						<input type="text" name="order.shipAddress" class="formText {required: true}" value="${order.shipAddress}" />
					</td>
					<th>
						邮编:
					</th>
					<td>
						<input type="text" name="order.shipZipCode" class="formText {required: true, zipCode: true}" value="${order.shipZipCode}"  />
					</td>
				</tr>
				<tr>
					<th>
						电话:
					</th>
					<td>
						<input type="text" name="order.shipPhone" class="formText {requiredOne: '#shipMobile', phone: true, messages: {requiredOne: '电话、手机必须填写其中一项！'}}" value="${order.shipPhone}" />
					</td>
					<th>
						手机:
					</th>
					<td>
						<input type="text" id="shipMobile" name="order.shipMobile" class="formText {mobile: true, messages: {mobile: '手机格式错误！'}}" value="${order.shipMobile}"  />
					</td>
				</tr>
				暂时隐藏receiver 结束-->
				
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<#if order.member??>
					<tr>
						<th>
							用户名:
						</th>
						<td>
							${order.member.username}
						</td>
						<th>
							会员等级:
						</th>
						<td>
							${order.member.memberRank.name}
							<#if order.member.memberRank.preferentialScale != 100>
								<span class="red">[优惠百分比：${order.member.memberRank.preferentialScale}%]</span>
							</#if>
						</td>
					</tr>
					<tr>
						<th>
							E-mail:
						</th>
						<td>
							${order.member.email}
						</td>
						<th>
							最后登录IP:
						</th>
						<td>
							${order.member.loginIp}
						</td>
					</tr>
					<tr>
					<!--隐藏预存款余额开始
						<th>
							预存款余额:
						</th>
						<td>
							${order.member.deposit?string(orderUnitCurrencyFormat)}
						</td>
					隐藏预存款余额结束-->
						<th>
							积分:
						</th>
						<td>
							${order.member.point}
						</td>
						<td colspan="2">
						&nbsp;
						</td>
					</tr>
					
<!--添加投保人和被保人信息开始-->
					<tr>
					<td colspan="4">
						&nbsp;
					</td>
					</tr>
				<!--
					<tr>
						<th>
							投保人姓名:
						</th>
						<td>
							${order.applicantName}
						</td>
						<th>
							被保人姓名:
						</th>
						<td>
							${order.recognizeeName}
						</td>
						
					</tr>
					<tr>
						<th>
							投保人证件类型:
						</th>
						<td>
							${order.applicantIdentityType}
						</td>
						<th>
							被保人证件类型:
						</th>
						<td>
							${order.recognizeeIdentityType}
						</td>
						
					</tr>
					<tr>
						<th>
							投保人证件号码:
						</th>
						<td>
							${order.applicantIdentityId}
						</td>
						<th>
							被保人证件号码:
						</th>
						<td>
							${order.recognizeeIdentityId}
						</td>
						
					</tr>
					<tr>
						<th>
							投保人性别:
						</th>
						<td>
							${order.applicantSex}
						</td>
						<th>
							被保人性别:
						</th>
						<td>
							${order.recognizeeSex}
						</td>
						
					</tr>
					<tr>
						<th>
							投保人出生日期:
						</th>
						<td>
							${order.applicantBirthday}
						</td>
						<th>
							被保人出生日期:
						</th>
						<td>
							${order.recognizeeBirthday}
						</td>
						
					</tr>
					<tr>
						<th>
							投保人电话:
						</th>
						<td>
							${order.applicantTel}
						</td>
						<th>
							被保人电话:
						</th>
						<td>
							${order.recognizeeTel}
						</td>
						
					</tr>
					<tr>
						<th>
							投保人电子邮箱:
						</th>
						<td>
							${order.applicantMail}
						</td>
						
					</tr>
			-->
<!--添加投保人和被保人信息结束-->
					
					
				<#else>
					<tr>
						<th>
							会员状态:
						</th>
						<td colspan="3">
							<span class="red">会员不存在</span>
						</td>
					</tr>
				</#if>
			</table>
			<table class="inputTable tabContent">
				<tr class="title">
					<th>产品名称</th>
					<th>险种号</th>
					<th>价格</th>
					<th>购买数量</th>
					<th>保单号</th>
					<!--
					<th>电子保单上传</th>
					<th>保单状态</th>
					<th>备注</th>
					<th>删除</th>
					-->
					<th>泰康接口测试</th>
				</tr>
				<#list order.orderItemSet as list>
					<tr>
						<td>
						<a href="information!edit.action?productId=${list.product.id}&orderId=${list.order.id}" title="修改投保信息">${list.productName}</a>
							<!--
							<a href="${base}${list.productHtmlFilePath}" target="_blank">
							${list.productName}
							-->	
							</a>
						</td>
						<td>
							<input type="hidden" name="orderItemList[${list_index}].id" value="${list.id}" />
							<a href="${base}${list.productHtmlFilePath}" target="_blank">
								${list.productSn}
							</a>
						</td>

						<td>
							<input type="text" name="orderItemList[${list_index}].productPrice" class="formText productPrice" value="${list.productPrice}" style="width: 50px;" />
						</td>
						<td>
							<#if list.product.store != null>
								<#if (systemConfig.storeFreezeTime == "payment" && order.paymentStatus == "unpaid") || (systemConfig.storeFreezeTime == "ship" && order.shippingStatus == "unshipped")>
									<#assign availableStore = list.product.store - list.product.freezeStore />
								<#else>
									<#assign availableStore = list.product.store - list.product.freezeStore + list.productQuantity />
								</#if>
							</#if>
							<input type="text" name="orderItemList[${list_index}].productQuantity" class="formText productQuantity <#if list.product.store != null>{availableStore: ${availableStore}}</#if>" value="${list.productQuantity}" style="width: 50px;" />
						</td>
						<td>
							<!--
							<input type="hidden" name="orderItemList[${list_index}].id" value="${list.id}" />
							<input type="text" name="orderItemList[${list_index}].productQuantity" class="formText productQuantity <#if list.product.store != null>{availableStore: ${availableStore}}</#if>" value="${list.productQuantity}" style="width: 50px;" />
							-->
							<input type="text" name="orderItemList[${list_index}].coutNo" class="formText coutNo" value="${list.coutNo}"/>
						</td>
						<!--
						<td>
							
							<input type="hidden" name="orderItemList[${list_index}].id" value="${list.id}" />
							<#if (list.valid == false)>
								<#else>
							</#if>
									<input type="button" onclick="location.href='${base}/shop/order!bbxinfoedit.action?orderId=${order.id}&productId=${list.product.id}'" value="查看保单信息" class="red" hidefocus="true" />
						
							<input type="file" name="orderItemList[${list_index}].electronic"/>
						</td>
							-->
						<!--
						<td>
							状态未定义
						</td>
						<td>
							备注未定义
						</td>
						<td>
							<a href="javascript: void(0);" class="deleteOrderItem">删除</a>
						</td>
						-->
						<td>
							<a href="information!tkPost.action?productId=${list.product.id}&orderId=${list.order.id}" title="泰康接口测试">泰康接口</a>
						</td>
					</tr>
				</#list>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>