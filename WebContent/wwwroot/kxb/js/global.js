jQuery_1_7_2(function(){
jQuery_1_7_2("#scrollNav li span").hover(function () {
    jQuery_1_7_2(this).addClass("salesnavon");
	},
  function () {
    jQuery_1_7_2(this).removeClass("salesnavon");
	}
);

jQuery_1_7_2(".salesnav01").click(function(){
jQuery_1_7_2("html,body").animate({
scrollTop: jQuery_1_7_2(".head").offset().top
},700);
});
jQuery_1_7_2(".salesnav02").click(function(){
jQuery_1_7_2("html,body").animate({
scrollTop: jQuery_1_7_2(".hdlctit").offset().top
},700);
});
jQuery_1_7_2(".salesnav03").click(function(){
jQuery_1_7_2("html,body").animate({
scrollTop: jQuery_1_7_2(".mao3").offset().top
},700);
});

jQuery_1_7_2(".egg01 span").hover(function () {
    jQuery_1_7_2(this).addClass("ehover");
	},
  function () {
    jQuery_1_7_2(this).removeClass("ehover");
	}
);
jQuery_1_7_2(".egg01 span").click(function () {
	if(submitp_check()&&check_choice()){
		jQuery_1_7_2(this).addClass("eclick").removeClass("ehover");
		setTimeout("submitp_1()",1000);
	}
});
	
jQuery_1_7_2(".egg02 span").hover(function () {
    jQuery_1_7_2(this).addClass("ehover");
	},
  function () {
    jQuery_1_7_2(this).removeClass("ehover");
	}
);
jQuery_1_7_2(".egg02 span").click(function () {
	if(submitp_check()&&check_choice()){
		jQuery_1_7_2(this).addClass("eclick").removeClass("ehover");
		setTimeout("submitp_1()",1000);
	}
});	

jQuery_1_7_2(".egg03 span").hover(function () {
    jQuery_1_7_2(this).addClass("ehover");
	},
  function () {
    jQuery_1_7_2(this).removeClass("ehover");
	});
jQuery_1_7_2(".egg03 span").click(function () {
	if(submitp_check()&&check_choice()){
		jQuery_1_7_2(this).addClass("eclick").removeClass("ehover");
		setTimeout("submitp_1()",1000);
	}
});	
});
