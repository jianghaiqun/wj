<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<head>
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/new_shops.css?v=1216"/>
<!--预览页面样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/new_preview.css?v=1216" />
<!--核保页面样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />


<script language="javascript" type="text/javascript">
var orderSn = ${sdorder.orderSn};
var needUWCheckFlag = ${needUWCheckFlag};
var orderId = "${sdorder.id}";
var productId = ${sdinformation.productId};
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

<div class="PayStatusArea" align="center"><!--     支付状态  begin   -->
<#if (sdorder.orderStatus="paid")>
<div class="PayStatus_4" align="center">&nbsp;</div>
<#elseif (sdorder.orderStatus="temptorysave")>
<div class="PayStatus_3" align="center">&nbsp;</div>
<#else>
<div class="PayStatus_2" align="center">&nbsp;</div>
</#if>
</div>
<div class="wrapper">
<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />

		<#if (typeFlag !="02")!>
			<div class="line_logo">
				<a href="${siteUrl}">
					<img src="${staticPath}/images/logo_03.gif" alt="开心保保险网，一站式服务，省钱更安心" title="开心保保险网，一站式服务，省钱更安心" width="447" height="72">
				</a>
			</div>
			<div class="up_line_log">
				<div class="sdong1_up pay_end_up"></div>
			</div>
			<div class="clear"></div>
		<#else>
			<div class="line_log">
        		<div class="sdong1 pay_end"> </div>
        	</div>
		</#if>
</div>
	<div class="w900 shop_border">
		<input type ="hidden" id="insuranceCompanySn" name="insuranceCompanySn" value="${sdinformation.insuranceCompanySn}">
		
		<#if (needUWCheckFlag=="1") >
		<form id ="resultForm" action="${base}/shop/order_config_new!tpyCheckPay.action" method="post"> 
		<#else>
		<form id ="resultForm" action="${base}/shop/order_config_new!pay.action" method="post"> 
		</#if>
			<div class="line_a shop_sptitle">
		        <div class="pri ">
		        	<span class="CInsuranceCompany icon_C${sdinformation.insuranceCompany}"></span>
		        	<div class="shop_titile">${sdinformation.productName}</div> 
		        	<small class="fr">订单号：${sdorder.orderSn}</small>
		        	<input type ="hidden" id="orderSn" name="orderSn" value="${sdorder.orderSn}">
		        </div>
	  		</div>		
		<div class="lay-ins-con pro-con-info">
			<div class="ins-tit">保险期间</div>
				<table border=0 cellspacing=0 cellpadding=0 width="100%">	
					<tr>
						<td >
								<table class="tab-bd pro-tab" border=0 cellspacing=0 cellpadding=0>
									<#assign preindex = 1 />
										<#list showPeriods as list>  
													<#assign preindexnum = 1 />
													    <#list list as i>
														       <#if (preindexnum%2==1)>
																       <tr>
														       </#if>  
														               <td class="table_th_title" style="width:13%"><span>${i.showName}</span></td>
														               <td style="width:20%">${i.showValue}</td> 
														       <#if (preindexnum%2==0)>
															       </tr>
														       </#if> 
													       <#assign preindexnum=preindexnum+1 />
													    </#list> 
													<#assign preindex=preindex+1 /> 
												</table>
											</div> 
										</#list> 
								</table>
						</td>
					</tr>
				</table>
        </div>
		 
		<div class="lay-ins-con pro-con-info">
				<div class="ins-tit">投保人信息</div>
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr>
								<td  >
									<table class="tab-bd pro-tab" border=0 cellspacing=0 cellpadding=0>
									     <#assign appindex = 1 />
											<#list showAppnts as list>  
														<#assign appindexnum = 1 />
														    <#list list as i>
															       <#if (appindexnum%2==1)>
																	       <tr>
															       </#if> 
															               <td class="table_th_title"><span>${i.showName}</span></td> 
																	       <td>${i.showValue}</td>
															       <#if (appindexnum%2==0)>
																       </tr>
															       </#if> 
														       <#assign appindexnum=appindexnum+1 />
														    </#list> 
														<#assign appindex=appindex+1 /> 
													</table>
												</div> 
											</#list> 
									</table>
								</td>
							</tr>
				</table>
		</div>
		
		
		
		<div class="lay-ins-con pro-tab">
			<div class="ins-tit"> 被保险人信息</div>
				<#assign index = 1 />
					<#list showInsureds as list> 
					  <div class="con_id">
					  	<span class="pep_num">${index}</span>
							<table width="100%"  class="tab-bd pro-tab bbr_table"  cellspacing="0" cellpadding="0" border="0">
								<#assign indexnum = 1 />
								    <#list list as i>
								       <#if (indexnum%3==1)>
								       <tr>
								       </#if> 
								       <td width="130"><span>${i.showName}</span> </td>
								       <td class="boder_left" width="150">${i.showValue}</td>
								       <#if (indexnum%3==0)>
								       </tr>
								       </#if> 
								        <#if (indexnum==list?size) >
								           <#assign tnum=indexnum%3 > 
								           <#if (tnum>=1) >
								    <#list 1..(3-tnum) as x >  
								               <td width="130" ><span></span></td>
								   <td class="boder_left" width="150"></td>  
								</#list> 
								</#if>  
								       </#if> 
								       <#assign indexnum=indexnum+1 />
								    </#list> 
								<#assign index=index+1 /> 
								</tr>
							</table>
						</div> 
					</#list>
		</div>
 <#if insuredToCountry? has_content>
	 	<div class="lay-ins-con pro-tab">
			<div class=ins-tit>旅游目的地</div>
				<ul class="md_d">
			    	<li>${insuredToCountry}</li>
			    </ul>
	   </div>
</#if>	   
	<div class="lay-ins-con pro-tab">
		<div class=ins-tit>受益人</div>
		<div class="fdsyr">法定继承人</div>
    </div>
 <#if showDuty? has_content>	
	<div class="lay-ins-con pro-tab">
		<h3 class=ins-tit>保障信息</h3>
			<table class="tab-bd insurant-tab prod-proj-tab" border=0 cellspacing=0
				cellpadding=0>
				<colgroup>
					<col width="20%">
					<col width="10%">
					<col width="70%">
						<tr class=prod-proj-tab-hd>
							<td >保障项目</td>
							<td>保险金额</td>
							<td>保障内容</td>
						</tr>
						<#list showDuty as list>
							<tr>
								<td>${list.dutyName}</td>
								<td>${list.showAmnt}</td>
								<td>${list.coverage}</td>
							</tr>
						</#list>
			</table>
	</div>
</#if>
			<div class="lay-ins-con pro-tab">
			<div class=ins-tit>费用合计</div>
			<table  width="100%" border="0" class="cp_pricebox">
			  <tr>
			    <td>产品名称</td>
			    <td>保险期限</td>
			    <td>有效份数</td>
			    <td>合计保费</td>
			  </tr>
			  <tr>
			    <td>${(sdinformation.productName)!} </td>
			    <td> 
			    ${(sdinformation.ensureDisplay)!}
			    </td>
			    <td>${(insuredCount)!}份</td>
			    <td><font  color="red">${sdorder.totalAmount}</font>元</td>
			  </tr>
			</table>
			<span class="ins-int-con">共<span class="num red">${(insuredCount)!}</span>名被保险人， <span class="num red">${(insuredCount)!}</span>份保单，保费合计： 
							<span class="ui-money"><dfn>¥</dfn>${sdorder.totalAmount}</span></span>
		</div> 
		<#if (canPreviewOperatorFlag != "0") >
			<div class=ins-tit>投保声明</div>
			<div class="msg-con msg-tips"  style="padding: 10px;">
				<ol class="sz_list">
					<li>本人作为投保人已经将此保险产品全部保障内容和保险金额向被保险人做了明确说明，被保险人对此已表示完全同意。</li>
					<li>本投保人声明均已如实填写上述各项投保信息，如果信息填写不真实或不准确，愿意承担一切责任。</li>
					<li>本投保人和被保险人均已详细阅读并认可该保险产品的各项保险条款，特别是对保险条款中有关责任免除部分已经详细了解并完全认可。同时阅读并已了解开心保提示、客户告知书以及关于投保人、被保险人权利和义务等相关内容，本投保人和被保险人均已认可保险合同的全部内容。</li>
					<li>根据《中华人民共和国合同法》第十一条规定，数据电文是合法有效的合同书面形式。本投保人同意保险公司提供的电子保单或其它保险信息作为本保险合同成立生效的合法有效凭证，并具有完全证据效力。</li>
				</ol>
			</div> 
   </div>
			<div class="object_btn" style="text-align: center;">
				<div class=btn-more>
					<div class="check_btn"><input name="agreeInsure" id="agreeInsure" type="checkbox" value="" />&nbsp;我接受以上投保申明 
				</div>
				<div class=" height_s"></div>
                  <input type="button"  class="fhxg_btn" value="返回修改" onclick="location.href='${base}/shop/order_config_new!buyNowUpdate.action?orderId=${sdorder.id}&productId=${productId}&orderSn=${sdorder.orderSn}'" >  
                  <input type="button" id="qrgm_pay"  class="qrgm_btn" value="确认购买">
               </div>
			</div>
		</#if>
	</form>
</div>
</div>
<div class="clear"></div>

<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml">
<script type="text/javascript" src="${shopStaticPath}/template/common/js/json2.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/calendar.js"></script>   
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/shop.js"></script>


</body>
</html>