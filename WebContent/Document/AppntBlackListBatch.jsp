<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../Framework/Main.js"></script>
<script type="text/javascript">
function getFileUrl(){
    var fileUrl=window.document.getElementById("upload").value;
    if(fileUrl==""){
		Dialog.alert("请选择文件! ");
	}else{
		if(fileUrl!=''&&/\S+\.xls$/.test(fileUrl)){
		    Dialog.wait("正在导入...");
			fm.submit();
			return;
		}else{
			Dialog.alert("请选择.xls格式的文件！");
		}
    }
}

function afterUpload(fileUrl){
	   var url = "com.sinosoft.cms.document.BlackListManage.AppntImportExcel";
	   var dc = new DataCollection();	
	    dc.add("fileaddress",fileUrl);
	    Server.sendRequest(url,dc,function(response){
	    	Dialog.closeEx();
	    	Dialog.alert(response.Message,function(){
		    	 if(response.Status==1){
			    	 
			     }
			});
	    });
	}
</script>
<title>上传</title>
<style>
#mid {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -50px 0 0 -120px;
	width: 250px;
	height: 100px;
}
</style>

</head>

<body>
<div style="display:none;"><iframe src="javascript:void(0);"
	name="targetFrame" width="0" height="0" frameborder="0"></iframe></div>
<div id=mid>
<form action="BlackListUploadSave.jsp" method="post" name="fm" enctype="multipart/form-data" target="targetFrame"> <br>
	<input name="upload" id="upload" type="file" value='' size='20' >
	<input onClick="getFileUrl();" type='button' value='上传' name="Import" id="Import">
	<a href="../DataService/ADTemplate/BlackList.xls">信息批量导入模板下载</a>
</form>
	<input name="ip" type="hidden" value="<%=request.getRemoteAddr()%>" style="width: 150px" class="inputText" id="ip" />
</div>
</body>
</html>