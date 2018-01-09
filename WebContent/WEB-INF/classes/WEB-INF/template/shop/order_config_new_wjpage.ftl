<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>填写投保信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/new_shops.css?v=1216"/>
<link href="${shopStaticPath}/template/shop/css/PayPromptSty2.css?v=1216" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/new_preview.css?v=1216" />
<style type="text/css">
#supinformation {width:875px;;height:145px;;overflow-y:hidden;}
.fhxg_btn, .qrgm_btn {
    border: medium none;
    color: #FFFFFF;
    cursor: pointer;
    font-size: 24px;
    font-weight: bold;
    height: 50px;
    line-height: 50px;
    text-align: center;
    width: 139px;
}      

.fhxg_btn {
    background: url("${staticPath}/style/shop/images/fhxg_11.gif") repeat scroll 0 0 transparent;
    color: #868686;
    cursor: pointer;
}
.form input { width: 188px;}
</style>
<script type="text/javascript" src="${shopStaticPath}/VerifyInput.js"></script>
<script type="text/javascript" src="${shopStaticPath}/Common.js"></script>
<script language="javascript" type="text/javascript">
var orderFlag = '${orderFlag}';

function sure(){
	init();
	if("02"=="${typeFlag}"){
	      sethash();
	 }
}

function questionDuty(){
	jQuery("#incomeSourceMessage").parent().children("i").remove();
	jQuery("#rentPrice").parent().children("i").remove();
	jQuery("#obg_detail").parent().children("i").remove();
	jQuery("#buyPrice").parent().children("i").remove();
	jQuery("#partnerName").parent().children("i").remove();
	jQuery("#childrenCount").parent().children("i").remove();
	if(document.getElementsByName("questionPaper.insurePurpose")[4].checked){
		if (!verifyElement('其它|NOTNULL',jQuery("#obg_detail").val(),"obg_detail")) {
			document.getElementById("obg_detail").focus();
			return false;
		}
	}
	
	if(document.getElementsByName("questionPaper.liveStatus")[0].checked){
		if (!verifyElement('购买价格|NOTNULL&NUM',jQuery("#buyPrice").val(),"buyPrice")) {
			document.getElementById("buyPrice").focus();
			return false;
		}
	} else if (jQuery('input:radio[name="questionPaper.rentPrice"]:checked').length == 0){
		jQuery("#rentPrice").after(jQuery("<i style='color:red;font-size:12.5px;'>请选择月租金</i>"));
		document.getElementById("rentPrice_1").focus();
		return false;
	}
	
	if(document.getElementsByName("questionPaper.familySituation")[1].checked){
		if (!verifyElement('配偶姓名|NOTNULL&UFO&LEN>2',jQuery("#partnerName").val(),"partnerName")) {
			document.getElementById("partnerName").focus();
			return false;
		}
		if (!verifyElement('21岁以下孩子人数|NOTNULL&NUM',jQuery("#childrenCount").val(),"childrenCount")) {
			document.getElementById("childrenCount").focus();
			return false;
		}
	}
	
	var incomeSource = "";
	for (var i = 1; i <= 6; i++) {
		if (document.getElementById("income_"+i).checked) {
			if ( i != 1) {
				incomeSource += ",";
			}
			incomeSource += (i - 1);
		}
	}
	document.getElementById("incomeSource").value = incomeSource;
	if (incomeSource == "") {
		jQuery("#incomeSourceMessage").after(jQuery("<i style='color:red;font-size:12.5px;'>请选择收入来源</i>"));
		document.getElementById("income_1").focus();
		return false;
	}

	if (document.getElementById("income_6").checked) {
		if (!verifyElement('详述|NOTNULL',jQuery("#incomeSourceDetail").val(),"incomeSourceDetail")) {
			document.getElementById("incomeSourceDetail").focus();
			return false;
		}
	}
	if(!verifyInput2()){
		document.getElementById("companyName").focus();
		return false;
	}

	orderInfoForm.submit();
}

function doSum1(){
	var value = new Number(document.getElementById("mainAmnt").value) / 10000;
	
	for (var i = 1; i <= 5; i++) {
		if (dateCompare(document.getElementById("CValiDate"+i).value)) {
			value += new Number(document.getElementById("amnt"+i).value);
		}
	}

	document.getElementById("amntSum").value = value;
	sureCheck();
}

function dateCompare(startdate){
	if (startdate == null || startdate =='') {
		return false;
	}
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	if (month < 10) {
		month = "0" + month;
	}
	var date = myDate.getDate();
	if (date < 10) {
		date = "0" + date;
	}
	var currentDate = myDate.getFullYear()+"-"+month+"-"+date;

	if(startdate>=currentDate) {   
		return false;
	} else {
	 	return true;
	}     
}

function init(){
	initRadio();
	var value = new Number(document.getElementById("mainAmnt").value) / 10000;
	for (var i = 1; i <= 5; i++) {
		if (dateCompare(document.getElementById("CValiDate"+i).value)) {
			value += new Number(document.getElementById("amnt"+i).value);
		}
	}
	document.getElementById("amntSum").value = value;
	sureCheck();
	var incomeSource = document.getElementById("incomeSource").value;
	if (incomeSource != null && incomeSource != "") {
		var income = incomeSource.split(",");
		for (var i = 0; i < income.length; i++) {
			document.getElementById("income_"+(new Number(income)+1)).checked = true;
		}
	}
}

function sureCheck(){
	if (amntCheck()) {
		displayNext();
	} else {
		document.getElementById("incomeInf").style.display="block";
		document.getElementById("supInf").style.display="none";
		document.getElementById("nextPath").disabled="disabled";
	}
}

function amntCheck(){
	var amntSum = document.getElementById("amntSum").value;
	var ltotalincome = document.getElementById("ltotalincome").value;
	if(amntSum != 0){
		if (ltotalincome < 10) {
			if (10/amntSum > 0.1) {
				return true;
			}
		} else {
			if(ltotalincome/amntSum >= 0.1){
				return true;
			}
		}
	}
	return false;
}

function displayNext(){
	var check = true;
	if (document.getElementsByName("questionPaper.insureStatus")[0].checked) {
		check = false;
	} else if (document.getElementsByName("questionPaper.workStatus")[1].checked) {
		check = false;
	} else if (document.getElementsByName("questionPaper.operationStatus")[0].checked) {
		check = false;
	} else if (document.getElementsByName("questionPaper.moveDisorder")[0].checked) {
		check = false;
	} else if (document.getElementsByName("questionPaper.drugStatus")[0].checked) {
		check = false;
	} else if (document.getElementsByName("questionPaper.riskyBehaviour")[0].checked) {
		check = false;
	} else if (document.getElementsByName("questionPaper.workPlaceStatus")[0].checked) {
		check = false;
	}

	if (check) {
		document.getElementById("nextPath").disabled="";
		document.getElementById("supInf").style.display="none";
	} else {
		document.getElementById("supInf").style.display="block";
		document.getElementById("nextPath").disabled="disabled";
	}
	document.getElementById("incomeInf").style.display="none";
}

function initRadio(){
	if (!document.getElementsByName("questionPaper.insureStatus")[0].checked 
		&& !document.getElementsByName("questionPaper.insureStatus")[1].checked) {
		document.getElementsByName("questionPaper.insureStatus")[1].checked = true;
	}
	if (!document.getElementsByName("questionPaper.liveStatus")[0].checked 
		&& !document.getElementsByName("questionPaper.liveStatus")[1].checked) {
		document.getElementsByName("questionPaper.liveStatus")[0].checked = true;
	}
	if (!document.getElementsByName("questionPaper.familySituation")[0].checked 
		&& !document.getElementsByName("questionPaper.familySituation")[1].checked) {
		document.getElementsByName("questionPaper.familySituation")[0].checked = true;
	}
	var check = false;
	for (var i=0;i<=7;i++) {
		check = document.getElementsByName("questionPaper.incomeSum")[i].checked;
		if(check){
			if (i == 0) {
				document.getElementById("ltotalincome").value = 9;
			} else if (i < 6) {
				document.getElementById("ltotalincome").value = (i + 1) * 5;
			} else {
				document.getElementById("ltotalincome").value = (i - 2) * 10;
			}
			break;
		}
	}
	if(!check){
		document.getElementsByName("questionPaper.incomeSum")[0].checked = true;
		document.getElementById("ltotalincome").value = 9;
	}
	if (!document.getElementsByName("questionPaper.workStatus")[0].checked 
		&& !document.getElementsByName("questionPaper.workStatus")[1].checked) {
		document.getElementsByName("questionPaper.workStatus")[0].checked = true;
	}
	if (!document.getElementsByName("questionPaper.operationStatus")[0].checked 
		&& !document.getElementsByName("questionPaper.operationStatus")[1].checked) {
		document.getElementsByName("questionPaper.operationStatus")[1].checked = true;
	}
	if (!document.getElementsByName("questionPaper.moveDisorder")[0].checked 
		&& !document.getElementsByName("questionPaper.moveDisorder")[1].checked) {
		document.getElementsByName("questionPaper.moveDisorder")[1].checked = true;
	}
	if (!document.getElementsByName("questionPaper.drugStatus")[0].checked 
		&& !document.getElementsByName("questionPaper.drugStatus")[1].checked) {
		document.getElementsByName("questionPaper.drugStatus")[1].checked = true;
	}
	if (!document.getElementsByName("questionPaper.riskyBehaviour")[0].checked 
		&& !document.getElementsByName("questionPaper.riskyBehaviour")[1].checked) {
		document.getElementsByName("questionPaper.riskyBehaviour")[1].checked = true;
	}
	if (!document.getElementsByName("questionPaper.workPlaceStatus")[0].checked 
		&& !document.getElementsByName("questionPaper.workPlaceStatus")[1].checked) {
		document.getElementsByName("questionPaper.workPlaceStatus")[1].checked = true;
	}
}

</script>
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body onLoad="sure();">
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if> 
<div class="wrapper">


<div class="wrapper">

	<#if (typeFlag !="02")!>
			<div class="line_logo">
				<a href="${siteUrl}">
					<img src="${staticPath}/images/logo_03.gif" alt="开心保保险网，一站式服务，省钱更安心" title="开心保保险网，一站式服务，省钱更安心" width="447" height="72">
				</a>
			</div>
			<div class="up_line_log">
				<div class="sdong_up"></div>
			</div>
			<div class="clear"></div>
		<#else>
			<div class="line_log">
				<div class="sdong"></div>
			</div>
		</#if>

 </div>
 <form id="orderInfoForm" name="orderInfoForm" action="order_config_new!questionPaperSave.action?" method="post" enctype ="multipart/form-data" >
   <input value="${sdinformationinsured.id}" name="sdinformationinsured.id" type="hidden" />
   <input value="${sdorder.orderSn}" name="questionPaper.ordID" type="hidden" />
   <input value="${sdorder.id}" name="sdorder.id" type="hidden" />
   <input value="${sdorder.orderSn}" name="orderSn" type="hidden" />  
   <input value="${questionPaper.id}" name="questionPaper.id" type="hidden" />
   <input value="${mainAmnt}" name="mainAmnt" id="mainAmnt" type="hidden" />
   <input value="${sdinformationinsured.recognizeeName}" name="questionPaper.applicantName" type="hidden" />
   <input value="${sdinformationinsured.recognizeeMobile}" name="questionPaper.applicantMobile" type="hidden" />
   <input value="" name="amntSum" id="amntSum" type="hidden" />
    <input value="" name="questionPaper.ctotalincome" id="ctotalincome" type="hidden" />
    <input value="" name="ltotalincome" id="ltotalincome" type="hidden" />
    <input value="" name="questionPaper.LTtotalincome" id="lttotalincome" type="hidden" />
    <input type="hidden" id="orderFlag" name="orderFlag" value="${orderFlag}"/>
               
   <div class="line_a shop_sptitle">
   <div class="pri ">
   <span class="CInsuranceCompany icon_C${supplierCode2}"></span>
   <div class="shop_titile">${sdinformation.productName}</div> <font size="4" color="red">
	 ￥	<span id="priceTotle_">${sdorder.productTotalPrice}</span>
	 </font>
   </div>
</div>

<div class="shop_border">
    <div class="line_a">
        <div  class="line_at">高额问卷<img width="18" height="16" title="请详细回答以下问题。上述信息将作为您此次投保申请的补充材料。您的信息将由本公司严格为您保管，不会外泄。" src="${staticPath}/style/shop/images/wen_a.png" /><font style="color:#F00;font-size:12px;font-weight:normal;margin-left:15px;">请详细回答以下问题。上述信息将作为您此次投保申请的补充材料。您的信息将由本公司严格为您保管，不会外泄。</font></div>
            <div class="form">
				<p style="width:980px;color:#F00;">
					本告知书各项内容是保险公司承保被保险人所申请的保险金额的重要依据，将成为保险合同的组成部分，请如实填写一下各个项目，<br>如有不实告知足以影响本合同的承保决定，即使已签发保单，保险公司仍有权依法解除本保险合同。<br>
				</p>
				<p></p>
	            <p>
					<span style="width:100px;"><font style="color:#F00;">*</font><b>A</b>&nbsp;被保险人姓名:</span> 
					<input type="text" id="" name="questionPaper.applicantName" value="${sdinformationinsured.recognizeeName}" disabled="disabled" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')" />
				</p>
				<p>
					<span style="width:100px;"><font style="color:#F00">*</font>电话号码:</span>
					<input type="text" id="applicantMobile" name="questionPaper.applicantMobile" disabled="disabled" value="${sdinformationinsured.recognizeeMobile}" />           
				</p>
				<p >
					<span><font style="color:#F00">*</font><b>B</b>&nbsp;保险经历:</span>
				</p>
				<p >
	                <span style="width:500px;"><font style="color:#F00;">*</font>(1)被保险人现时仍生效或同时申请之寿险及意外险(包括在大众及其他保险公司的保障)</span>
				</p>
				<div style="diaplay:block;background:#f7f7f7;height:260px;width:900px;border:1px #CCC solid;margin-bottom:20px;"">
                	<table style="margin-top:10px;">
                    	<tr>
                        	<td style="width:105px;color:#666;margin-right:5px;text-align:center; padding-bottom: 13px;line-height: 1.6em;">险种</td>
                            <td style="width:180px;color:#666;margin-right:5px;text-align:center; padding-bottom: 13px;line-height: 1.6em;">保险公司</td>
                            <td style="width:180px;color:#666;margin-right:5px;text-align:center; padding-bottom: 13px;line-height: 1.6em;">保障期限</td>
                            <td style="width:180px;color:#666;margin-right:5px;text-align:center; padding-bottom: 13px;line-height: 1.6em; ">保险金额(万元)</td>
							<td style="width:180px;color:#666;margin-right:5px;text-align:center; padding-bottom: 13px;line-height: 1.6em;">生效日期</td>
                        </tr>
                        <tr>
                        	<td style="width:105px;color:#666;margin-right:5px;text-align:center;" valign="top" rowspan="3">寿险</td>
                            <td> <input type="text" id="insuranceCompany1" name="questionPaper.insuranceCompany1" value="${questionPaper.insuranceCompany1}" maxlength="200" /></td>
                            <td> <input type="text" id="period1" name="questionPaper.period1"  value="${questionPaper.period1}"  maxlength="40"/> </td>
                            <td> <input type="text" id="amnt1" name="questionPaper.amnt1"   value="${questionPaper.amnt1}" onblur="doSum1();" maxlength="20" /></td>
							<td> <input type="text" id="CValiDate1" name="questionPaper.CValiDate1"  value="${questionPaper.CValiDate1}" 
									onclick="WdatePicker({skin:'whyGreen',startDate:'%y-%M-{%d+1}'})" onFocus = "effchange()" onblur="doSum1();" class="shop_day" /></td>
                        </tr>
                        <tr>
                            <td> <input type="text" id="insuranceCompany2" name="questionPaper.insuranceCompany2"  value="${questionPaper.insuranceCompany2}" maxlength="200" /></td>
                            <td> <input type="text" id="period2" name="questionPaper.period2" value="${questionPaper.period2}" maxlength="40" /> </td>
                            <td> <input type="text" id="amnt2" name="questionPaper.amnt2"  value="${questionPaper.amnt2}" onblur="doSum1();" maxlength="20" /></td>
							<td> <input type="text" id="CValiDate2" name="questionPaper.CValiDate2"  value="${questionPaper.CValiDate2}" 
									onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}',maxDate:'%y-%M-{%d+700}'})" onFocus = "effchange()" onblur="doSum1();" class="shop_day " /></td>
                        </tr>
                        <tr>
                            <td> <input type="text" id="insuranceCompany3" name="questionPaper.insuranceCompany3" value="${questionPaper.insuranceCompany3}" maxlength="200" /></td>
                            <td> <input type="text" id="period3" name="questionPaper.period3" value="${questionPaper.period3}" maxlength="40" /> </td>
                            <td> <input type="text" id="amnt3" name="questionPaper.amnt3" value="${questionPaper.amnt3}" onblur="doSum1();" maxlength="20" /></td>
							<td> <input type="text" id="CValiDate3" name="questionPaper.CValiDate3" value="${questionPaper.CValiDate3}" 
									onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}',maxDate:'%y-%M-{%d+700}'})" onFocus = "effchange()" onblur="doSum1();" class="shop_day " /></td>
                        </tr>
                        <tr>
                        	<td style="width:105px;color:#666;margin-right:5px;text-align:center;" valign="top" rowspan="2">意外险</td>
                            <td> <input type="text" id="insuranceCompany4" name="questionPaper.insuranceCompany4" value="${questionPaper.insuranceCompany4}" maxlength="200" /></td>
                            <td> <input type="text" id="period4" name="questionPaper.period4" value="${questionPaper.period4}" maxlength="40" /> </td>
                            <td> <input type="text" id="amnt4" name="questionPaper.amnt4" value="${questionPaper.amnt4}" onblur="doSum1();" maxlength="20" /></td>
							<td> <input type="text" id="CValiDate4" name="questionPaper.CValiDate4"  value="${questionPaper.CValiDate4}" 
									onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}',maxDate:'%y-%M-{%d+700}'})" onFocus = "effchange()" onblur="doSum1();" class="shop_day " /></td>
                        </tr>
                        <tr>
                            <td> <input type="text" id="insuranceCompany5" name="questionPaper.insuranceCompany5" value="${questionPaper.insuranceCompany5}" maxlength="200" /></td>
                            <td> <input type="text" id="period5" name="questionPaper.period5" value="${questionPaper.period5}" maxlength="40" /> </td>
                            <td> <input type="text" id="amnt5" name="questionPaper.amnt5"  value="${questionPaper.amnt5}" onblur="doSum1();" maxlength="20" /></td>
							<td> <input type="text" id="CValiDate5" name="questionPaper.CValiDate5" value="${questionPaper.CValiDate5}" 
									onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}',maxDate:'%y-%M-{%d+700}'})" onFocus = "effchange()" onblur="doSum1();" class="shop_day " /></td>
                        </tr>
                    </table>
				</div>
				<p >
	                <span style="width:500px;"><font style="color:#F00;">*</font>(2)被保险人是否曾被保险公司解除合同或申请人身保险而被延期、拒保或附加条件承保？</span>
                </p>
			    <p>
					<input type="radio" value="0" id="live_1" class="sex_radios" name="questionPaper.insureStatus" onclick="sureCheck();" <#if (questionPaper.insureStatus=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
					<input type="radio" value="1" id="live_2" class="sex_radios" name="questionPaper.insureStatus" onclick="sureCheck();" <#if (questionPaper.insureStatus=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
                </p>		
				<div id="otherInf">
					<p>
						<span><b>C</b>&nbsp;保险目的:</span>
					</p>
					<p>    
                        <input type="radio" value="0" id="obj_1" class="sex_radios" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="0")> checked </#if> />&nbsp;还贷保障&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="obj_2" class="sex_radios" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="1")> checked </#if> />&nbsp;家庭保障&nbsp;&nbsp;&nbsp;
						<input type="radio" value="2" id="obj_3" class="sex_radios" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="2")> checked </#if> />&nbsp;商业捐献&nbsp;&nbsp;&nbsp;
						<input type="radio" value="3" id="obj_4" class="sex_radios" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="3")> checked </#if> />&nbsp;抵押偿还&nbsp;&nbsp;&nbsp;
						<input type="radio" value="4" id="obj_5" class="sex_radios" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="4")> checked </#if> />&nbsp;如有其它，请列明
						<input type="text" id="obg_detail" name="questionPaper.otherInsurePurpose" value="${questionPaper.otherInsurePurpose}" maxlength="255" class="" />&nbsp;<label class="requireField"></label>
					</p>
					<p>
						<span><b>D</b>&nbsp;居住情况:</span>
					</p>
					<p >
						<span style="width:225px;"><font style="color:#F00;">*</font>(1)住所情况</span>
					</p>
					<p>     
						<input type="radio" value="0" id="liveStatus_1" class="sex_radios" name="questionPaper.liveStatus" <#if (questionPaper.liveStatus=="0")> checked </#if> />&nbsp;自有居所 购买价格(RMB) 
						<input type="text" id="buyPrice" name="questionPaper.buyPrice" value="${questionPaper.buyPrice}" class="" maxlength="255" />&nbsp;<label class="requireField"></label>
					</p>
					<p> 
						<input type="radio" value="1" id="liveStatus_2" class="sex_radios" name="questionPaper.liveStatus" <#if (questionPaper.liveStatus=="1")> checked </#if> />&nbsp;<label class="no-border" >租借住所， *月租金</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="0" id="rentPrice_1" class="sex_radios" name="questionPaper.rentPrice" <#if (questionPaper.rentPrice=="0")> checked </#if> />&nbsp;<label class="no-border" >3000元以下；</label>&nbsp;&nbsp;
						<input type="radio" value="1" id="rentPrice_2" class="sex_radios" name="questionPaper.rentPrice" <#if (questionPaper.rentPrice=="1")> checked </#if> />&nbsp;<label class="no-border" >3000 - 5000元；</label>&nbsp;&nbsp;
						<input type="radio" value="2" id="rentPrice_3" class="sex_radios" name="questionPaper.rentPrice" <#if (questionPaper.rentPrice=="2")> checked </#if> />&nbsp;<label class="no-border" >5000元以上</label>&nbsp;<span><label id="rentPrice" class="requireField"></label></span>
					</p>
					<p >
						<span style="width:225px;">(2)被保险人在现住地址已居住多久?</span>
					</p>
					<p>    
						<input type="radio" value="0" id="time_1" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="0")> checked </#if> />&nbsp;<label class="no-border" >一年以下</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="time_2" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="1")> checked </#if> />&nbsp;<label class="no-border" >1-3年</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="2" id="time_3" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="2")> checked </#if> />&nbsp;<label class="no-border" >3-6年</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="3" id="time_4" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="3")> checked </#if> />&nbsp;<label class="no-border" >更长时间</label>&nbsp;&nbsp;&nbsp;             	                
					</p>
					<p >
						<span style="width:225px;"><font style="color:#F00;">*</font><b>E</b>&nbsp;家庭情况:</span>
					</p>
					<p>    
						<input type="radio" value="0" id="family_1" class="sex_radios" name="questionPaper.familySituation" <#if (questionPaper.familySituation=="0")> checked </#if> />&nbsp;独身&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="family_2" class="sex_radios" name="questionPaper.familySituation" <#if (questionPaper.familySituation=="1")> checked </#if> />&nbsp;已婚，配偶姓名
						<input type="text" id="partnerName" name="questionPaper.partnerName" value="${questionPaper.partnerName}" maxlength="20" class="" /> ,21岁以下孩子人数：
						<input type="text" id="childrenCount" name="questionPaper.childrenCount" value="${questionPaper.childrenCount}" maxlength="2" class="" />&nbsp;<label class="requireField"></label>
					</p>
					<p >
						<span style="width:225px;"><b>F</b>&nbsp;资产情况</span>
					</p>
					<p >
						<span style="width:250px;">(1)其它拥有财产 (如 房屋，公司，土地):</span>
					</p>
					<p>
						<span>财产名称:</span> 
						<input type="text" id="propertyName" name="questionPaper.propertyName1" value="${questionPaper.propertyName1}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength="50"/>
						<input type="text" id="propertyName1" name="questionPaper.propertyName2" value="${questionPaper.propertyName2}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength="50"/>                
					</p>
					<p>
						<span>地址:</span> 
						<input type="text" id="propertyAdress" name="questionPaper.propertyAdress1" value="${questionPaper.propertyAdress1}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength="50"/>
						<input type="text" id="propertyAdress1" name="questionPaper.propertyAdress2" value="${questionPaper.propertyAdress2}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength="50"/>                
					</p>
					<p>
						<span>市值:</span>
						<input type="text" id="propertyValue" name="questionPaper.propertyValue1" value="${questionPaper.propertyValue1}"  class=""  maxlength="50"/>
						<input type="text" id="propertyValue1" name="questionPaper.propertyValue2" value="${questionPaper.propertyValue2}"  class=""  maxlength="50"/>                    
					</p>
					<p>
						<span>估计价值:</span> 
						<input type="text" id="gpropertyValue" name="questionPaper.GpropertyValue1" value="${questionPaper.gpropertyValue1}"  class=""  maxlength="50"/>
						<input type="text" id="gpropertyValue1" name="questionPaper.GpropertyValue2" value="${questionPaper.gpropertyValue2}"  class=""  maxlength="50"/>
					</p>
					<p >
						<span style="width:100px;">(2)拥有汽车数量:</span>
						<input type="radio" value="0" id="carNum_1" class="sex_radios" name="questionPaper.carNum" <#if (questionPaper.carNum=="0")> checked </#if> />&nbsp;<label class="no-border" >1</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="carNum_2" class="sex_radios" name="questionPaper.carNum" <#if (questionPaper.carNum=="1")> checked </#if> />&nbsp;<label class="no-border" >2</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="2" id="carNum_3" class="sex_radios" name="questionPaper.carNum" <#if (questionPaper.carNum=="2")> checked </#if> />&nbsp;<label class="no-border" >&gt;3</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p>
						<span>汽车品牌:</span> 
						<input type="text" id="carBrand1" name="questionPaper.carBrand1" value="${questionPaper.carBrand1}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  maxlength="50"/>
						<input type="text" id="carBrand2" name="questionPaper.carBrand2" value="${questionPaper.carBrand2}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"   maxlength="50"/>
						<input type="text" id="carBrand3" name="questionPaper.carBrand3" value="${questionPaper.carBrand3}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"   maxlength="50"/>
					</p>
					<p >
						<span style="width:225px;"><b>G</b>&nbsp;年收入情况</span>
					</p>
					<p >
						<span style="width:100px;"><font style="color:#F00;">*</font>(1)收入来源:</span>
						<input value="${questionPaper.incomeSource}" name="questionPaper.incomeSource" id="incomeSource" type="hidden" />
					</p>
					<p >
						<input type="checkbox" value="0" id="income_1" class="sex_radios" name="income_Source" />&nbsp;工资&nbsp;&nbsp;
						<input type="checkbox" value="1" id="income_2" class="sex_radios" name="income_Source" />&nbsp;奖金和分红&nbsp;&nbsp;
						<input type="checkbox" value="2" id="income_3" class="sex_radios" name="income_Source" />&nbsp;继承遗产&nbsp;&nbsp;
						<input type="checkbox" value="3" id="income_4" class="sex_radios" name="income_Source" />&nbsp;房屋租赁&nbsp;&nbsp;
						<input type="checkbox" value="4" id="income_5" class="sex_radios" name="income_Source" />&nbsp;投资收益&nbsp;&nbsp;
						<input type="checkbox" value="5" id="income_6" class="sex_radios" name="income_Source" />&nbsp;其它，请详述&nbsp;&nbsp;
						<input type="text" id="incomeSourceDetail" name="questionPaper.incomeSourceDetail" value="${questionPaper.incomeSourceDetail}"  class=""  maxlength="255"/> 
						&nbsp;<label id="incomeSourceMessage" class="requireField"></label>
					</p>
					<p >
						<span style="width:225px;"><font style="color:#F00;">*</font>(2)被保险人去年总计年收入约为:</span>
					</p>
					<div style="diaplay:block;background:#f7f7f7;width:450px;border:1px #CCC solid;margin-bottom:20px;overflow:hidden;">
						<table style="margin-top:5px;">
							<tr>
								<td style="width:110px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="0" id="incomeSum_1" class="sex_radios" onclick="document.getElementById('ltotalincome').value=9;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="0")> checked </#if> />&nbsp;<label class="no-border" >&lt;10万元</label>&nbsp;&nbsp;&nbsp;</td>
								<td style="width:100px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="1" id="incomeSum_2" class="sex_radios" onclick="document.getElementById('ltotalincome').value=10;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="1")> checked </#if> />&nbsp;<label class="no-border" >10万元</label>&nbsp;&nbsp;&nbsp;</td>
								<td style="width:100px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="2" id="incomeSum_3" class="sex_radios" onclick="document.getElementById('ltotalincome').value=15;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="2")> checked </#if> />&nbsp;<label class="no-border" >15万元</label>&nbsp;&nbsp;&nbsp;</td>
								<td style="width:120px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="3" id="incomeSum_4" class="sex_radios" onclick="document.getElementById('ltotalincome').value=20;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="3")> checked </#if> />&nbsp;<label class="no-border" >20万元</label>&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td style="width:110px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="4" id="incomeSum_5" class="sex_radios" onclick="document.getElementById('ltotalincome').value=25;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="4")> checked </#if> />&nbsp;<label class="no-border" >25万元</label>&nbsp;&nbsp;&nbsp;</td>
								<td style="width:100px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="5" id="incomeSum_6" class="sex_radios" onclick="document.getElementById('ltotalincome').value=30;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="5")> checked </#if> />&nbsp;<label class="no-border" >30万元</label>&nbsp;&nbsp;&nbsp;</td>
								<td style="width:100px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="6" id="incomeSum_7" class="sex_radios" onclick="document.getElementById('ltotalincome').value=40;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="6")> checked </#if> />&nbsp;<label class="no-border" >40万元</label>&nbsp;&nbsp;&nbsp;</td>
								<td style="width:120px;color:#666;margin-right:5px;text-align:center;">
									<input type="radio" value="7" id="incomeSum_8" class="sex_radios" onclick="document.getElementById('ltotalincome').value=50;sureCheck();" name="questionPaper.incomeSum" <#if (questionPaper.incomeSum=="7")> checked </#if> />&nbsp;<label class="no-border" >&ge;50万元</label>&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
					</div>
					<p >
						<span style="width:225px;"><b>H</b>&nbsp;负债情况</span>
					</p>
					<p >
						<span style="width:225px;">(1)被保险人是否存在负债情形?</span>
					</p>
					<p >
						<input type="radio" value="0" id="asset_1" class="sex_radios" name="questionPaper.assetStatus" <#if (questionPaper.assetStatus=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="asset_2" class="sex_radios" name="questionPaper.assetStatus" <#if (questionPaper.assetStatus=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p >
						<span style="width:225px;">(2)如是，请详述：</span>
					</p>
					<div style="diaplay:block;background:#f7f7f7;height:160px;width:705px;border:1px #CCC solid;margin-bottom:20px;">
						<table style="margin-top:10px;">
							<tr>
								<td style="width:105px;color:#666;margin-right:5px;text-align:center;">负债次数</td>
								<td style="width:180px;color:#666;margin-right:5px;text-align:center;">1</td>
								<td style="width:180px;color:#666;margin-right:5px;text-align:center;">2</td>
								<td style="width:180px;color:#666;margin-right:5px;text-align:center;">3</td>
							</tr>
							<tr>
								<td style="width:105px;color:#666;margin-right:5px;text-align:center;">债务种类</td>
								<td> <input type="text" id="debtKind_1" name="questionPaper.debtKind1" value="${questionPaper.debtKind1}" maxlength="255" /></td>
								<td> <input type="text" id="debtKind_2" name="questionPaper.debtKind2" value="${questionPaper.debtKind2}" maxlength="255" /></td>
								<td> <input type="text" id="debtKind_3" name="questionPaper.debtKind3" value="${questionPaper.debtKind3}" maxlength="255" /></td>
							</tr>
							<tr>
								<td style="width:105px;color:#666;margin-right:5px;text-align:center;">债务金额</td>
								<td> <input type="text" id="debtMoney_1" name="questionPaper.debtMoney1" value="${questionPaper.debtMoney1}" maxlength="100" /></td>
								<td> <input type="text" id="debtMoney_2" name="questionPaper.debtMoney2" value="${questionPaper.debtMoney2}" maxlength="100" /></td>
								<td> <input type="text" id="debtMoney_3" name="questionPaper.debtMoney3" value="${questionPaper.debtMoney3}" maxlength="100" /></td>
							</tr>
							<tr>
								<td style="width:105px;color:#666;margin-right:5px;text-align:center;">偿还条件及时间</td>
								<td> <input type="text" id="repayContent_1" name="questionPaper.repayContent1" value="${questionPaper.repayContent1}" maxlength="100" /></td>
								<td> <input type="text" id="repayContent_2" name="questionPaper.repayContent2" value="${questionPaper.repayContent2}" maxlength="100" /></td>
								<td> <input type="text" id="repayContent_3" name="questionPaper.repayContent3" value="${questionPaper.repayContent3}" maxlength="100" /></td>
							</tr>
						</table>
					</div>
					<p >
						<span style="width:225px;"><b>I</b>&nbsp;工作情况:</span>
					</p>
					<p >
					 	<span style="width:800px;"><font style="color:#F00;">*</font>(1)被保险人目前所在工作单位名称：
               			<input type="text" id="companyName" name="sdinformationAppnt.companyName" value="${questionPaper.companyName}" verify="工作单位名称|notnull" 
							onblur="verifyElement('工作单位名称|notnull',this.value,this.id);" maxlength=100 />
              	 		<label class="requireField"></label></span> 
					</p>
					<p >
						<span style="width:225px;">(2)被保险人在现工作单位服务年数？</span>
					</p>
					<p>    
						<input type="radio" value="0" id="work_1" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.serviceYear=="0")> checked </#if> />&nbsp;<label class="no-border" >一年以下</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="work_2" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.serviceYear=="1")> checked </#if> />&nbsp;<label class="no-border" >1-3年</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="2" id="work_3" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.serviceYear=="2")> checked </#if> />&nbsp;<label class="no-border" >3-6年</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="3" id="work_4" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.serviceYear=="3")> checked </#if> />&nbsp;<label class="no-border" >更长</label>&nbsp;&nbsp;&nbsp;             	                
					</p>
					<p >
						<span style="width:225px;"><b>J</b>&nbsp;风险告知:</span>
					</p>
					<p >
						<span style="width:225px;"><font style="color:#F00;">*</font>(1)被保险人是否能正常劳动工作?</span>
					</p>
					<p >
						<input type="radio" value="0" id="workStatus_1" class="sex_radios" name="questionPaper.workStatus" onclick="sureCheck();" <#if (questionPaper.workStatus=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="workStatus_2" class="sex_radios" name="questionPaper.workStatus" onclick="sureCheck();" <#if (questionPaper.workStatus=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p >
						<span style="width:400px;"><font style="color:#F00;">*</font>(2)被保险人曾或正接受任何外科手术或住院医疗?</span>
					</p>
					<p >
						<input type="radio" value="0" id="operationStatus_1" class="sex_radios" name="questionPaper.operationStatus" onclick="sureCheck();" <#if (questionPaper.operationStatus=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="operationStatus_2" class="sex_radios" name="questionPaper.operationStatus" onclick="sureCheck();" <#if (questionPaper.operationStatus=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p >
						<span style="width:800px;"><font style="color:#F00;">*</font>(3)被保险人是否有行动或智能障碍、失明、聋哑、跛行、无脊柱、胸廓、四肢、五官、手指、足趾缺损或畸形；言语、咀嚼、视力、听力、嗅觉、<br>&nbsp;&nbsp;&nbsp;&nbsp;四肢及中枢神经系统机能障碍?</span>
					</p>
					<p >
						<input type="radio" value="0" id="moveDisorder_1" class="sex_radios" name="questionPaper.moveDisorder" onclick="sureCheck();" <#if (questionPaper.moveDisorder=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="moveDisorder_2" class="sex_radios" name="questionPaper.moveDisorder" onclick="sureCheck();" <#if (questionPaper.moveDisorder=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p >
						<span style="width:800px;"><font style="color:#F00;">*</font>(4)被保险人是否使用镇静安眠剂、迷幻剂、违禁药物、毒品，以及其他各类药物滥用成瘾等？</span>
					</p>
					<p >
						<input type="radio" value="0" id="drugStatus_1" class="sex_radios" name="questionPaper.drugStatus" onclick="sureCheck();" <#if (questionPaper.drugStatus=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="drugStatus_2" class="sex_radios" name="questionPaper.drugStatus" onclick="sureCheck();" <#if (questionPaper.drugStatus=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p >
						<span style="width:800px;"><font style="color:#F00;">*</font>(5)被保险人是否具有经常从事潜水、滑水、滑雪、滑冰、驾驶航空机具、热气球、滑翔、跳水、跳伞、蹦极跳、武术空手道、击剑、马术、狩猎、<br>&nbsp;&nbsp;&nbsp;&nbsp;攀岩运动、探险活动、摔跤、登山、漂流、特技表演及车辆竞赛等危险活动的爱好或行为?</span>
					</p>
					<p >
						<input type="radio" value="0" id="riskyBehaviour_1" class="sex_radios" name="questionPaper.riskyBehaviour" onclick="sureCheck();" <#if (questionPaper.riskyBehaviour=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="riskyBehaviour_2" class="sex_radios" name="questionPaper.riskyBehaviour" onclick="sureCheck();" <#if (questionPaper.riskyBehaviour=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p >
						<span style="width:800px;"><font style="color:#F00;">*</font>(6)被保险人是否因工作原因需要经常前往危险地区或国家？（如正在或经常发生自然灾害、病疫、战乱、动乱、种族冲突、政局动荡等）</span>
					</p>
					<p >
						<input type="radio" value="0" id="workPlaceStatus_1" class="sex_radios" name="questionPaper.workPlaceStatus" onclick="sureCheck();" <#if (questionPaper.workPlaceStatus=="0")> checked </#if> />&nbsp;<label class="no-border" >是</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" value="1" id="workPlaceStatus_2" class="sex_radios" name="questionPaper.workPlaceStatus" onclick="sureCheck();" <#if (questionPaper.workPlaceStatus=="1")> checked </#if> />&nbsp;<label class="no-border" >否</label>&nbsp;&nbsp;&nbsp;
					</p>
					<p>
						<span style="width:980px;color:#F00;">声明：本告知书以上各项回答均属实，并以此作为此保险合同之依据。如上述资料不属实，任何据此告知书而缮发的保险合同将视作无效。</span> 
					</p>
					<p style="margin-bottom:40px;">
						<span><font style="color:#F00;">*</font>日期:</span> 
						<input id="effective1" name="effective1" type="text" disabled="disabled"  value="${questionPaper.effective}"   />
						<input id="effective" name="questionPaper.effective" type="hidden" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'})" value="${questionPaper.effective}"  />
					</p>   
				</div>
			</div>
		</div>
	</div>
	<div class="syr2">
		<input type="button" class="fhxg_btn" value="返回修改 " onclick="location.href='${base}/shop/order_config_new!buyNowUpdate.action?orderSn={sdorder.orderSn}&orderId=${sdorder.id}&productId=${sdinformation.productId}'" />  
		<input type="button" class="qrgm_btn" value="下一步" onclick="questionDuty();" id="nextPath" disabled="disabled" /><br />
		<font color="red">请把必填信息填写完整</font>
		<font color="red" style="display:none;" id="incomeInf">您的去年总收入未通过校验，不能购买此产品！</font>
		<font color="red" style="display:none;" id="supInf">您的信息未通过校验，不能购买此产品！</font>
	</div>
	<div class="clear"></div>
 </form>
 </div>
 <#if (typeFlag !="02")!>
 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
 <#include "/wwwroot/kxb/block/community_v1.shtml">
 <script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.validate.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="${shopStaticPath}/productDutyNew.js"></script>
</body>
</html>