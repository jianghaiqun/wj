/*
 * 订单导入
 */
 
function init() {
	if ($V("plan") != null && $V("plan") != "") {
		document.getElementById("planTR").style.display="";
	}
	if ($V("occup") != null && $V("occup") !="") {
		document.getElementById("occupTR").style.display="";
	}
	if ($V("textAge") != null && $V("textAge") != "") {
		document.getElementById("textAgeTR").style.display="";
	}
	if ($V("appType") != null && $V("appType") !="") {
		document.getElementById("appTypeTR").style.display="";
	}
	if ($V("feeYear") != null && $V("feeYear") != "") {
		document.getElementById("feeYearTR").style.display="";
	}
	if ($V("grade") != null && $V("grade") !="") {
		document.getElementById("gradeTR").style.display="";
	}
	if ($V("appLevel") != null && $V("appLevel") != "") {
		document.getElementById("appLevelTR").style.display="";
	}
	if ($V("mulPeople") != null && $V("mulPeople") !="") {
		document.getElementById("mulPeopleTR").style.display="";
	}
}

/**
 * 检查上传文件格式
 */
function getFileSuff() {
	var file = document.getElementById("importInsureFile").value;
	var ss = file.split(".");
	if (ss.length == 2) {
		if (ss[1] == "xls") {
			document.getElementById("importBtn").disabled = false;
			return true;
		} else {
			Dialog.alert("请上传扩展名为xls的文件！");
			document.getElementById("importBtn").disabled = true;
		}
	} else {
		Dialog.alert("请上传扩展名为xls的文件！");
		document.getElementById("importBtn").disabled = true;
	}
	return false;
}