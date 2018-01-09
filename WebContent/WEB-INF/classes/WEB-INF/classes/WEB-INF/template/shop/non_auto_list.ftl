<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>产品对比页面</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_detail.css" />
<script type="text/javascript" src="${shopStaticPath}/jquery-1.4.2.min.js"></script>
<script>

 window.onload = function(){
		var selectTag = document.getElementById("CompareProduct").getElementsByTagName("select");
		for ( var i = 0; i < selectTag.length; i++) {
			if(selectTag[i].getAttribute("id").indexOf("SELECT_Plan") != -1){
				var st = selectTag[i].getAttribute("id") ;
				var factorValue = selectTag[i].options[selectTag[i].selectedIndex].value;
			 	var productCode = st.replace("SELECT_Plan_","");
			 	var spanTag = document.getElementById("CompareProduct").getElementsByTagName("span");
			 	
				for ( var j = 0; j < spanTag.length; j++) {
					
					if(spanTag[j].getAttribute("id") == null){
						continue;
					}
					
					if(spanTag[j].getAttribute("id") != null && spanTag[j].getAttribute("id").indexOf("_PLAN1_") != -1){
						if(spanTag[j].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[j].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[j].style.display = "block";
							}  else {
								spanTag[j].style.display = "none";
							}
						}
					}
					if(spanTag[j].getAttribute("id") != null && spanTag[j].getAttribute("id").indexOf("_PLAN2_") != -1){
						if(spanTag[j].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[j].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[j].style.display = "block";
							}  else {
								spanTag[j].style.display = "none";
							}
						}
					}
					if(spanTag[j].getAttribute("id") != null && spanTag[j].getAttribute("id").indexOf("_PLAN3_") != -1){
						if(spanTag[j].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[j].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[j].style.display = "block";
							}  else {
								spanTag[j].style.display = "none";
							}
						}
					}
					if(spanTag[j].getAttribute("id") != null && spanTag[j].getAttribute("id").indexOf("_PLAN4_") != -1){
						if(spanTag[j].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[j].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[j].style.display = "block";
							}  else {
								spanTag[j].style.display = "none";
							}
						}
					}
				}
			}
		}
			var spanTag= document.getElementById("Comparedouble").getElementsByTagName("span");
			for(var i = 0; i < spanTag.length; i++){
				if(spanTag[i].getAttribute("id").indexOf("DiscountPrem") != -1){
					var productId = spanTag[i].getAttribute("id");
					productId = productId.substring(productId.indexOf("DiscountPrem")+12,productId.indexOf("_"));
					// 产品详细页活动展示
						jQuery.ajax({
							type: 'post',
							url: '${base}/shop/sales_volume!showActivity.action?productId='+productId+'&flag=compare',
							dataType: "json",
							async: true,
							success: function(data) {
								jQuery.each(data, function(index, object) {
									if("success"==index){
										jQuery("#yhinfo_"+data.productId).append(data.success);
										var pointtitle = data.pointtitle;
										if (pointtitle != null && pointtitle != '') {
											pointtitle = pointtitle + " ";
										}
										jQuery("#pointtitle_"+data.productId).html(pointtitle);
									}
								});
								// 对比页不显示满减描述
								jQuery(".active_04").parent().remove();
							}
						});
				}
			}
			
	}
	
jQuery.fn.fixedNavigation = function(opt) {
	var _opt, jWin, jNav, nNavOffTop;
	
	return this.each(function() {
		_opt = jQuery.extend({
		navElm: this,
		fixName: "fixed"
		}, opt);

		_init();
		_monitor();
	});
	
	/* init */
	function _init() {
		jWin = jQuery(window);		
		jNav = jQuery(_opt.navElm);
		nNavOffTop = jNav.offset().top;
	}

	/* bind event */
	function _monitor() {
		jWin.bind("scroll", function() {
			var nWinTop = jWin.scrollTop();

			if(nWinTop >= nNavOffTop) {
				jNav.addClass(_opt.fixName);
			} else{
				jNav.removeClass(_opt.fixName);
			}
		});
		

	}
};

function getPremTrial(factorValue,productCode,factortype,flag,amnt,dutyCode,columnNo){
       jQuery.ajax({
           type:'get',
           url:'/wj/shop/non_auto!getPremiumTrial.action?productCode='+productCode+'&factortype='+factortype+'&factorValue='+encodeURIComponent(factorValue)+'&flag='+flag+'&dutyCode='+dutyCode+'&amnt='+amnt,
           dataType:'json',
           success:function(de){
    	       fillPrice(de,columnNo);
           }
        });
        if(factortype == 'Plan' || factortype == 'plan'){
        	if(columnNo=='c1'){
		        var spanTag = document.getElementById("CompareProduct").getElementsByTagName("span");
				for ( var i = 0; i < spanTag.length; i++) {
					if(spanTag[i].getAttribute("id") == null){
						continue;
					}
					
					if(spanTag[i].getAttribute("id") != null && spanTag[i].getAttribute("id").indexOf("_PLAN1_") != -1){
						if(spanTag[i].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[i].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[i].style.display = "block";
							}  else {
								spanTag[i].style.display = "none";
							}
						}
					}
				}
			}
			if(columnNo=='c2'){
		        var spanTag = document.getElementById("CompareProduct").getElementsByTagName("span");
				for ( var i = 0; i < spanTag.length; i++) {
					if(spanTag[i].getAttribute("id") == null){
						continue;
					}
					
					if(spanTag[i].getAttribute("id") != null && spanTag[i].getAttribute("id").indexOf("_PLAN2_") != -1){
						if(spanTag[i].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[i].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[i].style.display = "block";
							}  else {
								spanTag[i].style.display = "none";
							}
						}
					}
				}
			}
	        if(columnNo=='c3'){
		        var spanTag = document.getElementById("CompareProduct").getElementsByTagName("span");
				for ( var i = 0; i < spanTag.length; i++) {
					if(spanTag[i].getAttribute("id") == null){
						continue;
					}
					
					if(spanTag[i].getAttribute("id") != null && spanTag[i].getAttribute("id").indexOf("_PLAN3_") != -1){
						if(spanTag[i].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[i].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[i].style.display = "block";
							}  else {
								spanTag[i].style.display = "none";
							}
						}
					}
				}
			}
	        if(columnNo=='c4'){
		        var spanTag = document.getElementById("CompareProduct").getElementsByTagName("span");
				for ( var i = 0; i < spanTag.length; i++) {
					if(spanTag[i].getAttribute("id") == null){
						continue;
					}
					
					if(spanTag[i].getAttribute("id") != null && spanTag[i].getAttribute("id").indexOf("_PLAN4_") != -1){
						if(spanTag[i].getAttribute("id").indexOf(productCode) != -1){
							if( spanTag[i].getAttribute("id").endsWith("_"+factorValue)){
								spanTag[i].style.display = "block";
							}  else {
								spanTag[i].style.display = "none";
							}
						}
					}
				}
			}
		}		
    }
    
    function fillPrice(de,columnNo){
    	if(de != null && de.prem != 0.0){
    		if(columnNo !=null){
    			try{
    			document.getElementById("InitPrem"+de.productCode+"_"+columnNo).innerHTML = de.prem;//原价
	    		}catch(e){}
	    		document.getElementById("DiscountPrem"+de.productCode+"_"+columnNo).innerHTML = de.discountPrem;//折扣价
	    		document.getElementById("Integral"+de.productCode+"_"+columnNo).innerHTML = de.productInt;//积分
    		}else{
    			try{
	    		document.getElementById("InitPrem"+de.productCode).innerHTML = de.prem;//原价
	    		}catch(e){}	
	    		document.getElementById("DiscountPrem"+de.productCode).innerHTML = de.discountPrem;//折扣价
	    		document.getElementById("Integral"+de.productCode).innerHTML = de.productInt;//积分
    		}
    	}
    }

//产品信息宽度计算
jQuery(document).ready(function() {
	var _oTdElm = jQuery("#CompareProduct").find("tr:not(:first-child)").first().find(".bj_4");
	var _oTdTtl = jQuery("#CompareProduct").find(".bj_2");
	var _width = 760/_oTdElm.length - 1;
	
	if(_oTdElm.length == 4) {
		$("#Comparedouble").find(".bj_3").width("14%");
	} else if(_oTdElm.length == 3) {
		$("#Comparedouble").find(".bj_3").width("121px");
	}
	
	$(".bj_4").each(function(i, v) {
		jQuery(v).width(_width);
	});
	
	
	
	jQuery("#Comparedouble").fixedNavigation({
		fixName: "fixed"
	});
	 var spanTag = $("span[id^='DiscountPrem']");
			for(var i = 0; i < spanTag.length; i++){
				if(spanTag[i].getAttribute("id").indexOf("DiscountPrem") != -1){
					var productId = spanTag[i].getAttribute("id");
					productId = productId.substring(productId.indexOf("DiscountPrem")+12,productId.indexOf("_"));
					// 产品详细页活动展示
						jQuery.ajax({
							type: 'post',
							url: '${base}/shop/sales_volume!showActivity.action?productId='+productId+'&flag=compare',
							dataType: "json",
							async: true,
							success: function(data) {
								jQuery.each(data, function(index, object) {
									if("success"==index){
										jQuery("#yhinfo_"+data.productId).append(data.success);
										var pointtitle = data.pointtitle;
										if (pointtitle != null && pointtitle != '') {
											pointtitle = pointtitle + " ";
										}
										jQuery("#pointtitle_"+data.productId).html(pointtitle);
									}
								});
								// 对比页不显示满减描述
								//jQuery(".active_04").parent().remove();
							}
						});
				}
			}
});

           
           
</script>
<style>
#Comparedouble.fixed { *width: 978px;}
#Comparedouble table { left: 0; }
@-moz-document url-prefix() { #Comparedouble table { left: -1px; } }
</style>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	<div class="NonCarMiddArea" style="padding-top:15px" >
		<table cellpadding="1" cellspacing="1" border="0" width="100%" id="CompareProduct">
		<#if (compareNonAutos.size()>0)!>
			<tr>
				<td class="bj_2" colSpan=${compareSize}>产品信息</td>
			</tr>
			
			<#list compareNonAutos as list>
			<#if list_index==0 && list_has_next>
			    <tr>
				<td colspan=${compareSize}>
				<div id="Comparedouble">
			    <table cellpadding="0" cellspacing="0" border="0" width="100%">
			</#if>
			<tr>
				<td class="bj_3">${list.compareName}</td>
				<#if (A ? has_content)>
				<td class="bj_4">
					<!--<span class="icon_sale"></span>-->
					<!--<div class="icon_C2071 cp_logos"></div>-->
					${list.informationA}
				</td>
               </#if> 
               <#if (B ? has_content)>
				<td class="bj_4">
					<!--<span class="icon_sale"></span>-->
					<!--<div class="icon_C2071 cp_logos"></div>-->
					${list.informationB}
				</td>
               </#if> 
               <#if (C ? has_content)>
				<td class="bj_4">
					<!--<span class="icon_sale"></span>-->
					<!--<div class="icon_C2071 cp_logos"></div>-->
					${list.informationC}
				</td>
               </#if> 
               <#if (D ? has_content)>
				<td class="bj_4">
					<!--<span class="icon_sale"></span>-->
					<!--<div class="icon_C2071 cp_logos"></div>-->
					${list.informationD}
				</td>
               </#if> 
			</tr>
			<#if list_index==0> 	
	        	 </table>
	        	 </div>
        	     </td>
        	 </tr>	
	        </#if>
			</#list>
 
			</#if>
			<#if (compareNonAutoDutys.size()>0)!>
			<tr>
				<td class="bj_2" colSpan=${compareSize}>保障信息</td>
			</tr>
			<#list compareNonAutoDutys as list3>
			<tr>
				<td class="bj_3">${list3.compareName}</td>
				<#if (A ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationA}
		      	     </td>
		        </#if>
                <#if (B ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationB}
		      	     </td>
		        </#if>
		        <#if (C ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationC}
		      	     </td>
		        </#if>
		        <#if (D ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationD}
		      	     </td>
		        </#if>
			</tr>
            </#list>
			</#if>
			
			<#if (compareNonAutoAppFs.size()>0)!>
			<tr>
				<td class="bj_2" colSpan=${compareSize}>销量评价</td>
			</tr>
			<#list compareNonAutoAppFs as list3>
			<tr>
				<td class="bj_3">${list3.compareName}</td>
				<#if (A ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationA}
		      	     </td>
		        </#if>
		        <#if (B ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationB}
		      	     </td>
		        </#if>
		        <#if (C ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationC}
		      	     </td>
		        </#if>
		        <#if (D ? has_content)>
		      	     <td class="bj_4">
		      	         ${list3.informationD}
		      	     </td>
		        </#if>				
			</tr>
			</#list>
			</#if>
			
			<tr>
				<td class="bj_3"></td>
				<#if (A ? has_content)>
	          	    <td class=bj_4>	          	    	
	          	       <a class="buy_now" target="_blank" href="${productPathA}">查看详情</a>
	          	    </td>
	          	</#if>		
	          	<#if (B ? has_content)>
	          	    <td class=bj_4>	          	    	
	          	       <a class="buy_now" target="_blank" href="${productPathB}">查看详情</a>
	          	    </td>
	          	</#if>
	          	<#if (C ? has_content)>
	          	    <td class=bj_4>	          	    	
	          	       <a class="buy_now" target="_blank" href="${productPathC}">查看详情</a>
	          	    </td>
	          	</#if>
	          	<#if (D ? has_content)>
	          	    <td class=bj_4>	          	    	
	          	       <a class="buy_now" target="_blank" href="${productPathD}">查看详情</a>
	          	    </td>
	          	</#if>	
			</tr>
			
		</table>

	</div>
</div>
 <!-- 底部开始 -->
 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<script type="text/javascript" src="${shopStaticPath}/jquery.cookie.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.form.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_base.js"></script>
<script type="text/javascript" src="${shopStaticPath}/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_header.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_footer.js"></script>
<script type="text/javascript" src="${shopStaticPath}/artDialog.js"></script>
<script type="text/javascript" src="${shopStaticPath}/js/redesign/xiaoneng_CustomerService.js"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script>
<script type="">

	//满减活动信息
function searchProductListAvtivity(){
	
	var list=jQuery('p[id^=Activity_]');
	var id = new Array(list.length);
	var idStr="";
	for (var i = 0; i < list.length; i++) {
           id[i] = list[i].id ;
           idStr += list[i].id;
	}
	
	if(idStr==""){
		return;
	}
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	
	
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!searchProductListAvtivityRe.action?productId='+id+'&channelSn='+channelsn+"&detailFlag=true",
		dataType: "json",
		async: true,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				index=index.substring(0,index.indexOf("@"));
				document.getElementById(index).innerHTML= "<span  class=\"icon_sale\" >" + object.substring(0,object.indexOf("@")) + "</span>";
			});
		}
	});
}

searchProductListAvtivity();

String.prototype.endsWith = String.prototype.endWith = function(str) {
	var i = this.lastIndexOf(str);
  return i>=0 && this.lastIndexOf(str) == this.length-str.length;
}
</script>

</body>
</html>