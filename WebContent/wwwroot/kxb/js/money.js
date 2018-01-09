/*理财险*/
jQuery(function() {
	//理财焦点图切换
	jQuery('#change_32 .a_bigImg').soChange({
		thumbObj: '#change_32 .ul_change_a2 span',
		thumbNowClass: 'on',
		//导航图标，选择器直接指向图标对象 
		botPrev: '#change_32 .a_last',
		botNext: '#change_32 .a_next',
		changeTime: 4000,
		changeType: 'slide',
		thumbOverEvent: false,
		delayTime: 300
	});

	/*理财险部分js*/
	var gm_nums = jQuery("#gm_nums");

	function send_data() {
		var s = gm_nums.val()
		//2000为本产品的可够份数 后台调去数据
		if (parseInt(gm_nums.val()) < 2000) {
			alert("去调用试算方法 亲 (*^__^*) 嘻嘻！");
		} else {
			alert("录入份数超出可购买最大份数请重新录入");
			gm_nums.val(2000);
		}
	}

	/*输入购买分数后自动试算*/

//	gm_nums.numeral();
	var t = '';
	gm_nums.keyup(function() {
		gm_nums.addClass("gm_mun_l");
		clearTimeout(t);
		t = setTimeout("send_data()", 500);
	});



});
//理财险计算器
function getMonney(){
	var amount = jQuery('#buyAmount').val();
	var day = jQuery('#buyDay').val();
	var rate = jQuery('#buyRate').val();
	if(!amount.match(/^[0-9]+(.[0-9]{1})?$/)){
		jQuery("#cal_err").show();
		jQuery("#cal_rs").hide();
		return;
	}
	if(!day.match(/^(0|[1-9][0-9]*)$/)){
		jQuery("#cal_err1").show();
		jQuery("#cal_rs").hide();
		jQuery("#cal_err").hide();
		return;
	}
	if(!rate.match(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/)){
		jQuery("#cal_err2").show();
		jQuery("#cal_rs").hide(); 
		jQuery("#cal_err").hide();
		jQuery("#cal_err1").hide();
		return;
	}
	var amt = amount*10000*(day/365)*(rate/100);
	var samt = amount*10000 + amt;
	amt = amt.toFixed(2);
	samt = samt.toFixed(2);
	jQuery('#getAmount').html('<strong>'+amt+'</strong>');
	jQuery('#allAmount').html('<strong>'+samt+'</strong>');
	jQuery("#cal_err").hide();
	jQuery("#cal_err1").hide();
	jQuery("#cal_err2").hide();
	jQuery("#cal_rs").show();
};
function update_nums(num){
    gm_nums.val(num) ;
    gm_nums.addClass("gm_mun_l");
}
jQuery('#car_news_tag li span').hover(
        function()
   {
       jQuery(this).addClass('hover').parent().siblings().find('span').removeClass('hover');
       jQuery('#car_mews_body > ul:eq('+ jQuery('#car_news_tag li').index(jQuery(this).parent()) +')').show().siblings().hide();

   });