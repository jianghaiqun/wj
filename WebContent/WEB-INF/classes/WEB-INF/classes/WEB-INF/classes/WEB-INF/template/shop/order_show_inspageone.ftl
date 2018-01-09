<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<!--<%@ taglib prefix="s" uri="/struts-tags" %>-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>填写投保信息</title> 
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/uploadify.css"/>
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/new_shops.css"/>

<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
<script type="text/javascript" src="${shopStaticPath}/VerifyInput.js"></script>
<script type="text/javascript" src="${shopStaticPath}/Common.js"></script>
<script language="javascript" type="text/javascript">
function loadRelation(){
 	 if("02"=="${typeFlag}"){
	      sethash();
	 }
	 /* zhangjinquan 11180 2012-10-11修改对保险期限的处理，以保证暂存后能够正确带出曾经保存的数据 */
	 var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if(("true"==protectionPeriodFlag) && ("" == "${(sdinformation.ensureLimit)!}")){
			 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 var d = new Date();
		 d.setDate(d.getDate()+${startPeriod});
		 var ndate = d.getFullYear()+"-"+add_zero(d.getMonth()+1)+"-"+add_zero(d.getDate());
		 document.getElementById("effective").value = ndate;
		 var temp = addDate(protectionPeriodTy,protectionPeriodLast, ndate);
		 document.getElementById("fail").value = temp;
		 document.getElementById("fail_").value = temp;
	 }
	 changeInformation("","");
	

}

function addValidation(){
}
function cancelVerification(){
}
var status = "${status}";
var updateOrderId = "${sdorder.id}";
var productId  ="${productId}"
var KID = "${KID}";
var companyCode = "${sdinformation.insuranceCompany}";
var typeFlag = "${typeFlag}";
var p_url = "${jrhsURL}";
var effdatedate = "${sdinformation.startDate}";
var orderSn = "${sdorder.orderSn}";
var productExcelFlag = "${(productExcelFlag)!}";
var loginFlag = "${loginFlag}";
</script>
</head>
<body onLoad="loadRelation();">
<div class="wrapper">
		<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
		<div class="line_log">
				<div class="sdong"></div>
		</div> 
 		<form id="orderInfoForm"  action="" method="post" enctype ="multipart/form-data"> 
   		<div class="line_a shop_sptitle">
		<div class="shop_border">
                 <#assign index = 1 />
                 <#assign moduleType = '' />
	    	     <#list pageModuleList as abc>
	    	        <#include "${abc.moduleURL}">    
	    	        ${abc.ModuleType}
	    	        <#if (index == 1) >
	    	        	<#assign moduleType = abc.ModuleType />
	    	        	<div class="line_a">
	    	        <#elseif moduleType != abc.ModuleType >
	    	      		</div>
	    	        	<div class="line_a">
	    	        	<#assign moduleType = abc.ModuleType />
	    	        	
	    	        </#if>
	    	        <#assign index=index+1 />
	       		 </#list> 
   		 </div>
	       		 
	    <div class="line_a clear_mar">
			  <div class="line_at">确定保单内容</div>
				
			    <table  border="0" class="cp_pricebox">
					  <tr>
					    <td>产品名称</td>
					    <td>保险期限</td>
					    <td>有效份数</td>
					    <td>合计保费</td>
					  </tr>
					  <tr>
					    <td>开心宝预览测试产品 </td>
					    <td>1天</td>
					    <td>1份</td>
					    <td><font id="priceTotle_2" color="red">000000</font>元</td>
					  </tr>
			</table>
			<div class="price_all">共
						<font id="insNum" color="red"><#if (mulInsuredFlag=="rid_td")>${insuredImpCount}<#else>${insuredCount}</#if></font>位被保人，保费
						<font  id = "priceTotle_1" color="red">000000</font>元，合计	<span >	
						<font  color="red">￥ <span id = "priceTotle" >000000</span></font> </span>元
						<span id = "discountPrice" style="display:none;">￥ ${sdorder.discountAmount}</span>
			</div>
 			</div>
   </form>
   </div>
			<script type="text/javascript">
			if ((document.getElementById("showOutGoingParpose") != null) && (typeof(document.getElementById("showOutGoingParpose")) != "undefined"))
			{
				document.getElementById("showOutGoingParpose").innerHTML = "${destinationCountryStr}";
				document.getElementById("checkOutPice").value = "${destinationCountryStr}";
			}
			
			</script>
 </div>
<#include "/wwwroot/kxb/block/community_v1.shtml">
<script	type="text/javascript" src="${shopStaticPath}/iframeTools.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/json2.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/calendar.js"></script>    
<script type="text/javascript" src="${shopStaticPath}/shop/js/shop.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
<script type="text/javascript" src="${shopStaticPath}/swfobject.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.uploadify.js"></script>


 <script type="text/javascript">
 if ((document.getElementById("showOutGoingParpose") != null) && (typeof(document.getElementById("showOutGoingParpose")) != "undefined"))
 {
 	document.getElementById("showOutGoingParpose").innerHTML = "${countryText}";
 	document.getElementById("checkOutPice").value = "${countryText}";
 }
 var insuredCount = "${insuredCount}"; 
 var insFlag = jQuery("#mulInsuredFlag").val();
 
 //pubGetChildrenOccupation("${sdinformationAppnt.applicantOccupation1}","${sdinformationAppnt.applicantOccupation3}");
 pubGetChildrenArea("${sdinformationAppnt.applicantArea1}", "${sdinformationAppnt.applicantArea2}", "${sdinformationAppnt.applicantArea3}","App","");
 pubGetChildrenOccupation("${sdinformationAppnt.applicantOccupation1}","${sdinformationAppnt.applicantOccupation2}","${sdinformationAppnt.applicantOccupation3}","App","");
 if(insFlag==null||insFlag=="undefined"){
	  <#assign index = 1>
	  <#list sdinformationinsuredList as tList>  
			pubGetChildrenArea("${tList.recognizeeArea1}", "${tList.recognizeeArea2}","${tList.applicantArea3}","Ins","${index}"); 
			pubGetChildrenOccupation("${tList.recognizeeOccupation1}","${tList.recognizeeOccupation2}","${tList.recognizeeOccupation3}","Ins","${index}");
			<#assign index = index+1>
	  </#list>
	  <#assign index1 = 1>
	  <#list sdinformationpropertyList as tList>  
			pubGetChildrenArea("${tList.propertyArea1}", "${tList.propertyArea2}","${tList.applicantArea3}","Property","${index1}");
			<#assign index1 = index1+1>
	 </#list>
 }else if(insFlag=="rid_me"){
		pubGetChildrenArea("${sdinformationinsured.recognizeeArea1}", "${sdinformationinsured.recognizeeArea2}","${sdinformationinsured.applicantArea3}","Ins","");
		pubGetChildrenOccupation("${sdinformationinsured.recognizeeOccupation1}","${sdinformationinsured.recognizeeOccupation2}","${sdinformationinsured.recognizeeOccupation3}","Ins","");
		 
 }else{
	 <#assign index = 1>
	  <#list sdinformationinsuredList as tList> 
			pubGetChildrenArea("${tList.recognizeeArea1}", "${tList.recognizeeArea2}","${tList.applicantArea3}","Ins","${index}");
			pubGetChildrenOccupation("${tList.recognizeeOccupation1}","${tList.recognizeeOccupation2}","${tList.recognizeeOccupation3}","Ins","${index}");
			<#assign index = index+1>
	  </#list>
	  <#assign index1 = 1>
	  <#list sdinformationpropertyList as tList>  
			pubGetChildrenArea("${tList.propertyArea1}", "${tList.propertyArea2}","${tList.applicantArea3}","Property","${index1}");
			<#assign index1 = index1+1>
	  </#list>
 }
	</script>
</body>
</html>