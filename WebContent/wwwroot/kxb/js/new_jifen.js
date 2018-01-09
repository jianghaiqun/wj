/**
* ------------------------------------------
* @make:郭东奇
* @version  1.0 
* @des：积分js
* ------------------------------------------
*/
jQuery('#jifen div.changeDiv').soChange({
thumbObj:'#jifen .ul_change_a2 span',
thumbNowClass:'on',
 slideTime:500,
 changeTime:6000
});
function validateData(){
            var NO=jQuery("#mobile").val();
            var flag=true;
           if(NO==""){
        	    jQuery(".int_error").show();
                jQuery(".int_error").text("充值号码不能为空!");
                flag =false;
                return;
            }
            var regu = /^1[3|4|5|8|7][0-9]\d{4,8}$/;
         	var type_is=jQuery("#mgiftclassfy_type").val();
         	if(type_is==6){
         	}else{
             if (!regu.test(NO)) {
            	 jQuery(".int_error").show();
                 jQuery(".int_error").text("请输入正确手机号!");
                 flag =false;
                 return;
             }
             }
            jQuery(".int_error").hide();
            jQuery(".int_error").text("");
            return flag;
        }

function doPoints(){
	//mod by wangej 20160218 调整校验位置，预防onclick事件在浏览器被篡改
	ExchangeGift();
}
function ExchangeGift(){
    //验证手机号 
    if(validateData()){
    	jQuery.ajaxLoadImg.show('exchangecheck');
    	jQuery.ajax({
		    url:sinosoft.base+"/shop/points!checkPoint.action?presentID="+jQuery("#presentID").val()+"&mobile="+jQuery("#mobile").val(),
		    type: "post",
		    dataType: "json",
		    success: function(data) {
		    	jQuery.ajaxLoadImg.hide('exchangecheck');
		  	  if (data.status =='2') {
		 			artLogin();
		  			return;
		        }else if (data.status =='4') {
		      	  jQuery(".int_error").hide();
		  		  jQuery(".int_error").text("");
		      	  var mobilemsg = jQuery.trim(data.mobilenum)+jQuery.trim(data.mobilemsg);
		      	  
		      	  var contentHtml = '<div class="yzmDiv"><p style="padding:0 4px 0 8px; color:#7B7B7B;">本次兑换将消耗<span class="dir_color">'+data.point+'</span>积分<br />充值号码为：'+mobilemsg
		      	  				+ '</p><span style="padding:0 4px 0 0; color:#7B7B7B;">&nbsp;&nbsp;  图片验证码:</span>'
		      	  				+ '<input id="inputTPYZM" style="width:4em; padding:4px; margin-top:5px;" class="checkYzm"/> '
		      	  				+ '<img style="width:80px; margin-top: -3px; height:24px;  vertical-align: middle;" id="telValidateCaptchaImage" src="/wj/captcha.jpg?timestamp'+ (new Date()).valueOf()+'" alt="验证码" onclick="captchaImageRefresh()"/> '
		      	  				+ '<span class="code_again jf_code">发送</span> <br />'
		      	  				+'<p class="code_tips">输入图片验证码，点击“发送”，获得兑换验证码</p>'
		      	  				+ '<span style="padding:0 4px 0 0; color:#7B7B7B;">&nbsp;&nbsp;  兑换验证码:</span>'
		      	  				+ '<input id="inputYZM" style="width:4em; padding:4px; margin-top:5px;" /><p style="color:#ff0000; padding-left:2px;">&nbsp;&nbsp;<span id="status1"></span></p>';
		      	
		      		art.dialog({
		      			id: 'exchanArt',
		                lock: true,
		                title:'兑换验证',
		                padding:'20px 30px 20px 15px',
		                background: '#000000', // 背景色
		                opacity: 0.6,  // 透明度
		                content: contentHtml,
		                cancel : true,
		                ok: function () {
		                	jQuery(".yzmDiv .error").remove();
		                  var inputYZM = jQuery("#inputYZM").val();
		                  if (inputYZM == null || inputYZM == '') {
		                	  jQuery("#status1").html("请输入兑换验证码！")
		                	  return false;
		                  }
		                  
		              	  jQuery.ajax({
		                        url:"/wj/shop/points!Exchange.action?presentID="+jQuery("#presentID").val()+"&mobile="+jQuery("#mobile").val()+"&sdcodef="+jQuery("#inputYZM").val(),
		                        type: "post",
		                        async: false,
		                        dataType: "json",
		                        success: function(data) {
		                          
		                      	  if (data.status =='2') {
		                      		  artLogin();
		                      		  
		              	          }else if (data.status =='4') {
		              	        	  var type_is=jQuery("#mgiftclassfy_type").val();
		              	        	  var pointsExchangeType=jQuery("#PointsExchangeType").val();
		              	        	  var msg = "30分钟内会将兑换码发送到您手机上，";
		              	        	  if (pointsExchangeType == '2' && type_is != '3') {
		              	        		msg = "30分钟内会充值成功，";
		              	        	  }
		                      		  art.dialog({
		                                    lock: true,
		                                    title:'兑换成功',
		                                    padding:'20px 30px 20px 15px',
		                                    background: '#000000', // 背景色
		                                    opacity: 0.6,  // 透明度
		                                    content: '<span class="ji_dir_tit">兑换成功</span><p class="jf_dir_con">'+msg+'<br />请耐心等待。</p>',
		                                    icon: 'face-smile',
		                                    ok: function () {
		                                  	  window.location.reload(true);
		                                    }
		                                }); 
		                      		  
		                            }else if (data.status =='5') { 
		                          	 // 积分不足
		               		         art.dialog({
		               		              lock: true,
		               		              title:'积分不足',
		               		              padding:'20px 30px 20px 15px',
		               		              background: '#000000', // 背景色
		               		              opacity: 0.6,  // 透明度
		               		              content: '<span class="ji_dir_tit">对不起，您的账户只有'+data.message+'积分不够兑换，<br /><a href="'+sinosoft.front+'/daogou/">看看需要的保险</a>提前购买赚积分吧！</span>',
		               		              icon: 'face-sad',
		               		              ok: function () {
		               		            	 window.location.reload(true);
		               		              }
		               		          });
		              	          }else if (data.status =='6') {
		              	        	  art.dialog({
		               		              lock: true,
		               		              title:'库存不足',
		               		              padding:'20px 30px 20px 15px',
		               		              background: '#000000', // 背景色
		               		              opacity: 0.6,  // 透明度
		               		              content: '<span class="ji_dir_tit">很抱歉，该礼品的库存已不足，请与我们的客服mm联系~</span>',
		               		              icon: 'face-sad',
		               		              ok: function () {
		               		            	 window.location.reload(true);
		               		              }
		               		          });
		              	    		  
		              	          }else if(data.status == '3'){
										 art.dialog({
							    			    id:'yhj_log',
							    			    padding: '25px 50px 5px 50px',
							    			    title:'积分兑换',
							    			    fixed: true,
							    			    drag: false,
							    			    content: "会员需在网站累计购物满"+data.message+"元后，方可在积分商城兑换商品。",
							    			    button:[{name: '确认'}]
							    			});
									
								 } else if (data.status == 'yzmerror') {
									 jQuery("#status1").html(data.message);
				                	  return false;
									 
								 }else{
		              	        	  jQuery(".int_error").show();
		              	        	  jQuery(".int_error").text(data.message);
		              	              
		              	         }
		                      	art.dialog({id:'exchanArt'}).close();
		                      }
		                    });
		              	  return false;
		                }
		            });
		      		
		      		new timeCountDown({
		      	        whichPage: 'yzm',
		      	        endHtml:'发送',
		      	        rebHTML:'重新发送',
		      	        sendCode: function(){
		      	      	  
		      	      	  jQuery.ajax({
		      					url: "points!sendYZM.action?inputTPYZM="+jQuery("#inputTPYZM").val(),
		      					type: "post",
		      					async:true,  
		      					dataType: "json",
		      					success: function(data){
		      						
		      						captchaImageRefresh();
		      						
		      						if(data.status == 'yzmerror'){
		      							jQuery("#status1").html("图片验证码错误，请重新输入！");
		      							
		      						} 
		      						else if (data.status == 'NoLogin') {
		      							jQuery("#status1").html("请登录后再兑换！");
		      						}
		      						else if(data.status == '0'){
		      							jQuery(".code_tips").html(data.message);
		      							
		      						} else{
		      							jQuery("#status1").html("验证码已发送失败！请重新发送或者联系客服！");
		      						}
		      						
		      				 	}			
		      				});
		      	        } 
		      	  });
		        
		        }else if (data.status =='5') {
		      	// 积分不足
		 	         art.dialog({
		 	              lock: true,
		 	              title:'积分不足',
		 	              padding:'20px 30px 20px 15px',
		 	              background: '#000000', // 背景色
		 	              opacity: 0.6,  // 透明度
		 	              content: '<span class="ji_dir_tit">对不起，您的账户只有'+data.message+'积分不够兑换，<br /><a href="'+sinosoft.front+'/daogou/">看看需要的保险</a>提前购买赚积分吧！</span>',
		 	              icon: 'face-sad',
		 	              ok: function () {
		 	            	return true;
		 	              }
		 	          });
		        }else if(data.status == '3'){
					 art.dialog({
		    			    id:'yhj_log',
		    			    padding: '25px 50px 5px 50px',
		    			    title:'积分兑换',
		    			    fixed: true,
		    			    drag: false,
		    			    content: "会员需在网站累计购物满"+data.message+"元后，方可在积分商城兑换商品。",
		    			    button:[{name: '确认'}]
		    			});
					 return;
			 }else{
		      	  jQuery(".int_error").show();
		      	  jQuery(".int_error").text(data.message);
		            return;
		        }
		    }
		 });
	}
}






//刷新验证码图片
function captchaImageRefresh(){
	jQuery("#telValidateCaptchaImage").attr("src", "/wj/captcha.jpg?timestamp" + (new Date()).valueOf());
}

jQuery(function(){
	jQuery(".shop-q").hover(function(){
        jQuery(".shop-a").hide();
        jQuery(".q-icon-up").removeClass("q-icon-up");
        jQuery(this).children(".q-icon-down").addClass("q-icon-up").end().siblings(".shop-a").show();
    })
 	jQuery('#PointShowLoginWindow').click(function(){
		artLogin();//用弹出窗口登录 
	});
 	jQuery('#PointShowRegisterWindow').click(function(){
 		artRegister();//用弹出窗口注册
	});
 	
 	jQuery('#inputTPYZM').live("blur",function(){
 		var inputTPYZM = jQuery("#inputTPYZM").val();
 		jQuery("#status1").html("");
 		if(jQuery.trim(inputTPYZM)==""){
 			if (jQuery('.yzmDiv').find('.error').length > 0) {
 				jQuery(".yzmDiv .error").remove();
 			}
 			jQuery("#status1").after("<span class='error'>请输入图片验证码！</span>");
 			
 			return;
 		  } else {
 			 jQuery(".yzmDiv .error").remove();
 		  }
 	});
 	
 })
$.fn.tagbinds = function(opt) {

	var nav, con, mode, sclass, navl, settings;

	return this.each(function() {

		settings = $.extend({
			parElm : this,
			nav : "#qa_nav",
			navl : " li",
			con : "#qa_con",
			mode : "P",
			sclass : "sel"
		}, opt);

		_init();
		_event();

	});
	function _init() {
		tagobj = jQuery(settings.parElm);
	}
	function _event() {
		tagobj.bind('click', function() {
			jQuery(this).addClass(settings.sclass).siblings().removeClass(
					settings.sclass);
			jQuery(
					settings.con
							+ ' > '
							+ settings.mode
							+ ':eq('
							+ jQuery(settings.nav + settings.navl).index(
									jQuery(this)) + ')').show().siblings()
					.hide();
		});
	}
}

jQuery(".Jf_screen span").click(function() {
	jQuery(this).addClass("jf_sel").siblings().removeClass('jf_sel');
})