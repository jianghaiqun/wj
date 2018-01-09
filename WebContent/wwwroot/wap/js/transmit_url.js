var ua = window.navigator.userAgent.toLowerCase();
if(ua.match(/MicroMessenger/i) == 'micromessenger'){
	var timestamp = (new Date()).valueOf().toString();
	timestamp = timestamp.substr(0, 10);
	var wxnonceStr = "kaixinbaodlr" + timestamp;
	var wxticket = "";
	wx_get_jsapi_ticket();
	var wxSha1 = "";
	var appid = "";
	var currenturl = encodeURIComponent(window.location.href);
	var tURL = $("#AppResourcePath").val()+"/weixin/ajaxSign.action?jsapi_ticket="+wxticket+"&noncestr="+wxnonceStr+"&timestamp="+timestamp+"&url="+currenturl;

	$.ajax({
		type : "get",
		url : tURL,
		dataType : "json",
		async: false,
		success : function(data) {
			wxSha1 = data.sign;
			appid = data.appid;
		}

	});
}

function wx_get_token(){
	var token = getcookie("access_token");
	if(token==null || token==""){
		var tURL = $("#AppResourcePath").val()+'/weixin/ajaxtoken.action';
		$.ajax({
			type : "POST",
			url : tURL,
			dataType : "json",
			async: false,
			success : function(data) {
				var access_token = data.access_token;
				setcookie("access_token",access_token);
				return access_token;
			}
		});

	}
}

//微信配置
function wx_get_jsapi_ticket() {
	var ticket = "";
	do {
		ticket = getcookie("wx_ticket");
		if (ticket !=null && ticket!="") {
			wxticket = ticket;
			break;
		}
		var token = getcookie("access_token");
		if (token == null || token=="") {
			wx_get_token();
		}
		token = getcookie("access_token");
		if (token == null || token=="") {
			break;
		}
		var tURL = $("#AppResourcePath").val()+"/weixin/ajaxticket.action?token="+token;
		$.ajax({
			type : "get",
			url : tURL,
			dataType : "json",
			async: false,
			success : function(data) {
				ticket = data.ticket;
				wxticket = ticket;
				setcookie("wx_ticket",wxticket);
			}

		});

	} while (0);
}

wx.config({
	debug : false,
	appId : appid,
	timestamp : timestamp,
	nonceStr : wxnonceStr,
	signature : wxSha1,
	jsApiList : ['onMenuShareTimeline', 'onMenuShareAppMessage']
// 功能列表，我们要使用JS-SDK的什么功能
});

wx.ready(function() {
	wx.onMenuShareTimeline({
		title : document.getElementById("wx_title").value, // 分享标题
		link : document.getElementById("wx_url").value,//+"&SourceOpenID="+$("#openID").val()+"&SourceUserCode="+$("#userCode").val(), // 分享链接
		imgUrl : document.getElementById("wx_msgimg").value, // 分享图标
		success : function() {
			// 用户确认分享后执行的回调函数
			shareCallback();
			// 用户确认分享后执行的回调函数
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
		}
	});
	wx.onMenuShareAppMessage({
		title : document.getElementById("wx_title").value, // 分享标题
		desc : document.getElementById("wx_description").value, // 分享描述
		link : document.getElementById("wx_url").value,//+"&SourceOpenID="+$("#openID").val()+"&SourceUserCode="+$("#userCode").val(), // 分享链接
		imgUrl : document.getElementById("wx_msgimg").value, // 分享图标
		type : '', // 分享类型,music、video或link，不填默认为link
		dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
		success : function() {
		}, 
		cancel : function() {
			// 用户取消分享后执行的回调函数	
		}
	});
});

function setcookie(name,value){  
    var exp  = new Date();  
    exp.setTime(exp.getTime() + 6000*1000);  
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();  
}  

function getcookie(name){  
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)")); 
    if(arr != null){  
        return (arr[2]);  
    }else{  
        return "";  
    }  
}  

function delcookie(name){  
    var exp = new Date();  
    exp.setTime(exp.getTime() - 1);  
    var cval=getCookie(name);  
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();  
}

/**
 * 微信分享的朋友圈回调函数
 */
function shareCallback(){
	// 不传表示不回调
	var CurrentPage = document.getElementById("CurrentPage").value;
	if (null == CurrentPage || "" == CurrentPage) {
		return;
	}
	var tURL = document.getElementById("AppResourcePath").value + "/weixin/shareCallback.action";
	$.ajax({
		type : "get",
		url : tURL,
		dataType : "json",
		data : {
			'CurrentPage' : document.getElementById("CurrentPage").value,
			'ActivityId' : document.getElementById("ActivityId").value
		},
		success : function(data) {
		}
	});
}