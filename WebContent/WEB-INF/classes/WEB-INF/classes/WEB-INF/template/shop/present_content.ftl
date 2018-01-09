<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${present.name}</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<#if (present.metaKeywords)! != ""><meta name="keywords" content="${present.metaKeywords}" /></#if>
<#if (present.metaDescription)! != ""><meta name="description" content="${present.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/present.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/present_content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/present.js"></script>

<style>
.more{
display:none;
background-color:#fdfef9;
width:300px;
line-height: 25px;
position: absolute;
color:#000;
border:#589021 2px solid;
font-size:12px;
padding:10px 5px;
position:absolute;
line-height:22px;
}
.divbox{/*提供一个相对节点，让弹出层相对它绝对定位；*/
height:0px;
line-height:0;
font-size:0;
position:relative;
}
</style>
<script type="text/javascript">
function player(id){
var id=document.getElementById(id)
id.style.display="block";
}
function clocer(id){
var id=document.getElementById(id)
id.style.display="none";
}
</script>


<script type="text/javascript">
$().ready( function() {
	
	// 添加商品浏览记录
	$.addPresentHistory("<#if (present.name?length <= 15)>${present.name}<#else>${present.name[0..12]}...</#if>", "${base}${present.htmlFilePath}");

})


function savepdf(filename){
var ss=window.open (filename,"hoho",'left=0,top=0,directories=0,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,width=10,height=10');
ss.document.execCommand('saveas');
//ss.close();
}
</script>
</head>

<body class="presentContent">
<script type="text/javascript" src="http://v2.jiathis.com/code/jiathis_r.js?move=0&amp;btn=r5.gif" charset="utf-8"></script>
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
			<input type="button" class="formButton tipClose" value="继续购物" hidefocus="true" />
			<input type="button" class="formButton" onclick="location.href='${base}/shop/order!info.action'" value="进入购物车" hidefocus="true" />
		</div>
		<div class="bottom"></div>
	</div>
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="bodyLeft">
			<div class="presentCategory">
            	<div class="top">礼品分类</div>
            	<div class="middle clearfix">
            		<ul class="menu">
            			<#list rootPresentCategoryList as list>
            				<li class="mainCategory">
            				<#if (list.name = "礼品中心")>
								<a href="${base}/shop/present!listhome.action?id=${list.id}">${list.name}</a>
							<#else>
								<a href="${base}/shop/present!list.action?id=${list.id}">${list.name}</a>
							</#if>
							</li>
							<#if (list.children != null && list.children?size > 0)>
								<#list list.children as list>
									<li>
										<a href="${base}/shop/present!list.action?id=${list.id}"><span class="icon"> </span>${list.name}</a>
										<#if (list.children != null && list.children?size > 0)>
											<ul>
												<#list list.children as list>
													<li>
														<#if (list.name?length < 15)>
															<a href="${base}/shop/present!list.action?id=${list.id}"><span class="icon"> </span>${list.name}</a>
														<#else>
															<a href="${base}/shop/present!list.action?id=${list.id}"><span class="icon"> </span>${list.name[0..12]}...</a>
														</#if>
													</li>
												</#list>
											</ul>
										</#if>
									</li>
									<#if list_index + 1 == 5>
										<#break />
									</#if>
								</#list>
							</#if>
							<#if list_index + 1 == 3>
								<#break />
							</#if>
            			</#list>
					</ul>
            	</div>
                <div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="hotPresent">
				<div class="top">热销排行</div>
				<div class="middle clearfix">
					<ul>
						<#list (hotPresentList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.name?length < 15)>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.name}">${list.name}</a>
								<#else>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.name}">${list.name[0..12]}...</a>
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
			<div class="blank"></div>
			<div class="presentHistory">
				<div class="top">浏览记录</div>
				<div class="middle clearfix">
					<ul id="presentHistoryListDetail"></ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="listBar">
				<div class="left"></div>
				<div class="middle">
					<div class="path">
						<a href="${base}/" class="home"><span class="icon"> </span>首页</a>>
						<#list pathList as list>
							<a href="${base}/shop/present!list.action?id=${list.id}">${list.name}</a>>
						</#list>
					</div>
				</div>
				<div class="right"></div>
			</div>
			<div class="blank"></div>
			<div class="presentTop">
				<div class="presentTopLeft">
					<div class="presentImage">
	                	<#list present.presentImageList as list>
	                		<a href="${base}${list.bigPresentImagePath}" class="tabContent zoom<#if (list_index > 0)> nonFirst</#if>">
								<img src="${staticPath}${list.smallPresentImagePath}" />
							</a>
						</#list>
						<#if present.presentImageList == null>
	            			<a href="${base}${systemConfig.defaultBigPresentImagePath}" class="zoom">
								<img src="${staticPath}${systemConfig.defaultSmallPresentImagePath}" />
							</a>
	                	</#if>
                	</div>
					<div class="thumbnailPresentImage">
						<a class="prev browse" href="javascript:void(0);" hidefocus="true"></a>
						<div class="scrollable">
							<ul class="items presentImageTab">
								<#if (present.presentImageList == null)!>
									<li>
										<img src="${staticPath}${systemConfig.defaultThumbnailPresentImagePath}" />
									</li>
	                        	</#if>
	                        	<#list (present.presentImageList)! as list>
									<li>
										<img src="${staticPath}${list.thumbnailPresentImagePath}" />
									</li>
								</#list>
							</ul>
						</div>
						<a class="next browse" href="javascript:void(0);" hidefocus="true"></a>
					</div>
				</div>
				<div class="presentTopRight">
					<h1>
						<#if (present.name?length > 50)>${present.name[0..46]}...<#else>${present.name}</#if>
					</h1>
					<ul class="presentInsAttribute">
						<#assign index = 1 />
						<#list (present.presentInsType.enabledPresentInsAttributeList)! as list>
							<#if (present.presentInsAttributeMap.get(list) != null)!>
	                    		<li>
	                    			<strong>${list.name}:</strong>
	                				<#list (present.presentInsAttributeMap.get(list))! as attributeOptionList>
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
					<div class="blank"></div>
					<div class="presentInfo">
						<div class="left"></div>
						<div class="right">
							<div class="bottom">
								市 场 价：<span class="marketPrice">${present.marketPrice?string(priceCurrencyFormat)}</span>
							</div>
						</div>
					</div>
					<div class="blank"></div>
					<div class="presentNumber">
					</div>
					<div class="blank"></div>
					<div class="presentBuy">
						<!--
						<div class="buyCount">
							我要购买: <input type="text" id="quantity" value="1" /> 件
						</div>
						-->
						<!--
						添加立刻购买功能
						-->
						<input type="button" value="立刻购买"   onclick="window.open('${base}/shop/order!buyNow.action?presentId=${present.id}')" hidefocus="true" />
						<#if present.isOutOfStock>
							<input type="button" class="outOfStockButton" value="" hidefocus="true" />
						<#else>
							<input type="button" class="addCartItemButton addCartItem {id: '${present.id}'}" value="" hidefocus="true" />
						</#if>
                        <input type="button" class="addFavoriteButton addFavorite {id: '${present.id}'}" value="" hidefocus="true" />
					</div>
				</div>
			</div>
			<div class="blank"></div>
			<div class="presentBottom">
				<ul class="presentAttributeTab">
					<li>
						<a href="javascript:void(0);" class="current" hidefocus="true">礼品介绍</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>