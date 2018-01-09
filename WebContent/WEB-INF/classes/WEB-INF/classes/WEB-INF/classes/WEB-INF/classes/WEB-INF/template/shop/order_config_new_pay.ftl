 <!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保_非车险_支付列表</title>
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>
<#if (sdorder.renewalId != "" && sdorder.renewalId != null)!>
	 <link rel="stylesheet" type="text/css" href="${staticPath}/style/wj_kxb/mod_renewal.css"/>
</#if>
<!--支付页面样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<style>
/*update artDialog win
.aui_buttons button {display: block; margin:0 auto;}
*/
.aui_buttons{background: none;  border-top:none;}
/*余额支付*/
.close_col{ background: url(http://resource.kaixinbao.com/images/jifen/fs_03.png) no-repeat;  width: 24px; height: 24px; position: absolute; right:10px; top: 6px; cursor: pointer;}
.balance_pay{ padding-left: 50px;}
.balance-tit{ font-weight: bold; padding-bottom: 6px;}
.balance_box{ padding-left: 30px; }
.balance_box p{ margin-bottom: 6px;}
.balance_zfx{ padding-left: 120px; background: url(http://www.inswindow.com/agent/images/wjs/zf_logo_03.gif) no-repeat; height: 30px; line-height: 30px;}
.balance_input{ width: 160px; border: 1px solid #ccc; height: 24px; line-height: 24px; color: #333333; padding-left: 6px;}
.balance_btsf{ display:inline-block; zoom:1; margin-left: 4px; width: 98px; height: 27px; border: none; cursor: pointer; line-height: 27px; text-align: center; color: #fff; background: #FF6D00;}

</style>

<script src="${shopStaticPath}/jquery-1.4.2.min.js"></script> 
<script src="${shopStaticPath}/jquery.poshytip.js"></script>
<script type="text/javascript" src="${shopStaticPath}/artDialog.js"></script> 

<script type="text/javascript">
   var p_url = "${jrhsURL}";
   var isYezfflag = "";
	function dosubmit(tmp, exchangeFlag){
		if("yezf" == tmp){
		var paypassword = jQuery("#paypassword").val();
		if(paypassword == ''){
			alert("请输入支付密码！");
			return;
		}
			isYezf();
			if(isYezfflag!=""){
				alert(isYezfflag);
				return;
			}
		}
	
		if(tmp == null || tmp == ''){
			return false;
		}
		jQuery("#payType").val(tmp);
		
		var bankCode;
        var bankAccNo;
		if ("zjzf" == tmp) {
			var display =jQuery('.lcBindList').css('display');
			if(display == 'none'){
				bankCode = jQuery("#bank_sel  option:selected").val();
				bankAccNo = jQuery("#bank_no").val();
			} else {
				var bis = jQuery("#bankInfoSel option:selected").val();
				var arr = bis.split('!');
				bankAccNo = arr[0];
				bankCode = arr[1];
			}
			jQuery("#bankCode").val(bankCode);
			jQuery("#bankAccNo").val(bankAccNo);
		}

		jQuery.ajax({
			type: 'post',
			url: sinosoft.base+'/shop/pay!isOutPeriod.action?orderId='+jQuery("#OrdId").val() + '&paySn=' +jQuery("#paySn").val() + '&exchangeFlag=' + exchangeFlag + '&payType=' + tmp + '&bankCode=' + bankCode + '&bankAccNo=' + bankAccNo + "&vCode=" + jQuery("#vCode").val(),
			dataType: "json",
			async: false,
			success: function(data) {
				//console.log(JSON.stringify(data));
				if (data[0].length <= 0) {
					jQuery.blockUI({ 
						"message":jQuery("#paytippopdiv"),
						"css":{ 
							"width": jQuery("#paytippopdiv").width(),
							"height":jQuery("#paytippopdiv").height()
						}
					});
							
					document.forms["payment_order"].submit();
					
				} else {
					if (data[0].status == '1' && data[0].lcbxFlag == 'true') {
						art.dialog({
							title:'发送提示',
							content: '<div class="tb_mes_des"><span>支付出错：</span><p>'+data[0].message+'</p></div>',
							button: [ { 
							name: '确认', 
							callback: function () { 
								 return true; 
								 },focus: true
							}]
						});
							return;
					}
					else if (data[0][0].status == '-1') {
					    var contentHtml = '<div class="yzmDiv"><p style="padding:0 4px 0 8px; color:#7B7B7B;">'
		      	  				+ '</p><span style="padding:0 4px 0 0; color:#7B7B7B;">&nbsp;&nbsp;  图片验证码:</span>'
		      	  				+ '<input id="inputTPYZM" style="width:4em; padding:4px; margin-top:5px;" class="checkYzm"/> '
		      	  				+ '<img style="width:80px; margin-top: -3px; height:24px;  vertical-align: middle;" id="telValidateCaptchaImage" src="/wj/captcha.jpg?timestamp1428995098451" alt="验证码" onclick="captchaImageRefresh()"/> '
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
				                  } else {
				                  	  jQuery.ajaxLoadImg.show('exchangeid');
					                	 jQuery.ajax({
					                        url:sinosoft.base+'/shop/pay!exchange.action?orderId='+jQuery("#OrdId").val()+'&sdcodef='+jQuery("#inputYZM").val(),
					                        type: "post",
					                        dataType: "json",
					                        success: function(data) {
					                            jQuery.ajaxLoadImg.hide('exchangeid');
					                        	if (data.status =='0') {
					                        		art.dialog({id:'exchanArt'}).close();
						                      		jQuery("#msg").html(data.message);
													jQuery.blockUI({ 
															"message":jQuery("#exchangeSuccDiv"),
															"css":{ 
																"width": jQuery("#exchangeSuccDiv").width(),
																"height":jQuery("#exchangeSuccDiv").height()
															}
													});
													return;
													
												}else if (data.status =='yzmerror') {
													jQuery("#status1").html(data.message);
				                	 				 return false;
						              	        } else {
						              	        	art.dialog({id:'exchanArt'}).close();
									              	if (data.message != null && data.message != '') {
														jQuery("#errMsg").html(data.message);
													}
																
													jQuery.blockUI({ 
														"message":jQuery("#exchangeFailDiv"),
														"css":{ 
															"width": jQuery("#exchangeFailDiv").width(),
															"height":jQuery("#exchangeFailDiv").height()
														}
													});
													return;
						              	        }
					                        }
					                    });
				                  }
				                  return false;
			                }
		                });
						new timeCountDown({
			      	        whichPage: 'yzm',
			      	        endHtml:'发送',
			      	        rebHTML:'重新发送',
			      	        sendCode: function(){
			      	      	  
			      	      	  jQuery.ajax({
			      					url: sinosoft.base+'/shop/points!sendYZM.action?inputTPYZM='+jQuery("#inputTPYZM").val(),
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
						
					} else if (data[0][0].status == '0') {
							jQuery("#msg").html(data[0][0].message);
							jQuery.blockUI({ 
								"message":jQuery("#exchangeSuccDiv"),
								"css":{ 
									"width": jQuery("#exchangeSuccDiv").width(),
									"height":jQuery("#exchangeSuccDiv").height()
								}
							});
							return;
					} else if (data[0][0].status == '1') {
						if (data[0][0].message != null && data[0][0].message != '') {
							jQuery("#errMsg").html("，" + data[0][0].message);
						}
									
						jQuery.blockUI({ 
							"message":jQuery("#exchangeFailDiv"),
							"css":{ 
								"width": jQuery("#exchangeFailDiv").width(),
								"height":jQuery("#exchangeFailDiv").height()
							}
						});
						return;
					}
					for (var i = 0; i < data[0].length; i++) {
						if (data[0][i].status == "error") {
							art.dialog({
								title: "错误提示",
   								content: "校验起保日期出错："+data[0][i].message
							});
							return;
						}
						
						if (data[0][i].status == "false") {
							art.dialog({
								title: "错误提示",
   								content: "订单出错："+data[0][i].message
							});
							return;
						}
						
						if (data[0][i].status == "timeout") {
						    jQuery("#startDate1").html(data[0][i].start);
						    jQuery("#now1").html(data[0][i].now);
						    jQuery("#front").val(data[0][i].front);
						    if (data[0][i].period == null || data[0][i].period == '') {
						    	jQuery("#flag1").html("小于&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						    } else {
						    	jQuery("#flag1").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						    	jQuery("#period1").html("不满足该产品起保日期间隔<span class='span_light_l'> " + data[0][i].period + " 天</span><br>");
						    }
	
							jQuery.blockUI({ 
								"message":jQuery("#timeoutdiv"),
								"css":{ 
							          "width": jQuery("#timeoutdiv").width(),
							          "height":jQuery("#timeoutdiv").height()
							     }
							});
							break;
						} else if (data[0][i].status == "currBeTimeout") {
							jQuery("#startDate2").html(data[0][i].start);
						    jQuery("#endDate2").html(data[0][i].start);
							jQuery("#period2").html(data[0][i].period);
							jQuery.blockUI({ 
								"message":jQuery("#currbetimeoutdiv"),
								"css":{ 
							          "width": jQuery("#currbetimeoutdiv").width(),
							          "height":jQuery("#currbetimeoutdiv").height()
							     }
							});
							break;
						} else if (data[0][i].status == "beTimeout") {
							jQuery("#startDate3").html(data[0][i].start);
						    jQuery("#newStartDate3").html(data[0][i].newStart);
							jQuery("#period3").html(data[0][i].period);
							jQuery("#isModify3").val(data[0][i].isModify);
							jQuery.blockUI({ 
								"message":jQuery("#betimeoutdiv"),
								"css":{ 
							          "width": jQuery("#betimeoutdiv").width(),
							          "height":jQuery("#betimeoutdiv").height()
							     }
							});
							break;
						}else if (data[0][i].status == "morethaninsured") {
							jQuery.blockUI({ 
								"message":jQuery("#morethaninsured"),
								"css":{ 
							          "width": jQuery("#morethaninsured").width(),
							          "height":jQuery("#morethaninsured").height()
							     }
							});
							break;
						}
					}
				}
			}
		});
	}
	
	function isYezf(){
		var balance = jQuery("#balance").val();
		var paypassword = jQuery("#paypassword").val();
		var p_price=jQuery("#p_price").text();
		jQuery.ajax({
			type: 'post',
			url: sinosoft.base+'/shop/pay!isYezf.action?balance='+balance+'&paypassword='+paypassword+'&payPrice='+p_price+'&orderSn='+jQuery("#OrdId").val(),
			dataType: "json",
			async: false,
			success: function(data) {
				if(data.Status==0){
					isYezfflag = data.Message;
				}else{
					isYezfflag = "";
				}
			}
		});
	}
	
	function modifyStartDate() {
		window.location.href = "${base}/shop/order_config_new!buyNowUpdate.action?orderSn="+jQuery("#OrdId").val()+"&orderId="+jQuery("#sdorderId").val()+"&Flag=Suc&KID="+jQuery("#KID").val();
	}
	
	function timeoutBack() {
		if (jQuery("#front").val() == "") {
			window.location.href = "${base}/shop/order_query!queryOrder.action";
		}else {
			window.location.href = jQuery("#front").val();
		}
		
	}
	
	function orderDetailLink() {
		window.location.href = "${base}/shop/order_config_new!linkOrderDetails.action?orderSn="+jQuery("#OrdId").val()+"&KID="+jQuery("#KID").val();
	}
	
	function closeExchangeDiv() {
		jQuery.unblockUI();
	}
	
	function continueSubmit(){
		jQuery.blockUI({ 
			"message":jQuery("#paytippopdiv"),
			"css":{ 
				"width": jQuery("#paytippopdiv").width(),
				"height":jQuery("#paytippopdiv").height()
			}
		});
						
		document.forms["payment_order"].submit();
	}
	
	function doclose(){
		jQuery.unblockUI();
		window.location.reload(); 
	}
	
	function docallBack(){
		jQuery.ajaxLoadImg.show('showid');
		var p_price=jQuery("#p_price").text();
		if(p_price==''||p_price==null){
			window.location.href = "${base}/shop/pay!doCallBack.action?paySn=${paySn}&payType="+jQuery("#payType").val()+"&OrdId=${sdorder.orderSn}&payPrice="+parseFloat(jQuery("#pay_price").text());
		}else{
			window.location.href = "${base}/shop/pay!doCallBack.action?paySn=${paySn}&payType="+jQuery("#payType").val()+"&OrdId=${sdorder.orderSn}&payPrice="+parseFloat(p_price);
		}
	    
	}
	//刷新验证码图片
function captchaImageRefresh(){
	jQuery("#telValidateCaptchaImage").attr("src", "/wj/captcha.jpg?timestamp" + (new Date()).valueOf());
}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<#if (typeFlag =="02")!>
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
</#if>
<#if (typeFlag !="02")!>
	<#if (typeFlag =="03")!>
	<iframe id="iframeA" name="iframeA" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" src="${CpsAgentURL}/front/include/agent_order_head.jsp" width="100%" height=100%></iframe> 
	<#else>
		<#if (sdorder.renewalId != "" && sdorder.renewalId != null)!>
			<#include "/wwwroot/kxb/block/kxb_header_evaluating.shtml">
			<div class="g-header ">
			    <div class="g-weaper">
			        <a href="/" class="m-logo-a">
			            <img src="${staticPath}/images/redesign/logo.gif" height="70px" class="m-logo" alt="">
			        </a>
			        <img src="${staticPath}/images/redesign/f_logo.gif" class="m-flogo" height="70px" alt="">
			        <div class="clear"></div>
		        </div>
	        </div>
		<#else>
			<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
		</#if>
	</#if>
</#if>
<div class="wrapper">
	<!--理财险特有支付 开始-->
	<#if (isLcx =="0")>
	<form action="pay!zjzfbgReply.action" id="payment_order" name="payment_order">
	<#else>
	<form action="pay!show.action" target="_blank" id="payment_order" name="payment_order">
	</#if>
	<input type="hidden" id ="artLoginFlag" name="artLoginFlag" value="1" />
	<input type="hidden" name="payType" id="payType" value="1"/>
	<input type="hidden" name="balance" id="balance" value="${balance}"/>
	<input type="hidden" id="OrdId" name="OrdId" value="${sdorder.orderSn}"/>
	<input type="hidden" id="sdorderId" name="sdorderId" value="${sdorder.id}"/>
	<input type="hidden" name="paySn" id="paySn" value="${paySn}"/>
	<input type="hidden" id="KID" name="KID" value="${KID}"/>
	<input type="hidden" name="typeFlag" value="${typeFlag}"/>
	<input type="hidden" name="CouponSn"  id="CouponSn" value="0"/>
	<input type="hidden" name="offsetPoint"  id="offsetPoint" value="0"/>
	<input type="hidden" id="channelsn" name="channelsn" value="${(sdorder.channelsn)!}"/>
	
	<#if (sdorder.renewalId != "")!>
		<div class="mod_nav_con">
	      <ul class="mod_nav_ul">
	        <li ><span class="num_icon num_icon1"></span>填写投保信息<em></em></li>
	        <li class="sel_nav"><span class="num_icon num_icon2"></span>确认支付<em></em></li>
	      </ul>
	    </div>
	<#else>
		<div class="re-line-log">
	        <ul class="re-line-ul">
	            <li class="re-line-d"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
	            <li class="re-line-d"><div class="re-linehead re-linehead2"><span></span></div><h3 class="re-line-h3">2、确认保单<p>确认信息填写是否正确无误</p></h3><span class="re-line-jiao"></span></li>
	            <li class="re-line-s"><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>
	            <#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")>积分全额兑换<#else>积分折扣、支持多种支付方式</#if></p></h3><span class="re-line-jiao"></span></li>
	        </ul>
	    </div>
    </#if>
    <div class="clear"></div>
	<div class="w900 order-bg">
		<div class="line_a shop_sptitle ">
        	<div class="pay_box">
	 			<div class="ins-tit un_bot_bor">支付清单</div>
	 			<table width="100%" border="1" >
	 			
	 				<tr>
     					<th><span class="ins-tit-c">订单号</span></th>
     					<th colspan="4" ><span class="ins-tit-ordernum"><a href="${base}/shop/order_config_new!linkOrderDetails.action?orderSn=${sdorder.orderSn}&KID=${KID}" target="_blank">${sdorder.orderSn}</a></span></th>
     				</tr>
					<tr>
						<td width="15%" class="ins-tit-td">投保人</td>
						<td class="ins-tit-td">产品名称</td>
						<td width="15%" class="ins-tit-td">份数</td>
						<#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")!>
							<td width="15%" class="ins-tit-td">费用合计</td>
						<#elseif (activityFlag == "0")>
							<td width="15%" class="ins-tit-td">费用合计</td>
						<#else>
							<td width="15%" class="ins-tit-td">原始价格</td>
							<td width="15%" class="ins-tit-td">优惠后价格</td>
						</#if>
					</tr>
					<#list activityMap?keys as itemKey>
							<#assign map_activity = activityMap[itemKey]>
							<#list map_activity?keys as key_activity>
								<#assign map_info = map_activity[key_activity]>
					<tr>
						<td>${map_info.ProductInfo[0].ApplicantName}</td>
						<td >${map_info.ProductInfo[0].ProductName}</td>
						<td >${map_info.ProductInfo[0].InsuredCount}</td>
						<#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")!>
							<td ><font class="pay_jg" id="pay_price">${sdorder.offsetPoint}积分</font><span class="yhj_yhts" id="yhj_tb_des"></span></td>
						<#elseif (activityFlag == "0")>
							<td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_info.ProductInfo[0].Amount}</font>
							<#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if>
							<span class="yhj_yhts" id="yhj_tb_des"></span></td>
						<#else>
						    <td class="pay_zk_money">￥${sdorder.productTotalPrice}</td>
						    <#if (map_info.ActivityInfo.type =="6")>
						    	<td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_info.ProductInfo[0].ActivityeAmount}</font><#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if><span class="yhj_yhts" id="yhj_tb_des"></span></td>
							<#else>
							    <td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_info.ProductInfo[0].Amount}</font><#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if><span class="yhj_yhts" id="yhj_tb_des"></span></td>
							</#if>
						</#if>
					</tr>
					<#if (isLcx !='0')>
					<#if (sdorder.channelsn != "jfsc" && sdorder.channelsn != "wj_jfsc" && sdorder.channelsn != "wap_jfsc")>
					<tr>
						<#if (map_info.ActivityInfo.type == "-1" || map_info.ActivityAmont.DiscountAmount == '0.00' )>
							<td class="clear-td-bor no-yh-td" colspan="5"></td>
						<#else>
							<td colspan="5" class="clear-td-bor">
								<div class="yh_mew_list">
							       <ul class="at_list">
							       <li>
							           <#if (map_info.ActivityInfo.type =="6")>
							           		<span class="tb_icon active_02">
							           <#else>
							           		<span class="tb_icon active_04">
							           	</#if>
							          ${map_info.ActivityInfo.typeName}</span><span class="tb_text">${map_info.ActivityInfo.description}</span></li>
					               </ul>
				        	       <div class="clear"></div>
							    </div>
							</td>
						</#if>
					</tr>
					</#if>
					</#if>
					</#list>
				</#list>
				</table>
				
				<#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")!>
					<div class="jf_concs">
						<span class="js-spanf">1</span>个订单&nbsp;&nbsp;&nbsp;&nbsp;总计：<span class="zj_jf">${sdorder.offsetPoint}</span>&nbsp;积分<div class="h10"></div>
							您剩余积分<em class="js-spanf">${points}</em>个
					</div>
					<a href="javascript:void(0);" onclick="dosubmit('zerozf', '1');" class="jf_gopay">确认支付</a>
				<#else>
				
			<#if (typeFlag !="02" && typeFlag !="03")!>
				<#if (map_pointinfo.givepoints !="0"&&map_pointinfo.givepoints !="0.0"&&map_pointinfo.givepoints !="0.00")>
					<div class="yhhd-des">
						<h3 class="yhhd-span">支付成功即可参加以下优惠活动：</h3>
						<ul class="yhhd-ul">
						<#if (map_pointinfo.pointDesFlag =="true")>
							<li>${map_pointinfo.pointDesStart}<b id="givepoint">${map_pointinfo.givepoints}</b>积分（下次购买可抵扣<b id="givepointvalue">${map_pointinfo.pointValue}</b>元）</li>
						<#else>
							<li>保单生效后可获得<b id="givepoint">${map_pointinfo.givepoints}</b>积分（下次购买可抵扣<b id="givepointvalue">${map_pointinfo.pointValue}</b>元）</li>
						</#if>
						<#if (activitylist?size > 0)>
							<#list activitylist as map>
							<li>${map.index}、${map.title}</li>
							</#list>
						</#if>
						</ul>
					</div>
										
				<#else>
					<#if (activitylist?size > 0)>
						<div class="yhhd-des">
							<h3 class="yhhd-span">支付成功即可参加以下优惠活动：</h3>
							<ul class="yhhd-ul">
								<#list activitylist as map>
								<li>${map.index}、${map.title}</li>
								</#list>
							</ul>
						</div>
					<#else>
						<div class="yhhd-des" style="display:none"></div>
					</#if>
				</#if>
			</#if>
						
						<!--优惠券和活动开始-->
						 <#if (isLcx !="0")>
						<div id="yhj_form" class="pay_postion_box">
							<table class="gwc_js_tables">
								<tr>
									<td class="gwc_js_table"></td>
									<td class="gwc_js_table_w"><span class="gwc_dd">共<i class="" id="ord_num">1</i>个订单&nbsp;&nbsp;总计：</span></td>
									<td class="gwc_js_table_pay">
							            <i class="gwc_dd pay-pic1">￥</i><span class="gwc_dd pay-pic2" id="ord_price">${callBackAmount}</span></td>
								</tr>
								<#list activityMap?keys as itemKey>
									<#assign map_activity = activityMap[itemKey]>
									<#list map_activity?keys as key_activity>
										<#if (key_activity !="_no_activity")>
											<#assign map_info = map_activity[key_activity]>
											<#if (map_info.ActivityInfo.type =="3" && map_info.ActivityAmont.DiscountAmount != '0.00' )>
												<tr id="pay_div">
													<td  class="gwc_js_table"></td>
													<td class="gwc_js_table_w"><span class="gwc_dd">减免：</span></td>
													<td class="gwc_js_table_pay">
														<span class="gwc_dd"><b>￥</b><i>-${map_info.ActivityAmont.DiscountAmount}</i></span>
											        </td>
												</tr>
											</#if>
										</#if>
									</#list>
								</#list>
								<tr id="yhq_price" style="display:none;">
								</tr>
								<tr id="yh_price" style="display:none;">
								</tr>
								<tr>
									<td  class="gwc_js_table"></td>
									<td class="gwc_js_table_w"><span class="gwc_dd">您需要支付：</span></td>
									<td class="gwc_js_table_pay">
										<span class="gwc_dd">
										<#list activityMap?keys as itemKey>
											<#assign map_activity = activityMap[itemKey]>
											<#list map_activity?keys as key_activity>
												<#if (key_activity !="_no_activity")>
													<#assign map_info = map_activity[key_activity]>
													<b class="pay_jg_2 ">￥</b><i class="pay_jg_3 " id="p_price">${map_info.ActivityAmont.RealAmount}</i>
												<#else>
													<b class="pay_jg_2 ">￥</b><i class="pay_jg_3 " id="p_price">${callBackAmount}</i>
												</#if>
											</#list>
										</#list>
										</span>
							        </td>
								</tr>
							 </table>		
						
							<#if (sdorder.channelsn == "cps_dlr" || typeFlag =="03")!>
								<div class="balance_pay">
									<h3 class="balance-tit">您可以选择以下支付方式：</h3>
									<div class="balance_box">
										<p class="balance_zfx">（请确保支付余额在支付限额以内，您可以到银行修改您的支付限额）</p>
										<p>您当前的余额为：${balance}元，需要
												<#list activityMap?keys as itemKey>
													<#assign map_activity = activityMap[itemKey]>
													<#list map_activity?keys as key_activity>
														<#if (key_activity !="_no_activity")>
															<#assign map_info = map_activity[key_activity]>
															${map_info.ActivityAmont.RealAmount}
														<#else>
															${callBackAmount}
														</#if>
													</#list>
												</#list>元才能完成该次支付。</p>
										<p>您可以点击立即支付按钮完成支付</p>
										<p><b>支付密码：</b><input type="password" class="balance_input" name="paypassword" id="paypassword"><span><input type="button" class="balance_btsf" value="确认支付" onclick="dosubmit('yezf', '')"></span></p>
									</div>
								</div>
							</#if>
							<input type = "hidden" id="yhj_orderId" value="${sdorder.orderSn}"/>
							<input type = "hidden" id="loginFlag" value="${loginFlag}" name="loginFlag"/>
							<input type = "hidden" id="shopcartflag" value="0"/>
							<input type = "hidden" id="jf_zero" value="true"/>
							<input type = "hidden" id="memberpoint" value="0"/>
							<input type = "hidden" id="PointScalerUnit" value="10"/>
							<#if (fkIsShow =="Y")!>
								<div class="fx_fkid">
							 	 飞客茶馆用户名<input type="text" id="fkID" name="fkID" maxlength="32" class="fx_fk_txt"> (飞客茶馆用户可返“飞米”，用户兑换礼品，航空公司里程，酒店积分等)
							    </div>
						    <#else>
						    </#if>
							<#if (loginFlag =="true" && typeFlag !="02" && typeFlag !="03")!>
							     <div id="youhui_div" class="yhj_con">
							 <#else>
							     <div style="display: none" id="youhui_div" class="yhj_con">
							 </#if>
							 		<h3 class="pay_make_yh" id="pay_yhj_gl">使用优惠券抵消部分总额</h3>
							 		<div class="pay_quan" style="display: none" id="pay_yhj_box">
							 			<span class="font-hscs">请选择使用的优惠券</span>
							 			<ul class="yhj-des-link">
											<li class="yhj-deslink1">如何得到优惠券?<span class="yhj_jts" style="display:none;">关注开心保微信公众号参与互动，或通过网站活动页面领取即可获得优惠券，优惠券可在会员中心查看</span></li>
											<li class="yhj-deslink2">优惠券如何使用?<span class="yhj_jts" style="display:none;">输入优惠券码或勾选优惠券即可使用优惠券</span></li>
										</ul>
										<div class="pre_yh_txt" >
											<ul class="pre_yh_li cf">
												<li class="pre_yh_li1">优惠券代码</li>
												<li class="pre_yh_li2"><input type="text" class="jhm_texts" id="jhm_text"/></li>
												<li class="pre_yh_li3"> <input type="button"  class="pay_jf_btn" id="yhj_jh_button" value="使用"></li>
											</ul>
										</div>
										<div id="yhj_list" class="pay_jf_tdjn" style="display:none">
											<img src="${staticPath}/images/loading2.gif" alt="" />
										</div>
									</div>
							 		<h3 class="pay_make_yh" id="pay_jf_gl">使用积分抵消部分总额</h3>
							 		<div class="clear"></div>
							 		<div class="pay_jifen" style="display: none" id="pay_jf_box">
							 			<table class="jf_table">
							 				<tr id="jf_sy" >
							 					<td class="pay_jf_td pay_jf_td1">本次使用</td>
							 					<td class="pay_jf_td pay_jf_td2"><input type="text"  class="pay_jf_txt" name="point_dz"/></td>
							 					<td class="pay_jf_td pay_jf_td3">积分支付</td>
							 					<td class="pay_jf_td pay_jf_td4" id="jf_sy_qx_botton"><input type="button" value="使用"  id="sy_botton" class="pay_jf_btn" onclick="jfsy()"/></td>
							 					<td class="pay_jf_td pay_jf_td5" id="jfsy_span"></td>
							 				</tr>
							 				<tr id="jf_qx" style="display:none;">
							 					<td class="pay_jf_td pay_jf_td1">本次使用</td>
							 					<td class="pay_jf_td pay_jf_td2"><input type="text" class="pay_jf_txt" name="point_dz" readonly="readonly"/></td>
							 					<td class="pay_jf_td pay_jf_td3">积分支付</td>
							 					<td class="pay_jf_td pay_jf_td4"><input type="button" value="取消" class="pay_jf_btn pay_quxiao" onclick="jfqx()" /></td>
							 					<td class="pay_jf_td pay_jf_td5" id="jfqx_span"></td>
							 				</tr>
							 			</table>
							 			<div class="pay_jf_td6">您有<span id="member_jf_one"></span>积分，本次最多可使用<span id="member_jf_two" ></span>积分</div>
							 		</div>
								</div>
							</div>
							<!--优惠券和活动结束-->
							 </#if>		
							<div id="jfzf_botton" class="jf_zhifu" style="display:none;"><input type="button" class="jf_zf_btn" onclick="dosubmit('zerozf', '')"/></div>
					</#if>	
     				</div>
 					<div class = "clear">clear</div>
 					
 					<!--理财险特有支付 开始-->
 					<#if (isLcx =="0")>
 					<div class="pay_box">
					    <div class="ban_pay" style=display:none; id="pay_zero_box">
							<div class="banpay_con">
								<span class="ban_header"></span><span class="ban_con">亲爱的用户，点击“<em class="red">确认支付</em>”即可成功支付本次订单哦！</span>
							</div>
						</div>
	 					<div class="ins-tit">银行卡支付</div>
      					<table class="lcBankPay">
      						
                            <input type="hidden" id="bussNo" />
                            <input type="hidden" id="validateStatus" />
                            <input type="hidden" id="bankCode" />
                            <input type="hidden" id="bankAccNo" />
                            <tr>
                                <td class="lcBanktd">持卡人姓名：</td>
                                <td>${applicantName}<span class="lcTipMes">持卡人姓名必须与投保人一致，如想修改请
                                <a href="${base}/shop/order_config_new!buyNowUpdate.action?orderId=${sdorder.id}&productId=${sdinformation.productId}&orderSn=${sdorder.orderSn}&orderFlag=${orderFlag}&KID=${KID}&order_healthySn=${sdorder.orderSn}" >返回修改投保人</a>
                                </span></td>
                            </tr>
                            
                            <tr class="lcBindList" style="display:none;">
                                <td class="lcBanktd">已绑定银行卡：</td>
                                <td><select id="bankInfoSel" class="lcBankSel" name="bankInfoSel">
                                <#if bindBankInfo??> 
                                <#list bindBankInfo?keys as key> 
				                   <option value="${key}">${bindBankInfo.get(key)}</option>
				                </#list>
				                </#if>
				                <!-- <a href="${base}/shop/order_config_new!pay.action?orderSn=${sdorder.orderSn}&orderId=${sdorder.id}&changeCard=true&KID=${KID}">其它银行卡支付</a> -->
                                </select><span class="lcOtherBank">其它银行卡支付</span>
                                    <#--<span class="lcTipMes">点击验证后请登录银行卡查看转账金额</span>-->
                                </td>
                            </tr>
                            <tr class="lcBankAdd lcBank_hide" style="display:none;">
	                            <td class="lcBanktd">银行-卡号：</td>
	                                <td class="pos-tb-p pos_td_fsf checkinput">
	                                <select class="lcBankSel lcBankSels" id="bank_sel" name="bank_sel">
	                                <#list lcxBank as list>
			                       		<option value="${list.flagType}">${list.codeValue}</option>
			                   	  	</#list>
	                                </select>
	                             	<p class="pos_td_cs"><input type="text" class="lcBankNum" id="bank_no"></p><label for="bank_no" class="app_mobile">
	                              		 请输入卡号</label><label class="requireField"></label><font color="#E7AC59" style="display: none;"> <i class="yz_mes_des">请填写与银行卡号</i></font>
	                                   <#-- <span class="lcTipMes">每卡可验证<i>3</i>次，超过<i>3</i>次需联系客服</span>-->
	                                    <input type="hidden" value="" id="BankID">
	                            </td>
                            </tr>
                            <tr>
                                <td class="lcBanktd">预留手机号：</td>
                                <td>${applicantMobile}<span class="lcTipMes">预留手机号必须与投保人手机号一致，如想修改请
                                <a href="${base}/shop/order_config_new!buyNowUpdate.action?orderId=${sdorder.id}&productId=${sdinformation.productId}&orderSn=${sdorder.orderSn}&orderFlag=${orderFlag}&KID=${KID}&order_healthySn=${sdorder.orderSn}" >返回修改投保人</a>
                                </span></td>
                            </tr>
                            <tr>
                                <td class="lcBanktd">验证码：</td>
                                <td><input type="text" class="lcBankTex" id="vCode" /><em class="lcYzMes "></em>
									<span class="code_again">获取验证码验证</span><#--<span class="lcTipMes">点击验证后请查看您收到的手机短信</span>--></td>
                            </tr>
                            <tr>
                                <td class="lcBanktd lcBankHei"></td>
                                <td class="pay_card lcBankHei"><label for="accept_p" class="accept_p"><input type="checkbox" id="accept_p" checked>已阅读并同意</label><span class="lcSQ">《授权委托书》</span></td>
                            </tr>
                            <tr>
                                <td class="lcBanktd"></td>
                                <td><input id="lcPayBtn" type="button" class="lcBankSub" value="确认支付" /></td>
                            </tr>
                            <tr>
                                <td colspan="2"></td>
                            </tr>
                        </table>
					</div>
						<!--授权用字段-->
						<input type="hidden" id="appname" name="appname" value="${applicantName}"/>
						<input type="hidden" id="cardid" name="cardid" value="${applicantIdentityid}"/>
						<input type="hidden" id="uname" name="uname" value="${loginname}"/>
						<input type="hidden" id="curdate" name="curdate" value="${curdate}"/>
						<input type="hidden" id="Amount" name="Amount" value="${callBackAmount}"/>
						
 					</#if>
 					<!--理财险特有支付 结束-->
 					
 					<#if (isLcx !="0")>
 					<#if (sdorder.channelsn != "jfsc" && sdorder.channelsn != "wj_jfsc" && sdorder.channelsn != "wap_jfsc")!>
				  	<div class="pay_box">
					    <div class="ban_pay" style=display:none; id="pay_zero_box">
							<div class="banpay_con">
								<span class="ban_header"></span><span class="ban_con">亲爱的用户，点击“<em class="red">确认支付</em>”即可成功支付本次订单哦！</span>
							</div>
						</div>
	 					<div class="ins-tit">请选择付款方式</div>
      					<div class="pay_zhifu">
				        	<div class="platform">
				            	<span class="pt-tags">支付平台</span>
					            <ul class="platform_list clearfix">
					            	<#assign index = 1>
					            	<#list bank1 as bank>
					            		<#if index%5 == 0>
				                    		<li class="clear-msf">
				                    	<#else>
				                    		<li>
				                    	</#if>
										<a href="javascript:void(0);" onclick="dosubmit('${bank.payType}', '')"></a><img  class="select_bank" src="${staticPath}/style/shop/images/pay/${bank.image}" width="150" height="38" alt="${bank.description}"/></li>
										<#assign index = index + 1>
									</#list>
					            </ul>
				            </div>
				            <div class="pay_bank" id="tag_box">
				            <#if ((bank3 != null && bank3?size > 0) || (bank2 != null && bank2?size > 0))>
				            	<ul class="tag_banktitile">
				            		<li class="select_banks"><a href="###">网银支付</a></li>
				            		 
				            		<li class=""><a href="###">企业网银</a></li>
				            	</ul>
            					<div id="pay_bank_box" class="pay_bank_boxs" >
				                	<div class="bank_list clearfix">
				                    	<ul>
				                    		<#assign index = 1>
				                    		 <#list bank3 as bank>
				                    		 	<#if index%5 == 0>
				                    		 	    <li class="clear-msf">
				                    		 	<#else>
				                    		 	    <li>
				                    		 	</#if>
									              <a href="javascript:void(0);" onclick="dosubmit('${bank.payType}', '')"></a><img  src="${staticPath}/style/shop/images/pay/${bank.image}" width="150" height="38" alt="${bank.description}"/></li>
									         	  <#assign index = index + 1>
									         </#list>
				                    	</ul>
                					</div>
				                    <div class="bank_list clearfix" style="display:none;">
				                    	<ul>
				                    		<#assign index = 1>
					                    	 <#list bank2 as bank>
					                    	 	<#if index%5 == 0>
				                    		 	    <li class="clear-msf">
				                    		 	<#else>
				                    		 	    <li>
				                    		 	</#if>
										          <a href="javascript:void(0);" onclick="dosubmit('${bank.payType}', '')"></a><img src="${staticPath}/style/shop/images/pay/${bank.image}" width="150" height="38" alt="${bank.description}"/></li>
										     	<#assign index = index + 1>
										     </#list>
									    </ul>
				                    </div>
            
            					</div>
            					</#if>
      						</div>
     					</div>
     
					</div>
					</#if>
					</#if>
			</div>
   			<div class="clear"></div>
 		</div>
 		<div class="clear"></div>
	</form>
</div>
<#if (typeFlag !="02")!>
	<#if (typeFlag =="03")!>
	<link rel="stylesheet" type="text/css" href="${CpsAgentURL}/style/def_web.css"/>
		 <div class="footerArea">
			<ul>
				<li><a href="http://www.kaixinbao.com/images/bxdlywxkz.jpg" target="_blank" rel="nofollow">保险代理业务许可证：网金保险销售服务有限公司210017000000800未经许禁止转载</a></li>
				<li><a href="#" target="_blank" rel="nofollow">中国保险监督管理委员会互联网业务备案</a></li>
				<li class="last"><a href="http://www.miitbeian.gov.cn" target="_blank" rel="nofollow">辽ICP备12007009号-1</a></li>
			</ul>
			<address>
				<p>Copyright&copy;2013-2021 kaixinbao.com.ALL Rights Reserved</p>
			</address>
		</div>
	<#else>
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
	</#if>
</#if>
<!-- 百分点发送逻辑 begin -->
<script type="text/javascript" src="${shopStaticPath}/jquery.cookie.js"></script>
<script type="text/javascript">
<#--/* //百分点合作结束，百分点核心代码暂存
try{
	window["_BFD"] = window["_BFD"] || {};
	_BFD.BFD_INFO = {
		"order_id" : jQuery("#OrdId").val(),   //当前订单号，如果有拆单等特殊情况现象（一次购买中出现多个订单号）此页面代码不可用，请联系我修改；
		"order_items" : [['${map_info.ProductInfo[0].ProductID}',jQuery("#p_price").text(),1]],   //同购物车页
		"total" : jQuery("#p_price").text(),   //用户实际支付的价格
		"payment" : "在线支付",   //支付方式 
		"express" : "",   //快递方式 不传
		"user_id" : jQuery.cookie("loginMemberId")==null?"":jQuery.cookie("loginMemberId"), //网站当前用户id，如果未登录就为0或空字符串
		"page_type" : "order" //当前页面全称，请勿修改
	};
}catch(e){
	
}
*/-->
</script>
<!-- 百分点发送逻辑 end -->
<#include "/wwwroot/kxb/block/community_v1.shtml">

<div id="paytippopdiv" style="background-color:white;width: 432px ;height:180px;border:1px solid #E5E5E5;display:none; " class="ColumnArea__" >
	<div class="TitleArea"><div class="TitleTxt">支付提示</div></div>
	<div class="ContentNew" >
		<#if (isLcx !="0")>
		<p  class="content_s"> 请您在新打开的页面进行支付，支付完成前请不要关闭新窗口</p>
		<button onclick="docallBack();"  >&nbsp;&nbsp;已完成支付&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
		<a vlpageid="xiaoneng"  href="javascript:void(0);" rel="nofollow" class="button-dsfsf" exturl="http://www.kaixinbao.com/xiaoneng"   id="qqwap2" onclick="return(VL_FileDL(this));return false;" >遇到问题？联系在线客服</a> <br>
		<#else>
		<div class="tip_message_box">
	      <div class="tip_mes_box">
	        <p>收到金主指令，您的订单正在提交中，请不要关闭本页面，耐心等待订单结果...<br /></p>
	      </div>
	    </div>
		</#if>
		<#if (isLcx !="0")>
		<a href="javascript:void(0);" onclick="doclose();" rel="nofollow"  class="pay_ser">更换其他支付方式></a>
		</#if>
    </div>
</div>
<div class="clear"></div>
<div id="timeoutdiv" style="background-color:white;width: 432px ;height:200px;border:1px solid #E5E5E5; display:none; " class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">温馨提示</div>
      </div>
      <br>
      <div class="pay_tip_box" style="padding-left:15px">
      		<p class="content_s"> 您选择的起保日期：<span id="startDate1" class='span_light_l'></span>
      		<br><span id="flag1"></span>当前日期：<span id="now1" class='span_light_l' ></span>
      		<br><span id="period1" ></span>
      		<b class="pay_tip_time_b">请您修改起保日期，谢谢！</b>
      		</p>
      		
      		<input id="front" type='hidden' value="">
      </div>
      <div class="Content" >
      		<button onclick="timeoutBack();"  >&nbsp;&nbsp;返回&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
			<button onclick="modifyStartDate();" class="button-dsfsf">修改起保日期</button>
	  </div>
</div>
<div class="clear"></div>
<div id="currbetimeoutdiv" style="background-color:white;width: 432px ;border:1px solid #E5E5E5; display:none;" class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">温馨提示</div>
      </div>
       <br>
      <div class="pay_tip_box" style="padding-left:15px">
      		<p class="content_s"> 
      		离您选择的终止时间：<span id="endDate2" class='span_light_l'></span>还剩余<span class='span_light_l'><span id="period2" ></span>分钟</span>，
      		<br>建议您选择<span id="startDate2" class='span_light_l'></span>&nbsp;&nbsp;以确保有充裕的时间完成支付和承保。
      		</p>
      </div>
      <div class="Content" >
      		<button onclick="alert('请修改起保日期');return false;"  >&nbsp;&nbsp;立即支付&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
			<button onclick="modifyStartDate();" class="button-dsfsf">修改起保日期</button>
	  </div>
</div>
<div class="clear"></div>
<div id="betimeoutdiv" style="background-color:white;width: 432px ;border:1px solid #E5E5E5; display:none;" class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">温馨提示</div>
      </div>
      <br>
      <div class="pay_tip_box" style="padding-left:15px">
      		<p class="content_s"> 
      		离您选择的起保日期：<span id="startDate3" class='span_light_l'></span><span class='span_light_l'>还剩余<span id="period3" ></span>分钟</span>，
      		<br>建议您选择<span id="newStartDate3" class='span_light_l'></span>&nbsp;&nbsp;以确保有充裕的时间完成支付和承保。
      		</p>
      		
      		<input id="isModify3" type='hidden' value="">
      </div>
      <div class="Content" >
      		<button onclick="if (jQuery('#isModify3').val() == '1') {alert('请修改起保日期');return false;};continueSubmit();"  >&nbsp;&nbsp;立即支付&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
			<button onclick="modifyStartDate();" class="button-dsfsf">修改起保日期</button>
	  </div>
</div>
<div id="morethaninsured" style="background-color:white;width: 432px ;border:1px solid #E5E5E5; display:none;" class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">温馨提示</div>
      </div>
   <div style="padding-left:15px" class="pay_tip_box">
      <p class="content_s"> 
      		本次需承保人数多于20人，离您选择的起保日期剩余不足15分钟,此时支付可能无法正常承保，请选择
      	</p>
      </div>
      <div class="Content" >
      		<button onclick="continueSubmit();">&nbsp;&nbsp;继续支付&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
      		<button onclick="modifyStartDate();" class="button-dsfsf">修改起保日期</button>&nbsp;&nbsp;&nbsp;
	  </div>
</div>
<div id="exchangeFailDiv" style="background-color:white;width: 432px ;border:1px solid #E5E5E5; display:none;" class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">支付提示</div> <div class="close_col" onclick="closeExchangeDiv();"></div>
      </div>
      <br>
     <div class="pay_tip_box" style="padding-left:15px">
      <p class="content_s content-sfu"> 
      		支付失败<span id="errMsg" ></span>
      	</p>
      </div>
      <div class="Content" >
      		<button  onclick="closeExchangeDiv();">&nbsp;&nbsp;继续支付&nbsp;&nbsp;</button>
	  </div>
</div>
<div id="exchangeSuccDiv" style="background-color:white;width: 432px ;border:1px solid #E5E5E5; display:none;" class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">支付提示</div> <div class="close_col" onclick="orderDetailLink();"></div>
      </div>
      <br>
      <div class="pay_tip_box" style="padding-left:15px">
      <p class="content_s content-sfu"> 
      		<span id="msg" ></span>
      	</p>
      </div>
      <div class="Content" >
      		<button onclick="orderDetailLink();">&nbsp;&nbsp;确定&nbsp;&nbsp;</button>
	  </div>
</div>
<script	type="text/javascript" src="${shopStaticPath}/iframeTools.js"></script>
 <script type="text/javascript" charset="utf-8" src="${shopStaticPath}/template/common/js/jquery.blockUI.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.validate.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.validate.methods.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.validate.cn.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/shop/js/pay.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
 <!-- 小能在线系统star -->
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/xiaoneng_CustomerService.js"></script>
<!-- 小能在线系统 end -->
 <script language="javascript" type="text/javascript">
 if("${typeFlag}"=="02"){
	sethash();
 }
   function payLoginAfterAppnt(){
 	 window.location.reload() 
 }
</script>
<script>
        jQuery(document).ready(function($) {
        	if (jQuery("#bankInfoSel option").size() > 0) {
        		jQuery(".lcBindList").show();
        	} else {
        		jQuery(".lcBank_hide").show();
        	}
        	//实例化倒计时
	        new timeCountDown({
	          whichPage: 'sign',
	          sendCode:authenticationApply, 
			  deadLine:120, 
				startHtml: '<em>120</em>秒后重发',
	        });
	        function checkBankNUm(){
				var bool = true;
			   	if(jQuery(".lcBankAdd:visible").length>0){
			    var bankNum = jQuery.trim(jQuery(".lcBankNum").val());
			       if(bankNum==""){
			           jQuery(".yz_mes_error").remove();
			           jQuery(".lcBankNum").after('<i class="yz_mes_error">请输入卡号</i>');
			           bool = false;
			          return;
			       }
			       if(isNaN(bankNum)){
			          jQuery(".yz_mes_error").remove();
			          jQuery(".lcBankNum").after('<i class="yz_mes_error">请输入数字卡号</i>');
			          bool = false;
			          return;
			        }
			        if(bankNum.length<16){
			           jQuery(".yz_mes_error").remove();
			           jQuery(".lcBankNum").after('<i class="yz_mes_error">输入位数错误</i>');
			           bool = false;
			          return;
			       }
                    
			        jQuery(".yz_mes_error").remove();
			        return bool;
			  	}
			}
			
			jQuery(".lcOtherBank").click(function(){
               jQuery(this).parent().parent().hide().siblings(".lcBank_hide").show();
            });
            jQuery(".lcBankNum").blur(function(event) {
                 checkBankNUm();
            });
            jQuery('.pos-tb-p input').focus(function() {
			    jQuery(this).parent().siblings('.app_mobile').hide()
			});
			jQuery('.pos-tb-p input').blur(function() {
			    if (jQuery(this).val() != '') {
			        jQuery(this).parent().siblings('.app_mobile').hide()
			    } else {
			        jQuery(this).parent().siblings('.app_mobile').show()
			    }
			});
			
        	//支付按钮
            jQuery("#lcPayBtn").click(function(){
            	//授权委托书
            	var check = jQuery("#accept_p").is(':checked');
            	var bankCode;
		        var bankAccNo;
				var display =jQuery('.lcBindList').css('display');
				if(display == 'none'){
					bankAccNo = jQuery("#bank_no").val();
					bankCode = jQuery("#bank_sel option:selected").val();
				} else {
					var bis = jQuery("#bankInfoSel option:selected").val();
					var arr = bis.split('!');
					bankAccNo = arr[0];
					bankCode = arr[1];
				}
            	
            	if(!check){
            		art.dialog({
                        title:'提示',
                        content:'未同意《授权委托书》!'
                    });
                }else if(bankCode == null || bankAccNo == null || bankCode =="" || bankAccNo == ""){
                	art.dialog({
                        title:'提示',
                        content:'银行卡信息不能为空!'
                    });
            	}else{
					checkBankNUm();
	                dosubmit("zjzf", "");
				}
             });
                 
            // 显示授权委托书
            jQuery(".lcSQ").click(function(event) {
                var appname =jQuery("#appname").val();
                var cardid =jQuery("#cardid").val();
                var uname =jQuery("#uname").val();
                var bank_sel =jQuery("#bank_sel  option:selected").text();
                var bank_no =jQuery("#bank_no").val();
                var curdate =jQuery("#curdate").val();
                var Amount =jQuery("#Amount").val();
                var bankCodeName;
		        var bankAccNo;
		        var display =jQuery('.lcBindList').css('display');
				if(display == 'none'){
					bankAccNo = jQuery("#bank_no").val();
					bankCodeName = jQuery("#bank_sel option:selected").text();
				} else {
					var bis = jQuery("#bankInfoSel option:selected").val();
					var arr = bis.split('!');
					bankAccNo = arr[0];
					bankCodeName = jQuery("#bankInfoSel option:selected").text().split(" ")[0];
				}
                
                // 判断是否输入银行卡
                if(bankCodeName != null && bankCodeName != '' && bankAccNo != null && bankAccNo != ''){
                    art.dialog({
                    title:'授权委托书',
                    content: '<div class="lcWtCon"><div style="text-align:center;">《授权委托书》</div>'+
                                '<p>授权人姓名：'+appname+'<br>'+
                                '证件类型：身份证<br>'+
                                '证件号码：'+cardid+'<br>'+
                                '开心保用户名：'+uname+'<br>'+
                                '授权日期：'+curdate+'<br>'+
                                '</p>'+
                                '<p>被授权人：百年人寿保险股份有限公司（以下简称“百年人寿”）就授权人向其开心保用户名项下账户（“授权人开心保账户”）充值、扣款的相关事宜向百年人寿授权如下：'+
                                '</p>'+
                                '<p>一、授权人授权百年人寿根据授权人的充值指令从本授权书第二条所述的授权人的银行账户通过银行或开心保指定的第三方支付机构（以下统称为“第三方机构”）主动扣收本授权书第二条所述的等值于充值金额的款项，用于向授权人开心保账户充值（“充值服务”）。</p>'+
                                '<p>二、授权人的银行账户及充值金额如下：</p>'+
                                '<p>户名: '+appname+'<br> '+
                                '账号：'+bankAccNo+'<br> '+
                                '开户银行：'+bankCodeName+'<br>  '+
                                '充值金额：人民币'+Amount+' 元（含第三方机构需收的手续费（如有））<br> </p>'+
                                '<p>三、授权人知晓并确认，本授权书系使用授权人开心保用户名、以网络在线点击确认的方式向百年人寿发出。 </p>'+
                                '<p>本授权书自授权人在开心保网站点击确认时生效，由百年人寿通过第三方机构从授权人的银行账户中代扣相当于充值金额的款项。 授权人已经通过本授权书确认代扣款项的银行账户信息，在代扣的过程中，百年人寿根据本授权书提供的银行账户信息进行相关操作，无需再向授权人确认银行账户信息和密码。授权人确认并承诺，开心保根据本授权书所采取的全部行动和措施的法律后果均由授权人承担。 </p>'+
                                '<p>四、授权人知晓并同意,受授权人银行账户状态、银行、第三方支付机构及网络等原因所限, 本授权书项下充值可能会通过多次代扣交易方可完成,百年人寿不对充值服务的资金到账时间做任何承诺。百年人寿或第三方机构仅根据本授权书所述的授权人的授权进行相关操作, 百年人寿或第三方机构无义务对其根据本授权书所采取的全部行动和措施的时效性和结果承担任何责任。</p> '+
                                '<p>五、本授权委托书有效期限为一年。</p>'+
                                '<p>特此授权。</p> '+
                                '</div>',
		                    button: [ { 
		                    name: '同意', 
		                    callback: function () { 
		                     return true; 
		                     },
		                     focus: true
		                   }]
		                });
                }else{
                      art.dialog({
                        title:'提示',
                        content:'请先验证银行卡信息'
                       });
                }
        	});
        });
        //给银行卡发送小额验签
        function cardCheckSend () {
		var od = jQuery("#OrdId").val();
		var bs = jQuery("#bank_sel  option:selected").val();
		var nb = jQuery("#bank_no").val();
		if(bs!=null && nb!= null && bs !="" && nb != ""){
			jQuery.ajax({
			type: 'post',
			url: sinosoft.base+'/shop/licai_baoxian!cardCheckSend.action?orderSn='+od+'&bankCode='+bs+'&bankAccNo='+nb,
			dataType: "json",
			async: true,
			success: function(data) {
					// console.log(JSON.stringify(data))
					var status = data['status'];
					jQuery("#bussNo").val(data.BussNo);
					if(status == '1'){
						status = "失败";
					}
					if(status == '0'){
						jQuery("#validateStatus").val(data.validateStatus);
						status = "成功";
					}
					 art.dialog({
						title:'发送提示',
						content: '<div class="tb_mes_des"><span>验证发送'+status+'！</span><p>'+data['MSG']+'</p></div>',
						button: [ { 
						name: '确认', 
						callback: function () { 
							 return true; 
							 },focus: true
						}]
					});
				}
			});
		}
		else{
			art.dialog({
				title:'发送提示',
				content: '请先验证银行卡信息',
				button: [ { 
				name: '确认', 
				callback: function () { 
					 return true; 
					 },focus: true
				}]
			});
		}
	}
    //给银行卡发送小额验签
    function cardCheckSearch () {
		if (jQuery("#bussNo").val() == '' || jQuery("#validateStatus").val() == '1') {
			return;
		}
		jQuery.ajax({
			type: 'post',
			url: sinosoft.base+'/shop/licai_baoxian!cardCheckSearch.action?orderSn='+jQuery("#OrdId").val()+'&bussNo='+jQuery("#bussNo").val(),
			dataType: "json",
			async: false,
			success: function(data) {
				//console.log(JSON.stringify(data))
	        }
	    });
	}
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

        /**
		 * 向银行发送鉴权申请.
         */
	function authenticationApply(){
            var od = jQuery("#OrdId").val();
            var bs;
            var nb;
            var display =jQuery('.lcBindList').css('display');
            if(display == 'none'){
                bs = jQuery("#bank_sel  option:selected").val();
                nb = jQuery("#bank_no").val();
            } else {
                var bis = jQuery("#bankInfoSel option:selected").val();
                var arr = bis.split('!');
                nb = arr[0];
                bs = arr[1];
            }
            if(bs!=null && nb!= null && bs !="" && nb != ""){
                jQuery.ajax({
                    type: 'post',
                    url: sinosoft.base+'/shop/licai_baoxian!authenticationApply.action',
					data:{
                        orderSn:od,
                        bankCode:bs,
                        bankAccNo:nb
					},
                    dataType: "json",
                    async: true,
                    success: function(data) {
                        // console.log(JSON.stringify(data))
                        var status = data['status'];
                        jQuery("#bussNo").val(data.BussNo);
                        if(status == '1'){
                            status = "失败";
                        }
                        if(status == '0'){
                            jQuery("#validateStatus").val(data.validateStatus);
                            status = "成功";
                        }
                        art.dialog({
                            title:'发送提示',
                            content: '<div class="tb_mes_des"><span>'+status+'！</span><p>'+data['MSG']+'</p></div>',
                            button: [ {
                                name: '确认',
                                callback: function () {
                                    return true;
                                },focus: true
                            }]
                        });
                    }
                });
            }
            else{
                art.dialog({
                    title:'发送提示',
                    content: '请先验证银行卡信息',
                    button: [ {
                        name: '确认',
                        callback: function () {
                            return true;
                        },focus: true
                    }]
                });
            }
	}
    </script>
</body>
</html>
 