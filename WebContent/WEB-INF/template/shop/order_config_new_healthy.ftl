<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>健康告知页</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<head>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<title></title>
<style type="text/css">
   body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td, hr, button, article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section{margin:0;padding:0}
    html{color:#444;background-color:#fff;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%}
	
    .w900{width:980px;margin:0 auto;}
    .tit-product {font-size:26px;margin:0px 0 20px;padding-left:10px;zoom:1; font-weight: normal; }
    .tit-product span {font-size:26px; font-weight: normal; }
    .fr{float:right}
    .lay-ins-con {border:1px solid #dedee0}
    .pro-con-info {background:#fff; padding: 45px;}
	.pro-con-info table table {width:97%;margin:5px;}
	.pro-con-info table table th {width:30%}
	.tabGrouplist th{text-align:left}
	.pro-con-info .pro-tab {background-color:#fff;border:1px solid #dedee0}
	.pro-tab th {text-align: right;}
	.ins-tit {background:none repeat scroll 0 0 #FFEAA2;color:#333333;font-size:14px;font-weight:bold;height:30px;line-height:30px;padding:0 10px;text-align:left}
     * html .ins-tit .icon-edit {background-position:left -99px;margin-top:10px}
	.tab-bd,.tab-bd th, .tab-bd td{border:1px solid #E8E8E8; font-size: 14px; color: #888888; }
	.pro-tab {margin-bottom:15px;padding-bottom:15px}
	.pro-tab table {margin:15px auto 0}
	.ui-money span{font-size:14px}
	.ins-int-con {font-size:14px;font-weight:500;padding:4px 10px;width:95%;margin:auto}
	.num {font-weight:700;margin:0 5px}
	.red{color:#E84720}
	.blue{color:#3068c5}
	.ui-money {	color:#E84720;	font-weight:700;}
	 dfn {font-family:Arial;	font-style:normal;	padding-right:2px;}
	 .f14{font-size:14px}
	 .msg-tips{background-color:#fffbed;border:1px solid #eae6da;padding:10px 15px;margin:15px auto}
	.msg-tips li{margin:5px 0;list-style:decimal inside none}
	.msg-tips a{margin:0 5px}
	.msg-tips .msg-tips-one li{list-style:none}
	 .lnk-red:link,.lnk-red:visited,.lnk-hover{color:#e64211;text-decoration:none}
	 .insurant-hd {font-size:14px;margin:auto;padding:4px 10px;width:95%;background-color:#FFF4D4}
	 .insurant-tab {margin:0 auto 15px;width:96%}
	 .insurant-tab th {text-align:center;background-color:#FFFBED;color:#666}
	 .prod-proj-tab th{ text-align:right;font-weight:700}
	 .prod-proj-tab-hd td{background-color:#FFF4D4;font-weight:700;color:#707070;text-align:center;font-size:14px}
	 .prod-proj-tab th{background-color:#FFFBED;color:#707070; text-align:right}
	 .msg-con{padding:0 20px}
	 button, input, select, textarea{font-size:100%;vertical-align:middle}
	 .btn-more{padding:10px 0}
	.btn-more label{margin:0 7px}
	.button{padding-bottom:20px;margin-top:10px;overflow:hidden;zoom:1;}
	.butL,.butM,.butS,.butB,.butMax{background-image:url(/images/buts.png);background-color:#fd623c;cursor:pointer;height:27px;line-height:26px;border:0;padding:0;font-size:14px;color:#fff;font-weight:bold;text-shadow:1px 1px #c62a0a;}
  .pro-tab th.pro-tablef{ background: #F2F2F2; height: 40px; line-height: 40px; font-size: 16px; text-align: left; padding-left: 20px; color:#6E6E6E; }
  .heathy_td_c{ text-align: center; width: 40px;}
  .heatyg_btns{ border-bottom: 1px dashed #ccc; height: 40px; padding-top: 10px; text-align: right;}
  .heatyg_btns input{ border: none; background: #FD8824; padding: 4px 8px; color:#fff; text-align: center; background-clip: padding-box; border-radius: 3px; cursor: pointer;}
   .heatyg_btns input:hover{ background-color: #FE9C4B;}
   .bt_healthsf{ display: block; padding-right: 0px; width: 160px; background:#fd8824; margin: 20px auto 0; float: left;  font-size: 18px; text-align: center; line-height: 39px; }
   .btnarea100{ overflow:hidden; padding-left: 285px; padding-bottom: 10px;}
   .londing_mes_health{ text-align: center;}
   .bt_healthsf:hover{ background:#f79038; color:#fff;}
   .bt_nobtns{ display:block;  width:160px; text-align: center; background:#fff; float:left;  height: 40px;
       line-height: 39px; cursor:pointer; border:1px solid #ccc; margin-left: 20px; color:#888888; margin-top: 20px;  font-size: 18px;   font-family: "Microsoft YaHei"; border-radius: 4px;}
   .bt_nobtns:hover{ color:#888888; }
     .linedown_tip{ background:#fff; border:1px solid #ffe2cd; color:#757575; padding:20px 30px; border-radius: 4px; font-size: 14px; }
    .linedown_tip p{ padding-bottom: 10px; line-height: 1.6em;}
    .linedown_tip a{ color:#fd8824; display:inline-block; padding-bottom: 10px;}
    .linedown_tip a:hover{text-decoration:underline;}
    .line_ok{ display:block; color:#fd8824; cursor:pointer; }
    .hbtext{ width:140px; height:30px; border:1px solid #ccc; padding-left:4px; font-size: 14px;}
    .me_error{ color:red; display:block; font-weight: normal; padding-left:84px;  padding-top:4px;}
	</style>
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>

<script>
var comCode = "${sdinformation.insuranceCompany}";
var productid = "${sdinformation.productId}";
var p_url = "${jrhsURL}";
var productExcelFlag = "${(productExcelFlag)!}";
var loginFlag = "${loginFlag}";
var orderFlag = "${orderFlag}";
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if> 
	<div class="w900">
		<div class="daohang"></div>
		<form id ="resultForm" name="resultForm" action="${base}/shop/order_config_new!saveOrUpdateHealthInf.action" method="post"> 
		<div class="lay-ins-con pro-con-info">
			<h2 class="tit-product f_mi"><a href="${(productHtml)!}" target="_blank">${sdinformation.productName}</a><span>【${sdinformation.insuranceCompany}】</span></h2>
			<table class="tab-bd pro-tab" border=0 cellspacing=0 cellpadding=0>
			    <tr>
			        <th colspan="3" class="pro-tablef f_mi">购前告知</th>
			    </tr>
			<#assign index = 1 />
			<#list 1..sdinsuredHealthList.size() as x>
			    <#assign hiddenIndex = 0 />
			    <#assign oldType = "" />
			    <#if (sdinsuredHealthList[x-1].showInfoType=="#")>
					<tr>
						<td colspan="3">${sdinsuredHealthList[x-1].showInfo}</td>
					</tr>
				<#else>
					<tr>
						<#if sdinsuredHealthList[x-1].showInfoType != oldType >
							<#assign hiddenIndex = 1 />
							<#assign oldType = sdinsuredHealthList[x-1].showInfoType />
						<#else>
							<#assign hiddenIndex=hiddenIndex+1 />
						</#if>
						<input type="hidden" name="sdinsuredHealthList[${x-1}].healthyInfoId" id="sdinsuredHealthList[${x-1}].healthyInfoId}" value="${(sdinsuredHealthList[x-1].healthyInfoId)!}">
						<input type="hidden" name="sdinsuredHealthList[${x-1}].orderSn" id="sdinsuredHealthList[${x-1}].orderSn}" value="${(sdinsuredHealthList[x-1].orderSn)!}">
						<td class="heathy_td_c">${index}</td>
						<td>${sdinsuredHealthList[x-1].showInfo}</td>
						<#assign index = index+1 />
					</tr>
				</#if>
			</#list>
			</table>
			<div class="healthy-conbog" style="display:none">
	              <div class="headylth-ciop"></div>
	              <span class="heady-hr">或者，您也可以：</span>
	              <a href="/" class="next_btn dev_submitButton other_shop_s ">购买其他商品</a>
	        </div>
	        <div class="btnarea100">
                <a id="bt_health" class="next_btn dev_submitButton bt_healthsf" href="javascript:void(0);" vlpageid="pjkgzy" exturl="http://www.kaixinbao.com/pjkgzy" onclick="return(VL_FileDL(this));return false;"/>没有以上情况</a>
                <a class="bt_nobtns" id="bt_confirm" href="javascript:void(0);" vlpageid="pjkgzw" exturl="http://www.kaixinbao.com/pjkgzw" onclick="return(VL_FileDL(this));return false;">有部分情况</a>
	        </div>

	        <div id="msg_3" class="londing_mes_health" style="display:none"><img src="${staticPath}/images/nloading.gif"  width="20px" height="20px" alt="" />正在为您保存健康告知信息，请稍等</div>
			<div class="clear h20"></div>
	         <#if (isUnderwritingOffline=="1")>
		        <div class="linedown_tip">
		           <p>${UnderwritingOfflineHealthy}</p>
		          <a href="underwriting_offline!init.action?productId=${sdinformation.productId}">点击申请线下核保&gt;</a>
		          <#if (sdinformation.productId=="224801001")>
		           <p>如您已申请过线下核保并且收到核保通过编码</p><span class="line_ok">录入核保编码进行投保&gt;</span>
	   		 	  </#if>
		        </div>
	   		 </#if>
	   		 
	        
		</div>
		<input type="hidden" name="Occup" id="Occup" value="${(Occup)!}">
		<input type="hidden" name="productId" id="productId" value="${(productId)!}">
		<input type="hidden" name="textAge" id="textAge" value="${(textAge)!}">
		<input type="hidden" name="period" id="period" value="${(period)!}">
		<input type="hidden" name="plan" id="plan" value="${(plan)!}">
		<input type="hidden" name="sourceFlag" id="sourceFlag" value="${(sourceFlag)!}">
		<input type="hidden" name="channelCode" id="channelCode" value="${(channelCode)!}">
		<input type="hidden" name="typeFlag" id="typeFlag" value="${(typeFlag)!}">
		<input type="hidden" id="order_healthySn" name="order_healthySn" value="${(order_healthySn)!}"/>
		<input type="hidden" name="feeYear" id="feeYear" value="${(feeYear)!}">
		<input type="hidden" name="appLevel" id="appLevel" value="${(appLevel)!}">
		<input type="hidden" name="appType" id="appType" value="${(appType)!}">
		<input type="hidden" name="Sex" id="Sex" value="${(Sex)!}">
		<input type="hidden" name="applicantBirthday" id="applicantBirthday" value="${(applicantBirthday)!}">
		<input type="hidden" name="complicatedFlag" id="complicatedFlag" value="${(complicatedFlag)!}">
		<input type="hidden" name="dutyTempSerials" id="dutyTempSerials" value="${(dutyTempSerials)!}">
		<input type="hidden" name="pointExchangeFlag" id="pointExchangeFlag" value="${(pointExchangeFlag)!}">
		<input type="hidden" name="productyTypeFirstList" id="productyTypeFirstList" value="${(productyTypeFirstList)!}"/>
		<input type="hidden" name="Mult" id="Mult" value="${(Mult)!}"/>
		<input type="hidden" name="UnderwritingOfflineCode" id="UnderwritingOfflineCode" value="${(UnderwritingOfflineCode)!}"/>
		</form>
	</div>
	<div class="clear"></div>
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
<#include "/wwwroot/kxb/block/community_v1.shtml">
<script	type="text/javascript" src="${shopStaticPath}/iframeTools.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/json2.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/healthy.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>

<script>
</script>
</body>
</html>