<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-注册成功页面</title>
<#include "/wwwroot/kxb/block/kxb_reg_header_new_css.shtml">
</head>
<body >
	<!-- head 开始 -->
	<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
    <!-- head 结束 -->
    
    <div class="reg_box_bg">
    
       <div class="message_hk">
       <!--手机注册提示信息-->
        <#if (member.registerType=="1")>
       <div class="mes_cons"  >
         <h3>恭喜您注册成功！</h3>
         <p>您的账号是：<em>${registerUserName}</em>, 您以后可以使用此账号登录</p>

<p>为了您的账号安全，您可以到会员中心进行验证！</p>

<p><span id="time" style="color:red">5</span>秒钟后自动跳转，请稍候</p>

<p>如果没有自动跳转，请点击<a href="${backURL}" >这里返回</a></p>
<div class="from_jxw"></div>
       </div>
       </#if> 
         <!--手机注册提示信息-->
         
         
          <!--邮件注册提示信息-->
           <#if (member.registerType=="0")> 
          <div class="mes_cons"  >
         <h3>恭喜您注册成功！</h3>
         <p>您的账号是：<em>${registerUserName}</em>, 您以后可以使用此账号登录</p>

<p>我们将发送一封验证邮件到您的邮箱,为了您的账号安全请尽快验证！</p>

<p><span id="time" style="color:red">5</span>秒钟后自动跳转，请稍候</p>

<p>如果没有自动跳转，请点击<a href="${backURL}">这里返回</a></p>
<div class="from_jxw"></div>
       </div>
       </#if>
         <!--邮件注册提示信息-->
         
       </div>
    </div>
 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">   
    
 <script type="text/javascript">
        function delayURL(url) {
       
            var times = document.getElementById("time").innerHTML; 
            if (times > 0) {
                times--;
                document.getElementById("time").innerHTML = times;
                setTimeout("delayURL('" + url + "')", 1000);   
            } else {
                window.top.location.href = url; 
            }
           
        }
</script>
  
  <script type="text/javascript">
    delayURL("${backURL}"); //arti
</script>

</body>
</html>
