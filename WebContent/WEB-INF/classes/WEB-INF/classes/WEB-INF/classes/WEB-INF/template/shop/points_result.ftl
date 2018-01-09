<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${shopStaticPath}/template/shop/css/PayPromptSty.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	
		<div class="PayPromptMiddArea">
		    <div class="PayDetailsColumnArea_2 mb10"><!--     订单创建成功  begin   -->
		        <div class="paywrap01">
					<div class="payh3a">积分兑换成功！请进入会员中心查询/领取礼品！</div>
					<div class="paywrap02">
						<h3>以下是您的兑换信息</h3>
						<ul>
							<li>兑换方式：开心果</li>
							<li>消耗开心果：<span class="payje01">${TotalPoints}</span></li>
							<li>剩余开心果：<span class="payje02">${SparePoint}</span></li>
						</ul>
					</div>
				</div>
		  	</div><!--    订单创建成功  end    -->
		</div>
		<div class="clear"></div>
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</div>
</body>
</html>


