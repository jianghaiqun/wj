<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付结果</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${shopStaticPath}/template/shop/css/PayPromptSty.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<script type="text/javascript">
$("#wwwshowRegisterLogin").click(function(){
$(".showLoginWindow").click();
});
</script>
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if> 
<div class="wrapper">
	
		<div class="PayPromptMiddArea">
		    <div class="PayDetailsColumnArea_2 mb10">
		        <div class="paywrap01">
					<div class="payh3a">支付成功！订单编号：${tradeInformation.ordID}</div>
					<div class="paywrap02">
						<h3>以下是您的支付信息</h3>
						<ul>
							<li>支付金额：<span class="payje01">${tradeInformation.ordAmt}</span>元</li>
							<li>支付流水号：<span class="payje02">${tradeInformation.tradeSeriNO}</span></li>
							<li>投保状态：<span class="payje02"><#if (tInsuredCompanyReturnData.appStatus==1)>投保成功<#else>投保失败,原因：${tInsuredCompanyReturnData.insuranceResultMsg}</#if></span></li>
							<li>保单号：<span class="payje02">${tInsuredCompanyReturnData.policyNo}</span></li>
							<li>财务通知单号：<span class="payje02">${tInsuredCompanyReturnData.noticeNo}</span></li>
						</ul>
						if($.cookie("loginMemberUsername") == null){
						本次交易您将获得投保奖励积分若干个开心果，请及时点击这里<a href="#" id="wwwshowRegisterLogin">登录/注册</a>领取。
						首次注册成功，您还可以获得若干个开心果的注册奖励积分。同时您可以随时登录会员中心查看保单情况
						}
					</div>
				</div>
		  	</div>
		</div>
		<div class="clear"></div>
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
<#include "/wwwroot/kxb/block/community_v1.shtml">
</div>
</body>
</html>


