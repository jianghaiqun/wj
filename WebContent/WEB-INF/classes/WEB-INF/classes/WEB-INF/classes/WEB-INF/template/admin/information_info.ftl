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
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>查看该产品投保信息</h1>
		</div>
		<div class="blank"></div>
		<table class="inputTable tabContent">
			<tr>
				<th>
					投保状态:
				</th>
				<td colspan="3">
					[${action.getText("OrderStatus." + information.orderItem.order.orderStatus)}]
					[${action.getText("PaymentStatus." + information.orderItem.order.paymentStatus)}]
					[${action.getText("ShippingStatus." + information.orderItem.order.shippingStatus)}]
				</td>
			</tr>
			<tr>
				<th>
					投保编号:
				</th>
				<td>
					${information.orderItem.order.orderSn}
				</td>
				<th>
					下单时间:
				</th>
				<td>
					${information.orderItem.order.createDate?string("yyyy-MM-dd HH:mm:ss")}
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
							<td>${information.applicantName}</td>
							<th>投保人性别：</th>
							<td>${information.applicantSex}</td>
						</tr>
						<tr>
							<th>投保人出生日期：</th>
							<td>${information.applicantBirthday}</td>
							<th>投保人电话：</th>
							<td>${information.applicantTel}</td>
						</tr>
						<tr>
							<th>投保人电子邮箱：</th>
							<td>${information.applicantMail}</td>
							<th>投保人地区：</th>
							<td>${information.applicantArea}</td>
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
							<td>${information.recognizeeName}</td>
							<th>被保人性别：</th>
							<td>${information.recognizeeSex}</td>
						</tr>
						<tr>
							<th>被保人出生日期：</th>
							<td>${information.recognizeeBirthday}</td>
							<th>被保人电话：</th>
							<td>${information.recognizeeTel}</td>
						</tr>
						<tr>
							<th>被保人电子邮箱：</th>
							<td>${information.recognizeeMail}</td>
							<th>被保人邮政编码：</th>
							<td>${information.recognizeeZipCode}</td>
						</tr>
						<tr>
							<th>被保人职业：</th>
							<td>${information.occupation1}-${information.occupation2}-${information.occupation3}</td>
							<th>被保人地区：</th>
							<td>${information.recognizeeArea}</td>					
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
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
		</div>
	</div>
</body>
</html>