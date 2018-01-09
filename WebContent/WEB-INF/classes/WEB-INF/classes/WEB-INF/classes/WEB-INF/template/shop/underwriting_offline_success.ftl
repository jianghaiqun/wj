<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>线下核保提交成功页</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css">
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"  />
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if> 



       <div class="wrapper order-bg">
            <div class="order-mes-des">
                <img src="${staticPath}/style/shop/images/success.gif" alt="" width="260px" height="260px" class="order-mes-img">
                <div class="order-mesbox">
                    <h2 class="mesbox-tit f_mi">恭喜您提交成功！${productName} <br></h2>
                    <p class="line_down_tip">审核时间<em>3-5</em>个工作日，请保持手机畅通，接收区号010的客服电话通知！请注意查收审核结果邮件！</p>
                    <p class="hs_fontsf">如有任何问题，请及时联系我们的<a class="zixun"  href="javascript:void(0);" >在线客服</a>。</p>
                </div>
                <div class="clear"></div>

            </div>
	<div class="bore-des-h clear"></div>

	<div class="order-weichart cf">
        <h3 class="or-mes-tit"><b  class="f_mi">关注微信公众号</b></h3>
        <img src="${staticPath}/style/shop/images/weichart_07.gif"  width="102px" height="102px" class="or-weichartimg">
         <div class="wiechat-des">
             <b class="f_mi">扫一扫关注后，您可以</b>
             <ul><li>随时随地咨询理赔问题</li>
                 <li>手机也可以购买保险</li>
                 <li>获取最新的优惠活动</li></ul>
         </div>
    </div>

    <div class="order-orter">
        <a href="${productDetailed}"  class="order-aooa f_mi">返回产品详情</a>
        <a href="/" class="order-aooa f_mi">浏览其他商品</a>
    </div>
 
    <div class="h20"></div> 
    <div class="clear"></div>


    <#if (productUse != '')!>
      <div class="weaper tj_list">
          <h3 class="f-titlesf"><span>购买该产品的用户还买了 </span></h3>
	      ${productUse}
      </div>	 
   </#if> 
</div>
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if>

<script>
function callback(){
	var orderSn = '${orderId}';
	$.ajax({
		url: sinosoft.base + "/shop/sales_volume!visitBackOnLine.action?orderSn="+orderSn,
		type: "post",
		dataType: "json", 
		success : function(data) {
		}
	});  
}

 jQuery(function(){
     jQuery(".order_tk span").click(function(){
       var title = jQuery(this).find("i").text();
       art.dialog({
            id: 'xyid',
            fixed: true,
            title: title,
            content: document.getElementById('order_xy'),
            button: [
                {
                    name: '我已仔细阅读',
                    callback: function () {
                    	callback();
                    },
                    focus: true
                },
                {
                    name: '没空阅读',
                    callback: function () {
                       
                    }
                }
            ]
        });
     })
 })

</script>

<!-- 百分点发送逻辑 end -->
<#include "/wwwroot/kxb/block/community_v1.shtml"> 
 
</body>

</html>


