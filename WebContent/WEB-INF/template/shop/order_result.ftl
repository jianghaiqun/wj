<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
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
<link rel="stylesheet" type="text/css" href="${base}/template/shop/css/order_result.css" />
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
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

<div class="PayStatusArea" align="center"><!--     支付状态  begin   -->
<#if (order.orderStatus="paid")>
<div class="PayStatus_4" align="center">&nbsp;</div>
<#elseif (order.orderStatus="temptorysave")>
<div class="PayStatus_3" align="center">&nbsp;</div>
<#else>
<div class="PayStatus_2" align="center">&nbsp;</div>
</#if>
</div>
	<div class="w900">
		<input type ="hidden" id="insuranceCompanySn" name="insuranceCompanySn" value="${insuranceCompanySn}">
		<!-- zhangjinquan 11180 太平洋财险和泰康均有核保 2012-11-12 -->
		<#if (needUWCheckFlag=="1") !>
		<form id ="resultForm" action="${base}/shop/order!tpyCheckPay.action" method="post">
		<#else>
		<!-- zhangjinquan 11180 其他保险公司直接支付 2012-11-12 -->
		<form id ="resultForm" action="${base}/shop/order!pay.action" method="post"> 
		</#if>
		<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
		<input type="hidden" name="orderSn" id="orderSn" value="${order.orderSn}" > 
		<input type="hidden" name="KID" id="KID" value="${KID}" >
		<h2 class="tit-product"><small class="fr">订单号：${order.orderSn}</small><a href="#">${order.productName}</a><span>【${order.insuranceCompany}】</span></h2>
		
		<div class="lay-ins-con pro-con-info">
				<div class="ins-tit">投保人信息</div>
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr>
								<td  >
									<table class="tab-bd pro-tab" border=0 cellspacing=0 cellpadding=0>
											<tr>
												<th>姓名</th>
												<td>${informationAppnt.applicantName}</td>
											</tr>
											<tr>
											<th >证件类型</th>
											<td>${informationAppnt.applicantIdentityTypeName}</td>
											</tr>
											<tr>
												<th >证件号码</th>
												<td>${informationAppnt.applicantIdentityId}</td>
											</tr>
											<tr>
											<th >性别</th>
											<td>${informationAppnt.applicantSexName}</td>
											</tr>
											<tr>
												<th >出生日期</th>
												<td>${informationAppnt.applicantBirthday}</td>
											</tr>
											<tr>
												<th >手机号码</th>
												<td>${informationAppnt.applicantMobile}</td>
											</tr>
											<tr>
												<th >电子邮箱</th>
												<td>${informationAppnt.applicantMail}</td>
											</tr>
											<#if (iarea!=null&&iarea!="")>
												<tr>
													<th >所在地区</th>
													<td>${iarea}</td>
												</tr>
											</#if>	
											<#if (informationAppnt.applicantAddress!=null&&informationAppnt.applicantAddress!="")>
												<tr>
													<th >联系地址</th>
													<td>${informationAppnt.applicantAddress}</td>
												</tr>
											</#if>
											<#if (informationAppnt.applicantZipCode!=null&&informationAppnt.applicantZipCode!="")>		
												<tr>
													<th >邮编</th>
													<td>${informationAppnt.applicantZipCode}</td>
												</tr>
											</#if>	
									</table>
								</td>
							</tr>
				</table>
		</div>
		
		<div class="lay-ins-con pro-tab">
			<div class="ins-tit"> 被保险人信息
				<span class="ins-int-con">共<span class="num red">1</span>名被保险人， <span class="num red">1</span>份保单，保费合计： 
				<span class=ui-money><dfn>&yen;</dfn>${order.totalAmount}</span></span>
			</div>
		
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
					<#assign index = 1 />
					<#list showInsureds as list>
						<#assign indexnum = 0 />
					    <#list list as i>
					       <#if (indexnum==0)>
						       <tr>
							       <td class=insurant-hd colspan=8><label>被保险人：<#if (i.showName == "showName")>${i.showValue}</#if></label> <label>投保份数：1份</label> <label>保费：<dfn>&yen;</dfn>${order.totalAmount}</label>
							       </td>
						       </tr>
						       <#else>   
							       <#if (indexnum%4==1)>
								       <tr>
							       </#if>
								       <th>${i.showName}</th>
								       <td>${i.showValue}</td>
							       <#if (indexnum%4==0 || indexnum == i.size)>
							       </tr>
							       </#if>
					       </#if>
					       <#assign indexnum=indexnum+1 />
					    </#list>
						<#assign index=index+1 />
					</#list>
			</table>
		</div>
		
		<div class="lay-ins-con pro-con-info">
		<div class="ins-tit">保险期间</div>
		<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td >
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
	<div class=ins-tit>受益人</div>
	<table border=0 cellspacing=0 cellpadding=0>
			<tr>
				<td>法定继承人</td>
			</tr>
	</table>
</div>
	
		<div class="lay-ins-con pro-tab">
		<h3 class=ins-tit>保障信息</h3>
			<table class="tab-bd insurant-tab prod-proj-tab" border=0 cellspacing=0
				cellpadding=0>
				<colgroup>
					<col width="25%">
					<col width="25%">
					<col width="50%">
						<tr class=prod-proj-tab-hd>
							<td >保障项目</td>
							<td>保险金额</td>
							<td>保障内容</td>
						</tr>
						<#list showDuty as list>
							<tr>
								<td>${list.dutyName}</td>
								<#if (list.showAmnt != "")>
									<td class=c>${list.showAmnt}</td>
								<#else>
									<td class=c>-</td> 
								</#if>
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
			</table>
		</div>
		<div class="msg-con msg-tips"  style="padding: 10px;">
			<h4 class=f14  >投保声明</h4>
			<ol>
			<li>本人作为投保人已经将此保险产品全部保障内容和保险金额向被保险人做了明确说明，被保险人对此已表示完全同意。</li>
			<li>本投保人声明均已如实填写上述各项投保信息，如果信息填写不真实或不准确，愿意承担一切责任。</li>
			<li>本投保人和被保险人均已详细阅读并认可该保险产品的各项保险条款，特别是对保险条款中有关责任免除部分已经详细了解并完全认可。同时阅读并已了解开心保提示、客户告知书以及关于投保人、被保险人权利和义务等相关内容，本投保人和被保险人均已认可保险合同的全部内容。</li>
			<li>根据《中华人民共和国合同法》第十一条规定，数据电文是合法有效的合同书面形式。本投保人同意保险公司提供的电子保单或其它保险信息作为本保险合同成立生效的合法有效凭证，并具有完全证据效力。</li>
			</ol>
		</div>
		<div class="button c" style="text-align: center;">
			<div class=btn-more>
				<div class=button><input id="agreecheckbox" type="checkbox" onclick="agreeCheck();"  /> <label for=agreecheckbox>已了解包括免责条款在内的保险合同内容</label>
				</div>
			</div>
				<BUTTON id="comfirmToPay" class="butM dev_submitButton" onclick="sure();" type="button" disabled="disabled">&nbsp;&nbsp; 确认并支付 &nbsp;&nbsp;</BUTTON>
				<div class="btnarea100">
			    <input class="btn300" value="取消订单" type="button" onclick="location.href='${base}/shop/order!orderStatusUpdate.action?orderId=${order.id}&productId=${productId}'" />&nbsp;
			    <input class="btn300" value="重新填写" type="button" onclick="location.href='${base}/shop/order!buyNowUpdate.action?orderId=${order.id}&productId=${productId}&orderSn=${order.orderSn}'"/>
			</div>
		</div>
		</form>
	</div>
	</div>

        <div class="clear"></div>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</body>
</html>