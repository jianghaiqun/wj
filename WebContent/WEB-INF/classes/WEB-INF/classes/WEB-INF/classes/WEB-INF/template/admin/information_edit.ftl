<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看指定产品投保信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$().ready( function() {
	var $informationEidtForm = $("#informationEidtForm");
	// 表单验证
	$informationEidtForm.validate({
		ignore: ".ignoreValidate",
		invalidHandler: function(form, validator) {
			$.each(validator.invalid, function(key, value){
				$.tip(value);
				return false;
			});
		},
		errorPlacement:function(error, element) {},
		submitHandler: function(form) {
			$informationEidtForm.find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
});
</script>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>查看该产品投保信息</h1>
		</div>
		<div class="blank"></div>
		<form id="informationEidtForm" action="information!update.action?orderId=${order.id}&productId=${product.id}" method="post">	
		<table class="inputTable tabContent">
			<tr>
				<th>
					投保状态:
				</th>
				<td colspan="3">
					[${action.getText("OrderStatus." + order.orderStatus)}]
					[${action.getText("PaymentStatus." + order.paymentStatus)}]
					[${action.getText("ShippingStatus." + order.shippingStatus)}]
				</td>
			</tr>
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
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
						
						<tr>
							<th><strong class="red">投保人信息</strong></th>
							<td colspan="3"></td>
						</tr>
						<tr>
							<th>投保人姓名：</th>
							<td>
							<input type="text" name="applicantName" value="${information.applicantName}" class="formText {required: true, messages: {required: '请填写投保人姓名！'}}" />
							<label class="requireField">*</label>							
							</td>
							<!--
							<td>${information.applicantName}</td>
							-->
							<th>投保人性别：</th>information.applicantSex
							<td>
							<input type="radio" name="applicantSex" value="男" <#if (information.applicantSex=="男")!> checked</#if> />男
	                        <input type="radio" name="applicantSex" value="女" <#if (information.applicantSex=="女")!> checked</#if> />女
							<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.applicantSex}</td>
							-->
						</tr>
						<tr>
							<th>投保人出生日期：</th>
							<td>
								<input type="text" name="applicantBirthday" value ="${information.applicantBirthday}" onfocus="setday(this)"  class="formText {required: true, messages: {required: '请填写投保人出生日期！'}} />
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.applicantBirthday}</td>
							-->
							<th>投保人手机号码：</th>
							<td>
								<input type="text" name="applicantTel" value="${information.applicantTel}" class="formText {mobile: true, messages: {mobile: '手机号码格式错误！'}}" />
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.applicantTel}</td>
							-->					
						</tr>
						<tr>
							<th>投保人电子邮箱：</th>
							<td>
								<input type="text" name="applicantMail" value="${information.applicantMail}" class="formText {required: true, email: true}" />
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.applicantMail}</td>
							-->
							<th>投保人地区：</th>
							<td>
								<input type="text" name="applicantArea" value="${information.applicantArea}" class="formText {required: true, messages: {required: '请填写投保人地区！'}} />
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.applicantArea}</td>
							-->
						</tr>
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
					
						<tr>
							<th><strong class="red">被保人信息<strong></th>
							<td colspan="3"></td>
						</tr>
						<tr>
							<th>被保人姓名：</th>
							<td>
								<input type="text" name="recognizeeName" value="${information.recognizeeName}" class="formText {required: true, messages: {required: '请填写被保人姓名！'}}" />
								<label class="requireField">*</label>					
							</td>
							<!--
							<td>${information.recognizeeName}</td>
							-->
							<th>被保人性别：</th>
							<td>
									<#if ("${information.recognizeeSex}"="男")>
											<input type="radio" name="recognizeeSex" value="男" checked>男
            								<input type="radio" name="recognizeeSex" value="女" >女	
										<#else>
											<input type="radio" name="recognizeeSex" value="男" >男
            								<input type="radio" name="recognizeeSex" value="女" checked>女	
            						</#if>	
									<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.recognizeeSex}</td>
							-->
						</tr>
						<tr>
							<th>被保人出生日期：</th>
							<td>
								<input type="text" name="recognizeeBirthday" value="${information.recognizeeBirthday}" class="formText {required: true, messages: {required: '请填写被保人出生日期！'}}/>
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.recognizeeBirthday}</td>
							-->
							<th>被保人手机号码：</th>
							<td>
								<input type="text" name="recognizeeTel" value="${information.recognizeeTel}" class="formText {mobile: true, messages: {mobile: '手机号码格式错误！'}}" />
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.recognizeeTel}</td>
							-->
						</tr>
						<tr>
							<th>被保人电子邮箱：</th>
							<td>
								<input type="text" name="recognizeeMail" value="${information.recognizeeMail}" class="formText {required: true, email: true}" />
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.recognizeeMail}</td>
							-->
							<th>被保人邮政编码：</th>
							<td>
								<input type="text" name="recognizeeZipCode" value="${information.recognizeeZipCode}" class="formText {required: true, zipCode: true, messages: {required: '请填写邮编！', zipCode: '邮编格式错误！'}}" />
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.recognizeeZipCode}</td>
							-->
						</tr>
						<tr>
							<th>被保人职业：</th>
								<td>
								<input type="text" name="occupation1" value="${information.occupation1}-${information.occupation2}-${information.occupation3}" class="formText {required: true, messages: {required: '请填写被保人职业！'}}" />
								<label class="requireField">*</label>
								</td>
							<!--
							<td>${information.occupation1}-${information.occupation2}-${information.occupation3}</td>
							-->
							<th>被保人地区：</th>
							<td>
								<input type="text" name="recognizeeArea" value="${information.recognizeeArea}" class="formText {required: true, messages: {required: '请填写被保人地区！'}}
								<label class="requireField">*</label>
							</td>
							<!--
							<td>${information.recognizeeArea}</td>	
							-->				
						</tr>
						
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
			<#if information.orderItem.order.member??>
				<tr>
					<th>
						用户名:
					</th>
					<td>
						${information.orderItem.order.member.username}
					</td>
					<th>
						会员等级:
					</th>
					<td>
						${information.orderItem.order.member.memberRank.name}
						<#if information.orderItem.order.member.memberRank.preferentialScale != 100>
							<span class="red">[优惠百分比：${information.orderItem.order.member.memberRank.preferentialScale}%]</span>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						E-mail:
					</th>
					<td>
						${information.orderItem.order.member.email}
					</td>
					<th>
						最后登录IP:
					</th>
					<td>
						${information.orderItem.order.member.loginIp}
					</td>
				</tr>
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
		<div class="buttonArea">
		<input type="submit" class="formButton" value="确定" />
		</div>
		</form>
	</div>
</body>
</html>