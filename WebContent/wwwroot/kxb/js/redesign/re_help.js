/* 理赔频道 */
function claimsFn(){
	var dialogTop = 1210;

	jQuery(".pay-notice ol li").bind("click", function(){
		jQuery(this).removeClass("on").addClass("on")
			   .siblings().removeClass("on");
	});

	jQuery(".pay-notice ul li").hover(function(){
		var $this = jQuery(this);
		$this.toggleClass("on");
		if($this.hasClass("on")){
			var $dialog = $this.find(".dialog");
			var wTop = jQuery(window).scrollTop();
			if(wTop > dialogTop){
				$dialog.addClass("behind");
			}
			else{
				$dialog.removeClass("behind");
			}
		}
	},function(){
		jQuery(this).toggleClass("on");
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
		autoplay: 6000,
		speed: 500,
		loop:true,
		pagination: ".f-tagul4",
		paginationClickable: true,
		autoplayDisableOnInteraction : false,
		onSwiperCreated: function(swiper) {
			jQuery(".f-tagul4").find("span").each(function(i, v) {
				jQuery(v).text(arrSwiper[i]);
			});
		}
	});
	
	jQuery(".f-swiper").hover(function() {
		mySwiper.stopAutoplay();
	}, function() {
		mySwiper.startAutoplay();
	});
	
	//解决IE7,8,9轮播效果过快问题
	if(navigator.appName == "Microsoft Internet Explorer" && (navigator.appVersion.match(/7./i) == "7." || navigator.appVersion.match(/8./i) == "8." || navigator.appVersion.match(/9./i) == "9.")) {
		mySwiper.params.speed = 4000;
	} else {
		mySwiper.params.speed = 500;
	}

}

// 理赔频道首页理赔提示
var winTip = function(opt) {
    this._opt = $.extend({
        winid: '.new_mod_win',
        closebtn:'.win_closed',
        otherdom:'.new_template',
        addstyle:'new_t_bol',
        callBack: function(){}
      }, opt);

    this.setInit();
    this.addEvent();

}
winTip.prototype.setInit = function() {

     this.winid = $(this._opt.winid);
     this.closebtn = $(this._opt.closebtn);
     this.otherdom = $(this._opt.otherdom);
     this.addstyle = this._opt.addstyle;
     this.callBack = this._opt.callBack;

}
winTip.prototype.addEvent =function(){

     var _this = this;
     _this.winid.fadeIn(500);
     _this.otherdom.addClass(_this.addstyle);
     _this.closebtn.click(function(){
     _this.winid.fadeOut(500);
    _this.otherdom.removeClass(_this.addstyle);
    })

}
jQuery(document).ready(function($) {
      jQuery(".clamislp_dl dt").click(function(event) {
        if(jQuery(this).siblings("dd").is(':visible')){
            jQuery(this).siblings("dd").slideUp("fast");
            jQuery(this).removeClass('hide_dt');
        }else{
            jQuery(this).siblings("dd").slideDown("fast");
            jQuery(this).addClass('hide_dt');
        }
      });
      jQuery(".clamis_ul_table li:odd").css("backgroundColor","#ffefe4");

      // 依赖footer.js
      jQuery(".modClamisUl .modClamisLi").tagclickbind({showClass:".ClamisConbox",con:"ClamisUlcon",nav:"modclamisul"});
       jQuery("#fix_nav_f").fixService({direction:'left'});



});