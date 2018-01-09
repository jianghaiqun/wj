/*
#@des:会员中心调用js
*@maker:吴波
*@time:2013-10-28
*/
jQuery(function(){   
var nav = document.getElementById("member_nav");
var links = nav.getElementsByTagName("dd");
var lilen = nav.getElementsByTagName("a"); //判断地址
var currenturl = document.location.href;
var currenturl1=currenturl.split("/");
var currenturl2=currenturl1[currenturl1.length-1];
var last = 0; 
//从我的积分 积分保单兑换点击保单下载 左侧列表我的积分高亮
if (currenturl2.indexOf("order_query!receiptsDownload.action") >= 0 && currenturl2.indexOf("source=jifen") >= 0) {
	for (var i=0;i<links.length;i++)
	{
	   var linkurl =  lilen[i].getAttribute("href");
	   var linkurl1=linkurl.split("/");
	   var linkurl2=linkurl1[linkurl1.length-1];
	     if("point!newList.action".indexOf(linkurl2)!=-1)
	        { last = i;}
	}
	lilen[last].className = "selects";
} else if (currenturl2.indexOf("bill_query!queryBillHist.action") >= 0 || currenturl2.indexOf("bill_detail!getBillDetailInfo.action") >= 0 || currenturl2.indexOf("bill_save!enterBilling.action") >= 0) {
	for (var i=0;i<links.length;i++)
	{
	   var linkurl =  lilen[i].getAttribute("href");
	   var linkurl1=linkurl.split("/");
	   var linkurl2=linkurl1[linkurl1.length-1];
	     if("bill_query!queryBill.action".indexOf(linkurl2)!=-1)
	        { last = i;}
	}
	lilen[last].className = "selects";
}
else if(currenturl2!="member_center!index.action?tagid=logintag" && currenturl2!="member_center!index.action" && currenturl2.indexOf("point!queryPointsDesc.action") < 0)
{
for (var i=0;i<links.length;i++)
{
   var linkurl =  lilen[i].getAttribute("href");
   var linkurl1=linkurl.split("/");
   var linkurl2=linkurl1[linkurl1.length-1];
     if(currenturl2.indexOf(linkurl2)!=-1)
        { last = i;}
}
         lilen[last].className = "selects";  //高亮代码样式
}
leftMenuLoad();
informLoad();

});
function leftMenuLoad() {
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/member!getMemLeftMenuNum.action',
		dataType: "json",
		async: true,
		success: function(data) {
			if (data.status == 'Y') {
				jQuery("#AllOrderEm").text(data.AllOrder);
				jQuery("#TripOrderNumEm").text(data.TripOrderNum);
				jQuery("#PrePayEm").text(data.PrePay);
				jQuery("#PayedEm").text(data.Payed);
				jQuery("#CommentNumEm").text(data.CommentNum);
				jQuery("#EffectiveEm").text(data.Effective);
				jQuery("#OutEffectEm").text(data.OutEffective);
				jQuery("#CouponCountEm").text(data.CouponCount);
				jQuery("#currentValidatePointEm").text(data.currentValidatePoint);
				jQuery("#collectionNumEm").text(data.collectionNum);
				jQuery("#compareNumEm").text(data.compareNum);
				jQuery("#fullDegreeEm").text(data.fullDegree);
				jQuery("#datetime1").text(data.datetime1);
				jQuery("#datetime2").text(data.datetime2);
				jQuery("#datetime3").text(data.datetime3);
				//显示隐藏理财险
				if(data.isLcx=='Y'){
					jQuery("#showLcx").show();
				}else{
					jQuery("#showLcx").hide();
				}
				if (typeof(jQuery("#dPayCountDt")) != 'undefined' && jQuery("#dPayCountDt") != null) {
					if (data.PrePay != null && data.PrePay != '') {
						jQuery("#dPayCountDt").text(data.PrePay);
					}
				}
				if (typeof(jQuery("#commentNumDt")) != 'undefined' && jQuery("#commentNumDt") != null) {
					if (data.CommentNum != null && data.CommentNum != '') {
						jQuery("#commentNumDt").text(data.CommentNum);
					}
				}
				if (typeof(jQuery("#noEffectCountDt")) != 'undefined' && jQuery("#noEffectCountDt") != null) {
					if (data.Payed != null && data.Payed != '') {
						jQuery("#noEffectCountDt").text(data.Payed);
					}
				}
				//显示隐藏存档条数
				if(data.count == 3){
					jQuery("#showchive").show();
					jQuery("#chive1").show();
					jQuery("#chive2").show();
					jQuery("#chive3").show();
				}else if(data.count == 2){
					jQuery("#showchive").show();
					jQuery("#chive1").show();
					jQuery("#chive2").show();
					jQuery("#chive3").hide();
				}else if(data.count == 1){
					jQuery("#showchive").show();
					jQuery("#chive1").show();
					jQuery("#chive2").hide();
					jQuery("#chive3").hide();
				}else if(data.count == 0){
					jQuery("#showchive").hide();
				}
			}
		}
	});
}

function showComment(ordersn){
	var Stars1 = new Stars("stars1_"+ordersn);
	var Stars2 = new Stars("stars2_"+ordersn);
	var Stars3 = new Stars("stars3_"+ordersn);
	var Stars4 = new Stars("stars4_"+ordersn);
};

     jQuery(function(){
        // 评论显示隐藏
           var pl_btn =  jQuery('.fb_pl');
           pl_btn.click(
        	function(){
        		 var pl_box =jQuery(this).parent().parent().next();
        		   if(pl_box.is(":visible"))
        		       {pl_box.hide();    jQuery(this).removeClass("zk_select");}
        		   else{pl_box.show();   jQuery(this).addClass("zk_select");}
        	}	   
           );
     });
     
     function submitComment(orderSn) {
    	 
 	  	// 评论内容
     	var content = jQuery("#content_" + orderSn).val();
     	if (content == null || content == '' || getByteLength(content) < 20 || "产品是否给力？分享你的购买心得给大家吧!"==content) {
     		jQuery("#error_"+orderSn).text("您需要填写10个以上的字哦！");
     		jQuery("#error_"+orderSn).css("display","inline-block");
     		return;
     	} else if (getByteLength(content) > 1000) {
     		jQuery("#error_"+orderSn).text("字数超了～评论的内容只能写500个字以内哟！");
     		jQuery("#error_"+orderSn).css("display","inline-block");
     		return;
     	}
     	
     	// 保障范围
     	var CoverageScore = document.getElementById("stars1_"+orderSn+"_input").value;
     	if (CoverageScore == null || CoverageScore == ''||!jQuery("#stars1_"+orderSn).find("a").hasClass("current-rating")) {
     		jQuery("#stars_"+orderSn+"_error").text("您的评分是我们前进的动力");
     		return;
     	}
     	// 保障程度
     	var DescribeScore = document.getElementById("stars2_"+orderSn+"_input").value;
     	if (DescribeScore == null || DescribeScore == ''||!jQuery("#stars2_"+orderSn).find("a").hasClass("current-rating")) {
     		jQuery("#stars_"+orderSn+"_error").text("您的评分是我们前进的动力");
     		return;
     	}
     	// 保障价格
     	var PolicyScore = document.getElementById("stars3_"+orderSn+"_input").value;
     	if (PolicyScore == null || PolicyScore == ''||!jQuery("#stars3_"+orderSn).find("a").hasClass("current-rating")) {
     		jQuery("#stars_"+orderSn+"_error").text("您的评分是我们前进的动力");
     		return;
     	}
     	// 售后服务
     	var clientScore = document.getElementById("stars4_"+orderSn+"_input").value;
     	if (clientScore == null || clientScore == ''||!jQuery("#stars4_"+orderSn).find("a").hasClass("current-rating")) {
     		jQuery("#stars_"+orderSn+"_error").text("您的评分是我们前进的动力");
     		return;
     	}
     	// 投保目的
     	var purpose = jQuery("#purpose_"+orderSn+"_span").find('select').val();
     	if (purpose == null || "undefined" == typeof(purpose)) {
     		purpose = "";
     	}
     
     	 jQuery("#cp_pl_btns_"+orderSn).addClass(" vsbtn"); 
    	 jQuery("#cp_pl_btns_"+orderSn).attr("disabled","disabled"); 
    	 
    	 jQuery.ajaxLoadImg.show('comment_'+orderSn);
     	jQuery.ajax({
     		type: 'post',
     		url: sinosoft.base+'/shop/member_comment!memberSubmit.action',
     		dataType: "json",
     		data: "orderSn="+orderSn+"&coverageScore="+CoverageScore+"&content="+content+"&describeScore="+DescribeScore+"&policyScore="+PolicyScore+"&clientScore="+clientScore+"&purpose="+purpose,
     		async: true,
     		success: function(data) {
     			jQuery.ajaxLoadImg.hide('comment_'+orderSn);
     			jQuery("#cp_pl_btns_"+orderSn).attr("disabled","");
			 	jQuery("#cp_pl_btns_"+orderSn).removeClass(" vsbtn"); 
     			if (data.status == "success")
     			{
     				jQuery("#Integral_"+orderSn).show();
     				jQuery("#points_"+orderSn).text(data.Points);
     				jQuery("#Integral1_"+orderSn).hide();
     				jQuery("#a_"+orderSn).show();
     				jQuery("#commented_"+orderSn).show();
     				jQuery("#notcomment_"+orderSn).removeClass("fb_pl");
     				jQuery("#notcomment_"+orderSn).removeClass("zk_select");
     				jQuery("#notcomment_"+orderSn).hide();
     				jQuery("#error_"+orderSn).text("");
     				jQuery("#error_"+orderSn).hide();
     				jQuery("#stars_"+orderSn+"_error").text("");
     				document.getElementById("stars1_"+orderSn+"_input").value="";
     				document.getElementById("stars2_"+orderSn+"_input").value="";
     				document.getElementById("stars3_"+orderSn+"_input").value="";
     				document.getElementById("stars4_"+orderSn+"_input").value="";
     				jQuery("#tr_comment_"+orderSn).removeClass("pl_con_boxs");
     				jQuery("#tr_comment_"+orderSn).hide();
     				var notCommOrderSn = jQuery("#notCommOrderSn").val();
     				if (notCommOrderSn != null && notCommOrderSn != '' && notCommOrderSn.indexOf(orderSn) >= 0) {
     					var index = notCommOrderSn.indexOf(orderSn);
     					var nextOrderSn = notCommOrderSn.substr(index + 17, 16)
     					jQuery("#notCommOrderSn").val(notCommOrderSn.substring(0,index) + notCommOrderSn.substring(index+17));
     					jQuery("#tr_comment_"+nextOrderSn).show();
     					jQuery("#tr_comment_"+nextOrderSn).addClass("zk_select");
     					showComment(nextOrderSn);
     					
     				}
     			}
     			else
     			{
    			 	jQuery("#stars_"+orderSn+"_error").text(data.message);
     			}
     		}
     	});
     }

   //获取字符串的字节长度
     function getByteLength(str) {
         if (null == str) {
             return 4;
         }
         if ("undefined" == typeof str) {
             return 9;
         }
         var len = str.length;
         var byteLen = 0;
         for (var i = 0; i < len; i++) {
             if (str.charCodeAt(i) > 255) {
                 byteLen += 2;
             } else {
                 byteLen += 1;
             }
         }
         return byteLen;
     }
     getFullDegree();
     function getFullDegree(){
    	var fullDegree = jQuery("#fullDegree").val();
    	jQuery("#MemberCenter").html(fullDegree);
    	if(fullDegree!="100%"){
    		jQuery("#tips").html('填写完整账户信息，可获得更多积分奖励哦！');
    	}else{
    		jQuery("#MemberCenter").hide();
    	}
    };
    function queryInfo(str){
    	jQuery.ajax({
     		type: 'post',
     		url: sinosoft.base+'/shop/profile!queryInfo.action',
     		dataType: "json",
     		data: "applicantName="+str,
     		async: true,
     		success: function(data) {
     			    var obj = eval(data);
     				jQuery("#rname").val(obj.applicantName);
     				jQuery("#sex").val(obj.applicantSex);
     				jQuery("#wall").val(obj.applicantBirthday);
     				//jQuery("#xuanzhong").val(obj.applicantIdentityType);
     				setIDTypeName("xuanzhong",obj.applicantIdentityType);
     				jQuery("#IDNO").val(obj.applicantIdentityId);
  				    jQuery("#city").val(obj.applicantArea2);
     				jQuery("#address").val(obj.applicantAddress);
     				jQuery("#zipcode").val(obj.applicantZipCode);
     				areaRedirect(obj.applicantArea2Rank);
     				setIDType("area",obj.applicantArea1Name);
     				setIDType("city",obj.applicantArea2Name);
     		}
     	});
    };
    function setIDType(id,p_oValue){
		 var objSelect = document.getElementById(id);
		 var len = objSelect.options.length;
		 for(var i = 0; i < len; i++){
		  var oValue = objSelect.options[i].value;
		  if(oValue == p_oValue){
		   objSelect.options[i].selected=true; 
		  }
		 }
	}
    function setIDTypeName(id,p_oValue){
		 var objSelect = document.getElementById(id);
		 var len = objSelect.options.length;
		 for(var i = 0; i < len; i++){
		  var oValue = objSelect.options[i].text;
		  if(oValue == p_oValue){
		   objSelect.options[i].selected=true; 
		  }
		 }
	}
    
    jQuery('#vip_qa_list li span').click(
    	      function()
    	            {
    	                jQuery(this).addClass('select_qa').parent().siblings().find('span').removeClass('select_qa');
    	                jQuery('#vip_ad_con > div:eq('+ jQuery('#vip_qa_list li').index(jQuery(this).parent()) +')').animate({right: '0px'}, "slow").siblings().animate({right: '-290px'}, "slow");
    	            });
    jQuery(".close_qa").click(function(){
    	                 jQuery(this).parent().animate({right: '-290px'}, "slow");
    	                   jQuery(".select_qa").removeClass("select_qa");

    	});
    function alertTips(){
    	  art.dialog({
    	    content:'<div class="jifen_tip" id="jifen_tips" ><a href="profile!edit.action" target="_blank" class="jifen_tx">立即填写</a><a href="javascript:void(0)" class="jifen_zbtx">暂不填写</a></div>',
    	    padding:0,
    	    fixed: true,
    	    drag: false,
    	    resize: false,
    	    id: 'tips'
    	});
    	  jQuery(".jifen_tx").click(function(){
  	        art.dialog.list['tips'].close();
  	    });
    	    jQuery(".jifen_zbtx").click(function(){
    	        art.dialog.list['tips'].close();
    	    });

    	 };
 if(jQuery("#isfirstMC").val()=="Y"){
    	alertTips();
    }
 if(jQuery("#isfirstMCAfterUngrade").val()=="Y"){
	//jifen add js 积分升值提示
	 art.dialog({
         lock: true,
         title:'积分升级',
         padding:'20px 30px 20px 15px',
         background: '#000000', // 背景色
         opacity: 0.6,  // 透明度
         content: '<span class="ji_dir_tit tit_colors">您的积分升级啦！</span><p class="jf_dir_con">逛<a href="'+sinosoft.front+'/jifen/">积分商城</a>，积分能抵钱花！<br />大牌好礼兑不停，<a href="'+sinosoft.base + '/shop/point!newList.action">快去查看</a>您的积分吧</p>',
         icon: 'up',
         ok: function () {
             return true;
         }
     });
 }
 // 用户被动注册（购买后自动注册）首次登录后进行账户验证引导
 if(jQuery("#isFirstLoginAutoReg").val()=="Y"){
	 var username = jQuery("#realName").val();
	 if (username == null || username == '') {
		 username="开心保会员";
	 }
	 var phone = jQuery("#phone").val();
     art.dialog({
         id: 'ones',
         content: '亲爱的'+username+'<br>为保证您的安全,推荐验证您的手机号码。',
         button: [
             {
                 name:'立即验证',
                 callback: function () {

                        art.dialog({
                        	   drag: false,
                        	    fixed: true,
                        	id:'bandingMobile',
                         content: '<table class="user_bindtable">'
                                 +'    <tr>'
                                 +'      <td class="user_bind_td">手机号码：</td>'
                                 +'      <td> <input type="text" class="userbing_input" id="user_bing_tel"> </td>'
                                 +'      <td><input type="button" id="send_btn"  class="send_code" value="发送验证码" /> </td>'
                                 +'    </tr>'
                                 +'    <tr>'
                                 +'      <td class="user_bind_td">验证码：</td>'
                                 +'      <td><input type="text" class="userbing_input" id="auth_code"></td>'
                                 +'      <td></td>'
                                 +'    </tr>'
                                 +'  </table>',
                            button: [
                             {
                               name:'确认',
                               focus: true,
                               callback:function(){
                            	   var mid = jQuery("#memberId").val();
           						   var inputPhone = jQuery("#user_bing_tel").val();
           						   var sdcode2 = jQuery("#auth_code").val();
	           					   if (sdcode2 == "") {
	           						   art.dialog({ icon: 'error', content: '请输入验证码！' });
	        						   return false;
	        					   }
	           					jQuery.ajax( {
	    							url : "member!bandingMobile.action?&myMobileNO="+phone+"&mid="+mid+"&sdcodes="+sdcode2+"&mobileNOO="+inputPhone,
	    								type : "post",
	    								dataType : "html",
	    								success : function(data) {
			    							if (data == "repeat") {
			    								art.dialog({ icon: 'error', content: '此手机已被注册！' });
			    							} 
			    							if (data == "codeError") {
			    								art.dialog({ icon: 'error', content: '验证码错误！' });
			    							}
			    							if (data == "nullCode") {
			    								art.dialog({ icon: 'error', content: '验证码失效,请重新发送验证码！' });
			    							} 
			    							if (data == "success") {
			    								art.dialog({
			                                         icon: 'succeed',
			                                         content: '<p ><span style="font-size:14px;">恭喜您~！<br>手机号码绑定成功。</span><br>现在可以使用手机号码作为帐号登录了。</p>',
			                                         ok : function(){
			                                        	art.dialog({id:'bandingMobile'}).close();
			 	    									jQuery("#queryMember").submit();
			 	    								}
			                                     });
			    							}
	    								}
	    						});
	           					
                                     
                               return false;
                       
                               }
                             }
                            ]
                       });

                     return true;
                 },
                 focus: true
             },
             {
                 name: '下次再说'
               
             }
         ]
     });
 }
 
 jQuery(function(){
	  var vip_list = jQuery(".member_material li:gt(6)");
	   vip_list.hide();
	  var vip_q = jQuery(".zs_vip_mes");
	       vip_q.click(function(){

	    if (vip_list.is(":visible")) {
	      vip_list.hide();
	      vip_q.addClass("zs_vip_s");

	    } else {
	      vip_q.removeClass("zs_vip_s");
	      vip_list.show();
	    }

	        });

	   });

 var Class = {
		    create: function() {
		        return function() { this.initialize.apply(this, arguments); };
		    }
		};
		var Extend = function(destination, source) {
		    for (var property in source) {
		        destination[property] = source[property];
		    }
		};
		function stopDefault( e ) {
		     if ( e && e.preventDefault ){
		        e.preventDefault();
		    }else{
		        window.event.returnValue = false;
		    }
		    return false;
		} 
		/**
		 * 星星打分组件
		 */
		var Stars = Class.create();
		Stars.prototype = {
		    initialize: function(star,options) {
		        this.SetOptions(options); //默认属性
		        var flag = 999; //定义全局指针
		        var isIE = (document.all) ? true : false; //IE?
		        if (document.getElementById(star) == null || typeof(document.getElementById(star))=="undefined" ) {
		        	return;
		        }
		        var starlist = document.getElementById(star).getElementsByTagName('a'); //星星列表
		        var input = document.getElementById(this.options.Input) || document.getElementById(star+"_input"); // 输出结果
		        var tips = document.getElementById(this.options.Tips) || document.getElementById(star+"_tips"); // 打印提示
		        var nowClass = " " + this.options.nowClass; // 定义选中星星样式名
		        var tipsTxt = this.options.tipsTxt; // 定义提示文案
		        var tipsstyle = this.options.tipsstyle; // 定义提示文案样式
		        var len = starlist.length; //星星数量
		        
		        for(i=0;i<len;i++){ // 绑定事件 点击 鼠标滑过
		            starlist[i].value = i;
		            starlist[i].onclick = function(e){
		                stopDefault(e);
		                this.className = this.className + nowClass;
		                flag = this.value;
		                input.value = this.getAttribute("star:value");
		                tips.innerHTML = tipsTxt[this.value];
		                var s =jQuery(this).parent().parent().siblings(".error_red").text("");
		            };
		            starlist[i].onmouseover = function(){
		                if (flag < 999){
		                    var reg = RegExp(nowClass,"g");
		                    starlist[flag].className = starlist[flag].className.replace(reg,"");
		                  // tips.innerHTML = tipsTxt[this.value]
		                }
		            };
		            starlist[i].onmouseout = function(){
		                if (flag < 999){
		                    starlist[flag].className = starlist[flag].className + nowClass;

		                }
		            };
		        };
		        if (isIE){ //FIX IE下样式错误
		            var li = document.getElementById(star).getElementsByTagName('li');
		            for (var i = 0, len = li.length; i < len; i++) {
		                var c = li[i];
		                if (c) {
		                    c.className = c.getElementsByTagName('a')[0].className;
		                }
		            }
		        }
		    },
		    //设置默认属性
		    SetOptions: function(options) {
		        this.options = {//默认值
		            Input:  "",//设置触保存分数的INPUT
		            Tips:     "",//设置提示文案容器
		            nowClass: "current-rating",//选中的样式名
		            tipsTxt:  ["<span class='yb_style'>一般</span>","<span class='yb_style'>一般</span>","<span class='my_style'>满意</span>","<span class='my_style'>满意</span>","<span class='jx_style'>惊喜</span>"]//提示文案
		        };
		        Extend(this.options, options || {});
		    }
		};
		var isclick = window.location.href;
		var start = isclick.indexOf("member_comment!queryComment.action?orderSn=");
		if(start!=-1){
			start+="member_comment!queryComment.action?orderSn=".length;
			isclick = isclick.substring(start,isclick.indexOf("#"));
		     if(isclick!=null && isclick !=""){
		     	showComment(isclick);
		     }
		}
		
/*会员中心账户信息 */
 jQuery(".member_defaut").hover(function(){
       jQuery(this).addClass("member_input_sele");
  },function(){
       jQuery(this).removeClass("member_input_sele");
 });
 /**
 * 加载公告
 */
function informLoad() {
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!showInforMem.action',
		dataType: "json",
		async: true,
		success: function(data) {
			if (data.info == null || data.info == '') {
				jQuery('.member_gg').hide();
			} else {
				jQuery('#member_gg').html(data.info);
				jQuery('.member_gg').show();
			}
		}
	});
}

function loadPointDesc(no) {
	jQuery(".plage_select").removeClass();
	jQuery("#page"+no).addClass("plage_select");
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/point!pageLoad.action?',
		data: {
			no: no,
			fenlei: jQuery("#pointDescFenlei").val()
		},
		dataType: "json",
		async: true,
		success: function(data) {
			if (data.info != null && data.info != '') {
				jQuery("#pointDescInfo").html(data.info);
				
			}
		}
	});

}

jQuery(".fl_list li").click(function(){
	jQuery(this).addClass("select").siblings().removeClass('select');
	jQuery("#pointDescFenlei").val(jQuery(this).text());
	loadPointDescByLabel(encodeURIComponent(encodeURIComponent(jQuery(this).text())));
});

function loadPointDescByLabel(fenlei) {
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/point!loadPointDescByLabel.action?fenlei='+fenlei,
		dataType: "json",
		async: true,
		success: function(data) {
			if (data.info != null && data.info != '') {
				jQuery("#pointDescInfo").html(data.info);
			} else {
				jQuery("#pointDescInfo").html('');
			}
			
			if (data.pageInfo != null && data.pageInfo != '') {
				jQuery("#pageInfo").html(data.pageInfo);
			} else {
				jQuery("#pageInfo").html('');
			}
		}
	});
}
function setPointContent(title,content) {
	jQuery("#showTitle").html(title);
	jQuery("#showContent").html(content);
	jQuery(".qa_consf").removeAttr("style");
	 jQuery("#qa_con_btn").remove();
	 pointDescChange();
}

function pointDescChange() {
	// 控制积分内容超出隐藏

    var qa_dom = jQuery(".qa_consf");

		var qa_h = parseInt(jQuery(".qa_consf").height());
		var m_eight = parseInt(222);
		if(qa_h>m_eight){ 
			qa_dom.height(m_eight);
			qa_dom.after("<b id='qa_con_btn'>展开</b>");
		}
// 给展开按收起按钮绑定事件
		jQuery("#qa_con_btn").click(function(){
			var if_text = jQuery(this).text();
			if(if_text=="展开"){
				jQuery(this).text("收起")
				qa_dom.height(qa_h);
			}else if(if_text=="收起"){
				jQuery(this).text("展开")
				qa_dom.height(136);
			}
		})

		//左边高度和右边对齐
	 var left_height = parseInt(jQuery(".member_left").height());
 var right_height = parseInt(jQuery("#right_height").height());
 if(right_height<left_height){
 		jQuery("#right_height").css("min-height",left_height-2);
 }

}



jQuery('.dh_nav_li li span').click(function () {
    jQuery(this).addClass('selecttable').parent().siblings().find('span').removeClass('selecttable');
    jQuery('.dh_table > div:eq(' + jQuery('.dh_nav_li li').index(jQuery(this).parent()) + ')').show().siblings().hide()
});
		
jQuery(".dh_zt>a").click(function(){
	jQuery(this).addClass("select_sp").siblings().removeClass('select_sp');
});


/** 
 * @des: 常用邮寄地址维护： 1. 新增邮寄地址
							2. 修改邮寄地址
 * @maker: Songzairan
 * @date: 2015.6.9
 */
function getChildrenArea(id,flag,flagvalue){
if(id!="-1"){
 if(flag != ""){
     jQuery.ajax({
         type:'get',
		    url: sinosoft.base+"/shop/donate!getChildernArea.action?id="+id,
		    dataType: "json",
		    success: function(item) {
			   fillArea(item,flag,flagvalue);
		    }
	    });
 }
}else{
var childrenArea = document.getElementById(flag);
childrenArea.options.length=1;
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

/**
 * 删除邮件地址信息
 */
function deleteDeliverAddrinfo(id) {
	if (confirm("确认删除该地址?")) {
		jQuery
				.ajax({
					url : sinosoft.base
							+ "/shop/member_info_maintain!deleteDeliverAddrInfo.action?info_id=" + id,
					dataType : "json",
					type : "POST",
					async : false,
					success : function(data) {
						if (data.tFlag == "Suc") {
							art.dialog.alert("删除信息成功！",function(){
								window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
							});
						} else {
							art.dialog.alert(data.Msg);
						}
					}
				});
	} else {
		return false;
	}
}
/**
 * 设置默认邮件地址信息
 */
function setDefaultAddrinfo(id, infoId) {
	jQuery
	.ajax({
		url : sinosoft.base
				+ "/shop/member_info_maintain!defaultDeliverAddrInfo.action?memberID=" + id + "&info_id=" + infoId,
		dataType : "json",
		type : "POST",
		async : false,
		success : function(data) {
			if (data.tFlag == "Suc") {
				art.dialog.alert("设置默认信息成功！",function(){
					window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
				})
				
			} else {
				art.dialog.alert(data.Msg);
			}
		}
	});
}

(function() {
	
	var oAddCont = jQuery("#addEmailAddress");
	var oRequire = jQuery("#sendEmail").find("a.require");
	
	//新增邮寄地址
	oAddCont.click(function() {
		var deliverAddrCnt = $("#deliverAddrCount").val();
        if (deliverAddrCnt>=5) {
        	art.dialog.alert("只能增加5个邮寄地址!");
        	return false;
        }
		art.dialog.open(
				"deliver_address_maintain!initAddDeliverAddr.action?count="+deliverAddrCnt,{
				title: "常用邮寄地址填写",
				id : 'deliverAddr',
				width:"600px",
				height:"300px",
				okVal : '保存',
				ok : function() {
					var iframeDoc = this.iframe.contentWindow.document;
					if ($(iframeDoc).find("#deliverAddrName").val() == '中英文姓名') {
						verifyElement1("姓名|NOTNULL&UFO&LEN>2","","deliverAddrName",iframeDoc);
						return false;
					} else if (!verifyElement1("姓名|NOTNULL&UFO&LEN>2",$(iframeDoc).find("#deliverAddrName").val(),"deliverAddrName",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#deliverAddrTel").val() == '手机或固话') {
						verifyElement1("手机号码|PHONE1","","deliverAddrTel",iframeDoc);
						return false;
					} else if (!verifyElement1("手机号码|PHONE1",$(iframeDoc).find("#deliverAddrTel").val(),"deliverAddrTel",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#address").val() == '请准确填写，以免无法邮寄给您') {
						verifyElement1("地址|notnull&LEN>4","","address",iframeDoc);
						return false;
					} else if (!verifyElement1("地址|notnull&LEN>4",$(iframeDoc).find("#address").val(),"address",iframeDoc)) {
						return false;
					}	
					if (!verifyElement1("邮编|ZIPCODE",$(iframeDoc).find("#zipCode").val(),"zipCode",iframeDoc)) {
						return false;
					}
					
					var addrName = $(iframeDoc).find("#deliverAddrName").val();
					var addrTel = $(iframeDoc).find("#deliverAddrTel").val();
					var addrProvince = $(iframeDoc).find("#area").val();
					var addrProvinceName = $(iframeDoc).find("#area option:selected").text();
					var addrCity = $(iframeDoc).find("#city").val();
					var addrCityName = $(iframeDoc).find("#city option:selected").text();
					var addrAddress = $(iframeDoc).find("#address").val();
					var addrZipCode = $(iframeDoc).find("#zipCode").val();
					if(addrProvinceName=="" || addrCityName == ""){
						return false;
					}
					jQuery.ajax({
						url: "deliver_address_maintain!saveDeliverAddrInfo.action",
						dataType: 'json',
						data: {
							count: deliverAddrCnt,
							info_Name: addrName,
							info_Tel: addrTel,
							info_ProvinceCode: addrProvince,
							info_ProvinceName: addrProvinceName,
							info_CityCode: addrCity,
							info_CityName: addrCityName,
							info_DetailAddr: addrAddress,
							info_ZipCode: addrZipCode
						},
						success: function(data) {
							var tFlag = data.tFlag;
							if (tFlag == "Err") {
								art.dialog.alert(data.Msg);
							} else {
							art.dialog({
								id: 'deliverAddr'
							}).close();
								window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
							}
						}
					});
					return false;
				},
				cancel : function(){
					art.dialog({id: 'deliverAddr'}).close();
				},
				cancelVal : '关闭'
			},false
		);
	});
		
		

	//修改邮寄地址
	oRequire.click(function(){ 
		var addr_id = jQuery(this).attr("id").split("_")[1];
		art.dialog.open(
				"deliver_address_maintain!getDeliverAddr.action?addr_id="+ addr_id,{
				title: "常用邮寄地址填写",
				id : 'deliverAddr',
				width:"600px",
				height:"300px",
				okVal : '保存',
				ok : function() {
					var iframeDoc = this.iframe.contentWindow.document;
					if ($(iframeDoc).find("#deliverAddrName").val() == '中英文姓名') {
						verifyElement1("姓名|NOTNULL&UFO&LEN>2","","deliverAddrName",iframeDoc);
						return false;
					} else if (!verifyElement1("姓名|NOTNULL&UFO&LEN>2",$(iframeDoc).find("#deliverAddrName").val(),"deliverAddrName",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#deliverAddrTel").val() == '手机或固话') {
						verifyElement1("手机号码|PHONE1","","deliverAddrTel",iframeDoc);
						return false;
					} else if (!verifyElement1("手机号码|PHONE1",$(iframeDoc).find("#deliverAddrTel").val(),"deliverAddrTel",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#address").val() == '请准确填写，以免无法邮寄给您') {
						verifyElement1("地址|notnull&LEN>4","","address",iframeDoc);
						return false;
					} else if (!verifyElement1("地址|notnull&LEN>4",$(iframeDoc).find("#address").val(),"address",iframeDoc)) {
						return false;
					}	
					if (!verifyElement1("邮编|ZIPCODE",$(iframeDoc).find("#zipCode").val(),"zipCode",iframeDoc)) {
						return false;
					}
					var addrName = $(iframeDoc).find("#deliverAddrName").val();
					var addrTel = $(iframeDoc).find("#deliverAddrTel").val();
					var addrProvince = $(iframeDoc).find("#area").val();
					var addrProvinceName = $(iframeDoc).find("#area option:selected").text();
					var addrCity = $(iframeDoc).find("#city").val();
					var addrCityName = $(iframeDoc).find("#city option:selected").text();
					var addrAddress = $(iframeDoc).find("#address").val();
					var addrZipCode = $(iframeDoc).find("#zipCode").val();
					if(addrProvinceName=="" || addrCityName == "" ){
						return false;
					}
//					if(addrName.trim() == "" || addrTel.trim() =="" || (typeof(addrProvince)=="undefined")  || (typeof(addrCity)=="undefined") || addrAddress.trim()=="" || addrZipCode.trim()==""){
//						art.dialog.alert("对不起，必填项填写后才能保存!");
//						return false;
//					}else if(addrProvinceName=="" || addrCityName == "" || addrProvince=="-1"){
//						art.dialog.alert("对不起，必填项填写后才能保存!");
//						return false;
//					}
					jQuery.ajax({
	  					  url: "deliver_address_maintain!saveDeliverAddrInfo.action",
	  					  dataType: 'json',
	  					  data: {
	  						    info_id: addr_id,
	                            info_Name: addrName,
	                            info_Tel: addrTel,
	                            info_ProvinceCode: addrProvince,
	                            info_ProvinceName: addrProvinceName,
	                            info_CityCode: addrCity,
	                            info_CityName: addrCityName,
	                            info_DetailAddr: addrAddress,
	                            info_ZipCode: addrZipCode
	                        },
	  					  success: function(data) {
	                            var tFlag = data.tFlag;
	                            if (tFlag == "Err") {
	                                dialog.alert(data.Msg);
	                            } else {
	                          	  art.dialog({
	                                    id: 'deliverAddr'
	                                }).close();
	                                window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
	                            }
	  					  }
	  				});
					
					return false;
					},
				cancel : function(){
					art.dialog({id: 'deliverAddr'}).close();
				},
				cancelVal : '关闭'
			}
		);

	});
})();




    /*会员中心实际支付金额优惠tib信息提示*/
  jQuery(".member_hui").hover(function() {
    jQuery(this).find(".member_h_des").show();
  }, function() {
    jQuery(this).find(".member_h_des").hide();
  });
	jQuery(document).ready(function() {
        jQuery(".member_t_tag li").tagclickbind({con:'browse-consf',addstyle:'hover',mode:'div',nav:'member_t_tag'});
  })



// 手机验证码验证。
    jQuery(function () {
            var util = {
                wait: 60,
                hsTime: function (that) {
                    _this = jQuery(this);

                 

                    if (util.wait == 0) {
                          jQuery(that).removeAttr("disabled").val('重发短信验证码');
                          jQuery(that).removeClass("disableds");
                          util.wait = 60;
                    } else {
                    	if (util.wait == 60) {
                    		
                    	     var tel = jQuery("#user_bing_tel").val();
                             if(!tel){

                               art.dialog({ icon: 'error', content: '手机号不能为空！' });
                                 return false;
                             }
                             var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
                               if(!reg.test(tel)){
                                  art.dialog({ icon: 'error', content: '请输入正确格式的手机号！' });
                                  return false;
                               }

                               
                               
                    		jQuery.ajax({
                        		url: "member!validatePhones.action?ways="+tel,
                        		type: "post",
                        		dataType: "json",
                        		success: function(data){
                        			if(data==true){
                        				art.dialog({icon: 'succeed', content: "短信验证码已发送,请查看您的手机短信<br>尽快完成操作！" });
                					} else{
                						art.dialog({ icon: 'error', content: '短信验证码已发送失败<br>请重新发送或者联系客服！' });
                					}
                                    return;
                        		}			
                        	});
                    	}
                        jQuery(that).addClass("disableds");
                        var _this = this;
                        jQuery(that).attr("disabled", true).val('在' + util.wait + '秒后点此重发');
                        util.wait--;
                        setTimeout(function () {
                            _this.hsTime(that);
                        }, 1000)
                    	
                        
                    }
                }
            }
      
             jQuery("#send_btn").live('click',function(){
                   util.hsTime('#send_btn');
             })

        })


/**
 * 跳转页
 * 
 * @param alias
 * @param index
 */
function gotoPage(alias, index,pagecount,idalias) {
	var div=jQuery("#"+idalias);
	var param = "";
	var sd=jQuery("#sd").val();
	var ed=jQuery("#ed").val();
	var kw=jQuery("#orderStatus  option:selected").val(); 
	var apt=jQuery("#apt").val();
	var ordSn=jQuery("#ordSn").val();
	var sdate = jQuery("#sdate").val();
	var edate = jQuery("#edate").val();
	var record_status = jQuery("#record_status").val();
	var record_status_str = jQuery("#record_status_str").val();

	if (sdate > edate) {
		alert("开始日期不能大于结束日期！");
		return false;
	}

	if (sd != 'undefined' && sd != null && sd != '') {
		param += ("&ldate="+encodeURIComponent(encodeURIComponent(sd)));
	}
	if (ed != 'undefined' && ed != null && ed != '') {
		param += ("&hdate="+encodeURIComponent(encodeURIComponent(ed)));
	}
	if (apt != 'undefined' && apt != null && apt != '') {
		param += ("&applicant="+encodeURIComponent(encodeURIComponent(apt)));
	}
	if (ordSn != 'undefined' && ordSn != null && ordSn != '') {
		param += ("&orderSn="+encodeURIComponent(encodeURIComponent(ordSn)));
	}
	if (kw != 'undefined' && kw != null && kw != '') {
		param += ("&orderStatus="+kw);
	}

	if (sdate != 'undefined' && sdate != null && sdate != '') {
		param += ("&sdate="+sdate);
	}
	if (edate != 'undefined' && edate != null && edate != '') {
		param += ("&edate="+edate);
	}
	if (record_status != 'undefined' && record_status != null && record_status != '') {
		param += ("&record_status="+record_status);
	}
	if (record_status_str != 'undefined' && record_status_str != null && record_status_str != '') {
		param += ("&record_status_str="+record_status_str);
	}


	if (alias.indexOf("?orderStatus") > 0) {
		if (kw != 'undefined' && kw != null && kw != '') {
			alias = alias.substring(0,alias.indexOf("?"))
			div.load(sinosoft.base+"/shop/"+alias+'?page_Index='+index+param);
		} else {
			div.load(sinosoft.base+"/shop/"+alias+'&page_Index='+index+param);
		}
	} else {
		div.load(sinosoft.base+"/shop/"+alias+'?page_Index='+index+param);

	}
	
}

function delBrowsRecord() {
	jQuery("#browse_record").html('<div class="no-mborwse"><a href="'+sinosoft.front+'">还没浏览过商品，去逛逛吧！~</a></div>');
	jQuery.ajax({
		type: 'get',
		url: sinosoft.base+"/shop/browse_record!delBrowseRecord.action",
		dataType: "json",
		async: true,
		success: function(data) {
			
		}
	});
}
