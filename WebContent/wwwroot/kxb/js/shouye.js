
/*
*@des:首页调用js
*@maker:dongqi.guo
*@time:2013-5-14-15:20
*/
/*快速导航*/
	jQuery(document).ready(function(){
allowScreen();
});
jQuery(window).resize(function(){
allowScreen();
});

function allowScreen(){
    var SCREENWIDTH_ALLOW = 1250;
var clientWidth = document.body.clientWidth;
if(clientWidth <= SCREENWIDTH_ALLOW){
document.getElementById("max_style").className="x1200";
document.getElementById("controlCss").href= sinosoft.cssPath + "/main_1200.css?v=124";
}else{
document.getElementById("max_style").className="";
document.getElementById("controlCss").href="";
}
} 
/*
function mobile_distinguish(url){

    var thismobileOS=navigator.platform;
    //var mobileOS=new Array("iPhone","iPod","iPad","android","Nokia","Symbian","Windows Phone","Phone","Linux armv71","BlackBerry");
    var mobileOS=new Array("iPhone","iPod","android","Nokia","Symbian","Windows Phone","Phone","Linux armv71","BlackBerry");


	for(var i=0;i<mobileOS.length;i++){
        if(thismobileOS.match(mobileOS[i])){
            window.location=url;
        }
    }
 
	//ipad 当pc端处理
    /*if(navigator.platform.indexOf('iPad') != -1){
        window.location=url;
    }*/
  /*
    var check = navigator.appVersion;
    if( check.match(/linux/i) ){

        if(check.match(/mobile/i) || check.match(/X11/i)){
            window.location=url;
        }
    }


    Array.prototype.in_array = function(e){
        for(i=0;i<this.length;i++){
            if(this[i] == e){
                return true;
            }
        }
        return false;
    }
}
*/
       

/*
var qsmenu = {};
qsmenu.init = function() {
    var index_menus = document.getElementById("index_menu");
    this.li = index_menus.getElementsByTagName("li");
	for (var b = 0; b < this.li.length; b++) {
		var a = this.li[b];
		a.cName = a.className;
		if (a.getElementsByTagName("dl")[0]) {
			a.submenu = a.getElementsByTagName("dl")[0].parentNode.parentNode;
		}
		if (a.submenu) {
			a.onmouseover = function() {
				this.className = "maintainHover";
				this.submenu.style.display = "";
			};
			a.onmouseout = function() {
				this.className = this.cName;
				this.submenu.style.display = "none";
			};
		}
	}
};

*/

function herf(){
		   window.location.href="${site.url}/hzpp/{catlog.SupplierCode}/index.shtml";
}
var crcont_content = jQuery("#crcont").html();
var crcont_n=(crcont_content.toLowerCase().split('<img')).length-1;
var crcont_count = 4;
if(crcont_n%2==0){
	crcont_count = crcont_n/2;
} else {
	crcont_count = (crcont_n + 1)/2;
}
jQuery("#crcont").width(crcont_count * 130);
var Speed = 10; // 速度(毫秒)
var Space = 5; // 每次移动(px)
var PageWidth = 126; // 翻页宽度
var fill = 0; // 整体移位
var MoveLock = false;  
var MoveTimeObj;  
var Comp = 0;  
var AutoPlayObj = null;  
GetObj("List2").innerHTML = GetObj("List1").innerHTML;  
GetObj('ISL_Cont').scrollLeft = fill;  
GetObj("ISL_Cont").onmouseover = function(){clearInterval(AutoPlayObj);};  
GetObj("ISL_Cont").onmouseout = function(){};  
function GetObj(objName){
	if(document.getElementById){
		return eval('document.getElementById("'+objName+'")');
	}else{
		return eval('document.all.'+objName);
	}
}
function AutoPlay(){ // 自动滚动
	clearInterval(AutoPlayObj);  
	AutoPlayObj = setInterval('ISL_GoDown();ISL_StopDown();',5000); // 间隔时间
}  
function ISL_GoUp(){ // 上翻开始
	if(MoveLock) return;  
	clearInterval(AutoPlayObj);  
	MoveLock = true;  
	MoveTimeObj = setInterval('ISL_ScrUp();',Speed);  
}  
function ISL_StopUp(){ // 上翻停止
	clearInterval(MoveTimeObj);  
	if(GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0){  
	Comp = fill - (GetObj('ISL_Cont').scrollLeft % PageWidth);  
	CompScr();  
	}else{  
	MoveLock = false;  
	}  
}  
function ISL_ScrUp(){ // 上翻动作
	if(GetObj('ISL_Cont').scrollLeft <= 0){GetObj('ISL_Cont').scrollLeft = GetObj('ISL_Cont').scrollLeft + GetObj('List1').offsetWidth;}  
	GetObj('ISL_Cont').scrollLeft -= Space ;  
}  
function ISL_GoDown(){ // 下翻
	clearInterval(MoveTimeObj);  
	if(MoveLock) return;  
	clearInterval(AutoPlayObj);  
	MoveLock = true;  
	ISL_ScrDown();  
	MoveTimeObj = setInterval('ISL_ScrDown()',Speed);  
}  
function ISL_StopDown(){ // 下翻停止
	clearInterval(MoveTimeObj);  
	if(GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0 ){  
	Comp = PageWidth - GetObj('ISL_Cont').scrollLeft % PageWidth + fill;  
	CompScr();  
	}else{  
	MoveLock = false;  
	}  
}  
function ISL_ScrDown(){ // 下翻动作
	if(GetObj('ISL_Cont').scrollLeft >= GetObj('List1').scrollWidth){GetObj('ISL_Cont').scrollLeft = GetObj('ISL_Cont').scrollLeft - GetObj('List1').scrollWidth;}  
	GetObj('ISL_Cont').scrollLeft += Space ;  
}  
function CompScr(){  
	var num;  
	if(Comp == 0){MoveLock = false;return;}  
	if(Comp < 0){ // 上翻
	if(Comp < -Space){  
	   Comp += Space;  
	   num = Space;  
	}else{  
	   num = -Comp;  
	   Comp = 0;  
	}  
	GetObj('ISL_Cont').scrollLeft -= num;  
	setTimeout('CompScr()',Speed);  
	}else{ // 下翻
	if(Comp > Space){  
	   Comp -= Space;  
	   num = Space;  
	}else{  
	   num = Comp;  
	   Comp = 0;  
	}  
	GetObj('ISL_Cont').scrollLeft += num;  
	setTimeout('CompScr()',Speed);  
	}  
}  


 jQuery(function(){
 	/*wap站跳转*/
 	//mobile_distinguish("http://wap.kaixinbao.com");
 	/*首页新版快速导航*/
    jQuery(".que_nav_zk").hover(
          function(){
              jQuery(this).addClass("que_hover").end();
              jQuery(this).find(".que_lv_box").show();
            
            
          },function(){
              jQuery(this).removeClass("que_hover").end();
              jQuery(this).find(".que_lv_box").hide();
              jQuery("#suggest").hide();
          }
      )

			  /*筛选条件折叠效果*/
	var people_list = jQuery('input.inputs-no');
	var people_btn = jQuery('#btns_add_ht');
	people_list.hide();
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
			//焦点图切换
			jQuery('#change_33 div.changeDiv').soChange({
			thumbObj:'#change_33 .ul_change_a2 span',
			thumbNowClass:'on',
			autoChange:true,
			overStop:true,
			slideTime:0, 
			botPrev:'#change_33 .a_last', 
			botNext:'#change_33 .a_next'
			});
			change_width(); //加载焦点图后运行宽度修正；
			jQuery(window).resize(function(){
			change_width();//焦点轮播导航宽度修正
			});
			 
			jQuery("#change_33").hover(
			function(){ 
			jQuery(".a_last").addClass("a_last_hover");
			jQuery(".a_next").addClass("a_last_hover");}
			,function(){
			jQuery(".a_last").removeClass("a_last_hover");
			jQuery(".a_next").removeClass("a_last_hover");
			}
			)
			function change_width(){
			var SCREENWIDTH_ALLOW = 1250; 
			var nav_w = jQuery(".ul_change_a2 li").length;
			var clientWidth = document.body.clientWidth;
			var span_w = jQuery(".ul_change_a2 li span");
			if(clientWidth >= SCREENWIDTH_ALLOW){
			switch(nav_w)
			{
			case 1:  span_w.width(928);    break; 
			case 2:  span_w.width(454);    break; 
			case 3:  span_w.width(294.6);  break; 
			case 4:  span_w.width(216);    break; 
			case 5:  span_w.width(169);    break; 
			case 6:  span_w.width(137.1);  break; 
			default: span_w.width(100);    break; 
			}
			}else{
			switch(nav_w)
			{
			case 1:  span_w.width(717);    break; 
			case 2:  span_w.width(347);    break; 
			case 3:  span_w.width(224);  break; 
			case 4:  span_w.width(163);    break; 
			case 5:  span_w.width(126);    break; 
			case 6:  span_w.width(102);  break; 
			default: span_w.width(100);    break; 
			}
			}
			 
			 
			}




		
	/*热销排行榜模块特效*/
	   jQuery('#hot_nav li span').mouseover(function(){curLi=jQuery(this); tagbind('hot_nav','hot_con','div',curLi) ;})
	/*去掉热销榜最后一个虚线*/
	   jQuery('#con_hot_1 dl').last().addClass("hot_clear_bors");
	   jQuery('#con_hot_2 dl').last().addClass("hot_clear_bors");
	   jQuery('#con_hot_3 dl').last().addClass("hot_clear_bors");
	   jQuery('#con_hot_4 dl').last().addClass("hot_clear_bors");
	/*显示更多产品信息*/ 
   jQuery(".hot_lists").mousemove(function(){
	   jQuery(this).siblings().children(".hot_content").css("display","none").end();
	   jQuery(this).siblings().children(".hot_titiles").children(".title_num").removeClass("select_num").end();
       jQuery(this).children(".hot_content").css("display","block").end();
	   jQuery(this).children(".hot_titiles").children(".title_num").addClass("select_num").end();
  });
    
	
	/*车险模拟select*/
	   jQuery('#car_tag_con > li').hide();
	   
	jQuery(".car_select_box").click(function(event){   
				event.stopPropagation();
				jQuery(this).find(".car_option").toggle();
				jQuery(this).parent().siblings().find(".car_option").hide();
			});
			jQuery(document).click(function(event){
				var eo=jQuery(event.target);
				if(jQuery(".car_select_box").is(":visible") && eo.attr("class")!="option" && !eo.parent(".car_option").length)
				jQuery('.car_option').hide();									  
			});
	
			jQuery(".car_option a").click(function(){
				var value=jQuery(this).text();
				jQuery(this).parent().siblings(".car_select_txt").text(value);
				
				  jQuery('#car_tag_con > li:eq('+ jQuery('#car_tag a').index(jQuery(this)) +')').show().siblings().hide();
				jQuery("#car_select_value").val(value)
			 })

  
  
	
	 
	
	   
	 })
/**
  * 循环报价方法
  * cpslink 全局变量保险公司cps链接
  * getlink 获取链接方法
  * comname 保险公司名称
  */
 var cpslink="#";
 var comname="";
 function getlink(strlink,strtitle){
	 cpslink=strlink;
	 comname=strtitle;
 }
 /**
  * 过度页面跳转
  */
 function guodu(pagelink){
	 if("平安车险"!=comname){
	     if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
	    	 cpslink = cpslink.replace("?", "$wen$");
	    	 var url = escape(comname);
	    	 var win =window.open("");
	    	 win.location.href=pagelink+'?'+cpslink+'*'+url;
	     }
	 }else{
		 var win =window.open("");
		 win.location.href=cpslink;
	 }
	
 }
 
 /**
  * 新过度页面跳转
  */
 function guoduNew(){
	 var url = comname;
	 if(comname.indexOf("PinAnTransition.jsp")<0){
	     if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
	    	 var win =window.open("");
	    	 win.location.href=url;
	     }
	 }else{
		 var win =window.open("");
		 win.location.href=cpslink;
	 }
	
 }
 
 
 jQuery(function(){
	   var curLi;
	   /*首页模块图片懒加载*/

		  jQuery("img.lazy").show().lazyload(); 
	/*tag切换方法初始化*/
	 jQuery('#in_s_nav li span').mouseover(function(){curLi=jQuery(this); tagbind('in_s_nav','i_new_max','div',curLi);})
	 jQuery('#in_s_nav2 li span').mouseover(function(){curLi=jQuery(this); tagbind('in_s_nav2','i_new_max2','div',curLi);})
	 jQuery('#in_s_nav3 li span').mouseover(function(){curLi=jQuery(this); tagbind('in_s_nav3','i_new_max3','div',curLi);})
	 jQuery('#in_s_navs1 li span').mouseover(function(){curLi=jQuery(this); tagbind('in_s_navs1','lv_box','div',curLi);jQuery("img.lazy").show().lazyload({   skip_invisible : false});})
	 jQuery('#in_s_navs2 li span').mouseover(function(){curLi=jQuery(this); tagbind('in_s_navs2','lv_box2','div',curLi);jQuery("img.lazy").show().lazyload({   skip_invisible : false});})
	 jQuery('#in_s_navs3 li span').mouseover(function(){curLi=jQuery(this); tagbind('in_s_navs3','lv_box3','div',curLi);jQuery("img.lazy").show().lazyload({ skip_invisible : false});})
	 jQuery('#in_s_nav4 li span').mouseover(function(){curLi=jQuery(this); tagbind('in_s_nav4','i_new_max4','div',curLi);})
	 });
	
	
	/*tag切换*/
	      	function tagbind(nav,con,mode,curLi) {
    curLi.addClass('selected').parent().siblings().find('span').removeClass('selected');
	  jQuery('#' + con +' > '+ mode +':eq('+ jQuery('#' +nav + ' li').index(curLi.parent()) +')').show().siblings().hide();
  };
 /*合作品牌*/
 jQuery('#min_tag_hzpp1 li em').click(
	function()
  {
      jQuery(this).addClass('select_tab_hzpp1').parent().siblings().find('em').removeClass('select_tab_hzpp1');
      jQuery('#tag_box_hzpp1 > ul:eq('+ jQuery('#min_tag_hzpp1 li').index(jQuery(this).parent()) +')').show().siblings().hide();
	  

  });
 /**
  * 得到参数
  * @param url
  * @returns
  */
 function getParam(){
 	
 	//加旅行者年龄输入域隐藏于显示控制
	var age = new Array([3]);
	var age_num = 0;
	jQuery('#select_age > li').each(function(){
		    var tClass = jQuery(this).attr('class');
		    if(tClass.indexOf("age_select")!=-1){
		    	age[age_num] = jQuery(this).attr("id");
		    }else{
		    	age[age_num]="";
		    }
		    age_num =parseInt(age_num)+1;
	});
 	var age_one = age[0];
 	var age_two = age[1];
 	var age_three = age[2];
 	var period = jQuery("#period").val();
 	var travelAddress = jQuery("#travelAddress").val();
 	var tRequest="&age_three="+age_three+"&age_two="+age_two+"&age_one="+age_one+"&period="+period+"&travelAddress="+travelAddress;
 	return tRequest;
 }
 /*查看计划*/
var ttFlag = true;
var emFlag = true;
jQuery("#lookplan").click(function(){ 
	
	emFlag = true;
	validAge(this);
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
		var tRequest = getParam();
		window.open (sinosoft.base+"/Search/newlist.jsp?1=1"+tRequest , "_blank");
	}else if(emFlag){
		jQuery("#age_one").addClass("que_shai_red");
	}
		
});
/*jQuery("input[id^='age']").blur(function(){
	validAge(this);
});*/
function validAge(obj){
	
	var nonFlag = false;
	jQuery('#select_age > li').each(function(){
	    var tClass1 = jQuery(this).attr("class");
	    if(tClass1.indexOf("age_select")!=-1){
	    	nonFlag = true;
	    }
	});
	if(!nonFlag){ttFlag = false;}else{emFlag=false;}
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
jQuery('#select_age > li').click(function(){
    jQuery(this).toggleClass("age_select");
});

var companyName = "中国平安";
var forlink = "/Transition/PinAnTransition.jsp";

// add 车险跳转
jQuery('.car_tb_corp').click(function () {
    jQuery('.car_tb_corp').removeClass('tb_active');
    jQuery(this).addClass('tb_active');
    if (jQuery(this).attr('rel') == 'pa') {
    	forlink = '/Transition/PinAnTransition.jsp';
        companyName = '中国平安';
        choose = true;
    }
    if (jQuery(this).attr('rel') == 'tpy') {
    	forlink = 'http://www.ecpic.com.cn/cpicmall/fastCalculation_cpic/CpicFastCalculation.jsp?bSource=02&sourceName=%25E5%25BC%2580%25E5%25BF%2583%25E4%25BF%259D&source=533';
        companyName = '太平洋保险';
        comCode = 2011;
    }
    if (jQuery(this).attr('rel') == 'yg') {
    	forlink = 'http://chexian.sinosig.com/simplePremium/territory_marketing.jsp?utm_source=P05-kaixinbao&utm_campaign=car&areaCode=W06010003';
        companyName = '阳光保险';
        comCode = 1087;
    }
    if (jQuery(this).attr('rel') == 'dd') {
    	forlink = 'http://www.95590.cn/ebiz/view/onlinePartner/opBaseInfo.jsp?utm_source=kaixinbao';
        companyName = '大地保险';
        comCode = 2021;
    }
    if (jQuery(this).attr('rel') == 'tp') {
    	forlink = 'http://chexian.axatp.com/toPreparation.do?cityCode=310100&localProvinceCode=310000&departmentCode=5&linkResource=&selectPayChannel=&isAgent=0&isRenewal=&ecInsureId=&select_city=%C9%CF%BA%A3%CA%D0&planDefineId=3&=&rt=0&ms=';
        companyName = '安盛天平保险';
        comCode = 2026;
    }
    if (jQuery(this).attr('rel') == 'pa1') {
    	forlink = 'http://www.pingan.com/property_insurance/pa18AutoInquiry/';
        companyName = '中国平安';
    }
    if (jQuery(this).attr('rel') == 'tpy1') {
    	forlink = 'http://www.ecpic.com.cn/mall/policy/policyInfo/view';
        companyName = '太平洋保险';
    }
    if (jQuery(this).attr('rel') == 'yg1') {
    	forlink = 'http://chexian.sinosig.com/NetCar/carCustomService_forwardClaimProgressSearch.action?selectedModuleId=claimProgress';
        companyName = '阳光保险';
    }
    if (jQuery(this).attr('rel') == 'dd1') {
    	forlink = 'http://www.95590.cn/ebiz/view/insuranceClaim/claimMain.jsp?showflag=1';
        companyName = '大地保险';
    }
    if (jQuery(this).attr('rel') == 'tp1') {
    	forlink = 'http://www.axatp.com/claim/';
        companyName = '安盛天平汽车保险';
    }
});
	 

/**
 * 新过度页面跳转
 */
function gainQuote(){
	 var carUser = jQuery("#carUser").val();
	 var carPhone = jQuery("#carPhone").val();
	 var carNO = jQuery("#carNO").val();
	 carUser = encodeURIComponent(encodeURIComponent(jQuery("#carUser").val()));
	 carNO = encodeURIComponent(encodeURIComponent(jQuery("#carNO").val()));
	 if(jQuery("#carUser").val().search(/^[\u0391-\uFFE5\w]+$/)==-1){
		 alert("输入姓名有误");
		 return;
	 }
	 var cellphone=/^(((1[0-9]{1}[0-9]{1}))+\d{8})$/;
	 if(!cellphone.test(carPhone))
	 {
	    alert('请输入有效的手机号码！');
	    return false;
	 }
	if("中国平安"!=companyName){
	     if(forlink==000 || forlink == '#' || forlink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
	    	 jQuery.ajax({
					url: sinosoft.base+"/car/car_user!save.action?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO,
					dataType: "json",
					async: false,
					success: function(data) {
				    	 var win =window.open("");
				    	 win.location.href=sinosoft.base+"/shop/shopping_cart!goToCompany.action?company="+comCode;
						}
					}); 
	    	 
	     }
	}else{
		jumpPa(carNO,carUser,carPhone);
	}
}

/**
 * 跳转到中间页jsp
 */
function jumpPa(carNO,carUser,carPhone){
	var check = 0;
	if($("#guidtype").attr("checked") == true){
		check = 1;
	}
	var driveCity = "";
	if(carNO != "" && check == 0){
		jQuery.ajax({
			url: sinosoft.base+"/car/transition!queryDrive.action?carNO="+carNO,
			dataType: "json",
			async: false,
			success: function(data) {
				driveCity = data.cityName;
				}
			}); 
	}
	carUser = encodeURIComponent(encodeURIComponent(carUser));
	carNO = encodeURIComponent(encodeURIComponent(carNO));
	driveCity = encodeURIComponent(encodeURIComponent(driveCity));
	var win =window.open("");
	win.location.href=sinosoft.base+forlink+"?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO+"&CarProperty="+check+"&driveCity="+driveCity;

}

	 
	 