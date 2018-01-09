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
var extsStr = 'xls,xlsx,xml,csv,txt';
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
			Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.improtRefundResult",dc,function(response){
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
					<!-- <td colspan="2" style="color:red;">暂时只支持微信，易宝支付，银联，主站支付宝，这些支付平台的批量导入退款结果。</td> -->
					<td colspan="2" style="color:red;">请根据退款管理模板填入退款数据批量导入退款结果。</td>
				</tr>
				<tr>
					<td width='6%' align="right"><label></label></td>
					<td><input name='File1' id='File1' type='file' value=''
						size='30' onChange="doPreview(this);"></td>
				</tr>
				<tr>
					<td colspan="3" align="center"><A href="./ADTemplate/refundresulttemplete.xls">批量导入模板下载</A></td>
				</tr>
				<%-- <tr>
					<td width='6%' align="right"><label></label></td>
					<td><input name='File2' id='File2' type='file' value=''
						size='30' onChange="doPreview(this);"></td>
				</tr>
				<tr>
					<td width='6%' align="right"><label></label></td>
					<td><input name='File3' id='File3' type='file' value=''
						size='30' onChange="doPreview(this);"></td>
				</tr>
				<tr>
					<td width='6%' align="right"><label></label></td>
					<td><input name='File4' id='File4' type='file' value=''
						size='30' onChange="doPreview(this);"></td>
				</tr>
				<tr>
					<td width='6%' align="right"><label></label></td>
					<td><input name='File5' id='File5' type='file' value=''
						size='30' onChange="doPreview(this);"></td>
				</tr> --%>
			</table>
			</fieldset>
			</td>
		</tr>
</table>
</form>
<div id="divMsg" align="center"></div>
</body>
</html>