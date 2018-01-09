<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<script type="text/javascript">     
function countDown(secs,surl){     
 var jumpTo = document.getElementById('jumpTo');
 jumpTo.innerHTML=secs;  
 if(--secs>0){     
     setTimeout("countDown("+secs+",'"+surl+"')",1000);     
     }     
 else{       
     location.href=surl;     
     }     
 }     
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

   
     <div style="height:50px;"></div>
     <div style="text-align:center;">
		 <h2 style="font-size:25px;font-weight: bold;">您的操作成功</h2><br>
		 <span id="jumpTo">3</span>秒后自动跳转到<a href="${front}/">首页</a>
		 <script type="text/javascript">countDown(3,"${front}/index.shtml");</script>  
	 </div>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</div>
</body>
</html>