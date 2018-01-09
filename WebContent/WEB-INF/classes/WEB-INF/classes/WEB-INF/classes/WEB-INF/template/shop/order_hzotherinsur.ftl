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
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/template/shop/css/insure.css" />
<link rel="stylesheet" type="text/css" href="${base}/template/shop/css/insure_table.css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
<style type="text/css">
<!--  
table.datagrid3 { table-layout: fixed; } 
table.datagrid3 td {overflow:hidden; white-space:nowrap; text-overflow:ellipsis;}

.btn1_mouseout {
	 BORDER-RIGHT: #7EBF4F 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7EBF4F 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#B3D997); BORDER-LEFT: #7EBF4F 1px solid; CURSOR: hand; PADDING-TOP: 2px; BORDER-BOTTOM: #7EBF4F 1px solid
} 
.btn1_mouseover {
BORDER-RIGHT: #7EBF4F 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7EBF4F 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#CAE4B6); BORDER-LEFT: #7EBF4F 1px solid; CURSOR: hand;PADDING-TOP: 2px; BORDER-BOTTOM: #7EBF4F 1px solid
} 
label.error {   
    color: red;display: none;  font-size:12px; 
}

.btn-more{padding:10px 0}
	.btn-more label{margin:0 7px}
	.button{padding-bottom:20px;margin-top:10px;overflow:hidden;zoom:1;}
	.butL,.butM,.butS,.butB,.butMax{background-image:url(/images/buts.png);background-color:#fd623c;cursor:pointer;height:27px;line-height:26px;border:0;padding:0;font-size:14px;color:#fff;font-weight:bold;text-shadow:1px 1px #c62a0a;}
	
-->
</style>

<script language="javascript" type="text/javascript">
function getFfrelas(temp,type){
	var frelas = eval("("+'${ffrelas}'+")");
	var value = "";
	var type2 = "";
	var str =  "";
	if(frelas!=null && frelas.length>0){
		for (var i=0;i<frelas.length;i++){
			if(frelas[i]!=null){
				if(type==frelas[i].factorType1){
					str = getValue(temp, type ,frelas[i].factorValue1 ,frelas[i].factorValue2);
					type2 = frelas[i].factorType2;
				}else if(type==frelas[i].factorType2){
					str = getValue(temp,type ,frelas[i].factorValue2 ,frelas[i].factorValue1);
					type2 = frelas[i].factorType1;
				}
				if(str!=null && str != ""){
					value = value + str + "#";
				}
			}
		}
		changePremByAFC(temp,type);
		changePremByAFC(value.split("#")[0],type2);
		return changeAFC(type2, value);
	}
}
function getValue(temp ,type ,temp1 ,temp2){
	var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
	if("TextAge" == type){
		var age = null;
		var birthday = null;
		if("本人"==s){
			birthday = document.getElementById("applicantBirthday").value;
		}else{
			birthday = document.getElementById("recognizeeBirthday1").value;
		}
		if(birthday != null && birthday != ""){
			age = getPersonAge(birthday);
		}
		var ageGroup = temp1.split("-");
		if(age != null){
			if(ageGroup[0].split("Y")[0] == age ){
				return temp2;
			}else if(ageGroup.length>1){
				if(ageGroup[0].split("Y")[0] < age && age < ageGroup[1].split("Y")[0]){
					return temp2;
				}
			}
		}
	}else if("Sex" == type){
		var localSex = "";
		var productSex = "";
		if("本人"==s){
			localsex = document.getElementById("recognizeeSex1").value;
		}else{
			localsex = document.getElementById("recognizeeSex1").value;
		}
		if("0" == localSex){
			productSex = "1";
		}else {
			productSex = "2";
		}
		if(productSex == temp1){
			return temp2;
		}
	}else{
		if(temp == temp1){
			return temp2;
		}
	}
	return "";
}
function changeAFC(type, value){
	var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
	if("TextAge" == type){
		var age = null;
		var birthday = null;
		if("本人"==s){
			birthday = document.getElementById("applicantBirthday").value;
		}else{
			birthday = document.getElementById("recognizeeBirthday1").value;
		}
		if(birthday != null && birthday != ""){
			age = getPersonAge(birthday);
		}
		if(age!=null){
			if(!checkAge(age,value)){
				alert("被保人年龄不符合投保规则");
				return "ERROR";
			}
		}
	}else if("Sex" == type){
		if("本人"==s){
			if(value.split("#")[0]=="1"){
				document.getElementById("applicantSex").value = "0";
			}else{
				document.getElementById("applicantSex").value = "1";
			}
		}else{
			if(value.split("#")[0]=="1"){
				document.getElementById("recognizeeSex1").value = "0";
			}else{
				document.getElementById("recognizeeSex1").value = "1";
			}
		}
	}else if("Grade" == type){
		if(value.split("#")!=null&&value.split("#")[0]!=null&&value.split("#")!=""){
			document.getElementById("grade").value = value.split("#")[0];
		}
	}else if("FeeYear" == type){
		if(value.split("#")!=null&&value.split("#")[0]!=null&&value.split("#")!=""){
			document.getElementById("feeYear").value = value.split("#")[0];
		}
	}else if("AppType" == type){
		if(value.split("#")!=null&&value.split("#")[0]!=null&&value.split("#")!=""){
			document.getElementById("appType").value = value.split("#")[0];
		}
	}else if("AppLevel" == type){
		if(value.split("#")!=null&&value.split("#")[0]!=null&&value.split("#")!=""){
			document.getElementById("appLevel").value = value.split("#")[0];
		}
	}else if("Period" == type){
		if(value.split("#")!=null&&value.split("#")[0]!=null&&value.split("#")!=""){
			document.getElementById("period").value = value.split("#")[0];
		}
	}else if("Amnt" == type){
		if(value.split("#")!=null&&value.split("#")[0]!=null&&value.split("#")!=""){
			document.getElementById("amnt").value = value.split("#")[0];
		}
	}else if("Plan" == type){
		if(value.split("#")!=null&&value.split("#")[0]!=null&&value.split("#")!=""){
			document.getElementById("plan").value = value.split("#")[0];
		}   
	}
	return "";   
}
function checkAge(age,value){
	var ageGroup = value.split("#");
	if(ageGroup != null && ageGroup.length>0){
		for(var i=0;i<ageGroup.length;i++){
			var ageGroupArray = ageGroup[i].split("-");
			if(ageGroupArray!=null){
				if(ageGroupArray.length>1){
					if(ageGroupArray[0].split("Y")[0] < age && age < ageGroupArray[1].split("Y")[0]){
						return true;
					}
				}else{
					var ageSelect = ageGroup[i].split("Y")[0];
					if(ageSelect == age){
						return true;
					}
				}
				if(i==ageGroup.length){
					return false;
				}
			}
		}
	}
}
function changePremByAFC(temp,type){
	var fdapAll = eval("("+'${fdapAll}'+")");
	var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
	if(fdapAll != null){
		var str = "";
		for(var j = 0; j<fdapAll.length; j++){
			if(fdapAll[j]!=null){
				if(type != "TextAge" && type != "Sex"){
					if(type == fdapAll[j].backUp2 && temp == fdapAll[j].appFactorValue){
						str = str +fdapAll[j].dutyFactorID + ":" + fdapAll[j].backUp1 + ":" + fdapAll[j].amnt + "#";
					}
				}else if(type == "Sex"){
					var sex = null;
					if("本人"==s){
						sex = document.getElementById("applicantSex").value;
					}else{
						sex = document.getElementById("recognizeeSex1").value;
					}
					if(sex != null){
						if(sex == "0"){
							temp = "1";
						}else{
							temp = "2"; 
						}
					}
					if(temp == fdapAll[j].appFactorValue){
						str = str +fdapAll[j].dutyFactorID + ":" + fdapAll[j].backUp1 + ":" + fdapAll[j].amnt + "#";
					}
				}else if(type == "TextAge"){
					var age = null;
					var birthday = null;
					if("本人"==s){
						birthday = document.getElementById("applicantBirthday").value;
					}else{
						birthday = document.getElementById("recognizeeBirthday1").value;
					}
					if(birthday != null && birthday != ""){
						age = getPersonAge(birthday);
					}
					if(type == fdapAll[j].backUp2 && age != null){
						var ageArray = fdapAll[j].appFactorValue.split("-");
						if(ageArray!=null && ageArray.length>0){
							if(age<=ageArray[1].substring(0,1) && age>= ageArray[0].substring(0,1)){
								str = str +fdapAll[j].dutyFactorID + ":" + fdapAll[j].backUp1 + ":" + fdapAll[j].amnt + "#";
							}
						}else if(age = ageArray[0].substring(0,1)){
							str = str +fdapAll[j].dutyFactorID + ":" + fdapAll[j].backUp1 + ":" + fdapAll[j].amnt + "#";
						}
					}
				}
			}
		}
		var array = str.split("#");
		if(array!=null && array.length>0){
			var dutyId = new Array();
			var dutyIdNum = 0;
			for(var i = 0 ; i<array.length-1 ; i++){
				var dutyIdChidl = null;
				var arraychild1 = array[i].split(":");
				var arraychild2 = array[i+1].split(":");
				if(arraychild1[0] == arraychild2[0]){
					dutyId[dutyIdNum] = array[i] + "#" + array[i+1];
				}else{
					dutyId[dutyIdNum] = array[i];
				}
				dutyIdNum = dutyIdNum + 1;
			}
			for(var i=0 ; i<dutyId.length; i++){
				var sutyIdString1 = dutyId[i].split("#");
				var sutyIdString2 = sutyIdString1[0].split(":");
				var dutyIdString = "Fduty-"+sutyIdString2[0];
				var childrenOPT = document.getElementById(dutyIdString);
				childrenOPT.options.length=0;
				for(var z = 0 ; z < sutyIdString1.length ; z++){
					var dutyShow = sutyIdString1[z].split(":");
					var oo=new Option(dutyShow[2],dutyShow[1]);
					childrenOPT.options[childrenOPT.options.length]=oo;
					if(z==0){
						var amnt = "amnt" + sutyIdString2[0];
						var amntDis = "amntDis" + sutyIdString2[0];
						document.getElementById(amnt).value=dutyShow[1];
						document.getElementById(amntDis).value=dutyShow[2];
					}
				}
			}
		}
	}
}
function changeAmnt(temp,dutyFactorID){
	var fdapAll = eval("("+'${fdapAll}'+")");
	var amnt = "amnt" + dutyFactorID;
	var amntDis = "amntDis" + dutyFactorID;
	if(fdapAll!=null && fdapAll.length>0){
		for(var i=0;i<fdapAll.length;i++){
			if(fdapAll[i]!=null){
				if(temp == fdapAll[i].backUp1){
					document.getElementById(amnt).value=fdapAll[i].backUp1;
					document.getElementById(amntDis).value=fdapAll[i].amnt;
				}
			}
		}
	}
}
function loadDutyAndAFC(){
	var temp = null;
	var type = null;
	var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
	if(document.getElementById("plan") != null){
		temp = document.getElementById("plan").value;
		type = "Plan";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	if(document.getElementById("grade") != null){
		temp = document.getElementById("grade").value;
		type = "Grade";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	if(document.getElementById("feeYear") != null){
		temp = document.getElementById("feeYear").value;
		type = "FeeYear";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	if(document.getElementById("appType") != null){
		temp = document.getElementById("appType").value;
		type = "AppType";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	if(document.getElementById("appLevel") != null){
		temp = document.getElementById("appLevel").value;
		type = "AppLevel";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	if(document.getElementById("period") != null){
		temp = document.getElementById("period").value;
		type = "Period";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	if(document.getElementById("amnt") != null){
		temp = document.getElementById("amnt").value;
		type = "Amnt";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	if(document.getElementById("applicantSex") != null){
		var applicantSex = document.getElementById("applicantSex").value;
		var recognizeeSex1 = document.getElementById("recognizeeSex1").value;
		if("本人"==s){
			if(applicantSex=="0"){
				temp = "1";
			}else{
				temp = "2";
			}
		}else{
			if(recognizeeSex1=="0"){
				temp = "1";
			}else{
				temp = "2";
			}
		}
		type = "Sex";
		var str = getFfrelas(temp,type);
		if(str == "ERROR"){
			return false;
		}
	}
	return true;
}
function loadRelation(){
	 var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
	 var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("本人"==s){
		 document.getElementById("relationFlag1").value="true";
	 }else{
		 document.getElementById("relationFlag1").value="";
	 }
	 /* zhangjinquan 11180 2012-10-11修改对保险期限的处理，以保证暂存后能够正确带出曾经保存的数据 */
	 if(("true"==protectionPeriodFlag) && ("" == "${(information.protectionPeriod)!}")){
			 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 var d = new Date();
		 d.setDate(d.getDate()+1);
		 var ndate = d.getFullYear()+"-"+add_zero(d.getMonth()+1)+"-"+add_zero(d.getDate());
		 document.getElementById("effective").value = ndate;
		 var temp = addDate(protectionPeriodTy,protectionPeriodLast, ndate);
		 document.getElementById("fail").value = temp;
		 document.getElementById("fail_").value = temp;
	 }
	  // 证件类型
	 var applicantTypeId = document.getElementById('applicantTypeId');
	 var applicantTypeId_index = applicantTypeId.selectedIndex;          
	 var applicantTypeId_value = applicantTypeId.options[applicantTypeId_index].text;     
	 if(applicantTypeId_value.indexOf('身份证') == -1){
		 document.getElementById("applicantId").className = "required";
	 }
	 
	 // 与投保人关系
	 var relation1 = document.getElementById('relation1');
	 var relation1_index = relation1.selectedIndex;          
	 var relation1_value = relation1.options[relation1_index].text; 
	 if(relation1_value.indexOf('本人') == -1){
	 	 addValidation();
	 	 document.getElementById("insurancedOne").style.display="block";
	 } else {
	 	 cancelVerification();
		 document.getElementById("insurancedOne").style.display="none";
	 }
	 loadDutyAndAFC();
}
 
function changeInformation(objForm,aa){
	if(aa=="insurancedOne"){
		var objSelect = objForm.relation1;
		var s = objSelect.options[objSelect.selectedIndex].text;
	}
   if(s=="本人"){
       	   cancelVerification();
	       /* zhangjinquan 11180 bug963 修改与被保人关系时是本人，则拷贝相关信息 2012-10-12 start */
	       document.getElementById("recognizeeName1").value = document.getElementById("applicantName").value;
	       document.getElementById("recognizeeTypeId1").options.selectedIndex = document.getElementById("applicantTypeId").options.selectedIndex;
	       document.getElementById("recognizeeId1").value = document.getElementById("applicantId").value;
	       document.getElementById("recognizeeSex1").options.selectedIndex = document.getElementById("applicantSex").options.selectedIndex;
	       document.getElementById("recognizeeBirthday1").value = document.getElementById("applicantBirthday").value;
	       document.getElementById("recognizeeMobile1").value=document.getElementById("applicantMobile").value;
	       document.getElementById("recognizeeMail1").value =document.getElementById("applicantMail").value;
	       /* zhangjinquan 11180 bug963 修改与被保人关系时是本人，则拷贝相关信息 2012-10-12 end   */
        
       document.getElementById(aa).style.display="none";
   }else{
        	addValidation();
	        /* zhangjinquan 11180 bug963 修改与被保人关系时不是本人，则清空相关信息 2012-10-12 start */
	        document.getElementById("recognizeeName1").value = "";
	        document.getElementById("recognizeeTypeId1").options.selectedIndex = 0;
	        document.getElementById("recognizeeId1").value = "";
	        document.getElementById("recognizeeSex1").options.selectedIndex = 0;
	        document.getElementById("recognizeeBirthday1").value = "";
	        document.getElementById("recognizeeMobile1").value="";
	        document.getElementById("recognizeeMail1").value ="";
	        /* zhangjinquan 11180 bug963 修改与被保人关系时不是本人，则清空相关信息 2012-10-12 end   */
        
        document.getElementById(aa).style.display="block";
   }
   validateBirthday();
   dateTrigger();
}
function addValidation(){
	document.getElementById("recognizeeName1").className = "required";
    if(document.getElementById("insuredAreaA2")!=null){
    	document.getElementById("insuredAreaA2").className =  "selectTest {selectTest:false, messages: {selectTest: '请选择地区'}}";
    }
    document.getElementById("recognizeeMobile1").className = "required mobile";
    var recognizeeTypeId1 = document.getElementById('recognizeeTypeId1');
	var recognizeeTypeId_index = recognizeeTypeId1.selectedIndex;          
	var recognizeeTypeId_value = recognizeeTypeId1.options[recognizeeTypeId_index].text;     
	if(recognizeeTypeId_value.indexOf("身份证") == -1){
		document.getElementById("recognizeeId1").className = "required";
	}else{
		document.getElementById("recognizeeId1").className = "required isIdCardNo";
	}
    document.getElementById("recognizeeBirthday1").className = "required";
    document.getElementById("recognizeeMail1").className ="required email";
    if(document.getElementById("recognizeeAddress1")!=null){
    	document.getElementById("recognizeeAddress1").className ="required adress";
    }
    if(document.getElementById("recognizeeZipCode1")!=null){
    	document.getElementById("recognizeeZipCode1").className ="required zipCode";
    }
    document.getElementById("relationFlag1").value="";
}
function cancelVerification(){
	remoteAttributeNew(document.getElementById("recognizeeName1"), "class");
    if(document.getElementById("insuredAreaA2")!=null){
    	remoteAttributeNew(document.getElementById("insuredAreaA2"), "class");
    }
    remoteAttributeNew(document.getElementById("recognizeeMobile1"), "class");
    remoteAttributeNew(document.getElementById("recognizeeId1"), "class");
    remoteAttributeNew(document.getElementById("recognizeeBirthday1"), "class");
    remoteAttributeNew(document.getElementById("recognizeeMail1"), "class");
    if(document.getElementById("recognizeeAddress1")!=null){
    	remoteAttributeNew(document.getElementById("recognizeeAddress1"), "class");
    }
    if(document.getElementById("recognizeeZipCode1")!=null){
    	remoteAttributeNew(document.getElementById("recognizeeZipCode1"), "class");
    }
    document.getElementById("relationFlag1").value="true";
}

</script>
</head>
<body onLoad="loadRelation();">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">



<div class="line_log">
<div class="sdong"></div>

</div>
 
    <#if (status =="update")!>
 	<form id="orderInfoForm" action="order!bbxinfoUpdate.action?" method="post" enctype ="multipart/form-data" onsubmit="return insuredAndDuty();">
 	</#if>
 	<#if (status =="")!>
 	<form id="orderInfoForm" action="order!bbxinfosave.action?" method="post" enctype ="multipart/form-data" onsubmit="return insuredAndDuty();">
   </#if>
   
   <div class="line_a">
   <div class="pri">
   ${productName}&nbsp;&nbsp;&nbsp;
   <span>【${insuranceCompany}】</span>  
   &nbsp;&nbsp;&nbsp;
   </div>
</div>

    
    <div>&nbsp;</div>
    <div class="line_a">
	 <div class="pri">
	 <center>
		 <span>
		 	保费:
		</span>
		 <font  size="4" color="red">
		 ￥	<span id = "priceTotle_"><#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if></span>
		 </font>
	 </center>
	 </div>
</div>
       <div class="line_a">
            <div  class="line_at">保险期限<img width="18" height="16" title="是指保险合同约定承担保险责任的一段时间。您可以根据实际需要选择起保时间和终止时间。" src="${base}/template/shop/images/wen_a.png"/></div>
            <div class="form">
            	 <#if (planList.size()>0)!>
                    <p>
                        <span>投保计划</span> 
                        <select name="plan"  id="plan" onchange="getFfrelas(this.value,'Plan');">
								<#list planList as list>
									<option value="${list.factorValue}" <#if (list.factorValue == plan)!>selected</#if>>
										${list.factorDisplayValue}
									</option>
								</#list>
						 </select>
                    </p>
                 </#if>
                 
            
                 <#if (gradeList.size()>0)!>
                    <p>
                        <span>产品级别</span> 
                        <select name="grade"  id="grade" onchange="getFfrelas(this.value,'Grade');changePrem();">
							<#list gradeList as list>
								<option value="${list.factorValue}" <#if (list.factorValue == grade)!>selected</#if>>
									${list.factorDisplayValue}
								</option>
							</#list>
						</select>
                    </p>
                 </#if>
                 <#if (feeYearList.size()>0)!>
					<p>
						<span>缴费年期</span>
						<select name="feeYear"  id="feeYear" onchange="getFfrelas(this.value,'FeeYear');changePrem();">
							<#list feeYearList as list>
								<option value="${list.factorValue}" <#if (list.factorValue == feeYear)!>selected</#if>>
									${list.factorDisplayValue}
								</option>
							</#list>
						</select>
					</p>
				  </#if>
				  <#if (appLevelList.size()>0)!>
				      <p>
				         <span>缴费方式</span>
				         <select name="appLevel"  id="appLevel" onchange="getFfrelas(this.value,'AppLevel');changePrem();">
								<#list appLevelList as list>
									<option value="${list.factorValue}" <#if (list.factorValue == appLevel)!>selected</#if>>
										${list.factorDisplayValue}
									</option>
								</#list>
						 </select>
				      </p>
				  </#if>
				  <#if (amntList.size()>0)!>
			      <p>
			         <span>保额</span>
			         <select name="amnt"  id="amnt" onchange="getFfrelas(this.value,'Amnt');changePrem();">
							<#list amntList as list>
								<option value="${list.factorValue}" <#if (list.factorValue == amnt)!>selected</#if>>
									${list.factorDisplayValue}
								</option>
							</#list>
					 </select>
				  </p>
			      </#if>
				  <#if (appTypeList.size()>0)!>
				      <p>
				          <span>投保类别</span>
				          <select name="appType"  id="appType" onchange="getFfrelas(this.value,'AppType');changePrem();">
								<#list appTypeList as list>
									<option value="${list.factorValue}" <#if (list.factorValue == appType)!>selected</#if>>
										${list.factorDisplayValue}
									</option>
								</#list>
						  </select>
				      </p>
				  </#if>
				  
				  <#if (periodList.size()>0)!>
				      <p>
				          <span>保险期限</span>
							 <#if (periodList?size>1)>
					        <select name="period"  id="period" onchange="periodChange();getFfrelas(this.value,'Period');changePrem();">
									<#list periodList as list>
										<!-- zhangjinquan 11180 bug880 通过字符串包含判断是否为选中的保险期限 2012-10-11 -->
										<#if (information.protectionPeriod)??>
											<#if (list.factorValue)?contains("-")>
												<#assign subPeriod="-"+information.protectionPeriod+(information.protectionPeriodTy?upper_case) >
												<option value="${list.factorValue}" <#if (list.factorValue?contains(subPeriod)) >selected="selected"</#if> >
											<#else>
												<#assign subPeriod=information.protectionPeriod+(information.protectionPeriodTy?upper_case) >
												<option value="${list.factorValue}" <#if (list.factorValue?index_of(subPeriod)==0) >selected="selected"</#if> >
											</#if>
										<#else>
										<option value="${list.factorValue}"  <#if (list.factorValue == period)!> selected</#if> >
										</#if>
											${list.factorDisplayValue}
										</option>
									</#list>
							  </select>
						   <#else>
						   		<#list periodList as list>
						   		        <input value="${list.factorValue}" name="period" id="period" type="hidden">
										<span>${list.factorDisplayValue}</span>
								</#list>
						   </#if>
				      </p>
				  </#if>
				 <p>
                    <span>保单起保日期:</span> 
                    <input id="effective" name="effective" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'});effchange();"   value="${(information.effective)!}"  />
                 </p>
                   <#if (periodList.size()>0)!>
	                 <p>
	                    <span>保单终止日期:</span> 
	                    <input id="fail_" name="fail_" disabled="disabled" value="${(information.fail)!}" />
	                    <input id="fail" name="fail" type="hidden"   onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'effective\')}'})" onchange = "failchange()" value="${(information.fail)!}" />
	                </p>
                 <#else>
                     <p>
	                    <span>保单终止日期:</span> 
	                    <input id="fail_" name="fail_" type="hidden"   value="${(information.fail)!}" />
	                    <input id="fail" name="fail"   onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'effective\')}'})" onchange = "failchange()" value="${(information.fail)!}" />
	                </p>
                 </#if>
                 
            </div>
       </div>
       
       
       <div class="line_a">
       <div  class="line_at">投保人信息<img width="18" height="16"  title="投保人,是指与保险公司签订保险合同、缴纳保费的人。投保人同时也是保险合同的所有人。投保人信息非常重要，因此请按照有效身份证件记载的信息准确填写。" src="${base}/template/shop/images/wen_a.png"/></div>
       <div class="form">
       <input type="hidden" id="price"  name="price" value="<#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if>" />
	    
	    <input type ="hidden" id="productName" name="productName" value="${productName}">
	    <input type ="hidden" id="productId" name="productId" value="${productId}">
	    <input type ="hidden" name="insurType" value="${insurType}">
	    <input type ="hidden" id="orderStatus" name="orderStatus" value="${orderstatus}" />
	    <input type ="hidden"  id ="status" name="status" value="${status}" />
	    <input type ="hidden"  id ="orderId" name="orderId" value="${orderId}" />
	    <input type ="hidden"  id ="dutyReq" name="dutyReq" value="${dutyReq}" />
	    <input type ="hidden"  id ="dutyDisReq" name="dutyDisReq" value="${dutyDisReq}" />
	    <input type ="hidden"  id ="dutyPremReq" name="dutyPremReq" value="${dutyPremReq}" />
	    <input type ="hidden"  id ="insureReq" name="insureReq" value="${insureReq}" />
	    <input type ="hidden" id="insuranceCompany" name="insuranceCompany" value="${insuranceCompany}">
	    <input type ="hidden" id="insuranceCompanySn" name="insuranceCompanySn" value="${insuranceCompanySn}">
	    <input type ="hidden" id="outRiskCode" name="outRiskCode" value="${outRiskCode}">
	    <input type ="hidden" id="protectionPeriodTy" name="protectionPeriodTy" value="${protectionPeriodTy}">
	    <input type ="hidden" id="protectionPeriodLast" name="protectionPeriodLast" value="${protectionPeriodLast}">
	    <input type ="hidden" id="protectionPeriodFlag" name="protectionPeriodFlag" value="${protectionPeriodFlag}">
	    <input type="hidden" id="relation1name" name="informationInsureds1.recognizeeAppntRelationName" value="${(informationInsureds1.recognizeeAppntRelationName)!}"/>
	    <input type="hidden" id="applicanTypeName" name="informationAppnt.applicantIdentityTypeName" value="${(informationAppnt.applicantIdentityTypeName)!}"/>
	    <input type="hidden" id="recognizeeIdentityTypeName" name="informationInsureds1.recognizeeIdentityTypeName" value="${(informationInsureds1.recognizeeIdentityTypeName)!}"/>
	    <input type="hidden" id="recognizeeSexName" name="informationInsureds1.recognizeeSexName" value="${(informationInsureds1.recognizeeSexName)!}"/>
	    <input type="hidden" id="applicantSexName" name="informationAppnt.applicantSexName" value="${(informationAppnt.applicantSexName)!}"/>
	    <input type ="hidden"  id ="insurTypeChild" name="insurTypeChild" value="${insurTypeChild}" />
	    <input type ="hidden"  id ="textAgeFlag" name="textAgeFlag" value="${textAgeFlag}" />
	    <input type ="hidden"  id ="amntNum" name="amntNum" value="${amntNum}" />
	    <input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
       <p>
               <span>姓名:</span> 
               <input type="text" id="applicantName" name="informationAppnt.applicantName" value="${(informationAppnt.applicantName)!}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class="required"  maxlength=50/>
            <label class="requireField">*</label>
           </p>
           <p>
           <span></span>
           <font color="#808080">填写的姓名一定要与有效身份证件保持一致，以保证日后理赔顺利。</font>
           </p>
            <p>
               <span>证件类型:</span>
               <select id="applicantTypeId" name="informationAppnt.applicantIdentityType" style="width:125px;" onchange="changeVerification('applicantTypeId','applicantId');">
                   <#list certificateCode as list>
                       <option value="${list.get('codeValue')}"  <#if (informationAppnt.applicantIdentityType==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
                   </#list>
				</select>
				<label class="requireField">*</label>
            </p>
            <p>
               <span>证件号码:</span>   
               <input id="applicantId" name="informationAppnt.applicantIdentityId" maxlength=64 type="text" value="${(informationAppnt.applicantIdentityId)!}" name="informationAppnt.applicantIdentityId" onblur="idcheck('applicantTypeId','applicantId','applicantBirthday','applicantSex');"  class="required isIdCardNo"/>
           	<label class="requireField">*</label>
            </p>
            <p>
            <span></span>
            <font color="#808080">证件类型和号码请您仔细选择或填写并核对无误，以免影响日后理赔。</font>
            </p>
                <p>
	                 <span>性别:</span>
		                <select id="applicantSex" name="informationAppnt.applicantSex" style="width:125px;"  onchange="getFfrelas(this.value,'Sex');changePrem();">
		                  <#list sexs as list>
		                      <option value="${list.get('codeValue')}" <#if (informationAppnt.applicantSex==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
		                  </#list>
		                </select>
	                 <label class="requireField">*</label>
                </p>
                <p>
                   <span>出生日期:</span> 
                   <input id="applicantBirthday" name="informationAppnt.applicantBirthday" type="text" onclick="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})"  value="${(informationAppnt.applicantBirthday)!}"  class="required applicantBirthday" onchange="validateBirthday();getFfrelas(this.value,'TextAge');failchangeByBirthday();dateTrigger();"/>
                	<label class="requireField">*</label>
                	<font id="applicantBirthdayID" color="red"></font> 
                </p>
            <p>
               <span>手机号码:</span>
               <input type="text" id="applicantMobile" name="informationAppnt.applicantMobile" value="${(informationAppnt.applicantMobile)!}"  class="required mobile" />
           	<label class="requireField">*</label>
			</p>
			<p>
            <span></span>
            <font color="#808080">请填写自己真实的号码，方便开心保为您提供优质服务。</font>
            </p>
            <p>
               <span>电子邮箱:</span>
               <input type="text" id="applicantMail" name="informationAppnt.applicantMail" value="${(informationAppnt.applicantMail)!}" class="required email"/>
               <label class="requireField">*</label>
           </p>
           <p>
           <span></span>
           <font color="#808080">提醒您！我们仅向该邮箱发送电子保单。并且在办理保单业务时，开心保网也只接收您通过该邮箱提交的材料。</font>
           </p>
           <p>
           <span>所在地区:</span>
           <select id="applicantArea1" name="informationAppnt.applicantArea1" onchange="getChildrenArea(this.value,'applicantArea2','-1');" style="width:125px;">
                   <option value="-1">--请选择--</option>
                   <#list areas as list>
                   		<option value="${list.get('id')}" <#if (informationAppnt.applicantArea1==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
        			</#list>
              </select>
              <select id="applicantArea2" name="informationAppnt.applicantArea2" class="selectTest {selectTest:false, messages: {selectTest: '请选择地区'}}"  style="width:125px;">
                  <option value="-1">--请选择--</option> 
                  <#list appntAreas2 as list>
	                  <option value="${list.get('id')}" <#if (informationAppnt.applicantArea2==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
       			  </#list>
              </select>
			      <label class="requireField">*</label>
        </p>
           <p>
	           <span>联系地址:</span> 
	           <input type="text" id="applicantAddress" name="informationAppnt.applicantAddress" maxlength=200 class="required adress" value="${(informationAppnt.applicantAddress)!}"/>
	           <label class="requireField">*</label>
	        </p>
	        <p>
		        <span>邮编:</span> 
		        <input type="text" id="applicantZipCode" name="informationAppnt.applicantZipCode" value="${(informationAppnt.applicantZipCode)!}" class="required zipCode"/>
		        <label class="requireField">*</label>
	     </p>
      </div>
  </div>
  
  <div class="line_a">
     <div  class="line_at">被保人信息<img width="18" height="16" title="被保险人，就是指保险合同的保障对象。通常，被保险人或其继承人（被保险人身故且未指定受益人时）会享有保险金的申请权。 " src="${base}/template/shop/images/wen_a.png"/></div>
     <div class="form">
       <p>
           <span>与投保人关系:</span> 
           <select name="informationInsureds1.recognizeeAppntRelation"  id="relation1" class = "selectTest {selectTest:false, messages: {selectTest: '请选择投保人关系'}}" onchange="changeInformation(this.form,'insurancedOne');" style="width:125px;">
					<#list listRelation as list>
						<option value="${list.get('codeValue')}" <#if (informationInsureds1.recognizeeAppntRelation==list.get('codeValue'))>selected="selected"</#if>>
							${list.get("codeName")}
						</option>
					</#list>
			 </select>
			 <input type="hidden" id="relationFlag1" name="relationFlag1" value=""/>
			 <label class="requireField">*</label>
        </p>
        <div id="insurancedOne"  style="display:none;">
             <p>
                <span>姓名:</span> 
                <input type="text" id="recognizeeName1" name="informationInsureds1.recognizeeName" value="${(informationInsureds1.recognizeeName)!}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')" maxlength=50/>
            	 <label class="requireField">*</label>
             </p>
           
            <p id="testid111">
               <span>证件类型:</span> 
               <select id="recognizeeTypeId1" name="informationInsureds1.recognizeeIdentityType" style="width:125px;" onchange="changeVerification('recognizeeTypeId1','recognizeeId1');">
					<#list certificateCode as list>
                    <option value="${list.get('codeValue')}" <#if (informationInsureds1.recognizeeIdentityType==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
                </#list>
					<label class="requireField">*</label>
				</select>
				<label class="requireField">*</label>	
            </p>
            <p>
                <span>证件号码:</span> 
                <input id="recognizeeId1" type="text" name="informationInsureds1.recognizeeIdentityId" value="${(informationInsureds1.recognizeeIdentityId)!}"  onblur="idcheck('recognizeeTypeId1','recognizeeId1','recognizeeBirthday1','recognizeeSex1');"/>
            	<label class="requireField">*</label>
            </p>
	             <p>
	             	<span>性别:</span> 
		             <select id="recognizeeSex1" name="informationInsureds1.recognizeeSex" style="width:125px;" onchange="getFfrelas(this.value,'Sex');changePrem();">
		             	<#list sexs as list>
		             		<option value="${list.get('codeValue')}" <#if (informationInsureds1.recognizeeSex==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
		             		</#list>
		             </select>
		             <label class="requireField">*</label>
	             </p>
	             <p>
	                 <span>出生日期:</span> 
	                 <input id="recognizeeBirthday1" name="informationInsureds1.recognizeeBirthday"  type="text" onclick="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})"  value="${informationInsureds1.recognizeeBirthday}" onchange="validateBirthday();getFfrelas(this.value,'TextAge');failchangeByBirthday();dateTrigger();"/>
	            	 <label class="requireField">*</label>
	            	 <font id="recognizeeBirthdayID" color="red"></font> 
	             </p>
            <p>
                <span>手机号码:</span> 
                <input type="text" id="recognizeeMobile1" name="informationInsureds1.recognizeeMobile" value="${(informationInsureds1.recognizeeMobile)!}" />
                <label class="requireField">*</label>
            </p>
            <p>
                <span>电子邮箱:</span> 
                <input type="text" id="recognizeeMail1" name="informationInsureds1.recognizeeMail" value="${(informationInsureds1.recognizeeMail)!}" />
            	<label class="requireField">*</label>
            </p>
            <p>
            <span>所在地区:</span> 
              <select id="insuredAreaA1" name="informationInsureds1.recognizeeArea1" onchange="getChildrenArea(this.value,'insuredAreaA2','-1');" style="width:125px;">
                    <option value="-1">--请选择--</option>
                    <#list areas as list>
                    	<option value="${list.get('id')}" <#if (informationInsureds1.recognizeeArea1==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
                    </#list>
               </select>
               <select id="insuredAreaA2" name="informationInsureds1.recognizeeArea2"  style="width:125px;">
                   <option value="-1">--请选择--</option>
                   <#list regAreas2 as list>
                       <option value="${list.get('id')}" <#if (informationInsureds1.recognizeeArea2==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
                   </#list>
               </select>
               <label class="requireField">*</label>
         </p>
	         <p>
	            <span>联系地址:</span> 
	            <input type="text" id="recognizeeAddress1" maxlength=200 name="informationInsureds1.recognizeeAddress" value="${(informationInsureds1.recognizeeAddress)!}"/>
	            <label class="requireField">*</label>
	        </p>
	        <p>
	            <span>邮编:</span> 
	            <input type="text" id="recognizeeZipCode1" name="informationInsureds1.recognizeeZipCode" value="${(informationInsureds1.recognizeeZipCode)!}"/>
	            <label class="requireField">*</label>
	        </p>
	        
        </div>
        <p>
			 <span>身高:</span> 
			 <input type="text" id="height1" name="informationInsureds1.height" value="${(informationInsureds1.height)!}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength=3 class="required" />(厘米)
			 <label class="requireField">*</label>
		</p>
		<p>
			 <span>体重:</span> 
			 <input type="text" id="weight1" name="informationInsureds1.weight" value="${(informationInsureds1.weight)!}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" maxlength=7 class="required" />(公斤 )
			 <label class="requireField">*</label>
		</p>
        <p>
        <span>职业:</span>
             <select name="informationInsureds1.occupation1" id="s1" onchange="getChildrenOPT(this.value,'s2');" style="width:125px;_width:180px;" >
             	<option value="-1">--请选择--</option>
             	<#list opts as list>
             		<option value="${list.get('id')}" <#if (informationInsureds1.occupation1==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>	
        		</#list>
             </select> 
             <select name="informationInsureds1.occupation2" id="s2" onchange="getChildrenOPT(this.value,'s3');" style="width:125px;_width:200px;" >
             		<option value="-1">--请选择--</option>
             	<#list opts2 as list>
             		<option value="${list.get('id')}" <#if (informationInsureds1.occupation2==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>	
        		</#list>
             </select>
             <select name="informationInsureds1.occupation3" id="s3" style="width:125px;_width:200px;" class="selectTest {selectTest:false, messages: {selectTest: '请选择职业'}}">
             		<option value="-1">--请选择--</option>
             	<#list opts3 as list>
             		<option value="${list.get('id')}" <#if (informationInsureds1.occupation3==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>	
        		</#list>
             </select>
             <label class="requireField">*</label>
             <font id="occupID" color="red"></font>
    </p>
      </div>
   </div>

 <div class="line_a">
 <div  class="line_at">受益人<img width="18" height="16" title="受益人是指人身保险合同中由投保人和被保险人共同指定的享有保险金请求权利的人。由于网上投保时，受益人指定无法根据法律规定得到被保险人的书面确认而合法有效，因此通常采取默认被保险人的法定继承人作为保单的身故受益人。法定继承人是指根据法律规定直接继承被保险人遗产的配偶、父母、子女等人。" src="${base}/template/shop/images/wen_a.png"/></div>
 <div class="syr">
法定继承人
     </div>
     </div>
 <div class="clear"></div>
 
 
 <div class="line_a">
     <div class="line_at">保障内容信息</div>
     <div>
     <table id="mytable" cellspacing="1" class="datagrid3" style="TABLE-LAYOUT: fixed;word-wrap: break-word; word-break: break-all;">
     <tr> 
     <th  width="25%">保障项目</th> 
     <th  width="25%">保额</th> 
     <th>保障内容</th> 
     </tr> 
 		<caption> </caption> 
			<#assign index = 1 />
				<#list dutyFactor as list>
					<tr  style="background-color: #EFEFEF; " >
						<td style="word-break:break-all" width="260">
							<span>${list.dudtyFactorName} </span>
						</td>
						<td style="padding-left:100px" align="left">
						    <#if (list.fdAmntPremList.size()>0)!>
							    <select id="Fduty-${list.dutyFactorID}" onchange="changeAmnt(this.value,'${list.dutyFactorID}');changePrem();">
								    <#list list.fdAmntPremList as list1>
									    <option value="${list1.backUp1}">
									    	<span>${list1.amnt}</span>
									    </option>
								    </#list>
							    </select>
							    <input type="hidden" id="dutyFactorID${index}" name="dutyFactorID${index}" value="${list.dutyFactorID}">
							    <input type="hidden" id="amnt${list.dutyFactorID}" name="amnt${list.dutyFactorID}" value="${list.fdAmntPremList.get(0).backUp1}">
							    <input type="hidden" id="amntDis${list.dutyFactorID}" name="amntDis${list.dutyFactorID}" value="${list.fdAmntPremList.get(0).amnt}">
							    <input type="hidden" id="prem-${list.dutyCode}" name="prem-${list.dutyCode}" value="${list.prem}">
						    </#if>
						</td>
						<td style="word-break:break-all" width="520">
						${list.define}
					</td>
						<input type="hidden" id="dutyCode${index}" name="dutyCode${index}" value="${list.dutyCode}" />
					</tr>
			<#assign index=index+1 />
				</#list>
		</table>
			</div>
			<br>
			<input type="hidden" id="dutyFactorSize" name="dutyFactorSize" value="${dutyFactor.size()}" />
 </div>
 <div class="line_a">
	 <div class="pri">
	 <center>
		 <span>
		 	保费:
		</span>
		 <font  size="4" color="red">
		 ￥	<span id = "priceTotle"><#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if></span>
		 </font>
	 </center>
	 </div>
</div>
 <div class="syr2">
 <#if (loginFlag =="true")!>
 <input  id="comfirmToPay" class="butM dev_submitButton" onclick="temptorysave();" type="button" value="   暂存   " >
</#if>
 <input type="submit" value="  下一步  "  class="butM dev_submitButton" style="height=20px;width=100px;" >
 &nbsp; &nbsp; &nbsp; 

 </div>
 <div class="clear"></div>
   </form>
<script type="text/javascript">
function idcheck(idtype,idCode,birthdayId,sexId){
    str=document.getElementById(idCode).value;
    var idType = document.getElementById(idtype);
	var idType_index = idType.selectedIndex;          
	var idType_value = idType.options[idType_index].text;     
	if(idType_value.indexOf("身份证") != -1){
    if(str.length==15 || str.length==18){
    	if(str.length==18){
    		var sex;
    		if(str.substring(16,17)%2==0){   
    			sex = "1";   
            }else{   
            	sex = "0";   
            }   
    		var year = str.substring(6, 10);      
            var month = str.substring(10, 12);      
    		var day = str.substring(12, 14);
    		document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
    		document.getElementById(sexId).value = sex;
    	}else{
    		var sex;
    		if(str.substring(14,15)%2==0){   
    			sex = "1";   
            }else{   
            	sex = "0";   
            }   
    		var year = "19"+str.substring(6, 8);      
            var month = str.substring(8, 10);      
    		var day = str.substring(10, 12);      
    		document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
    		document.getElementById(sexId).value = sex;
    		}
    	}
		validateBirthday();
		dateTrigger();
		var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
		var birthday = "";
		if("本人"==s){
			birthday = document.getElementById("applicantBirthday").value;
		}else{
			birthday = document.getElementById("recognizeeBirthday1").value;
		}
		if(birthday !=null && birthday != ""){
			getFfrelas(birthday,'TextAge');
			changePrem();
		}
    }   
}

</script>
 </div>
 <script type="text/javascript" src="${base}/template/common/js/json2.js"></script>
 <script type="text/javascript" src="${base}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
 <script type="text/javascript" src="${base}/wwwroot/kxb/js/productDutyComplicated.js"></script>
 <script type="text/javascript">
	pubGetChildrenArea("${informationAppnt.applicantArea1}", "${informationAppnt.applicantArea2}", "${informationInsureds1.recognizeeArea1}", "${informationInsureds1.recognizeeArea2}");
</script>
</body>
</html>