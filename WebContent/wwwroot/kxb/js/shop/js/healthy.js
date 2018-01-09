/**
* ------------------------------------------
* @make:崔世刚
* @version  1.0 
* @des：购买流程.
* ------------------------------------------
*/
jQuery(function(){
	/**
	 * 健康告知"下一步"
	 */
	jQuery("#bt_health").click(function(){ 
		showUI();
		if(!sure()){
			hideUI();
			return false;
		}
		var turl=sinosoft.base+"/shop/order_config_new!saveOrUpdateHealthInf.action",
	    /*前台加密两次，后台解密一次*/
		turl = encodeURI(turl);
		turl = encodeURI(turl); 
		 
			var options = { 
			url:turl, 
			async:true,
			type:"POST",  
			dataType: "json",
			resetForm:false,
		success: function(data){ 
			var order_healthySn = data.ORDERSN_HEALTHYSN;
			var productId = data.productId;
			var textAge = data.TextAge;
			var period = data.Period;
			var plan = data.Plan;
			var tFlag = data.Flag;
			var saveFlag = data.saveFlag;
			var kid = data.KID;
			var orderSn = data.orderSn;
			var orderId = data.orderId;
			var sourceFlag = data.sourceFlag;
			var feeYear = data.feeYear;
			var appLevel = data.appLevel;
			var appType = data.appType;
			var sex = data.Sex;
			var applicantBirthday = data.applicantBirthday;
			var complicatedFlag = data.complicatedFlag;
			var dutyTempSerials = data.dutyTempSerials;
			var pointExchangeFlag = data.pointExchangeFlag;
			var uURL = "";
			
			if(jQuery("#channelCode").val() != ''){
				uURL = "&channelCode="+jQuery("#channelCode").val();
			}
			
			if(jQuery("#typeFlag").val() != ''){
				uURL += "&typeFlag="+jQuery("#typeFlag").val();
			}
			
			if(jQuery("#Mult").val() != ''){
				uURL += "&Mult="+jQuery("#Mult").val();
			}
			
			var tURL = "/shop/order_config_new!buyNow.action?order_healthySn="+order_healthySn+"&productId="+productId+"&Flag=Suc"+"&textAge="+textAge+"&period="+period+"&plan="+plan+"&sourceFlag="+sourceFlag+"&feeYear="+feeYear+"&appLevel="+appLevel+"&appType="+appType+"&Sex="+sex+"&applicantBirthday="+applicantBirthday+"&complicatedFlag="+complicatedFlag+"&dutyTempSerials="+dutyTempSerials+"&pointExchangeFlag="+pointExchangeFlag+uURL;
			if(saveFlag=="updateHealthy"){
				tURL = "/shop/order_config_new!buyNowUpdate.action?order_healthySn="+order_healthySn+"&orderSn="+orderSn+"&orderId="+orderId+"&Flag=Suc&KID="+kid+"&status="+saveFlag+"&sourceFlag="+sourceFlag+"&feeYear="+feeYear+"&appLevel="+appLevel+"&appType="+appType+"&Sex="+sex+"&applicantBirthday="+applicantBirthday+"&complicatedFlag="+complicatedFlag+"&dutyTempSerials="+dutyTempSerials+"&pointExchangeFlag="+pointExchangeFlag+uURL;
				if (orderFlag != null && orderFlag != '') {
					tURL = (tURL +"&orderFlag="+orderFlag);
				}
			}
			hideUI();
			if(tFlag=="Err"){
				jQuery("#order_err").show();
				jQuery("#order_err").html(data.Msg);
			}else{
				window.location.href=sinosoft.base +tURL;
				
			}
		 }
		};  
	    jQuery('#resultForm').ajaxSubmit(options); 
		return false; 
	});
	/**
	 * 点击有部分情况
	 */
	jQuery("#bt_confirm").click(function(){ 
		art.dialog({
 		    icon: 'warning',
		    id: 'alertmes',
		    content: '不符合本产品的投保条件，请重新确认或购买其他产品',
		    button: [
		        {
		            name: '确认',
		            callback: function () {
		            	return true;
		            },
		            focus: true
		        },
		        {
		            name: '购买其他产品',
		            callback:function(){
		            	window.location.href = jQuery("#productyTypeFirstList").val()
		            }
		        }
		    ]
		});
	});
	
	jQuery(".line_ok").click(function(){
        art.dialog({
          title: '录入核保编码',
          padding:'20px 40px',
          id:"hb_con",
          content: '录入核保编码：<input type="text" name="" id="hb_text" class="hbtext" value="">',
          fixed: true,
          lock:true,
          ok: function () {
            var n = jQuery("#hb_text").val();
            if(n!=""){
            	jQuery("#UnderwritingOfflineCode").val(n);
            	var turl=sinosoft.base+"/shop/order_config_new!saveOrUpdateHealthInf.action",
        	    /*前台加密两次，后台解密一次*/
        		turl = encodeURI(turl);
        		turl = encodeURI(turl); 
        		 
        			var options = { 
        			url:turl, 
        			async:true,
        			type:"POST",  
        			dataType: "json",
        			resetForm:false,
        		success: function(data){ 
        			var order_healthySn = data.ORDERSN_HEALTHYSN;
        			var productId = data.productId;
        			var textAge = data.TextAge;
        			var period = data.Period;
        			var plan = data.Plan;
        			var tFlag = data.Flag;
        			var saveFlag = data.saveFlag;
        			var kid = data.KID;
        			var orderSn = data.orderSn;
        			var orderId = data.orderId;
        			var sourceFlag = data.sourceFlag;
        			var feeYear = data.feeYear;
        			var appLevel = data.appLevel;
        			var appType = data.appType;
        			var sex = data.Sex;
        			var applicantBirthday = data.applicantBirthday;
        			var complicatedFlag = data.complicatedFlag;
        			var dutyTempSerials = data.dutyTempSerials;
        			var pointExchangeFlag = data.pointExchangeFlag;
        			var uURL = "";
        			
        			if(jQuery("#channelCode").val() != ''){
        				uURL = "&channelCode="+jQuery("#channelCode").val();
        			}
        			
        			if(jQuery("#typeFlag").val() != ''){
        				uURL += "&typeFlag="+jQuery("#typeFlag").val();
        			}
        			
        			if(jQuery("#Mult").val() != ''){
        				uURL += "&Mult="+jQuery("#Mult").val();
        			}
        			if(jQuery("#UnderwritingOfflineCode").val() != ''){
        				uURL += "&UnderwritingOfflineCode="+jQuery("#UnderwritingOfflineCode").val();
        			}
        			var tURL = "/shop/order_config_new!buyNow.action?order_healthySn="+order_healthySn+"&productId="+productId+"&Flag=Suc"+"&textAge="+textAge+"&period="+period+"&plan="+plan+"&sourceFlag="+sourceFlag+"&feeYear="+feeYear+"&appLevel="+appLevel+"&appType="+appType+"&Sex="+sex+"&applicantBirthday="+applicantBirthday+"&complicatedFlag="+complicatedFlag+"&dutyTempSerials="+dutyTempSerials+"&pointExchangeFlag="+pointExchangeFlag+uURL;
        			if(saveFlag=="updateHealthy"){
        				tURL = "/shop/order_config_new!buyNowUpdate.action?order_healthySn="+order_healthySn+"&orderSn="+orderSn+"&orderId="+orderId+"&Flag=Suc&KID="+kid+"&status="+saveFlag+"&sourceFlag="+sourceFlag+"&feeYear="+feeYear+"&appLevel="+appLevel+"&appType="+appType+"&Sex="+sex+"&applicantBirthday="+applicantBirthday+"&complicatedFlag="+complicatedFlag+"&dutyTempSerials="+dutyTempSerials+"&pointExchangeFlag="+pointExchangeFlag+uURL;
        				if (orderFlag != null && orderFlag != '') {
        					tURL = (tURL +"&orderFlag="+orderFlag);
        				}
        			}
        			hideUI();
        			if(tFlag=="Err"){
        				if (jQuery(".me_error").length > 0) {
        	            	   jQuery(".me_error").text(data.Msg);
        	               } else {
        	            	   jQuery("#hb_text").after('<em class="me_error">'+data.Msg+'</em>');
        	               }
        			}else{
        				//art.dialog.get('hb_con').content('正在跳转...');
        				window.location.href=sinosoft.base +tURL;
        			}
        		 }
        		};  
        	    jQuery('#resultForm').ajaxSubmit(options); 
             
            }else{
               if (jQuery(".me_error").length > 0) {
            	   jQuery(".me_error").text("请填写核保编码");
               } else {
            	   jQuery("#hb_text").after('<em class="me_error">请填写核保编码</em>');
               }
             
              return false;
            }
           
            return false;
          },
        });
      });
});
function agreeCheck(){
	 var agreecheckbox =  document.getElementById("agreecheckbox");
	 if(agreecheckbox.checked = "checked" ){
		 document.getElementById("comfirmToPay").disabled="";
	 }else{
		 document.getElementById("comfirmToPay").disabled="disabled";
	 }
}
function  sure(){
	var k = 1;
	var proStr="";//提示串
	for(i=0;i<document.resultForm.length;i++) {
		if (document.resultForm.elements[i].type =="radio" )
		{ 
			if(document.resultForm.elements[i].checked == false && document.resultForm.elements[i+1].checked == false 
			   && document.resultForm.elements[i].disabled == false && document.resultForm.elements[i+1].disabled == false)		
			{
				proStr+=k+"," 
			}
			k++;
			i++;
		}
	} 
	if(proStr!=null&&proStr!=""){
		jQuery(".headylth-ciop").html("您的第"+proStr.substring(0,proStr.length-1)+"条告知没有选择，请您选择");
		jQuery(".healthy-conbog").show();
		jQuery(".btnarea100").hide();
		return false;
	}
	
	if(comCode!="1015"){
		if(!checkHealthInfo())  return false;
	} else {
		jQuery(".healthy-conbog").hide();
		jQuery(".btnarea100").show();
	}

	return true;
}
//健康告知校验，和众简单产品如果有一个选择了是那么，提示用户  add by  fhz
function checkHealthInfo() {
	 var m=1;
	 var proStr="";//提示串
	 for(i=0;i<document.resultForm.length;i++) {
		if (document.resultForm.elements[i].type =="radio" &&document.resultForm.elements[i].value=="Y"){ 
			if(document.resultForm.elements[i].checked==true){
		    proStr+=m+","
			}
			m++;
		}
	}
	if(proStr!=null&&proStr!=""){
		jQuery(".headylth-ciop").html("您的第"+proStr.substring(0,proStr.length-1)+"条告知不符合本产品的投保条件，请重新确认");
		jQuery(".healthy-conbog").show();
		jQuery(".btnarea100").hide();
		return false;
	}
	
	jQuery(".healthy-conbog").hide();
	jQuery(".btnarea100").show();
   return  true;
} 

jQuery(".radioCla").click(function(){
	jQuery(".healthy-conbog").hide();
	jQuery(".btnarea100").show();
});
function selectAll(flag)
{
	jQuery(".healthy-conbog").hide();
	jQuery(".btnarea100").show();
	for(i=0;i<document.resultForm.length;i++) 
	{
		if (document.resultForm.elements[i].type =="radio" )
		{ 
		   if(flag=="Y")
		   {
		       if(document.resultForm.elements[i].value=="Y")
		       {
		        document.resultForm.elements[i].checked =true;
		       }   
		   	  
		   }
		   else
		   {   
		       if(document.resultForm.elements[i].value=="N")
		       {
		         document.resultForm.elements[i].checked =true;
		       }  
		   }
		}
	}
}
/**
 * 遮罩层隐藏
 */
function hideUI(){
	jQuery.unblockUI();
	jQuery("#msg_3").hide();  
}
/**
 * 遮罩层显示
 */
function showUI(){
	jQuery.blockUI({
		overlayCSS:{backgroundColor:'#fff',opacity:0.0},
		showOverlay:true,
		message:""
	}); 
	jQuery("#msg_3").show();  
}
