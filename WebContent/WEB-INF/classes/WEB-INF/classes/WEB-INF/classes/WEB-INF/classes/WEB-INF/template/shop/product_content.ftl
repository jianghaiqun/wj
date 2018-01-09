<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${product.name}</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<#if (product.metaKeywords)! != ""><meta name="keywords" content="${product.metaKeywords}" /></#if>
<#if (product.metaDescription)! != ""><meta name="description" content="${product.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/product_content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/product.js"></script>

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
	$.addProductHistory("<#if (product.name?length <= 15)>${product.name}<#else>${product.name[0..12]}...</#if>", "${base}${product.htmlFilePath}");

})


function savepdf(filename){
var ss=window.open (filename,"hoho",'left=0,top=0,directories=0,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,width=10,height=10');
ss.document.execCommand('saveas');
//ss.close();
}
</script>
</head>

<body class="productContent">
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
							</li>
							<#if (list.children != null && list.children?size > 0)>
								<#list list.children as list>
									<li>
										<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon"> </span>${list.name}</a>
										<#if (list.children != null && list.children?size > 0)>
											<ul>
												<#list list.children as list>
													<li>
														<#if (list.name?length < 15)>
															<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon"> </span>${list.name}</a>
														<#else>
															<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon"> </span>${list.name[0..12]}...</a>
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
							<#if list_index + 1 == 10>
								<#break />
							</#if>
						</#list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="productHistory">
				<div class="top">浏览记录</div>
				<div class="middle clearfix">
					<ul id="productHistoryListDetail"></ul>
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
							<a href="${base}/shop/product!list.action?id=${list.id}">${list.name}</a>>
						</#list>
					</div>
				</div>
				<div class="right"></div>
			</div>
			<div class="blank"></div>
			<div class="productTop">
				<div class="productTopLeft">
					<div class="productImage">
	                	<#list product.productImageList as list>
	                		<a href="${base}${list.bigProductImagePath}" class="tabContent zoom<#if (list_index > 0)> nonFirst</#if>">
								<img src="${staticPath}${list.smallProductImagePath}" />
							</a>
						</#list>
						<#if product.productImageList == null>
	            			<a href="${base}${systemConfig.defaultBigProductImagePath}" class="zoom">
								<img src="${staticPath}${systemConfig.defaultSmallProductImagePath}" />
							</a>
	                	</#if>
                	</div>
					<div class="thumbnailProductImage">
						<a class="prev browse" href="javascript:void(0);" hidefocus="true"></a>
						<div class="scrollable">
							<ul class="items productImageTab">
								<#if (product.productImageList == null)!>
									<li>
										<img src="${staticPath}${systemConfig.defaultThumbnailProductImagePath}" />
									</li>
	                        	</#if>
	                        	<#list (product.productImageList)! as list>
									<li>
										<img src="${staticPath}${list.thumbnailProductImagePath}" />
									</li>
								</#list>
							</ul>
						</div>
						<a class="next browse" href="javascript:void(0);" hidefocus="true"></a>
					</div>
				</div>
				<div class="productTopRight">
					<h1>
						<#if (product.name?length > 50)>${product.name[0..46]}...<#else>${product.name}</#if>
					</h1>
					<ul class="productInsAttribute">
						<#assign index = 1 />
						<#list (product.productInsType.enabledProductInsAttributeList)! as list>
							<#if (product.productInsAttributeMap.get(list) != null)!>
	                    		<li>
	                    			<strong>${list.name}:</strong>
	                				<#list (product.productInsAttributeMap.get(list))! as attributeOptionList>
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
						<!--
                        <#if (product.productInsType.productInsAttributeList != null && product.productInsType.productInsAttributeList?size > 0)!>
                        	<li><a href="#productInsAttribute" id="moreProductInsAttribute">更多参数>></a></li>
                        </#if>
                        -->
					</ul>
					<div class="blank"></div>
					<div class="productInfo">
						<div class="left"></div>
						<div class="right">
							<div class="top">
								销 售 价：<span class="price">${product.price?string(priceCurrencyFormat)}</span>
							</div>
							<div class="bottom">
								市 场 价：<span class="marketPrice">${product.marketPrice?string(priceCurrencyFormat)}</span>
							</div>
						</div>
					</div>
					<div class="blank"></div>
					<div class="productNumber">
<!----------2011.8.28  sky  修改保险公司弹出公司简介-------===================================================--->

						<div class="brandInstructionList showBrandInstructionList">
						<input type="hidden" name="productId" value="${product.id}" />
						险种号: <span>${product.productSn}</span>
						
						<span OnMouseOver="player('any');" onMouseOut="clocer('any');" style="cursor:pointer;">${("保险公司： <span>" + product.brand.name + "</span>")!}</span> 
						<div class="divbox">
						    <div id="any" class="more" OnMouseOver="player('any');" onMouseOut="clocer('any');">
						   		 ${product.brand.introduction}
						    </div>
						</div>
						<!--
						${("保险公司： <span>" + product.brand.name + "</span>")!}
						
						<ul id="brandInstructionListDetail"></ul>
						-->
						</div>
						
<!----------2011.8.28  sky  修改保险公司弹出公司简介------====================================================---->
					</div>
					<div class="blank"></div>
					<div class="productBuy">
						<!--
						<div class="buyCount">
							我要购买: <input type="text" id="quantity" value="1" /> 件
						</div>
						-->
						<!--
						添加立刻购买功能
						-->
						<input type="button" value="立刻购买"   onclick="window.open('${base}/shop/order!buyNow.action?productId=${product.id}')" hidefocus="true" />
						<#if product.isOutOfStock>
							<input type="button" class="outOfStockButton" value="" hidefocus="true" />
						<#else>
							<input type="button" class="addCartItemButton addCartItem {id: '${product.id}'}" value="" hidefocus="true" />
						</#if>
                        <input type="button" class="addFavoriteButton addFavorite {id: '${product.id}'}" value="" hidefocus="true" />
					</div>
							<br><br>	<span><a href="${base}${product.duty}"><strong><font color="red">保险条款下载</font></strong></a></span>&nbsp&nbsp&nbsp<span>右键目标另存为<span>
							<!--
							<input type="submit" name="Submit" value="条款下载测试" onClick="savepdf('${base}${product.duty}')">
							-->
							
								<br><span><a href="${base}${product.professionalPath}"><img src="${staticPath}/upload/image/btnzjdp.gif" alt="专家点评" /></a></span>
									<span><a href="${base}${product.videoPath}"><img src="${staticPath}/upload/image/btnspjs.gif" alt="视频介绍" /></a></span>
									<!--
									<span><a href="${base}${product.professionalPath}"><strong><font color="red">专家点评</font></strong></a></span>
									<span><a href="${base}${product.videoPath}"><strong><font color="red">视频介绍</font></strong></a></span>
									-->
				</div>
			</div>
			<div class="blank"></div>
			<div class="productBottom">
				<ul class="productAttributeTab">
					<li>
						<a href="javascript:void(0);" class="current" hidefocus="true">产品介绍</a>
					</li>
					<li>
						<a href="javascript:void(0);" name="productAttribute" hidefocus="true">保障条款</a>
					</li>
					<li>
						<a href="javascript:void(0);" class="current" hidefocus="true">理赔须知</a>
					</li>
				</ul>
				<div class="tabContent productDescription">
					${product.description}
				</div>
				<div class="tabContent productAttribute">
					<table class="productAttributeTable">
						<#list (product.productType.enabledProductAttributeList)! as list>
							<#if (product.productAttributeMap.get(list) != null)!>
								<tr>
									<th>${list.name}</th>
									<td>
										<#list (product.productAttributeMap.get(list))! as attributeOptionList>
											${attributeOptionList} 
										</#list>
									</td>
								</tr>
							</#if>
						</#list>
					</table>
				</div>
				<div class="tabContent productDescription">
					<span><a href="${base}${product.app}"><strong><font color="red">理赔申请书下载</font></strong></a></span>&nbsp&nbsp&nbsp<span>右键目标另存为<span>
					${product.compensation}
				</div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>