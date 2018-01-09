/*
#@des:导购页面js
*@maker:guodongqi
*@time:2015-8-19
*/
// 设置筛筛选项偏倚
function getposleft(){
    var  obj  =  jQuery(".g-guide-uls li");
    var  gnum =  obj.size();
    var  gwidth = parseInt(jQuery(".g-guide").width());
    var  gboxwidth = parseInt(jQuery('.g-guide-uls li').width());
    var  pleft = gwidth/(gnum+1); 
        obj.each(function (i,o){
            var s = parseInt(i+1);
            var zs = pleft*s-(gboxwidth/2);
            jQuery(o).animate({ 
                  left: zs+'px'
                }, 1000 );
})
}

// 设置标题位置
function settitpos(){
      var obj =  jQuery(".g-guide-tit");
      var titwidth = parseInt(obj.width())/2;
      obj.removeClass("alphatm");
            obj.animate({ 
                  marginLeft: -titwidth+'px',
                  left:50+"%"
                }, 2000 );
}

// 进度条进度
function progress(){
      var page = jQuery("#g-guidenum").val(); //获取当前第几步
      var probj = jQuery("#g-guideb").val();  //每步变化百分比
      var l = jQuery("#g-guidelase").val();   //后台返回是否为最后一页
      jQuery(".gg-next-b").width(page*probj+'%');
      if(l=="true"){ 
            jQuery(".gg-next-b").width(101+'%');
            jQuery(".gg-next").addClass("guides_06");
      }
}
// 设置画副高度 根据屏幕高度 增加动画画幅
 function winHeight(){
      var winH = jQuery(window).height();
      if (winH>750) {
        jQuery(".g-guide").height( 500+winH-750+'px');
        jQuery(".g-guide-uls").css({marginTop:(winH-750)/2})
      };
 }

 // 优势pk
 function updateclass(){
      var dlobjl = jQuery(".gys-pk-dll ");
      var dlobjr = jQuery(".gys-pk-dlr ");
      var dlpk = jQuery(".gys-pk-icon");
      dlobjl.show(1000);
      dlobjr.show(1000);
      dlpk.show(1000);
      var dlsizel = jQuery(".gys-pk-dll dd").size();
      var dlsizer = jQuery(".gys-pk-dlr dd").size();
   switch(dlsizel)
      {
      case 3:  dlobjl.addClass("gys-pk-dd3");
     break;
      case 4:  dlobjl.addClass("gys-pk-dd4");
     break;
      case 5:  dlobjl.addClass("gys-pk-dd5");
      break;
      case 6:  dlobjl.addClass("gys-pk-dd6");

      break;

      }
      switch(dlsizer)

      {

      case 3:  dlobjr.addClass("gys-pk-dd3");
       break;
      case 4:  dlobjr.addClass("gys-pk-dd4");
       break;
      case 5:  dlobjr.addClass("gys-pk-dd5");
       break;
      case 6:  dlobjr.addClass("gys-pk-dd6");
      break;

      }
}

 function againtime(){

     jQuery(".g-again-no").click(
      function(){
        jQuery(".ga-again-time").hide();

      })
 }
 
 function reselectXC() {
	 var oobjs = jQuery(".ga-again-time");
     if(oobjs.is(':visible')){
            oobjs.hide();
     }else{
        oobjs.show();
     }
 }
jQuery(document).ready(function() {

  
jQuery(".hovercs").hover(function(){
      var objs = jQuery(this).children(".g-pk-desf");
      if (objs.children('p').text()!="") {
            objs.show();
        var objh = objs.height();
        objs.css({ top: -(objh+32)+'px'});
      };
      
},function(){
   var objs = jQuery(this).children(".g-pk-desf");
     objs.hide();
     jiao.hide();
})




  updateclass();
    // 初始化
  getposleft();
  settitpos();
  progress();
  winHeight();
  winHeight();
  againtime();

  // 分类选中
    jQuery(".g-guideli li").tagclickbind();





// 分类筛选动画
    jQuery(".g-guideli li").click(function(){
        var owidth =  168;
        var orderdate = jQuery(this).attr("data-filter"); //分类变量
        var olist = jQuery('.g-guide-box li.'+orderdate);
        var alllist = jQuery(".g-guide-box li");
         alllist.animate({width: '-0px',marginLeft:'-0'}, "normal");
         olist.animate({width: '+'+owidth+'px',marginLeft:'+20'}, "normal");
         if(orderdate=="all"){
            alllist.animate({width: '+'+owidth+'px',marginLeft:'+20'}, "normal");
         }
    })


// 鼠标移上气球移动
  jQuery(".g-guide-uls li").hover(function(){
     var h =  jQuery(".g-guide-uls li").height();
            jQuery(this).animate({ 
                  marginTop: -30+'px',
                  height:h+30+'px'
                }, 200 );
  },function(){
           jQuery(this).animate({ 
                  marginTop: 0+'px'
                }, 200 );
  })

	var list = jQuery('span[name^=R_Ajax_PKPrice_]');
    // 现价
    var price = new Array(list.length);
	var productIDArray = new Array(list.length);
	for (var i = 0; i < list.length; i++) {
		productIDArray[i] = jQuery(list[i]).attr("name").replace("R_Ajax_PKPrice_","");
		price[i] = jQuery("span[name=R_Ajax_PKPrice_"+productIDArray[i]+"]").html().replace('￥','');
	}
	
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/ajax_price!ajaxPKActivityPrice.action?&callback=?&channelSn='+channelsn+'&ProductIDS='+productIDArray+'&discountPrices='+price,
		dataType: "json",
		async: false,
		success: function(data) {
		
			jQuery.each(data, function(index, price) {
				
				var total = jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").html()
				if (total == null || total == '') {
					total = jQuery("span[name=R_Ajax_PKPrice_"+index+"]").html().replace('￥','');
					if(parseFloat(jQuery.trim(price)) < parseFloat(jQuery.trim(total))){
						jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").html("￥"+total); 
//						jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").show();
					}
				}
				jQuery("span[name=R_Ajax_PKPrice_"+index+"]").html("￥"+price);
			});
		}
	});
  })


// 改变窗口大小 改变气球定位
jQuery(window).resize(function() {
 getposleft();
 winHeight();
});

function chooseSchedule(startId, endId, articleId) {
	var beginDate = jQuery.trim(jQuery('#' + startId).val());
	var endDate = jQuery.trim(jQuery('#' + endId).val());
	if (beginDate == null || beginDate == '') {
		jQuery("#DayE").html("请选择日期");
		jQuery('#DayE').show();
		return;
	}
	if (endDate == null || endDate == '') {
		jQuery("#DayE").html("请选择日期");
		jQuery('#DayE').show();
		return;
	}
	var diffDays = 0;
	if ((isDate(beginDate) != false) && (isDate(endDate) != false)) {
		var rbegin = beginDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		var rend = endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		var Date1 = new Date(rbegin[1], rbegin[3] - 1, rbegin[4]);
		var Date2 = new Date(rend[1], rend[3] - 1, rend[4]);
		if (Date1 > Date2) {
			jQuery("#DayE").html("出发日期不能晚于结束日期");
			jQuery('#DayE').show();
			return;
		} else {
			diffDays = parseInt(Math.abs(Date2 - Date1) / 1000 / 60 / 60 / 24) + 1;
		}
		jQuery("#defaultPeriod").html(diffDays + '天行程');
		jQuery(".ga-again-time").hide();
		var cpsUserId = jQuery.cookie('cpsUserId');
		var channelsn = "wj";
		if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
			channelsn = "cps";
		}
		jQuery.ajax({
			type: 'post',
			url: sinosoft.base+'/shop/ajax_price!ajaxPKPrice.action?&callback=?&channelSn='+channelsn+'&articleId='+articleId+'&diffDays='+diffDays,
			dataType: "json",
			async: false,
			success: function(data) {
				jQuery.each(data, function(index, price) {
					if (price == '超过最长保险期限') {
						jQuery("span[name=R_Ajax_PKPrice_"+index+"]").html("");
						jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").html(price);
						jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").show();
					} else {
						if (price.indexOf("_") == -1) {
							jQuery("span[name=R_Ajax_PKPrice_"+index+"]").html("￥"+price);
							jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").html("");
							jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").hide();
						} else {
							var mPrice = price.split("_");
							var tprice = "￥"+mPrice[0];
							var initPrem = "￥"+mPrice[1];
							jQuery("span[name=R_Ajax_PKPrice_"+index+"]").html(tprice);
							if(initPrem && parseFloat(jQuery.trim(mPrice[0])) >= parseFloat(jQuery.trim(mPrice[1]))){
								jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").html("");
								jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").hide();
								
							}else{
								jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").html(initPrem);
//								jQuery("span[name=Clear_Ajax_PKPrice_"+index+"]").show();
							}
						}
					}
				});
			}
		});
	}
}

/**
 * 清除错误信息.
 * 
 * @param erroId
 * @return
 */
function ErroClear(erroId) {
	jQuery('#' + erroId).html("");
	if (erroId == "DayE") {
		jQuery('#' + erroId).hide();
	}
}
/**
 * 判断是否是日期格式.
 * 
 * @param id
 * @param erroId
 * @return
 */
function isBirthDate(id, erroId) {
	var date = jQuery.trim(jQuery('#' + id).val());
	if (date == null || date == '') {
		jQuery('#' + erroId).html("请选择日期");
		if (erroId == "DayE") {
			jQuery('#' + erroId).show();
		}
		return false;
	}
	if (isDate(date) == false) {
		jQuery('#' + erroId).html("日期格式错误");
		if (erroId == "DayE") {
			jQuery('#' + erroId).show();
		}
		return false;
	}
}

/**
 * 判断是否是日期.
 * 
 * @param dateString
 * @return
 */
function isDate(dateString) {
	var r = dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[4]);
	var num = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d
			.getDate() == r[4]);
	var time = d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3]
			&& d.getDate() == r[4];
	if (time != true) {
		return false;
	}
}