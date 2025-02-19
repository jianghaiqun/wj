/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/ 
NTKF_PARAM = {
		  siteid:"kf_9401",  //小能提供企业id,
		  settingid:sinosoft.xiaoNeng_CustomerService,   //小能分配的缺省客服组id  
		  uid:"",     //用户id
		  uname:"",   //用户名              
		  isvip:"",   //是否为VIP用户,登录用户可选,游客为"0"
		  userlevel:""//网站用户级别，登录用户可选，游客不填

} ;

var loginflag = false;//登陆标记
var gradeflag="";//等级标记
(function() {
	// 刷新header登录、注册信息
	jQuery.flushHeaderInfo = function () { 
		var $headerShowLoginWindow = jQuery("#headerShowLoginWindow");
		var $headerShowRegisterWindow = jQuery("#headerShowRegisterWindow");
		var $headerLoginMemberUsername = jQuery("[id=headerLoginMemberUsername]");
		var $headerMemberCenter = jQuery("#headerMemberCenter");
		var $headerRegister = jQuery("#headerRegister");
		var $headerLogout = jQuery("#headerLogout");
		var $YesLogin = jQuery("#YesLogin");
		var $NoLogin = jQuery("#NotLogin");
		
		jQuery.getJSON(sinosoft.base + '/shop/member!checkLogin.action?callback=?',
				function(data) {
					if (data && data.status == '0') {
						    loginflag = true;
						    gradeflag = data.grade;
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
								
							}
							if (jQuery.cookie("loginMemberId")==null) {
								jQuery.cookie("loginMemberId", data.loginMemberId);
							}
							if (data.grade == 'K0') {
								jQuery("[id=headerLoginMemGradeIcon]").html('<span style="display: none;" class="vip_tipsf" id="headerGradeSpan"><span class="vip_sfs"><a href="'+sinosoft.front+'/about/xszn/hysm/index.shtml">成为会员</a></span>购买折扣更多</span>');
							}
							
							if (data.gradeClass != null && data.gradeClass != '') {
								jQuery("[id=headerLoginMemGradeIcon]").addClass(data.gradeClass);
							}
							jQuery('#prepay').text("待支付("+data.PrePay+")");
							jQuery('#preeffecitive').text("待生效("+data.Payed+")");
							jQuery('#effecitive').text("已生效("+data.Effective+")");
							jQuery('#shopcartinfo').html(data.carlist);
							jQuery('#shoptotal').text(data.shoptotal);
							jQuery('#shopCartNo').text(data.shopcount);
							jQuery('#gradeinfo').text(data.gradeinfo);
							jQuery('#memberphoto').attr("src",data.headPicPath);
							jQuery('#CouponCount').text(data.CouponCount);
							
							if (typeof(jQuery('#troberName')) != 'undefined') {
						 		if (jQuery('#troberName').text() == null || jQuery('#troberName').text() == '') {
						 			if (jQuery("#headerLoginMemberUsername").text() != null && jQuery("#headerLoginMemberUsername").text() != '') {
						 				jQuery('#troberName').text(jQuery("#headerLoginMemberUsername").text());
						 			}
						 		}
						 	}
							// 小能客服获取客户信息
							getUserInfo();
							 
				} else {
						if(jQuery.cookie("loginMemberId")=="tencent"){
							if(jQuery.cookie("loginMemberUsername")!=null && jQuery.cookie("loginMemberUsername")!=""){
								$headerLoginMemberUsername.text(getNameSplit(jQuery.cookie("loginMemberUsername")));
								$YesLogin.show();
								$NoLogin.hide();
								jQuery("#quick_1").hide();
								jQuery("#quick_2").show();
								gradeflag = "";
							}
						}else{
							$headerLoginMemberUsername.text("");
							$YesLogin.hide();
							$NoLogin.show();
							jQuery.cookie("loginMemberId", null);
							jQuery.cookie("VIPUsername", null);
							
							// 设置评论登录标记
							var commentLogin = jQuery("#commentLogin");
							if ((null != commentLogin) && ("undefined" != typeof(commentLogin)))
							{
								jQuery('#commentLogin').val("0");
							}
							
							jQuery('#shopCartNo').text("0");
							loginflag = false;
							gradeflag = "";
						}
						if (typeof(jQuery('#troberName')) != 'undefined') {
							if (jQuery('#troberName').text() != null && jQuery('#troberName').text() != '') {
								jQuery('#troberName').text('');
								window.location.reload();
							}
					 	}
						// 小能客服获取客户信息
						getUserInfo();
					}
		       });
		
	};
	jQuery.flushHeaderInfo();
})();

function getUserInfo() {
	
	var userName=jQuery.cookie("loginMemberUsername");
	var memberid=jQuery.cookie("loginMemberId");
    var vip = "0";
	var level = "0";
	var uname = ""
	if(gradeflag != null && gradeflag!=""){
		vip="1";
		if(gradeflag=="VIP"){
			level="99";
		}else{
			level=Number(gradeflag.substring(1,2))+1;
		}
	}
	if (userName == null||userName==""||userName==undefined||memberid=="tencent") {
		userName = "";
	}
	if (memberid == null||memberid==""||memberid==undefined||memberid=="tencent") {
		memberid = "";
	}
	NTKF_PARAM["uname"]=userName;
	NTKF_PARAM["uid"]=memberid;
	NTKF_PARAM["isvip"]=vip;
	NTKF_PARAM["userlevel"]=level;
}
function getNameSplit(cName) {
	if (cName.indexOf("@") > 0) {
		cName = cName.substring(0, cName.indexOf("@"));
	}
	return cName;
//	if (cName.indexOf("@") == -1) {
//		return cName;
//	} else {
//		if (cName.length < 36) {
//			return cName;
//		} else {
//			var tName = cName.split("@")[0];
//			if (tName.length <= 20) {
//				return tName + "...";
//			} else {
//				return tName.substring(0, 19) + "...";
//			}
//		}
//	}
}
// add by cuishigang 2015-07-07 首页改版
jQuery("#NotLoginSpan").hover(function(){
	jQuery(this).addClass("hover");
	jQuery(this).children(".m-login-x").show();
	jQuery(".m-login-no").show();
},function(){
	jQuery(this).removeClass("hover");
	jQuery(".m-login-no").hide();
	jQuery(this).children(".m-login-x").hide();
})

jQuery("#YesLoginSpan").hover(function(){
	jQuery(this).addClass("hover");
	jQuery(".m-login-yes").show();
},function(){
	jQuery(this).removeClass("hover");
	jQuery(".m-login-yes").hide();
})

jQuery("#i-user-span").hover(function(){
	jQuery(this).addClass("hover");
	//if(jQuery.cookie("loginMemberUsername")!=null && jQuery.cookie("loginMemberUsername")!=""){
	if(loginflag){
		jQuery("#m-user-yes").show();
	}else{
		jQuery("#m-user-no").show();
	}
},function(){
	jQuery(this).removeClass("hover");
	jQuery("#m-user-no").hide();
	jQuery("#m-user-yes").hide();
})

jQuery("#i-user-cart-span").hover(function(){
	jQuery(this).addClass("hover");
	//if(jQuery.cookie("loginMemberUsername")!=null && jQuery.cookie("loginMemberUsername")!=""){
	if(loginflag){
		var tcount = jQuery('#shopCartNo').text();
		if(tcount>=1){
			jQuery("#m-cart-yes").show();
		}else{
			jQuery("#m-cart-no").show();
		}
	}else{
		jQuery("#m-cart-nologin").show();
	}
},function(){
	jQuery(this).removeClass("hover");
	jQuery("#m-cart-yes").hide();
	jQuery("#m-cart-nologin").hide();
	jQuery("#m-cart-no").hide();
})

/*jQuery("#i-service").hover(function(){
	jQuery(".m-service_con").show();
},function(){
	jQuery(".m-service_con").hide();
})

jQuery("#i-WeChat").hover(function(){
	jQuery(".m-WeChatImg").show();
},function(){
	jQuery(".m-WeChatImg").hide();
})*/

/**
  * 得到参数
  * @param url
  * @returns
  */
 function getParam(){
 	
 	//加旅行者年龄输入域隐藏于显示控制
	var age = new Array([3]);
	var age_num = 0;
	jQuery('#select_age > span').each(function(){
		    var tClass = jQuery(this).attr('class');
		    if(tClass.indexOf("select")!=-1){
		    	age[age_num] = jQuery(this).attr("id");
		    }else{
		    	age[age_num]="";
		    }
		    age_num =parseInt(age_num)+1;
	});
 	var age_one = age[0];
 	var age_two = age[1];
 	var age_three = age[2];
 	var period = "";
	jQuery('#select_period > span').each(function(){
	    var tClass = jQuery(this).attr('class');
	    if(tClass.indexOf("select")!=-1){
	    	period = jQuery(this).attr("id");
	    } 
});
 	var travelAddress = jQuery("#travelAddress").val();
 	var tRequest="&age_three="+age_three+"&age_two="+age_two+"&age_one="+age_one+"&period="+period+"&travelAddress="+travelAddress;
 	return tRequest;
 }
 /*查看计划*/
jQuery("#lookplan").click(function(){ 
	
	//modify by cuishigang 首页改版
	var tRequest = getParam();
	window.open (sinosoft.base+"/Search/newlist.jsp?1=1"+tRequest , "_blank");
    /*jQuery.ajax({
		url: sinosoft.base + "/shop/index_navigation!indexQueryProduct.action?callback=?"+tRequest,
		dataType: "json",
		async: false,
		success: function(data) {
			   jQuery("#lvyouquick").html(data.lvyouhtml);
			}
	});*/
		
});
jQuery('#select_age > li').click(function(){
    jQuery(this).toggleClass("age_select");
});
var companyName = "中国平安";
var forlink = "/Transition/PinAnTransition.jsp";
var comCode="2007";
var choose = false;
// add 车险跳转
jQuery('.f-car').click(function () {
    if (jQuery(this).attr('rel') == 'pa') {
    	forlink = '/Transition/PinAnTransition.jsp';
        companyName = '中国平安';
        choose = true;
    }
    if (jQuery(this).attr('rel') == 'tpy') {
    	forlink = 'http://www.ecpic.com.cn/cpicmall/fastCalculation_cpic/CpicFastCalculation.jsp?bSource=02&sourceName=%25E5%25BC%2580%25E5%25BF%2583%25E4%25BF%259D&source=533';
        companyName = '太平洋保险';
        comCode = 2011;
    }
    if (jQuery(this).attr('rel') == 'yg') {
    	forlink = 'http://chexian.sinosig.com/simplePremium/territory_marketing.jsp?utm_source=P05-kaixinbao&utm_campaign=car&areaCode=W06010003';
        companyName = '阳光保险';
        comCode = 1087;
    }
    if (jQuery(this).attr('rel') == 'dd') {
    	forlink = 'http://www.95590.cn/ebiz/view/onlinePartner/opBaseInfo.jsp?utm_source=kaixinbao';
        companyName = '大地保险';
        comCode = 2021;
    }
    if (jQuery(this).attr('rel') == 'tp') {
    	forlink = 'http://chexian.axatp.com/toPreparation.do?cityCode=310100&localProvinceCode=310000&departmentCode=5&linkResource=&selectPayChannel=&isAgent=0&isRenewal=&ecInsureId=&select_city=%C9%CF%BA%A3%CA%D0&planDefineId=3&=&rt=0&ms=';
        companyName = '安盛天平保险';
        comCode = 2026;
    }
    if (jQuery(this).attr('rel') == 'pa1') {
    	forlink = 'http://www.pingan.com/property_insurance/pa18AutoInquiry/';
        companyName = '中国平安';
    }
    if (jQuery(this).attr('rel') == 'tpy1') {
    	forlink = 'http://www.ecpic.com.cn/mall/policy/policyInfo/view';
        companyName = '太平洋保险';
    }
    if (jQuery(this).attr('rel') == 'yg1') {
    	forlink = 'http://chexian.sinosig.com/NetCar/carCustomService_forwardClaimProgressSearch.action?selectedModuleId=claimProgress';
        companyName = '阳光保险';
    }
    if (jQuery(this).attr('rel') == 'dd1') {
    	forlink = 'http://www.95590.cn/ebiz/view/insuranceClaim/claimMain.jsp?showflag=1';
        companyName = '大地保险';
    }
    if (jQuery(this).attr('rel') == 'tp1') {
    	forlink = 'http://www.axatp.com/claim/';
        companyName = '安盛天平汽车保险';
    }
});

function initCom(){
	if (jQuery("#id_car").val() == 'pa') {
    	forlink = '/Transition/PinAnTransition.jsp';
        companyName = '中国平安';
        choose = true;
    }
	if (jQuery("#id_car").val() == 'tpy') {
    	forlink = 'http://www.ecpic.com.cn/cpicmall/fastCalculation_cpic/CpicFastCalculation.jsp?bSource=02&sourceName=%25E5%25BC%2580%25E5%25BF%2583%25E4%25BF%259D&source=533';
        companyName = '太平洋保险';
        comCode = 2011;
    }
	if (jQuery("#id_car").val() == 'yg') {
    	forlink = 'http://chexian.sinosig.com/simplePremium/territory_marketing.jsp?utm_source=P05-kaixinbao&utm_campaign=car&areaCode=W06010003';
        companyName = '阳光保险';
        comCode = 1087;
    }
	if (jQuery("#id_car").val() == 'dd') {
    	forlink = 'http://www.95590.cn/ebiz/view/onlinePartner/opBaseInfo.jsp?utm_source=kaixinbao';
        companyName = '大地保险';
        comCode = 2021;
    }
	if (jQuery("#id_car").val() == 'tp') {
    	forlink = 'http://chexian.axatp.com/toPreparation.do?cityCode=310100&localProvinceCode=310000&departmentCode=5&linkResource=&selectPayChannel=&isAgent=0&isRenewal=&ecInsureId=&select_city=%C9%CF%BA%A3%CA%D0&planDefineId=3&=&rt=0&ms=';
        companyName = '安盛天平保险';
        comCode = 2026;
    }
}
/**
 * 新过度页面跳转
 */
function gainQuote(source){
	
	
	 var carUser = "";
	 var carPhone = "";
	 var carNO = "";
	 if(source=="1"){
		 carUser = jQuery("#carUser").val();
		 carPhone = jQuery("#carPhone").val();
		 carNO = jQuery("#carNO").val();
		 carUser = jQuery("#carUser").val();
		 carNO = jQuery("#carNO").val();
		 if(jQuery("#carUser").val().search(/^[\u0391-\uFFE5\w]+$/)==-1){
			 alert("输入姓名有误");
			 return;
		 }
		 var cellphone=/^(((1[0-9]{1}[0-9]{1}))+\d{8})$/;
		 if(!cellphone.test(carPhone))
		 {
		    alert('请输入有效的手机号码！');
		    return false;
		 }
	 }else{
		 carUser = jQuery("#id_caruser").val();
		 carPhone = jQuery("#id_mobile").val();
		 carNO = jQuery("#id_carno").val();
		 carUser =jQuery("#id_caruser").val();
		 carNO = jQuery("#id_carno").val();
		 if(jQuery("#id_caruser").val().search(/^[\u0391-\uFFE5\w]+$/)==-1){
			 alert("输入姓名有误");
			 return;
		 }
		 var cellphone=/^(((1[0-9]{1}[0-9]{1}))+\d{8})$/;
		 if(!cellphone.test(carPhone))
		 {
		    alert('请输入有效的手机号码！');
		    return false;
		 }
		 initCom();
	 }

	if("中国平安"!=companyName){
	     if(forlink==000 || forlink == '#' || forlink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
	    	 jQuery.ajax({
					url: sinosoft.base+"/car/car_user!save.action?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO,
					dataType: "json",
					async: false,
					success: function(data) {
				    	 var win =window.open("");
				    	 win.location.href=sinosoft.base+"/shop/shopping_cart!goToCompany.action?company="+comCode;
						}
					}); 
	    	 
	     }
	}else{
		jumpPa(carNO,carUser,carPhone);
	}
}
/**
 * 跳转到中间页jsp
 */
function jumpPa(carNO,carUser,carPhone){
	var check = 0;
	if($("#guidtype").attr("checked") == true){
		check = 1;
	}
	var driveCity = "";
	if(carNO != "" && check == 0){
		jQuery.ajax({
			url: sinosoft.base+"/car/transition!queryDrive.action?carNO="+carNO,
			dataType: "json",
			async: false,
			success: function(data) {
				driveCity = data.cityName;
				}
			}); 
	}
	carUser = encodeURIComponent(encodeURIComponent(carUser));
	carNO = encodeURIComponent(encodeURIComponent(carNO));
	driveCity = encodeURIComponent(encodeURIComponent(driveCity));
	var win =window.open("");
	win.location.href=sinosoft.base+forlink+"?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO+"&CarProperty="+check+"&driveCity="+driveCity;

}
