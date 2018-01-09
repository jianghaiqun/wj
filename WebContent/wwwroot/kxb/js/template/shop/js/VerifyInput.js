/**
* ------------------------------------------
* @make:胡博
* @version  1.0 
* @des：通用校验函数Ver2 比Ver1增加科学记数法校验，校验类型间可进行“与（&）”、“或（|）”运算
* ------------------------------------------
*/
var arrVerifyErrInfo = new Array();   //记录一个字段的校验错误信息

function verifyClass()
{
	this.verifyInput = verifyInput;
	this.verifyForm = verifyForm;
	this.verifyElement = verifyElement;
	this.verifyType = verifyType;
	this.verifyMustNull = verifyMustNull;
	this.verifyNotNull = verifyNotNull;
	this.verifyNumber = verifyNumber;
	this.verifyDate = verifyDate;
	this.verifyEmail = verifyEmail;
	this.verifyDecimal=verifyDecimal;
	this.verifyInteger = verifyInteger;
	this.verifyPStyle = verifyPStyle;
	this.verifyLength = verifyLength;
	this.verifyValue = verifyValue;
	this.verifyCode = verifyCode;
	this.verifyCheckDifferent = verifyCheckDifferent;
	this.verifyNotNaN = verifyNotNaN;
	this.verifyPartLen = verifyPartLen;
	this.verifyRealLength = verifyRealLength;
	this.verifyMoney = verifyMoney;
	this.verifyCodeSel = verifyCodeSel;
}

/**
 * 业务程序调用接口，如果通过校验返回true，否则返回false
 * @return
 */
function verifyInput()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志

	//遍历所有FORM
	 
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT 
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//元素校验属性verify不为NULL
			if (window.document.forms[formsNum].elements[elementsNum].verify !="undefined" && window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "")
			{
				//进行校验verifyElement
				if (!verifyElement(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value))
				{
					passFlag = false;
					break;
				}
			}
		}
		if (!passFlag) break;
	}

	return passFlag;
}

/**
 * 业务程序调用接口，以FORM为单位，如果通过校验返回true，否则返回false
 * @param formName
 * @return
 */
function verifyForm(formName)
{
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志

	//遍历FORM中的所有ELEMENT
	for (elementsNum=0; elementsNum<window.document.all(formName).elements.length; elementsNum++)
	{
		//元素校验属性verify不为NULL
		if (window.document.all(formName).elements[elementsNum].verify != null && window.document.all(formName).elements[elementsNum].verify != "")
		{
			//进行校验verifyElement
			if (!verifyElement(window.document.all(formName).elements[elementsNum].verify, window.document.all(formName).elements[elementsNum].value))
			{
				passFlag = false;
				break;
			}
		}
	}
	return passFlag;
}

//校验元素，strInfo为元素校验信息，strValue为元素值
function verifyElement(strInfo, strValue, boxName)
{
	 
    if(strValue == null)
    {
       strValue = "";
    } 
    var cBoxName = "";
    if(typeof(boxName)!="object"){
    	cBoxName = boxName;
    }else{
    	cBoxName = boxName.id;
    }
    var strboxName = cBoxName;

    var strValue = trim(strValue);	//清空空格
	 
	var passFlag = true;	//校验通过标志，true表示通过
	 
	var vName;	//校验字段名称
	 
	var vType;	//要进行的校验类型
	 
	var intIndex;	//运算符索引
	 
	var typeStack = new Array();	//一个字段的校验结果堆栈
	 
	var operStack = new Array();	//一个字段的校验计算符号堆栈，仅限于“与”，“或”计算
	 
	while (arrVerifyErrInfo != "")
	{
		//清空前一个字段的校验错误信息
		arrVerifyErrInfo.pop();
	}
	
	//分离出字段名称，兼容前一版本，故仍用“|”分隔
	vName = strInfo.substring(0, strInfo.indexOf("|"));
	strInfo = strInfo.substring(strInfo.indexOf("|") + 1);
	if(jQuery("#"+cBoxName).parent().children("font")){
		   jQuery("#"+cBoxName).parent().children("font").hide();
	}
	
	//拆分出校验类型，并进行校验，返回校验结果（通过TRUE，否FALSE），并入堆栈
	while (strInfo != "")
	{
		if (strInfo.indexOf("|") != -1 && strInfo.indexOf("&") != -1)
		{
			//存在两种运算
			intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("&"):strInfo.indexOf("|");
			vType = strInfo.substring(0, intIndex);
			typeStack.push(verifyType(vName, vType, strValue,strboxName));
			operStack.push(strInfo.substring(intIndex, intIndex + 1));
			strInfo = strInfo.substring(intIndex + 1);
		}
		else if (strInfo.indexOf("|") != -1 || strInfo.indexOf("&") != -1)
			{
				//只有一种运算
				intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("|"):strInfo.indexOf("&");
				vType = strInfo.substring(0, intIndex);
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
				operStack.push(strInfo.substring(intIndex, intIndex + 1));
				strInfo = strInfo.substring(intIndex + 1);
			}
			else
			{
				//无运算
				vType = strInfo;
				strInfo = "";
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
			}
	}

	passFlag = typeStack[0];
	//只有一个校验类型时
	for (var k=0; k<operStack.length; k++)
	{
		//有多个校验类型，进行运算
		if (operStack[k] == "|")
		{
			typeStack[k + 1] = typeStack[k] | typeStack[k + 1];
		}
		else if (operStack[k] == "&")
			{
				typeStack[k + 1] = typeStack[k] & typeStack[k + 1];
			}
			else
			{
				alert("校验参数设置有误");
			}
		passFlag = typeStack[k + 1];
	}
	
	var strVerifyErrInfo = "\n";
	if (!passFlag)
	{
		while (arrVerifyErrInfo != "")
		{
			strVerifyErrInfo = strVerifyErrInfo + arrVerifyErrInfo.pop();
			while (arrVerifyErrInfo != "")
			{
				arrVerifyErrInfo.pop();	
			}		
		} 
		if(jQuery("#"+cBoxName).parent().children("label").next().is('i')){
			   jQuery("#"+cBoxName).parent().children("i").remove();
		}
		if(!jQuery("#"+cBoxName).parent().children("label").next().is('i')){
			   jQuery("#"+cBoxName).parent().children("label").after(jQuery("<i  class=\"yz_mes_error\" >"+strVerifyErrInfo+"</i>"));
		
			   //控制多被保人打开 
			   if(cBoxName!=""&&cBoxName!=null&&cBoxName!="showOutGoingParpose"){
				   jQuery("#"+cBoxName).parent().parent().parent().parent().find("dd").show();  
					//tObj.parent().parent().parent().parent().find("a").text("收起");    
				   jQuery("#"+cBoxName).parent().parent().parent().parent().find("a").removeClass("kg_jia");
				   jQuery("#"+cBoxName).parent().parent().parent().parent().parent().removeClass("bbr_bor");
				   jQuery("#"+cBoxName).parent().parent().parent().parent().find("a").each(function(){ 
					   var tID = jQuery(this).attr("id");
					   var tClass = jQuery(this).attr("class");
					   var tHref = jQuery(this).attr("href");
					   if((tID==null || tID=="")&&tClass!="windowClose_ loginWindowClose_"
						   && typeof(tHref) != "undefined" &&tHref.indexOf("traveltemplate")==-1){
						   jQuery(this).toggle(function(){  
							   jQuery(this).parent().siblings().hide(); 
							   jQuery(this).text("打开");
							   jQuery(this).addClass("kg_jia");
							   jQuery(this).parent().parent().addClass("bbr_bor");
							   if(tInsLastNum!=0){ 
								   jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val());
								   jQuery("#nametitle_"+(tInsNum)).html(jQuery("#recognizeeName_"+(tInsNum)).val()); 
							   }else{
								   jQuery("#ins_"+tInsNum).find(".bxr_num").html(tInsNum+1);
								   jQuery("#nametitle_"+(tInsNum)).html(jQuery("#recognizeeName_"+(tInsNum)).val()); 
							   }
						   },
						   function() { 
							   jQuery(this).parent().siblings().show();  
							   jQuery(this).text("收起");    
							   jQuery(this).removeClass("kg_jia");
							   jQuery(this).parent().parent().removeClass("bbr_bor"); 
							   
						   });
					   }
					}); 
			   } 
		}
		if(jQuery("#"+cBoxName).parent().children("i")){
			   jQuery("#"+cBoxName).parent().children("i").remove();
		}
		if(!jQuery("#"+cBoxName).parent().children("label").next().is('i')){
			  jQuery("#"+cBoxName).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">"+strVerifyErrInfo+"</i>"));
		}
	}else{ 
		if(jQuery("#"+cBoxName).parent().children("i")){
			   jQuery("#"+cBoxName).parent().children("i").remove();
		}
		if(!jQuery("#"+cBoxName).parent().children("label").next().is('i')){
			jQuery("#"+cBoxName).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
		}
	}
	
	return passFlag;
}
//modify in 2014-09-24 bug0001021,去掉投保录入页面元素onfocus效果
function verifyShowInf(strValue, boxName){
	if(jQuery("#"+boxName).parent().children("i")){
		   jQuery("#"+boxName).parent().children("i").remove();
	}
	if(strValue==null || strValue==""){
		jQuery("#"+boxName).parent().children("font").show();
	}
	return false;
}
function verifyType(vName, vType, strValue, boxName)
{
	var passFlag = true;
	
	  
	if (vType.toUpperCase() == "NULL") passFlag = verifyMustNull(vName, strValue);
	
	if (vType.toUpperCase() == "NOTNULL") passFlag = verifyNotNull(vName, strValue,boxName);

	if (vType.toUpperCase() == "NUM") passFlag = verifyNumber(vName, strValue);

	if (vType.toUpperCase() == "DATE") passFlag = verifyDate(vName, strValue);
	
	if (vType.toUpperCase() == "AGE") passFlag = verifyAge(vName, strValue);
	
	if (vType.toUpperCase() == "EMAIL") passFlag = verifyEmail(vName, strValue);
  
    if (vType.toUpperCase() == "DECIMAL") passFlag = verifyDecimal(vName, strValue);
	if (vType.toUpperCase() == "INT") passFlag = verifyInteger(vName, strValue);
	
	if (vType.toUpperCase() == "TELEPHONE" ) passFlag = verifyPStyle(vName, strValue);

	if (vType.toUpperCase() == "CHECKDIFFERENT") passFlag = verifyCheckDifferent(vName, strValue, boxName);
	
	if (vType.toUpperCase().indexOf("FLOAT") == 0 ) passFlag =  verifyPartLen(vName, strValue, vType);
	
	//不能随意修改约定“LEN”
	if (vType.toUpperCase().indexOf("LEN") == 0) passFlag = verifyLength(vName, strValue, vType);

	//不能随意修改电话“PHONE:”
	if (vType.toUpperCase().indexOf("PHONE") == 0) passFlag = verifyPhone(vName, strValue, boxName);
	
	//不能随意修改电话“MOBILENO:”
	if (vType.toUpperCase().indexOf("MOBILENO") == 0) passFlag = verifyMobileNo(vName, strValue, boxName);
	
	//不能随意修改约定“VALUE”
	if (vType.toUpperCase().indexOf("VALUE") == 0) passFlag = (verifyNumber(vName, strValue) && verifyValue(vName, strValue, vType));

	//不能随意修改约定“CODE:”
	if (vType.toUpperCase().indexOf("CODE:") == 0) passFlag = verifyCode(vName, strValue, vType,boxName);

	//不能随意修改约定“CODE:”
	if (vType.toUpperCase() == "ZIPCODE") passFlag = (verifyLength(vName, strValue, "len=6") && verifyNumber(vName, strValue)&& verifyZipCode(vName, strValue));
	
	//不能随意修改约定“CODE:”
	if (vType.toUpperCase() == "NOTNAN") passFlag = verifyNotNaN(vName, strValue);
	
	//不能随意修改预定的“RLEN:”
	if (vType.toUpperCase().indexOf("RLEN") == 0) passFlag = verifyRealLength(vName, strValue, vType);

	//不能随意修改预定的“MONEY:”
	if (vType.toUpperCase().indexOf("MONEY") == 0) passFlag = verifyMoney(vName, strValue, vType);
	
	//代码选择校验 add by zx
	if (vType.toUpperCase() == "CODESEL") passFlag = verifyCodeSel(vName, strValue, vType , boxName);	
	
	if (vType.toUpperCase() == "URL") passFlag = verifyURL(vName, strValue);
	//单引号，双引号校验 T add
	if (vType.toUpperCase() == "UFO") passFlag = verifyUFO(vName, strValue);
	//校验汉字
	if (vType.toUpperCase() == "CHI") passFlag = verifyCHI(vName, strValue);
	//校验地区
	if (vType.toUpperCase() == "AREAFORMAT") passFlag = verifyAREA(vName, strValue);
	//校验地区
	if (vType.toUpperCase() == "OCCUFORMAT") passFlag = verifyOCCU(vName, strValue);
	//与投保人关系
	if (vType.toUpperCase() == "APPRELATION") passFlag = verifyAPPRELATION(vName, strValue);
	//与被保人关系
	if (vType.toUpperCase() == "APPRELATION2") passFlag = verifyAPPRELATION2(vName, strValue);
	//受益人受益比例
	if (vType.toUpperCase() == "BENEFIT") passFlag = verifyBenefit(vName, strValue,boxName);
	//投保人年龄 18岁
	if (vType.toUpperCase() == "APPAGE") passFlag = verifyAppAge(vName, strValue);
	//受益人性别
	if (vType.toUpperCase() == "SYSEX") passFlag = verifySYSex(vName, strValue,boxName);
	//联系地址
	if (vType.toUpperCase() == "ADDRESS") passFlag = verifyADDRESS(vName, strValue);
	//起飞日期
	if (vType.toUpperCase() == "FIGHTTIME") passFlag = verifyFightTime(vName, strValue);
	//旅游目的地
	if (vType.toUpperCase() == "TRAVEL") passFlag = verifyTRAVEL(vName, strValue);
	//房龄校验
	if (vType.toUpperCase() == "HOURSEAGE") passFlag = verifyHOURSEAGE(vName, strValue);
	//房屋类型
	if (vType.toUpperCase() == "HOURSETYPE") passFlag = verifyHOURSETYPE(vName, strValue);
	//字母校验
	if (vType.toUpperCase() == "ENG") passFlag = verifyEnglish(vName, strValue);
	//字母校验
	if (vType.toUpperCase() == "IDCARD") passFlag = verifyIdCard(vName, strValue);
	if (vType.toUpperCase() == "IDTYPE") passFlag = verifyIDCardType(vName, strValue);
	//校验下拉框选择值
	if (vType.toUpperCase() == "CODETYPE") passFlag = verifyCodeType(vName, strValue, vType,boxName);
	
	//校验生日并保费试算---调用shop.js 中方法
	if (vType.toUpperCase() == "CALLPREM") passFlag = verifyCallPrem(boxName);
	//校验数据唯一
	if (vType.toUpperCase().indexOf("ONLYONE") == 0) passFlag = verifyOnlyOne(vName, strValue, vType,boxName);
	//excel导入身份证校验 & 赋值
	if (vType.toUpperCase() == "IDCARDEXCEL") passFlag = verifyIdCardExcel(vName, strValue, vType,boxName);
	// 推荐邮箱校验
	if (vType.toUpperCase() == "RECOEMAIL") passFlag = verifyRecommEmail(vName, strValue);
	// 开户银行
	if (vType.toUpperCase() == "BANKCODE") passFlag = verifyBankCode(vName, strValue);
	// 银行卡号
	if (vType.toUpperCase() == "BANKNO") passFlag = verifyBankNO(vName, strValue);
	// 确认银行卡号
	if (vType.toUpperCase() == "REBANKNO") passFlag = verifyBankNO(vName, strValue) && verifyReBankNO(vName, strValue, boxName);

	return passFlag;
}
function verifyIdCardExcel(vName,strValue,vType,cboxName){
	strValue = trim(strValue); 
	if (strValue == "")
	{   
		arrVerifyErrInfo.push("请输入"+vName + "\n");
		return false;
	}
	var id_index = cboxName.split("_")[1];
	var idtype_value = jQuery("#sdrecognizeeTypeName_"+id_index).val();
	var birthday_value = jQuery("#sdrecognizeeBirthday_"+id_index).val();
	if(idtype_value.indexOf("身份证")!=-1){
		if(!checkId(strValue, "")){arrVerifyErrInfo.push("请输入正确的身份证号！");return false;}
		var year = strValue.substring(6, 10);      
		var month = strValue.substring(10, 12);      
		var day = strValue.substring(12, 14);
		var birthday_value_count = year+"-"+month+"-"+day;
		if(trim(birthday_value_count) != trim(birthday_value)){arrVerifyErrInfo.push("身份证计算的出生日期与输入的出生日不一致！");return false;}
	}
    return true;
}
function verifyOnlyOne(vName,strValue,vType,cboxName){

	var tFlag = false;
	if(vType.indexOf("=")!=-1){
		var compare_val = vType.split("=")[1];
		if(strValue==compare_val){
			var id_name = cboxName.split("_")[0];
			jQuery("select[id^='"+id_name+"']").each(function(){
				var id = jQuery(this).attr("id");
				if(id!=cboxName &&  jQuery(this).val()==strValue){
					arrVerifyErrInfo.push(vName+"为“"+compare_val+"”的不能多于1个！ ");
					tFlag = true;
					return;
				}
			});
		}
	}
	if(tFlag) return false;
    return true;
}

function verifyCodeType(vName,strValue,vType,cboxName){
	jQuery.ajax({
		url: sinosoft.base+"/shop/order_config_new!ajaxValidateCode.action?supplierCode2=excel&codeType="+vType+"&codeName="+encodeURIComponent(encodeURIComponent(strValue)),		
		dataType: "json",
		async: false,
		success: function(data) {
			var appCode=data.codeNameFlag;
			    if(appCode!="Y"){
			    	arrVerifyErrInfo.push("请选择正确的"+vName+"！");
			    	return false;
			    }
			}
		});
    return true;
}
function verifyIdCard(vName, strValue){
	 
    var SystemDate=new Date();
 	var year=SystemDate.getFullYear();
 	var month=SystemDate.getMonth()+1;
 	var day=SystemDate.getDate();
 	var yyyy; //年
 	var mm; //月
 	var dd; //日
 	var birthday; //生日
 	var sex; //性别
 	var id=strValue;
 	var id_length=id.length;
 	if (id_length==0)
 	{
 		arrVerifyErrInfo.push("请输入身份证号码!");
 		return false;
 	}
 	if (id_length!=15 && id_length!=18)
 	{
 		arrVerifyErrInfo.push("身份证号长度应为18位！");
 		return false;
 	}
 	if (id_length==15)
 	{
 		for(var i =0 ;i<id_length;i++)
 		{
 			if(isNaN(id.charAt(i)))
 			{
 				arrVerifyErrInfo.push("15位身份证号中不能有字符！");
 				return false;
 			}
 		}
 		yyyy="19"+id.substring(6,8);
 		mm=id.substring(8,10);
 		dd=id.substring(10,12);
 		if (mm>12 || mm<=0)
 		{
 			arrVerifyErrInfo.push("身份证号月份非法！");
 			return false;
 		}
 		if (dd>31 || dd<=0)
 		{
 			arrVerifyErrInfo.push("身份证号日期非法！");
 			return false;
 		}
 		//4,6,9,11月份日期不能超过30
 		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
 		{
 			arrVerifyErrInfo.push("身份证号日期非法！");
 			return false;
 		}
 		//判断2月份
 		if(mm==2)
 		{
 			if(LeapYear(yyyy))
 			{
 				if(dd>29)
 				{
 					arrVerifyErrInfo.push("身份证号日期非法！");
 					return false;
 				}
 			}
 			else
 			{
 				if(dd>28)
 				{
 					arrVerifyErrInfo.push("身份证号日期非法！");
 					return false;
 				}
 			}
 		}
 	}
 	else
 	{
 		for(var i =0 ;i<id_length-1;i++)
 		{
 			if(isNaN(id.charAt(i)))
 			{
 				arrVerifyErrInfo.push("身份证号中前17位中不能有字符！");
 				return false;
 			}
 		}
 		if(isNaN(id.charAt(17))&& id.charAt(17) !="X" && id.charAt(17) !="x" )
 		{
 			arrVerifyErrInfo.push("身份证校验错误，请认真检查！");
 			return false;
 		}
 		if (id.indexOf("X") > 0 && id.indexOf("X")!=17 || id.indexOf("x")>0 && id.indexOf("x")!=17)
 		{
 			arrVerifyErrInfo.push("身份证中\"X\"输入位置不正确！");
 			return false;
 		}
 		yyyy=id.substring(6,10);
 		if (yyyy>year || yyyy<1900)
 		{
 			arrVerifyErrInfo.push("身份证号年度非法！");
 			return false;
 		}
 		mm=id.substring(10,12);
 		if (mm>12 || mm<=0)
 		{
 			arrVerifyErrInfo.push("身份证号月份非法！");
 			return false;
 		}
 		if(yyyy==year&&mm>month)
 		{
 			arrVerifyErrInfo.push("身份证号月份非法！");
 			return false;
 		}
 		dd=id.substring(12,14);
 		if (dd>31 || dd<=0)
 		{
 			arrVerifyErrInfo.push("身份证号日期非法！");
 			return false;
 		}
 		//4,6,9,11月份日期不能超过30
 		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
 		{
 			arrVerifyErrInfo.push("身份证号日期非法！");
 			return false;
 		}
 		//判断2月份
 		if(mm==2)
 		{
 			if(LeapYear(yyyy))
 			{
 				if(dd>29)
 				{
 					arrVerifyErrInfo.push("身份证号日期非法！");
 					return false;
 				}
 			}
 			else
 			{
 				if(dd>28)
 				{
 					arrVerifyErrInfo.push("身份证号日期非法！");
 					return false;
 				}
 			}
 		}
 		if(yyyy==year&&mm==month&&dd>day)
 		{
 			arrVerifyErrInfo.push("身份证号日期非法！");
 			return false;
 		}
 		if (id.charAt(17)=="x" || id.charAt(17)=="X")
 		{
 			if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id))
 			{
 				arrVerifyErrInfo.push("身份证校验错误，请认真检查！");
 				return false;
 			}
 		}
 		else
 		{
 			if (id.charAt(17)!=GetVerifyBit(id))
 			{
 				arrVerifyErrInfo.push("身份证校验错误，请认真检查！");
 				return false;
 			}
 		}
 	}
 	return true;
    

}
function verifyEnglish(vName, strValue){
	var i = /^(?!_)([A-Za-z .]+)$/;
    if(!strValue.match(i)){
    	arrVerifyErrInfo.push("请输入正确的英文名");
    	return false;	
    } 
    return true;

}
function verifyHOURSETYPE(vName, strValue){
	if(strValue=="-1"){
		 arrVerifyErrInfo.push("请选择房屋类型\n");
		 return false;  
	 }
	  return true;
}
function verifyHOURSEAGE(vName,strValue){
	 if(strValue=="-1"){
		 arrVerifyErrInfo.push("请选择房屋年龄\n");
 		 return false;  
	 }
	  return true;
}
//返回true表示通过校验，返回false表示不通过，数据违法
//必须为数值校验
function verifyNotNaN(vName, strValue) {
	if (strValue != "" && isNaN(strValue))
	{
		arrVerifyErrInfo.push(vName + " 不是有效的数字!\n");
		return false;
	}
	return true;
}

//必须为空校验
function verifyMustNull(vName, strValue) {
	if (strValue != "")
	{
		arrVerifyErrInfo.push(vName + " 必须为空!\n");
		return false;
	}
	return true;
}

//不能为空校验
function verifyNotNull(vName, strValue,cboxName)
{
	strValue = trim(strValue); 
	if (strValue == "")
	{   if(cboxName.indexOf("showOutGoingParpose")!=-1){
		   art.dialog.alert("请选择旅游目的地");
		   arrVerifyErrInfo.push("请选择"+vName + "\n");
	    }else{
	       arrVerifyErrInfo.push("请输入"+vName + "\n");
	    } 
		return false;
	}
	return true;
}
//旅游目的地校验
function verifyTRAVEL(vName, strValue,cboxName)
{
	var comCode = companyCode; 
	var r = /\s+/g; 
	var s = strValue.replace(r, " "); 
	if(comCode=="2071"){
		var len = s.length;  
		if(len>=51){
		    arrVerifyErrInfo.push("旅游目的地长度应小于等于50");
		    return false;
		}
	}else{ 
		var arr = s.split(" "); 
		var alen = arr.length;  
		var tlen=11;
		if(s.indexOf("申根国家")!=-1){
			tlen=12;
		}
		if(alen>=tlen){
		    arrVerifyErrInfo.push("旅游目的地国家数应小于等于10");
		    return false;
		}
	} 
	return true;
}


//数字类型校验
function verifyNumber(vName, strValue)
{ 
	if (strValue != "" && !isNumeric(strValue))
	{
		arrVerifyErrInfo.push(vName + " 不是有效的数字\n");
		return false;
	}
	return true;
}


//日期类型校验
function verifyAge(vName, strValue) {
	if (!CheckAge(strValue))
	{
		arrVerifyErrInfo.push("被保人年龄应在正常出生满1天-150周岁\n");
		return false;
	}
	if(!validateBirthdayExcel(strValue))
	{
		arrVerifyErrInfo.push("此年龄不能购买本产品!");
		return false;
	}
	return true;
}
//投保人年龄校验
function verifyAppAge(vName, strValue) { 
	if (!CheckAppAge(strValue))
	{
		arrVerifyErrInfo.push("太年轻啦~18周岁以上的成年人才能当投保人呢");
		jQuery("#"+vName).val("");
		return false;
	}
	return true;
}
//年龄校验
function verifyDate(vName, strValue) {
	if (strValue != "" && !isDate(strValue))
	{
		arrVerifyErrInfo.push("请输入正确的"+vName + "\n");
		return false;
	}
	return true;
}

function verifyRecommEmail (vName, strValue) 
{
	if (strValue != "")
	{
		var s=strValue;
		var i = 1;
		var len = s.length;
		if (len > 50)
		{
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false;
		}
		var pos1 = s.indexOf("@");
		var pos2 = s.indexOf(".");
		var pos3 = s.lastIndexOf("@");
		var pos4 = s.lastIndexOf(".");

		if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len))
		{
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false;
		}
		else
		{
			if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)|| ( pos1 != pos3 )|| ( pos4 < pos3 ) )
			{
				arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
				return false;
    		}
    	}
		if ( !isCharsInBag( s, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@"))
		{
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false;
		}
		var badChar ="><,[]{}?/+=|\:;!#$%^&()`";
		if (isCharsInBag(s,badChar))
		{
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false;
		}
	}
	return true;
}

function verifyEmail (vName, strValue)
{
	if (strValue != "")
	{
		var s=strValue;
		var i = 1;
		var len = s.length;
		if (len > 50)
		{
			arrVerifyErrInfo.push("邮箱地址的格式一定要填写正确哟（你的帐号@服务器地址 例如: kf@kaixinbao.com）");
			return false;
		}
		var pos1 = s.indexOf("@");
		var pos2 = s.indexOf(".");
		var pos3 = s.lastIndexOf("@");
		var pos4 = s.lastIndexOf(".");

		if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len))
		{
			arrVerifyErrInfo.push("邮箱地址的格式一定要填写正确哟（你的帐号@服务器地址 例如: kf@kaixinbao.com）");
			return false;
		}
		else
		{
			if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)|| ( pos1 != pos3 )|| ( pos4 < pos3 ) )
			{
				arrVerifyErrInfo.push("邮箱地址的格式一定要填写正确哟（你的帐号@服务器地址 例如: kf@kaixinbao.com）");
				return false;
    		}
    	}
		if ( !isCharsInBag( s, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@"))
		{
			arrVerifyErrInfo.push("邮箱地址的格式一定要填写正确哟（你的帐号@服务器地址 例如: kf@kaixinbao.com）");
			return false;
		}
		var badChar ="><,[]{}?/+=|\:;!#$%^&()`";
		if (isCharsInBag(s,badChar))
		{
			arrVerifyErrInfo.push("邮箱地址的格式一定要填写正确哟（你的帐号@服务器地址 例如: kf@kaixinbao.com）");
			return false;
		}
	}
	return true;
}
//在0到1之间的数值(0,1]
function verifyDecimal(vName, strValue) {
        if (strValue != "" &&(parseFloat(strValue)<=0||parseFloat(strValue)>1) )
        {
		arrVerifyErrInfo.push(vName + " 不是0到1之间的小数!\n");
		return false;
	}
	return true;        
}
//整数类型校验
function verifyInteger(vName, strValue) {
	if (strValue != "" && !isInteger(strValue))
	{
		arrVerifyErrInfo.push(vName + " 只能是整数数值，不能包含其它字符!\n");
		return false;
	}
	return true;
}

function verifyPStyle(vName, strValue) {

	var patten = /(((\([0-9]{3,4}\)){1}|([0-9]{3,4}\-){1})([1-9]{1}[0-9]{6,7})(\-\d{3,4})*)|(0*1[0-9]{10})$/;	
    if (strValue != "" && !patten.test(strValue) )
    {
		arrVerifyErrInfo.push(vName + "不是有效的电话号码!\n正确的电话格式包括："
							+"\n(0574)12345678"
							+"\n(0574)1234567"
							+"\n(574)12345678"
							+"\n(574)1234567"
							+"\n0574-12345678"
							+"\n0574-12345678-1234"
							+"\n13012345678"
							+"\n013012345678");
		return false;
	}
	return true;        
}

//校验数字的小数点前面整数部分的长度
function verifyPartLen(vName, strValue, vType){

	var indexNega = strValue.indexOf('-');  //负号位置 负数为0,非负数为-1
	var indexPoint = strValue.indexOf('.'); //小数点位置 没有小数点时取数字的长度
	var intFlag = indexPoint;
	if('-1'==indexPoint){
		indexPoint = strValue.length;
	}
	
	var subValue = strValue.substring(parseInt(indexNega)+1,indexPoint); //数字整数部分
	
	var key = vType.substring(6);
	var split = key.split(',');
	var pre = split[0];
	var later = split[1];
	var flag = true;
	
	if('-1'==intFlag){
		var len = parseInt(pre)-parseInt(later);
		if(!verifyLength(vName, subValue, "len<="+len)){
			while (arrVerifyErrInfo != "")
			{
				//清空前一个字段的校验错误信息
				arrVerifyErrInfo.pop();
			}
			arrVerifyErrInfo.push(vName + "是整数时长度需要小于等于" + len + "\n");
			return false;			
		}
	}else{
		var len = parseInt(pre)-parseInt(later)-1;
		flag = verifyLength(vName + "整数部分", subValue, "len<="+len);
	}
	
	return flag;
}

/**
 * 校验金额的限制
 * 目前只处理保留小数的情况
 * @param vName
 * @param strValue
 * @param vType
 * @return
 */
function verifyMoney(vName, strValue, vType) 
{
	var strOperLen = vType.substring(6);	//截取出操作符和值
	strValue = strValue.trim();
	
	var otherValue;  //小数点以后
	
	if(strValue == "")
	{
	    return true;
	}
    if(strValue.substring(0, 1) == "-")
    {
      strValue = strValue.substring(1);
    }
	var array = strOperLen.split("-");

	if(array.length > 2)
	{
		arrVerifyErrInfo.push(vName + " 的校验格式不正确!\n");
		return false;
	}
	var p = array[0];
	var s = array[1];
	
    var indexPoint = strValue.indexOf('.');
    if('-1' != indexPoint)
    {
      otherValue = strValue.substring(indexPoint+1);
      if(otherValue.length > s)
      {
			arrVerifyErrInfo.push(vName + "小数部分的长度需要小于等于" + s + "!\n");
			return false;
      }     
      strValue = strValue.substring(0, indexPoint);
    }
    var len = p-s;
    if(strValue.length > len)
    {
		arrVerifyErrInfo.push(vName + "整数部分的长度需要小于等于" + len + "!\n");
		return false;
    }
  	return true;
}

/**
 * 输入长度校验
 * @param vName
 * @param strValue
 * @param vType
 * @return
 */
function verifyLength(vName, strValue, vType) {
	var oper;
	var len;
	var lenStr = 0;
	var comCode = "";
	if(typeof(companyCode) != "undefined" && companyCode!=null && companyCode!=""){
		comCode = companyCode;
	}
	
	var strOperLen = vType.substring(3);	//截取出操作符和值

	if (strValue == "") return true;

	if (isNaN(parseInt(strOperLen.substring(1))))
	{
		oper = strOperLen.substring(0, 2);	//当为">=", "<="时
		len = strOperLen.substring(2);
	}
	else
	{
		oper = strOperLen.substring(0, 1);	//当为"=", "<", ">"时
		len = strOperLen.substring(1);
	}
	for (var i=0; i<strValue.length; i++) { 
		   var c = strValue.charCodeAt(i); 
		   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
			   lenStr = lenStr + 1;
		   }
		   else { 
			   if(comCode=="2011"&&"起飞地点"==vName){
				   lenStr = lenStr + 3;
			   }else{
			       lenStr = lenStr + 2;
			   }
		   } 
	   }
	if(comCode=="2011"&&"起飞地点"==vName){
			len=32;
	}
	switch (oper)
	{
		case "=" :
			if (lenStr != parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "的长度需要等于" + len + "\n");
				return false;
			}
			break;
		case ">" :
			if (lenStr <= parseInt(len))
			{
				arrVerifyErrInfo.push("这个"+vName + "太短了吧，好像没写全呀\n");
				return false;
			}
			break;
		case "<" :
			if (lenStr >= parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "的长度需要小于" + len + "\n");
				return false;
			}
			break;
		case ">=" :
			if (lenStr < parseInt(len))
			{
				arrVerifyErrInfo.push("这个"+vName + "太短了吧，好像没写全呀\n");
				return false;
			}
			break;
		case "<=" :
			if (lenStr > parseInt(len))
			{
				if(comCode=="2011"){
					arrVerifyErrInfo.push(vName + "的长度需要小于等于" + len + "，一个汉字是3个长度\n");
					return false;
				}else{
					arrVerifyErrInfo.push(vName + "的长度需要小于等于" + len + "\n");
					return false;
				}
			}
			break;
	}
	return true;
}


 function verifyURL(vName, strValue){ 

	  if(strValue != ""){
        var strRegex = "^((([hH]{1}[tT]{2}[pP]{1}[sS]{1})|([hH]{1}[tT]{2}[pP]{1})|([fF]{1}[tT]{2}[pP]{1})|([rR]{1}[tT]{2}[sS]{1}[pP]{1})|([mM]{2}[sS]{1}))?://)"  
        + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" // ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
       + "|" // 允许IP和DOMAIN（域名）
       + "([0-9a-zA-Z_!~*'()-]+\.)*" // 域名- www.
       + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\." // 二级域名
       + "[a-zA-Z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
        var re=new RegExp(strRegex);  
        
        if (re.test(strValue)){ 
        	return true;
       }else{  
    		arrVerifyErrInfo.push(vName + "的格式不正确"+ "!\n");
    		 return false;  
        } 
	  }
	  return true;
   } 
 function verifyUFO(vName, strValue){ 

	  if(strValue != ""){
       var strRegex =  /^[^"',，。!@#$%&*()0-9]*$/ ; //       /[^0-9a-zA-Z\u4E00-\u9FA5]/
       var re=new RegExp(strRegex);         
       if (re.test(strValue)){ 
       	return true;
      }else{  
   		arrVerifyErrInfo.push(vName + "不能有特殊字符哟，请仔细检查一下"+ "!\n");
   		 return false;  
       } 
	  }
	  return true;
  }
 /**
  * 校验汉字
  */
 function verifyCHI(vName, strValue){ 

	   
	  if(strValue != ""){
      var strRegex =  /^[\u4e00-\u9fa5]+$/i ; 
      var re=new RegExp(strRegex);         
      if (re.test(strValue)){ 
      	return true;
     }else{  
  		arrVerifyErrInfo.push("请输入正确的"+vName +"\n");
  		 return false;  
      } 
	  }
	  return true;
 } 
 function verifyAREA(vName, strValue){ 

	 if(strValue.indexOf("-1")!=-1){
		 arrVerifyErrInfo.push("了解您的所在地区，为您提供更精准服务\n");
 		 return false;  
	 }
	  return true;
} 
 function verifyOCCU(vName, strValue){ 
 
	 if(strValue.indexOf("-1")!=-1){
		 arrVerifyErrInfo.push("请填写您的职业，为您提供更精准的服务\n");
 		 return false;  
	 }
	  return true;
} 
function verifyIDCardType(vName, strValue){ 
	 
	 if(strValue.indexOf("-1")!=-1){
		 arrVerifyErrInfo.push("请填写您的证件类型，为您提供更精准的服务\n");
 		 return false;  
	 }
	  return true;
}
 function verifyAPPRELATION(vName, strValue){ 
	 
	 if(strValue.indexOf("-1")!=-1){
		 arrVerifyErrInfo.push("请选择您与投保人的关系吧\n"); 
 		 return false;  
	 }
	  return true;
}
/*
*选择受益人与被保人关系
*/
 function verifyAPPRELATION2(vName, strValue){ 
	 
	 if(strValue.indexOf("-1")!=-1){
		 arrVerifyErrInfo.push("请选择您与被保人的关系吧\n"); 
 		 return false;  
	 }
	  return true;
} 
/*受益人性别*/
function verifySYSex(vName,strValue,boxName){
   var sexnum = boxName;

   var cord_id = sexnum.substr(sexnum.length-1,1);
   var relValue = jQuery('#recognizeeRelation2_'+cord_id).find("option:selected").text();//父子父女，母子母女


    if (jQuery('#RdoMaleSex2_'+cord_id).attr('checked')) {
        if (relValue.indexOf('母女') != -1) {
        	arrVerifyErrInfo.push("受益人关系与被保人性别不对应\n");
            return false;
        }
    }
    else if (jQuery('#RdoFemaleSex2_'+cord_id).attr('checked')) {
        if (relValue.indexOf('父子') != -1) {
        	 arrVerifyErrInfo.push("受益人关系与被保人性别不对应\n");
            return false;
        }
    }
return true;

}
 /*
 *受益人比例
 */
 function verifyBenefit(vName, strValue,boxName){

    var text=trim(jQuery('#'+boxName).val());

    if(text.length==0)
    {
    	arrVerifyErrInfo.push("受益比例不能为空\n"); 
        return false;
    }
    else if(issyInteger(text)==false)
    {
    	arrVerifyErrInfo.push("受益比例只能为大于0且小于等于100之间的整数\n"); 
        return false;
    }
     return true;
 }
// 开户银行校验
function verifyBankCode(vName, strValue){ 
	 if(strValue.indexOf("-1")!=-1){
		 arrVerifyErrInfo.push("请选择您的开户银行\n"); 
 		 return false;  
	 }
	  return true;
}
// 银行卡号校验(全数字)
function verifyBankNO(vName,strValue,boxName){	
	var reg = new RegExp("^[0-9]*$");
	if (strValue=="" || !reg.test(strValue)){
		arrVerifyErrInfo.push("请填写正确的银行卡号\n");
		return false;
	}
	return true;
}
//确认银行卡号校验
function verifyReBankNO(vName,strValue,boxName){
	if (strValue != jQuery('#'+boxName.substring(2)).val()){
		arrVerifyErrInfo.push("两次输入的银行卡号不一致\n");
		return false;
	}
	return true;
}

 //检查输入对象的值是否符合整数格式，且在0-100之间
function issyInteger(str) {
    var regu = /^[-]{0,1}[0-9]{1,}$/;
    if (regu.test(str)) {
        if (str <= 0 || str > 100) {
            return false;
        }
        else {
            return true;
        }
    }
    else {
        return false;
    }
}

/**
 * 输入真实长度校验
 * @param vName
 * @param strValue
 * @param vType
 * @return
 */
function verifyRealLength(vName, strValue, vType) {
	var oper;
	var len;
	var strOperLen = vType.substring(4);	//截取出操作符和值
	if (strValue == "") return true;
	
	var indexPoint = strValue.indexOf('.');
	if('-1'!=indexPoint)
	{
		var tlen = strValue.length;
		for(var i=tlen;i>indexPoint+1;i--)
		{
			if(strValue.substring(i-1,i)=="0"||strValue.substring(i-1,i)==" ")
				tlen=tlen-1;	
			else break;
		}
		if(tlen == (indexPoint+1))
		 tlen=indexPoint;
		 strValue = strValue.substring(0,tlen);	
	}
	
	if (isNaN(parseInt(strOperLen.substring(1))))
	{
		oper = strOperLen.substring(0, 2);	//当为">=", "<="时
		len = strOperLen.substring(2);
	}
	else
	{
		oper = strOperLen.substring(0, 1);	//当为"=", "<", ">"时
		len = strOperLen.substring(1);
	}

	switch (oper)
	{
		case "=" :
			if (strValue.length != parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "的长度需要等于" + len + "!\n");
				return false;
			}
			break;
		case ">" :
			if (strValue.length <= parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "的长度需要大于" + len + "!\n");
				return false;
			}
			break;
		case "<" :
			if (strValue.length >= parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "的长度需要小于" + len + "!\n");
				return false;
			}
			break;
		case ">=" :
			if (strValue.length < parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "的长度需要大于等于" + len + "!\n");
				return false;
			}
			break;
		case "<=" :
			if (strValue.length > parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "的长度需要小于等于" + len + "!\n");
				return false;
			}
			break;
	}
	return true;
}
/**
 * 后四位不为0000,不能是000000
 * @param vName
 * @param strValue
 * @return
 */
function verifyZipCode(vName, strValue)
{
			if (strValue != "" && strValue=="000000")
			{
				arrVerifyErrInfo.push("请输入正确的邮编\n");
				return false;
			}	
			else
			{
				return true;
			}
}
/**
 * 输入值校验
 * @param vName
 * @param strValue
 * @param vType
 * @return
 */
function verifyValue(vName, strValue, vType)
{
	var oper;
	var Val;
	var strOperVal = vType.substring(5);	//截取出操作符和值

	if (strValue == "") return true;

	if (isNaN(parseFloat(strOperVal.substring(1))))
	{
		oper = strOperVal.substring(0, 2);	//当为">=", "<="时
		Val = strOperVal.substring(2);
	}
	else
	{
		oper = strOperVal.substring(0, 1);	//当为"=", "<", ">"时
		Val = strOperVal.substring(1);
	}

	switch (oper)
	{
		case "=" :
			if (parseFloat(strValue) != parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + " 的值需要等于" + Val + "!\n");
				return false;
			}
			break;
		case ">" :
			if (parseFloat(strValue) <= parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + " 的值需要大于" + Val + "!\n");
				return false;
			}
			break;
		case "<" :
			if (parseFloat(strValue) >= parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + " 的值需要小于" + Val + "!\n");
				return false;
			}
			break;
		case ">=" :
			if (parseFloat(strValue) < parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + " 的值需要大于等于" + Val + "!\n");
				return false;
			}
			break;
		case "<=" :
			if (parseFloat(strValue) > parseFloat(Val))
			{
	arrVerifyErrInfo.push(vName + " 的值需要小于等于" + Val + "!\n");
	return false;
	}
	break;
	}
	return true;
	}

/**
 * 代码类型校验，可与CodeSelect功能配合
 * @param vName
 * @param strValue
 * @param vType
 * @param boxName
 * @param returnCode
 * @return
 */
function verifyCode(vName, strValue, vType,boxName, returnCode)
{
	var strCode = vType.substring(5);	//截取出代码类型标志
	if(mVs == null)
	{
		var win = searchMainWindow(this);
		if (win == false){ win = this; }
		mVs=win.parent.VD.gVCode;
	}
	var arrCode = mVs.getVar(strCode);	//从内存读数据
	var passFlag = false;	//校验通过标志，true表示通过
	var arrRecord;	//拆分的记录数组
	var arrField;	//拆分的字段数组
	var recordNum;	//记录数
	var fieldNum;	//字段数
	var arrResult = new Array();	//结果数组，模仿CodeSelect数据格式
	var urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
	//urlStr：查询窗口URL和查询参数
	var sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
	//sFeatures：查询窗口样式
	var strCodeSelect = "";
	if (strValue == "") return true;
	//内存中有数据，直接进行校验
	if (arrCode != false)
	{
		for (var i=0; i<arrCode.length; i++)
		{
			for (var j=0; j<2; j++)
			{
				if (strValue == arrCode[i][j])
				{
					//在个人承保录入的校验职业和职业代码中用到
					if (typeof(returnCode) != "undefined") return arrCode[i];
					passFlag = true;
					break;
				}
			}
		}
	}
	else if(eval(boxName+".CodeData")!=''&& typeof(eval(boxName+".CodeData"))!='undefined')
	{
				arrRecord = eval(boxName+".CodeData").split("^");	//拆分记录，形成返回的数组
				recordNum = arrRecord.length;
				for (i=1; i<recordNum; i++)
				{
					arrField  = arrRecord[i].split("|");	//拆分字段,将每个纪录拆分为一个数组
					fieldNum = arrField.length;
					arrResult[i-1] = new Array();
					for (j=0;j<fieldNum;j++)
					{
						arrResult[i-1][j] = arrField[j];
					}
					strCodeSelect = strCodeSelect + "<option value=" + arrResult[i-1][0] + ">";
					strCodeSelect = strCodeSelect + arrResult[i-1][0] + "-" + arrResult[i-1][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				mVs.addArrVar(strCode,"",arrResult);	//保存进内存，提供给CodeSelect共享
				mVs.addVar(strCode+"Select","",strCodeSelect);	//无论是否有数据从服务器端得到,都设置该变量

				for (i=0; i<arrResult.length; i++)
				{
					for (j=0; j<2; j++)
					{
						if (strValue == arrResult[i][j])
						{
							//在个人承保录入的校验职业和职业代码中用到
							if (typeof(returnCode) != "undefined") return arrResult[i];
							passFlag = true;
							break;
						}
					}
				}    
	}	
	else
	{
		//连接数据库进行CODE查询，返回查询结果给arrCode
		arrCode = window.showModalDialog(urlStr, "", sFeatures);

		if ((arrCode == false) || (arrCode == ""))
		{
			//等待从数据库端取回数据
			arrVerifyErrInfo.push("CODE查询功能错误，请与管理员联系！\n");
			return false;
		}
		else if (arrCode == "Code Query Faile")
			{
				//查询数据库失败处理
				arrVerifyErrInfo.push(vName + " 数据库代码查询失败，请与管理员联系！\n");
				return false;
			}
			else
			{
				arrRecord = arrCode.split("^");	//拆分记录，形成返回的数组
				recordNum = arrRecord.length;
				for (i=1; i<recordNum; i++)
				{
					arrField  = arrRecord[i].split("|");	//拆分字段,将每个纪录拆分为一个数组
					fieldNum = arrField.length;
					arrResult[i-1] = new Array();
					for (j=0;j<fieldNum;j++)
					{
						arrResult[i-1][j] = arrField[j];
					}
					strCodeSelect = strCodeSelect + "<option value=" + arrResult[i-1][0] + ">";
					strCodeSelect = strCodeSelect + arrResult[i-1][0] + "-" + arrResult[i-1][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				mVs.addArrVar(strCode,"",arrResult);	//保存进内存，提供给CodeSelect共享
				mVs.addVar(strCode+"Select","",strCodeSelect);	//无论是否有数据从服务器端得到,都设置该变量

				for (i=0; i<arrResult.length; i++)
				{
					for (j=0; j<2; j++)
					{
						if (strValue == arrResult[i][j])
						{
							//在个人承保录入的校验职业和职业代码中用到
							if (typeof(returnCode) != "undefined") return arrResult[i];
							passFlag = true;
							break;
						}
					}
				}
			}
	}
	if (!passFlag)
	{
		arrVerifyErrInfo.push(vName + "输入错误！\n");
	}
	return passFlag;
}

function verifyElementWrap(strInfo, strValue,boxName)
{
	var strboxName,strfocus,strcolor,cleardata;
	strboxName=boxName;
	chkoldclass="if(!"+strboxName+".oldclass) \n"+strboxName+".oldclass="+strboxName+".className;";
	eval(chkoldclass);
	if (!verifyElement(strInfo, strValue))
	{
		strfocus=strboxName+".onblur="+strboxName+".focus;";
		strcolor=strboxName+".className=\"warn\";";
		cleardata=strboxName+".value='';";
		eval(strcolor);
		eval(cleardata);
		eval(strfocus);
		return false;
	}
	strfocus=strboxName+".onblur=null";
	strcolor=strboxName+".className="+strboxName+".oldclass;";
	eval(strcolor);
	eval(strfocus);
	return true;
}

function verifyElementWrap2(strInfo, strValue,boxName)
{
	var strboxName,strfocus,strcolor,cleardata;
	strboxName=boxName;
	if(!strboxName.oldclass){
		strboxName.oldclass=strboxName.className;
	}
	if (!verifyElement(strInfo, strValue,strboxName))
	{
		if(boxName.style.display ='none')
		{
			boxName.style.display ='';
		}
		//strboxName.focus();
		var strboxid = strboxName.getAttribute("id");
		var t = jQuery("#"+strboxid).offset().top; 
	        jQuery("body,html").animate({
	            scrollTop:t
	        }, 500);
		strboxName.className="warn";
		/*校验生效后，不清空原来值*/
		return false;
	}
	strboxName.className=strboxName.oldclass;
	return true;
}

//业务程序调用接口，如果通过校验返回true，否则返回false
function verifyInput2()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志
	
	// 初始化直接进行保费试算的产品,判断身份证年龄与初始年龄是否一致
	var ageFlag = false;
	if(premProducts.indexOf(productId)>-1){
		ageFlag = true;
	}
	
	//遍历所有FORM 
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{	
			if(ageFlag && window.document.forms[formsNum].elements[elementsNum].id == "applicantId"){
				var idStr = document.getElementById("applicantId").value;
				var year = idStr.substring(6, 10);      
				var month = idStr.substring(10, 12);      
				var day = idStr.substring(12, 14);
				if(document.getElementById("applicantBirthday").value!=null && document.getElementById("applicantBirthday").value!=year+"-"+month+"-"+day){
					if(jQuery("#applicantId").parent().children("i")){
						jQuery("#applicantId").parent().children("i").remove();
					}
					jQuery("#applicantId").parent().children("label").after(jQuery("<i  class=\"yz_mes_error\" >身份证与出生日期不符，请确认</i>"));
					
					if(document.getElementById("applicantId").style.display = "none"){
						document.getElementById("applicantId").style.display = "";
					}
					var t = jQuery("#applicantId").offset().top; 
					jQuery("body,html").animate({
			            scrollTop:t
			        }, 500);
			        document.getElementById("applicantId").className="warn";
					passFlag = false;
					break;
				}
			}
			
			//元素校验属性verify不为NULL
			//alert(jQuery("#recognizeeOperate").val() + '-' + window.document.forms[formsNum].elements[elementsNum].id);
			if(window.document.forms[formsNum].elements[elementsNum] !=null 
				&& window.document.forms[formsNum].elements[elementsNum].id !=null 
				&& window.document.forms[formsNum].elements[elementsNum].id.indexOf("SWFUpload") == -1
				&& window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != "undefined" 
				&& window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != null 
				&& window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != "") 
			{
				if(window.document.forms[formsNum].elements[elementsNum].id.indexOf("recognizee")!=-1){
					if(jQuery("#recognizeeOperate").val()=="1" && jQuery("#mulInsuredFlag").val()!="rid_me" && window.document.forms[formsNum].elements[elementsNum].id.indexOf("_")!=-1){
						if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value,window.document.forms[formsNum].elements[elementsNum]))
						{
							passFlag = false;
							// ! 的图片
							break;
						}
					}else if(jQuery("#recognizeeOperate").val()=="1" && jQuery("#mulInsuredFlag").val()=="rid_me" && window.document.forms[formsNum].elements[elementsNum].id.indexOf("_")==-1){
						if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value,window.document.forms[formsNum].elements[elementsNum]))
						{
								passFlag = false;
								break;
						}
					}else if(jQuery("#recognizeeOperate").val()=="2" && window.document.forms[formsNum].elements[elementsNum].id.indexOf("_")!=-1){
						if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value,window.document.forms[formsNum].elements[elementsNum])){
							passFlag = false;
							break;
						}
					}
				}else{ 
					if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value,window.document.forms[formsNum].elements[elementsNum]))
					{
						passFlag = false;
						break;
					}
				}
			}
		}
		if (!passFlag) break;
	}
	return passFlag;
}
//验证重复输入是否不同f
function verifyCheckDifferent(vName, strValue, boxName)
{
	    var ConfirmValue = boxName.substring(0, boxName.indexOf(".")+1)+"Confirm"+boxName.substring(boxName.indexOf(".")+1,boxName.length)+".value";
	    if(strValue!=""&& eval(ConfirmValue)=="" )
		{
				arrVerifyErrInfo.push(vName + "请在重复输入处填值!\n");
				return false;
		}	    
	    if(strValue!=""&& eval(ConfirmValue)!="" && strValue!=eval(ConfirmValue))
		{
				arrVerifyErrInfo.push(vName + "两次输入有误!\n");
				return false;
		}
		return true; 
}
function verifyPhone(vName, strValue, boxName)
{
	strValue = trim(strValue); 
	if (strValue == ""){
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false;
	}
	var myreg = /^\d{11}$/; 
	var re=new RegExp(myreg);      
	if(!re.test(strValue)){
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false;
	}
	return true;
}
function verifyMobileNo(vName, strValue, boxName)
{
	strValue = trim(strValue); 
	if (strValue == ""){
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false;
	}
	var myreg = /^\d{11}$/; 
	var re=new RegExp(myreg);      
	if(!re.test(strValue)){
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false;
	}
	if(!/^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$/.test(strValue))
	{
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false;
	}
	return true;
}
function forconfirm()
{	
	var keycode = event.keyCode;
	if (keycode == "13"&&document.activeElement.elementtype && document.activeElement.elementtype.indexOf("con")!=-1) 
	{
		var tconfirm="fm.Confirm"+document.activeElement.name+".style.display =''";
		var fconfirm="fm.Confirm"+document.activeElement.name+".focus()";
		var vconfirm="fm.Confirm"+document.activeElement.name+".value";		
		var cconfirm="fm.Confirm"+document.activeElement.name+".value=''";				
		var nconfirm=document.activeElement.name+"n.style.display ='none'";	
		if(eval(vconfirm)!=""&&eval(vconfirm)!=document.activeElement.value)
		{
			eval(cconfirm);
		}				
		if(document.activeElement.elementtype.indexOf("nacessary")!=-1)
		{
		eval(nconfirm);		
	  }		
		document.activeElement.style.display ="none";
		eval(tconfirm);
		eval(fconfirm);	
		keycode="00";
	}
	
	if (keycode == "13"&&document.activeElement.elementtype && document.activeElement.elementtype.indexOf("firm")!=-1) 
	{		
		var tconfirm="fm."+document.activeElement.name.substring(7,document.activeElement.name.length)+".style.display =''";
		var fconfirm="fm."+document.activeElement.name.substring(7,document.activeElement.name.length)+".focus()";		
		var vconfirm="fm."+document.activeElement.name.substring(7,document.activeElement.name.length)+".value";		
		var wconfirm="fm."+document.activeElement.name.substring(7,document.activeElement.name.length)+".className='warn'";				
		var rconfirm="fm."+document.activeElement.name.substring(7,document.activeElement.name.length)+".className='confirm'";			
		var nconfirm=document.activeElement.name.substring(7,document.activeElement.name.length)+"n.style.display =''";			
		var econfirm="fm."+document.activeElement.name.substring(7,document.activeElement.name.length)+".elementtype.indexOf('nacessary')!=-1";						
		var cconfirm="fm."+document.activeElement.name.substring(7,document.activeElement.name.length)+".value=''";					
		if(eval(vconfirm)!=document.activeElement.value)
		{
			alert("两次输入值不一致，请确认！");
			document.activeElement.value="";
			eval(wconfirm);
			eval(cconfirm);
		}
		else
		{
			eval(rconfirm);
		}
		if(eval(econfirm))
		{
			eval(nconfirm);
	        }						
		document.activeElement.style.display ="none";		
		eval(tconfirm);
		eval(fconfirm);
		keycode="00";		
	}	
	if (keycode == "13"&&document.activeElement.name.indexOf("OccupationCode")!=-1) 
	{
			getdetailwork(); 
	}	
}

//清空页面上的变色效果
function clearVerify()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志
	var boxName = "";
	var strcolor = "";
	
	//遍历所有FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			if(window.document.forms[formsNum].elements[elementsNum].name==null
			|| window.document.forms[formsNum].elements[elementsNum].name=="")
			{
				continue;
			}
			
			boxName = window.document.forms[formsNum].name+"."+window.document.forms[formsNum].elements[elementsNum].name;
			if(eval(boxName+".className") == "warnno" || eval(boxName+".className") == "warn")
			{
				strcolor=boxName+".className="+boxName+".oldclass;";
				eval(strcolor);
			}
		}
	}
}

/**
* 手工写校验时调用函数，展示效果同正常调用verify
*boxName：帧+控件名，如：fm.ContNo
*strVerifyErrInfo：错误提示信息，如：保单号码不合法！
*boxType：控件类型，可为空，默认为common
*/
function showVerify(boxName,strVerifyErrInfo,boxType)
{
	var strTemp = "";
	clearVerify();
	if(typeof(boxType) == "undefined"||boxType==null||boxType=="")
	{
		boxType = "common";
	}

	if(boxType=="codeno")
	{
		strTemp = boxName+".className=\"warnno\";";
	}
	else if(boxType=="common")
	{
		strTemp = boxName+".className=\"warn\";";
	}
	
	eval(strTemp);
	strTemp = boxName+".value=''";
	eval(strTemp);
	strTemp = boxName+".focus()";
	eval(strTemp);
}

/**
 * 代码选择校验
 * @param vName
 * @param strValue
 * @param vType
 * @param boxName
 * @return
 */
function verifyCodeSel(vName, strValue, vType, boxName)
{
	var strboxName = boxName;
	var strCodeValue = strValue;	//代码值
	var strCodeData = eval(strboxName+".CodeData"); //虚拟数据源的查询结果集
	var strQueryResult = "";	//代码选择的查询结果集
	var cacheFlag = false;	//数据校验标志
	var arrCode = null;	//拆分数组
	var aCode = "";	//查询类型
	var bCode = "";	//查询条件值
	var cCode = "";	//查询条件对象
	var strCode = "";
	var strEvent = "";	//保存onDoubleClick事件代码
	
	//空值则不处理
	if (strCodeValue==null || strCodeValue=="") 
		return true;
	
	//虚拟数据源处理，同样是和数组进行一一匹配，找到符合的就通过，如果全部遍历后仍无匹配则报错
	if (strCodeData != null && strCodeData != ""){
		strQueryResult = strCodeData;
		cacheFlag = false;
		arrCode = decodeEasyQueryResult(strQueryResult,0,1);
	
		for (var m=0; m<arrCode.length; m++){
			if (strCodeValue==arrCode[m][0] || strCodeValue==arrCode[m][1]){//因为可能存在双击选择框（蓝色的框）中是代码选择的中文而不是代码的情况（显示时做了调整），所以在这里需要考虑用代码或者名称进行匹配
				cacheFlag = true;
				break;
			}	
		}	
		
		if (cacheFlag==false){
			arrVerifyErrInfo.push(vName+"输入不符合数据库规定的取值范围！请查阅或双击输入框选择！\n");
			return false;
		}
	}
	else{
		strEvent = eval(strboxName+".ondblclick");
		if (strEvent == null) return true;
		
		strCode = new String(strEvent);
		strCode = strCode.substring(strCode.indexOf("showCodeList") + 14);	
		aCode = strCode.substring(0, strCode.indexOf("'"));	//得到查询类型
		
		//如果有null出现，则表示将会有查询条件（但是还不确定一定有，因为有可能只有强制刷新）
		if (strCode.indexOf("null")!=-1){
			strCode = strCode.substring(strCode.indexOf("null") + 5);
			if(strCode.indexOf("]")>=0){
				var bCodeArr = eval(strCode.substring(0, strCode.indexOf("]")+1));
				if (typeof(bCodeArr) == "object")
				{
					var tLength = bCodeArr.length;
					for(var m=0;m<tLength;m++)
					{
						bCode = bCode+bCodeArr[m];
						if(m<tLength-1)
						{
							bCode=bCode+"|";
						}
					}
				}
				else
				{
					bCode = bCodeArr;
				}
					
				strCode = strCode.substring(strCode.indexOf("]")+3);
				cCode = strCode.substring(0, strCode.indexOf("'"));	//得到查询条件所要查询的字段
			}
			else{
				bCode = strCode.substring(0, strCode.indexOf(","));
				bCode = eval(bCode);//得到查询条件字段所匹配的值
				
				strCode = strCode.substring(strCode.indexOf(",")+2);
				cCode = strCode.substring(0, strCode.indexOf("'"));	//得到查询条件所要查询的字段			
			}
			

			if (bCode==null || bCode == "null") bCode = "";	//出现null的情况
			if (cCode==null || cCode == "null") cCode = "";
		}
		else{
			bCode = "";	//没有查询条件的情况
			cCode = "";
		}
		
		strCode = aCode + bCode + cCode;	//拼写查询条件，缓存的存储方式为类型＋匹配字段值+匹配字段
		//如果内容中有数据，匹配数据和实际数据，无则跳过，可能是因为别的双击带出的
		if (win.parent.VD.gVCode.getVar(strCode) != false){
			cacheFlag = false;
			arrCode = win.parent.VD.gVCode.getVar(strCode);
			//alert(arrCode);
			for(var m=0;m<arrCode.length;m++){
				if (strCodeValue==arrCode[m][0] || strCodeValue==arrCode[m][1]){//因为可能存在双击选择框（蓝色的框）中是代码选择的中文而不是代码的情况（显示时做了调整），所以在这里需要考虑用代码或者名称进行匹配
					cacheFlag = true;
					break;
					//如果发现有匹配的则立即跳出
				}
			}
			if (cacheFlag==false){
				arrVerifyErrInfo.push(vName+"输入不符合数据库规定的取值范围！请查阅或双击输入框选择！\n");
				return false;
			}
		}

	}

	return true;
}
//通过元素ID校验该ID下全部内容
function verifyById(cId)
{
	var passFlag = true;
	var obj = document.getElementById(cId);
	var elementsNum = 0;	//FORM中的元素数
		//遍历ID下所有ELEMENT
		for (elementsNum=0; elementsNum<obj.all.length; elementsNum++)
		{
			//元素校验属性verify不为NULL
			if (obj.all[elementsNum].getAttributeNode("verify").value != null && obj.all[elementsNum].getAttributeNode("verify").value!= "")
			{
				//进行校验verifyElement
				if (!verifyElementWrap2(obj.all[elementsNum].getAttributeNode("verify").value, obj.all[elementsNum].value,"document.all('"+obj.all[elementsNum].name+"')"))
				{
					passFlag = false;
					break;
				}
			}
		}
	return passFlag;
}
function verifyADDRESS(vName, strValue){
	   var sum = 0 ;
	   for (var i=0; i<strValue.length; i++) { 
		   var c = strValue.charCodeAt(i); 
		   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
			   
		   }
		   else {     
			   sum++; 
		   } 
	   }
	   if(sum<=6){
		   arrVerifyErrInfo.push("请填写正确的通讯地址，不少于6个汉字\n");
		   return false;
	   }else{
		   return true;
	   }

}
function verifyFightTime(vName, strValue){
	
	   var effDate = jQuery("#effective").val()+" 00:00:00"; 
	   var fallDate = jQuery("#fail").val()+" 23:59:59"; 
	   if(strValue<effDate){
		   arrVerifyErrInfo.push("起飞时间应不早于起保日期\n");
		   return false;
	   }else if(strValue>fallDate){
		   arrVerifyErrInfo.push("起飞时间应不晚于止保日期\n");
	   }else{
		   return true;
	   }

}
function trim(s)
{
	//使用正则表达式进行全局替换
	return s.replace(/(^\s*)|(\s*$)/g,"");
}

//通过table元素ID校验该ID下全部内容
function verifyByTrId(cId)
{
	//遍历所有行
	jQuery("#"+cId).find("td").each(function(){
		//定位第二个em
		jQuery(this).find("em:eq(1)").each(function(){
			//定位input
			jQuery(this).find("input,select").each(function(){
				var verifyValue = jQuery(this).attr("excelverify");
				if(verifyValue!=null && verifyValue!="" && typeof(verifyValue) != "undefined"){
					verifyElementToExcel(verifyValue,jQuery(this).attr("value"),jQuery(this).attr("id"));
				}
			});
		});
	});
	
} 
//校验元素，strInfo为元素校验信息，strValue为元素值
function verifyElementToExcel(strInfo, strValue, boxName)
{
	
    if(strValue == null)
    {
       strValue = "";
    } 
    var cBoxName = "";
    if(typeof(boxName)!="object"){
    	cBoxName = boxName;
    }else{
    	cBoxName = boxName.id;
    }
    var strboxName = cBoxName;

    var strValue = trim(strValue);	//清空空格
	 
	var passFlag = true;	//校验通过标志，true表示通过
	 
	var vName;	//校验字段名称
	 
	var vType;	//要进行的校验类型
	 
	var intIndex;	//运算符索引
	 
	var typeStack = new Array();	//一个字段的校验结果堆栈
	 
	var operStack = new Array();	//一个字段的校验计算符号堆栈，仅限于“与”，“或”计算
	 
	while (arrVerifyErrInfo != "")
	{
		//清空前一个字段的校验错误信息
		arrVerifyErrInfo.pop();
	}
	
	//分离出字段名称，兼容前一版本，故仍用“|”分隔
	vName = strInfo.substring(0, strInfo.indexOf("|"));
	strInfo = strInfo.substring(strInfo.indexOf("|") + 1);
	
	//拆分出校验类型，并进行校验，返回校验结果（通过TRUE，否FALSE），并入堆栈
	while (strInfo != "")
	{
		if (strInfo.indexOf("|") != -1 && strInfo.indexOf("&") != -1)
		{
			//存在两种运算
			intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("&"):strInfo.indexOf("|");
			vType = strInfo.substring(0, intIndex);
			typeStack.push(verifyType(vName, vType, strValue,strboxName));
			operStack.push(strInfo.substring(intIndex, intIndex + 1));
			strInfo = strInfo.substring(intIndex + 1);
		}
		else if (strInfo.indexOf("|") != -1 || strInfo.indexOf("&") != -1)
			{
				//只有一种运算
				intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("|"):strInfo.indexOf("&");
				vType = strInfo.substring(0, intIndex);
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
				operStack.push(strInfo.substring(intIndex, intIndex + 1));
				strInfo = strInfo.substring(intIndex + 1);
			}
			else
			{
				//无运算
				vType = strInfo;
				strInfo = "";
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
			}
	}

	passFlag = typeStack[0];
	//只有一个校验类型时
	for (var k=0; k<operStack.length; k++)
	{
		//有多个校验类型，进行运算
		if (operStack[k] == "|")
		{
			typeStack[k + 1] = typeStack[k] | typeStack[k + 1];
		}
		else if (operStack[k] == "&")
			{
				typeStack[k + 1] = typeStack[k] & typeStack[k + 1];
			}
			else
			{
				alert("校验参数设置有误");
			}
		passFlag = typeStack[k + 1];
	}
	
	var strVerifyErrInfo = "\n";
	if (!passFlag)
	{
		jQuery("#"+cBoxName).parent().parent().addClass("td_hlight");
		//显示错误信息
		showExcelError(cBoxName,arrVerifyErrInfo);
	}else{ 
		jQuery("#"+cBoxName).parent().parent().removeClass("td_hlight");
		//清楚原信息
		deleteExcelError(cBoxName);
	}
	return passFlag;
}
/*excel错误显示*/
function showExcelError(cBoxName,mError){
	var cError = mError[0];
	var errorFlag = "_error";
	var id_index = cBoxName.split("_")[1];
	var id_name = cBoxName.split("_")[0];
	var appntName = jQuery("#sdrecognizeeName_"+id_index).val();
	var tr_id = "recognizee_"+id_index;
	if ( jQuery("#"+tr_id).length > 0 ) {
		var id = cBoxName+errorFlag;
		if (jQuery("#"+id).length > 0 ) {
			jQuery("#"+id).text(cError);
		}else{
			var cErrorInfo = "<i id='"+id+"'>"+cError+"</i>";
			jQuery("#"+tr_id).append(cErrorInfo);
		}
	}else{
		var id = cBoxName+errorFlag;
		var cErrorInfo = "<i id='"+id+"'>"+cError+"</i>";
		var cErrorInfo2 = "<div id='"+tr_id+"'>被保人"+appntName+"的录入项有错误 :<i id="+id+">"+cError+"</i></div>";
		jQuery("#importMessage").append(cErrorInfo2);
		jQuery("#importMessage").attr("style", "overflow:hidden;color:red");
	}
}
/*清除校验信息*/
function deleteExcelError(cBoxName){
	var errorFlag = "_error";
	var id_index = cBoxName.split("_")[1];
	var id_name = cBoxName.split("_")[0];
	var tr_id = "recognizee_"+id_index;

	var id = cBoxName+errorFlag;
	if(jQuery("#"+tr_id+" i").length>0){
		jQuery("#"+tr_id+" i").each(function(){
			if(id==jQuery(this).attr("id")){
				jQuery("#"+id).remove();
			}
		})
	}
	if(jQuery("#"+tr_id+" i").length<=0){
		jQuery("#"+tr_id).remove();
	}

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
	  if(!/^\d{16}(x{2})$/i.test(strIDno)){
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
function validateBirthdayExcel(bId){
	 var effdate = jQuery("#effective").val();
	 var tFlag = true;
	   if(bId!=null && bId!=""){
			jQuery.ajax({
				url: sinosoft.base+"/shop/order_config_new!ajaxAge.action?applicantBirthday="+bId+"&productId="+productId+"&effdate="+effdate,
				dataType: "json",
				type:"GET",
				async: false,
				success: function(data) {
					if(!data){
						tFlag = false;
						return false;
					}else{							
						tFlag = true;
						calPremFlag=true;
						return true;
					}  
				}
			});
		}	 			
	   return tFlag;
}
