<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.underwriting.UnderwritingOfflineImages.initDialog" >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function upload(){
	if(Verify.hasError()){
		return;
	}
	if(getUploader("AttachFile").hasFile()&&!getUploader("AttachFile").hasError()){
		var dc = Form.getData("form3");
		getUploader("AttachFile").addPostParam(dc.toQueryString());
		getUploader("AttachFile").doUpload();
		$E.disable(window.Parent.$D.CancelButton);
		$E.disable(window.Parent.$D.OKButton);
	}else{
		Dialog.alert("未选择文件或文件选择有误");	
	}
}

/** 可选参数FileIDs,FileTypes,FilePaths,FileStatus */
function onUploadCompleted(FileIDs){
	window.Parent.onFileUploadCompleted();
}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<form id="form3" >
<input type="hidden" id="FileType" name="FileType" value="UnderwritingOffline">
<input type="hidden" id="infoID" name="infoID" value="${infoID}">
<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
			<td  valign="top">
			<fieldset><legend> <strong>图片上传:</strong></legend>
			<table id="Videotable0" style="display:" width="100%" cellpadding="2"
				cellspacing="0">
				<tr id="tr1">
					<td width="16%" align="right">上传说明：</td>
					<td width="84%" colspan="2">允许大小 ${fileMaxSizeHuman} <br>支持格式 ${allowType}等文件</td>
				</tr>
				<tr id="tr2">
					<td align="right">文件浏览：</td>
					<td colspan="2">
                    <z:uploader id="AttachFile" action="../Editor/filemanager/upload/zuploader.jsp" onComplete="onUploadCompleted" allowtype="${allowType}" fileMaxSize="${fileMaxSize}" fileCount="1"/>
						</td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
</table>
</form>
<div id="divMsg" align="center"></div>
</body>
</html>
</z:init>