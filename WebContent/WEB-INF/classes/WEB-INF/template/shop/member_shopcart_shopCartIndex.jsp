<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
	String shouye = Config.getFrontServerContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>购物车</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css" />
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/shop/css/re_shops.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
<script type="text/javascript">
    function modifyInfo(orderSn,KID){
    	if (jQuery("#YesLogin").is(':hidden')) {
    		artLoginShopCart();
			return;
		}
    	jQuery.ajax({
    		type: 'post',
    		data: {"orderSn":orderSn},
    		url: sinosoft.base+'/shop/sales_volume!queryProState.action',
    		dataType: "json",
    		async: false,
    		success: function(data) {
    			
    			if(data.state == "N"){
    				if(data.HtmlPath="NoLogin"){
    					artLoginShopCart();
    				}else{
    					window.open(data.HtmlPath)
    				}
    			} else {
    				window.location.href='<%=serverContext%>/shop/order_config_new!buyNowUpdate.action?orderSn='+orderSn+'&orderFlag=ShopCart&KID='+KID;
    			}
    		}
    	});
    }
    
    function selectg(){
        jQuery("input[name=checkOrderSn]").each(function() {
          if(jQuery(this).attr("checked")==true){
              jQuery(this).parents("ul").addClass("select_shop_ul");
          }else{
               jQuery(this).parents("ul").removeClass("select_shop_ul");
          }
      } )}
    
    /*选择购物车订单计算优惠信息*/
    function selectShop(){
    	selectg(); //选中产品变色
    	if(jQuery('#showForm').serialize()==''||jQuery('#showForm').serialize()==null){
    		initActivityInfo();
    		jQuery('#payprice').html('￥0.00');
    		jQuery('#DiscountPrice').html('￥-0.00');
    		jQuery('#totalprice').html('￥0.00');
    		jQuery('#orders_num').html('0');
    		jQuery('#orders_num1').html('0');
    		return;
    	}
    	jQuery.ajax({
			url: sinosoft.base+"/shop/shopping_cart!ajaxDiscountPrice.action",
			data:jQuery('#showForm').serialize(),
			async:true,
			type:"POST",
			dataType: "json",  
			success: function(data) {
					initActivityInfo();
					jQuery.each(data,function(key,value) { 
						if(key=='discountamount'){
							jQuery('#DiscountPrice').html('￥-'+value);
							if(Number(value)==0){
								jQuery('#mj_show_message').hide();
							}else{
								jQuery('#mj_show_message').show();
							}
						}else if(key=='realamount'){
							jQuery('#payprice').html('￥'+value); 
						}else if(key=='totalamount'){
							jQuery('#totalprice').html('￥'+value);
						}else if(key=='ordernum'){
							jQuery('#orders_num').html(value);
							jQuery('#orders_num1').html(value);
						}else{
							var price=value.split("&");
							jQuery('#'+key+'_amount').show();
							jQuery('#'+key+'_amount').html('￥'+price[0]);
							jQuery('#'+key+'_yhAmount').html(price[1]);
							if(Number(price[1])!=0){
								jQuery('#'+key+'_yh_message').show();
							}
						}
     				})
 				}
			}); 
    }
    /*初始化活动优惠信息*/
    function initActivityInfo(){
    	
    	jQuery('[id$=_amount]').html('￥0.00')
		jQuery('[id$=_yhAmount]').html('0.00')
		jQuery('[id$=_yh_message]').hide();
		jQuery('#mj_show_message').hide();
		jQuery('[id$=_amount').hide();
    }
    
    var xinhuaConfirm = false;  // 新华产品提示框弹出判断
    
    function sure(){
    	if (jQuery("#YesLogin").is(':hidden')) {
			artLogin();
			return;
		}
    	var flag = false;
    	var hasXinHua = false;
    	jQuery("input[name=checkOrderSn]").each(function() {  
            if(jQuery(this).attr("checked")==true){
            	flag = true;
            	if (jQuery(this).next("input").val().indexOf("1014") == 0) {
            		hasXinHua = true;
            	}
            }  
        });  
    	
    	if(flag){
    		// 新华产品弹出核保确认提示框
    		if (hasXinHua && !xinhuaConfirm) {
    			art.dialog({
    				title:'消息',
    				lock:true,
                    opacity: 0.6,
    			    content: '<p style="text-align: center;">本产品同一被保人当天只能提交一次<br>确认提交？<p>',
    			    ok: function () {
    			    	xinhuaConfirm = true;
    			    	sure();
    			    },
    			    cancelVal: '关闭',
    			    cancel: true
    			});
    			return;
    		}
    		
    		document.getElementById("showForm").submit();
    	}else{
    		art.dialog({
    			lock: true,
                icon:'warning',
                content: '请至少选择一件商品'
            });  
    	}
    }
</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
<form id="showForm" action="member_shopcart!toCheckInsure.action" >
<div class="wrapper">
	<div class="re-line-log">
        <ul class="re-line-ul">
            <li class="re-line-d"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
            <li class="re-line-s"><div class="re-linehead re-linehead4"><span></span></div><h3 class="re-line-h3">2、查看购物车<p>加入购物车，多订单一起付款</p></h3><span class="re-line-jiao"></span></li>
            <li><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>积分折扣、支持多种支付方式</p></h3><span class="re-line-jiao"></span></li>
        </ul>
    </div>
	<div class="clear"></div>
	<s:if test='#request.activityFlag=="1"'>
		<div class="shop_cart"> 
	</s:if> 
	<s:else>
		<div class="shop_cart rows8">
	</s:else>
		<div class="order-shop-num f-mi">
		        共<span id="orders_num1">0</span>个订单
		</div>
	    <div class="shop_cart_wp clearfix">
	        <div class="th td_chk"><input type="checkbox" class="selAll" />全选</div>
	        <div class="th td_infco_up">产品名称</div>
	        <div class="th td_time">保障期限</div>
	        <div class="th td_tbr">投保人</div>
	        <div class="th td_bbr">被保人</div>
	        <s:if test='#request.activityFlag=="1"'> 
		        <div class="th td_price">原始价格</div>
		        <div class="th td_price">优惠后价格</div>
	        </s:if> 
	        <s:else>
	        	<div class="th td_price">原始价格</div>
	        </s:else> 
	        <div class="th td_op">操作</div>
	   </div>
	   <!--购物车信息列表-->
	   <div class="shop_cart_box">
	   		<!--没有任何优惠活动的订单列表 -->
	   		<s:iterator value="activityMap" id="map_activity">
				<s:iterator value="#map_activity.value" id="map_info" >
					<s:if test='key=="_no_activity"'>
						<!-- 购物车产品信息-->
						<s:iterator value="#map_info.value" id="map_data" >
							<s:if test='key=="ProductInfo"'>
								<s:iterator value="#map_data.value" id="product_list" status="st">
									<s:if test="#st.last"> 
										<ul class="clearfix shop_car_list clear_borders">
	 								</s:if> 
	 								<s:else> 
										<ul class="clearfix shop_car_list">
	 								</s:else> 
	 								<li class="td td_chk">
										<div class="td_inner">
										   <s:if test='#product_list.IsEffective=="N"'>
							  		   		   <input type="checkbox"  name="checkOrderSn" onclick="selectShop();" value="<s:property value="#product_list.OrderSn"/>" disabled="disabled" />
						  		   		   </s:if>
						  		   		   <s:else>
						  		   		   		<input type="checkbox"  name="checkOrderSn" onclick="selectShop();" value="<s:property value="#product_list.OrderSn"/>"/>
						  		   		   </s:else>
						  		   		   <input type="hidden" name="checkProductId" value="<s:property value="#product_list.ProductId"/>"/>
										</div>
									</li>
									<li class="td td_info2">
										<a href="<s:property value="#product_list.DetailPath"/>"  target="_blank"><span class="chart_cplogo icon_C<s:property value="#product_list.ComanyCode"/>"></span></a>
										<a href="<s:property value="#product_list.DetailPath"/>" class="item-title" target="_blank"><s:property value="#product_list.ProductName"/></a>
									</li> 
											<s:if test="#product_list.EndDate =='9999-12-31'">
													<li class="td td_time"><s:property value="#product_list.StartDate" /> 00  时<br>至<br> 终身<br>
											</s:if>
											<s:else>
													<li class="td td_time"><s:property value="#product_list.StartDate" /> 00  时<br>至<br><s:property value="#product_list.EndDate" /> 24  时<br>
										   </s:else>
											
							        	<span>
							        		<s:if test='#product_list.IsEffective=="N"'> 
						   					    <font color="red">（您的保单已过期）</font>
						   					</s:if>
					   					</span>
						   			</li>
						   			<li class="td td_tbr"><span class="shop_m_ts"><s:property value="#product_list.AppntName"/></span></li>
									<s:if test='#product_list.AllInsuredName!=null && #product_list.AllInsuredName != ""'>
							        	<li class="td td_bbr"><span class="shop_m_ts" title="<s:property value="#product_list.AllInsuredName"/>"><s:property value="#product_list.FirstInsuredName"/>...</span></li>
							        </s:if>
							        <s:else>
							        	<li class="td td_bbr"><span class="shop_m_ts"><s:property value="#product_list.FirstInsuredName"/></span></li>
							        </s:else>
							        <s:if test='#request.activityFlag=="1"'> 
							        	<s:if test='#product_list.ActivityFlag == "1"'>
							        		<li class="td td_price"><span class="shop_m_ts shop_money f-mi   zk_money">￥<s:property value="#product_list.ProductTotalPrice"/></span></li>
							        		<li class="td td_price"><span class="shop_m_ts shop_money ps-jf-tops f-mi amount">￥<s:property value="#product_list.Amount"/></span></li>
								        </s:if>
								        <s:else>
								        	<li class="td td_price"><span class="shop_m_ts shop_money f-mi ps-jf-tops amount">￥<s:property value="#product_list.Amount"/></span></li>
								        	<li class="td td_price"><span class="shop_m_ts shop_money f-mi"></span></li>
								        	<input type="hidden" name="amountPlasList" value="<s:property value="#product_list.Amount"/>"/>
								        	
								        </s:else>
							        </s:if>
							        <s:else>
							        	<li class="td td_price"><span class="shop_m_ts shop_money f-mi ps-jf-tops amount">￥<s:property value="#product_list.Amount"/></span></li>
							        	<input type="hidden" name="amountPlasList" value="<s:property value="#product_list.Amount"/>"/>
							        </s:else>
							        <li class="td td_op"><a href="#" onclick='modifyInfo("<s:property value="#product_list.OrderSn"/>","<s:property value="#product_list.OrderSnKid"/>");return false;' class="shop_update_op">修改投保信息</a>
							        <a href="#" onclick='deleteInfo("<s:property value="#product_list.OrderSn"/>","<s:property value="#product_list.ProductId"/>");return false;'  class="shop_del">删除</a></li>
							        </ul>
							     </s:iterator>
							 </s:if>
						</s:iterator>
					</s:if>
				</s:iterator>
			</s:iterator>
			<!--参加折扣活动的订单列表-->
			<s:iterator value="activityMap" id="map_activity">
				<s:iterator value="#map_activity.value" id="map_info" >
					<s:if test='key!="_no_activity"'>
						<!--折扣活动购物车优惠信息-->
						<s:if test='#map_info.value.ActivityInfo.type=="6"'>
							<div class="shop_car_ft">
		                        <ul class="at_list">
		                            <li><span class="tb_icon active_02">折扣</span><span class="tb_text"><s:property value="#map_info.value.ActivityInfo.title"/></span></li>
		                        </ul>        
		                        <span class="yh-money-car f-mi"><span id="<s:property value="#map_info.key"/>_amount" style="display:none;">￥0.00</span><em id="<s:property value="#map_info.key"/>_yh_message" style="display:none;">（已节省¥<i id="<s:property value="#map_info.key"/>_yhAmount" ></i>）</em></span>
		                    </div>
		                    <!-- 购物车产品信息-->
							<s:iterator value="#map_info.value" id="map_data" >
								<s:if test='key=="ProductInfo"'>
									<s:iterator value="#map_data.value" id="product_list" status="st">
										<s:if test="#st.last"> 
											<ul class="clearfix shop_car_list clear_borders">
		 								</s:if> 
		 								<s:else> 
											<ul class="clearfix shop_car_list">
		 								</s:else> 
		 							<li class="td td_chk">
										<div class="td_inner">
										   <s:if test='#product_list.IsEffective=="N"'>
							  		   		   <input type="checkbox"  name="checkOrderSn" onclick="selectShop();" value="<s:property value="#product_list.OrderSn"/>" disabled="disabled" />
						  		   		   </s:if>
						  		   		   <s:else>
						  		   		   		<input type="checkbox"  name="checkOrderSn" onclick="selectShop();" value="<s:property value="#product_list.OrderSn"/>"/>
						  		   		   </s:else>
						  		   		   <input type="hidden" name="checkProductId" value="<s:property value="#product_list.ProductId"/>"/>
										</div>
									</li>
									<li class="td td_info2">
										<a href="<s:property value="#product_list.DetailPath"/>"  target="_blank"><span class="chart_cplogo icon_C<s:property value="#product_list.ComanyCode"/>"></span></a>
										<a href="<s:property value="#product_list.DetailPath"/>" class="item-title" target="_blank"><s:property value="#product_list.ProductName"/></a>
									</li>
									<li class="td td_time"><s:property value="#product_list.StartDate" /> 00  时<br>至<br><s:property value="#product_list.EndDate" /> 24  时<br>
							        	<span>
							        		<s:if test='#product_list.IsEffective=="N"'> 
						   					    <font color="red">（您的保单已过期）</font>
						   					</s:if>
					   					</span>
						   			</li>
						   			<li class="td td_tbr"><span class="shop_m_ts"><s:property value="#product_list.AppntName"/></span></li>
									<s:if test='#product_list.AllInsuredName!=null && #product_list.AllInsuredName != ""'>
							        	<li class="td td_bbr"><span class="shop_m_ts" title="<s:property value="#product_list.AllInsuredName"/>"><s:property value="#product_list.FirstInsuredName"/>...</span></li>
							        </s:if>
							        <s:else>
							        	<li class="td td_bbr"><span class="shop_m_ts"><s:property value="#product_list.FirstInsuredName"/></span></li>
							        </s:else>
							        <li class="td td_price"><span class="shop_m_ts shop_money f-mi   zk_money">￥<s:property value="#product_list.ProductTotalPrice"/></span></li>
							        <li class="td td_price"><span class="shop_m_ts shop_money ps-jf-tops f-mi amount">￥<s:property value="#product_list.ActivityeAmount"/></span></li>
									<li class="td td_op"><a href="#" onclick='modifyInfo("<s:property value="#product_list.OrderSn"/>","<s:property value="#product_list.OrderSnKid"/>");return false;' class="shop_update_op">修改投保信息</a>
							        <a href="#" onclick='deleteInfo("<s:property value="#product_list.OrderSn"/>","<s:property value="#product_list.ProductId"/>");return false;'  class="shop_del">删除</a></li>
							        </ul>
									</s:iterator>
							 	</s:if>   
							</s:iterator>
						</s:if>
					</s:if>
				</s:iterator>
			</s:iterator>
			<!-- 参加满减活动的订单列表 -->
			<s:iterator value="activityMap" id="map_activity">
				<s:iterator value="#map_activity.value" id="map_info" >
					<s:if test='key!="_no_activity"'>
						<!--满减活动购物车优惠信息-->
						<s:if test='#map_info.value.ActivityInfo.type=="3"'>
							<div class="shop_car_ft">
		                        <ul class="at_list">
		                            <li><span class="tb_icon active_04">满减</span><span class="tb_text"><s:property value="#map_info.value.ActivityInfo.title"/></span></li>
		                        </ul>        
		                        <span class="yh-money-car f-mi"><span id="<s:property value="#map_info.key"/>_amount" style="display:none;">￥0.00</span><em id="<s:property value="#map_info.key"/>_yh_message" style="display:none;">（已减¥<i id="<s:property value="#map_info.key"/>_yhAmount" ></i>）</em></span>
		                    </div>
		                    <!-- 购物车产品信息-->
		                    <s:iterator value="#map_info.value" id="map_data" > 
		                    	<s:if test='key=="ProductInfo"'>
									<s:iterator value="#map_data.value" id="product_list" status="st">
										<s:if test="#st.last"> 
											<ul class="clearfix shop_car_list clear_borders">
		 								</s:if> 
		 								<s:else> 
											<ul class="clearfix shop_car_list">
		 								</s:else> 
			 							<li class="td td_chk">
											<div class="td_inner">
											   <s:if test='#product_list.IsEffective=="N"'>
								  		   		   <input type="checkbox"  name="checkOrderSn" onclick="selectShop();" value="<s:property value="#product_list.OrderSn"/>" disabled="disabled" />
							  		   		   </s:if>
							  		   		   <s:else>
							  		   		   		<input type="checkbox"  name="checkOrderSn" onclick="selectShop();" value="<s:property value="#product_list.OrderSn"/>"/>
							  		   		   </s:else>
							  		   		   <input type="hidden" name="checkProductId" value="<s:property value="#product_list.ProductId"/>"/>
											</div>
										</li>
										<li class="td td_info2">
											<a href="<s:property value="#product_list.DetailPath"/>"  target="_blank"><span class="chart_cplogo icon_C<s:property value="#product_list.ComanyCode"/>"></span></a>
											<a href="<s:property value="#product_list.DetailPath"/>" class="item-title" target="_blank"><s:property value="#product_list.ProductName"/></a>
										</li>
										<li class="td td_time"><s:property value="#product_list.StartDate" /> 00  时<br>至<br><s:property value="#product_list.EndDate" /> 24  时<br>
								        	<span>
								        		<s:if test='#product_list.IsEffective=="N"'> 
							   					    <font color="red">（您的保单已过期）</font>
							   					</s:if>
						   					</span>
							   			</li>
							   			<li class="td td_tbr"><span class="shop_m_ts"><s:property value="#product_list.AppntName"/></span></li>
										<s:if test='#product_list.AllInsuredName!=null && #product_list.AllInsuredName != ""'>
								        	<li class="td td_bbr"><span class="shop_m_ts" title="<s:property value="#product_list.AllInsuredName"/>"><s:property value="#product_list.FirstInsuredName"/>...</span></li>
								        </s:if>
								        <s:else>
								        	<li class="td td_bbr"><span class="shop_m_ts"><s:property value="#product_list.FirstInsuredName"/></span></li>
								        </s:else>
								        <s:if test='#request.activityFlag=="1"'> 
								        	<s:if test='#product_list.ActivityFlag == "1"'>
								        		<li class="td td_price"><span class="shop_m_ts shop_money f-mi   zk_money">￥<s:property value="#product_list.ProductTotalPrice"/></span></li>
								        		<li class="td td_price"><span class="shop_m_ts shop_money ps-jf-tops f-mi amount">￥<s:property value="#product_list.Amount"/></span></li>
									        </s:if>
									        <s:else>
									        	<li class="td td_price"><span class="shop_m_ts shop_money f-mi ps-jf-tops amount">￥<s:property value="#product_list.Amount"/></span></li>
									        	<li class="td td_price"><span class="shop_m_ts shop_money f-mi"></span></li>
									        	<input type="hidden" name="amountPlasList" value="<s:property value="#product_list.Amount"/>"/>
									        </s:else>
								        </s:if>
								        <s:else>
								        	<li class="td td_price"><span class="shop_m_ts shop_money f-mi ps-jf-tops amount">￥<s:property value="#product_list.Amount"/></span></li>
								        	<input type="hidden" name="amountPlasList" value="<s:property value="#product_list.Amount"/>"/>
								        </s:else>
								        <li class="td td_op"><a href="#" onclick='modifyInfo("<s:property value="#product_list.OrderSn"/>","<s:property value="#product_list.OrderSnKid"/>");return false;' class="shop_update_op">修改投保信息</a>
								        <a href="#" onclick='deleteInfo("<s:property value="#product_list.OrderSn"/>","<s:property value="#product_list.ProductId"/>");return false;'  class="shop_del">删除</a></li>
								        </ul>
								    </s:iterator>
								</s:if>
							</s:iterator>
						</s:if>
					</s:if>
				</s:iterator>
			</s:iterator>
		</div>
		<div class="shop_all">
			<div class="pay_box pay_box_gotsf">
				<div class="pay_action_con">
					<label class="pay_aciton_bs2"><input type="checkbox" class="selAll"/>全选</label>
					<span  class="pay_aciton_bs" id="collect-shop">收藏选中的商品</span> <span id="delete-cartshop" class="pay_aciton_bs">删除选中的商品</span>
				</div>
				<input type="button" value="去结算" class="next_btn2"  onclick="sure();"/>
				<table class="gwc_js_tables gwc_js_tables2">
			        <tbody>
				        <tr>
				            <td class="gwc_js_table_w" ><span class="gwc_dd gwc-sf">共<i class="pay_jg_s" id="orders_num">0</i>个订单</span></td>
				            <td class="gwc_js_table_pay  gwc_js_table_pay2"><span class="gwc_dd gwc-sf">总计：</span><span class="gwc_dd" id="totalprice">￥0.00</span>&nbsp;&nbsp;<span id="mj_show_message" style="display:none;"><span class="gwc_dd gwc-sf">减免：</span> <span class="gwc_dd" id="DiscountPrice">￥-0.00</span></span></td>
				        </tr>
				   
				        <tr>
				            <td class="gwc_js_table_w">
				            
				            </td>
				            <td class="gwc_js_table_pay gwc_js_table_pay2">  <span class="gwc_dd">您需要支付：</span>
				            	<span class="gwc_dd"><i class="pay_jg" id="payprice">￥0.00</i></span>
				            </td>
				        </tr>
			    	</tbody>
			    </table>
			</div> 
		</div>
		<div class="go_js_bnt">
	        <a href="<%=shouye%>" class="shop_cart_goon">继续选购商品</a>
	    </div>
	    <div class="clear h30"></div>
	    <s:if test="#request.temptorysaveList.size()==0">
	    	<div class="ins-tit un_bot_bor" style="display:none">暂存保单</div>
	    	<div class="zc-orders" style="display:none"><div id="change_zc" class="changeBox_zc" style="display:none"></div></div>
		</s:if>
		<s:else>
		    <div class="ins-tit un_bot_bor">暂存保单</div>
		    <div class="zc-orders">
		    	<div id="change_zc" class="changeBox_zc">
		    	<s:iterator value="#request.temptorysaveList" id="list">
		    		<div class="changeDiv" style="display: block;">
		    		<s:iterator value="top" id="inner"> 
		    			<dl class="zd_dlscon">
			    			<dt><a href="<s:property value="#inner.HtmlPath"></s:property>" target="_blank"><span class="chart_cplogo icon_C<s:property value="#inner.insuranceCompany"></s:property>"></span></a></dt>
					        <dd><a href="<s:property value="#inner.HtmlPath"></s:property>" target="_blank"><s:property value="#inner.productName"></s:property></a></dd>
					        <dd>投保人：<s:property value="#inner.applicantName"></s:property></dd>
					        <dd>被保人：<s:property value="#inner.insuredName"></s:property></dd>
					        <dd><span class="zd_addcart">加入购物车</span><input type="hidden" value="<s:property value="orderSn"></s:property>" /></dd>
				        </dl> 
	    			</s:iterator> 
		    		</div>
		    	</s:iterator>
		    		<ul class="ul_change_zc">
		    			<s:iterator value="#request.temptorysaveList" id="list">
						<li><span class="">&nbsp;</span></li>
						</s:iterator>
					</ul>
		    	</div>
		    	<div class="clear"></div>
		    </div>
		</s:else>
	</div>
</div>
</form>
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/shop/js/shopCart.js"></script>

  <script type="text/javascript">
        jQuery(function () {
          /*购物车复选框全选*/
            jQuery(".selAll").click(function () {
               if (jQuery(this).attr("checked")) {  
            	   jQuery(".selAll").attr('checked','true');
		            jQuery("input[name=checkOrderSn]").each(function() {
		            	if(jQuery(this).attr("disabled")!=true){
		            		jQuery(this).attr("checked", true);
		            	}
		            });  
		        } else {  
		        	jQuery(".selAll").attr('checked','');
		            jQuery("input[name=checkOrderSn]").each(function() {  
		            	if(jQuery(this).attr("disabled")!=true){
		                	jQuery(this).attr("checked", false); 
		            	} 
		            });  
		        };  
		     selectShop();
            });
          
            jQuery('#change_zc div.changeDiv').soChange({
                thumbObj: '#change_zc .ul_change_zc span',
                thumbNowClass: 'on',
                slideTime: 500,
                changeTime: 10000,
                overStop: true
            });
        });
        
        function deleteInfo(orderSn,productId){
        	if (jQuery("#YesLogin").is(':hidden')) {
        		artLoginShopCart();
    			return;
    		}
            art.dialog({
                id: 'delID',
                lock: true,
                background: '#222', 
                opacity: 0.4, 
                fixed: true,
                title:"删除商品",
                content: '您真的要删除此商品吗？<br>可以把商品移入收藏夹，下次再购买',
                button: [
                    {
                        name: '移入收藏夹',
                        callback: function () {
                        	jQuery.ajax({
                				type : 'get',
                				url : sinosoft.base+'/shop/favorites!add.action?productId=' + productId,
                				dataType : 'html',
                				success : function(de) {

                					if (de == 'notlogin') {
                						artLogin();//使用弹出窗口登录
                					} else if (de == '商品收藏失败!') {
                						art.dialog({ id: 'delID' }).close()
                						art.dialog({
                							  time: 2,
							                  icon:'warning',
							                  content: '商品收藏失败'
							              });
                					}
                					else {
                						art.dialog({ id: 'delID' }).close()
                						art.dialog({
                							  time: 2,
							                  icon:'succeed',
							                  content: '成功移入收藏夹并删除成功'
							              }); 
                						window.location.href='<%=serverContext%>/shop/member_shopcart!deleteInfo.action?orderSn='+ orderSn;
                                        
                					}
                				}
                			});
                        	
                            return false;
                        },
                        focus: true
                    },
                    {
                        name: '删除商品',
                        callback: function () {
                        	window.location.href='<%=serverContext%>/shop/member_shopcart!deleteInfo.action?orderSn='+ orderSn;
                        }
                    }
                ]
             });
        }

    </script> 
</body>
</html>