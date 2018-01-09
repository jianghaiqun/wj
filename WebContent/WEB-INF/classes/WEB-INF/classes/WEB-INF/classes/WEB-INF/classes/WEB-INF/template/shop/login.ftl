<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员登录</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${front}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<script type="text/javascript" >
	function doReflush(){
		show_onclick_('validateCode22_','validateCode2_');
		show_onclick_('registerUserName2_','registerUserName_');
		jQuery("#wgqyzm_").html("");
	}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body onload="doReflush();">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	

	<div class="wraplogin3" >
		<div class="wraploginleft3">
			<div class="logintit3"></div>
			<form id="loginForm_" action="${base}/shop/member!login.action" method="post">
				<div><input type="text" name="loginName" id="loginUsername_" value="请输入电子邮件/手机号/用户名登录" onfocus="if(this.value=='请输入电子邮件/手机号/用户名登录'){this.value='';}" onblur="if(this.value==''){this.value='请输入电子邮件/手机号/用户名登录';}" class="inputlogin1a"    /></div>
				
				<div><input type="text" id="loginPassword__"  value="请输入密码"  onfocus="show_onclick_('loginPassword__','loginPassword_');"   class="inputlogin2a"/>
				     <input type="password" name="member.password" id="loginPassword_"  value="" onblur="show_onblur_('loginPassword__','loginPassword_');" style="display:none;"   class="inputlogin2a" /></div>
				
				<div class="yz"><span><input type="text" class="inputlogina"  id="loginWindowCaptcha_" name="j_captcha" value="输入验证码"   onfocus="if(this.value=='输入验证码'){this.value='';}" onblur="if(this.value==''){this.value='输入验证码';}"  class="inputlogina"  style="width: 106px;" /></span><span><img id="loginWindowCaptchaImage_" src="" alt="换一张" /></span></div>
				<div><input name="" type="checkbox" value="" id="isSaveUsername_" />记住我的电子邮件和手机号</div>
				<div><input type="submit" id="loginWindowSubmit_" class="button2Login" value="" hidefocus="true" /></div>
			</form>
			 <div style="color:red;height:35px;margin-top:5px;"><span id="loginStatus_"></span></div>  
			<div>忘记密码？<span class="orangelink"><a href="${base}/shop/member!passwordRecover.action">重新找回</a></span></div>
		</div>
	
		<div class="wraploginright3">
			<div class="regtit3"></div>
			<form id="registerWindowForm_" name="fm" action="${base}/shop/member!register.action" method="post" onsubmit="return validateForm_();">
				 <div ><input type="text" id="registerUserName2_"   class="inputlogin1a" value="请输入邮箱或手机号"  onfocus="show_onclick_('registerUserName2_','registerUserName_');"  style="" /></div>
				 <div ><input type="text" name="registerUserName" id="registerUserName_"   class="inputlogin1a" value=""  onblur="validateRegisterUserName_();show_onblur_('registerUserName2_','registerUserName_');"  style="display:none;" /></div>
				 <div style=" height:20px;color:red;"><span id="status_"></span></div>
				
				 <div><input type="text" name="validateCode_" id="validateCode22_" class="inputlogina"  value="请输入验证码"  onfocus="show_onclick_('validateCode22_','validateCode2_');"   style="width: 106px;color:#6A6A6A;" /><input type="text" name="validateCode"  id="validateCode2_"  class="inputlogina"  value=""  onblur="show_onblur_('validateCode22_','validateCode2_');validateValidateCode2_();" style="width: 106px;color:#6A6A6A;display:none;" /><input type="button" disabled="true" id="sendVC_" onclick="sendVCode_()" class="buttonSendVcode" value="获取验证码"></div>
				 <div style=" height:20px;color:red;"><span id="wgqyzm_"></span></div>
	 
				 <div><input type="text"     id="registerWindowPassword2_" onfocus="show_onclick_('registerWindowPassword2_','registerWindowPassword_');"  class="inputlogin2b" value="请输入密码"  style="" /> 
				  <input type="password" id="registerWindowPassword_"  onblur="IsEmpty_();show_onblur_('registerWindowPassword2_','registerWindowPassword_');" name="member.password" class="inputlogin2b"   style="display:none;" /></div>
				 <div style=" height:20px;color:red;"><span id="wgqmm_"></span></div>
	
				 <div><input type="text"  id="rePassword2_" value="请输入重复密码" onfocus="show_onclick_('rePassword2_','rePassword_');" class="inputlogin2b"   style="" /> 
				  <input type="password" name="rePassword" id="rePassword_" onblur="validatePassword_();show_onblur_('rePassword2_','rePassword_');" class="inputlogin2b"   style="display:none;" /></div>
				 <div style=" height:20px;color:red;"><span id="wgqmmt_"></span></div>
	
				
				<div><input type="checkbox" id="isAgreeAgreement_" name="isAgreeAgreement" value="true"  messagePosition="#isAgreeAgreementMessagePosition" /><a id="showAgreementWindow" class="showAgreementWindow" href="javascript:void(0);">已阅读并同意《注册协议》</a></div>
	    		<div><input type="submit" id="registerWindowSubmit_" class="button2Register" value="" /></div>
				<input type="hidden" name="member.VIPFrom" value="0"/>
				<input type="hidden" id="timeswgq_" value="0"/>
				<input type="hidden" id="used_" />
				
			</form>
		</div>
    </div>
		
    <div class="clear"></div> 		
</div>   		
  <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">

</body>
</html>
<script type="text/javascript">
jQuery().ready( function() {
	loginWindowCaptchaImageRefresh();
	
	// 提交表单验证,记住登录用户名
	jQuery("#loginForm_").submit( function() {
		if (jQuery("#loginUsername_").val() == "" || jQuery("#loginUsername_").val() == "请输入电子邮件/手机号/用户名登录" ) {
			jQuery("#loginStatus_").html("请输入您的用户名!");
			return false;
		}
		if (jQuery("#loginPassword_").val() == "") {
			jQuery("#loginStatus_").html("请输入您的密码!");
			return false;
		}
		if (jQuery("#loginWindowCaptcha_").val() == "" || jQuery("#loginWindowCaptcha_").val() == "输入验证码") {
			jQuery("#loginStatus_").html("请输入您的验证码!");
			return false;
		}
		
		if(jQuery("#isSaveUsername").attr("checked") == true) {
			jQuery.cookie("VIPUsername", $loginUsername.val(), {expires: 30});
			
		} else {
			jQuery.cookie("VIPUsername", null);
		}
	});

	// 刷新验证码图片
	function loginWindowCaptchaImageRefresh() {
		jQuery("#loginWindowCaptchaImage_").attr("src", "${base}/captcha.jpg?timestamp" + (new Date()).valueOf());
	}
	
	// 点击刷新验证码图片
	jQuery("#loginWindowCaptchaImage_").click( function() {
		loginWindowCaptchaImageRefresh();
	});
});

function copy(){
	jQuery("#wgqmmt_").html("请输入重复密码");
}

function show_onclick_(showValue,hideValue){
	jQuery("#"+showValue).hide();
	jQuery("#"+hideValue).show();
	jQuery("#"+hideValue).focus();
}

function show_onblur_(showValue,hideValue){
	if(jQuery("#"+hideValue).val() == ''){
		jQuery("#"+hideValue).hide();
		jQuery("#"+showValue).focus();
		jQuery("#"+showValue).show();
	}
}
function orignal(){
	jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
}
function IsEmpty_(){
	var registerWindowPassword = jQuery("#registerWindowPassword_").val();
	if(registerWindowPassword == null || registerWindowPassword == "" || "undefined" == typeof(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码不能为空");
		return false;
	}
	if(/^\d+$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
		if(/^[a-zA-Z]+$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
		if(/^[_]+$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
		if(!/^[0-9a-zA-Z_]{6,16}$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
		jQuery("#wgqmm_").html("");
}
function agree(){
	jQuery("#isAgreeAgreement_").attr("checked",true);
}

function validateValidateCode2_(){
	var validateCode2 = jQuery("#validateCode2_").val();
	if(validateCode2 == null || validateCode2 == "" || "undefined" == typeof(validateCode2)){
	    if(!jQuery("#sendVC_").attr("disabled")){
		    jQuery("#wgqyzm_").html("验证码不能为空");
			return false;
	    }
	} else {
		jQuery("#wgqyzm_").html("");
		return false;
	}
}
function  validatePassword_(){
	var registerWindowPassword=jQuery("#registerWindowPassword_").val();
	var rePassword=jQuery("#rePassword_").val();
	if(rePassword == null || rePassword == "" || "undefined" == typeof(rePassword)){
		jQuery("#wgqmmt_").html("重复密码不能为空");
		return false;
	}
	if(rePassword!=registerWindowPassword){
		jQuery("#wgqmmt_").html("两次密码输入不一致");
		jQuery("#registerWindowPassword_").attr("value","");
		jQuery("#rePassword_").attr("value","");
		return false;
	}
	jQuery("#wgqmmt_").html("");
}
function clearUserName(){
	var registerUserName = jQuery("#registerUserName_").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
	jQuery("#status").html("请输入邮箱或手机号");	
	return false;
	}
	var myReg = /^[-_A-Za-z0-9\.]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (myReg.test(registerUserName) || regu.test(registerUserName)) {
		jQuery("#registerUserName_").attr("value","");
		return false;
	}
	
}
function clearLogin(){
	jQuery("#loginWindowCaptcha_").attr("value","");
    }
function clearRegister(){
	jQuery("#validateCode2_").attr("value","");
}
function empty(){
var cdsf = jQuery("#validateCode2_").val();
if(cdsf == null || cdsf == "" || "undefined" == typeof(cdsf)){}
}
function validateForm_(){
	var registerUserName = jQuery("#registerUserName_").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
		jQuery("#status_").html("邮箱号/手机号不能为空");
		return false;
	}
	// 判断是邮箱还是手机号
	var myReg = /^[-_A-Za-z0-9\.]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (!myReg.test(registerUserName) && !regu.test(registerUserName)) {
		jQuery("#status_").html("请输入正确的邮箱号/手机号");
		jQuery("#sendVC_").attr("disabled" ,true);
		jQuery("#registerUserName_").attr("value","");
		return false;
	}
	var registerWindowPassword = jQuery("#registerWindowPassword_").val();
	if(registerWindowPassword == null || registerWindowPassword == "" || "undefined" == typeof(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码不能为空");
		return false;
	}
	if(/^\d+$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
		if(/^[a-zA-Z]+$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
		if(/^[_]+$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
		if(!/^[0-9a-zA-Z_]{6,16}$/.test(registerWindowPassword)){
		jQuery("#wgqmm_").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
		}
	
	var rePassword=jQuery("#rePassword_").val();
	if(rePassword == null || rePassword == "" || "undefined" == typeof(rePassword)){
		jQuery("#wgqmmt_").html("重复密码不能为空");
		return false;
	}
	if(rePassword!=registerWindowPassword){
	jQuery("#wgqmmt_").html("两次密码输入不一致啊");
	jQuery("#rePassword_").attr("value","");
	return false;
	}
	var dddd = jQuery("#used_").val();
	if(dddd==Y){
	return false;}
	jQuery("#registerWindowForm_").Submit();
	
}
//验证邮箱是否已注册
function validateRegisterUserName_(){
	var registerUserName = jQuery("#registerUserName_").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
		jQuery("#status_").html("邮箱号/手机号不能为空");
		jQuery("#sendVC_").attr("disabled" ,true);
		return false;
	}
	
	// 判断是邮箱还是手机号
	var myReg = /^[-_A-Za-z0-9\.]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (!myReg.test(registerUserName) && !regu.test(registerUserName)) {
		jQuery("#status_").html("请输入正确的邮箱号/手机号");
		//jQuery("#registerUserName_").attr("value","");
		jQuery("#sendVC_").attr("disabled" ,true);
		return false;
	}
	
	 jQuery.ajax({
	        url: sinosoft.base+"/shop/member!checkEmail.action?em="+registerUserName,
	        type: "post",
			dataType: "json",
			success: function(data) {
				if (data == false) {
					jQuery("#status_").html("此邮箱/手机号已注册");
					//jQuery("#registerUserName_").attr("value","");
					jQuery("#sendVC_").attr("disabled" ,true);
					jQuery("#used_").attr("value" ,"Y");
				}else{
				    jQuery("#sendVC_").attr("disabled" ,false);
				    jQuery("#used_").attr("value" ,"N");
				    jQuery("#status_").html("");
					
				}
			}
		});
}
var total ;
var se ;  
function OneSecond0_(){  
	jQuery("#sendVC_").attr("value",  total/1000);
	if(total <= 0){
		jQuery("#sendVC_").attr("value",  "发送验证码");
		jQuery("#sendVC_").attr("disabled",false);
		clearInterval(se);
		total = 1 * 60 * 1000;
		
	} else {
		jQuery("#sendVC_").attr("disabled","disabled");
	}
	total -= 1000;  
	
} 
function ThreeSecond0_(){  
	jQuery("#sendVC_").attr("value",  total/1000);
	if(total <= 0){
		jQuery("#sendVC_").attr("value",  "发送验证码");
		jQuery("#sendVC_").attr("disabled",false);
		clearInterval(se);
		total = 1 * 180 * 1000;
		
	} else {
		jQuery("#sendVC_").attr("disabled","disabled");
	}
	total -= 1000;  
} 

//发送验证码	 
function sendVCode_(){
	var count = jQuery("#timeswgq_").val();
	
	if(Number(count)>=5){
		jQuery("#wgqyzm_").html("系统已经给您发送了5次验证码了,为确保是您本人系统不再给您发送了!");
		return false;	
	}
	var registerUserName = jQuery("#registerUserName_").val();
	var radio = -1;
	var myReg1 = /^[_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu1 = /^[1][3-8][0-9]{9}$/;
	if (myReg1.test(registerUserName)) {
		radio = 0;
	} else if(regu1.test(registerUserName)){
		radio = 1;
		jQuery("#timeswgq_").attr("value",Number(count)+Number(1));
	}  
	if(radio == -1){
		jQuery.tip("请输入正确的邮箱号/手机号");
		return false;
	}else if(radio=='0'){
		total=1 * 180 * 1000;
		se = setInterval("ThreeSecond0_()",1000);
	}else{
		total=1 * 60 * 1000;
		se = setInterval("OneSecond0_()",1000);
	}
	
	jQuery.ajax({
				url: sinosoft.base + "/shop/member!sendCode.action?rtype="+radio+"&ways="+registerUserName,
				type: "post",
				dataType: "json",
				success: function(data){
					document.getElementById("status").innerHTML=data;
					jQuery("#sendVC_").attr("value", countDown);
					jQuery("#sendVC_").attr("disable",true);
				}
	});
}
</script>