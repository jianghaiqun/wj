<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${shopStaticPath}/template/shop/css/PayPromptSty.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
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
		    <div class="PayDetailsColumnArea_2 mb10"><!--     订单创建成功  begin   -->
		        <div class="paywrap01">
					<div class="payh3b">支付失败！原因：${ErrMsg} </div>
				</div>
		  	</div><!--    订单创建成功  end    -->
		</div>
		<div class="clear"></div>
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
</div>
</body>
</html>


