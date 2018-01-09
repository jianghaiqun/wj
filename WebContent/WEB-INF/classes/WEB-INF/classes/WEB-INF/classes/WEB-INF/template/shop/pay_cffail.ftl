<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${shopStaticPath}/template/shop/css/PayPromptSty.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	
		<div class="PayPromptMiddArea">
		    <div class="PayDetailsColumnArea_2 mb10"> 
		        <div class="paywrap01">
					<div class="payh3b">支付失败！原因：${ErrMsg}</div>
				</div>
		  	</div>
		</div>
		<div class="clear"></div>
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</div>
</body>
</html>


