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
<link href="${base}/template/shop/css/login1.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/index.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/shop/css/product_list.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/shop/css/article.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
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
<body class="index">
	
<!-------------------------sky--下-------------------------------->

		<div id="addCartItemTip" class="addCartItemTip">
		<div class="top">
			<div class="tipClose addCartItemTipClose"></div>
		</div>
		<div class="middle">
			<p>
				<span id="addCartItemTipMessageIcon"> </span>
				<span id="addCartItemTipMessage"></span>
			</p>
			<p id="addCartItemTipInfo" class="red"></p>
			<input type="button" class="formButton tipClose" value="继续投保" hidefocus="true" />
			<input type="button" class="formButton" onclick="location.href='${base}/shop/order!info.action'" value="进入购物车" hidefocus="true" />
		</div>
		<div class="bottom"></div>
	</div>
<!-------------------------sky-------上--------------------------->
	<#include "/WEB-INF/template/shop/header.ftl">
	<a href="shop/pay!list.action">第三方支付</a>
	<a href="shop/member!operationRegister.action?serialNO=1000000000000003&codeType=0&source=1">操作后注册</a>
	<a href="${base}/shop/order!buyNow.action?productId=123&insurType=1"><span class="icon"> </span>购买一款意外险</a>
	<a href="${base}/shop/order!buyNow.action?productId=123&insurType=3"><span class="icon"> </span>购买一款旅游险</a>
	<a href="${base}/shop/order!buyNow.action?productId=123&insurType=4"><span class="icon"> </span>购买一款健康险</a>
	<div class="body">
		<div class="bodyLeft">
<!--产品分类 开始-->
			<div class="productCategory">
            	<div class="top">产品分类</div>
            	<div class="middle clearfix">
            		<ul class="menu">
            			<#list rootProductCategoryList as list>
   
            				<li class="mainCategory">
            				<#if (list.name = "产品中心")>
								<a href="${base}/shop/product!listhome.action?id=${list.id}">${list.name}</a>
							<#else>
								<a href="${base}/shop/product!list.action?id=${list.id}">${list.name}</a>
							</#if>
						
							<#if (list.children != null && list.children?size > 0)>
								<ul style="z-index:2">
								<#list list.children as list>
											<li>
												<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon"> </span>${list.name}</a>
											</li>
<!--================暂时隐藏====================================================================================
									<li>
										<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon"> </span>${list.name}</a>
										<#if (list.children != null && list.children?size > 0)>
											<ul>
												<#list list.children as list>
													<li>
														<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon"> </span>${list.name}</a>
													</li>
												</#list>
											</ul>
										</#if>
									</li>
===================================================================暂时隐藏==========================================-->	

											
									<#if list_index + 1 == 10>
										<#break />
									</#if>
								</#list>
								</ul>
							</#if>
							
							</li>
							<#if list_index + 1 == 10>
								<#break />
							</#if>
							
            			</#list>
            			
            			
					</ul>
            	</div>
                <div class="bottom"></div>
			</div>
	<!--产品分类结束-->
			<div class="blank"></div>
			<!--===============================================================xuqiu----------------------------->
	<!--客户需求 开始-->	
			<div class="hotProduct">
				<div class="top">客户需求</div>
				<div class="middle clearfix">
					<form id="inputForm" class="validate" action="${base}/shop/customer_demand!save.action" method="post">
					<input type="hidden" name="id" value="${id}" />
					<table class="tablexq">
				<tr>
					<th align="right" valign="top">
						客户姓名:
					</th>
					<td>
							<input type="text" value="" id="customername" name="customerDemand.customername" class="formText {required: true, customername: true}" onblur="change()" />
							<label class="requireField red">*</label>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						联系电话:
					</th>
					<td>
							<input type="text" value="" id="phone" name="customerDemand.phone" class="formText {required: true, phone: true}" onblur="change()" />
							<label class="requireField red">*</label>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						E-Mail:
					</th>
					<td>
							<input type="text" value="" id="email" name="customerDemand.email" class="formText {required: true, email: true}" onblur="change()" />
							<label class="requireField red">*</label>
					</td>
				</tr>
				<tr id='insurednameTr'>
					<th align="right" valign="top">
						为谁投保:
					</th>
					<td>
						<select id="insuredname" name="customerDemand.insuredname" onchange="change();">
						<option value="">请选择...</option>
							<#list allInsuranceName as list>
							<option value="${list}">
									${list}
							</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						被保人年龄:
					</th>
					<td>
							<input type="text" value="" id="insuredage" name="customerDemand.insuredage" class="formText {required: true, insuredage: true}"  onblur="change()"/>
							<label class="requireField red">*</label>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						险种类型:
					</th>
					<td>
						<select id="insurancetype" name="customerDemand.insurancetype"  onchange="change();">
						<option value="">请选择...</option>
							<#list allInsuranceType as list>
								<option value="${list}">
									${list}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						保障期限:
					</th>
					<td>
						<select id="guaranteeperiod" name="customerDemand.guaranteeperiod" onchange="change();">
						<option value="">请选择...</option>
								<#list allGuaranteePeriod as list>
								<option value="${list}">
									${list}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						内容:
					</th>
					<td>
					   <textarea  id=neirong   rows="6" onblur="change()" style="width:110px;"></textarea>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<div class="btnareaxq">
					<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
				</div>
			</div>
		</form>
		</div>
				<div class="bottom"></div>
			</div>
	<!--客户需求 结束-->
<!--===============================================================xuqiu----------------------------->
	</div>
		
		
		<div class="bodyRight">
<!--图片切换 开始-->
			<div class="slider">
				<div class="scrollable">
					<div class="items">
						<div>
							<img src="${base}/upload/image/banner1.jpg" />
						</div>
						<div>
							<img src="${base}/upload/image/banner2.jpg" />
						</div>
						<div>
							<img src="${base}/upload/image/banner3.jpg" />
						</div>
						<div>
							<img src="${base}/upload/image/banner4.jpg" />
						</div>
					</div>
					<div class="navi"></div>
					<div class="prevNext">
						<a class="prev browse left"></a>
						<a class="next browse right"></a>
					</div>
				</div>
			</div>
	<!--图片切换 结束-->
		<div class="productList">
		<div class="blank"></div>
		<div class="bodyRight">
<!--===============================================================精品开始----------------------------->
		<div class="titprodu01"><strong></strong></div><div class="more"><a href="${base}/shop/product!listmore.action">更多>></a></div>
		<div class="bestProduct">
					<div class="middle">
						<ul>
							<#list (bestProductList)! as list>
								<li>
									<a href="${base}${list.htmlFilePath}">
										<img src="${base}${(list.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" alt="${list.name}" />
										<#if (list.name?length < 12)>
											<p title="${list.name}">${list.name}</p>
										<#else>
											<p title="${list.name}">${list.name[0..9]}...</p>
										</#if>
										<p class="red">${list.price?string(priceCurrencyFormat)}</p>
									</a>
								</li>
								<#if list_index + 1 == 10>
									<#break />
								</#if>
							</#list>
						</ul>
						<div class="clearfix"></div>
					</div>
					</div>
		</div>
<!--===============================================================精品结束----------------------------->
<!--合适的产品 开始-->
	<div class="titprodu02"><strong></strong></div>
		<div>
					<div class="fitProduct">
						<div class="tit">孩子成长</div>
						<#list (homeList1)! as list>
						<div class="more"><a href="${base}/shop/article!list.action?id=${list.articleCategory.id}">更多>></a></div>
						</#list>
						<ul>
							<#list (homeList1)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 12)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
										<span class="company">${list.author}</span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
										<span class="company">${list.author}</span>
									</#if>
								</li>
								<#if list_index + 1 == 6>
									<#break/>
								</#if>
							</#list>
						</ul>
					</div>
					<div class="fitProduct">
						<div class="tit">出去旅游</div>
						<#list (homeList2)! as list>
						<div class="more"><a href="${base}/shop/article!list.action?id=${list.articleCategory.id}">更多>></a></div>
						</#list>
						<ul>
							<#list (homeList2)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 12)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
										<span class="company">${list.author}</span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
										<span class="company">${list.author}</span>
									</#if>
								</li>
								<#if list_index + 1 == 6>
									<#break/>
								</#if>
							</#list>
						</ul>
					</div>
					<div class="fitProduct nomarg">
						<div class="tit">关爱健康</div>
						<#list (homeList3)! as list>
						<div class="more"><a href="${base}/shop/article!list.action?id=${list.articleCategory.id}">更多>></a></div>
						</#list>
						<ul>
							<#list (homeList3)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 12)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
										<span class="company">${list.author}</span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
										<span class="company">${list.author}</span>
									</#if>
								</li>
								<#if list_index + 1 == 6>
									<#break/>
								</#if>
							</#list>
						</ul>
					</div>
		</div>
	
<!--合适的产品 结束-->
		<div class="blank"></div>
		<!--新闻动态&风险案例 开始-->
		<div>
					<div class="news">
						<div class="titprodu03"></div>
						<#list (homeList4)! as list>
						<div class="more"><a href="${base}/shop/article!list.action?id=${list.articleCategory.id}">更多>></a></div>
						</#list>
						<ul>
							<#list (homeList4)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 30)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
									</#if>
								</li>
								<#if list_index + 1 == 9>
									<#break/>
								</#if>
							</#list>
						</ul>
					</div>
					<div class="news nomarg">
								<div class="titprodu04"></div>
						<#list (homeList5)! as list>
						<div class="more"><a href="${base}/shop/article!list.action?id=${list.articleCategory.id}">更多>></a></div>
						</#list>
						<ul>
							<#list (homeList5)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 30)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
									</#if>
								</li>
								<#if list_index + 1 == 9>
									<#break/>
								</#if>
							</#list>
						</ul>					
					</div>
		</div>
		
		<!--新闻动态&风险案例 结束-->
		<div class="blank"></div>
		</div>
	</div>
		<div class="bodyLeft">
		<div class="blank"></div>
			<div class="hotProduct">
				<div class="top">热销排行</div>
				<div class="middle clearfix">
					<ul>
						<#list (hotProductList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.name?length < 15)>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.name}">${list.name}</a>
								<#else>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.name}">${list.name[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 5>
								<#break />
							</#if>
						</#list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="hotArticle">
				<div class="top">热点文章</div>
				<div class="middle clearfix">
					<ul>
						<#list (newArticleList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.title?length < 15)>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a>
								<#else>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 7>
								<#break/>
							</#if>
						</#list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		
		<div class="blank"></div>
		<div class="clear"></div>
		<!--投保流程 开始-->
		
		<div class="tblc"><a href="#"><img src="upload/image/tblcpic.jpg" alt="投保流程" /></a></div>
		<!--投保流程 结束-->
		<div class="blank"></div>
		<!--保险小课堂 开始-->
		<div class="tblc">
		  <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="1000" height="150">
	          <param name="movie" value="upload/flash/class.swf" />
	          <param name="quality" value="high" />
		<param name="wmode" value="opaque">
	          <embed src="upload/flash/class.swf" wmode="opaque" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="1000" height="150"></embed>
		  </object>
		</div>
		<!--保险小课堂 结束-->
		
	</div>
	<div class="body">
	<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
		<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
	
<script 
        type="text/javascript" charset="utf-8" src="http://lead.soperson.com/10007216/10007542.js">
</script>
<div id="gotopbtn"><img src="upload/image/top.gif"></img></div>
<script type="text/javascript">
backTop=function (btnId){
	var btn=document.getElementById(btnId);
	var d=document.documentElement;
	window.onscroll=set;
	btn.onclick=function (){
		btn.style.display="none";
		window.onscroll=null;
		this.timer=setInterval(function(){
			d.scrollTop-=Math.ceil(d.scrollTop*0.7);
			if(d.scrollTop==0) clearInterval(btn.timer,window.onscroll=set);
		},10);
	};
	function set(){btn.style.display=d.scrollTop?'block':"none"}
};
backTop('gotopbtn');
</script>

	
	</body>
</html>