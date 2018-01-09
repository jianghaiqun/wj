<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>核保结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<head>
<link rel="stylesheet" type="text/css" href="${shopStaticPath}/template/shop/css/order_result.css" />
<link href="${shopStaticPath}/template/shop/css/PayPromptSty2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<title></title>
<style type="text/css">
   body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td, hr, button, article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section{margin:0;padding:0}
    html{color:#444;background-color:#fff;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%}
	html, body{height:100%}
    body, button, input, select, textarea, table{font:12px/1.6 tahoma,arial,'Hiragino Sans GB',simsun,sans-serif}
   	table th, table td{padding:5px;font-weight:normal;vertical-align:top;text-align:left}
    table{border-collapse:collapse;border-spacing:0}
   	a{color:#333;text-decoration:none;cursor:pointer}
    .w900{width:900px;margin:0 auto}
    .tit-product {font-size:14px;margin:10px 0;padding-left:10px;zoom:1}
    .tit-product span {font-size:12px;font-weight:500}
    .fr{float:right}
    .lay-ins-con {border:1px solid #dedee0}
    .pro-con-info {background:#FFFBED;border:1px solid #dedee0;margin-bottom:15px}
	.pro-con-info table table {width:97%;margin:5px;}
	.pro-con-info table table th {width:30%}
	.tabGrouplist th{text-align:left}
	.pro-con-info .pro-tab {background-color:#fff;border:1px solid #dedee0}
	.pro-tab th {text-align: right;}
	.ins-tit {background:none repeat scroll 0 0 #FFEAA2;color:#333333;font-size:14px;font-weight:bold;height:30px;line-height:30px;padding:0 10px;text-align:left}
     * html .ins-tit .icon-edit {background-position:left -99px;margin-top:10px}
	.tab-bd,.tab-bd th, .tab-bd td{border:1px solid #dedee0}
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
	.mydiv {
		background-color: #FFFFFF;
		border: 5px solid #c7c7c7;
		text-align: center;
		line-height: 40px;
		font-size: 12px;
		font-weight: bold;
		z-index:999;
		width: 500px;
		height: 300px;
		left:50%;
		top:50%;
		margin-left:-150px!important;/*FF IE7 该值为本身宽的一半 */
		margin-top:-60px!important;/*FF IE7 该值为本身高的一半*/
		margin-top:0px;
		position:fixed!important;/* FF IE7*/
		position:absolute;/*IE6*/
		_top:       expression(eval(document.compatMode &&
		            document.compatMode=='CSS1Compat') ?
		            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
		            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/
		}
		.bg,.popIframe {
		background-color: #666; display:none;
		width: 100%;
		height: 100%;
		left:0;
		top:0;/*FF IE7*/
		filter:alpha(opacity=50);/*IE*/
		opacity:0.5;/*FF*/
		z-index:1;
		position:fixed!important;/*FF IE7*/
		position:absolute;/*IE6*/
		_top:       expression(eval(document.compatMode &&
		            document.compatMode=='CSS1Compat') ?
		            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
		            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/* www.codefans.net IE5 IE5.5*/
		}
	.loading-indicator-bars {
			background-image: url('${shopStaticPath}/template/shop/images/loading-bars.gif');
			width: 150px;
}
	</style>
<script language="javascript" type="text/javascript">
function agreeCheck(){
	 var agreecheckbox =  document.getElementById("agreecheckbox");
	 if(agreecheckbox.checked = "checked" ){
		 document.getElementById("comfirmToPay").disabled="";
	 }else{
		 document.getElementById("comfirmToPay").disabled="disabled";
	 }
}

function testBlack(TagName){
	var obj = document.getElementById(TagName);
	if(obj.style.display==""){
	   obj.style.display = "none";
		}else{
		   obj.style.display = "";
		}
	}

function checkInsurePay(){
	   var orderSn = document.getElementById("orderSn").value;
		jQuery.ajax({
					url: sinosoft.base+"/shop/order_config_new!checkInsurePay.action?orderSn="+orderSn+"&callback=?",
					dataType: "json",
					async: false,
					success: function(data) {
					var passFlag = data.passFlag;
					var rtnMessage = data.rtnMessage;
					var renMessageCh = "您的投保信息未通过审核";
					if(passFlag == "pass"){
						document.getElementById("resultForm").submit();
					}else{
						document.getElementById("divc1").style.display =  "none";
						document.getElementById("divc2").style.display =  "none";
						document.getElementById("divc3").style.display =  "";
						if("noRtnMssage"==rtnMessage){
							document.getElementById("rtnMessages").innerHTML=renMessageCh;
						}else{
							document.getElementById("rtnMessages").innerHTML=data.rtnMessage;
						}
					}
				}
		});
}

function showDiv(tag,tid,lawyerName){
		var light1=document.getElementById(tag);
		light1.style.display='block';
		document.getElementById('bg').style.display='block';
		checkInsurePay();
}
function closeDiv(){
		document.getElementById('popDiv').style.display='none';
		document.getElementById('bg').style.display='none';
		
}

</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

	<div class="w900">
		<form id ="resultForm" action="${base}/shop/order_config_new!pay.action" method="post">
		<input type="hidden" name="orderSn" id="orderSn" value="${sdorder.orderSn}" > 
		<input type="hidden" name="passFlag" id="passFlag" value="${passFlag}" > 
		<input type="hidden" value="${KID}" name="KID" id="KID">
		<h2 class="tit-product"><small class="fr">订单号：${sdorder.orderSn}</small> </h2>
		
		<div class="lay-ins-con pro-tab">
			<div class=ins-tit>核保操作</div>
			<table border=0 cellspacing=0 cellpadding=0>
					<tr id="divc1">
						<td>
						<span id="apppricediv">正在为您进行核保操作，请稍候</span></td>
					</tr>
					<tr>
					<td id="divc2" ><img width="170" height="15" title="正在核保，请稍等" src="${shopStaticPath}/template/shop/images/loading-bars.gif"/></td>
					</tr>
					<tr id="divc3" style="display:none;">
					<#assign href=base + "/shop/order_config_new!buyNowUpdate.action?orderId=" + sdorder.id + "&productId=" + productId +"&orderSn=" + orderSn + "&KID=" + KID />
			        <td>很抱歉，<span id = "rtnMessages"></span>，请点击<a onclick="location.href='${href}'"><font color="red">【返回】</font></a>进行修改。</td>
			        </tr>
			</table>
		</div>
       </div>
     </div>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml">	

<script language="javascript" for="window" event="onload">
checkInsurePay();
</script>
</body>
</html>