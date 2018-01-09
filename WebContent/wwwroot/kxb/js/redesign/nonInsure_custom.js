
	// 在线客服
	BizQQWPA.addCustom([ {
		aty : '0',
		a : '0',
		nameAccount : 800032037,
		selector : 'qq_bind1'
	}, {
		aty : '0',
		a : '0',
		nameAccount : 800032037,
		selector : 'qq_bind2'
	}, {
		aty : '0',
		a : '0',
		nameAccount : 800032037,
		selector : 'qq_bind3'
	}, {
		aty : '0',
		a : '0',
		nameAccount : 800032037,
		selector : 'qq_bind4'
	} ]);



	jQuery("#fix_nav").remove();
	jQuery("#fix_nav_right span").taghoverbind();
	jQuery("#fix_nav_right").fixService();
	
	
	jQuery('#gotop-up').click(function () {
	    jQuery('body,html').animate({
	        scrollTop: 0
	    }, 500)
	});