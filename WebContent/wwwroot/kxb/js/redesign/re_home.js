/*
*@des:首页调用js
*@maker:guodongqi
*@time:2015-7-4
*/
function allowScreen() {
    var SCREENWIDTH_ALLOW = 1250;
    var clientWidth = document.body.clientWidth;
    if (clientWidth <= SCREENWIDTH_ALLOW) {
        document.getElementById('max_style').className = 'x1200';
      //document.getElementById('controlCss').href = sinosoft.cssPath + '/re_main980.css'
        document.getElementById('controlCss').href ='../../style/redesign/re_main980.css'
    } else {
        document.getElementById('max_style').className = '';
        document.getElementById('controlCss').href = ''
    }
}


jQuery(document).ready(function() {
  jQuery(".divselect").divselect();
  jQuery("#f-friend li").tagclickbind({con:'f-linkCon',addstyle:'select',nav:'f-friend'});
  jQuery("#f-hot-mod li").tagclickbind({con:'f-hotCon',addstyle:'select',nav:'f-hot-mod'});
  jQuery("#g-strategyUl li").tagclickbind({con:'g-strategyboxBox',addstyle:'select',nav:'g-strategyUl'});
  jQuery("#f-tagul1 li").tagclickbind({con:'f-incon1',addstyle:'select',nav:'f-tagul1',callBack:function(){
     jQuery("img.m-in-img").lazyload();  //图片延迟加载
  }
  });
  jQuery("#f-tagul2 li").tagclickbind({con:'f-incon2',addstyle:'select',nav:'f-tagul2',callBack:function(){
     jQuery("img.m-in-img").lazyload();  //图片延迟加载
  }
   });
  jQuery("#f-tagul3 li").tagclickbind({con:'f-incon3',addstyle:'select',nav:'f-tagul3',callBack:function(){
     jQuery("img.m-in-img").lazyload();  //图片延迟加载
  }
   });
  
  jQuery("img.lazy").lazyload({ threshold :300});  //图片延迟加载
  jQuery("img.m-in-img").lazyload({ threshold :200});  //图片延迟加载



 
  var seltag = jQuery.cookie('codetags');  //判断cookie标记
  if(seltag!="true"){
      var yheight =  jQuery(this).scrollTop(); //滚动条距顶端的距离
        if(yheight<300){
            jQuery("#fix_wechar").hide();
        }else{
            jQuery("#fix_wechar").show();
        }
  }
  
  jQuery(".fix_closed").click(function(){ //关闭二维码
    jQuery(this).parent().hide();
    jQuery.cookie('codetags', 'true'); 
  });

  setTimeout(function() { imgProScroll(); }); //理赔案件：宽窄拼轮播

    var mySwiper = new Swiper('.swiper-container', {//首页轮播加载
            autoplay: 6000,
            pagination: '.pagination',
            paginationClickable: true,
            autoplayDisableOnInteraction : false,
            moveStartThreshold: 100,
            speed:500,
            loop : true,
            autoplay : 5000,
            onSlideChangeStart:function(swiper){
                var index  = mySwiper.activeIndex;
                if(index!=0){
                        var pageImg =jQuery('.g-Home-banner .swiper-slide').eq(index).find(".lazy");
                        var imgLength = pageImg.length;
                        for (var i = 0; i < imgLength; i++) {
                           var imgSrc = pageImg.eq(i).attr('data-original');
                           var imgSrcy = pageImg.eq(i).attr('src');
                           if(imgSrcy!=imgSrc){
                            pageImg.eq(i).attr('src',imgSrc);
                           }
                        }
                }
            }
    })
      
      jQuery("#Age_A").val("");
      jQuery("#g-screen-ctiy").val("");
      jQuery("#Period_A").val("");
      jQuery("#Age_B").val("");
      jQuery("#Features_B").val("");
      jQuery("#Prem_B").val("");
      jQuery("#Age_D").val("");
      jQuery("#Features_D").val("");
      indexLoad();
});

/** 
 * @des: 开心保首页改版：猜你计划换一换
 * @maker: Songzairan
 * @date: 2015.7.7
 */
var changePlanSort = function() {
  
	this.sortArea = jQuery(".g-Guess-ul");
	this.btnElm = jQuery(".g-guess-fun");
	this.setInit();  //设置初始值 
	this.addEvent(); //绑定事件
  
}
changePlanSort.prototype.setInit = function() {
  
	this.sortElm = this.sortArea.children();
	this.sortElm.hide().slice(0, 6).show();  //默认显示长度6 
  
}
changePlanSort.prototype.addEvent = function() {
	
	var _this = this;
	var _len = this.sortElm.length;
	var isFadeIn = false;
	
	this.btnElm.click(function() {
		
		if(_len <= 6 || isFadeIn) return;
			
		isFadeIn = true;
		
		var _arrElms = _this.sortElm.slice(0, 6).hide().clone();
		_this.sortArea.append(_arrElms);
		
		setTimeout(function() {	
			_this.sortArea.children().slice(6, 12).fadeIn(function() {
				isFadeIn = false;
			});
			_this.sortArea.children().slice(0, 6).remove();
			_this.sortElm = _this.sortArea.children();
			
		});
		
	});
	
}


 /** 
  * @des: 开心保首页改版：理赔案例图片轮播
  * @maker: Songzairan
  * @date: 2015.7.8
  */
var imgProScroll = function() {

	var arrSwiper = [];
	var elms = jQuery(".f-tagul4").find("span");
	elms.each(function(i, v) {
		arrSwiper.push(jQuery(v).text());
	});
	
	var mySwiper = new Swiper(".f-swiper", {
		autoplay: 3000,
		speed: 500,
		loop:true,
		pagination: ".f-tagul4",
		paginationClickable: true,
		onSwiperCreated: function(swiper) {
			jQuery(".f-tagul4").find("span").each(function(i, v) {
				jQuery(v).text(arrSwiper[i]);
			});
		},
		onSlideChangeStart: function() {
             var index  = mySwiper.activeIndex;

                if(index!=0){
                        var pageImg =jQuery('.f-swiper .swiper-slide').eq(index).find(".lazy");
                        var imgLength = pageImg.length;
                        for (var i = 0; i < imgLength; i++) {
                           var imgSrc = pageImg.eq(i).attr('data-original');
                           var imgSrcy = pageImg.eq(i).attr('src');
                           if(imgSrcy!=imgSrc){
                            pageImg.eq(i).attr('src',imgSrc);
                           }
                        }
                }
                
			mySwiper.stopAutoplay();
		},
		onSlideChangeEnd: function() {
			mySwiper.startAutoplay();
		}
	});
	
	//解决IE7,8,9轮播效果过快问题
	if(navigator.appName == "Microsoft Internet Explorer" && (navigator.appVersion.match(/7./i) == "7." || navigator.appVersion.match(/8./i) == "8." || navigator.appVersion.match(/9./i) == "9.")) {
		mySwiper.params.speed = 4000;
	} else {
		mySwiper.params.speed = 500;
	}

}

/**
 * 首页一键筛选
 */
function quicksearch(){
	var param = getSearchParam();
	window.open (sinosoft.base+"/Search/newlist.jsp?1=1"+param , "_blank");
}


function getSearchParam(){
	
	//保险类别
	var productType = jQuery("#mod-ul").find(".hover").find("span").attr("id");
	
	//组装参数   参数格式： 投保年龄|旅行国家|停留天数|保障类型|保险金额
	
	var tparam = "";
	var age="";
    var travelAddress = "";
    var period = "";
    var features = "";
    var prem = "";
	if(productType=="A00"){
		age = jQuery("#Age_A").val();
		if(age == null ||"undefined" == typeof (age) || age=="undefined"){
			age = "";
		}
		var travelAddress = jQuery("#g-screen-ctiy").val();
		if(travelAddress==null || "undefined" == typeof (travelAddress) || travelAddress=="undefined"){
			travelAddress = "";
		} else {
			travelAddress=encodeURIComponent(travelAddress);
		}
		var period = jQuery("#Period_A").val();
		if(period=="" || "undefined" == typeof (period) || period=="undefined"){
			period = "";
		}
	}else if(productType=="B00"){
		age = jQuery("#Age_B").val();
		if(age == null ||"undefined" == typeof (age) || age=="undefined"){
			age = "";
		}
		features = jQuery("#Features_B").val();
		if(features == null || "undefined" == typeof (features) || features=="undefined"){
			features = "";
		}
		prem = jQuery("#Prem_B").val();
		if(prem == null || "undefined" == typeof (prem) || prem=="undefined"){
			prem = "";
		}
	}else if(productType=="D00"){
		age = jQuery("#Age_D").val();
		if(age == null || "undefined" == typeof (age) || age=="undefined"){
			age = "";
		}
		features = jQuery("#Features_D").val();
		if(features == null || "undefined" == typeof (features) || features=="undefined"){
			features = "";
		}
	}
	
	var param = "&productType="+productType+"&age="+age+"&travelAddress="+travelAddress+"&period="+period+"&features="+features+"&prem="+prem;
	
	return param;
	
}


/**
 * 首页产品产品销量、评论
 */
function indexLoad() {
	
	
	var ulist=jQuery('i[id^=SalesI_]');
	var uid = new Array(ulist.length);
	var uidStr="";
	for (var i = 0; i < ulist.length; i++) {
		uid[i] = ulist[i].id;
        uidStr+= ulist[i].id;
	}
	if(uidStr==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!search.action?productIds='+uid,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				jQuery("."+index).html(object+"人");
			});
		}
	});
	
	var ulist=jQuery('a[id^=CommentI_]');
	var uid = new Array(ulist.length);
	var uidStr="";
	for (var i = 0; i < ulist.length; i++) {
		uid[i] = ulist[i].id;
        uidStr+= ulist[i].id;
	}
	if(uidStr==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!comment.action?productIds='+uid,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				jQuery("#"+index).html(object+"人");
			});
		}
	});
}

jQuery().ready( function() {
	// 快速筛选模块交互变化
	   function n(s) {
	    if (s.val() != "") {
	        s.prev().hide()
	    } else {
	        s.prev().show()
	    } 
	}

	 var city = jQuery("#g-screen-ctiy");


	city.focus(function(){
	     var t = jQuery(this);
	     t.closest(".trealy_input").addClass("city-focus");
	     n(t);
	});


	city.blur(function(){
	   var t = $(this);
	   t.closest(".trealy_input").removeClass("city-focus");
	   n(t);
	});

	city.keyup(function(){
	   var t = $(this);
	   n(t);
	});
})

