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
	var k = 1;
	for(i=0;i<document.resultForm.length;i++) {
		if (document.resultForm.elements[i].type =="radio" )
		{ 
			if(document.resultForm.elements[i].checked == false && document.resultForm.elements[i+1].checked == false 
			   && document.resultForm.elements[i].disabled == false && document.resultForm.elements[i+1].disabled == false)		
			{
				alert("第" + k + "个问题没有回答。");	   				
				return false;   
			}
			k++;
			i++;
		}
	}
	document.getElementById("resultForm").submit();
	return true;
}
function  selectAll(flag)
{
	for(i=0;i<document.resultForm.length;i++) 
	{
		if (document.resultForm.elements[i].type =="radio" )
		{ 
		   if(flag=="Y")
		   {
		       if(document.resultForm.elements[i].value=="Y")
		       {
		        document.resultForm.elements[i].checked =true;
		       }   
		   	  
		   }
		   else
		   {   
		       if(document.resultForm.elements[i].value=="N")
		       {
		         document.resultForm.elements[i].checked =true;
		       }  
		   }
		}
	}
}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

</div>
	<div class="w900">
		<form id ="resultForm" name="resultForm" action="${base}/shop/order_config!saveOrUpdateHealthInf.action" method="post"> 
		<input type="hidden" name="informationInsureds.id" id="informationInsuredsId" value="${informationInsureds.id}">
		<input type="hidden" name="order.orderSn" id="orderSn" value="${order.orderSn}" > 
		<input type="hidden" name="order.id" id="orderId" value="${order.id}" > 
		<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
		<input type="hidden" name="KID" id="KID" value="${KID}" >
		<h2 class="tit-product"><small class="fr">订单号：${order.orderSn}</small><a href="#">${order.productName}</a><span>【${order.insuranceCompany}】</span></h2>
		
		<div class="lay-ins-con pro-con-info">
				<div class="ins-tit">告知栏</div>
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr>
								<td>
									<table class="tab-bd pro-tab" border=0 cellspacing=0 cellpadding=0>
											<#if (insuredHealthList.size()>0)!>
											    <#assign index = 1 />
											    <#assign hiddenIndex = 0 />
											    <#assign oldType = "" />
												<#list insuredHealthList as list>
												    <#if (list.showInfoType=="#")>
												        <tr>
												        	<td colspan="2">${list.showInfo}</td>
												        	<td width="100px">告知类型</td>
												        	<td width="80px">选择</td>
												        </tr>
													<#else>
													    <tr>
													    	<#if list.showInfoType != oldType >
													    		<#assign hiddenIndex = 1 />
													    		<#assign oldType = list.showInfoType />
													    	<#else>
													    		<#assign hiddenIndex=hiddenIndex+1 />
													    	</#if>
													    	<input type="hidden" name="${list.healthyInfoId}order" id="${list.healthyInfoId}Order" value="${hiddenIndex}">
													    	<td width="40px">${index}</td>
														    <td>${list.showInfo}</td>
														    <td>${list.showInfoType}</td>
														    <td width="80px">
															    &nbsp;<input type="radio" name="${list.healthyInfoId}" id="${list.healthyInfoId}r0" <#if (list.selectFlag=="Y")> checked </#if> value="Y"/>是&nbsp;
															    <input type="radio" name="${list.healthyInfoId}" id="${list.healthyInfoId}r1" <#if (list.selectFlag=="N")> checked </#if> value="N"/>否
												    		</td>
													    </tr>
													    <#assign index=index+1 />
												    </#if>
												</#list>
											</#if>
									</table>
								</td>
							</tr>
						</table>
				</div>
				<div align="right">
					<input type="button" name="allSelect" id="allSelect"  value="全部选是" algin="right" value="Y" onclick="selectAll('Y');" />&nbsp;
					<input type="button" name="allSelect" id="allSelect"   value="全部选否" algin="right"  value="N"  onclick="selectAll('N');"/>&nbsp;
				</div>
				<div class="button c" style="text-align: center;">
					<div class="btnarea100">
					    <input class="btn300" value="上一步" type="button" onclick="location.href='${base}/shop/order_config!buyNowUpdate.action?orderId=${order.id}&productId=${productId}'" />&nbsp;
					    <input class="btn300" value="下一步" type="button" onclick="sure();" />
				    </div>
				</div>
		</form>
	</div>
	</div>

        <div class="clear"></div>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</body>
</html>