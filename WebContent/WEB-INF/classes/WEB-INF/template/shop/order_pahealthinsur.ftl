<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<!--<%@ taglib prefix="s" uri="/struts-tags" %>-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>填写投保信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
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
	jQuery(document).ready(function(){ 
		jQuery("#orderInfoForm").validate();
		jQuery('#plan').change(function(){
		var value = jQuery('#plan').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		}) 
		jQuery('#appType').change(function(){ 
		var value = jQuery('#appType').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#feeYear').change(function(){ 
		var value = jQuery('#feeYear').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#grade').change(function(){ 
		var value = jQuery('#grade').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#occup').change(function(){ 
		var value = jQuery('#occup').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#period').change(function(){ 
		var value = jQuery('#period').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#sex').change(function(){ 
		var value = jQuery('#sex').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#amnt').change(function(){ 
		var value = jQuery('#amnt').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
	})
function insuredElements(){
	 if(document.getElementById("plan")){
		var plan=document.getElementById("plan").value;
	 }
	 if(document.getElementById("appType")){
		var appType=document.getElementById("appType").value;
	 }
	 if(document.getElementById("feeYear")){
	 var feeYear=document.getElementById("feeYear").value;
	 }
	 if(document.getElementById("grade")){ 
	 var grade=document.getElementById("grade").value;
	 }
	 if(document.getElementById("recognizeeBirthday")){ 
	 var BirthDay=document.getElementById("recognizeeBirthday").value;
	 }
	 if(document.getElementById("period")){ 
	 var period=document.getElementById("period").value;
	 }
	 if(document.getElementById("recognizeeSex")){ 
	 var sex=document.getElementById("recognizeeSex").value;
	 }
//	 var textAge=document.getElementById("textAge").value;
//	 var amnt=document.getElementById("amnt").value;
//	 var occup=document.getElementById("occup").value;

	 var o={"Plan":encodeURIComponent(plan),"AppType":encodeURIComponent(appType),"FeeYear":feeYear,"Grade":encodeURIComponent(grade),"BirthDay":BirthDay,"Period":encodeURIComponent(period),"Sex":encodeURIComponent(sex)};
	 var last=JSON.stringify(o);
	 return last;
}
	function insuredElementsd(){
		 if(document.getElementById("plan")){
			var plan=document.getElementById("plan").value;
		 }
		 if(document.getElementById("appType")){
			var appType=document.getElementById("appType").value;
		 }
		 if(document.getElementById("feeYear")){
		 var feeYear=document.getElementById("feeYear").value;
		 }
		 if(document.getElementById("grade")){ 
		 var grade=document.getElementById("grade").value;
		 }
		 if(document.getElementById("recognizeeBirthday")){ 
		 var BirthDay=document.getElementById("recognizeeBirthday").value;
		 }
		 if(document.getElementById("period")){ 
		 var period=document.getElementById("period").value;
		 }
		 if(document.getElementById("recognizeeSex")){ 
		 var sex=document.getElementById("recognizeeSex").value;
		 }
//		 var textAge=document.getElementById("textAge").value;
//		 var amnt=document.getElementById("amnt").value;
//		 var occup=document.getElementById("occup").value;

		 var o={"Plan":plan,"AppType":appType,"FeeYear":feeYear,"Grade":grade,"BirthDay":BirthDay,"Period":period,"Sex":sex};
		 var last=JSON.stringify(o);
		 return last;
	}

function dutyElements(){
	 var dutyCodeSize=document.getElementById("dutyFactorSize").value;
	 var tempDuty = parseInt(dutyCodeSize);
	 tempDuty=tempDuty+1;
	 var amnt="";
	 var dutyCode="";
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsonlast = "";
	 var nvalue="nvalue";
	 
	 if(tempDuty>0){//有责任信息
		 for(i=1;i<tempDuty;i++){
		 	amnt = "amnt"+i;
		 	dutyCode = "dutyCode"+i;
		 	var dutyCodeTemp=document.getElementById(dutyCode).value;//责任编码
		 	if(document.getElementById(amnt)){
		 		var amntTemp=document.getElementById(amnt).value;//责任编码
		 		if(i<tempDuty-1){
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+encodeURIComponent(amntTemp)+'","';
			 	}else{
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+encodeURIComponent(amntTemp);
			 	}
		 	}else{
			 	if(i<tempDuty-1){
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+nvalue+'","';
			 	}else{
			 		jsonstar =jsonstar+encodeURIComponent(dutyCodeTemp)+jsoncen+nvalue;
			 	}
		 	}
		 }
	jsonlast = jsonstar+jsonend;
	 }
	return jsonlast;
}
function dutyElementsDis(){
	 var dutyCodeSize=document.getElementById("dutyFactorSize").value;
	 var tempDuty = parseInt(dutyCodeSize);
	 tempDuty=tempDuty+1;
	 var amnt="";
	 var dutyCode="";
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsonlastDis = "";
	 var nvalue="nvalue";
	 
	 if(tempDuty>0){//有责任信息
		 for(i=1;i<tempDuty;i++){
		 	amnt = "amntDis"+i;
		 	dutyCode = "dutyCode"+i;
		 	var dutyCodeTemp=document.getElementById(dutyCode).value;//责任编码
		 	if(document.getElementById(amnt)){
		 		var amntTemp=document.getElementById(amnt).value;//责任编码
		 		if(i<tempDuty-1){
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+amntTemp+'","';
			 	}else{
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+amntTemp;
			 	}
		 	}else{
			 	if(i<tempDuty-1){
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+nvalue+'","';
			 	}else{
			 		jsonstar =jsonstar+dutyCodeTemp+jsoncen+nvalue;
			 	}
		 	}
		 }
	jsonlastDis = jsonstar+jsonend;
	 }
	return jsonlastDis;
}

   function dateTrigger(){
   	var birthday = document.getElementById("recognizeeBirthday").value;
   	var insureJson = insuredElements();
	var dutyJson = dutyElements();
	var riskAppFactior = document.getElementById("riskAppFactior").value;
	var riskAppFactiorJson = JSON.stringify(riskAppFactior);
	jQuery.ajax({
		url: "${base}/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson+"&riskAppFactiorJson="+riskAppFactiorJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("totlePrem").innerHTML=data;
			}
		});
    }
    
    function st(){
    	var effective = document.getElementById("effective").value;
    	document.getElementById("start").innerHTML=effective+"起";
    }
    function en(){
    	var fail = document.getElementById("fail").value;
    	document.getElementById("end").innerHTML=fail+"止";
    }
    //save前得到投保要素和责任
    function insuredAndDuty(){
   	var insureJson = insuredElementsd();
	var dutyJson = dutyElements();
	var dutyJsonDis = dutyElementsDis();
	document.getElementById("insureReq").value=insureJson;
	document.getElementById("dutyReq").value=dutyJson;
	document.getElementById("dutyDisReq").value=dutyJsonDis;
    }
    
</script>

<script>
function temptorysave(){
document.getElementById("orderStatus").value="temptorysave";
insuredAndDuty();
document.getElementById("orderInfoForm").submit();
}
function saveOrder(){
	document.getElementById("orderInfoForm").validate({
		ignore: ".ignoreValidate",
		invalidHandler: function(form, validator) {
			jQuery.each(validator.invalid, function(key, value){
				jQuery.tip(value);
				return false;
			});
		},
		errorPlacement:function(error, element) {},
		submitHandler: function(form) {
			$orderInfoForm.find(":submit").attr("disabled", true);
			form.submit();
		}
		});
	document.getElementById("orderInfoForm").submit();
}

</script>
<script type="text/javascript">
function getChildrenOPT(id,flag){
   if(id!="-1"){
        jQuery.ajax({
            type:'get',
		    url: "${base}/shop/order!getChildernOPT.action?id="+id,
		    dataType: "json",
		    success: function(item) {
			   fillOPT(item,flag);
		    }
	    });
   }
}
function fillOPT(item,flag){
    var childrenOPT = document.getElementById(flag);
    childrenOPT.options.length=1
    for(var i=0;i<item.length;i++){
     	var oo=new Option(item[i].name,item[i].id);
     	childrenOPT.options[childrenOPT.options.length]=oo;
    }
}
function getChildrenArea(id,flag){
   if(id!="-1"){
        if(flag != ""){
	        jQuery.ajax({
	            type:'get',
			    url: "${base}/shop/order!getChildernArea.action?id="+id,
			    dataType: "json",
			    success: function(item) {
				   fillArea(item,flag);
			    }
		    });
        }
   }else{
      var childrenArea = document.getElementById(flag);
	  childrenArea.options.length=1;
   }
}
function fillArea(item,flag){
    var childrenArea = document.getElementById(flag);
    childrenArea.options.length=1;
    for(var i=0;i<item.length;i++){
     	var oo=new Option(item[i].name,item[i].id);
     	childrenArea.options[childrenArea.options.length]=oo;
    }
}

function add_zero(temp)
{
 if(temp<10) return "0"+temp;
 else return temp;
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
	 if(("true"==protectionPeriodFlag) && ("" == "${(information.protectionPeriod)!}"))
	 {
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 if("D" == protectionPeriodTy){
			 var effective = document.getElementById("effective");
			 var fail = document.getElementById("fail");
			 var d = new Date();
			 var years = d.getFullYear();  
			 var month = add_zero(d.getMonth()+1);
			 var days = add_zero(d.getDate()+1);
			 var ndate = years+"-"+month+"-"+days;
			 effective.value = ndate;
			 
			 fail.value = this.DateAdd('d',protectionPeriodLast,ndate);
		 }
	 }
}
function effchange(){
	var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("true"==protectionPeriodFlag){
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 if("D" == protectionPeriodTy){
			 var effective = document.getElementById("effective").value;
			 var temp = str2date(effective+'',protectionPeriodLast);
			 jQuery("#fail").attr("value",temp);
		 }
	 }
}

function failchange(){
	var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("true"==protectionPeriodFlag){
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 if("D" == protectionPeriodTy){
			 var fail = document.getElementById("fail").value;
			 protectionPeriodLast = "-"+protectionPeriodLast;
			 parseInt=parseInt(protectionPeriodLast);
			 var  temp = str2date(fail+'',protectionPeriodLast);
			 jQuery("#effective").attr("value",temp);
		 }
	 }
}
function str2date(str,n){  
	  var   dd, mm, yy;   
	  var   reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
	  if (arr = str.match(reg)) {
	    yy = Number(arr[1]);
	    mm = Number(arr[2])-1;
	    dd = Number(arr[3]);
	  } else {
	    var d = new Date();
	    yy = d.getUTCFullYear();
	    mm = ("00"+(d.getUTCMonth())).slice(-2);
	    dd = ("00"+d.getUTCDate()).slice(-2);
	  }
	return  date2str(yy, mm, dd,n);
	} 
	
	function date2str(yy, mm, dd,n) {
  var s, d, t, t2;
  t = Date.UTC(yy, mm, dd);
  t2 = n * 1000 * 3600 * 24;
  t+= t2;
  d = new Date(t);
  s = d.getUTCFullYear() + "-";
  s += ("00"+(d.getUTCMonth()+1)).slice(-2) + "-";
  s += ("00"+d.getUTCDate()).slice(-2);
  
  return s;
	}


function   DateAdd(strInterval,NumDay,dtDate)   {   //参数 增加类型，增加天数，被增加日期
	  var dtTmp = new Date(dtDate);   
	  if   (isNaN(dtTmp)) dtTmp = new Date();
	  switch (strInterval){
	   case   "s":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (1000   *   parseInt(NumDay))); 
	     break;  
	   case   "n":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (60000   *   parseInt(NumDay))); 
	     break;  
	   case   "h":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (3600000   *   parseInt(NumDay)));
	     break;
	   case   "d":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   (86400000   *   parseInt(NumDay)));
	     break;
	   case   "w":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   +   ((86400000   *   7)   *   parseInt(NumDay))); 
	     break;
	   case   "m":
	     dtTmp  =   new   Date(dtTmp.getFullYear(),   (dtTmp.getMonth())+parseInt(NumDay),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     break;   
	   case   "y":
	     //alert(dtTmp.getFullYear());
	     dtTmp  =   new   Date(dtTmp.getFullYear()+parseInt(NumDay),   dtTmp.getMonth(),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     //alert(dtTmp);
	     break;
	  }
	  var mStr=new String(dtTmp.getMonth()+1);
	  var dStr=new String(dtTmp.getDate());
	  if (mStr.length==1){
	   mStr="0"+mStr;
	  }
	  if (dStr.length==1){
	   dStr="0"+dStr;
	  }
	  return dtTmp.getFullYear()+"-"+mStr+"-"+dStr;
	}

function   DateDel(strInterval,NumDay,dtDate)   {   //参数 增加类型，增加天数，被增加日期
	  var   dtTmp   =   new   Date(dtDate);   
	  if   (isNaN(dtTmp))   dtTmp   =   new   Date();   
	  switch   (strInterval)   {   
	   case   "s":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (1000   *   parseInt(NumDay))); 
	     break;  
	   case   "n":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (60000   *   parseInt(NumDay))); 
	     break;  
	   case   "h":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (3600000   *   parseInt(NumDay)));
	     break;
	   case   "d":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   (86400000   *   parseInt(NumDay)));
	     break;
	   case   "w":
	     dtTmp  =   new   Date(Date.parse(dtTmp)   -   ((86400000   *   7)   *   parseInt(NumDay))); 
	     break;
	   case   "m":
	     dtTmp  =   new   Date(dtTmp.getFullYear(),   (dtTmp.getMonth())+parseInt(NumDay),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     break;   
	   case   "y":
	     //alert(dtTmp.getFullYear());
	     dtTmp  =   new   Date(dtTmp.getFullYear()+parseInt(NumDay),   dtTmp.getMonth(),   dtTmp.getDate(),   dtTmp.getHours(),   dtTmp.getMinutes(),   dtTmp.getSeconds());
	     //alert(dtTmp);
	     break;
	  }
	  var mStr=new String(dtTmp.getMonth()+1);
	  var dStr=new String(dtTmp.getDate());
	  if (mStr.length==1){
	   mStr="0"+mStr;
	  }
	  if (dStr.length==1){
	   dStr="0"+dStr;
	  }
	  return dtTmp.getFullYear()+"-"+mStr+"-"+dStr;
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
		remoteAttributeNew(document.getElementById("recognizeeId1"), "class");
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
function changeVerification(Idtype,changeId,changeDivId){
	var ss = document.getElementById(Idtype);
	document.getElementById(changeId).value="";
	if(Idtype=="applicantTypeId"){
		var s = ss.options[ss.selectedIndex].text;
		if(s=="身份证"){
			document.getElementById(changeDivId).style.display="none";
			document.getElementById(changeId).setAttribute("class", "required isIdCardNo");
		}else{
			document.getElementById(changeDivId).style.display="block";
			document.getElementById(changeId).removeAttributeNode(document.getElementById(changeId).getAttributeNode("class"));
		}
	}
	if(Idtype=="recognizeeTypeId1"){
		var s = ss.options[ss.selectedIndex].text;
		if(s=="身份证"){
			document.getElementById(changeDivId).style.display="none";
			document.getElementById(changeId).setAttribute("class", "required isIdCardNo");
		}else{
			document.getElementById(changeDivId).style.display="block";
			document.getElementById(changeId).setAttribute("class", "required");
		}
	}
}
function changeAmnt(amnt,amntDis){
	
	//var objSelect = objForm.relation2;
	//var s = objSelect.options[objSelect.selectedIndex].text;
	//document.getElementById(changeId).value = value;
}

</script>
</head>
<body onLoad="loadRelation()">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">



<div class="line_log">
<div class="sdong"></div>

</div>
 
    <#if (status =="update")!>
 	<form id="orderInfoForm" action="order!bbxinfoUpdate.action?" method="post" enctype ="multipart/form-data" onsubmit="insuredAndDuty();">
 	</#if>
 	<#if (status =="")!>
 	<form id="orderInfoForm" action="order!bbxinfosave.action?" method="post" enctype ="multipart/form-data" onsubmit="insuredAndDuty();">
   </#if>
   
    <div class="line_a">
	    <div class="pri">
	    ${productName}&nbsp;&nbsp;&nbsp;
	    <span>【${insuranceCompany}】</span>  
	    &nbsp;&nbsp;&nbsp;
	    <font   color="red">
	    ￥<#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if>
	    </font>
	    
	    </div>
    </div>
    
    <div>&nbsp;</div>
       <div class="line_a">
            <div  class="line_at">保险期限</div>
            <div class="form">
            	 <#if (planList.size()>0)!>
                    <p>
                        <span>投保计划</span> 
                        <select name="plan"  id="plan">
								<#list planList as list>
									<option value="${list.factorValue}">
										${list.factorDisplayValue}
									</option>
								</#list>
						 </select>
                    </p>
                 </#if>
                 
            
                 <#if (gradeList.size()>0)!>
                    <p>
                        <span>产品级别</span> 
                        <select name="grade"  id="grade">
							<#list gradeList as list>
								<option value="${list.factorValue}">
									${list.factorDisplayValue}
								</option>
							</#list>
						</select>
                    </p>
                 </#if>
                 <#if (feeYearList.size()>0)!>
					<p>
						<span>缴费年期</span>
						<select name="feeYear"  id="feeYear">
							<#list feeYearList as list>
								<option value="${list.factorValue}">
									${list.factorDisplayValue}
								</option>
							</#list>
						</select>
					</p>
				  </#if>
				  <#if (appLevelList.size()>0)!>
				      <p>
				         <span>缴费方式</span>
				         <select name="appLevel"  id="appLevel">
								<#list appLevelList as list>
									<option value="${list.factorValue}">
										${list.factorDisplayValue}
									</option>
								</#list>
						 </select>
				      </p>
				  </#if>
				  <#if (appTypeList.size()>0)!>
				      <p>
				          <span>投保类别</span>
				          <select name="appType"  id="appType">
								<#list appTypeList as list>
									<option value="${list.factorValue}">
										${list.factorDisplayValue}
									</option>
								</#list>
						  </select>
				      </p>
				  </#if>
				  <#if (periodList.size()>0)!>
				      <p>
				          <span>保险期限</span>
				          <select name="period"  id="period">
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
									<option value="${list.factorValue}" <#if (list.factorValue == period)!> selected</#if> >
									</#if>
										${list.factorDisplayValue}
									</option>
								</#list>
						  </select>
				      </p>
				  </#if>
				 <p>
                    <span>保单起保日期:</span> 
                    <input id="effective" name="effective" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'})"  onchange = "effchange()" value="${(information.effective)!}"  />
                 </p>
                 <p>
                    <span>保单终止日期:</span> 
                    <input id="fail" name="fail" type="text"   onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'effective\')}'})" onchange = "failchange()" value="${(information.fail)!}" />
                 </p>
                 
            </div>
       </div>
       
       
       <div class="line_a">
       <div  class="line_at">投保人信息</div>
       <div class="form">
       <input type="hidden" id="price"  name="price" value="<#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if>" />
	    
	    <input type ="hidden" name="productName" value="${productName}">
	    <input type ="hidden" name="productId" value="${productId}">
	    <input type ="hidden" name="insurType" value="${insurType}">
	    <input type ="hidden" id="orderStatus" name="orderStatus" value="${orderstatus}" />
	    <input type ="hidden"  name="insurType" value="${insurType}" />
	    <input type ="hidden"  id ="status" name="status" value="${status}" />
	    <input type ="hidden"  id ="orderId" name="orderId" value="${orderId}" />
	    <input type ="hidden"  id ="dutyReq" name="dutyReq" value="${dutyReq}" />
	    <input type ="hidden"  id ="dutyDisReq" name="dutyDisReq" value="${dutyDisReq}" />
	    <input type ="hidden"  id ="insureReq" name="insureReq" value="${insureReq}" />
	    <input type ="hidden" id="insuranceCompany" name="insuranceCompany" value="${insuranceCompany}">
	    <input type ="hidden" id="insuranceCompanySn" name="insuranceCompanySn" value="${insuranceCompanySn}">
	    <input type ="hidden" id="outRiskCode" name="outRiskCode" value="${outRiskCode}">
	    <input type ="hidden" id="protectionPeriodTy" name="protectionPeriodTy" value="${protectionPeriodTy}">
	    <input type ="hidden" id="protectionPeriodLast" name="protectionPeriodLast" value="${protectionPeriodLast}">
	    <input type ="hidden" id="protectionPeriodFlag" name="protectionPeriodFlag" value="${protectionPeriodFlag}">
	    
       <p>
               <span>姓名:</span> 
               <input type="text" id="applicantName" name="informationAppnt.applicantName" value="${(informationAppnt.applicantName)!}"  class="required"  maxlength=50/>
            <label class="requireField">*</label>
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
	                 <span>性别:</span>
		                <select id="applicantSex" name="informationAppnt.applicantSex" style="width:125px;">
		                  <#list sexs as list>
		                      <option value="${list.get('codeValue')}" <#if (informationAppnt.applicantSex==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
		                  </#list>
		                </select>
	                 <label class="requireField">*</label>
                </p>
                <p>
                   <span>出生日期:</span> 
                   <input id="applicantBirthday" name="informationAppnt.applicantBirthday" type="text" onclick="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})"  value="${(informationAppnt.applicantBirthday)!}"  class="required applicantBirthday" onchange="validateBirthday();dateTrigger();"/>
                	<label class="requireField">*</label>
                </p>
            <p>
               <span>手机号码:</span>
               <input type="text" id="applicantMobile" name="informationAppnt.applicantMobile" value="${(informationAppnt.applicantMobile)!}"  class="required mobile" />
           	<label class="requireField">*</label>
			</p>
            <p>
               <span>电子邮箱:</span>
               <input type="text" id="applicantMail" name="informationAppnt.applicantMail" value="${(informationAppnt.applicantMail)!}" class="required email"/>
               <label class="requireField">*</label>
           </p>
      </div>
  </div>
  
  <div class="line_a">
     <div  class="line_at">被保人信息</div>
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
                <input type="text" id="recognizeeName1" name="informationInsureds1.recognizeeName" value="${(informationInsureds1.recognizeeName)!}" maxlength=50/>
            	 <label class="requireField">*</label>
             </p>
            <p>
               <span>证件类型:</span> 
               <select id="recognizeeTypeId1" name="informationInsureds1.recognizeeIdentityType" style="width:125px;" onblur="changeVerification('recognizeeTypeId1','recognizeeId1');">
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
		             <select id="recognizeeSex1" name="informationInsureds1.recognizeeSex" style="width:125px;">
		             	<#list sexs as list>
		             		<option value="${list.get('codeValue')}" <#if (informationInsureds1.recognizeeSex==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
		             		</#list>
		             </select>
		             <label class="requireField">*</label>
	             </p>
	             <p>
	                 <span>出生日期:</span> 
	                 <input id="recognizeeBirthday1" name="informationInsureds1.recognizeeBirthday"  type="text" onclick="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})"  value="${informationInsureds1.recognizeeBirthday}" onchange="validateBirthday();dateTrigger();" />
	            	 <label class="requireField">*</label>
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
        </div>
        <p>
        <span>联系地址:</span> 
        <input type="text" id="recognizeeAddress1" maxlength=200 name="informationInsureds1.recognizeeAddress" value="${(informationInsureds1.recognizeeAddress)!}" class="required adress"/>
        <label class="requireField">*</label>
    </p>
    <p>
        <span>邮编:</span> 
        <input type="text" id="recognizeeZipCode1" name="informationInsureds1.recognizeeZipCode" value="${(informationInsureds1.recognizeeZipCode)!}" class="required zipCode"/>
        <label class="requireField">*</label>
    </p>
    <p>
        <span>居住地址:</span> 
    	<input type="text" id="recognizeeLiveAddress1" maxlength=200 name="informationInsureds1.recognizeeLiveAddress" value="${(informationInsureds1.recognizeeLiveAddress)!}" class="required liveAddress"/>
    	<label class="requireField">*</label>
    </p>
      </div>
   </div>

 <div class="line_a">
 <div  class="line_at">受益人</div>
 <div class="syr">
法定继承人<img title="点击了解什么是法定继承人" src="${base}/template/shop/images/wen_a.png"/>
     </div>
     </div>
 <div class="clear"></div>
 
  <input type="hidden" id="dutyFactor-plan" name="dutyFactor-plan" value="" />
 <input type="hidden" id="dutyFactorSize" name="dutyFactorSize" value="${dutyFactor.size()}" />

<#if (planList.size()>0)!>
 <#assign index_ = 1 />
<#list dutyFactor as list>
	 <input type="hidden" id="amnt${index_}" name="amnt${index_}" value="${list.fdAmntPremList.get(0).backUp1}">
	 <input type="hidden" id="amntDis${index_}" name="amntDis${index_}" value="${list.fdAmntPremList.get(0).amnt}">
	 <input type="hidden" id="dutyCode${index_}" name="dutyCode${index_}" value="${list.dutyCode}" />
	 <#assign index_=index_+1 />
</#list>

<#list planList as planList>
		 <div class="line_a" id="planList-${planList.factorValue}" style="display:none;">
		     <div class="line_at">保障内容信息</div>
		     <div>
		     <table id="mytable" cellspacing="1" class="datagrid3" style="TABLE-LAYOUT: fixed; word-wrap: break-word; word-break: break-all;">
		     <tr> 
		     <th  width="25%">保障项目</th> 
		     <th  width="25%">保额</th> 
		     <th  >保障内容</th> 
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
								      <#assign amt_flag = 0 />
							            <#list list.fdAmntPremList as list2>
							                <#if (planList.factorValue == list2.appFactorValue) || (list2.appFactorValue=='0000')  !>
							                	<span>${list2.amnt}</span>
							                	<input type="hidden" id="amnt-${planList.factorValue}-${index}" name="amnt-${planList.factorValue}${index}" value="${list2.backUp1}">
							               	    <input type="hidden" id="amntDis-${planList.factorValue}-${index}" name="amntDis-${planList.factorValue}${index}" value="${list2.amnt}">
							                  <#else>
							                	<#assign amt_flag = amt_flag + 1 />
							                </#if>
							            </#list>
							             <#if amt_flag == list.fdAmntPremList.size()>
							            	<span>-</span>
							            </#if>
								    </#if>
								</td>
								<td style="word-break:break-all" width="520">
								${list.define}
							</td>
							</tr>
					<#assign index=index+1 />
						</#list>
				</table>
		</div>
			<br>
	</div>
 </#list>
			
 <#else>
	  	<div class="line_a">
	     <div class="line_at">保障内容信息</div>
	     <div>
	     <table id="mytable" cellspacing="1" class="datagrid3" style="TABLE-LAYOUT: fixed; word-wrap: break-word; word-break: break-all;">
	     <tr> 
	     <th  width="25%">保障项目</th> 
		 <th  width="25%">保额</th> 
	     <th  >保障内容</th> 
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
							        <#if (list.fdAmntPremList.size()>1)>
									    <select onchange="changeAmnt('amnt${index}','amntDis${index}');">
											<#list list.fdAmntPremList as list1>
												<option value="${list1.backUp1}">
							                	<span>${list1.amnt}</span>
												</option>
											</#list>
										</select>
										<input type="hidden" id="amnt${index}" name="amnt${index}" value="${list.fdAmntPremList.get(0).backUp1}">
										<input type="hidden" id="amntDis${index}" name="amntDis${index}" value="${list.fdAmntPremList.get(0).amnt}">
									<#else>
							            <#list list.fdAmntPremList as list2>
							               <span>${list2.amnt}</span>
							                <input type="hidden" id="amnt${index}" name="amnt${index}" value="${list2.backUp1}">
							                <input type="hidden" id="amntDis${index}" name="amntDis${index}" value="${list2.amnt}">
							            </#list>
							         </#if>
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
	 </div>
 </#if>
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
     			sex = "F";   
             }else{   
             	sex = "M";   
             }   
     		var year = str.substring(6, 10);      
            var month = str.substring(10, 12);      
    		var day = str.substring(12, 14);
    		document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
    		document.getElementById(sexId).value = sex;
     	}else{
     		var sex;
     		if(str.substring(14,15)%2==0){   
     			sex = "F";   
             }else{   
             	sex = "M";   
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
     }
 }
 </script>
 </div>
 <script type="text/javascript" src="${base}/template/common/js/json2.js"></script>
 <script type="text/javascript" src="${base}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
 <script type="text/javascript" src="${base}/wwwroot/kxb/js/productDuty.js"></script>
 <script type="text/javascript">
 // 只有存在计划列表的时候才执行此方法 
	<#if (planList.size()>0)!>
		planChange();
  </#if>
	pubGetChildrenArea("${informationAppnt.applicantArea1}", "${informationAppnt.applicantArea2}", "${informationInsureds1.recognizeeArea1}", "${informationInsureds1.recognizeeArea2}");
</script>
</body>
</html>