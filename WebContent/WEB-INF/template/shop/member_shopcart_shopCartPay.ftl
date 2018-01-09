<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保_非车险_支付列表</title>
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>

<!--支付页面样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<style>
/*update artDialog win*/
.aui_buttons button {display: block; margin:0 auto;}
.aui_buttons{background: none;  border-top:none;}
</style>
<script type="text/javascript" src="${shopStaticPath}/artDialog.js"></script>

<script type="text/javascript">
	function dosubmit(tmp){
		if(tmp == null || tmp == ''){
			return false;
		}
		document.getElementById("payType").value = tmp;
		
		jQuery.ajax({
			type: 'post',
			url: sinosoft.base+'/shop/pay!isOutPeriod.action?orderId='+jQuery("#OrdId").val(),
			dataType: "json",
			async: false,
			success: function(data) {
				var orderInfo = "";

				if (data[0].length <= 0) {
					jQuery.blockUI({ 
						"message":jQuery("#paytippopdiv"),
						"css":{ 
						       "width": jQuery("#paytippopdiv").width(),
						       "height":jQuery("#paytippopdiv").height()
						   }
					});
						
					document.forms["payment_order"].submit();
					
				} else {
					if (data[0][0].status == "error") {
						art.dialog({
							title: "错误提示",
   							content: "校验起保日期出错："+data[0][0].message
						});
						return;
					}
					
					if (data[0][0].status == "false") {
						art.dialog({
							title: "错误提示",
   							content: "订单出错："+data[0][0].message
						});
						return;
					}
					
					orderInfo = "<table class='pay-tip-table'><tr><th class='order-tip-th'>订单号</th><th class='order-tip-th order-btd-rbor'>说明</th></tr>";
					
					var modifyFlag = "0";
					for (var i = 0; i < data[0].length; i++) {
						orderInfo += "<tr>";
						if (data[0][i].status == "timeout") {
							modifyFlag = "1";
							orderInfo += "<td>"+data[0][i].OrderId+"<br>"+data[0][i].ProductName+"</td>";
							orderInfo += ("<td class='order-btd-rbor'><p class='Content content-csf'>您选择的起保日期：<span class='span_light_l'>"
										+ data[0][i].start + "</span><br>");
							if (data[0][i].period == null || data[0][i].period == '') {
								orderInfo += "小于&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前日期：<span class='span_light_l'>"+data[0][i].now + "</span><br>";
							} else {
								orderInfo += ("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前日期：<span class='span_light_l'>" 
											+ data[0][i].now + "</span><br>不满足该产品起保日期间隔<span class='span_light_l'>" + data[0][i].period + " 天</span> <br>");	
							}
							orderInfo += "<b class='pay_tip_time_b'>请您修改起保日期，谢谢！</b></p></td>"
	
						} else if (data[0][i].status == "currBeTimeout") {
							modifyFlag = "1";
							orderInfo += "<td>"+data[0][i].OrderId+"<br>"+data[0][i].ProductName+"</td>";
							orderInfo += ("<td class='order-btd-rbor'><p class='Content content-csf'> 离您选择的终止时间：<span class='span_light_l'>"
										+ data[0][i].start + "</span>还剩余<span class='span_light_l'>" + data[0][i].period
										+ "分钟</span>，<br>建议您选择<span class='span_light_l'>" + data[0][i].start +
										"</span>&nbsp;&nbsp;以确保有充裕的时间完成支付和承保。</p></td>");
						
						} else if (data[0][i].status == "beTimeout") {
							if (modifyFlag == "0") {
								modifyFlag = data[0][i].isModify;
							}
							orderInfo += "<td>"+data[0][i].OrderId+"<br>"+data[0][i].ProductName+"</td>";
							orderInfo += ("<td class='order-btd-rbor'><p class='Content content-csf'> 离您选择的起保日期：<span class='span_light_l'>"
										+ data[0][i].start + "</span>还剩余<span class='span_light_l'>" + data[0][i].period
										+ "分钟</span>，<br>建议您选择<span class='span_light_l'>" + data[0][i].newStart +
										"</span>&nbsp;&nbsp;以确保有充裕的时间完成支付和承保。</p></td>");
	
						}else if (data[0][i].status == "morethaninsured") {
							orderInfo += "<td class='order-btd-rbor'>"+data[0][i].OrderId+"<br>"+data[0][i].ProductName+"</td>";
							orderInfo += ("<td><p class='Content content-csf'>本次需承保人数多于20人,离您选择的起保日期：<span class='span_light_l'>"
										+ data[0][i].start + "</span>还剩余不足15分钟,此时支付可能正常承保，请选择</p></td>");
						}
						orderInfo += "</tr>";
					}
					orderInfo += "</table>";
					jQuery("#modifyFlag").val(modifyFlag);
					jQuery("#orderInfo").html(orderInfo);
					var height = (200 + (data.length - 1)*50)+"px";
					jQuery.blockUI({ 
						"message":jQuery("#timeoutdiv"),
						"css":{ 
							   "width": jQuery("#timeoutdiv").width(),
							   "height": jQuery("#timeoutdiv").height(),
							   "top":'27%'
							  }
					});
				}
			}
		});
	}
	
	function modifyStartDate() {
		window.location.href = "${base}/shop/member_shopcart!getShopCartINF.action";
	}
	
	function continueSubmit(){
		jQuery.blockUI({ 
			"message":jQuery("#paytippopdiv"),
			"css":{ 
				"width": jQuery("#paytippopdiv").width(),
				"height":jQuery("#paytippopdiv").height()
			}
		});
						
		document.forms["payment_order"].submit();
	}
	
	function timeoutBack() {
		if (jQuery("#front").val() == "") {
			window.location.href = "${base}/shop/order_query!queryOrder.action";
		}else {
			window.location.href = jQuery("#front").val();
		}	
	}
	
	function doclose(){
		jQuery.unblockUI();
		window.location.reload(); 
	}
	
	function docallBack(){
		jQuery.ajaxLoadImg.show('showid');
	    var p_price=jQuery("#p_price").text();
		if(p_price==''||p_price==null){
			window.location.href = "${base}/shop/pay!doCallBack.action?paySn=${paySn}&payType="+jQuery("#payType").val()+"&OrdId=${ordIDs}&payPrice="+parseFloat(jQuery("#total_pay_price").text());
		}else{
			window.location.href = "${base}/shop/pay!doCallBack.action?paySn=${paySn}&payType="+jQuery("#payType").val()+"&OrdId=${ordIDs}&payPrice="+parseFloat(p_price);
		}
	}
	
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	<form action="pay!showShopCart.action" target="_blank" id="payment_order" name="payment_order">
	<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
	<input type="hidden" name="payType" id="payType" value="1"/>
	<input type="hidden" name="paySn" id="paySn" value="${paySn}"/>
	<input type="hidden" name="OrdId" id="OrdId" value="${ordIDs}"/>
	<input type="hidden" name="KID" value="${KID}"/>
	<input type="hidden" name="CouponSn"  id="CouponSn" value="0"/>
	<input type="hidden" name="offsetPoint"  id="offsetPoint" value="0"/>
	<input type = "hidden" id="PointScalerUnit" value="200"/>
	<div class="re-line-log">
        <ul class="re-line-ul">
            <li class="re-line-d"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
            <li class="re-line-d"><div class="re-linehead re-linehead4"><span></span></div><h3 class="re-line-h3">2、查看购物车<p>加入购物车，多订单一起付款</p></h3><span class="re-line-jiao"></span></li>
            <li class="re-line-s"><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>积分折扣、支持多种支付方式</p></h3><span class="re-line-jiao"></span></li>
        </ul>
    </div>
    <div class="clear"></div>
	<div class="w900 order-bg">
		<div class="line_a shop_sptitle ">
        	<div class="pay_box">
	 			<div class="ins-tit un_bot_bor">支付清单</div>
	 			
	 				<#list activityMap?keys as itemKey>
						<#assign map_activity = activityMap[itemKey]>
						<#list map_activity?keys as key_activity>
							<#assign map_info = map_activity[key_activity]>
							<#list map_info?keys as key_product>
								<#assign activityInfo = map_info["ActivityInfo"]>
								<#if (key_product =="ProductInfo")>
								<#assign list_product = map_info[key_product]>
									<#list list_product as map_product>
				<table width="100%" border="1" >
					<tr>
     					<th><span class="ins-tit-c">订单号</span></th>
     					<th colspan="4" ><span class="ins-tit-ordernum">${map_product.OrderSn}</span></th>
     				</tr>
     				<tr>
						<td width="15%" class="ins-tit-td">投保人</td>
						<td class="ins-tit-td">产品名称</td>
						<td width="15%" class="ins-tit-td">份数</td>
						<#if (map_product.ActivityFlag == "0")>
							<td width="15%" class="ins-tit-td">费用合计</td>
						<#else>
							<td width="15%" class="ins-tit-td">原始价格</td>
							<td width="15%" class="ins-tit-td">优惠后价格</td>
						</#if>
					</tr>
     				<tr>
						<td>${map_product.AppntName}</td>
						<td >${map_product.ProductName}</td>
						<td >${map_product.RecognizeeMul}</td>
						<#if (map_product.ActivityFlag == "0")>
							<td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_product.ProductTotalPrice}</font>
							<#if ((map_product.ComanyCode == '0007' && map_product.riskType == '02') || map_product.ComanyCode == '2034')><span class="gray-color">（含6%增值税）</span></#if>
							<span class="yhj_yhts" id="yhj_tb_des"></span></td>
						<#else>
						    <td class="pay_zk_money">￥${map_product.ProductTotalPrice}</td>
						    <#if (map_info.ActivityInfo.type =="6")>
						    	<td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_product.ActivityeAmount}</font><#if ((map_product.ComanyCode == '0007' && map_product.riskType == '02') || map_product.ComanyCode == '2034')><span class="gray-color">（含6%增值税）</span></#if><span class="yhj_yhts" id="yhj_tb_des"></span></td>
							<#else>
							    <td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_product.Amount}</font><#if ((map_product.ComanyCode == '0007' && map_product.riskType == '02') || map_product.ComanyCode == '2034')><span class="gray-color">（含6%增值税）</span></#if><span class="yhj_yhts" id="yhj_tb_des"></span></td>
							</#if>
						</#if>
					</tr>
					<tr>
						<#if (activityInfo.type == "6")>
							<td colspan="5" class="clear-td-bor">
								<div class="yh_mew_list"><ul class="at_list"><li>
								<span class="tb_icon active_02">${activityInfo.typeName}</span><span class="tb_text">${activityInfo.description}</span>
								</li></ul><div class="clear"></div></div></td>
						<#elseif (activityInfo.type == "3" && map_info.ActivityAmont.DiscountAmount != '0.00')>
							<td colspan="5" class="clear-td-bor">
								<div class="yh_mew_list"><ul class="at_list"><li>
								<span class="tb_icon active_04">${activityInfo.typeName}</span><span class="tb_text">${activityInfo.description}</span>
								</li></ul><div class="clear"></div></div></td>
						<#else>
							<td class="clear-td-bor no-yh-td" colspan="5"></td>
						</#if>
					</tr>	
				</table>
									</#list>
								</#if>
							</#list>
						</#list>
					</#list>
					
				<#if (map_pointinfo.points !="0"&&map_pointinfo.points !="0.0"&&map_pointinfo.points !="0.00")>
					<div class="yhhd-des">
						<h3 class="yhhd-span">支付成功即可参加以下优惠活动：</h3>
						<ul class="yhhd-ul">
						<#if (map_pointinfo.pointDesFlag =="true")>
							<li>${map_pointinfo.pointDesStart}<b id="givepoint">${map_pointinfo.givepoints}</b>积分（下次购买可抵扣<b id="givepointvalue">${map_pointinfo.pointValue}</b>元）</li>
						<#else>
							<li>保单生效后可获得<b id="givepoint">${map_pointinfo.givepoints}</b>积分（下次购买可抵扣<b id="givepointvalue">${map_pointinfo.pointValue}</b>元）</li>
						</#if>
						<#if (list_activity?size > 0)>
							<#list list_activity as map>
								<li>${map.index}、${map.title}</li>
							</#list>
						</#if>
						</ul>
					</div>
				<#else>
					<#if (list_activity?size > 0)>
						<div class="yhhd-des">
							<h3 class="yhhd-span">支付成功即可参加以下优惠活动：</h3>
							<ul class="yhhd-ul">
							<#list list_activity as map>
								<li>${map.index}、${map.title}</li>
							</#list>
							</ul>
						</div>
					<#else>
						<div class="yhhd-des" style="display:none"></div>
					</#if>
				</#if>
				<!--优惠券和活动开始-->
				<div id="yhj_form" class="pay_postion_box">
					<table class="gwc_js_tables">
						<tr>
							<td class="gwc_js_table"></td>
							<td class="gwc_js_table_w"><span class="gwc_dd">共<i class="" id="ord_num">${ordNum}</i>个订单&nbsp;&nbsp;总计：</span></td>
							<td class="gwc_js_table_pay">
							    <i class="gwc_dd pay-pic1">￥</i><span class="gwc_dd pay-pic2" id="ord_price">${totalAmount}</span></td>
						</tr>
						<#if (discountAmount!='0.00')>
						<tr id="pay_div">
							<td class="gwc_js_table"></td>
							<td class="gwc_js_table_w"><span class="gwc_dd">减免：</span></td>
							<td class="gwc_js_table_pay"> <span class="gwc_dd"><b>￥</b><i>-${discountAmount}</i></span></td>
						</tr>
						</#if>
						<tr id="yhq_price" style="display:none;"></tr>
						<tr id="yh_price" style="display:none;"></tr>
						<tr>
							<td  class="gwc_js_table"></td>
							<td class="gwc_js_table_w"><span class="gwc_dd">您需要支付：</span></td>
							<td class="gwc_js_table_pay">
								<span class="gwc_dd"><b class="pay_jg_2 ">￥</b><i class="pay_jg_3 " id="p_price">${realAmount}</i></span>
							</td>
						</tr>
					</table>
					
					<input type = "hidden" id="yhj_orderId" value="${checkOrderSn}"/>
					<input type = "hidden" id="shopcartflag" value="1"/>
					<input type = "hidden" id="jf_zero" value="true"/>
					<input type = "hidden" id="memberpoint" value="0"/>
					<#if (fkIsShow =="Y")!>
						<div class="fx_fkid">
					 	 飞客茶馆用户ID<input type="text" id="fkID" name="fkID" maxlength="32" class="fx_fk_txt"> (飞客茶馆用户可返“飞米”，用户兑换礼品，航空公司里程，酒店积分等)
					    </div>
				    <#else>
				    </#if>
    				<div id="youhui_div" class="yhj_con">
    					<h3 class="pay_make_yh" id="pay_yhj_gl">使用优惠券抵消部分总额</h3>
						<div class="pay_quan" style="display: none" id="pay_yhj_box">
							<span class="font-hscs">请选择使用的优惠券</span>
							<ul class="yhj-des-link">
								<li class="yhj-deslink1">如何得到优惠券?<span class="yhj_jts" style="display:none;">关注开心保微信公众号参与互动，或通过网站活动页面领取即可获得优惠券，优惠券可在会员中心查看</span></li>
								<li class="yhj-deslink2">优惠券如何使用?<span class="yhj_jts" style="display:none;">输入优惠券码或勾选优惠券即可使用优惠券</span></li>
							</ul>
							<div class="pre_yh_txt" >
								<ul class="pre_yh_li cf">
									<li class="pre_yh_li1">优惠券代码</li>
									<li class="pre_yh_li2"><input type="text" class="jhm_texts" id="jhm_text"/></li>
									<li class="pre_yh_li3"> <input type="button"  class="pay_jf_btn" id="yhj_jh_button" value="使用"></li>
								</ul>
							</div>
							<div id="yhj_list" class="pay_jf_tdjn" style="display:none">
								<img src="${staticPath}/images/loading2.gif" alt="" />
							</div>
						</div>
						<h3 class="pay_make_yh" id="pay_jf_gl">使用积分抵消部分总额</h3>
					    <div class="clear"></div>
						<div class="pay_jifen" style="display: none" id="pay_jf_box">
							 <table class="jf_table">
							 	<tr id="jf_sy" >
							 		<td class="pay_jf_td pay_jf_td1">本次使用</td>
							 		<td class="pay_jf_td pay_jf_td2"><input type="text"  class="pay_jf_txt" name="point_dz"/></td>
							 		<td class="pay_jf_td pay_jf_td3">积分支付</td>
							 		<td class="pay_jf_td pay_jf_td4" id="jf_sy_qx_botton"><input type="button" value="使用"  id="sy_botton" class="pay_jf_btn" onclick="jfsy()"/></td>
							 		<td class="pay_jf_td pay_jf_td5" id="jfsy_span"></td>
							 	</tr>
							 	<tr id="jf_qx" style="display:none;">
							 		<td class="pay_jf_td pay_jf_td1">本次使用</td>
							 		<td class="pay_jf_td pay_jf_td2"><input type="text" class="pay_jf_txt" name="point_dz" readonly="readonly"/></td>
							 		<td class="pay_jf_td pay_jf_td3">积分支付</td>
							 		<td class="pay_jf_td pay_jf_td4"><input type="button" value="取消" class="pay_jf_btn pay_quxiao" onclick="jfqx()" /></td>
							 		<td class="pay_jf_td pay_jf_td5" id="jfqx_span"></td>
							 	</tr>
							 </table>
							 <div class="pay_jf_td6">您有<span id="member_jf_one"></span>积分，本次最多可使用<span id="member_jf_two" ></span>积分</div>
						</div>
					</div>
				</div>
				<!--优惠券和活动结束-->
				<div id="jfzf_botton" class="jf_zhifu"  style="display:none;"><input type="button" class="jf_zf_btn" onclick="dosubmit('zerozf')"/></div>
			</div>
			<div class = "clear"></div>
		    <div class="pay_box">
		    	<div class="ban_pay" style=display:none; id="pay_zero_box">
					<div class="banpay_con">
						<span class="ban_header"></span><span class="ban_con">亲爱的用户，点击“<em class="red">确认支付</em>”即可成功支付本次订单哦！</span>
					</div>
			    </div>
			 	<div class="ins-tit">请选择付款方式</div>
		      	<div class="pay_zhifu">
		        	<div class="platform">
		            <span class="pt-tags">支付平台</span>
		            <ul class="platform_list clearfix">
		            	<#assign index = 1>
		            	<#list bank1 as bank>
		            		<#if index%5 == 0>
						        <li class="clear-msf">
						    <#else>
						        <li>
						    </#if>
							<a href="javascript:void(0);" onclick="dosubmit('${bank.payType}')"></a><img  class="select_bank" src="${staticPath}/style/shop/images/pay/${bank.image}" width="150" height="38" alt="${bank.description}"/></li>
							<#assign index = index + 1>
						</#list>
		            </ul>
		            </div>
		            
		            <div class="pay_bank" id="tag_box">
		            	<ul class="tag_banktitile">
		            		<li class="select_banks"><a href="###">网银支付</a></li>
		            		<li class=""><a href="###">企业网银</a></li>
		            	</ul>
		            	<div id="pay_bank_box" class="pay_bank_boxs" >
		                	<div class="bank_list clearfix">
		                    	<ul>
		                    		<#assign index = 1>
		                    		 <#list bank3 as bank>
		                    		 	<#if index%5 == 0>
						                    <li class="clear-msf">
						                <#else>
						                    <li>
						                </#if>
							            <a href="javascript:void(0);" onclick="dosubmit('${bank.payType}')"></a><img  src="${staticPath}/style/shop/images/pay/${bank.image}" width="150" height="38" alt="${bank.description}"/></li>
							         	<#assign index = index + 1>
							         </#list>
		                    	</ul>
		                	</div>
		                    <div class="bank_list clearfix" style="display:none;">
		                    	<ul>
		                    		<#assign index = 1>
			                    	 <#list bank2 as bank>
			                    	 	<#if index%5 == 0>
						                    <li class="clear-msf">
						                <#else>
						                    <li>
						                </#if>
								        <a href="javascript:void(0);" onclick="dosubmit('${bank.payType}')"></a><img src="${staticPath}/style/shop/images/pay/${bank.image}" width="150" height="38" alt="${bank.description}"/></li>
								     	<#assign index = index + 1>
								     </#list>
							    </ul>
		                    </div>
		            	</div>
		      		</div>
		     	</div>
			</div>
		</div>
   		<div class="clear"></div>
 	</div>
	<div class="clear"></div>
	</form>
</div>

<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml">

<div id="paytippopdiv" style="background-color:white;width: 432px ;height:180px;border:1px solid #E5E5E5;display:none; " class="ColumnArea__" >
	<div class="TitleArea"><div class="TitleTxt">支付提示</div></div>
	<div class="ContentNew" >
		<p  class="content_s"> 请您在新打开的页面进行支付，支付完成前请不要关闭新窗口</p>
		<button onclick="docallBack();"  >&nbsp;&nbsp;已完成支付&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
		<a vlpageid="xiaoneng" href="javascript:void(0); rel="nofollow" class="button-dsfsf" exturl="http://www.kaixinbao.com/xiaoneng" id="qqwap2" onclick="return(VL_FileDL(this));return false;" >遇到问题？联系在线客服</a> <br>
		<a href="javascript:void(0);" onclick="doclose();" rel="nofollow"  class="pay_ser">更换其他支付方式></a>
    </div>
</div>
<div id="timeoutdiv" style="background-color:white;width: 590px ;border:1px solid #E5E5E5; display:none; " class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">温馨提示</div>
      </div>
      <div class="pay_tip_box pay_tip_boxup" id="orderInfo">
      		
      </div>
      <input id="front" type='hidden' value="">
      <input id="modifyFlag" type='hidden' value="">
      <div class="Content" >
      	  <button onclick="if (jQuery('#modifyFlag').val() == '1') {alert('请修改起保日期');return false;};continueSubmit();"  >&nbsp;&nbsp;立即支付&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
		  <button onclick="modifyStartDate();" class="button-dsfsf">修改起保日期</button>
	  </div>
</div>

<script	type="text/javascript" src="${shopStaticPath}/iframeTools.js"></script>
 <script type="text/javascript" charset="utf-8" src="${shopStaticPath}/template/common/js/jquery.blockUI.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.validate.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.validate.methods.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.validate.cn.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/shop/js/pay.js"></script>
</body>
</html> 
 