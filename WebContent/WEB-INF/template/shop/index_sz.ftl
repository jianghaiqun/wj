<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>百保箱—您最专业的保险管家</title>
<meta name="Author" content="SinoSoft Team" />
<meta name="Copyright" content="SinoSoft" />
<meta name="keywords" content="51百保箱,买保险,保险超市,百保箱,首都保险,投保,PICC,中盛国际,保险经,51baibaoxiang" />
<meta name="description" content="百保箱—您最专业的保险管家" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product_list.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/sz/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/index.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/product.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/customer_demand.js"></script>
<script type="text/javascript">

function change (){
	var customername = document.getElementById("customername").value;
	var phone = document.getElementById("phone").value;
    var email = document.getElementById("email").value;
    var insuredage = document.getElementById("insuredage").value;
    
	var t =  document.getElementById("insuredname");
	var insuredname = t.options[t.selectedIndex].value;
	var t1 =  document.getElementById("insurancetype");
	var insurancetype = t1.options[t1.selectedIndex].value;
	var t2 =  document.getElementById("guaranteeperiod");
	var guaranteeperiod = t2.options[t2.selectedIndex].value;
	
    //var str = "尊敬的"+customername+"您好，通过您登记联系方式"+phone+"   "+email +" ,我们的客服人员会尽快与您联络，为您介绍符合您购买意向的保险产品 :  为了您的 "+insuredage + " 的 "+insuredname +",打算购买保障期限为 "+guaranteeperiod +" 的 "+insurancetype ；

	 //document.getElementById("neirong").value="尊敬的   "+customername+"    您好，通过您登记联系方式"+phone+"  "+email +"    ,我们的客服人员会尽快与您联络，为您介绍符合您购买意向的保险产品 :  为了您的  "+insuredage + "  岁的   "+insuredname +"   , 打算购买保障期限为   "+guaranteeperiod +"  的   "+insurancetype ;
	$("#neirong").attr("value", "尊敬的   "+customername+"    您好，通过您登记联系方式"+phone+"  "+email +"    ,我们的客服人员会尽快与您联络，为您介绍符合您购买意向的保险产品 :  为了您的  "+insuredage + "  岁的   "+insuredname +"   , 打算购买保障期限为   "+guaranteeperiod +"  的   "+insurancetype); 
}

$(document).ready(function() {

	$(".slider .scrollable").scrollable({
		circular: true,
		speed: 500
	}).autoscroll({
		autoplay: true,
		interval: 4000
	}).navigator();
	
	$(".hotProduct .scrollable").scrollable({
		circular: true,
		speed: 500
	});
	
	$(".newProduct ul.newProductTab").tabs(".newProduct .newProductTabContent", {
		effect: "fade",// 逐渐显示动画
		fadeInSpeed: 500,// 动画显示速度
		event: "mouseover"// 触发tab切换的事件

	});
})
</script>
 <!--[if lt IE 7]>
<style type="text/css">
#gotopbtn{position:absolute;top:expression(eval(document.documentElement.scrollTop + 50));}
</style>
<![endif]-->
<style type="text/css">

#gotopbtn {
	width:0px;
	height:0px;
	background:#fff;
	position:fixed;
	bottom:70px;
	right:20px;
	display:none;
	cursor:pointer;
	font-size:14px;
	line-height:30px;
	border:1px solid #aaa; 
}

</style>
</head>
<body>
	<div class="loginwrap">
		<div class="login">
			<div class="loginleft"><span>您好，欢迎来到12580商城联盟！</span><span><a href="#">登录</a>|<a href="#">注册</a></span></div>
			<div class="loginright"><span><a href="#">返回商城首页</a>|<a href="#">我的联盟</a>|<a href="#">使用帮助</a></span></div>
		</div>
	</div>
    <div class="headwrap">
		<div class="head">
			<div class="logo"><a href="${base}/index_sz.html"><img src="${base}/template/shop/sz/images/logo.gif" /></a></div>
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
    		<div class="bread"><span>首页</span><span>></span><span>产品中心</span></div>
			<div class="part1">
			<div class="produquery">
					<h3>保险产品分类</h3>
					<ul class="produmenu">
						<li><span><a href="${base}/shop/product!szListHome.action?id=8a36800e36bf40d90136bf45b4640001">意外保险</a></span></li>
						<li><span><a href="${base}/shop/product!szListHome.action?id=8a36800e36bf40d90136bf45e04d0002">旅游保险</a></span></li>
					</ul>
					<div class="colum1bgbot"></div>
				</div>
				<div class="news">
            		<iframe name="toppic" src="${base}/html/index_sz_advert.html" 
            		frameborder=no width="770px" scrolling=no  height="263px"></iframe>
				</div>
				<div class="clear"></div>
			</div>
			<div class="h10"></div>
			<div class="wrapb">
				<div class="rxcptit gray"><a href="productlistsub.html">更多保险产品>></a></div>
				<div class="rxcpcont">
				<#list (bestProductList)! as list>
					<div class="produli">
						<div class="produlileft"><img src="${base}${(list.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" alt="${list.name}" /></div>
						<div class="produliright">
							<h3><a title="${list.name}" href="${base}${list.szHtmlFilePath}">${list.name}</a></h3>
							<p class="suitp"><span class="orange">适合人群：</span>${list.crowd}</p>
							<p class="sdate"><span class="orange">保障期限：</span>${list.timeLimit}</p>
							<p><span class="orange">产品特点：</span>${list.specialty}</p>
							<p><span class="orange">保障利益：</span>${list.benefit}</p>
							<p><span class="orange">投保前阅读：</span><span class="blue"><a href="#">保险条款</a></span>&nbsp;<span class="blue"><a href="#">职业分类表</a></span></p>
							<p class="sbtn"><a href="${base}/shop/order!szBuyNow.action?productId=${list.id}"><img src="${base}/template/shop/sz/images/btn05.gif" border="0" /></a></p>
						</div>
						<div class="clear"></div>
					</div>
				</#list>
				</div>
				<div class="clear"></div>
				<div class="rxcpbot"></div>
			</div>
			<div class="h10"></div>
			<div class="part2">
				<div class="step">
					<ul>
						<li class="step01">简单几步即可注册成功，登录网站，享受快捷的网上投保服务</li>
						<li class="step02">家庭综合险、人身意外险、个人责任险多种产品任您选择</li>
						<li class="step03">根据您选择的产品，填写相关投保信息，提交后生成订单</li>
						<li class="step04">投保成功，立即享受我们为您提供的便捷高质的保险服务</li>
					</ul>
				</div>
				<div class="knowledge gray">
						<div class="more"><a href="${base}/shop/article!list.action?id=8a368087328b9e5f01328be458c30006">更多>></a></div>
				<ul>
							<#list (homeList4)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 30)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
									</#if>
								</li>
								<#if list_index + 1 == 6>
									<#break/>
								</#if>
							</#list>
				</ul>
				</div>
				<div class="clear"></div>
			</div>
    	</div>
    </div>
    <!--
    <iframe name="toppic" src="static/foot.html" 
            frameborder=no width="100%" scrolling=no  height="100px"></iframe>
    -->
</body>
</html>