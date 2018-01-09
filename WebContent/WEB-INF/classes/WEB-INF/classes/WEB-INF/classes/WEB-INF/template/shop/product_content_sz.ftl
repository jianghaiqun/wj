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
<link href="${shopStaticPath}/template/shop/sz/css/main.css" rel="stylesheet" type="text/css" />
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

<script> 
<!-- 
/*第一种形式 第二种形式 更换显示样式*/ 
function setTab(name,cursel,n){ 
for(i=1;i<=n;i++){ 
var menu=document.getElementById(name+i); 
var con=document.getElementById("con_"+name+"_"+i); 
menu.className=i==cursel?"hover":""; 
con.style.display=i==cursel?"block":"none"; 
} 
} 
//--> 
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
						<li class="link01on"><span></span></li>
						<li class="link02"><span><a href="helplist.html"></a></span></li>
						<li class="link03"><span><a href="knowledgelist.html"></a></span></li>
					</ul>
				<div class="navbgright"></div>
			</div>
			<div class="bgwhiteline"></div>
		</div>
	</div>
    <div class="wrap">
		<div class="wrapa">
    		<div class="bread"><span>首页</span><span>></span><span>产品中心</span>> <span>保险产品</span><span>></span><span>意外保险</span><span>></span><span>产品详细</span></div>
			<div class="wrapb">
				<div class="left02">
					<div class="promenu">
						<h3>推荐产品</h3>
						<ul>
							<#list (hotProductList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.name?length < 15)>
									<span class="icon"> </span><span>·</span><a href="${base}${list.szHtmlFilePath}" title="${list.name}">${list.name}</a>
								<#else>
									<span class="icon"> </span><span>·</span><a href="${base}${list.szHtmlFilePath}" title="${list.name}">${list.name[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 10>
								<#break />
							</#if>
						</#list>
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
						<div class="produli02 noline">
							<div class="produlileft02">
							<#list product.productImageList as list>
	                		<a href="${base}${list.bigProductImagePath}" class="tabContent zoom<#if (list_index > 0)> nonFirst</#if>">
								<img width="180" src="${staticPath}${list.smallProductImagePath}" />
							</a>
							</#list>
							<#if product.productImageList == null>
		            			<a href="${base}${systemConfig.defaultBigProductImagePath}" class="zoom">
									<img width="180" src="${staticPath}${systemConfig.defaultSmallProductImagePath}" />
								</a>
		                	</#if>
	                		
							</div>
							<div class="produliright02">
								<h3><#if (product.name?length > 50)>${product.name[0..46]}...<#else>${product.name}</#if></h3>
								<p class="suitp2"><span class="orange">适合人群：</span>${product.crowd}</p>
								<p class="sdate2"><span class="orange">保障期限：</span>${product.timeLimit}</p>
								<p><span class="orange">产品特点：</span>${product.specialty}</p>
								<p><span class="orange">保障利益：</span>${product.benefit}</p>
								<p><span class="orange">投保前阅读：</span><span class="blue"><a href="#">保险条款</a></span>&nbsp;<span class="blue"><a href="#">职业分类表</a></span></p>
								<p class="sbtn3"><a href="#"  onclick="window.open('${base}/shop/order!szBuyNow.action?productId=${product.id}')"><img src="${shopStaticPath}/template/shop/sz/images/btn06.gif" width="106" height="35" border="0" /></a></p>
							</div>
							<div class="clear"></div>
						</div>
						<div class="tabbox02"> 
                <div class="tab"> 
                    <ul> 
                        <li id="one1" onMouseOver="setTab('one',1,4)" class="hover">产品介绍</li> 
                        <li id="one2" onMouseOver="setTab('one',2,4)">产品卖点</li> 
                        <li id="one3" onMouseOver="setTab('one',3,4)">案例分析</li>
						<li id="one4" onMouseOver="setTab('one',4,4)">购买指南</li>
                    </ul>
                    <div class="clear"></div>
                    <div class="tabbg01"></div>
                </div> 
                <div class="tabcont"> 
                    <div class="tabc02" id="con_one_1">
                        <h3>产品说明</h3>
						<table width="656" cellspacing="0" cellpadding="0" class="table01">
						<tbody>
  							<tr>
    							<td width="140" align="right" valign="top" class="orange">产品种类：</td>
    							<td width="514">健康保障型保险</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">产品特点：</td>
    							<td>${product.specialty}</td>
  							</tr>
						</tbody>
						</table>
						<h3>投保须知</h3>
						<table width="656" cellspacing="0" cellpadding="0" class="table01">
						<tbody>
							<tr>
								<td width="140" align="right" valign="top" class="orange">被保险人年龄：</td>
								<td width="514">18周岁至50周岁</td>
							</tr>
							<tr>
								<td align="right" valign="top" class="orange">缴费期间：</td>
								<td>10年缴</td>
							</tr>
							<tr>
								<td align="right" valign="top" class="orange">缴费方式：</td>
								<td>年缴</td>
							</tr>
						</tbody>
						</table>
                    </div>
                    <div class="tabc02" id="con_one_2" style="display:none;">
                        <table width="656" cellspacing="0" cellpadding="0" class="table01">
						<tbody>
  							<tr>
    							<td width="140" align="right" valign="top" class="orange">投保年龄：</td>
    							<td width="514">投保人与被保险人必须为同一人，且投保年龄为18-50周岁之间。
</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">保险费：</td>
    							<td>首次保险费不得低于500元，同一投保人累计基本保险金额不超过500000元。投保时请使用投保人本人的缴费账户。
</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">阅读保险条款：
</td>
    							<td>首次保险费不得低于500元，同一投保人累计基本保险金额不超过500000元。投保时请使用投保人本人的缴费账户。
</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">保险费：</td>
    							<td>首次保险费不得低于500元，同一投保人累计基本保险金额不超过500000元。投保时请使用投保人本人的缴费账户。
</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">保险费：</td>
    							<td>首次保险费不得低于500元，同一投保人累计基本保险金额不超过500000元。投保时请使用投保人本人的缴费账户。
</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">保险费：</td>
    							<td>首次保险费不得低于500元，同一投保人累计基本保险金额不超过500000元。投保时请使用投保人本人的缴费账户。
</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">保险费：</td>
    							<td>首次保险费不得低于500元，同一投保人累计基本保险金额不超过500000元。投保时请使用投保人本人的缴费账户。
</td>
  							</tr>
							<tr>
    							<td align="right" valign="top" class="orange">保险费：</td>
    							<td>首次保险费不得低于500元，同一投保人累计基本保险金额不超过500000元。投保时请使用投保人本人的缴费账户。
</td>
  							</tr>
						</tbody>
						</table>
                    </div>
                    <div class="tabc02" id="con_one_3" style="display:none;">
                        案例分析
                    </div>
					<div class="tabc02" id="con_one_4" style="display:none;">
                        购买指南
                    </div>
                </div> 
			</div>
						<div class="clear"></div>
				  </div>
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