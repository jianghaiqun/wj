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
			searchProductListAvtivity();
			
	});
/**
 * 得到参数
 * @param url
 * @returns
 */
function getParam(){
	
	/*地址栏参数
	 * var url = unescape(window.location.href);
	var url = window.location.href;

	var tRequest='';
	if(url.indexOf("?")!=-1){
		var allargs = url.split("?")[1];
		if(allargs.indexOf("&")!=-1){
			var args = allargs.split("&");
			for(var i = 0; i < args.length; i ++) {
				var arg = args[i].split("=");
			    tRequest += "&"+arg[0]+"="+encodeURI(arg[1])+"";
			    //jQuery("#"+arg[0]).val(arg[1]);
			}
		}
	}*/
	//加旅行者年龄输入域隐藏于显示控制
	var ageArr = new Array([6]);
	ageArr[0] = jQuery("#age_one").val();
	ageArr[1] = jQuery("#age_two").val();
	ageArr[2] = jQuery("#age_three").val();
	ageArr[3] = jQuery("#age_four").val();
	ageArr[4] = jQuery("#age_five").val();
	ageArr[5] = jQuery("#age_six").val();
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
	        document.getElementById(index).innerHTML="销量：<i class=\"img_xl_num\">"+object+"</i>";
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
	       document.getElementById(index).innerHTML="已有"+object+"人投保";
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
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!searchProductListAvtivity.action?productId='+id,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				document.getElementById(index).innerHTML=object;
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
var ttFlag = true;
var emFlag = true;
jQuery("#lookplan").click(function(){
	
	emFlag = true;
	jQuery("input[id^='age']").each(function(){
		validAge(this);
		if(!ttFlag){
			return ;
		}
	});
	if(!ttFlag){
		return ;
	}
	jQuery("#travelAddress").each(function(){
		validAddress(this);
		if(!ttFlag){
			return ;
		}
	});
	if(!ttFlag){
		return ;
	}
	jQuery("#period").each(function(){
		validPeriod(this);
		if(!ttFlag){
			return ;
		}
	});
	if(!ttFlag){
		return ;
	}
	if(ttFlag&&!emFlag){
		quickSearchProduct(0);
	}else if(emFlag){
		jQuery("#age_one").addClass("que_shai_red");
	}
	
});
jQuery("input[id^='age']").blur(function(){
	validAge(this);
});
function validAge(obj){
	var reg = /^[0-9]*$/;
	var tValue = jQuery(obj).val();
	var nan = true;
	var nLen = tValue.length;
	if(tValue!=null && tValue!=""){
		emFlag = false;
		nan = reg.test(tValue);
		if(nLen>=3){
			jQuery(obj).val(jQuery(obj).val().substring(0, 2));
		}
		if(nan==false){
			jQuery(obj).addClass("que_shai_red");
			ttFlag = false;
			return ;
		}else{
			if(tValue<=0 || tValue>=100){
				jQuery(obj).addClass("que_shai_red");
				ttFlag = false;
				return ;
			}
		}
		if(nan){
			jQuery(obj).removeClass("que_shai_red");
			ttFlag = true;
		}
	}else{
		jQuery(obj).removeClass("que_shai_red");
	}
}
jQuery("#period").blur(function(){
	validPeriod(this);
});
function validPeriod(obj){
	var reg = /^[0-9]*$/;
	var tValue = jQuery(obj).val();
	var nan = true;
	var nLen = tValue.length;
	if(tValue!=null && tValue!=""){
		emFlag = false;
		nan = reg.test(tValue);
		if(!nan){
			jQuery(obj).addClass("que_shai_red");
			ttFlag = false;
		}
		if(tValue>365){
			jQuery(obj).val("365");
		}
		if(nan&&tValue<=365){
			jQuery(obj).removeClass("que_shai_red");
			ttFlag = true;
		}
	}else{
		jQuery(obj).removeClass("que_shai_red");
	}
}
/*jQuery("#travelAddress").blur(function(){
	validAddress(this);
});*/
function validAddress(obj){

	var reg1 =  /^[0-9\u4e00-\u9faf]+$/;
	var tValue = jQuery(obj).val();
	var nan = true;
	var nLen = tValue.length;
	if(tValue!=null && tValue!=""){
		emFlag = false;
		nan = reg1.test(tValue);
		if(nan==false){
			jQuery(obj).addClass("que_shai_red");
			ttFlag = false;
		}
		if(nan){
			jQuery(obj).removeClass("que_shai_red");
			ttFlag = true;
		}
	}else{
		jQuery(obj).removeClass("que_shai_red");
	}

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