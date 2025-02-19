<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单超出最早起保日期处理</title>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_member.css">
<script type="text/javascript" src="${shopStaticPath}/VerifyInput.js"></script>
<script type="text/javascript" src="${shopStaticPath}/Common.js"></script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">

	<div class="vip_time">                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		<div class="vip_box">
			<b class="vip_c_time_b">尊敬的用户：</b>
			<p>
				您的订单起保日期：<span class="vip_time_co">${startDate}</span>
			</p>
			<p>
			<#if startPerid? has_content>
			    <span id="flag" class="vip_time_co2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 
			<#else>
				<span id="flag" class="vip_time_co2">小于</span>
			</#if>
			当前日期：<span class="vip_time_co">${currentDate}</span>
			</p>
			${startPerid}
			<b class="vip_c_time_b">请您修改起保日期，谢谢！</b>
		</div>
		<div class="vip_ctime_btn">
			<a href="${base}/shop/order_query!queryOrder.action" class="ctime_goback">返回我的订单</a> 
			<a href="${base}/shop/order_config_new!buyNowUpdate.action?orderSn=${orderSn}&orderFlag=ShopCart&KID=${KID}" class="ctime_go">确认修改</a></div>
		</div>
	</div>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml">
</body>
</html>