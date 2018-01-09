/*
 * 评论导入
 */

/**
 * 检查上传文件格式 
 */
function getFileSuff() {
	var file = document.getElementById("importCommentFile").value;
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

/**
 * 上传按钮的处理
 * @returns {Boolean}
 */
function ImportExcel() {
	var file = document.getElementById("importCommentFile").value;
	if (file == null || file == '') {
		Dialog.alert("请选择上传的文件！");
		return false;
	}
	
	Dialog.confirm("确认要导入？",function() { 
		Dialog.wait("正在生成......"); 
		fm.submit();
	});
}
