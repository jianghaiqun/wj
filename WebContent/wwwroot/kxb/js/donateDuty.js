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

	 var o={"Plan":encodeURIComponent(plan),"AppType":encodeURIComponent(appType),"FeeYear":feeYear,"Grade":encodeURIComponent(grade),"TextAge":BirthDay,"Period":encodeURIComponent(period),"Sex":encodeURIComponent(sex)};
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
		 if(document.getElementById("recognizeeBirthday1")){ 
		 var BirthDay=document.getElementById("recognizeeBirthday1").value;
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

		 var o={"Plan":plan,"AppType":appType,"FeeYear":feeYear,"Grade":grade,"TextAge":BirthDay,"Period":period,"Sex":sex};
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
  
    //save前得到投保要素和责任
    function insuredAndDuty(){
	   	var insureJson = insuredElementsd();
		var dutyJson = dutyElements();
		var dutyJsonDis = dutyElementsDis();
		
		var temp2 = document.getElementById("applicantTypeId");
		var temp5 = document.getElementById("applicantSex");
		
		var b = temp2.options[temp2.selectedIndex].text;
		var e = temp5.options[temp5.selectedIndex].text;
		document.getElementById("relation1name").value='本人';//被保人关系中文
		document.getElementById("applicanTypeName").value=b;//投保人证件类型中文
		document.getElementById("applicantSexName").value=e;//投保人性别
		
		document.getElementById("insureReq").value=insureJson;
		document.getElementById("dutyReq").value=dutyJson;
		document.getElementById("dutyDisReq").value=dutyJsonDis;
		
		 return true;
    }
    
  
function add_zero(temp)
{
 if(temp<10) return "0"+temp;
 else return temp;
}

 
function effchange(){
	 var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("true"==protectionPeriodFlag){
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 var effective = document.getElementById("effective").value;
		// alert(protectionPeriodLast+'-'+protectionPeriodTy+'-'+effective);
		 //jQuery("#fail").attr("value" , addDate(protectionPeriodTy,protectionPeriodLast,effective));
		 //alert(protectionPeriodTy+'-'+protectionPeriodLast+'-'+effective);
		 var temp = addDate(protectionPeriodTy,protectionPeriodLast,effective);
		 document.getElementById("fail").value ="";
		 document.getElementById("fail").value = temp;
		 document.getElementById("fail_").value ="";
		 document.getElementById("fail_").value = temp;
	 }
}



function  addDate(type,NumDay,dtDate) { 
	 var   reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
	 var all_  = "";
	 if (arr = dtDate.match(reg)) {
	      var yy = Number(arr[1]);
		  var mm = Number(arr[2]);
		  var dd = Number(arr[3]);
		  all_ = mm + '-' + dd + '-' +yy;
	 } else {
		 var myDate = new Date();
		 all_ = (myDate.getMonth()+1) + '-' + ( myDate.getDate() +1) + '-' +myDate.getFullYear();
	 }
	 
	var   date_   =   new   Date(all_); 
	lIntval   =   parseInt(NumDay);//间隔 
	switch(type) { 
			case   'Y' ://年 
				date_.setYear(date_.getFullYear()   +   lIntval) ;
				date_.setDate(date_.getDate()   +   -1) ;
			break; 
			
			case   'M'://月 
				date_.setMonth(date_.getMonth()   +   lIntval) ;
				date_.setDate(date_.getDate()   +   -1) ;
			break; 
			
			case   'D'://天 
				date_.setDate(date_.getDate()   +   lIntval) ;
				date_.setDate(date_.getDate()   +   -1) ;
			break ;
			 
	} 
	var mm = date_.getMonth()+1;
	var dd = date_.getDate();
	if(mm < 10){
	    mm = '0' +mm;
	}
    if(dd < 10){
	    dd = '0' +dd;
	}
	return   date_.getFullYear()   + '-'   +   mm   +   '-'   + dd;
}   



function getChildrenOPT(id,flag){
	   if(id!="-1"){
	        jQuery.ajax({
	            type:'get',
			    url: sinosoft.base+"/shop/donate!getChildernOPT.action?id="+id,
			    dataType: "json",
			    success: function(item) {
				   fillOPT(item,flag);
			    }
		    });
	   }
	}

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



function fillOPT(item,flag){
var childrenOPT = document.getElementById(flag);
childrenOPT.options.length=1
for(var i=0;i<item.length;i++){
	var oo=new Option(item[i].name,item[i].id);
	childrenOPT.options[childrenOPT.options.length]=oo;
}
}
function getChildrenArea(id,flag,flagvalue){
if(id!="-1"){
 if(flag != ""){
     jQuery.ajax({
         type:'get',
		    url: sinosoft.base+"/shop/donate!getChildernArea.action?id="+id,
		    dataType: "json",
		    success: function(item) {
			   fillArea(item,flag,flagvalue);
		    }
	    });
 }
}else{
var childrenArea = document.getElementById(flag);
childrenArea.options.length=1;
}
}
function fillArea(item,flag,flagvalue){
var childrenArea = document.getElementById(flag);
childrenArea.options.length=1;
for(var i=0;i<item.length;i++){
	var oo=new Option(item[i].name,item[i].id);
	childrenArea.options[childrenArea.options.length]=oo;
	if (typeof(flagvalue)!="undefined" && oo.value == flagvalue)
	{
		oo.selected = "selected";
	}
}
}


function failchange(){
	var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("true"==protectionPeriodFlag){
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		
			 var fail = document.getElementById("fail").value;
			 protectionPeriodLast = "-"+protectionPeriodLast;
			 protectionPeriodLast=parseInt(protectionPeriodLast);
			 var  temp = str2date(fail+'',protectionPeriodLast);
			// jQuery("#effective").attr("value",temp);
			 document.getElementById("effective").value = "";
			 document.getElementById("effective").value = temp;
			
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
	return  date2str(yy, mm, dd , n);
} 
	
	function date2str(yy, mm, dd ,n) {
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
function changeVerification(Idtype,changeId){
	var ss = document.getElementById(Idtype);
	//alert(ss.value);
	document.getElementById(changeId).value="";
	if(Idtype=="applicantTypeId"){
		var s = ss.options[ss.selectedIndex].text;
		if(s=="身份证"){
			document.getElementById(changeId).className ="required isIdCardNo";
		}else{
			String(s);
			document.getElementById("applicanTypeName").value=s;
			document.getElementById(changeId).className ="required";
		}
	}
	if(Idtype=="recognizeeTypeId1"){
		var s = ss.options[ss.selectedIndex].text;
		if(s=="身份证"){
			document.getElementById(changeId).className ="required isIdCardNo";
		}else{
			document.getElementById(changeId).className ="required";
		}
	}
}
function changeAmnt(amnt,amntDis){
	
	//var objSelect = objForm.relation2;
	//var s = objSelect.options[objSelect.selectedIndex].text;
	//document.getElementById(changeId).value = value;
}

function periodChange(){
	var pp = jQuery("#period").val();
	var pp_length = pp.length;
	if(pp != null && pp.indexOf('-') == -1){
		jQuery("#protectionPeriodLast").attr("value" ,pp.substr(0, pp_length - 1));
		jQuery("#protectionPeriodTy").attr("value" ,pp.substr(pp_length - 1,1));
		
	} else if(pp.indexOf('-') != -1){
		var p_index = pp.indexOf('-') + 1;
		var pp_ = pp.substr(p_index);
		pp_length =  pp_.length;
		jQuery("#protectionPeriodLast").attr("value" , pp_.substr(0,pp_length - 1));
		jQuery("#protectionPeriodTy").attr("value" , pp_.substr(pp_length - 1,1));
	}
	effchange();
}


jQuery("#light").jqm({
	modal: true,// 是否开启模态窗口
	overlay: 100,// 屏蔽层透明度
	trigger: ".selectOutPice",// 激活元素
	closeClass: "loginWindowClose_",// 关闭按钮
	onHide: function(hash) {
		hash.o.remove();
		hash.w.fadeOut();
	},
	onShow: function(hash){
		hash.w.fadeIn();
 }
}).jqDrag(".windowTop");

function setOutGoingParpose(){
	 var chks=new Array();
	    var lbls=new Array();
	    var val='';
	    chks=document.getElementsByTagName('input');
	    lbls=document.getElementsByTagName('label');
	 
	    for(var i=0;i<chks.length;i++){
	        if(chks[i].type.toString()=='checkbox'){
	           
	            for(var j=0;j<lbls.length;j++){
	                
	                if(lbls[j].getAttributeNode('for')!=null&&lbls[j].getAttributeNode('for').value==chks[i].value && chks[i].checked== true){
	                    val+=lbls[j].innerText.toString()+"  ";
	                }
	            }
	        }
	      
	    }
		document.getElementById("showOutGoingParpose").innerHTML = val;   
		document.getElementById("checkOutPice").value = val;
}
// 校验生日有效期
function validateBirthday(){
	 var relation1 = document.getElementById('relation1');
	 var relation1_index = relation1.selectedIndex;          
	 var relation1_value = relation1.options[relation1_index].text; 
	 var obj = "";
	 var birthday = "";
	 if(relation1_value.indexOf('本人') != -1){
		 obj = "applicantBirthdayID";
		 birthday = jQuery('#applicantBirthday').val();
		 
	 } else {
		 obj = "recognizeeBirthdayID";
		 birthday = jQuery('#recognizeeBirthday1').val();
		 document.getElementById("applicantBirthdayID").innerHTML = "";
	 }
	
	jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxAge.action?applicantBirthday="+birthday+"&productId="+jQuery('#productId').val(),
		dataType: "json",
		async: false,
		success: function(data) {
			 if(!data){
				 document.getElementById(obj).innerHTML = "此年龄不能购买本产品";
			 } else {
				 document.getElementById(obj).innerHTML = "";
			 }
			}
		});
}


function dateTrigger(){
	   var textAgeFlag = document.getElementById("textAgeFlag").value;
	 if(textAgeFlag =="true"){
		 var birthday = document.getElementById("recognizeeBirthday1").value;
			var insureJson = insuredElements();
			var dutyJson = dutyElements();
			jQuery.ajax({
				url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
				dataType: "json",
				async: false,
				success: function(data) {
					 document.getElementById("priceTotle").innerHTML=data;
					}
				});
	 }
 }
 
 function st(){
 	var effective = document.getElementById("effective").value;
 	document.getElementById("start").innerHTML=effective+"起";
 }
 function en(){
 	var fail = document.getElementById("fail").value;
 	document.getElementById("end").innerHTML=fail+"止";
 }

 jQuery(document).ready(function(){ 
		jQuery("#orderInfoForm").validate();
		jQuery('#plan').change(function(){
		var value = jQuery('#plan').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		}) 
		jQuery('#appType').change(function(){ 
		var value = jQuery('#appType').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#feeYear').change(function(){ 
		var value = jQuery('#feeYear').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#grade').change(function(){ 
		var value = jQuery('#grade').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#occup').change(function(){ 
		var value = jQuery('#occup').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#period').change(function(){ 
		var value = jQuery('#period').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#sex').change(function(){ 
		var value = jQuery('#sex').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		})
		jQuery('#amnt').change(function(){ 
		var value = jQuery('#amnt').val();
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/donate!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 document.getElementById("priceTotle").innerHTML=data;
			}
		});
		});
		
		jQuery('#s3').change(function(){ 
			jQuery.ajax({
			url: sinosoft.base+"/shop/donate!ajaxOccup.action?occupation3="+jQuery('#s3').val()+"&productId="+jQuery('#productId').val(),
			dataType: "json",
			async: false,
			success: function(data) {
				 if(!data){
					 document.getElementById("occupID").innerHTML = "此职业不能购买本产品";
					 
				 } else {
					 document.getElementById("occupID").innerHTML = "";
				 }
				 
				}
			});
		});
	})