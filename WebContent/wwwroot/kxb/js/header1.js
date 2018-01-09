/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/
(function() {
	// 刷新header登录、注册信息
	jQuery.flushHeaderInfo = function () { 
		var $headerShowLoginWindow = jQuery("#headerShowLoginWindow");
		var $headerShowRegisterWindow = jQuery("#headerShowRegisterWindow");
		var $headerLoginMemberUsername = jQuery("#headerLoginMemberUsername");
		var $headerMemberCenter = jQuery("#headerMemberCenter");
		var $headerRegister = jQuery("#headerRegister");
		var $headerLogout = jQuery("#headerLogout");
		var $YesLogin = jQuery("#YesLogin");
		var $NoLogin = jQuery("#NotLogin");
		
		//QQ彩贝的显示信息
		var headshow=jQuery.cookie("QQCB_headshow");
		jQuery.getJSON(sinosoft.base + '/shop/member!checkLogin.action?callback=?',
				function(data) {
					if (data && data.status == '0') {
							if (jQuery.cookie("pointsChange") == '1') {
								jQuery("#pointsChange").show();
							}
							if (data.MemberPoints != null && data.MemberPoints != '') {
								jQuery("#memberPointsCount").html(data.MemberPoints);
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
								//zhangjinquan 11180 req20121204001-评论修改-增加评论用户和评论内容刷新 2012-12-11 start
								var tCommentUser = document.getElementById('CommentUser');
								if ((null != tCommentUser) && ("undefined" != typeof(tCommentUser)))
								{
									tCommentUser.innerHTML = jQuery.cookie("loginMemberUsername");
									tCommentUser.title = jQuery.cookie("loginMemberUsername");
									var oldComment = jQuery.cookie('CmntContent');
									if ((null != oldComment) && ("" != oldComment))
									{
										document.getElementById('CmntContent').value = oldComment;
										jQuery.cookie('CmntContent', null, {path: '/'});
									}
									var oldCmntChecked = jQuery.cookie('CmntAnonymous');
									if ("1" == oldCmntChecked)
									{
										document.getElementById('Anonymous').checked = true;
										jQuery.cookie('CmntAnonymous', null, {path: '/'});
									}
								}
								// 设置评论登录标记
								var commentLogin = jQuery("#commentLogin");
								if ((null != commentLogin) && ("undefined" != typeof(commentLogin)))
								{
									jQuery('#commentLogin').val("1");
								}
//								var trevw10cont02 = document.getElementById('revw10cont02');
//								if ((null != trevw10cont02) && ("undefined" != typeof(trevw10cont02)))
//								{
//									trevw10cont02.style.display="block";
//									document.getElementById('revw10cont01').style.display="none";
//								}
							} else if(data.MemberUsername != null ){
								$headerLoginMemberUsername.text(getNameSplit(data.MemberUsername));
								$YesLogin.show();
								$NoLogin.hide();
								var tCommentUser = document.getElementById('CommentUser');
								if ((null != tCommentUser) && ("undefined" != typeof(tCommentUser)))
								{
									tCommentUser.innerHTML = data.MemberUsername;
									tCommentUser.title = data.MemberUsername;
									var oldComment = jQuery.cookie('CmntContent');
									if ((null != oldComment) && ("" != oldComment))
									{
										document.getElementById('CmntContent').value = oldComment;
										jQuery.cookie('CmntContent', null, {path: '/'});
									}
									var oldCmntChecked = jQuery.cookie('CmntAnonymous');
									if ("1" == oldCmntChecked)
									{
										document.getElementById('Anonymous').checked = true;
										jQuery.cookie('CmntAnonymous', null, {path: '/'});
									}
								}
								
								// 设置评论登录标记
								var commentLogin = jQuery("#commentLogin");
								if ((null != commentLogin) && ("undefined" != typeof(commentLogin)))
								{
									jQuery('#commentLogin').val("1");
								}
//								var trevw10cont02 = document.getElementById('revw10cont02');
//								if ((null != trevw10cont02) && ("undefined" != typeof(trevw10cont02)))
//								{
//									trevw10cont02.style.display="block";
//									document.getElementById('revw10cont01').style.display="none";
//								}
								
							}
							//QQ彩贝的显示信息
							if(!(headshow==""||headshow==null)){
								jQuery("#qqcb_login_content").show();
								jQuery("#qqcb_headshow").append(headshow);
								jQuery("#qqcb_jifenurl").attr('href',jQuery.cookie("QQCB_JifenUrl")); 
							}else{
								jQuery("#qqcb_login_content").hide();
								jQuery("#qqcb_headshow").append('');
								jQuery("#qqcb_jifenurl").attr('href',''); 
							}
							
							if (data.grade == 'K0') {
								jQuery("#headerLoginMemGradeIcon").html('<span style="display: none;" class="vip_tipsf" id="headerGradeSpan"><span class="vip_sfs"><a href="'+sinosoft.front+'/about/xszn/hysm/index.shtml">成为会员</a></span>购买折扣更多</span>');
							}
							
							if (data.gradeClass != null && data.gradeClass != '') {
								jQuery("#headerLoginMemGradeIcon").addClass(data.gradeClass);
							}
							 
				} else {
						if(jQuery.cookie("loginMemberId")=="tencent"){
							if(jQuery.cookie("loginMemberUsername")!=null && jQuery.cookie("loginMemberUsername")!=""){
								$headerLoginMemberUsername.text(getNameSplit(jQuery.cookie("loginMemberUsername")));
								$YesLogin.show();
								$NoLogin.hide();
								jQuery("#quick_1").hide();
								jQuery("#quick_2").show();
								//QQ彩贝的显示信息
								if(!(headshow==""||headshow==null)){
									jQuery("#qqcb_login_content").show();
									jQuery("#qqcb_headshow").append(headshow);
									jQuery("#qqcb_jifenurl").attr('href',jQuery.cookie("QQCB_JifenUrl")); 
								}
							}
						}else{
							$headerLoginMemberUsername.text("");
							$YesLogin.hide();
							$NoLogin.show();
							// 设置评论登录标记
							var commentLogin = jQuery("#commentLogin");
							if ((null != commentLogin) && ("undefined" != typeof(commentLogin)))
							{
								jQuery('#commentLogin').val("0");
							}
//							var trevw10cont02 = document.getElementById('revw10cont02');
//							if ((null != trevw10cont02) && ("undefined" != typeof(trevw10cont02)))
//							{
//								trevw10cont02.style.display="none";
//								document.getElementById('revw10cont01').style.display="block";
//							}
							//QQ彩贝显示内容情况以及隐藏
							jQuery("#qqcb_login_content").hide();
							jQuery("#qqcb_headshow").append('');
							jQuery("#qqcb_jifenurl").attr('href',''); 
						}
					}
		       });
		
	};
	jQuery.flushHeaderInfo();
})();

function getNameSplit(cName) {
	if (cName.indexOf("@") == -1) {
		return cName;
	} else {
		if (cName.length < 36) {
			return cName;
		} else {
			var tName = cName.split("@")[0];
			if (tName.length <= 20) {
				return tName + "...";
			} else {
				return tName.substring(0, 19) + "...";
			}
		}
	}
}
