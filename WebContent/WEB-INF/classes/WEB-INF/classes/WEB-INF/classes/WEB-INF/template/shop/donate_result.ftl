<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<head>
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/commonality.css" />
<link href="${base}/template/shop/css/PayPromptSty2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
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
	function  sure(){
		document.getElementById("resultForm").submit();
		return true;
	}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

	<div class="w900" style="margin-top:20px;">
		<form id ="resultForm" action="${base}/shop/donate!submit.action" method="post"> 
		<input type="hidden" name="orderSn" id="orderSn" value="${order.orderSn}" > 
		<h2 class="tit-product"><small class="fr">订单号：${order.orderSn}</small><a href="#">${order.productName}</a></h2>
		
		<div class="lay-ins-con pro-con-info">
			<div class="ins-tit">保单明细</div>
			<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td>
							<table class="tab-bd pro-tab" border=0 cellspacing=0 cellpadding=0>
								<tr>
									<th>投保日期</th>
									<td>${order.modifyDate?string("yyyy-MM-dd")}</td>
								</tr>
								<tr>
									<th>保单起止日期</th>
									<td>${information.effective?string("yyyy-MM-dd")}&nbsp;00时 - ${information.fail?string("yyyy-MM-dd")}&nbsp;24时</td>
								</tr>
							</table>
						</td>
					</tr>
			</table>
		</div>
		
		<div class="lay-ins-con pro-tab">
			<div class="ins-tit">投保人信息</div>
			<table class="tab-bd insurant-tab" border=0 cellspacing=0 cellpadding=0>
				<colgroup>
					<col width="10%">
					<col width="12%">
					<col width="15%">
					<col width="11%">
					<col width="10%">
					<col width="14%">
					<col width="10%">
					<col width="18%">
					<tr>
						<th>姓名</th>
						<td>${informationAppnt.applicantName}</td>
						<th>证件类型</th>
						<td>${informationAppnt.applicantIdentityTypeName}</td>
						<th>证件号码</th>
						<td>${informationAppnt.applicantIdentityId}</td>
					</tr>
					<tr>
						<th>性别</th>
						<td>${informationAppnt.applicantSexName}</td>
						<th>出生日期</th>
						<td>${informationAppnt.applicantBirthday}</td>
						<th>联系方式</th>
						<td>${informationAppnt.applicantMobile}</td>
					</tr>
			</table>
		</div>
		
		<div class="lay-ins-con pro-con-info">
			<div class="ins-tit"> 被保险人信息
				<span class="ins-int-con">共<span class="num red">1</span>名被保险人， <span class="num red">1</span>份保单，保费合计： 
				<span class=ui-money><dfn>&yen;</dfn>${order.totalAmount}</span></span>
			</div>
			<table border=0 cellspacing=0 cellpadding=0 width="100%">
				<tr>
					<td>
						<table class="tab-bd pro-tab" border=0 cellspacing=0 cellpadding=0>
							<tr>
								<th>投保人与被保人关系</th>
								<td>本人</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="lay-ins-con pro-tab">
		<h3 class=ins-tit>保障项目</h3>
			<table class="tab-bd insurant-tab prod-proj-tab" border=0 cellspacing=0
				cellpadding=0>
				<colgroup>
					<col width="30%">
					<col width="25%">
					<col width="50%">
					<tr class=prod-proj-tab-hd>
						<td>保障项目</td>
						<td >保险金额</td>
						<td>保障内容</td>
					</tr>
					<#list showDuty as list>
						<tr>
							<td>${list.dutyName}</td>
							<td class=c>${list.showAmnt}</td>
							<td>${list.coverage}</td>
						</tr>
					</#list>
			</table>
		</div>
		<div class="lay-ins-con pro-tab">
			<div class=ins-tit>费用合计</div>
			<table border=0 cellspacing=0 cellpadding=0>
				<tr>
					<th>保费</th>
					<td><dfn>&yen;</dfn><span id=apppricediv class=pricer>${order.totalAmount}</span></td>
				</tr>
				<tr>
					<th>合计</th>
					<td class=ui-money><dfn>&yen;</dfn>${order.totalAmount}</td>
				</tr>
			</table>
		</div>
		<div class="msg-con msg-tips"  style="padding: 10px;">
			<h4 class=f14>投保声明</h4>
			<ol>
				<li>投保时，本投保人已就该产品的保障内容以及保险金额向被保险人进行了明确说明，并征得其同意。</li>
				<li>本投保人兹声明上述各项内容填写属实，并知道如果投保信息不真实，保险公司将有权拒赔，一切后果由本人承担。</li>
				<li>本投保人已阅读该产品详细条款，并特别就条款中有关责任免除和投保人、被保险人义务的内容进行阅读。本投保人特此同意接受条款全部内容。</li>
				<li>根据&laquo;中华人民共和国合同法&raquo;第十一条规定，数据电文是合法的合同表现形式。本人接受保险公司在新一站提供的电子保单作为本投保书成立生效的合法有效凭证，具有完全证据效力。</li>
			</ol>
		</div>
		<div class="button c" style="text-align: center;">
			<div class=btn-more>
				<div class=button><input id="agreecheckbox" type="checkbox" onclick="agreeCheck();"  /> <label for=agreecheckbox>已了解包括免责条款在内的保险合同内容</label>
				</div>
			</div>
				<BUTTON id="returnToTry" class="butM dev_submitButton" onclick="location.href='${base}/shop/donate!buyNowUpdate.action?orderId=${order.id}&productId=${productId}&orderSn=${order.orderSn}'" type="button">&nbsp;&nbsp; 重新填写 &nbsp;&nbsp;</BUTTON> &nbsp;&nbsp;
				<BUTTON id="comfirmToPay" class="butM dev_submitButton" onclick="sure();" type="button" disabled="disabled">&nbsp;&nbsp; 确认并提交 &nbsp;&nbsp;</BUTTON>
			</div>
		</div>
		<div class="form">
		    <input type ="hidden"  id ="orderId" name="orderId" value="${orderId}" />
		    <input type ="hidden" id="productId" name="productId" value="${productId}">
		    <input type="hidden" id="applicantName" name="informationAppnt.applicantName" value="${(informationAppnt.applicantName)}"/>
		    <input type="hidden" id="applicantIdentityType" name="informationAppnt.applicantIdentityType" value="${(informationAppnt.applicantIdentityType)!}"/>
		    <input type="hidden" id="applicantIdentityId" name="informationAppnt.applicantIdentityId" value="${(informationAppnt.applicantIdentityId)!}"/>
		    <input type="hidden" id="applicantMobile" name="informationAppnt.applicantMobile" value="${(informationAppnt.applicantMobile)!}"/>
		    <input type="hidden" id="applicantMail" name="informationAppnt.applicantMail" value="${(informationAppnt.applicantMail)!}"/>
		    <input type="hidden" id="effective" name="effective" value="${information.effective?string("yyyy-MM-dd")}"/>
		</div>
		</form>
	</div>
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
	</div>
</body>
</html>