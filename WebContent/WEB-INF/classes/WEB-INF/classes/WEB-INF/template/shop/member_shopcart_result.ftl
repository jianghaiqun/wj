<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>

<script language="javascript" type="text/javascript">
var orderSn = '${sdorder.orderSn}';
var needUWCheckFlag = '${needUWCheckFlag}';
var orderId = "${sdorder.id}";
var productId = '${sdinformation.productId}';
var productExcelFlag = "${(productExcelFlag)!}";
var p_url = "${jrhsURL}";
var productExcelFlag = "${(productExcelFlag)!}";
var loginFlag = "${loginFlag}";
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/block/kxb_header_index_new_v2.shtml">
</#if> 
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
	<input type="hidden" id="orderFlag" name="orderFlag" value="${orderFlag}"/>
	
	<#if (typeFlag !="02")!>
			<div class="line_logo">
				<a href="${siteUrl}">
					<img src="${staticPath}/images/logo_03.gif" alt="开心保保险网，一站式服务，省钱更安心" title="开心保保险网，一站式服务，省钱更安心" width="447" height="72">
				</a>
			</div>
			<div class="up_line_log">
				<#if (sdorder.orderStatus="paid")>
					<div class="sdong1_up pay_end_up"> </div>
				<#else>
    				<div class="sdong_up yl_bd_up"></div>
    			</#if> 
			</div>
			<div class="clear"></div>
		<#else>
			<div class="line_log">
			<#if (sdorder.orderStatus="paid")>
				<div class="sdong1 pay_end"> </div>
			<#else>
    			<div class="sdong yl_bd"></div>
    		</#if> 
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
								<#assign indexflag = 0 />
								    <#list list as i>
								    <#assign indexflag = indexflag+1 />
								       <#if (indexnum%3==1)>
								            <#if (indexnum%2==0)>
								            <tr style="background:#E6E6E6">
								            <#else>
								            <tr>
								            </#if>
								       </#if> 
								       <td width="130"><span>${i.showName}</span> </td>
								       <td width="150">${i.showValue}</td>
								       <#if (indexnum%3==0)>
								       </tr>
								       </#if> 
								        <#if (indexnum==list?size) >
								           <#assign tnum=indexnum%3 > 
								           <#if (tnum>=1) >
								           		<#list 1..(3-tnum) as x >  
									               <td width="130" ><span></span></td>
									               <td width="150"></td>  
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
		<#if showPropertys? has_content>
		<div class="lay-ins-con pro-tab">
			<div class="ins-tit"> 财产信息</div>
				<#assign index = 1 />
				<div class="con_id">
				<table width="100%"  class="tab-bd pro-tab bbr_table"  cellspacing="0" cellpadding="0" border="0">
					<#list showPropertys as list> 
						<#if (index%3==1)>
				            <#if (index%2==0)>
				            	<tr style="background:#E6E6E6">
				            <#else>
				            	<tr>
			            	</#if>
		            	</#if> 
		            	<td width="130"><span>${list.showName}</span> </td>
		            	<td width="150">${list.showValue}</td>
		            	<#if (index%3==0)>
				       		</tr>
			       		</#if> 
			       		<#if (index==showPropertys?size) >
				           <#assign tnum=index%3 > 
				           <#if (tnum>=1) >
				           		<#list 1..(3-tnum) as x >  
					           		<td width="130" ><span></span></td>
					           		<td width="150"></td>  
				           		</#list> 
			           		</#if> 
		           		</#if> 
			       		<#assign index=index+1 /> 
		       		</#list>
		       		</tr>
				</table>
				</div>
		 	</div>
	 	</div>
	 </#if>
 <#if insuredToCountry? has_content>
	 	<div class="lay-ins-con pro-tab">
			<div class=ins-tit>旅游目的地</div>
				<ul class="md_d">
			    	<li>${insuredToCountry}</li>
			    </ul>
	   </div>
</#if>	 
<#if sdinformation.riskType!="11">  
	<div class="lay-ins-con pro-tab">
		<div class=ins-tit>受益人</div>
		<div class="fdsyr">法定继承人</div>
    </div>
</#if>    
 <div style="display:none;">
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
</div>
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
			<span class="ins-int-con">共<span class="num red">${(insuredResultCount)!}</span>名被保险人， <span class="num red">${(insuredCount)!}</span>份保单，保费合计： 
							<span class="ui-money"><dfn>￥</dfn>${sdorder.totalAmount}</span></span>
		</div> 
			<div class="object_btn" style="text-align: center;">
				<div class=" height_s"></div>
                  <input type="button"  class="fhxg_btn" value="返回修改" onclick="location.href='${base}/shop/order_config_new!buyNowUpdate.action?orderSn=${sdorder.orderSn}&orderFlag=ShopCart'" >  
                  <input type="button" id="backShopCart"  class="fhxg_btn" value="返回购物车" onclick="location.href='${base}/shop/member_shopcart!shopCartPreview.action'">
               </div>
			</div>
	</form>
</div>
</div>
<div class="clear"></div> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
<#include "/wwwroot/kxb/block/community_v1.shtml">
<script	type="text/javascript" src="${shopStaticPath}/iframeTools.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/json2.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/calendar.js"></script>   
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/shop.js"></script>
</body>
</html>