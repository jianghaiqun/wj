
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邮件-找回密码</title>
<#include "/wwwroot/kxb/block/kxb_reg_header_new_css.shtml">
</head>
<body>
	<!-- head 开始 -->
	<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
    <!-- head 结束 -->
    
    <div class="reg_con">
      	<div class="reg_box_bg">
         <div class="reg-box up_ragbox">
        
        	<div id="reg-wrap">
        		<div class="jxw_kxb"></div>
                    <h3>找回密码</h3>
                    <form method="post" action="member!doRetrieveByEmail.action?member.id=${member.id}" id="form1" >
                    <div class="reg_form">
                        <div class="row fm-item">
                            <div class="collection">
                            	<label class="fm-label-name fm-label-pass" ></label>
                                <label for="new_password_input" class="fm-label ">
                                密码须在6-16位之间</label>
                             
                               <input type="password" tabindex="1" class="ui-input" value="" name="member.password" onpaste="return false" id="new_password_input" autocomplete="off" >
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error8"></div>
                        
                         <div class="row fm-item">
                            <div class="collection">
                            	<label class="fm-label-name fm-label-pass" ></label>
                                <label for="re_password_input" class="fm-label ">
                                请再次输入新密码</label>
                             
                               <input type="password" tabindex="2" class="ui-input " value="" name="rePassword" onpaste="return false" id="re_password_input" autocomplete="off" >
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error9"></div>

                        
                        <div class="fm-submit-box retrieve_cos">
                         <span class="ui-btn ui-btn-c">
                               <input type="button" value="重新发送链接" tabindex="4" hidefocus="true" class="ui-btn-text re_link" onclick="reSendMail(); ">
                        </span>
                             <span class="ui-btn ui-btn-c">
                         
                            <input type="submit" value="提交" tabindex="3" hidefocus="true" class="ui-btn-text remes_btn" id="btn-submit">
                        </span>
                        </div>
                        <div class="clear"></div>
                        
                    </div>
                    </form>
                </div>
                

         </div>
        </div>
       
    </div>
    
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!-- js加载 -->
<#include "/wwwroot/kxb/block/kxb_reg_header_js.shtml">
<script type="text/javascript">
jQuery().ready( function() {
	function n(s) {
	    if (s.val() != "") {
	        s.prev().hide()
	    } else {
	        s.prev().show()
	    }
	}

var o = $("#new_password_input");
var f = $("#re_password_input");
var e8=$("#error8");
var e9=$("#error9");
var ref=$("#form1");
f.on("focus",
function(s) {
    var t = $(this);
    t.closest(".collection").addClass("ph-focus");
});
f.on("blur",
function(s) {
    var t = $(this);
    t.closest(".collection").removeClass("ph-focus");

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

o.on("keyup",
function(s) {
    var t = $(this);
    n(t);
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
    if (o.val() != "") {
        Check();
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



function Check() {
    if (o.val().length < 6 || o.val().length > 16) {
        e8.addClass("error-inputs");
        e8.text("密码长度应在6-16位之间!");
        o.removeClass("uimessage");
        o.val("");
        return false;
    }
     else if(o.val().indexOf(" ")!=-1){
        e8.addClass("error-inputs");
        e8.text("密码中不能含有空格!");
        o.removeClass("uimessage");
        o.val("");
        return false;
    } 
    else {
        e8.removeClass("error-inputs");
        e8.text("");
        o.addClass("uimessage");
        return true;
    }
}


function wd() {
    if (o.val() != f.val()) {
        e9.addClass("error-inputs");
        e9.text("两次输入密码不一致");
        o.removeClass("uimessage");
        f.removeClass("uimessage");
        f.val("");
    } else {
        e9.removeClass("error-inputs");
        e9.text("");
        f.addClass("uimessage");
        o.addClass("uimessage");
    }
}


ref.on("submit",
	function(s) {
		ref.find(".error-input").removeClass("error-input");
		var A = o.val();
			if ($.trim(A) == "") {
				o.addClass("error-input");
				o.focus();
				e8.addClass("error-inputs");
				e8.show().text("密码不能为空！");
				return false;
			}
			
		var B = f.val();
			if ($.trim(B) == "") {
				f.addClass("error-input");
				f.focus();
				e9.addClass("error-inputs");
				e9.show().text("密码确认不能为空！");
				return false;
			}
		})
    
});

function reSendMail() {
   window.location.href("${base}/shop/member!reSendRetrieveMail.action?member.id=${member.id}");
}
	  
	  
    </script>
</body>
</html>
