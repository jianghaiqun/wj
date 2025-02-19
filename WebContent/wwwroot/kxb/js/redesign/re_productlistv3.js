jQuery(function(){
	jQuery('#searchProductCount').text(jQuery('#listProductCount').val());
	/*筛选条件折叠效果*/
		var Selectlist = jQuery('.radiolist div.bznx-no');//获取除了前2条筛选条件集合对象
		var goggleBtn = jQuery('#zd_click');//获取折叠显示按钮
		
		goggleBtn.click(
			function(){
				if(Selectlist.is(":hidden")){
					Selectlist.show();
					goggleBtn.addClass("zd_sx_tog");
					goggleBtn.attr("title","收起筛选条件");
				}else{
					 goggleBtn.removeClass("zd_sx_tog");
				     Selectlist.hide();
					 goggleBtn.attr("title","展开筛选条件");
				}
			}
		);
		var selectcheck = jQuery(".chklist .checked");
		selectcheck.parents(".bznx-no").show();
		var proType = jQuery('#ChiProductType').val();
		if (proType == 'A00' || proType == 'B00' || proType == 'C00' || proType == 'D00' || proType == 'E00' || proType == 'H00') {
			Selectlist.hide();
			if(Selectlist.not(":visible")){
				goggleBtn.removeClass("zd_sx_tog");
				goggleBtn.attr("title","展开筛选条件");
			}else{
				goggleBtn.addClass("zd_sx_tog");
				goggleBtn.attr("title","收起筛选条件");
			}	
		} else {
			Selectlist.show();
			goggleBtn.addClass("zd_sx_tog");
			goggleBtn.attr("title","收起筛选条件");
		}
	
		productChange();
		initProduct();
		salesVolumeLoad();
		commnetLoad();
		searchProductListAvtivity();

		
});

/**
 * 得到参数
 * @param url
 * @returns
 */
function getParam(){
	
	var age = jQuery("#age").val();
	var period = jQuery("#period").val();
	var travelAddress = jQuery("#travelAddress").val();
	var features = jQuery("#features").val();
	var prem = jQuery("#searchPrem").val();
	var ProductType = jQuery("#ProductType").val();
	var tRequest="&ProductType="+ProductType+"&age="+age+"&travelAddress="+travelAddress+"&period="+period+"&features="+features+"&prem="+prem;
	//var tRequest="&ages="+ageArr+"&period=8&travelAddress="+encodeURI(encodeURI(travelAddress));
	return tRequest;
}
/**
 * 列表页产品销量
 */
function salesVolumeLoad() {
	var list=jQuery('span[id^=SalesV_]');
	var ulist=jQuery('span[id^=SalesU_]');
	var Ilist=jQuery('span[id^=SalesI_]');
	var id = new Array(list.length + ulist.length + Ilist.length);
	var idStr="";
	var aryIndex = 0;
	for (var i = 0; i < list.length; i++) {
        id[aryIndex++] = list[i].id;
        idStr+= list[i].id;
	}
	for (var i = 0; i < ulist.length; i++) {
		id[aryIndex++] = ulist[i].id;
        idStr+= ulist[i].id;
	}
	for (var i = 0; i < Ilist.length; i++) {
		id[aryIndex++] = Ilist[i].id;
        idStr+= Ilist[i].id;
	}
	if(idStr==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!search.action?productIds='+id,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				if(index.indexOf("SalesI_") != -1){
					document.getElementById(index).innerHTML="<i>"+object+"人</i>已购买";
				}else{
					document.getElementById(index).innerHTML=object+"人已投保";
				}
			});
		}
	});
}
/**
 * 列表页产品评论
 */
function commnetLoad() {
	var list=jQuery('a[id^=CommentV_]');
	var id = new Array(list.length);
	var idStr="";
	for (var i = 0; i < list.length; i++) {
           id[i] = list[i].id;
           idStr+= list[i].id;
	}
	if(idStr==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!comment.action?productIds='+id,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
	        document.getElementById(index).innerHTML="<i>"+object+"</i>人已评价";
			});
		}
	});
}
//活动信息
function searchProductListAvtivity(){
	var list=jQuery('p[id^=Activity_]');
	var id = new Array(list.length);
	var idStr="";
	for (var i = 0; i < list.length; i++) {
           id[i] = list[i].id;
           idStr+= list[i].id;
	}
	if(idStr==""){
		return;
	}
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!searchProductListAvtivityRe.action?productId='+id+'&channelSn='+channelsn,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				var index_productid=index.substring(0,index.indexOf("@"));
				if  (object != null && object != '') {
					var obj_str=object+"";
					var activity_title=obj_str.substring(0,obj_str.indexOf("@"));
					var activity_description=obj_str.substring(obj_str.indexOf("@")+1,obj_str.length);
					jQuery('#Diy_'+index_productid).show();
					jQuery('#Diy_Title_'+index_productid).show();
					document.getElementById("Diy_"+index_productid).innerHTML=activity_description;
					document.getElementById("Diy_Title_"+index_productid).innerHTML=activity_title;
					
					var activity_type = index.substring(index.indexOf("@")+1);
					var active_class;
					if (activity_type == "x") {
						active_class = " active_07";
					} else if (activity_type == "1") {
						active_class = " active_06";
					} else if (activity_type == "2") {
						active_class = " active_05";
					} else if (activity_type == "3") {
						active_class = " active_04";
					} else if (activity_type == "6") {
						active_class = " active_02";
					} else if (activity_type == "7") {
						active_class = " active_08";
					} else if (activity_type == "8") {
						active_class = " active_03";
					} else if (activity_type == "9") {
						active_class = " active_09";
					}
					document.getElementById("Diy_Title_"+index_productid).className += active_class;
				}
			});
		}
	});
}
    
		function chakan(str)
			{
				window.open(str);
				
			}
	
		function submitp(str) {
			jQuery.ajax({
				type : 'get',
				url : sinosoft.base+'/shop/favorites!add.action?productId=' + str,
				dataType : 'html',
				success : function(de) {

					if (de == 'notlogin') {
						artLogin();//使用弹出窗口登录
					} else {
							jQuery("#contrast_"+str).parent().parent().find(".shop_img_head").append("<span class='shop_suc_fk'>收藏成功</span>");
					jQuery(".shop_suc_fk").fadeOut(3000);
					}
				}
			});
		}
function productChange(){
	 /*产品信息交互*/ 
	var r_c = jQuery('.shop_box_r');
	var l_c = jQuery('.shop_box_l');
	var shop_c = jQuery('.shop_img_head'); 
	shop_c.hover(function() {
		jQuery(this).children(".shop_box_des").fadeIn("fast");
        },function(){
	   	jQuery(this).children(".shop_box_des").fadeOut("fast");
	 			  }
 			);
	r_c.click(function() {
        
        jQuery(this).siblings(".shop_box_con").animate({left: '-190px'}, "slow");

        jQuery(this).siblings(".shop_box_con2").animate({left: '+1px'}, "slow");
     jQuery(this).children().removeClass("shop_btns_r");
        jQuery(this).siblings(".shop_box_l").children().addClass("shop_btns_l");
    });
    l_c.click(
        function(){
            jQuery(this).siblings(".shop_box_con2").animate({left: '+190px'}, "slow");
            jQuery(this).siblings(".shop_box_con").animate({left: '+1px'}, "slow");
            jQuery(this).children().removeClass("shop_btns_l");
            jQuery(this).siblings(".shop_box_r").children().addClass("shop_btns_r");
            }
    );
	 //焦点图切换
	jQuery('#change_33 div.changeDiv').soChange({
		thumbObj:'#change_33 .ul_change_a2 span',
		thumbNowClass:'on',
		changeTime:4000//自定义切换时间为4000ms
	});
}

//与后台交互进行产品筛选
function quickSearchProduct(pageIndex){
	
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
	
	var tRequest = getParam();
	//jQuery.getJSON(sinosoft.base + '/shop/index_navigation!quickQueryProduct.action?callback=?',
	jQuery.getJSON(sinosoft.base + "/shop/index_navigation!quickQueryProductRe.action?callback=?&pageIndex="+pageIndex+tRequest+'&ProductsOrder='+ProductsOrder+'&activityFlag='+activityFlag,
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
//分页
function doQuickOrder2(num){
	jQuery("#load_con").show();
    jQuery("#load_con").html("<img src='"+sinosoft.front+"/images/loading.gif' />");
    quickSearchProduct(num);
}
function newQuickJump(){
	var JumpPage = document.getElementById('_PageBar_Index_').value;
	doQuickOrder2(JumpPage);
	
	recommendHide();
}
/*筛选条件折叠效果*/
var people_list = jQuery('input.inputs-no');
var people_btn = jQuery('#btns_add_ht');
var age_four = jQuery("#age_four").val();
var age_five = jQuery("#age_five").val();
var age_six = jQuery("#age_six").val();
if((age_four!=null && age_four!="")||(age_five!=null && age_five!="")||(age_six!=null && age_six!="")){
	people_btn.addClass("que_shai_s");
	people_list.show();
} 
people_btn.click(
            function(){
                    if(people_list.is(":visible")){
                        people_list.hide();
                        people_btn.removeClass("que_shai_s");
                    }else{
                        people_btn.addClass("que_shai_s");
                        people_list.show();
                }
            }
        )
function openCommnet(turl,param){
	if (param != null && param != '') {
		url = turl + "?" + param + "&count=count";
	} else {
		url=turl+"?count=count";
	}
	window.open(url,"_blank");
}




// 改版新增js
// 列表页div轮播
jQuery('#change_34 div.changeDiv').soChange({
thumbObj:'#change_34 .ul_change_a3 span',
thumbNowClass:'on',
slideTime:500,
changeTime:6000,
 overStop:true
});


jQuery(".m-list-tip2").taghoverbind();


//隐藏产品
jQuery('#radiolist div').eq(0).addClass("m-first-bznx");
    var Selectlist = jQuery('.radiolist div.bznx-no');
    var goggleBtn = jQuery('#zd_click');
    Selectlist.hide();

//点击筛选第一项 展开筛选条件
//jQuery('#radiolist div').eq(0).find('.hRadio').click(function(){
//         if (Selectlist.is(':visible')==false) {
//            goggleBtn.addClass('zd_sx_tog');
//            Selectlist.show();
//            goggleBtn.attr('title', '收起筛选条件')
//        }
//})



function selectshow(){
   var Selectlist = jQuery('.radiolist div.bznx-no');
    var goggleBtn = jQuery('#zd_click');
    Selectlist.hide();
    goggleBtn.click(function () {
        if (Selectlist.is(':visible')) {
            Selectlist.hide();
            goggleBtn.removeClass('zd_sx_tog');
            goggleBtn.attr('title', '展开筛选条件')
        } else {
            goggleBtn.addClass('zd_sx_tog');
            Selectlist.show();
            goggleBtn.attr('title', '收起筛选条件')
        }
    });
}