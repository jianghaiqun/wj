<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function RepeatUpload(){
	if(getUploader("RepeatFile").hasFile()){
		var dc = Form.getData($F("form4"));
		getUploader("RepeatFile").addPostParam(dc.toQueryString());
		getUploader("RepeatFile").doUpload();
		$E.disable(window.Parent.$D.CancelButton);
		$E.disable(window.Parent.$D.OKButton);
	}else{
		Dialog.alert("请选择文件");	
	}
}

function onUploadCompleted(){
	window.Parent.onFileUploadCompleted();
}
</script>
</head>
<body class="dialogBody">
<z:init>
<form id="form4">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr height="20">
        <td width="20%">&nbsp;</td>
        <td width="80%">&nbsp;</td>
    </tr>
    <tr>
        <td width="20%" align="right">重新上传：</td>
        <td width="80%"><input id="Repeat" name="Repeat" type="hidden" value="1" />
        <input id="RepeatID" name="RepeatID" type="hidden" value="${ID}" /> 
        <input type="hidden" id="FileType" name="FileType" value="CustomClaimsData">
        <z:uploader id="RepeatFile" action="../Editor/filemanager/upload/zuploader.jsp" onComplete="onUploadCompleted" fileCount="1" allowtype="${suffix}"/>
        </td>
    </tr>
</table>
</form>
</z:init>
</body>
</html>