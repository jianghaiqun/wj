<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="限时抢购，保险促销，保险折扣，保险优惠" />
<meta name="description" content="开心保网促销频道汇集热买品牌和热销单品，为您提供保险限时抢购、优惠促销等服务，省钱又安心，免费热线：4009-789-789！" />
<title>限时抢购_促销频道-开心保保险网-最便捷的在线比价网上投保平台_好保险,聪明选</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css" />
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css" />
<link id="controlCss" rel="stylesheet" type="text/css" href="javascript:void(0);">
<style type="text/css">
.g-nav-main li a.nav_3{ color:#f08225; }
</style>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>

</head> 
<body id="max_style">
	<!-- 公共头部 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 公共头部 -->
	<div class="sales_con">
		<div class="g-weaper">
			<div class="daohang">
				<span class="daohang_home"></span> 
				<a target="_self" href="<%=Config.getValue("FrontServerContextPath")%>"><span class="orange">您的现在位置：首页</span></a>
				<span class="separator">&gt;</span>
				<span class="separator1">限时抢购</span>
			</div>
			<div class="rales_jd">
				<!--图片广告-->
		        	<s:include value="/wwwroot/kxb/block/promotion_ad_player.shtml"></s:include>
				<!--倒计时产品-->
				<s:iterator id="SpecialProduct" value="#request.SpecialProductList" status="status">
					<div class="mini_qg">
						<h3 class="mini_qg_tit">
							<span class="yomibox" data="<s:property value="#SpecialProduct.endtime"/>">
							<span class="hg"></span></span>
						</h3>
						<div class="mini_qg_img"> 
							<a href="<s:property value="#SpecialProduct.URL"/>"  target="_blank"></a><img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#SpecialProduct.LogoUrl"/>" alt="<s:property value="#SpecialProduct.ProductName"/>"><span class="sales_num f-ib"><s:property value="#SpecialProduct.buynum"/>人已购买</span>
						</div>
						<p class="mini_zk_mes"><s:property value="#SpecialProduct.ProductName"/></p>
						<div class="jg_cons">
							<span id="initprice" class="sales_xj f_mi">￥<s:property value="#SpecialProduct.initprice"/></span><span id="disprice" class="salaes_yj f_mi">￥<s:property value="#SpecialProduct.initprice"/></span>
							<input type="hidden" value="<s:property value="#SpecialProduct.productid"/>"  id="specilaproductid">
						</div>
						<div class="clear"></div>
						<div class="mini_sales_go" ><a href="<s:property value="#SpecialProduct.URL"/>"  target="_blank">立即购买</a></div>
					</div>
				</s:iterator>
			</div>
			<div class="clear h20"></div>
			<div class="sales_left">
				<!--模块活动遍历-->
				<s:iterator id="Model" value="#request.ModelList" status="status">
					<s:if test="#status.index!=0&&#status.even">
						<div class="clear h20"></div>
					</s:if>
					<div class="sales_mod">
						<h2 class="sales_title">
							<span class="sales_title1" id="hot_id<s:property value="#status.count"/>"><s:property value="#Model.ModuleName"/></span>
						</h2>
						<s:iterator id="Activity" value="#Model.ActivityList" status="activity_status">
							<div class="sales_shop">
								<a href="<s:property value="#Activity.URL"/>" target="_blank" class="sales_s_tit f_mi"><s:property value="#Activity.ActivityName"/></a>
								<div class="sales_img_c">
									<a href="<s:property value="#Activity.URL"/>" target="_blank"></a><img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#Activity.LogoUrl"/>" alt="<s:property value="#Activity.ActivityName"/>">
									
								</div>
								<p class="sales_time">
									距离活动结束还有：<span class="yomibox" data="<s:property value="#Activity.endtime"/>"><span class="hg"></span></span>
								</p>
								<a href="<s:property value="#Activity.URL"/>" target="_blank" class="sales_ck f_mi">立即查看</a>
							</div>
						</s:iterator>
					</div>
				</s:iterator>
			</div>
			<div class="sales_right">
				<div class="sales_mod">
					<h2 class="sales_title">
						<span class="sales_title1">热卖品牌</span>
					</h2>
					<s:iterator id="SellingBrand" value="#request.SellingBrandList" status="status">
						<dl class="sales_hot_dl">
							<dt class="icon_C<s:property value="#SellingBrand.LogoUrl"/> cp_logos">
								<a target="_blank" class="cp_logo_a" href="<s:property value="#SellingBrand.URL"/>"></a>
							</dt>
						</dl>
					</s:iterator>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="clear h40"></div>
	</div>
    <!--模块短标题遍历-->
	<div id="fix_nav_N" class="fix_nav">
		<s:iterator id="Model" value="#request.ModelList" status="status">
			 <span class="fix-hot-mod<s:property value="#status.count"/>"><a  href="#hot_id<s:property value="#status.count"/>" class="fix_a"><s:property value="#Model.ShortTitle"/></a></span>
		</s:iterator>
	</div>
	<!-- 公共底部 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 公共底部 -->
	<!-- 城市选择 -->
	<div id='suggest' class="ac_results"></div>
	<!-- 城市选择 -->
	<div id='suggest1' class="ac_results ac_results1"></div>
</body>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<s:include value="/wwwroot/kxb/block/common_js.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.soChange-min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/jquery.sales.js"></script>
<script>
  $('#change_33 .changeDiv').soChange({
      thumbObj:'#change_33 .ul_change_a2 span',
      thumbNowClass:'on',
      changeTime:4000
  });
$(function(){
  $(".yomibox").each(function(){
    var _this = this;
    $(this).yomi(
      {
        callback:function(){$(_this).parent().parent().remove()},
        callbackhours:function(){ $(_this).find(".time-hidden").show(); $(_this).find(".time-hidden2").hide();}
      }

      );
  });
   jQuery("#fix_nav_N").fixService({direction:'left'});
});
jQuery(document).ready(function() { 
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	var productIDArray = new Array(1);
	productIDArray[0]="Ajax_Prict_"+jQuery("#specilaproductid").val();
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/ajax_price!ajaxPrice.action?&callback=?&channelSn='+channelsn+'&ProductIDS='+productIDArray,
		dataType: "json",
		async: false,
		success: function(data) {
		
		jQuery.each(data, function(index, price) {
			var mPrice = price.split("_");
			if(parseFloat(jQuery.trim(mPrice[0])) < parseFloat(jQuery.trim(mPrice[1]))){
				jQuery("#initprice").html("￥"+mPrice[0]);
				jQuery("#disprice").html("￥"+mPrice[1]);
				jQuery("#disprice").show();
			}else{
				jQuery("#initprice").html("￥"+mPrice[0]);
				jQuery("#disprice").html("");
				jQuery("#disprice").hide();
			}
		});
	}
	});
});
</script>
</html>
