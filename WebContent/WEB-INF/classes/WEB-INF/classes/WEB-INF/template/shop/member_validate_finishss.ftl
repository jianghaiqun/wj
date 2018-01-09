<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
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
<body>
<body><h2>操作成功</h2><br>
<span id="jumpTo">3</span>秒后自动跳转到<a href="${base}/wwwroot/kxb/">首页</a>
<script type="text/javascript">countDown(3,"${base}/wwwroot/kxb/index.shtml");</script>  

</body>
</html>