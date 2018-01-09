<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-手机找回密码</title>
<#include "/wwwroot/kxb/block/kxb_reg_header_new_css.shtml">
</head>
<body>
 	<!-- 顶部 开始 1-->
	<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml" >
	<!-- 顶部 结束 -->
    
    <div class="reg_con">
      	<div class="reg_box_bg">
         <div class="reg-box up_ragbox">
        
        	<div id="reg-wrap">
        		<div class="jxw_kxb"></div>
                    <h3>找回密码</h3>
                    <form method="post" action="${base}/shop/member!mobileRetrievePassword.action?mobileNoOrEmail=${mobileNoOrEmail}" id="form2">
                    <div class="reg_form">
                    
                    <div class="row fm-item">
                    	<label class="fm-label-name fm-label-sp"></label>
                            <div class="collection">
                               <label for="yzm_con" class="fm-label" autocomplete="off">
                                请输入您手机收到的验证码</label>
                             
                               <input type="text" tabindex="1" class="ui-input" value="" name="validateCode" onpaste="return false" id="yzm_con" autocomplete="off" >
                            </div>
                        </div>
                         <div class="errror_boxs "  id="error44"></div>
                        <div class="row fm-item">
                            <div class="collection">
                            	<label class="fm-label-name fm-label-pass" ></label>
                               	<label for="new_password" class="fm-label" autocomplete="off">
                              密码须在6-16位之间</label>
                             
                               <input type="password" tabindex="2" class="ui-input" value="" name="member.password" onpaste="return false" id="new_password" autocomplete="off" >
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error2"></div>
                        
                         <div class="row fm-item">
                            <div class="collection">
                            	<label class="fm-label-name fm-label-pass" ></label>
                               <label for="re_password" class="fm-label ">
                                请再次输入新密码</label>
                             
                               <input type="password" tabindex="3" class="ui-input " value="" name="rePassword" onpaste="return false" id="re_password" autocomplete="off">
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error3"></div>

                        
                        <div class="fm-submit-box retrieve_cos">
                         <span class="ui-btn ui-btn-c">
                               <input type="button" value="返回" tabindex="5" hidefocus="true" class="ui-btn-text re_link" id="reSendCode">
                        </span>  
                        <span class="ui-btn ui-btn-c">
                            <input type="submit" value="提交" tabindex="4" hidefocus="true" class="ui-btn-text remes_btn" id="btn-submit">
                        </span>
                        </div>
                        <div class="clear"></div>
                         <div class="errror_boxs "  id="isOften"></div>
                        <div class="clear"></div>
                        
                    </div>
                    </form>
                </div>
         </div>
        </div>
       <input type="hidden" name="oldtime" id="olddate"/>
       <input type="hidden" name="times" id="timess" value="0"/>
    </div>
    
<!-- 底部开始 -->
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!-- 底部结束 -->
<!-- js加载 -->
<#include "/wwwroot/kxb/block/kxb_reg_header_js.shtml">
<script type="text/javascript">
jQuery().ready(function() {

var subFlag=false;

 /**************js验证开始************/
function n(s) {
    if (s.val() != "") {
        s.prev().hide()
    } else {
        s.prev().show()
    }
}

var l = $("#form2");
var o = $("#new_password");
var f = $("#re_password");
var yz = $("#yzm_con");
var e2 = $("#error2");
var e3 = $("#error3");
var e44 = $("#error44");

function checkPassword() {
    if (o.val().length < 6 || o.val().length > 16) {
        e2.addClass("error-inputs");
        e2.text("密码长度不符");
        o.removeClass("uimessage");
        return false;
    }
    else if(o.val().indexOf(" ")!=-1){
        e2.addClass("error-inputs");
        e2.text("密码中不能含有空格");
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
        e3.text("两次密码输入不一致");
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
    yz.removeClass("uimessage");
    if($.trim(yz.val())!= ""){
	        jQuery.ajax({
		        url: "${base}/shop/member!checkSVC.action?svc=" + yz.val(),
		        type: "post",
		        dataType: "json",
		        success: function(data) {
		            if (data == false) {
		                subFlag=false;
		                e44.addClass("error-inputs");
		                e44.text("验证码错误");
		                yz.removeClass("uimessage");
		            } else {
		                subFlag=true;
		                e44.removeClass("error-inputs");
		                e44.text("");
		                yz.addClass("uimessage");
		
		            }
		        }
	    });
        
     }   
       
    
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
    o.removeClass("uimessage");
    t.closest(".collection").removeClass("ph-focus");
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


l.on("submit",
function(s) {
    l.find(".error-input").removeClass("error-input");
    
    var y = yz.val(); //验证码
    if ($.trim(y) == "") {
        yz.addClass("error-input");
        yz.focus();
        e44.addClass("error-inputs");
        e44.show().text("验证码不能为空");
        return false;
    }
    
    
    var w = o.val();
    if ($.trim(w) == "") {
        o.addClass("error-input");
        o.focus();
        e2.addClass("error-inputs");
        e2.show().text("密码不能为空");
        return false;
    }
    if(!checkPassword()) return false;
    var t = f.val();
    if ($.trim(t) == "") {
        f.addClass("error-input");
        f.focus();
        e3.addClass("error-inputs");
        e3.show().text("确认密码不能为空");
        return false;
    }
    if(!wd()) return false;
    jQuery.ajaxLoadImg.show('showid');
      jQuery.ajax({
		        url: "${base}/shop/member!checkSVC.action?svc=" + yz.val(),
		        type: "post",
		        dataType: "json",
		        success: function(data) {
		            if (data == false) {
		                subFlag=false;
		                e44.addClass("error-inputs");
		                e44.text("验证码错误");
		                yz.removeClass("uimessage");
		            } else {
		                subFlag=true;
		                e44.removeClass("error-inputs");
		                e44.text("");
		                yz.addClass("uimessage");
		
		            }
		        }
	    });
	    
	 if(!subFlag) {
	    jQuery.ajaxLoadImg.hide('showid');
	 	return false;
	 }

    return true;
})
 /**************js验证结束************/

   /**重新发送验证码开始***/
    var wait = 60;
    function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        o.value = "重新发送验证码";
        wait = 60;
    } else {
        o.setAttribute("disabled", true);
        o.value = wait;
        wait--;
        setTimeout(function() {
            time(o)
        },
        1000)
    }
}

    jQuery("#reSendCode").click(function() {
      	history.go(-1);
    });
    
       /**重新发送验证码结束***/
});
  
</script>

</body>
</html>
