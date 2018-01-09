<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看投保</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>查看投保</h1>
		</div>
		<div class="blank"></div>
		<ul class="tab">
			<li>
				<input type="button" value="订单信息" hidefocus="true" />
			</li>
			<li>
				<input type="button" value="产品信息" hidefocus="true" />
			</li>
			<li>
				<input type="button" value="收款记录" hidefocus="true" />
			</li>
			<li>
				<input type="button" value="退款记录" hidefocus="true" />
			</li>
			<li>
				<input type="button" value="投保记录" hidefocus="true" />
			</li>
			<!--
			<li>
				<input type="button" value="退货记录" hidefocus="true" />
			</li>
			-->
			<li>
				<input type="button" value="投保日志" hidefocus="true" />
			</li>
		</ul>
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
				<td colspan="4">
					&nbsp;
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
					<span id="totalAmount" class="red">${order.totalAmount?string(orderUnitCurrencyFormat)}</span>&nbsp;&nbsp;
					<strong class="red">[已付金额：${order.paidAmount?string(orderUnitCurrencyFormat)}]</strong>
				</td>
			</tr>
			<tr>
				<th>
					配送方式:
				</th>
				<td>
					${order.deliveryTypeName}
				</td>
				<th>
					支付方式:
				</th>
				<td>
					${order.paymentConfigName}
				</td>
			</tr>
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
					<th>
						预存款余额:
					</th>
					<td>
						${order.member.deposit?string(orderUnitCurrencyFormat)}
					</td>
					<th>
						积分:
					</th>
					<td>
						${order.member.point}
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
		<table class="inputTable tabContent">
			<tr class="title">
				<th>产品名称</th>
				<th>险种号</th>
				<th>价格</th>
				<th>购买数量</th>
				<th>产品相关投保信息</th>
				
			</tr>
			<#list order.orderItemSet as list>
		      <#if (list.valid == true)>
				<tr>
					<td>
					<a href="information!info.action?id=${list.product.id}&orderId=${list.order.id}" title="查看投保信息">${list.productName}</a>
					<!--
						<a href="${base}${list.productHtmlFilePath}" target="_blank">
							${list.productName}
						</a>
					-->	
					</td>
					<td>
						<a href="${base}${list.productHtmlFilePath}" target="_blank">
							${list.productSn}
						</a>
					</td>
					<td>
						${list.productPrice?string(priceUnitCurrencyFormat)}
					</td>
					<td>
						${list.productQuantity}
					</td>
	<!--=============产品相关投保信息==================-->				
					<td>
						${list.product.productInsType}
					<!--
					<ul class="productInsAttribute">
						<#assign index = 1 />
						<#list (list.product.productInsType.enabledProductInsAttributeList)! as list>
							<#if (list.product.productInsAttributeMap.get(list) != null)!>
	                    		<li>
	                    			<strong>${list.name}:</strong>
	                				<#list (list.product.productInsAttributeMap.get(list))! as attributeOptionList>
	                            		${attributeOptionList} 
	                            		<#if (attributeOptionList_index == 3) >
											<#break>
										</#if>
	                            	</#list>
	                            </li>
	                            <#if index == 5 >
									<#break>
								</#if>
								<#assign index = index + 1 />
							</#if>
						</#list>
					</ul>
					-->
					</td>
	<!--=============产品相关投保信息==================-->				
					
				</tr>
			  </#if>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>支付编号</th>
				<th>支付类型</th>
				<th>支付方式</th>
				<th>支付金额</th>
				<th>支付手续费</th>
				<th>付款人</th>
				<th>支付状态</th>
				<th>支付时间</th>
			</tr>
			<#list order.paymentSet as list>
				<tr>
					<td>
						<a href="payment!view.action?id=${list.id}">
							${list.paymentSn}
						</a>
					</td>
					<td>
						${action.getText("PaymentType." + list.paymentType)}
					</td>
					<td>
						${list.paymentConfigName}
					</td>
					<td>
						${list.totalAmount?string(orderUnitCurrencyFormat)}
					</td>
					<td>
						${list.paymentFee?string(orderUnitCurrencyFormat)}
					</td>
					<td>
						${list.payer}
					</td>
					<td>
						${action.getText("PaymentStatus." + list.paymentStatus)}
					</td>
					<td>
						<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>退款编号</th>
				<th>退款类型</th>
				<th>支付方式</th>
				<th>退款金额</th>
				<th>收款人</th>
				<th>退款时间</th>
			</tr>
			<#list order.refundSet as list>
				<tr>
					<td>
						<a href="refund!view.action?id=${list.id}">
							${list.refundSn}
						</a>
					</td>
					<td>
						${action.getText("RefundType." + list.refundType)}
					</td>
					<td>
						${list.paymentConfigName}
					</td>
					<td>
						${list.totalAmount?string(orderUnitCurrencyFormat)}
					</td>
					<td>
						${list.payee}
					</td>
					<td>
						<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>发货编号</th>
				<th>配送方式名称</th>
				<th>物流公司名称</th>
				<th>物流编号</th>
				<th>物流费用</th>
				<th>投保人姓名</th>
				<th>投保地区</th>
				<th>发货时间</th>
			</tr>
			<#list order.shippingSet as list>
				<tr>
					<td>
						<a href="shipping!view.action?id=${list.id}">
							${list.shippingSn}
						</a>
					</td>
					<td>
						${list.deliveryTypeName}
					</td>
					<td>
						${list.deliveryCorpName}
					</td>
					<td>
						${list.deliverySn}
					</td>
					<td>
						${list.deliveryFee?string(orderUnitCurrencyFormat)}
					</td>
					<td>
						${list.shipName}
					</td>
					<td>
						${list.shipArea}
					</td>
					<td>
						<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<!--
		<table class="inputTable tabContent">
			<tr class="title">
				<th>退货编号</th>
				<th>配送方式名称</th>
				<th>物流公司名称</th>
				<th>物流编号</th>
				<th>物流费用</th>
				<th>退货人姓名</th>
				<th>退货地区</th>
				<th>退货时间</th>
			</tr>
			<#list order.reshipSet as list>
				<tr>
					<td>
						<a href="reship!view.action?id=${list.id}">
							${list.reshipSn}
						</a>
					</td>
					<td>
						${list.deliveryTypeName}
					</td>
					<td>
						${list.deliveryCorpName}
					</td>
					<td>
						${list.deliverySn}
					</td>
					<td>
						${list.deliveryFee?string(orderUnitCurrencyFormat)}
					</td>
					<td>
						${list.shipName}
					</td>
					<td>
						${list.shipArea}
					</td>
					<td>
						<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		-->
		<table class="inputTable tabContent">
			<tr class="title">
				<th>日志类型</th>
				<th>操作员</th>
				<th>日志信息</th>
				<th>操作日间</th>
			</tr>
			<#list order.orderLogSet as list>
				<tr>
					<td>
						${action.getText("OrderLogType." + list.orderLogType)}
					</td>
					<td>
						${list.operator}
					</td>
					<td>
						${list.info}
					</td>
					<td>
						<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<div class="buttonArea">
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
		</div>
	</div>
</body>
</html>