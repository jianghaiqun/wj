/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/
jQuery().ready( function() {
	
	//登录窗口用artdialog实现 fhz
 	jQuery('#headerShowLoginWindow').click(
		     function(){
		    	   //配置
		    	 if(jQuery('#artLoginFlag').val()==1){
		    		 artLogin();//用弹出窗口登录
		    	 }
		    	 else{
		    		 var localURL=window.location.href;
		    		 var loginBackURL=encodeURIComponent(localURL);
		    		 window.location.href= sinosoft.base + "/shop/member!newLogin.action?loginBackURL="+loginBackURL; 
		    	 }
			 }
		);	
	// 登录窗口显示
//	jQuery.loginWindowShow = function () {
//		jQuery("#loginWindow").jqmShow();
//	}
 	
 

	// 必须会员登录才允许的操作，若未登录则显示登录窗口
	jQuery("a.mustMemberLogin").click( function(event) {
		alert();
		if (jQuery.cookie("loginMemberUsername") == null) {
			jQuery.cookie("redirectionUrl", jQuery(this).attr("href"), {path: "/"});
			jQuery("#loginWindow").jqmShow();
			return false; 
		}
	});
	
	// 协议窗口显示
	jQuery.agreementWindowShow = function () {
		jQuery("#agreementWindow").jqmShow();
	}
	
	jQuery("#agreementButton").click( function() {
    	jQuery("#isAgreeAgreement").attr("checked", true);
    });

	
	// 登录窗口
	//var loginWindowHtml = '<div id="loginWindow" class="loginWindow"><div class="windowTop"><div class="windowTitle">会员登录</div><a class="windowClose loginWindowClose" href="#" hidefocus="true"></a></div><div class="windowMiddle"><form id="loginWindowForm" action="'+sinosoft.base+'/shop/member!ajaxLogin.action" method="post"><table><tr><th>用户名: </th><td><input type="text" name="loginName" id="loginUsername" class="formText {required: true, messages: {required: \'请填写用户名!\'}}" /></td></tr><tr><th>密&nbsp;&nbsp;&nbsp;码: </th><td><input type="password" name="member.password" id="loginPassword" class="formText {required: true, messages: {required: \'请填写密码!\'}}" /></td></tr><tr><th>验证码: </th><td><input type="text" id="loginWindowCaptcha" name="j_captcha" class="formTextS {required: true, messages: {required: \'请填写验证码!\'}}" /><img id="loginWindowCaptchaImage" src="" alt="换一张" /></td></tr><tr><th>&nbsp;</th><td><span class="warnIcon">&nbsp;</span><a href="' + sinosoft.base + '/shop/member!passwordRecover.action">忘记了密码? 点击找回!</a></td></tr><tr><th>&nbsp;</th><td><input type="checkbox" id="isSaveUsername" /><label for="isSaveUsername">&nbsp;记住用户名</label></td></tr><tr><th>&nbsp;</th><td><input type="submit" id="loginWindowSubmit" class="loginSubmit" value="登 录" hidefocus="true" /></td></tr></table></form></div><div class="windowBottom"></div></div>';
//	var loginWindowHtml='' ;
//	loginWindowHtml+='<div id="loginWindow" class="loginWindow"><div class="windowTop"><div class="windowTitle"></div><a class="windowClose loginWindowClose" href="javascript:void(0);" hidefocus="true" onclick="resetForm();"></a></div>';
//	loginWindowHtml += '<div class="wraplogin">';
//	loginWindowHtml += '<div class="wraploginleft">';
//	loginWindowHtml += '<div class="logintit"></div>';
//	loginWindowHtml +='<form id="loginWindowForm" action="'+sinosoft.base+'/shop/member!ajaxLogin.action" method="post">';
//	
//	loginWindowHtml += '<div style="min-height: 55px;">';
//	loginWindowHtml += '<div><input type="text" name="loginName" id="loginUsername" value="请输入电子邮箱/手机号/用户名登陆"  onfocus="if(this.value==\'\'){this.value=\'请输入电子邮箱/手机号/用户名登陆\';}else if(this.value==\'请输入电子邮箱/手机号/用户名登陆\'){this.value=\'\'}" onblur="if(this.value==\'\'){this.value=\'请输入电子邮箱/手机号/用户名登陆\';}" class="formText {required: true, messages: {required: \'请输入电子邮箱/手机号/用户名登陆\'}};"    style="width: 234px;color:#6A6A6A;" /></div>';
//	loginWindowHtml += '</div>';
//	
//	loginWindowHtml += '<div style="min-height: 55px;">';
//	loginWindowHtml += '<div ><input type="text"  value="请输入密码"  class="inputlogin2b"  id="loginPassword2"  onfocus="show_onclick(\'loginPassword2\',\'loginPassword\');" style="width: 234px;color:#6A6A6A" /></div>';
//	loginWindowHtml += '<div><input type="password" name="member.password" id="loginPassword"  class="inputlogin2b"  onblur="show_onblur(\'loginPassword2\',\'loginPassword\');"  style="width: 234px;display:none;" /></div>';
//	loginWindowHtml += '</div>';
//	
//	loginWindowHtml += '<div class="yz"><span><input type="text" class="inputlogina"  id="loginWindowCaptcha" name="j_captcha" value="输入验证码"   onfocus="if(this.value==\'输入验证码\'){this.value=\'\';}" onblur="if(this.value==\'\'){this.value=\'输入验证码\';}"    style="width: 76px;color:#6A6A6A;" /></span><span><img id="loginWindowCaptchaImage" src="" alt="换一张" /></span></div>';
//	loginWindowHtml += '<div style="text-align:left;border:1px solid white;margin-top:5px;margin-bottom:8px;width:200px;"><input name="" type="checkbox" value="" id="isSaveUsername" />记住我的电子邮箱和手机号</div>';
//	loginWindowHtml += '<div><input type="submit" id="loginWindowSubmit" class="button2Login" value="" hidefocus="true" /></div>';
//	loginWindowHtml += '</form>';
//	loginWindowHtml +=' <div style="color:red;height:35px;margin-top:5px;"><span id="loginStatus"></span></div>'; 
//	loginWindowHtml += '<div>忘记密码？<span class="orangelink"><a href="'+sinosoft.base+'/shop/member!passwordRecover.action">重新找回</a></span></div>';
//	loginWindowHtml += '</div>';
//	
//	
//	
//	loginWindowHtml += '<div class="wraploginright">';
//	loginWindowHtml += '<div class="regtit"></div>';
//	loginWindowHtml += '<form id="registerWindowForm" name="fm" action="'+sinosoft.base+'/shop/member!ajaxRegister.action" method="post" onsubmit="return validateForm();">';
//	
//	// 注册手机号或者邮箱
//	loginWindowHtml +=' <div ><input type="text" id="registerUserName2"   class="inputlogin1a" value="请输入邮箱或手机号"  onfocus="show_onclick(\'registerUserName2\',\'registerUserName\');"  style="" /></div>';
//	loginWindowHtml +=' <div ><input type="text" name="registerUserName" id="registerUserName"   class="inputlogin1a" value=""  onblur="validateRegisterUserName();show_onblur(\'registerUserName2\',\'registerUserName\');"  style="display:none;" /></div>';
//	loginWindowHtml +=' <div style=" height:20px;color:red;"><span id="status"></span></div>'; 
//	
//	// 注册的验证码
//	loginWindowHtml +=' <div><input type="text"  id="validateCode22" class="inputlogina"  value="请输入验证码"  onfocus="show_onclick(\'validateCode22\',\'validateCode2\');"                       style="width: 106px;color:#6A6A6A;" />';
//	loginWindowHtml +='<input type="text" name="validateCode"  id="validateCode2"  class="inputlogina"  value=""  onblur="show_onblur(\'validateCode22\',\'validateCode2\');validateValidateCode2();" style="width: 106px;color:#6A6A6A;display:none;" /><input type="button" disabled="true" id="sendVC" onclick="sendVCode()" class="buttonSendVcode" value="获取验证码"></div>';
//	loginWindowHtml +=' <div style=" height:20px;color:red;"><span id="wgqyzm"></span></div>';
//	
//	// 注册的第一次密码
//	loginWindowHtml +=' <div><input type="text"     id="registerWindowPassword2" onfocus="show_onclick(\'registerWindowPassword2\',\'registerWindowPassword\');"  class="inputlogin2b" value="请输入密码"  style="" /> ';
//	loginWindowHtml +='  <input type="password" id="registerWindowPassword"  onblur="IsEmpty();show_onblur(\'registerWindowPassword2\',\'registerWindowPassword\');" name="member.password" class="inputlogin2b"   style="width: 234px;display:none;" /></div>';
//	loginWindowHtml +=' <div style=" height:20px;color:red;"><span id="wgqmm"></span></div>'; 
//	
//	// 注册的第二次密码
//	loginWindowHtml +=' <div><input type="text"  id="rePassword2" value="请输入重复密码" onfocus="show_onclick(\'rePassword2\',\'rePassword\');" class="inputlogin2b"   style="" /> ';
//	loginWindowHtml +='  <input type="password" name="rePassword" id="rePassword" onblur="validatePassword();show_onblur(\'rePassword2\',\'rePassword\');" class="inputlogin2b"   style="width: 234px;display:none;" /></div>';
//	loginWindowHtml +=' <div style=" height:20px;color:red;"><span id="wgqmmt"></span></div>';
//	
//	loginWindowHtml +=' <div><input type="checkbox" id="isAgreeAgreement" name="isAgreeAgreement" value="true"  messagePosition="#isAgreeAgreementMessagePosition" /><a id="showAgreementWindow" class="showAgreementWindow" href="javascript:void(0);">已阅读并同意《注册协议》</a></div>';
//	loginWindowHtml +=' <div style=" height:20px;color:red;"><span id="registerStatus"></span></div>'; 
//	loginWindowHtml +=' <div><input type="submit" id="registerWindowSubmit" class="button2Register" value="" /></div>';
//	loginWindowHtml +='<input type="hidden" name="member.VIPFrom" value="0"/>';
//	loginWindowHtml +='<input type="hidden" name="timeswgq" value="0"/>';
//    loginWindowHtml += ' </form></div>';
//    loginWindowHtml += ' </div>';
//    loginWindowHtml += "<form id='registerGAForm' name='registerGAForm' action='"+sinosoft.base+"/shop/member!registerGA.action' method='post'>";
//    loginWindowHtml += "<input type='hidden' id='windowAddr' name='windowAddr' value='"+sinosoft.front+"/index.shtml'/>";
//    loginWindowHtml += "<input type='hidden' id='registerfrom' name='registerfrom' value='ajaxregister'/>";
//    loginWindowHtml += "<input type='hidden' id='message' name='message' value='message'/>";
//    loginWindowHtml += "</form>";
//    loginWindowHtml += ' </div>';
//	
//	
//	var agreementWindowHtml = '<div id="agreementWindow" class="agreementWindow"><div class="windowTop"><div class="windowTitle">注册协议</div><a class="windowClose agreementWindowClose" href="javascript:void(0);" hidefocus="true"></a></div><div class="windowMiddle"><div id="agreementContent"></div><input type="button" id="agreementButton" onclick="agree();" class="agreementButton agreementWindowClose" value="" hidefocus="true" /></div><div class="windowBottom"></div></div>';
//	jQuery("body").prepend(loginWindowHtml).append(agreementWindowHtml);
//	
//	// 登录悬浮窗口
//    jQuery("#loginWindow").jqm({
//		modal: true,// 是否开启模态窗口
//		overlay: 80,// 屏蔽层透明度
//		trigger: ".showLoginWindow",// 激活元素
//		closeClass: "loginWindowClose",// 关闭按钮
//		onHide: function(hash) {
//			jQuery("#loginWindowForm").resetForm();
//			jQuery.cookie("redirectionUrl", null, {path: "/"});
//    		hash.o.remove();
//    		hash.w.fadeOut();
//    	},
//    	onShow: function(hash){
//    		hash.w.fadeIn();
//    		loginWindowCaptchaImageRefresh();
//	    }
//	}).jqDrag(".windowTop");
	
	
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
					url: sinosoft.base + "/shop/member!getAgreement.action",
					success: function(data){
						jQuery("#agreementContent").html(data);
					}
				});
			}
			hash.w.fadeIn();
	    }
	}).jqDrag(".windowTop");
    
    
    // 表单验证
	jQuery("#loginWindowForm").validate({
		submitHandler: function(form) {
			if(jQuery("#loginUsername").val() == '' || jQuery("#loginUsername").val() == '请输入电子邮箱/手机号/用户名登陆'){
				jQuery("#loginStatus").html("请输入电子邮箱/手机号/用户名登陆");
				return false;
			}
			if(jQuery("#loginPassword").val() == ''){
				jQuery("#loginStatus").html("请输入密码");
				return false;
			}
			if(jQuery("#loginWindowCaptcha").val() == '' || jQuery("#loginWindowCaptcha").val() == '输入验证码'){
				jQuery("#loginStatus").html("请输入验证码");
				return false;
			}
			
			jQuery("#loginWindowForm").ajaxSubmit({
				dataType: "json",
				success: function(data) {
					if (data.status == "success") {
						jQuery.tip(data.status, data.message);
						jQuery.flushHeaderInfo();
						jQuery.flushCartItemList();

						if($isSaveUsername.attr("checked") == true) {
							jQuery.cookie("VIPUsername", $loginUsername.val(), {expires: 30});
							
						} else {
							jQuery.cookie("VIPUsername", null);
						}
						var redirectionUrl = jQuery.cookie("redirectionUrl");
						jQuery("#loginWindow").jqmHide();
						if(redirectionUrl != null && redirectionUrl != "") {
							location.href = redirectionUrl;
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
					}if(data.status == "content"){
						jQuery.tip(data.status, data.message);
						jQuery.flushHeaderInfo();
						jQuery.flushCartItemList();

						if($isSaveUsername.attr("checked") == true) {
							jQuery.cookie("VIPUsername", $loginUsername.val(), {expires: 30});
						} else {
							jQuery.cookie("VIPUsername", null);
						}
						jQuery("#loginWindow").jqmHide();
						alert("会员登录成功,首次登录请修改密码！");
						location.href = sinosoft.base+"/shop/password!edit.action";
					} else {
						loginWindowCaptchaImageRefresh();
						jQuery("#loginWindowCaptcha").val("");
						jQuery("#loginStatus").html(data.message);
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
		}
	});
	

	
	
	// 刷新验证码图片
	function loginWindowCaptchaImageRefresh() {
		jQuery("#loginWindowCaptchaImage").attr("src", sinosoft.base + "/captcha.jpg?timestamp" + (new Date()).valueOf());
	}
	
	// 点击刷新验证码图片
	jQuery("#loginWindowCaptchaImage").click( function() {
		loginWindowCaptchaImageRefresh();
	});
	
	// 刷新验证码图片
	function loginCaptchaImageRefresh() {
		jQuery("#loginCaptchaImage").attr("src", sinosoft.base + "/captcha.jpg?timestamp" + (new Date()).valueOf());
	}
	
	// 点击刷新验证码图片
	jQuery("#loginCaptchaImage").click( function() {
		loginCaptchaImageRefresh();
	});
	
	var $loginUsername = jQuery("#loginUsername");
	var $loginPassword = jQuery("#loginPassword");
	var $loginCaptcha = jQuery("#loginWindowCaptcha");
	var $captchaImage = jQuery("#loginWindowCaptchaImage");
	var $isSaveUsername = jQuery("#isSaveUsername");

	// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
	if(jQuery.cookie("VIPUsername") != null) {
		$isSaveUsername.attr("checked", true);
		$loginUsername.val(jQuery.cookie("VIPUsername"));
		$loginPassword.focus();
	} else {
		$isSaveUsername.attr("checked", false);
		$loginUsername.focus();
	}
	
});

//弹出窗回调
function artSuccessBack(data){
	jQuery.tip(data.status, data.message);
	jQuery.flushHeaderInfo();
	jQuery.flushCartItemList();
	var redirectionUrl = jQuery.cookie("redirectionUrl");
	if(redirectionUrl != null && redirectionUrl != "") {
		location.href = redirectionUrl;
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
	
}

//弹出窗回调
function artContentBack(data){
	jQuery.tip(data.status, data.message);
	jQuery.flushHeaderInfo();
	jQuery.flushCartItemList();
	alert("会员登录成功,首次登录请修改密码！");
	location.href = sinosoft.base+"/shop/password!edit.action";
	
}


function orignal(){
	jQuery("#wgqmm").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
}

function validateValidateCode2(){
	var validateCode2 = jQuery("#validateCode2").val();
	if(validateCode2 == null || validateCode2 == "" || "undefined" == typeof(validateCode2)){
		jQuery("#wgqyzm").html("验证码不能为空");
		return false;
	} else {
		jQuery("#wgqyzm").html("");
		return false;
	}
}

function IsEmpty(){
	var registerWindowPassword = jQuery("#registerWindowPassword").val();
	if(registerWindowPassword == null || registerWindowPassword == "" || "undefined" == typeof(registerWindowPassword)){
		jQuery("#wgqmm").html("密码不能为空");
		return false;
	}
	if(/^\d+$/.test(registerWindowPassword)){
		jQuery("#registerWindowPassword").attr("value","");
		jQuery("#wgqmm").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
	}
	if(/^[a-zA-Z]+$/.test(registerWindowPassword)){
			jQuery("#registerWindowPassword").attr("value","");
			jQuery("#wgqmm").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
			return false;
	}
	if(/^[_]+$/.test(registerWindowPassword)){
		jQuery("#registerWindowPassword").attr("value","");
		jQuery("#wgqmm").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
	}
	if(!/^[a-zA-Z0-9_]{6,16}$/.test(registerWindowPassword)){
		jQuery("#registerWindowPassword").attr("value","");
		jQuery("#wgqmm").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
		return false;
	}
	jQuery("#wgqmm").html("");
	return true;
}
function agree(){
	jQuery("#isAgreeAgreement").attr("checked",true);
}

function  validatePassword(){
	var registerWindowPassword=jQuery("#registerWindowPassword").val();
	var rePassword=jQuery("#rePassword").val();
	if(rePassword == null || rePassword == "" || "undefined" == typeof(rePassword)){
		jQuery("#wgqmmt").html("重复密码不能为空");
		return false;
	}
	if(rePassword!=registerWindowPassword){
		jQuery("#wgqmmt").html("两次密码输入不一致");
		jQuery("#registerWindowPassword").attr("value","");
		jQuery("#rePassword").attr("value","");
		return false;
	}
	jQuery("#wgqmmt").html("");
	return true;
}
function clearUserName(){
	var registerUserName = jQuery("#registerUserName").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
	jQuery("#status").html("请输入邮箱或手机号");	
	return false;
	}
	if(registerUserName=="请输入邮箱或手机号"){
		jQuery("#registerUserName").attr("value","");	
	}
	var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (!myReg.test(registerUserName) && !regu.test(registerUserName)) {
		jQuery("#registerUserName").attr("value","");
		return false;
	}
	
}

function resetForm(){
	jQuery("#loginWindowForm").resetForm();
	jQuery("#registerWindowForm").resetForm();
}

function showwsmm(){
	var loginPassword = jQuery("#loginPassword").val();
	if(loginPassword == null || loginPassword == "" || "undefined" == typeof(loginPassword)){
	jQuery("#showmw").show();
	jQuery("#loginPassword").hide();}
}
function showawmm(){
	jQuery("#showmw").hide();
	jQuery("#loginPassword").show();
	jQuery("#loginPassword").focus();
}
function clearLogin(){
	jQuery("#loginWindowCaptcha").attr("value","");
    }
function clearRegister(){
	jQuery("#validateCode2").attr("value","");
}
function empty(){
var cdsf = jQuery("#validateCode2").val();
if(cdsf == null || cdsf == "" || "undefined" == typeof(cdsf)){}
}
function validateForm(){
	var registerUserName = jQuery("#registerUserName").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
		jQuery("#status").html("邮箱号/手机号不能为空");
		return false;
	}
	// 判断是邮箱还是手机号
	var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (!myReg.test(registerUserName) && !regu.test(registerUserName)) {
		jQuery("#status").html("请输入正确的邮箱号/手机号");
		return false;
	}
	if(!IsEmpty()){
		return false;
	}
	if(!validatePassword()){
		return false;
	}
	jQuery("#registerWindowForm").ajaxSubmit({
			dataType: "json",
			success: function(data) {
				if (data.status == "success") {
					//jQuery.tip(data.status, data.message);
					//jQuery.flushHeaderInfo();
					//jQuery("#loginWindow").jqmHide();
					/* zhangjinquan 11180 需求-注册增加统计页面-浮动层注册 2012-10-25 */
					document.getElementById("message").value = data.message;
					document.getElementById("windowAddr").value = location.href;
					document.forms["registerGAForm"].submit();
				} else {
					jQuery("#registerStatus").html(data.message);
					//jQuery.flushHeaderInfo();
					//jQuery("#loginWindow").jqmHide();
				}
				jQuery("#registerWindowSubmit").attr("disabled", false);
			}
		});
	return false;
}
//验证邮箱是否已注册
function validateRegisterUserName(){
	var registerUserName = jQuery("#registerUserName").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
		jQuery("#status").html("邮箱号/手机号不能为空");
		jQuery("#sendVC").attr("disabled" ,true);
		return false;
	}
	
	// 判断是邮箱还是手机号
	var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (!myReg.test(registerUserName) && !regu.test(registerUserName)) {
		jQuery("#status").html("请输入正确的邮箱号/手机号");
		//jQuery("#registerUserName").attr("value","");
		jQuery("#sendVC").attr("disabled" ,true);
		return false;
	}
	
	 jQuery.ajax({
	        url: sinosoft.base+"/shop/member!checkEmail.action?em="+registerUserName,
	        type: "post",
			dataType: "json",
			success: function(data) {
				if (data == false) {
					jQuery("#status").html("此邮箱/手机号已注册");
					//jQuery("#registerUserName").attr("value","");
					jQuery("#sendVC").attr("disabled" ,true);
				}else{
				    jQuery("#sendVC").attr("disabled" ,false);
				    jQuery("#status").html("");
					
				}
			}
		});
}
var total ;
var se ;  
function OneSecond_(){  
	jQuery("#sendVC").attr("value",  total/1000);
	if(total <= 0){
		jQuery("#sendVC").attr("value",  "发送验证码");
		jQuery("#sendVC").attr("disabled",false);
		clearInterval(se);
		total = 1 * 60 * 1000;
		
	} else {
		jQuery("#sendVC").attr("disabled","disabled");
	}
	total -= 1000;  
	
}
function show_onclick(showValue,hideValue){
	jQuery("#"+showValue).hide();
	jQuery("#"+hideValue).show();
	jQuery("#"+hideValue).focus();
}

function show_onblur(showValue,hideValue){
	if(jQuery("#"+hideValue).val() == ''){
		jQuery("#"+hideValue).hide();
		jQuery("#"+showValue).focus();
		jQuery("#"+showValue).show();
	}
}

//弹出登陆框 华丽
function artLogin(){
	 art.dialog.data('base', sinosoft.base);//将服务
	 art.dialog.data('front', sinosoft.front);
	 art.dialog.open(sinosoft.front+'/template/art_login.html',	 {id: 'loginArtWindow',title: '登录窗口',width:363,fixed:true}, false);
}

function showwgq111(){
	jQuery("#word").hide();
	jQuery("#rePassword").show();
	jQuery("#rePassword").focus();
}
function ThreeSecond_(){  
	jQuery("#sendVC").attr("value",  total/1000);
	if(total <= 0){
		jQuery("#sendVC").attr("value",  "发送验证码");
		jQuery("#sendVC").attr("disabled",false);
		clearInterval(se);
		total = 1 * 180 * 1000;
		
	} else {
		jQuery("#sendVC").attr("disabled","disabled");
	}
	total -= 1000;  
} 

//发送验证码	 
function sendVCode(){
	var count = jQuery("#timeswgq").val();
	if(Number(count)>=5){
		jQuery("#wgqyzm").html("系统已经给您发送了5次验证码了,为确保是您本人系统不再给您发送了!");
		return false;	
	}
	var registerUserName = jQuery("#registerUserName").val();
	var radio = -1;
	var myReg1 = /^[_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu1 = /^[1][3-8][0-9]{9}$/;
	if (myReg1.test(registerUserName)) {
		radio = 0;
	} else if(regu1.test(registerUserName)){
		radio = 1;
		jQuery("#timeswgq").attr("value",Number(count)+Number(1));
	}  
	if(radio == -1){
		jQuery.tip("请输入正确的邮箱号/手机号");
		return false;
	}else if(radio=='0'){
		total=1 * 180 * 1000;
		se = setInterval("ThreeSecond_()",1000);
	}else{
		total=1 * 60 * 1000;
		se = setInterval("OneSecond_()",1000);
	}
	
	jQuery.ajax({
				url: sinosoft.base + "/shop/member!sendCode.action?rtype="+radio+"&ways="+registerUserName,
				type: "post",
				dataType: "html",
				success: function(data){
					document.getElementById("wgqyzm").innerHTML=data;
					jQuery("#sendVC").attr("disable",true);
				}
	});
}

 