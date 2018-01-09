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
					jQuery("#insuredInfoDIV").hide();
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
						url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
						dataType: "json",
						async: false,
						success: function(data) {
								var obj = eval(data);
								fillin(obj);
							}
						}); 
				}else if("rid_orther"==myradio.get(i).id){
					//被保人快速录入隐藏
					if(insuredLenFlag){
						jQuery("#insuredInfoDIV").show();
					}
					tInsLastNum=parseInt(jQuery("dl[id^=ins_]:last").find(".bxr_num").html())+1;
					jQuery("select[id^='recognizeeMul']").each(function(){
						jQuery(this).val("1");
					});
					jQuery("#recognizeeOperate").val("2");
					 jQuery('.bbr_boxs:first dt a').toggle(function(){  
						  jQuery(this).parent().siblings().hide(); 
				  		  jQuery(this).text("打开");
				  		  jQuery(this).addClass("kg_jia");
				  		//金融会所
							selectIframe();
				  		  jQuery(this).parent().parent().addClass("bbr_bor");
				  		  jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val()); 
					  },
					  function() { 
					     jQuery(this).parent().siblings().show();  
					     jQuery(this).text("收起");    
					     jQuery(this).removeClass("kg_jia");
						 jQuery(this).parent().parent().removeClass("bbr_bor");
						//金融会所
						    selectIframe();
					  });  
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
											alert("最后一个被保险人不能删除"); 
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
							url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
							dataType: "json",
							async: false,
							success: function(data) {
									var obj = eval(data);
									fillin(obj);
								}
							}); 
				}else{
					jQuery("#recognizeeOperate").val("3");
					jQuery("#impValadate").val("N");
					//被保人快速录入隐藏
					jQuery("#insuredInfoDIV").hide();
				} 
			
			}
	}
	//金融会所
	selectIframe();
	
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
			if(!tObj.parent().children("label").is('i')){
				tObj.parent().children("label").next("i").remove();
				tObj.parent().children("label").after(jQuery("<i class=\"yz_mes_error\">输入内容有误！</i>"));
				return false;
			} ;
		tObj.parent().parent().parent().parent().find("dd").show();  //不包括投、被保人信息快速录入
		tObj.parent().parent().parent().parent().find("a").text("收起");   
		//金融会所
	    selectIframe();
		tObj.parent().parent().parent().parent().find("a").removeClass("kg_jia");
		tObj.parent().parent().parent().parent().parent().removeClass("bbr_bor");
		tObj.parent().parent().parent().parent().find("a").each(function(){ 
			jQuery(this).toggle(function(){  
				jQuery(this).parent().siblings().hide(); 
		  		  jQuery(this).text("打开");
		  		  jQuery(this).addClass("kg_jia");
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
				 jQuery(this).parent().parent().removeClass("bbr_bor"); 
				//金融会所
				    selectIframe();
				  
			  });
		});
		return false;
	}
	return true;
	
}
jQuery(function(){ 
	
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
		jQuery("#recognizeePrem_0").val("");
		jQuery("#recognizeeBirthdayID_0").html(""); 
	};  
	jQuery('#addIns1').click( function() {  
			var tFlag = true;
			tInsNum = tInsNum+1;  
			jQuery('.bbr_boxs: dt a').each(function(){ 
				jQuery(this).toggle(function(){  
					 jQuery(this).parent().siblings().show();  
				     jQuery(this).text("收起");    
				     jQuery(this).removeClass("kg_jia");
					 jQuery(this).parent().parent().removeClass("bbr_bor"); 
					//金融会所
					selectIframe();
				  },
				  function() { 
					  jQuery(this).parent().siblings().hide(); 
			  		  jQuery(this).text("打开");
			  		  jQuery(this).addClass("kg_jia");
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
		  		  jQuery(this).parent().parent().addClass("bbr_bor"); 
		  		//金融会所
				    selectIframe();
			});
			var sex1 = jQuery("input[name='sdinformationinsuredList[0].recognizeeSex']:checked").val();
			/*复制被保人信息*/
			
			var ttInsInof = tInsInof.replace(/\[0\]/g, "["+tInsNum+"]");
			ttInsInof = ttInsInof.replace(/_0/g, "_"+tInsNum)
			jQuery("#addIns").before("<dl id='ins_"+tInsNum+"' class='clearfix bbr_boxs bbr_bor'>"+ttInsInof+"</dl>");
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
									alert("最后一个被保险人不能删除");
									return;
									 }
							} 
					 });
			       });
			//金融会所
		    selectIframe();
			calPrem();
		}); 

	
	/**
	 * Ajax提交Form表单
	 */
	jQuery("#confirm").click(function(){
		if(jQuery("#effective").val()==null||jQuery("#effective").val()==''){
			alert("请输入起保日期");
			jQuery("#effective").focus();
			return false;
		}
		if(!verifyInput2()){return false;}; 
		/*针对投被保人的证件类型是“身份证”的时候校验*/ 
		if(jQuery("#mulInsuredFlag").val()=="rid_orther"){
			if(!checkAppInsID()){return false;}  
		}else if(jQuery("#mulInsuredFlag").val()=="rid_me" || jQuery("#relationIsSelf").val()=="Y"){ 
			if(!validateRec()){return false;};
		}else if(jQuery("#mulInsuredFlag").val()=="rid_td"){ 
			if(!checkAppID()){return false;}
			if(jQuery("#impValadate").val()!="Y"){
				jQuery("#importMessage").focus();
				jQuery("#importMessage").html("请重新上传被保人信息!");
				jQuery("#importMessage").show();
				jQuery("#importMessage").attr("style", "overflow:hidden;color:red");
				return false;
			}
		};
		if(!verifyInput2()){return false;}; 
	 	jQuery.blockUI({
			overlayCSS:{backgroundColor:'#fff',opacity:0.7},
			showOverlay:true,
			message:"<table border=0 cellspacing=0 cellpadding=0><tr><td><span>正在为您保存订单，请稍候</span></td></tr><tr><td><img width='170' height='15' title='正在处理，请稍等' src='"+sinosoft.jsPath+"/template/shop/images/loading-bars.gif'/></td></tr></table>"
			 
		}); 
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
				var turl= sinosoft.base + "/order_config_new!saveOrder.action?recoJson="+recoJson+"&productId="+productId;
				
				if("update"==status){
				    turl= sinosoft.base + "/order_config_new!orderUpdate.action?recoJson="+recoJson+"&productId="+productId;
					
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
						jQuery.unblockUI();
						var tOrderSn = data.OrderSn;
						var tFlag = data.Flag;
						var orderId = data.OrderId;
						var kid = data.kid; 
						var orderFlag = data.orderFlag;
						if(tFlag=="Err"){
							jQuery.unblockUI();
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
							jQuery.unblockUI(); 
							window.location.href=sinosoft.base + "/order_config_new!sendDirectUrl.action?orderSn="+tOrderSn+"&orderId="+orderId+"&Flag="+tFlag+"&KID="+kid+"&orderFlag="+orderFlag;
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
								jQuery.unblockUI();
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
								jQuery.unblockUI(); 
								window.location.href=sinosoft.base + "/order_config_new!sendDirectUrl.action?orderSn="+tOrderSn+"&orderId="+orderId+"&Flag="+tFlag+"&KID="+kid+"&orderFlag="+orderFlag;
							}
				 		} catch (e) {
				 			jQuery.unblockUI(); 
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
			if(jQuery("#impValadate").val()!="Y"){
				jQuery("#importMessage").focus();
				jQuery("#importMessage").html("请重新上传被保人信息!");
				jQuery("#importMessage").show();
				jQuery("#importMessage").attr("style", "overflow:hidden;color:red");
				return false;
			}
		}; 
		document.getElementById("orderStatus").value="temptorysave";
		
		if(!verifyInput2()){ 
			return false;
		}; 
		jQuery.blockUI({
			overlayCSS:{backgroundColor:'#fff',opacity:0.7},
			
			message:"<table border=0 cellspacing=0 cellpadding=0><tr><td><span>正在为您保存订单，请稍候</span></td></tr><tr><td><img width='170' height='15' title='正在处理，请稍等' src='"+sinosoft.jsPath+"/template/shop/images/loading-bars.gif'/></td></tr></table>"
			 
		});
		
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
		var turl= sinosoft.base + "/order_config_new!saveOrder.action?recoJson="+recoJson+"&productId="+productId;
		if("update"==status){
		    turl= sinosoft.base + "/order_config_new!orderUpdate.action?recoJson="+recoJson+"&productId="+productId;
			
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
				jQuery.unblockUI();
				var tOrderSn = data.OrderSn;
				var tFlag = data.Flag;
				var orderId = data.OrderId; 
				var kid = data.kid;
				if(tFlag=="Err"){
					jQuery.unblockUI();
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
					jQuery.unblockUI();
					status = data.status;
					updateOrderId = data.OrderId;
					var orderSn = data.OrderSn;
					alert("暂存操作成功！");
					window.location.href=sinosoft.base + "/order_config_new!buyNowUpdate.action?orderSn="+orderSn+"&orderId="+updateOrderId+"&Flag=Suc&KID="+kid;
					
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
			 			
		 				jQuery.unblockUI();
						var tOrderSn = data.OrderSn;
						var tFlag = data.Flag;
						var orderId = data.OrderId; 
						if(tFlag == "Err"){
							jQuery.unblockUI();
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
							jQuery.unblockUI();
							status = data.status;
							updateOrderId = data.OrderId;
							var orderSn = data.OrderSn;
							var kid = data.kid;
							alert("暂存操作成功！");
							window.location.href=sinosoft.base + "/order_config_new!buyNowUpdate.action?orderSn="+orderSn+"&orderId="+updateOrderId+"&Flag=Suc&KID="+kid;
							
						}
			 		} catch (e) {
			 			jQuery.unblockUI(); 
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
	jQuery("#applicantBirthday").focus(function(){
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
					alert("请输入起保日期");
					return false;
				}
				if(brithday!=null && brithday!=""){
				jQuery.ajax({
					url: sinosoft.base+"/shop/order_config_new!ajaxAge.action?applicantBirthday="+brithday+"&productId="+productId+"&effdate="+effective,
					dataType: "json",
					type:"GET",
					async: true,
					success: function(data) {
						if(!data){
							return false;
						}else{
							jQuery.ajax({
								url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
			dataType: "json",
			async: false,
			success: function(data) {
				    var obj = eval(data);
					fillin(obj);
				}
			});
		}); 

	/*确认投保后弹窗合保效果*/
	 
	jQuery("#qrgm_pay").click(function(){  
		 var agreeInsure =  document.getElementById("agreeInsure");
			 if(!agreeInsure.checked){
			 alert("请确认您同意了以上投保声明！ ");
			 return ;
		 }
		/*是否需要核保*/  
		if(needUWCheckFlag=="1"){
			/*需要核保*/ 
			jQuery.blockUI({
				overlayCSS:{backgroundColor:'#fff',opacity:0.7}, 
				message:"<div><span>正在为您进行核保操作，请稍候</span><br/><img width='170' height='15' title='正在核保，请稍等' src='"+sinosoft.jsPath+"/template/shop/images/loading-bars.gif'/></div>"
				 
			});
			jQuery.getJSON( 
				sinosoft.base+"/shop/order_config_new!checkInsurePay.action?orderSn="+orderSn+"&callback=?",
				function(data) { 
					var obj = eval(data);
					var passFlag = obj.passFlag;
					var tMessage = obj.rtnMessage; 
					var tKID = obj.KID;
				    if(obj){  
						if(passFlag=="pass"){
							jQuery.unblockUI();
							window.location.href=sinosoft.base + "/order_config_new!pay.action?orderSn="+orderSn+"&orderId="+orderId+"&KID="+tKID;
						}else{
							jQuery.unblockUI();
							/*核保错误提示*/ 
							pay_error(orderSn,productId,tMessage,tKID);	 
						} 
				      } 
					});
			 
		}else{
			jQuery.blockUI({
				overlayCSS:{backgroundColor:'#fff',opacity:0.7}, 
				message:"<div><span>正在处理，请稍候</span><br/><img width='170' height='15' title='正在处理，请稍等' src='"+sinosoft.jsPath+"/template/shop/images/loading-bars.gif'/></div>"
				 
			});
			window.location.href=sinosoft.base + "/order_config_new!pay.action?orderSn="+orderSn+"&orderId="+orderId;
		}
	});
	
	/**
	 * 健康告知"下一步"
	 */
	jQuery("#bt_health").click(function(){  
		 jQuery.blockUI({
			overlayCSS:{backgroundColor:'#fff',opacity:0.7},
			message:"<div><span>正在为您保存健康告知信息，请稍候</span><br/><img width='170' height='15' title='正在核保，请稍等' src='"+sinosoft.jsPath+"/template/shop/images/loading-bars.gif'/></div>"
		});  
		if(!sure()){
			jQuery.unblockUI(); 
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
			jQuery.unblockUI();
			var tOrderSn = data.OrderSn;
			var tFlag = data.Flag;
			var orderId = data.OrderId; 
			var orderFlag = data.orderFlag;
			if(tFlag=="Err"){
				jQuery.unblockUI();
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
				jQuery.unblockUI(); 
				updateOrderId = data.OrderId;
				var orderSn = data.OrderSn;
				var kid = data.kid;
				window.location.href=sinosoft.base + "/order_config_new!sendDirectUrl.action?orderSn="+data.OrderSn+"&orderId="+data.OrderId+"&Flag=Suc"+"&KID="+kid+"&orderFlag="+orderFlag;
				
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
			window.location.href=sinosoft.base + "/order_config_new!buyNowUpdate.action?orderSn="+orderSn+"&orderId="+orderId+"&Flag=Suc&orderFlag="+orderFlag;
	});
	
	jQuery('.bbr_boxs dt a').each(function(){  
			jQuery(this).toggle(function(){   
				  jQuery(this).parent().siblings().hide(); 
				  jQuery(this).text("打开");
				  jQuery(this).addClass("kg_jia");
				  jQuery(this).parent().parent().addClass("bbr_bor");  
				  //金融会所
				  selectIframe();
			  },
			  function() { 
			     jQuery(this).parent().siblings().show();  
			     jQuery(this).text("收起");    
			     jQuery(this).removeClass("kg_jia");
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
						alert("最后一个被保险人不能删除"); 
					 } 
			} 
		});
	});
	if ((null != jQuery("#uploadify")) &&("" != jQuery("#uploadify")) &&  ("undefined" != typeof (jQuery("#uploadify")))) {
		jQuery("#uploadify").uploadify({ 
		       'swf': sinosoft.base+'/wwwroot/kxb/style/shop/images/uploadify.swf',
		       'uploader':sinosoft.base+'/shop/order_config_new!importBatch.action',   
		       'buttonImage':sinosoft.base+'/wwwroot/kxb/style/shop/images/xz_tbale.gif',
		       'buttonAfterImage':sinosoft.base+'/wwwroot/kxb/style/shop/images/xz_tbale2_03.gif',  
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
		            var peoples=eval('('+data +')');
		            if(peoples.state=="0"){
		            	importSuccess(peoples,peoples.successMsg);
		            }else if(peoples.state=="1"){
		            	importError(peoples,peoples.errorMsg);
		            }else{
		            	importGError(peoples.errorMsg);
		            }
		            selectIframe();
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
});
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
function importSuccess(peoples,successMsg){
	 var returnList = peoples.insuredList;
	 var a_prem = 0;
	 deleteTableRow();
	 if(returnList!=null){
		 for(var i=0;i<returnList.length;i++){
      var info ="<tr>"
			   +" <td scope='col'>"+(i+1)+"</td>"
			   +" <td scope='col'>"+returnList[i].recognizeeName+"<input type='hidden' id='sdrecognizeeName_"+i+"' name='sdinsuredList["+i+"].recognizeeName' value='"+returnList[i].recognizeeName+"'/></td>"
			   +" <td scope='col'>"+returnList[i].recognizeeAppntRelationName+"<input type='hidden' id='sdrecognizeeRelationName_"+i+"' name='sdinsuredList["+i+"].recognizeeAppntRelationName' value='"+returnList[i].recognizeeAppntRelationName+"'/></td>"
			   +" <td scope='col'>"+returnList[i].recognizeeIdentityTypeName+"<input type='hidden' id='sdrecognizeeTypeName_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityTypeName' value='"+returnList[i].recognizeeIdentityTypeName+"'/></td>"
			   +" <td scope='col'>"+returnList[i].recognizeeIdentityId+"<input type='hidden' id='sdrecognizeeId_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityId' value='"+returnList[i].recognizeeIdentityId+"'/></td>"
			   +" <td scope='col'>"+returnList[i].recognizeeBirthday+"<input type='hidden' id='sdrecognizeeBirthday_"+i+"' name='sdinsuredList["+i+"].recognizeeBirthday' value='"+returnList[i].recognizeeBirthday+"'/></td>"
			   +" <td scope='col'>"+returnList[i].recognizeeSexName+"<input type='hidden' id='sdrecognizeeSexName_"+i+"' name='sdinsuredList["+i+"].recognizeeSexName' value='"+returnList[i].recognizeeSexName+"'/></td>"
			   +" <td scope='col'>完成</td>"
			   +" <td scope='col'>"+returnList[i].discountPrice+"<input type='hidden' id='sddiscountPrice_"+i+"' name='sdinsuredList["+i+"].discountPrice' value='"+returnList[i].discountPrice+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeePrem+"<input type='hidden' id='sdrecognizeePrem_"+i+"' name='sdinsuredList["+i+"].recognizeePrem' value='"+returnList[i].recognizeePrem+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeTotalPrem+"<input type='hidden' id='sdrecognizeeTotalPrem_"+i+"' name='sdinsuredList["+i+"].recognizeeTotalPrem' value='"+returnList[i].recognizeeTotalPrem+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeIdentityType+"<input type='hidden' id='recognizeeTypeId_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityType' value='"+returnList[i].recognizeeIdentityType+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeAppntRelation+"<input type='hidden' id='sdrecognizeeRelation_"+i+"' name='sdinsuredList["+i+"].recognizeeAppntRelation' value='"+returnList[i].recognizeeAppntRelation+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeSex+"<input type='hidden' id='sdrecognizeeSex_"+i+"' name='sdinsuredList["+i+"].recognizeeSex' value='"+returnList[i].recognizeeSex+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeMobile+"<input type='hidden' id='recognizeeMobile_"+i+"' name='sdinsuredList["+i+"].recognizeeMobile' value='"+returnList[i].recognizeeMobile+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].destinationCountry+"<input type='hidden' id='recognizeedestinationCountry_"+i+"' name='sdinsuredList["+i+"].destinationCountry' value='"+returnList[i].destinationCountry+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].destinationCountryText+"<input type='hidden' id='recognizeedestinationCountryText_"+i+"' name='sdinsuredList["+i+"].destinationCountryText' value='"+returnList[i].destinationCountryText+"'/></td>"
			   +" <td style='display:none;' scope='col'>"+returnList[i].flightNo+"<input type='hidden' id='recognizeeflightNo_"+i+"' name='sdinsuredList["+i+"].flightNo' value='"+returnList[i].flightNo+"'/></td>"
               +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeEnName+"<input type='hidden' id='recognizeeEnName_"+i+"' name='sdinsuredList["+i+"].recognizeeEnName' value='"+returnList[i].recognizeeEnName+"'/></td>";
			   if(returnList[i].flightTime!=null && returnList[i].flightTime!=''){
				   info=info  +" <td style='display:none;' scope='col'>"+returnList[i].flightTime+"<input type='hidden' id='recognizeeflightTime_"+i+"' name='sdinsuredList["+i+"].flightTime' value='"+returnList[i].flightTime+"'/></td>";
			   }
     info=info +" <td style='display:none;' scope='col'>"+returnList[i].flightLocation+"<input type='hidden' id='recognizeeflightLocation_"+i+"' name='sdinsuredList["+i+"].flightLocation' value='"+returnList[i].flightLocation+"'/></td>"
			   +"</tr>";
			 jQuery("#insuredtab").append(info);  
		 }
     }
	 jQuery("#insuedlistDiv").show();
	 if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=peoples.proTolPrem;
		 //document.getElementById("discountPrice_").innerHTML=obj.retTotlePrem;
		 jQuery("#productTotalPrice").attr("value",peoples.disPrem); 
	 }
	 jQuery("#totalAmount").val(peoples.disPrem);
	 jQuery("#payPrice").val(peoples.insuredPrem);
	 jQuery("#productTotalPrice").attr("value",peoples.proTolPrem);
	 document.getElementById("priceTotle_1").innerHTML=peoples.insuredPrem;
	 document.getElementById("priceTotle").innerHTML=peoples.insuredPrem; 
	 document.getElementById("priceTotle_").innerHTML=peoples.insuredPrem; 
	 document.getElementById("priceTotle_2").innerHTML=peoples.insuredPrem;

	 jQuery("#importMessage").html(successMsg);
	 jQuery("#importMessage").show();
	 jQuery("#insNum").html(returnList.length);
	 jQuery("#insMult").html(returnList.length);
	 jQuery("#insMult_1").html(returnList.length);
	 if(returnList.length>=1){
		 jQuery("#impValadate").val("Y");//是否有错误信息，"Y":没有； "N"：有
	 }
	 
}
function importError(peoples,errorMsg){
	var returnList = peoples.errorList;
	 var a_prem = 0;
	 deleteTableRow();
	 if(returnList!=null){
		 for(var i=0;i<returnList.length;i++){
			 jQuery("#insuredtab").append("<tr>"
					   +" <td scope='col'>"+(i+1)+"</td>"
					   +" <td scope='col'>"+returnList[i].recognizeeName+"<input type='hidden' id='sdrecognizeeName_"+i+"' name='sdinsuredList["+i+"].recognizeeName' value='"+returnList[i].recognizeeName+"'/></td>"
					   +" <td scope='col'>"+returnList[i].recognizeeAppntRelationName+"<input type='hidden' id='sdrecognizeeRelationName_"+i+"' name='sdinsuredList["+i+"].recognizeeAppntRelationName' value='"+returnList[i].recognizeeAppntRelationName+"'/></td>"
					   +" <td scope='col'>"+returnList[i].recognizeeIdentityTypeName+"<input type='hidden' id='sdrecognizeeTypeName_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityTypeName' value='"+returnList[i].recognizeeIdentityTypeName+"'/></td>"
					   +" <td scope='col'>"+returnList[i].recognizeeIdentityId+"<input type='hidden' id='sdrecognizeeId_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityId' value='"+returnList[i].recognizeeIdentityId+"'/></td>"
					   +" <td scope='col'>"+returnList[i].recognizeeBirthday+"<input type='hidden' id='sdrecognizeeBirthday_"+i+"' name='sdinsuredList["+i+"].recognizeeBirthday' value='"+returnList[i].recognizeeBirthday+"'/></td>"
					   +" <td scope='col'>"+returnList[i].recognizeeSexName+"<input type='hidden' id='sdrecognizeeSexName_"+i+"' name='sdinsuredList["+i+"].recognizeeSexName' value='"+returnList[i].recognizeeSexName+"'/></td>"
					   +" <td scope='col'>完成</td>"
					   +" <td scope='col'>"+returnList[i].discountPrice+"<input type='hidden' id='sddiscountPrice_"+i+"' name='sdinsuredList["+i+"].discountPrice' value='"+returnList[i].discountPrice+"'/></td>"
					   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeePrem+"<input type='hidden' id='sdrecognizeePrem_"+i+"' name='sdinsuredList["+i+"].recognizeePrem' value='"+returnList[i].recognizeePrem+"'/></td>"
					   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeTotalPrem+"<input type='hidden' id='sdrecognizeeTotalPrem_"+i+"' name='sdinsuredList["+i+"].recognizeeTotalPrem' value='"+returnList[i].recognizeeTotalPrem+"'/></td>"
					   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeIdentityType+"<input type='hidden' id='recognizeeTypeId_"+i+"' name='sdinsuredList["+i+"].recognizeeIdentityType' value='"+returnList[i].recognizeeIdentityType+"'/></td>"
					   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeAppntRelation+"<input type='hidden' id='sdrecognizeeRelation_"+i+"' name='sdinsuredList["+i+"].recognizeeAppntRelation' value='"+returnList[i].recognizeeAppntRelation+"'/></td>"
					   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeSex+"<input type='hidden' id='sdrecognizeeSex_"+i+"' name='sdinsuredList["+i+"].recognizeeSex' value='"+returnList[i].recognizeeSex+"'/></td>"
					   +" <td style='display:none;' scope='col'>"+returnList[i].recognizeeMobile+"<input type='hidden' id='recognizeeMobile_"+i+"' name='sdinsuredList["+i+"].recognizeeMobile' value='"+returnList[i].recognizeeMobile+"'/></td>"
					   +"</tr>");  
		 }
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
	 document.getElementById("priceTotle_").innerHTML=peoples.insuredPrem; 
	 document.getElementById("priceTotle_2").innerHTML=peoples.insuredPrem;

	 jQuery("#importMessage").html(errorMsg);
	 
	 jQuery("#importMessage").show();
	 jQuery("#importMessage").attr("style", "overflow:hidden;color:red");
	 jQuery("#impValadate").val("N");//是否有错误信息，"Y":没有； "N"：有
}
function importGError(errorMsg){
	 
	 deleteTableRow();
	  
	 jQuery("#insuedlistDiv").show();
	 
	 jQuery("#importMessage").html(errorMsg);
	 jQuery("#importMessage").css("color","red");
	 jQuery("#importMessage").show();
	 jQuery("#impValadate").val("N");//是否有错误信息，"Y":没有； "N"：有
}
function deleteTableRow()
{
	jQuery("#insuredtab tr").eq(0).nextAll().remove();
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
	if (clsName == "wrap_gj_sx0") {
		var type1 = jQuery(".wrap_gj_sx0").find("a.selected").attr("id");
		if (type1 == "travelCountryTypeALL") {
			type1 = "";
		} else if (type1 == "travelCountryTypeCom") {
			type1 = "2";
		} else if (type1 == "travelCountryTypeSG") {
			type1 = "1";
		} else {
			return;
		}
		jQuery(".wrap_gj_sx").find("a").each(function(){
			var  type2 = jQuery(this).attr("id");
			if (type2 == "ALL" || type1 == "") {
				jQuery(this).show();
			} else {
				if (jQuery("#index_Country_Set").find(".css_Country_" + type2 + ".css_Country_" +type1).length > 0
						|| jQuery("#index_Country_Set").find(".css_Country_" + type2 + ".css_Country_12").length > 0
						|| jQuery("#index_Country_Set").find(".css_Country_0" + type2 + ".css_Country_" +type1).length > 0
						|| jQuery("#index_Country_Set").find(".css_Country_0" + type2 + ".css_Country_12").length > 0) {
					jQuery(this).show();
				} else {
					if (jQuery(this).attr("class") == "selected") {
						jQuery(".wrap_gj_sx").find("a[id='ALL']").click();
					}
					jQuery(this).hide();
				}
			}
		});
	}
	countryDisplay();
}
function countryDisplay() {
	var type1 = jQuery(".wrap_gj_sx0").find("a.selected").attr("id");
	var type2 = jQuery(".wrap_gj_sx").find("a.selected").attr("id");
	if (type1 == "travelCountryTypeALL") {
		type1 = "";
	} else if (type1 == "travelCountryTypeCom") {
		type1 = "2";
	} else if (type1 == "travelCountryTypeSG") {
		type1 = "1";
	} else {
		return;
	}
	jQuery("#index_Country_Set").find("li").each(function(){
		var cls2 = jQuery(this).attr("class").split(" ")[0];
		var cls1 = jQuery(this).attr("class").split(" ")[1];
		if (("css_Country_" + type1 == cls1 || "css_Country_12" == cls1 || type1 == "")
				&& ("css_Country_" + type2 == cls2 || "css_Country_0" + type2 == cls2 || type2 == "ALL")){
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
				jQuery.ajax({
					url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
				jQuery.ajax({
					url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
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
			url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+jQuery("#channelsn").val(),
			dataType: "json",
			async: false,
			success: function(data) {
				var obj = eval(data);  
				fillin(obj);
			}
		});
	 
}
/**
 * 保费试算后回调函数
 * @param obj
 */
function fillin(obj,cFlag){
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
			 }else if(jQuery("#mulInsuredFlag").val()=="rid_me"){
				 insuredCount = jQuery("#recognizeeMul"+_id).val();
			 }
			 if(insuredCount=="undefined" || insuredCount==null || insuredCount==""){
				 insuredCount="1";
			 }
			 a_prem = parseFloat(a_prem)+parseFloat(_disPrem)*parseFloat(insuredCount);
			 t_prem = parseFloat(t_prem)+parseFloat(_prem)*parseFloat(insuredCount);
			}
	 }
	 if(obj.insFlag!=null && obj.insFlag!="" && obj.insFlag!="undefined"){
		 var insFlag = obj.insFlag;
		 if(insFlag=="rid_td"){
			 a_prem = obj.retCountPrem;
			 t_prem = obj.retPrem;
		 }
	 }
	 if(jQuery("#relationIsSelf").val()=="Y"){
		 a_prem = parseFloat(obj.retCountPrem);
		 t_prem = parseFloat(obj.retPrem);
	 }
	 a_prem = a_prem.toFixed(2);
	 t_prem = t_prem.toFixed(2);
	 jQuery("#totalAmount").val(t_prem);
	 jQuery("#productTotalPrice").attr("value",obj.retTotlePrem);
	 jQuery("#payPrice").val(a_prem);
	 document.getElementById("priceTotle_1").innerHTML=a_prem;
	 document.getElementById("priceTotle").innerHTML=a_prem; 
	 document.getElementById("priceTotle_").innerHTML=a_prem; 
	 document.getElementById("priceTotle_2").innerHTML=a_prem;

}
/**
 * 封装投保要素
 * @returns
 */
function insuredElements(){
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
	  
	 var obj = "";
	 var BirthDay = "";
	 BirthDay = initAge();
	 var selfFlag = jQuery("#relationFlag_0").val();
	 if(selfFlag=="Y"){
		 BirthDay = jQuery("#applicantBirthday").val();
	 }
	 var period="";
	 if(document.getElementById("Ensure")){ 
	    period=document.getElementById("Ensure").value;
	 }
	 var sex="";
	 if(document.getElementById("recognizeeSex")){ 
	   sex=document.getElementById("recognizeeSex").value;
	 }
	 var mulPeople = "";
	 if(document.getElementById("mulPeople")){ 
		  mulPeople=document.getElementById("mulPeople").value;
		  mulPeople = encodeURI(encodeURI(mulPeople));
	 }

	 var o={"Plan":plan,"AppType":encodeURIComponent(appType),"FeeYear":feeYear,"Grade":encodeURIComponent(grade),"TextAge":BirthDay,"Period":encodeURIComponent(period),"Sex":encodeURIComponent(sex),"MulPeople":mulPeople};
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
	 var sex="";
	 if(document.getElementById("recognizeeSex")){ 
	 sex=document.getElementById("recognizeeSex").value;
	 }
	 var mulPeople = "";
	 /*适用人群*/
	 if(document.getElementById("mulPeople")){ 
		  mulPeople=document.getElementById("mulPeople").value;
	 }
	 var o={"Plan":plan,"AppType":encodeURIComponent(appType),"FeeYear":encodeURIComponent(feeYear),"Grade":encodeURIComponent(grade),"TextAge":BirthDay,"Period":encodeURIComponent(period),"Sex":encodeURIComponent(sex),"MulPeople":mulPeople};
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
	if(len>=1 || lent>=1){
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
					var arr =new Array();
					arr[0] = relation_id;
					arr[1] = bir;
					arr[2] = sex;
					arr[3] = insuredCount;
					jsoninf = jsoninf+i+jsoncen+arr;
			 });
		 }else{
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
					}
					if("Y"==jQuery("#relationFlag"+_id).val()){
						//var sex = jQuery("#applicantSex").val();
						var sex = jQuery("input[name='sdinformationAppnt.applicantSex']:checked").val();
						var bir = jQuery("#applicantBirthday").val();
					}else{
						//var sex = jQuery("#recognizeeSex"+_id+"").val();
						var sex = jQuery("input[name='sdinformationinsuredList["+insuredindex+"].recognizeeSex']:checked").val();
						var bir = jQuery("#recognizeeBirthday"+_id+"").val();
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
	 var arr =new Array();
	 arr[0] = relation_id;
	 arr[1] = bir;
	 arr[2] = sex; 
	 arr[3] = insuredCount;
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
						jQuery("#recognizeeStartID"+_id+"").val(jQuery("#applicantStartID").val());
						jQuery("#recognizeeEndID"+_id+"").val(jQuery("#applicantEndID").val());
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
							alert("请输入起保日期");
							return false;
						 }
						if(jQuery("#recognizeeBirthday"+_id).val()!=null&&jQuery("#recognizeeBirthday"+_id).val()!=""){
							if(!validateBirthday_1("recognizeeBirthday"+_id)){alert("您好，您输入的年龄不能购买本产品，请重新输入");return false;};
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
				jQuery.unblockUI();
				return false;
			},
			focus: true
		}],
	close: function () {
		  jQuery.unblockUI(); 
	  }
	});
}
/**
 * 初始化保费试算
 */
function initPrem(){
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
	var channelsn = jQuery("#channelsn").val();
	if (channelsn == '' || channelsn == null) {
		jQuery("#channelsn").val("wj");
		channelsn = "wj";
	}
	
	jQuery.ajax({
		type:'get',
		url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&recoJson="+recoJson+"&productId="+productId+"&effective="+effective+"&typeFlag="+typeFlag+"&channelsn="+channelsn,
		data:{orderSn:orderSn},
		dataType: "json",
		async: false,
		success: function(item) {
			var obj = eval(item);
			fillin(obj,"1");
		}
	});  
	rel();
	//被保人快速录入隐藏
	if(jQuery("#mulInsuredFlag").val()=="rid_orther"){
		jQuery("#insuredInfoDIV").show();
	}else{
		//但被保人需要显示
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
				getChildrenArea(Area1,'applicantArea2',Area2);
				getChildrenArea(Area2,'applicantArea3',Area3);
			}else{
				getChildrenArea(Area1,'applicantArea2',Area2);
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
				getChildrenArea(Area1,'recognizeeArea2'+tIndex,Area2);
				getChildrenArea(Area2,'recognizeeArea3'+tIndex,Area3);
			}else{
				getChildrenArea(Area1,'recognizeeArea2'+tIndex,Area2);
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
	   if ((child==null) || (typeof(child)=="undefined"))
	   {
		   return;
	   }

	   child.options.length=1;
	    if(value!="-1"){
		   jQuery.ajax({
		       type:'get',
			    url: sinosoft.base+"/shop/order_config_new!getChildernArea.action?id="+value,
			    dataType: "json",
			    success: function(item) {
			   	   if ((src!="")&&(src!=null) && (typeof(src)!="undefined"))
			   	   {
			       	   var nowValue = document.getElementById(src.id).value;
			       	   if (nowValue == value)
			       	   {
			       		   fillArea(item,flag,flagvalue);
			       	   }
			   	   }
			   	   else
			   	   {
			   		   fillArea(item,flag,flagvalue);
			   	   }
			   	   if(_id!="" && _id!=null && (typeof(src)!="undefined")){
			   		   jQuery("#recognizeeArea2"+_id).val(jQuery("#applicantArea2 option:selected").val());
			   		   jQuery("#recognizeeArea2"+_id).find("option[text^='"+jQuery("#applicantArea2 option:selected").text()+"']").attr("selected",true);
			   	   }

			    }
		    });
	   }
	}
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
	      			alert("请输入起保日期");
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
							url: sinosoft.base+"/shop/order_config_new!ajaxAge.action?applicantBirthday="+birthday+"&productId="+productId+"&effdate="+effdate,
							dataType: "json",
							type:"GET",
							async: false,
							success: function(data) {
								if(!data){
									alert("您好，您输入的年龄不在本产品承保范围内"); 
									jQuery("#"+bId).val("");
									tFlag = false;
									return false;
								}else{							
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
		url: sinosoft.base+"/shop/order_config_new!ajaxAge.action?applicantBirthday="+jQuery("#"+obj).val()+"&productId="+productId+"&effdate="+effdate,
		dataType: "json",
		type:"GET",
		async: false,
		success: function(data) {
			if(!data){ 
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
			if(str.substring(16,17)%2==0||str.substring(16,17)%2=='X'||str.substring(16,17)%2=='x'){  
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
			sex = jQuery("input[name="+sexName+"] :checked").val();
			if(cFlag!="1"){
				document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
				//document.getElementById(sexId).value = sex;
				//jQuery("input[name="+sexName+"][value="+sex+"]").attr("checked",'checked'); 
			} 
					
			if("applicantTypeId"==idtype){
				if(jQuery("#mulInsuredFlag").val()=="rid_me"){
					var brithday = document.getElementById("applicantBirthday").value;
					var effective = "";
					if(document.getElementById("effective")){
						effective = document.getElementById("effective").value;
					}
					if(brithday!=null && brithday!=""){
					jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxAge.action?applicantBirthday="+brithday+"&productId="+productId+"&effdate="+effective,
						dataType: "json",
						type:"GET",
						async: true,
						success: function(data) {
							if(data){
								calPrem();
							}else{
								return false;
							} 
						}
					});
					}
				}
			}else{
					var brithday = document.getElementById(birthdayId).value;
					var effective = "";
					if(document.getElementById("effective")){
						effective = document.getElementById("effective").value;
					}
					if(brithday!=null && brithday!=""){
					jQuery.ajax({
						url: sinosoft.base+"/shop/order_config_new!ajaxAge.action?applicantBirthday="+brithday+"&productId="+productId+"&effdate="+effective,
						dataType: "json",
						type:"GET",
						async: false,
						success: function(data) {
							if(data){
								calPrem();
							}else{
								return false;
							}
						}
					});
					}
				}
			
			
		}else{
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">一定要填写18位有效身份证号码哟，请仔细检查一下</i>"));
		 	}
			return false;
		} 
		/*2014-03-05 modify by cuishigang 投保录入页面添加逻辑：生日校验通过后，才进行保费试算
		 * if(jQuery("#mulInsuredFlag").val()=="rid_me"){
			dateTrigger(birthdayId);
		}*/
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
					return false; 
				}
			});
			if(tFlag){
				if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
				}
			}
		}
	}else if(idType_id == 2){
		//军官证、士兵证
		if(str.length2() <= 20){
				if(!(str.indexOf("字第") != -1 && str.indexOf("字第") > 0)){
					//不符合字第
					if(isSpecialCharacters(str,"2") || str.length < 4){
						errorMessage(idCode,"证件号码有误,格式错误!");
						return false;
					}
				}else{
					var tempStr = str.substr(str.indexOf("字第")+2,str.length);
					if(isSpecialCharacters(tempStr,"2") || tempStr.length < 4){
						errorMessage(idCode,"证件号码有误,格式错误!");
						return false;
					}
				}
		}else{
			errorMessage(idCode,"证件号码有误!格式错误!");
			return false;
		}
		if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
			jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
		}
		
	}else if(idType_id == 3){
		//护照-外国护照
		if(str.length2() < 3){
			errorMessage(idCode,"证件号码有误,格式错误!");
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
			return false;
		}else if(isSpecialCharacters(str,"1")){
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
				  return false;
			}
		}else{
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

function effchange(){
	var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("true"==protectionPeriodFlag){
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 var effective = document.getElementById("effective").value;
		 var temp = addDate(protectionPeriodTy,protectionPeriodLast,effective);
		 document.getElementById("fail").value ="";
		 document.getElementById("fail").value = temp;
		 document.getElementById("fail_").value ="";
		 document.getElementById("fail_").value = temp;
	 }
	    var start = jQuery('#effective').val();
		var end = jQuery('#fail').val();
		jQuery('#ensureDate').html(start+" 00时  至  "+end+" 24时");
}

function  addDate(type,NumDay,dtDate) { 
	 var   reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
		var   date_   =   new   Date( ); 
	 if (arr = dtDate.match(reg)) {
	      var yy = Number(arr[1]);
		  var mm = Number(arr[2]);
		  var dd = Number(arr[3]);
		  var all_ = mm + '/' + dd + '/' +yy;
		  date_ = new Date(all_);
	 } else {
		 var myDate = new Date();
	 }
	lIntval   =   parseInt(NumDay); 
	switch(type) { 
			case   'Y' : 
				date_.setYear(date_.getFullYear()   +   lIntval) ;
				date_.setDate(date_.getDate()  -  1) ;
			break; 
			
			case   'M': 
				date_.setMonth(date_.getMonth()   +   lIntval) ;
				date_.setDate(date_.getDate() - 1) ;
			break; 
			
			case   'D': 
				date_.setDate(date_.getDate()   +   lIntval) ;
				date_.setDate(date_.getDate() - 1) ;
			break ;
			 
	} 
	var mm = date_.getMonth() + 1;
	var dd = date_.getDate();
	if(mm < 10){
	    mm = '0' +mm;
	}
    if(dd < 10){
	    dd = '0' +dd;
	}
    
	return   date_.getFullYear()   + '-'   +   mm   +   '-'   + dd;
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
				jQuery("#applicantBirthday").click(function(){
					WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+appEndDate+'}',maxDate:'%y-%M-{%d-'+appStartDate+'}'});
				});
			}else{
				jQuery("#applicantBirthday").removeAttr("onclick");
				jQuery("#applicantBirthday").unbind("click").click(function() {
				});
				jQuery("#applicantBirthday").attr("readonly","readonly");
			}
		}else{
			jQuery("#applicantBirthday").click(function(){
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
function checkId(value, element) { 
	 var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
	 var iSum = 0;
	 var strIDno = value;
	 if(!/^\d{17}(\d|x)$/i.test(strIDno)&&!/^\d{15}$/i.test(strIDno)){
	     return false; //非法身份证号
	 }
	 if(aCity[parseInt(strIDno.substr(0,2))]==null){
	       return false;// 非法地区
	 }
	 
	     // 判断是否大于2078年，小于1900年
	     var year =strIDno.substring(6,10);
	     if (year<1900 || year>2078 ){ 
	         return false;//非法生日
	     }

	    //18位身份证处理

	   //在后面的运算中x相当于数字10,所以转换成a
	    strIDno = strIDno.replace(/x$/i,"a");

	  sBirthday=strIDno.substr(6,4)+"-"+Number(strIDno.substr(10,2))+"-"+Number(strIDno.substr(12,2));
	  var d = new Date(sBirthday.replace(/-/g,"/"));
	  if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
	         return false;//非法生日
	   }
	    // 身份证编码规范验证
	  for(var i = 17;i>=0;i --)
	   iSum += (Math.pow(2,i) % 11) * parseInt(strIDno.charAt(17 - i),11);
	  if(iSum%11!=1){
	      return false;// 非法身份证号
	   }
	   // 判断是否屏蔽身份证
	    var words = new Array();
	    words = new Array("11111119111111111","12121219121212121");

	    for(var k=0;k<words.length;k++){
	        if (strIDno.indexOf(words[k])!=-1){
	            return false;
	        }
	    }

	 return true;
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
		    				//保费试算
		    				//金融会所
							selectIframe();
		    				calPrem(); 
		    			}else{
		    				alert("最后一个被保险人不能删除");
		    				return;
		    				 }
		    		} 
	    		}
	    	}); 
	 }); 
}
function validateRec(){
	var t1=idcheck('applicantTypeId','applicantId','applicantBirthday','applicantSex',"1");
	if(!t1){jQuery('#applicantId').focus();return false;}
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
					alert("您好，此年龄不能购买本产品");  
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
			   newYear = berfore.split("D")[0];
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
				alert("没有查到该投保人的相关信息，请选择其他投保人或者直接录入投保人信息！");
			}else{
				alert(data.Emessage);
			}
		}
	});
}
/**
 * 快速查询投保人信息
 */
function quickQueryInsured(obj,insuredName){
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
				fillInsured(data,"input");
			}else if(data.message=="Fal"){
				alert("没有查到该被保人的相关信息，请选择其他被保人或者直接录入被保人信息！");
			}else{
				alert(data.Emessage);
			}
		}
	});
}
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
	jQuery("#quick_3").hide();
	jQuery("#quick_4").show();
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!quickInputInsured.action?supplierCode2="+companyCode+"&productId="+productId,
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			fillInsured(data,"init");
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
	jQuery("#quick_3").show();
	jQuery("#quick_4").hide();
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
             info = info+"<label> <input name='quickappnt' type='checkbox' onclick='quickQueryAppnt(this,this.value);' title='"+returnList[i].applicantName+"' value='"+returnList[i].applicantName+"' />"+returnList[i].applicantName+"</label>";			 
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
					 jQuery("#applicantSex").val(returnList[i].applicantSex);
					 jQuery("input[name=sdinformationAppnt.applicantSex][value="+returnList[i].applicantSex+"]").attr("checked",'checked'); 
				  }
				  if(returnList[i].socialSecurity!=null&&returnList[i].socialSecurity!=""){
					  jQuery("#socialSecurity").val(returnList[i].socialSecurity);	
				  }
			  }
			  if(returnList[i].applicantBirthday!=null&&returnList[i].applicantBirthday!=""){
				  jQuery("#applicantBirthday").val(returnList[i].applicantBirthday);	
			  }
			  if(returnList[i].applicantEndID!=null&&returnList[i].applicantEndID!=""){
				  jQuery("#applicantEndID").val(returnList[i].applicantEndID);	
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
			  }
			  if(returnList[i].applicantMobile!=null&&returnList[i].applicantMobile!=""){
				  jQuery("#applicantMobile").val(returnList[i].applicantMobile);	
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
								 jQuery("#applicantSex").val(data.appDictionaryCode);	
								 jQuery("input[name=sdinformationAppnt.applicantSex][value="+data.appDictionaryCode+"]").attr("checked",'checked'); 
							 }else{
								 jQuery.ajax({
										url: sinosoft.base+"/shop/order_config_new!ajaxGetCode.action?supplierCode2="+companyCode+"&codeType=Sex&codeName="+encodeURIComponent(encodeURIComponent("男")),
										dataType: "json",
										type:"GET", 
										async: false,
										success: function(data) {
											 var appCode1=data.appDictionaryCode;
											 jQuery("#applicantSex").val(appCode1);
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
	 }
	 initBirthday("applicantTypeId","applicantId");
	 calPrem();	
	 
}
/**
 * 投保人信息快速录入-复制投保人信息
 * @param peaples 后台返回数据
 * @param flag 操作标记 init:页面初始化，input:点击“Name”调用
 * @param flag 操作标记 01:产品匹配，02：保险公司匹配,03:全站匹配
 */
function fillInsured(peaples,flag){
	 
	 var returnList = peaples.relationrecognizee;
	 if(flag=="init"){
		 jQuery("#quick_4_1").html("");
		 var info = "";
		 for(var i=0;i<returnList.length;i++){
			 jQuery("#quick_4_1").html("");
             info = info+"<label> <input name='quickrecognizee' type='checkbox' onclick='quickQueryInsured(this,this.value);' title='"+returnList[i].recognizeeName+"' value='"+returnList[i].recognizeeName+"' />"+returnList[i].recognizeeName+"</label>";			 
		 }
		 if(returnList.length>=1){
			 insuredLenFlag = true;
			 var myradiochecked=jQuery("input[name='myradio']:checked");
			 if(myradiochecked.get(0).id=="rid_orther"){
				 jQuery("#insuredInfoDIV").show();
			 }
			 jQuery("#quick_4_1").show();
			 jQuery("#quick_4_2").show();
			 jQuery("#quick_4_1").append(info); 
		 }else{
			 insuredLenFlag = false;
			 jQuery("#insuredInfoDIV").hide();
			 jQuery("#quick_4_1").html("");
			 jQuery("#quick_4_1").hide();
			 jQuery("#quick_4_2").hide();
		 }
	 }else{
		 //去最后一个被保人、进行被保人快速录入操作
		 var insuredID = jQuery("input[id^='recognizeeName']:last").attr("id");
		 var index_insuredID = "_0";
		 if(insuredID!=null && insuredID!="null"){
			 index_insuredID = "_"+insuredID.split("_")[1];
		 }
		 var index_insured = index_insuredID.split("_")[1];
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
					  jQuery("#recognizeeSex"+index_insuredID).val(returnList[i].recognizeeSex);	
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
	 }
	 calPrem();	
	 
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
	
    if(event.keyCode==8){  
        var elem = event.srcElement;  
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
