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
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/index.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/shop/css/product_list.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/shop/css/article.css" rel="stylesheet" type="text/css" />
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
								<a href="${base}/shop/product!list.action?id=${list.id}">${list.name}</a>
							
						
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
				<tr id='insurednameTr1'>
					<th align="right" valign="top">
						测试:
					</th>
					<td>
					123
					<#list (allInsuranceNames)! as list>
					${list}
					</#list>
					</td>
				</tr>
				<tr id='insurednameTr'>
					<th align="right" valign="top">
						为谁投保:
					</th>
					<td>
						<select id="insuredname" name="customerDemand.insuredname" onchange="change();">
						<option value="">请选择...</option>
							<#list allInsuranceNames as list>
								<option value="${list}">
								${list}
								</option>
							</#list>
						</select>
					</td>
				</tr>
								<tr id='测试下拉框'>
					<th align="right" valign="top">
						测试下拉框:
					</th>
					<td>
<select name="ClassLevel1"  id="ClassLevel1">
<option value="74">电脑/网络</option>
<option value="80">生活</option>
<option value="79">医疗健康</option>
<option value="78">体育/运动</option>
<option value="95">电子数码</option>
<option value="82">商业/理财</option>
<option value="83">教育/科学</option>
<option value="84">社会民生</option>
<option value="85">文化/艺术</option>
<option value="77">游戏</option>
<option value="1031">娱乐休闲</option>
<option value="81">烦恼</option>
<option value="1101">资源共享</option>
<option value="1">地区</option>
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
								<option value="${list}"<#if (list == customerDemand.insurancetype)!> selected</#if>>
									${action.getText(list)}
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
								<option value="${list}"<#if (list == customerDemand.guaranteeperiod)!> selected</#if>>
									${action.getText(list)}
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
		<div class="titprodu"><strong>精品推荐</strong></div>
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
								<#if list_index + 1 == 12>
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
	<div class="titprodu"><strong>合适的产品</strong></div>
		<div>
					<div class="fitProduct">
						<div class="tit">孩子成长</div>
						<div class="more"><a href="#">更多>></a></div>
						<ul>
							<#list (recommendArticleList)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 12)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
										<span class="company">${list.author}</span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
										<span class="company">${list.author}</span>
									</#if>
								</li>
								<#if list_index + 1 == 18>
									<#break/>
								</#if>
							</#list>
						</ul>
					</div>
					<div class="fitProduct">
						<div class="tit">出去旅游</div>
						<div class="more"><a href="#">更多>></a></div>
						<ul>
							<#list (recommendArticleList)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 12)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
										<span class="company">${list.author}</span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
										<span class="company">${list.author}</span>
									</#if>
								</li>
								<#if list_index + 1 == 18>
									<#break/>
								</#if>
							</#list>
						</ul>
					</div>
					<div class="fitProduct nomarg">
						<div class="tit">关爱健康</div>
						<div class="more"><a href="#">更多>></a></div>
						<ul>
							<#list (recommendArticleList)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 12)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
										<span class="company">${list.author}</span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
										<span class="company">${list.author}</span>
									</#if>
								</li>
								<#if list_index + 1 == 18>
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
						<div class="tit">新闻动态</div>
							<div class="more"><a href="#">更多>></a></div>
							<h3><a href="#">一系列投资新规酝酿出台</a></h3>
							<p class="alincenter"><a href="#">[保险业总资产再现月度负增长]</a><a href="#">[社保卡今后可作银行借记卡]</a></br><a href="#">[中国太保上半年净利润58亿]</a><a href="#">[史玉柱炮轰中国人寿]</a></p>
						<ul>
							<#list (recommendArticleList)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 30)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
									</#if>
								</li>
								<#if list_index + 1 == 18>
									<#break/>
								</#if>
							</#list>
						</ul>
					</div>
					<div class="news nomarg">
								<div class="tit">风险案例</div>
								<div class="more"><a href="#">更多>></a></div>
								<h3><a href="#">企业高管-团体医疗保险</a></h3>
								<p class="alinleft"><a href="#">客户资料：陈先生，30岁，经理，月均收入20000元</br>年缴保费：6000元...</a></p>
						<ul>
							<#list (recommendArticleList)! as list>
								<li class="number${list_index + 1}">
									<#if (list.title?length < 30)>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a></span>
									<#else>
										<span class="cont"> <a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..10]}...</a></span>
									</#if>
								</li>
								<#if list_index + 1 == 18>
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
							<#if list_index + 1 == 10>
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
							<#if list_index + 1 == 10>
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
	          <embed src="upload/flash/class.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="1000" height="150"></embed>
		  </object>
		</div>
		<!--保险小课堂 结束-->
		
	</div>

		<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
	

</body>
</html>