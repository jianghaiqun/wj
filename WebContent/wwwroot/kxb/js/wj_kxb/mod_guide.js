define(["jquery", "jquery_cookie", "jquery_form",  "art_dialog", "header" , "login" , "wdate_picker" , "footer", "jquery_SuperSlide" ], function() {

   return {
        init: function() {
              this.load_page();
              this.playsel();
              this.timesel();
              this.plansel();
              this.fiexedNav();
              this.funlist();
              this.funlist2();
              this.funlist3();
              this.funlist4();
        },
        //用户点评轮播初始化
            load_page:function(){

                    jQuery(".slideTxtBox1").slide({mainCell:".bd ul",autoPlay:true,effect:"topMarquee",vis:3,interTime:50,pnLoop:false,trigger:"click"});
                    jQuery(".slideTxtBox2").slide({mainCell:".bd ul",autoPlay:true,effect:"topMarquee",vis:3,interTime:50,pnLoop:false,trigger:"click"});
                    
            },
        //PK和保障对比隐藏信息展示
            playsel:function(){

                jQuery(".g-bzpk-con .g-bzpk-dl:eq(0) .g-pk-desf").show();
                jQuery(".g-bzpk-dl").on("click",function(){
                        if(jQuery(this).find(".g-pk-desf").is(':hidden')){
                             jQuery(this).find(".g-pk-desf").show();
                         }else{
                              jQuery(this).find(".g-pk-desf").hide();
                         }
                });


                jQuery(".gys-pk-dl .hovercs").hover(function(){
                    var objs= jQuery(this).children(".g-pk-desd");
                          if (objs.children('p').text()!="") {
                                objs.show();
                          };
                    },function(){
                          var objs= jQuery(this).children(".g-pk-desd");
                          objs.hide();
                })

            },
        //自选行程事件
            timesel:function(){

                var oobjs = jQuery('.ga-again-time');
                jQuery(".titcons").on("click",function(){
                    if (oobjs.is(':visible')) {
                        oobjs.hide()
                    } else {
                        oobjs.show()
                    }
                })
              
            },

          //健康闲选择计划
          plansel:function(){
                var lsel = jQuery('.g-pk-planli span');
                var dsel = jQuery('.g-pk-planli span.sel');

                dsel.each(function(index, el) {
                  var p = jQuery(el).attr("data-plan");
                  jQuery(".g-bzpk-dl").find("."+p).show();
                  jQuery(".plan_pay").find("."+p).show();
                });

                lsel.on("click",function(){
                  var plan = jQuery(this).attr("data-plan");
                  var plansel = plan.substring(0,8);
                  jQuery(this).siblings('span').removeClass("sel").end().addClass("sel");
                  jQuery(".g-bzpk-dl em[class^="+plansel+"]").hide();
                  jQuery(".g-bzpk-dl").find("."+plan).show();
                  jQuery(".plan_pay dd[class^="+plansel+"]").hide();
                  jQuery(".plan_pay").find("."+plan).show();
                })
          },
          //计划标题跟随
          fiexedNav:function(){

              var jWin = jQuery(window),    
              jNav = jQuery(".plan_title"),
              classname = "fixd",
              nNavOffTop = jNav.offset().top;


              jWin.bind("scroll", function() {
                  var nWinTop = jWin.scrollTop();

                  if(nWinTop >= nNavOffTop) {
                    jNav.addClass(classname);
                  } else{
                    jNav.removeClass(classname);
                  }
              });

          }
          
          
          ,funlist:function(){
        	  window.chooseSchedule = function(startId, endId, articleId){
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
        		  						jQuery("em[name=R_Ajax_PKPrice_"+index+"]").html("");
        		  						jQuery("i[name=Clear_Ajax_PKPrice_"+index+"]").html(price);
        		  						jQuery("i[name=Clear_Ajax_PKPrice_"+index+"]").show();
        		  					} else {
        		  						if (price.indexOf("_") == -1) {
        		  							jQuery("em[name=R_Ajax_PKPrice_"+index+"]").html("￥"+price);
        		  							jQuery("i[name=Clear_Ajax_PKPrice_"+index+"]").html("");
        		  							jQuery("i[name=Clear_Ajax_PKPrice_"+index+"]").hide();
        		  						} else {
        		  							var mPrice = price.split("_");
        		  							var tprice = "￥"+mPrice[0];
        		  							var initPrem = "￥"+mPrice[1];
        		  							jQuery("em[name=R_Ajax_PKPrice_"+index+"]").html(tprice);
        		  							if(initPrem && parseFloat(jQuery.trim(mPrice[0])) >= parseFloat(jQuery.trim(mPrice[1]))){
        		  								jQuery("i[name=Clear_Ajax_PKPrice_"+index+"]").html("");
        		  								jQuery("i[name=Clear_Ajax_PKPrice_"+index+"]").hide();
        		  								
        		  							}else{
        		  								jQuery("i[name=Clear_Ajax_PKPrice_"+index+"]").html(initPrem);

        		  							}
        		  						}
        		  					}
        		  				});
        		  			}
        		  		});
        		  	}
        	  }
          }
          
         , funlist2:function(){
        	  window.isDate = function(dateString){
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
          }
         , funlist3:function(){
	       	  window.isBirthDate = function(id, erroId){
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
         }
          
         , funlist4:function(){
       	  window.ErroClear = function(erroId){
  		  	jQuery('#' + erroId).html("");
  		  	if (erroId == "DayE") {
  		  		jQuery('#' + erroId).hide();
  		  	}
  	  }
         }
               
}
});