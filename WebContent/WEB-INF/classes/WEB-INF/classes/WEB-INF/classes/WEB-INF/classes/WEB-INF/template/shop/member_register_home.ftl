<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-注册页面</title>
<#include "/wwwroot/kxb/block/kxb_reg_header_css.shtml">
</head>
<body>
	<!-- head 开始 -->
		<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
    <!-- head 结束 -->
    
    <div class="reg_con">
      	<div class="reg_box_bg">
         <div class="reg-box">
        	<div id="reg-wrap">
                    <h3>
                        注册开心保<span>></span></h3>
                    <form method="post"   id="regform" onkeydown="if(event.keyCode==13) document.getElementById('btn-submit').click();">
                    <div class="reg_form">
                        <div class="row">
                            <label class="sd-form-label">
                                账号</label>
                            <div class="collection">
                                <label for="registerUserName" class="ph-label">
                                   请使用邮箱或手机号注册</label>
                                <input type="text" maxlength="40" name="registerUserName" id="registerUserName" class="ui-input " tabindex="1" >
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error1"> </div>
                        <div class="row">
                            <label class="sd-form-label">
                                密码</label>
                            <div class="collection">
                                <label for="regpassword" class="ph-label">
                                   密码须在6-16位之间</label>
                                <input type="password"  name="member.password" id="regpassword" class="ui-input " onpaste="return false" tabindex="2">
                            </div>
                        </div>
                      <div class="errror_boxs " id="error2"></div>
                        <div class="row">
                            <label class="sd-form-label">
                                重复密码</label>
                            <div class="collection">
                            <label for="realpassword" class="ph-label">
                                 请再次输入密码</label>
                                  <input type="password"  name="rePassword" id="realpassword" class="ui-input " onpaste="return false" tabindex="3">
                            </div>
                        </div>
                        <div class="errror_boxs " id="error3">
                        		
                        </div>
                        
                        
                        
                   <div class="row fm-item" id="veriCode" style="display:none">
                            <label class="sd-form-label">
                                验证码</label>
                            <div class="collection">
                            <label for="yz_btn2" class="ph-label"  >
                                 请输入右侧图片中的内容</label>
                                <input id="yz_btn2" class="ui-input yzm_input " type="text"   name="j_captcha"   tabindex="4" autocomplete="off" >
                            </div>
                            <div class="zym_cbox"><img src="" width="78" height="27" alt="验证码"  id="loginWindowCaptchaImage" /></div>
                        </div>
                         <div class="errror_boxs " id="error44">
                        		
                        </div>
                        
                        <div class="row reg-help-cnt">
                        <p class="margin_top_10"><p>
                           <input name="isAgreeAgreement" id="isAgreeAgreement_"  type="checkbox" value="true" checked="checked"/>已阅读并同意《<a id="showAgreementWindow"  class="showAgreementWindow" href="#">注册协议</a>》<span id="isAgreeAgreementMessagePosition"></span></div>
                        <div class="reg-submit">
                            <span class="ui-btn ui-btn-a">
                               <input type="button" value="立即注册" class="ui-btn-text" hidefocus="true" id="btn-submit" tabindex="5" >
                            </span>
                        </div>
                         <br>
                        <div class="clear"></div>
                        <div class="zc_zh_box">
                      已有开心保账号？ <a href="member!newLogin.action">点击登录</a>
                        </div>
                        <div id="form-validation-info" class="reg-error">
                        </div>
                        
                          <input type="hidden" name="member.VIPFrom" value="0"/>
                            <input type="hidden" name="backURL" value="${windowAddr}" id="backURL"/>
                    </div>
                 
                    </form>
                </div>

         </div>
        </div>
       
    </div>
    
    <div id="agreementWindow" class="agreementWindow">
     <div class="windowTop">
        <div class="windowTitle">注册协议</div>
        <a class="windowClose agreementWindowClose" href="#" hidefocus="true"></a>
     </div>
     <div class="windowMiddle">
         <div id="agreementContent"></div>
         <input type="button" id="agreementButton"  class="agreementButton agreementWindowClose" value="" hidefocus="true" />
     </div>
     <div class="windowBottom"></div>
</div>

<script type="text/javascript">
	  	// 点击刷新验证码图片
   function loginWindowCaptchaImageRefresh() {
		document.getElementById("loginWindowCaptchaImage").setAttribute("src", "${base}/captcha.jpg?timestamp" + (new Date()).valueOf()); 
	  }
	  //先加载验证码
   loginWindowCaptchaImageRefresh();
</script> 

<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.form.js"></script>
<script type="text/javascript">
jQuery().ready( function() {
	jQuery.agreementWindowShow = function () {
		jQuery("#agreementWindow").jqmShow();
	};
// 协议悬浮窗口
    jQuery("#agreementWindow").jqm({
		modal: true,// 是否开启模态窗口
		overlay: 0,// 屏蔽层透明度
		trigger: ".showAgreementWindow",// 激活元素
		closeClass: "agreementWindowClose",// 关闭按钮
		onShow: function(hash){
			if (jQuery.trim(jQuery("#agreementContent").html()) == "") {
				jQuery.ajax({
					beforeSend: function(data) {
						jQuery("#agreementContent").html('<span class="loadingIcon">&nbsp;</span> 加载中...');
					},
					url: "${base}/shop/member!getAgreement.action",
					success: function(data){
						jQuery("#agreementContent").html(data);
					}
				});
			}
			hash.w.fadeIn();
	    }
	}).jqDrag(".windowTop");
	
	jQuery("#agreementButton").click( function() {
    	jQuery("#isAgreeAgreement_").attr("checked", true);
    });
    
    
    /*注册*/
function n(s) {
    if (s.val() != "") {
        s.prev().hide()
    } else {
        s.prev().show()
    }
}

var k = $("#registerUserName");
var o = $("#regpassword");
var f = $("#realpassword");
var l = $("#btn-submit");
var q = $("#regform");
var a = $("#form-validation-info");
var r_mes = $("#repass");
var yz2 = $("#yz_btn2");
var e1 = $("#error1");
var e2 = $("#error2");
var e3 = $("#error3");
var e44 = $("#error44");

function checkPassword() {
    if (o.val().length < 6 || o.val().length > 16) {
        e2.addClass("error-inputs");
        e2.text("您的密码太短了，灰常不安全");
        o.removeClass("uimessage");
        return false;
    }
    else if(o.val().indexOf(" ")!=-1){
        e2.addClass("error-inputs");
        e2.text("密码是不能填写空格的，改一下吧");
        o.removeClass("uimessage");
        return false;
    }
    else {
        e2.removeClass("error-inputs");
        e2.text("");
        o.addClass("uimessage");
        return true;
    }
    
    
}

function wd() {
    if (o.val() != f.val()) {
        e3.addClass("error-inputs");
        e3.text("两次输入的密码一定要一致才能设置成功");
        o.removeClass("uimessage");
        f.removeClass("uimessage");
        return false;
    } else {
        e3.removeClass("error-inputs");
        e3.text("");
        f.addClass("uimessage");
        o.addClass("uimessage");
        return true;
    }
}

function checkUserName() {
    var userName = k.val();
    var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,4}$/; //验证邮箱的正则  允许@与.之间存在-
    var regu = /^[1][3-8][0-9]{9}$/;
    if (userName!=null&&userName!=""&&!myReg.test(userName) && !regu.test(userName)) {
        k.removeClass("uimessage");
        e1.addClass("error-inputs");
        e1.text("请输入正确的邮箱或手机号");
        return false;
    }

    return true;
}



k.on("focus",
function(s) {
    var t = $(this);
    t.closest(".collection").addClass("ph-focus");
});
k.on("blur",
function(s) {
    var t = $(this);
    t.closest(".collection").removeClass("ph-focus");
    k.removeClass("uimessage");
    var regName = k.val();
    if(regName!=null&&regName!=""){
    if(!checkUserName()) return false;
	     jQuery.ajax({
	        url: "${base}/shop/member!checkEmail.action?em=" + regName,
	        type: "post",
	        dataType: "json",
	        success: function(data) {
	            if (data == false) {
	                e1.addClass("error-inputs");
	                e1.text("这个邮箱或手机号已经注册过了");
	                k.removeClass("uimessage");
	            } else {
	                e1.removeClass("error-inputs");
	                e1.text("");
	                k.addClass("uimessage");
	
	            }
	        }
	    });
    }
   
});
k.on("keyup",
function(s) {
    var t = $(this);
    n(t)
});
k.on("change",
function(s) {
    var t = $(this);
    n(t);

});

f.on("focus",
function(s) {
    var t = $(this);
    t.closest(".collection").addClass("ph-focus");
});
f.on("blur",
function(s) {
    var t = $(this);
    t.closest(".collection").removeClass("ph-focus");
    f.removeClass("uimessage");
    if (f.val() != "" && o.val() != "") {
        wd();
    }
});
f.on("keyup",
function(s) {
    var t = $(this);
    n(t);
});
f.on("change",
function(s) {
    var t = $(this);
    n(t);

});

yz2.on("focus",
function(s) {
    var t = $(this);
    t.closest(".fm-item").addClass("ph-focus");
    n(t)
    yz2.removeClass("uimessage");
});
yz2.on("blur",
function(s) {
    var t = $(this);
    t.closest(".fm-item").removeClass("ph-focus");
    n(t);
});
yz2.on("keyup",
function(s) {
    var t = $(this);
    n(t)
});

q.find("input").each(function(t, s) {
    var u = $(this);
    n(u)
});
o.on("focus",
function(s) {
    var t = $(this);
    t.closest(".collection").addClass("ph-focus");
    n(t);

});

o.on("blur",
function(s) {
    var t = $(this);
    t.closest(".collection").removeClass("ph-focus");
    o.removeClass("uimessage");
    if (o.val() != "") {
        checkPassword();
    }
    if (f.val() != "" && o.val() != "") {
        wd();
    }

});

o.on("change",
function(s) {
    var t = $(this);
    n(t);
});

o.on("keyup",
function(s) {
    var t = $(this);
    n(t);
});


l.on("click",
function(s) {
    q.find(".error-input").removeClass("error-input");
    var A = k.val();
    if ($.trim(A) == "") {
        k.addClass("error-input");
        k.focus();
        e1.addClass("error-inputs");
        e1.show().text("请填写正确账号才行哦");
        remarkError();
        return false;
    }
     if(!checkUserName()) return false;
    var w = o.val();
    if ($.trim(w) == "") {
        o.addClass("error-input");
        o.focus();
        e2.addClass("error-inputs");
        e2.show().text("密码是一定要写滴~");
        remarkError();
        return false;
    }
    if(!checkPassword()) return false;
    var t = f.val();
    if ($.trim(t) == "") {
        f.addClass("error-input");
        f.focus();
        e3.addClass("error-inputs");
        e3.show().text("不要忘记写确认密码哦");
        remarkError();
        return false;
    }
    
    if(!wd()) return false;
    var y = yz2.val(); //验证码
     if(document.getElementById('veriCode').style.display==""){
    if ($.trim(y) == "") {
        f.addClass("error-input");
        f.focus();
        e44.addClass("error-inputs");
        e44.show().text("这里一定要写上验证码才行");
        return false;
    }
    }
    
    q.ajaxSubmit({
			url:'${base}/shop/member!newAjaxRegister.action',
			type: "post",    
			dataType: "json",
			success: function(data) {
			if (data.status == "success") {
			var orgBackURL=$("#backURL").val();
			var backURL=encodeURIComponent(orgBackURL);
			location.href="${base}/shop/member!registerSuccess.action?registerUserName="+A+"&backURL="+backURL;
			} 
			else {
				loginWindowCaptchaImageRefresh();
				yz2.val("");
		        e44.addClass("error-inputs");
		        e44.show().text(data.message);
				
			}
		}
	});
    

    return true;
})
		// 点击刷新验证码图片
		$("#loginWindowCaptchaImage").click(function(){
			loginWindowCaptchaImageRefresh();
		});  
});
//标记注册错误次数
function remarkError(){
	jQuery.ajax({
				url: '${base}/shop/member!remarkError.action',
				type: "post",
				dataType: "json",
				success: function(data){
					if (data == false) {
		               document.getElementById('veriCode').style.display="";
		            }
			 	}			
			});
}
//查询注册错误次数
queryRemarkError();
function queryRemarkError(){
	jQuery.ajax({
					url: '${base}/shop/member!queryRemarkError.action',
					type: "post",
					dataType: "json",
					success: function(data){
						if (data == false) {
			               document.getElementById('veriCode').style.display="";
			            }else{
			               document.getElementById('veriCode').style.display="none";
			            }
				 	}			
				});
}
</script> 

</body>
</html>
