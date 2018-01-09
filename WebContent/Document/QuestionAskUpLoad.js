/*
 * 问答导入
 */
 
/**
 * 检查上传文件格式
 */
function getFileSuff() {
	var file = document.getElementById("importWendaFile").value;
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
 * 选择菜单
 * @param ele
 */
function onTreeClick(ele) {
	var cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	if(!Tree.isRoot(ele)){
		Cookie.set("DocList.LastCatalog",cid,"2100-01-01");
		Cookie.set("DocList.LastCatalogCode",code,"2100-01-01");
	}else{
		Cookie.set("DocList.LastCatalog","0","2100-01-01");
	}
	document.getElementById("treeId").value = ele.id;
	if (ele.id == 'tree1__TreeRoot') {
		document.getElementById("importBtn").disabled = true;
	} else {
		document.getElementById("importBtn").disabled = false;
	}
}

/**
 * 上传按钮的处理
 * @returns {Boolean}
 */
function ImportExcel() {
	var id = document.getElementById("treeId").value;
	if (id == null || id == '' || id == 'tree1') {
		Dialog.alert("请选择左侧的菜单！");
		return false;
	}
	id = id.replace("tree1_", "");
	document.getElementById("treeId").value = id;
	var file = document.getElementById("importWendaFile").value;
	if (file == null || file == '') {
		Dialog.alert("请选择上传的文件！");
		return false;
	}
	
	Dialog.confirm("确认要导入？",function() { 
		Dialog.wait("正在生成......");
		fm.action = "QuestionAskUpLoadSave.jsp?treeId=" + id; 
		fm.submit();
	});
}
