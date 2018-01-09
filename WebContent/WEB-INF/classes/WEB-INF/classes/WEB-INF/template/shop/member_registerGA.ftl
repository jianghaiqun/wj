<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<title></title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<#assign flag=false>
<#if flag>
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
</#if>
<body>
<#if windowNewAddr??>
	<#assign redirectAddr=windowNewAddr>
	<#assign pagename="新页面">
<#elseif windowAddr??>
	<#assign redirectAddr=windowAddr>
	<#assign pagename="原页面">
<#else>
	<#assign redirectAddr=front+"/index.shtml">
	<#assign pagename="首页">
</#if>
<#if message??>
	<#assign messageContent=message>
<#else>
	<#assign messageContent="会员注册成功！">
</#if>
<#if flag>
<div class="wrapper" style="display:none;">
<#include "/wwwroot/kxb/block/kxb_header.shtml">
   
     <div style="height:50px;"></div>
     <div style="text-align:center;">
		 <h2 style="font-size:25px;font-weight: bold;">${messageContent}</h2><br>
		 <span id="jumpTo">3</span>秒后自动跳转到<a href="${redirectAddr}">${pagename}</a> 
		 <script type="text/javascript">countDown(3,"${redirectAddr}");</script>
	 </div>
<#include "/wwwroot/kxb/block/kxb_footer.shtml">
</div>
<#else>
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/wwwroot/kxb/js/footer.js"></script>
<script type="text/javascript">
	window.location.href = "${redirectAddr}";
</script>
</#if>
</body>
</html>