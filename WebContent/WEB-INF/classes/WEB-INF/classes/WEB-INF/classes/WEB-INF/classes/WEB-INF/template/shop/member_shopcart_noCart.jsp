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
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
<div class="wrapper">
	<div class="re-line-log">
         <ul class="re-line-ul">
            <li class="re-line-d"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
            <li class="re-line-s"><div class="re-linehead re-linehead4"><span></span></div><h3 class="re-line-h3">2、查看购物车<p>加入购物车，多订单一起付款</p></h3><span class="re-line-jiao"></span></li>
            <li><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>积分折扣、支持多种支付方式</p></h3><span class="re-line-jiao"></span></li>
         </ul>
    </div>
    <div class="clear"></div>
	<div class="shop_cart">
    	<div class="order-shop-num f-mi">共<span>0</span>个订单</div>
	    <div class="shop_cart_wp clearfix">
	       <div class="th td_chk"><input type="checkbox" class="selAll" />全选</div>
	        <div class="th td_infco_up">产品名称</div>
	        <div class="th td_time">保障期限</div>
	        <div class="th td_tbr">投保人</div>
	        <div class="th td_bbr">被保人</div>
            <div class="th td_price">原始价格</div>
            <div class="th td_price">优惠后价格</div>
	        <div class="th td_op">操作</div>
	    </div>
	    <div class="shop_cart_box">
	    	<div class="cart_empty"><p class="cart_empty_p">购物车中还没有商品<br>去逛逛吧 ~</p></div>
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
	    		<div class="changeDiv" style="display: none;">
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
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.soChange-min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/shop/js/shopCart.js"></script>
  <script type="text/javascript">
        jQuery(function () {
          
            jQuery('#change_zc div.changeDiv').soChange({
                thumbObj: '#change_zc .ul_change_zc span',
                thumbNowClass: 'on',
                slideTime: 500,
                changeTime: 10000,
                overStop: true
            });
        });
    </script> 
</body>
</html>