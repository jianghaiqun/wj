jQuery('.colse_sc').click(function () {  jQuery(".goto_jifen").hide(); 
    jQuery.cookie("pointsChange", "" , { path: '/' });
});

//头部会员等级显示
jQuery("#headerLoginMemGradeIcon").hover(function(){
    jQuery(this).find(".vip_tipsf").show();
},function(){
	jQuery(this).find(".vip_tipsf").delay(4000).hide(0);
});

jQuery(".vip_k").hover(function(){
    jQuery(this).children(".vip_tipsf").show();
},function(){
    jQuery(this).children(".vip_tipsf").hide();
});

function linkMemDesc() {
	location.href=sinosoft.front+'/about/xszn/hysm/index.shtml';
}

String.prototype.trim=function() {
    return this.replace(/(^\s*)|(\s*$)/g,'');
}
jQuery(document).ready(function() { 
	
	if(jQuery('span[name^=Ajax_Prict_]').length <= 0){
		return;
	}
	var list = jQuery('span[name^=Ajax_Prict_],span[name^=R_Ajax_Prict_]');
	
	var productIDArray = new Array(list.length);
	for (var i = 0; i < list.length; i++) {
		productIDArray[i] = jQuery(list[i]).attr("name").replace("R_","");
	}
	
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/ajax_price!ajaxPrice.action?&callback=?&channelSn='+channelsn+'&ProductIDS='+productIDArray,
		dataType: "json",
		async: false,
		success: function(data) {
		
		jQuery.each(data, function(index, price) {
			var mPrice = price.split("_");
			var tprice = "<em>￥</em>"+mPrice[0];
			var initPrem = "<em>￥</em>"+mPrice[1];
			var ttLen = mPrice.length;
			var disrate = "dis";
			if(ttLen>=4){
				disrate = mPrice[3];
			}
			//列表页推荐产品-无“￥”
			jQuery("span[name=R_Ajax_Prict_"+index+"]").html(mPrice[0]);
			
			jQuery("span[name=Ajax_Prict_"+index+"]").html(tprice);
			
			if(jQuery("#txt_price").length > 0){
				var v_riskCode = jQuery("#RiskCode").val();
				if(v_riskCode == index){
					jQuery("#txt_price").html("<em>￥</em>"+mPrice[0]);
				}
			} 
			if(initPrem && parseFloat(jQuery.trim(mPrice[0])) >= parseFloat(jQuery.trim(mPrice[1]))){
				if(disrate=="dis"){
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").html("");
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").hide();
					jQuery("#Clear_li_Ajax_Prict_"+index).hide();
				}
			}else{
				jQuery("span[name=Clear_Ajax_Prict_"+index+"]").html(initPrem);
				jQuery("span[name=Clear_Ajax_Prict_"+index+"]").show();
				jQuery("#Clear_li_Ajax_Prict_"+index).show();
			}
			if(jQuery("#productIntegral_"+index).length > 0){
				 if(mPrice[2] < 1){
					 jQuery("#productIntegral_" + index).html("0");
				 }else{
					 if(ttLen>=8&&'true'==mPrice[6]&&mPrice[7]!=''){
						 jQuery("#pointdes").html(mPrice[7]);
					 }else{
						 jQuery("#productIntegral_" + index).html(Math.floor(mPrice[2])+" ");
					 }
					 if(ttLen>=6){
						  if("true"!=mPrice[5]){
							  jQuery("#integer_login").show();
						  }
					}
				 }
				 if(mPrice.length > 4){
					 if(parseFloat(mPrice[4])!=0){
						 if("true"!=mPrice[5]){
							 jQuery("#maxIntegralPrice_" + index).html("<i class=\"jf_icons\">&nbsp;</i><span>积分最多可抵<em>¥ "+mPrice[4]+"</em></span>");
						 }else{
							 jQuery("#maxIntegralPrice_" + index).html("<i class=\"jf_icons\">&nbsp;</i><span>您有积分<em>"+mPrice[8]+"</em> 本次可抵<em>¥ "+mPrice[4]+"</em></span>");
						 }
					 }
				 }
			}
		});
	}
	});
	
	
    jQuery("#kinMaxShow,#change_33 ").append("<em style='position:absolute; display:block; width:30px; height:15px; text-align:center;  background: #969696;   border-radius: 2px; color:#fff; z-index:2; font-size:12px; bottom:0px; left:4px;'>广告</em>");

});

var classificArea = document.getElementById("ClassificationArea");

var doautoChange = null != classificArea && "undefined" != typeof classificArea;

// 左右浮动
function changeFluctuate() {
    var width_used = jQuery(window).width();
    jQuery("#servfloat").width(62);
    var isIE = !!window.ActiveXObject;
    var isIE6 = isIE && !window.XMLHttpRequest;
    var ua = navigator.userAgent.toLowerCase();
    if (width_used >= 1144) {
        document.getElementById("ClassificationArea").style.marginLeft = (width_used - 980) / 2 - 82 + "px";
        if (isIE && isIE6) {
            document.getElementById("servfloat").style.marginRight = (width_used - 980) / 2 - 80 + "px";
            jQuery("#servfloat").width(80);
        } else if (ua.indexOf("chrome") > 0) {
            document.getElementById("ClassificationArea").style.marginLeft = (width_used - 980) / 2 - 88 + "px";
            document.getElementById("servfloat").style.marginRight = (width_used - 980) / 2 - 11 + "px";
        } else {
            document.getElementById("servfloat").style.marginRight = (width_used - 980) / 2 - 11 + "px";
        }
    } else {
        document.getElementById("ClassificationArea").style.marginLeft = "0px";
        if (isIE6) {
            document.getElementById("servfloat").style.marginRight = "0px";
        } else {
            document.getElementById("servfloat").style.marginRight = "18px";
        }
    }
}

if (doautoChange) {
    changeFluctuate();
}

function getWinSize() {
    var winHeight = window.innerHeight, winWidth = window.innerWidth;
    if (document.documentElement.clientHeight) {
        winHeight = document.documentElement.clientHeight;
        winWidth = document.documentElement.clientWidth;
    } else {
        winHeight = document.body.clientHeight;
        winWidth = document.body.clientWidth;
    }
    var height = winHeight - 50;
    var width = winWidth - 100;
}

if (doautoChange) {
    getWinSize();
    // 改变浏览器大小
    window.onresize = doonresize;
}

function doonresize() {
    getWinSize();
    changeFluctuate();
}

//获取字符串的字节长度
function getByteLength(str) {
    if (null == str) {
        return 4;
    }
    if ("undefined" == typeof str) {
        return 9;
    }
    var len = str.length;
    var byteLen = 0;
    for (var i = 0; i < len; i++) {
        if (str.charCodeAt(i) > 255) {
            byteLen += 2;
        } else {
            byteLen += 1;
        }
    }
    return byteLen;
}

//右侧字节截取替换
function rPadByte(str, len, padChar) {
    if (null == str || "undefined" == typeof str) {
        return str;
    }
    var preSublen = 0;
    var substr = "";
    var strlen = str.length;
    for (var i = 0; i < strlen; i++) {
        substr = str.substring(0, i + 1);
        var sublen = getByteLength(substr);
        if (sublen > len) {
            substr = str.substring(0, i);
            break;
        }
        preSublen = sublen;
    }
    if ("" == padChar || "undefined" == typeof padChar) {
        return substr;
    }
    for (var i = 0; i < len - preSublen; i++) {
        substr += padChar;
    }
    return substr;
}

//左侧字节截取替换
function lPadByte(str, len, padChar) {
    if (null == str || "undefined" == typeof str) {
        return str;
    }
    var preSublen = 0;
    var substr = "";
    var strlen = str.length;
    for (var i = 0; i < strlen; i++) {
        substr = str.substring(strlen - i, strlen);
        var sublen = getByteLength(substr);
        if (sublen > len) {
            substr = str.substring(strlen - i + 1, strlen);
            break;
        }
        preSublen = sublen;
    }
    if ("" == padChar || "undefined" == typeof padChar) {
        return substr;
    }
    for (var i = 0; i < len - preSublen; i++) {
        substr = padChar + substr;
    }
    return substr;
}


//百度统计变量声明
(function() {
    //百度统计
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?2d7e032a5f8d4e609feb9e22c0cb83f8";
    var s = document.getElementsByTagName("script")[0]; 
    s.parentNode.insertBefore(hm, s);
    
//    var _bdhmProtocol = "https:" == document.location.protocol ? " https://" :" http://";
//    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F2d7e032a5f8d4e609feb9e22c0cb83f8' type='text/javascript'%3E%3C/script%3E"));
    //weixi 产品详细页不加载
    if("undefined"!= typeof productDetailFlag && productDetailFlag!=null){
        if(productDetailFlag!="true"){
            document.write("<div id=\"Weixi\" style=\"display:none;\"><script type=\"text/javascript\" src=\"" + sinosoft.jsPath + "/weixitrack.js\"></sc" + "ript></div>");
        } 
    }else{
        document.write("<div id=\"Weixi\" style=\"display:none;\"><script type=\"text/javascript\" src=\"" + sinosoft.jsPath + "/weixitrack.js\"></sc" + "ript></div>");
    }
    
})();

/*jQuery("#callus").click(function() {
    _gaq.push([ "_trackEvent", "char", "xiaoneng" ]);
});

jQuery("#callus1").click(function() {
    _gaq.push([ "_trackEvent", "char", "xiaoneng" ]);
});

jQuery("#callus2").click(function() {
    _gaq.push([ "_trackEvent", "char", "xiaoneng" ]);
});*/

//当浏览器的宽度小于1000时，右侧边栏的悬浮框自动隐藏
function getId(obj) {
    return document.getElementById(obj);
}

function decideHide() {
    if (document.body.clientWidth < 1e3) {
        if (getId("servfloat") != null) {
            getId("servfloat").style.visibility = "hidden";
        }
    } else {
        if (getId("servfloat") != null) {
            getId("servfloat").style.visibility = "visible";
        }
    }
}

jQuery(document).ready(function() {
    decideHide();
});

jQuery(window).resize(function() {
    decideHide();
});

/*返回顶部*/
function a(x, y) {
	if (jQuery(".wrapper").offset() != null) {
	    l = jQuery(".wrapper").offset().left;
	    w = jQuery(".wrapper").width();
	    jQuery("#tbox").css("left", l + w + 10 + "px");
	    jQuery("#tbox").css("bottom", y + "px");
	}
}

function b() {
    h = jQuery(window).height();
    t = jQuery(document).scrollTop();
    if (t > h) {
        jQuery("#gotop").fadeIn("slow");
    } else {
        jQuery("#gotop").fadeOut("slow");
    }
}

jQuery(document).ready(function(e) {
    // a(10, 10);
    //#tbox的div距浏览器底部和页面内容区域右侧的距离
    // b();
    jQuery("#gotop").click(function() {
        jQuery("body,html").animate({
            scrollTop:0
        }, 500);
    });
    jQuery(".weixin").hover(function(){
        jQuery(".weixinDiv").fadeIn();
	},function(){
	        jQuery(".weixinDiv").fadeOut();
	})
});

jQuery(window).resize(function() {
    //a(10, 10);
});

jQuery(window).scroll(function(e) {
    //b();
});

jQuery(function() {
    /*主导航下拉*/
    jQuery(".nav_main li").hover(function() {
        jQuery(this).addClass("hovers_c");
        jQuery(this).children("ul li").attr("class", "");
    }, function() {
        jQuery(this).removeClass("hovers_c");
        jQuery(this).children("ul li").attr("class", "");
    });
    jQuery("#topnav_link li").hover(function() {
        jQuery(this).children("#wz_nav").addClass("q_wzdh").end();
        jQuery(this).children("#topNavDropMenu").show().end();
        jQuery(this).children(".q_link_cord").show().end;
    }, function() {
        jQuery(this).children("#wz_nav").removeClass("q_wzdh").end();
        jQuery(this).children("#topNavDropMenu").hide();
        jQuery(this).children(".q_link_cord").hide();
    });
});

//if(typeof(BizQQWPA)!='undefined'){
//	// 在线客服 
//	BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//}
jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
	try {
		NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
	} catch (e) {
	}
});
jQuery.ajax({
    url: sinosoft.base+"/shop/shopping_cart!getShopCartNo.action",
    async:true,
    type:"get", 
    success: function(data) {
         jQuery('#shopCartNo').html(data);
        }
    });
/*首页活动
jQuery(document).ready(function() {
    jQuery(".min_nav").after('<div class="wrapper" style=" height:60px; background:#F62525;"><a href="http://www.kaixinbao.com/yxhd/znq/" target="_blank" ><img src="http://www.kaixinbao.com/images/znq_ad.gif" width="980" height="60"/></a></div>');
    jQuery(".ste_nav").before('<div class="wrapper" style=" height:60px; background:#F62525;"><a href="http://www.kaixinbao.com/yxhd/znq/" target="_blank" ><img src="http://www.kaixinbao.com/images/znq_ad.gif" width="980" height="60"/></a></div>');
});
*/
/*jQuery(".min_nav").after("<div style='background:#062155;'  height='45px'><a href='www.kaixinbao.com/yxhd/shijiebei/' target='_blank' ><img  style='display:block; margin:0 auto;'  src='www.kaixinbao.com/images/sf.gif' width='1190' height='45'/></a></div>" );*/

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
                //    str = "<style>.main_nav{border-top:none;}</style><div><a href=\"http://"+list[5]+"\" target=\""+list[4]+"\"><img width=\""+list[1]+"\" height=\""+list[2]+"\" src=\""+sinosoft.staticPath+list[0]+"\" style=\"display:block; margin:0 auto;\" alt=\""+list[3]+"\"></a></div>";
                //}
                str = "<div style=\"background: url(" + sinosoft.staticPath + list[0] + ") no-repeat center  center; height:" + list[2] + "px;\">";
            	if (list[5]) {
            		str = str + "<a href=\"http://"+list[5]+"\" target=\""+list[4]+"\" style=\"width:100%; display:block; height:" + list[2] + "px;\" alt=\""+list[3]+"\"></a></div>";
            	} else {
            		str = str + "</div>";
            	}
                jQuery(".min_nav").after(str);
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
 });
/**
 * 判断是否动态显示广告
 */
if(jQuery("div[id='ajaxad']").find("p:eq(1)")){
	jQuery("div[id='ajaxad']").find("p:eq(1)").each(function(){
		//str = '<a href="http://www.kaixinbao.com"><img width=214 height=248 hspace=10  align="left" border="0" 	src="http://images.hzins.com/short/www/special/ad/zjbxdg.jpg" style=\"display:block; margin:0 auto;\" alt="开心保"></a>';
		jQuery.getJSON( 
			sinosoft.base+"/shop/ajax_ad!getAdContent.action?articleID="+jQuery("#ArticleID").val()+"&callback=?",
			function(data) { 
				var obj = data.content;
				var twidth = data.width;
				var theight = data.height;
				if(twidth==null ||twidth=="" || typeof(twidth)=="undefined"){
					twidth='214';
				}
				if(theight==null ||theight=="" || typeof(theight)=="undefined"){
					theight='268';
				}
				var AD = eval('('+obj+')');
				var str="";
			    str += "<a href='"+AD.Images[0].imgADLinkUrl+"' target='"+((AD.imgADLinkTarget == "Old") ? "_self" : "_blank") + "'>";
				str += "<img title='"+AD.Images[0].imgADAlt+"' alt='"+AD.Images[0].imgADAlt+"' width='"+twidth+"' height='"+theight+"' style='margin-top:20px'  vspace='5' hspace='10' align='left' src='"+sinosoft.staticPath+AD.Images[0].ImgPath+"' style='border:0px;'>";
				str += "</a>";
			    jQuery("div[id='ajaxad']").find("p:eq(1)").before(str);
		   });
	}) 
}