<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${productCategory.name} 产品列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<#if (productCategory.metaKeywords)! != ""><meta name="keywords" content="${productCategory.metaKeywords}" /></#if>
<#if (productCategory.metaDescription)! != ""><meta name="description" content="${productCategory.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/product_list.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/sz/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/product.js"></script>
</head>
<body>
	<div class="loginwrap">
		<div class="login">
			<div class="loginleft"><span>您好，欢迎来到12580商城联盟！</span><span><a href="#">登录</a>|<a href="#">注册</a></span></div>
			<div class="loginright"><span><a href="#">返回商城首页</a>|<a href="#">我的联盟</a href="#">|<a>使用帮助</a></span></div>
		</div>
	</div>
    <div class="headwrap">
		<div class="head">
			<div class="logo"><a href="indexProduct.html"><img src="${shopStaticPath}/template/shop/sz/images/logo.gif" /></a></div>
			<div class="navarea">
				<div class="navbgleft"></div>
					<ul class="nav">
						<li class="link01on"><span><a href="${base}/index_sz.html"></a></span></li>
						<li class="link02"><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be2714c60009"></a></span></li>
						<li class="link03"><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be274e51000a"></a></span></li>
					</ul>
				<div class="navbgright"></div>
			</div>
			<div class="bgwhiteline"></div>
		</div>
	</div>
    <div class="wrap">
		<div class="wrapa">
    		<div class="bread"><span>首页</span><span>></span><span>产品中心</span>> <span>保险产品</span><span>></span><span>意外保险</span></div>
			<div class="wrapb">
				<div class="left02">
					<div class="produquery">
						<h3>保险产品分类</h3>
						<ul class="produmenu">
							<li><span><a href="${base}/shop/product!szList.action?id=8a36800e36bf40d90136bf45b4640001">意外保险</a></span></li>
							<li><span><a href="${base}/shop/product!szList.action?id=8a36800e36bf40d90136bf45e04d0002">旅游保险</a></span></li>
						</ul>
						<div class="colum1bgbot"></div>
					</div>
					<div class="clear"></div>
					<div class="step11">
						<h3>1.注册/登录</h3>
						<p>简单几步即可注册成功，登录网站享受快捷的网上投保服务</p>
						<h3>2.选择产品</h3>
						<p>家庭综合险、人身意外险、个人责任险多种产品任您选择</p>
						<h3>3.填写投保单</h3>
						<p>根据您选择的产品，填写相关投保信息，提交后生成订单</p>
						<h3>4.投保成功</h3>
						<p>投保成功，立即享受我们为您提供的便捷高质的保险服务</p>
					</div>
				</div>
				<div class="right02">
					<div class="produtit01"></div>
					<div class="producont02">
					<#list  pager.list as list>
						<div class="produli02">
							<div class="produlileft02"><span class="produpicarea"><a href="${base}${list.szHtmlFilePath}"><img src="${staticPath}${(list.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" /></a></span></div>
							<div class="produliright02">
								<h3><a href="${base}${list.szHtmlFilePath}" target="_blank">${list.name}</a></h3>
								<p class="suitp2"><span class="orange">适合人群：</span>${list.crowd}</p>
								<p class="sdate2"><span class="orange">保障期限：</span>${list.timeLimit}</p>
								<p><span class="orange">产品特点：</span>${list.specialty}</p>
								<p><span class="orange">保障利益：</span>${list.benefit}</p>
								<p><span class="orange">投保前阅读：</span><span class="blue"><a href="#">保险条款</a></span>&nbsp;<span class="blue"><a href="#">职业分类表</a></span></p>
								<p class="sbtn2"><a href="${base}/shop/order!szBuyNow.action?productId=${list.id}"><img src="${shopStaticPath}/template/shop/sz/images/btn05.gif" border="0" /></a></p>
							</div>
							<div class="clear"></div>
						</div>
						
						</#list>
						<div class="clear"></div>
				  </div>
					<div class="page2"><span class="infor">共7条</span><span class="pageon">1</span></div>
					<div class="produbot"></div>
				</div>
			</div>
			<div class="h10"></div>
			<div class="clear"></div>
    	</div>
		<div class="clear"></div>
    </div>
</body>
</html>