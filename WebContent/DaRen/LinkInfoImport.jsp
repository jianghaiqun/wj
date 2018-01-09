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
function onUploadCompleted(returnValue,errorMessage,FilePath){
	switch ( returnValue )
	{
		case 0 :	// No errors
			break ;
		case 1 :	// 
		    var dc = new DataCollection();
			dc.add("FilePath",FilePath);
			Server.sendRequest("com.wangjin.daren.DataMiningManage.improt",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						$("divMsg").innerHTML="<font color=red>导入失败。</font>";
					} else {
						try {
							window.Parent.onFileUploadCompleted();
						}catch(ex){
						}
					}
				});
			});
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
					<td colspan="2" style="color:red;">请从作者详细录入功能中导出帖子信息。</td>
				</tr>
				<tr>
					<td width='6%' align="right"><label></label></td>
					<td><input name='File1' id='File1' type='file' value=''
						size='30' onChange="doPreview(this);"></td>
				</tr>
				<tr>
					<td colspan="3" align="center"><A href="./ExcelTemplate.xls">批量导入模板下载</A></td>
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