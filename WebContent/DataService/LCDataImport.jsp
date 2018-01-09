<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
var extsStr = 'xls,xlsx';
var exts = extsStr.split(",");
function upload(){
	if(Verify.hasError()){
		return;
	}
	var flag = false;
	var count = 1;
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
function onUploadCompleted(returnValue,errorMessage,FilePath){
	switch ( returnValue )
	{
		case 0 :	// No errors
			break ;
		case 1 :	// 
		    var dc = new DataCollection();
			dc.add("FilePath",FilePath);
			Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.improtLCData",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						setInterval(function(){$("divMsg").innerHTML="<font color=red>导入失败。</font>";counter++}, 0);
					} else {
						$("divMsg").hide();
						try {
						}catch(ex){
						}
					}
				});
			});
			return ;
		default :
			Dialog.alert(errorMessage ,function(){
			}) ;
			return ;
	}
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
			<fieldset><legend><strong>导入:</strong></legend>
			<table id="Videotable0" width="100%" cellpadding="2">
				<tr>
					<td width='6%' align="right"><label></label></td>
					<td colspan="3" style="color:red;">请根据渠道数据导入理财险数据。</td>
				</tr>
				<tr>
					<td width='6%' align="right"><label></label></td>
					<td width='20%' ><input name='File1' id='File1' type='file' value='' onChange="doPreview(this);"></td>
					<td><input type="button" value="导入" onClick="upload()"></td>
					<td width='50%' ></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><A href="./ADTemplate/lcdatatemplete.xls">批量导入模板下载</A></td>
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