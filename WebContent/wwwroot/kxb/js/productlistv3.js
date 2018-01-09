jQuery(function(){

		/*筛选条件折叠效果*/
			var Selectlist = jQuery('.radiolist div.bznx-no');//获取除了前2条筛选条件集合对象
			var goggleBtn = jQuery('#zd_click');//获取折叠显示按钮
			Selectlist.hide();
			goggleBtn.click(
				function(){
					if(Selectlist.is(":visible")){
						Selectlist.hide();
						goggleBtn.removeClass("zd_sx_tog");
						 goggleBtn.attr("title","展开筛选条件");
						}else{
						 goggleBtn.addClass("zd_sx_tog");
					     Selectlist.show();
						 goggleBtn.attr("title","收起筛选条件");
							}
					
					}
			);
		
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
	
	//加旅行者年龄输入域隐藏于显示控制
	var ageArr = new Array([3]);
	ageArr[0] = jQuery("#age_one").val();
	ageArr[1] = jQuery("#age_two").val();
	ageArr[2] = jQuery("#age_three").val();
	var period = jQuery("#period").val();
	var travelAddress = jQuery("#travelAddress").val();
	var tRequest="&ages="+ageArr+"&period="+period+"&travelAddress="+encodeURI(encodeURI(travelAddress));
	//var tRequest="&ages="+ageArr+"&period=8&travelAddress="+encodeURI(encodeURI(travelAddress));
	return tRequest;
}
/**
 * 列表也产品销量
 */
function salesVolumeLoad() {
	var list=jQuery('span[id^=SalesV_]');
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
		url: sinosoft.base+'/shop/sales_volume!search.action?productIds='+id,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
	        document.getElementById(index).innerHTML="已有  "+object+" 人投保";
			});
		}
	});
	var ulist=jQuery('span[id^=SalesU_]');
	var uid = new Array(ulist.length);
	var uidStr="";
	for (var i = 0; i < ulist.length; i++) {
		uid[i] = ulist[i].id;
        uidStr+= list[i].id;
	}
	if(uidStr==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!search.action?productIds='+uid,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
	       document.getElementById(index).innerHTML="已有  "+object+" 人投保";
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
	        document.getElementById(index).innerHTML=object;
			});
		}
	});
	var ulist=jQuery('a[id^=CommentU_]');
	var uid = new Array(ulist.length);
	var uidStr="";
	for (var i = 0; i < ulist.length; i++) {
		uid[i] = ulist[i].id;
        uidStr+= list[i].id;
	}
	if(uidStr==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!comment.action?productIds='+uid,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
	       document.getElementById(index).innerHTML=object;
			});
		}
	});
}
//满减活动信息
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
		url: sinosoft.base+'/shop/sales_volume!searchProductListAvtivity.action?productId='+id+'&channelSn='+channelsn,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				var index_productid=index.substring(0,index.indexOf("@"));
				var activity_type=index.substring(index.indexOf("@")+1,index.length);
				if(activity_type=="8"){
					var obj_str=object+"";
					var activity_title=obj_str.substring(0,obj_str.indexOf("@"));
					var activity_description=obj_str.substring(obj_str.indexOf("@")+1,obj_str.length);
					jQuery('#Diy_em_'+index_productid).show();
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
				}else{
					document.getElementById(index_productid).innerHTML=object;
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
	
	var searchProductFlag = jQuery("#searchProductFlag").val();
	var tRequest = getParam();
	//jQuery.getJSON(sinosoft.base + '/shop/index_navigation!quickQueryProduct.action?callback=?',
	jQuery.getJSON(sinosoft.base + "/shop/index_navigation!quickQueryProduct.action?callback=?&searchProductFlag="+searchProductFlag+"&pageIndex="+pageIndex+tRequest,
			function(data) {
			 if(data && data.status == '1'){
				    // 无产品提示
				    jQuery("#s_noshop").hide();
				    // 替换产品
					jQuery("#products").html(data.sb1);
					jQuery("#productsPageBar").html(data.sb3);
					// 拼装分页
			   } else if(data && data.status == '2'){
				     //针对第一次筛选有结果，而第二次没有筛选结构的情况
				     var notproduct = "<div class=\"noshop_img\"><img src=\"" + sinosoft.staticPath + "/images/searchfs_03.jpg\" width=\"128\" height=\"90\" /></div>";
				     notproduct += " <div class=\"noshop_des\"> <b>很抱歉，您需要的产品没有找到。</b>建议您更换筛选条件试一试</div>";
				     notproduct += " <div class=\"clear\"></div>";
				     notproduct += "  <p class=\"shop_titles\">我们为您精心挑选了以下产品：</p>";
					 jQuery("#s_noshop").html(notproduct);
					 jQuery("#s_noshop").show();
			   } else {
				     var notproduct = "<div class=\"noshop_img\"><img src=\"" + sinosoft.staticPath + "/images/searchfs_03.jpg\" width=\"128\" height=\"90\" /></div>";
				     notproduct += " <div class=\"noshop_des\"> <b>很抱歉，您需要的产品没有找到。</b>建议您更换筛选条件试一试</div>";
				     notproduct += " <div class=\"clear\"></div>";
				     notproduct += "  <p class=\"shop_titles\">我们为您精心挑选了以下产品：</p>";
					 jQuery("#s_noshop").html(notproduct);
					 jQuery("#s_noshop").show();
			   }
			 	
			  jQuery("#load_con").html("");
			  jQuery("#load_con").hide();
			  
			   if(state != 1){
					recommendHide();
				}
				state = 0;
				productChange();
			 }); 
}
//分页
function doQuickOrder2(num){
	jQuery("#load_con").show();
    jQuery("#load_con").html("<img src='" + sinosoft.staticPath + "/images/loading.gif' />");
    quickSearchProduct(num);
	
	recommendHide();
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