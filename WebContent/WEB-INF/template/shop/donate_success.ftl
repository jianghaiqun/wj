<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>投保结果</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${base}/template/shop/css/PayPromptSty.css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
 
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	
		<div class="PayPromptMiddArea">
		    <div class="PayDetailsColumnArea_2 mb10">
		        <div class="paywrap01">
		        <#if (icr.appStatus==1)>
					<div class="payh3a">投保成功！恭喜您获得20万元的航空意外险一份！</div>
					<div class="paywrap02">
						<h3>保单信息如下：</h3>
						<ul>
							<li>保单号：<span class="payje02">${icr.policyNo}</span></li>
						</ul>
						<ul>
							<li><span><a href="http://www.kaixinbao.com/">点击这里返回首页</a></span></li>
						</ul>
					</div>
				<#else>
					<div class="payh3b">投保失败！</div>
					<div class="paywrap02">
						<h3>投保失败原因如下：</h3>
						<ul>
							<li><span>${icr.insuranceResultMsg}</span></li>
						</ul>
						<ul>
							<li><span><a href="http://www.kaixinbao.com/">点击这里返回首页</a></span></li>
						</ul>
					</div>
				</#if>
				</div>
		  	</div>
		</div>
		<div class="clear"></div>
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</div>
</body>
</html>


