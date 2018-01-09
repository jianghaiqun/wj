<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-登录页面</title>
</head>

<link type="text/css" rel="stylesheet" href="http://localhost:8080/style_index/new/main.css">
<link rel="stylesheet" type="text/css" href="http://localhost:8080/style/register.css"/>
<script type="text/javascript" src="http://localhost:8080/js/artDialog.js"></script>
<script type="text/javascript" src="http://localhost:8080/js/jquery-1.4a2.min.js"></script>  
<script	src="http://localhost:8080/js/iframeTools.js"></script>



<style type="text/css">
#reg-wrap{ position:relative !important; top:0px; left:0px;}
</style>

<body>
        
  <div id="reg-wrap">
                    <h3>
                        登录开心保<span>></span></h3>
                    <form method="post" action="" id="login_form">
                    <div class="reg_form">
                        <div class="row fm-item">
                            <label class="sd-form-label">
                                帐号</label>
                            <div class="collection ">
                               <label for="logonId" class="fm-label">
                            邮箱或手机号码</label>
                                <input id="logonId" class="ui-input " type="text" maxlength="100" tabindex="1" value="" name="logonId" autocomplete="off" >
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error4"></div>
                        <div class="row fm-item">
                            <label class="sd-form-label">
                                密码</label>
                            <div class="collection">
                               <label for="password_input" class="fm-label ">
                                 请输入正确的邮箱/手机号</label>
                             
                               <input type="password" tabindex="2" class="ui-input " value="" name="password_input" onpaste="return false" id="password_input" autocomplete="off" >
                            </div>
                        </div>
                      <div class="errror_boxs " id="error5"></div>
                        <div class="row fm-item">
                            <label class="sd-form-label">
                                验证码</label>
                            <div class="collection">
                            <label for="yz_btn" class="ph-label"  >
                                 请输入右侧图片中的内容</label>
                                <input id="yz_btn" class="ui-input yzm_input " type="text"  value="" name="yam_btn" tabindex="3" autocomplete="off" >
                            </div>
                            <div class="zym_cbox"><img src="${staticPath}/images/7_03.gif" width="78" height="27" alt="验证码" /></div>
                        </div>
                        <div class="errror_boxs " id="error6">
                        		
                        </div>
                        
                        
                        <div class="row reg-help-cnt reg-help-cnt2">
         
                          
                          <label for="low-login-enable" class="fm-label-new">
                                <input type="checkbox" checked="checked" class="check1" name="low-login-enable" value="1" tabindex="5" id="low-login-enable">
                                记住我的账号</label>
                          </div>
                        <div class="fm-submit-box">
                             <span class="ui-btn ui-btn-c">
                            <input type="submit" value="登录" tabindex="4" hidefocus="true" class="ui-btn-text login_btns" id="btn-submit">
                        </span>
                        </div>
                        <div class="clear"></div>
                        <div class="zc_zh_box">
                     <a href="message/retrieve.html">忘记密码</a>  |  还没有开心保帐号？<a href="kxb_register.html">点击注册</a>
                        </div>
                        <div id="form-validation-info" class="reg-error">
                        </div>
                    </div>
                    </form>
                </div>


</body>
</html>
