<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="积分商城登录" />
	<meta name="description" content="积分商城登录" />
	<title>积分商城登录</title>

	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_integral.css" />
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/min-jquery.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/template/common/js/base.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/header1.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/login.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/artDialog.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/iframeTools.js"></script>
	
	<script type="text/javascript">
  		/*点击刷新验证码图片*/
	    function loginWindowCaptchaImageRefresh() {
			document.getElementById("loginWindowCaptchaImage").setAttribute("src", sinosoft.base+"/captcha.jpg?timestamp" + (new Date()).valueOf()); 
		  }
  		/*评论*/
		function queryComment(){
			window.parent.location.href="<%=Config.getValue("ServerContext")%>/shop/member_comment!queryComment.action";
		}
		/*找回密码*/
		function findpassword(){
			window.parent.location.href="<%=Config.getValue("ServerContext")%>/shop/member!passwordRetrieve.action";
		}
		/*tip冷冻积分提示*/
	/* 	jQuery("#int_jf_tips").hover(
		  function () {
		    jQuery(".int_tip_des").show();
		  },
		  function () {
		   jQuery(".int_tip_des").hide();
		  }
		); */
		  function tips() {
			    jQuery(".int_tip_des").show();
			  }
		  function tips_hid(){
			  jQuery(".int_tip_des").hide();
		  }
		function logqueryRemarkError(){
		    jQuery.ajax({
		                    url: '/wj/shop/member!queryRemarkError.action',
		                    type: "post",
		                    dataType: "json",
		                    success: function(data){
		                        if (data == false) {
		                           //document.getElementById('logveriCode').style.display="";
		                           jQuery("#logveriCode").show();
		                        }else{
		                           //document.getElementById('logveriCode').style.display="none";
		                           jQuery("#logveriCode").hide();
		                        }
		                    }           
		                });
		};
		/*登录*/
		var tagid = "logintag";
		if(tagid==null || tagid==""){
		    tagid ="logintag";
		}
		if(tagid==null || tagid==""){
		    tagid="logintag";
		}
		jQuery().ready( function() {
			
		/*查询注册错误次数*/
		logqueryRemarkError();
		var loginBackURL = "http://www.kaixinbao.com/";
		    loginBackURL = encodeURIComponent(loginBackURL);
		jQuery("#sina").attr("href","/wj/shop/sina_login!aouth.action?loginBackURL="+loginBackURL);
		jQuery("#tencent").attr("href","/wj/shop/tencent_login!aouth.action?loginBackURL="+loginBackURL);
		jQuery("#alipay").attr("href","/wj/shop/alipay_login!aouth.action?loginBackURL="+loginBackURL);
		jQuery("#"+tagid).addClass('select_tag').append("<em class='xsjiao'></em>").parent().siblings().find('span').removeClass('select_tag').empty("xsjiao");
		jQuery('#from_box > div:eq('+ jQuery('.tag_sff li').index(jQuery("#"+tagid).parent()) +')').show().siblings().hide();
		    /*注册*/
		function n(s) {
		    if (s.val() != "") {
		        s.prev().hide()
		    } else {
		        s.prev().show()
		    }
		}
		function checkPassword() {
		    if (o.val().length < 6 || o.val().length > 16) {
		        e2.addClass("error-inputs");
		        e2.text("您的密码太短了！");
		        o.removeClass("uimessage");
		        return false;
		    } 
		     else if(o.val().indexOf(" ")!=-1){
		        e2.addClass("error-inputs");
		        e2.text("密码是不能填写空格的！");
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
		    var myReg = /^[-_A-Za-z0-9\.]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,4}$/; //验证邮箱的正则
		    var regu = /^[1][3-8][0-9]{9}$/;
		    if (userName!=null&&userName!=""&&!myReg.test(userName) && !regu.test(userName)) {
		        k.removeClass("uimessage");
		        e1.addClass("error-inputs");
		        e1.text("请填写正确账号才行哦！");
		        return false;
		    }

		    return true;
		}
		var k = $("#loginUsername");
		var o = $("#password_input");
		var l = $("#loginAjax");
		var yz = $("#yz_btn2");
		var e1 = $("#error1");
		var e2 = $("#error2");
		var e3 = $("#error3");
		k.on("focus",function(s) {
		    var t = $(this);
		    t.closest(".collection").addClass("ph-focus");
		    n(t);
		});
		k.on("blur",function(s) {
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
		                if (data == true) {
		                    e1.removeClass("error-inputs");
		                    e1.text("");
		                } else {
		                    e1.addClass("error-inputs");
		                    e1.text("请填写正确账号才行哦！");
		                    k.removeClass("uimessage");
		                }
		            }
		        });
		    }
		});
		k.on("keyup",function(s) {
		    var t = $(this);
		    n(t)
		});
		k.on("change",function(s) {
		    var t = $(this);
		    n(t);

		});
		yz.on("focus",function(s) {
		    var t = $(this);
		    t.closest(".fm-item").addClass("ph-focus");
		    n(t)
		});
		yz.on("blur",function(s) {
		    var t = $(this);
		    t.closest(".fm-item").removeClass("ph-focus");
		    n(t);
		});
		yz.on("keyup",function(s) {
		    var t = $(this);
		    n(t)
		});

		l.find("input").each(function(t, s) {
		    var u = $(this);
		    n(u)
		});

		o.on("focus",function(s) {
		    var t = $(this);
		    t.closest(".collection").addClass("ph-focus");
		    n(t);

		});

		o.on("blur",function(s) {
		    var t = $(this);
		    t.closest(".collection").removeClass("ph-focus");
		    o.removeClass("uimessage");
		    if (o.val() != "") {
		       checkPassword();
		    }

		});

		o.on("change",function(s) {
		    var t = $(this);
		    n(t);
		});

		o.on("keyup",function(s) {
		    var t = $(this);
		    n(t);
		});


		//提交
		l.on("click",function(s) {
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
		    jQuery("#login_form").ajaxSubmit({
		            url:'/wj/shop/member!artAjaxLogin.action?',
		            type: "post", 
		            crossDomain: true,  
		            dataType: "jsonp",
					jsonp: "jsonpCallback",
		            success: function(data) {
		            if (data.status == "success") {
		            	 jQuery("#jifen_login_form",window.parent.document).submit();
// 		            	 jQuery("#headerLoginMemberUsername",window.parent.document).text(jQuery.cookie("loginMemberUsername"));
// 		            	 jQuery("#YesLogin",window.parent.document).show();
// 		            	 jQuery("#NotLogin",window.parent.document).hide();
		            	 var $headerShowLoginWindow = jQuery("#headerShowLoginWindow",window.parent.document);
		            	 var $headerShowRegisterWindow = jQuery("#headerShowRegisterWindow",window.parent.document);
		            	 var $headerLoginMemberUsername = jQuery("[id=headerLoginMemberUsername]",window.parent.document);
		            	 var $headerMemberCenter = jQuery("#headerMemberCenter",window.parent.document);
		            	 var $headerRegister = jQuery("#headerRegister",window.parent.document);
		            	 var $headerLogout = jQuery("#headerLogout",window.parent.document);
		            	 var $YesLogin = jQuery("#YesLogin",window.parent.document);
		            	 var $NoLogin = jQuery("#NotLogin",window.parent.document);	
		            	 
		            	 loginflag = true;
							if (jQuery.cookie("pointsChange") == '1') {
								jQuery("#pointsChange",window.parent.document).show();
							}
							if (data.MemberPoints != null && data.MemberPoints != '') {
								jQuery("#memberPointsCount",window.parent.document).html(data.MemberPoints);
							}
							if(jQuery.cookie("loginMemberUsername") != null) {
								var loginMemberUserName = jQuery.cookie("loginMemberUsername");
								if (loginMemberUserName.indexOf("@") > 0) {
									loginMemberUserName = loginMemberUserName.substring(0, loginMemberUserName.indexOf("@"));
								}
								$headerLoginMemberUsername.text(loginMemberUserName);
								//$headerLoginMemberUsername.text(getNameSplit(jQuery.cookie("loginMemberUsername")));
								$YesLogin.show();
								$NoLogin.hide();
							} else if(data.MemberUsername != null ){
								$headerLoginMemberUsername.text(getNameSplit(data.MemberUsername));
								$YesLogin.show();
								$NoLogin.hide();
							}
							if (data.grade == 'K0') {
								jQuery("[id=headerLoginMemGradeIcon]",window.parent.document).html('<span style="display: none;" class="vip_tipsf" id="headerGradeSpan"><span class="vip_sfs"><a href="'+sinosoft.front+'/about/xszn/hysm/index.shtml">成为会员</a></span>购买折扣更多</span>');
							}
							
							if (data.gradeClass != null && data.gradeClass != '') {
								jQuery("[id=headerLoginMemGradeIcon]",window.parent.document).addClass(data.gradeClass);
							}
							jQuery('#prepay',window.parent.document).text("待支付("+data.PrePay+")");
							jQuery('#preeffecitive',window.parent.document).text("待生效("+data.Payed+")");
							jQuery('#effecitive',window.parent.document).text("已生效("+data.Effective+")");
							jQuery('#shopcartinfo',window.parent.document).html(data.carlist);
							jQuery('#shoptotal',window.parent.document).text(data.shoptotal);
							jQuery('#shopCartNo',window.parent.document).text(data.shopcount);
							jQuery('#gradeinfo',window.parent.document).text(data.gradeinfo);
							jQuery('#memberphoto',window.parent.document).attr("src",data.headPicPath);
							jQuery('#CouponCount',window.parent.document).text(data.CouponCount);	 
		            }else if(data.status == "content"){
		            	
		                if(document.getElementById("isSaveUsername").checked){
		                    jQuery.cookie("VIPUsername", A, {expires: 30});
		                } else {
		                    jQuery.cookie("VIPUsername", null);
		                }
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
		                if("密码没有写对哟" == data.message){
		                  e2.addClass("error-inputs");
		                  e2.show().text(data.message);
		                }else{
		                e3.addClass("error-inputs");
		                e3.show().text(data.message);
		                }
		                logqueryRemarkError();
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
		                url: '/wj/shop/member!remarkError.action',
		                type: "post",
		                dataType: "json",
		                success: function(data){
		                    if (data == false) {
		                       //document.getElementById('logveriCode').style.display="";
		                       jQuery("#logveriCode").hide();
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
		        overlay: 0,// 屏蔽层透明度
		        trigger: ".showAgreementWindow",// 激活元素
		        closeClass: "agreementWindowClose",// 关闭按钮
		        onShow: function(hash){
		            if (jQuery.trim(jQuery("#agreementContent").html()) == "") {
		                jQuery.ajax({
		                    beforeSend: function(data) {
		                        jQuery("#agreementContent").html('<span class="loadingIcon">&nbsp;</span> 加载中...');
		                    },
		                    url: "/wj/shop/member!getAgreement.action",
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
		    
		  

		        // 点击刷新验证码图片
		        $("#regWindowCaptchaImage").click(function(){
		            regWindowCaptchaImageRefresh();
		        });  
		});
	     function zhuce(){
	    		 artNewLogin();//用弹出窗口登录
	    	 }
	</script>
</head>
<body  style="overflow:hidden;">
	<div class="int_right fright">
			<s:if test="loginmap.isEmpty()">
				<div class="int_logins">
					<p class="int_login_p">登录账号后，可查看可用积分</p>
					<div id="reg-wrap">
						<form onkeydown="if(event.keyCode==13) document.getElementById('loginAjax').click();" id="login_form" action="" method="post">
							<div class="reg_form">
								<div class="row fm-item cf">
									<label class="sd-form-label"> 账号</label>
									<div class="collection ">
										<label class="fm-label" for="loginUsername"> 请输入邮箱或手机号</label>
										<input type="text" autocomplete="off" name="loginName" value=""
											tabindex="1" maxlength="100" class="ui-input "
											id="loginUsername">
									</div>
								</div>
								<div id="error1" class="errror_boxs "></div>
								<div class="row fm-item cf">
									<label class="sd-form-label"> 密码</label>
									<div class="collection">
										<label class="fm-label " for="password_input"> 请输入密码</label> <input
											type="password" autocomplete="off" id="password_input"
											onpaste="return false" name="member.password" value=""
											class="ui-input" tabindex="2">
									</div>
								</div>
								<div id="error2" class="errror_boxs "></div>
								<div id="logveriCode" class="row fm-item cf">
									<label class="sd-form-label"> 验证码</label>
									<div class="collection collection2">
										<label class="ph-label" for="yz_btn2"> 验证码</label> <input
											type="text" autocomplete="off" tabindex="4" name="j_captcha"
											class="ui-input yzm_input " id="yz_btn2">
									</div>
									<div class="zym_cbox">
										<img width="78" height="27" id="loginWindowCaptchaImage"
											alt="验证码" src="/wj/captcha.jpg?timestamp1413946663966">
									</div>
									<div class="clear"></div>
									<div id="error3" class="errror_boxs "></div>
								</div>
								
								<div class="fm-submit-box cf">
									<span class="ui-btn ui-btn-c">
									<input type="button" id="loginAjax" class="ui-btn-text login_btns" hidefocus="true" tabindex="4" value="">
									</span> &nbsp;&nbsp;
									<a class="e_password" href="###" onclick="findpassword()">忘记密码</a>
								</div>
	
								<div class="clear"></div>
								<p class="int_to_reg">
									还不是会员，<a href="###"  onclick="zhuce()">点击注册</a>
								</p>
								<div class="reg-error" id="form-validation-info"></div>
							</div>
						</form>
					</div>
				</div>
			</s:if>
			<s:else>
				<div class="int_loginend">
					<p class="int_goinedn_p">您的账户拥有积分：</p>
					<div class="int_headr cf">
						<img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="loginmap.headpicpath"/>" width="65px"
							height="65px" class="int_headerimg" alt=""> <span
							class="int_user_jf"><s:property value="loginmap.currentvalidatepoint"/>个</span>
					</div>
					<p class="int_pp">
						在线投保可抵值<em><s:property value="loginmap.pointprice"/></em>元<br> <a href="###" onclick="queryComment()"><s:property value="loginmap.commentnum"/></a>待评价商品，可得<em><s:property value="loginmap.commentpoint"/></em>个积分
					</p>
					<div class="int_djjf">
						冻结积分： <s:property value="loginmap.point"/>个<em class="int_jf_tip" onmouseover="tips();"  onmouseout="tips_hid();" id="int_jf_tips">&nbsp;&nbsp;</em><a class="go_tojf" href="<%=Config.getValue("ServerContext")%>/shop/point!queryPointsDesc.action?source=jfsc"  target="blank;">赚积分</a>
						<div class="int_tip_des" style="display:none;">保单生效前，积分处于冻结状态，不可使用</div>
						<div class="clear"></div>
					</div>
				</div>
			</s:else>
	</div>
</body>
</html>
