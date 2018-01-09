/*
*@des:全站通用js
*@maker:guodongqi
*@time:2015-7-4
*/
// hover切换
 jQuery.fn.taghoverbind = function(opt) {  
 	var _opts ;
	return this.each(function() {
	
		_opts = jQuery.extend({
			tagid: this,
			addClass: "hover",
			showClass: ".tagshow"

		}, opt);
		
		_init();
		_monitor();	
		
	});
	function _init() {
		othis = jQuery(_opts.tagid);
	}
	
	function _monitor(){
	 	 othis.hover(function() {
	        jQuery(this).addClass(_opts.addClass);
	        jQuery(this).find(_opts.showClass).show();
   			 }, function() {
	      	jQuery(this).removeClass(_opts.addClass);
	        jQuery(this).find(_opts.showClass).hide();
    	});

 	};
}

// click切换
 jQuery.fn.tagclickbind = function(opt) {
 	var _opts ;

	return this.each(function() {
	
		_opts = jQuery.extend({
			tagid: this,
			addstyle: "hover",
			showClass: ".tagshow",
			con: "mod-con",
			mode: "div",
			nav: "mod-ul",
            callBack : function() {}
		}, opt);
		
		_init();
		_monitor();	
		
	});


	function _init() {
		othis = jQuery(_opts.tagid);
	}
	
	function _monitor(){

		 othis.click( function(){
		 		jQuery(this).addClass(_opts.addstyle).siblings().removeClass(_opts.addstyle);
		 		jQuery('#' + _opts.con + ' > ' + _opts.mode + ':eq(' + jQuery('#' + _opts.nav + ' li').index(jQuery(this)) + ')').show().siblings().hide();
                _opts.callBack();
		 })
	 
       
 	};
}

 ;(function ($) {
	  // 执行ajax的时候调用提示层
	   jQuery.ajaxLoadImg = { 
	    show: function (id) {
	    	setTimeout(function () { 
	  	      var loadingImg = jQuery("<img src='http://resource.kaixinbao.com/images/redesign/loading.gif' id='"+id+"' style='margin-left:-35px;float:left;vertical-align:top; position:fixed; left:50%; top:35%; z-index:99999;'/>");
	  	          loadingImg.prependTo('body');
	  	    }, 1);
	      },
	    
	    /** 隐藏元素 */
	    hide: function (id) {
	      jQuery("#"+id).remove();
	    }
	    }; 
	})(jQuery);
 
// 文本框选中交互

 jQuery.fn.inputfocusstyle = function(opt) {
 	var _opts ;

	return this.each(function() {
	
		_opts = jQuery.extend({
			tagid: this,
			addstyle: "z-fouse-bor"
		}, opt);
		
		_init();
		_monitor();	
		
	});


	function _init() {
		othis = jQuery(_opts.tagid);
	}
	
	function _monitor(){

			othis.focus(function(){
				jQuery(this).addClass(_opts.addstyle);
			});
			othis.focusout(function(){
				jQuery(this).removeClass(_opts.addstyle);
			});

       
 	};
}


// div模拟select

 jQuery.fn.divselect = function(opt) {
 	var _opts ,selectTit, selectUl ,selectA ,inputSelect;

	return this.each(function() {
	
		_opts = jQuery.extend({
			tagid: this,
			speed: "fast"

		}, opt);
		
		_init();
		_monitor();	
		
	});


	function _init() {
		othis = jQuery(_opts.tagid);
		selectTit = jQuery("cite" , othis);
		selectUl = jQuery("ul" , othis);
		selectA = jQuery("ul li a", othis);
		inputSelect = jQuery("inputselect", othis);
	}
	
	function _monitor(){
			selectTit.click(
					function(){
					if (jQuery(this).next("ul").css("display") == "none") {
							jQuery(this).next("ul").slideDown(_opts.speed);
						} else {
							jQuery(this).next("ul").slideUp(_opts.speed);
						}
					})
	  		selectA.click(function() {
				var txt = jQuery(this).text();
				jQuery(this).parents("ul").prev().html(txt);
				var value = jQuery(this).attr("selectid");
				jQuery(this).parents("ul").next().val(value);
				jQuery(this).parents("ul").hide();
 			});
}
}

// 客服侧边栏

 jQuery.fn.fixService = function(opt) {
 	 
 	var _opts,DOM,FiX,Mrigh,fix_right,Othis;

	return this.each(function() {
	
		_opts = jQuery.extend({
			fixid: this,
			fixWidth: "#fix_nav",
			ckDomc:".g-weaper",
			MRight:'10',
			fix_positon:'',
			direction:'right',
			maxposid:''
		}, opt);
		
		_init();
		_monitor();	
		_refunction();
	});


	function _init() {
		Othis = jQuery(_opts.fixid);
		Wid = jQuery(window);
		DOM = jQuery(_opts.ckDomc);
		FiX = jQuery(_opts.fixWidth);
		Max = jQuery(_opts.maxposid);
		
	}
	
	function _monitor(){
					
     			if(_opts.direction=="right"){
     				_opts.fix_positon = (Wid.width()-DOM.width())/2-Othis.width()-_opts.MRight;
  					if(_opts.fix_positon<0){ _opts.fix_positon = 0; }
						Othis.css({'right':_opts.fix_positon});
     			}else{
     					_opts.fix_positon = (Wid.width()-DOM.width())/2-Othis.width()-_opts.MRight;
     					if(_opts.fix_positon<Othis.width()){ _opts.fix_positon = 0; }
     					Othis.css({'left':_opts.fix_positon });		
     			}
     			
     			function _MaxPosTop(){
					    var nWinTop = Wid.scrollTop();
	     			var maxPosTop = Max.offset().top+Max.height();
	     			   if (nWinTop <= Max.offset().top) {
	               	  		Othis.css({
			                    position: 'absolute',
			                    top: 'auto',
			                    top: maxPosTop
			                })
			            } else {
			                Othis.css({
			                    position: 'fixed',
			                    top: '40%'
			                })
			            }
			}
     			if(_opts.maxposid!=""){
						 Wid.bind('scroll', function () {
				     			_MaxPosTop();
				     	})
     			}

		}
		// 改变窗口时重新定位
	function _refunction(){
     jQuery(window).resize( function() { _monitor();});
	}

}


  jQuery("#gotop").click(function() {

        jQuery("body,html").animate({
            scrollTop:0
        }, 500);

    });


//搜索按钮点击交互
	jQuery(".f-search-i").click(function(){
				jQuery(this).hide();
				jQuery(".m-search-box").animate({  opacity: 'show'
					 }, 300);
				
	})
	jQuery(".m-search-input").blur(function(){
				jQuery(".m-search-box").animate({  opacity: 'hide'
					 },300,function() {
   				jQuery(".f-search-i").show();
 			 }	);
		
	})
 

//百度统计变量声明
var _hmt = _hmt || [];
//add by wangej 20151116 百年大数据统计
var _paq = _paq || [];
(function() {
    //百度统计
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?2d7e032a5f8d4e609feb9e22c0cb83f8";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);

  
        //weixi 产品详细页不加载
        if("undefined"!= typeof productDetailFlag && productDetailFlag!=null){
            if(productDetailFlag!="true"){
                 document.write('<div id="Weixi" style="display:none;"><script type="text/javascript" src="' + sinosoft.jsPath + '/weixitrack.js"></script></div>')
            }
        }else{
               document.write('<div id="Weixi" style="display:none;"><script type="text/javascript" src="' + sinosoft.jsPath + '/weixitrack.js"></script></div>')
        }
   
    
})();

/*展示广告*/
jQuery(document).ready(function(){
    var popcookie=jQuery.cookie("popupcookie");
    if(popcookie!=null&&popcookie!=""){
        return;
    }
    var topUrl = window.location.pathname;
    jQuery.ajax({
        url: sinosoft.base+"/shop/shopping_cart!showTopAd.action?callback=?&topUrl="+topUrl,
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
                
            	str = "<div style=\"background: url(" + sinosoft.staticPath + list[0] + ") no-repeat center  center; height:" + list[2] + "px;\">";
            	if (list[5]) {
            		str = str + "<a href=\"http://"+list[5]+"\" target=\""+list[4]+"\" style=\"width:100%; display:block; height:" + list[2] + "px;\" alt=\""+list[3]+"\"></a></div>";
            	} else {
            		str = str + "</div>";
            	}
                jQuery(".g-top").after(str);
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
    jQuery("#kinMaxShow,#change_33,.g-Home-banner").append("<em style='position:absolute; display:block; width:30px; height:15px; text-align:center;  background: #969696;   border-radius: 2px; color:#fff; z-index:2; font-size:12px; bottom:0px; left:4px;'>广告</em>");
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
	
	var channel_sn = jQuery.cookie('channelSn');
	if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
		channelsn = channel_sn;
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
			var initPrem = "¥"+mPrice[1];
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
				if(mPrice[9]=="discount"){
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").html(initPrem);
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").show();
					jQuery("#Clear_li_Ajax_Prict_"+index).show();
				}
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
							 jQuery("#maxIntegralPrice_" + index).html("<span>积分最多可抵<em>"+mPrice[4]+"</em>元</span>");
						 }else{
							 jQuery("#maxIntegralPrice_" + index).html("<span>您有积分<em>"+mPrice[8]+"</em> 本次可抵<em>"+mPrice[4]+"</em>元</span>");
						 }
					 }
				 }
			}
		});
	}
	});
});

jQuery(document).ready(function() {
	jQuery(".que_dl").taghoverbind();
	jQuery("#fix_nav span").taghoverbind();
	jQuery(".m-service").taghoverbind({showClass:".m-service_con"});
	jQuery(".m-WeChat").taghoverbind({showClass:".m-WeChatImg"});
	jQuery(".m-WeChat a").taghoverbind({showClass:'.m-WeChatImg'});
	jQuery(".g-screen-tagul li").tagclickbind();
	jQuery(".m-travel-txt").inputfocusstyle();
	jQuery(".m-select_tag span").tagclickbind({addstyle:'select'});
	jQuery("#m-hotCityTit li").tagclickbind({con:'m-hotCity',addstyle:'select',mode:'p',nav:'m-hotCityTit'});
	jQuery("#fix_nav").fixService();
	jQuery(".g-nav").taghoverbind({showClass:"#g-nav-box",addClass:"nostyle"});
	
	jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
		try{
			NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
		} catch (e) {
		}
	});
}); 

function wxsend(source) {
	if (source != null && source !='') {
		if ((typeof(jQuery('#m-user-no')) != 'undefined' && jQuery("#m-user-no").css("display") != 'none') || (typeof(jQuery('#m-cart-nologin')) != 'undefined' && jQuery("#m-cart-nologin").css("display") != 'none')) {
			VL_Send(source,'','');
		}
	}
}

//理财险新增
/** 
 * @des: 点击发送验证码倒计时
 * @maker: 宋再冉
 * @date: 2016.6.7
 */
var timeCountDown = function(opt) {
  
  this._opt = $.extend({
    wrap: '.code_again',
    elem: '.code_again em',
    deadLine: 60, // 单位为s
    startClass: 'count_down',
    startHtml: '<em>60</em>秒后重发',
    endHtml: '点击验证',
    rebHTML: '',
    whichPage: '',
    sendCode: function(){},
    callBack: function(){}
  }, opt);

  this.setInit();
  this.addEvent();
  
}
timeCountDown.prototype.setInit = function() {
  
  this.wrap = $(this._opt.wrap);
  this.elem = $(this._opt.elem);
  this.startClass = this._opt.startClass;
  this.startHtml = this._opt.startHtml;
  this.endHtml = this._opt.endHtml;
  this.rebHTML = this._opt.rebHTML;
  this.deadLine = this._opt.deadLine;
  this.isCounting = this.wrap.hasClass(this.startClass);
  this.sendCode = this._opt.sendCode;
  this.callBack = this._opt.callBack;
  
}
timeCountDown.prototype.addEvent = function() {
  
  var _this = this;

  if(_this.isCounting){
    _this.start();
  } else {
    switch(_this._opt.whichPage) {
      // 登录注册_注册_02：倒计时结束后改为‘发送验证码’
      case 'sign':
        _this.wrap.removeClass(_this.startClass)
           .html(_this.endHtml)
           .bind("click", function(e) {
              
              e.preventDefault();

              // 手机号校验不合适时，无法发送验证码
              $('.phone_wrap input').blur();
              if($('.phone_wrap').length = 0 || !$('.phone_wrap').find('.error').length > 0){
                $(this).html(_this.startHtml);
                clearInterval(_this.timer);
                _this.sendCode();
                _this.start();
              }
           });
        break;
      case 'yzm':
    	  _this.wrap.unbind("click");
          _this.wrap.removeClass(_this.startClass)
             .html(_this.endHtml)
             .bind("click", function(e) {
            	 
                e.preventDefault();
  
                // 图片验证码未输入时，无法发送验证码
                $('.yzmDiv .checkYzm').blur();
               
                if($('.yzmDiv').length = 0 || !$('.yzmDiv').find('.error').length > 0){
                  $(this).html(_this.startHtml);
                  clearInterval(_this.timer);
                  _this.sendCode();
                  _this.start();
                }
             });
          break;
        
    };
  }
  
}
timeCountDown.prototype.start = function(){
  
  var _this = this;

  _this.deadLine = this._opt.deadLine;
  _this.elem = $(this._opt.elem);
  _this.wrap.addClass(_this.startClass).unbind("click");
  _this.timer = setInterval(function(){
  _this.elem.html(--_this.deadLine);

    if(_this.deadLine <= 0) {

      clearInterval(_this.timer);
      _this.isCounting = false;
      _this.addEvent();
      if(_this.rebHTML!=""){
    	  _this.wrap.html(_this.rebHTML);
  	  }
      _this.callBack();
    }
  }, 1000);
  
}