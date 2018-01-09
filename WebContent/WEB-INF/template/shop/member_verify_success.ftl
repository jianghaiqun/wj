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
           <div class="mes_cons success_g">
         <h3>恭喜您验证成功！</h3>
         <p>您的账号是：<em>${mobileOrEmail}</em>, 您以后可以使用此账号登录</p>

<p>您现在购买保险可以获取积分，积分可以在积分商城中兑换，请妥善保管好您的账号密码</p>

<p><span id="time" style="color:red">5</span>秒钟后自动跳转，请稍候</p>

<p>如果没有自动跳转，请点击<a href="${frontPage}" class="fh_link">这里返回</a></p>
       </div>
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
    delayURL("${frontPage}"); //arti
</script>

</body>
</html>
