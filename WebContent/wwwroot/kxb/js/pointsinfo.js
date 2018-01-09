jQuery.ajax({
	url: sinosoft.base+"/shop/points!hotgiftlist.action",
	dataType: "json",
	async: true,
	success: function(data) {
			var obj = eval(data);
			var hot = obj.pointhothtml;
			var rec = obj.pointrechtml;
			jQuery("#recgiftlist").html(rec);
			jQuery("#hotgiftlist").html(hot);
			mousemovefunc();
		}
	}); 

//mousemovefunc();
function mousemovefunc(){
	jQuery('.hot_giftdl').mousemove(function () {
	jQuery(this).siblings().children('.hot_giftdd').hide();
	jQuery(this).children('.hot_giftdd').show();
			 });
}
