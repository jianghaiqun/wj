<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${(systemConfig.shopName)!}</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<#if (systemConfig.metaKeywords)! != ""><meta name="keywords" content="${systemConfig.metaKeywords}" /></#if>
<#if (systemConfig.metaDescription)! != ""><meta name="description" content="${systemConfig.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/index.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/product.css" rel="stylesheet" type="text/css" />

<link href="${shopStaticPath}/template/shop/css/product_list.css" rel="stylesheet" type="text/css" />

<link href="${shopStaticPath}/template/shop/css/article.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/index.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/product.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/customer_demand.js"></script>
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
								<ul>
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
							<input type="text" value="朱勇" id="customername" name="customerDemand.customername" class="formText {required: true, customername: true}" onblur="change()" />
							<label class="requireField red">*</label>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						联系电话:
					</th>
					<td>
							<input type="text" value="13666368773" id="phone" name="customerDemand.phone" class="formText {required: true, phone: true}" onblur="change()" />
							<label class="requireField red">*</label>
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">
						E-Mail:
					</th>
					<td>
							<input type="text" value="123@163.com" id="email" name="customerDemand.email" class="formText {required: true, email: true}" onblur="change()" />
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
							<input type="text" value="13" id="insuredage" name="customerDemand.insuredage" class="formText {required: true, insuredage: true}"  onblur="change()"/>
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
<!--===============================================================精品开始----------------------------->
		<div class="titprodu"><strong>精品推荐</strong></div><div class="more"><a href="${base}">返回首页>></a></div>
		<div class="bestProduct">
					<div class="middle">
						<ul>
							<#list (bestProductList)! as list>
								<li>
									<a href="${base}${list.htmlFilePath}">
										<img src="${staticPath}${(list.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" alt="${list.name}" />
										<#if (list.name?length < 12)>
											<p title="${list.name}">${list.name}</p>
										<#else>
											<p title="${list.name}">${list.name[0..9]}...</p>
										</#if>
										<p class="red">${list.price?string(priceCurrencyFormat)}</p>
									</a>
								</li>
								<#if list_index + 1 == 1000>
									<#break />
								</#if>
							</#list>
						</ul>
						<div class="clearfix"></div>
					</div>
					</div>
		</div>
<!--===============================================================精品结束----------------------------->

		<div class="blank"></div>
		</div>
	</div>
		
		<div class="blank"></div>
		
	</div>

		<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
	

</body>
</html>