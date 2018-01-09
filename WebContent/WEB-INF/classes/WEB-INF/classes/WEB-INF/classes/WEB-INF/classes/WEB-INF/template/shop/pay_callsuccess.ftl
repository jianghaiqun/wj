<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css">
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"  />
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if> 
<div class="wrapper order-bg">
<input type="hidden" id="OrdId" name="OrdId" value="${orderId}"/>
<input type="hidden" id="jrhsURL" name="jrhsURL" value="${jrhsURL}"/>
<#if (typeFlag  == "02")!>
	<div class="order-mes-des">
		<img src="${staticPath}/style/shop/images/success.gif" alt="" width="260px" height="260px" class="order-mes-img">
		<div class="order-mesbox">
			<h2 class="mesbox-tit f_mi">恭喜！您已支付成功</h2>
			<span class="mesbox-num">订单编号&nbsp;&nbsp;&nbsp;${orderId}</span>
			<span class="mesbox-num ">实际支付&nbsp;&nbsp;<em class="f_mi"><i>￥</i>${callBackAmount}</em></span>
			<div id="inswindow">
            </div>
    	</div>
    	<div class="clear"></div>
		<p class="o-mesc-p">尊敬的用户，电子保单将会在30分钟内发送到您的邮箱，请注意查收！如未收到电子保单，请及时联系我们的客服。</p>
    </div>
<#else>
	<div class="order-mes-des">
		<img src="${staticPath}/style/shop/images/success.gif" alt="" width="260px" height="260px" class="order-mes-img">
		<div class="order-mesbox">
			<h2 class="mesbox-tit f_mi">恭喜！您已支付成功</h2>
			<span class="mesbox-num">订单编号&nbsp;&nbsp;&nbsp;${orderId}</span>
			<span class="mesbox-num ">实际支付&nbsp;&nbsp;
			<#if (channelsn  != "jfsc" && channelsn  != "wj_jfsc" && channelsn  != "wap_jfsc")>
			<em class="f_mi"><i>￥</i>${callBackAmount}</em>
			<#else>
			<em class="f_mi">${callBackAmount} 积分</em>
			</#if>
			</span>
			
			<#if (map_point_result != null && map_point_result.status=="0")!>
				<div class="order-mesc cf">
				    <#if (map_point_result.GradeClass != "" && map_point_result.GradeClass != null)>
				    尊敬的<i class="${map_point_result.GradeClass}" ></i>会员 <br>
				    </#if>
				    <p class="order-mesc-p">     保单生效后您将获得${map_point_result.Points}积分（下次购买可抵扣${map_point_result.PointsValue}元）</p>
				    <a href="${front}/about/xszn/index.shtml#xszn5" class="order-mesc-a">如何使用积分？</a>
			    
				</div>
			<#else>
				<div class="order-mesc cf" style="display:none;"></div>
			
			</#if>
		</div>
		<div class="clear"></div>
		<p class="o-mesc-p">尊敬的用户，电子保单将会在30分钟内发送到您的邮箱，请注意查收！如未收到电子保单，请及时联系我们的客服。</p>
	</div>
	<div class="bore-des-h clear"></div>
	<!-- 百分点产品推荐 -->
	  <!-- <div class="bfd_cp_box" style="display:none;"> -->
	  <!-- <span class="bfd_tit">继续看看</span> -->
	  <!-- <div class="changeBox_bfd" id="change_bfd"> -->
	      
	 <!--  </div> -->
	 <!--  </div> -->
	<!-- 百分点产品推荐end -->
	<#if (channelsn  != "jfsc" && channelsn  != "wj_jfsc" && channelsn  != "wap_jfsc")>
	<p class="bore-csj-p">接下来，您可以：</p>
	<div class="order-weichart cf">
        <h3 class="or-mes-tit"><b  class="f_mi">关注微信公众号</b></h3>
        <img src="${staticPath}/style/shop/images/weichart_07.gif"  width="102px" height="102px" class="or-weichartimg">
         <div class="wiechat-des">
             <b class="f_mi">扫一扫关注后，您可以</b>
             <ul><li>随时随地咨询理赔问题</li>
                 <li>手机也可以购买保险</li>
                 <li>获取最新的优惠活动</li></ul>
         </div>
    </div>

    <div class="order-orter">
        <a href="${base}/shop/order_query!list.action" class="order-aooa order-aooa2 f_mi">查看订单</a>
        <a href="/" class="order-aooa f_mi">浏览其他商品</a>
    </div>
     <div class="clear h20"></div>
     <div class="bore-des-h clear"></div>
     <div class="clear h20"></div>
	<#assign num = 1 />
	<#list commentList as list>
		<div class="order-mes-pj" id="comment_${list.orderSn}">
			<h3 class="or-mes-tit"><b class="f_mi">评价商品<em class="order_num_pl">（订单号：${list.orderSn}）</em></b><span class="or-mes-p">评价商品提升会员等级，获得更多优惠</span></h3>
	        <div class="success_plcs" style="display:${list.disCommented}">评论已提交，您已获得&nbsp;<i>${list.points}</i>&nbsp;积分！</div>
	        <p class="order_tits" style="display:${list.disComment}">购买商品：${list.productName}</p>
	        <div class="order-xing " style="display:${list.disComment}">
                <div class="shop-rating">
                    <span class="title">保障范围</span>
                    <ul class="rating-level" id="stars1_${list.orderSn}">
                        <li><a class="one-star" star:value="1" href="#">1</a></li>
                        <li><a class="two-stars" star:value="2" href="#">2</a></li>
                        <li><a class="three-stars" star:value="3" href="#">3</a></li>
                        <li><a class="four-stars" star:value="4" href="#">4</a></li>
                        <li><a class="five-stars" star:value="5" href="#">5</a></li>
                    </ul>
                    <span class="result" id="stars1_${list.orderSn}_tips"></span>
                    <input type="hidden" id="stars1_${list.orderSn}_input" name="stars1_${list.orderSn}_input" value="" size=""/>
                </div>
                <div class="shop-rating">
                    <span class="title">保障程度</span>
                    <ul class="rating-level" id="stars2_${list.orderSn}">
                    	<li><a class="one-star" star:value="1" href="#">1</a></li>
                        <li><a class="two-stars" star:value="2" href="#">2</a></li>
                        <li><a class="three-stars" star:value="3" href="#">3</a></li>
                        <li><a class="four-stars" star:value="4" href="#">4</a></li>
                        <li><a class="five-stars" star:value="5" href="#">5</a></li>
                    </ul>
                    <span class="result" id="stars2_${list.orderSn}_tips"></span>
                    <input type="hidden" id="stars2_${list.orderSn}_input" name="stars2_${list.orderSn}_input" value="" size="">
                </div>
                <div class="shop-rating">
                    <span class="title">保障价格</span>
                    <ul class="rating-level" id="stars3_${list.orderSn}">
                        <li><a class="one-star" star:value="1" href="#">1</a></li>
                        <li><a class="two-stars" star:value="2" href="#">2</a></li>
                        <li><a class="three-stars" star:value="3" href="#">3</a></li>
                        <li><a class="four-stars" star:value="4" href="#">4</a></li>
                        <li><a class="five-stars" star:value="5" href="#">5</a></li>
                    </ul>
                    <span class="result" id="stars3_${list.orderSn}_tips"></span>
                    <input type="hidden" id="stars3_${list.orderSn}_input" name="stars3_${list.orderSn}_input" value="" size="">
                </div>
                <div class="shop-rating">
                    <span class="title">售后服务</span>
                    <ul class="rating-level" id="stars4_${list.orderSn}">
                        <li><a class="one-star" star:value="1" href="#">1</a></li>
                        <li><a class="two-stars" star:value="2" href="#">2</a></li>
                        <li><a class="three-stars" star:value="3" href="#">3</a></li>
                        <li><a class="four-stars" star:value="4" href="#">4</a></li>
                        <li><a class="five-stars" star:value="5" href="#">5</a></li>
                    </ul>
                    <span class="result" id="stars4_${list.orderSn}_tips"></span>
                    <input type="hidden" id="stars4_${list.orderSn}_input" name="stars4_${list.orderSn}_input" value="" size="">
                </div>
                <span class="error_red" id="stars_${list.orderSn}_error"></span>
            </div>
            <div class="order-xing-pl" style="display:${list.disComment}">
                <div class="order-jsf-bor">
                    <span class="order-muds">投保目的 </span>
                    <span class="order-muds order-muds2">
	                    <select name="purpose_${list.orderSn}" id="purpose_${list.orderSn}">
	                       <option value="" id="">请选择</option>
	                       <#list list.purpose?keys as itemKey>
	                       <option value="${itemKey}" id="">${list.purpose[itemKey]}</option>
	                       </#list>
	               		</select>
               		</span><div class="clear h10"></div>
                    <textarea value=""  name="content_${list.orderSn}" id="content_${list.orderSn}" class="order-plsf" 
                    onfocus="if(this.innerHTML=='产品是否给力？分享你的购买心得给大家吧!'){this.innerHTML='';this.style.color='#000'}" 
					onblur="if(this.innerHTML==''){this.innerHTML='产品是否给力？分享你的购买心得给大家吧!';this.style.color='#D1D1D1'}" 
					onkeyup="hideMessage(${list.orderSn});">产品是否给力？分享你的购买心得给大家吧!</textarea>
                </div>
                <span class="pl_error" id="error_${list.orderSn}" style="display: none"></span>
                <input type="button" value="提交" class="re-btnts f-mi" id="cp_pl_btns_${list.orderSn}" onclick="submitComment('${list.orderSn}');" />
            </div>
            <div class="clear h20"></div>
        </div>
        
        <#assign num=num+1 />
    </#list>  
	</#if>
	<#if (channelsn  == "jfsc" || channelsn  == "wj_jfsc" || channelsn  == "wap_jfsc")>
	<div class="order-weichart cf">
        <h3 class="or-mes-tit"><b  class="f_mi">关注微信公众号</b></h3>
        <img src="${staticPath}/style/shop/images/weichart_07.gif"  width="102px" height="102px" class="or-weichartimg">
         <div class="wiechat-des">
             <b class="f_mi">扫一扫关注后，您可以</b>
             <ul><li>随时随地咨询理赔问题</li>
                 <li>手机也可以购买保险</li>
                 <li>获取最新的优惠活动</li></ul>
         </div>
    </div>

    <div class="order-orter">
        <a href="${base}/shop/order_query!list.action" class="order-aooa order-aooa2 f_mi">查看订单</a>
        <a href="/" class="order-aooa f_mi">浏览其他商品</a>
    </div>
    </#if>
</#if>
    <div class="clear"></div>
    
</div>	 

<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if>
<#if (couponShowFlag !="y")!>
<div id="yhj_box">
    <div class="yhj_pay_con">
        <div class="yhj_header"></div>
        <div class="yhj_cons">
            <h3 class="yjj_tit">开心保会员专享福利</h3>
            <div class="yhj_pop">
                <div class="yhj_sc">
                    <p class="yhj_tit_des">优惠券已放入账户中，请[登录后]使用</p>
                    <div class="yhj_list_con">
                    </div>
                </div>
            </div>
            <a href="javascript:void(0);" class="yhj_btns">我知道了</a>
        </div>

    </div>
</div>
</#if>

<#if (onLineCallBackInfo != null)!>
<!-- 在线回访 -->
<div id="visit_area" style="display:none;">
		<p class="ttl">百年人寿回访问卷 </p>
		<p>尊敬的${onLineCallBackInfo.appntName}先生/女士：感谢您通过开心保购买了我公司的${onLineCallBackInfo.productName}产品，此产品相关内容如下，请认真阅读并确认：</p>
        <#if (lcbxFlag == "true")!>
        	<p>1.本人亲自确认投保，已阅读并了解保险条款、投保提示、产品说明书及投保声明上的内容，并对产品的保险责任及责任免除、犹豫期、风险提示语等相关权益都已经清楚。</p>
            <p>2.本产品为万能型产品，一次性缴纳保费为${callBackAmount}元，保障期限最长为五年。</p>
            <p>3.本产品具有投资和保障的双重功能，最低保证利率是年利率3.0%，超过这部分的投资收益是不确定的，取决于公司的实际经营状况。</p>
            <p>4.您可以通过站内信下载并查看保单。自保单成功购买后的第二天起，有10天的犹豫期，犹豫期内您对保单如有疑义，可退还所交保费。超过此期间，如果提前解除保险合同，对您可能会有一定的损失。</p>
        <#else>
        	<p>1.本人亲自确认投保，已阅读并了解保险条款、投保提示、产品说明书及投保声明上的内容，并对产品的保险责任及责任免除、犹豫期等相关权益都已经清楚。</p>
            <p>2.本人投保时录入的投保信息均为真实准确、如实告知。</p>
            <p>3.本产品为保障型产品，第一次共交纳保费为${onLineCallBackInfo.totalAmount}元，<#if (onLineCallBackInfo.chargeYear != "0C")!>需连续交费${onLineCallBackInfo.chargeYearName}，</#if>保障期限为${onLineCallBackInfo.ensureDisplay}。</p>
            <p>4.您可以通过您投保时填写的电子邮箱接收并查看电子保单。自保单成功购买后的第二天起，有${onLineCallBackInfo.hesitatePeriod}天的犹豫期，犹豫期内您对保单如有疑义，可退还所交保费。超过此期间，如果提前解除保险合同，对您可能会有一定的损失。</p>
        </#if>
</div>
<div id="dark_area"></div>
<!-- /在线回访 -->
</#if>

<!-- 百分点发送逻辑 begin --> 
<!-- <script type="text/javascript" src="${shopStaticPath}/json2.js"></script> -->
<script type="text/javascript" src="${shopStaticPath}/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.cookie.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_pay.js"></script>
<script type="text/javascript">
/*  //百分点合作结束，百分点核心代码暂存
try{
	// 回调
	window["_BFD"] = window["_BFD"] || {};
	_BFD.pm_RecVAV = function(data,bid,req_id){
	*/
		/*
		此处请添加推荐栏的回调方法,参数data为我们返回的商品信息数组
		如果只返回商品ID，data格式为["item1","item2","item3"]这样的数组。
		如果商品信息全返回，data格式为[{"iid":"item1","img":"http://images.*.com/**.jpg","name":"商品1","price":10.0,"url":"http://**"},{"iid":"item2","img":"http://images.*.com/**.jpg","name":"商品2","price":120.0,"url":"http://**"}]
		
		推荐栏请添加百分点的logo，详见：http://www.baifendian.com/service/logo.html
		*/
		/*
		//console.log(JSON.stringify(data));
		if (data && (data instanceof Array) && data.length > 0) {
			
			var ulHtml = "";
			for (var i = 0; i < data.length; i++) {
				var item = data[i];
				var url = item.url;
				if (url != null && url != "") {
					var url_index = url.indexOf("?");
					if (url_index > 0) {
						url = url.substring(0, url_index);
					}
					url = url + "?link_id=bfdpaysuccess";
				}
				ulHtml += "<div class=\"changeBox\"><div class=\"bfd_ul\">"
				ulHtml += "<li><div class=\"bfd_img\"><span><a target=\"_blank\" href=\"" + url + "\" onclick=\"_BFD.FeedBack('" + item.iid + "', '" + (i+1) + "', '" + bid + "','" + req_id + "');\"></span><img src=\"" + item.img +	"\" width=\"78px\" height=\"78px\" alt=\"" + item.name + "\" style=\"display: inline;\"></a></div>";
				ulHtml += "<div class=\"bfd_list\"><a href=\"" + url + "\" target=\"_blank\" onclick=\"_BFD.FeedBack('" + item.iid + "', '" + (i+1) + "', '" + bid + "','" + req_id + "');\">" + item.name + "</a>";
				ulHtml += "<ul class=\"fte_list\" id=\"Bright_" + item.iid + "\"></ul>";
				ulHtml += "<p class=\"bfd_goto\"><span class=\"bfd_pays\">￥ " + item.price + "</span><span class=\"bfd_pepole\" id=\"SalesI_" + item.iid + "\"></span></p></div></li>";
				i++;
				item = data[i];
				url = (item.url == null || item.url == "")?"":item.url + "?link_id=bfdpaysuccess";
				ulHtml += "<li><div class=\"bfd_img\"><span><a target=\"_blank\" href=\"" + url + "\" onclick=\"_BFD.FeedBack('" + item.iid + "', '" + (i+1) + "', '" + bid + "','" + req_id + "');\"></span><img src=\"" + item.img +	"\" width=\"78px\" height=\"78px\" alt=\"" + item.name + "\" style=\"display: inline;\"></a></div>";
				ulHtml += "<div class=\"bfd_list\"><a href=\"" + url + "\" target=\"_blank\" onclick=\"_BFD.FeedBack('" + item.iid + "', '" + (i+1) + "', '" + bid + "','" + req_id + "');\">" + item.name + "</a>";
				ulHtml += "<ul class=\"fte_list\" id=\"Bright_" + item.iid + "\"></ul>";
				ulHtml += "<p class=\"bfd_goto\"><span class=\"bfd_pays\">￥ " + item.price + "</span><span class=\"bfd_pepole\" id=\"SalesI_" + item.iid + "\"></span></p></div></li>";
				ulHtml += "</div></div>";
			}
			ulHtml += "<a title=\"上一个\" class=\"a_last\" href=\"#\">上一个</a>";
	        ulHtml += "<a title=\"下一个\" class=\"a_next\" href=\"#\">下一个</a>";
			//console.log(ulHtml);
			jQuery("#change_bfd").html(ulHtml);
			jQuery(".bfd_cp_box").show();
			
			 $('#change_bfd div.changeBox').soChange({
			    thumbObj:'#change_bfd .ul_change_a2 span',
			    thumbNowClass:'on',
			    botPrev:'#change_bfd .a_last',
			    botNext:'#change_bfd .a_next'
			 });
			 
			 bfdload();
		}
		
		_BFD.showBind(data,"pm_RecVAV",bid,req_id);//此处是推荐返回时百分点绑定事件的方法，请不要修改。
	}

	// 发送
	var order_items = new Array();
	var bdinfo = '${BD_INFO}';
	var bdArrs = bdinfo.split('|');
	for (var i = 0; i < bdArrs.length; i++) {
		var arrTemp = bdArrs[i].split('@');
		order_items[i] = new Array(arrTemp[0], arrTemp[1], arrTemp[2]);
	}
	window["_BFD"] = window["_BFD"] || {};
	_BFD.BFD_INFO = {
		"order_id" : jQuery("#OrdId").val(),   //当前订单号，如果有拆单等特殊情况现象（一次购买中出现多个订单号）此页面代码不可用，请联系我修改；
	   	"order_items" : order_items,   //同购物车页
		"total" : '${callBackAmount}',   //用户实际支付的价格
		"payment" : "在线支付",   //支付方式 
		"express" : "",   //快递方式 不传
		"user_id" : jQuery.cookie("loginMemberId")==null?"":jQuery.cookie("loginMemberId"), //网站当前用户id，如果未登录就为0或空字符串
		"page_type" : "payment" //当前页面全称，请勿修改
	};
	//console.log(JSON.stringify(_BFD.BFD_INFO));
}catch(e){
	
}

// 加载产品亮点购买人数
function bfdload() {
	var ulist=jQuery('span[id^=SalesI_]');
	var uid = new Array(ulist.length);
	var uidStr="";
	for (var i = 0; i < ulist.length; i++) {
		uid[i] = ulist[i].id.replace("SalesI_", "");
        uidStr+= ulist[i].id.replace("SalesI_", "");
	}
	if(uidStr==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!searchSalesVolume.action?productIds='+uid,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				jQuery("#SalesI_"+index).html(object+"人已购买");
			});
		}
	});
	
	var ulist2=jQuery('ul[id^=Bright_]');
	var uid2 = new Array(ulist2.length);
	var uidStr2="";
	for (var i = 0; i < ulist2.length; i++) {
		uid2[i] = ulist2[i].id.replace("Bright_", "");
        uidStr2+= ulist2[i].id.replace("Bright_", "");
	}
	if(uidStr2==""){
		return;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!loadProductDetailBright.action?productIds='+uid2,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				jQuery("#Bright_"+index).html(object);
			});
		}
	});
}
*/
</script>
<script>
function callback(){
	var orderSn = '${orderId}';
	$.ajax({
		url: sinosoft.base + "/shop/sales_volume!visitBackOnLine.action?orderSn="+orderSn,
		type: "post",
		dataType: "json", 
		success : function(data) {
		}
	});  
}

 jQuery(function(){
 	 var isRevisit = $("#visit_area").length > 0 ? true:false;//是否弹出在线回访标识

     jQuery(".order_tk span").click(function(){
       var title = jQuery(this).find("i").text();
       art.dialog({
            id: 'xyid',
            fixed: true,
            title: title,
            content: document.getElementById('order_xy'),
            button: [
                {
                    name: '我已仔细阅读',
                    callback: function () {
                    	callback();
                    },
                    focus: true
                },
                {
                    name: '没空阅读',
                    callback: function () {
                       
                    }
                }
            ]
        });
     })
     var orderSn = '${orderId}';
     $.ajax({
         url: sinosoft.base + "/shop/pay!showPayCoupon.action?orderId="+orderSn,
         type: "post",
         dataType: "json",
         success : function(data) {
             if (data.status == "y") {
             	if(!isRevisit){
	                 var CouponDateJSON = data.couponDateJSON;

	                 new Couponbox(CouponDateJSON,{
	                     keyVal : ["name", "value","confine","classname","time","link"], //JSON中【选项名称】和【选项值】的索引
	                     yhjBtn : "#yhj_box .yhj_btns",
	                 });
                 }
             }
         }
     });

     if(isRevisit){
     	// 保存在线回访记录
		$.ajax({
		   url: sinosoft.base + "/shop/pay!saveOnlineRevisit.action",
		   type: "post",
		   dataType: "json",
		   data: {"orderSn" : orderSn},
		   success : function(data) {
		   		if(data.status == "0"){
		   			//在线回访确认框
				     art.dialog({
				         id:'shaop_car',
				         title: '在线回访',
				         content: jQuery("#visit_area")[0],
				         fixed: true,
				         button: [
				             {
				                 name: '确认在线回访',
				                 callback: function () {
				                     jQuery("#dark_area").hide();
				                     callback();
				                 }
				             }
				         ],
				         close: function () {
				             jQuery("#dark_area").hide();
				         }
				     });
				    jQuery("#dark_area").show();
		   		}
	        }
		 });
     }

 })

</script>

<!-- 百分点发送逻辑 end -->
<#include "/wwwroot/kxb/block/community_v1.shtml"> 
<script type="text/javascript" src="${shopStaticPath}/shop/js/pay_success.js"></script>
</body>

</html>


