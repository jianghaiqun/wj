<!DOCTYPE html > 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-登录页面</title>
<link rel="stylesheet"  type="text/css" href="/style/redesign/re_header.css"/>
<link rel="stylesheet"  type="text/css" href="/style/redesign/re_register.css"/>
<script type="text/javascript" src="/js/mobile.js"></script>
<script type="text/javascript">
var tagid1 = "${tagid}";
var rmemId = "${rmemId}";
if(tagid1==null || tagid1==""){
    tagid1 ="${Parameters.tagid}";
}
if(tagid1=="regtag"){
	if (rmemId != null && rmemId != '') {
		uaredirect("http://wap.kaixinbao.com/mobile/register.html?rmemId="+rmemId);
	} else {
		uaredirect("http://wap.kaixinbao.com/mobile/register.html");
	}
} else {
	uaredirect("http://wap.kaixinbao.com/mobile/login.html");
}

</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>

<body>
    <!-- 顶部 开始 1-->
	<#include "/wwwroot/kxb/block/kxb_header_index_new_login.shtml" >
	<!-- 顶部 结束 -->
	
	
    
    <div class="reg_con">
      	<div class="reg_box_bg">
         <div class="reg-box">
            
        	<div id="reg-wrap">
               <div class="login_title cf"  id="login_tag">
               <ul class="tag_sff">
               <li> <span class="log_t select_tag f_mi" id="logintag" href="###">登录</span> </li>
                  <li>  <span class="log_t res_t f_mi" id="regtag" href="###">注册</span></li>
                 
                   </ul>
                  
               </div>
                <div id="from_box">
               <div class="from_sf_box">
                    <form method="post" action="" id="login_form" onkeydown="if(event.keyCode==13) document.getElementById('loginAjax').click();">
                    <input type="hidden" name="judge" id="judge"  value="${judge}">
                    <input type="hidden" name="orderSn" id="orderSn"  value="${orderSn}">
                    <div class="reg_form">
                        <div class="row fm-item">
                            <div class="collection ">
                            	<label for="loginUsername" class="fm-label-name"></label>
                                <label for="loginUsername" class="fm-label">
                            请输入邮箱或手机号</label>
                                <input id="loginUsername" class="ui-input " type="text" maxlength="100" tabindex="1" value="" name="loginName" autocomplete="off" >
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error1"></div>
                        <div class="row fm-item">
                            <div class="collection">
                            	<label for="password_input" class="fm-label-name fm-label-pass"></label>
                                <label for="password_input" class="fm-label ">
                               请输入密码</label>
                             
                               <input type="password" tabindex="2" maxlength="16" class="ui-input " value="" name="member.password" onpaste="return false" id="password_input" autocomplete="off" >
                            </div>
                        </div>
                         <div class="errror_boxs " id="error2"> </div>
                        		
                       
                        <div class="row fm-item" id="logveriCode" style="display:none">
                            <div class="collection">
                            <label for="yz_btn2" class="ph-label yzm_labels"  >
                                 请输入右侧图片中的内容</label>
                                <input id="yz_btn2" class="ui-input yzm_input " type="text"   name="j_captcha" tabindex="4" autocomplete="off" >
                            </div>
                            <div class="zym_cbox"><img src="" width="120" height="38" alt="验证码"  id="loginWindowCaptchaImage" /></div>
                         <div class="errror_boxs " id="error3"></div>
                        </div>
                        
                        <div class="row reg-help-cnt reg-help-cnt2">
                          
                          <label for="isSaveUsername" class="fm-label-new">
                                <input type="checkbox" checked="checked" class="check1" name="isSaveUsername" value="true" tabindex="5" id="isSaveUsername">
                                记住我的账号</label><a href="../shop/member!passwordRetrieve.action" class="e_password">忘记密码</a>
                          </div>
                        <div class="fm-submit-box">
                             <span class="ui-btn ui-btn-c">
                            <input type="button" value="登 录" tabindex="4" hidefocus="true" class="ui-btn-text login_btns f_mi" id="loginAjax">
                        </span>
                        </div>
                        <div class="clear"></div>
                        <div id="form-validation-info" class="reg-error">
                        </div>

                    </div>
                    </form>
                    </div>
                     

 				<div class="from_sf_box" style="display:none">
	                <form onkeydown="if(event.keyCode==13) document.getElementById('btn-submit').click();" id="regform" method="post" >
	                <input type="hidden" name="rmemId" id="rmemId"  value="${rmemId}">
	                <input type="hidden" name="memPointsChange" id="memPointsChange"  value="">
	                    <div class="reg_form">
	                        <div class="row">
	                            <div class="collection">
	                            	<label for="registerUserName" class="fm-label-name"></label>
	                                <label class="ph-label" for="registerUserName" style="">请使用邮箱或手机号注册</label>
	                                <input type="text" tabindex="1" class="ui-input " id="registerUserName" name="registerUserName" maxlength="40"></div>
	                        </div>
	                        <div id="error4" class="errror_boxs "></div>
	                        <div class="row">
	                            <div class="collection">
	                            	<label class="fm-label-name fm-label-pass" for="password_input"></label>
	                                <label class="ph-label" for="regpassword" style="">密码须在6-16位之间</label>
	                                <input type="password" tabindex="2" onpaste="return false" class="ui-input " maxlength="16" id="regpassword" name="member.password"></div>
	                        </div>
	                        <div id="error5" class="errror_boxs "></div>
	                        <div class="row">
	                            <div class="collection">
	                            	<label class="fm-label-name fm-label-pass" for="password_input"></label>
	                                <label class="ph-label" for="realpassword">请再次输入密码</label>
	                                <input type="password" tabindex="3" onpaste="return false" class="ui-input " maxlength="16" id="realpassword" name="rePassword"></div>
	                        </div>
	                        <div id="error6" class="errror_boxs "></div>
	
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
	                                <input type="checkbox" checked="checked" value="true" id="isAgreeAgreement_" name="isAgreeAgreement">已阅读并同意<a href="#" class="showAgreementWindow" id="showAgreementWindow">《注册协议》</a>
	                                <span id="isAgreeAgreementMessagePosition"></span>
	                            </p>
	                        </div>
	                                <div class="reg-submit">
	                                    <span class="ui-btn ui-btn-a">
	                                        <input type="button" tabindex="5" id="btn-submit" hidefocus="true" class="ui-btn-text login_btns" value="免费注册"></span>
	                                </div>
	                                <div class="clear"></div>
	                               
	                                <div class="reg-error" id="form-validation-info" style="display: none;"></div>
	
	                                <input type="hidden" value="0" name="member.VIPFrom" style="display: none;">
	                                <input type="hidden" id="backURL" value="${windowAddr}" name="backURL">
	                            </div>
	
	                        </form>
                		</div>
                    </div>
                    <div class="lh_login">
                            <p class="lh_ps">使用合作网站账号登陆</p>
                             <ul class="open-auth cf" id="open-auth">
                              <li><a data-mtevent=" " id="sina"  gaevent="" class="min"><i class="sina"></i>新浪微博</a></li>
                              <li><a data-mtevent=" "  id="tencent"  gaevent="" class="min"><i class="qq"></i>QQ账号</a></li>
                              <li class="clear_m"><a data-mtevent=" " id="alipay"   gaevent="" class="min"><i class="alipay"></i>支付宝</a></li>
                              <div id ="wb_connect_btn"></div>
                             </ul> 
                    </div>
                    <div class="from_img">
                    	<ul>
                     		<li>会员专享优惠价格</li>
                      		<li>积累积分兑换礼品</li>
                      		<li>一键自动填写保单，方便快捷</li>
                    	</ul>
                    	<div class="from_jxw"></div>
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
         <input type="button" id="agreementButton"  class="agreementButton agreementWindowClose" value="同意" hidefocus="true" />
     </div>
     <div class="windowBottom"></div>
    </div>
    </div>
    
<script type="text/javascript">
	  	// 点击刷新验证码图片
   function loginWindowCaptchaImageRefresh() {
		document.getElementById("loginWindowCaptchaImage").setAttribute("src", "../captcha.jpg?timestamp" + (new Date()).valueOf()); 
	  }
   function regWindowCaptchaImageRefresh() {
		document.getElementById("regWindowCaptchaImage").setAttribute("src", "../captcha.jpg?timestamp" + (new Date()).valueOf());
	  }
	  //先加载验证码
   loginWindowCaptchaImageRefresh();
   regWindowCaptchaImageRefresh();
</script>    
 
    
<!-- 底部开始 -->
	<#include "/wwwroot/kxb/block/kxb_footer_new_login.shtml">
<!-- 底部结束 -->

<!-- 百分点发送逻辑 end -->
<!-- js加载 -->
<script type="text/javascript" src="/js/min-jquery.js"></script>
<script type="text/javascript" src="/js/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="/js/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="/js/redesign/re_base.js"></script>
<script type="text/javascript" src="/js/redesign/re_footer.js"></script>
<script type="text/javascript" src="/js/login.js"></script>
<script type="text/javascript" src="/js/redesign/re_header.js"></script>
<script type="text/javascript" src="/js/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/js/redesign/xiaoneng_CustomerService.js"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script> 

<script type="text/javascript">
jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
	try {
		NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
	} catch (e) {
	}
});
var tagid = "${tagid}";
if(tagid==null || tagid==""){
    tagid ="${Parameters.tagid}";
}
if(tagid==null || tagid==""){
    tagid="logintag";
}
var ipRepeat = "${ipRepeat}";
jQuery().ready( function() {
var loginBackURL = "${loginBackURL}";
    loginBackURL = encodeURIComponent(loginBackURL);
jQuery("#sina").attr("href","../shop/sina_login!aouth.action?loginBackURL="+loginBackURL);
jQuery("#tencent").attr("href","../shop/tencent_login!aouth.action?loginBackURL="+loginBackURL);
jQuery("#alipay").attr("href","../shop/alipay_login!aouth.action?loginBackURL="+loginBackURL);
jQuery("#"+tagid).addClass('select_tag').parent().siblings().find('span').removeClass('select_tag');
jQuery('#from_box > div:eq('+ jQuery('.tag_sff li').index(jQuery("#"+tagid).parent()) +')').show().siblings().hide();
if(tagid=="regtag"){
    if (ipRepeat == '1') {
        document.getElementById('regveriCode').style.display="";
    }
}
var points = "${points}";
/*
if (points == null || points == '' || points =='0') {
	document.getElementById('getPointMess').style.display="none";
} else {
	document.getElementById('getPointMess').style.display="";
}
*/
    /*注册*/
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
var e1 = $("#error1");
var e2 = $("#error2");
var e3 = $("#error3");
var t1 = $("#judge");
var t2 = $("#orderSn");


// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
if(jQuery.cookie("VIPUsername") != null) {
	k.val(jQuery.cookie("VIPUsername"));
	o.focus();
	n(k);
}

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
    }else {
        e2.removeClass("error-inputs");
        e2.text("");
        return true;
    }
}


function checkUserName() {
    var userName = k.val();
    var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9_-]+\.)+[A-Za-z0-9]{2,4}$/; //验证邮箱的正则
    var regu = /^[1][3-8][0-9]{9}$/;
    if (userName!=null&&userName!=""&&!myReg.test(userName) && !regu.test(userName)) {
        k.removeClass("uimessage");
        e1.addClass("error-inputs");
        e1.text("正确的邮箱或手机号是登录的唯一凭证哟");
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
	var regName = k.val();
    if(regName!=null&&regName!=""){
         if(!checkUserName()) return false;
	     jQuery.ajax({
	        url:"../shop/member!checkRegisterName.action?regName=" + regName,
	        type: "post",
	        dataType: "json",
	        success: function(data) {
	            if (data.status == "true") {
	                e1.removeClass("error-inputs");
	                e1.text("");
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
        e1.addClass("error-inputs");
        e1.show().text("请填写正确账号才行哦!");
        logremarkError();
        return false;
    }
     if(!checkUserName()) return false;
    var w = o.val();
    if ($.trim(w) == "") {
        o.addClass("error-input");
        o.focus();
        e2.addClass("error-inputs");
        e2.show().text("密码是一定要写滴~");
        logremarkError();
        return false;
    }
     if(!checkPassword()) return false;
    var y = yz.val(); //验证码
    if(document.getElementById('logveriCode').style.display==""){
	    if ($.trim(y) == "") {
	        yz.addClass("error-input");
	        yz.focus();
	        e3.addClass("error-inputs");
	        e3.show().text("验证码不能为空哟");
	        return false;
	    }
    }
    var judge = t1.val();
    var orderSn = t2.val();
    jQuery.ajaxLoadImg.show('showid');
	jQuery("#login_form").ajaxSubmit({
			url:'../shop/member!artAjaxLogin.action?judge='+judge+'&orderSn='+orderSn,
			type: "post",  
			crossDomain: true,  
			dataType: "jsonp",
			jsonp: "jsonpCallback",
			success: function(data) {
			jQuery.ajaxLoadImg.hide('showid');
			if (data.status == "success") {
			    if (data.firstLoginAfterUngrade == '1') {
			    	jQuery.cookie("firstLoginAfterUngrade", "1", {expires: 1,path: '${front}/'});
			    }
				
				if(document.getElementById("isSaveUsername").checked){
					jQuery.cookie("VIPUsername", A, {expires: 30});
				} else {
					jQuery.cookie("VIPUsername", null);
				}
				
				if (data.pointsChange == "1") {
					jQuery.cookie("pointsChange", "1",{ expires: 30,path: '${front}/' });
				} else {
					jQuery.cookie("pointsChange", null,{ path: '${front}/' });
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
				
				
				var lbURL="${loginBackURL}";
				if(lbURL!=null&&lbURL!=''&&lbURL.charAt(lbURL.length-1)!='?'){
				   window.location.href = "${loginBackURL}";
				   return;
				}
				//var redirectionUrl = jQuery.cookie("redirectionUrl");
				var redirectionUrl= "${redirectionUrl}";
				if(redirectionUrl != null && redirectionUrl != "") {
					window.location.href = redirectionUrl;
					return;
				}else{
					window.location.href="${frontPage}";
					return;
				}
			
			
			}else if(data.status == "content"){
				if(document.getElementById("isSaveUsername").checked){
					jQuery.cookie("VIPUsername", A, {expires: 30});
				} else {
					jQuery.cookie("VIPUsername", null);
				}
				alert("会员登录成功,首次登录请修改密码！");
				location.href = "../shop/password!edit.action";
			} else if(data.status == "memberCenter"){
				location.href = "../shop/profile!edit.action";
			}else if(data.status == "MailJump"){
				location.href =data.message;
			}else {
				loginWindowCaptchaImageRefresh();
				yz.val("");
				//o.val("");
				// 该处为登陆时 密码3次 错误，刷新验证码
		        logqueryRemarkError();
		        if("密码没有写对哟" == data.message || (document.getElementById('logveriCode').style.display!="" && "验证码没有写对哟" != data.message)){
		          e2.addClass("error-inputs");
		          jQuery("#password_input").val('').focus();
		          e2.show().text(data.message);
		        }else{
		        e3.addClass("error-inputs");
		        e3.show().text(data.message);
			        jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
						try {
							NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
						} catch (e) {
						}
					});
		        }
		        
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
		// 登录点击刷新验证码图片
		$("#loginWindowCaptchaImage").click(function(){
			loginWindowCaptchaImageRefresh();
		}); 
});
//标记登录错误次数
function logremarkError(){
	jQuery.ajax({
				url: '../shop/member!remarkError.action',
				type: "post",
				dataType: "json",
				success: function(data){
					if (data == false) {
		               document.getElementById('logveriCode').style.display="";
		            }
			 	}			
			});
}
//查询注册错误次数
logqueryRemarkError();
function logqueryRemarkError(){
	jQuery.ajax({
					url: '../shop/member!queryLoginRemark.action',
					type: "post",
					dataType: "json",
					success: function(data){
						if (data == false) {
			               document.getElementById('logveriCode').style.display="";
			            }else{
			               document.getElementById('logveriCode').style.display="none";
			            }
				 	}			
				});
}


//注册使用js
jQuery().ready( function() {
	jQuery.agreementWindowShow = function () {
		jQuery("#agreementWindow").jqmShow();
	};
// 协议悬浮窗口
    jQuery("#agreementWindow").jqm({
		modal: true,// 是否开启模态窗口
		overlay: -20,// 屏蔽层透明度
		trigger: ".showAgreementWindow",// 激活元素
		closeClass: "agreementWindowClose",// 关闭按钮
		onShow: function(hash){
			if (jQuery.trim(jQuery("#agreementContent").html()) == "") {
				jQuery.ajax({
					beforeSend: function(data) {
						jQuery("#agreementContent").html('<span class="loadingIcon">&nbsp;</span> 加载中...');
					},
					url: "../shop/member!getAgreement.action",
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
var yz2 = $("#yz_btn3");
var e1 = $("#error4");
var e2 = $("#error5");
var e3 = $("#error6");
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
    } else {
    	e1.removeClass("error-inputs");
	    e1.text("");
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


l.on("click", function(s) {
	 
    q.find(".error-input").removeClass("error-input");
    var A = k.val();
    if ($.trim(A) == "") {
        k.addClass("error-input");
        k.focus();
        e1.addClass("error-inputs");
        e1.show().text("请填写正确账号才行哦");
        regremarkError();
        return false;
    }
    
    if(!checkUserName()) return false;
    var w = o.val();
    if ($.trim(w) == "") {
        o.addClass("error-input");
        o.focus();
        e2.addClass("error-inputs");
        e2.show().text("密码是一定要写滴~");
        regremarkError();
        return false;
    }
    
    if(!checkPassword()) return false;
    var t = f.val();
    if ($.trim(t) == "") {
        f.addClass("error-input");
        f.focus();
        e3.addClass("error-inputs");
        e3.show().text("不要忘记写确认密码哦");
        regremarkError();
        return false;
    }
    
    
    if(!wd()) return false;
    
    
    if(jQuery("#isAgreeAgreement_").attr("checked") != "checked"){
		alert("请勾选注册协议");
		return false;
	}	
    
    var y = yz2.val(); //验证码
     if(document.getElementById('regveriCode').style.display==""){
  		  if ($.trim(y) == "") {
        	f.addClass("error-input");
        	f.focus();
        	e44.addClass("error-inputs");
        	e44.show().text("这里一定要写上验证码才行");
        	return false;
   		  }
    }
    jQuery.ajaxLoadImg.show('showid');
    q.ajaxSubmit({
			url:'../shop/member!newAjaxRegister.action',
			type: "post",    
			dataType: "json",
			success: function(data) {
			jQuery.ajaxLoadImg.hide('showid');
			if (data.status == "success") {
				var orgBackURL=$("#backURL").val();
				var backURL=encodeURIComponent(orgBackURL);
				location.href="../shop/member!registerSuccess.action?registerUserName="+A+"&backURL="+backURL;
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
				jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
					try {
						NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
					} catch (e) {
					}
				});
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
//标记注册错误次数
function regremarkError(){
	jQuery.ajax({
				url: '../shop/member!remarkError.action',
				type: "post",
				dataType: "json",
				success: function(data){
					if (data == false) {
		               document.getElementById('regveriCode').style.display="";
		            }
			 	}			
			});
}
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


jQuery(".tag_sff li span").click(function(){
	jQuery(this).addClass('select_tag').parent().siblings().find('span').removeClass('select_tag');
    jQuery('#from_box > div:eq('+ jQuery('.tag_sff li').index(jQuery(this).parent()) +')').show().siblings().hide();
})

jQuery("#regpassword,#realpassword,#password_input").keyup(function(){
    var s = jQuery(this).val().length;
    if(s>=16){
        jQuery(this).parent().parent().next(".errror_boxs").addClass("error-inputs").html("您的密码长度已经达到上限了哦~");
    }else{
        jQuery(this).parent().parent().next(".errror_boxs").removeClass("error-inputs").html("");
    }
})
</script>

</body>
</html>
