// JavaScript Document
  jQuery(function(){
  jQuery(".CRiskAppDuty > div").hover(
  function(){
	   jQuery(this).addClass("row0");
	  },
  function () {
    jQuery(this).removeClass("row0");
	  }
  );
  
  jQuery(".CRiskAppFactor ul li span").hover(
  function(){
	   jQuery(this).addClass("hover");
	  },function () {
    jQuery(this).removeClass("hover");
  });
  
    jQuery(".button_b span.button_b1").hover(
  function(){
	   jQuery(this).addClass("hover");
	  },function () {
    jQuery(this).removeClass("hover");
  });
     jQuery(".button_b span.aad_button_c").hover(
  function(){
	   jQuery(this).addClass("hover2");
	  },function () {
    jQuery(this).removeClass("hover2");
  });
   jQuery(".button_b span.button_a").hover(
  function(){
	   jQuery(this).addClass("hover3");
	  },function () {
    jQuery(this).removeClass("hover3");
  })

//合作品牌tab切换
			
             jQuery('#tag_box_hzpp1 > ul:not(:first)').hide();
			  
            jQuery('#min_tag_hzpp1 li em').click(
			function()
            {
                jQuery(this).addClass('select_tab_hzpp1').parent().siblings().find('em').removeClass('select_tab_hzpp1');
                jQuery('#tag_box_hzpp1 > ul:eq('+ jQuery('#min_tag_hzpp1 li').index(jQuery(this).parent()) +')').show().siblings().hide();
            });
			  jQuery('#tag_box_hzpp2 > ul:not(:first)').hide();
			  
            jQuery('#min_tag_hzpp2 li em').click(
			function()
            {
                jQuery(this).addClass('select_tab_hzpp1').parent().siblings().find('em').removeClass('select_tab_hzpp1');
                jQuery('#tag_box_hzpp2 > ul:eq('+ jQuery('#min_tag_hzpp2 li').index(jQuery(this).parent()) +')').show().siblings().hide();
            });
			
			 jQuery('#tag_box_hzpp3 > div:not(:first)').hide();
			  
            jQuery('#min_tag_hzpp3 li em').click(
			function()
            {
                jQuery(this).addClass('select_tab_hzpp1').parent().siblings().find('em').removeClass('select_tab_hzpp1');
                jQuery('#tag_box_hzpp3 > div:eq('+ jQuery('#min_tag_hzpp3 li').index(jQuery(this).parent()) +')').show().siblings().hide();
            });
			
	  jQuery(".pp_cp_des").hover(
  function(){
	   jQuery(this).addClass("hover_s");
	  },function () {
    jQuery(this).removeClass("hover_s");
  });
			
			
			
			
  jQuery('#tag_box > div:not(:first)').hide();


	jQuery('#min_tag em a').click(function() {
		if(jQuery(this).parent().is(".bor_scr")) {
			jQuery('#min_tag em a').first().click();
			var _id = jQuery(this).attr("data-id");
			var _top = jQuery("#" + _id).offset().top - $("#min_tag").height();
			if(parseInt(jQuery('#tag_box').css("paddingTop")) == 0) _top = _top - 45;
				jQuery("html, body").stop().animate({scrollTop: _top}, 1);
		} else {
			jQuery(this).addClass('selects_tag').parent().siblings().find('a').removeClass('selects_tag');
			jQuery('#tag_box > div:eq('+ jQuery('#min_tag em:not(".bor_scr")').index(jQuery(this).parent()) +')').show().siblings().hide();
		}
	});
			
			  jQuery('#tag_boxs > div:not(:first)').hide();
			  
            jQuery('#min_tags h3 a').click(
			function()
            {
                jQuery(this).addClass('selects_tags').parent().siblings().find('a').removeClass('selects_tags');
                jQuery('#tag_boxs > div:eq('+ jQuery('#min_tags h3').index(jQuery(this).parent()) +')').show().siblings().hide();
            });
				  jQuery('#tag_boxs2 > div:not(:first)').hide();
			  
            jQuery('#min_tags2 h3 a').click(
			function()
            {
                jQuery(this).addClass('selects_tags').parent().siblings().find('a').removeClass('selects_tags');
                jQuery('#tag_boxs2 > div:eq('+ jQuery('#min_tags2 h3').index(jQuery(this).parent()) +')').show().siblings().hide();
            });

              //判断是否存在计划并且计划数量在2个以上则出现对比功能按钮(长期险不添加计划对比按钮)
		if(jQuery("span[class=button_a]") && jQuery("span[class=button_a]").length > 0 && jQuery('ul[id$="_Plan"]') && jQuery('ul[id$="_Plan"]>li').length>1&&LongInsurance!='L'){  
			 jQuery('ul[id$="_Plan"] > li:last-child').after("<li><span  class='elect_plan'>计划对比</span></li>");
		}

		if(jQuery("span[class=button_a]") && jQuery("span[class=button_a]").length > 0 && jQuery('ul[id$="_Plan"]') && jQuery('ul[id$="_Plan"]>li').length>1&&LongInsurance!='L'){  
	     	jQuery('ul[id$="_Plan"] > li:last-child').click(
			     function(){
		            var aProductId = jQuery("#RiskCode").val();//得到产品ID
		            //得到投保要素 copy doBuy()
		            var rootEle = document.getElementById("divRiskAppFactor_" + aProductId);
		         	var tempURL = "";
		  
						if (rootEle != null) {
							var dc = new DataCollection();
							var ulNodeList = rootEle.getElementsByTagName("UL");
							for ( var i = 0; i < ulNodeList.length; i++) {
								for ( var j = 0; j < ulNodeList[i].childNodes.length; j++) {
									if (ulNodeList[i].childNodes[j].className == "li_selected") {
										var param = ulNodeList[i].id;
										param = param.substring(param.indexOf("_")+1);
										var nameval = ulNodeList[i].childNodes[j].firstChild.getAttribute("name");
										if(nameval == null){
											if(jQuery("#LiDayItemAuto").html() == null  || jQuery("#LiDayItemAuto").html() == 'undefined' || jQuery("#LiDayItemAuto").html() == '帮您选天数▼'
												|| jQuery("#LiDayItemAuto").html().indexOf('自主选择')!=-1
											){
												var txtStartDay = jQuery("#txtStartDay").val();
												var txtEndDay = jQuery("#txtEndDay").val();
												
												if(txtStartDay == null || txtStartDay == ''){
													jQuery("#DayE").html("请选择出发日期");
													jQuery('#DayE').show();
													return false;
												}
												if(txtEndDay == null || txtEndDay == ''){
													jQuery("#DayE").html("请选择结束日期");
													jQuery('#DayE').show();
													return false;
												}
												jQuery("#DayE").html("请计算天数");
												jQuery('#DayE').show();
												return false;
											}
											
											var day = parseInt(getDay(jQuery("#LiDayItemAuto").html()));
											if(day <= 0){
												jQuery("#DayE").html("天数只能选择大于等于1天");
												jQuery('#DayE').show();
												return false;
											}
											tempURL += "&"+param + "="+encodeURIComponent(day+"X");
										}else{
											tempURL += "&"+param + "="+encodeURIComponent(encodeURIComponent(ulNodeList[i].childNodes[j].firstChild.getAttribute("name")));
										}
										
										continue;
									}
								}
			 
							}
						}
						tempURL += "&complicatedFlag="+complicatedFlag;
		         	//传递到计划对比页面
					 art.dialog.data('productId', aProductId);
					 art.dialog.data('tempURL', tempURL);
			   		 art.dialog.open('../block/price_page.shtml',{title: '计划对比',width:790}, false);
				 }
			);
		}
	});
  
  // 解决IE8不支持数组的indexOf问题
  if (!Array.prototype.indexOf) {
	  Array.prototype.indexOf = function(elt /*, from*/){  
      var len = this.length >>> 0;  
      var from = Number(arguments[1]) || 0;  
      from = (from < 0) ? Math.ceil(from) : Math.floor(from);  
      if (from < 0) {
    	  from += len; 
      }
      for (; from < len; from++) {  
        if (from in this && this[from] === elt) { 
        	return from;
        }
      }  
      return -1;  
    };  
  }  