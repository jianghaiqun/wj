/**  
 * 邮箱和手机绑定.
 * @param bindingId
 * @return
 */
function binding(bindingId) {
	// 绑定邮箱
	if (bindingId == "email") {
		var mail = jQuery("#emailId").val();
		var mailContent =  '<span style="padding:0 4px; color:#7B7B7B;">绑定邮箱:</span>'
			+ '<input onfocus="clearJY()" id="inputEmail" disabled="disabled" value="'
			+ mail
			+ '" style="width:10em; padding:4px; margin-top:5px;" /> <input  style="" type="button" value="发送验证码" onclick="SVCToEmail()" class="member_yanzheng" />'
			+ '<p style="color:#666; padding-left:60px;">绑定邮箱后，您即可使用邮箱进行登录，<br>在忘记密码时也可以通过绑定邮箱找回</p>  '
			+ '<span style="padding:0 4px; color:#7B7B7B;">&nbsp;&nbsp;  验证码:</span>'
			+ '<input id="inputYZM" style="width:4em; padding:4px; margin-top:5px;" /><p style="color:#ff0000; padding-left:50px;">&nbsp;&nbsp;<span id="status1"></span></p>';
		if(mail == ""){
			mailContent=mailContent.replace("disabled=\"disabled\"","");
		}
		
		// 邮箱绑定
		art('#member_email')
				.dialog(
						{
							content : mailContent,
							title : '邮箱绑定',
							drag : false,
							okVal : '绑定',
							ok : function() {
								var myEm = jQuery("#emailId").val();
								var inputEmail = jQuery("#inputEmail").val();
								var mid = jQuery("#memberId").val();
								var sdcode2 = jQuery("#inputYZM").val();
								if (sdcode2 == "") {
									jQuery("#status1").html("请输入验证码！");
									this.shake && this.shake(); // 调用抖动接口
									return false;
								} else {
									jQuery.ajax( {
										url : "member!bandingEmail.action?&mid="
												+ mid + "&sdcodef=" + sdcode2
												+ "&myEm=" + myEm + "&emailo="
												+ inputEmail,
										type : "post",
										dataType : "html",
										success : function(data) {
											if (data == "repeat") {
												jQuery("#inputEmail").attr("disabled","");
												jQuery("#inputYZM").attr("value","");
												jQuery("#inputEmail").focus();
												jQuery("#status1").html("此邮箱已被注册！");
											} 
											if (data == "emailError") {
												jQuery("#status1").html("验证码错误！");
											}
											if (data == "nullCode") {
												jQuery("#status1").html("验证码失效,请重新发送验证码！");
											} 
											if (data == "success") {
												art.dialog( {
													content : '邮箱绑定成功！',
													icon : 'succeed',
													fixed : true,
													lock : true,
													ok : function(){
													jQuery("#queryMember").submit();
												}
												});
											}
											if (data == "success") {
												jQuery("#sysError").html("服务器无响应,请稍后再试！");
											}
										}
									});
								}
								//
								return false;
							},
							cancel : true
						});
	}

	// 修改绑定邮箱
	
	if (bindingId == "emailModify") {
		var mail = jQuery("#emailId").val();
		// 邮箱绑定
		art('#member_email_update')
				.dialog(
						{
							content : '<span style="padding:0 4px; color:#7B7B7B;">修改邮箱:</span>'
									+ '<input onfocus="clearJY()" id="inputEmail" value="'
									+ mail
									+ '" style="width:10em; padding:4px; margin-top:5px;" /> <input  style="" type="button" value="发送验证码" onclick="SVCToEmail()" class="member_yanzheng" />'
									+ '<p style="color:#666; padding-left:60px;">绑定邮箱后，您即可使用邮箱进行登录，<br>在忘记密码时也可以通过绑定邮箱找回</p>  '
									+ '<span style="padding:0 4px; color:#7B7B7B;">&nbsp;&nbsp;  验证码:</span>'
									+ '<input id="inputYZM" style="width:4em; padding:4px; margin-top:5px;" /><p style="color:#ff0000; padding-left:50px;">&nbsp;&nbsp;<span id="status1"></span></p>',
							title : '邮箱绑定',
							drag : false,
							okVal : '绑定',
							ok : function() {

								var myEm = jQuery("#emailId").val();
								var inputEmail = jQuery("#inputEmail").val();
								var mid = jQuery("#memberId").val();
								var sdcode2 = jQuery("#inputYZM").val();
								
								if (sdcode2 == "") {
									jQuery("#status1").html("请输入验证码！");
									this.shake && this.shake(); // 调用抖动接口
									return false;
								} else {
									jQuery.ajax( {
										url : "member!bandingEmail.action?type=1&mid="
												+ mid + "&sdcodef=" + sdcode2
												+ "&myEm=" + myEm + "&emailo="
												+ inputEmail,
										type : "post",
										dataType : "html",
										success : function(data) {
											
											if(data == "noChange"){
												jQuery("#status1").html("不能重复绑定！");
											}
											if (data == "repeat") {
												jQuery("#inputEmail").attr("disabled","");
												jQuery("#inputYZM").attr("value","");
												jQuery("#inputEmail").focus();
												jQuery("#status1").html("此邮箱已被注册！");
											} 
											if (data == "emailError") {
												jQuery("#status1").html("验证码错误！");
											}
											if (data == "nullCode") {
												jQuery("#status1").html("验证码失效,请重新发送验证码！");
											} 
											if (data == "success") {
												art.dialog( {
													content : '邮箱修改绑定成功！',
													icon : 'succeed',
													fixed : true,
													lock : true,
													ok : function(){
													jQuery("#queryMember").submit();
												}
												});
											}
										}
									});
								}
								//
								return false;
							},
							cancel : true
						});
	}
	
	//绑定手机
	if(bindingId == "phone"){
		
		var phone = jQuery("#phone").val();
		
		var phoneContent = '<table >'
			+ '<tr  height="40"><td><div  ><span style="color:#7B7B7B;">图片验证码:</span>'
			+ '</td><td><input id="inputTPYZM" style="width:4em; padding:4px;margin-right:10px; " onfocus="clearStatus2()" /><img  width="78" height="27" id="telValidateCaptchaImage" alt="验证码" src="/wj/captcha.jpg?timestamp1428995098451" onclick="captchaImageRefresh()" > </td></tr>'
			+ '<tr height="40"><td><span style="color:#7B7B7B;">手机号:</span></td>'
			+ '    <td><input onfocus="clearJY()" id="inputPhone" disabled="disabled" style="width:8em; padding:4px; margin-top:5px;" value="'+phone+'" /> <input  " class="member_yanzheng"  type="button" value="发送验证码" onclick="SVCToPhone()" /></td></tr>'
			+ '<tr><td colspan="2"><p style="color:#666; padding-left:50px;">绑定手机后，您即可使用手机号进行登录，<br>在忘记密码时也可以通过绑定手机找回</p></td></tr>'
			+ '<tr  height="40"><td><span style="padding:5 4px; color:#7B7B7B;">短信验证码:</span></td>'
			+ '<td><input id="inputYZM" style="width:4em; padding:4px; margin-top:5px;" /></td></tr>'
		    + '<tr><td  colspan="2" > <p style="color:#ff0000; padding-left:50px;"><span id="status2"></span></p> </td></tr></table>';
		
		//没有录入手机。
		if(phone == ""){
			phoneContent=phoneContent.replace("disabled=\"disabled\"","");
		}
		art('#member_imme')
		.dialog(
				{
					content : phoneContent,
						title : '手机验证',
						drag : false,
						okVal : '绑定',
						ok : function() {
					
						var mid = jQuery("#memberId").val();
						var inputPhone = jQuery("#inputPhone").val();
						var sdcode2 = jQuery("#inputYZM").val();
						
						if (jQuery.trim(inputPhone) == "" || inputPhone.length != 11) {
							jQuery("#status2").html("请输入正确的手机号码！");
							this.shake && this.shake(); // 调用抖动接口
							return false;
							
						}  else if (sdcode2 == "") {
							jQuery("#status2").html("请输入短信验证码！");
							this.shake && this.shake(); // 调用抖动接口
							return false;
							
						} else {
							jQuery.ajax( {
								url : "member!bandingMobile.action?&myMobileNO="+phone+"&mid="+mid+"&sdcodes="+sdcode2+"&mobileNOO="+inputPhone,
									type : "post",
									dataType : "html",
									success : function(data) {
										if (data == "repeat") {
											jQuery("#inputPhone").attr("disabled","");
											jQuery("#inputYZM").attr("value","");
											jQuery("#inputPhone").focus();
											jQuery("#timess").attr("value","0");
											jQuery("#status2").html("此手机已被注册！");
											
										} else if (data == "codeError") {
											jQuery("#status2").html("短信验证码错误！");
											
										}  else if (data == "nullCode") {
											jQuery("#status2").html("短信验证码失效,请重新发送短信验证码！");
											
										} else if (data == "success") {
											art.dialog( {
												content : '手机绑定成功！',
												icon : 'succeed',
												fixed : true,
												lock : true,
												ok : function(){
												jQuery("#queryMember").submit();
											}
											});
											
										} else {
											jQuery("#sysError").html("服务器无响应,请稍后再试！");
										}
									}
							});
					   }
					//
					return false;
				},
				cancel : true
				});
	}
	
	
	//修改绑定手机
	if(bindingId == "phoneModify"){
		var phone = jQuery("#phone").val();
		art('#member_immer_update')
		.dialog(
				{
					content : '<span style="padding:0 4px; color:#7B7B7B;">原手机号:</span>'
						+ '<input  disabled="disabled" value="'+phone+'"  style="width:8em; padding:4px; margin-top:5px;" /> <br>'
						+ '<span style="color:#7B7B7B;">图片验证码:</span>'
						+ '<input id="inputTPYZM" style="width:4em; padding:4px;margin-right:10px; " onfocus="clearStatus2()" /><img  width="78" height="27" id="telValidateCaptchaImage" alt="验证码" src="/wj/captcha.jpg?timestamp1428995098451" onclick="captchaImageRefresh()" > <br>'
						+ '<span style="padding:0 4px; color:#7B7B7B;">新手机号:</span>'
						+ '<input onfocus="clearJY()" id="inputPhone" style="width:8em; padding:4px; margin-top:5px;" /> <input " class="member_yanzheng"  type="button" value="发送验证码" onclick="SVCToPhone()"/>'
						+ '<p style="color:#666; padding-left:60px;">绑定手机后，您即可使用手机号进行登录，<br>在忘记密码时也可以通过绑定手机找回</p>'
						+ '<span style="padding:0 4px; color:#7B7B7B;">&nbsp;&nbsp;短信验证码:</span>'
						+ '<input id="sdcode2" style="width:4em; padding:4px; margin-top:5px;" /><p style="color:#ff0000; padding-left:60px;"><span id="status2"></span></p>',
						title : '手机修改',
						drag : false,
						okVal : '绑定',
						ok : function() {
					
						var mid = jQuery("#memberId").val();
						var inputPhone = jQuery("#inputPhone").val();
						var sdcode2 = jQuery("#sdcode2").val();
						
						if (sdcode2 == "") {
							jQuery("#status2").html("请输入验证码！");
							this.shake && this.shake(); // 调用抖动接口
							return false;
						} else {
							jQuery.ajax( {
								url : "member!bandingMobile.action?type=1&myMobileNO="+phone+"&mid="+mid+"&sdcodes="+sdcode2+"&mobileNOO="+inputPhone,
									type : "post",
									dataType : "html",
									success : function(data) {
								if(data == "fromatError"){
									jQuery("#status2").html("手机格式不正确！");
								}
								if(data == "noChange"){
									jQuery("#status2").html("不能重复绑定！");
								}
								if (data == "repeat") {
									jQuery("#inputPhone").attr("disabled","");
									jQuery("#sdcode2").attr("value","");
									jQuery("#inputPhone").focus();
									jQuery("#timess").attr("value","0");
									jQuery("#status2").html("此手机已被注册！");
								} 
								if (data == "codeError") {
									jQuery("#status2").html("验证码错误！");
								}
								if (data == "nullCode") {
									jQuery("#status2").html("验证码失效,请重新发送验证码！");
								} 
								if (data == "success") {
									art.dialog( {
										content : '手机修改绑定成功！',
										icon : 'succeed',
										fixed : true,
										lock : true,
										ok : function(){
										jQuery("#queryMember").submit();
									}
									});
								}
								if (data == "sysError") {
									jQuery("#sysError").html("服务器无响应,请稍后再试！");
								}
							}
							});
						}
					//
					return false;
				},
				cancel : true
				});
	}

	// 绑定失败抖动效果
	artDialog.fn.shake = function() {
		var style = this.DOM.wrap[0].style, p = [ 4, 8, 4, 0, -4, -8, -4, 0 ], fx = function() {
			style.marginLeft = p.shift() + 'px';
			if (p.length <= 0) {
				style.marginLeft = 0;
				clearInterval(timerId);
			}
			;
		};
		p = p.concat(p.concat(p));
		timerId = setInterval(fx, 13);
		return this;
	};
	
}

// 刷新验证码图片
function captchaImageRefresh(){
	jQuery("#telValidateCaptchaImage").attr("src", "/wj/captcha.jpg?timestamp" + (new Date()).valueOf());
}

function clearJY(){
	
	var inputPhoneValue = jQuery("#inputPhone").val();
	var inputEmailValue = jQuery("#inputEmail").val();
	
	if(inputEmailValue == ""){
		jQuery("#status1").html("");
	}else if(inputPhoneValue == ""){
		jQuery("#status2").html("");
	}
	
	
}

function clearStatus2(){
	jQuery("#status2").html("");
}

// 发送验证码到邮箱
function SVCToEmail() {
	var oldmail = jQuery("#emailId").val();
	var newmail = jQuery("#inputEmail").val();
	var mailBinding = jQuery("#mailBinding").val();
	
	if(newmail==""){
		jQuery("#status1").html("请输入绑定邮箱！");
		return;
	}
	
	if(mailBinding=="Y" && oldmail==newmail){
		jQuery("#status1").html("不能重复绑定！");
		return;
	}
	
	if(!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(newmail))
	{
		jQuery("#status1").html("邮箱格式不正确！");
		jQuery("#newmail").attr("value","");
		jQuery("#newmail").focus();
		return;
	}
	
	var times1 = jQuery("#timest").val();
	if (times1 == 0) {
		jQuery("#inputEmail").attr("disabled","disabled");
		var olddate1 = new Date().getTime();
		jQuery("#olddate3").attr("value", olddate1);
		jQuery("#timest").attr("value", "1");
		jQuery.ajax( {
			url : "member!SVCToEmails.action?ways=" + newmail,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data == true) {
					jQuery("#status1").html("验证码已发送,请您登录邮箱查看<br>&nbsp;&nbsp;尽快完成操作！");
				} else {
					jQuery("#status1").html("验证码已发送失败<br>&nbsp;&nbsp;请重新发送或者联系客服！");
				}

			}
		});
	} else {
		var news = new Date().getTime();
		var old = document.getElementById("olddate3").value;
		if ((news - old) < (1000 * 60)) {
			document.getElementById("status1").innerHTML = "您的验证码已发送,请耐心等待!";
		} else {
			var olddate2 = new Date().getTime();
			jQuery("#olddate3").attr("value", olddate2);
			document.getElementById("status1").innerHTML = "";
			var newmail = jQuery("#emails").val();
			jQuery.ajax( {
				url : "member!SVCToEmails.action?ways="
						+ newmail,
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data == true) {
						jQuery("#status1").html("验证码已发送,请您登录邮箱查看<br>&nbsp;&nbsp;尽快完成操作！");
					} else {
						jQuery("#status1").html("验证码已发送失败<br>&nbsp;&nbsp;请重新发送或者联系客服！");
					}

				}
			});
		}
	}
}

//发送验证码到手机
function SVCToPhone(){
	var iphone = jQuery("#phone").val();
	var mobileno = jQuery("#inputPhone").val();
	var mobileNOBinding = jQuery("#mobileNOBinding").val();
	var inputTPYZM = jQuery("#inputTPYZM").val();
	
	var times1= jQuery("#timess").val();
	
	if(jQuery.trim(inputTPYZM)==""){
		jQuery("#status2").html("请输入图片验证码！");
		return;
	}
	
	if(mobileno==""){
		jQuery("#status2").html("请输入您的手机号码！");
		return;
	}
	
	if(mobileNOBinding == "Y" && mobileno==iphone){
		jQuery("#status2").html("不能重复绑定！");
		return;
	}
	
	if(!/^(13[0-9]{9}|17[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8})$/.test(mobileno))
	{
		jQuery("#status2").html("手机号格式不正确！");
		jQuery("#inputPhone").focus();
		return;
	}
	if(times1==0){
		jQuery("#inputPhone").attr("disabled","disabled");
		var olddate1 =new Date().getTime();
		jQuery("#olddate").attr("value",olddate1);
		jQuery("#timess").attr("value","1");
		jQuery.ajax({
				url: "member!SVCToPhones.action?ways="+mobileno+"&inputTPYZM="+inputTPYZM,
				type: "post",
				dataType: "html",
				success: function(data){
					captchaImageRefresh();
					
					if(data == 'yzmerror'){
						jQuery("#timess").attr("value","0");
						jQuery("#inputPhone").attr("disabled","");
						jQuery("#status2").html("图片验证码错误，请重新输入！");
						
					}
					else if (data == 'registered') {
						jQuery("#timess").attr("value","0");
						jQuery("#inputPhone").attr("disabled","");
						jQuery("#status2").html("此手机已被注册！");
					}
					else if(data == 'true'){
						jQuery("#status2").html("短信验证码已发送,请查看您的手机短信<br>尽快完成操作！");
						
					} else{
						jQuery("#timess").attr("value","0");
						jQuery("#inputPhone").attr("disabled","");
						jQuery("#status2").html("短信验证码已发送失败<br>请重新发送或者联系客服！");
					}
					
			 	}			
			});
		  } else {
			  var news = new Date().getTime();
			  var old = document.getElementById("olddate").value;
			  if((news-old)<(1000*60)){
				  document.getElementById("status2").innerHTML="您的短信验证码已发送,请耐心等待!";
			  } else{
				   var olddate2 =new Date().getTime();
				   jQuery("#olddate").attr("value",olddate2);
				   document.getElementById("status2").innerHTML="";
				  var mobileno = jQuery("#phone").val();
				  jQuery.ajax({
						url: "member!SVCToPhones.action?ways="+mobileno+"&inputTPYZM="+inputTPYZM,
						type: "post",
						dataType: "html",
						success: function(data){
							
							captchaImageRefresh();
							
							if(data == 'yzmerror'){
								jQuery("#timess").attr("value","0");
								jQuery("#inputPhone").attr("disabled","");
								jQuery("#status2").html("图片验证码错误，请重新输入！");
								
							}
							else if (data == 'registered') {
								jQuery("#status2").html("此手机已被注册！");
							}
							else if(data == 'true'){
								jQuery("#status2").html("短信验证码已发送,请查看您的手机短信<br>尽快完成操作！");
								
							} else{
								jQuery("#timess").attr("value","0");
								jQuery("#inputPhone").attr("disabled","");
								jQuery("#status2").html("短信验证码已发送失败<br>请重新发送或者联系客服！");
							}
							
					 	}			
					});
			  }
		  }
} 

jQuery(function() {
    /*理财tip提示*/
    jQuery('.demo-basic').poshytip({
         className: 'tip-darkgray',
         bgImageFrameSize: 11,
         showTimeout: 1,
         offsetX: 5,
         offsetY: 5,
         allowTipHover: false,
         fade: false,
         slide: false 
    });
});


