/*
#@des:促销倒计时
*@maker:guodongqi
*@time:2015-7-1
*/
(function($){
$.fn.yomi=function(option){
	var data="";
	var _DOM=null;
	var TIMER;
	
	createdom =function(dom){
		_DOM=dom;
		data=$(dom).attr("data");
		data = data.replace(/-/g,"/");
		data = Math.round((new Date(data)).getTime()/1000);
		$(_DOM).append("<span class='yomi'><em class='f-ib yomiday'>03</em>天<em class='f-ib yomihour'>03</em>小时<span class='time-hidden'><em class='yomimin f-ib'></em>分<span class='time-hidden2'><em class='yomisec f-ib'></em>秒</span></span></span>")
		reflash();

	};

	 var callbackhours =function(){};//时间为00时执行此函数

	reflash=function(){
		var	range  	= data-Math.round((new Date()).getTime()/1000),
					secday = 86400, sechour = 3600,
					days 	= parseInt(range/secday),
					hours	= parseInt((range%secday)/sechour),
					min		= parseInt(((range%secday)%sechour)/60),
					sec		= ((range%secday)%sechour)%60;
		$(_DOM).find(".yomiday").html(nol(days));
		$(_DOM).find(".yomihour").html(nol(hours));
		$(_DOM).find(".yomimin").html(nol(min));
		$(_DOM).find(".yomisec").html(nol(sec));

		 if(days==00 && hours==00){
		 	option.callbackhours();
		 }

		 if(sec<0){
		 	option.callback();
		 }
      
	};

	TIMER = setInterval( reflash,1000 );
	nol = function(h){
					return h>9?h:'0'+h;
	}
	return this.each(function(){
		var $box = $(this);
		createdom($box);
	});
}
})(jQuery);


// $(function(){
// 	$(".yomibox").each(function(){
// 		$(this).yomi();
// 	});

// 	// $("head").append("<style type='text/css'>.yomi {list-style:none;}.yomi li{float:left; color:#b89d63; padding:10px;font-size:35px;margin:10px;}.yomi li.split{background:none;margin:10px 0;padding:33px 0 0px;color:#b89d63; font-size:14px;}</style>")
// });