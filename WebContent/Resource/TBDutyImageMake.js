/**
 * 淘宝责任图片生成
 */
/**
 * 检查上传文件格式 
 */
function getFileSuff() {
	var file = document.getElementById("importDutyFile").value;
	var ss = file.substring(file.lastIndexOf(".")+1);

	if (ss == "xls") {
		document.getElementById("importBtn").disabled = false;
		return true;
	} else {
		Dialog.alert("请上传扩展名为xls的文件！");
		document.getElementById("importBtn").disabled = true;
	}
	
	return false;
}

/**
 * 上传按钮的处理
 * @returns {Boolean}
 */
function ImportExcel() {
	var file = document.getElementById("importDutyFile").value;
	if (file == null || file == '') {
		Dialog.alert("请选择上传的文件！");
		return false;
	}
	document.getElementById("result").innerHTML="";
	Dialog.confirm("确认要导入？",function() { 
		Dialog.wait("正在生成......"); 
		fm.submit();
	});
}

function afterUpLoad(flag, content,path) {
	Dialog.closeEx();
	if (flag == "Succ") {
		document.getElementById("result").display="";
		document.getElementById("result").innerHTML="<a href='"
			+ path + "' target='_blank' >生成图片页面,点此打开</a>";
	} else if (flag == "Fail") {
		document.getElementById("result").display='none';
	}
	Dialog.alert(content);

}