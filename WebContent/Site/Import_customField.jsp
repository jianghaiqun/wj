<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../Framework/Main.js"></script>
<script type="text/javascript">

	function getFileUrl() {
		var fileUrl=window.document.getElementById("upload").value;
	    if(fileUrl==""){
			Dialog.alert("请选择文件! ");
		}else{
			//如果选择了导入文件按上传文件导入
			if(fileUrl!=''&&/\S+\.xls$/.test(fileUrl)){
			    Dialog.wait("正在导入自定义字段信息...");
				fm.submit();
				return;
			}else{
				Dialog.alert("请选择.xls格式的文件！");
			}
	    }
	}
	function afterUpload(fileUrl){
		   var CatalogID = $V("CatalogID");
		   var dc = new DataCollection();	
		    dc.add("CatalogID",CatalogID);
		    dc.add("fileaddress",fileUrl);
		    Server.sendRequest("com.sinosoft.cms.site.CatalogColumn.uploadExcel",dc,function(response){
		    	Dialog.closeEx();
		    	Dialog.alert(response.Message,function(){
			    	 if(response.Status==1){
			    		try {
				    		window.parent.frames['_MainArea'].useByAdd_Batch();//frame
			    		} catch (ex) {
			    			if (window.parent.frames['_DialogFrame_Diag_MOUD_2']){
			    				window.parent.frames['_DialogFrame_Diag_MOUD_2'].useByAdd_Batch();//frame

			    			}
						}
			    		Dialog.close();
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
<form  action="ImportCustomFieldUploadSave.jsp" method="post" name="fm" enctype="multipart/form-data" target="targetFrame"> <br>
	<input name="upload" id="upload" type="file" value='' size='20' >
	<input onClick="getFileUrl();" type='button' value='上传' name="Import" id="Import">
	<a href="../DataService/ADTemplate/Import_customField.xls">自定义字段信息导入模板下载</a>
	<input name="CatalogID" id="CatalogID" value="<%=request.getParameter("CatalogID")%>" type="hidden" />
</form>
	<input name="ip" type="hidden" value="<%=request.getRemoteAddr()%>" style="width: 150px" class="inputText" id="ip" />
</div>
</body>
</html>