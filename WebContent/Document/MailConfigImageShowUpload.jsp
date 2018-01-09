<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.cms.resource.ConfigImageLib"%>
<%@page import="com.sinosoft.framework.utility.Mapx"%>
<%@page import="com.sinosoft.framework.utility.Filter"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript"><!--

var extsStr = 'jpg,jpeg,png,bmp';
var exts = extsStr.split(",");

function upload(){
	if(Verify.hasError()){
		return;
	}
	var flag = false;
	var count = 4;
	for(var i=1;i<=count;i++){
			var imgName = $V("File"+i);
			var ext = imgName.substring(imgName.lastIndexOf(".")+1).toLowerCase();
			if(imgName==""){
				continue;
			}
			flag=false;
			for(var j=0; j<exts.length; j++) {
				if(ext.trim()==exts[j].toLowerCase().trim()) {
					flag = true;
				}
			}
			if(!flag) {
				Dialog.alert("图片上传不支持"+ext+"文件，请重新选择！");
				return;
			}
		}
	if(flag){
	    $E.disable(window.Parent.$D.CancelButton);
	    $E.disable(window.Parent.$D.OKButton);
		$F("form2").submit();
	}else{
		Dialog.alert("请先浏览选择文件!");
		return;
	}
}


function getExtname(filename){
	var pos = filename.lastIndexOf("\\");
	return (filename.substr(pos+1));
}

function setNameInfo(ele){
	var name = getExtname(ele.value);
	name = name.substring(0,name.lastIndexOf("."));
	var id =ele.id;
	$S(id+"Name","http://");
	$S(id+"Info",name);
}

function onUploadCompleted(returnValue,errorMessage,returnPath){
	if("1"== returnValue){
		Dialog.alert( errorMessage ,function(){
			try {
				window.Parent.DataList.loadData("dg1");
				Dialog.close();
			}catch(ex){
				alert(ex);
			}
		}) ;
	}
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div style="display:none"><iframe name="formTarget"
	src="javascript:void(0)"></iframe></div>
	
<form enctype="multipart/form-data" id="form2" action="../Editor/filemanager/upload/ImageUploader.jsp?emailType=<%=request.getParameter("emailType") %>" method="post" target="formTarget">
<input type="hidden" id="FileType" name="FileType" value="Image">
<table width="760" cellpadding="3" cellspacing="0">
	<tr>
		<td width="75%" valign="top">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td style="height:190px" valign="top">
				<fieldset><legend>图片上传:(支持jpg,jpeg,png,bmp文件上传)</legend>
				<table id="imagetable0" style="display:" width="100%"
					cellpadding="2" cellspacing="0">
					<tr>
						<td width="6%" align="right"><label> </label></td>
						<td width="34%"><label> 图片浏览</label></td>
						<td width="30%">图片链接</td>
						<td width="30%">图片描述</td>
					</tr>
					<tr>
						<td width='6%' align="right"><label>1:</label></td>
						<td><input name='File1' id='File1' type='file' value=''
							size='30' onChange="setNameInfo(this);"></td>
						<td><input name="File1Name" id="File1Name" type="text"
							size='50' value="" verify="图片链接|NotNull" condition="$V('File1').trim()!=''"></td>
						<td><input name="File1Info" id="File1Info" type="text"
							value="" verify="图片描述|NotNull" condition="$V('File1').trim()!=''"></td>
					</tr>
					<tr>
						<td align="right"><label>2:</label></td>
						<td><input name='File2' id='File2' type='file' value=''
							size='30' onChange="setNameInfo(this);"></td>
						<td><input name="File2Name" id="File2Name" type="text"
							size='50' value="" verify="图片链接|NotNull" condition="$V('File2').trim()!=''"></td>
						<td><input name="File2Info" id="File2Info" type="text"
							value="" verify="图片描述|NotNull" condition="$V('File2').trim()!=''"></td>
					</tr>
					<tr>
						<td align="right"><label>3:</label></td>
						<td><input name='File3' id='File3' type='file' value=''
							size='30' onChange="setNameInfo(this);"></td>
						<td><input name="File3Name" id="File3Name" type="text"
							size='50' value="" verify="图片链接|NotNull" condition="$V('File3').trim()!=''"></td>
						<td><input name="File3Info" id="File3Info" type="text"
							value="" verify="图片描述|NotNull" condition="$V('File3').trim()!=''"></td>
					</tr>
					<tr>
						<td align="right"><label>4:</label></td>
						<td><input name='File4' id='File4' type='file' value=''
							size='30' onChange="setNameInfo(this);"></td>
						<td><input name="File4Name" id="File4Name" type="text"
							size='50' value="" verify="图片链接|NotNull" condition="$V('File4').trim()!=''"></td>
						<td><input name="File4Info" id="File4Info" type="text"
							value="" verify="图片描述|NotNull" condition="$V('File4').trim()!=''"></td>
					</tr>
				</table>
				</fieldset>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>