<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>  
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">
Page.onLoad(function(){ 
	pageload();
});

// 变更操作
function policyChange() {
	var dc = Form.getData("form2");
	if(Verify.hasError()){
		return;
	}
	
	var applicantEnName = document.getElementById("applicantEnName");
	
	if ($V("company") == '2101') {
		var enName = applicantEnName.value;
		if (enName == null || enName.trim() == '') {
			Dialog.alert("投保人英文名不能为空");
			return;
		}
	}
//     if (!applicantEnName.disabled) {
//     	var enName = applicantEnName.value;
//     	if (enName == null || enName.trim() == '') {
//     		Dialog.alert("投保人英文名不能为空");
// 			return;
//     	}
//     }
	var applicantIdentityType = document.getElementById("applicantIdentityTypeName");
	var applicantIdentityId = document.getElementById("applicantIdentityId").value;
	var applicantSexName = document.getElementById("applicantSexName").textField.value
	var applicantBirthday = document.getElementById("applicantBirthday").value;
	// 投保人证件号校验
	if (!idcheck(applicantIdentityType.textField.value,applicantIdentityType.value,applicantIdentityId, applicantSexName, applicantBirthday)) {
		return;
	}
	
	var ele = $("dg1");
	if (ele != null && ele.rows.length > 0) {
		for(var i=1;i<ele.rows.length;i++){
			if (ele.rows[i].cells[1].children.dg1_RowCheck.checked) {
				var flag = ele.rows[i].cells[10].children[0].disabled;
				var flag1 = ele.rows[i].cells[9].children[0].disabled;
				var flag2 = ele.rows[i].cells[4].children[0].disabled;
				var mail = ele.rows[i].cells[10].children[0].value;
				var mobile = ele.rows[i].cells[9].children[0].value;
				var enName = ele.rows[i].cells[4].children[0].value;
				if(!flag){
					if (mail == null || mail.trim() == '') {
						Dialog.alert("保单信息的第"+i+"行的电子邮箱不能为空");
						return;
					}
				}
				if(!flag1){
					if (mobile == null || mobile.trim() == '') {
						Dialog.alert("保单信息的第"+i+"行的手机号不能为空");
						return;
					}
				}
				if(!flag2){
					if (enName == null || enName.trim() == '') {
			    		Dialog.alert("保单信息的第"+i+"行的英文名不能为空");
						return;
			    	}
				}
				// 被保人身份证校验
				var identityType = ele.rows[i].cells[6].children[0].value.split("_")[1];
				var identityTypeName = ele.rows[i].cells[6].children[0].textField.value;
				var identityTypeId = ele.rows[i].cells[7].children[0].value;
				var sexName = ele.rows[i].cells[5].children[0].textField.value;
				var Birthday = ele.rows[i].cells[8].children[0].value;
				if (!idcheck(identityTypeName,identityType,identityTypeId,sexName,Birthday)) {
					return;
				}
			}
		}
	}
	var dt = getDataTable();
	dc.add("Data", dt);
	
	var healdt = getHealthDataTable();
	dc.add("healData", healdt);
	
	Dialog.confirm("确认变更吗？", function() {
		Dialog.wait("变更中，请稍后..."); 
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.policyChange",dc,function(){
			Dialog.closeEx();
			var response = Server.getResponse();
			Dialog.alert(response.Message);
			if(response.Status==1){
					//DataGrid.loadData('dg1');
			}
		});
	});
	
}

function checkLength(name, value, len) {
	var lenStr = 0;
	for (var k=0; k<value.length; k++) { 
		   var c = value.charCodeAt(k); 
		   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
			   lenStr = lenStr + 1;
		   }
		   else {     
			   lenStr = lenStr + 2;
		   } 
	 }
	if (lenStr<=len) {
		return name+"长度必须大于" + len  + "个字符！";
	}
	return "";
}

function getDataTable() {
	var dg = $("dg1");
	var dt = dg.DataSource;
	var names = ["recognizeeAppntRelation", "recognizeeName", "recognizeeEnName", "recognizeeSex", "recognizeeIdentityType", "recognizeeIdentityId",
			"recognizeeBirthday", "recognizeeMobile", "recognizeeMail"];
	var map = {};
	for ( var i = 0; i < names.length; i++) {
		var eles = $N(names[i]);
		var arr = [];
		if (eles != null) {
			for ( var j = 0; j < eles.length; j++) {
				arr.push($V(eles[j]));
			}
		}
		map[names[i]] = arr;
	}
	var strID = dg.id;
	var arr1 = $N(strID+"_RowCheck");
	for ( var i = 0; i < dt.getRowCount(); i++) {
		for ( var j = 0; j < names.length; j++) {
			dt.Rows[i].set(names[j], map[names[j]][i]);
		}
		if (arr1[i].checked) {
			dt.Rows[i].set("checkflag", "1");
		} else {
			dt.Rows[i].set("checkflag", "0");
		}
	}
	return dt;
}

function getHealthDataTable() {
	var dg = $("dghealth");
	var dt = dg.DataSource;
	var names = ["healthid", "healthyinfoid", "selectFlag"];
	var map = {};
	for ( var i = 0; i < names.length; i++) {
		var eles = $N(names[i]);
		var arr = [];
		if (eles != null) {
			for ( var j = 0; j < eles.length; j++) {
				arr.push($V(eles[j]));
			}
		}
		map[names[i]] = arr;
	}
	var strID = dg.id;
	for ( var i = 0; i < dt.getRowCount(); i++) {
		for ( var j = 0; j < names.length; j++) {
			dt.Rows[i].set(names[j], map[names[j]][i]);
		}
	}
	return dt;
}

function effchange(){
	var specialEffDateFlag=document.getElementById("specialEffDateFlag").value;//太平E宝贝重疾保障计划 终保日期根据被保人生日改变
	var protectionPeriodLast = document.getElementById("ensureLimit").value;//保障期限后段
	var protectionPeriodTy = document.getElementById("ensureLimitType").value;//保障期限类型Y,M,D
	if(specialEffDateFlag!="Y"){
		var effective = document.getElementById("svalidate").value;
		var temp = addDate(protectionPeriodTy,protectionPeriodLast,effective);
		document.getElementById("evaliDate").value =temp;
	}
}

function effchange2(day){
	var specialEffDateFlag=document.getElementById("specialEffDateFlag").value;//太平E宝贝重疾保障计划 终保日期根据被保人生日改变
	var protectionPeriodLast = document.getElementById("ensureLimit").value;//保障期限后段
	var protectionPeriodTy = document.getElementById("ensureLimitType").value;//保障期限类型Y,M,D
	if(protectionPeriodTy!="Y"||protectionPeriodTy!="M"||protectionPeriodTy!="D"){
		protectionPeriodTy="Y";
	}
	if(specialEffDateFlag=="Y"){
		var effective = day;//document.getElementById("svalidate").value;
		var temp = addDate(protectionPeriodTy,protectionPeriodLast,effective);
		document.getElementById("evaliDate").value =temp;
	}
}

function  addDate(type,NumDay,dtDate) { 
	 var   reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
		var   date_   =   new   Date( ); 
	 if (arr = dtDate.match(reg)) {
	      var yy = Number(arr[1]);
		  var mm = Number(arr[2]);
		  var dd = Number(arr[3]);
		  var all_ = mm + '/' + dd + '/' +yy;
		  date_ = new Date(all_);
	 } else {
		 var myDate = new Date();
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
			 
	} 
	var mm = date_.getMonth() + 1;
	var dd = date_.getDate();
	if(mm < 10){
	    mm = '0' +mm;
	}
    if(dd < 10){
	    dd = '0' +dd;
	}
    
	return   date_.getFullYear()   + '-'   +   mm   +   '-'   + dd + " 23:59:59";
}

function checkId(value, element) { 
	 var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
	 var iSum = 0;
	 var strIDno = value;
	 if(!/^\d{17}(\d|x)$/i.test(strIDno)&&!/^\d{15}$/i.test(strIDno)){
 		if(!/^\d{16}(x{2})$/i.test(strIDno)){
			 return false; //非法身份证号
		 }
	 }
	 if(aCity[parseInt(strIDno.substr(0,2))]==null){
	       return false;// 非法地区
	 }
	 
	     // 判断是否大于2078年，小于1900年
	     var year =strIDno.substring(6,10);
	     if (year<1900 || year>2078 ){ 
	         return false;//非法生日
	     }

	    //18位身份证处理

	   //在后面的运算中x相当于数字10,所以转换成a
	    strIDno = strIDno.replace(/x$/i,"a");

	  sBirthday=strIDno.substr(6,4)+"-"+Number(strIDno.substr(10,2))+"-"+Number(strIDno.substr(12,2));
	  var d = new Date(sBirthday.replace(/-/g,"/"));
	  if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
	         return false;//非法生日
	   }
	    // 身份证编码规范验证
	  for(var i = 17;i>=0;i --)
	   iSum += (Math.pow(2,i) % 11) * parseInt(strIDno.charAt(17 - i),11);
	  if(/^\d{16}(x{2})$/i.test(strIDno)){
		  iSum=12;
	  }
	  if(iSum%11!=1){
	      return false;// 非法身份证号
	   }
	   // 判断是否屏蔽身份证
	    var words = new Array();
	    words = new Array("11111119111111111","12121219121212121");

	    for(var k=0;k<words.length;k++){
	        if (strIDno.indexOf(words[k])!=-1){
	            return false;
	        }
	    }

	 return true;
}

/**
 * 校验证件号码
 * @param idType_value 证件类型名称
 * @param idType_id 证件类型id
 * @param idTypeNo 证件号
 * @param SexName 性别
 * @param Birthday 生日
 * @returns {Boolean}
 */
function idcheck(idType_value,idType_id, idTypeNo, SexName, Birthday){

	str = idTypeNo.trim();
	var StrLast=str.substring(str.length-1);

	if(idType_value.indexOf("身份证") != -1){
		if(str.length==18){
			  
			if(!checkId(str,"")){
				Dialog.alert("身份证:"+str+"一定要填写18位有效身份证号码");
				return false ;
			}else{
				if(StrLast=='x'){
					Dialog.alert("身份证:"+str+"有误,身份证号码最后一位请输入大写:X");
					return false ;
				}
			}
			if(str.substring(16,17)%2==0||str.substring(16,17)=='x'||str.substring(16,17)=='X'){  
				if(SexName != '女') {
					Dialog.alert("身份证:"+str+",性别应该选择“女”");
					return false ;
				}
			} else {
				if(SexName != '男') {
					Dialog.alert("身份证:"+str+",性别应该选择“男”");
					return false ;
				}
			}
			var year = str.substring(6, 10);      
			var month = str.substring(10, 12);      
			var day = str.substring(12, 14);
			if (year != Birthday.substring(0, 4) || month != Birthday.substring(5, 7) || day != Birthday.substring(8, 10)) {
				Dialog.alert("身份证:"+str+",与生日"+Birthday+"不一致");
				return false ;
			}
			
		}else{
			Dialog.alert("身份证:"+str+"一定要填写18位有效身份证号码");
			return false;
		} 

	}else if(idType_id == 2){
		//军官证、士兵证
		if(str.length2() <= 20){
				if(!(str.indexOf("字第") != -1 && str.indexOf("字第") > 0)){
					//不符合字第
					if(isSpecialCharacters(str,"2") || str.length < 4){
						Dialog.alert("证件号码:"+str+"有误,格式错误!");
						return false;
					}
				}else{
					var tempStr = str.substr(str.indexOf("字第")+2,str.length);
					if(isSpecialCharacters(tempStr,"2") || tempStr.length < 4){
						Dialog.alert("证件号码:"+str+"有误,格式错误!")
						return false;
					}
				}
		}else{
			Dialog.alert("证件号码:"+str+"有误,格式错误!")
			return false;
		}
		
	}else if(idType_id == 3){
		//护照-外国护照
		if(str.length2() < 3){
			Dialog.alert("证件号码:"+str+"有误,格式错误!")
			return false;
		}
	}else if(idType_id == 4){
		//台湾通行证
		if(!/[A-Z].*[0-9]|[0-9].*[A-Z]/.test(str)||str.length < 8){
			Dialog.alert("证件号码:"+str+"有误,格式错误!")
			return false;
		}else if(isSpecialCharacters(str,"1")){
			Dialog.alert("证件号码:"+str+"有误,格式错误!")
			return false;
		}
		
	}
	return true;
}
DataGrid.nextPage = function(ele){
		ele = $(ele);
		var index = DataGrid.getParam(ele,Constant.PageIndex);
		DataGrid.setParam(ele,Constant.PageIndex,parseInt(index)+1);
		DataGrid.loadData(ele, pageload);
}

DataGrid.firstPage = function(ele){
	ele = $(ele);
	DataGrid.setParam(ele,Constant.PageIndex,0);
	DataGrid.loadData(ele, pageload);
}

DataGrid.lastPage = function(ele){
	ele = $(ele);
	var total = DataGrid.getParam(ele,Constant.PageTotal);
	var size = DataGrid.getParam(ele,Constant.Size);
	var max = Math.ceil(parseInt(total)*1.0/parseInt(size));
	DataGrid.setParam(ele,Constant.PageIndex,max-1);
	DataGrid.loadData(ele, pageload);
}

DataGrid.previousPage = function(ele){
	ele = $(ele);
	var index = DataGrid.getParam(ele,Constant.PageIndex);
	DataGrid.setParam(ele,Constant.PageIndex,parseInt(index)-1);
	DataGrid.loadData(ele, pageload);
}

DataGrid.discard = function(ele){
	DataGrid.loadData(ele, pageload);
}
function pageload(){
	    var enName = document.getElementById("applicantEnName").value;
	    if (enName != null && enName.trim() != '') {
	    	document.getElementById("applicantEnName").disabled = false;
	    }
		var ele = $("dg1");
		if (ele != null) {
			for(var i=1;i<ele.rows.length;i++){
				if(ele.rows[i].cells[12].innerText == "1"){
					ele.rows[i].cells[2].children[0].disabled = false;
					ele.rows[i].cells[3].children[0].disabled = false;
					ele.rows[i].cells[5].children[0].disabled = false;
					ele.rows[i].cells[6].children[0].disabled = false;
					ele.rows[i].cells[7].children[0].disabled = false;
					ele.rows[i].cells[8].children[0].disabled = false;
					var mail = ele.rows[i].cells[10].children[0].value;
					var mobile = ele.rows[i].cells[9].children[0].value;
					var recoEnName = ele.rows[i].cells[4].children[0].value;
					if (mail != null && mail.trim() != '') {
						ele.rows[i].cells[10].children[0].disabled = false;
					}
					if (mobile != null && mobile.trim() != '') {
						ele.rows[i].cells[9].children[0].disabled = false;
					}
					if (recoEnName != null && recoEnName.trim() != '') {
						ele.rows[i].cells[4].children[0].disabled = false;
					}
				}
			}
		}
	
}
function GetDestination(productid,comCode){
	var NDestination = encodeURIComponent(encodeURIComponent($V("NDestination")));
	var FDestination = encodeURIComponent(encodeURIComponent($V("FDestination")));
	var diag = new Dialog("product_source");
	diag.Width = 1050;
	diag.Height = 300;
	diag.Title = "地区列表";
	diag.URL = "DataService/DestinationAdd.jsp?productid=" + productid +"&comCode="+comCode+"&FDestination="+FDestination+"&NDestination="+NDestination;
	diag.onLoad = function() {
	};
	diag.OKEvent = addSave;
	diag.show();
	diag.OKButton.value = "保 存";
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	document.getElementById("NDestination").value=dc.get("NDestination");
	document.getElementById("FDestination").value=dc.get("FDestination");
	document.getElementById("tDestination").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;"+dc.get("FDestination");
	$D.close();
}

</script>
</head>
<body class="dialogBody">
	<form id="form2">
	 <z:init method="com.wangjin.cms.orders.QueryOrders.initDialog2">
	<table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
	            <tr> <td>
	           
	                  <fieldset>
		                    <table width="100%" cellpadding="2" cellspacing="0" >
								<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 订单基本信息</td></tr>
								<tr>
								    <input type="hidden" id="specialEffDateFlag" name="specialEffDateFlag" value="${specialEffDateFlag}" style="width:150px" />
								    <input type="hidden" id="order_Sn" name="order_Sn" value="${orderSn}" style="width:150px" />
								    <input type="hidden" id="recognizee_Sn" name="recognizee_Sn" value="${recognizeeSn}" style="width:150px" />
								    <input type="hidden" id="information_Sn" name="information_Sn" value="${informationSn}" style="width:150px" />
								    <input type="hidden" id="company" name="company" value="${company}" />
								    <input name="id" type="hidden" value="${id}" id="id" />
								    <input name="informationId" type="hidden" value="${informationId}" id="informationId" />
								    <input type="hidden" id="ensureLimit" name="ensureLimit" value="${ensureLimit}" />
								    <input type="hidden" id="ensureLimitType" name="ensureLimitType" value="${ensureLimitType}" />
								    <input type="hidden" id="svalidate_hid" name="svalidate_hid" value="${svalidate}" />
								    <input type="hidden" id="svalitime" name="svalitime" value="${svalitime}" />
								    <input name="NDestination" id="NDestination" type="hidden" value="${NDestination}" />
									<input name="FDestination" id="FDestination" type="hidden" value="${FDestination}" />
									<td height="25" align="right" class="tdgrey1">订单号码：</td>
									<td height="25">
						            <input name="orderSn" type="text" value="${orderSn}" style="width:150px"  id="orderSn"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">产品：</td>
									<td height="25">
						            <input name="productName" type="text" value="${productName}" style="width:160px"  id="productName"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">承保公司：</td>
									<td height="25">
						            <input name="insurCompany" type="text" value="${insurCompany}" style="width:150px"  id="insurCompany"  disabled="true"/>
						            </td>
						         </tr>
						         <tr>
						            <td height="25" align="right" class="tdgrey1">保险起期：</td>
									<td height="25">
						            <input name="svalidate" type="text" value="${svalidate}" style="width:150px" onblur = "effchange()" id="svalidate" ztype="date" verify="保险起期|NotNull&&Date"  />${svalitime}
						            </td>
						            <td height="25" align="right" class="tdgrey1">保险止期：</td>
									<td height="25">
						            <input name="evaliDate" type="text" value="${evaliDate}" style="width:160px;border-color: #ccc; background:#f5f5f5 none;"  id="evaliDate"  readonly/>
						            </td>
						            <td height="25" align="right" class="tdgrey1" >创建时间：</td>
									<td height="25"><b></b><input type="text" id="createDate" name="createDate" value="${createDate}" style="width:150px" disabled="true"/></td>
						         </tr>  
						         <tr>
							         <td height="25" align="right" class="tdgrey1">订单原价：</td>
									<td height="25">
									<input name="productTotalPrice" type="text" value="${productTotalPrice}" style="width:150px" class="inputText" id="productTotalPrice"  disabled="true" / >
									</td>
							         <td height="25" align="right" class="tdgrey1">订单总价：</td>
										<td height="25">
						                <input name="totalAmount" type="text" value="${totalAmount}" style="width:160px" class="inputText" id="totalAmount"  disabled="true"/>
						                </td>
							         <td height="25" align="right" class="tdgrey1">订单实付：</td>
										<td height="25">
						                <input name="paidAmount" type="text" value="${payPrice}" style="width:150px" class="inputText" id="paidAmount"  disabled="true"/>
						                </td>
						         </tr>	
						         <tr>
							         <td height="25" align="right" class="tdgrey1">保单份数：</td>
										<td height="25">
							            <input name="policySum" type="text" value="${policyNumber}" style="width:150px"  id="policySum"  disabled="true"/>
							         </td>
							         <td height="25" align="right" class="tdgrey1">递送费用：</td>
										<td height="25">
							            <input name="recCost" type="text" value="" style="width:160px"  id="recCost"  disabled="true"/>
							         </td>
							         <td height="25" align="right" class="tdgrey1">会员 ID：</td>
										<td height="25">
							            <input name="mid" type="text" value="${memberid}" style="width:150px"  id="memberid"  disabled="true"/>
							         </td>
						         </tr>
						         <tr>
						            <td height="25" align="right" class="tdgrey1">订单状态：</td>
									<td height="25">
						               <z:select id="orderStatus" name="orderStatus" style="width:150px" listWidth="150"  disabled="true"> ${orderStatus}</z:select></td>
						               
						                <td height="25" align="right" class="tdgrey1">商户订单号：</td>
										<td height="25">
							            <input name="paySn" type="text" value="${paySn}" style="width:160px"  id="paySn"  disabled="true"/>
							         </td>
								</tr>
								<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 购买优惠信息</td></tr>
								<tr>
									<td height="25" align="right" class="tdgrey1">优惠券优惠说明：</td>
									<td height="25">
						            <input name="direction" type="text" value="${direction}" style="width:150px"  id="direction"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">优惠券优惠金额：</td>
									<td height="25">
						            <input name="parValue" type="text" value="${parValue}" style="width:160px"  id="parValue"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">优惠券号：</td>
									<td height="25">
						            <input name="couponSn" type="text" value="${couponSn}" style="width:150px"  id="couponSn"  disabled="true"/>
						            </td>
						         </tr>
						         <tr>
									<td height="25" align="right" class="tdgrey1">${HDtype}说明：</td>
									<td height="25">
						            <input name="HDdirection" type="text" value="${HDdescription}" style="width:150px"  id="HDdirection"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">活动优惠金额：</td>
									<td height="25">
						            <input name="orderActivity" type="text" value="${orderActivity}" style="width:160px"  id="orderActivity"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">活动号：</td>
									<td height="25">
						            <input name="ActivitySn" type="text" value="${ActivitySn}" style="width:150px"  id="ActivitySn"  disabled="true"/>
						            </td>
						         </tr>
				              </table>
				       </fieldset>
				     
				       </td>
	             </tr>
	             </table>
	             
	            <table> 
	              <tr> <td>
	             <div style="display: ${FCountry}">
				<fieldset style="margin-left: 10px; margin-right: 10px;">
					<legend>
						<label>旅游目的地</label>
					</legend>
					<table width="100%" border="1" cellpadding="1" cellspacing="0"
						bordercolor="#eeeeee">
						<tr>
							<td height="25" align="right" width="200"><input
								id="GDestination" type="button" value="*目的地"
								onclick="GetDestination('${productId}','${company}');" /></td>
							<td><span id="tDestination">${FDestination}
							</span></td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div style="display: ${XCountry}">
				<fieldset style="margin-left: 10px; margin-right: 10px;">
					<legend>
						<label>旅游目的地</label>
					</legend>
					<table width="100%" border="1" cellpadding="1" cellspacing="0"
						bordercolor="#eeeeee">
						<tr>
							<td width="400px" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*目的地：&nbsp;
								<z:select style="width:150px;" id="destination" name="destination" value="" > ${@destinationCountry}</z:select>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			</table>
			 </td>
			</tr>
 	</z:init>
	            <z:init method="com.wangjin.cms.orders.QueryOrders.orderInqueryApp2">
	                  <fieldset>
		                    <table width="100%" cellpadding="2" cellspacing="0" >
								<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 投保人信息</td></tr>
								<tr>
									<td height="25" align="right" class="tdgrey1">姓名：</td>
									<td height="25">
						            <input name="applicantName" type="text" value="${applicantName}" style="width:150px" maxlength="50" verify="投保人姓名|NotNull&&UFO&&LEN>2" id="applicantName" />
						            </td>
						            <td height="25" align="right" class="tdgrey1">证件类型：</td>
									<td height="25">
									<z:select name="applicantIdentityTypeName" id="applicantIdentityTypeName" style="width:150px;" >${applicantIdentityTypeName}</z:select>
						            </td>
						            <td height="25" align="right" class="tdgrey1">证件号码：</td>
									<td height="25">
						            <input name="applicantIdentityId" type="text" value="${applicantIdentityId}" style="width:150px" maxlength="64" id="applicantIdentityId" verify="投保人证件号码|NotNull" />
						            </td>
						         </tr>
						         <tr>
						            <td height="25" align="right" class="tdgrey1">英文名：</td>
									<td height="25">
						            <input name="applicantEnName" type="text" value="${applicantEnName}" style="width:150px" maxlength="50" verify="投保人英文名|UFO&&LEN>2" id="applicantEnName"  />
						            </td>
									<td height="25" align="right" class="tdgrey1">性别：</td>
									<td height="25">
									<z:select name="applicantSexName" id="applicantSexName" style="width:150px;" >${applicantSexName}</z:select>
						            </td>
						            <td height="25" align="right" class="tdgrey1">出生日期：</td>
									<td height="25">
						            <input name="applicantBirthday" type="text" value="${applicantBirthday}" style="width:150px"  id="applicantBirthday" ztype="date" verify="投保人出生日期|NotNull&&Date&&APPAGE" />
						            </td>
						            
						         </tr>
						         <tr>
						            <td height="25" align="right" class="tdgrey1">手机号码：</td>
									<td height="25">
						            <input name="applicantMobile" type="text" value="${applicantMobile}" style="width:150px" maxlength="11" id="applicantMobile" verify="投保人手机号码|NotNull&&PHONE" />
						            </td>
						            <td height="25" align="right" class="tdgrey1">电子邮箱：</td>
									<td height="25">
						            <input name="applicantMail" type="text" value="${applicantMail}" style="width:150px"  id="applicantMail" verify="投保人电子邮箱|NotNull&&Email" />
						            <input type="hidden" id="applicantId" name="applicantId" value="${applicantId}" />
						            </td>
						            
						         </tr>
				              </table>
				       </fieldset>
				      </z:init>
	             <table width="100%" cellpadding="2" cellspacing="0" >
	             <tr>
		              <td>
		            	   <z:tbutton onClick="policyChange();"  id="policyChange">
		            	     <img src="../Icons/icon021a4.gif" width="20" height="20"/>变更
		            	   </z:tbutton>
		            	</td>
		          </tr>
	             </table>
	             <z:init method="com.wangjin.cms.orders.QueryOrders.initDialog2">
	             <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 保单基本信息</td>
		          </tr>
				<tr>
					<td style="padding:8px 10px;">
					</td>
				</tr>
				<tr>
					<td >
					<z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.orderInquery2" >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="300px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="50" style="text-align:center;"><b>关系</b></td>
								<td width="70" style="text-align:center;"><b>被保人姓名</b></td>
								<td width="70" style="text-align:center;"><b>英文名</b></td>
								<td width="30" style="text-align:center;"><b>性别</b></td>
								<td width="110" style="text-align:center;"><b>证件类型</b></td>
								<td width="130" style="text-align:center;"><b>证件号码</b></td>
								<td width="105" style="text-align:center;"><b>出生日期</b></td>
								<td width="95" style="text-align:center;"><b>手机号码</b></td>
								<td width="115" style="text-align:center;"><b>电子邮箱</b></td>
								<td width="90" style="text-align:center;"><b>保单号</b></td>
								<td style="display:none"></td>
							</tr>
							<tr >
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td><z:select name="recognizeeAppntRelation"  style="width:50px;" value="${recognizeeAppntRelation}" disabled="true">${@recognizeeAppntRelation}</z:select></td>
								<td><input name="recognizeeName" type="text" id="recognizeeName" 
									value="${recognizeeName}" size="10" maxlength="50" verify="被保人姓名|NotNull&&UFO&&LEN>2" disabled/></td>
								<td><input name="recognizeeEnName" type="text" id="recognizeeEnName"  
									value="${recognizeeEnName}" size="10" verify="被保人英文名|UFO&&LEN>2" disabled/></td>
								<td><z:select name="recognizeeSex"  style="width:30px;" value="${recognizeeSex}" disabled="true">${@recognizeeSex}</z:select></td>
								<td><z:select name="recognizeeIdentityType" style="width:110px;" value="${recognizeeIdentityType}" disabled="true">${@recognizeeIdentityType}</z:select></td>
								<td><input name="recognizeeIdentityId" type="text" id="recognizeeIdentityId" 
									value="${recognizeeIdentityId}" size="20" maxlength="64" verify="被保人证件号码|NotNull"  disabled/></td>
								<td><input name="recognizeeBirthday" type="text" id="${baodanId}" value="${recognizeeBirthday}" size="15" ztype="date" verify="被保人出生日期|NotNull&&Date&&AGE" onblur = "effchange2(this.value)" disabled/></td>
								<td><input name="recognizeeMobile" type="text" id="recognizeeMobile" 
									value="${recognizeeMobile}" size="15" maxlength="11" verify="被保人手机号码|PHONE" disabled/></td>
								<td><input name="recognizeeMail" type="text" id="recognizeeMail" 
									value="${recognizeeMail}" size="20" verify="被保人电子邮箱|Email" disabled/></td>
								<td title="${policyNo}">${policyNo}</td>
								<td style="display:none">${appFlag}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
				<tr>
					<td >
					 健康告知信息
					<z:datagrid id="dghealth" method="com.wangjin.cms.orders.QueryOrders.orderInquery3" >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="300px" >
							<tr ztype="head" class="dataTableHead">
								<td width="90" style="text-align:center;"><b>告知兰</b></td>
								<td width="10" style="text-align:center;"><b>选项</b></td>
								<td style="display:none"></td>
								<td style="display:none"></td>
							</tr>
							<tr >
								<td >${showinfo}</td>
								<td><z:select name="selectFlag"  style="width:50px;" value="${selectFlag}">${@healthyFlag}</z:select></td>
								<td style="display:none"><input name="healthid" type="text" id="healthid" 
									value="${healthid}" /></td>
								<td style="display:none"><input name="healthyinfoid" type="text" id="healthyinfoid" 
									value="${healthyinfoid}" /></td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
			</z:init>
	</form>
</body>
</html>