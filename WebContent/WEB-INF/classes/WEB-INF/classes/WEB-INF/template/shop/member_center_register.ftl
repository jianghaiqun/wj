<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-注册页面</title>
<#include "/wwwroot/kxb/block/kxb_reg_header_new_css.shtml">
</head>

<body>
    <!-- 顶部 开始 1-->
	<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml" >
	<!-- 顶部 结束 -->
    
    <div class="reg_con">     
      	<div class="reg_box_bg">
         	<div class="reg-box up_ragbox set_user_box">
        
        		<div id="reg-wrap">
	        		<div class="jxw_kxb"></div>
	                <h3>设置账号</h3>
                	<div class="lh_title cf">
		                <div class="lh_tou"> <img id="lh_img" src="${bindInfoForLogin.avatar}" height="60"  alt=""/></div>
		                <div class="lh_tit_p">您好,${bindInfoForLogin.userNickName}</br >欢迎使用${bindInfoForLogin.comName}登录开心保网站！<br>已有会员账号绑定可享专属优惠！<br>跳过绑定此单不享受会员优惠哦！</div>
	                </div>
	                    
	                <ul class="reg_ul_tag" id="mod-ul">
	                    <li class="hover">创建新账号</li>
	                    <li>绑定已有账号</li>
	                </ul>
                    <div class="clear"></div>
                    <div id="mod-con">
                    	<div class="reg_form">
	                    	<form method="post" action="" id="regform">
	                        	<div class="row">
	                            	<div class="collection">
	                            		<label class="fm-label-name" ></label>
	                                	<label class="ph-label" for="registerUserName">请使用邮箱或手机号注册</label>
	                                	<input type="text" tabindex="1" class="ui-input " id="registerUserName" name="registerUserName" maxlength="40">
	                            	</div>
	                        	</div>
	                        	<div class="errror_boxs "  id="error1"> </div>
	                        	<div class="row">
	                            	<div class="collection">
	                            		<label class="fm-label-name fm-label-pass" ></label>
	                                	<label class="ph-label" for="regpassword">密码须在6-16位之间</label>
	                                	<input type="password" tabindex="2" onpaste="return false" class="ui-input " id="regpassword" name="member.password">
	                            	</div>
	                        	</div>
	                      		<div class="errror_boxs " id="error2"></div>
	                        	<div class="row">
	                            	<div class="collection">
	                            		<label class="fm-label-name fm-label-pass" ></label>
	                            		<label class="ph-label" for="realpassword">请再次输入密码</label>
	                                    <input type="password" tabindex="3" onpaste="return false" class="ui-input " id="realpassword" name="rePassword">
	                            	</div>
	                        	</div>
	                        	<div class="errror_boxs " id="error3"></div>
		                        <div class="row fm-item">
			                        
				                    <div class="row fm-item" id="regveriCode" style="display:none">
				                        <div class="collection">
				                        <label for="yz_btn3" class="ph-label yzm_labels"  >
				                           		  请输入右侧图片中的内容</label>
				                            <input id="yz_btn3" class="ui-input yzm_input " type="text"   name="j_captcha"   tabindex="4" autocomplete="off" >
				                        </div>
				                        <div class="zym_cbox"><img src="" width="120" height="27" alt="验证码"  id="regWindowCaptchaImage" /></div>
				                     <div id="error44" class="errror_boxs "></div>
				                    </div>
			                    </div>
								<div class="clear h10"></div>
								<div class="row reg-help-cnt">
		                        	<p>
		                           		<input type="checkbox" checked="checked" value="true" id="isAgreeAgreement_" name="isAgreeAgreement">已阅读并同意
		                           		<a href="#" class="showAgreementWindow" id="showAgreementWindow">《注册协议》</a><span id="isAgreeAgreementMessagePosition"></span>
		                            </p>
		                        </div>                        
		                        <div class="reg-submit">
		                            <span class="ui-btn ui-btn-a">
		                               <input type="button" value="立即注册" class="ui-btn-text login_btns" hidefocus="true" id="btn-submit" tabindex="5" >
		                            </span>
		                        </div>
		                        <div class="clear"></div> 
		                        <input type="hidden" id="out_member" value="member"/>
		                        <input type="hidden" name="member.VIPFrom" value="0"/>
		                        <input type="hidden" name="backURL" value="${loginBackURL}" id="backURL"/>
		                        <input type="hidden" name="bindInfoForLogin.openID" value="${(bindInfoForLogin.openID)!}" id="openID"/>
		                        <input type="hidden" name="bindInfoForLogin.id" value="${(bindInfoForLogin.id)!}" id="id"/>
		                        <input type="hidden" name="bindInfoForLogin.comCode" value="${(bindInfoForLogin.comCode)!}" id="comCode"/>
		                        <input type="hidden" name="bindInfoForLogin.comName" value="${(bindInfoForLogin.comName)!}" id="comName"/>
		                        <input type="hidden" name="bindInfoForLogin.userEmail" value="${(bindInfoForLogin.userEmail)!}" id="userEmail"/>
		                        <input type="hidden" name="bindInfoForLogin.userPhone" value="${(bindInfoForLogin.userPhone)!}" id="userPhone"/>
		                        <input type="hidden" name="bindInfoForLogin.avatar" value="${(bindInfoForLogin.avatar)!}" id="userPhone"/>
		                        <div id="form-validation-info" class="reg-error"></div>
	                    	
	                    	</form>
	                    </div>
	                    <div class="from_sf_box" style="display: none;">
	                    	<form onkeydown="if(event.keyCode==13) document.getElementById('loginAjax').click();" id="login_form1" action="" method="post">
	                    		<input type="hidden" name="judge" id="judge" value="${judge}">
	                    		<input type="hidden" name="orderSn" id="orderSn" value="${orderSn}">
	                    		<div class="reg_form">
	                    			<div class="row fm-item">
	                    				<div class="collection ">
	                    					<label class="fm-label-name" for="loginUsername"></label>
	                    					<label class="fm-label" for="loginUsername">请输入邮箱或手机号</label>
	                    					<input type="text" autocomplete="off" name="loginName" value="" tabindex="1" maxlength="100" class="ui-input " id="loginUsername">
	                    				</div>
	                    			</div>
	                    			<div id="error11" class="errror_boxs "></div>
	                    			<div class="row fm-item">
	                    				<div class="collection">
	                    					<label class="fm-label-name fm-label-pass" for="password_input"></label>
	                    					<label class="fm-label " for="password_input">请输入密码</label>
	                    					<input type="password" autocomplete="off" id="password_input" onpaste="return false" name="member.password" value="" class="ui-input " tabindex="2">
	                    				</div>
	                    			</div>
	                    			<div id="error22" class="errror_boxs "></div>
	                    			<div id="logveriCode" class="row fm-item" style="display:none;">
	                    				<div class="collection">
	                    					<label class="ph-label yzm_labels" for="yz_btn2">请输入右侧图片中的内容</label>
	                    					<input type="text" autocomplete="off" tabindex="4" name="j_captcha" class="ui-input yzm_input " id="yz_btn2">
	                    				</div>
	                    				<div class="zym_cbox"><img width="120" height="38" id="loginWindowCaptchaImage" alt="验证码" src=""></div>
	                    				<div id="error33" class="errror_boxs "></div>
	                    			</div>
	                    			<div class="fm-submit-box">
	                    				<span class="ui-btn ui-btn-c"><input type="button" id="loginAjax" class="ui-btn-text login_btns f_mi" hidefocus="true" tabindex="4" value="立即绑定"></span>
	                    			</div>
	                    			<div class="clear"></div>
	                    			<div class="reg-error" id="form-validation-info"></div>
	                    		</div>
	                    		<input type="hidden" name="member.VIPFrom" value="0"/>
		                        <input type="hidden" name="backURL" value="${loginBackURL}" id="backURL"/>
		                        <input type="hidden" name="bindInfoForLogin.openID" value="${(bindInfoForLogin.openID)!}" id="openID"/>
		                        <input type="hidden" name="bindInfoForLogin.id" value="${(bindInfoForLogin.id)!}" id="id"/>
		                        <input type="hidden" name="bindInfoForLogin.comCode" value="${(bindInfoForLogin.comCode)!}" id="comCode"/>
		                        <input type="hidden" name="bindInfoForLogin.comName" value="${(bindInfoForLogin.comName)!}" id="comName"/>
		                        <input type="hidden" name="bindInfoForLogin.userEmail" value="${(bindInfoForLogin.userEmail)!}" id="userEmail"/>
		                        <input type="hidden" name="bindInfoForLogin.userPhone" value="${(bindInfoForLogin.userPhone)!}" id="userPhone"/>
		                        <input type="hidden" name="bindInfoForLogin.avatar" value="${(bindInfoForLogin.avatar)!}" id="userPhone"/>
	                    	</form>
	                    </div>
	                </div>
                    <div class="zc_zh_box">
                        <a href="http://www.kaixinbao.com">以后再说，立即去购买！</a>
                    </div>
                  
                </div>
                <!--注册成功提示-->
                <div id="reg-wrap" class="zccg_box"  style=" display:none;">
<script type="text/javascript">
        function delayURL(url) {
            var times = document.getElementById("time").innerHTML; 
            if (times > 0) {
                times--;
                document.getElementById("time").innerHTML = times;
            } else {
                window.top.location.href = url; 
            }
            setTimeout("delayURL('" + url + "')", 1000);    
        }
</script>

                    <h3>
                        恭喜您注册成功！<span>></span></h3>
                        <div class="message_conts">
                    我们将发送一封验证邮件到您的邮箱<br />
为了您的安全请尽快验证
</div>
<p class="tz_box">
<span id="time" style="color:red">5</span>秒钟后自动跳转，请稍候<br />

如果没有自动跳转，请点击这里<a href="http://www.kaixinbao.com">跳回首页</a>
 </p>              
 <!--注册成功后调用以上模块和此js 路径需要获取
  <script type="text/javascript">
    delayURL("http://www.kaixinbao.com"); //arti
</script>
-->
  </div>
                <!--注册成功提示-->  
                
                
                
                
                
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
							         <input type="button" id="agreementButton"  class="agreementButton agreementWindowClose" value="同意" hidefocus="true" />
							     </div>
							     <div class="windowBottom"></div>
</div>
<!-- 底部开始 -->
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!-- 底部结束 -->
<!-- js加载 -->
<!-- js加载 -->
<#include "/wwwroot/kxb/block/kxb_reg_header_js.shtml">
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.form.js"></script>

<script type="text/javascript">

	  	// 点击刷新验证码图片
   function loginWindowCaptchaImageRefresh() {
		document.getElementById("loginWindowCaptchaImage").setAttribute("src", "${base}/captcha.jpg?timestamp" + (new Date()).valueOf()); 
	  }
   function regWindowCaptchaImageRefresh() {
		document.getElementById("regWindowCaptchaImage").setAttribute("src", "${base}/captcha.jpg?timestamp" + (new Date()).valueOf());
	  }
	  regWindowCaptchaImageRefresh();
jQuery().ready( function() {
	jQuery('#mod-ul li').tagclickbind();
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

var k = jQuery("#registerUserName");
var o = jQuery("#regpassword");
var f = jQuery("#realpassword");
var l = jQuery("#btn-submit");
var q = jQuery("#regform");
var a = jQuery("#form-validation-info");
var r_mes = jQuery("#repass");
var yz2 = jQuery("#yz_btn3");
var e1 = jQuery("#error1");
var e2 = jQuery("#error2");
var e3 = jQuery("#error3");
var e44 = jQuery("#error44");

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
    n(t);
});
k.on("blur",
function(s) {
    var t = $(this);
    t.closest(".collection").removeClass("ph-focus");
    k.removeClass("uimessage");
    var regName = k.val();
    if(regName!=null&&regName!=""){
    	if(!checkUserName()) return false;
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
//查询注册错误次数及同一ip注册次数
regqueryRemarkError();
function regqueryRemarkError(){
	jQuery.ajax({
					url: '../shop/member!queryRemarkError.action',
					type: "post",
					dataType: "json",
					success: function(data){
						if (data == false) {
			               document.getElementById('regveriCode').style.display="";
			            }else{
			               document.getElementById('regveriCode').style.display="none";
			            }
				 	}			
				});
}

l.on("click",
function(s) {
    q.find(".error-input").removeClass("error-input");
    var A = k.val();
    if ($.trim(A) == "") {
        k.addClass("error-input");
        k.focus();
        e1.addClass("error-inputs");
        e1.show().text("账号不能为空");
        return false;
    }
     if(!checkUserName()) return false;
    var w = o.val();
    if ($.trim(w) == "") {
        o.addClass("error-input");
        o.focus();
        e2.addClass("error-inputs");
        e2.show().text("密码是一定要写滴~");
        return false;
    }
    if(!checkPassword()) return false;
    var t = f.val();
    if ($.trim(t) == "") {
        f.addClass("error-input");
        f.focus();
        e3.addClass("error-inputs");
        e3.show().text("密码确认不能为空");
        return false;
    }
    if(jQuery("#isAgreeAgreement_").attr("checked") != "checked"){
		alert("请勾选注册协议");
		return false;
	}
	
	jQuery.ajaxLoadImg.show('showid');
    q.ajaxSubmit({ 
			url:'${base}/shop/member!otherRegister.action',
			type: "post",    
			dataType: "json",
			success: function(data) {
			jQuery.ajaxLoadImg.hide('showid');
			if (data.status == "success") {
			var orgBackURL=$("#backURL").val();
			var backURL=encodeURIComponent(orgBackURL);
			location.href="${base}/shop/member!registerSuccess.action?registerUserName="+A+"&backURL="+backURL;
			} 
			else {
				regqueryRemarkError();
			 
				regWindowCaptchaImageRefresh();
				yz2.val("");
				if (document.getElementById('regveriCode').style.display == '') {
					e44.addClass("error-inputs");
			        e44.show().text(data.message);
				} else {
				    e3.addClass("error-inputs");
	        	    e3.show().text(data.message);
		        }
			}
		}
	});	
	return true; 
})
		// 点击刷新验证码图片
		$("#regWindowCaptchaImage").click(function(){
			regWindowCaptchaImageRefresh();
		});  
});
// 登录绑定


function n(s) {
    if (s.val() != "") {
        s.prev().hide()
    } else {
        s.prev().show()
    }
}



var k = $("#loginUsername");
var o = $("#password_input");
var l = $("#loginAjax");
var yz = $("#yz_btn2");
var e11 = $("#error11");
var e22 = $("#error22");
var e33 = $("#error33");
var t1 = $("#judge");
var t2 = $("#orderSn");



function checkUserName() {
    var userName = k.val();
    var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,4}$/; //验证邮箱的正则
    var regu = /^[1][3-8][0-9]{9}$/;
    if (userName!=null&&userName!=""&&!myReg.test(userName) && !regu.test(userName)) {
        k.removeClass("uimessage");
        e11.addClass("error-inputs");
        e11.text("正确的邮箱或手机号是登录的唯一凭证哟");
        return false;
    }

    return true;
}


function checkPassword() {
    if (o.val().length < 6 || o.val().length > 16) {
        e22.addClass("error-inputs");
        e22.text("您的密码太短了，灰常不安全");
        o.removeClass("uimessage");
        return false;
    } 
     else if(o.val().indexOf(" ")!=-1){
        e22.addClass("error-inputs");
        e22.text("密码是不能填写空格的，改一下吧");
        o.removeClass("uimessage");
        return false;
    }else {
        e22.removeClass("error-inputs");
        e22.text("");
        return true;
    }
}




k.on("focus",
function(s) {
    var t = $(this);
    t.closest(".collection").addClass("ph-focus");
    n(t);
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
          url:"/wj/shop/member!checkRegisterName.action?regName=" + regName,
          type: "post",
          dataType: "json",
          success: function(data) {
          		if (data.status == "true") {
	                e11.removeClass("error-inputs");
	                e11.text("");
	                if (document.getElementById('logveriCode').style.display!="") {
		                if (data.remarkFlag == "true") {
					        document.getElementById('logveriCode').style.display="";
					        loginWindowCaptchaImageRefresh();
					        
		                }else{
					        document.getElementById('logveriCode').style.display="none";
					    }
	                }
	                
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


yz.on("focus",
function(s) {
    var t = $(this);
    t.closest(".fm-item").addClass("ph-focus");
    n(t)
});
yz.on("blur",
function(s) {
    var t = $(this);
    t.closest(".fm-item").removeClass("ph-focus");
    n(t);
});
yz.on("keyup",
function(s) {
    var t = $(this);
    n(t)
});

l.find("input").each(function(t, s) {
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


//提交
l.on("click",
function(s) {
    var A = k.val();
    if ($.trim(A) == "") {
        k.addClass("error-input");
        k.focus();
        e11.addClass("error-inputs");
        e11.show().text("请填写正确账号才行哦!");
        logremarkError();
        return false;
    }
     if(!checkUserName()) return false;
    var w = o.val();
    if ($.trim(w) == "") {
        o.addClass("error-input");
        o.focus();
        e22.addClass("error-inputs");
        e22.show().text("密码是一定要写滴~");
        logremarkError();
        return false;
    }
     if(!checkPassword()) return false;
    var y = yz.val(); //验证码
    if(document.getElementById('logveriCode').style.display==""){
      if ($.trim(y) == "") {
          yz.addClass("error-input");
          yz.focus();
          e33.addClass("error-inputs");
          e33.show().text("验证码不能为空哟");
          return false;
      }
    }
    var judge = t1.val();
    var orderSn = t2.val();
    
    jQuery.ajaxLoadImg.show('showid');
  jQuery("#login_form1").ajaxSubmit({
      url:'/wj/shop/member!otherLogin.action?judge='+judge+'&orderSn='+orderSn,
      type: "post",    
      dataType: "json",
      success: function(data) {
      jQuery.ajaxLoadImg.hide('showid');
      if (data.status == "success") {
          if (data.firstLoginAfterUngrade == '1') {
            jQuery.cookie("firstLoginAfterUngrade", "1", {expires: 1,path: '/'});
          }
        

          jQuery.cookie("VIPUsername", null);
        
        if (data.pointsChange == "1") {
          jQuery.cookie("pointsChange", "1",{ expires: 30,path: '/' });
        } else {
          jQuery.cookie("pointsChange", null,{ path: '/' });
        }
        //zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 start
        var tCommentUser = document.getElementById('CommentUser');
        if ((null != tCommentUser) && ("undefined" != typeof(tCommentUser)))
        {
          tCommentUser.innerHTML = jQuery.cookie("loginMemberUsername");
          tCommentUser.title = jQuery.cookie("loginMemberUsername");
        }
        var trevw10cont02 = document.getElementById('revw10cont02');
        if ((null != trevw10cont02) && ("undefined" != typeof(trevw10cont02)))
        {
          trevw10cont02.style.display="";
          document.getElementById('revw10cont01').style.display="none";
        }
        //zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 end
        
        
        var lbURL="http://www.kaixinbao.com/";
        if(lbURL!=null&&lbURL!=''&&lbURL.charAt(lbURL.length-1)!='?'){
           window.location.href = "http://www.kaixinbao.com/";
           return;
        }
        //var redirectionUrl = jQuery.cookie("redirectionUrl");
        var redirectionUrl= "";
        if(redirectionUrl != null && redirectionUrl != "") {
          window.location.href = redirectionUrl;
          return;
        }else{
          window.location.href="http://www.kaixinbao.com";
          return;
        }
      
      
      }else if(data.status == "content"){
        
        jQuery.cookie("VIPUsername", null);
        
        alert("会员登录成功,首次登录请修改密码！");
        location.href = "/wj/shop/password!edit.action";
      } else if(data.status == "memberCenter"){
        location.href = "/wj/shop/profile!edit.action";
      }else if(data.status == "MailJump"){
        location.href =data.message;
      }else {
        loginWindowCaptchaImageRefresh();
        yz.val("");
        //o.val("");
        
            if("密码没有写对哟" == data.message || (document.getElementById('logveriCode').style.display!="" && "验证码没有写对哟" != data.message)){
              e22.addClass("error-inputs");
              e22.show().text(data.message);
            }else{
	            if (data.message != null && data.message.indexOf("您输入的账号已经绑定过") >= 0) {
	            	e11.addClass("error-inputs");
              		e11.show().text(data.message);
	            } else {
	            	e33.addClass("error-inputs");
	            	e33.show().text(data.message);
	            }
            
            }
            // 该处为登陆时 密码3次 错误，刷新验证码
            logqueryRemarkError();
        //zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 start
        var trevw10cont02 = document.getElementById('revw10cont02');
        if ((null != trevw10cont02) && ("undefined" != typeof(trevw10cont02)))
        {
          trevw10cont02.style.display="none";
          document.getElementById('revw10cont01').style.display="";
        }
        //zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 end
      }
    }
  });

});

	
function logqueryRemarkError(){
	jQuery.ajax({
					url: '${base}/shop/member!queryRemarkError.action',
					type: "post",
					dataType: "json",
					success: function(data){
						if (data == false) {
			               document.getElementById('logveriCode').style.display="";
			               loginWindowCaptchaImageRefresh();
			            }else{
			               document.getElementById('logveriCode').style.display="none";
			            }
				 	}			
				});
}

// 登录点击刷新验证码图片
    $("#loginWindowCaptchaImage").click(function(){
      loginWindowCaptchaImageRefresh();
    }); 

//标记登录错误次数
function logremarkError(){
  jQuery.ajax({
        url: '/wj/shop/member!remarkError.action',
        type: "post",
        dataType: "json",
        success: function(data){
          if (data == false) {
                   document.getElementById('logveriCode').style.display="";
                   loginWindowCaptchaImageRefresh();
                }
        }     
      });
}

</script> 

</body>
</html>
