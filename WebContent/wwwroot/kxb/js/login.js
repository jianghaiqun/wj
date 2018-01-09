/***
 *
 *	http://www.sinosoft.com.cn 
 *
 **/
(function(){
	//登录窗口用artdialog实现 fhz
 	jQuery('#headerShowLoginWindow').click(function(){
		    	   //配置
		    	 if(jQuery('#artLoginFlag').val()==1){
		    		 artLogin();//用弹出窗口登录
		    	 }
		    	 else{
		    		 var localURL=window.location.href;
		    		 var loginBackURL=encodeURIComponent(localURL);
		    		 //window.location.href= sinosoft.base + "/shop/member!newLogin.action?tagid=logintag&loginBackURL="+loginBackURL;

		    		 window.location.href= sinosoft.httpsbase + "/shop/member!newLogin.action?tagid=logintag&loginBackURL="+loginBackURL;
		    	 }
			 });
		//投保录入页登录
	 	jQuery('#shoplogin').click(function(){
	 		         if(!checkCode()) return false;
			    	 if(!checkShopUserName()) return false;
			    	 if(!checkShopPassword()) return false;
			    	 jQuery(this).addClass("login_londing");
			    	 jQuery('#shoperror').html("");
			    	 jQuery.getJSON(sinosoft.base + '/shop/member!artAjaxLoginDoamin.action?callback=?&loginName='+jQuery("#shoploginname").val()+"&loginPassword="+jQuery("#shoppass").val()+"&j_captcha="+jQuery("#shopCode").val(),
			          			function(data) {
				  	          			if (data.status == "success") {
				  	          			    jQuery('#shop_login').hide();
				  	          				artSuccessBack(data);
				  	          				jQuery("#shoploginname").val("");
				  	          				jQuery("#shoppass").val("");
				  	          			}else{
				  	          			    loginCaptchaImageRefresh();
				  	          			    jQuery('#shoplogin').removeClass("login_londing");
				  	          			    jQuery('#shoperror').html(data.message);
				  	          			    logqueryRemarkError();
				  	          			}
			  	            	     });
			    	 VL_Send('buylogin','','');
		});
 		//modify by liuc 20130710 当点击退出时页面仍停留在当前页面、用户退出
 		jQuery('#headerLogout').click(
 			function() {
 				jQuery.getJSON(sinosoft.base + '/shop/member!logoutAjax.action?callback=?' ,
 						function(data) {
		 					    if(data && data.status == '1'){	
									jQuery.flushHeaderInfo();
									if(jQuery("#out_member")){
										var str = jQuery("#out_member").val();
										if(str == "member"){
											window.location.href= sinosoft.base + "/shop/member!newLogin.action";
										}
									}
								} else {
									jQuery.tip("退出异常!");
								}
 				         }
 				);
 				
 		    if(typeof logoutAfterAppnt!='undefined'){
 		    	logoutAfterAppnt();
 		    }
 		    var tConfirm = document.getElementById('comfirmToPay');
 			if ((null != tConfirm) && ("" != tConfirm) && ("undefined" != typeof(tConfirm))){
 				jQuery("#comfirmToPay").hide();
 				jQuery("#comfirmToPay").siblings("em").hide();
 				jQuery("#comfirmToPay").parent().addClass(" syr33");
 			}
 			var youhui_div = jQuery('#youhui_div');
 			 if ((null != youhui_div) && ("" != youhui_div) && ("undefined" != typeof(youhui_div))){
 				 youhui_div.hide();
 			 }
 			 //积分商城的会员退出 			
 		    var jifen_login_form = document.getElementById('jifen_login_form');
		    if ((null != jifen_login_form) && ("" != jifen_login_form) && ("undefined" != typeof(jifen_login_form))){
			 jifen_login_form.submit();
		    }
		    //详细页会员退出		
 		    var integer_login = document.getElementById('integer_login');
		    if ((null != integer_login) && ("" != integer_login) && ("undefined" != typeof(integer_login))){
		    	var riskcode_int=jQuery('#RiskCode').val();
		    	if(complicatedFlag == "Y") {
		    		compRecal(riskcode_int,"");
		    	}else{
		    		premDocal(riskcode_int);
		    	}
		    	jQuery("#integer_login").show();
		    	jQuery("#favorite").addClass("no_add_sc");
		    	jQuery("#favorite").html("<em></em>收藏");
		    }
		    //预览页刷新
			if(window.location.href.indexOf("shop/order_config_new!sendDirectUrl")!=-1){
				jQuery('.integal_hjcon').hide();
				jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?orderSn='+orderSn+"&productId="+productId,
 						function(data) {
		 					    if(data){
		 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
									jQuery("#result_sendPoints").html(data.result_sendPoints);
									jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
								}
 				         }
 				);
			}
			
			if(window.location.href.indexOf("shop/order_config_new!buyNow")!=-1 
					|| window.location.href.indexOf("shop/order_config_new!buyNowUpdate")!=-1 
					|| window.location.href.indexOf("shop/order_config_new!keepInput")!=-1){
				jQuery('.integal_hjcon').hide();
				if (pointExchangeFlag != "1") {
					var varPrice = document.getElementById('priceTotle').innerHTML;
					var cpsUserId = jQuery.cookie('cpsUserId');
					var channelsn = "wj";
					if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
						channelsn = "cps";
					}
					var insMult = jQuery("#insMult").text();
					jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+varPrice+"&productId="+productId+"&channelsn="+channelsn+"&risktypeNum="+insMult,
								function(data) {
			 					    if(data){
			 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
										jQuery("#result_sendPoints").html(data.result_sendPoints);
										jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
									}
						         }
						);
				}
			}
			
		    if(typeof logoutAfterMsg!='undefined'){
		    	logoutAfterMsg();
 		    }

 	});
})();
function artLoginShopCart(){
	art.dialog.data('base', sinosoft.base);//将服务
	art.dialog.data('front', sinosoft.front);
	art.dialog.data('shopcarturl', sinosoft.base + "/shop/member_shopcart!getShopCartINF.action");
	art.dialog.open(sinosoft.front+'/block/art_login.shtml',	 {id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
}
//弹出登陆框 华丽
function artLogin(){
	art.dialog.data('base', sinosoft.base); 
	art.dialog.data('front', sinosoft.front);
	art.dialog.data('tagid', "");
	art.dialog.open( sinosoft.front + '/block/art_login.shtml', {id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
	//art.dialog.open( sinosoft.front + '/block/art_login.shtml',{id: 'loginArtWindow',title: '登录窗口',width:362,height:410,fixed:true}, false);
}
//弹出注册框
function artRegister(){
	art.dialog.data('base', sinosoft.base); 
	art.dialog.data('front', sinosoft.front);
	art.dialog.data('tagid', "regtag");
	art.dialog.open( sinosoft.front + '/block/art_login.shtml', {id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
	//art.dialog.open( sinosoft.front + '/block/art_login.shtml',{id: 'loginArtWindow',title: '登录窗口',width:362,height:410,fixed:true}, false);
}
/**
 * 投保录入页，“现在注册”
 */
function artNewLogin(){
	art.dialog.data('base', sinosoft.base); 
	art.dialog.data('front', sinosoft.front);
	art.dialog.data('tagid', "regtag");
	art.dialog.open( sinosoft.front + '/block/art_login.shtml',{id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
}
//弹出登陆框 华丽
function artLogin_other(frontPath){
	art.dialog.data('base', sinosoft.base); 
	art.dialog.data('front', sinosoft.front);
	art.dialog.open(frontPath + 'template/art_login.shtml',{id: 'loginArtWindow',title: '登录窗口',width:362,height:389}, false);
}

function artSuccessBack(data){
	jQuery.flushHeaderInfo();
	var redirectionUrl = jQuery.cookie("redirectionUrl");
	if(redirectionUrl != null && redirectionUrl != "") {
	location.href = redirectionUrl;
	}
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
	var tConfirm = document.getElementById('comfirmToPay');
	if ((null != tConfirm) && ("" != tConfirm) && ("undefined" != typeof(tConfirm))){
		jQuery("#comfirmToPay").show();
		jQuery("#comfirmToPay").siblings("em").show();
		jQuery("#comfirmToPay").parent().removeClass(" syr33");
	}
	//add by cuishigang 投保人信息快速录入，登录实现--start
	if(typeof loginAfterAppnt!='undefined'){
		loginAfterAppnt();
		moreQuicklist();
	} 
	//支付页优惠活动
	 /*var youhui_div = jQuery('#youhui_div');
	 if ((null != youhui_div) && ("" != youhui_div) && ("undefined" != typeof(youhui_div))){
		 youhui_div.show();
	 }*/
	//add by cuishigang 投保人信息快速录入，登录实现--start
	
	// 支付页面登录
	if(typeof payLoginAfterAppnt!='undefined'){
		payLoginAfterAppnt();
	} 
	if(typeof loginAfterMsg!='undefined'){
		loginAfterMsg();
	}
	var url = window.location.href;
	//预览页刷新
	if(url.indexOf("shop/order_config_new!sendDirectUrl")!=-1){
		window.location.reload();
	}
	
	if(url.indexOf("/jifen/")!=-1){
		if (url.substr(url.length-7) == '/jifen/') {
			window.location.reload();
		}
	}
	if (url.indexOf("shop/points!integralMall.action")!=-1) {
		window.location.reload();
	}
	if(url.indexOf("shop/order_config_new!buyNow")!=-1){
		if(pointExchangeFlag !='1'){
			ajaxAlreadySave(data.memberid);
		}
	}
	
	if(window.location.href.indexOf("shop/order_config_new!buyNow")!=-1 
			|| window.location.href.indexOf("shop/order_config_new!buyNowUpdate")!=-1 
			|| window.location.href.indexOf("shop/order_config_new!keepInput")!=-1){
		if (pointExchangeFlag != "1") {
			var varPrice = document.getElementById('priceTotle').innerHTML;
			var cpsUserId = jQuery.cookie('cpsUserId');
			var channelsn = "wj";
			if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
				channelsn = "cps";
			}
			var insMult = jQuery("#insMult").text();
			jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+varPrice+"&productId="+productId+"&channelsn="+channelsn+"&risktypeNum="+insMult,
						function(data) {
	 					    if(data){
	 					    	if (data.inspageone_pointsInfo != "") {
	 					    		jQuery('.integal_hjcon').show();
	 					    		jQuery(".integral_hj").html(data.inspageone_pointsInfo);
	 					    	}
	 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
								jQuery("#result_sendPoints").html(data.result_sendPoints);
								jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
							}
				         }
				);
		}
	}
	//详细页会员登录		
	var integer_login = document.getElementById('integer_login');
    if ((null != integer_login) && ("" != integer_login) && ("undefined" != typeof(integer_login))){
    	var riskcode_int=jQuery('#RiskCode').val();
    	if(complicatedFlag == "Y") {
    		compRecal(riskcode_int,"");
    	}else{
    		premDocal(riskcode_int);
    	}
    	jQuery("#integer_login").hide();
    	initFavorites();
    }
    // 新版sem页登录
    var sem_list = document.getElementById('sem_list');
    if ((null != sem_list) && ("" != sem_list) && ("undefined" != typeof(sem_list))){
    	initSemFavorites();
    }
}
	
	//弹出窗回调
	function artContentBack(data){
		jQuery.flushHeaderInfo();
	}
	var localURL=window.location.href;
	var loginBackURL=encodeURIComponent(localURL);
	function otherLogin(comCode, source){
		if(comCode=="tencent"){
			if (source != null && source !='') {
				VL_Send(source,'','');
			}
			window.location.href="/wj/shop/tencent_login!aouth.action?loginBackURL="+loginBackURL+"";
		}else if(comCode=="sina"){
			if (source != null && source !='') {
				VL_Send(source,'','');
			}
			window.location.href="/wj/shop/sina_login!aouth.action?loginBackURL="+loginBackURL+"";
		}else if(comCode=="alipay"){
			if (source != null && source !='') {
				VL_Send(source,'','');
			}
			window.location.href="/wj/shop/alipay_login!aouth.action?loginBackURL="+loginBackURL+"";
		}
	}
 function checkShopUserName() {
	    var userName = jQuery('#shoploginname').val()
	    var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,4}$/; //验证邮箱的正则
	    var regu = /^[1][3-8][0-9]{9}$/;
	    if(userName==null || userName==""){
	    	jQuery('#shoperror').html("用户名不能为空哟");
	        return false;
	    }
	    if (userName!=null&&userName!=""&&!myReg.test(userName) && !regu.test(userName)) {
	    	jQuery('#shoperror').html("正确的邮箱或手机号是登录的唯一凭证哟");
	        return false;
	    }

	    return true;
 }
 function checkShopPassword() {
	    var regPass = jQuery('#shoppass').val()
	    if (regPass.length < 6 || regPass.length > 16) {
	        jQuery('#shoperror').html("您的密码太短了，灰常不安全");
	        return false;
	    } 
	     else if(regPass.indexOf(" ")!=-1){
	        jQuery('#shoperror').html("密码是不能填写空格的，改一下吧");
	        return false;
	    }else {
	        return true;
	    }
 }
 function checkCode(){
	 	if(jQuery('#shopCode').is(':visible')){
	 		var userCode = jQuery('#shopCode').val();

	 	  if(userCode==null || userCode==""){
		    	jQuery('#shoperror').html("验证码不能为空哟");
		        return false;
		  }else {
		        return true;
		  }
	 	}else{
	 		return true;
	 	}
}
 function ajaxAlreadySave(memberid){
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxAlreadySave.action?productId="+productId+"&memberid="+memberid,
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					if(obj.status == "1"){
							var content = "<p>您于"+obj.date+"日暂存过此保单<br>是否继续填写上次暂存结果</p>";
						    if(obj.isoverdue == '0'){
						    	content += "<p style=\"color:red;\">（保单已过期，填入可修改）</p>";
							}
						    art.dialog({
								    title:'温馨提示',
								    content: content,
								    button: [
											       
											        {
											            name: '自动填写',
											            callback: function () {
											                //alert('自动填写逻辑');
											                window.location.href = sinosoft.base+obj.fillUrl;
											            } ,
										                focus: true
											        },
											        {
											            name: '不用',
											            callback: function () {
											               // this.content('关闭窗口').time(2);
											            	this.close();
											                return false;
											            }
											        }
											 ]
								    });
					}
			}
		}); 
	}


//点击刷新验证码图片
function loginCaptchaImageRefresh() {
 	document.getElementById("loginCaptchaImage").setAttribute("src", sinosoft.base+"/captcha.jpg?timestamp" + (new Date()).valueOf()); 
}
//标记登录错误次数
function logqueryRemarkError(){
 	jQuery.ajax({
 				url: sinosoft.base+'/shop/member!queryLoginRemark.action',
 				type: "post",
 				dataType: "json",
 				success: function(data){
 					if (data == false) {
 						jQuery('#logveriCode').show();
 						jQuery('#shopCode').val("");
 		            }
 			 	}			
 			});
}

//登录点击刷新验证码图片
$("#loginCaptchaImage").click(function(){
	loginCaptchaImageRefresh();
}); 