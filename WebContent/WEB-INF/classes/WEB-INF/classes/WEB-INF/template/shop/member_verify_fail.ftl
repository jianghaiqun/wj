<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-注册成功页面</title>
<#include "/wwwroot/kxb/block/kxb_reg_header_css.shtml">
</head>
<body >
	<!-- head 开始 -->
	<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
    <!-- head 结束 -->
    
     <div class="reg_con_box">
       <div class="message_hk">
        <!--邮件注册提示信息-->
           <div class="mes_cons error_g">
         <h3>很抱歉，${verifyError}验证失败</h3>
         <p>您的账号是：<em>${mobileOrEmail}</em>, 您以后可以使用此账号登录</p>

<p class="error_des_cs">您可以点击<a href="${reVerifyURL}" class="fh_link">重新发送</a> 来进行验证，我们将再次发送验证邮件到您的邮箱，请注意查收</p>

<p class="error_link">如需帮助可以联系在线客服或拨打<em>4009-789-789</em>，谢谢</p>
       </div>
         <!--邮件注册提示信息-->
       </div>
    </div>

     <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">   
         
</body>
</html>
