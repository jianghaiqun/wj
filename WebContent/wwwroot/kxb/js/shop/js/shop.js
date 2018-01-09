/**
* ------------------------------------------
* @make:崔世刚
* @version  1.0
* @des：购买流程.  
* ------------------------------------------
*/
var tInsPartNum=0;
var tInsNum=0; 
var calPremFlag=false;
/*被保人信息*/
var tInsInof="";
/*被保人数量*/ 
var tInsNum=parseInt(jQuery("dl[id^=ins_]:last").find(".bxr_num").html())+1;
/*被保人最终数量*/
var tInsLastNum=parseInt(jQuery("dl[id^=ins_]:last").find(".bxr_num").html())+1;
/*被保人radio切换*/ 
function radioShow()
{
	var myradio=jQuery("input[name='myradio']");
	var div=  jQuery('#from_tab > div'); 
	if(document.getElementById("effective")){
		effective = document.getElementById("effective").value;
	}
	var divLen = div.length;
	if(productExcelFlag=="N"){divLen = divLen-1;}
	for(var i=0;i<divLen;i++){
		if(!myradio.get(i).checked){
			div[i].style.display="none";
		}else{
			/*本人:rid_me, 其他被保人：rid_orther，批量上传:rid_td*/  
			div[i].style.display="block"; 
			/*配置被保人操作方式*/
			jQuery("#mulInsuredFlag").val(""+myradio.get(i).id+"");
				if("rid_me"==myradio.get(i).id){
					//被保人快速录入隐藏
					jQuery(".cy_user order-user").each(function(){
						jQuery(this).hide();
					});
					tInsLastNum=2;
					jQuery("#recognizeeOperate").val("1");
					jQuery("#recognizeeMul").val("1");
					jQuery("#insNum").html(1);
					/*有效保单份数*/
					jQuery("#insMult").html(1);
					jQuery("#insMult_1").html(1);
					//保费试算
					var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
					var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
					var recoJson = recognizeeAppInfo();
					jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson
						+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="
						+jQuery("#channelsn").val()+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
						data:{orderSnTem:orderSn},
						dataType: "json",
						async: false,
						success: function(data) {
								var obj = eval(data);
								fillin(obj);
							}
						}); 
					excelsaveFlag = false;
				}else if("rid_orther"==myradio.get(i).id){
					if (jQuery("dl[id^=ins_]:last").find(".bxr_num").html() == null || jQuery("dl[id^=ins_]:last").find(".bxr_num").html() =='') {
						tInsLastNum = 2;
					} else {
						tInsLastNum=parseInt(jQuery("dl[id^=ins_]:last").find(".bxr_num").html())+1;
					}
					
					jQuery("select[id^='recognizeeMul']").each(function(){
						jQuery(this).val("1");
					});
					jQuery("#recognizeeOperate").val("2");
					 jQuery('.bbr_boxs:first dt a').toggle(function(){  
						  jQuery(this).parent().siblings().hide(); 
				  		  jQuery(this).text("打开");
				  		  jQuery(this).addClass("kg_jia");
				  		  jQuery(this).siblings(".bxr-up-data").show();
				  		//金融会所
							selectIframe();
				  		  jQuery(this).parent().parent().addClass("bbr_bor");
				  		  jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val()); 
					  },
					  function() { 
					     jQuery(this).parent().siblings().show();  
					     jQuery(this).text("收起");    
					     jQuery(this).removeClass("kg_jia");
					     jQuery(this).siblings(".bxr-up-data").hide();
						 jQuery(this).parent().parent().removeClass("bbr_bor");
						//金融会所
						    selectIframe();
					  });
					 
						//被保人快速录入隐藏
						if(insuredLenFlag){
							jQuery(".order-user").each(function(){
							
								jQuery(this).show();
//								jQuery(this).children("dl").children("dd").html(jQuery("#hidd_insuredInfo").html());
//								alert(jQuery("#hidd_insuredInfo").html());
//								alert(jQuery(this).children("dl").children("dd").html());
								
							});
						} else {
							jQuery(".order-user").each(function(){
//								jQuery(this).children("dl").children("dd").html("");
//								alert(11);
								jQuery(this).hide();
							});
						}
						
					    jQuery('.bbr_boxs:first dt em').unbind("click").click(function() {
								if(confirm("确定要删除此被保人信息吗?")){
									if( jQuery('.bbr_boxs').length>1){ 
										jQuery(this).parent().parent().remove(); 
										tInsLastNum = tInsLastNum-1;
										tInsPartNum= tInsLastNum; 
									    //被保人序号显示  
										var j = (tInsPartNum-1);
										jQuery("dl[id^=ins]").each(function(){ 
											   jQuery(this).find(".bxr_num").html(tInsPartNum-j+".");
											   j=j-1;
											   
										   });
										jQuery("#insNum").html(tInsPartNum-1);
										jQuery("#insMult").html(tInsPartNum-1+calInsuredCount());
										jQuery("#insMult_1").html(tInsPartNum-1+calInsuredCount());
										//金融会所
										selectIframe();
										calPrem(); 
										}else{
											art.dialog.alert("最后一个被保险人不能删除"); 
										 } 
								}
						}); 
						//保费试算
					    jQuery("#insNum").html(tInsLastNum-1);
						/*有效保单份数*/
						jQuery("#insMult").html(tInsLastNum-1+calInsuredCount());
						jQuery("#insMult_1").html(tInsLastNum-1+calInsuredCount());
						var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
						var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
						var recoJson = recognizeeInfo();
						jQuery.ajax({
							url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson
							+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag
							+"&channelsn="+jQuery("#channelsn").val()+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
							data:{orderSnTem:orderSn},
							dataType: "json",
							async: false,
							success: function(data) {
									var obj = eval(data);
									fillin(obj);
								}
							}); 
						excelsaveFlag = false;
				}else{
					jQuery("#recognizeeOperate").val("3");
					jQuery("#impValadate").val("N");
					//被保人快速录入隐藏
					jQuery(".cy_user order-user").each(function(){
						jQuery(this).hide();
					});
				} 
			
			}
	}
	//金融会所
	selectIframe();
	hidetimeout_tip();
}



/**
 * 增加被保人时的校验
 * @param tObj
 * @param tVal
 * @returns {Boolean}
 */
function defaltValidate(tObj,tVal)
{
	if(tVal==null || tVal=="" || tVal=="null" ||tVal==-1){
			if(!tObj.parent().parent().children("label").is('i')){
				//针对证件有效期是长期的，无需校验日期的非空情况
				if((tObj.next().attr("id").indexOf("Checkzjnum_long")!=-1)&&(tObj.next().attr("checked")==true)){
					return  true;
				}
					tObj.parent().parent().children("label").next("i").remove();
					tObj.parent().parent().children("label").after(jQuery("<i class=\"yz_mes_error\">输入内容有误！</i>"));
					return false;
			} ;
		tObj.parent().parent().parent().parent().find("dd").show();  //不包括投、被保人信息快速录入
		tObj.parent().parent().parent().parent().find("a").text("收起");
		tObj.parent().parent().parent().parent().find("i").hide();  
		//金融会所
	    selectIframe();
		tObj.parent().parent().parent().parent().find("a").removeClass("kg_jia");
		tObj.parent().parent().parent().parent().parent().removeClass("bbr_bor");
		tObj.parent().parent().parent().parent().find("a").each(function(){ 
			jQuery(this).toggle(function(){  
				jQuery(this).parent().siblings().hide(); 
		  		  jQuery(this).text("打开");
		  		  jQuery(this).addClass("kg_jia");
		  		jQuery(this).siblings(".bxr-up-data").show();
		  		  jQuery(this).parent().parent().addClass("bbr_bor");
		  		//金融会所
					selectIframe();
			  		if(tInsLastNum!=0){ 
						jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val());
						jQuery("#nametitle_"+(tInsNum)).html(jQuery("#recognizeeName_"+(tInsNum)).val()); 
					}else{
						jQuery("#ins_"+tInsNum).find(".bxr_num").html(tInsNum+1+".");
						jQuery("#nametitle_"+(tInsNum)).html(jQuery("#recognizeeName_"+(tInsNum)).val()); 
					}
			  },
			  function() { 
				 jQuery(this).parent().siblings().show();  
			     jQuery(this).text("收起");    
			     jQuery(this).removeClass("kg_jia");
			     jQuery(this).siblings(".bxr-up-data").hide();
				 jQuery(this).parent().parent().removeClass("bbr_bor"); 
				//金融会所
				    selectIframe();
				  
			  });
		});
		return false;
	}
	return true;
	
}
/**
* 被保人查看职业类别表
*/
function showOccupations(){
	window.open(sinosoft.base + "/shop/order_config_new!showOccupations.action?supplierCode2="+companyCode+"&productId="+productId+"&OccupLevel="+OccupLevel);
}

/**
 * @des: 根据证件类型判断要素是否显示
 * @maker: 郭东奇
 * @date: 2016.9.1
 */
var typecontrol = function(opt) {

	this._opt = $.extend({

		typeobj: '.typeoption',
		control: '.idControl',
		changeCode: function(){}
	}, opt);

	this.setInit();
	this.addEvent();
}
typecontrol.prototype.setInit = function() {

	this.typeobj = $(this._opt.typeobj);
	this.control = this.typeobj.parent().parent().siblings(this._opt.control);
	this.changeCode = this._opt.changeCode;

}
typecontrol.prototype.addEvent = function() {

	var _this = this;

	_this.typeobj.each(function(index, el) {
		var Typesname = jQuery(this).find("option:selected").text();
		if(Typesname=="身份证"){
			jQuery(this).parent().parent().siblings(_this._opt.control).hide();
		}else{
			jQuery(this).parent().parent().siblings(_this._opt.control).show();
		}
	});

	_this.typeobj.live('change',function(){
		var id = jQuery(this).attr("id");
		var isAppn = (id == "applicantTypeId")?true:false;
		var applicantId = "applicantId";
		var applicantBirthdayId = "applicantBirthday";
		var _id,recognizeeId,recognizeeBirthdayId;
		if(!isAppn){
			_id = "_"+id.split("_")[1];
			recognizeeId = "recognizeeId"+_id;
			recognizeeBirthdayId = "recognizeeBirthday"+_id;
		}
		var Typesname =  jQuery(this).find("option:selected").text();
		if(Typesname=="身份证"){
			jQuery(this).parent().parent().siblings(_this._opt.control).hide();
			if(isAppn){
				jQuery("#"+applicantId).attr("verify","证件号码|notnull&BIRAPPAGE");
				jQuery("#"+applicantBirthdayId).removeAttr("verify");
			}else{
				jQuery("#"+recognizeeId).attr("verify","证件号码|NOTNULL&BIRAGE");
				jQuery("#"+recognizeeBirthdayId).removeAttr("verify");
			}
		}else{
			if(isAppn){
				jQuery("#"+applicantId).attr("verify","证件号码|notnull");
				jQuery("#"+applicantBirthdayId).attr("verify","出生日期|notnull&APPAGE");
			}else{
				jQuery("#"+recognizeeId).attr("verify","证件号码|notnull");
				jQuery("#"+recognizeeBirthdayId).attr("verify","出生日期|NOTNULL&AGE");
			}
			jQuery(this).parent().parent().siblings(_this._opt.control).show();
		}

	})
}

// 证件类型控制 投保人
new typecontrol();

jQuery(function(){ 
	
	// 需求初始化
	bindclose();
	bingshow();

	jQuery(".shop_checksf").click(function(){
		if(jQuery(this).attr("checked")==true){
			jQuery(".shop_login").show();
		}else{
			jQuery(".shop_login").hide();
		}
    })

	jQuery("#showOutGoingParpose").focus(function(){
		
	 		jQuery("#flow_md").show();
							
	})
	jQuery(".close_city").click(function(){
			jQuery("#flow_md").hide();
	})
	/*页签显示*/
	var myradio=jQuery("input[name='myradio']");
	var div=  jQuery('#from_tab > div'); 
	var divLen = div.length;
	if(productExcelFlag=="N"){divLen = divLen-1;}
	for(i=0;i<divLen;i++){
		if(myradio.get(i).checked){ 
			/*本人:rid_me, 其他被保人：rid_orther，批量上传:rid_td*/  
			div[i].style.display="block"; 
			/*配置被保人操作方式*/
			jQuery("#mulInsuredFlag").val(""+myradio.get(i).id+"");
				if("rid_me"==myradio.get(i).id){
					jQuery("#recognizeeOperate").val("1"); 
				}else if("rid_orther"==myradio.get(i).id){
					jQuery("#recognizeeOperate").val("2");
				}else{
					jQuery("#recognizeeOperate").val("3");
				}
			}else{
				div[i].style.display="none";
			}
	}
    /**
     * 增加一个被保人
     */
    if(tInsInof==""){
    	var tName = jQuery("#recognizeeName_0").val();
    	jQuery("#recognizeeName_0").val("");
        tInsInof=jQuery("#ins_0").html(); 
        jQuery("#recognizeeName_0").val(tName);
		if(tInsInof != null && jQuery("#ins_0")){
			var isIE8 = !!window.XDomainRequest&&!!document.documentMode;
			var isIE7 = navigator.userAgent.toLowerCase().indexOf("msie 7.0") != -1 && !isIE8;
			var isIE6 = navigator.userAgent.toLowerCase().indexOf("msie 6.0") != -1;
			var regexTag= new RegExp("<\\s*(I|i)(N|n)(P|p)(U|u)(T|t)\\s[^>]*(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*\"([^\"]*)\"","g");
			var regexTag1= new RegExp("(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*\"([^\"]*)\"","g");
		    var regexTag2= new RegExp("selected=\"selected\"","g");
			if(isIE8||isIE7||isIE6){ 
				 regexTag= new RegExp("<\\s*(I|i)(N|n)(P|p)(U|u)(T|t)\\s[^>]*(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*","g");
				 regexTag1= new RegExp("(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*","g");
			     regexTag2= new RegExp("\s*selected\s*","g");
			}
			
		    //var regexTag2= new RegExp("\s*selected\s*","g");
			var resaultList = tInsInof.match(regexTag); 
			if(resaultList != null){ 
				for (var i = 0; i < resaultList.length; i++){
					var resinfo = new String(resaultList[i]);
					if(resinfo.indexOf("name=\"sdinformationinsuredList[0].recognizeeSex\"")==-1
							&& resinfo.indexOf("type=\"radio\"")==-1
							&& resinfo.indexOf("type=radio")==-1
							&& resinfo.indexOf("id=recognizeeSex_0")==-1
							&& resinfo.indexOf("id=\"recognizeeSex_0\"")==-1
					 
					){
						tInsInof = tInsInof.replace(resaultList[i],resaultList[i].replace(regexTag1,"value=\"\""));
					}
				}
				tInsInof = tInsInof.replace(regexTag2,"");
			}
			var regexTag3= new RegExp("<b\\s*[nametitle_0][^>]*>([^>]*)</b>","i");
			var resaultList2 = tInsInof.match(regexTag3);
			if(resaultList2 != null){
				tInsInof = tInsInof.replace(resaultList2[0],resaultList2[0].replace(resaultList2[1],""));
			} 
		};
		//jQuery("#recognizeePrem_0").val("");
		//jQuery("#recognizeeBirthdayID_0").html(""); 
	};  
	jQuery('#addIns1').click( function() {  

		    if(!verifyInput2()){return false;}; 
		
			var tFlag = true;
			tInsNum = tInsNum+1;  
			jQuery('.bbr_boxs: dt a').each(function(){ 
				jQuery(this).toggle(function(){  
					 jQuery(this).parent().siblings().show();  
				     jQuery(this).text("收起");    
				     jQuery(this).removeClass("kg_jia");
				     jQuery(this).siblings(".bxr-up-data").hide();
					 jQuery(this).parent().parent().removeClass("bbr_bor"); 
					//金融会所
					selectIframe();
				  },
				  function() { 
					  jQuery(this).parent().siblings().hide(); 
			  		  jQuery(this).text("打开");
			  		  jQuery(this).addClass("kg_jia");
			  		  jQuery(this).siblings(".bxr-up-data").show();
			  		  jQuery(this).parent().parent().addClass("bbr_bor");
			  		  jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val()); 
			  		//金融会所
					selectIframe();
				  });
			});
			jQuery("div[id^='insurance_']:last").find(":input[type!=hidden][type!=radio]").each(function(){
				
				//校验信息
				if(!defaltValidate(jQuery(this),jQuery(this).val())){ 
					  tFlag = false;
						//金融会所
					  selectIframe();
					  return false; 
					};
				 
			});
			if(!tFlag){return false;} 
			/*当新增一个被保人时，之前录入的被保人是“收起状态”*/
			jQuery('.bbr_boxs:last dt').find('a').each(function(){
				  jQuery(this).parent().siblings().hide(); 
		  		  jQuery(this).text("打开");
		  		  jQuery(this).addClass("kg_jia");
		  		  jQuery(this).siblings(".bxr-up-data").show();
		  		  jQuery(this).parent().parent().addClass("bbr_bor"); 
		  		//金融会所
				 selectIframe();
			});
			var sex1 = jQuery("input[name='sdinformationinsuredList[0].recognizeeSex']:checked").val();
			/*复制被保人信息*/
			var ttInsInof = tInsInof.replace(/\[0\]/g, "["+tInsNum+"]");
			ttInsInof = ttInsInof.replace(/_0/g, "_"+tInsNum);
			
			var cIns = jQuery("div[id^='insurance_']:first").siblings(".order-user").html();
			var cInsDis = false;
			if (jQuery("#YesLogin").is(':hidden')) {
				cInsDis = false;
			} else {
				cInsDis = true;;
			}
			jQuery("#addIns").before("<dl id='ins_"+tInsNum+"' class='clearfix bbr_boxs bbr_bor'>"+ttInsInof+"</dl>");
			
			if (cInsDis) {
				cIns = cIns.replace(/_[0-9]/g, "_"+tInsNum);
				jQuery("div[id^='insurance_']:last").siblings(".order-user").show();
				jQuery("div[id^='insurance_']:last").siblings(".order-user").html(cIns);
			}
			
			
			jQuery("input[name=sdinformationinsuredList[0].recognizeeSex][value="+sex1+"]").attr("checked",'checked');
			/*替换与Name相关*/ 
			//jQuery("#ins_"+tInsNum).html(ttInsInof);
			/*替换与ID相关*/ 
			//jQuery("#ins_"+tInsNum).html(ttInsInof);  
			/*替换增加第几个被保人*/
			//触发与多被保人关系函数
			changeInformation("","");
			/*在没有删除被保人的情况下增加被保人 tInsLastNum！=0 表示已经删除过被保人了*/ 
			if(tInsLastNum!=0){
				jQuery("#ins_"+tInsNum).find(".bxr_num").html(tInsLastNum+".");
				 
				jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val());
				jQuery("#nametitle_"+(tInsNum-1)).html(jQuery("#recognizeeName_"+(tInsNum-1)).val());
				 
				tInsLastNum = tInsLastNum+1; 
			}else{
				jQuery("#ins_"+tInsNum).find(".bxr_num").html(tInsNum+1+".");
				jQuery("#nametitle_"+(tInsNum-1)).html(jQuery("#recognizeeName_"+(tInsNum-1)).val());
				 
				tInsLastNum = tInsNum+2; 
			}  
			
			/*页面显示被保人个数*/
			jQuery("#insNum").html(tInsLastNum-1);
			/*有效保单份数*/
			jQuery("#insMult").html(tInsLastNum-1+calInsuredCount());
			jQuery("#insMult_1").html(tInsLastNum-1+calInsuredCount());
			/*添加打开与收起事件*/
			 //jQuery('.bbr_boxs:last dt a').live('click',function(){
				  jQuery('.bbr_boxs:last dt a').toggle(function() {  
					  jQuery(this).parent().siblings().hide(); 
			  		  jQuery(this).text("打开");
			  		  jQuery(this).addClass("kg_jia");
			  		  jQuery(this).siblings(".bxr-up-data").show();
			  		  jQuery(this).parent().parent().addClass("bbr_bor");   
			  		//金融会所
						selectIframe();
			  		if(tInsLastNum!=0){ 
						jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val());
						jQuery("#nametitle_"+(tInsNum)).html(jQuery("#recognizeeName_"+(tInsNum)).val()); 
					}else{
						jQuery("#ins_"+tInsNum).find(".bxr_num").html(tInsNum+1+".");
						jQuery("#nametitle_"+(tInsNum)).html(jQuery("#recognizeeName_"+(tInsNum)).val()); 
					}
				  },
				  function() {  
				     jQuery(this).parent().siblings().show();  
				     jQuery(this).text("收起");    
				     jQuery(this).removeClass("kg_jia");
				     jQuery(this).siblings(".bxr-up-data").hide();
					 jQuery(this).parent().parent().removeClass("bbr_bor"); 
					//金融会所
					    selectIframe();
				  });
				  
			 //});
			 /*添加删除事件*/
				  jQuery('.bbr_boxs dt em').each(function(){
					 jQuery(this).unbind("click").click(function() {
				     //jQuery('.bbr_boxs:last dt em').click(function() { 
							if(confirm("确定要删除此被保人信息吗?")){
								if( jQuery('.bbr_boxs').length>1){
									jQuery(this).parent().parent().remove();
									tInsLastNum = tInsLastNum-1;
									tInsPartNum= tInsLastNum; 
								    //被保人序号显示  
									var j = (tInsPartNum-1);
									jQuery("dl[id^=ins_]").each(function(){ 
										   jQuery(this).find(".bxr_num").html(tInsPartNum-j+".");
										   j=j-1;
										   
									   }); 
									/*页面显示被保人个数*/
									jQuery("#insNum").html(tInsPartNum-1);
									jQuery("#insMult").html(tInsPartNum-1+calInsuredCount());
									jQuery("#insMult_1").html(tInsPartNum-1+calInsuredCount());
									//金融会所
									selectIframe();
									//保费试算
									calPrem(); 
									//金融会所
									selectIframe();
								}else{
									art.dialog.alert("最后一个被保险人不能删除");
									return;
									 }
							} 
					 });
			       });
			//金融会所
		    selectIframe();
			calPrem();
			moreQuicklist();
		}); 

	
	/**
	 * Ajax提交Form表单
	 */
	jQuery("#confirm").click(function(){
		
		if (benefitRadioShow()) {
			   if(!verifyFavoreeRatio()){return false;};
		};
		
		if(jQuery("#effective").val()==null||jQuery("#effective").val()==''){
			art.dialog.alert("请输入起保日期");
			jQuery("#effective").focus(); 
			return false;
		}
		if(jQuery("#lookOccuDiv").length > 0 && !jQuery("#lookOccuDiv").is(":hidden")){
	        var s = jQuery("#sel_zys").is(':checked');
	        if(!s){
	        	if(jQuery("#sel_zys").parent().siblings(".yz_mes_error").length==0){
		             jQuery("#sel_zys").parent().parent().append("<em class='yz_mes_error'>请选择您是否为可投保职业，以免对您的理赔造成影响</em>")
		        }
		        jQuery("html, body").animate({scrollTop:jQuery("#radiobox").offset().top});
	            return false;
	        }
	     }
	    if (jQuery('#lookclassDiv').length > 0 && !jQuery('#lookclassDiv').is(':hidden')) {
            var s = jQuery('#sel_zys2').is(':checked');
            if (!s) {
                if (jQuery('#sel_zys2').parent().siblings('.yz_mes_error').length == 0) {
                    jQuery('#sel_zys2').parent().parent().append('<em class=\'yz_mes_error\'>请选择您是否为可投保职业，以免对您的理赔造成影响</em>')
                }
                jQuery('html, body').animate({scrollTop: jQuery('#radiobox').offset().top});
                return false
            }else{
            	jQuery("#lookclassDiv .yz_mes_error").remove();
            }
        }
		if(!verifyInput2()){return false;}
		if(!selecttrave()){ return false;}
		/*针对投被保人的证件类型是“身份证”的时候校验*/ 
		if(jQuery("#mulInsuredFlag").val()=="rid_orther"){
			if(!checkAppInsID()){return false;}  
		}else if(jQuery("#mulInsuredFlag").val()=="rid_me" || jQuery("#relationIsSelf").val()=="Y"){ 
			if(!validateRec()){return false;};
		}else if(jQuery("#mulInsuredFlag").val()=="rid_td"){ 
			if(!checkAppID()){return false;}
			if(!excelsaveFlag){
				var dialog = art.dialog({
				    title: '提示',
				    content: '请重新上传被保人信息！',
				    ok: function(){
				    }
				});
				return;
			}
			if(excelinsuredImpFlag){
				var dialog = art.dialog({
				    title: '提示',
				    content: '被保人信息未保存，请先保存！',
				    ok: function(){
				    }
				});
				return;
			}
			if(jQuery("div[id^='recognizee_']").length<=0){jQuery("#impValadate").val("Y")}else{jQuery("#impValadate").val("N")};
			if(jQuery("#impValadate").val()!="Y"){
				//jQuery("#importMessage").html("请重新上传被保人信息!");
				jQuery("#importMessage").show();
				//jQuery("#importMessage").attr("style", "overflow:hidden;color:red");
				jQuery("#importMessage").focus();
				return false;
			}
		}else{//单被保人
			var oneInsuredFlag = jQuery("#relationFlag_0").val();
			if(oneInsuredFlag == "Y"){//本人
				if(!validateRec())	return false;
			}else{//其他
				if(!checkAppInsID())	return false;
			}
		}
		if(!verifyInput2()){return false;}; 
				showUI();
	 	        jQuery("#order_err").hide(); 
	 	        
		 		document.getElementById("orderStatus").value="prepay";
		 		//calPrem()
				//准备后台提交数据
				var insureJson = insuredElementsd();
				var dutyJson = dutyElements();
				var dutyJsonDis = dutyElementsDis();
				var dutyJsonPrem = dutyPrem();
				var recoJson = ""; 
				if(jQuery("#mulInsuredFlag").val()=="rid_me"||jQuery("#relationIsSelf").val()=="Y" ){
					recoJson = recognizeeAppInfo();  
				}else{
					recoJson = recognizeeInfo(); 
				} 
				var complicatedFlag=jQuery("#complicatedFlag").val();
				var dutyTempSerials=jQuery("#dutyTempSerials").val();
				var turl= sinosoft.base + "/shop/order_config_new!saveOrder.action?recoJson="+recoJson+"&productId="+productId;
				
				if("update"==status){
				    turl= sinosoft.base + "/shop/order_config_new!orderUpdate.action?recoJson="+recoJson+"&productId="+productId;
					
				}
			    //前台加密两次，后台解密一次
				turl = encodeURI(encodeURI(turl));  
					var options = { 
					url:turl, 
					async:true,
					type:"POST", 
					data:{orderId:updateOrderId,supplierCode2:companyCode,insureJson:encodeURIComponent(insureJson),dutyJson:encodeURIComponent(dutyJson),dutyDisReq:encodeURIComponent(dutyJsonDis),dutyPremReq:encodeURIComponent(dutyJsonPrem)},
					dataType: "json",
					resetForm:false,
					//timeout:1000,
					success: function(data){  
						hideUI();
						var tOrderSn = data.OrderSn;
						var tFlag = data.Flag;
						var orderId = data.OrderId;
						var kid = data.kid; 
						var orderFlag = data.orderFlag;
						if(tFlag=="Err"){
							jQuery("#order_err").show();
							jQuery("#order_err").html(data.Msg);
//							if(typeof(BizQQWPA)!='undefined'){
//								// 在线客服 
//								BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//							}
							jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
								try {
									NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
								} catch (e) {
								}
							});
						}else{
							hideUI();
							window.location.href=sinosoft.base + "/shop/order_config_new!sendDirectUrl.action?orderSn="+tOrderSn+"&orderId="+orderId+"&Flag="+tFlag+"&KID="+kid+"&orderFlag="+orderFlag+"&productId="+productId;
							}
					}, error:function(response, textStatus, errorThrown){
						try{
				 			var data ;
				 			var responseText = response.responseText;
				 		// iphone 手机兼容问题处理
				 			var regexTag3= new RegExp("<a\\s[^>]*href=\"tel:[^\"]*\">([^\"]*)</a>","g");
			 				var regexTag4= new RegExp("<a\\s[^>]*href=\"tel:[^\"]*\">|</a>","g");
			 				var resaultList = responseText.match(regexTag3); 
			 				if(resaultList != null){ 
			 					var ordersn = resaultList[0].replace(regexTag4,"") ;
			 					data = $.parseJSON(responseText.replace(resaultList[0],ordersn));
			 					
				 			}  else { 
				 				data = $.parseJSON(responseText);
				 			}
				 			
				 			var tOrderSn = data.OrderSn;
							var tFlag = data.Flag;
							var orderId = data.OrderId;
							var kid = data.kid; 
							var orderFlag = data.orderFlag;
							if(tFlag == "Err"){
								hideUI();
								jQuery("#order_err").show();
								jQuery("#order_err").html(data.Msg);
//								if(typeof(BizQQWPA)!='undefined'){
//									// 在线客服 
//									BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//								}
								jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
									try {
										NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
									} catch (e) {
									}
								});
							}else{
								hideUI();
								window.location.href=sinosoft.base + "/shop/order_config_new!sendDirectUrl.action?orderSn="+tOrderSn+"&orderId="+orderId+"&Flag="+tFlag+"&KID="+kid+"&orderFlag="+orderFlag+"&productId="+productId;
							}
				 		} catch (e) {
				 			hideUI();
				 			jQuery("#order_err").show();
							jQuery("#order_err").html("矮油，出了点小状况①,点击<a href=\"javascript:void(0);\" vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\"><这里></a>！请客服帮帮忙吧！");
//							if(typeof(BizQQWPA)!='undefined'){
//								// 在线客服 
//								BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//							}
							jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
								try {
									NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
								} catch (e) {
								}
							});
				 		}
					}
				};  
			    jQuery('#orderInfoForm').ajaxSubmit(options); 
		
	});
	
	jQuery("#comfirmToPay").click(function(){
		if(!verifyInput2()){ 
			return false;
		}; 
		/*针对投被保人的证件类型是“身份证”的时候校验*/ 
		if(jQuery("#mulInsuredFlag").val()=="rid_orther"){
			if(!checkAppInsID()){return false;}  
		}else if(jQuery("#mulInsuredFlag").val()=="rid_me" || jQuery("#relationIsSelf").val()=="Y"){ 
			if(!validateRec()){return false;};
		}else if(jQuery("#mulInsuredFlag").val()=="rid_td"){ 
			if(!excelsaveFlag){
				var dialog = art.dialog({
				    title: '提示',
				    content: '请重新上传被保人信息！',
				    ok: function(){
				    }
				});
				return;
			}
			if(excelinsuredImpFlag){
				var dialog = art.dialog({
				    title: '提示',
				    content: '被保人信息未保存，请先保存！',
				    ok: function(){
				    }
				});
				return;
			}
			if(jQuery("div[id^='recognizee_']").length<=0){jQuery("#impValadate").val("Y")}else{jQuery("#impValadate").val("N")};
			if(jQuery("#impValadate").val()!="Y"){
				//jQuery("#importMessage").html("请重新上传被保人信息!");
				jQuery("#importMessage").show();
				//jQuery("#importMessage").attr("style", "overflow:hidden;color:red");
				jQuery("#importMessage").focus();
				return false;
			}
		}; 
		document.getElementById("orderStatus").value="temptorysave";
		
		if(!verifyInput2()){ 
			return false;
		}; 
		showUI();
		var insureJson = insuredElementsd();
		var dutyJson = dutyElements();
		var dutyJsonDis = dutyElementsDis();
		var dutyJsonPrem = dutyPrem();
		var recoJson = "";
		if(jQuery("#mulInsuredFlag").val()=="rid_me"){
			recoJson = recognizeeAppInfo(); 
		}else{
			recoJson = recognizeeInfo(); 
		}
		var complicatedFlag=jQuery("#complicatedFlag").val();
		var dutyTempSerials=jQuery("#dutyTempSerials").val();
		var turl= sinosoft.base + "/shop/order_config_new!saveOrder.action?recoJson="+recoJson+"&productId="+productId;
		if("update"==status){
		    turl= sinosoft.base + "/shop/order_config_new!orderUpdate.action?recoJson="+recoJson+"&productId="+productId;
			
		}
		
	    /*前台加密两次，后台解密一次*/
		turl = encodeURI(encodeURI(turl));   
			var options = { 
			url:turl, 
			async:true,
			type:"POST", 
			//避免IE6下由于路径过长导致“没有权限”，参数传输不采用url地址方式；
			//data:{orderId:updateOrderId,insureJson:insureJson,dutyJson:dutyJson,dutyDisReq:dutyJsonDis,dutyPremReq:dutyJsonPrem},
			data:{orderId:updateOrderId,supplierCode2:companyCode,insureJson:encodeURIComponent(insureJson),dutyJson:encodeURIComponent(dutyJson),dutyDisReq:encodeURIComponent(dutyJsonDis),dutyPremReq:encodeURIComponent(dutyJsonPrem)},
			dataType: "json",
			resetForm:false,
			success: function(data){ 
				var tOrderSn = data.OrderSn;
				var tFlag = data.Flag;
				var orderId = data.OrderId; 
				var kid = data.kid;
				if(tFlag=="Err"){
					hideUI();
					jQuery("#order_err").show();
					jQuery("#order_err").html(data.Msg);
//					if(typeof(BizQQWPA)!='undefined'){
//						// 在线客服 
//						BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//					}
					jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
						try {
							NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
						} catch (e) {
						}
					});
				}else{
					hideUI();
					status = data.status;
					updateOrderId = data.OrderId;
					var orderSn = data.OrderSn;
					art.dialog.alert("暂存操作成功！");
					window.location.href=sinosoft.base + "/shop/order_config_new!buyNowUpdate.action?orderSn="+orderSn+"&orderId="+updateOrderId+"&Flag=Suc&KID="+kid;
					
				}
			 }, error:function(response, textStatus, errorThrown){
				 try{
			 			var data ;
			 			var responseText = response.responseText;
			 		// iphone 手机兼容问题处理
			 			var regexTag3= new RegExp("<a\\s[^>]*href=\"tel:[^\"]*\">([^\"]*)</a>","g");
		 				var regexTag4= new RegExp("<a\\s[^>]*href=\"tel:[^\"]*\">|</a>","g");
		 				var resaultList = responseText.match(regexTag3); 
		 				if(resaultList != null){ 
		 					var ordersn = resaultList[0].replace(regexTag4,"") ;
		 					data = $.parseJSON(responseText.replace(resaultList[0],ordersn));
		 					
			 			}  else {
			 				data = $.parseJSON(responseText);
			 			}
			 			
						var tOrderSn = data.OrderSn;
						var tFlag = data.Flag;
						var orderId = data.OrderId; 
						if(tFlag == "Err"){
							hideUI();
							jQuery("#order_err").show();
							jQuery("#order_err").html(data.Msg);
//							if(typeof(BizQQWPA)!='undefined'){
//								// 在线客服 
//								BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//							}
							jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
								try {
									NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
								} catch (e) {
								}
							});
						}else{
							hideUI();
							status = data.status;
							updateOrderId = data.OrderId;
							var orderSn = data.OrderSn;
							var kid = data.kid;
							art.dialog.alert("暂存操作成功！");
							window.location.href=sinosoft.base + "/shop/order_config_new!buyNowUpdate.action?orderSn="+orderSn+"&orderId="+updateOrderId+"&Flag=Suc&KID="+kid;
							
						}
			 		} catch (e) {
			 			hideUI();
			 			jQuery("#order_err").show();
						jQuery("#order_err").html("矮油，出了点小状况②,点击<a href=\"javascript:void(0);\" vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\"><这里></a>！请客服帮帮忙吧！");
//						if(typeof(BizQQWPA)!='undefined'){
//							// 在线客服 
//							BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//						}
						jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
							try {
								NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
							} catch (e) {
							}
						});
			 		}
			    }
		};  
	    jQuery('#orderInfoForm').ajaxSubmit(options); 
		return false;
	});
	/**
	 * 计划改变--保费试算
	 */
	jQuery('#plan').change(function(){
		var value = jQuery('#plan').val();
		var text = jQuery('#plan').find("option:selected").text();
		if(document.getElementById("brkRiskCode")){
			 jQuery("#brkRiskCode").attr("value",value);
			 jQuery("#brkRiskName").attr("value",text);
		}
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		}) ;
	/**
	 * 改变投保人生日根据页签判断是否需要试算--保费试算
	 */
	jQuery("#applicantBirthday").blur(function(){

		   var lent = jQuery("input[id^='recognizeeRelation']").length;
		   if(jQuery("#mulInsuredFlag").val()=="rid_me" || lent>=1){
			    var brithday = document.getElementById("applicantBirthday").value;
				var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
				var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
				var recoJson = recognizeeAppInfo();
				var effective = "";
				if(document.getElementById("effective")){
					effective = document.getElementById("effective").value;
				}
				if(jQuery.trim(effective)=="" || effective==null || effective=="null"){
					jQuery("#gotop").click();
					jQuery("#effective").focus();
					jQuery("#applicantBirthday").val("");
					art.dialog.alert("请输入起保日期");
					return false;
				}
				if(brithday!=null && brithday!=""){
				jQuery.ajax({
					url: sinosoft.base+"/shop/order_config_new!ajaxAgeAndCheckTomorrowBirthday.action?applicantBirthday="+brithday+"&productId="+productId+"&effdate="+effective,
					dataType: "json",
					type:"GET",
					async: true,
					success: function(data) {
						whenTomorrowBirthday("1","",data.tomorrowIsBirthday);
						if(data.ageFlag=='false'){
							return false;
						}else{
							jQuery.ajax({
								url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
								+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
								+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
								data:{orderSnTem:orderSn},
								dataType: "json",
								async: false,
								success: function(data) {
									var obj = eval(data);  
									fillin(obj);
								}
							});
							return true;
						}  
					}
				});
				}   
		}

	}) ; 
	/**
	 * 适用人群--保费试算
	 */
	jQuery('#mulPeople').change(function(){
		var value = jQuery('#mulPeople').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var recoJson = recognizeeAppInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		}); 
	/**
	 * 投保类型--保费试算
	 */
	jQuery('#appType').change(function(){ 
		var value = jQuery('#appType').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		});
	/**
	 * 缴费年期--保费试算
	 */
	jQuery('#feeYear').change(function(){ 
		var value = jQuery('#feeYear').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		});
	/**
	 * 产品级别--保费试算
	 */
	jQuery('#grade').change(function(){ 
		var value = jQuery('#grade').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		});
		jQuery('#occup').change(function(){ 
		var value = jQuery('#occup').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		});
	/**
	 * 保险期限--保费试算
	 */	
	jQuery('#period').change(function(){ 
		var value = jQuery('#period').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		});
		jQuery('#sex').change(function(){ 
		var value = jQuery('#sex').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
					var obj = eval(data);
					fillin(obj);
				}
			});
		});
	/**
	 * 保额--保费试算
	 */
	jQuery('#amnt').change(function(){ 
		var value = jQuery('#amnt').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		var recoJson = recognizeeInfo();
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
				    var obj = eval(data);
					fillin(obj);
				}
			});
		}); 

	var xinhuaConfirm = false;  // 新华产品提示框弹出判断
	/*确认投保后弹窗合保效果*/
	jQuery("#qrgm_pay").click(function(){  
		 var agreeInsure =  document.getElementById("agreeInsure");
			 if(!agreeInsure.checked){
				 jQuery('#agreeInsure').parent().addClass("red");
				 art.dialog.alert("请仔细阅读投保须知和必读，<br>并勾选“我接受以上投保声明及必读内容”");
			 return ;
		 }
		
		// 新华产品弹出核保确认提示框
		if (companyCode == "1014" && !xinhuaConfirm) {
			art.dialog({
				title:'消息',
				lock:true,
                opacity: 0.6,
			    content: '<p style="text-align: center;">本产品同一被保人当天只能提交一次<br>确认提交？<p>',
			    ok: function () {
			    	xinhuaConfirm = true;
			    	jQuery("#qrgm_pay").trigger("click");
			    },
			    cancelVal: '关闭',
			    cancel: true
			});
			return;
		}
		
		/*是否需要核保*/  
		if(needUWCheckFlag=="1"){
			/*需要核保*/ 
			jQuery("#msg_2_2").html("正在为您进行核保操作，请稍等");
			showUI();
			jQuery.getJSON( 
				sinosoft.base+"/shop/order_config_new!checkInsurePay.action?orderSn="+orderSn+"&callback=?",
				function(data) { 
					var obj = eval(data);
					var passFlag = obj.passFlag;
					var tMessage = obj.rtnMessage; 
					var tKID = obj.KID;
				    if(obj){  
						if(passFlag=="pass"){
							hideUI();
							window.location.href=sinosoft.base + "/shop/order_config_new!pay.action?orderSn="+orderSn+"&orderId="+orderId+"&KID="+tKID;
						}else{
							hideUI();
							/*核保错误提示*/ 
							pay_error(orderSn,productId,tMessage,tKID);	 
						} 
				      } 
					});
			 
		}else{
			jQuery("#msg_2_2").html("正在处理，请稍等");
			showUI();
			window.location.href=sinosoft.base + "/shop/order_config_new!pay.action?orderSn="+orderSn+"&orderId="+orderId;
		}
	});
	
	/*赠险购买*/
	jQuery("#free_pay").click(function(){  
		 var agreeInsure =  document.getElementById("agreeInsure");
			 if(!agreeInsure.checked){
				 jQuery('#agreeInsure').parent().addClass("red");
				 art.dialog.alert("请仔细阅读投保须知，<br>并勾选“我接受以上投保声明及必读内容”");
			 return ;
		 }
		jQuery.blockUI({
			overlayCSS:{backgroundColor:'#fff',opacity:0.7}, 
			message:"<div><span>正在处理，请稍候</span><br/><img width='170' height='15' title='正在处理，请稍等' src='"+sinosoft.jsPath+"/template/shop/images/loading-bars.gif'/></div>"
			 
		});
		window.location.href=sinosoft.base + "/shop/pay!freePay.action?orderSn="+orderSn+"&orderId="+orderId;
	});
	
	/**
	 * 健康告知"下一步"
	 */
	jQuery("#bt_health").click(function(){  
		showUI();
		if(!sure()){
			hideUI();
			return false;
		}
		var turl=sinosoft.base+"/shop/order_config_new!saveOrUpdateHealthInf.action?insuredId="+sdInsId+"&orderSn="+orderSn+"&orderId="+orderId,
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
			hideUI();
			var tOrderSn = data.OrderSn;
			var tFlag = data.Flag;
			var orderId = data.OrderId; 
			var orderFlag = data.orderFlag;
			if(tFlag=="Err"){
				jQuery("#order_err").show();
				jQuery("#order_err").html(data.Msg);
//				if(typeof(BizQQWPA)!='undefined'){
//					// 在线客服 
//					BizQQWPA.addCustom([{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap1'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap2'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap3'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap4'},{aty: '0', a: '0', nameAccount: 800118066, selector: 'qqwap5'}]);
//				}
				jQuery(".fix-onlineqq, .zixun, a[vlpageid=xiaoneng]").bind("click",function() {
					try {
						NTKF.im_openInPageChat(sinosoft.xiaoNeng_CustomerService);
					} catch (e) {
					}
				});
			}else{
				updateOrderId = data.OrderId;
				var orderSn = data.OrderSn;
				var kid = data.kid;
				window.location.href=sinosoft.base + "/shop/order_config_new!sendDirectUrl.action?orderSn="+data.OrderSn+"&orderId="+data.OrderId+"&Flag=Suc"+"&KID="+kid+"&orderFlag="+orderFlag+"&productId="+productId;
				
			}
		 }
		};  
	    jQuery('#resultForm').ajaxSubmit(options); 
		return false; 
	});
	
	/**
	 * 健康告知“上一步”
	 */
	jQuery("#bt_health_be").click(function(){  
		    var kid = jQuery("#KID").val();
			window.location.href=sinosoft.base + "/shop/order_config_new!buyNowUpdate.action?orderSn="+orderSn+"&orderId="+orderId+"&Flag=Suc&orderFlag="+orderFlag+"&KID="+kid;
	});
	
	jQuery('.bbr_boxs dt a').each(function(){  
			jQuery(this).toggle(function(){   
				  jQuery(this).parent().siblings().hide(); 
				  jQuery(this).text("打开");
				  jQuery(this).addClass("kg_jia");
				  jQuery(this).siblings(".bxr-up-data").show();
				  jQuery(this).parent().parent().addClass("bbr_bor");  
				  //金融会所
				  selectIframe();
			  },
			  function() { 
			     jQuery(this).parent().siblings().show();  
			     jQuery(this).text("收起");    
			     jQuery(this).removeClass("kg_jia");
			     jQuery(this).siblings(".bxr-up-data").hide();
				 jQuery(this).parent().parent().removeClass("bbr_bor");   
				//金融会所
				selectIframe();
		});
	});
	jQuery('.bbr_boxs dt em').each(function(){
	    jQuery(this).unbind("click").click(function(){ 
			if(confirm("确定要删除此被保人信息吗?")){
				if( jQuery('.bbr_boxs').length>1){ 
					jQuery(this).parent().parent().remove(); 
					tInsLastNum = tInsLastNum-1;
					tInsPartNum= tInsLastNum; 
				    //被保人序号显示  
					var j = (tInsPartNum-1);
					jQuery("dl[id^=ins]").each(function(){ 
						   jQuery(this).find(".bxr_num").html(tInsPartNum-j+".");
						   j=j-1;
						   
					   });
					jQuery("#insNum").html(tInsPartNum-1);
					jQuery("#insMult").html(tInsPartNum-1+calInsuredCount());
					jQuery("#insMult_1").html(tInsPartNum-1+calInsuredCount());
					calPrem();  
					//金融会所
					selectIframe();
					}else{
						art.dialog.alert("最后一个被保险人不能删除"); 
					 } 
			} 
		});
	});
	if (jQuery("#uploadify").length>=1) {
		var complicatedFlag=jQuery("#complicatedFlag").val();
		var dutyTempSerials=jQuery("#dutyTempSerials").val();
		var textAge=jQuery("#textAge").val();
		var memid = jQuery.cookie('loginMemberId');
		if (memid == null || memid == '') {
			memid = memberid;
		}
		
		jQuery("#uploadify").uploadify({ 
		       'swf': sinosoft.base+'/wwwroot/kxb/style/shop/images/uploadify.swf',
		       'uploader':sinosoft.base+'/shop/order_config_new!importBatch.action',   
		       'buttonImage':sinosoft.base+'/wwwroot/kxb/style/shop/images/up-load_05.gif',
		       'button_image_url':sinosoft.base+'/wwwroot/kxb/style/shop/images/up-load_05.gif',
		       'buttonAfterImage':sinosoft.base+'/wwwroot/kxb/style/shop/images/donw-exl_08.gif',  
		       'formData' : {complicatedFlag:complicatedFlag,dutyTempSerials:dutyTempSerials,effdateNew:getEff(),supplierCode2:companyCode,productId:productId,insureJson:insuredElementsd(),dutyJson:dutyElements(),dutyDisReq:dutyElementsDis(),dutyPremReq:dutyPrem(),orderSn:orderSn,memberid:memid},
		       'folder': 'BuyExcel',                
		       'queueID': 'fileQueue',   
		       'fileObjName' : 'uploadfile',
		       'auto': true,                
		       'multi': false,
		       'width': 200,
		       'height': 41,
		       'fileTypeDesc' : 'Excel文件', 
		       'fileTypeExts': '*.xls',
		       'uploadLimit': 864000,
		       'onUploadStart' : function(file) {
		    	   jQuery("#uploadify").uploadify('settings','formData', {complicatedFlag:complicatedFlag,dutyTempSerials:dutyTempSerials,effdateNew:getEff(),supplierCode2:companyCode,productId:productId,insureJson:insuredElementsd(),dutyJson:dutyElements(),dutyDisReq:dutyElementsDis(),dutyPremReq:dutyPrem(),orderSn:orderSn,memberid:memid});
		    	   jQuery("#importMessage").hide();  
		       },
		       'onUploadError': function(event, queueID, fileObj,errorObj)   
		        {    
		    	   //jQuery('#upmessage').html("文件:" + fileObj.name + " 上传失败,请确保没修改过模板结构");
		    	   //alert(1);
		        } ,
		        'onUploadSuccess':function(file, data, response)
		        {  
		            var peoples=eval('('+data +')');
		            if(peoples.state=="0"){
		            	importSuccess(peoples,peoples.successMsg);
		            }else if(peoples.state=="1"){
		            	importError(peoples,peoples.errorMsg);
		            }else{
		            	importGError(peoples.errorMsg);
		            }
		            selectIframe();
		            if (peoples.warnMessage != null && peoples.warnMessage != '') {
		            	alert(peoples.warnMessage);
		            }
		        }
		 });
	}
	
	//add by cuishigang 投保人信息快速录入，登录实现--start
	//登录窗口用artdialog实现 fhz
 	jQuery('#appLogin').click(
		     function(){
		    	   //配置
		    	 if(jQuery('#artLoginFlag').val()==1){
		    		 artNewLogin();//用弹出窗口登录
		    	 }
		    	 else{
		    		 var localURL=window.location.href;
		    		 var loginBackURL=encodeURIComponent(localURL);
		    		 window.location.href= sinosoft.base + "/shop/member!newLogin.action?loginBackURL="+loginBackURL; 
		    	 }
			 });
 	//add by cuishigang 投保人信息快速录入，登录实现--end 	
 	/*******************多旅游目的地选择-开始******************/
	var mdd =  jQuery("#trave_dss"),
        cityName = [],
        cityId = [],
        tab = jQuery("#index_Country_Set"),
        txt = jQuery("#key_word"),
        li = jQuery("#index_Country_Set li input,.sgall input"),
        sel = jQuery(".yx_ctiy_con li,#city_list li"),
        gjtxt = jQuery(".yx_city_span"),
        sellist = jQuery('#city_list'),
        sels = jQuery("#city_list li"),
        clist =[];


        /*jQuery(data.rows).each(function(i,dom){

            var tr = jQuery("<li class='"+dom.class+"'>");
            tr.append("<input type='checkbox' data-name='"+dom.name+"' id='"+dom.idn+"'  value='"+dom.idn+"' onclick='checkSchenGen(this)' name='DestinationCountry'><label forcode='"+dom.idn+"'>"+dom.name+"&nbsp;"+dom.ename+"</label></li>"); 
             tab.append(tr);

        });*/

        var tarve_idlist = jQuery("#trave_id").val();
        var tarve_namelist = jQuery("#trave_name").val();
        // 初始化显示旅游目的地
        if(tarve_namelist!=null && tarve_idlist!=null){
            
           cityId = tarve_idlist.split(',');
           cityName = tarve_namelist.split(',');
        
           if(tarve_idlist!=""){

                  var html = '';
                  var dom = "<span class='yx_city_span'>已选国家：</span><ul id='city_list'>";
                  jQuery(cityName).each(function(i,v){
                     html += "<li id="+cityId[i]+">"+v+"</li>";
                  });
                  jQuery('#mdd_con').html(dom+html+"</ul>");
                  jQuery('.yx_ctiy_con').html(html);
                  gjtxt.show();

            }else{

                  jQuery('#mdd_con').html("");
                  gjtxt.hide();

            }

          if(tarve_idlist!=""){

            jQuery(cityId).each(function(i,v) {
              tab.find("#"+v).attr("checked","checked");
            });

           }
        }

        txt.keyup(function(){

            var me = jQuery(this), v = me.val().replace(/^\s+|\s+$/g,"");
            var trs = tab.find("li");
            if(v==""){
                trs.filter(":hidden").show();
            }else{
                trs.hide().filter(":contains('"+me.val()+"')").show();
            }

        });

        li.live('click',function(){

            var cname = jQuery(this).attr('data-name');
            var ids = jQuery(this).attr('value');
            if(jQuery(this).is(":checked")){
              jQuery('.yx_ctiy_con').append("<li id="+ids+">"+cname+"</li>");
            }else{
              jQuery('.yx_ctiy_con').find("#"+ids).remove();
            }
            var sel = jQuery(".yx_ctiy_con li");
            if(sel.length>0){
               gjtxt.show();
            }else{
               gjtxt.hide();
            }

        });
        sel.live('click',function(){

          var ids = jQuery(this).attr('id');
            jQuery(this).remove();
            jQuery("#"+ids).removeAttr("checked");
            var sel = jQuery(".yx_ctiy_con li");
            if(sel.length>0){
               gjtxt.show();
            }else{
               gjtxt.hide();
            }

        })
        sels.live('click',function(){

             var ids = jQuery(this).attr('id');
             var name = jQuery(this).text();
             var tg = jQuery(".yx_city_span");
             var idlist ;
             var namelist ;

             jQuery("#"+ids).removeAttr("checked");
             jQuery(".yx_ctiy_con").find("#"+ids).click();
             var sels = jQuery("#city_list li");
             cityName.splice($.inArray(name,cityName),1);
             cityId.splice($.inArray(ids,cityId),1);
             jQuery("#trave_id").val(cityId);
             jQuery("#trave_name").val(cityName);
             
             if(sels.length>0){
               tg.show();
             }else{
               tg.hide();
             }
        })


        mdd.live('click',function(){
            art.dialog({
                title:"选择目的地",
                content: document.getElementById('flow_md'),
                id: 'flow_md',
                background:'#000',
                lock: true,
                opacity: 0.6,
                fixed: true,
                padding:0,
                 button: [
                      {
                          name: '确认',
                          callback: function () {

                              var g = jQuery(".yx_ctiy_con li").length;
                              var selg = jQuery(".yx_ctiy");
                              var cid;
                              var name;
                              clist = [];
                              jQuery(".yx_ctiy_con li").each(function(i,v){
                                   cid = jQuery(v).attr("id");
                                   name = jQuery(v).text();
                                   clist.push({value : name,id : cid});
                                  }
                              )
                              if(clist.length!=0){

                                  var html = '';
                                  var dom = "<span class='yx_city_span'>已选国家：</span><ul id='city_list'>";
                                    cityName.splice(0, cityName.length);
                                    cityId.splice(0, cityId.length);
                                    jQuery(clist).each(function(i,v){
                                      html += "<li id='"+v.id+"'>"+v.value+"</li>";
                                      cityName.push(v.value);
                                      cityId.push(v.id);
                                    });
                                    jQuery('#mdd_con').html(dom+html+"</ul>");
                                    jQuery('#trave_id').val(cityId);
                                    jQuery('#trave_name').val(cityName);
                              }else{

                                 jQuery('#mdd_con').html("");
                                 jQuery('#trave_id').val("");
                                 jQuery('#trave_name').val("");
                              }
                              return true;
                          },
                          focus: true
                      },
                      {
                          name: '取消',
                          callback: function () {
                          }
                      }
                  ]
            });
        })

		jQuery("#sel_zys").click(function(){
		       var s = jQuery(this).is(':checked');
		       if(s){
		         jQuery(this).parent().siblings(".yz_mes_error").remove();
		       }
		 })
    /*******************多旅游目的地选择-结束******************/ 
    //投保人姓名拼音转换
	/*jQuery("#applicantName").live("keyup keydown change blur",function (){
	    jQuery("#applicantEnName").val(jQuery(this).toPinyin());
	});
    //被保人姓名拼音转换
    jQuery("[id*='recognizeeName']").live("keyup keydown change blur",function (){
        jQuery(this).parent().siblings().children("[id*='recognizeeEnName']").val(jQuery(this).toPinyin());
    });*/
    // 选择投被保人关系时触发证件类型控制性别和出生日期
    jQuery("[id*='recognizeeRelation']").live("change",function(){
	    var Typesname =  jQuery(this).parent().siblings().find(".typeoption").find("option:selected").text();
	    console.log(Typesname)
	    if(Typesname=="身份证"){
	       jQuery(this).parent().siblings(".typeoption").hide();
	       jQuery(this).parent().siblings('.idControl').hide();
	    }else{
	       jQuery(this).parent().siblings(".typeoption").show();
	       jQuery(this).parent().siblings('.idControl').show();
	    }
	    console.log()
	 });
});

function validateExcelId(obj){
	var id_index = jQuery(obj).attr("id").split("_")[1];
	var id_value = jQuery(obj).val();
	var idtype_value = jQuery("#sdrecognizeeTypeName_"+id_index).val();
	if(idtype_value.indexOf("身份证")!=-1){
		try{
			if(id_value.substring(16,17)%2==0){  
				jQuery("#sdrecognizeeSexName_"+id_index).val("女");
			}if(id_value.substring(16,17).toLowerCase()=='x'){
				jQuery("#sdrecognizeeSexName_"+id_index).val("女");
			}else{
				jQuery("#sdrecognizeeSexName_"+id_index).val("男");
			}
			var year = id_value.substring(6, 10);      
			var month = id_value.substring(10, 12);      
			var day = id_value.substring(12, 14);
			jQuery("#sdrecognizeeBirthday_"+id_index).val(year+"-"+month+"-"+day);
		}catch(error){
			
		}
	}
}
/**
 * 根据长期险的证件有效期控制截止日期，当勾选长期时，截止日期为不可编辑并清空内容
 */
function CheckapplicantEndID(applicantEndID,checkid){
	if(applicantEndID.length && applicantEndID.length>0){
		if(jQuery(checkid).attr("checked")==true){
			jQuery(applicantEndID).val("");
			jQuery(applicantEndID).attr("disabled","disabled");
			jQuery(applicantEndID).removeAttr("verify");
			
			if(jQuery(applicantEndID).parent().children("label").next().is('i')){
				jQuery(applicantEndID).parent().children("i").remove();
			}
			jQuery(applicantEndID).parent().children("label").after(jQuery(" <i class=\"yz_mes_yes\">  </i>"));
	    }else{
	    	jQuery(applicantEndID).attr("disabled","");
	    	jQuery(applicantEndID).attr("verify","证件有效日期|NOTNULL");
	    }
	}
}
/*此方法暂时不用，请不要删除--cuishigang
 * function uploadAfter(){
	jQuery("#uploadify").uploadify({ 
	       'swf': sinosoft.base+'/wwwroot/kxb/style/shop/images/uploadify.swf',
	       'uploader':sinosoft.base+'/shop/order_config_new!importBatch.action',   
	       'buttonImage':sinosoft.base+'/wwwroot/kxb/style/shop/images/xz_tbale2_03.gif',  
	       'formData' : {effdateNew:getEff(),supplierCode2:companyCode,productId:productId,insureJson:insuredElementsd(),dutyJson:dutyElements(),dutyDisReq:dutyElementsDis(),dutyPremReq:dutyPrem()},
	       'folder': 'BuyExcel',                
	       'queueID': 'fileQueue',   
	       'fileObjName' : 'uploadfile',
	       'auto': true,                
	       'multi': false,
	       'width': 92,
	       'height': 28,
	       'fileTypeDesc' : 'Excel文件', 
	       'fileTypeExts': '*.xls',
	       'uploadLimit': 864000,
	       'onUploadStart' : function(file) {
	    	   jQuery("#uploadify").uploadify('settings','formData', {effdateNew:getEff(),supplierCode2:companyCode,productId:productId,insureJson:insuredElementsd(),dutyJson:dutyElements(),dutyDisReq:dutyElementsDis(),dutyPremReq:dutyPrem()});
	    	   jQuery("#importMessage").hide();  
	       },
	       'onUploadError': function(event, queueID, fileObj,errorObj)   
	        {    
	    	   //jQuery('#upmessage').html("文件:" + fileObj.name + " 上传失败,请确保没修改过模板结构");
	    	   //alert(1);
	        } ,
	        'onUploadSuccess':function(file, data, response)
	        {  
	        	//jQuery("#uploadify").uploadify('settings', 'buttonImage', sinosoft.base+'shop/images/uploadify-cancel.png');
	            var peoples=eval('('+data +')');
	            if(peoples.state=="0"){
	            	importSuccess(peoples,peoples.successMsg);
	            }else{
	            	importError(peoples,peoples.errorMsg);
	            }
	        }
	  });
}*/
var excelinsuredCount = jQuery("#insNum").html();// excel导入被保人人数
var excelinsuredImpFlag = false;//是否处于编辑状态标识  false:没有；true 有
var excelsaveFlag = true;//excel导入是否有效，false:无效不能保存；true:有效 可以保存
function importSuccess(peoples,successMsg){
	 var returnList = peoples.insuredList;
	 var a_prem = 0;
	 deleteTableRow();
	 if(returnList!=null){
		 var option = "<option value='身份证'>身份证</option><option value='护照'>护照</option><option value='其他'>其他</option>";
		 if(companyCode=="2071"){
			 option = "<option value='身份证'>身份证</option><option value='中国护照'>中国护照</option><option value='外国护照'>外国护照</option><option value='其他证件'>其他证件</option>";
		 }
		 var c_info ="<ul class='table_active'>";
		 for(var i=0;i<returnList.length;i++){
			 var info ="<tr id='sdrecognizee_"+i+"'>"
			   +" <td scope='col'>"+(i+1)+"</td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeName_"+i+"'>"+returnList[i].recognizeeName+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeName_"+i+"' excelverify='姓名|NOTNULL&UFO&LEN>2' name='sdinsuredList["+i+"].recognizeeName' class='td_input_a' value='"+returnList[i].recognizeeName+"'/></em></em></td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeRelationName_"+i+"'>"+returnList[i].recognizeeAppntRelationName+"</em><em class='tb_up_date' style='display:none'><select id='sdrecognizeeRelationName_"+i+"' excelverify='与投保人关系|NOTNULL&ONLYONE=本人' name='sdinsuredList["+i+"].recognizeeAppntRelationName' class='td_select_a'><option value='本人'>本人</option><option value='其他'>其他</option></select></em></td>" //value='"+returnList[i].recognizeeAppntRelationName+"'
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeTypeName_"+i+"'>"+returnList[i].recognizeeIdentityTypeName+"</em><em class='tb_up_date' style='display:none'><select id='sdrecognizeeTypeName_"+i+"' excelverify='证件类型|NOTNULL' name='sdinsuredList["+i+"].recognizeeIdentityTypeName' class='td_select_a'></select></em></td>"//value='"+returnList[i].recognizeeIdentityTypeName+"'
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeId_"+i+"'>"+returnList[i].recognizeeIdentityId+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeId_"+i+"' onchange='validateExcelId(this)' excelverify='证件号码|NOTNULL&IDCARDEXCEL' name='sdinsuredList["+i+"].recognizeeIdentityId' class='td_input_b' value='"+returnList[i].recognizeeIdentityId+"'/></em></td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeBirthday_"+i+"'>"+returnList[i].recognizeeBirthday+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeBirthday_"+i+"' excelverify='出生日期|NOTNULL&AGE&CALLPREM' name='sdinsuredList["+i+"].recognizeeBirthday' class='td_input_a' value='"+returnList[i].recognizeeBirthday+"'/></em></td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeSexName_"+i+"'>"+returnList[i].recognizeeSexName+"</em><em class='tb_up_date' style='display:none'><select id='sdrecognizeeSexName_"+i+"' excelverify='证件类型|NOTNULL' name='sdinsuredList["+i+"].recognizeeSexName' class='td_select_b'><option value='男'>男</option><option value='女'>女</option></select></em></td>" //value='"+returnList[i].recognizeeSexName+"'
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeMobile_"+i+"'>"+returnList[i].recognizeeMobile+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeMobile_"+i+"' excelverify='手机号|MOBILENO&LEN=11' name='sdinsuredList["+i+"].recognizeeMobile' class='td_input_c' value='"+returnList[i].recognizeeMobile+"'/></em></td>";
			   if(jQuery("#excleTempEnName").val()=="Y"){
				   info=info  +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeEnName_"+i+"'>"+returnList[i].recognizeeEnName+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeEnName_"+i+"' excelverify='英文名|NOTNULL&UFO&ENG&LEN>2' name='sdinsuredList["+i+"].recognizeeEnName' class='td_input_a' value='"+returnList[i].recognizeeEnName+"'/></em></td>";
			   }
			   info=info +" <td scope='col'><em id='em_sddiscountPrice_"+i+"'>"+returnList[i].discountPrice+"</em><em style='display:none'><input type='text' id='sddiscountPrice_"+i+"' name='sdinsuredList["+i+"].discountPrice' class='td_input_d' readonly value='"+returnList[i].discountPrice+"'/></em></td>"
			   +" <td style='width:130px'><span  onclick='edit_user(sdrecognizee_"+i+",this)' class='eidit-uptable eidit-btns2s'>修改 </span><span class='eidit-uptable td_ac_del' onclick='del_user(sdrecognizee_"+i+",this);' class='eidit-uptable '>删除</span></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeePrem_"+i+"'>"+returnList[i].recognizeePrem+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeePrem_"+i+"' name='sdinsuredList["+i+"].recognizeePrem' value='"+returnList[i].recognizeePrem+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeTotalPrem_"+i+"'>"+returnList[i].recognizeeTotalPrem+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeTotalPrem_"+i+"' name='sdinsuredList["+i+"].recognizeeTotalPrem' value='"+returnList[i].recognizeeTotalPrem+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeTypeId_"+i+"'>"+returnList[i].recognizeeIdentityType+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeTypeId_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityType' value='"+returnList[i].recognizeeIdentityType+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeRelation_"+i+"'>"+returnList[i].recognizeeAppntRelation+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeRelation_"+i+"' name='sdinsuredList["+i+"].recognizeeAppntRelation' value='"+returnList[i].recognizeeAppntRelation+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeSex_"+i+"'>"+returnList[i].recognizeeSex+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeSex_"+i+"' name='sdinsuredList["+i+"].recognizeeSex' value='"+returnList[i].recognizeeSex+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeedestinationCountry_"+i+"'>"+returnList[i].destinationCountry+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeedestinationCountry_"+i+"' name='sdinsuredList["+i+"].destinationCountry' value='"+returnList[i].destinationCountry+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeedestinationCountryText_"+i+"'>"+returnList[i].destinationCountryText+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeedestinationCountryText_"+i+"' name='sdinsuredList["+i+"].destinationCountryText' value='"+returnList[i].destinationCountryText+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeflightNo_"+i+"'>"+returnList[i].flightNo+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeflightNo_"+i+"' name='sdinsuredList["+i+"].flightNo' value='"+returnList[i].flightNo+"'/></em></td>";
			   if(returnList[i].flightTime!=null && returnList[i].flightTime!=''){
				   info=info  +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeflightTime_"+i+"'>"+returnList[i].flightTime+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeflightTime_"+i+"' name='sdinsuredList["+i+"].flightTime' value='"+returnList[i].flightTime+"'/></em></td>";
			   }
			   	   info=info +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeflightLocation_"+i+"'>"+returnList[i].flightLocation+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeflightLocation_"+i+"' name='sdinsuredList["+i+"].flightLocation' value='"+returnList[i].flightLocation+"'/></em></td>";
			 jQuery("#insuredtab").append(info);  
			 jQuery("#sdrecognizeeRelationName_"+i).val(returnList[i].recognizeeAppntRelationName);
			 jQuery("#sdrecognizeeTypeName_"+i).append(option);
			 jQuery("#sdrecognizeeTypeName_"+i).val(returnList[i].recognizeeIdentityTypeName);
			 jQuery("#sdrecognizeeSexName_"+i).val(returnList[i].recognizeeSexName);
			// c_info = c_info+"<li><span onclick='edit_user(sdrecognizee_"+i+",this)'>编辑 </span><span class='td_ac_del' onclick='del_user(sdrecognizee_"+i+",this)'>×</span></li>";
		 }
		// c_info = c_info+"</ul>";
		 //jQuery("#insuredtab").after(c_info);  
		 dealrecognizeeBir();
		 excelsaveFlag = true;
		 excelinsuredImpFlag = false;
     }
	 jQuery("#insuedlistDiv").show();
	 if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=peoples.proTolPrem;
		 //document.getElementById("discountPrice_").innerHTML=obj.retTotlePrem;
		 jQuery("#productTotalPrice").attr("value",peoples.disPrem); 
	 }
	 
	 if (jQuery("#pointExchangeFlag").val() == '1') {
		 var PointScalerUnit = jQuery("#PointScalerUnit").val();
		 if (PointScalerUnit == null || PointScalerUnit == '') {
			 PointScalerUnit = '10';
		 }
		 var pointPrem = (peoples.insuredPrem * parseInt(PointScalerUnit)).toFixed(1);
		 var pointPrem1 = Math.ceil(pointPrem);
		 document.getElementById("priceTotle").innerHTML=Math.ceil(pointPrem1);
		 jQuery("#offsetPoint").val(pointPrem1);
		 document.getElementById("priceTotle_1").innerHTML=peoples.proTolPrem;
		 jQuery("#payPrice").val(0);
	 } else {
		 jQuery("#payPrice").val(peoples.insuredPrem);
		 jQuery("#productTotalPrice").attr("value",peoples.proTolPrem);
		 document.getElementById("priceTotle_1").innerHTML=peoples.proTolPrem;
		 document.getElementById("priceTotle").innerHTML=peoples.insuredPrem; 
	 }
	 jQuery("#totalAmount").val(peoples.disPrem);
	 jQuery("#importMessage").html(successMsg);
	 jQuery("#importMessage").show();
	 excelinsuredCount = returnList.length;
	 jQuery("#insNum").html(returnList.length);
	 jQuery("#insMult").html(returnList.length);
	 jQuery("#insMult_1").html(returnList.length);
	 if(returnList.length>=1){
		 jQuery("#impValadate").val("Y");//是否有错误信息，"Y":没有； "N"：有
	 }
	 
	 if (jQuery("#pointExchangeFlag").val() != '1') {
		 var varPrice = jQuery("#priceTotle").text();
			var cpsUserId = jQuery.cookie('cpsUserId');
			var channelsn = "wj";
			if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
				//channelsn = "cps";
			}
			var insMult = returnList.length;
			jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+varPrice+"&productId="+productId+"&channelsn="+jQuery("#channelsn").val()+"&risktypeNum="+insMult+"&orderSnTem="+orderSn,
						function(data) {
	 					    if(data){
	 					    	if (data.inspageone_pointsInfo != "") {
	 					    		jQuery('.integal_hjcon').show();
	 					    		jQuery(".integral_hj").html(data.inspageone_pointsInfo);
	 					    	}
	 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
								jQuery("#result_sendPoints").html(data.result_sendPoints);
								jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
								whenIntegralZero(data.result_sendPoints);
							}
				         }
				);
		 
	 }
	 
}
function importError(peoples,errorMsg){
	var returnList = peoples.insuredList;
	 var a_prem = 0;
	 deleteTableRow();
	 if(returnList!=null){
		 var option = "<option value='身份证'>身份证</option><option value='护照'>护照</option><option value='其他'>其他</option>";
		 if(companyCode=="2071"){
			 option = "<option value='身份证'>身份证</option><option value='中国护照'>中国护照</option><option value='外国护照'>外国护照</option><option value='其他证件'>其他证件</option>";
		 }
		 var c_info ="<ul class='table_active'>";
		 for(var i=0;i<returnList.length;i++){
			 var info ="<tr id='sdrecognizee_"+i+"'>"
			   +" <td scope='col'>"+(i+1)+"</td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeName_"+i+"'>"+returnList[i].recognizeeName+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeName_"+i+"' excelverify='姓名|NOTNULL&UFO&LEN>2&CHANDEH' name='sdinsuredList["+i+"].recognizeeName' class='td_input_a' value='"+returnList[i].recognizeeName+"'/></em></em></td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeRelationName_"+i+"'>"+returnList[i].recognizeeAppntRelationName+"</em><em class='tb_up_date' style='display:none'><select id='sdrecognizeeRelationName_"+i+"' excelverify='与投保人关系|NOTNULL&ONLYONE=本人' name='sdinsuredList["+i+"].recognizeeAppntRelationName' class='td_select_a'><option value='本人'>本人</option><option value='其他'>其他</option></select></em></td>" //value='"+returnList[i].recognizeeAppntRelationName+"'
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeTypeName_"+i+"'>"+returnList[i].recognizeeIdentityTypeName+"</em><em class='tb_up_date' style='display:none'><select id='sdrecognizeeTypeName_"+i+"' excelverify='证件类型|NOTNULL' name='sdinsuredList["+i+"].recognizeeIdentityTypeName' class='td_select_a'></select></em></td>"//value='"+returnList[i].recognizeeIdentityTypeName+"'
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeId_"+i+"'>"+returnList[i].recognizeeIdentityId+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeId_"+i+"' onchange='validateExcelId(this)' excelverify='证件号码|NOTNULL&IDCARDEXCEL&LEN>2&LEN<40' name='sdinsuredList["+i+"].recognizeeIdentityId' class='td_input_b' value='"+returnList[i].recognizeeIdentityId+"'/></em></td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeBirthday_"+i+"'>"+returnList[i].recognizeeBirthday+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeBirthday_"+i+"' excelverify='出生日期|NOTNULL&AGE&CALLPREM' name='sdinsuredList["+i+"].recognizeeBirthday' class='td_input_a' value='"+returnList[i].recognizeeBirthday+"'/></em></td>"
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeSexName_"+i+"'>"+returnList[i].recognizeeSexName+"</em><em class='tb_up_date' style='display:none'><select id='sdrecognizeeSexName_"+i+"' excelverify='证件类型|NOTNULL&CARDSEX' name='sdinsuredList["+i+"].recognizeeSexName' class='td_select_b'><option value='男'>男</option><option value='女'>女</option></select></em></td>" //value='"+returnList[i].recognizeeSexName+"'
			   +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeMobile_"+i+"'>"+returnList[i].recognizeeMobile+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeMobile_"+i+"' excelverify='手机号|MOBILENO&LEN=11' name='sdinsuredList["+i+"].recognizeeMobile' class='td_input_c' value='"+returnList[i].recognizeeMobile+"'/></em></td>"
			   if(jQuery("#excleTempEnName").val()=="Y"){
				   info=info  +" <td scope='col'><em class='tb_mes' id='em_sdrecognizeeEnName_"+i+"'>"+returnList[i].recognizeeEnName+"</em><em class='tb_up_date' style='display:none'><input type='text' id='sdrecognizeeEnName_"+i+"' excelverify='英文名|NOTNULL&UFO&ENG&LEN>2' name='sdinsuredList["+i+"].recognizeeEnName' class='td_input_a' value='"+returnList[i].recognizeeEnName+"'/></em></td>";
			   }
			   info=info +" <td scope='col'><em id='em_sddiscountPrice_"+i+"'>"+returnList[i].discountPrice+"</em><em style='display:none'><input type='text' id='sddiscountPrice_"+i+"' name='sdinsuredList["+i+"].discountPrice' class='td_input_d' readonly value='"+returnList[i].discountPrice+"'/></em></td>"
			   +" <td style='width:130px'><span  onclick='edit_user(sdrecognizee_"+i+",this)' class='eidit-uptable eidit-btns2s'>修改 </span><span class='eidit-uptable td_ac_del' onclick='del_user(sdrecognizee_"+i+",this);' class='eidit-uptable '>删除</span></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeePrem_"+i+"'>"+returnList[i].recognizeePrem+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeePrem_"+i+"' name='sdinsuredList["+i+"].recognizeePrem' value='"+returnList[i].recognizeePrem+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeTotalPrem_"+i+"'>"+returnList[i].recognizeeTotalPrem+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeTotalPrem_"+i+"' name='sdinsuredList["+i+"].recognizeeTotalPrem' value='"+returnList[i].recognizeeTotalPrem+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeTypeId_"+i+"'>"+returnList[i].recognizeeIdentityType+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeTypeId_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityType' value='"+returnList[i].recognizeeIdentityType+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeRelation_"+i+"'>"+returnList[i].recognizeeAppntRelation+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeRelation_"+i+"' name='sdinsuredList["+i+"].recognizeeAppntRelation' value='"+returnList[i].recognizeeAppntRelation+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeSex_"+i+"'>"+returnList[i].recognizeeSex+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeSex_"+i+"' name='sdinsuredList["+i+"].recognizeeSex' value='"+returnList[i].recognizeeSex+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeedestinationCountry_"+i+"'>"+returnList[i].destinationCountry+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeedestinationCountry_"+i+"' name='sdinsuredList["+i+"].destinationCountry' value='"+returnList[i].destinationCountry+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeedestinationCountryText_"+i+"'>"+returnList[i].destinationCountryText+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeedestinationCountryText_"+i+"' name='sdinsuredList["+i+"].destinationCountryText' value='"+returnList[i].destinationCountryText+"'/></em></td>"
			   +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeflightNo_"+i+"'>"+returnList[i].flightNo+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeflightNo_"+i+"' name='sdinsuredList["+i+"].flightNo' value='"+returnList[i].flightNo+"'/></em></td>";
			   if(returnList[i].flightTime!=null && returnList[i].flightTime!=''){
				   info=info  +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeflightTime_"+i+"'>"+returnList[i].flightTime+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeflightTime_"+i+"' name='sdinsuredList["+i+"].flightTime' value='"+returnList[i].flightTime+"'/></em></td>";
			   }
			   	   info=info +" <td style='display:none;' scope='col'><em class='tb_mes' id='em_sdrecognizeeflightLocation_"+i+"'>"+returnList[i].flightLocation+"</em><em class='tb_up_date' style='display:none'><input type='hidden' id='sdrecognizeeflightLocation_"+i+"' name='sdinsuredList["+i+"].flightLocation' value='"+returnList[i].flightLocation+"'/></em></td>";
			 jQuery("#insuredtab").append(info);  
			 jQuery("#sdrecognizeeRelationName_"+i).val(returnList[i].recognizeeAppntRelationName);
			 jQuery("#sdrecognizeeTypeName_"+i).append(option);
			 jQuery("#sdrecognizeeTypeName_"+i).val(returnList[i].recognizeeIdentityTypeName);
			 jQuery("#sdrecognizeeSexName_"+i).val(returnList[i].recognizeeSexName);
			 //c_info = c_info+"<li><span onclick='edit_user(sdrecognizee_"+i+",this)'>编辑 </span><span class='td_ac_del' onclick='del_user(sdrecognizee_"+i+",this)'>×</span></li>";
		 }
		// c_info = c_info+"</ul>";
		// jQuery("#insuredtab").after(c_info);  
		 dealrecognizeeBir();
		 excelsaveFlag = true;
		 excelinsuredImpFlag = false;
    }
	 jQuery("#insuedlistDiv").show();
	 if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=peoples.proTolPrem;
		 jQuery("#productTotalPrice").attr("value",peoples.disPrem); 
	 }
	 jQuery("#totalAmount").val(peoples.disPrem);
	 jQuery("#payPrice").val(peoples.insuredPrem);
	 jQuery("#productTotalPrice").attr("value",peoples.proTolPrem);
	 document.getElementById("priceTotle_1").innerHTML=peoples.insuredPrem;
	 document.getElementById("priceTotle").innerHTML=peoples.insuredPrem; 

	 dealimpExcelError(errorMsg);
	 jQuery("#importMessage").show();
	 //jQuery("#importMessage").attr("style", "overflow:hidden;color:red");
	 excelinsuredCount = returnList.length;
	 jQuery("#insNum").html(returnList.length);
	 jQuery("#insMult").html(returnList.length);
	 jQuery("#insMult_1").html(returnList.length);
	 jQuery("#impValadate").val("N");//是否有错误信息，"Y":没有； "N"：有
	 if (jQuery("#pointExchangeFlag").val() != '1') {
		 var varPrice = peoples.insuredPrem;
			var cpsUserId = jQuery.cookie('cpsUserId');
			var channelsn = "wj";
			if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
				//channelsn = "cps";
			}
			var insMult = returnList.length;
			jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+varPrice+"&productId="+productId+"&channelsn="+jQuery("#channelsn").val()+"&risktypeNum="+insMult+"&orderSnTem="+orderSn,
						function(data) {
	 					    if(data){
	 					    	if (data.inspageone_pointsInfo != "") {
	 					    		jQuery('.integal_hjcon').show();
	 					    		jQuery(".integral_hj").html(data.inspageone_pointsInfo);
	 					    	}
	 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
								jQuery("#result_sendPoints").html(data.result_sendPoints);
								jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
								whenIntegralZero(data.result_sendPoints);
							}
				         }
				);
		 
	 }
}
/*excel导入 处理错误信息显示*/
function dealimpExcelError(errorMsg){
	jQuery("#importMessage").html(errorMsg);
	jQuery("div[id^='recognizee_']").each(function(){
		var id_index = jQuery(this).attr("id").split("_")[1];
		jQuery(this).find("i").each(function(){
			var i_id = jQuery(this).attr("id").split("_")[0];
			jQuery("#em_"+i_id+"_"+id_index).parent().addClass("td_hlight");
		});
	});
}
/*excel导入 为单被保人出身日期增加点击事件*/
function dealrecognizeeBir(){
	jQuery("input[id^='sdrecognizeeBirthday']").each(function(){
		jQuery(this).click(function(){
			WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+insEndDate+'}',maxDate:'%y-%M-{%d-'+insStartDate+'}'});
		});
	});
}

function importGError(errorMsg){
	 
	 deleteTableRow();
	  
	 jQuery("#insuedlistDiv").show();
	 
	 jQuery("#importMessage").html(errorMsg);
	 //jQuery("#importMessage").css("color","red");
	 jQuery("#importMessage").show();
	 jQuery("#impValadate").val("N");//是否有错误信息，"Y":没有； "N"：有
}
function deleteTableRow()
{
	jQuery("#insuredtab tr").eq(0).nextAll().remove();
	jQuery("#insuedlistDiv ul").remove();
}
/**
 * 得到生效日期
 * @returns
 */
function getEff(){
	return jQuery("#effective").val();
}
/**
 * 选中旅游目的地
 * @param obj
 */
function checkSchenGen(obj){ 
	var countryNo = 0;
	jQuery("input[name='DestinationCountry']").each(function(){
		if(jQuery(this).attr("checked")==true){
			countryNo = countryNo + 1;
		} 
	});
	if(countryNo>=11){
		jQuery(obj).removeAttr("checked");
	}else{
		var cla = jQuery(obj).parent().attr("class"); 
		if(cla.indexOf("_1")!=-1&&cla.indexOf("css_Country_0S")==-1){ 
			jQuery(".css_Country_0S").find("input").attr("checked",true); 
		}
		var tFlag = false;
		jQuery("input[name='DestinationCountry']").each(function(){
			if(jQuery(this).attr("checked")==true){
				var cla_1 = jQuery(this).parent().attr("class"); 
				if(cla_1.indexOf("_1")!=-1 && cla_1.indexOf("css_Country_0S")==-1){
					jQuery(".css_Country_0S").find("input").attr("checked",true); 
					tFlag = true;
				}
			} 
		}); 
		if(cla.indexOf("css_Country_0S")==-1&&cla.indexOf("_1")!=-1){
			if(!tFlag){jQuery(".css_Country_0S").find("input").attr("checked",false);}
		}
	}
}
/**
 * 旅游目的地刷选函数
 * @param obj 当前对象
 */
function setTabIndex(obj,clsName){
	jQuery("." + clsName).find("a").each(function(){
		if(jQuery(this).attr("id") == obj.id) {
			jQuery(this).attr("class", "selected");
		} else {
			jQuery(this).attr("class","");
		}
	});

	var type2 = jQuery(".wrap_gj_sx").find("a.selected").attr("id");

	jQuery("#index_Country_Set").find("li").each(function(){
		var cls2 = jQuery(this).attr("class");

		if (("css_Country_" + type2 == cls2) || type2=="ALL" ){
			jQuery(this).show();
		} else {
			jQuery(this).hide();
		}
	});
}
/**
 * 协议条款
 */
function agreeCheck(){
	 var agreecheckbox =  document.getElementById("agreecheckbox");
	 if(agreecheckbox.checked = "checked" ){
		 document.getElementById("qrgm_pay").disabled="";
	 }else{
		 document.getElementById("qrgm_pay").disabled="disabled";
	 }
}
/**
 * 生日改变后触发的函数，保费试算
 * @param obj
 */
function dateTrigger(obj){
	   
	   var relation_id = obj;
	   var _id ="_0";
		if(relation_id.indexOf("_")!=-1){
		    _id = "_"+relation_id.split("_")[1];
		} 
		 
		if(document.getElementById("relationFlag"+_id)){
			  var relationFlag = document.getElementById("relationFlag"+_id).value;
			   var brithday = ""; 
			   if(relationFlag == "Y"){
				   brithday = document.getElementById("applicantBirthday").value;
			   }else{
				   brithday = document.getElementById("recognizeeBirthday"+_id).value;
			   }  
			   
				var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
				var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
				var recoJson ="";
				if(jQuery("#mulInsuredFlag").val()=="rid_me"){
					recoJson = recognizeeAppInfo(); 
				}else{
					recoJson = recognizeeInfo(); 
				}
				var effective = "";
				if(document.getElementById("effective")){
					effective = document.getElementById("effective").value;
				}
				changeEndDateForToAge(brithday, effective);
				
				jQuery.ajax({
					url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
					+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
					+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
					data:{orderSnTem:orderSn},
					dataType: "json",
					async: true,
					success: function(data) {
						var obj = eval(data);  
						fillin(obj);
					}
				});
		}
	 
 }

/**
 * 生日改变后触发的函数前台调用，保费试算
 * @param obj
 */
function dateTriggerFront(obj){
	validateBirthday(obj);
	  if(calPremFlag == true){
	   var relation_id = obj;
	   var _id ="_0";
	   var  id ="0";
		if(relation_id.indexOf("_")!=-1){
		    _id = "_"+relation_id.split("_")[1];
		    id= relation_id.split("_")[1];
		}
		if(document.getElementById("relationFlag"+_id)){
			  var relationFlag = document.getElementById("relationFlag"+_id).value;
			   var brithday = ""; 
			   var sexvalue = ""; 
			   if(relationFlag == "Y"){
				   brithday = document.getElementById("applicantBirthday").value;
				   sexvalue = jQuery("input[name='sdinformationinsuredList["+id+"].recognizeeSex']:checked").val();
				   if (sexvalue == null || sexvalue == undefined || sexvalue == "") {
					   sexvalue = document.getElementById("recognizeeSex"+_id).value;
					 }
				   jQuery("input[name=sdinformationAppnt.applicantSex][value='"+sexvalue+"']").attr("checked",'checked');
			   }else{
				   brithday = document.getElementById("recognizeeBirthday"+_id).value;
			   }  
			   
				var insureJson = encodeURIComponent(encodeURIComponent(insuredElements(_id)));
				var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
				var recoJson ="";
				if(jQuery("#mulInsuredFlag").val()=="rid_me"){
					recoJson = recognizeeAppInfo(); 
				}else{
					recoJson = recognizeeInfo(); 
				}
				var effective = "";
				if(document.getElementById("effective")){
					effective = document.getElementById("effective").value;
				}
				changeEndDateForToAge(brithday, effective);
				
				jQuery.ajax({
					url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
					+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
					+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
					data:{orderSnTem:orderSn},
					dataType: "json",
					async: true,
					success: function(data) {
						var obj = eval(data);  
						fillin(obj);
					}
				});
		}
	   }
	 
 }


/**
 * 保险期限为至XX岁时，需要改变保单终止日期(被保险人XX岁的保单生效对应日前一天)
 * @param brithday 被保人生日(yyyy-MM-dd)
 */
function changeEndDateForToAge(brithday, effective) {
	if (document.getElementById("Ensure") && document.getElementById("Ensure").value.indexOf("A") != -1) {
		var toAge = document.getElementById("Ensure").value;
		toAge = parseInt(toAge.substring(0, toAge.indexOf("A")));
		var specialEffDateFlag=document.getElementById("specialEffDateFlag").value;
		var brithDate = getDate(brithday);
		var effDate = getDate(effective);
		
		if (brithDate.getMonth() < effDate.getMonth()) {
			brithDate.setFullYear(brithDate.getFullYear() + toAge);
		} else if (brithDate.getMonth() > effDate.getMonth()) {
			brithDate.setFullYear(brithDate.getFullYear() + toAge + 1);
		} else {
				//mod by wangej 20160223 处理日期相等的情况，年应该+1
			if (brithDate.getDate() <= effDate.getDate()) {
				brithDate.setFullYear(brithDate.getFullYear() + toAge);
			} else {
				brithDate.setFullYear(brithDate.getFullYear() + toAge + 1);
			}
		}
		var endDate;
		if(specialEffDateFlag=="Y"){//太平E宝贝重疾保障计划 终保日期根据被保人生日改变
		    brithDate.setDate(brithDate.getDate() - 1);
		    endDate =  dateToString2(brithDate);
		}else{
			effDate.setFullYear(brithDate.getFullYear());
			effDate.setDate(effDate.getDate() - 1);
			endDate =  dateToString2(effDate);
		}
		
		document.getElementById("fail_").value = endDate;
		document.getElementById("fail").value = endDate;
		//add by wangej 20160223 止保日期更新后，投保范围也随之更新
		var start = jQuery('#effective').val();
		var end = jQuery('#fail').val();
		
	 
		if(end=="9999-12-31"||end=="终身"){
			
			jQuery('#ensureDate').html("从&nbsp;&nbsp;&nbsp;"+start+"&nbsp;&nbsp;&nbsp;00时起到&nbsp;&nbsp;&nbsp;终身");
			jQuery('#ensureDate_em').html("( 从"+start+" 00时起到&nbsp;&nbsp;终身 )");
		}else{
			
			jQuery('#ensureDate').html("从&nbsp;&nbsp;&nbsp;"+start+"&nbsp;&nbsp;&nbsp;00时起到&nbsp;&nbsp;&nbsp;"+end+"&nbsp;&nbsp;&nbsp;24时为止");
			jQuery('#ensureDate_em').html("( 从"+start+" 00时起到"+end+" 24时为止 )");
		}
		 
	}
}

function reCalPrem(){
	var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
	var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
	var recoJson;
	
	var excelFlag = (jQuery("#mulInsuredFlag").val()=="rid_td" && jQuery("em[id^='em_sdrecognizeeBirthday']").length > 0);
	if (excelFlag) {
		recoJson = recognizeeExcelInfo(); 
	}else if(jQuery("#mulInsuredFlag").val()=="rid_me"||jQuery("#relationIsSelf").val()=="Y"){
		recoJson = recognizeeAppInfo(); 
	}else{
		recoJson = recognizeeInfo(); 
	}
	var effective = "";
	if(document.getElementById("effective")){
		effective = document.getElementById("effective").value;
	}
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
		+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
		+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
		data:{orderSnTem:orderSn},
		dataType: "json",
		async: false,
		success: function(data) {
			var obj = eval(data);  
			if (excelFlag) {
				fillinExcelDis(obj);
			} else {
				fillin(obj);
			}
		}
	});
 
}
function calPrem(){
		var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
		var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
		if(jQuery("#mulInsuredFlag").val()=="rid_me"||jQuery("#relationIsSelf").val()=="Y"){
			recoJson = recognizeeAppInfo(); 
		}else{
			recoJson = recognizeeInfo(); 
		}
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSnTem:orderSn},
			dataType: "json",
			async: false,
			success: function(data) {
				var obj = eval(data);  
				fillin(obj);
			}
		});
	 
}
function verifyCallPrem(cBoxName){
	   
	var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
	var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
	var	recoJson = recognizeeInfoForExcel(cBoxName); 
	var effective = "";
	if(document.getElementById("effective")){
		effective = document.getElementById("effective").value;
	}
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
		+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
		+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
		data:{orderSnTem:orderSn},
		dataType: "json",
		async: false,
		success: function(data) {
			var obj = eval(data);  
			fillinExcel(obj,cBoxName);
		}
	});
 
	return true;
}
function recognizeeExcelInfo() {
	var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsoninf = '';
	 var jsonlastPrem = "";
	 var i = 1;
	 var tlen = jQuery("em[id^='em_sdrecognizeeBirthday']").length;
	 jQuery("em[id^='em_sdrecognizeeBirthday']").each(function(){
	    var relation_id = jQuery(this).attr("id"); 
		var id_index ="";
		if(relation_id.indexOf("_")!=-1){
			id_index = relation_id.split("_")[2];
		}
		
		 var relation_id = "sdrecognizeeRelationName_"+id_index;
		 var sex = jQuery("#sdrecognizeeSexName_"+id_index).val();
		 var bir = jQuery("#sdrecognizeeBirthday_"+id_index).val();
		 var insuredCount = "1";
		 var IdentityType = jQuery("#sdrecognizeeTypeName"+id_index).val();
		 var IdentityId = jQuery("#sdrecognizeeId"+id_index).val();
		 var arr =new Array();
		 arr[0] = relation_id;
		 arr[1] = bir;
		 arr[2] = sex; 
		 arr[3] = insuredCount;
		 arr[4] = IdentityType;
		 arr[5] = IdentityId;
		 if(i==tlen){
			jsoninf = jsoninf+i+jsoncen+arr;
		}else{
			jsoninf = jsoninf+i+jsoncen+arr+'","'; 
		}
		i=i+1;
	});
	if (jsoninf != '') {
		var o=jsonstar+jsoninf+jsonend;
		var last=JSON.stringify(o);
		return o;
	} else {
		return "";
	}
}

function recognizeeInfoForExcel(cBoxName){
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsoninf = '';
	 var jsonlastPrem = "";
	 var i=1; 
	 var id_index = cBoxName.split("_")[1];
	 var relation_id = "sdrecognizeeRelationName_"+id_index;
	 var sex = jQuery("#sdrecognizeeSexName_"+id_index).val();
	 var bir = jQuery("#sdrecognizeeBirthday_"+id_index).val();
	 var insuredCount = "1";
	 var IdentityType = jQuery("#sdrecognizeeTypeName"+id_index).val();
	 var IdentityId = jQuery("#sdrecognizeeId"+id_index).val();
	 var arr =new Array();
	 arr[0] = relation_id;
	 arr[1] = bir;
	 arr[2] = sex; 
	 arr[3] = insuredCount;
	 arr[4] = IdentityType;
	 arr[5] = IdentityId;
	 jsoninf = jsoninf+i+jsoncen+arr;
	  
	var o=jsonstar+jsoninf+jsonend;
	var last=JSON.stringify(o);
	return o;
	
}
/**
 * 批量导入修改单被保人 保费试算后回调函数
 * @param obj
 */
function fillinExcel(obj,cBoxName){
	
	 var id_index = cBoxName.split("_")[1];
	 var rePrem = jQuery("#sddiscountPrice_"+id_index).val();//原网站折后保费
	 var reComPrem = jQuery("#sdrecognizeePrem_"+id_index).val();//原保险公司折后保费
	 var reTotalPrem = jQuery("#sdrecognizeeTotalPrem_"+id_index).val();//原折前保费
	 
	 var newPrem = obj.retCountPrem;
	 var newComPrem = obj.retPrem;
	 var newTotalPrem = obj.retTotlePrem;
	 
	 //更新保费
	 jQuery("#sddiscountPrice_"+id_index).val(newPrem);//原网站折后保费
	 jQuery("#sdrecognizeePrem_"+id_index).val(newComPrem);//原保险公司折后保费
	 jQuery("#sdrecognizeeTotalPrem_"+id_index).val(newTotalPrem);//原折前保费
	 
	 var subPrem = parseFloat(newPrem)-parseFloat(rePrem);
	 subPrem = subPrem.toFixed(2);
	 
	 var subComPrem = parseFloat(newComPrem)-parseFloat(reComPrem);
	 subComPrem = subComPrem.toFixed(2);
	 
	 var subTotalPrem = parseFloat(newTotalPrem)-parseFloat(reTotalPrem);
	 subTotalPrem = subTotalPrem.toFixed(2);
	 
     var priceTotle_1 = document.getElementById("priceTotle_1").innerHTML;
     var priceTotle = document.getElementById("priceTotle").innerHTML;
     var totalAmount = jQuery("#totalAmount").val();
	 var discountPrice = document.getElementById("discountPrice").innerHTML;
	 var newproductTotalPrice = document.getElementById("productTotalPrice").value;
	 var payPrice = document.getElementById("payPrice").value; 
	 var newpriceTotle_1 = parseFloat(priceTotle_1)+parseFloat(subComPrem);
	 newpriceTotle_1= newpriceTotle_1.toFixed(2);
     var newtotalAmount = parseFloat(totalAmount)+parseFloat(subComPrem);
     newtotalAmount= newtotalAmount.toFixed(2);
	 var newdiscountPrice = parseFloat(discountPrice)+parseFloat(subTotalPrem);
	 newdiscountPrice= newdiscountPrice.toFixed(2);
	 var newproductTotalPrice = parseFloat(newproductTotalPrice)+parseFloat(subTotalPrem);
	 newproductTotalPrice= newproductTotalPrice.toFixed(2);
	 var newpayPrice = parseFloat(payPrice)+parseFloat(subPrem);
	 newpayPrice= newpayPrice.toFixed(2);
	 if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=newdiscountPrice;
		 jQuery("#productTotalPrice").attr("value",newproductTotalPrice); 
	 }
	 if (jQuery("#pointExchangeFlag").val() == '1') {
		 var PointScalerUnit = jQuery("#PointScalerUnit").val();
		 if (PointScalerUnit == null || PointScalerUnit == '') {
			 PointScalerUnit = '10';
		 }
		 var pointPrem = (newpriceTotle_1 * parseInt(PointScalerUnit)).toFixed(1);
		 var pointPrem1 = Math.ceil(pointPrem);
		 document.getElementById("priceTotle").innerHTML=Math.ceil(pointPrem1);
		 document.getElementById("priceTotle_1").innerHTML=newpriceTotle_1;
		 jQuery("#offsetPoint").val(pointPrem1);
		 jQuery("#totalAmount").val(newpriceTotle_1);
		 jQuery("#payPrice").val(0);
	 } else {
		 jQuery("#totalAmount").val(newtotalAmount);
		 document.getElementById("priceTotle_1").innerHTML=newpriceTotle_1;
		 jQuery("#payPrice").val(newpayPrice);
		 document.getElementById("priceTotle").innerHTML=newpayPrice;
		 var cpsUserId = jQuery.cookie('cpsUserId');
		 var channelsn = "wj";
		 if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
			 //channelsn = "cps";
		 }
		var insMult = jQuery("#insMult").text();
		jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+newpayPrice+"&productId="+productId+"&channelsn="+jQuery("#channelsn").val()+"&risktypeNum="+insMult+"&orderSnTem="+orderSn,
				function(data) {
	 				if(data){
	 					if (data.inspageone_pointsInfo != "") {
	 						jQuery('.integal_hjcon').show();
					    	jQuery(".integral_hj").html(data.inspageone_pointsInfo);
					    }
	 					jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
						jQuery("#result_sendPoints").html(data.result_sendPoints);
						jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
						whenIntegralZero(data.result_sendPoints);
					}
				}
		);
	 }
	 
	 var warnMessage = obj.warnMessage;
	 if (warnMessage != null && warnMessage != '') {
		alert(warnMessage);
	 }
}

function fillinExcelDis(obj) {
	var errMessage = obj.errMessage;
	if (errMessage != null && errMessage != '') {
		alert(errMessage);
		return;
	}
	if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=obj.retCountPrem;
		 jQuery("#productTotalPrice").attr("value",obj.retTotlePrem); 
		 jQuery("#discountRates").attr("value",obj.discountRate);
	 }
	
	var returnList = obj.returnList;
	 // 折扣活动后金额
	 var a_prem = 0;
	 // 折扣活动前金额
	 var t_prem = 0;
	 // 原价
	 var y_prem = 0;
	 if(returnList!=null){
		 for(var i=0;i<returnList.length;i++){
			 var tValue = returnList[i].split("-");
			 var _id ="";
			 if(tValue[0].indexOf("_")!=-1){
				_id = "_"+tValue[0].split("_")[1];
			 }
			 
			 var _prem = tValue[1];
			 var _yPrem = tValue[2]; 
			 var _disPrem = tValue[3]; 
			 jQuery("#em_sddiscountPrice"+_id).text(_disPrem);
			 jQuery("#sddiscountPrice"+_id).val(_disPrem);//原网站折后保费
			 jQuery("#sdrecognizeePrem"+_id).val(_prem);//原保险公司折后保费
			 jQuery("#sdrecognizeeTotalPrem"+_id).val(_yPrem);//原折前保费
			 a_prem = parseFloat(a_prem)+parseFloat(_disPrem);
			 t_prem = parseFloat(t_prem)+parseFloat(_prem);
			 y_prem = parseFloat(y_prem)+parseFloat(_yPrem);
		 }
		 
	 }
	 a_prem = a_prem.toFixed(2);
	 t_prem = t_prem.toFixed(2);
	 y_prem = y_prem.toFixed(2);
	 jQuery("#totalAmount").val(t_prem);
	 jQuery("#productTotalPrice").attr("value",y_prem);
	 if (jQuery("#pointExchangeFlag").val() == '1') {
		 var PointScalerUnit = jQuery("#PointScalerUnit").val();
		 if (PointScalerUnit == null || PointScalerUnit == '') {
			 PointScalerUnit = '10';
		 }
		 var pointPrem = (a_prem * parseInt(PointScalerUnit)).toFixed(1);
		 var pointPrem1 = Math.ceil(pointPrem);
		 document.getElementById("priceTotle").innerHTML=Math.ceil(pointPrem1);
		 jQuery("#offsetPoint").val(pointPrem1);
		 
		 jQuery("#payPrice").val(0);
	 } else {
		 jQuery("#payPrice").val(a_prem);
		 document.getElementById("priceTotle").innerHTML=a_prem;
		 var cpsUserId = jQuery.cookie('cpsUserId');
		 var channelsn = "wj";
		 if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
			 //channelsn = "cps";
		 }
		 var insMult = jQuery("#insMult").text();
		 jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+a_prem+"&productId="+productId+"&channelsn="+jQuery("#channelsn").val()+"&risktypeNum="+insMult+"&orderSnTem="+orderSn,
						function(data) {
	 					    if(data){
	 					    	if (data.inspageone_pointsInfo != "") {
	 					    		jQuery('.integal_hjcon').show();
	 					    		jQuery(".integral_hj").html(data.inspageone_pointsInfo);
	 					    	}
	 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
								jQuery("#result_sendPoints").html(data.result_sendPoints);
								jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
								whenIntegralZero(data.result_sendPoints);
							}
				         }
				);
	 }
	 document.getElementById("priceTotle_1").innerHTML=y_prem;
	 
	 var warnMessage = obj.warnMessage;
	 if (warnMessage != null && warnMessage != '') {
		alert(warnMessage);
	 }
}
/**
 * 保费试算后回调函数
 * @param obj
 */
function fillin(obj,cFlag){
	var errMessage = obj.errMessage;
	if (errMessage != null && errMessage != '') {
		alert(errMessage);
		return;
	}
	if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=obj.retCountPrem;
		 jQuery("#productTotalPrice").attr("value",obj.retTotlePrem); 
		 jQuery("#discountRates").attr("value",obj.discountRate);
	 }
	var plan = "";
	if(document.getElementById("planCode")!=null){
		plan = document.getElementById("planCode").value;
	} 
	 var dutyAmntList = obj.retDutyAmounts; 
	 if(dutyAmntList!=null && dutyAmntList.length>0){
		 for(var i=0;i<dutyAmntList.length;i++){
			 var dutyAmnt = dutyAmntList[i];
			 if(document.getElementById(plan+dutyAmnt.dutyCode)!=null){
				 document.getElementById(plan+dutyAmnt.dutyCode).innerHTML = dutyAmnt.prem;
				 var premId = "prem" + "-" + plan + "-" +dutyAmnt.dutyCode;
				 if(document.getElementById(premId)!=null){ 
					 document.getElementById(premId).value = dutyAmnt.prem;
				 }
			 }
			 
		 }
	 }
	 var returnList = obj.returnList;
	 // 折扣活动后金额
	 var a_prem = 0;
	 // 折扣活动前金额
	 var t_prem = 0;
	 // 原价
	 var y_prem = 0;
	 if(returnList!=null){
		 for(var i=0;i<returnList.length;i++){
			 
			 var tValue = returnList[i].split("-");
			 var _id ="";
			 if(tValue[0].indexOf("_")!=-1){
				_id = "_"+tValue[0].split("_")[1];
			 }
			 
			 var _prem = tValue[1];
			 var _yPrem = tValue[2]; 
			 var _disPrem = tValue[3]; 
			 /*填写页面被保人保费*/  
			 if(cFlag!="1"){
				 jQuery("#recognizeeBirthdayID"+_id).html(_disPrem);
				 jQuery("#recognizeePrem"+_id).val(_prem);
				 jQuery("#recognizeeTotalPrem"+_id).val(_yPrem);
				 jQuery("#discountPrice"+_id).val(_disPrem);
			 }else{
				 jQuery("#recognizeeBirthdayID"+_id).html(_disPrem);
				 jQuery("#recognizeePrem"+_id).val(_prem);
				 jQuery("#recognizeeTotalPrem"+_id).val(_yPrem);
				 jQuery("#discountPrice"+_id).val(_disPrem);
				 
			 }  
			 var insuredCount = jQuery("#recognizeeMul"+_id).val();
			 if(jQuery("#mulInsuredFlag").val()=="rid_me"){
				 insuredCount = jQuery("#recognizeeMul").val();
				 jQuery("#insNum").html(1);
				 if(jQuery("#insMult").html()=="0"||jQuery("#insMult").html()==undefined || jQuery("#insMult").html()=="" ){
				   jQuery("#insMult").html(1);
				 }
				 jQuery("#insMult_1").html(1);
			 }else if(jQuery("#mulInsuredFlag").val()=="rid_me"){
				 insuredCount = jQuery("#recognizeeMul"+_id).val();
				  
			 }
			 if(insuredCount=="undefined" || insuredCount==null || insuredCount==""){
				 insuredCount="1";
			 }
		    if(jQuery("#mulInsuredFlag").val()=="undefined" || jQuery("#mulInsuredFlag").val()==null || jQuery("#mulInsuredFlag").val()==""){
			    //(b == false) ? a="true" : a="false"; (calInsuredCount()==0) ? 1 : calInsuredCount();
		    	 jQuery("#insNum").html((calInsuredCount()==0) ? 1 : calInsuredCount());
			     jQuery("#insMult").html((calInsuredCount()==0) ? 1 : calInsuredCount());
				 jQuery("#insMult_1").html((calInsuredCount()==0) ? 1 : calInsuredCount());
		    }
			 a_prem = parseFloat(a_prem)+parseFloat(_disPrem)*parseFloat(insuredCount);
			 t_prem = parseFloat(t_prem)+parseFloat(_prem)*parseFloat(insuredCount);
			 y_prem = parseFloat(y_prem)+parseFloat(_yPrem)*parseFloat(insuredCount);
			}
	 }
	 if(obj.insFlag!=null && obj.insFlag!="" && obj.insFlag!="undefined"){
		 var insFlag = obj.insFlag;
		 if(insFlag=="rid_td"){
			 a_prem = parseFloat(obj.retCountPrem);
			 t_prem = obj.retPrem;
			 y_prem = obj.retTotlePrem;
		 }
	 }
	 if(jQuery("#relationIsSelf").val()=="Y"){
		 a_prem = parseFloat(obj.retCountPrem);
		 t_prem = parseFloat(obj.retPrem);
		 y_prem = obj.retTotlePrem;
	 }
	 a_prem = a_prem.toFixed(2);
	 t_prem = t_prem.toFixed(2);
	 y_prem = y_prem.toFixed(2);
	 jQuery("#totalAmount").val(t_prem);
	 jQuery("#productTotalPrice").attr("value",y_prem);
	 
	 if (jQuery("#pointExchangeFlag").val() == '1') {
		 var PointScalerUnit = jQuery("#PointScalerUnit").val();
		 if (PointScalerUnit == null || PointScalerUnit == '') {
			 PointScalerUnit = '10';
		 }
		 var pointPrem = (a_prem * parseInt(PointScalerUnit)).toFixed(1);
		 var pointPrem1 = Math.ceil(pointPrem);
		 document.getElementById("priceTotle").innerHTML=Math.ceil(pointPrem1);
		 jQuery("#offsetPoint").val(pointPrem1);
		 
		 jQuery("#payPrice").val(0);
	 } else {
		 jQuery("#payPrice").val(a_prem);
		 document.getElementById("priceTotle").innerHTML=a_prem;
		 var cpsUserId = jQuery.cookie('cpsUserId');
		 var channelsn = "wj";
		 if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
			 //channelsn = "cps";
		 }
		 var insMult = jQuery("#insMult").text();
		 jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+a_prem+"&productId="+productId+"&channelsn="+jQuery("#channelsn").val()+"&risktypeNum="+insMult+"&orderSnTem="+orderSn,
						function(data) {
	 					    if(data){
	 					    	if (data.inspageone_pointsInfo != "") {
	 					    		jQuery('.integal_hjcon').show();
	 					    		jQuery(".integral_hj").html(data.inspageone_pointsInfo);
	 					    	}
	 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
								jQuery("#result_sendPoints").html(data.result_sendPoints);
								jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
								whenIntegralZero(data.result_sendPoints);
							}
				         }
				);
	 }
	 document.getElementById("priceTotle_1").innerHTML=y_prem;
	 
	 var warnMessage = obj.warnMessage;
	 if (warnMessage != null && warnMessage != '') {
		alert(warnMessage);
	 }
}
/**
 * 封装投保要素
 * @returns
 */
function insuredElements(id){
	 /*投保计划*/
	 var plan ="";
	 if(document.getElementById("planCode")){
		 plan  = document.getElementById("planCode").value;
	 }
	 var appType="";
	 if(document.getElementById("AppType")){
		appType=document.getElementById("AppType").value;
	 }
	 var feeYear="";
	 if(document.getElementById("chargeYear")){
	    feeYear=document.getElementById("chargeYear").value;
	 }
	 var grade="";
	 if(document.getElementById("grade")){ 
	     grade=document.getElementById("grade").value;
	 }
	 var mult="";
	 if(document.getElementById("AppMult")){ 
		 mult=document.getElementById("AppMult").value;
	 }
	 var sex = "M";
	 if (id == null) {
		 id = "_0";
	 }
	 if(jQuery("#mulInsuredFlag").val()=="rid_me"||jQuery("#relationIsSelf").val()=="Y"){
		 sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
		 if (sex == null || sex == undefined || sex == "") {
			 sex = document.getElementById("applicantSex").value;
		 }
	 } else {
		 if (id.indexOf("_") != -1) {
			 var idNum = id.substring(id.indexOf("_") + 1);
			 sex = jQuery("input[name='sdinformationinsuredList["+idNum+"].recognizeeSex']:checked").val();
		 }
	 }
	  
	 var obj = "";
	 var BirthDay = "";
	 BirthDay = initAge();
	 var selfFlag = jQuery("#relationFlag_0").val();
	 if(selfFlag=="Y"){
		 BirthDay = jQuery("#applicantBirthday").val();
		 sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
		 if (sex == null || sex == undefined || sex == "") {
			 sex = document.getElementById("applicantSex").value;
		 }
	 }
	 var period="";
	 if(document.getElementById("Ensure")){ 
	    period=document.getElementById("Ensure").value;
	 }
	 //针对长险的投保人
	 var mulPeople = "";
	 if(document.getElementById("mulPeople")){ 
		  mulPeople=document.getElementById("mulPeople").value;
		  mulPeople = encodeURI(encodeURI(mulPeople));
	 }

	 var o={"Plan":plan,"AppType":encodeURIComponent(appType),"FeeYear":feeYear,"Grade":encodeURIComponent(grade),"Mult":encodeURIComponent(mult),"TextAge":BirthDay,"Period":encodeURIComponent(period),"Sex":encodeURIComponent(sex),"MulPeople":mulPeople};
	 var last=JSON.stringify(o);
	 return last;
}
/**
 * 责任要素
 */
function dutyElements(){
	 var dutyCodeSize;
	 if ((null == document.getElementById("dutyFactorSize")) ||("" == document.getElementById("dutyFactorSize")) ||  ("undefined" == typeof (document.getElementById("dutyFactorSize")))) {
		 dutyCodeSize="0";
	 }else{
		 dutyCodeSize=document.getElementById("dutyFactorSize").value;
	 }
	 var tempDuty = parseInt(dutyCodeSize);
	 var plan = "";
	 if(document.getElementById("planCode")!=null){
		 plan = document.getElementById("planCode").value;
	 }
	 tempDuty=tempDuty+1;
	 var dutyCode="";
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsonlast = "";
	 var nvalue="nvalue";
	 
	 if(tempDuty>0){//有责任信息
		 for(var i=1;i<tempDuty;i++){
		 	dutyCode = "dutyCode"+i;
		 	/*责任编码*/
		 	var dutyCodeTemp=document.getElementById(dutyCode).value;
		 	var amntId = "amnt" + "-" + plan +  "-" + i;
		 	if(document.getElementById(amntId)){
		 		/*责任编码*/
		 		var amntTemp=document.getElementById(amntId).value;
		 		if(i<tempDuty-1){
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+encodeURIComponent(amntTemp)+'","';
			 	}else{
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+encodeURIComponent(amntTemp);
			 	}
		 	}else{
			 	if(i<tempDuty-1){
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+nvalue+'","';
			 	}else{
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+nvalue;
			 	}
		 	}
		 }
	jsonlast = jsonstar+jsonend;
	 }
	return jsonlast;
}

/**
 * 
 * @returns {String}
 */
function dutyElementsDis(){
	 var dutyCodeSize;
	 if ((null == document.getElementById("dutyFactorSize")) ||("" == document.getElementById("dutyFactorSize")) ||  ("undefined" == typeof (document.getElementById("dutyFactorSize")))) {
		 dutyCodeSize="0";
	 }else{
		 dutyCodeSize=document.getElementById("dutyFactorSize").value;
	 }
	 var tempDuty = parseInt(dutyCodeSize);
	 var plan = "";
	 if(document.getElementById("planCode")!=null){
		 plan = document.getElementById("planCode").value;
	 }
	 tempDuty=tempDuty+1;
	 var dutyCode="";
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsonlastDis = "";
	 var nvalue="nvalue";
	 
	 if(tempDuty>0){//有责任信息
		 for(var i=1;i<tempDuty;i++){
		 	dutyCode = "dutyCode"+i;
		 	var dutyCodeTemp=document.getElementById(dutyCode).value;//责任编码
		 	var amntDisId = "amntDis" + "-" +plan + "-" +i;
		 	if(document.getElementById(amntDisId)){
		 		var amntTemp=document.getElementById(amntDisId).value;//责任编码
		 		if(i<tempDuty-1){
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+amntTemp+'","';
			 	}else{
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+amntTemp;
			 	}
		 	}else{
			 	if(i<tempDuty-1){
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+nvalue+'","';
			 	}else{
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+nvalue;
			 	}
		 	}
		 }
	jsonlastDis = jsonstar+jsonend;
	 }
	return jsonlastDis;
}
/**
 * 投保计划
 * @returns
 */
function insuredElementsd(){
	 /*投保计划*/
	 var plan="";
	 if(document.getElementById("planCode")){
		plan = document.getElementById("planCode").value;
	 }
	/*投保类型*/
	 var appType="";
	 if(document.getElementById("AppType")){
		appType=document.getElementById("AppType").value;
	 }
	 /*缴费年期*/
	 var feeYear="";
	 if(document.getElementById("chargeYear")){
	    feeYear=document.getElementById("chargeYear").value;
	 }
	 /*产品级别*/
	 var grade="";
	 if(document.getElementById("grade")){ 
	    grade=document.getElementById("grade").value;
	 }
	 /*购买份数*/
	 var mult="";
	 if(document.getElementById("AppMult")){ 
		 mult=document.getElementById("AppMult").value;
	 }
	 /*被保人生日*/
	 var BirthDay="";
	 if(document.getElementById("recognizeeBirthday1")){ 
	     BirthDay=document.getElementById("recognizeeBirthday1").value;
	 }
	 /*保险期限*/
	 var period="";
	 if(document.getElementById("Ensure")){ 
	     period=document.getElementById("Ensure").value;
	 }
	 /*被保人性别*/
	 var sex;
	 if(jQuery("#mulInsuredFlag").val()=="rid_me"||jQuery("#relationIsSelf").val()=="Y"){
		 sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
		 if (sex == null || sex == undefined || sex == "") {
			 sex = document.getElementById("applicantSex").value;
		 }
	 } else {
		 sex = jQuery("input[name='sdinformationinsuredList[0].recognizeeSex']:checked").val();
		 if ((sex == null || sex == "") && document.getElementById("applicantSex")) {
			 sex = document.getElementById("applicantSex").value;
		 }
	 }
	 
	 var mulPeople = "";
	 /*适用人群*/
	 if(document.getElementById("mulPeople")){ 
		  mulPeople=document.getElementById("mulPeople").value;
	 }
	 var o={"Plan":plan,"AppType":encodeURIComponent(appType),"FeeYear":encodeURIComponent(feeYear),"Grade":encodeURIComponent(grade),"Mult":encodeURIComponent(mult),"TextAge":BirthDay,"Period":encodeURIComponent(period),"Sex":encodeURIComponent(sex),"MulPeople":mulPeople};
	 var last=JSON.stringify(o);
	 return last;
}
/**
 * 
 * @returns {String}
 */
function dutyPrem(){
	 var dutyCodeSize;
	if ((null == document.getElementById("dutyFactorSize")) ||("" == document.getElementById("dutyFactorSize")) ||  ("undefined" == typeof (document.getElementById("dutyFactorSize")))) {
		 dutyCodeSize="0";
	 }else{
		 dutyCodeSize=document.getElementById("dutyFactorSize").value;
	 }
	 var tempDuty = parseInt(dutyCodeSize);
	 var plan = "";
	 if(document.getElementById("planCode")!=null){
		 plan = document.getElementById("planCode").value;
	 }
	 tempDuty=tempDuty+1;
	 var dutyCode="";
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsonlastPrem = "";
	 var nvalue="nvalue";
	 
	 if(tempDuty>0){//有责任信息
		 for(var i=1;i<tempDuty;i++){
		 	dutyCode = "dutyCode"+i;
		 	var dutyCodeTemp=document.getElementById(dutyCode).value;//责任编码
		 	var premId = "prem" + "-" + plan + "-" +dutyCodeTemp;
		 	if(document.getElementById(premId)){
		 		var amntTemp=document.getElementById(premId).value;//责任编码
		 		if(i<tempDuty-1){
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+amntTemp+'","';
			 	}else{
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+amntTemp;
			 	}
		 	}else{
			 	if(i<tempDuty-1){
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+nvalue+'","';
			 	}else{
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+nvalue;
			 	}
		 	}
		 }
		 jsonlastPrem = jsonstar+jsonend;
	 }
	return jsonlastPrem;
}

/**
 * 封装被保人的生日，性别信息
 */
function recognizeeInfo(){
	
	var len = jQuery("select[id^='recognizeeRelation']").length;//
	var lent = jQuery("input[id^='recognizeeRelation']").length;//被保人关系只有本人
	var lenj = jQuery("select[id^='propertyToRecognizee']").length;//家财险（暂时只支持被保人关系只有本人）
	if(len>=1 || lent>=1 || lenj >=1){
		var jsonstar ='{"';
		 var jsoncen = '":"';
		 var jsonend = '"}';
		 var jsoninf = '';
		 var jsonlastPrem = "";
		 var i=1;    
		 if(lent>=1){
			 jQuery("input[id^='recognizeeRelation']").each(function(){
				    var relation_id = jQuery(this).attr("id"); 
					var _id ="";
					if(relation_id.indexOf("_")!=-1){
					    _id = "_"+relation_id.split("_")[1];
					} 
					//var sex = jQuery("#applicantSex").val();
					var sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
					var bir = jQuery("#applicantBirthday").val();
					var insuredCount = jQuery("#recognizeeMul"+_id).val();//被保人购买份数
					if(typeof(insuredCount)== "undefined" || insuredCount==null){
						 insuredCount="1";
					 }
					var IdentityType = jQuery("#applicantTypeId").val();
					var IdentityId = jQuery("#applicantId").val();
					var arr =new Array();
					arr[0] = relation_id;
					arr[1] = bir;
					arr[2] = sex;
					arr[3] = insuredCount;
					arr[4] = IdentityType;
					arr[5] = IdentityId;
					jsoninf = jsoninf+i+jsoncen+arr;
			 });
		 }else if(lenj >=1 ){
			 jQuery("select[id^='propertyToRecognizee']").each(function(){
				    var relation_id = jQuery(this).attr("id"); 
					var _id ="";
					if(relation_id.indexOf("_")!=-1){
					    _id = "_"+relation_id.split("_")[1];
					} 
					//var sex = jQuery("#applicantSex").val();
					var sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
					var bir = jQuery("#applicantBirthday").val();
					var insuredCount = jQuery("#recognizeeMul"+_id).val();//被保人购买份数
					if(typeof(insuredCount)== "undefined" || insuredCount==null){
						 insuredCount="1";
					 }
					var IdentityType = jQuery("#applicantTypeId").val();
					var IdentityId = jQuery("#applicantId").val();
					var arr =new Array();
					arr[0] = relation_id;
					arr[1] = bir;
					arr[2] = sex;
					arr[3] = insuredCount;
					arr[4] = IdentityType;
					arr[5] = IdentityId;
					jsoninf = jsoninf+i+jsoncen+arr;
			 });
		 }
		
		 else{
			 jQuery("select[id^='recognizeeRelation']").each(function(){
					var relation_id = jQuery(this).attr("id"); 
					var _id ="";
					var insuredindex ="";
					if(relation_id.indexOf("_")!=-1){
					    _id = "_"+relation_id.split("_")[1];
					    insuredindex = relation_id.split("_")[1];
					} 
					if(jQuery("input[name='myradio']:checked").attr("id")=="rid_me"){
						//var sex = jQuery("#applicantSex").val();
						var sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
						var bir = jQuery("#applicantBirthday").val();
						var IdentityType = jQuery("#applicantTypeId").val();
						var IdentityId = jQuery("#applicantId").val();
					}
					
					if("Y"==jQuery("#relationFlag"+_id).val()){
						//var sex = jQuery("#applicantSex").val();
						var sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
						var bir = jQuery("#applicantBirthday").val();
						var IdentityType = jQuery("#applicantTypeId").val();
						var IdentityId = jQuery("#applicantId").val();
					}else{
						//var sex = jQuery("#recognizeeSex"+_id+"").val();
						var sex = jQuery("input[name='sdinformationinsuredList["+insuredindex+"].recognizeeSex']:checked").val();
						var bir = jQuery("#recognizeeBirthday"+_id+"").val();
						var IdentityType = jQuery("#recognizeeTypeId"+_id+"").val();
						var IdentityId = jQuery("#recognizeeId"+_id+"").val();
					}
					var insuredCount = jQuery("#recognizeeMul"+_id).val();//被保人购买份数
					if(typeof(insuredCount)== "undefined" || insuredCount==null){
						 insuredCount="1";
					 }
					var arr =new Array();
					arr[0] = relation_id;
					arr[1] = bir;
					arr[2] = sex;
					arr[3] = insuredCount;
					arr[4] = IdentityType;
					arr[5] = IdentityId;
					if(i==len){
						jsoninf = jsoninf+i+jsoncen+arr;
					}else{
						jsoninf = jsoninf+i+jsoncen+arr+'","'; 
					}
					i=i+1;
				});
		 }
		
		var o=jsonstar+jsoninf+jsonend;
		var last=JSON.stringify(o);
		return o;
	}else{
		return "";
	}
	 
	
}

function recognizeeAppInfo(){
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsoninf = '';
	 var jsonlastPrem = "";
	 var i=1; 
	 var relation_id = "recognizeeRelation_0";
	 //var sex = jQuery("#applicantSex").val();
	 var sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
	 var bir = jQuery("#applicantBirthday").val();
	 var insuredCount = jQuery("#recognizeeMul").val();//被保人购买份数
	 if(typeof(insuredCount)== "undefined" || insuredCount==null){
		 insuredCount="1";
	 }
	 var IdentityType = jQuery("#applicantTypeId").val();
	 var IdentityId = jQuery("#applicantId").val();
	 var arr =new Array();
	 arr[0] = relation_id;
	 arr[1] = bir;
	 arr[2] = sex; 
	 arr[3] = insuredCount;
	 arr[4] = IdentityType;
	 arr[5] = IdentityId;
	 jsoninf = jsoninf+i+jsoncen+arr;
	  
	var o=jsonstar+jsoninf+jsonend;
	var last=JSON.stringify(o);
	return o;
	
}
/**
 * 与投保人关系修改事件
 */
function changeInformation(objid,aa){ 
	if(objid!=null && objid!="" && typeof(objid) != "undefined"){
	        var tValue="";
	        var _id="";  
	        var insuredindex="";
			var relation_id = objid;
			if(relation_id.indexOf("_")!=-1){
			    _id = "_"+relation_id.split("_")[1];
			    insuredindex = relation_id.split("_")[1];
			} 
			tValue=jQuery("#"+relation_id+"  option:selected").text(); 
			var effdate = jQuery("#effective").val();
			if(tValue.indexOf("本人")!=-1){  
				jQuery("#relationFlag"+_id).val("Y");
				var reId = jQuery("#relationFlag"+_id).attr("id");  
				jQuery("input[id^='relationFlag']").each(function(){
					if(jQuery(this).attr("id")!=reId&&jQuery(this).val().indexOf("Y")!=-1){ 
						jQuery("#"+relation_id).val("-1");
						jQuery("#"+relation_id).find("option[text^='请选择']").attr("selected",true);
						if(!jQuery("#"+relation_id+"").parent().children("label").is('i')){
							jQuery("#"+relation_id+"").parent().children("label").next("i").remove();
							jQuery("#"+relation_id+"").parent().children("label").after(jQuery("<i  style='color:red;font-size:12.5px;'>已添加本人为被保人，请勿重复添加</i>"));
							return false;
						} 
					}else{ 
						jQuery("#insHidden").hide();
						jQuery("#recognizeeName"+_id+"").val(jQuery("#applicantName").val());
						jQuery("#recognizeeTypeId"+_id).val(jQuery("#applicantTypeId option:selected").val());
						jQuery("#recognizeeTypeId"+_id).find("option[text^='"+jQuery("#applicantTypeId option:selected").text()+"']").attr("selected",true);
						jQuery("#recognizeeId"+_id+"").val(jQuery("#applicantId").val());
						var sexvalue = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
						jQuery("input[name=sdinformationinsuredList["+insuredindex+"].recognizeeSex][value="+sexvalue+"]").attr("checked",'checked');
						//jQuery("#recognizeeSex"+_id+"").val(jQuery("#applicantSex :checked").val());
						jQuery("#recognizeeBirthday"+_id+"").val(jQuery("#applicantBirthday").val());
						jQuery("#recognizeeMail"+_id+"").val(jQuery("#applicantMail").val());
						jQuery("#recognizeeMobile"+_id+"").val(jQuery("#applicantMobile").val());
						jQuery("#recognizeeZipCode"+_id+"").val(jQuery("#applicantZipCode").val());
						jQuery("#recognizeeEnName"+_id+"").val(jQuery("#applicantEnName").val());
						
						jQuery("#recognizeeEndID"+_id+"").attr("disabled","");
						jQuery("#Checkzjnum_long"+_id+"").attr("checked","");
						jQuery("#recognizeeStartID"+_id+"").val(jQuery("#applicantStartID").val());
						jQuery("#recognizeeEndID"+_id+"").val(jQuery("#applicantEndID").val());
						// 针对证件有效期勾选长期特殊处理
						if(jQuery("#applicantEndID").val()==""&&jQuery("#applicantEndID").attr("disabled")){
							jQuery("#recognizeeEndID"+_id+"").attr("disabled","disabled");
							jQuery("#recognizeeEndID"+_id+"").removeAttr("verify");
							jQuery("#Checkzjnum_long"+_id+"").attr("checked","checked");
						}
						
						if(jQuery("#applicantArea1 option:selected").val()!=null && jQuery("#applicantArea1 option:selected").val()!="" &&jQuery("#applicantArea1 option:selected").val()!="undefined"){
							jQuery("#recognizeeArea1"+_id).val(jQuery("#applicantArea1 option:selected").val());
							jQuery("#recognizeeArea1"+_id).find("option[text^='"+jQuery("#applicantArea1 option:selected").text()+"']").attr("selected",true);
						}
						getChildrenArea(jQuery("#recognizeeArea1"+_id).val(),"recognizeeArea2"+_id,jQuery("#recognizeeArea2"+_id).val(),"",_id);
				 		
						jQuery("#recognizeeAddress"+_id+"").val(jQuery("#applicantAddress").val());
						if(jQuery("#applicantOccupation1 option:selected").val()!=null && jQuery("#applicantOccupation1 option:selected").val()!="" &&jQuery("#applicantOccupation1 option:selected").val()!="undefined"){
							jQuery("#recognizeeOccupation1"+_id+" option:selected").val(jQuery("#applicantOccupation1 option:selected").val());
							jQuery("#recognizeeOccupation2"+_id+" option:selected").val(jQuery("#applicantOccupation2 option:selected").val());
							jQuery("#recognizeeOccupation3"+_id+" option:selected").val(jQuery("#applicantOccupation3 option:selected").val()); 
						}
						if(jQuery("#applicantOccupation1 option:selected").text()!=null && jQuery("#applicantOccupation1 option:selected").text()!="" &&jQuery("#applicantOccupation1 option:selected").text()!="undefined"){
							jQuery("#recognizeeOccupation1"+_id+" option:selected").text(jQuery("#applicantOccupation1 option:selected").text());
							jQuery("#recognizeeOccupation2"+_id+" option:selected").text(jQuery("#applicantOccupation2 option:selected").text());
							jQuery("#recognizeeOccupation3"+_id+" option:selected").text(jQuery("#applicantOccupation3 option:selected").text());
						}
						 if(jQuery.trim(effdate)=="" || effdate==null || effdate=="null"){
							jQuery("#recognizeeBirthday"+_id).val("");
							jQuery("#recognizeeId"+_id).val("");
							jQuery("#gotop").click();
							jQuery("#effective").focus();
							art.dialog.alert("请输入起保日期");
							return false;
						 }
						if(jQuery("#recognizeeBirthday"+_id).val()!=null&&jQuery("#recognizeeBirthday"+_id).val()!=""){
							if(!validateBirthday_1("recognizeeBirthday"+_id)){art.dialog.alert("您好，您输入的年龄不能购买本产品，请重新输入");return false;};
							dateTrigger("#recognizeeBirthday"+_id);
						} 
					}
				});  
				initBirthday(jQuery("#recognizeeTypeId"+_id).attr("id"),jQuery("#recognizeeId"+_id+"").attr("id"));	
			}else{
				jQuery("#insHidden").show();
				jQuery("#relationFlag"+_id).val("N");
				//jQuery("#recognizeeName"+_id+"").val("");
				//jQuery("#recognizeeId"+_id+"").val("");
				//jQuery("#recognizeeBirthday"+_id+"").val("");
				//jQuery("#recognizeeMail"+_id+"").val("");
				//jQuery("#recognizeeMobile"+_id+"").val("");
				//jQuery("#recognizeeZipCode"+_id+"").val("");
				//jQuery("#recognizeeAddress"+_id+"").val("");
				//jQuery("#recognizeeEnName"+_id+"").val("");
			}; 
	}
}

/**
 * 与投保人关系修改事件(只有本人)
 */
function changeInformationSelf(objid){ 
	if(objid!=null && objid!="" && typeof(objid) != "undefined"){
	        var tValue="";
	        var _id="";  
			var relation_id = objid;
			if(relation_id.indexOf("_")!=-1){
			    _id = "_"+relation_id.split("_")[1];
			} 
			tValue=jQuery("#"+relation_id);
			if(tValue.indexOf("本人")!=-1){
				jQuery("#relationFlag"+_id).val("Y");
			}
	}
}

/**
 * 核保失敗囘調函數
 */

function pay_error(orderSn,productId,tMessage,tKID){
	
    var mes = "很抱歉，您的核保因以下原因未通过，请返回修改，谢谢</br>" 
    		+ tMessage;
    jQuery("#qrgm_pay").hide();
		var dialog = art.dialog({
		width:350,
		title: '核保未通过',
		content:mes,
		icon: 'face-sad',
		button: [
		{
			name: '返回修改',
			callback: function () {
				window.location.href=sinosoft.base+'/shop/order_config_new!buyNowUpdate.action?orderSn='+orderSn+'&productId='+productId+"&KID="+tKID;      
				hideUI();
				return false;
			},
			focus: true
		}],
	close: function () {
		  hideUI();
	  }
	});
}
/**
 * 初始化保费试算
 */
function initPrem(){
	//判断是否存在cps用户cookie，如果存在则渠道编码为cps
	var cpsUserId = jQuery.cookie('cpsUserId');
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		//jQuery("#channelsn").val("cps");
	}
	var channelsn = jQuery("#channelsn").val();
	if (channelsn == '' || channelsn == null) {
		//jQuery("#channelsn").val("wj");
		//channelsn = "wj";
	}
	//被保人快速录入隐藏
	if(jQuery("#mulInsuredFlag").val()=="rid_orther"){
		jQuery("#insuredInfoDIV").show();
	}else{
		//但被保人需要显示
		if(jQuery("#mulInsuredFlag").val()=="rid_td"){ 
			dealrecognizeeBir();
		};
		if(typeof(jQuery("#mulInsuredFlag").val())=="undefined" && typeof(jQuery("#relationIsSelf").val())=="undefined"){
			jQuery("#insuredInfoDIV").show();
		}else{
			jQuery("#insuredInfoDIV").hide();
		}
	}
	//初始化投、被保人出生日期
	initBirthday("applicantTypeId","applicantId");
	jQuery("select[id^='recognizeeTypeId']").each(function(){ 
		var tId = jQuery(this).attr("id"); 
		var _id = "_"+tId.split("_")[1];
		initBirthday(jQuery(this).attr("id"),"recognizeeId"+_id);
	});
	
	//初始化直接进行保费试算的产品集合
	if((orderSn==null || orderSn=="" || typeof(orderSn)=="undefined") && risktype!='11' && "undefined" != typeof productId && premProducts.indexOf(productId)==-1){
		 jQuery("#payPrice").val("0");
		 jQuery("#productTotalPrice").attr("value","0");
		 document.getElementById("priceTotle_1").innerHTML="0";
		 document.getElementById("priceTotle").innerHTML="0"; 

		 jQuery("#insNum").html("0");
		 jQuery("#insMult").html("0");
		 jQuery("#insMult_1").html("0");
		
	}else{

		var insureJson = encodeURIComponent(encodeURIComponent(insuredElements()));
		var dutyJson = encodeURIComponent(encodeURIComponent(dutyElements()));
		if(jQuery("#mulInsuredFlag").val()=="rid_me"||jQuery("#relationIsSelf").val()=="Y"){
			recoJson = recognizeeAppInfo(); 
		}else{
			recoJson = recognizeeInfo(); 
		}
		if(jQuery("#recognizeeMul").val()!=null && jQuery("#recognizeeMul").val()!=""){
			
			jQuery("#insMult").val(jQuery("#recognizeeMul").val());//被保人购买份数
			jQuery("#insMult_1").val(jQuery("#recognizeeMul").val());//被保人购买份数
		}
		var effective = "";
		if(document.getElementById("effective")){
			effective = document.getElementById("effective").value;
		}
		jQuery.ajax({
			type:'get',
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson
			+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val()
			+"&complicatedFlag="+jQuery("#complicatedFlag").val()+"&dutyTempSerials="+jQuery("#dutyTempSerials").val(),
			data:{orderSn:orderSn},
			dataType: "json",
			async: false,
			success: function(item) {
				var obj = eval(item);
				fillin(obj,"1", memberid);
			}
		});  
		rel();
	}
}
function pubGetChildrenArea(Area1,Area2,Area3,Flag,Index)
{
	if(Flag=="App"){
		if ((typeof(Area1) != "undefined") && (Area1 !="") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenArea(Area1,'applicantArea2',Area2);
				getChildrenArea(Area2,'applicantArea3',Area3);
			}else{
				getChildrenArea(Area1,'applicantArea2',Area2);
			}
		}
	}else if(Flag=="Property"){
		var tIndex = "";
		if(Index!="" && Index!="undefined" && Index!=null){
			tIndex = "_"+(Index-1);
		}
		if ((typeof(Area1) != "undefined") && (Area1 != "") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenArea(Area1,'propertyArea2'+tIndex,Area2);
				getChildrenArea(Area2,'propertyArea3'+tIndex,Area3);
			}else{
				getChildrenArea(Area1,'propertyArea2'+tIndex,Area2);
			}
		}
	}else if(Flag=="Bank"){
		if ((typeof(Area1) != "undefined") && (Area1 !="") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenArea(Area1,'bankCity',Area2);
				//getChildrenArea(Area2,'applicantArea3',Area3);
			}else{
				getChildrenArea(Area1,'bankCity',Area2);
			}
		}
	}else if(Flag=="Bnf"){
		var tIndex = "";
		if(Index!="" && Index!="undefined" && Index!=null){
			tIndex = "_"+(Index-1);
		}
		if ((typeof(Area1) != "undefined") && (Area1 != "") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenArea(Area1,'favoreeBnfArea2'+tIndex,Area2);
				//getChildrenArea(Area2,'recognizeeArea3'+tIndex,Area3);
			}else{
				getChildrenArea(Area1,'favoreeBnfArea2'+tIndex,Area2);
			}
		}
	}else if(Flag=="Origin"){
		var tIndex = "";
		if(Index!="" && Index!="undefined" && Index!=null){
			tIndex = "_"+(Index-1);
		}
		if ((typeof(Area1) != "undefined") && (Area1 != "") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenArea(Area1,'recognizeeOrigin2'+tIndex,Area2);
				getChildrenArea(Area2,'recognizeeOrigin3'+tIndex,Area3);
			}else{
				getChildrenArea(Area1,'recognizeeOrigin2'+tIndex,Area2);
			}
		}
	}else if(Flag=="Destination"){
		var tIndex = "";
		if(Index!="" && Index!="undefined" && Index!=null){
			tIndex = "_"+(Index-1);
		}
		if ((typeof(Area1) != "undefined") && (Area1 != "") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenArea(Area1,'recognizeeDestination2'+tIndex,Area2);
				getChildrenArea(Area2,'recognizeeDestination3'+tIndex,Area3);
			}else{
				getChildrenArea(Area1,'recognizeeDestination2'+tIndex,Area2);
			}
		}
	}
	else{
		var tIndex = "";
		if(Index!="" && Index!="undefined" && Index!=null){
			tIndex = "_"+(Index-1);
		}
		if ((typeof(Area1) != "undefined") && (Area1 != "") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenArea(Area1,'recognizeeArea2'+tIndex,Area2);
				getChildrenArea(Area2,'recognizeeArea3'+tIndex,Area3);
			}else{
				getChildrenArea(Area1,'recognizeeArea2'+tIndex,Area2);
			}
		}
	}


} 
//目的地级联查询（非旅游目的地）
function pubGetChildrenDestination(Area1,Area2,Area3,Flag,Index)
{
	if(Flag=="App"){
		if ((typeof(Area1) != "undefined") && (Area1 !="") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenCountry(Area1,'recognizeeDestinationCountry',Area2);
				getChildrenCountry(Area2,'recognizeeDestinationCountryText',Area3);
			}else{
				getChildrenCountry(Area1,'recognizeeDestinationCountry',Area2);
			}
		}
	}else if(Flag=="Property"){
		var tIndex = "";
		if(Index!="" && Index!="undefined" && Index!=null){
			tIndex = "_"+(Index-1);
		}
		if ((typeof(Area1) != "undefined") && (Area1 != "") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenCountry(Area1,'recognizeeDestinationCountry'+tIndex,Area2);
				getChildrenCountry(Area2,'recognizeeDestinationCountryText'+tIndex,Area3);
			}else{
				getChildrenCountry(Area1,'recognizeeDestinationCountry'+tIndex,Area2);
			}
		}
	}else{
		var tIndex = "";
		if(Index!="" && Index!="undefined" && Index!=null){
			tIndex = "_"+(Index-1);
		}
		if ((typeof(Area1) != "undefined") && (Area1 != "") && (Area1 != "-1"))
		{
			if((typeof(Area2) != "undefined") && (Area2 != "") && (Area2 != "-1")&&(typeof(Area3) != "undefined") && (Area3 != "") && (Area3 != "-1")){
				getChildrenCountry(Area1,'recognizeeDestinationCountry'+tIndex,Area2);
				getChildrenCountry(Area2,'recognizeeDestinationCountryText'+tIndex,Area3);
			}else{
				getChildrenCountry(Area1,'recognizeeDestinationCountry'+tIndex,Area2);
			}
		}
	}


} 
function pubGetChildrenOccupation(Occupation1,Occupation2,Occupation3,Flag,index)
{

	if(Flag=="App"){
		if ((typeof(Occupation1) != "undefined") && (Occupation1 !="") && (Occupation1 != "-1"))
		{
			if((typeof(Occupation2) != "undefined") && (Occupation2 != "") && (Occupation2 != "-1")){
				getChildrenOPT(Occupation1,'applicantOccupation2',Occupation2,null,'2');
				getChildrenOPT(Occupation2,'applicantOccupation3',Occupation3,null,'3');
			}else{
				getChildrenOPT(Occupation1,'applicantOccupation3',Occupation3,null,'3');
			}
			
		}
	}else if(Flag=="Bnf"){
		var tIndex = "";
		if(index!="" && index!="undefined" && index!=null){
			tIndex = "_"+(index-1);
		}  
		if ((typeof(Occupation1) != "undefined") && (Occupation1 != "") && (Occupation1 != "-1"))
		{   
			 
			if((typeof(Occupation2) != "undefined") && (Occupation2 != "") && (Occupation2 != "-1")){
		        
			   getChildrenOPT(Occupation1,'favoreeBnfOccupation2'+tIndex,Occupation2,null,'2');
			   getChildrenOPT(Occupation2,'favoreeBnfOccupation3'+tIndex,Occupation3,null,'3');
		    }else{
		    	getChildrenOPT(Occupation1,'favoreeBnfOccupation3'+tIndex,Occupation3,null,'3');
		    }
			
		}
		
	}else {
		  
		var tIndex = "";
		if(index!="" && index!="undefined" && index!=null){
			tIndex = "_"+(index-1);
		}  
		if ((typeof(Occupation1) != "undefined") && (Occupation1 != "") && (Occupation1 != "-1"))
		{   
			 
			if((typeof(Occupation2) != "undefined") && (Occupation2 != "") && (Occupation2 != "-1")){
		        
			   getChildrenOPT(Occupation1,'recognizeeOccupation2'+tIndex,Occupation2,null,'2');
			   getChildrenOPT(Occupation2,'recognizeeOccupation3'+tIndex,Occupation3,null,'3');
		    }else{
		    	getChildrenOPT(Occupation1,'recognizeeOccupation3'+tIndex,Occupation3,null,'3');
		    }
			
		}
	}
}
function getChildrenArea(value,flag,flagvalue,src,_id){

	   var child = document.getElementById(flag);
	   if ((child==null) || (typeof(child)=="undefined")){
		   return;
	   }
	   child.options.length=1;
	   
	   if(flag.indexOf("Area2")!=-1){
		   var flag3=flag.replace("Area2","Area3");
		   var child3 = document.getElementById(flag3);
		   if ((typeof(child3)!="undefined") && (child3!=null)  ){
			   child3.options.length=1;
		   }
	   }
	   
	   if(value!="-1"){
		   jQuery.ajax({
			   type:'get',
			   url: sinosoft.base+"/shop/order_config_new!getChildernArea.action?id="+value,
			   dataType: "json",
			   success: function(item) {
				   if ((src!="")&&(src!=null) && (typeof(src)!="undefined")){
					   var nowValue = document.getElementById(src.id).value;
			       	   if (nowValue == value){
			       		   fillArea(item,flag,flagvalue);
			       	   }
			   	   }
			   	   else{
			   		   fillArea(item,flag,flagvalue);
			   	   }
			   	   if(_id!="" && _id!=null && (typeof(src)!="undefined")){
			   		   var k;
			   		   if(flag.indexOf("recognizeeArea")!=-1){
			   			   k=flag.substring(14,15);
			   		   }
			   		  
			   		   if(k==2){
			   			   jQuery("#recognizeeArea2"+_id).val(jQuery("#applicantArea2 option:selected").val());
			   			   jQuery("#recognizeeArea2"+_id).find("option[text^='"+jQuery("#applicantArea2 option:selected").text()+"']").attr("selected",true);
			   			   getChildrenArea(jQuery("#recognizeeArea2"+_id).val(),"recognizeeArea3"+_id,jQuery("#recognizeeArea3"+_id).val(),"",_id);
			   		   }else if(k==3){
			   			   jQuery("#recognizeeArea3"+_id).val(jQuery("#applicantArea3 option:selected").val());
			   			   jQuery("#recognizeeArea3"+_id).find("option[text^='"+jQuery("#applicantArea3 option:selected").text()+"']").attr("selected",true);
			   		   }else{
			   			   jQuery("#recognizeeArea2"+_id).val(jQuery("#applicantArea2 option:selected").val());
			   			   jQuery("#recognizeeArea2"+_id).find("option[text^='"+jQuery("#applicantArea2 option:selected").text()+"']").attr("selected",true);
			   		   }
			   	   }
			    }
		    });
	   }
	}
//目的地查询子栏目（非旅游目的地）
function getChildrenCountry(value,flag,flagvalue,src,_id){

	   var child = document.getElementById(flag);
	   if ((child==null) || (typeof(child)=="undefined")){
		   return;
	   }
	   child.options.length=1;
	   
	   if(flag.indexOf("recognizeeDestinationCountry")!=-1){
		   var flag3=flag.replace("recognizeeDestinationCountry","recognizeeDestinationCountryText");
		   var child3 = document.getElementById(flag3);
		   if ((typeof(child3)!="undefined") && (child3!=null)  ){
			   child3.options.length=1;
		   }
	   }
	   
	   if(value!="-1"){
		   jQuery.ajax({
			   type:'get',
			   url: sinosoft.base+"/shop/order_config_new!getChildernArea.action?id="+value,
			   dataType: "json",
			   success: function(item) {
				   if ((src!="")&&(src!=null) && (typeof(src)!="undefined")){
					   var nowValue = document.getElementById(src.id).value;
			       	   if (nowValue == value){
			       		   fillArea(item,flag,flagvalue);
			       	   }
			   	   }
			   	   else{
			   		   fillArea(item,flag,flagvalue);
			   	   }
			   	   if(_id!="" && _id!=null && (typeof(src)!="undefined")){
			   		  
			   		   if(flag=="recognizeeDestinationCountry"){
			   			   jQuery("#recognizeeDestinationCountry"+_id).val(jQuery("#recognizeeDestinationCountry option:selected").val());
			   			   jQuery("#recognizeeDestinationCountry"+_id).find("option[text^='"+jQuery("#recognizeeDestinationCountry option:selected").text()+"']").attr("selected",true);
			   			   getChildrenArea(jQuery("#recognizeeDestinationCountry"+_id).val(),"recognizeeDestinationCountryText"+_id,jQuery("#recognizeeDestinationCountryText"+_id).val(),"",_id);
			   		   }else if(flag=="recognizeeDestinationCountryText"){
			   			   jQuery("#recognizeeDestinationCountryText"+_id).val(jQuery("#recognizeeDestinationCountryText option:selected").val());
			   			   jQuery("#recognizeeDestinationCountryText"+_id).find("option[text^='"+jQuery("#recognizeeDestinationCountryText option:selected").text()+"']").attr("selected",true);
			   		   }else{
			   			   jQuery("#recognizeeDestinationCountry"+_id).val(jQuery("#recognizeeDestinationCountry option:selected").val());
			   			   jQuery("#recognizeeDestinationCountry"+_id).find("option[text^='"+jQuery("#recognizeeDestinationCountry option:selected").text()+"']").attr("selected",true);
			   		   }
			   	   }
			    }
		    });
	   }
	}


//function getChildrenArea(value,flag,flagvalue,src,_id){
//	alert("2"+flag);
//	   var child = document.getElementById(flag);
//	   if ((child==null) || (typeof(child)=="undefined")){
//		   return;
//	   }
//	   child.options.length=1;
//	   
//	   if(flag.indexOf("Area2")!=-1){
//		   var flag3=flag.replace("Area2","Area3");
//		   var child3 = document.getElementById(flag3);
//		   if ((typeof(child3)!="undefined") && (child3!=null)  ){
//			   child3.options.length=1;
//		   }
//	   }
//	   
//	   if(value!="-1"){
//		   jQuery.ajax({
//			   type:'get',
//			   url: sinosoft.base+"/shop/order_config_new!getChildernArea.action?id="+value,
//			   dataType: "json",
//			   success: function(item) {
//				   if ((src!="")&&(src!=null) && (typeof(src)!="undefined")){
//					   var nowValue = document.getElementById(src.id).value;
//			       	   if (nowValue == value){
//			       		   fillArea(item,flag,flagvalue);
//			       	   }
//			   	   }
//			   	   else{
//			   		   fillArea(item,flag,flagvalue);
//			   	   }
//			   	   if(_id!="" && _id!=null && (typeof(src)!="undefined")){
//			   		   var k;
//			   		   if(flag.indexOf("recognizeeArea")!=-1){
//			   			   k=flag.substring(14,15);
//			   		   }
//			   		   if(k==2){
//			   			   jQuery("#destinationCountry"+_id).val(jQuery("#destinationCountry option:selected").val());
//			   			   jQuery("#destinationCountry"+_id).find("option[text^='"+jQuery("#destinationCountry option:selected").text()+"']").attr("selected",true);
//			   			   getChildrenArea(jQuery("#recognizeeArea2"+_id).val(),"recognizeeArea3"+_id,jQuery("#destinationCountryText"+_id).val(),"",_id);
//			   		   }else if(k==3){
//			   			   jQuery("#destinationCountryText"+_id).val(jQuery("#applicantArea3 option:selected").val());
//			   			   jQuery("#destinationCountryText"+_id).find("option[text^='"+jQuery("#applicantArea3 option:selected").text()+"']").attr("selected",true);
//			   		   }else{
//			   			   jQuery("#destinationCountry"+_id).val(jQuery("#applicantArea2 option:selected").val());
//			   			   jQuery("#destinationCountry"+_id).find("option[text^='"+jQuery("#applicantArea2 option:selected").text()+"']").attr("selected",true);
//			   		   }
//			   	   }
//			    }
//		    });
//	   }
//	}



function fillArea(item,flag,flagvalue){
    var childrenArea = document.getElementById(flag);
    childrenArea.options.length=1;
    for(var i=0;i<item.length;i++){
     	var oo=new Option(item[i].name,item[i].id);
     	childrenArea.options[childrenArea.options.length]=oo;
     	if (typeof(flagvalue)!="undefined" && oo.value == flagvalue)
     	{
     		oo.selected = "selected";
     	}
    }
}
function getChildrenOPT(value,flag,flagvalue, src,level){
	
	   var child = document.getElementById(flag); 
	   if ((child==null) || (typeof(child)=="undefined"))
	   {
		   return;
	   }
	   child.options.length=1;
	   
	   if(flag.indexOf("Occupation2")!=-1){
		   var flag3=flag.replace("Occupation2","Occupation3");
		   var child3 = document.getElementById(flag3);
		   if ((typeof(child3)!="undefined") && (child3!=null)  ){
			   child3.options.length=1;
		   }
	   }
	   
	   if(value!="-1"){
	        jQuery.ajax({
	            type:'get',
			    url: sinosoft.base+"/shop/order_config_new!getChildernOPT.action?id="+value+"&level="+level+"&productId="+productId,
			    dataType: "json",
			    success: function(item) {
	        	   if ((src!=null) && (typeof(src)!="undefined"))
	        	   {
	            	   var nowValue = document.getElementById(src.id).value;
	            	   if (nowValue == value)
	            	   {
	        			   fillOPT(item,flag,flagvalue);
	            	   }
	        	   }
	        	   else
	        	   {
	        		   fillOPT(item,flag,flagvalue);
	        	   }
			    }
		    });
	   }
	}
	 

function fillOPT(item,flag,flagvalue){
    var childrenOPT = document.getElementById(flag);
    childrenOPT.options.length=1;
    for(var i=0;i<item.length;i++){
     	var oo=new Option(item[i].name,item[i].id);
     	childrenOPT.options[childrenOPT.options.length]=oo;
     	if (typeof(flagvalue)!="undefined" && oo.value == flagvalue)
     	{
     		oo.selected = "selected";
     	}
    }
}
//校验生日有效期
function validateBirthday(bId){   
	  
	   var relation_id = bId;  
	   var _id ="_0"; 
	   var birthday = ""; 
	   var effdate = jQuery("#effective").val(); 
	   var tFlag = true;
	   if(relation_id.indexOf("_")!=-1){
	      _id = "_"+relation_id.split("_")[1];
	      		if(jQuery.trim(effdate)=="" || effdate==null || effdate=="null"){
	      			jQuery("#recognizeeBirthday"+_id).val("");
	      			jQuery("#recognizeeId"+_id).val("");
	      			art.dialog.alert("请输入起保日期");
	      			jQuery("#gotop").click();
	      			jQuery("#effective").focus();
	      			return false;
	      		}
			 	 if(document.getElementById('recognizeeRelation'+_id)){
			 		  
					   
					 var relation1 = document.getElementById('recognizeeRelation'+_id);
					 var relation1_index = relation1.selectedIndex;          
					 var relation1_value = relation1.options[relation1_index].text; 
					 birthday = jQuery('#recognizeeBirthday'+_id).val();  
					 if(relation1_value.indexOf('本人') != -1){  
						 jQuery('#applicantBirthday').val(jQuery('#recognizeeBirthday'+_id).val());
					 }   
					birthday = jQuery('#recognizeeBirthday'+_id).val();
					if(birthday!=null && birthday!=""){
						jQuery.ajax({
							url: sinosoft.base+"/shop/order_config_new!ajaxAgeAndCheckTomorrowBirthday.action?applicantBirthday="+birthday+"&productId="+productId+"&effdate="+effdate,
							dataType: "json",
							type:"GET",
							async: false,
							success: function(data) {	
								if(data.ageFlag=="false"){
									art.dialog.alert("您好，您输入的年龄不在本产品承保范围内"); 
									jQuery("#"+bId).val("");
									tFlag = false;
									return false;
								}else{	
									whenTomorrowBirthday("2",_id,data.tomorrowIsBirthday);
									tFlag = true;
									calPremFlag=true;
									return true;
								}  
							}
						});
					}
			   }
	   }else{
		   birthday = jQuery('#applicantBirthday').val(); 
		   if (!CheckAppAge(birthday))
			{ 
				jQuery("#"+bId).parent().children("i").remove();
				jQuery("#"+bId).parent().children("label").after(jQuery("<i style='color:red;font-size:12.5px;'>投保人年龄应大于等于18岁</i>"));
				jQuery("#"+bId).focus();
				tFlag = false;
				return false;
			}else{
				jQuery("#"+bId).parent().children("i").remove();
				tFlag = true;
				return true;
			}
	   } 
	   return tFlag;
}
function validateBirthday_1(obj){
	var tFlag = true;
    var effdate = jQuery("#effective").val(); 
	if(obj!=null && obj!=""){
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!ajaxAgeAndCheckTomorrowBirthday.action?applicantBirthday="+jQuery("#"+obj).val()+"&productId="+productId+"&effdate="+effdate,
		dataType: "json",
		type:"GET",
		async: false,
		success: function(data) {
			whenTomorrowBirthday("2","_"+obj.split("_")[1],data.tomorrowIsBirthday);	
			if(data.ageFlag=="false"){ 
				jQuery("#"+obj).val("");
							tFlag = false;
						}  
					}
				});
			}
return tFlag;
}
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
		}else{
			jQuery("#"+birthdayId).click(function(){
				if(birthdayId=="applicantBirthday"){
					WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+appEndDate+'}',maxDate:'%y-%M-{%d-'+appStartDate+'}'});
				}else{
					WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+insEndDate+'}',maxDate:'%y-%M-{%d-'+insStartDate+'}'});
				}
			});
		}
		if(str.length==18){
			  
			if(!checkId(str,"")){  
				if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">一定要填写18位有效身份证号码哟，请仔细检查一下</i>"));
					  deltimeoutIDError(idtype);
		 		}
				elementFocus(idCode);
				return false ;
			}else{
				if(StrLast=='x'){
					if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
						  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">身份证有误,身份证号码最后一位请输入大写:X</i>"));
			 		}
					elementFocus(idCode);
					return false ;
				}else{
					if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
					}
				}
			}
			var sex=''; 
			var sexName = jQuery("#"+sexId).attr("name");
			// 身份证对应性别
			var strSex = "";
			if(str.substring(16,17)%2==0||str.substring(16,17).toLowerCase()=='x'){  
				strSex = "女";
				jQuery("input[name="+sexName+"]").each(function(){
					//性别处理，根据汉字匹配证件类型编码appDictionaryCode
					  jQuery.ajax({
						  url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=Sex&codeName="+encodeURIComponent(encodeURIComponent("女")),
							dataType: "json",
							type:"GET", 
							async: false,
							success: function(data) {
								var appCode=data.appDictionaryCode;
								jQuery("input[name="+sexName+"][value="+appCode+"]").attr("checked",'checked'); 
							}
						});
		        });
			}else{   
				strSex = "男";
				jQuery("input[name="+sexName+"]").each(function(){
					//性别处理，根据汉字匹配证件类型编码appDictionaryCode
					  jQuery.ajax({
						  url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=Sex&codeName="+encodeURIComponent(encodeURIComponent("男")),
							dataType: "json",
							type:"GET", 
							async: false,
							success: function(data) {
								var appCode=data.appDictionaryCode;
								jQuery("input[name="+sexName+"][value="+appCode+"]").attr("checked",'checked'); 
							}
						});
		        });  
			}   
			var year = str.substring(6, 10);      
			var month = str.substring(10, 12);      
			var day = str.substring(12, 14);
			var checkSex = jQuery("input[name="+sexName+"] :checked");
			sex = checkSex.val();

			if(birthdayId.indexOf("favoreeBirthday")==-1){
				// 初始化直接进行保费试算的产品,判断身份证年龄与初始年龄是否一致
				if ("undefined" != typeof productId && premProducts.indexOf(productId) > -1) {
					if(document.getElementById(birthdayId).value!=null && document.getElementById(birthdayId).value!=year+"-"+month+"-"+day){
						if (jQuery("#" + idCode).parent().children("i")) {
							jQuery("#"+idCode).parent().children("i").remove();
						}
						jQuery("#"+idCode).parent().children("label").after(jQuery("<i  class=\"yz_mes_error\" >身份证与出生日期不符，请确认</i>"));
						return false;
					}
					if ("applicantTypeId"==idtype && jQuery("#applicantSexName") != null && "undefined" != typeof(jQuery("#applicantSexName")) && "undefined" != typeof(jQuery("#applicantSexName").val())) {
						if (strSex != jQuery("#applicantSexName").val()) {
							if (jQuery("#" + idCode).parent().children("i")) {
								jQuery("#"+idCode).parent().children("i").remove();
							}
							jQuery("#"+idCode).parent().children("label").after(jQuery("<i  class=\"yz_mes_error\" >身份证与性别不符，请确认</i>"));
							return false;
						}
					}
				}	
			}
				
			if(cFlag!="1"){
				document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
				//jQuery(".idControl").find("#"+birthdayId).val( year+"-"+month+"-"+day);
				//document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
				//document.getElementById(sexId).value = sex;
				//jQuery("input[name="+sexName+"][value="+sex+"]").attr("checked",'checked'); 
			} else {
				if ("applicantTypeId"==idtype && jQuery("#applicantSexName") != null && "undefined" != typeof(jQuery("#applicantSexName")) && "undefined" != typeof(jQuery("#applicantSexName").val())) {
					if (strSex != jQuery("#applicantSexName").val()) {
						if (jQuery("#" + idCode).parent().children("i")) {
							jQuery("#"+idCode).parent().children("i").remove();
						}
						jQuery("#"+idCode).parent().children("label").after(jQuery("<i  class=\"yz_mes_error\" >身份证与性别不符，请确认</i>"));
						return false;
					}
				}
			}
			if("applicantTypeId"==idtype){
				var brithday = document.getElementById("applicantBirthday").value;
				var effective = "";
				if(document.getElementById("effective")){
					effective = document.getElementById("effective").value;
				}
				if(brithday!=null && brithday!=""){
					jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxAgeAndCheckTomorrowBirthday.action?applicantBirthday="+brithday+"&productId="+productId+"&effdate="+effective,
						dataType: "json",
						type:"GET",
						async: true,
						success: function(data) {
							whenTomorrowBirthday("1","",data.tomorrowIsBirthday);	
							if(jQuery("#mulInsuredFlag").val()=="rid_me" || jQuery("#relationIsSelf").val()=="Y"){
								if(data.ageFlag=="true"){
									changeEndDateForToAge(brithday, effective);
									calPrem();
									if(!checkIdtypeUse(flag,idCode,idtype)){
										return false;
									}else{
										return true;
									}
								}else{
									elementFocus(idCode);
									return false;
								} 
							}
						}
					});
				}
			}else if(idtype.indexOf("favoreeTypeId") == -1){
					var brithday = document.getElementById(birthdayId).value;
					var effective = "";
					if(document.getElementById("effective")){
						effective = document.getElementById("effective").value;
					}
					if(brithday!=null && brithday!=""){
					jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxAgeAndCheckTomorrowBirthday.action?applicantBirthday="+brithday+"&productId="+productId+"&effdate="+effective,
						dataType: "json",
						type:"GET",
						async: false,
						success: function(data) {
							whenTomorrowBirthday("1","",data.tomorrowIsBirthday);
							if(data.ageFlag=="true"){
								changeEndDateForToAge(brithday, effective);
								calPrem();
								if(!checkIdtypeUse(flag,idCode,idtype)){
									return false;
								}else{
									return true;
								}
							}else{
								elementFocus(idCode);
								return false;
							}
						}
					});
					}
				}
			
			
		}else{
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">一定要填写18位有效身份证号码哟，请仔细检查一下</i>"));
				  deltimeoutIDError(idtype);
				  if(!checkIdtypeUse(flag,idCode,idtype)){
					return false;
				  }else{
					return true;
				  }
		 	}
			elementFocus(idCode);
			return false;
		} 
		
		/*2014-03-05 modify by cuishigang 投保录入页面添加逻辑：生日校验通过后，才进行保费试算
		 * if(jQuery("#mulInsuredFlag").val()=="rid_me"){
			dateTrigger(birthdayId);
		}*/
	/*	if("applicantTypeId"==idtype){
			verifyElement('出生日期|NOTNULL&APPAGE',jQuery("#applicantBirthday").val(),"applicantBirthday");
		}else{
			var _id = "_"+idtype.split("_")[1];
			validateBirthday("recognizeeBirthday"+_id);
		}
		var tFlag = true;
		if(idCode.indexOf("recognizee")!=-1){
			jQuery("select[id^='recognizeeTypeId']").each(function(){ 
				var tId = jQuery(this).attr("id"); 
				var _id = "_"+tId.split("_")[1];
				var tValue = jQuery("#recognizeeId"+_id).val();  
				var a1 = jQuery("#"+idtype).val();
				var a2 = jQuery("#"+tId).val(); 
				if(idtype!=tId&&a1==a2&&tValue==str){
					jQuery("#"+idCode).parent().children("i").remove();
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">该证件号已购买此产品</i>"));
					jQuery("#"+idCode).focus();
					tFlag = false;
					flag = false;
					elementFocus(idCode);
					return false; 
				}
			});
			if(tFlag){
				if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
				}
			}
		}*/
		
	}else if(idType_id == 2){
		//军官证、士兵证
		if(str.length2() <= 20){
				if(!(str.indexOf("字第") != -1 && str.indexOf("字第") > 0)){
					//不符合字第
					if(isSpecialCharacters(str,"2") || str.length < 4){
						errorMessage(idCode,"证件号码有误,格式错误!");
						elementFocus(idCode);
						return false;
					}
				}else{
					var tempStr = str.substr(str.indexOf("字第")+2,str.length);
					if(isSpecialCharacters(tempStr,"2") || tempStr.length < 4){
						errorMessage(idCode,"证件号码有误,格式错误!");
						elementFocus(idCode);
						return false;
					}
				}
		}else{
			errorMessage(idCode,"证件号码有误!格式错误!");
			elementFocus(idCode);
			return false;
		}
		if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
			jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
		}
		
	}else if(idType_id == 3){
		//护照-外国护照
		if(str.length2() < 3){
			errorMessage(idCode,"证件号码有误,格式错误!");
			elementFocus(idCode);
			return false;
		}else{
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
			}
		}
	}else if(idType_id == 4){
		//台湾通行证
		if(!/[A-Z].*[0-9]|[0-9].*[A-Z]/.test(str)||str.length < 8){
			errorMessage(idCode,"证件号码有误!格式错误!");
			elementFocus(idCode);
			return false;
		}else if(isSpecialCharacters(str,"1")){
			elementFocus(idCode);
			errorMessage(idCode,"证件号码有误!格式错误!");
			return false;
		}else{
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
			}
		}
		
	}else{
		if(jQuery("#"+idCode).val()==""){
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">请输入证件号码</i>"));
				  //jQuery("#"+idCode).focus();
				  elementFocus(idCode);
				  return false;
			}
		}else{
			if(idType_value.indexOf("护照") != -1){//>=3个字符
				if(!/[0-9a-zA-Z]{3,}/.test(str) || eval("/["+ str.substring(str.length-1) +"]{"+ str.length +"}/").test(str)){
					errorMessage(idCode,"一定要填写3位以上有效护照号码呦,请仔细检查一下!");
					elementFocus(idCode);
					return false;
				}
			}else if(idType_value.indexOf("港澳") != -1 || idType_value.indexOf("港台") != -1 || idType_value.indexOf("台湾") != -1 
					|| idType_value.indexOf("台胞证") != -1 || idType_value.indexOf("回乡证") != -1 || idType_value.indexOf("返乡证") != -1){//>=8个字符
				if(!/[0-9a-zA-Z]{8,}/.test(str) || eval("/["+ str.substring(str.length-1) +"]{"+ str.length +"}/").test(str)){
					errorMessage(idCode,"一定要填写8位以上有效证件号码呦,请仔细检查一下!");
					elementFocus(idCode);
					return false;
				}
			}else if(idType_value.indexOf("出生证") != -1 || idType_value.indexOf("户口") != -1){//>=3个字符
				if(!/[0-9a-zA-Z]{3,}/.test(str) || eval("/["+ str.substring(str.length-1) +"]{"+ str.length +"}/").test(str)){
					errorMessage(idCode,"一定要填写3位以上有效证件号码呦,请仔细检查一下!");
					elementFocus(idCode);
					return false;
				}
			}else if(idType_value.indexOf("军官证") != -1 || idType_value.indexOf("士兵证") != -1 || idType_value.indexOf("军人证") != -1){//10<= xxx <=18
				if(!/[0-9a-zA-Z]{10,18}/.test(str) || eval("/["+ str.substring(str.length-1) +"]{"+ str.length +"}/").test(str)){
					errorMessage(idCode,"一定要填写10-18位有效证件号码呦,请仔细检查一下!");
					elementFocus(idCode);
					return false;
				}
			}
			
			var tFlag = true;
			if(idCode.indexOf("recognizee")!=-1){
				jQuery("select[id^='recognizeeTypeId']").each(function(){ 
					var tId = jQuery(this).attr("id"); 
					var _id = "_"+tId.split("_")[1];
					var tValue = jQuery("#recognizeeId"+_id).val();  
					var a1 = jQuery("#"+idtype).val();
					var a2 = jQuery("#"+tId).val();
					
					if(idtype!=tId&&a1==a2&&tValue==str){
						jQuery("#"+idCode).parent().children("i").remove();
						jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">该证件号已购买此产品</i>"));
						//jQuery("#"+idCode).focus();
						tFlag = false;
						flag = false;
						return false; 
					}
				});
			}
			if(tFlag){
				if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
				}
			}
		}
		changeEndDateForToAge(document.getElementById(birthdayId).value, document.getElementById("effective").value);
	}
	if(!flag){
		return false;
	}else{
		return true;
	}
	
}




function checkIdtypeUse(flag,idCode,idtype){
	if("applicantTypeId"==idtype){
		verifyElement('出生日期|NOTNULL&APPAGE',jQuery("#applicantBirthday").val(),"applicantBirthday");
	}else{
		var _id = "_"+idtype.split("_")[1];
		validateBirthday("recognizeeBirthday"+_id);
	}
	var tFlag = true;
	if(idCode.indexOf("recognizee")!=-1){
		jQuery("select[id^='recognizeeTypeId']").each(function(){ 
			var tId = jQuery(this).attr("id"); 
			var _id = "_"+tId.split("_")[1];
			var tValue = jQuery("#recognizeeId"+_id).val();  
			var a1 = jQuery("#"+idtype).val();
			var a2 = jQuery("#"+tId).val(); 
			if(idtype!=tId&&a1==a2&&tValue==str){
				jQuery("#"+idCode).parent().children("i").remove();
				jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">该证件号已购买此产品</i>"));
				jQuery("#"+idCode).focus();
				tFlag = false;
				flag = false;
				elementFocus(idCode);
				return false; 
			}
		});
		if(tFlag){
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

/**
 * 校验提示信息.
 * @param cId
 * @return
 */
function errorMessage(idCode,msg){
	jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">"+msg+"</i>"));
	return false;
}

/**
 * 清除错误信息.
 * @param idCode
 * @return
 */
function clearMessage(idCode){
	if(jQuery("#"+idCode).parent().children("i")){
		   jQuery("#"+idCode).parent().children("i").remove();
	}
}
/**
 * 判断字符.
 * type 1.特殊字符 2.数字
 * @param str
 * @param type
 * @return
 */
function isSpecialCharacters(str,type){
	var tempStr;
	var count = 0;
	for (var i = 0; i < str.length; i++) { 
		tempStr = str.substr(i, 1);
		var code = tempStr.charCodeAt();
		if(type == 1){
			if(!((code >= 65 && code <= 90) || (code >= 48 && code <= 57)))
				return true;
		}else if(type == 2){
			if(!(code >= 48 && code <= 57)){
				return true;
			}
		}
	}
	return false;
}


String.prototype.length2 = function() {
	return this.replace(/[^\x00-\xff]/g, "**").length;
}

function effchange() {
	var specialEffDateFlag=document.getElementById("specialEffDateFlag").value;
	if(specialEffDateFlag!="Y"){//太平E宝贝重疾保障计划 终保日期根据被保人生日改变
		var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
		if ("true" == protectionPeriodFlag) {
			var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;// 保障期限后段
			var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;// 保障期限类型Y,M,D,A
			var effective = document.getElementById("effective").value;
			var relationFlag = "Y";
			if (document.getElementById("relationFlag_0")) {
				relationFlag = document.getElementById("relationFlag_0").value;
			}
			var brithday = "";  // 获取被保人生日，用户保障期限为至XX岁时使用
			if (relationFlag == "Y") {
				brithday = document.getElementById("applicantBirthday").value;
			} else {
				brithday = document.getElementById("recognizeeBirthday_0").value;
			} 
			var temp = addDate(protectionPeriodTy, protectionPeriodLast, effective, brithday);
			document.getElementById("fail").value = "";
			document.getElementById("fail").value = temp;
			document.getElementById("fail_").value = "";
			document.getElementById("fail_").value = temp;
		}
		var start = jQuery('#effective').val();
		var end = jQuery('#fail').val();
		
		 
		if(end=="9999-12-31" || end=="终身"){
			jQuery('#ensureDate').html("从&nbsp;&nbsp;&nbsp;"+start+"&nbsp;&nbsp;&nbsp;00时起到&nbsp;&nbsp;&nbsp;终身");
			jQuery('#ensureDate_em').html("( 从"+start+" 00时起到&nbsp;终身 )");
			
		}else{
			jQuery('#ensureDate').html("从&nbsp;&nbsp;&nbsp;"+start+"&nbsp;&nbsp;&nbsp;00时起到&nbsp;&nbsp;&nbsp;"+end+"&nbsp;&nbsp;&nbsp;24时为止");
			jQuery('#ensureDate_em').html("( 从"+start+" 00时起到"+end+" 24时为止 )");
		}
	}
}

function addDate(type, NumDay, dtDate, brithday) { 
	
	var reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
	var date_ = new Date();
	if (arr = dtDate.match(reg)) {
		var yy = Number(arr[1]);
		var mm = Number(arr[2]);
		var dd = Number(arr[3]);
		var all_ = mm + '/' + dd + '/' + yy;
		date_ = new Date(all_);
	} else {
		var myDate = new Date();
	}
	lIntval = parseInt(NumDay);
	var mmlast = "";
	switch (type) { 
		case 'Y': 
			date_.setYear(date_.getFullYear() + lIntval);
			date_.setDate(date_.getDate() - 1);
			break;
			
		case 'M': 
			date_.setMonth(date_.getMonth() + lIntval);
			date_.setDate(date_.getDate() - 1);
			break; 
			
		case 'D': 
			date_.setDate(date_.getDate() + lIntval);
			date_.setDate(date_.getDate() - 1) ;
			break;
		
		case 'A':
			if (brithday != null && brithday.match(reg)) {
				var toAge = document.getElementById("Ensure").value;
				toAge = parseInt(toAge.substring(0, toAge.indexOf("A")));
				
				var brithDate = getDate(brithday);
				if (brithDate.getMonth() < date_.getMonth()) {
					date_.setFullYear(brithDate.getFullYear() + toAge);
				} else if (brithDate.getMonth() > date_.getMonth()) {
					date_.setFullYear(brithDate.getFullYear() + toAge + 1);
				} else {
					if (brithDate.getDate() <= date_.getDate()) {
						date_.setFullYear(brithDate.getFullYear() + toAge);
					} else {
						date_.setFullYear(brithDate.getFullYear() + toAge + 1);
					}
				}
				date_.setDate(date_.getDate() - 1);
			}
			break;
		
		case 'L': 
			return '终身';
			 
	} 
	var mm = date_.getMonth() + 1;
	var dd = date_.getDate();
	
	if (mm < 10) {
		mm = '0' + mm;
	}
    if (dd < 10) {
		dd = '0' + dd;
	}
    
	return date_.getFullYear() + '-' + mm + '-' + dd;
}   
jQuery("#light").jqm({
	modal: true,// 是否开启模态窗口
	overlay: 100,// 屏蔽层透明度
	trigger: ".select_mmd_btn",// 激活元素
	closeClass: "loginWindowClose_",// 关闭按钮
	onHide: function(hash) {
		hash.o.remove();
		hash.w.fadeOut();
	},
	onShow: function(hash){
		jQuery(".wrap_gj_sx0").find("a.selected").click();
		hash.w.fadeIn();
 }
}).jqDrag(".windowTop");
function setOutGoingParpose(){
		var chks=new Array();
	    var lbls=new Array();
	    var val='';
	    chks=document.getElementsByTagName('input');
	    lbls=document.getElementsByTagName('label');
	
	     
	    for(var i=0;i<chks.length;i++){
	        if(chks[i].type.toString()=='checkbox'){
	            for(var j=0;j<lbls.length;j++){
	                if(lbls[j].getAttributeNode('forCode')!=null && lbls[j].getAttributeNode('forCode').value==chks[i].value && chks[i].checked== true){
	                	if(val!=null && trim(val)!=""){
	                		if(trim(val)!=trim(lbls[j].innerHTML)){
	                			val+=lbls[j].innerHTML + "  ";
	                		}
	                	}else{
	                		val+=lbls[j].innerHTML + "  ";
	                	}
	                }
	            }
	        }
	      
	    }
		document.getElementById("showOutGoingParpose").innerHTML = val;   
		document.getElementById("checkOutPice").value = val;
		// 点击确定后，修改国家后把错误提示取消
		if(jQuery("#showOutGoingParpose").parent().children("i")){
			 jQuery("#showOutGoingParpose").parent().children("i").remove();
		}
		jQuery("#flow_md").hide();
}
function clearOutGoingParpose(){
	var chks=new Array();
   chks=document.getElementsByTagName('input');
   for(var i=0;i<chks.length;i++){ 
   	if(chks[i].type.toString()=='checkbox'){
   		if (chks[i].checked == true) {   
   			chks[i].checked = false;   
   		}   
   	}
	}
   document.getElementById("showOutGoingParpose").innerHTML = "";   
	document.getElementById("checkOutPice").value = "";
}
function str2date(str,n){  
	  var   dd, mm, yy;   
	  var   reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
	  if (arr = str.match(reg)) {
	    yy = Number(arr[1]);
	    mm = Number(arr[2])-1;
	    dd = Number(arr[3]);
	  } else {
	    var d = new Date();
	    yy = d.getUTCFullYear();
	    mm = ("00"+(d.getUTCMonth())).slice(-2);
	    dd = ("00"+d.getUTCDate()).slice(-2);
	  }
	return  date2str(yy, mm, dd , n);
} 
	
	function date2str(yy, mm, dd ,n) {
		  var s, d, t, t2;
		  t = Date.UTC(yy, mm, dd);
		  t2 = n * 1000 * 3600 * 24;
		  t+= t2;
		  d = new Date(t);
		  s = d.getUTCFullYear() + "-";
		  s += ("00"+(d.getUTCMonth()+1)).slice(-2) + "-";
		  s += ("00"+d.getUTCDate()).slice(-2);
		  return s;
	}


function   DateAdd(strInterval,NumDay,dtDate)   {   //参数 增加类型，增加天数，被增加日期
	  var dtTmp = new Date(dtDate);   
	  if   (isNaN(dtTmp)) dtTmp = new Date();
	  switch (strInterval){
	   case   "s":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (1000   *   parseInt(NumDay))); 
	     break;  
	   case   "n":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (60000   *   parseInt(NumDay))); 
	     break;  
	   case   "h":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (3600000   *   parseInt(NumDay)));
	     break;
	   case   "d":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (86400000   *   parseInt(NumDay)));
	     break;
	   case   "w":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   ((86400000   *   7)   *   parseInt(NumDay))); 
	     break;
	   case   "m":
	     dtTmp  =   new   Date(dtTmp.getFullYear(),   (dtTmp.getMonth())+parseInt(NumDay),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     break;   
	   case   "y":
	     dtTmp  =   new   Date(dtTmp.getFullYear()+parseInt(NumDay),   dtTmp.getMonth(),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     break;
	  }
	  var mStr=new String(dtTmp.getMonth()+1);
	  var dStr=new String(dtTmp.getDate());
	  if (mStr.length==1){
	   mStr="0"+mStr;
	  }
	  if (dStr.length==1){
	   dStr="0"+dStr;
	  }
	  return dtTmp.getFullYear()+"-"+mStr+"-"+dStr;
	}

function   DateDel(strInterval,NumDay,dtDate)   {   //参数 增加类型，增加天数，被增加日期
	  var   dtTmp   =   new   Date(dtDate);   
	  if   (isNaN(dtTmp))   dtTmp   =   new   Date();   
	  switch   (strInterval)   {   
	   case   "s":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (1000   *   parseInt(NumDay))); 
	     break;  
	   case   "n":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (60000   *   parseInt(NumDay))); 
	     break;  
	   case   "h":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (3600000   *   parseInt(NumDay)));
	     break;
	   case   "d":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (86400000   *   parseInt(NumDay)));
	     break;
	   case   "w":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   ((86400000   *   7)   *   parseInt(NumDay))); 
	     break;
	   case   "m":
	     dtTmp  =   new   Date(dtTmp.getFullYear(),   (dtTmp.getMonth())+parseInt(NumDay),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     break;   
	   case   "y":
	     dtTmp  =   new   Date(dtTmp.getFullYear()+parseInt(NumDay),   dtTmp.getMonth(),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     break;
	  }
	  var mStr=new String(dtTmp.getMonth()+1);
	  var dStr=new String(dtTmp.getDate());
	  if (mStr.length==1){
	   mStr="0"+mStr;
	  }
	  if (dStr.length==1){
	   dStr="0"+dStr;
	  }
	  return dtTmp.getFullYear()+"-"+mStr+"-"+dStr;
	}
function planChange(){
	var planValue = jQuery("#plan").val();
	jQuery("#planList-"+jQuery("#dutyFactor-plan").val()).hide();
	jQuery("#dutyFactor-plan").attr("value",planValue);
	jQuery("#planList-"+planValue).show();
	 var dutyCodeSize = jQuery("#dutyFactorSize").val();
	 var tempDuty = parseInt(dutyCodeSize);
	 
	 if(tempDuty > 1){//有责任信息
		 for(i=1;i<=tempDuty;i++){
			 jQuery("#amnt"+i).attr("value",jQuery("#amnt-"+planValue+"-"+i).val());
			 jQuery("#amntDis"+i).attr("value",jQuery("#amntDis-"+planValue+"-"+i).val());
			 jQuery("#prems"+i).attr("value",jQuery("#prem-"+planValue+"-"+i).val());
		 }
	 }
}
function st(){
	var effective = document.getElementById("effective").value;
	document.getElementById("start").innerHTML=effective+"起";
	
}
function en(){
	var fail = document.getElementById("fail").value;
	document.getElementById("end").innerHTML=fail+"止";

}
function add_zero(temp)
{
 if(temp<10) return "0"+temp;
 else return temp;
}
function failchange(){
	var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("true"==protectionPeriodFlag){
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 if("D" == protectionPeriodTy){
			 var fail = document.getElementById("fail").value;
			 protectionPeriodLast = "-"+protectionPeriodLast;
			 protectionPeriodLast=parseInt(protectionPeriodLast);
			 var  temp = str2date(fail+'',protectionPeriodLast);
			 document.getElementById("effective").value = "";
			 document.getElementById("effective").value = temp;
			
		 }
	 }
}
function periodChange(){
	setPeriodLastAndType();
	effchange();
}
 
function setPeriodLastAndType()
{
	var pp = jQuery("#period").val();
	if ((null == pp) || (typeof(pp)=="undefined"))
	{
		return;
	}
	var pp_length = pp.length;
	if(pp != null && pp.indexOf('-') == -1){
		jQuery("#protectionPeriodLast").attr("value" ,pp.substr(0, pp_length - 1));
		jQuery("#protectionPeriodTy").attr("value" ,pp.substr(pp_length - 1,1));
		
	} else if(pp.indexOf('-') != -1){
		var p_index = pp.indexOf('-') + 1;
		var pp_ = pp.substr(p_index);
		pp_length =  pp_.length;
		jQuery("#protectionPeriodLast").attr("value" , pp_.substr(0,pp_length - 1));
		jQuery("#protectionPeriodTy").attr("value" , pp_.substr(pp_length - 1,1));
	}
}

function setTab(name,cursel,n){ 
	for(var i=1;i<=n;i++){ 
		var menu=document.getElementById(name+i); 
		var con=document.getElementById(name+"_"+i); 
		menu.className=i==cursel?"butMSelect":"butM"; 
		con.style.display=i==cursel?"block":"none"; 
	} 
}
/*
 * 证件类型修改，原证件号码清空
 */
function changeVerification(Idtype,changeId){
	deltimeout_tipAll();//清空投保人信息的生日提示
	document.getElementById(changeId).value="";
	tObj = jQuery("#"+changeId); 
	if(tObj.parent().children("label").next("i")){
		tObj.parent().children("label").next("i").remove(); 
	} ;
	initBirthday(Idtype,changeId);
}
//出生日期變為灰色
function initBirthday(Idtype,changeId){
	var idType = document.getElementById(Idtype);
	var idType_index = idType.selectedIndex;          
	var idType_value = idType.options[idType_index].text;   
	var appOrrec_value =document.getElementById(changeId).value;
	if(changeId.indexOf("applicant")!=-1){
		if(idType_value.indexOf("身份证") != -1){
			if(appOrrec_value == ""){
				jQuery("#applicantBirthday").live('click',function(){
					WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+appEndDate+'}',maxDate:'%y-%M-{%d-'+appStartDate+'}'});
				});
			}else{
				jQuery("#applicantBirthday").removeAttr("onclick");
				jQuery("#applicantBirthday").unbind("click").click(function() {
				});
				jQuery("#applicantBirthday").attr("readonly","readonly");
			}
		}else{
			jQuery("#applicantBirthday").live('click',function(){
					WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+appEndDate+'}',maxDate:'%y-%M-{%d-'+appStartDate+'}'});
			});
		}
	}else if(changeId.indexOf("recognizee")!=-1){
		if(idType_value.indexOf("身份证") != -1){
			var _id = "_"+changeId.split("_")[1];
			if(appOrrec_value == ""){
				jQuery("#recognizeeBirthday"+_id).click(function(){
					WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+insEndDate+'}',maxDate:'%y-%M-{%d-'+insStartDate+'}'});
				});
			}else{
				jQuery("#recognizeeBirthday"+_id).removeAttr("onclick");
				jQuery("#recognizeeBirthday"+_id).unbind("click").click(function() {
				});
				jQuery("#recognizeeBirthday"+_id).attr("readonly","readonly");
			}
		}else{
			var _id = "_"+changeId.split("_")[1];
			jQuery("#recognizeeBirthday"+_id).click(function(){
				WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+insEndDate+'}',maxDate:'%y-%M-{%d-'+insStartDate+'}'});
			});
		}
	}
	/*if(idType_value.indexOf("身份证") != -1){
		if(changeId.indexOf("applicant")!=-1){
			jQuery("#applicantBirthday").attr("onclick","");
			jQuery("#applicantBirthday").attr("readonly","readonly");
		}else if(changeId.indexOf("recognizee")!=-1){
			var _id = "_"+changeId.split("_")[1];
			jQuery("#recognizeeBirthday"+_id).attr("onclick","");
			jQuery("#recognizeeBirthday"+_id).attr("readonly","readonly");
		}
	}else{
		if(changeId.indexOf("applicant")!=-1){
			alert(2)
			jQuery("#applicantBirthday").click(function(){
				WdatePicker({skin:'whyGreen',startDate:'1980-01-01'});
			});
		}else if(changeId.indexOf("recognizee")!=-1){
			var _id = "_"+changeId.split("_")[1];
			jQuery("#recognizeeBirthday"+_id).click(function(){
				WdatePicker({skin:'whyGreen',startDate:'1980-01-01'});
			});
		}
	}*/
}
function initRemove(){
	jQuery("#applicantBirthday").removeAttr("disabled");
	jQuery("select[id^='recognizeeTypeId']").each(function(){ 
		var tId = jQuery(this).attr("id"); 
		var _id = "_"+tId.split("_")[1];
		jQuery("#recognizeeBirthday"+_id).removeAttr("disabled");
	});
}
function changeAmnt(amnt,amntDis){
}

function checkAppInsID(){
	var t=idcheck('applicantTypeId','applicantId','applicantBirthday','applicantSex',"1");
	if(!t){jQuery('#applicantId').focus();return false;};
	var t1=true; 
	var flag = true;
	var tID="";
	jQuery("select[id^='recognizeeTypeId']").each(function(){
		var relation_id = jQuery(this).attr("id"); 
	    var _id ="";
		if(relation_id.indexOf("_")!=-1){
		    _id = "_"+relation_id.split("_")[1];
		}
		var t2=idcheck('recognizeeTypeId'+_id,'recognizeeId'+_id,'recognizeeBirthday'+_id,'recognizeeSex'+_id,"1"); 
	 	if(!t2){jQuery('#recognizeeId'+_id).focus();tID = "recognizeeId"+_id;flag = false;return false;}
	 	var t3=validateBirthday('recognizeeBirthday'+_id);  
	 	if(!t3){flag = false;tID="recognizeeBirthday"+_id;}
	}); 
	if(!flag){jQuery('#'+tID).focus();return false;};
	return true;
}
function checkAppID(){
	var t=idcheck('applicantTypeId','applicantId','applicantBirthday','applicantSex',"1");
	if(!t){jQuery('#applicantId').focus();return false;};
	return true;
}

function temptorysave(){
}
function rel(){
	 jQuery('.bbr_boxs dt em').each(function() { 
		    jQuery(this).unbind("click").click(function() {
	    	//jQuery(this).click(function(){
	    		if(tInsLastNum-1!=1){
		    			if( jQuery('.bbr_boxs').length>1){
		    				jQuery(this).parent().parent().remove();
		    				tInsLastNum = tInsLastNum-1;
		    				tInsPartNum= tInsLastNum; 
		    			    //被保人序号显示  
		    				var j = (tInsPartNum-1);
		    				jQuery("dl[id^=ins_]").each(function(){ 
		    					   jQuery(this).find(".bxr_num").html(tInsPartNum-j+".");
		    					   j=j-1;
		    				   }); 
		    				/*页面显示被保人个数*/
		    				jQuery("#insNum").html(tInsPartNum-1);
		    				jQuery("#insMult").html(tInsPartNum-1+calInsuredCount());
		    				jQuery("#insMult_1").html(tInsPartNum-1+calInsuredCount());
		    				//保费试算
		    				//金融会所
							selectIframe();
		    				calPrem(); 
		    			}else{
		    				art.dialog.alert("最后一个被保险人不能删除");
		    				return;
		    				 }
		    		}
	    	}); 
	 }); 
}
function validateRec(){
	var t1=idcheck('applicantTypeId','applicantId','applicantBirthday','applicantSex',"1");
	if(!t1){jQuery('#applicantTypeId').focus();return false;}
	var t = validateRecognizeeBir();   
	if(!t){jQuery('#applicantBirthday').focus();jQuery('#applicantBirthday').val("");return false;}
	return true;
}
function validateRecognizeeBir(){ 
	
    birthday = jQuery('#applicantBirthday').val(); 
	var effdate = jQuery("#effective").val(); 
	var tFlag;  
	if(birthday!=null && birthday!=""){
		jQuery.ajax({
			url: sinosoft.base+"/shop/order_config_new!ajaxAge.action",
			dataType: "json",
			data:{"applicantBirthday":birthday,"productId":productId,"effdate":effdate},
			type:"POST", 
			async: false,
			success: function(data) {
				if(!data){
					art.dialog.alert("您好，此年龄不能购买本产品");  
					tFlag =  false;
				}else{
					tFlag =  true;
				} 
			}
		});
	}  
    return tFlag;
}
function initAge(){
	
	var initYear = jQuery('#textAge').val(); 
	var newDate = new Date();
	var newYear = 0;
	var berfore = "";
	if(initYear!=null && initYear!=""){
	   
	   if(initYear.indexOf("-")!=-1){
		   berfore = initYear.split("-")[0];
		   if(berfore.indexOf("Y")!=-1){
			   newYear = berfore.split("Y")[0];
			   newDate.setFullYear(newDate.getFullYear()-parseInt(newYear));
			   newDate.setDate(newDate.getDate()-1);
		   }else if(berfore.indexOf("M")!=-1){
			   newYear = berfore.split("M")[0];
			   newDate.setMonth(newDate.getMonth()-parseInt(newYear));
			   newDate.setDate(newDate.getDate()-1);
		   }else if(berfore.indexOf("D")!=-1){
			   newYear = berfore.split("D")[0];  28
			   newDate.setDate(newDate.getDate()-newYear-1);
		   }
	   }else{
		   berfore = initYear;
		   newYear = berfore;
		   if(berfore.indexOf("Y")!=-1){
			   newDate.setFullYear(newDate.getFullYear()-parseInt(newYear));
			   newDate.setDate(newDate.getDate()-1);
		   }else if(berfore.indexOf("M")!=-1){
			   newDate.setMonth(newDate.getMonth()-parseInt(newYear));
			   newDate.setDate(newDate.getDate()-1);
		   }else if(berfore.indexOf("D")!=-1){
			   newDate.setDate(newDate.getDate()-newYear-1);
		   }
	   }
	   var initMonth = parseInt(newDate.getMonth())+1;
	   if(parseInt(initMonth)<10){
		   initMonth = "0"+initMonth;
	   }
	   var initDate = newDate.getDate();
	   if(parseInt(initDate)<10){
		   initDate = "0"+initDate;
	   }
	   return newDate.getFullYear()+"-"+initMonth+"-"+initDate;
	}else{
		return "";
	}
}
/**
 * 快速查询投保人信息
 */
function quickQueryAppnt(obj,appntName){
	var checkboxName = jQuery(obj).attr("name");
	jQuery(':checkbox[name='+checkboxName+']').removeAttr('checked');
	jQuery(obj).attr('checked','checked');
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!quickInputAppntOne.action?supplierCode2="+companyCode+"&loginFlag="+loginFlag+"&productId="+productId+"&reappntName="+encodeURIComponent(encodeURIComponent(appntName)),
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			if(data.message=="Suc"){
				fillAppnt(data,"input");
				
			}else if(data.message=="Fal"){
				art.dialog.alert("没有查到该投保人的相关信息，请选择其他投保人或者直接录入投保人信息！");
			}else{
				art.dialog.alert(data.Emessage);
			}
			whenTomorrowBirthday("1","",data.tomorrowIsBirthday);
		}
	});
	if(jQuery("#zhm_name")){
	   jQuery("#zhm_name").html(jQuery("#applicantName").val());
	}
	new typecontrol();// 证件类型控制 投保人
}
/**
 * 快速查询被保人信息
 */
function quickQueryInsured(obj,insuredName,selectIndex){
	var checkboxName = jQuery(obj).attr("name");
	jQuery(':checkbox[name='+checkboxName+']').removeAttr('checked');
	jQuery(obj).attr('checked','checked');
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!quickInputinsuredOne.action?supplierCode2="+companyCode+"&loginFlag="+loginFlag+"&productId="+productId+"&reinsuredName="+encodeURIComponent(encodeURIComponent(insuredName)),
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			if(data.message=="Suc"){
				fillInsured(data,"input",selectIndex);
			}else if(data.message=="Fal"){
				art.dialog.alert("没有查到该被保人的相关信息，请选择其他被保人或者直接录入被保人信息！");
			}else{
				art.dialog.alert(data.Emessage);
			}
			whenTomorrowBirthday("2",selectIndex,data.tomorrowIsBirthday);
		}
	});
	new typecontrol();// 证件类型控制 投保人
}
/**
 * 会员登錄，控制“快速录入”隐藏与显示
 */
function loginAfterAppnt(){
	jQuery('#shop_login').hide();
	//投保人
	jQuery("#quick_1").hide();
	jQuery("#quick_2").show();
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!quickInputAppnt.action?supplierCode2="+companyCode+"&productId="+productId,
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			fillAppnt(data,"init");
		}
	});
	//被保人
//	jQuery("#quick_3").hide();
//	jQuery("#quick_4").show();
//	jQuery("#insuredInfoDIV").hide();

	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!quickInputInsured.action?supplierCode2="+companyCode+"&productId="+productId,
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			fillInsured(data,"init","_0");
		}
	});
}
/**
 * 会员退出后，控制“快速录入”隐藏与显示
 */
function logoutAfterAppnt(){
	jQuery('#shop_login').show();
	//投保人
	jQuery("#quick_1").show();
	jQuery("#quick_2").hide();
	//被保人
	insuredLenFlag = false;
	jQuery("#hidd_insuredInfo").html("");
	jQuery(".order-user").each(function(){
		jQuery(this).hide();
	});
	// 快速登录按钮
	jQuery("#shoplogin").removeClass("login_londing");
	 // 会员活动，退出登陆后重新保费试算价格变化
	 if (memberActivity=='Y' && jQuery("#priceTotle").text() != null && jQuery("#priceTotle").text() != '') {
		 reCalPrem();
	 }
}
/**
 * 登錄后控制文案提示顯示樣式
 */
function loginAfterMsg(){
	//錄入頁
	jQuery("#msg_1").addClass("londing_meslog");
	//预览页
	jQuery("#msg_2").addClass("londing_mes_up");
}
/**
 * 退出后控制文案提示顯示樣式
 */
function logoutAfterMsg(){
	//錄入頁
	jQuery("#msg_1").removeClass("londing_meslog");
	//预览页
	jQuery("#msg_2").removeClass("londing_mes_up");
}
/**
 * 遮罩层隐藏
 */
function hideUI(){
	jQuery.unblockUI();
	jQuery("#msg_1").hide();  
	jQuery("#msg_2").hide();  
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
	jQuery("#msg_1").show();  
	jQuery("#msg_2").show();  
	jQuery("#msg_3").show();  
}
/**
 * 投保人信息快速录入-复制投保人信息
 * @param peaples 后台返回数据
 * @param flag 操作标记 init:页面初始化，input:点击“Name”调用
 * @param flag 操作标记 01:产品匹配，02：保险公司匹配,03:全站匹配
 */
function fillAppnt(peaples,flag){
	 
	 var returnList = peaples.sdrelationappnt;
	 if(flag=="init"){
		 jQuery("#quick_2_1").html("");
		 var info = "";
		 for(var i=0;i<returnList.length;i++){
			 jQuery("#quick_2_1").html("");
             info = info+"<label> <input name='quickappnt' type='radio' onclick='quickQueryAppnt(this,this.value);' title='"+returnList[i].applicantName+"' value='"+returnList[i].applicantName+"' />"+returnList[i].applicantName+"</label>";
		 }
		 if(returnList.length>=1){
			 jQuery("#quick_2").show();
			 jQuery("#quick_2_1").show();
			 jQuery("#quick_2_2").show();
			 jQuery("#quick_2_1").append(info); 
		 }else{
			 jQuery("#quick_2").hide();
			 jQuery("#quick_2_1").html("");
			 jQuery("#quick_2_1").hide();
			 jQuery("#quick_2_2").hide();
		 }
	 }else{
		 for(var i=0;i<returnList.length;i++){
			  if(returnList[i].applicantName!=null&&returnList[i].applicantName!=""){
				  jQuery("#applicantName").val(returnList[i].applicantName);	
			  }
			  if(returnList[i].applicantEnName!=null&&returnList[i].applicantEnName!=""){
				  jQuery("#applicantEnName").val(returnList[i].applicantEnName);	
			  }
			  //证件类型处理，根据汉字匹配证件类型编码appDictionaryCode
			  if(peaples.appflag!="03"){
				  if(returnList[i].applicantIdentityType!=null&&returnList[i].applicantIdentityType!=""){
					  jQuery("#applicantTypeId").val(returnList[i].applicantIdentityType);	
				  }
				  if(returnList[i].applicantIdentityId!=null&&returnList[i].applicantIdentityId!=""){
					  jQuery("#applicantId").val(returnList[i].applicantIdentityId);	
				  }
				  if(returnList[i].applicantSex!=null&&returnList[i].applicantSex!=""){
					 jQuery("input[name=sdinformationAppnt.applicantSex][value='"+returnList[i].applicantSex+"']").attr("checked",'checked'); 
					 //jQuery("#applicantSex").val(returnList[i].applicantSex);
				  }
				  if(returnList[i].socialSecurity!=null&&returnList[i].socialSecurity!=""){
					  jQuery("#socialSecurity").val(returnList[i].socialSecurity);	
				  }
			  }
			  if (returnList[i].applicantBirthday != null && returnList[i].applicantBirthday != "") {
				  // 初始化直接进行保费试算的产品,初始年龄不变,不回显
				  if ("undefined" == typeof productId || premProducts.indexOf(productId) == -1) {
					  jQuery("#applicantBirthday").val(returnList[i].applicantBirthday);	
				  }
			  }
			  if(returnList[i].applicantEndID!=null&&returnList[i].applicantEndID!=""){
				  if(returnList[i].applicantEndID=="9999-12-31"){
					  jQuery("#applicantEndID").attr("disabled","disabled");
					  jQuery("#applicantEndID").removeAttr("verify");
					  jQuery("#Checkzjnum_long").attr("checked","checked");
				  }else{
					  jQuery("#applicantEndID").val(returnList[i].applicantEndID);	
				  }
			  }else{
				  jQuery("#applicantEndID").val("");
			  }
			  if(returnList[i].applicantStartID!=null&&returnList[i].applicantStartID!=""){
				  jQuery("#applicantStartID").val(returnList[i].applicantStartID);	
			  }else{
				  jQuery("#applicantStartID").val("");
			  }
			  if(returnList[i].applicantMail!=null&&returnList[i].applicantMail!=""){
				  jQuery("#applicantMail").val(returnList[i].applicantMail);
				  jQuery("#applicantMail").parent().siblings(".app_mobile").hide();
			  } else {
				  jQuery("#applicantMail").parent().siblings(".app_mobile").show();
			  }
			  if(returnList[i].applicantMobile!=null&&returnList[i].applicantMobile!=""){
				  jQuery("#applicantMobile").val(returnList[i].applicantMobile);	
				  jQuery("#applicantMobile").parent().siblings(".app_mobile").hide();
			  } else {
				  jQuery("#applicantMobile").parent().siblings(".app_mobile").show();
			  }
			  if(returnList[i].applicantZipCode!=null&&returnList[i].applicantZipCode!=""){
				  jQuery("#applicantZipCode").val(returnList[i].applicantZipCode);	
			  }else{
				  jQuery("#applicantZipCode").val("");
			  }
			  if(returnList[i].applicantArea2!=null&&returnList[i].applicantArea2!=""){
				  jQuery("#applicantArea1").val(returnList[i].applicantArea1);
				  jQuery("#applicantArea2").val(returnList[i].applicantArea2);
				  getChildrenArea(returnList[i].applicantArea1,"applicantArea2",returnList[i].applicantArea2); 
			  }
			  if(returnList[i].applicantAddress!=null&&returnList[i].applicantAddress!=""){
				  jQuery("#applicantAddress").val(returnList[i].applicantAddress);	
			  }
			  //如果进行全站匹配，那么职业、地区、则不作匹配
			  if(peaples.appflag=="03"){
				//证件类型处理，根据汉字匹配证件类型编码appDictionaryCode
				  jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=certificate&codeName="+encodeURIComponent(encodeURIComponent(returnList[i].applicantIdentityTypeName)),
						dataType: "json",
						type:"GET", 
						async: false,
						success: function(data) {
							 var appCode=data.appDictionaryCode;
 							 if(appCode!=null&&appCode!=""){
								 jQuery("#applicantTypeId").val(appCode);	
								 if(returnList[i].applicantIdentityId!=null&&returnList[i].applicantIdentityId!=""){
									  jQuery("#applicantId").val(returnList[i].applicantIdentityId);	
								 }
							 }else{
								 jQuery.ajax({
										url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=certificate&codeName="+encodeURIComponent(encodeURIComponent("身份证")),
										dataType: "json",
										type:"GET", 
										async: false,
										success: function(data) {
											 var appCode1=data.appDictionaryCode;
											 jQuery("#applicantTypeId").val(appCode1);
											 jQuery("#applicantId").val("");
										}
								 });
							 }
						}
					});
				//性别处理，根据汉字匹配证件类型编码appDictionaryCode
				  jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=Sex&codeName="+encodeURIComponent(encodeURIComponent(returnList[i].applicantSexName)),
						dataType: "json",
						type:"GET", 
						async: false,
						success: function(data) {
							var appCode=data.appDictionaryCode;
							 if(appCode!=null&&appCode!=""){
								 jQuery("input[name=sdinformationAppnt.applicantSex][value="+data.appDictionaryCode+"]").attr("checked",'checked'); 
							 }else{
								 jQuery.ajax({
										url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=Sex&codeName="+encodeURIComponent(encodeURIComponent("男")),
										dataType: "json",
										type:"GET", 
										async: false,
										success: function(data) {
											 var appCode1=data.appDictionaryCode;
											 jQuery("input[name=sdinformationAppnt.applicantSex][value="+appCode1+"]").attr("checked",'checked'); 
										}
								 });
							 }
						}
					});
				  jQuery("#applicantArea1").val("-1");
				  jQuery("#applicantArea2").val("-1");
				  jQuery("#applicantArea3").val("-1");
				  jQuery("#applicantAddress").val("");
				  jQuery("#applicantOccupation1").val("-1");
				  jQuery("#applicantOccupation2").val("-1");
				  jQuery("#applicantOccupation3").val("-1");
			  }else{
				  if(returnList[i].applicantArea3!=null&&returnList[i].applicantArea3!=""){
					  jQuery("#applicantArea1").val(returnList[i].applicantArea1);
					  jQuery("#applicantArea2").val(returnList[i].applicantArea2);
					  jQuery("#applicantArea3").val(returnList[i].applicantArea3);
					  getChildrenArea(returnList[i].applicantArea1,"applicantArea2",returnList[i].applicantArea2); 
					  getChildrenArea(returnList[i].applicantArea2,"applicantArea3",returnList[i].applicantArea3); 
				  }
				  if(returnList[i].applicantOccupation2!=null&&returnList[i].applicantOccupation2!=""){
					    jQuery("#applicantOccupation1").val(returnList[i].applicantOccupation1);
					    getChildrenOPT(returnList[i].applicantOccupation1,'applicantOccupation2',returnList[i].applicantOccupation2,null,'2');
						getChildrenOPT(returnList[i].applicantOccupation2,'applicantOccupation3',returnList[i].applicantOccupation3,null,'3');
				  }else if(returnList[i].applicantOccupation3!=null&&returnList[i].applicantOccupation3!=""){
					  jQuery("#applicantOccupation1").val(returnList[i].applicantOccupation1);
					  getChildrenOPT(returnList[i].applicantOccupation1,'applicantOccupation3',returnList[i].applicantOccupation3,null,'3');
				  }
			  }
		 } 
		 // 如果是批量导入咋无需从新计算保费
		 if(jQuery("#mulInsuredFlag").val()!="rid_td"){ 
			  calPrem();
			  
		 }
		 
	 }
	 initBirthday("applicantTypeId","applicantId");
	 
	 
}

/**
 * 投保人信息快速录入-复制投保人信息
 * @param peaples 后台返回数据
 * @param flag 操作标记 init:页面初始化，input:点击“Name”调用
 * @param flag 操作标记 01:产品匹配，02：保险公司匹配,03:全站匹配
 */
function fillInsured(peaples,flag,selectIndex){
	jQuery(".cy_user order-user").each(function(){
		jQuery(this).children("dl").children("dd").html("");
		jQuery(this).hide();
	});
	 var returnList = peaples.relationrecognizee;
	 if(flag=="init"){
		 jQuery("#quick_4_1").html("");
		 var info = "";
		 for(var i=0;i<returnList.length;i++){
			 jQuery("#quick_4_1").html("");
			 info = info+"<label> <input name='quickrecognizee' type='radio' onclick='quickQueryInsured(this,this.value,&quot_0" +
      		"&quot);' title='"+returnList[i].recognizeeName+"' value='"+returnList[i].recognizeeName+"' />"+returnList[i].recognizeeName+"</label>";		 
		 }
		 if(returnList.length>=1){
			 insuredLenFlag = true;
			 var myradiochecked=jQuery("input[name='myradio']:checked");
			 if(myradiochecked && myradiochecked.get(0)){
                 if(myradiochecked.get(0).id=="rid_orther"){
                     jQuery(".order-user").each(function(){
                         jQuery(this).show();
                     });
                 }
			 } else if (jQuery("#relationIsSelf").val()!="Y"){
                 jQuery(".order-user").each(function(){
                     jQuery(this).show();
                 });
             }
			 jQuery(".order-user").each(function(){
				 jQuery(this).children("dl").children("dd").html(info);
			 });
			 
		 }else{
			 insuredLenFlag = false;
			 jQuery(".cy_user order-user").each(function(){
				 jQuery(this).hide();
				 jQuery(this).children("dl").children("dd").html("");
			 });
		 }
		 // 会员活动，登陆后重新保费试算价格变化
		 if (memberActivity=='Y' && jQuery("#priceTotle").text() != null && jQuery("#priceTotle").text() != '') {
			 reCalPrem();
		 }
	 }else{
		 //去最后一个被保人、进行被保人快速录入操作
		 var insuredID = jQuery("input[id^='recognizeeName']:last").attr("id");
		 var index_insuredID = "_0";
		 if(insuredID!=null && insuredID!="null"){
			 index_insuredID = "_"+insuredID.split("_")[1];
		 }
		 if(selectIndex!=null && selectIndex!="null"){
			 index_insuredID = selectIndex;
		 }
		 
		 var index_insured = index_insuredID.split("_")[1];
		 
		 //保险期限为至XX岁时的产品一键添加常用被保人后设置保单终止日期
		 if (document.getElementById("Ensure") && 
		 	 document.getElementById("Ensure").value.indexOf("A") != -1) {
		 	 var recognizeeBirthday_0 = returnList[0].recognizeeBirthday;
		     if(recognizeeBirthday_0 !=null && recognizeeBirthday_0 != ""){
		     	changeEndDateForToAge(recognizeeBirthday_0, document.getElementById("effective").value);
		     }
		 }	
		

		 for(var i=0;i<returnList.length;i++){
			  if(returnList[i].recognizeeName!=null&&returnList[i].recognizeeName!=""){
				  jQuery("#recognizeeName"+index_insuredID).val(returnList[i].recognizeeName);	
			  }
			  
			  if(returnList[i].recognizeeEnName!=null&&returnList[i].recognizeeEnName!=""){
				  jQuery("#recognizeeEnName"+index_insuredID).val(returnList[i].recognizeeEnName);	
			  }
			  //证件类型处理，根据汉字匹配证件类型编码appDictionaryCode
			  if(peaples.appflag!="03"){
				  if(returnList[i].recognizeeIdentityType!=null&&returnList[i].recognizeeIdentityType!=""){
					  jQuery("#recognizeeTypeId"+index_insuredID).val(returnList[i].recognizeeIdentityType);	
				  }
				  if(returnList[i].recognizeeIdentityId!=null&&returnList[i].recognizeeIdentityId!=""){
					  jQuery("#recognizeeId"+index_insuredID).val(returnList[i].recognizeeIdentityId);	
				  }
				  if(returnList[i].recognizeeSex!=null&&returnList[i].recognizeeSex!=""){
					  //jQuery("#recognizeeSex"+index_insuredID).val(returnList[i].recognizeeSex);	
					  jQuery("input[name=sdinformationinsuredList["+index_insured+"].recognizeeSex][value="+returnList[i].recognizeeSex+"]").attr("checked",'checked'); 
				  }
			  }
			  if(returnList[i].recognizeeBirthday!=null&&returnList[i].recognizeeBirthday!=""){
				  jQuery("#recognizeeBirthday"+index_insuredID).val(returnList[i].recognizeeBirthday);	
			  }
			  if(returnList[i].recognizeeEndID!=null&&returnList[i].recognizeeEndID!=""){
				  jQuery("#recognizeeEndID"+index_insuredID).val(returnList[i].recognizeeEndID);	
			  }else{
				  jQuery("#recognizeeEndID"+index_insuredID).val("");
			  }
			  if(returnList[i].recognizeeStartID!=null&&returnList[i].recognizeeStartID!=""){
				  jQuery("#recognizeeStartID"+index_insuredID).val(returnList[i].recognizeeStartID);	
			  }else{
				  jQuery("#recognizeeStartID"+index_insuredID).val("");
			  }
			  if(returnList[i].recognizeeMail!=null&&returnList[i].recognizeeMail!=""){
				  jQuery("#recognizeeMail"+index_insuredID).val(returnList[i].recognizeeMail);	
			  }
			  if(returnList[i].recognizeeMobile!=null&&returnList[i].recognizeeMobile!=""){
				  jQuery("#recognizeeMobile"+index_insuredID).val(returnList[i].recognizeeMobile);	
			  }
			  if(returnList[i].recognizeeZipCode!=null&&returnList[i].recognizeeZipCode!=""){
				  jQuery("#recognizeeZipCode"+index_insuredID).val(returnList[i].recognizeeZipCode);	
			  }else{
				  jQuery("#recognizeeZipCode"+index_insuredID).val("");
			  }
			  if(returnList[i].recognizeeArea2!=null&&returnList[i].recognizeeArea2!=""){
				  jQuery("#recognizeeArea1"+index_insuredID).val(returnList[i].recognizeeArea1);
				  jQuery("#recognizeeArea2"+index_insuredID).val(returnList[i].recognizeeArea2);
				  getChildrenArea(returnList[i].recognizeeArea1,"recognizeeArea2"+index_insuredID,returnList[i].recognizeeArea2); 
			  }
			  if(returnList[i].recognizeeAddress!=null&&returnList[i].recognizeeAddress!=""){
				  jQuery("#recognizeeAddress"+index_insuredID).val(returnList[i].recognizeeAddress);	
			  }
			  //如果进行全站匹配，那么职业、地区、则不作匹配
			  if(peaples.appflag=="03"){
				//证件类型处理，根据汉字匹配证件类型编码appDictionaryCode
				  jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=certificate&codeName="+encodeURIComponent(encodeURIComponent(returnList[i].recognizeeIdentityTypeName)),
						dataType: "json",
						type:"GET", 
						async: false,
						success: function(data) {
							 var appCode=data.appDictionaryCode;
 							 if(appCode!=null&&appCode!=""){
								 jQuery("#recognizeeTypeId"+index_insuredID).val(appCode);	
								 if(returnList[i].recognizeeIdentityId!=null&&returnList[i].recognizeeIdentityId!=""){
									  jQuery("#recognizeeId"+index_insuredID).val(returnList[i].recognizeeIdentityId);	
								 }
							 }else{
								 jQuery.ajax({
										url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=certificate&codeName="+encodeURIComponent(encodeURIComponent("身份证")),
										dataType: "json",
										type:"GET", 
										async: false,
										success: function(data) {
											 var appCode1=data.appDictionaryCode;
											 jQuery("#recognizeeTypeId"+index_insuredID).val(appCode1);
											 jQuery("#recognizeeId"+index_insuredID).val("");
										}
								 });
							 }
						}
					});
				//性别处理，根据汉字匹配证件类型编码appDictionaryCode
				  jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=Sex&codeName="+encodeURIComponent(encodeURIComponent(returnList[i].recognizeeSexName)),
						dataType: "json",
						type:"GET", 
						async: false,
						success: function(data) {
							var appCode=data.appDictionaryCode;
							 if(appCode!=null&&appCode!=""){
								 jQuery("#recognizeeSex"+index_insuredID).val(data.appDictionaryCode);	
								 jQuery("input[name=sdinformationinsuredList["+index_insured+"].recognizeeSex][value="+data.appDictionaryCode+"]").attr("checked",'checked');
							 }else{
								 jQuery.ajax({
										url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=Sex&codeName="+encodeURIComponent(encodeURIComponent("男")),
										dataType: "json",
										type:"GET", 
										async: false,
										success: function(data) {
											 var appCode1=data.appDictionaryCode;
											 jQuery("#recognizeeSex"+index_insuredID).val(appCode1);
											 jQuery("input[name=sdinformationinsuredList["+index_insured+"].recognizeeSex][value="+appCode1+"]").attr("checked",'checked');
										}
								 });
							 }
						}
					});
				  jQuery("#recognizeeArea1"+index_insuredID).val("-1");
				  jQuery("#recognizeeArea2"+index_insuredID).val("-1");
				  jQuery("#recognizeeArea3"+index_insuredID).val("-1");
				  jQuery("#recognizeeAddress"+index_insuredID).val("");
				  jQuery("#recognizeeOccupation1"+index_insuredID).val("-1");
				  jQuery("#recognizeeOccupation2"+index_insuredID).val("-1");
				  jQuery("#recognizeeOccupation3"+index_insuredID).val("-1");
			  }else{
				  if(returnList[i].recognizeeArea3!=null&&returnList[i].recognizeeArea3!=""){
					  jQuery("#recognizeeArea1"+index_insuredID).val(returnList[i].recognizeeArea1);
					  jQuery("#recognizeeArea2"+index_insuredID).val(returnList[i].recognizeeArea2);
					  jQuery("#recognizeeArea3"+index_insuredID).val(returnList[i].recognizeeArea3);
					  getChildrenArea(returnList[i].recognizeeArea1,"recognizeeArea2"+index_insuredID,returnList[i].recognizeeArea2); 
					  getChildrenArea(returnList[i].recognizeeArea2,"recognizeeArea3"+index_insuredID,returnList[i].recognizeeArea3); 
				  }
				  if(returnList[i].recognizeeOccupation2!=null&&returnList[i].recognizeeOccupation2!=""){
					    jQuery("#recognizeeOccupation1"+index_insuredID).val(returnList[i].recognizeeOccupation1);
					    getChildrenOPT(returnList[i].recognizeeOccupation1,'recognizeeOccupation2'+index_insuredID,returnList[i].recognizeeOccupation2,null,'2');
						getChildrenOPT(returnList[i].recognizeeOccupation2,'recognizeeOccupation3'+index_insuredID,returnList[i].recognizeeOccupation3,null,'3');
				  }else if(returnList[i].recognizeeOccupation3!=null&&returnList[i].recognizeeOccupation3!=""){
					  jQuery("#recognizeeOccupation1"+index_insuredID).val(returnList[i].recognizeeOccupation1);
					  getChildrenOPT(returnList[i].recognizeeOccupation1,'recognizeeOccupation3'+index_insuredID,returnList[i].recognizeeOccupation3,null,'3');
				  }
			  }
			  initBirthday(jQuery("#recognizeeTypeId"+index_insuredID).attr("id"),jQuery("#recognizeeId"+index_insuredID+"").attr("id"));				  
		 
		 }
		 calPrem();	
	 }

}
/**
 * 购买份数改变后，保费随之改变
 * @param eID 元素ID
 * @param eValue 元素value
 */
function changePremByCont(eID,eValue){
	
	//var tID = eID.split("_")[1];
	//var tValue = eValue;
	var mCountPrem=0;
	var mCountTotalPrem=0;
	jQuery("select[id^='recognizeeMul']").each(function(){
		var id = jQuery(this).attr("id"); 
		var value = jQuery(this).val();
		var _id ="";
		if(jQuery("#mulInsuredFlag").val()=="undefined" || jQuery("#mulInsuredFlag").val()==null || jQuery("#mulInsuredFlag").val()=="" ||jQuery("#relationIsSelf").val()=="Y"){
			calPrem();
		}else if(jQuery("#mulInsuredFlag").val()=="rid_me"){
			if(id.indexOf("_")==-1){
				calPrem();
				/*不要删除
				 * var basePrem = parseFloat(jQuery("#totalAmount").val())/parseFloat(jQuery("#recognizeeMul").val());//被保人一份保费
				var baseTotalPrem = parseFloat(jQuery("#productTotalPrice").val())/parseFloat(jQuery("#recognizeeMul").val());//被保人一份原价
				alert(basePrem);
				var countPrem = parseFloat(basePrem)*(parseFloat(value));
				var countTotalPrem = parseFloat(baseTotalPrem)*(parseFloat(value));
				mCountPrem = parseFloat(mCountPrem)+parseFloat(countPrem);
				mCountTotalPrem = parseFloat(mCountTotalPrem)+parseFloat(countTotalPrem);*/
			}
		}else if(jQuery("#mulInsuredFlag").val()=="rid_orther"){
			if(id.indexOf("_")!=-1){
				calPrem();
			    /*不要删除
			     * _id = id.split("_")[1];
			    var basePrem = jQuery("#recognizeePrem_"+_id).val();//被保人一份保费
				var baseTotalPrem = jQuery("#recognizeePrem_"+_id).val();//被保人一份原价
				var countPrem = parseFloat(basePrem)*(parseFloat(value));
				var countTotalPrem = parseFloat(baseTotalPrem)*(parseFloat(value));
				mCountPrem = parseFloat(mCountPrem)+parseFloat(countPrem);
				mCountTotalPrem = parseFloat(mCountTotalPrem)+parseFloat(countTotalPrem);
				if(document.getElementById("discountPrice")){
					 document.getElementById("discountPrice").innerHTML=mCountTotalPrem;
					 //document.getElementById("discountPrice_").innerHTML=obj.retTotlePrem;
					 jQuery("#productTotalPrice").attr("value",mCountPrem); 
				 }
				 jQuery("#totalAmount").val(mCountPrem);
				 jQuery("#productTotalPrice").attr("value",mCountTotalPrem);
				 document.getElementById("priceTotle_1").innerHTML=mCountPrem;
				 document.getElementById("priceTotle").innerHTML=mCountPrem; 
				 document.getElementById("priceTotle_").innerHTML=mCountPrem; 
				 document.getElementById("priceTotle_2").innerHTML=mCountPrem;*/
			} 
		}
	});
	if(jQuery("#mulInsuredFlag").val()=="undefined" || jQuery("#mulInsuredFlag").val()==null || jQuery("#mulInsuredFlag").val()==""){
		jQuery("#insMult").html(calInsuredCount());//有效份数
		jQuery("#insMult_1").html(calInsuredCount());//有效份数
	}else{
		jQuery("#insMult").html(tInsLastNum-1+calInsuredCount());//有效份数
		jQuery("#insMult_1").html(tInsLastNum-1+calInsuredCount());//有效份数
	}
	/*不要删除
	 * var basePrem = jQuery("#recognizeePrem_"+tID).val();//被保人一份保费
	var baseTotalPrem = jQuery("#recognizeePrem_"+tID).val();//被保人一份原价
	var countPrem = parseFloat(basePrem)*(parseFloat(tValue)-1);
	var countTotalPrem = parseFloat(baseTotalPrem)*(parseFloat(tValue)-1);
	jQuery("#insMult").html(parseFloat(parseFloat(tValue)-1)+parseFloat(jQuery("#insMult").html()));//有效份数
	if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=parseFloat(countTotalPrem)+parseFloat(document.getElementById("discountPrice").innerHTML);
		 //document.getElementById("discountPrice_").innerHTML=obj.retTotlePrem;
		 jQuery("#productTotalPrice").attr("value",parseFloat(countPrem)+parseFloat(jQuery("#productTotalPrice").val())); 
	 }
	 jQuery("#totalAmount").val(parseFloat(countPrem)+parseFloat(jQuery("#totalAmount").val()));
	 jQuery("#productTotalPrice").attr("value",parseFloat(countTotalPrem)+parseFloat(jQuery("#productTotalPrice").val()));
	 document.getElementById("priceTotle_1").innerHTML=parseFloat(countPrem)+parseFloat(document.getElementById("priceTotle_1").innerHTML);
	 document.getElementById("priceTotle").innerHTML=parseFloat(countPrem)+parseFloat(document.getElementById("priceTotle").innerHTML); 
	 document.getElementById("priceTotle_").innerHTML=parseFloat(countPrem)+parseFloat(document.getElementById("priceTotle_").innerHTML); 
	 document.getElementById("priceTotle_2").innerHTML=parseFloat(countPrem)+parseFloat(document.getElementById("priceTotle_2").innerHTML);*/
}
/**
 * 计算有效份数
 */
function calInsuredCount(){
	
	var tCount=0;
	jQuery("select[id^='recognizeeMul']").each(function(){
		var id = jQuery(this).attr("id"); 
		var value = jQuery(this).val();
		if(jQuery("#mulInsuredFlag").val()!="undefined" && jQuery("#mulInsuredFlag").val()!=null && jQuery("#mulInsuredFlag").val()!=""){
			if(jQuery("#mulInsuredFlag").val()=="rid_me"){
				if(id.indexOf("_")==-1){
					tCount = parseFloat(value)-1;
				}
			}else if(jQuery("#mulInsuredFlag").val()=="rid_orther"){
				if(id.indexOf("_")!=-1){
					tCount = parseFloat(tCount)+parseFloat(value)-1;
				}
			}
		}else{
			tCount = parseFloat(value);
		}
	});
	return tCount;
}
document.getElementsByTagName("body")[0].onkeydown =function(){  
	//解决firefox报错：event is not defined
	var event = arguments[0]||window.event;
    if(event.keyCode==8){  
        var elem = event.srcElement;
        if(elem!=undefined){
	        var name = elem.nodeName;  
	          
	        if(name!='INPUT' && name!='TEXTAREA'){  
	        	 return false ;  
	        }  
	        var type_e = elem.type.toUpperCase();  
	        if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){  
	        	 return false ;  
	        }  
	        if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){  
	        	 return false ;   
	        } 
        }
    }  
}  
/*控制被保人信息打开与收起*/
function elementFocus(cBoxName){
	var tObj = jQuery("#"+cBoxName);
	tObj.parent().parent().parent().parent().find("dt").show();  //不包括投、被保人信息快速录入
	tObj.parent().parent().parent().parent().find("dt").find("a").text("收起");   
	tObj.parent().parent().parent().parent().find("dt").find("a").removeClass("kg_jia");
	tObj.parent().parent().parent().parent().find("dt").find("i").hide();
	tObj.parent().parent().parent().parent().parent().removeClass("bbr_bor");
}

/*控制被保人信息打开与收起*/
function elementInsuredFocus(cBoxName){
	var tObj = jQuery("#"+cBoxName);
	tObj.parent().parent().parent().parent().parent().find("dt").find("a").trigger("click");
}

var editFlag = false;
function edit_user(user_ids, e) {

	if (jQuery(user_ids).find(".tb_mes").is(":visible")) {
		if(excelinsuredImpFlag){
			art.dialog.tips('被保人信息未保存，请先保存！');
			return;
		}
		jQuery(user_ids).find(".tb_mes").hide();
		jQuery(user_ids).find(".tb_up_date").show();
		jQuery(e).text("保存");
		excelinsuredImpFlag = true;
	} else {
		jQuery(user_ids).find(".tb_mes").show();
		jQuery(user_ids).find(".tb_up_date").hide();
		validateExcelInfo(user_ids);
		copyExcelInfo(user_ids);
		jQuery(e).text("修改");
		excelinsuredImpFlag = false;
	}
}
/*excel 赋值*/
function copyExcelInfo(user_ids){
	//遍历所有咧
	jQuery(user_ids).find("td").each(function(){
		//定位第二个em
		jQuery(this).find("em:eq(1)").each(function(){
			//定位子元素
			jQuery(this).children().each(function(){
				 var c_id = jQuery(this).attr("id");
				 var c_value = jQuery(this).val();
				 jQuery("#em_"+c_id).html(c_value);
			});
		});
	});
}
/*excel导入 被保人信息校验*/
function validateExcelInfo(user_ids){
	var id = jQuery(user_ids).attr("id");

	var index = id.split("_")[1];
	// 校验身份证是否相同
	var passFlag = true;
	var idType = $("#"+id).find("#sdrecognizeeTypeName_"+index).val();
	var idVal = $("#"+id).find("#sdrecognizeeId_"+index).val();
	var sdrecognizeeName = $("#sdrecognizeeName_"+index).val();
	var boxId = "sdrecognizeeId_"+index;
	$("#insuredtab").find("input[id^='sdrecognizeeId_']").each(function(){
		var otherId = $(this).attr("id");
		var otherIndex = otherId.split("_")[1];
		var otherIdVal = $(this).val();
		var otherIdType = $("#sdrecognizeeTypeName_"+otherIndex).val();
		var otherSdrecognizeeName = $("#sdrecognizeeName_"+otherIndex).val();
		//console.log(otherIdVal + otherIdType + otherSdrecognizeeName);
		if(index != otherIndex && idVal == otherIdVal && idType == otherIdType){
			$("#sdrecognizeeId_"+index).parent().parent().addClass("td_hlight");
			showExcelError(boxId,["与被保人"+otherSdrecognizeeName+"证件相同"],"sameIdNo");
			passFlag = false;
			return;
		}
	});
	$("#importMessage").find("i").each(function(){
		if($(this).text().indexOf(sdrecognizeeName+"证件相同") != -1){
			var id = $(this).attr("id");
			var dindex = id.split("_")[1];
			var dIdVal = $("#sdrecognizeeId_"+dindex).val();
			if(passFlag || dIdVal != idVal){
				if($("#recognizee_"+dindex+" i").length < 2){
					$("#recognizee_"+dindex).remove();
				}else{
					$(this).remove();
				}
				$("#sdrecognizeeId_"+dindex).parent().parent().removeClass("td_hlight");
			}
		}
	});
	if(!passFlag){
		return;
	}
	verifyByTrId(id);
}

/*excel导入删除单被保人 */
function del_user(user_ids,e){
	
	art.dialog.confirm('确定删除此被保人信息？', function () {
		if(jQuery("tr[id^='sdrecognizee_']").length==1){
			var dialog = art.dialog({
			    title: '提示',
			    content: '最后一个被保人不能删除！',
			    ok: function(){
			    }
			});
		}else{
			var id = jQuery(user_ids).attr("id").split("_")[1];
			fillinDeleteExcel(id);
			jQuery(user_ids).remove();
			jQuery(e).parents("li").remove();
			jQuery("#recognizee_"+id).remove();
			resetInsSeriNo();
		    art.dialog.tips('删除被保人成功！');
		    excelinsuredImpFlag = false;
		}
		
	}, function () {
	});
	excelsaveFlag = true;
}
/**
 * 批量导入删除单被保人 保费试算后回调函数
 * @param obj
 */
function fillinDeleteExcel(id_index){
	
	 var rePrem = jQuery("#sddiscountPrice_"+id_index).val();//原网站折后保费
	 var reComPrem = jQuery("#sdrecognizeePrem_"+id_index).val();//原保险公司折后保费
	 var reTotalPrem = jQuery("#sdrecognizeeTotalPrem_"+id_index).val();//原折前保费
	 
     var priceTotle_1 = document.getElementById("priceTotle_1").innerHTML;
     var totalAmount = jQuery("#totalAmount").val();
	 var productTotalPrice = document.getElementById("productTotalPrice").value;
	 var discountPrice = document.getElementById("discountPrice").innerHTML;
	 var payPrice = document.getElementById("payPrice").value;
	 var newpayPrice = parseFloat(payPrice)-parseFloat(rePrem);
	 newpayPrice= newpayPrice.toFixed(2);
	 
	 var newpriceTotle_1 = parseFloat(priceTotle_1)-parseFloat(reTotalPrem);
	 newpriceTotle_1= newpriceTotle_1.toFixed(2);
     var newtotalAmount = parseFloat(totalAmount)-parseFloat(reComPrem);
     newtotalAmount= newtotalAmount.toFixed(2);
	 var newdiscountPrice = parseFloat(discountPrice)-parseFloat(reComPrem);
	 newdiscountPrice= newdiscountPrice.toFixed(2);
	 
	 var newproductTotalPrice = parseFloat(productTotalPrice)-parseFloat(reTotalPrem);
	 newproductTotalPrice= newproductTotalPrice.toFixed(2);
	 
	 if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=newdiscountPrice;
		 jQuery("#productTotalPrice").attr("value",newproductTotalPrice); 
	 }
	 if (jQuery("#pointExchangeFlag").val() == '1') {
		 var PointScalerUnit = jQuery("#PointScalerUnit").val();
		 if (PointScalerUnit == null || PointScalerUnit == '') {
			 PointScalerUnit = '10';
		 }
		 var pointPrem = (newpriceTotle_1 * parseInt(PointScalerUnit)).toFixed(1);
		 var pointPrem1 = Math.ceil(pointPrem);
		 document.getElementById("priceTotle").innerHTML=Math.ceil(pointPrem1);
		 document.getElementById("priceTotle_1").innerHTML=newpriceTotle_1;
		 jQuery("#offsetPoint").val(pointPrem1);
		 jQuery("#totalAmount").val(newpriceTotle_1);
		 jQuery("#payPrice").val(0);
	 } else {
		 jQuery("#totalAmount").val(newtotalAmount);
		 jQuery("#payPrice").val(newpayPrice);
		 document.getElementById("priceTotle_1").innerHTML=newpriceTotle_1;
		 document.getElementById("priceTotle").innerHTML=newpayPrice;
		 var cpsUserId = jQuery.cookie('cpsUserId');
		 var channelsn = "wj";
		 if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
			 //channelsn = "cps";
		 }
		 var insMult = excelinsuredCount-1;
		 jQuery.getJSON(sinosoft.base + '/shop/sales_volume!pointInfo.action?price='+newpayPrice+"&productId="+productId+"&channelsn="+jQuery("#channelsn").val()+"&risktypeNum="+insMult+"&orderSnTem="+orderSn,
						function(data) {
	 					    if(data){
	 					    	if (data.inspageone_pointsInfo != "") {
	 					    		jQuery('.integal_hjcon').show();
	 					    		jQuery(".integral_hj").html(data.inspageone_pointsInfo);
	 					    	}
	 					    	jQuery("#result_sendPointsDesc").html(data.result_sendPointsDesc);
								jQuery("#result_sendPoints").html(data.result_sendPoints);
								jQuery("#result_sendPointsValue").html(data.result_sendPointsValue);
								whenIntegralZero(data.result_sendPoints);
							}
				         }
				);
	 }
	 
	 //页面人数控制
	 excelinsuredCount = excelinsuredCount-1;
	 jQuery("#insNum").html(excelinsuredCount);
	 jQuery("#insMult").html(excelinsuredCount);
	 jQuery("#insMult_1").html(excelinsuredCount);

}

function getLastDay(year,month)        
{     
 var new_year = year;    //取当前的年份         
 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）         
 if(month>12)            //如果当前大于12月，则年份转到下一年         
 {        
  new_month -=12;        //月份减         
  new_year++;            //年份增         
 }       
 var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天         
 return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期         
} 
/*重置被保人编号*/
function resetInsSeriNo(){
	jQuery("#insuredtab tr:gt(0)").each(function(){
		var index = jQuery(this).index();
		jQuery(this).find("td:eq(0)").html(index);
	});
}

// 初始化受益人信息
jQuery(document).ready(function() {
		benefitRadioShow();

// 判断受益人是否为独立受益人 
			if (jQuery("#benefitb_num").val()==1) {
			jQuery("#addfavoreeins").hide();
			jQuery(".bbr_boxs-syr dt a").hide();
			jQuery(".bbr_boxs-syr dt i").hide();
			jQuery(".bbr_boxs-syr dt em").hide();
			jQuery(".bbr_boxs-syr .bbr-remes-btn").hide();
			};


})

var syInsNum=0;
/*受益人信息*/ 
var syInsInof="";
/*受益人数量*/
var syInsNum=parseInt(jQuery("dl[id^=ins2_]:last").find(".bxr_num").html())+1;
/*受益人最终数量*/
var syInsLastNum=parseInt(jQuery("dl[id^=ins2_]:last").find(".bxr_num").html())+1;
/*受益人radio切换*/ 
function benefitRadioShow(){
	var myradio=jQuery("#recognizeetg_zd");
	if(myradio.attr("checked")){
			jQuery("#benefitb_box").show();
			return true
		}else{
			jQuery("#benefitb_box").hide();
			return false
		}
}

 	 /*
     * 增加一个受益人
     */
    if(syInsInof==""){
    	 var tName2 = jQuery("#favoreeName_0").val();
    	 jQuery("#favoreeName_0").val("");
     	 syInsInof=jQuery("#ins2_0").html(); 
         jQuery("#favoreeName_0").val(tName2);
		if(syInsInof != null && jQuery("#ins2_0")){
			var isIE8 = !!window.XDomainRequest&&!!document.documentMode;
			var isIE7 = navigator.userAgent.toLowerCase().indexOf("msie 7.0") != -1 && !isIE8;
			var isIE6 = navigator.userAgent.toLowerCase().indexOf("msie 6.0") != -1;
			var aregexTag1= new RegExp("<\\s*(I|i)(N|n)(P|p)(U|u)(T|t)\\s[^>]*(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*\"([^\"]*)\"","g");
			var regexTag11= new RegExp("(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*\"([^\"]*)\"","g");
		    var regexTag21= new RegExp("selected=\"selected\"","g");
			if(isIE8||isIE7||isIE6){ 
				 aregexTag1= new RegExp("<\\s*(I|i)(N|n)(P|p)(U|u)(T|t)\\s[^>]*(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*","g");
				 regexTag11= new RegExp("(V|v)(A|a)(L|l)(U|u)(E|e)\\s*=\\s*","g");
			     regexTag21= new RegExp("\s*selected\s*","g");
			}
			
		    //var regexTag21= new RegExp("\s*selected\s*","g");
			var resaultList = syInsInof.match(aregexTag1); 
			if(resaultList != null){ 
				for (var i = 0; i < resaultList.length; i++){	
					var resinfo = new String(resaultList[i]);
					if(resinfo.indexOf("name=\"sdinformationbnfList[0].bnfSex\"")==-1
							&& resinfo.indexOf("type=\"radio\"")==-1
							&& resinfo.indexOf("type=radio")==-1
							&& resinfo.indexOf("id=favoreeSex_0")==-1
							&& resinfo.indexOf("id=\"favoreeSex_0\"")==-1
					 
					){
						syInsInof = syInsInof.replace(resaultList[i],resaultList[i].replace(regexTag11,"value=\"\""));
					}
		
				}
				syInsInof = syInsInof.replace(regexTag21,"");
			}
			
            var regexTag3= new RegExp("<b\\s*[nametitle2_0][^>]*>([^>]*)</b>","i");
			var resaultList2 = tInsInof.match(regexTag3);
			if(resaultList2 != null){
				tInsInof = tInsInof.replace(resaultList2[0],resaultList2[0].replace(resaultList2[1],""));
			} 
		
		};
	
	}; 
	
	jQuery('#addfavoree').click( function() {  
		if(!verifyInput2()){return false;}; 
    /*添加受益人之前判断受益人个数是否超过N个*/
		    var realNum = 0;
		    var benefitb_num = jQuery("#benefitb_num").val(); //限制受益人数量
		 for (var s = 0; s <= parseInt(syInsNum) + 1; s++) {
        		if (jQuery('#favoreeRelation_' + s).length > 0) {
            	realNum = realNum + 1;
        		}
   		 }
   	  		if (realNum >= benefitb_num) { 
   	  					art.dialog.alert("受益人数量不能大于"+benefitb_num);
   	  	
   	  			return false; 
   	  		}

			var syFlag = true;
			 syInsNum = syInsNum+1; 
			jQuery('.bbr_boxs-syr: dt a').each(function(){ 
					 jQuery(this).live('click',function(){
			 			if ( jQuery(this).parent().siblings('dd').is(":visible")==false) {
								    jQuery(this).parent().siblings().show();
						            jQuery(this).text('收起');
						            jQuery(this).removeClass('kg_jia');
						            jQuery(this).siblings(".bxr-up-data").hide();
						            jQuery(this).parent().parent().removeClass('bbr_bor');
								}else{
						             jQuery(this).parent().siblings().hide();
						            jQuery(this).text('打开');
						            jQuery(this).addClass('kg_jia');
						            jQuery(this).siblings(".bxr-up-data").show();
						            jQuery(this).parent().parent().addClass('bbr_bor');

								}
			 			})
					});
				jQuery("div[id^='favoreeinsurance_']:last").find(":input[type!=hidden][type!=radio]").each(function(){
				 //校验信息
				 if(!favoreedefaltValidate(jQuery(this),jQuery(this).val())){ 
				 	  syFlag = false;
				 	  return false; 
				 	}; 
				  });
				if(!syFlag){return false;} 

				/*当新增一个受益人时，之前录入的受益人是“收起状态”*/
			jQuery('.bbr_boxs-syr:last dt').find('a').each(function(){
				  jQuery(this).parent().siblings().hide(); 
		  		  jQuery(this).text("打开");
		  		  jQuery(this).addClass("kg_jia");
		  		  jQuery(this).siblings(".bxr-up-data").show();
		  		  jQuery(this).parent().parent().addClass("bbr_bor"); 
			});
			
			

			var sex1 = jQuery("input[name='sdinformationbnfList[0].bnfSex']:checked").val();

				/*复制受益人信息*/
			var tsyInsInof = syInsInof.replace(/\[0\]/g, "["+syInsNum+"]");
			tsyInsInof = tsyInsInof.replace(/_0/g, "_"+syInsNum)
		
			/*复制受益人人信息*/ 
			jQuery("#addfavoreeins").before("<dl id='ins2_"+syInsNum+"' class='clearfix bbr_boxs-syr bbr_bor'>"+syInsInof+"</dl>");
			/*替换与Name相关*/ 
			jQuery("input[name=sdinformationbnfList["+syInsNum+"].bnfSex][value="+sex1+"]").attr("checked",'checked');
			jQuery("#ins2_"+syInsNum).html(jQuery("#ins2_"+syInsNum).html().replace(/\[0\]/g, "["+syInsNum+"]"));
			/*替换与ID相关*/ 
			jQuery("#ins2_"+syInsNum).html(jQuery("#ins2_"+syInsNum).html().replace(/_0/g, "_"+syInsNum));
			/*替换所有默认禁用的属性值*/
			jQuery("#ins2_"+syInsNum).html(jQuery("#ins2_"+syInsNum).html().replace(/disabled=""/g,""));
			
			jQuery("input[name=sdinformationbnfList[0].bnfSex][value="+sex1+"]").attr("checked",'checked');
	
			jQuery("#ins2_"+syInsNum).find(".bxr-up-data").hide();
			FavoreechangeInformation("","");
			/*统计显示受益人个数*/
			/*在没有删除受益人的情况下增加受益人 tInsLastNum！=0 表示已经删除过受益人了*/ 
		

			if(syInsLastNum!=0){
				jQuery("#ins2_"+syInsNum).find(".bxr_num").html(syInsLastNum+".");
				jQuery("#ins2_"+syInsNum).find(".favoreeOrder").val(syInsLastNum);
				syInsLastNum = syInsLastNum+1; 
			}else{
				jQuery("#ins2_"+syInsNum).find(".bxr_num").html(syInsNum+1+".");
				jQuery("#ins2_"+syInsNum).find(".favoreeOrder").html(syInsNum+1);
				syInsLastNum = syInsNum+2; 
			}
		



			/*添加打开与收起事件*/
			 //jQuery('.bbr_boxs:last dt a').live('click',function(){
				  jQuery('.bbr_boxs-syr:last dt a').toggle(function() {  
					  jQuery(this).parent().siblings().hide(); 
			  		  jQuery(this).text("打开");
			  		  jQuery(this).addClass("kg_jia");
			  		  jQuery(this).siblings(".bxr-up-data").show();
			  		  jQuery(this).parent().parent().addClass("bbr_bor");   
			  		if(syInsLastNum!=0){ 
						jQuery("#nametitle2_0").html(jQuery("#favoreeName_0").val());
						jQuery("#nametitle2_"+(syInsNum)).html(jQuery("#recognizeeName2_"+(syInsNum)).val()); 
					}else{
						jQuery("#ins2_"+syInsNum).find(".bxr_num").html(syInsNum+1+".");
						jQuery("#ins2_"+syInsNum).find(".favoreeOrder").val(syInsNum+1);
						jQuery("#nametitle2_"+(syInsNum)).html(jQuery("#recognizeeName2_"+(syInsNum)).val()); 
					}
				  },
				  function() {  
				     jQuery(this).parent().siblings().show();  
				     jQuery(this).text("收起");    
				     jQuery(this).removeClass("kg_jia");
				     jQuery(this).siblings(".bxr-up-data").hide();
					 jQuery(this).parent().parent().removeClass("bbr_bor"); 
				  }); 
					
				  	 /*添加删除事件*/
				  jQuery('.bbr_boxs-syr dt em').each(function(){
					 jQuery(this).unbind("click").click(function() {
				     //jQuery('.bbr_boxs:last dt em').click(function() { 
				     	var objthis = jQuery(this);

				     	art.dialog({
				     		    icon: 'warning',
							    id: 'alertmes',
							    content: '确定要删除此受益人信息吗?',
							    button: [
							        {
							            name: '确认',
							            callback: function () {

							              	if( jQuery('.bbr_boxs-syr').length>1){
													objthis.parent().parent().remove();
													syInsLastNum = syInsLastNum-1;
													syInsPartNum= syInsLastNum; 
												    //被保人序号显示  
													var j = (syInsPartNum-1);
													jQuery("dl[id^=ins2_]").each(function(){ 
														jQuery(this).find(".bxr_num").html(syInsPartNum-j+".");
														jQuery(this).find(".favoreeOrder").val(syInsPartNum-j);
														   j=j-1;
														   
													   }); 
												}else{
														art.dialog.alert("最后一个受益人不能删除");
													return;
													 }
							                return true;
							            },
							            focus: true
							        },
							        {
							            name: '关闭'
							        }
							    ]
							});


					 });
			       });
				 

	})


/*
 * 受益人与被保人关系
 */
	
	
function FavoreechangeInformation(objid,aa){ 
	if(objid!=null && objid!="" && typeof(objid) != "undefined"){
	        var tValue="";
	        var _id="";  
	        var insuredindex="";
			var relation_id = objid;
			if(relation_id.indexOf("_")!=-1){
			    _id = "_"+relation_id.split("_")[1];
			    insuredindex = relation_id.split("_")[1];
			} 
			
			tValue=jQuery("#"+relation_id+"  option:selected").text(); 
			
			if(tValue.indexOf("本人")!=-1){  
				//jQuery("#favrelationFlag"+_id).val("Y");
				//var reId = jQuery("#favrelationFlag"+_id).attr("id"); 
					
				if(jQuery("select[id^='favoreeRelation']").length!=1){
					
					var favoreeRelationFlag = false;
					jQuery("[id^='favoreeRelation']").each(
							 function(){
								 var tempId = jQuery(this).attr('id');
								 if(relation_id == tempId){
									 return;
								 }
								 var tempText = jQuery(this).find("option:selected").text(); 
								 if(tempText.indexOf("本人")!=-1){
									 favoreeRelationFlag = true;
									 return false;
								 }
					})
					 
					if(favoreeRelationFlag){
						jQuery("#"+relation_id).val("-1");
						jQuery("#"+relation_id).find("option[text^='请选择']").attr("selected",true);
						if(!jQuery("#"+relation_id+"").parent().children("label").is('i')){
							jQuery("#"+relation_id+"").parent().children("label").next("i").remove();
							jQuery("#"+relation_id+"").parent().children("label").after(jQuery("<i  style='color:red;font-size:12.5px;'>已添加本人为受益人，请勿重复添加</i>"));
							return false;
						} 
						}else{ 
						
						//如果被保险人选择本人则将投保人信息代入受益人信息
						var insFlag = jQuery("#relationFlag_0").val();
						if(insFlag=="Y"){
							jQuery("#favoreeName"+_id+"").val(jQuery("#applicantName").val());
							jQuery("#favoreeTypeId"+_id).val(jQuery("#applicantTypeId option:selected").val());
							jQuery("#favoreeTypeId"+_id).find("option[text^='"+jQuery("#applicantTypeId option:selected").text()+"']").attr("selected",true);
							jQuery("#favoreeId"+_id+"").val(jQuery("#applicantId").val());
							var sexvalue = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
							jQuery("input[name=sdinformationbnfList["+_id+"].favoreeSex][value="+sexvalue+"]").attr("checked",'checked');
							jQuery("#favoreeBirthday"+_id+"").val(jQuery("#applicantBirthday").val());
							jQuery("#favoreeMail"+_id+"").val(jQuery("#applicantMail").val());
							jQuery("#favoreeMobile"+_id+"").val(jQuery("#applicantMobile").val());
							jQuery("#favoreeZipCode"+_id+"").val(jQuery("#applicantZipCode").val());
							jQuery("#favoreeEnName"+_id+"").val(jQuery("#applicantEnName").val());
							
							jQuery("#favoreeEndID"+_id+"").attr("disabled","");
							jQuery("#Checkzjnum_long"+_id+"").attr("checked","");
							jQuery("#favoreeStartID"+_id+"").val(jQuery("#applicantStartID").val());
							jQuery("#favoreeEndID"+_id+"").val(jQuery("#applicantEndID").val());
							// 针对证件有效期勾选长期特殊处理
							if(jQuery("#applicantEndID").val()==""&&jQuery("#applicantEndID").attr("disabled")){
								jQuery("#favoreeEndID"+_id+"").attr("disabled","disabled");
								jQuery("#favoreeEndID"+_id+"").removeAttr("verify");
								jQuery("#reCheckzjnum_long"+_id+"").attr("checked","checked");
							}
							jQuery("#favoreeAddress"+_id+"").val(jQuery("#applicantAddress").val());
	
						}else{
							// jQuery("#insHidden").hide();
							jQuery("#favoreeName"+_id+"").val(jQuery("#recognizeeName_0").val());
							jQuery("#favoreeTypeId"+_id).val(jQuery("#recognizeeTypeId_0 option:selected").val());
							jQuery("#favoreeTypeId"+_id).find("option[text^='"+jQuery("#recognizeeTypeId_0 option:selected").text()+"']").attr("selected",true);
							jQuery("#favoreeId"+_id+"").val(jQuery("#recognizeeId_0").val());
							var sexvalue = jQuery("input[name='sdinformationinsuredList[0].recognizeeSex']:checked").val();
							jQuery("input[name=sdinformationbnfList["+_id+"].bnfSex][value="+sexvalue+"]").attr("checked",'checked');
							jQuery("#favoreeBirthday"+_id+"").val(jQuery("#recognizeeBirthday_0").val());
							jQuery("#favoreeMail"+_id+"").val(jQuery("#recognizeeMail_0").val());
							jQuery("#favoreeMobile"+_id+"").val(jQuery("#recognizeeMobile_0").val());
							jQuery("#favoreeZipCode"+_id+"").val(jQuery("#recognizeeZipCode_0").val());
							jQuery("#favoreeEnName"+_id+"").val(jQuery("#recognizeeEnName_0").val());
							
							jQuery("#favoreeEndID"+_id+"").attr("disabled","");
							jQuery("#Checkzjnum_long"+_id+"").attr("checked","");
							jQuery("#favoreeStartID"+_id+"").val(jQuery("#recognizeeStartID_0").val());
							jQuery("#favoreeEndID"+_id+"").val(jQuery("#recognizeeEndID_0").val());
							// 针对证件有效期勾选长期特殊处理
							if(jQuery("#recognizeeEndID_0").val()==""&&jQuery("#recognizeeEndID_0").attr("disabled")){
								jQuery("#favoreeEndID"+_id+"").attr("disabled","disabled");
								jQuery("#favoreeEndID"+_id+"").removeAttr("verify");
								jQuery("#reCheckzjnum_long"+_id+"").attr("checked","checked");
							}
							jQuery("#favoreeAddress"+_id+"").val(jQuery("#recognizeeAddress_0").val());
							
							
						}
					}
			}else{
				var insFlag = jQuery("#relationFlag_0").val();
				if(insFlag=="Y"){
					jQuery("#favoreeName"+_id+"").val(jQuery("#applicantName").val());
					jQuery("#favoreeTypeId"+_id).val(jQuery("#applicantTypeId option:selected").val());
					jQuery("#favoreeTypeId"+_id).find("option[text^='"+jQuery("#applicantTypeId option:selected").text()+"']").attr("selected",true);
					jQuery("#favoreeId"+_id+"").val(jQuery("#applicantId").val());
					var sexvalue = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
					jQuery("input[name=sdinformationbnfList["+_id+"].favoreeSex][value="+sexvalue+"]").attr("checked",'checked');
					jQuery("#favoreeBirthday"+_id+"").val(jQuery("#applicantBirthday").val());
					jQuery("#favoreeMail"+_id+"").val(jQuery("#applicantMail").val());
					jQuery("#favoreeMobile"+_id+"").val(jQuery("#applicantMobile").val());
					jQuery("#favoreeZipCode"+_id+"").val(jQuery("#applicantZipCode").val());
					jQuery("#favoreeEnName"+_id+"").val(jQuery("#applicantEnName").val());
					
					jQuery("#favoreeEndID"+_id+"").attr("disabled","");
					jQuery("#Checkzjnum_long"+_id+"").attr("checked","");
					jQuery("#favoreeStartID"+_id+"").val(jQuery("#applicantStartID").val());
					jQuery("#favoreeEndID"+_id+"").val(jQuery("#applicantEndID").val());
					// 针对证件有效期勾选长期特殊处理
					if(jQuery("#applicantEndID").val()==""&&jQuery("#applicantEndID").attr("disabled")){
						jQuery("#favoreeEndID"+_id+"").attr("disabled","disabled");
						jQuery("#favoreeEndID"+_id+"").removeAttr("verify");
						jQuery("#reCheckzjnum_long"+_id+"").attr("checked","checked");
					}
					jQuery("#favoreeAddress"+_id+"").val(jQuery("#applicantAddress").val());

				}else{
					// jQuery("#insHidden").hide();
					jQuery("#favoreeName"+_id+"").val(jQuery("#recognizeeName_0").val());
					jQuery("#favoreeTypeId"+_id).val(jQuery("#recognizeeTypeId_0 option:selected").val());
					jQuery("#favoreeTypeId"+_id).find("option[text^='"+jQuery("#recognizeeTypeId_0 option:selected").text()+"']").attr("selected",true);
					jQuery("#favoreeId"+_id+"").val(jQuery("#recognizeeId_0").val());
					var sexvalue = jQuery("input[name='sdinformationinsuredList[0].recognizeeSex']:checked").val();
					jQuery("input[name=sdinformationbnfList["+_id+"].bnfSex][value="+sexvalue+"]").attr("checked",'checked');
					jQuery("#favoreeBirthday"+_id+"").val(jQuery("#recognizeeBirthday_0").val());
					jQuery("#favoreeMail"+_id+"").val(jQuery("#recognizeeMail_0").val());
					jQuery("#favoreeMobile"+_id+"").val(jQuery("#recognizeeMobile_0").val());
					jQuery("#favoreeZipCode"+_id+"").val(jQuery("#recognizeeZipCode_0").val());
					jQuery("#favoreeEnName"+_id+"").val(jQuery("#recognizeeEnName_0").val());
					
					jQuery("#favoreeEndID"+_id+"").attr("disabled","");
					jQuery("#Checkzjnum_long"+_id+"").attr("checked","");
					jQuery("#favoreeStartID"+_id+"").val(jQuery("#recognizeeStartID_0").val());
					jQuery("#favoreeEndID"+_id+"").val(jQuery("#recognizeeEndID_0").val());
					// 针对证件有效期勾选长期特殊处理
					if(jQuery("#recognizeeEndID_0").val()==""&&jQuery("#recognizeeEndID_0").attr("disabled")){
						jQuery("#favoreeEndID"+_id+"").attr("disabled","disabled");
						jQuery("#favoreeEndID"+_id+"").removeAttr("verify");
						jQuery("#reCheckzjnum_long"+_id+"").attr("checked","checked");
					}
					jQuery("#favoreeAddress"+_id+"").val(jQuery("#recognizeeAddress_0").val());
					
					
				}
			}
					
			}else{
				//jQuery("#insHidden").show();
				//jQuery("#favrelationFlag"+_id).val("N");
				//jQuery("#recognizeeName"+_id+"").val("");
				//jQuery("#recognizeeId"+_id+"").val("");
				//jQuery("#recognizeeBirthday"+_id+"").val("");
				//jQuery("#recognizeeMail"+_id+"").val("");
				//jQuery("#recognizeeMobile"+_id+"").val("");
				//jQuery("#recognizeeZipCode"+_id+"").val("");
				//jQuery("#recognizeeAddress"+_id+"").val("");
				//jQuery("#recognizeeEnName"+_id+"").val("");
			}; 
	}
}

/*验证受益人比例*/
  		function verifyFavoreeRatio(){
  			    var earnings = 0;
				jQuery("input[id^=favoreeRatio_]").each(function () {
					earnings += parseInt(jQuery(this).val());
                });
                if(earnings!=100){ art.dialog.alert('受益人受益比例相加不满足100，请修改受益人受益比例') 
                	 return false;
                }
                return true;
  		}




// 为受益人的确定按钮绑定验证事件
	jQuery('.bbr-remes-btn').live('click', function () {
				var tFlag = true;
				if (!verifyInput2()) {
					tFlag = false
				}
				jQuery(this).parent().parent().find(':input[type!=hidden][type!=radio]').each(function () {
				if (jQuery(this).attr('id').indexOf('favoreeName') != - 1) {
				jQuery('#favoreenametitle_' + (jQuery(this).attr('id').split('_') [1])).html(jQuery(this).val())
				}
				}); 
				if (tFlag == false) {
						return false; 
				}
				jQuery(this).parent().parent().siblings("dt").children("a").trigger("click");
	})

	 jQuery('.bbr_boxs-syr dt a').each(function () {
        jQuery(this).toggle(function () {
            jQuery(this).parent().siblings().hide();
            jQuery(this).text('打开');
            jQuery(this).addClass('kg_jia');
            jQuery(this).siblings(".bxr-up-data").show();
            jQuery(this).parent().parent().addClass('bbr_bor');
        }, function () {
            jQuery(this).parent().siblings().show();
            jQuery(this).text('收起');
            jQuery(this).removeClass('kg_jia');
            jQuery(this).siblings(".bxr-up-data").hide();
            jQuery(this).parent().parent().removeClass('bbr_bor');
        })
    });


	     jQuery('.bbr_boxs-syr:first dt em').unbind('click').click(function () {
                   	var objthis = jQuery(this);
				     	art.dialog({
				     		    icon: 'warning',
							    id: 'alertmes',
							    content: '确定要删除此受益人信息吗?',
							    button: [
							        {
							            name: '确认',
							            callback: function () {

							              	if( jQuery('.bbr_boxs-syr').length>1){
													objthis.parent().parent().remove();
													syInsLastNum = syInsLastNum-1;
													syInsPartNum= syInsLastNum; 
												    //被保人序号显示  
													var j = (syInsPartNum-1);
													jQuery("dl[id^=ins2_]").each(function(){ 
														   objthis.find(".bxr_num").html(syInsPartNum-j+".");
														   objthis.find(".favoreeOrder").val(syInsPartNum-j);
														   j=j-1;
														   
													   }); 
												}else{
														art.dialog.alert("最后一个收益人不能删除");
													return;
													 }
							                return true;
							            },
							            focus: true
							        },
							        {
							            name: '关闭'
							        }
							    ]
							});

                    });


/*判断是否显示指定受益人*/
	 jQuery("input[name*='benefitOperate']").click( function(){
	 	if(jQuery(this).attr("checked")==true&&jQuery(this).val()==1){
					jQuery('#benefitb_box').show();
				} else{
					jQuery('#benefitb_box').hide();
	}})


	
	// 初始化绑定删除按钮的事件
function bindclose(){

		  	 /*添加删除事件*/
				  jQuery('.bbr_boxs-syr dt em').each(function(){
					 jQuery(this).unbind("click").click(function() {
				     //jQuery('.bbr_boxs:last dt em').click(function() { 
				     	var objthis = jQuery(this);

				     	art.dialog({
				     		    icon: 'warning',
							    id: 'alertmes',
							    content: '确定要删除此受益人信息吗?',
							    button: [
							        {
							            name: '确认',
							            callback: function () {

							              	if( jQuery('.bbr_boxs-syr').length>1){
													objthis.parent().parent().remove();

													syInsLastNum = syInsLastNum-1;
													syInsPartNum= syInsLastNum; 
												    //被保人序号显示  
													var j = (syInsPartNum-1);
													

													jQuery("dl[id^=ins2]").each(function(){ 
														   var s = syInsPartNum-j;
														   jQuery(this).find(".bxr_num").html(s +".");
														   jQuery(this).find(".favoreeOrder").val(s);
														   j=j-1;
														   
													   }); 
												}else{
														art.dialog.alert("最后一个受益人不能删除");
													return;
													 }
							                return true;
							            },
							            focus: true
							        },
							        {
							            name: '关闭'
							        }
							    ]
							});


					 });
			       });

}
// 初始化修改按钮为隐藏状态
function bingshow(){
			jQuery('.bbr_boxs-syr').find('dd').each(function(){
				if ( jQuery(this).is(":visible")==false) {
				     jQuery(this).siblings('dt').find(".bxr-up-data").hide();
				}else{
			  		  jQuery(this).siblings('dt').find(".bxr-up-data").show();

				}
				
			});
}

/**
 * 增加受益人时的校验
 * @param tObj
 * @param tVal
 * @returns {Boolean}
 */
function favoreedefaltValidate(tObj,tVal)
{
	if(tVal==null || tVal=="" || tVal=="null" ||tVal==-1){
			if(!tObj.parent().parent().children("label").is('i')){
				//针对证件有效期是长期的，无需校验日期的非空情况
				if((tObj.next().attr("id").indexOf("reCheckzjnum_long")!=-1)&&(tObj.next().attr("checked")==true)){
					return  true;
				}
					tObj.parent().parent().children("label").next("i").remove();
					tObj.parent().parent().children("label").after(jQuery("<i class=\"yz_mes_error\">输入内容有误！</i>"));
					return false;
			} ;
		tObj.parent().parent().parent().parent().find("dd").show();  
		tObj.parent().parent().parent().parent().find("a").text("收起");
		tObj.parent().parent().parent().parent().find("i").hide();  

		tObj.parent().parent().parent().parent().find("a").removeClass("kg_jia");
		tObj.parent().parent().parent().parent().parent().removeClass("bbr_bor");
		tObj.parent().parent().parent().parent().find("a").each(function(){ 
			jQuery(this).toggle(function(){  
				jQuery(this).parent().siblings().hide(); 
		  		  jQuery(this).text("打开");
		  		  jQuery(this).addClass("kg_jia");
		  		jQuery(this).siblings(".bxr-up-data").show();
		  		  jQuery(this).parent().parent().addClass("bbr_bor");
	
			  		if(tInsLastNum!=0){ 
						jQuery("#favoreenametitle_0").html(jQuery("#favoreeName_0").val());
						jQuery("#favoreenametitle_"+(tInsNum)).html(jQuery("#favoreeName_"+(tInsNum)).val()); 
					}else{
						jQuery("#ins2_"+tInsNum).find(".bxr_num").html(tInsNum+1+".");
						jQuery("#ins2_"+tInsNum).find(".favoreeOrder").val(tInsNum+1);
						jQuery("#favoreenametitle_"+(tInsNum)).html(jQuery("#favoreeName_"+(tInsNum)).val()); 
					}
			  },
			  function() { 
				 jQuery(this).parent().siblings().show();  
			     jQuery(this).text("收起");    
			     jQuery(this).removeClass("kg_jia");
			     jQuery(this).siblings(".bxr-up-data").hide();
				 jQuery(this).parent().parent().removeClass("bbr_bor"); 
			
				  
			  });
		});
		return false;
	}
	return true;
	
}
	 
jQuery('.bxr-up-data').live('click', function() {
	jQuery(this).siblings("a").click();
});


jQuery('.bbr-mes-btn').live('click', function() {
	var tFlag = true;
	
	if(!verifyInput2()){return false;}; 
	
	jQuery(this).parent().parent().find(":input[type!=hidden][type!=radio]").each(function(){
	 
		if(jQuery(this).attr("id").indexOf("recognizeeName") != -1){
			jQuery("#nametitle_"+(jQuery(this).attr("id").split("_")[1])).html(jQuery(this).val()); 
		}
		 
	});
	
	
	
	if (tFlag == false) {
		return false; 
	}
	
	
	
	jQuery(this).parent().parent().siblings("dt").children("a").trigger("click");
	
});


(function($){
	  $.fn.autoMail = function(options){
	    var opts = $.extend({}, $.fn.autoMail.defaults, options);
	    return this.each(function(){
	      var $this = $(this);
	      var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
	      
	      var top = $this.offset().top + $this.height() + 6;
	      var left = $this.offset().left;
	      var $mailBox = $('<div id="mailBox" style="top:'+top+'px;left:'+left+'px;width:'+$this.width()+'px"></div>');
	      $('body').append($mailBox);
	      
	      //设置高亮li
	      function setEmailLi(index){
	        $('#mailBox li').removeClass('cmail').eq(index).addClass('cmail');
	      }
	      //初始化邮箱列表
	      var emails = o.emails;
	      var init = function(input){
	        //取消浏览器自动提示
	        input.attr('autocomplete','off');
	        //添加提示邮箱列表
	        if(input.val()!=""){
	          var emailList = '<p>请选择邮箱类型</p><ul>';
	          for(var i = 0; i < emails.length; i++) {
	            emailList += '<li>'+input.val()+'@'+emails[i]+'</li>';
	          }
	          emailList += '</ul>';
	          $mailBox.html(emailList).show(0);
	        }else{
	          $mailBox.hide(0);
	        }
	        //添加鼠标事件
	        $('#mailBox li').hover(function(){
	          $('#mailBox li').removeClass('cmail');
	          $(this).addClass('cmail');
	        },function(){
	          $(this).removeClass('cmail');
	        }).click(function(){
	          input.val($(this).html());
	          $mailBox.hide(0);
	        });
	      }
	      //当前高亮下标
	      var eindex = -1;
	      //监听事件
	      $this.focus(function(){
	        if($this.val().indexOf('@') == -1){
	          init($this);
	        }else{
	          $mailBox.hide(0);
	        }
	      }).blur(function(){
	        setTimeout(function(){
	          $mailBox.hide(0);
	        },1000);//
	      }).keyup(function(event){
	        if($this.val().indexOf('@') == -1){
	          //上键
	          if(event.keyCode == 40){
	            eindex ++;
	            if(eindex >= $('#mailBox li').length){
	              eindex = 0;
	            }
	            setEmailLi(eindex);
	          //下键
	          }else if(event.keyCode == 38){
	            eindex --;
	            if(eindex < 0){
	              eindex = $('#mailBox li').length-1;
	            }
	            setEmailLi(eindex);
	          //回车键
	          }else if(event.keyCode == 13){
	            if(eindex >= 0){
	              $this.val($('#mailBox li').eq(eindex).html());
	              $mailBox.hide(0);
	            }
	          }else{
	            eindex = -1;
	            init($this);
	          }
	        }else{
	          $mailBox.hide(0);
	        }
	      //如果在表单中，防止回车提交
	      }).keydown(function(event){
	        if(event.keyCode == 13){
	          return false;
	        }
	      });
	    });
	  }
	  $.fn.autoMail.defaults = {
	    emails:[]
	  }
	})(jQuery);

	jQuery(document).ready(function(){
	  jQuery('#applicantMail').autoMail({
	    emails:['qq.com','163.com','126.com','hotmail.com','sina.com','139.com','gmail.com','sohu.com']
	  });
	   
	  // 详细页传购买份数时直接赋值
	  if(jQuery("#insMult").html()=="0"||jQuery("#insMult").html()==undefined || jQuery("#insMult").html()=="" ){
	      jQuery("#insMult").html(jQuery("#recognizeeMul_0").val());
	  }
	  
	  // 初始化判断有提示的文本框内是否有文字 有则去掉提示框
	  jQuery(".pos-tb-p input").each(function(index, el) {
	  		 if (jQuery(el).val()!="") {
	                 jQuery(el).parent().siblings(".app_mobile").hide();
	         }else{
	             jQuery(el).parent().siblings(".app_mobile").show();
	         }
	  });
	});
	
	// 点击邮件提示输入后 出发邮箱失去焦点以验证邮箱是否正确。
	jQuery("#mailBox li").live('click', function() {
	        jQuery("#applicantMail").blur();
	});

	jQuery(".pos-tb-p input").focus(
	      function(){
	          jQuery(this).parent().siblings(".app_mobile").hide();
	      })



	jQuery(".pos-tb-p input").blur( function () { 
	         if (jQuery(this).val()!="") {
	                 jQuery(this).parent().siblings(".app_mobile").hide();
	         }else{
	             jQuery(this).parent().siblings(".app_mobile").show();
	             
	         }
	      });
	//理财险特有提示
	jQuery(".tap_name").blur(function(){
        if(jQuery(this).siblings(".yz_mes_yes").length>0){
          jQuery(this).siblings(".other_tip").show();
        }else{
          jQuery(this).siblings(".other_tip").hide();
        }
   })
	//将投保人姓名带入到续期缴费信息模块
	jQuery("#applicantName").bind('input propertychange', function() {
	    if(jQuery("#zhm_name")){
	       jQuery("#zhm_name").html(jQuery(this).val());
	    }
	})

function selecttrave(){
	var mddlist=jQuery("#city_list li");
	if(jQuery("#trave_dss").length > 0 && mddlist.length == 0){
			jQuery("#mdd_con").html("<i class='yz_mes_error'>请选择旅游目的地</i>");
			jQuery("html, body").animate({scrollTop:jQuery("#trave_dss").offset().top});
			return false;
	}else{
		return true
	}
}


jQuery(document).ready(function($) {

	var renewalobj = jQuery("#renewal_sel");
	var renewalhtml = jQuery("#renewal_box p:not(:first)").clone(); //克隆元素
	//初始续期缴费元素
	if (renewalobj.length!=0 && jQuery("#renewal_sel option:selected").val()=="N"){
		jQuery("#renewal_box .form p:not(:first)").remove();
	}
	//根据元素值控制元素
	renewalobj.change(function(){
		var checkValue = $(this).val();
		if(checkValue=="N"){
			jQuery("#renewal_box .form p:not(:first)").remove();
		}else{
			jQuery("#renewal_box .form p").after(renewalhtml);
		}
	})

	moreQuicklist();

});


jQuery("#class_sel_rideo").click(function(){
	 if (jQuery('#lookclassDiv').length > 0 && !jQuery('#lookclassDiv').is(':hidden')) {
       var s = jQuery('#sel_zys2').is(':checked');
       if (!s) {
           if (jQuery('#sel_zys2').parent().siblings('.yz_mes_error').length == 0) {
               jQuery('#sel_zys2').parent().parent().append('<em class=\'yz_mes_error\'>请选择您是否为可投保职业，以免对您的理赔造成影响</em>')
           }
           jQuery('html, body').animate({
               scrollTop: jQuery('#radiobox').offset().top
           });
           return false
       }else{
       	jQuery("#lookclassDiv .yz_mes_error").remove();
       	var downlink = jQuery(this).attr("data-url");
		    window.open(downlink);
       }
   }else{
   	jQuery("#lookclassDiv .yz_mes_error").remove();
       var downlink = jQuery(this).attr("data-url");
		window.open(downlink);
   }

})
/*
*常用投保人信息增加换一换
 */
function moreQuicklist(){
	var i=1;
	jQuery(".cy_user_ch").each(function(index, el) {
		var clickId = '#quick_listbtn_' + i;
		var quick_listbtn_onclick='<em class="quick_listbtn" id ="quick_listbtn_'  +i +'">换一换</em>';
		var quickNUm = jQuery(el).find("label").length;
		if(quickNUm>5){
			var qulistmone  = jQuery(el).find("label:lt(5)");
			var qulistmtwo  = jQuery(el).find("label:gt(4)");
			qulistmone.show();
			qulistmtwo.hide();
			if(jQuery(clickId).length<=0){
				jQuery(el).parent().append(quick_listbtn_onclick);
			}		
			if($(clickId).attr("click")){
				jQuery(clickId).die("click")
			}
			jQuery(clickId).live("click",function(){
				if(qulistmone.is(":hidden")){
					qulistmone.show();
					qulistmtwo.hide();
				}else{
					qulistmone.hide();
					qulistmtwo.show();
				}
			})
		}
		i++;
	});
}



/* 
 * 不送积分时，页面积分不显示
 */
function whenIntegralZero(productIntegralValue){
	if(productIntegralValue=='0'){
		if(jQuery(".at_list li").length==1){
			jQuery(".at-desp").hide();
			if(jQuery(".integral_hj").html()=='')   {
				jQuery("#inspageone_activityTD").hide();
			} else{
				jQuery("#inspageone_activityTD").show();
			}
		}else{
			jQuery("#inspageone_activityTD").show();
			jQuery(".at_list li:first").hide();
		}
	}else{
		jQuery("#inspageone_activityTD").show();
		jQuery(".at-desp").show();
		jQuery(".at_list li:first").show();;
	}
}


/* 
 * 当明天是生日时
 */
function whenTomorrowBirthday(buyers,recognizeeRelation_ID,tomorrowIsBirthday){
	deltimeout_tipAll();//清空投保人信息的生日提示
	if(buyers=="1"){//被保人选本人的投保人信息
		var applicantTypeId = jQuery("#applicantTypeId").find("option:selected").text();
		if(applicantTypeId=="身份证"){//身份证
			if(tomorrowIsBirthday=='Y' && jQuery("#applicantIDBirthdaytimeout").length <=0 ){
				jQuery("#applicantId").after("<em class='timeout_tip' id='applicantIDBirthdaytimeout'>超过24点未支付再次投保保费将会变更。</em>");
			}
		}else{//生日
			if(tomorrowIsBirthday=='Y' && jQuery("#applicantBirthdaytimeout").length <=0 ){ 
				jQuery("#applicantBirthday").after("<em class='timeout_tip' id='applicantBirthdaytimeout'>超过24点未支付再次投保保费将会变更。</em>");
			}
		}
		if(jQuery("#mulInsuredFlag").val()!="rid_me" ){
			hidetimeout_tip();
		}
	}else if(buyers=="2"){
		deltimeout_tipAll2(recognizeeRelation_ID);
		var applicantTypeId = jQuery("#recognizeeTypeId"+recognizeeRelation_ID).find("option:selected").text();
		if(applicantTypeId=="身份证"){//身份证
			if(tomorrowIsBirthday=='Y' && jQuery("#insuranceIDBirthdaytimeout"+recognizeeRelation_ID).length <=0 ){
				jQuery("#recognizeeId"+recognizeeRelation_ID).after("<em class='timeout_tip' id='insuranceIDBirthdaytimeout"+recognizeeRelation_ID+"'>超过24点未支付再次投保保费将会变更。</em>");
			}
		}else{//生日
			if(tomorrowIsBirthday=='Y' && jQuery("#insuranceBirthdaytimeout_"+recognizeeRelation_ID).length <=0 ){
				jQuery("#recognizeeBirthdayID"+recognizeeRelation_ID).parent().after("<em class='timeout_tip'  id='insuranceBirthdaytimeout"+recognizeeRelation_ID+"' >超过24点未支付再次投保保费将会变更。</em>");
			}
		}
	}
	
}

function deltimeout_tip(timeout){
	if(jQuery("#"+timeout).length > 0 ){
		jQuery("#"+timeout).remove();
	}
}
function deltimeout_tipAll(){//清空投保人信息的生日提示
	deltimeout_tip("applicantIDBirthdaytimeout");
	deltimeout_tip("applicantBirthdaytimeout");
}
function deltimeout_tipAll2(id){//清空投保人信息的生日提示
	deltimeout_tip("insuranceIDBirthdaytimeout"+id);
	deltimeout_tip("insuranceBirthdaytimeout"+id);
}

function hidetimeout_tip(){
	if(jQuery("#applicantIDBirthdaytimeout").length > 0 ){//身份证
		if(jQuery("#mulInsuredFlag").val()!="rid_me"){
			jQuery("#applicantIDBirthdaytimeout").hide();
		}else{
			jQuery("#applicantIDBirthdaytimeout").show();
		}
	}
	if(jQuery("#applicantBirthdaytimeout").length > 0 ){//生日
		if(jQuery("#mulInsuredFlag").val()!="rid_me"){
			jQuery("#applicantBirthdaytimeout").hide();
		}else{
			jQuery("#applicantBirthdaytimeout").show();
		}
	}
}

function deltimeoutIDError(idtype){
	if("applicantTypeId"==idtype){//投保人
		deltimeout_tipAll();
	}else{//被保人
		var _id = "_"+idtype.split("_")[1];
		deltimeout_tipAll2(_id);
	}
}
