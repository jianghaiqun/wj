<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付结果</title>
<title>支付错误</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if>
<div class="wrapper order-bg">
	<div class="order-mes-des">
		<img src="${staticPath}/style/shop/images/errors.gif" alt="" width="260px" height="260px" class="order-mes-img">
		<div class="order-mesbox order-mesbox2">
            <h2 class="mesbox-tit f_mi">您的支付还没有完成哦~</h2>
            <span class="mes-erro-des f_mi">如果遇到问题请联系一下客服MM吧~</span><div id="loading"><img src="${staticPath}/images/nloading.gif"  width="20px" height="20px" alt="" /></div>
            <a href="" id="toShouYe" class="bad-btnsf bad-btnsf2">返回首页</a><a href="#" onclick="sure();" class="bad-btnsf">继续支付</a>
         </div>
         <div class="clear"></div>
     </div>
     <div class="clear"></div>
</div>       
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
<!-- 产品分类浮动层 开始 -->
<div class="ClassificationArea" id="ClassificationArea">
</div>
<!-- 客服类浮动层 开始 -->
<div class="servfloat" id="servfloat" style="_margin-right:0;">
  <ul>
	<li><a class="weixin" href="javascript:void(0);"><span>加入微信</span></a>
	<div class="weixinDiv" style="display: none;">
	<img src="${shopStaticPath}/images/wxnew.png">关注领优惠券</div>
	</li>
	<li><a class="zixun" href="javascript:void(0);" vlpageid="xiaoneng" exturl="http://www.kaixinbao.com/xiaoneng" id="qqwap1" onclick="return(VL_FileDL(this));return false;" ><span style="color: #cc1d00;">
	在线客服</span></a>
	 </li>
	
	<li class="top"><a class="top" id="gotop" href="javascript:void(0);"></a>
	</li>
</ul>
</div>
<!-- 代码结束 -->

<script type="text/javascript" src="${shopStaticPath}/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/base.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
<script type="text/javascript" src="${shopStaticPath}/footer.js"></script>

<script type="text/javascript"> 
    jQuery("#toShouYe").attr("href",sinosoft.front);
	function sure(){
		jQuery("#loading").css("display", "inline-block");
	    var orderSn = '${orderId}';
	    var paySn = '${paySn}';
	    var needUWCheckFlag = '${needUWCheckFlag}';
	    var paySnFlag = paySn.substring(paySn.length-1,paySn.length);
	    if(paySnFlag=="D"){
	        if(needUWCheckFlag=="1"){
	    		jQuery.blockUI({
				overlayCSS:{backgroundColor:'#fff',opacity:0.7}, 
				message:"<div><span>正在为您进行核保操作，请稍候</span><br/><img width='170' height='15' title='正在核保，请稍等' src='"+sinosoft.jsPath+"/template/shop/images/loading-bars.gif'/></div>"
				 
			});
			jQuery.getJSON( 
				sinosoft.base+"/shop/order_config_new!checkInsurePay.action?orderSn="+orderSn+"&callback=?",
				function(data) { 
					var obj = eval(data);
					var passFlag = obj.passFlag;
					var tMessage = obj.rtnMessage; 
					var KID = obj.KID;
				    if(obj){  
						if(passFlag=="pass"){
							jQuery.unblockUI();
							window.location.href="${base}/shop/order_config_new!pay.action?orderSn="+orderSn;
						}else{
							jQuery.unblockUI();
							/*核保错误提示*/ 
							pay_error(orderSn,productId,tMessage);	 
						} 
				      } 
					});
	        }else{
	            window.location.href = "${base}/shop/order_config_new!pay.action?orderSn="+orderSn;
	        }
	    }else{
	    	window.location.href = "${base}/shop/member_shopcart!toCheckInsure.action?checkOrderSn="+orderSn;
	    }
	}

  
</script>

</body>
</html>


