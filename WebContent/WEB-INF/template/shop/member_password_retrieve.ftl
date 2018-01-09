<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保-找回密码</title>
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
                    <form method="post" action="member!sendPasswordRetrieveMessage.action" id="reg_form">
                    <div class="reg_form">
                        <div class="row fm-item">
                            <label class="fm-label-name" for="loginUsername"></label>
                            <div class="collection ">
                            	<#if (mobileNoOrEmail?size > 0)!>
	                               
								<#else>
								    <label for="retrieveId" class="fm-label">请输入邮箱或手机号</label>					 
								</#if>
                                <input id="retrieveId" class="ui-input " type="text" maxlength="100" tabindex="1"  name="mobileNoOrEmail" autocomplete="off" value="${mobileNoOrEmail}">
                            </div>
                        </div>
                        <div class="errror_boxs "  id="error7"></div>
                        <div class="row fm-item" id="logveriCode">
                            <div class="collection">
                            <label for="yz_btn2" class="ph-label yzm_labels"  >
                                 请输入右侧图片中的内容</label>
                                <input id="yz_btn2" class="ui-input yzm_input " type="text"   name="j_captcha" tabindex="2" autocomplete="off" >
                            </div>
                            <div class="zym_cbox"><img src="" width="120" height="38" alt="验证码"  id="loginWindowCaptchaImage" onclick="loginWindowCaptchaImageRefresh()" /></div>
                         <div class="errror_boxs " id="error3" style="color:#fb5759;">
                         	<#if (errorMessages?size > 0)!>
								<#list errorMessages as list>${list}<br></#list>
							</#if>
                         </div>
                        </div>
                        <div class="fm-submit-box retrieve_co">
                             <span class="ui-btn ui-btn-c">
                            <input type="submit" value="找回" tabindex="3" hidefocus="true" class="ui-btn-text retrieve_btn" id="btn-submit">
                        </span>
                        </div>
                        <div class="clear"></div>
                    </div>
                    </form>
                </div>
         </div>
        </div>
       
    </div>
    
<!-- 底部开始 -->
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!-- 底部结束 -->
<!-- js加载 -->
<#include "/wwwroot/kxb/block/kxb_reg_header_js.shtml">
<script type="text/javascript">
var checkReNameFlag=false;
	  	// 点击刷新验证码图片
function loginWindowCaptchaImageRefresh() {
		document.getElementById("loginWindowCaptchaImage").setAttribute("src", "${base}/captcha.jpg?timestamp" + (new Date()).valueOf()); 
}
	  //先加载验证码
loginWindowCaptchaImageRefresh();
jQuery().ready( function() {
var yz = $("#yz_btn2");
var e3 = $("#error3");
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
function n(s) {
    if (s.val() != "") {
        s.prev().hide()
    } else {
        s.prev().show()
    }
}

function checkReName(){
	var reName = re.val();
	    var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; //验证邮箱的正则
	    var regu = /^[1][3-8][0-9]{9}$/;
	    if (!myReg.test(reName) && !regu.test(reName)) {
	        e7.addClass("error-inputs");
	        e7.text("请输入正确的邮箱号/手机号");
	        return false;
	    }
	return true;
}

    var re = $("#retrieveId");
	var ref = $("#reg_form");
    var e7 = $("#error7");
		   
    re.on("keyup",
	function(s) {
		var t = $(this);
		n(t);
	});
	re.on("focus",
	function(s) {
		var t = $(this);
		t.closest(".fm-item").addClass("ph-focus");
		n(t);
	});

	ref.on("submit",
	function(s) {
		
	    re.blur();
		ref.find(".error-input").removeClass("error-input");
	
		var A = re.val();
			if ($.trim(A) == "") {
				re.addClass("error-input");
				re.focus();
				e7.addClass("error-inputs");
				e7.show().text("账号不能为空");
				return false;
			}
		//校验账号	
		if(!checkReName()) return false; 
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

		})

});
</script>
</body>
</html>
