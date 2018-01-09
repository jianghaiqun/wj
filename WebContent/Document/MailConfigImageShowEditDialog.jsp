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
<style>
.ErrorMsg {
background:#FFF2E9   scroll 5px;
border:1px solid #FF6600;
color:#000000;
padding:5px 5px 5px 25px;
}
</style>
<script type="text/javascript">
function RepeatUpload(){
	if($V("RepeatFile")){
		$F("form2").submit();
	}else{
		var dc = new DataCollection();
		dc.add("MailImageID",$V("MailImageID"));
		dc.add("File1Name",$V("File1Name"));
		dc.add("File1Info", $V("File1Info"));
		
		Server.sendRequest("com.sinosoft.cms.document.MailConfigImage.updateMailImageInfo",
				dc, function() {
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							window.Parent.DataList.loadData("dg1");
							Dialog.close();
						}
					});
				});
	}
}
//"form2" 提交后回调
function onUploadCompleted( returnValue,errorMessage,returnID){
	
	if("1"== returnValue){
		Dialog.alert( errorMessage ,function(){
			try {
				//window.location = window.location;
				window.Parent.DataList.loadData("dg1");
				Dialog.close();
			}catch(ex){
				alert(ex);
			}
		}) ;
	}
	
}
</script>
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.document.MailConfigImage.initEditDialog">
<div style="display:none">
	<iframe name="formTarget" src="javascript:void(0)"></iframe></div>
<form id="form2" enctype="multipart/form-data"
		action="../Editor/filemanager/upload/ImageUploader.jsp" method="post"
		target="formTarget">
<input id="MailImageID" type="hidden" value="${ID}"/>
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td height="120" colspan="2" align="center" valign="middle"><br>
			<img src="../${ImagePath}" width="150px" height="150px"></td>
		</tr>
		<tr>
			<td height="10" align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">重新上传：</td>
			<td><input id="Repeat" name="Repeat" type="hidden" value="1" />
			<input id="RepeatID" name="RepeatID" type="hidden" value="${ID}" />
			<input type="hidden" id="FileType" name="FileType" value="Image"> 
			<input name='RepeatFile' id='RepeatFile' type='file' value='' size='30'>
			</td>
		</tr>
		<tr >
			<td align="right">图片链接:</td>
			<td><input id="File1Name"  name="File1Name" type="text"
				class="input1" size='50' value="${ImageLink}" /></td>
		</tr>
		<tr>
			<td align="right">图片描述：</td>
			<td><textarea id="File1Info" name="File1Info" cols="50" rows="2"
				class="input3" >${ImageDesc}</textarea></td>
		</tr>
		<tr>
			<td align="right">最后修改人：</td>
			<td>${Operator}</td>
		</tr>
		<tr>
			<td align="right">最后修改时间：</td>
			<td>${ModifyDate}</td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
