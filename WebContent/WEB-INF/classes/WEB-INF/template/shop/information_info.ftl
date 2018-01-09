<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
				
					<div class="blank"></div>
					
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th>商品名称</th>
							<th>购买价格</th>
							<th>购买数量</th>
							<th>小计</th>
						</tr>
						<#list information.orderItem.order.orderItemSet as list>
							<tr>
								<td>
								<!--
								<a href="order!information.action?id=${list.product.id}">${list.productName}</a>
								-->
									<a href="${base}${list.productHtmlFilePath}" target="_blank">${list.productName}</a>
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
					</table>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="4">投保人信息</th>
						</tr>
						<tr>
							<td class="title">投保人姓名：</td>
							<td>${information.applicantName}</td>
							<td class="title">投保人性别：</td>
							<td>${information.applicantSex}</td>
						</tr>
						<tr>
							<td class="title">投保人出生日期：</td>
							<td>${information.applicantBirthday}</td>
							<td class="title">投保人电话：</td>
							<td>${information.applicantTel}</td>
						</tr>
						<tr>
							<td class="title">投保人电子邮箱：</td>
							<td>${information.applicantMail}</td>
						</tr>
					</table>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="4">被保人信息</th>
						</tr>
						<tr>
							<td class="title">被保人姓名：</td>
							<td>${information.recognizeeName}</td>
							<td class="title">被保人性别：</td>
							<td>${information.recognizeeSex}</td>
						</tr>
						<tr>
							<td class="title">被保人出生日期：</td>
							<td>${information.recognizeeBirthday}</td>
							<td class="title">被保人电话：</td>
							<td>${information.recognizeeTel}</td>
						</tr>
						<tr>
							<td class="title">被保人电子邮箱：</td>
							<td>${information.recognizeeMail}</td>
							<td class="title">被保人邮政编码：</td>
							<td>${information.recognizeeZipCode}</td>
						</tr>
						<tr>
							<td class="title">被保人职业：</td>
							<td>${information.occupation1}-${information.occupation2}-${information.occupation3}</td>
							<td class="title"></td>
							<td></td>					
						</tr>
					</table>
					<div class="blank"></div>
					<div class="blank"></div>
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
						<#list information.orderItem.order.orderLogSet as list>
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
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>