jQuery(document).ready(function(){ 
		jQuery("#orderInfoForm").validate();
		jQuery('#plan').change(function(){
		var value = jQuery('#plan').val();
		var text = jQuery('#plan').find("option:selected").text();
		if(document.getElementById("brkRiskCode")){
			 jQuery("#brkRiskCode").attr("value",value);
			 jQuery("#brkRiskName").attr("value",text);
		}
		var insureJson = insuredElements();
		var dutyJson = dutyElements();
		jQuery.ajax({
		url: sinosoft.base+"/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			 	var obj = eval(data);
				fillin(obj);
			}
		});
		}); 
		jQuery('#s3').change(function(){ 
			var data_falg = false;
			jQuery.ajax({
			url: sinosoft.base+"/shop/order!ajaxOccup.action?occupation3="+jQuery('#s3').val()+"&productId="+jQuery('#productId').val(),
			dataType: "json",
			async: false,
			success: function(data) {
				 if(!data){
					 document.getElementById("occupID").innerHTML = "此职业不能购买本产品";
					 data_falg = false;
				 } else {
					 document.getElementById("occupID").innerHTML = "";
					 data_falg = true;
				 }
				 
				}
			});
			 var relation1 = document.getElementById('relation1');
			 var relation1_index = relation1.selectedIndex;          
			 var relation1_value = relation1.options[relation1_index].text; 
			 
			 var occupation3 = document.getElementById('s3');
			 var occupation3_index = occupation3.selectedIndex;          
			 var occupation3_value = occupation3.options[occupation3_index].text; 
			 
			 if(relation1_value.indexOf('本人') != -1 && data_falg  ) {
				if(occupation3_value.indexOf('17周岁以下')!=-1){
					document.getElementById("occupID").innerHTML = "此职业不能购买本产品";
				}else{
					document.getElementById("occupID").innerHTML = "";
				}
			 }
		});
	});
function insuredElements(){
	var plan = "";
	var relation1 = document.getElementById('relation1');
	var relation1_index = relation1.selectedIndex;          
	var relation1_value = relation1.options[relation1_index].text; 
	var BirthDay = "";
	var sex= "";
	var sexlocal = null;
	var appType = null;
	var feeYear = null;
	var grade = null;
	var period = null;
	var amnt = "";
	var mulPeople = "";
	 if(document.getElementById("plan")){
		 plan =document.getElementById("plan").value;
		 plan = encodeURI(encodeURI(plan));
	 }
	 if(document.getElementById("appType")){
		appType=document.getElementById("appType").value;
	 }
	 if(document.getElementById("feeYear")){
		 feeYear=document.getElementById("feeYear").value;
	 }
	 if(document.getElementById("grade")){ 
		 grade=document.getElementById("grade").value;
	 }
	 if(relation1_value.indexOf('本人') != -1){
		 BirthDay = jQuery('#applicantBirthday').val();
	 } else {
		 BirthDay = jQuery('#recognizeeBirthday1').val();
	 }
	 if(document.getElementById("period")){ 
		 period=document.getElementById("period").value;
	 }
	 if(document.getElementById("amnt")){ 
		 amnt=document.getElementById("amnt").value;
	 }
	 if(relation1_value.indexOf('本人') != -1){
		 sexlocal = document.getElementById("applicantSex").value;
	 }else{
		 sexlocal = document.getElementById("recognizeeSex1").value;
	 }
	 if(sexlocal!=null){
		 if(sexlocal=="0"){
			 sex = "1";
		 }else{
			 sex = "0";
		 }
	 }
	 if(document.getElementById("mulPeople")){ 
		  mulPeople=document.getElementById("mulPeople").value;
		  mulPeople = encodeURI(encodeURI(mulPeople));
	 }

	 var o={"Plan":plan,"AppType":encodeURIComponent(appType),"FeeYear":feeYear,"Grade":encodeURIComponent(grade),"TextAge":BirthDay,"Period":encodeURIComponent(period),"Sex":encodeURIComponent(sex),"MulPeople":mulPeople,"Amnt":amnt};
	 var last=JSON.stringify(o);
	 return last;
}
	function insuredElementsd(){
		 var relation1 = document.getElementById('relation1');
		 var relation1_index = relation1.selectedIndex;          
		 var relation1_value = relation1.options[relation1_index].text;
		 var plan = "";
		 var appType = "";
		 var feeYear = "";
		 var grade = "";
		 var BirthDay = "";
		 var period = "";
		 var sex= "";
		 var amnt = "";
		 var mulPeople = "";
		 var sexlocal = null;
		 if(document.getElementById("plan")){
			plan=document.getElementById("plan").value;
		 }
		 if(document.getElementById("appType")){
			appType=document.getElementById("appType").value;
		 }
		 if(document.getElementById("feeYear")){
			 feeYear=document.getElementById("feeYear").value;
		 }
		 if(document.getElementById("grade")){ 
			 grade=document.getElementById("grade").value;
		 }
		 if(document.getElementById("amnt")){ 
			 amnt=document.getElementById("amnt").value;
		 }
		 if(relation1_value.indexOf('本人') != -1){
			 BirthDay = jQuery('#applicantBirthday').val();
		 } else {
			 BirthDay = jQuery('#recognizeeBirthday1').val();
		 }
		 if(relation1_value.indexOf('本人') != -1){
			 sexlocal = document.getElementById("applicantSex").value;
		 }else{
			 sexlocal = document.getElementById("recognizeeSex1").value;
		 }
		 if(sexlocal!=null){
			 if(sexlocal=="0"){
				 sex = "1";
			 }else{
				 sex = "0";
			 }
		 }
		 if(document.getElementById("period")){ 
			 period=document.getElementById("period").value;
		 }
		 if(document.getElementById("mulPeople")){ 
			  mulPeople=document.getElementById("mulPeople").value;
		 }
		 var o={"Plan":plan,"AppType":appType,"FeeYear":feeYear,"Grade":grade,"TextAge":BirthDay,"Period":period,"Sex":sex,"MulPeople":mulPeople,"Amnt":amnt};
		 var last=JSON.stringify(o);
		 return last;
	}

function dutyElements(){
	 var dutyCodeSize=document.getElementById("dutyFactorSize").value;
	 var tempDuty = parseInt(dutyCodeSize);
	 tempDuty=tempDuty+1;
	 var dutyCode="";
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsonlast = "";
	 var nvalue="nvalue";
	 var dutyFactor = "";
	 if(tempDuty>0){//有责任信息
		 for(var i=1;i<tempDuty;i++){
			 dutyFactor= "dutyFactorID"+i;
			 var amnt ="amnt" + document.getElementById(dutyFactor).value;
		 	 dutyCode = "dutyCode"+i;
		 	 var dutyCodeTemp=document.getElementById(dutyCode).value;//责任编码
		 	 if(document.getElementById(dutyFactor)){
		 		var amntTemp=document.getElementById(amnt).value;//责任计算值
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
	 var dutyCode="";
	 var jsonstar ='{"';
	 var jsoncen = '":"';
	 var jsonend = '"}';
	 var jsonlastDis = "";
	 var nvalue="nvalue";
	 if(tempDuty>0){//有责任信息
		 for(var i=1;i<tempDuty;i++){
		 	dutyFactor= "dutyFactorID"+i;
			var amntDis ="amntDis" + document.getElementById(dutyFactor).value;
		 	dutyCode = "dutyCode"+i;
		 	var dutyCodeTemp=document.getElementById(dutyCode).value;//责任编码
		 	if(document.getElementById(amntDis)){
		 		var amntTemp=document.getElementById(amntDis).value;//责任编码
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
	   var textAgeFlag = document.getElementById("textAgeFlag").value;
	   var relationFlag = document.getElementById("relationFlag1").value;
	   var brithday = "";
	   if(relationFlag == "true"){
		   brithday = document.getElementById("applicantBirthday").value;
	   }else{
		   brithday = document.getElementById("recognizeeBirthday1").value;
	   }
		 if(textAgeFlag =="true"){
			 if(brithday != null && brithday != ""){
				 var insureJson = insuredElements();
				 var dutyJson = dutyElements();
				 jQuery.ajax({
					 url: sinosoft.base+"/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
					 dataType: "json",
					 async: false,
					 success: function(data) {
						 var obj = eval(data);
						 fillin(obj);
					 }
				 });
			 }
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
    //save前得到投保要素和责任
    function insuredAndDuty(){
    	/* zhangjinquan 11180 bug933 下一步重新填写自动带出保障期限，提交前设置保险期限隐藏域  2012-10-12 */
    	setPeriodLastAndType();
	   	var insureJson = insuredElementsd();
		var dutyJson = dutyElements();
		var dutyJsonDis = dutyElementsDis();
		var dutyJsonPrem = dutyPrem();
		var temp1 = document.getElementById("relation1");
		var temp2 = document.getElementById("applicantTypeId");
		var temp3 = document.getElementById("recognizeeTypeId1");
		var temp4 = document.getElementById("recognizeeSex1");
		var temp5 = document.getElementById("applicantSex");
		
		var a = temp1.options[temp1.selectedIndex].text;
		var b = temp2.options[temp2.selectedIndex].text;
		var c = temp3.options[temp3.selectedIndex].text;
		var d = temp4.options[temp4.selectedIndex].text;
		var e = temp5.options[temp5.selectedIndex].text;
		document.getElementById("relation1name").value=a;//被保人关系中文
		document.getElementById("applicanTypeName").value=b;//投保人证件类型中文
		document.getElementById("recognizeeIdentityTypeName").value=c;//被保人证件类型中文
		document.getElementById("recognizeeSexName").value=d;//被保人性别
		document.getElementById("applicantSexName").value=e;//投保人性别
		
		document.getElementById("insureReq").value=insureJson;
		document.getElementById("dutyReq").value=dutyJson;
		document.getElementById("dutyDisReq").value=dutyJsonDis;
		document.getElementById("dutyPremReq").value=dutyJsonPrem;
		if(document.getElementById("brkRiskCode")){
			var value = jQuery('#plan').val();
			var text = jQuery('#plan').find("option:selected").text();
			 jQuery("#brkRiskCode").attr("value",value);
			 jQuery("#brkRiskName").attr("value",text);
		}
		// effective
		 var effective = document.getElementById('effective').value;
		 if(effective == null || effective ==''){
			 return false;
		 }
		 // 与投保人关系
		 var relation1 = document.getElementById('relation1');
		 var relation1_index = relation1.selectedIndex;          
		 var relation1_value = relation1.options[relation1_index].text;
		 var obj ;
		 if(relation1_value.indexOf('本人') != -1){
			 document.getElementById("recognizeeTypeId1").options.selectedIndex = document.getElementById("applicantTypeId").options.selectedIndex;  
			 document.getElementById("recognizeeId1").value = document.getElementById("applicantId").value;  
			 obj = "applicantBirthdayID";
		 } else {
			 obj = "recognizeeBirthdayID";
		 }
		 if(jQuery("#"+obj).html() != null && jQuery("#"+obj).html() != ''){
			 return false;
		 }
		 if(jQuery("#occupID").html() != null && jQuery("#occupID").html() != ''){
			 return false;
		 }
		 if(!loadDutyAndAFC()){
			 return false;
		 }
		 return true;
    }
function dutyPrem(){
   	 var dutyCodeSize=document.getElementById("dutyFactorSize").value;
   	 var tempDuty = parseInt(dutyCodeSize);
   	 tempDuty=tempDuty+1;
   	 var dutyCode="";
   	 var jsonstar ='{"';
   	 var jsoncen = '":"';
   	 var jsonend = '"}';
   	 var jsonlastPrem = "";
   	 var nvalue="nvalue";
   	 
   	 if(tempDuty>0){//有责任信息
   		 for(var i=1;i<tempDuty;i++){
   		 	dutyCode = "dutyCode"+i;
   		 	var dutyCodeTemp=document.getElementById(dutyCode).value;//责任编码
   		 	var premId = "prem-" +dutyCodeTemp;
   		 	if(document.getElementById(premId)){
   		 		var amntTemp=document.getElementById(premId).value;//责任编码
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
   		 jsonlastPrem = jsonstar+jsonend;
   	 }
   	return jsonlastPrem;
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

function getChildrenOPT(id,flag,flag2){
   if(id!="-1"){
        jQuery.ajax({
            type:'get',
		    url: sinosoft.base+"/shop/order!getChildernOPT.action?id="+id,
		    dataType: "json",
		    success: function(item) {
			   fillOPT(item,flag,flag2);
		    }
	    });
   }
}
 

function fillOPT(item,flag,flag2){
	if(flag2!=null&&flag2!=""){
		var childrenOPT3 = document.getElementById(flag2);
		childrenOPT3.options.length=1;
	}
    var childrenOPT = document.getElementById(flag);
    childrenOPT.options.length=1;
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
			    url: sinosoft.base+"/shop/order!getChildernArea.action?id="+id,
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
	 //var all_  = "";
		var   date_   =   new   Date( ); 
	 if (arr = dtDate.match(reg)) {
	      var yy = Number(arr[1]);
		  var mm = Number(arr[2]);
		  var dd = Number(arr[3]);
		  var all_ = mm + '/' + dd + '/' +yy;
		  date_ = new Date(all_);
	 } else {
		 //var myDate = new Date();
		// all_ = (myDate.getMonth()+1) + '-' + ( myDate.getDate() +1) + '-' +myDate.getFullYear();
	 }
	lIntval   =   parseInt(NumDay); 
	switch(type) { 
			case   'Y' : 
				date_.setYear(date_.getFullYear()   +   lIntval) ;
				date_.setDate(date_.getDate()  -  1) ;
			break; 
			
			case   'M': 
				date_.setMonth(date_.getMonth()   +   lIntval) ;
				date_.setDate(date_.getDate() - 1) ;
			break; 
			
			case   'D': 
				date_.setDate(date_.getDate()   +   lIntval) ;
				date_.setDate(date_.getDate() - 1) ;
			break ;
			case   'A':
				date_ = getEndDate(NumDay,dtDate);
			break;
			 
	} 
	var mm = date_.getMonth() + 1;
	var dd = date_.getDate();
	if(mm < 10){
	    mm = '0' +mm;
	}
    if(dd < 10){
	    dd = '0' +dd;
	}
    
	return   date_.getFullYear()   + '-'   +   mm   +   '-'   + dd;
}   
function getEndDate(NumDay,dtDate){
	var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
	var birthday = null;
	var reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
	var lIntval = parseInt(NumDay);
	var arr = null;
	var yy = null;
	var mm = null;
	var dd = null;
	var all_ = null;
	var date_  =  new   Date(); 
	if("本人"==s){
		birthday = document.getElementById("applicantBirthday").value;
	}else{
		birthday = document.getElementById("recognizeeBirthday1").value;
	}
	if(birthday != null && birthday != ""){
		arr = birthday.match(reg);
		yy = Number(arr[1]);
		mm = Number(arr[2]);
		dd = Number(arr[3]);
		all_ = mm + '/' + dd + '/' +yy;
		date_ = new Date(all_);
		date_.setYear(date_.getFullYear() + lIntval);
		date_.setMonth(date_.getMonth()) ;
		date_.setDate(date_.getDate()) ;
		return date_;
	}else{
		birthday = document.getElementById("fail").value;
		arr = birthday.match(reg);
		yy = Number(arr[1]);
		mm = Number(arr[2]);
		dd = Number(arr[3]);
		all_ = mm + '/' + dd + '/' +yy;
		date_ = new Date(all_);
		date_.setYear(date_.getFullYear());
		date_.setMonth(date_.getMonth()) ;
		date_.setDate(date_.getDate()) ;
		return date_;
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
			 protectionPeriodLast=parseInt(protectionPeriodLast);
			 var  temp = str2date(fail+'',protectionPeriodLast);
			// jQuery("#effective").attr("value",temp);
			 document.getElementById("effective").value = "";
			 document.getElementById("effective").value = temp;
			
		 }
	 }
}
function failchangeByBirthday(){
	var s = document.getElementById("relation1").options[document.getElementById("relation1").options.selectedIndex].text;
	var birthday = null;
	if("本人"==s){
		birthday = document.getElementById("applicantBirthday").value;
	}else{
		birthday = document.getElementById("recognizeeBirthday1").value;
	}
	if(birthday != null && birthday != ""){
		periodChange();
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
/* zhangjinquan 11180 bug933 下一步重新填写自动带出保障期限，提取函数 2012-10-12 */
function setPeriodLastAndType()
{
	var pp = jQuery("#period").val();
	/* zhangjinquan 11180  解决部分产品没有保障期间元素js报错的情况 2012-10-20 */
	if ((null == pp) || (typeof(pp)=="undefined"))
	{
		return;
	}
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
}

function periodChange(){
	setPeriodLastAndType();
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
	                if(lbls[j].getAttributeNode('for')!=null && lbls[j].getAttributeNode('for').value==chks[i].value && chks[i].checked== true){
	                	val+=lbls[j].innerHTML + "  ";
	                }
	            }
	        }
	      
	    }
		document.getElementById("showOutGoingParpose").innerHTML = val;   
		document.getElementById("checkOutPice").value = val;
}
function clearOutGoingParpose(){
	var chks=new Array();
    chks=document.getElementsByTagName('input');
    for(var i=0;i<chks.length;i++){ 
    	if(chks[i].type.toString()=='checkbox'){
    		if (chks[i].checked == true) {   
    			chks[i].checked = false;   
    		}   
    	}
	}
    document.getElementById("showOutGoingParpose").innerHTML = "";   
	document.getElementById("checkOutPice").value = "";
}
// 校验生日有效期
function validateBirthday(){
	
	 var relation1 = document.getElementById('relation1');
	 var relation1_index = relation1.selectedIndex;          
	 var relation1_value = relation1.options[relation1_index].text; 
	 var effective = document.getElementById("effective").value;
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
	 if(birthday!=null && birthday!=""){
		 jQuery.ajax({
			 url: sinosoft.base+"/shop/order!ajaxAge.action?applicantBirthday="+birthday+"&productId="+jQuery('#productId').val()+"&effective="+effective,
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
}
function pubGetChildrenArea(appntArea1, appntArea2, insuredArea1, insuredArea2)
{
	if ((typeof(appntArea1) != "undefined") && (appntArea1 !="") && (appntArea1 != "-1"))
	{
		getChildrenArea(appntArea1,'applicantArea2',appntArea2);
	}
	if ((typeof(insuredArea1) != "undefined") && (insuredArea1 != "") && (insuredArea1 != "-1"))
	{
		getChildrenArea(insuredArea1,'insuredAreaA2',insuredArea2);
	}
}

//zhangjinquan 11180 通过节点和节点的属性名称，删除指定节点的指定属性 2012-12-14
function remoteAttributeNew(obj, attrName)
{
	if ((null == obj) || ("undefined" == typeof(obj)))
	{
		return;
	}
	var tAttrNode = null;
	if ((null != obj.getAttributeNode) && ("undefined" != typeof(obj.getAttributeNode)))
	{
		tAttrNode = obj.getAttributeNode(attrName);
	}
	else if ((null != obj.getAttribute) && ("undefined" != typeof(obj.getAttribute)))
	{
		tAttrNode = obj.getAttribute(attrName);
	}
	if (null != tAttrNode)
	{
		if ((null != obj.removeAttributeNode) && ("undefined" != typeof(obj.removeAttributeNode)))
		{
			obj.removeAttributeNode(tAttrNode);
		}
		else if ((null != obj.removeAttribute) && ("undefined" != typeof(obj.removeAttribute)))
		{
			obj.removeAttribute(tAttrNode);
		}
	}
}
function getPersonAge(birthday){
	var r = birthday.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);  
	var age = 0;
	if(r==null){
		return age; 
	}
	var d= new Date(); 
	var thisMonth = d.getMonth()+1;
	if(d.getFullYear()-r[1]<0){
		return age;
	}else{
		if (thisMonth-r[3]<0) { 
			age = d.getFullYear()-r[1]-1; 
		} else{
			if(d.getDate()-r[4]<0){
				age = d.getFullYear()-r[1]-1; 
			}else{
				age = d.getFullYear()-r[1]; 
			}
		}
	}
	return age;
}
function changePrem(){
	var insureJson = insuredElements();
	var dutyJson = dutyElements();
	jQuery.ajax({
		url: sinosoft.base+"/shop/order!ajaxCounter.action?insureJson="+insureJson+"&dutyJson="+dutyJson,
		dataType: "json",
		async: false,
		success: function(data) {
			var obj = eval(data);
			fillin(obj);
		}
	});
}
function fillin(obj){
	if(document.getElementById("discountPrice")){
		 document.getElementById("discountPrice").innerHTML=obj.retTotlePrem;
		 document.getElementById("discountPrice_").innerHTML=obj.retTotlePrem;
		 jQuery("#currentTermAmount").attr("value",obj.retTotlePrem);
		 jQuery("#discountRates").attr("value",obj.discountRate);
	 }
	 document.getElementById("priceTotle").innerHTML=obj.retCountPrem;
	 document.getElementById("priceTotle_").innerHTML=obj.retCountPrem;
	 document.getElementById("price").value = obj.retCountPrem;
	 jQuery("#productTotalPrice").attr("value",obj.retCountPrem);
	 var dutyAmntList = obj.retDutyAmounts;
	 if(dutyAmntList!=null && dutyAmntList.length>0){
		 for(var i=0;i<dutyAmntList.length;i++){
			 var dutyAmnt = dutyAmntList[i];
			 var premId = "prem-" +dutyAmnt.dutyCode;
			 if(document.getElementById(premId)!=null){
				 document.getElementById(premId).value = dutyAmnt.prem;
			 }
		 }
	 }
}