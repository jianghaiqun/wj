<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/member_center.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body orderList">
		<div class="bodyLeft">
			<div class="memberInfo">
				<div class="top"></div>
				<div class="middle">
					<p>欢迎您！<span class="username">${loginMember.username}</span> [<a class="userLogout" href="member!logout.action"">退出</a>]</p>
					<p>会员等级:<span class="red"> ${loginMember.memberRank.name}</span></p>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="memberMenu">
				<div class="top">
					<a href="member_center!index.action">会员中心首页</a>
				</div>
				<div class="middle">
					<ul>
	                	<li class="order">
	                    	<ul>
	                        	<li class="current"><a href="order!list.action">我的订单</a></li>
	                        </ul>
	                    </li>
	                    <li class="category favorite">
	                    	<ul>
	                        	<li><a href="favorite!list.action">产品收藏</a></li>
	                        </ul>
	                    </li>
	                  	<li class="message">
	                    	<ul>
	                        	<li><a href="message!send.action">发送消息</a></li>
	                            <li><a href="message!inbox.action">收件箱</a></li>
	                            <li><a href="message!draftbox.action">草稿箱</a></li>
	                            <li><a href="message!outbox.action">发件箱</a></li>
	                        </ul>
	                    </li>
	                    <li class="profile">
	                    	<ul>
	                        	<li><a href="profile!edit.action">个人信息</a></li>
	                            <li><a href="password!edit.action">修改密码</a></li>
	                            <li><a href="receiver!list.action">投保地址</a></li>
	                        </ul>
	                    </li>
	                </ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="memberCenterDetail">
				<div class="top">订单详情[${order.orderSn}]</div>
				<div class="middle">
					<div class="blank"></div>
					<div class="operationInfo">
						<strong class="green">订单状态 [订单编号：${order.orderSn}]</strong>
						<#if order.orderStatus == "unprocessed">
							<p class="red">等待订单处理</p>
						<#elseif order.orderStatus == "processed">
							<p class="red">您的订单正在处理中</p>
						<#elseif order.orderStatus == "completed">
							<p class="red">您的订单已完成</p>
						<#elseif order.orderStatus == "invalid">
							<p class="red">您的订单已作废</p>
						</#if>
						<#if order.orderStatus != "completed" && order.orderStatus != "invalid">
							<#if order.paymentConfig != null>
								<#if order.paymentStatus == "unpaid">
									<form action="payment!confirm.action" method="post">
										<#if order.paymentConfig.paymentConfigType == "deposit">
											<input type="hidden" name="paymentType" value="deposit" />
										<#elseif  order.paymentConfig.paymentConfigType == "offline">
											<input type="hidden" name="paymentType" value="offline" />
										<#else>
											<input type="hidden" name="paymentType" value="online" />
										</#if>
										<input type="hidden" name="order.id" value="${order.id}" />
										<p class="red">您尚未完成订单支付</p>
										<input type="submit" class="formButton" value="立即支付" />
										<input type="button" onclick="location.href='${base}/shop/order!orderStatusUpdate.action?orderId=${order.id}&productId=${product.id}'" value="取消订单" class="red" hidefocus="true" />
									</form>
								<#elseif order.paymentStatus == "partPayment">
									<form action="payment!confirm.action" method="post">
										<#if order.paymentConfig.paymentConfigType == "deposit">
											<input type="hidden" name="paymentType" value="deposit" />
										<#elseif  order.paymentConfig.paymentConfigType == "offline">
											<input type="hidden" name="paymentType" value="offline" />
										<#else>
											<input type="hidden" name="paymentType" value="online" />
										</#if>
										<input type="hidden" name="order.id" value="${order.id}" />
										<p class="red">您尚未完成订单支付</p>
										<input type="submit" class="formButton" value="支付余款" />
									</form>
								<#else>
									<p class="red">${action.getText("PaymentStatus." + order.paymentStatus)}</p>
								</#if>
							</#if>
						</#if>
					</div>
					<div class="blank"></div>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="4">支付信息</th>
						</tr>
						<tr>
							<td class="title">配送方式：</td>
							<td>${order.deliveryTypeName}</td>
							<td class="title">支付方式：</td>
							<td>${order.paymentConfigName}</td>
						</tr>
						<tr>
							<td class="title">产品保额(万元)：</td>
							<td>${order.productWeight} </td>
							<td class="title"></td>
							<td></td>
						</tr>
					</table>
					<div class="blank"></div>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th>产品名称</th>
							<th>购买价格</th>
							<th>购买数量</th>
							<th>小计</th>
						</tr>
						<#list order.orderItemSet as list>
							<tr>
								<td>
								<a href="information!info.action?id=${list.product.id}&orderId=${list.order.id}" title="查看投保信息">${list.productName}</a>
								<!--
									<a href="${base}${list.productHtmlFilePath}" target="_blank">${list.productName}</a>
								-->
								</td>
								<td>
									${list.productPrice?string(priceCurrencyFormat)}
								</td>
								<td>
									${list.productQuantity}
								</td>
								<td>
									<span class="subtotalPrice">${list.subtotalPrice?string(orderCurrencyFormat)}</span>
								</td>
							</tr>
						</#list>
						<tr>
							<td class="info" colspan="4">
								产品总金额：<span class="red">${order.productTotalPrice?string(orderUnitCurrencyFormat)}</span>    
								    
								订单总金额：<span class="red">${order.totalAmount?string(orderUnitCurrencyFormat)}</span>
							</td>
						</tr>
					</table>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="5">订单日志</th>
						</tr>
						<tr>
							<th>序号</th>
							<th>日志类型</th>
							<th>日志信息</th>
							<th>操作时间</th>
							<th>操作人</th>
						</tr>
						<#list order.orderLogSet as list>
							<tr>
								<td>${list_index + 1}</td>
								<td>${action.getText("OrderLogType." + list.orderLogType)}</td>
								<td>${list.info!"-"}</td>
								<td>${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
								<td>${list.operator!"-"}</td>
							</tr>
						</#list>
					</table>
					<div class="blank"></div>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>