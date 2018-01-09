var arrVerifyErrInfo = new Array(); 
function verifyClass() {
	this.verifyInput = verifyInput;
	this.verifyForm = verifyForm;
	this.verifyElement = verifyElement;
	this.verifyType = verifyType;
	this.verifyMustNull = verifyMustNull;
	this.verifyNotNull = verifyNotNull;
	this.verifyNumber = verifyNumber;
	this.verifyDate = verifyDate;
	this.verifyEmail = verifyEmail;
	this.verifyDecimal = verifyDecimal;
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
	this.verifyCodeSel = verifyCodeSel
}
function verifyInput() {
	var formsNum = 0;
	var elementsNum = 0;
	var passFlag = true;
	for (formsNum = 0; formsNum < window.document.forms.length; formsNum++) {
		for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++) {
			if (window.document.forms[formsNum].elements[elementsNum].verify != "undefined" && window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "") {
				if (!verifyElement(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value)) {
					passFlag = false;
					break
				}
			}
		}
		if (!passFlag) {
			break
		}
	}
	return passFlag
}
function verifyForm(formName) {
	var elementsNum = 0;
	var passFlag = true;
	for (elementsNum = 0; elementsNum < window.document.all(formName).elements.length; elementsNum++) {
		if (window.document.all(formName).elements[elementsNum].verify != null && window.document.all(formName).elements[elementsNum].verify != "") {
			if (!verifyElement(window.document.all(formName).elements[elementsNum].verify, window.document.all(formName).elements[elementsNum].value)) {
				passFlag = false;
				break
			}
		}
	}
	return passFlag
}
function verifyElement(strInfo, strValue, boxName) {
	var _el = jQuery(document.getElementById(boxName));
	var _v = jQuery.trim(strValue);
	_el.val(_v);
	if (strValue == null) {
		strValue = ""
	}
	var cBoxName = "";
	if (typeof (boxName) != "object") {
		cBoxName = boxName
	} else {
		cBoxName = boxName.id
	}
	var strboxName = cBoxName;
	var strValue = trim(strValue);
	var passFlag = true;
	var vName;
	var vType;
	var intIndex;
	var typeStack = new Array();
	var operStack = new Array();
	while (arrVerifyErrInfo != "") {
		arrVerifyErrInfo.pop()
	}
	vName = strInfo.substring(0, strInfo.indexOf("|"));
	strInfo = strInfo.substring(strInfo.indexOf("|") + 1);
	if (jQuery("#" + cBoxName).parent().children("font")) {
		jQuery("#" + cBoxName).parent().children("font").hide()
	}
	while (strInfo != "") {
		if (strInfo.indexOf("|") != -1 && strInfo.indexOf("&") != -1) {
			intIndex = strInfo.indexOf("|") > strInfo.indexOf("&") ? strInfo.indexOf("&") : strInfo.indexOf("|");
			vType = strInfo.substring(0, intIndex);
			typeStack.push(verifyType(vName, vType, strValue, strboxName));
			operStack.push(strInfo.substring(intIndex, intIndex + 1));
			strInfo = strInfo.substring(intIndex + 1)
		} else {
			if (strInfo.indexOf("|") != -1 || strInfo.indexOf("&") != -1) {
				intIndex = strInfo.indexOf("|") > strInfo.indexOf("&") ? strInfo.indexOf("|") : strInfo.indexOf("&");
				vType = strInfo.substring(0, intIndex);
				typeStack.push(verifyType(vName, vType, strValue, strboxName));
				operStack.push(strInfo.substring(intIndex, intIndex + 1));
				strInfo = strInfo.substring(intIndex + 1)
			} else {
				vType = strInfo;
				strInfo = "";
				typeStack.push(verifyType(vName, vType, strValue, strboxName))
			}
		}
	}
	passFlag = typeStack[0];
	for (var k = 0; k < operStack.length; k++) {
		if (operStack[k] == "|") {
			typeStack[k + 1] = typeStack[k] | typeStack[k + 1]
		} else {
			if (operStack[k] == "&") {
				typeStack[k + 1] = typeStack[k] & typeStack[k + 1]
			} else {
				alert("校验参数设置有误")
			}
		}
		passFlag = typeStack[k + 1]
	}
	var strVerifyErrInfo = "\n";
	if (!passFlag) {
		while (arrVerifyErrInfo != "") {
			strVerifyErrInfo = strVerifyErrInfo + arrVerifyErrInfo.pop();
			while (arrVerifyErrInfo != "") {
				arrVerifyErrInfo.pop()
			}
		}
		if (jQuery("#" + cBoxName).parent().children("label").next().is("i")) {
			jQuery("#" + cBoxName).parent().children("i").remove()
		}
		if (cBoxName.indexOf("tj_mail") < 0 && !jQuery("#" + cBoxName).parent().children("label").next().is("i")) {
			jQuery("#" + cBoxName).parent().children("label").after(jQuery('<i  class="yz_mes_error" >' + strVerifyErrInfo + "</i>"));
			if (cBoxName != "" && cBoxName != null && cBoxName != "showOutGoingParpose") {
				jQuery("#" + cBoxName).parent().parent().parent().parent().find("dd").show();
				jQuery("#" + cBoxName).parent().parent().parent().parent().find("a").removeClass("kg_jia");
				jQuery("#" + cBoxName).parent().parent().parent().parent().parent().removeClass("bbr_bor");
				jQuery("#" + cBoxName).parent().parent().parent().parent().find("a").each(function() {
					var tID = jQuery(this).attr("id");
					var tClass = jQuery(this).attr("class");
					var tHref = jQuery(this).attr("href");
					if ((tID == null || tID == "") && tClass != "windowClose_ loginWindowClose_" && typeof (tHref) != "undefined" && tHref.indexOf("traveltemplate") == -1) {
						jQuery(this).toggle(function() {
							jQuery(this).parent().siblings().hide();
							jQuery(this).text("打开");
							jQuery(this).addClass("kg_jia");
							jQuery(this).parent().parent().addClass("bbr_bor");
							if (tInsLastNum != 0) {
								jQuery("#nametitle_0").html(jQuery("#recognizeeName_0").val());
								jQuery("#nametitle_" + (tInsNum)).html(jQuery("#recognizeeName_" + (tInsNum)).val())
							} else {
								jQuery("#ins_" + tInsNum).find(".bxr_num").html(tInsNum + 1);
								jQuery("#nametitle_" + (tInsNum)).html(jQuery("#recognizeeName_" + (tInsNum)).val())
							}
						}, function() {
							jQuery(this).parent().siblings().show();
							jQuery(this).text("收起");
							jQuery(this).removeClass("kg_jia");
							jQuery(this).parent().parent().removeClass("bbr_bor")
						})
					}
				})
			}
		}
		if (jQuery("#" + cBoxName).parent().children("i")) {
			jQuery("#" + cBoxName).parent().children("i").remove()
		}
		if (!jQuery("#" + cBoxName).parent().children("label").next().is("i")) {
			jQuery("#" + cBoxName).parent().children("label").after(jQuery('<i class="yz_mes_error">' + strVerifyErrInfo + "</i>"))
		}
	} else {
		if (jQuery("#" + cBoxName).parent().children("i")) {
			jQuery("#" + cBoxName).parent().children("i").remove()
		}
		if (!jQuery("#" + cBoxName).parent().children("label").next().is("i")) {
			jQuery("#" + cBoxName).parent().children("label").after(jQuery('<i class="yz_mes_yes">&nbsp;&nbsp;</i>'))
		}
	}
	return passFlag
}
function verifyElement1(strInfo, strValue, boxName, iframeDoc) {
	if (strValue == null) {
		strValue = ""
	}
	var cBoxName = "";
	if (typeof (boxName) != "object") {
		cBoxName = boxName
	} else {
		cBoxName = boxName.id
	}
	var strboxName = cBoxName;
	var strValue = trim(strValue);
	var passFlag = true;
	var vName;
	var vType;
	var intIndex;
	var typeStack = new Array();
	var operStack = new Array();
	while (arrVerifyErrInfo != "") {
		arrVerifyErrInfo.pop()
	}
	vName = strInfo.substring(0, strInfo.indexOf("|"));
	strInfo = strInfo.substring(strInfo.indexOf("|") + 1);
	if ($(iframeDoc).find("#" + cBoxName).parent().children("font")) {
		$(iframeDoc).find("#" + cBoxName).parent().children("font").hide()
	}
	while (strInfo != "") {
		if (strInfo.indexOf("|") != -1 && strInfo.indexOf("&") != -1) {
			intIndex = strInfo.indexOf("|") > strInfo.indexOf("&") ? strInfo.indexOf("&") : strInfo.indexOf("|");
			vType = strInfo.substring(0, intIndex);
			typeStack.push(verifyType(vName, vType, strValue, strboxName));
			operStack.push(strInfo.substring(intIndex, intIndex + 1));
			strInfo = strInfo.substring(intIndex + 1)
		} else {
			if (strInfo.indexOf("|") != -1 || strInfo.indexOf("&") != -1) {
				intIndex = strInfo.indexOf("|") > strInfo.indexOf("&") ? strInfo.indexOf("|") : strInfo.indexOf("&");
				vType = strInfo.substring(0, intIndex);
				typeStack.push(verifyType(vName, vType, strValue, strboxName));
				operStack.push(strInfo.substring(intIndex, intIndex + 1));
				strInfo = strInfo.substring(intIndex + 1)
			} else {
				vType = strInfo;
				strInfo = "";
				typeStack.push(verifyType(vName, vType, strValue, strboxName))
			}
		}
	}
	passFlag = typeStack[0];
	for (var k = 0; k < operStack.length; k++) {
		if (operStack[k] == "|") {
			typeStack[k + 1] = typeStack[k] | typeStack[k + 1]
		} else {
			if (operStack[k] == "&") {
				typeStack[k + 1] = typeStack[k] & typeStack[k + 1]
			} else {
				alert("校验参数设置有误")
			}
		}
		passFlag = typeStack[k + 1]
	}
	var strVerifyErrInfo = "\n";
	if (!passFlag) {
		while (arrVerifyErrInfo != "") {
			strVerifyErrInfo = strVerifyErrInfo + arrVerifyErrInfo.pop();
			while (arrVerifyErrInfo != "") {
				arrVerifyErrInfo.pop()
			}
		}
		if ($(iframeDoc).find("#" + cBoxName).parent().children("label").next().is("i")) {
			$(iframeDoc).find("#" + cBoxName).parent().children("i").remove()
		}
		if ($(iframeDoc).find("#" + cBoxName).parent().children("i")) {
			$(iframeDoc).find("#" + cBoxName).parent().children("i").remove()
		}
		if (!$(iframeDoc).find("#" + cBoxName).parent().children("label").next().is("i")) {
			$(iframeDoc).find("#" + cBoxName).parent().children("label").after(jQuery('<i class="yz_mes_error">' + strVerifyErrInfo + "</i>"))
		}
	} else {
		if ($(iframeDoc).find("#" + cBoxName).parent().children("i")) {
			$(iframeDoc).find("#" + cBoxName).parent().children("i").remove()
		}
		if (!$(iframeDoc).find("#" + cBoxName).parent().children("label").next().is("i")) {
			$(iframeDoc).find("#" + cBoxName).parent().children("label").after(jQuery('<i class="yz_mes_yes">&nbsp;&nbsp;</i>'))
		}
	}
	return passFlag
}
function verifyShowInf(strValue, boxName) {
	if (jQuery("#" + boxName).parent().children("i")) {
		jQuery("#" + boxName).parent().children("i").remove()
	}
	if (strValue == null || strValue == "") {
		jQuery("#" + boxName).parent().children("font").show()
	}
	return false
}
function verifyType(vName, vType, strValue, boxName) {
	var passFlag = true;
	if (vType.toUpperCase() == "NULL") {
		passFlag = verifyMustNull(vName, strValue)
	}
	if (vType.toUpperCase() == "NOTNULL") {
		passFlag = verifyNotNull(vName, strValue, boxName)
	}
	if (vType.toUpperCase() == "NUM") {
		passFlag = verifyNumber(vName, strValue)
	}
	if (vType.toUpperCase() == "DATE") {
		passFlag = verifyDate(vName, strValue)
	}
	if (vType.toUpperCase() == "AGE") {
		passFlag = verifyAge(vName, strValue)
	}
	if (vType.toUpperCase() == "BIRAGE") {
		passFlag = verifyBirAge(vName, strValue, boxName)
	}
	if (vType.toUpperCase() == "EMAIL") {
		passFlag = verifyEmail(vName, strValue)
	}
	if (vType.toUpperCase() == "DECIMAL") {
		passFlag = verifyDecimal(vName, strValue)
	}
	if (vType.toUpperCase() == "INT") {
		passFlag = verifyInteger(vName, strValue)
	}
	if (vType.toUpperCase() == "TELEPHONE") {
		passFlag = verifyPStyle(vName, strValue)
	}
	if (vType.toUpperCase() == "CHECKDIFFERENT") {
		passFlag = verifyCheckDifferent(vName, strValue, boxName)
	}
	if (vType.toUpperCase().indexOf("FLOAT") == 0) {
		passFlag = verifyPartLen(vName, strValue, vType)
	}
	if (vType.toUpperCase().indexOf("LEN") == 0) {
		passFlag = verifyLength(vName, strValue, vType)
	}
	if (vType.toUpperCase().indexOf("PHONE") == 0) {
		passFlag = verifyPhone(vName, strValue, boxName)
	}
	if (vType.toUpperCase().indexOf("PHONE1") == 0) {
		passFlag = verifyPhone1(vName, strValue, boxName)
	}
	if (vType.toUpperCase().indexOf("MOBILENO") == 0) {
		passFlag = verifyMobileNo(vName, strValue, boxName)
	}
	if (vType.toUpperCase().indexOf("VALUE") == 0) {
		passFlag = (verifyNumber(vName, strValue) && verifyValue(vName, strValue, vType))
	}
	if (vType.toUpperCase().indexOf("CODE:") == 0) {
		passFlag = verifyCode(vName, strValue, vType, boxName)
	}
	if (vType.toUpperCase() == "ZIPCODE") {
		passFlag = (verifyLength(vName, strValue, "len=6") && verifyNumber(vName, strValue) && verifyZipCode(vName, strValue))
	}
	if (vType.toUpperCase() == "NOTNAN") {
		passFlag = verifyNotNaN(vName, strValue)
	}
	if (vType.toUpperCase().indexOf("RLEN") == 0) {
		passFlag = verifyRealLength(vName, strValue, vType)
	}
	if (vType.toUpperCase().indexOf("MONEY") == 0) {
		passFlag = verifyMoney(vName, strValue, vType)
	}
	if (vType.toUpperCase() == "CODESEL") {
		passFlag = verifyCodeSel(vName, strValue, vType, boxName)
	}
	if (vType.toUpperCase() == "URL") {
		passFlag = verifyURL(vName, strValue)
	}
	if (vType.toUpperCase() == "UFO") {
		passFlag = verifyUFO(vName, strValue)
	}
	if (vType.toUpperCase() == "CHI") {
		passFlag = verifyCHI(vName, strValue)
	}
	if (vType.toUpperCase() == "AREAFORMAT") {
		passFlag = verifyAREA(vName, strValue)
	}
	if (vType.toUpperCase() == "OCCUFORMAT") {
		passFlag = verifyOCCU(vName, strValue)
	}
	if (vType.toUpperCase() == "APPRELATION") {
		passFlag = verifyAPPRELATION(vName, strValue)
	}
	if (vType.toUpperCase() == "APPRELATION2") {
		passFlag = verifyAPPRELATION2(vName, strValue)
	}
	if (vType.toUpperCase() == "BENEFIT") {
		passFlag = verifyBenefit(vName, strValue, boxName)
	}
	if (vType.toUpperCase() == "APPAGE") {
		passFlag = verifyAppAge(vName, strValue)
	}
	if (vType.toUpperCase() == "BIRAPPAGE") {
		passFlag = verifyBIRAppAge(vName, strValue)
	}
	if (vType.toUpperCase() == "SYSEX") {
		passFlag = verifySYSex(vName, strValue, boxName)
	}
	if (vType.toUpperCase() == "ADDRESS") {
		passFlag = verifyADDRESS(vName, strValue)
	}
	if (vType.toUpperCase() == "FIGHTTIME") {
		passFlag = verifyFightTime(vName, strValue)
	}
	if (vType.toUpperCase() == "TRAVEL") {
		passFlag = verifyTRAVEL(vName, strValue)
	}
	if (vType.toUpperCase() == "HOURSEAGE") {
		passFlag = verifyHOURSEAGE(vName, strValue)
	}
	if (vType.toUpperCase() == "HOURSETYPE") {
		passFlag = verifyHOURSETYPE(vName, strValue)
	}
	if (vType.toUpperCase() == "ENG") {
		passFlag = verifyEnglish(vName, strValue)
	}
	if (vType.toUpperCase() == "CHANDEH") {
		passFlag = verifyName(vName, strValue)
	}
	if (vType.toUpperCase() == "IDCARD") {
		passFlag = verifyIdCard(vName, strValue)
	}
	if (vType.toUpperCase() == "IDTYPE") {
		passFlag = verifyIDCardType(vName, strValue)
	}
	if (vType.toUpperCase() == "CODETYPE") {
		passFlag = verifyCodeType(vName, strValue, vType, boxName)
	}
	if (vType.toUpperCase() == "CALLPREM") {
		passFlag = verifyCallPrem(boxName)
	}
	if (vType.toUpperCase().indexOf("ONLYONE") == 0) {
		passFlag = verifyOnlyOne(vName, strValue, vType, boxName)
	}
	if (vType.toUpperCase() == "IDCARDEXCEL") {
		passFlag = verifyIdCardExcel(vName, strValue, vType, boxName)
	}
	if (vType.toUpperCase() == "RECOEMAIL") {
		passFlag = verifyRecommEmail(vName, strValue)
	}
	if (vType.toUpperCase() == "BANKCODE") {
		passFlag = verifyBankCode(vName, strValue)
	}
	if (vType.toUpperCase() == "BANKNO") {
		passFlag = verifyBankNO(vName, strValue)
	}
	if (vType.toUpperCase() == "REBANKNO") {
		passFlag = verifyBankNO(vName, strValue) && verifyReBankNO(vName, strValue, boxName)
	}
	if (vType.toUpperCase() == "CARDSEX") {
		passFlag = verifyCardSex(vName, strValue, vType, boxName)
	}
	return passFlag
}
function verifyIdCardExcel(vName, strValue, vType, cboxName) {
	strValue = trim(strValue);
	if (strValue == "") {
		arrVerifyErrInfo.push("请输入" + vName + "\n");
		return false
	}
	var id_index = cboxName.split("_")[1];
	var idtype_value = jQuery("#sdrecognizeeTypeName_" + id_index).val();
	var birthday_value = jQuery("#sdrecognizeeBirthday_" + id_index).val();
	if (idtype_value.indexOf("身份证") != -1) {
		if (!checkId(strValue, "")) {
			arrVerifyErrInfo.push("请输入正确的身份证号！");
			return false
		}
		var year = strValue.substring(6, 10);
		var month = strValue.substring(10, 12);
		var day = strValue.substring(12, 14);
		var birthday_value_count = year + "-" + month + "-" + day;
		if (trim(birthday_value_count) != trim(birthday_value)) {
			arrVerifyErrInfo.push("身份证计算的出生日期与输入的出生日不一致！");
			return false
		}
	}
	return true
}
function verifyOnlyOne(vName, strValue, vType, cboxName) {
	var tFlag = false;
	if (vType.indexOf("=") != -1) {
		var compare_val = vType.split("=")[1];
		if (strValue == compare_val) {
			var id_name = cboxName.split("_")[0];
			jQuery("select[id^='" + id_name + "']").each(function() {
				var id = jQuery(this).attr("id");
				if (id != cboxName && jQuery(this).val() == strValue) {
					arrVerifyErrInfo.push(vName + "为“" + compare_val + "”的不能多于1个！ ");
					tFlag = true;
					return
				}
			})
		}
	}
	if (tFlag) {
		return false
	}
	return true
}
function verifyCodeType(vName, strValue, vType, cboxName) {
	jQuery.ajax({
		url: sinosoft.base + "/shop/order_config_new!ajaxValidateCode.action?supplierCode2=excel&codeType=" + vType + "&codeName=" + encodeURIComponent(encodeURIComponent(strValue)),
		dataType: "json",
		async: false,
		success: function(data) {
			var appCode = data.codeNameFlag;
			if (appCode != "Y") {
				arrVerifyErrInfo.push("请选择正确的" + vName + "！");
				return false
			}
		}
	});
	return true
}
function verifyIdCard(vName, strValue) {
	var SystemDate = new Date();
	var year = SystemDate.getFullYear();
	var month = SystemDate.getMonth() + 1;
	var day = SystemDate.getDate();
	var yyyy;
	var mm;
	var dd;
	var birthday;
	var sex;
	var id = strValue;
	var id_length = id.length;
	if (id_length == 0) {
		arrVerifyErrInfo.push("请输入身份证号码!");
		return false
	}
	if (id_length != 15 && id_length != 18) {
		arrVerifyErrInfo.push("身份证号长度应为18位！");
		return false
	}
	if (id_length == 15) {
		for (var i = 0; i < id_length; i++) {
			if (isNaN(id.charAt(i))) {
				arrVerifyErrInfo.push("15位身份证号中不能有字符！");
				return false
			}
		}
		yyyy = "19" + id.substring(6, 8);
		mm = id.substring(8, 10);
		dd = id.substring(10, 12);
		if (mm > 12 || mm <= 0) {
			arrVerifyErrInfo.push("身份证号月份非法！");
			return false
		}
		if (dd > 31 || dd <= 0) {
			arrVerifyErrInfo.push("身份证号日期非法！");
			return false
		}
		if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && (dd > 30)) {
			arrVerifyErrInfo.push("身份证号日期非法！");
			return false
		}
		if (mm == 2) {
			if (LeapYear(yyyy)) {
				if (dd > 29) {
					arrVerifyErrInfo.push("身份证号日期非法！");
					return false
				}
			} else {
				if (dd > 28) {
					arrVerifyErrInfo.push("身份证号日期非法！");
					return false
				}
			}
		}
	} else {
		for (var i = 0; i < id_length - 1; i++) {
			if (isNaN(id.charAt(i))) {
				arrVerifyErrInfo.push("身份证号中前17位中不能有字符！");
				return false
			}
		}
		if (isNaN(id.charAt(17)) && id.charAt(17) != "X" && id.charAt(17) != "x") {
			arrVerifyErrInfo.push("身份证校验错误，请认真检查！");
			return false
		}
		if (id.indexOf("X") > 0 && id.indexOf("X") != 17 || id.indexOf("x") > 0 && id.indexOf("x") != 17) {
			arrVerifyErrInfo.push('身份证中"X"输入位置不正确！');
			return false
		}
		yyyy = id.substring(6, 10);
		if (yyyy > year || yyyy < 1900) {
			arrVerifyErrInfo.push("身份证号年度非法！");
			return false
		}
		mm = id.substring(10, 12);
		if (mm > 12 || mm <= 0) {
			arrVerifyErrInfo.push("身份证号月份非法！");
			return false
		}
		if (yyyy == year && mm > month) {
			arrVerifyErrInfo.push("身份证号月份非法！");
			return false
		}
		dd = id.substring(12, 14);
		if (dd > 31 || dd <= 0) {
			arrVerifyErrInfo.push("身份证号日期非法！");
			return false
		}
		if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && (dd > 30)) {
			arrVerifyErrInfo.push("身份证号日期非法！");
			return false
		}
		if (mm == 2) {
			if (LeapYear(yyyy)) {
				if (dd > 29) {
					arrVerifyErrInfo.push("身份证号日期非法！");
					return false
				}
			} else {
				if (dd > 28) {
					arrVerifyErrInfo.push("身份证号日期非法！");
					return false
				}
			}
		}
		if (yyyy == year && mm == month && dd > day) {
			arrVerifyErrInfo.push("身份证号日期非法！");
			return false
		}
		if (id.charAt(17) == "x" || id.charAt(17) == "X") {
			if ("x" != GetVerifyBit(id) && "X" != GetVerifyBit(id)) {
				arrVerifyErrInfo.push("身份证校验错误，请认真检查！");
				return false
			}
		} else {
			if (id.charAt(17) != GetVerifyBit(id)) {
				arrVerifyErrInfo.push("身份证校验错误，请认真检查！");
				return false
			}
		}
	}
	return true
}
function verifyName(vName, strValue) {
	var i = /((^[\u4E00-\u9FA5]+(\·)?[\u4E00-\u9FA5]+$)|(^([A-Za-z]+\s?)*[A-Za-z]$))/;
	if (!strValue.match(i)) {
		arrVerifyErrInfo.push("请输入正确的姓名");
		return false
	}
	return true
}
function verifyEnglish(vName, strValue) {
	var i = /^([A-Za-z]+\s?)*[A-Za-z]$/;
	if (!strValue.match(i)) {
		arrVerifyErrInfo.push("请输入正确的英文名");
		return false
	}
	return true
}
function verifyHOURSETYPE(vName, strValue) {
	if (strValue == "-1") {
		arrVerifyErrInfo.push("请选择房屋类型\n");
		return false
	}
	return true
}
function verifyHOURSEAGE(vName, strValue) {
	if (strValue == "-1") {
		arrVerifyErrInfo.push("请选择房屋年龄\n");
		return false
	}
	return true
}
function verifyNotNaN(vName, strValue) {
	if (strValue != "" && isNaN(strValue)) {
		arrVerifyErrInfo.push(vName + " 不是有效的数字!\n");
		return false
	}
	return true
}
function verifyMustNull(vName, strValue) {
	if (strValue != "") {
		arrVerifyErrInfo.push(vName + " 必须为空!\n");
		return false
	}
	return true
}
function verifyNotNull(vName, strValue, cboxName) {
	strValue = trim(strValue);
	if (strValue == "") {
		if (cboxName.indexOf("showOutGoingParpose") != -1) {
			art.dialog.alert("请选择旅游目的地");
			arrVerifyErrInfo.push("请选择" + vName + "\n")
		} else {
			arrVerifyErrInfo.push("请输入" + vName + "\n")
		}
		return false
	}
	return true
}
function verifyTRAVEL(vName, strValue, cboxName) {
	var comCode = companyCode;
	var r = /\s+/g;
	var s = strValue.replace(r, " ");
	if (comCode == "2071") {
		var len = s.length;
		if (len >= 51) {
			arrVerifyErrInfo.push("旅游目的地长度应小于等于50");
			return false
		}
	} else {
		var arr = s.split(" ");
		var alen = arr.length;
		var tlen = 11;
		if (s.indexOf("申根国家") != -1) {
			tlen = 12
		}
		if (alen >= tlen) {
			arrVerifyErrInfo.push("旅游目的地国家数应小于等于10");
			return false
		}
	}
	return true
}
function verifyNumber(vName, strValue) {
	if (strValue != "" && !isNumeric(strValue)) {
		arrVerifyErrInfo.push(vName + " 不是有效的数字\n");
		return false
	}
	return true
}
function verifyAge(vName, strValue) {
	if (!CheckAge(strValue)) {
		arrVerifyErrInfo.push("被保人年龄应在正常出生满1天-150周岁\n");
		return false
	}
	if (!validateBirthdayExcel(strValue)) {
		arrVerifyErrInfo.push("此年龄不能购买本产品!");
		return false
	}
	return true
}
function verifyBirAge(vName, strValue, boxName) {
	var idType = document.getElementById("recognizeeTypeId_" + boxName.split("_")[1]);
	var idType_index = idType.selectedIndex;
	var idType_value = idType.options[idType_index].text;
	if (idType_value.indexOf("身份证") != -1) {
		var year = strValue.substring(6, 10);
		var month = strValue.substring(10, 12);
		var day = strValue.substring(12, 14);
		return verifyAge(vName, year + "-" + month + "-" + day)
	} else {
		return true
	}
}
function verifyAppAge(vName, strValue) {
	if (!CheckAppAge(strValue)) {
		var appAgeTips = $("#appAgeTips").val();
		if(appAgeTips != ""){
			arrVerifyErrInfo.push(appAgeTips);
		}else{
			arrVerifyErrInfo.push("太年轻啦~18周岁以上的成年人才能当投保人呢");
		}
		jQuery("#" + vName).val("");
		return false
	}
	return true
}
function verifyBIRAppAge(vName, strValue, boxName) {
	var idType = document.getElementById("applicantTypeId");
	var idType_index = idType.selectedIndex;
	var idType_value = idType.options[idType_index].text;
	if (idType_value.indexOf("身份证") != -1) {
		var year = strValue.substring(6, 10);
		var month = strValue.substring(10, 12);
		var day = strValue.substring(12, 14);
		return verifyAppAge(vName, year + "-" + month + "-" + day)
	} else {
		return true
	}
}
function verifyDate(vName, strValue) {
	if (strValue != "" && !isDate(strValue)) {
		arrVerifyErrInfo.push("请输入正确的" + vName + "\n");
		return false
	}
	return true
}
function verifyRecommEmail(vName, strValue) {
	if (strValue != "") {
		var s = strValue;
		var i = 1;
		var len = s.length;
		if (len > 50) {
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false
		}
		var pos1 = s.indexOf("@");
		var pos2 = s.indexOf(".");
		var pos3 = s.lastIndexOf("@");
		var pos4 = s.lastIndexOf(".");
		if ((pos1 <= 0) || (pos1 == len) || (pos2 <= 0) || (pos2 == len)) {
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false
		} else {
			if ((pos1 == pos2 - 1) || (pos1 == pos2 + 1) || (pos1 != pos3) || (pos4 < pos3)) {
				arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
				return false
			}
		}
		if (!isCharsInBag(s, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@")) {
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false
		}
		var badChar = "><,[]{}?/+=|:;!#$%^&()`";
		if (isCharsInBag(s, badChar)) {
			arrVerifyErrInfo.push("您的邮件地址有误，请仔细查看");
			return false
		}
	}
	return true
}
function verifyEmail(vName, strValue) {
	var i = /^[A-Za-z0-9-_.]+[@][A-Za-z0-9]+([-_.][A-Za-z0-9]+)?(\.com|\.cn|\.net|\.org|\.hk|\.uk|\.cc|\.edu)$/;
	if (!strValue.match(i)) {
		arrVerifyErrInfo.push("请输入正确的电子邮箱");
		return false
	}
	return true
}
function verifyDecimal(vName, strValue) {
	if (strValue != "" && (parseFloat(strValue) <= 0 || parseFloat(strValue) > 1)) {
		arrVerifyErrInfo.push(vName + " 不是0到1之间的小数!\n");
		return false
	}
	return true
}
function verifyInteger(vName, strValue) {
	if (strValue != "" && !isInteger(strValue)) {
		arrVerifyErrInfo.push(vName + " 只能是整数数值，不能包含其它字符!\n");
		return false
	}
	return true
}
function verifyPStyle(vName, strValue) {
	var patten = /(((\([0-9]{3,4}\)){1}|([0-9]{3,4}\-){1})([1-9]{1}[0-9]{6,7})(\-\d{3,4})*)|(0*1[0-9]{10})$/;
	if (strValue != "" && !patten.test(strValue)) {
		arrVerifyErrInfo.push(vName + "不是有效的电话号码!\n正确的电话格式包括：\n(0574)12345678\n(0574)1234567\n(574)12345678\n(574)1234567\n0574-12345678\n0574-12345678-1234\n13012345678\n013012345678");
		return false
	}
	return true
}
function verifyPartLen(vName, strValue, vType) {
	var indexNega = strValue.indexOf("-");
	var indexPoint = strValue.indexOf(".");
	var intFlag = indexPoint;
	if ("-1" == indexPoint) {
		indexPoint = strValue.length
	}
	var subValue = strValue.substring(parseInt(indexNega) + 1, indexPoint);
	var key = vType.substring(6);
	var split = key.split(",");
	var pre = split[0];
	var later = split[1];
	var flag = true;
	if ("-1" == intFlag) {
		var len = parseInt(pre) - parseInt(later);
		if (!verifyLength(vName, subValue, "len<=" + len)) {
			while (arrVerifyErrInfo != "") {
				arrVerifyErrInfo.pop()
			}
			arrVerifyErrInfo.push(vName + "是整数时长度需要小于等于" + len + "\n");
			return false
		}
	} else {
		var len = parseInt(pre) - parseInt(later) - 1;
		flag = verifyLength(vName + "整数部分", subValue, "len<=" + len)
	}
	return flag
}
function verifyMoney(vName, strValue, vType) {
	var strOperLen = vType.substring(6);
	strValue = strValue.trim();
	var otherValue;
	if (strValue == "") {
		return true
	}
	if (strValue.substring(0, 1) == "-") {
		strValue = strValue.substring(1)
	}
	var array = strOperLen.split("-");
	if (array.length > 2) {
		arrVerifyErrInfo.push(vName + " 的校验格式不正确!\n");
		return false
	}
	var p = array[0];
	var s = array[1];
	var indexPoint = strValue.indexOf(".");
	if ("-1" != indexPoint) {
		otherValue = strValue.substring(indexPoint + 1);
		if (otherValue.length > s) {
			arrVerifyErrInfo.push(vName + "小数部分的长度需要小于等于" + s + "!\n");
			return false
		}
		strValue = strValue.substring(0, indexPoint)
	}
	var len = p - s;
	if (strValue.length > len) {
		arrVerifyErrInfo.push(vName + "整数部分的长度需要小于等于" + len + "!\n");
		return false
	}
	return true
}
function verifyLength(vName, strValue, vType) {
	var oper;
	var len;
	var lenStr = 0;
	var comCode = "";
	if (typeof (companyCode) != "undefined" && companyCode != null && companyCode != "") {
		comCode = companyCode
	}
	var strOperLen = vType.substring(3);
	if (strValue == "") {
		return true
	}
	if (isNaN(parseInt(strOperLen.substring(1)))) {
		oper = strOperLen.substring(0, 2);
		len = strOperLen.substring(2)
	} else {
		oper = strOperLen.substring(0, 1);
		len = strOperLen.substring(1)
	}
	for (var i = 0; i < strValue.length; i++) {
		var c = strValue.charCodeAt(i);
		if ((c >= 1 && c <= 126) || (65376 <= c && c <= 65439)) {
			lenStr = lenStr + 1
		} else {
			if (comCode == "2011" && "起飞地点" == vName) {
				lenStr = lenStr + 3
			} else {
				lenStr = lenStr + 2
			}
		}
	}
	if (comCode == "2011" && "起飞地点" == vName) {
		len = 32
	}
	switch (oper) {
		case "=":
			if (lenStr != parseInt(len)) {
				arrVerifyErrInfo.push(vName + "的长度需要等于" + len + "\n");
				return false
			}
			break;
		case ">":
			if (lenStr <= parseInt(len)) {
				arrVerifyErrInfo.push("这个" + vName + "太短了吧，好像没写全呀\n");
				return false
			}
			break;
		case "<":
			if (lenStr >= parseInt(len)) {
				arrVerifyErrInfo.push(vName + "的长度需要小于" + len + "\n");
				return false
			}
			break;
		case ">=":
			if (lenStr < parseInt(len)) {
				arrVerifyErrInfo.push("这个" + vName + "太短了吧，好像没写全呀\n");
				return false
			}
			break;
		case "<=":
			if (lenStr > parseInt(len)) {
				if (comCode == "2011") {
					arrVerifyErrInfo.push(vName + "的长度需要小于等于" + len + "，一个汉字是3个长度\n");
					return false
				} else {
					arrVerifyErrInfo.push(vName + "的长度需要小于等于" + len + "\n");
					return false
				}
			}
			break
	}
	return true
}
function verifyURL(vName, strValue) {
	if (strValue != "") {
		var strRegex = "^((([hH]{1}[tT]{2}[pP]{1}[sS]{1})|([hH]{1}[tT]{2}[pP]{1})|([fF]{1}[tT]{2}[pP]{1})|([rR]{1}[tT]{2}[sS]{1}[pP]{1})|([mM]{2}[sS]{1}))?://)?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z].[a-zA-Z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		var re = new RegExp(strRegex);
		if (re.test(strValue)) {
			return true
		} else {
			arrVerifyErrInfo.push(vName + "的格式不正确!\n");
			return false
		}
	}
	return true
}
function verifyUFO(vName, strValue) {
	if (strValue != "") {
		var strRegex = /^[^"',，。!@#$%&*()0-9]*$/;
		var re = new RegExp(strRegex);
		if (re.test(strValue)) {
			return true
		} else {
			arrVerifyErrInfo.push(vName + "不能有特殊字符哟，请仔细检查一下!\n");
			return false
		}
	}
	return true
}
function verifyCHI(vName, strValue) {
	if (strValue != "") {
		var strRegex = /^[\u4e00-\u9fa5]+$/i;
		var re = new RegExp(strRegex);
		if (re.test(strValue)) {
			return true
		} else {
			arrVerifyErrInfo.push("请输入正确的" + vName + "\n");
			return false
		}
	}
	return true
}
function verifyAREA(vName, strValue) {
	if (strValue.indexOf("-1") != -1) {
		arrVerifyErrInfo.push("了解您的所在地区，为您提供更精准服务\n");
		return false
	}
	return true
}
function verifyOCCU(vName, strValue) {
	if (strValue.indexOf("-1") != -1) {
		arrVerifyErrInfo.push("请填写您的职业，为您提供更精准的服务\n");
		return false
	}
	return true
}
function verifyIDCardType(vName, strValue) {
	if (strValue.indexOf("-1") != -1) {
		arrVerifyErrInfo.push("请填写您的证件类型，为您提供更精准的服务\n");
		return false
	}
	return true
}
function verifyAPPRELATION(vName, strValue) {
	if (strValue.indexOf("-1") != -1) {
		arrVerifyErrInfo.push("请选择您与投保人的关系吧\n");
		return false
	}
	return true
}
function verifyAPPRELATION2(vName, strValue) {
	if (strValue.indexOf("-1") != -1) {
		arrVerifyErrInfo.push("请选择您与被保人的关系吧\n");
		return false
	}
	return true
}
function verifySYSex(vName, strValue, boxName) {
	var sexnum = boxName;
	var cord_id = sexnum.substr(sexnum.length - 1, 1);
	var relValue = jQuery("#recognizeeRelation2_" + cord_id).find("option:selected").text();
	if (jQuery("#RdoMaleSex2_" + cord_id).attr("checked")) {
		if (relValue.indexOf("母女") != -1) {
			arrVerifyErrInfo.push("受益人关系与被保人性别不对应\n");
			return false
		}
	} else {
		if (jQuery("#RdoFemaleSex2_" + cord_id).attr("checked")) {
			if (relValue.indexOf("父子") != -1) {
				arrVerifyErrInfo.push("受益人关系与被保人性别不对应\n");
				return false
			}
		}
	}
	return true
}
function verifyBenefit(vName, strValue, boxName) {
	var text = trim(jQuery("#" + boxName).val());
	if (text.length == 0) {
		arrVerifyErrInfo.push("受益比例不能为空\n");
		return false
	} else {
		if (issyInteger(text) == false) {
			arrVerifyErrInfo.push("受益比例只能为大于0且小于等于100之间的整数\n");
			return false
		}
	}
	return true
}
function verifyBankCode(vName, strValue) {
	if (strValue.indexOf("-1") != -1) {
		arrVerifyErrInfo.push("请选择您的开户银行\n");
		return false
	}
	return true
}
function verifyBankNO(vName, strValue, boxName) {
	var reg = new RegExp("^[0-9]{8,}$");
	if (strValue == "" || !reg.test(strValue) || eval("/[" + strValue.substring(strValue.length - 1) + "]{" + strValue.length + "}/").test(strValue)) {
		arrVerifyErrInfo.push("请输入8位以上不包含符号的正确银行卡卡号，不能为相同数字哦\n");
		return false
	}
	return true
}
function verifyReBankNO(vName, strValue, boxName) {
	if (strValue != jQuery("#" + boxName.substring(2)).val()) {
		arrVerifyErrInfo.push("两次输入的银行卡号不一致\n");
		return false
	}
	return true
}
function issyInteger(str) {
	var regu = /^[-]{0,1}[0-9]{1,}$/;
	if (regu.test(str)) {
		if (str <= 0 || str > 100) {
			return false
		} else {
			return true
		}
	} else {
		return false
	}
}
function verifyRealLength(vName, strValue, vType) {
	var oper;
	var len;
	var strOperLen = vType.substring(4);
	if (strValue == "") {
		return true
	}
	var indexPoint = strValue.indexOf(".");
	if ("-1" != indexPoint) {
		var tlen = strValue.length;
		for (var i = tlen; i > indexPoint + 1; i--) {
			if (strValue.substring(i - 1, i) == "0" || strValue.substring(i - 1, i) == " ") {
				tlen = tlen - 1
			} else {
				break
			}
		}
		if (tlen == (indexPoint + 1)) {
			tlen = indexPoint
		}
		strValue = strValue.substring(0, tlen)
	}
	if (isNaN(parseInt(strOperLen.substring(1)))) {
		oper = strOperLen.substring(0, 2);
		len = strOperLen.substring(2)
	} else {
		oper = strOperLen.substring(0, 1);
		len = strOperLen.substring(1)
	}
	switch (oper) {
		case "=":
			if (strValue.length != parseInt(len)) {
				arrVerifyErrInfo.push(vName + "的长度需要等于" + len + "!\n");
				return false
			}
			break;
		case ">":
			if (strValue.length <= parseInt(len)) {
				arrVerifyErrInfo.push(vName + "的长度需要大于" + len + "!\n");
				return false
			}
			break;
		case "<":
			if (strValue.length >= parseInt(len)) {
				arrVerifyErrInfo.push(vName + "的长度需要小于" + len + "!\n");
				return false
			}
			break;
		case ">=":
			if (strValue.length < parseInt(len)) {
				arrVerifyErrInfo.push(vName + "的长度需要大于等于" + len + "!\n");
				return false
			}
			break;
		case "<=":
			if (strValue.length > parseInt(len)) {
				arrVerifyErrInfo.push(vName + "的长度需要小于等于" + len + "!\n");
				return false
			}
			break
	}
	return true
}
function verifyZipCode(vName, strValue) {
	if (strValue == "000000") {
		arrVerifyErrInfo.push("请输入正确的邮编\n");
		return false
	}
	var i = /^[0-9]{6}$/;
	if (!strValue.match(i)) {
		arrVerifyErrInfo.push("请输入正确的邮编\n");
		return false
	}
	return true
}
function verifyValue(vName, strValue, vType) {
	var oper;
	var Val;
	var strOperVal = vType.substring(5);
	if (strValue == "") {
		return true
	}
	if (isNaN(parseFloat(strOperVal.substring(1)))) {
		oper = strOperVal.substring(0, 2);
		Val = strOperVal.substring(2)
	} else {
		oper = strOperVal.substring(0, 1);
		Val = strOperVal.substring(1)
	}
	switch (oper) {
		case "=":
			if (parseFloat(strValue) != parseFloat(Val)) {
				arrVerifyErrInfo.push(vName + " 的值需要等于" + Val + "!\n");
				return false
			}
			break;
		case ">":
			if (parseFloat(strValue) <= parseFloat(Val)) {
				arrVerifyErrInfo.push(vName + " 的值需要大于" + Val + "!\n");
				return false
			}
			break;
		case "<":
			if (parseFloat(strValue) >= parseFloat(Val)) {
				arrVerifyErrInfo.push(vName + " 的值需要小于" + Val + "!\n");
				return false
			}
			break;
		case ">=":
			if (parseFloat(strValue) < parseFloat(Val)) {
				arrVerifyErrInfo.push(vName + " 的值需要大于等于" + Val + "!\n");
				return false
			}
			break;
		case "<=":
			if (parseFloat(strValue) > parseFloat(Val)) {
				arrVerifyErrInfo.push(vName + " 的值需要小于等于" + Val + "!\n");
				return false
			}
			break
	}
	return true
}
function verifyCode(vName, strValue, vType, boxName, returnCode) {
	var strCode = vType.substring(5);
	if (mVs == null) {
		var win = searchMainWindow(this);
		if (win == false) {
			win = this
		}
		mVs = win.parent.VD.gVCode
	}
	var arrCode = mVs.getVar(strCode);
	var passFlag = false;
	var arrRecord;
	var arrField;
	var recordNum;
	var fieldNum;
	var arrResult = new Array();
	var urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
	var sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
	var strCodeSelect = "";
	if (strValue == "") {
		return true
	}
	if (arrCode != false) {
		for (var i = 0; i < arrCode.length; i++) {
			for (var j = 0; j < 2; j++) {
				if (strValue == arrCode[i][j]) {
					if (typeof (returnCode) != "undefined") {
						return arrCode[i]
					}
					passFlag = true;
					break
				}
			}
		}
	} else {
		if (eval(boxName + ".CodeData") != "" && typeof (eval(boxName + ".CodeData")) != "undefined") {
			arrRecord = eval(boxName + ".CodeData").split("^");
			recordNum = arrRecord.length;
			for (i = 1; i < recordNum; i++) {
				arrField = arrRecord[i].split("|");
				fieldNum = arrField.length;
				arrResult[i - 1] = new Array();
				for (j = 0; j < fieldNum; j++) {
					arrResult[i - 1][j] = arrField[j]
				}
				strCodeSelect = strCodeSelect + "<option value=" + arrResult[i - 1][0] + ">";
				strCodeSelect = strCodeSelect + arrResult[i - 1][0] + "-" + arrResult[i - 1][1];
				strCodeSelect = strCodeSelect + "</option>"
			}
			mVs.addArrVar(strCode, "", arrResult);
			mVs.addVar(strCode + "Select", "", strCodeSelect);
			for (i = 0; i < arrResult.length; i++) {
				for (j = 0; j < 2; j++) {
					if (strValue == arrResult[i][j]) {
						if (typeof (returnCode) != "undefined") {
							return arrResult[i]
						}
						passFlag = true;
						break
					}
				}
			}
		} else {
			arrCode = window.showModalDialog(urlStr, "", sFeatures);
			if ((arrCode == false) || (arrCode == "")) {
				arrVerifyErrInfo.push("CODE查询功能错误，请与管理员联系！\n");
				return false
			} else {
				if (arrCode == "Code Query Faile") {
					arrVerifyErrInfo.push(vName + " 数据库代码查询失败，请与管理员联系！\n");
					return false
				} else {
					arrRecord = arrCode.split("^");
					recordNum = arrRecord.length;
					for (i = 1; i < recordNum; i++) {
						arrField = arrRecord[i].split("|");
						fieldNum = arrField.length;
						arrResult[i - 1] = new Array();
						for (j = 0; j < fieldNum; j++) {
							arrResult[i - 1][j] = arrField[j]
						}
						strCodeSelect = strCodeSelect + "<option value=" + arrResult[i - 1][0] + ">";
						strCodeSelect = strCodeSelect + arrResult[i - 1][0] + "-" + arrResult[i - 1][1];
						strCodeSelect = strCodeSelect + "</option>"
					}
					mVs.addArrVar(strCode, "", arrResult);
					mVs.addVar(strCode + "Select", "", strCodeSelect);
					for (i = 0; i < arrResult.length; i++) {
						for (j = 0; j < 2; j++) {
							if (strValue == arrResult[i][j]) {
								if (typeof (returnCode) != "undefined") {
									return arrResult[i]
								}
								passFlag = true;
								break
							}
						}
					}
				}
			}
		}
	}
	if (!passFlag) {
		arrVerifyErrInfo.push(vName + "输入错误！\n")
	}
	return passFlag
}
function verifyElementWrap(strInfo, strValue, boxName) {
	var strboxName, strfocus, strcolor, cleardata;
	strboxName = boxName;
	chkoldclass = "if(!" + strboxName + ".oldclass) \n" + strboxName + ".oldclass=" + strboxName + ".className;";
	eval(chkoldclass);
	if (!verifyElement(strInfo, strValue)) {
		strfocus = strboxName + ".onblur=" + strboxName + ".focus;";
		strcolor = strboxName + '.className="warn";';
		cleardata = strboxName + ".value='';";
		eval(strcolor);
		eval(cleardata);
		eval(strfocus);
		return false
	}
	strfocus = strboxName + ".onblur=null";
	strcolor = strboxName + ".className=" + strboxName + ".oldclass;";
	eval(strcolor);
	eval(strfocus);
	return true
}
function verifyElementWrap2(strInfo, strValue, boxName) {
	var strboxName, strfocus, strcolor, cleardata;
	strboxName = boxName;
	if (!strboxName.oldclass) {
		strboxName.oldclass = strboxName.className
	}
	if (!verifyElement(strInfo, strValue, strboxName)) {
		if (boxName.style.display = "none") {
			boxName.style.display = ""
		}
		var strboxid = strboxName.getAttribute("id");
		var t = jQuery("#" + strboxid).offset().top;
		jQuery("body,html").animate({
			scrollTop: t
		}, 500);
		strboxName.className = "warn";
		return false
	}
	strboxName.className = strboxName.oldclass;
	return true
}
function verifyInput2() {
	var formsNum = 0;
	var elementsNum = 0;
	var passFlag = true;
	var ageFlag = false;
	if ("undefined" != typeof productId && premProducts.indexOf(productId) > -1) {
		ageFlag = true
	}
	for (formsNum = 0; formsNum < window.document.forms.length; formsNum++) {
		for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++) {
			if (window.document.forms[formsNum].elements[elementsNum] != null && window.document.forms[formsNum].elements[elementsNum].id != null && window.document.forms[formsNum].elements[elementsNum].id.indexOf("SWFUpload") == -1 && window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != "undefined" && window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != null && window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != "") {
				if (window.document.forms[formsNum].elements[elementsNum].id.indexOf("recognizee") != -1) {
					if (jQuery("#recognizeeOperate").val() == "1" && jQuery("#mulInsuredFlag").val() != "rid_me" && window.document.forms[formsNum].elements[elementsNum].id.indexOf("_") != -1) {
						if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value, window.document.forms[formsNum].elements[elementsNum])) {
							passFlag = false;
							break
						}
					} else {
						if (jQuery("#recognizeeOperate").val() == "1" && jQuery("#mulInsuredFlag").val() == "rid_me" && window.document.forms[formsNum].elements[elementsNum].id.indexOf("_") == -1) {
							if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value, window.document.forms[formsNum].elements[elementsNum])) {
								passFlag = false;
								break
							}
						} else {
							if (jQuery("#recognizeeOperate").val() == "2" && window.document.forms[formsNum].elements[elementsNum].id.indexOf("_") != -1) {
								if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value, window.document.forms[formsNum].elements[elementsNum])) {
									passFlag = false;
									break
								}
							}
						}
					}
				} else {
					if (jQuery("#recognizeetg_fd").attr("checked") && window.document.forms[formsNum].elements[elementsNum].id.indexOf("favoree") != -1) {
						continue
					} else {
						if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value, window.document.forms[formsNum].elements[elementsNum])) {
							passFlag = false;
							break
						}
					}
				}
			}
		}
		if (!passFlag) {
			break
		}
	}
	return passFlag
}
function verifyInput3() {
	var formsNum = 0;
	var elementsNum = 0;
	var passFlag = true;
	for (formsNum = 0; formsNum < window.document.forms.length; formsNum++) {
		var flag = true;
		for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++) {
			if (window.document.forms[formsNum].elements[elementsNum] != null && window.document.forms[formsNum].elements[elementsNum].id != null && window.document.forms[formsNum].elements[elementsNum].id.indexOf("SWFUpload") == -1) {
				var id = window.document.forms[formsNum].elements[elementsNum].id;
				if (id.indexOf("identityType") >= 0) {
					var idType = document.getElementById(id);
					var idType_index = idType.selectedIndex;
					var idType_value = idType.options[idType_index].text;
					if (idType_value.indexOf("护照") != -1) {
						flag = true
					} else {
						flag = false
					}
				}
				if (window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != "undefined" && window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != null && window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify") != "") {
					if (id.indexOf("identityStartDate") >= 0 || id.indexOf("identityEndDate") >= 0) {
						if (!flag) {
							continue
						}
					}
					if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].getAttributeNode("verify").value, window.document.forms[formsNum].elements[elementsNum].value, window.document.forms[formsNum].elements[elementsNum])) {
						passFlag = false;
						break
					}
				}
			}
		}
		if (!passFlag) {
			break
		}
	}
	return passFlag
}
function verifyCheckDifferent(vName, strValue, boxName) {
	var ConfirmValue = boxName.substring(0, boxName.indexOf(".") + 1) + "Confirm" + boxName.substring(boxName.indexOf(".") + 1, boxName.length) + ".value";
	if (strValue != "" && eval(ConfirmValue) == "") {
		arrVerifyErrInfo.push(vName + "请在重复输入处填值!\n");
		return false
	}
	if (strValue != "" && eval(ConfirmValue) != "" && strValue != eval(ConfirmValue)) {
		arrVerifyErrInfo.push(vName + "两次输入有误!\n");
		return false
	}
	return true
}
function verifyPhone(vName, strValue, boxName) {
	strValue = trim(strValue);
	if (strValue == "") {
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false
	}
	var myreg = /^\d{11}$/;
	var re = new RegExp(myreg);
	if (!re.test(strValue)) {
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false
	}
	return true
}
function verifyMobileNo(vName, strValue, boxName) {
	strValue = trim(strValue);
	if (strValue == "") {
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false
	}
	var myreg = /^\d{11}$/;
	var re = new RegExp(myreg);
	if (!re.test(strValue)) {
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false
	}
	var r = /^(13|14|15|17|18)\d{9}$/;
	if (!r.test(strValue)) {
		arrVerifyErrInfo.push("一定要填写正确的手机号码，理赔时很关键哦！");
		return false
	}
	return true
}
function verifyPhone1(vName, strValue, boxName) {
	strValue = trim(strValue);
	if (strValue == "") {
		arrVerifyErrInfo.push("请输入电话");
		return false
	}
	var myreg = /^\d{11}$/;
	var re = new RegExp(myreg);
	if (!re.test(strValue)) {
		arrVerifyErrInfo.push("对不起，请输入正确的手机号，固话请加区号");
		return false
	}
	return true
}
function forconfirm() {
	var keycode = event.keyCode;
	if (keycode == "13" && document.activeElement.elementtype && document.activeElement.elementtype.indexOf("con") != -1) {
		var tconfirm = "fm.Confirm" + document.activeElement.name + ".style.display =''";
		var fconfirm = "fm.Confirm" + document.activeElement.name + ".focus()";
		var vconfirm = "fm.Confirm" + document.activeElement.name + ".value";
		var cconfirm = "fm.Confirm" + document.activeElement.name + ".value=''";
		var nconfirm = document.activeElement.name + "n.style.display ='none'";
		if (eval(vconfirm) != "" && eval(vconfirm) != document.activeElement.value) {
			eval(cconfirm)
		}
		if (document.activeElement.elementtype.indexOf("nacessary") != -1) {
			eval(nconfirm)
		}
		document.activeElement.style.display = "none";
		eval(tconfirm);
		eval(fconfirm);
		keycode = "00"
	}
	if (keycode == "13" && document.activeElement.elementtype && document.activeElement.elementtype.indexOf("firm") != -1) {
		var tconfirm = "fm." + document.activeElement.name.substring(7, document.activeElement.name.length) + ".style.display =''";
		var fconfirm = "fm." + document.activeElement.name.substring(7, document.activeElement.name.length) + ".focus()";
		var vconfirm = "fm." + document.activeElement.name.substring(7, document.activeElement.name.length) + ".value";
		var wconfirm = "fm." + document.activeElement.name.substring(7, document.activeElement.name.length) + ".className='warn'";
		var rconfirm = "fm." + document.activeElement.name.substring(7, document.activeElement.name.length) + ".className='confirm'";
		var nconfirm = document.activeElement.name.substring(7, document.activeElement.name.length) + "n.style.display =''";
		var econfirm = "fm." + document.activeElement.name.substring(7, document.activeElement.name.length) + ".elementtype.indexOf('nacessary')!=-1";
		var cconfirm = "fm." + document.activeElement.name.substring(7, document.activeElement.name.length) + ".value=''";
		if (eval(vconfirm) != document.activeElement.value) {
			alert("两次输入值不一致，请确认！");
			document.activeElement.value = "";
			eval(wconfirm);
			eval(cconfirm)
		} else {
			eval(rconfirm)
		}
		if (eval(econfirm)) {
			eval(nconfirm)
		}
		document.activeElement.style.display = "none";
		eval(tconfirm);
		eval(fconfirm);
		keycode = "00"
	}
	if (keycode == "13" && document.activeElement.name.indexOf("OccupationCode") != -1) {
		getdetailwork()
	}
}
function clearVerify() {
	var formsNum = 0;
	var elementsNum = 0;
	var passFlag = true;
	var boxName = "";
	var strcolor = "";
	for (formsNum = 0; formsNum < window.document.forms.length; formsNum++) {
		for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++) {
			if (window.document.forms[formsNum].elements[elementsNum].name == null || window.document.forms[formsNum].elements[elementsNum].name == "") {
				continue
			}
			boxName = window.document.forms[formsNum].name + "." + window.document.forms[formsNum].elements[elementsNum].name;
			if (eval(boxName + ".className") == "warnno" || eval(boxName + ".className") == "warn") {
				strcolor = boxName + ".className=" + boxName + ".oldclass;";
				eval(strcolor)
			}
		}
	}
}
function showVerify(boxName, strVerifyErrInfo, boxType) {
	var strTemp = "";
	clearVerify();
	if (typeof (boxType) == "undefined" || boxType == null || boxType == "") {
		boxType = "common"
	}
	if (boxType == "codeno") {
		strTemp = boxName + '.className="warnno";'
	} else {
		if (boxType == "common") {
			strTemp = boxName + '.className="warn";'
		}
	}
	eval(strTemp);
	strTemp = boxName + ".value=''";
	eval(strTemp);
	strTemp = boxName + ".focus()";
	eval(strTemp)
}
function verifyCodeSel(vName, strValue, vType, boxName) {
	var strboxName = boxName;
	var strCodeValue = strValue;
	var strCodeData = eval(strboxName + ".CodeData");
	var strQueryResult = "";
	var cacheFlag = false;
	var arrCode = null;
	var aCode = "";
	var bCode = "";
	var cCode = "";
	var strCode = "";
	var strEvent = "";
	if (strCodeValue == null || strCodeValue == "") {
		return true
	}
	if (strCodeData != null && strCodeData != "") {
		strQueryResult = strCodeData;
		cacheFlag = false;
		arrCode = decodeEasyQueryResult(strQueryResult, 0, 1);
		for (var m = 0; m < arrCode.length; m++) {
			if (strCodeValue == arrCode[m][0] || strCodeValue == arrCode[m][1]) {
				cacheFlag = true;
				break
			}
		}
		if (cacheFlag == false) {
			arrVerifyErrInfo.push(vName + "输入不符合数据库规定的取值范围！请查阅或双击输入框选择！\n");
			return false
		}
	} else {
		strEvent = eval(strboxName + ".ondblclick");
		if (strEvent == null) {
			return true
		}
		strCode = new String(strEvent);
		strCode = strCode.substring(strCode.indexOf("showCodeList") + 14);
		aCode = strCode.substring(0, strCode.indexOf("'"));
		if (strCode.indexOf("null") != -1) {
			strCode = strCode.substring(strCode.indexOf("null") + 5);
			if (strCode.indexOf("]") >= 0) {
				var bCodeArr = eval(strCode.substring(0, strCode.indexOf("]") + 1));
				if (typeof (bCodeArr) == "object") {
					var tLength = bCodeArr.length;
					for (var m = 0; m < tLength; m++) {
						bCode = bCode + bCodeArr[m];
						if (m < tLength - 1) {
							bCode = bCode + "|"
						}
					}
				} else {
					bCode = bCodeArr
				}
				strCode = strCode.substring(strCode.indexOf("]") + 3);
				cCode = strCode.substring(0, strCode.indexOf("'"))
			} else {
				bCode = strCode.substring(0, strCode.indexOf(","));
				bCode = eval(bCode);
				strCode = strCode.substring(strCode.indexOf(",") + 2);
				cCode = strCode.substring(0, strCode.indexOf("'"))
			}
			if (bCode == null || bCode == "null") {
				bCode = ""
			}
			if (cCode == null || cCode == "null") {
				cCode = ""
			}
		} else {
			bCode = "";
			cCode = ""
		}
		strCode = aCode + bCode + cCode;
		if (win.parent.VD.gVCode.getVar(strCode) != false) {
			cacheFlag = false;
			arrCode = win.parent.VD.gVCode.getVar(strCode);
			for (var m = 0; m < arrCode.length; m++) {
				if (strCodeValue == arrCode[m][0] || strCodeValue == arrCode[m][1]) {
					cacheFlag = true;
					break
				}
			}
			if (cacheFlag == false) {
				arrVerifyErrInfo.push(vName + "输入不符合数据库规定的取值范围！请查阅或双击输入框选择！\n");
				return false
			}
		}
	}
	return true
}
function verifyById(cId) {
	var passFlag = true;
	var obj = document.getElementById(cId);
	var elementsNum = 0;
	for (elementsNum = 0; elementsNum < obj.all.length; elementsNum++) {
		if (obj.all[elementsNum].getAttributeNode("verify").value != null && obj.all[elementsNum].getAttributeNode("verify").value != "") {
			if (!verifyElementWrap2(obj.all[elementsNum].getAttributeNode("verify").value, obj.all[elementsNum].value, "document.all('" + obj.all[elementsNum].name + "')")) {
				passFlag = false;
				break
			}
		}
	}
	return passFlag
}
function verifyADDRESS(vName, strValue) {
	var sum = 0;
	for (var i = 0; i < strValue.length; i++) {
		var c = strValue.charCodeAt(i);
		if ((c >= 1 && c <= 126) || (65376 <= c && c <= 65439)) {} else {
			sum++
		}
	}
	if (sum < 6) {
		arrVerifyErrInfo.push("请填写正确的通讯地址，不少于6个汉字\n");
		return false
	} else {
		return true
	}
}
function verifyFightTime(vName, strValue) {
	var effDate = jQuery("#effective").val() + " 00:00:00";
	var fallDate = jQuery("#fail").val() + " 23:59:59";
	if (strValue < effDate) {
		arrVerifyErrInfo.push("起飞时间应不早于起保日期\n");
		return false
	} else {
		if (strValue > fallDate) {
			arrVerifyErrInfo.push("起飞时间应不晚于止保日期\n")
		} else {
			return true
		}
	}
}
function trim(s) {
	return s.replace(/(^\s*)|(\s*$)/g, "")
}
function verifyByTrId(cId) {
	jQuery("#" + cId).find("td").each(function() {
		jQuery(this).find("em:eq(1)").each(function() {
			jQuery(this).find("input,select").each(function() {
				var verifyValue = jQuery(this).attr("excelverify");
				if (verifyValue != null && verifyValue != "" && typeof (verifyValue) != "undefined") {
					verifyElementToExcel(verifyValue, jQuery(this).attr("value"), jQuery(this).attr("id"))
				}
			})
		})
	})
}
function verifyElementToExcel(strInfo, strValue, boxName) {
	if (strValue == null) {
		strValue = ""
	}
	var cBoxName = "";
	if (typeof (boxName) != "object") {
		cBoxName = boxName
	} else {
		cBoxName = boxName.id
	}
	var strboxName = cBoxName;
	var strValue = trim(strValue);
	var passFlag = true;
	var vName;
	var vType;
	var intIndex;
	var typeStack = new Array();
	var operStack = new Array();
	while (arrVerifyErrInfo != "") {
		arrVerifyErrInfo.pop()
	}
	vName = strInfo.substring(0, strInfo.indexOf("|"));
	strInfo = strInfo.substring(strInfo.indexOf("|") + 1);
	while (strInfo != "") {
		if (strInfo.indexOf("|") != -1 && strInfo.indexOf("&") != -1) {
			intIndex = strInfo.indexOf("|") > strInfo.indexOf("&") ? strInfo.indexOf("&") : strInfo.indexOf("|");
			vType = strInfo.substring(0, intIndex);
			typeStack.push(verifyType(vName, vType, strValue, strboxName));
			operStack.push(strInfo.substring(intIndex, intIndex + 1));
			strInfo = strInfo.substring(intIndex + 1)
		} else {
			if (strInfo.indexOf("|") != -1 || strInfo.indexOf("&") != -1) {
				intIndex = strInfo.indexOf("|") > strInfo.indexOf("&") ? strInfo.indexOf("|") : strInfo.indexOf("&");
				vType = strInfo.substring(0, intIndex);
				typeStack.push(verifyType(vName, vType, strValue, strboxName));
				operStack.push(strInfo.substring(intIndex, intIndex + 1));
				strInfo = strInfo.substring(intIndex + 1)
			} else {
				vType = strInfo;
				strInfo = "";
				typeStack.push(verifyType(vName, vType, strValue, strboxName))
			}
		}
	}
	passFlag = typeStack[0];
	for (var k = 0; k < operStack.length; k++) {
		if (operStack[k] == "|") {
			typeStack[k + 1] = typeStack[k] | typeStack[k + 1]
		} else {
			if (operStack[k] == "&") {
				typeStack[k + 1] = typeStack[k] & typeStack[k + 1]
			} else {
				alert("校验参数设置有误")
			}
		}
		passFlag = typeStack[k + 1]
	}
	var strVerifyErrInfo = "\n";
	if (!passFlag) {
		jQuery("#" + cBoxName).parent().parent().addClass("td_hlight");
		showExcelError(cBoxName, arrVerifyErrInfo)
	} else {
		jQuery("#" + cBoxName).parent().parent().removeClass("td_hlight");
		deleteExcelError(cBoxName)
	}
	return passFlag
}
function showExcelError(cBoxName, mError) {
	var cError = mError[0];
	var errorFlag = "_error";
	var id_index = cBoxName.split("_")[1];
	var id_name = cBoxName.split("_")[0];
	var appntName = jQuery("#sdrecognizeeName_" + id_index).val();
	var tr_id = "recognizee_" + id_index;
	if (jQuery("#" + tr_id).length > 0) {
		var id = cBoxName + errorFlag;
		if (jQuery("#" + id).length > 0) {
			jQuery("#" + id).text(cError)
		} else {
			var cErrorInfo = "<i id='" + id + "'>" + cError + "</i>";
			jQuery("#" + tr_id).append(cErrorInfo)
		}
	} else {
		var id = cBoxName + errorFlag;
		var cErrorInfo = "<i id='" + id + "'>" + cError + "</i>";
		var cErrorInfo2 = "<div id='" + tr_id + "'>被保人" + appntName + "的录入项有错误 :<i id=" + id + ">" + cError + "</i></div>";
		jQuery("#importMessage").append(cErrorInfo2);
		jQuery("#importMessage").attr("style", "overflow:hidden;color:red")
	}
}
function deleteExcelError(cBoxName) {
	var errorFlag = "_error";
	var id_index = cBoxName.split("_")[1];
	var id_name = cBoxName.split("_")[0];
	var tr_id = "recognizee_" + id_index;
	var id = cBoxName + errorFlag;
	if (jQuery("#" + tr_id + " i").length > 0) {
		jQuery("#" + tr_id + " i").each(function() {
			if (id == jQuery(this).attr("id")) {
				jQuery("#" + id).remove()
			}
		})
	}
	if (jQuery("#" + tr_id + " i").length <= 0) {
		jQuery("#" + tr_id).remove()
	}
}
function checkId(value, element) {
	var aCity = {
		11: "北京",
		12: "天津",
		13: "河北",
		14: "山西",
		15: "内蒙古",
		21: "辽宁",
		22: "吉林",
		23: "黑龙江",
		31: "上海",
		32: "江苏",
		33: "浙江",
		34: "安徽",
		35: "福建",
		36: "江西",
		37: "山东",
		41: "河南",
		42: "湖北",
		43: "湖南",
		44: "广东",
		45: "广西",
		46: "海南",
		50: "重庆",
		51: "四川",
		52: "贵州",
		53: "云南",
		54: "西藏",
		61: "陕西",
		62: "甘肃",
		63: "青海",
		64: "宁夏",
		65: "新疆",
		71: "台湾",
		81: "香港",
		82: "澳门",
		91: "国外"
	};
	var iSum = 0;
	var strIDno = value;
	if (!/^\d{17}(\d|x)$/i.test(strIDno) && !/^\d{15}$/i.test(strIDno)) {
		if (!/^\d{16}(x{2})$/i.test(strIDno)) {
			return false
		}
	}
	if (aCity[parseInt(strIDno.substr(0, 2))] == null) {
		return false
	}
	var year = strIDno.substring(6, 10);
	if (year < 1900 || year > 2078) {
		return false
	}
	strIDno = strIDno.replace(/x$/i, "a");
	sBirthday = strIDno.substr(6, 4) + "-" + Number(strIDno.substr(10, 2)) + "-" + Number(strIDno.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, "/"));
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
		return false
	}
	for (var i = 17; i >= 0; i--) {
		iSum += (Math.pow(2, i) % 11) * parseInt(strIDno.charAt(17 - i), 11)
	}
	if (/^\d{16}(x{1})(a{1})$/i.test(strIDno)) {
		iSum = 12
	}
	if (iSum % 11 != 1) {
		return false
	}
	var words = new Array();
	words = new Array("11111119111111111","12121219121212121");
	for (var k = 0; k < words.length; k++) {
		if (strIDno.indexOf(words[k]) != -1) {
			return false
		}
	}
	return true
}
function validateBirthdayExcel(bId) {
	var effdate = jQuery("#effective").val();
	var tFlag = true;
	if (bId != null && bId != "" && "undefined" != typeof productId) {
		jQuery.ajax({
			url: sinosoft.base + "/shop/order_config_new!ajaxAge.action?applicantBirthday=" + bId + "&productId=" + productId + "&effdate=" + effdate,
			dataType: "json",
			type: "GET",
			async: false,
			success: function(data) {
				if (!data) {
					tFlag = false;
					return false
				} else {
					tFlag = true;
					calPremFlag = true;
					return true
				}
			}
		})
	}
	return tFlag
}
function verifyIframeForm(iframeDoc, formName) {
	var elementsNum = 0;
	var passFlag = true;
	for (elementsNum = 0; elementsNum < iframeDoc.all(formName).elements.length; elementsNum++) {
		if ($(iframeDoc.all(formName).elements[elementsNum]).attr("verify") != null && $(iframeDoc.all(formName).elements[elementsNum]).attr("verify") != "") {
			if (!verifyElement($(iframeDoc.all(formName).elements[elementsNum]).attr("verify"), iframeDoc.all(formName).elements[elementsNum].value, iframeDoc.all(formName).elements[elementsNum].name)) {
				passFlag = false;
				break
			}
		}
	}
	return passFlag
}
function verifyCardSex(vName, strValue, vType, cboxName) {
	var id_index = cboxName.split("_")[1];
	var card_value = jQuery("#sdrecognizeeId_" + id_index).val();
	var ctype = jQuery("#sdrecognizeeTypeName_" + id_index).val();
	if (ctype == "身份证") {
		var sexno;
		if (card_value.length == 18) {
			sexno = card_value.substring(16, 17)
		} else {
			if (card_value.length == 15) {
				sexno = card_value.substring(14, 15)
			}
		}
		var tempid = sexno % 2;
		if (tempid == 0) {
			if (strValue != "女") {
				arrVerifyErrInfo.push(vName + "与身份证不符\n");
				return false
			}
		} else {
			if (strValue != "男") {
				arrVerifyErrInfo.push(vName + "与身份证不符\n");
				return false
			}
		}
	}
	return true
}
;