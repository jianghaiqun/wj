var classificArea = document.getElementById("ClassificationArea");
var doautoChange = null != classificArea && "undefined" != typeof classificArea;
function changeFluctuate() {
	var width_used = jQuery(window).width();
	jQuery("#servfloat").width(62);
	var isIE = !!window.ActiveXObject;
	var isIE6 = isIE && !window.XMLHttpRequest;
	var ua = navigator.userAgent.toLowerCase();
	if (width_used >= 1144) {
		document.getElementById("ClassificationArea").style.marginLeft = (width_used - 980)
				/ 2 - 82 + "px";
		if (isIE && isIE6) {
			document.getElementById("servfloat").style.marginRight = (width_used - 980)
					/ 2 - 80 + "px";
			jQuery("#servfloat").width(80)
		} else {
			if (ua.indexOf("chrome") > 0) {
				document.getElementById("ClassificationArea").style.marginLeft = (width_used - 980)
						/ 2 - 88 + "px";
				document.getElementById("servfloat").style.marginRight = (width_used - 980)
						/ 2 - 11 + "px"
			} else {
				document.getElementById("servfloat").style.marginRight = (width_used - 980)
						/ 2 - 11 + "px"
			}
		}
	} else {
		document.getElementById("ClassificationArea").style.marginLeft = "0px";
		if (isIE6) {
			document.getElementById("servfloat").style.marginRight = "0px"
		} else {
			document.getElementById("servfloat").style.marginRight = "18px"
		}
	}
}
if (doautoChange) {
	changeFluctuate()
}
function getWinSize() {
	var winHeight = window.innerHeight, winWidth = window.innerWidth;
	if (document.documentElement.clientHeight) {
		winHeight = document.documentElement.clientHeight;
		winWidth = document.documentElement.clientWidth
	} else {
		winHeight = document.body.clientHeight;
		winWidth = document.body.clientWidth
	}
	var height = winHeight - 50;
	var width = winWidth - 100
}
if (doautoChange) {
	getWinSize();
	window.onresize = doonresize
}
function doonresize() {
	getWinSize();
	changeFluctuate()
}
function getByteLength(str) {
	if (null == str) {
		return 4
	}
	if ("undefined" == typeof str) {
		return 9
	}
	var len = str.length;
	var byteLen = 0;
	for ( var i = 0; i < len; i++) {
		if (str.charCodeAt(i) > 255) {
			byteLen += 2
		} else {
			byteLen += 1
		}
	}
	return byteLen
}
function rPadByte(str, len, padChar) {
	if (null == str || "undefined" == typeof str) {
		return str
	}
	var preSublen = 0;
	var substr = "";
	var strlen = str.length;
	for ( var i = 0; i < strlen; i++) {
		substr = str.substring(0, i + 1);
		var sublen = getByteLength(substr);
		if (sublen > len) {
			substr = str.substring(0, i);
			break
		}
		preSublen = sublen
	}
	if ("" == padChar || "undefined" == typeof padChar) {
		return substr
	}
	for ( var i = 0; i < len - preSublen; i++) {
		substr += padChar
	}
	return substr
}
function lPadByte(str, len, padChar) {
	if (null == str || "undefined" == typeof str) {
		return str
	}
	var preSublen = 0;
	var substr = "";
	var strlen = str.length;
	for ( var i = 0; i < strlen; i++) {
		substr = str.substring(strlen - i, strlen);
		var sublen = getByteLength(substr);
		if (sublen > len) {
			substr = str.substring(strlen - i + 1, strlen);
			break
		}
		preSublen = sublen
	}
	if ("" == padChar || "undefined" == typeof padChar) {
		return substr
	}
	for ( var i = 0; i < len - preSublen; i++) {
		substr = padChar + substr
	}
	return substr
}
var _gaq = _gaq || [];
_gaq.push( [ "_setAccount", "UA-34720431-1" ]);
_gaq.push( [ "_setDomainName", "kaixinbao.com" ]);
_gaq.push( [ "_addOrganic", "m.baidu.com", "word" ]);
_gaq.push( [ "_addOrganic", "wap.baidu.com", "word" ]);
_gaq.push( [ "_addOrganic", "baidu", "word" ]);
_gaq.push( [ "_addOrganic", "baidu", "ie" ]);
_gaq.push( [ "_addOrganic", "baidu", "wd" ]);
_gaq.push( [ "_addOrganic", "so.360.cn", "q" ]);
_gaq.push( [ "_addOrganic", "so", "ie" ]);
_gaq.push( [ "_addOrganic", "so.com", "q" ]);
_gaq.push( [ "_addOrganic", "m.so.com", "q" ]);
_gaq.push( [ "_addOrganic", "sogou", "query" ]);
_gaq.push( [ "_addOrganic", "soso", "w" ]);
_gaq.push( [ "_addOrganic", "soso.com", "w" ]);
_gaq.push( [ "_addOrganic", "wap.soso.com", "key" ]);
_gaq.push( [ "_addOrganic", "wap.sogou.com", "keyword" ]);
_gaq.push( [ "_addOrganic", "youdao", "q" ]);
_gaq.push( [ "_addOrganic", "m.yisou.com", "q" ]);
_gaq.push( [ "_addOrganic", "jike.com", "q" ]);
_gaq.push( [ "_addOrganic", "glb.uc.cn", "keyword" ]);
_gaq.push( [ "_trackPageview" ]);
(function() {
	var ga = document.createElement("script");
	ga.type = "text/javascript";
	ga.async = true;
	ga.src = ("https:" == document.location.protocol ? "https://ssl"
			: "http://www")
			+ ".google-analytics.com/ga.js";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(ga, s);
	var _bdhmProtocol = "https:" == document.location.protocol ? " https://"
			: " http://";
	document
			.write(unescape("%3Cscript src='"
					+ _bdhmProtocol
					+ "hm.baidu.com/h.js%3F2d7e032a5f8d4e609feb9e22c0cb83f8' type='text/javascript'%3E%3C/script%3E"));
	document
			.write('<div id="Weixi" style="display:none;"><script type="text/javascript" src="http://www.kaixinbao.com/js/weixitrack.js"><\/script></div>')
	
//	// qq统计
//    var qq = document.createElement("script");
//    qq.src = "//wpa.b.qq.com/cgi/wpa.php?key=XzgwMDExODA2Nl8yNzU5MDRfODAwMTE4MDY2Xw8";
//    var sqq = document.getElementsByTagName("script")[0]; 
//    sqq.parentNode.insertBefore(qq, sqq);
//    
    // 秒针监控
    /*window._CiQ10818 = window._CiQ10818 || [];
    window._CiQ10818.push(['_cookieUseRootDomain', true]);
    var c = document.createElement('script');
    c.type = 'text/javascript';
    c.async = true;
    c.charset = 'utf-8';
    c.src = '//collect.cn.miaozhen.com/ca/10818';
    var h = document.getElementsByTagName('script')[0];
    h.parentNode.insertBefore(c, h);*/
    

})();
jQuery("#callus").click(function() {
	_gaq.push( [ "_trackEvent", "char", "xiaoneng" ])
});
jQuery("#callus1").click(function() {
	_gaq.push( [ "_trackEvent", "char", "xiaoneng" ])
});
jQuery("#callus2").click(function() {
	_gaq.push( [ "_trackEvent", "char", "xiaoneng" ])
});
function getId(obj) {
	return document.getElementById(obj)
}
function decideHide() {
	if (document.body.clientWidth < 1000) {
		if (getId("servfloat") != null) {
			getId("servfloat").style.visibility = "hidden"
		}
	} else {
		if (getId("servfloat") != null) {
			getId("servfloat").style.visibility = "visible"
		}
	}
}
jQuery(document).ready(function() {
	decideHide()
});
jQuery(window).resize(function() {
	decideHide()
});
function a(x, y) {
	l = jQuery(".wrapper").offset().left;
	w = jQuery(".wrapper").width();
	jQuery("#tbox").css("left", l + w + 10 + "px");
	jQuery("#tbox").css("bottom", y + "px")
}
function b() {
	h = jQuery(window).height();
	t = jQuery(document).scrollTop();
	if (t > h) {
		jQuery("#gotop").fadeIn("slow")
	} else {
		jQuery("#gotop").fadeOut("slow")
	}
}
jQuery(document).ready(function(e) {
	// a(10, 10);
	//b();
	jQuery("#gotop").click(function() {
		jQuery("body,html").animate( {
			scrollTop : 0
		}, 500)
	})
	jQuery(".weixin").hover(function(){
            jQuery(".weixinDiv").fadeIn();
    },function(){
            jQuery(".weixinDiv").fadeOut();
    })
});
jQuery(window).resize(function() {
	// a(10, 10)
});
jQuery(window).scroll(function(e) {
	// b()
});
jQuery(function() {
	jQuery(".nav_main li").hover(function() {
		jQuery(this).addClass("hovers_c");
		jQuery(this).children("ul li").attr("class", "")
	}, function() {
		jQuery(this).removeClass("hovers_c");
		jQuery(this).children("ul li").attr("class", "")
	});
	jQuery("#topnav_link li").hover(function() {
		jQuery(this).children("#wz_nav").addClass("q_wzdh").end();
		jQuery(this).children("#topNavDropMenu").show().end()
	}, function() {
		jQuery(this).children("#wz_nav").removeClass("q_wzdh").end();
		jQuery(this).children("#topNavDropMenu").hide()
	})
});

/*展示广告*/
jQuery(document).ready(function(){
	var popcookie=jQuery.cookie("popupcookie");
	if(popcookie!=null&&popcookie!=""){
		return;
	}
	var topUrl = window.location.pathname;
	jQuery.ajax({
		url: sinosoft.base+"/shop/shopping_cart!showTopAd.action?topUrl="+topUrl,
		async:true,
		dataType: "json",
		type:"get", 
		success: function(data) {
			
			var top = data.top;
			var popup = data.popup;
			
			//横幅广告
			if( top != undefined ){
				var list = top.split(",");
				var str = "";
				//if(list[1]>0 && list[2]>0){
				//	str = "<style>.main_nav{border-top:none;}</style><div><a href=\"http://"+list[5]+"\" target=\""+list[4]+"\"><img width=\""+list[1]+"\" height=\""+list[2]+"\" src=\""+sinosoft.staticPath+list[0]+"\" style=\"display:block; margin:0 auto;\" alt=\""+list[3]+"\"></a></div>";
				//}
				str = "<div style=\"background: url(" + sinosoft.staticPath + list[0] + ") no-repeat center  center; height:" + list[2] + "px;\">";
            	if (list[5]) {
            		str = str + "<a href=\"http://"+list[5]+"\" target=\""+list[4]+"\" style=\"width:100%; display:block; height:" + list[2] + "px;\" alt=\""+list[3]+"\"></a></div>";
            	} else {
            		str = str + "</div>";
            	}
                jQuery(".mini_nav").after(str);
			}
			
			//弹窗广告
			if( popup != undefined ){
				var list = popup.split(",");
				if(list[0]!='' && list[0]!=null){
				    // 初始化弹窗内容
					var t_window ="  <div id=\"message_ad\" style=\" z-index: 100; width: 300px; height: 210px;position:fixed; background: #fff; bottom: 0; right: 0; \">"+
					"<div style=\" width: 300px;  position:relative\">"+
					" <div class=\"\" id=\"sq_ad_btns_s\"></div>"+
					" <div class=\"ad_btn_ss\" id=\"sq_ad_btns\"></div>     "+
					"<span id=\"message_closes\" style=\"padding: 5px 0 5px 0; width:73px; line-height: auto; color: red; font-size: 12px; font-weight: bold; text-align: center; cursor: pointer; overflow: hidden;     position: absolute;    right: 0px;   top: 0px;     display: block;   height: 19px;\"></span></div> "+
					" <div style=\"  border-top: none; width: 100%; height: auto; font-size: 16px;\">"+
					"<div id=\"message_content\" style=\"  font-size: 14px; width: 300px; height: 210px; color: #1f336b; text-align: left; overflow: hidden;\">"+
					"<a  onClick=\"return(VL_FileDL(this));\"  exturl=\"http://www.kaixinbao.com/popopen\" vlpageid=\"popopen\"    href=\"http://"+list[5]+"\" target=\"_blank\"><img src=\""+sinosoft.staticPath+list[0]+"\" width=\"300px\" height=\"210px\" alt=\"\"></a></div> </div>" +
					"<a  onClick=\"return(VL_FileDL(this));\"  exturl=\"http://www.kaixinbao.com/popclose\" vlpageid=\"popclose\"  href=\"javascript:void(0);\"  style=\"display:none;\"  id=\"popupclosea\"/> </div>";
				    //将弹窗内容追加到页面内        
				    jQuery("body").append(t_window);
				    //广告关闭事件
				    jQuery("#message_closes").click(function(){
				        jQuery("#message_ad").hide();
				        //触发维析统计
				        jQuery("#popupclosea").click();
				        //保留6小时cookie时效
				        var cookietime = new Date();
				        cookietime.setTime(cookietime.getTime() + (60 * 60 * 1000*6));//coockie保存6小时
				        jQuery.cookie("popupcookie", "kaixinbaopopup",{expires:cookietime}); 
				    })
				    //收起按钮事件
				     jQuery("#sq_ad_btns").click(function(){
				        jQuery("#message_ad").animate({width: '0px'}, "slow");
				        jQuery("#sq_ad_btns_s").addClass("ad_btn_tt");
				    });
				     //展开按钮事件
				     jQuery("#sq_ad_btns_s").click(function(){
				             jQuery("#sq_ad_btns_s").removeClass("ad_btn_tt");
				             jQuery("#message_ad").animate({width: '300px'}, "slow");
				     })
				}
			}
		}
	});
	
	jQuery("#kinMaxShow,#change_33 ").append("<em style='position:absolute; display:block; width:30px; height:15px; text-align:center;  background: #969696;   border-radius: 2px; color:#fff; z-index:2; font-size:12px; bottom:0px; left:4px;'>广告</em>");
	
 });