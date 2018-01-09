//2014-03-14 保险公司选择
var cpslink=sinosoft.base+"/Transition/PinAnTransition.jsp";
var comname="中国平安";
var choose = false;
var comCode = 2007;
(function(window, $, undefined){
	//车险模拟
	jQuery('#car_xian > li > div').hide();
		jQuery("#car_xian li").hover(function(){
			jQuery(this).children().first().addClass("car_hover");
		    jQuery(this).children(".p_conss").show().end();
		},function(){
			jQuery(this).children().first().removeClass("car_hover");  
			jQuery(this).children(".p_conss").hide();
		}
	)
})(window, jQuery);

jQuery(".car_tb_corp").click(function () {
    jQuery(".car_tb_corp").removeClass("tb_active");
    jQuery(this).addClass("tb_active");
    if(jQuery(this).attr("rel")=="pa"){
		cpslink=sinosoft.base+"/Transition/PinAnTransition.jsp";
		comname="中国平安";
		choose = true; 
	}
	if(jQuery(this).attr("rel")=="tpy"){
		cpslink="http://www.ecpic.com.cn/cpicmall/fastCalculation_cpic/CpicFastCalculation.jsp?bSource=02&sourceName=%25E5%25BC%2580%25E5%25BF%2583%25E4%25BF%259D&source=533";
		comname="太平洋保险";
		comCode=2011;
	}
	if(jQuery(this).attr("rel")=="yg"){
		cpslink="http://chexian.sinosig.com/simplePremium/territory_marketing.jsp?utm_source=P05-kaixinbao&utm_campaign=car&areaCode=W06010003";
		comname="阳光保险";
		comCode=1087;
	}
	if(jQuery(this).attr("rel")=="dd"){
		cpslink="http://www.95590.cn/ebiz/view/onlinePartner/opBaseInfo.jsp?utm_source=kaixinbao";
		comname="大地保险";
		comCode=2021;
	}
	if(jQuery(this).attr("rel")=="tp"){
		cpslink="http://chexian.axatp.com/toPreparation.do?cityCode=310100&localProvinceCode=310000&departmentCode=5&linkResource=&selectPayChannel=&isAgent=0&isRenewal=&ecInsureId=&select_city=%C9%CF%BA%A3%CA%D0&planDefineId=3&=&rt=0&ms=";
		comname="安盛天平保险";
		comCode=2026;
	}
	if(jQuery(this).attr("rel")=="pa1"){
		cpslink="http://www.pingan.com/property_insurance/pa18AutoInquiry/";
		comname="中国平安";
	}
	if(jQuery(this).attr("rel")=="tpy1"){
		cpslink="http://www.ecpic.com.cn/mall/policy/policyInfo/view";
		comname="太平洋保险";
	}
	if(jQuery(this).attr("rel")=="yg1"){
		cpslink="http://chexian.sinosig.com/NetCar/carCustomService_forwardClaimProgressSearch.action?selectedModuleId=claimProgress";
		comname="阳光保险";
	}
	if(jQuery(this).attr("rel")=="dd1"){
		cpslink="http://www.95590.cn/ebiz/view/insuranceClaim/claimMain.jsp?showflag=1";
		comname="大地保险";
	}
	if(jQuery(this).attr("rel")=="tp1"){
		cpslink="http://www.axatp.com/claim/";
		comname="安盛天平汽车保险";
	}
});
/**
 * 立即报价方法
 * @param strlink 保险公司cps链接
 * @param strtitle 保险公司名称
 * @param pagelink 过渡页地址
 * @return
 */
function getNowCpslink(strlink,strtitle,pagelink){
	
		if(strlink=="" || strlink==null){
			strlink = jQuery("#comUrl").val();
		}
		if(strtitle=="" || strtitle==null){
			strtitle = jQuery("#comName").val();
		}
		if(pagelink=="" || pagelink==null){
			pagelink = jQuery("#comGuoDu").val();
		}
         if(strlink==000 || strlink == '#' || strlink == 'javascript:void(0);' ){
       	 //history.go(0);
        }else{
        	if("中国平安"==strtitle){
        		var win =window.open("");
        		win.open(cpslink);
        	}else{
        		 strlink = strlink.replace("?", "$wen$");
               	 var url = escape(strtitle);
               	 window.open(pagelink+'?'+strlink+'*'+url);
        	}
        }
}
/**
 * 过度页面跳转
 */
function guodu(pagelink){
	 var carUser = jQuery("#carUser").val();
	 var carPhone = jQuery("#carPhone").val();
	 var carNO = jQuery("#carNO").val();
	if("中国平安"!=comname){
	     if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
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
	    	 if(jQuery("#guidtype").attr("checked")){
             }else{
            	 if(carNO == "" || carNO == null){
            		 alert("车牌号不能为空");
            		 return;
            	 }
             }
	    	 jQuery.ajax({
					url: sinosoft.base+"/car/car_user!save.action?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO,
					dataType: "json",
					async: false,
					success: function(data) {
						 cpslink = cpslink.replace("?", "$wen$");
				    	 var url = escape(comname);
				    	 var win =window.open("");
				    	 win.location.href=pagelink+'?'+cpslink+'*'+url;
						}
					}); 
	    	 
	     }
	}else{
		jumpPa(carNO,carUser,carPhone);
	}
}

/**
 * 新过度页面跳转
 */
function guoduNew(){
	 var carUser = jQuery("#carUser").val();
	 var carPhone = jQuery("#carPhone").val();
	 var carNO = jQuery("#carNO").val();
	if("中国平安"!=comname){
	     if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
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
	    	 if(jQuery("#guidtype").attr("checked")){
             }else{
            	 if(carNO == "" || carNO == null){
            		 alert("车牌号不能为空");
            		 return;
            	 }
             }
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
 * 立即报价方法
 * @param strlink 保险公司cps链接
 * @param strtitle 保险公司名称
 * @param pagelink 过渡页地址
 * @return
 */
function getComNowCpslink(strlink,strtitle,pagelink){
	 var carUser = jQuery("#carUser").val();
	 var carPhone = jQuery("#carPhone").val();
	 var carNO = jQuery("#carNO").val();
	if("中国平安"!=strtitle){
        if(strlink==000 || strlink == '#' || strlink == 'javascript:void(0);' ){
       	 //history.go(0);
        }else{
        	 carUser = encodeURIComponent(encodeURIComponent(carUser));
	    	 carNO = encodeURIComponent(encodeURIComponent(carNO));
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
	    	 if(jQuery("#guidtype").attr("checked")){
             }else{
            	 if(carNO == "" || carNO == null){
            		 alert("车牌号不能为空");
            		 return;
            	 }
             }
	    	 jQuery.ajax({
					url: sinosoft.base+"/car/car_user!save.action?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO,
					dataType: "json",
					async: false,
					success: function(data) {
						strlink = strlink.replace("?", "$wen$");
				       	 var url = escape(strtitle);
				       	 window.open(pagelink+'?'+strlink+'*'+url);
						}
					}); 
        }
	}else{
		jumpPa(carNO,carUser,carPhone);
	}
}


/**
 * 新立即报价方法
 * @param strlink 保险公司cps链接
 * @param strtitle 保险公司名称
 * @param pagelink 过渡页地址
 * @return
 */
function getComNowCpslinkNew(strlink,strtitle,pagelink){
	 var carUser = jQuery("#carUser").val();
	 var carPhone = jQuery("#carPhone").val();
	 var carNO = jQuery("#carNO").val();
	if("中国平安"!=strtitle){
        if(strlink==000 || strlink == '#' || strlink == 'javascript:void(0);' ){
       	 //history.go(0);
        }else{
        	 carUser = encodeURIComponent(encodeURIComponent(carUser));
	    	 carNO = encodeURIComponent(encodeURIComponent(carNO));
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
	    	 if(jQuery("#guidtype").attr("checked")){
             }else{
            	 if(carNO == "" || carNO == null){
            		 alert("车牌号不能为空");
            		 return;
            	 }
             }
	    	 jQuery.ajax({
					url: sinosoft.base+"/car/car_user!save.action?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO,
					dataType: "json",
					async: false,
					success: function(data) {
						   var win =window.open("");
				    	 win.location.href=pagelink;
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
	win.location.href=cpslink+"?carUser="+carUser+"&carPhone="+carPhone+"&carNO="+carNO+"&CarProperty="+check+"&driveCity="+driveCity;

}


/**
 * 中间页跳转到维析
 */
function MiddlePA(pagelink){
	var carUser = encodeURIComponent(encodeURIComponent(jQuery("#CarOwner").val()));
	var Phone = jQuery("#ContactPhone").val();
	var carNO = encodeURIComponent(encodeURIComponent(jQuery("#carnum").val()));
	var InsuranceDate = jQuery("#InsuranceDate").val();
	var BuyDate = jQuery("#BuyDate").val();
	var carValue = jQuery("#CarValue").val();
	var carProperty= jQuery("#CarProperty").val();
	var Email = encodeURIComponent(encodeURIComponent(jQuery("#ContactEmail").val()));
	var carAddress = encodeURIComponent(encodeURIComponent(jQuery("#sltCity").val()));
	var insureYear = jQuery("#InsureYear").val();
	var sltProvince = encodeURIComponent(encodeURIComponent(jQuery("#sltProvince").val()));
	var cpsname = encodeURIComponent(encodeURIComponent(jQuery("#cpsname").val()));
	jQuery.ajax({
		url: sinosoft.base+"/car/transition!saveMiddle.action?carUser="+carUser+"&carPhone="+Phone+"&carNO="+carNO
		+"&InsuranceDate="+InsuranceDate+"&BuyDate="+BuyDate+"&carValue="+carValue+"&carProperty="+carProperty
		+"&Email="+Email+"&carAddress="+carAddress+"&InsureYear="+insureYear+"&sltProvince="+sltProvince+"&cpsname="+cpsname,
		dataType: "json",
		async: false,
		success: function(data) {
			var str = data.jumpPath;
			window.location.href=str;
			}
		});
}





/**
 * 过度页面跳转
 */
function guoduNow(pagelink){
	if("中国平安"!=strtitle){
	     if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
	    	 cpslink = cpslink.replace("?", "$wen$");
	    	 var url = escape(comname);
	    	 var win =window.open("");
	    	 win.location.href=pagelink+'?'+cpslink+'*'+url;
	     }
	}else{
		win.location.href=pagelink;
	}
	
}
function navHeight(){
	var type=jQuery("#type").val();
	jQuery(".orange").removeClass("orange");
	if(type=="A"){
		jQuery("#A").addClass("on");
	}
	if(type=="B01"){
		jQuery("#B01").find('a').addClass("on");
	}
	if(type=="B02"){
		jQuery("#B02").find('a').addClass("on");
	}
	if(type=="B03"){
		jQuery("#B03").find('a').addClass("on");
	}
	if(type=="B04"){
		jQuery("#B04").find('a').addClass("on");
	}
	if(type=="B05"){
		jQuery("#B05").find('a').addClass("on");
	}
	if(type=="C"){
		jQuery("#C").addClass("on");
	}
	if(type=="D"){
		jQuery("#D").addClass("on");
	}
	if(type=="E"){
		jQuery("#E").addClass("on");
	}
}
function navHeight1(){
	jQuery("#A").addClass("on");
	jQuery(".orange").removeClass("orange");
	 jQuery.ajax({
			url: sinosoft.base+"/car/car_user!show.action",
			dataType: "json",
			async: false,
			success: function(data) {
				var obj = eval(data);
				jQuery("#cainiao").html("("+obj.cainiao+")");
				jQuery("#man").html("("+obj.OldDriver+")");
				jQuery("#women").html("("+obj.women+")");
				}
			}); 
}
function changeSum(type){
	jQuery.ajax({
		url: sinosoft.base+"/car/car_user!change.action?type="+type,
		dataType: "json",
		async: false,
		success: function(data) {
			var obj = eval(data);
			jQuery("#cainiao").html("("+obj.cainiao+")");
			jQuery("#man").html("("+obj.OldDriver+")");
			jQuery("#women").html("("+obj.women+")");
			}
		}); 
}