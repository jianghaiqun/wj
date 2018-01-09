define(["jquery", "jquery_cookie", "jquery_form", "art_dialog", "header", "footer", "login", "wdate_picker", "validate"], function() {
    return {
        init: function() {
           if(jQuery('#renwealOne').length <= 0){
                // 公共参数
               this.param = {"lastEffective":""}
               // 绑定事件
               this.bindEvents();
               this.vallidatefrom();
               this.premcal();
           }
           this.iChoseProductPlanStartWith();
           this.vallidateReneFrom();
           this.textTip();
        },
        // 事件绑定
        bindEvents:function(){
            var _this = this;
            var param = _this.param;

            jQuery("#effective").blur(function(){
                if(this.value != param.lastEffective){
                    _this.effchange(this.value);
                }
                param.lastEffective = this.value;
            });

            jQuery("#lookInsureStatement").click(function(){
                art.dialog({
                    padding:'20px 25px',
                    content : document.getElementById("insureStatement"),
                    id : 'error_id',
                    title : '投保声明及必读',
                    width:600,
                    height:100
                });
            });

            jQuery("#agreeInsure").click(function(){
                var agreeInsure =  document.getElementById("agreeInsure");
                    if(agreeInsure.checked){
                         jQuery('#agreeInsure').parent().removeClass("red");
                    }
            });


        },
        // 表单校验
        vallidatefrom:function (){
            var _this = this;
            var param = _this.param;
            jQuery(document).wjDataVelify({
                tarArea : ".renewal_box_con",           //校验的DOM作用域
                tarElms : "input[data-verify]",     //校验的DOM元素集合
                sbtBtn : "#qrgm_pay",                 //提交按钮
                callBack : function() {             //回调函数
                    var agreeInsure =  document.getElementById("agreeInsure");
                    if(!agreeInsure.checked){
                         jQuery('#agreeInsure').parent().addClass("red");
                          art.dialog({
                            padding:'20px 25px',
                            content : "请仔细阅读投保须知和必读，<br>并勾选“我接受以上投保声明及必读内容”",
                            id : 'error_id',
                            title : '提示'
                        });
                        return ;
                    }
                    _this.shade_show();
                    jQuery("#msg_2_2").html("正在处理，请稍等");
                    jQuery("#msg_2").show();
                    jQuery("#msg_2_2").prev().show();
                    var turl= sinosoft.base + "/shop/order_config_new!saveOrder.action?recoJson="+param.recoJsonStr+"&productId="+param.productId;
                    turl = encodeURI(encodeURI(turl));  
                    var options = { 
                        url:turl, 
                        async:true,
                        type:"POST", 
                        data:{
                            supplierCode2:param.companyCode,
                            insureJson:param.insureJsonStr,
                            dutyJson:param.dutyJsonStr,
                            dutyDisReq:encodeURIComponent(param.dutyJsonDis),
                            dutyPremReq:encodeURIComponent(param.dutyJsonStr)
                        },
                        dataType: "json",
                        resetForm:false,
                        success: function(data){  
                            _this.shade_hide();
                            if(data.Flag == "Err"){
                                jQuery("#msg_2_2").prev().hide();
                                jQuery("#msg_2_2").html("<font style='color:red;'>" + data.Msg + "</font>");
                            }else{
                                var needUWCheckFlag = jQuery("#needUWCheckFlag").val();
                                /*是否需要核保*/  
                                if(needUWCheckFlag=="1"){
                                    /*需要核保*/ 
                                    jQuery("#msg_2_2").html("正在为您进行核保操作，请稍等");
                                    jQuery.getJSON( 
                                        sinosoft.base+"/shop/order_config_new!checkInsurePay.action?orderSn="+orderSn+"&callback=?",
                                        function(data) { 
                                            _this.hideUI();
                                            var obj = eval(data);
                                            var passFlag = obj.passFlag;
                                            var tMessage = obj.rtnMessage; 
                                            var tKID = obj.KID;
                                            if(obj){  
                                                if(passFlag=="pass"){
                                                    window.location.href=sinosoft.base + "/shop/order_config_new!pay.action?orderSn="+orderSn+"&orderId="+orderId+"&KID="+tKID;
                                                }else{
                                                    /*核保错误提示*/ 
                                                    pay_error(orderSn,productId,tMessage,tKID);  
                                                } 
                                              } 
                                            });
                                     
                                }else{
                                    window.location.href=sinosoft.base + "/shop/order_config_new!pay.action?orderSn="+data.OrderSn+"&orderId="+data.OrderId;
                                }
                            }
                        }, error:function(response, textStatus, errorThrown){
                            _this.shade_hide();
                            jQuery("#msg_2_2").prev().hide();
                            jQuery("#msg_2_2").html("订单保存失败");
                        }
                    }; 
                    jQuery('#orderInfoForm').ajaxSubmit(options); 

                }
            });
        },
        // 保费试算
        premcal:function(){
            var _this = this;
            var productId = jQuery("#productId").val();
            var effective = jQuery("#effective").val();
            var channelsn = "wj";
            var complicatedFlag = jQuery("#complicatedFlag").val();

            var insureJson = {};
            insureJson["Period"] = jQuery("#factor_Period").val();
            insureJson["Plan"] = jQuery("#factor_Plan").val();
            insureJson["Sex"] = jQuery("#factor_Sex").val();
            insureJson["TextAge"] = jQuery("#factor_TextAge").val();
            var insureJsonStr = JSON.stringify(insureJson);
            var insureJsonEncodeStr = encodeURIComponent(encodeURIComponent(insureJsonStr));

            var recoJson = {"1":"recognizeeRelation_0,"+jQuery("#factor_TextAge").val()+","+jQuery("#factor_Sex").val()+",1,"+jQuery("#recognizeeIdentityType").val()+","+jQuery("#recognizeeIdentityId").val()};
            var recoJsonStr = JSON.stringify(recoJson);

            var dutyJsonStr = jQuery("#factor_Dutys").val();

            _this.param.productId = productId;
            _this.param.companyCode = productId.substring(0,4);
            _this.param.insureJsonStr = insureJsonStr;
            _this.param.recoJsonStr = recoJsonStr;
            _this.param.complicatedFlag = complicatedFlag;
            _this.param.dutyJsonStr = dutyJsonStr;
            _this.param.effective = effective;
            _this.param.channelsn = channelsn;
            _this.param.dutyJsonDis =  jQuery("#factor_DutyDis").val();

            jQuery.ajax({
                url: sinosoft.base+"/shop/order_config_new!ajaxCounter.action?insureJson="+insureJsonEncodeStr+"&dutyJson="+dutyJsonStr+"&recoJson="+recoJsonStr
                +"&productId="+productId+"&effective="+effective+"&channelsn="+channelsn
                +"&complicatedFlag="+complicatedFlag,
                data:{},
                dataType: "json",
                async: false,
                success: function(data) {
                    var obj = eval(data);
                    var prem = obj.retTotlePrem;
                    var amount = obj.retCountPrem;
                    if (parseFloat(prem) != parseFloat(amount)) {
                    	 jQuery("#productPrem").html("原价:￥"+prem);
                    }
                    jQuery("#amount").html(amount);
                    jQuery("#productTotalPrice").val(prem);
                    jQuery("#payPrice").val(amount);
                    jQuery("#discountPrice_0").val(amount);
                    jQuery("#totalAmount").val(prem);
                    jQuery("#recognizeePrem_0").val(prem);
                    jQuery("#recognizeeTotalPrem_0").val(prem);
                }
            });
        },
        // 保单终止日期计算
        effchange:function(policyStart) {
            var _this = this;
            var period = jQuery("#factor_Period").val();
            var protectionPeriodLast = period.substring(0, period.length - 1);// 保障期限后段
            var protectionPeriodTy = period.substring(period.length - 1);// 保障期限类型Y,M,D,A
            jQuery("#protectionPeriodTy").val(protectionPeriodTy);
            jQuery("#protectionPeriodLast").val(protectionPeriodLast);
            var effective = jQuery("#effective").val();
            var brithday = "";  // 获取被保人生日，用户保障期限为至XX岁时使用
            var policyEnd = _this.addDate(protectionPeriodTy, protectionPeriodLast, effective, brithday);
            jQuery("#policyStartEndTime").show();
            jQuery("#policyStart").html(policyStart);
            jQuery("#policyEnd").html(policyEnd);
            jQuery("#m_startDate").val(policyStart);
            jQuery("#m_endDate").val(policyEnd);
        },
        iChoseProductPlanStartWith:function(){
        	if(jQuery('#renwealOne').val()=="true"){
	        	jQuery('.up_btns').on("click", function(s) {
	        			jQuery("#choseProduct").val("up_btns");
	        		})
	    		jQuery('.old_btns').on("click", function(s) {
	    			jQuery("#choseProduct").val("old_btns");
	    		})
	    		
	    		//页头页尾显示（去除不要显示的栏）
	    		jQuery(".g-nav-main").hide();
	        	jQuery(".g-nav-title").hide();
	        	jQuery(".m-search").hide();
	        	jQuery(".g-f-right_new").hide();
	         
	        	
	         	if(errorMassage!=''){
	         		art.dialog({ icon: 'error', content: errorMassage });
	         	}
	         	
	         	if("CPS" == jQuery("#zhenAiChoseProductPlanFlag").val() || "WJIN" == jQuery("#zhenAiChoseProductPlanFlag").val() ){
	         		jQuery("#policyNo").attr("disabled","disabled");
	         		jQuery("#applicantMobile").attr("disabled","disabled");
	         	}
	         	
	         
	        	 
	        	jQuery('#PointShowLoginWindow').live('click',function(){
	        	 artLogin();
	        	})
	
	
	        	// 刷新验证码图片
	        	function loginWindowCaptchaImageRefresh() {
	        		jQuery("#loginWindowCaptchaImage").attr("src", sinosoft.base + "/captcha.jpg?timestamp" + (new Date()).valueOf());
	        	}
	        	
	        	// 点击刷新验证码图片
	        	jQuery("#loginWindowCaptchaImage").click( function() {
	        		loginWindowCaptchaImageRefresh();
	        	});
	        	loginWindowCaptchaImageRefresh();
	        	
	        	if(jQuery("#isLogin").val() == "Y"){
	        		jQuery("#PointShowLoginWindow").hide();
	        	}
        	}
        },
        
        vallidateReneFrom:function(){
        	jQuery(document).wjDataVelify({
                tarArea : ".renewal_from",           //校验的DOM作用域
                tarElms : "input[data-verify]",     //校验的DOM元素集合
                sbtBtn : ".renewal_from_right span",                 //提交按钮
                callBack : function() {             //回调函数
        		    jQuery("#chosePlanForm").ajaxSubmit({
    		            url: sinosoft.base+'/shop/renewal_insurance!chosePlanFormSubmit.action?policyNo='+jQuery("#policyNo").val()+"&applicantMobile="+jQuery("#applicantMobile").val(),
    		            type: "post", 
    		            crossDomain: true,  
    		            dataType: "jsonp",
    					jsonp: "jsonpCallback",
    		            success: function(data) {
	    		            if (data.status == "success") {
	    		         		location.href = sinosoft.base+"/shop/renewal_insurance!initInsure.action?kid="+jQuery("#zhenAiRenewalKID").val()+"&mid="+jQuery("#zhenAiRenewalID").val();
	    		            }else{
	    		            	jQuery("#loginWindowCaptchaImage").attr("src", sinosoft.base + "/captcha.jpg?timestamp" + (new Date()).valueOf());
	    		            	art.dialog({ icon: 'error', content: data.message });
	    		            }
    		            }
    		         })
                }
            });

        },
        textTip:function(){

                jQuery(".renewal_con_ul").on('click','.error',function(){
                    jQuery(this).hide();
                    jQuery(this).siblings('.text').children('.item_text').focus();

                })
        },
        addDate:function(type, NumDay, dtDate, brithday) { 
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
        },
        shade_show:function(){
            var _this = this;
            jQuery('body').before("<div class='shade_box'></div>");
        },
         shade_hide:function(){
             jQuery('.shade_box').remove();
        }
    
    }.init();
});