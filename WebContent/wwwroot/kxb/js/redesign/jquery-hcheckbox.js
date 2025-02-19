/**
* ------------------------------------------
* @make:郭斌
* @version  1.0 
* @des：产品筛选效果
* ------------------------------------------
*/
(function(jQuery){
	
  jQuery.fn.bindbase=function(){
  	//取每一个筛选条件进行循环
  	jQuery("div[class^=bznx]").each(function(){
  	 
  		var ele_Name = jQuery(this).attr("id").substring(18);
  		var selected_ele_value = jQuery("#hdn_" + ele_Name).val().split(',');
    	if (jQuery("#" + ele_Name + "_all").length == 1) {
    		jQuery("input[name='" + ele_Name + "']").each(function(){
    			jQuery(this).attr("checked",false);
    		});
    		var i = 0;
    		for (;i < selected_ele_value.length; i++) {
    			jQuery("input[name='" + ele_Name + "'][value='" + selected_ele_value[i] + "']").attr("checked",true);
    		}
    	} else {
    		if (selected_ele_value.length == 1) {
	    		selected_ele_value[1] = selected_ele_value[0];
	    	}
    		jQuery("input[name='" + ele_Name + "']").each(function(){
	    		var sub_eles = jQuery("#CsearchConditions_" + ele_Name + "_" + jQuery(this).attr("value"));
	    		
	    		if(jQuery(this).attr("value") == selected_ele_value[0]){
	    			jQuery(this).attr("checked",true);
	    			if (sub_eles.length == 1) {
	            sub_eles.show();
	            jQuery("#CsearchConditions_" + ele_Name + "_").show();
	            jQuery("input[name='" + ele_Name + "_sub']").each(function(){
	              if(jQuery(this).attr("value") == selected_ele_value[1])
	              {
	                jQuery(this).attr("checked",true);
	              }
	      			});
	      		}
	      	} else {
	      		if (sub_eles.length == 1) {
	      			sub_eles.hide();
	      		}
	      	}
    		});
    	}
  	});
  };

  
	jQuery.fn.hcheckbox=function(options){
		
		jQuery(':checkbox+label',this).each(function(){
			  jQuery(this).addClass('checkbox');
		      if(jQuery(this).prev().is(':checked')){
		        jQuery(this).addClass("checked");
		      }else{
		        jQuery(this).removeClass('checked');
		      }
		}).click(function(event){
			var FrontServer = document.getElementById("FrontServer").value;
			jQuery("#load_con").show();
	        jQuery("#load_con").html("<img src='"+FrontServer+"/images/loading.gif' />");
			if(!(jQuery(this).prev().is(':checked'))){
				jQuery(this).addClass("checked");
        jQuery(this).prev()[0].checked = true;
      } else {
	        jQuery(this).removeClass('checked');			
	        jQuery(this).prev()[0].checked = false;
      }
      //获取点击对象的前一个控件的name属性
      var objName=jQuery(this).prev().attr("name");
      var idStr="";
      jQuery("input[name='"+objName+"']").each(function(){
	        if(jQuery(this).is(":checked") && (jQuery(this).attr("id") != (objName + "_all")))
	        {  
	        	idStr=idStr+jQuery(this).val()+",";
	        }
      });
      if(idStr.length==0)
      {   
        //空串，表示没有被选中的
        jQuery("#"+objName + "_all").attr("checked",true);
        jQuery("#"+objName + "_all").next().addClass('hRadio_Checked');
        jQuery("#hdn_"+objName).val("default_"+objName+"_1");
      } else {
        //全部
        jQuery("#"+objName + "_all").attr("checked",false);
        jQuery("#"+objName + "_all").next().removeClass('hRadio_Checked');
        idStr=idStr.substring(0,idStr.length-1);
        jQuery("#hdn_"+objName).val(idStr);
      }
	    //取每一个筛选条件进行循环取值
	  	searchProduct(0);
	  	event.stopPropagation();
		}).prev().hide();
	};

	jQuery.fn.hradio = function(options){
	  var self = this;
	  return jQuery(':radio+label,:radio+a',this).each(function(){
	    jQuery(this).addClass('hRadio');
	    //执行绑定
	    if(jQuery(this).prev().is(":checked")) {
	    	jQuery(this).addClass('hRadio_Checked');
	    } else {
	    	jQuery(this).removeClass('hRadio_Checked');
	    }
    }
	
	  ).click(function(event){
    	var FrontServer = document.getElementById("FrontServer").value;
    	jQuery("#load_con").show();
        jQuery("#load_con").html("<img src='"+FrontServer+"/images/loading.gif' />");
	    jQuery(this).parent().siblings().children('label').removeClass("hRadio_Checked");
      if(!jQuery(this).prev().is(':checked')){
      	jQuery(this).addClass("hRadio_Checked");
      	jQuery(this).prev()[0].checked = true;
      }
      var clickName = jQuery(this).prev().attr("name");
      var clickValue = jQuery(this).prev().attr("value");
      var parentName = clickName;
      if (clickName.indexOf("_sub") > 0 ) {
      	parentName = jQuery(this).parent().parent().attr("id");
      	jQuery("#hdn_" + clickName.substring(0,clickName.length - 4)).val(parentName.substring(parentName.lastIndexOf("_") + 1) + "," + clickValue);
      } else {
      	jQuery("#hdn_" + clickName).val(clickValue);
      	if (jQuery("#CsearchConditions_" + clickName + "_" + clickValue).length == 1) {
      		var old_ele = jQuery("#CsearchConditions_" + clickName + "_" + clickValue).find(".hRadio_Checked");
					if (old_ele.length >= 1) {
						jQuery("#hdn_" + clickName).val(clickValue + "," + old_ele.prev()[0].value);
					} else {
	      		jQuery("#" + clickName + "_sub_" + clickValue).attr("checked",true);
	      		jQuery("#" + clickName + "_sub_" + clickValue).siblings().addClass('hRadio_Checked');
	      	}
      		jQuery("[id^='CsearchConditions_" + clickName + "_']").each(function(){
      			if (jQuery(this).attr("id") == "CsearchConditions_" + clickName + "_" || jQuery(this).attr("id") == "CsearchConditions_" + clickName + "_" + clickValue) {
      				jQuery(this).show();
      			}else if (jQuery(this).attr("id") == "CsearchConditions_" + clickName + "_jn-sj") {
                    var _XOffSet = jQuery(":input[name='"+clickName+"'][value='"+clickValue+"']").parent().offset().left - jQuery(this).parent().offset().left + 40;
                    jQuery(this).css("margin","0 0 -4px " + _XOffSet + "px");
                    jQuery(this).show();
      			}else {
      				jQuery(this).hide();
      			}
      		});
      	} else if (jQuery("#CsearchConditions_" + clickName + "_").length == 1) {
      		jQuery("#CsearchConditions_" + clickName + "_").hide();
      	} else if (jQuery(this).prev().attr("id") == clickName + "_all") {
      		jQuery("input[name='"+clickName+"']").each(function(){
      			if (jQuery(this).attr("id") != clickName + "_all") {
      				jQuery(this).siblings().removeClass('checked');
      				jQuery(this).attr("checked",false);
      			}
      		});
      	}
      }
      
      //取每一个筛选条件进行循环取值
	  	searchProduct(0);
      event.stopPropagation();
    }).prev().hide();
  }
	
	//初始化表单
	jQuery('.chklist').bindbase();
	jQuery('.chklist').hcheckbox();
	jQuery('.radiolist').hradio();
	
})(jQuery);

var state = 0;

// 与后台交互进行产品筛选
function searchProduct(pageIndex){
	var checkvalue = "";
	jQuery("div[class^=bznx]").each(function(){
  		var ele_Name = jQuery(this).attr("id").substring(18);
  		checkvalue += "|" + jQuery("#hdn_" + ele_Name).val()
  	});
  	
	var ProductsOrder = "";
	if (document.getElementById("order_Popular").className == "twoasc") {
		ProductsOrder = "Popular asc";
	} else if (document.getElementById("order_Popular").className == "twodesc") {
		ProductsOrder = "Popular desc";
	} else if (document.getElementById("order_SalesVolume").className == "twoasc") {
		ProductsOrder = "SalesVolume asc";
	} else if (document.getElementById("order_SalesVolume").className == "twodesc") {
		ProductsOrder = "SalesVolume desc";
	} else if (document.getElementById("order_InitPrem").className == "twoasc") {
		ProductsOrder = "InitPrem asc";
	} else if (document.getElementById("order_InitPrem").className == "twodesc") {
		ProductsOrder = "InitPrem desc";
	}
	var activityFlag = "N";
	if (document.getElementById("order_sale").className == "order-sale selct_sale") {
		activityFlag = "Y";
	}
	
	var ProductType = document.getElementById("ProductType").value;
	var ChiProductType = document.getElementById("ChiProductType").value;
	var searchProductFlag = jQuery("#searchProductFlag").val();
	jQuery.getJSON(sinosoft.base + '/shop/filter!searchRe.action?callback=?&searchProductFlag='+searchProductFlag+'&condtion=' + checkvalue 
			+'&pageIndex='+pageIndex+'&ProductsOrder='+ProductsOrder+'&ProductType='+ProductType+'&ChiProductType='+ChiProductType+'&activityFlag='+activityFlag,
			function(data) {
			 if(data && data.status == '1'){
				    // 无产品提示
				    jQuery("#s_noshop").hide();
				    // 替换产品
					jQuery("#products").html(data.sb1);
					jQuery("#productsPageBar").html(data.sb3);
					jQuery("#searchProductCount").html(data.total);
					jQuery('#searchProductCount').text(jQuery('#listProductCount').val());
					// 拼装分页
			   } else if(data && data.status == '2'){
				     //针对第一次筛选有结果，而第二次没有筛选结构的情况
				   	 var notproduct = "<div class=\"noshop_img\"><img src=\"" + sinosoft.staticPath + "/images/redesign/noshop_47.gif\" width=\"72\" height=\"93\" /></div>";
				     notproduct += " <div class=\"noshop_des\"> 抱歉，没有找到您需要的产品<br>建议换筛选条件试试<br>或者看看相似产品</div>";
				     notproduct += " <div class=\"clear\"></div>";
				     notproduct += "  <p class=\"shop_titles\">相似产品推荐：</p>";
					 jQuery("#s_noshop").html(notproduct);
					 jQuery("#s_noshop").show();
					 jQuery('#searchProductCount').text("0");
					 jQuery("#productsPageBar").html("");
			   } else {
				     var notproduct = "<div class=\"noshop_img\"><img src=\"" + sinosoft.staticPath + "/images/redesign/noshop_47.gif\" width=\"72\" height=\"93\" /></div>";
				     notproduct += " <div class=\"noshop_des\"> 抱歉，没有找到您需要的产品<br>建议换筛选条件试试<br>或者看看相似产品</div>";
				     notproduct += " <div class=\"clear\"></div>";
				     notproduct += "  <p class=\"shop_titles\">相似产品推荐：</p>";
					 jQuery("#s_noshop").html(notproduct);
					 jQuery("#s_noshop").show();
					 jQuery('#searchProductCount').text("0");
					 jQuery("#productsPageBar").html("");
			   }
			 	
			  jQuery("#load_con").html("");
			  jQuery("#load_con").hide();
				state = 0;
				productChange();
				
			 });
}


//排序
function doOrder1(ele){
	jQuery("#load_con").show();
    jQuery("#load_con").html("<img src='"+sinosoft.front+"/images/loading.gif' />");
	changeOrderInfoStyle(ele);
	searchProduct(0);
	setTimeout("AjaxPriceOrder()",2000);
}
//分页
function doOrder2(num){
	jQuery("#load_con").show();
    jQuery("#load_con").html("<img src='"+sinosoft.front+"/images/loading.gif' />");
	searchProduct(num);
	setTimeout("AjaxPriceOrder()",2000);
	
}
function newJump(){
	var JumpPage = document.getElementById('_PageBar_Index_').value;
	doOrder2(JumpPage);
	
}
function AjaxPriceOrder(){
	
	if(jQuery('span[name^=Ajax_Prict_]').length <= 0){
		return;
	}
	var list = jQuery('span[name^=Ajax_Prict_],span[name^=R_Ajax_Prict_]');
	
	var productIDArray = new Array(list.length);
	for (var i = 0; i < list.length; i++) {
		productIDArray[i] = jQuery(list[i]).attr("name").replace("R_","");
	}
	
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/ajax_price!ajaxPrice.action?&callback=?&channelSn='+channelsn+'&ProductIDS='+productIDArray,
		dataType: "json",
		async: false,
		success: function(data) {
		
		jQuery.each(data, function(index, price) {
			var mPrice = price.split("_");
			var tprice = "<em>￥</em>"+mPrice[0];
			var initPrem = "<em>￥</em>"+mPrice[1];
			var ttLen = mPrice.length;
			var disrate = "dis";
			if(ttLen>=4){
				disrate = mPrice[3];
			}
			//列表页推荐产品-无“￥”
			jQuery("span[name=R_Ajax_Prict_"+index+"]").html(mPrice[0]); 
			
			jQuery("span[name=Ajax_Prict_"+index+"]").html(tprice);
			
			if(jQuery("#txt_price").length > 0){
				var v_riskCode = jQuery("#RiskCode").val();
				if(v_riskCode == index){
					jQuery("#txt_price").html("<em>￥</em>"+mPrice[0]);
				}
			} 
			if(initPrem && parseFloat(jQuery.trim(mPrice[0])) >= parseFloat(jQuery.trim(mPrice[1]))){
				if(disrate=="dis"){
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").html("");
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").hide();
					jQuery("#Clear_li_Ajax_Prict_"+index).hide();
				}
			}else{
				if(mPrice[9]=="discount"){
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").html(initPrem);
					jQuery("span[name=Clear_Ajax_Prict_"+index+"]").show();
					jQuery("#Clear_li_Ajax_Prict_"+index).show();
				}
			}
			if(jQuery("#productIntegral_"+index).length > 0){
				 if(mPrice[2] < 1){
					 jQuery("#productIntegral_" + index).html("0");
				 }else{
					 if(ttLen>=8&&'true'==mPrice[6]&&mPrice[7]!=''){
						 jQuery("#pointdes").html(mPrice[7]);
					 }else{
						 jQuery("#productIntegral_" + index).html(Math.floor(mPrice[2])+" ");
					 }
					 if(ttLen>=6){
						  if("true"!=mPrice[5]){
							  jQuery("#integer_login").show();
						  }
					}
				 }
				 if(mPrice.length > 4){
					 if(parseFloat(mPrice[4])!=0){
						 if("true"!=mPrice[5]){
							 jQuery("#maxIntegralPrice_" + index).html("<span>积分最多可抵<em>"+mPrice[4]+"</em>元</span>");
						 }else{
							 jQuery("#maxIntegralPrice_" + index).html("<span>您有积分<em>"+mPrice[8]+"</em> 本次可抵<em>"+mPrice[4]+"</em>元</span>");
						 }
					 }
				 }
			}
		});
	}
	});
}

