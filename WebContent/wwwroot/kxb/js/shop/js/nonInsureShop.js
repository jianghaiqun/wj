jQuery(document).ready(function() {

  jQuery("#accept_em").click(function(){
            jQuery("#popup_area").show();
    jQuery("#dark").show();
  })

jQuery("#btnOK").click(function(){
        jQuery("#popup_area").hide();
    jQuery("#dark").hide();
    jQuery("#accept_b").attr("checked","checked");
})
	//预定须知弹层关闭
	jQuery("#popup_area > .close").click(function() {
		jQuery("#popup_area").hide();
		jQuery("#dark").hide();
	});

});

/**
 * 根据长期险的证件有效期控制截止日期，当勾选长期时，截止日期为不可编辑并清空内容
 */
function CheckEndID(applicantEndID,checkid){
	if(applicantEndID.length && applicantEndID.length>0){
		if(jQuery(checkid).attr('checked')){
			jQuery(applicantEndID).val("");
			jQuery(applicantEndID).attr("disabled",true);
			jQuery(applicantEndID).removeAttr("verify");
			
			if(jQuery(applicantEndID).parent().children("label").next().is('i')){
				jQuery(applicantEndID).parent().children("i").remove();
			}
			jQuery(applicantEndID).parent().children("label").after(jQuery(" <i class=\"yz_mes_yes\">  </i>"));
	    }else{
	    	
	    	jQuery(applicantEndID).attr("disabled",false);
	    	jQuery(applicantEndID).attr("verify","证件有效日期|NOTNULL");
	    }
	}
}

function checkInsID(){
	var t1=true; 
	var flag = true;
	var tID="";
	jQuery("select[id^='identityType']").each(function(){
		var relation_id = jQuery(this).attr("id"); 
	    var _id ="";
		if(relation_id.indexOf("_")!=-1){
		    _id = "_"+relation_id.split("_")[1];
		}
		var t2=idcheck('identityType'+_id,'identityId'+_id,'birthday'+_id,'sex'+_id,"1"); 
	 	if(!t2){jQuery('#identityId'+_id).focus();tID = "identityId"+_id;flag = false;return false;}
	}); 
	if(!flag){jQuery('#'+tID).focus();return false;};
	return true;
}
// 订单保存
$("#dosubmit").bind("click touchstart", function(e) {
	e.preventDefault();
		
	if(!verifyInput3()){return false;}; 
	if(!checkInsID()){return false;}
	if(!$('#accept_b').attr('checked')){
		 jQuery('#accept_b').parent().addClass("red");
		 art.dialog.alert("您还没有勾选旅游预定条款，选择后才能购买哦");
		 return ;
	}
	jQuery("#msg_1").show();
    $("#order_form").ajaxSubmit( {
		type : "post",
		url : "../shop/saveorder.shtml",
		dataType : "json",
		beforeSend:function(){
			jQuery("#msg_1").show();
			
		} , 
		success : function(data) {
			jQuery("#msg_1").hide();
			if(data.status=='1'){
				art.dialog.alert(data.msg);
			}else{
				window.location.href=sinosoft.tripPayBase+"/shop/payonline.shtml?orderSn="+data.data.orderSn+"&productId="+data.data.productId+"&KID="+data.data.KID
			} 
		},
		error : function() {
			jQuery("#orderErrors").show();
			jQuery("#orderErrors").html("矮油，出了点小状况①,点击<a href=\"javascript:void(0);\" vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\"><这里></a>！请客服帮帮忙吧！");
		}
	});
})

$("#saveTravelPeo").bind("click touchstart", function(e) {
	e.preventDefault();
	if(!verifyInput3()){return false;}; 
	if(!checkInsID()){return false;}
	jQuery("#msg_1").show();
    $("#orderInfoForm").ajaxSubmit( {
		type : "post",
		url : "../shop/savetravelpeopleinfo.shtml",
		dataType : "json",
		beforeSend:function(){
			jQuery("#msg_1").show();
			
		} , 
		success : function(data) {
			jQuery("#msg_1").hide();
			if(data.status=='1'){
				art.dialog.alert(data.msg);
			}else{
				window.location.href=sinosoft.tripPayBase+"/shop/payonline.shtml?orderSn="+data.data.orderSn+"&productId="+data.data.productId+"&KID="+data.data.KID
			}
		},
		error : function() {
			jQuery("#orderErrors").show();
			jQuery("#orderErrors").html("矮油，出了点小状况①,点击<a href=\"javascript:void(0);\" vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\"><这里></a>！请客服帮帮忙吧！");
		}
	});
})

/**
 * 校验证件号码
 * @param idtype 类型
 * @param idCode id
 * @param birthdayId 生日
 * @param sexId 性别
 * @param cFlag
 * @returns {Boolean}
 */
function idcheck(idtype,idCode,birthdayId,sexId,cFlag){
	if(jQuery("#"+idCode).parent().children("font")){
		   jQuery("#"+idCode).parent().children("font").hide();
	}
    str=document.getElementById(idCode).value;
    var idType = document.getElementById(idtype);
	var idType_index = idType.selectedIndex;          
	var idType_value = idType.options[idType_index].text;   
	var idType_id = idType.options[idType_index].id; 
	var flag = true;
	str = jQuery.trim(str);
	var StrLast=str.substring(str.length-1);
	if(jQuery("#"+idCode).parent().children("i")){
		   jQuery("#"+idCode).parent().children("i").remove();
	}
	if(idType_value.indexOf("身份证") != -1){
		if(jQuery("#"+idCode).val() !="" ){
			jQuery("#"+birthdayId).removeAttr("onclick");
			jQuery("#"+birthdayId).unbind("click").click(function() {
			});
			jQuery("#"+birthdayId).attr("readonly","readonly");
		}
		if(str.length==18){
			  
			if(!checkId(str,"")){  
				if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">一定要填写18位有效身份证号码哟，请仔细检查一下</i>"));
		 		}
				return false ;
			}else{
				if(StrLast=='x'){
					if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
						  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">身份证有误,身份证号码最后一位请输入大写:X</i>"));
			 		}
					return false ;
				}else{
					if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
					}
				}
			}
			var sex=''; 
			var sexName = jQuery("#"+sexId).attr("name");
			if(str.substring(16,17)%2==0||str.substring(16,17).toLowerCase()=='x'){  
				jQuery("input[name='"+sexName+"']").each(function(){
					//性别处理，根据汉字匹配证件类型编码appDictionaryCode
					jQuery("input[name='"+sexName+"'][value=F]").attr("checked",'checked'); 

		        });
			}else{   
				jQuery("input[name='"+sexName+"']").each(function(){
					//性别处理，根据汉字匹配证件类型编码appDictionaryCode
					jQuery("input[name='"+sexName+"'][value=M]").attr("checked",'checked'); 
		        });  
			}   
			var year = str.substring(6, 10);      
			var month = str.substring(10, 12);      
			var day = str.substring(12, 14);
			sex = jQuery("input[name='"+sexName+"'] :checked").val();
			
			if(cFlag!="1"){
				document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
			}
		
			if(birthdayId.indexOf("favoreeBirthday")==-1){
				if(cFlag=="1"){
					// 判断身份证年龄与初始年龄是否一致
					if(document.getElementById(birthdayId).value!=null && document.getElementById(birthdayId).value != ''&& document.getElementById(birthdayId).value!=year+"-"+month+"-"+day){
						if (jQuery("#" + idCode).parent().children("i")) {
							jQuery("#"+idCode).parent().children("i").remove();
						}
						jQuery("#"+idCode).parent().children("label").after(jQuery("<i  class=\"yz_mes_error\" >身份证与出生日期不符，请确认</i>"));
						return false;
					}
				}
			}
		}else{
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">一定要填写18位有效身份证号码哟，请仔细检查一下</i>"));
		 	}
			return false;
		} 

	}else if(idType_id == 7){
		//护照
		if(str.length2() < 3){
			errorMessage(idCode,"证件号码有误,格式错误!");
			return false;
		}else{
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
			}
		}
	}
	if(!flag){
		return false;
	}else{
		return true;
	}
	
}

/*
 * 证件类型修改，原证件号码清空
 */
function changeVerification(Idtype,changeId,birthId){
	
	jQuery("#"+changeId).val("");
	tObj = jQuery("#"+changeId); 
	if(tObj.parent().children("label").next("i")){
		tObj.parent().children("label").next("i").remove(); 
	} ;
	initBirthday(Idtype,changeId,birthId);
	var id=Idtype.split("_")[1];
	jQuery("#identityStartDate_"+id).val("");
	jQuery("#identityEndDate_"+id).attr("disabled", false);
	jQuery("#Checkzjnum_long_"+id).attr("checked", false);
	
	jQuery("#identityEndDate_"+id).val("");
	if (jQuery("#"+Idtype).find("option:selected").text() == '护照') {
		jQuery("#passport_"+id).show();
	} else {
		jQuery("#passport_"+id).hide();
	}
}
//出生日期變為灰色
function initBirthday(Idtype,changeId,birthId){
	var idType = document.getElementById(Idtype);
	var idType_index = idType.selectedIndex;          
	var idType_value = idType.options[idType_index].text;   
	var appOrrec_value =document.getElementById(changeId).value;
	if(idType_value.indexOf("身份证") != -1){
		if(appOrrec_value != null && appOrrec_value != ""){
			jQuery("#"+birthId).removeAttr("onclick");
			jQuery("#"+birthId).unbind("click").click(function() {});
			jQuery("#"+birthId).attr("readonly","readonly");
		}
		
	}

}

//文本框文案提示
jQuery('.pos-tb-p input').focus(function () {
	jQuery(this).parent().siblings('.app_mobile').hide()
	});
	jQuery('.pos-tb-p input').blur(function () {
	if (jQuery(this).val() != '') {
	jQuery(this).parent().siblings('.app_mobile').hide()
	} else {
	jQuery(this).parent().siblings('.app_mobile').show()
	}
	});