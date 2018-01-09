<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.cms.dataservice.CustomerServiceManage.uploadInit" >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
});

var extsStr = '${allowType}';
var exts = extsStr.split(",");
function upload(){
	if(Verify.hasError()){
		return;
	}
	var flag = false;
	var count = 4;
	for(var i=1;i<=count;i++){
		var fileName = $V("File"+i);
		var ext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		if(fileName==""){
			continue;
		}
		flag=false;
		for(var j=0; j<exts.length; j++) {
			if(ext.trim()==exts[j].toLowerCase().trim()) {
				flag = true;
			}
		}
		if(!flag) {
			Dialog.alert("附件上传不支持"+ext+"文件，请重新选择！");
			return;
		}
	}
	if(flag){
		$E.disable(window.Parent.$D.CancelButton);
	    $E.disable(window.Parent.$D.OKButton);
		msg();
		$("divMsg").className="ErrorMsg";
		$F("form2").submit();
	}
}
var counter=1;
function msg(){
	var txt = "正在上传处理中，请稍后...耗时";
	setInterval(function(){$("divMsg").innerHTML="<font color=red>"+txt+counter+"秒</font>";counter++}, 1000);
}
function onUploadCompleted(returnValue,errorMessage,returnID){
	switch ( returnValue )
	{
		case 0 :	// No errors
			break ;
		case 1 :	// 
			$("divMsg").hide();
			$E.disable(window.Parent.$D.CancelButton);
		    $E.disable(window.Parent.$D.OKButton);
		    Dialog.alert(errorMessage ,function(){
				try {
					window.Parent.onFileUploadCompleted(returnID);
				}catch(ex){
				}
			}) ;
			return ;
		default :
			Dialog.alert(errorMessage ,function(){
				try {
					window.parent.Dialog.close();
				}catch(ex){
				}
			}) ;
			return ;
	}
}
function setNameInfo(ele){
	var name = getExtname(ele.value);
	name = name.substring(0,name.lastIndexOf("."));
	var id =ele.id;
	$S(id+"Name",name);
	$S(id+"Info",name);
}

function getExtname(filename){
	var pos = filename.lastIndexOf("\\");
	return (filename.substr(pos+1));
}

function doPreview(sender){   
    var flag = 0;
    for(var i=0; i<exts.length; i++) {
	  	if(sender.value.toLowerCase().trim().endsWith("." + exts[i].toLowerCase().trim())) {
			flag = 1;
	  	}
	}
    if(flag == 0) {
    	Dialog.alert('文件格式无效！');
        return false;   
	}
} 
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div style="display:none"><iframe name="formTarget"
	src="javascript:void(0)"></iframe></div>
<form id="form2" action="../Editor/filemanager/upload/CustomerServiceFileUploader.jsp" method="post" target="formTarget" enctype="multipart/form-data">
<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
			<td  valign="top">
			<fieldset><legend><strong>附件:</strong></legend>
			<table id="Videotable0" width="100%" cellpadding="2"
				<tr>
					<td width="6%" align="right"><label> </label></td>
					<td width="34%"><label> 附件浏览</label></td>
					<td width="30%">附件名称</td>
					<td width="30%">附件描述</td>
				</tr>
				<tr>
					<td width='6%' align="right"><label>1:</label></td>
					<td><input name='File1' id='File1' type='file' value=''
						size='30' onChange="setNameInfo(this);doPreview(this);"></td>
					<td><input name="File1Name" id="File1Name" type="text"
						value="" verify="附件名称|NotNull" condition="$V('File1').trim()!=''"></td>
					<td><input name="File1Info" id="File1Info" type="text"
						value="" verify="附件描述|NotNull" condition="$V('File1').trim()!=''"></td>
				</tr>
				<tr>
					<td align="right"><label>2:</label></td>
					<td><input name='File2' id='File2' type='file' value=''
						size='30' onChange="setNameInfo(this);doPreview(this);"></td>
					<td><input name="File2Name" id="File2Name" type="text"
						value="" verify="附件名称|NotNull" condition="$V('File2').trim()!=''"></td>
					<td><input name="File2Info" id="File2Info" type="text"
						value="" verify="附件描述|NotNull" condition="$V('File2').trim()!=''"></td>
				</tr>
				<tr>
					<td align="right"><label>3:</label></td>
					<td><input name='File3' id='File3' type='file' value=''
						size='30' onChange="setNameInfo(this);doPreview(this);"></td>
					<td><input name="File3Name" id="File3Name" type="text"
						value="" verify="附件名称|NotNull" condition="$V('File3').trim()!=''"></td>
					<td><input name="File3Info" id="File3Info" type="text"
						value="" verify="附件描述|NotNull" condition="$V('File3').trim()!=''"></td>
				</tr>
				<tr>
					<td align="right"><label>4:</label></td>
					<td><input name='File4' id='File4' type='file' value=''
						size='30' onChange="setNameInfo(this);doPreview(this);"></td>
					<td><input name="File4Name" id="File4Name" type="text"
						value="" verify="附件名称|NotNull" condition="$V('File4').trim()!=''"></td>
					<td><input name="File4Info" id="File4Info" type="text"
						value="" verify="附件描述|NotNull" condition="$V('File4').trim()!=''"></td>
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