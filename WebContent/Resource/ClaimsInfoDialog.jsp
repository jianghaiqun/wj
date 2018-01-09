<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.cms.resource.ClaimsInfo.initClassifyInfo" >
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
	var sid = $V("comCode");
	if(sid==null || sid == ''){
		Dialog.alert("您没有选择保险公司分类，请选择。");
		return;
	}
	if(getUploader("AttachFile").hasFile()&&!getUploader("AttachFile").hasError()){
		var dc = Form.getData("form2");
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

function browse(){
	var comCode = $V("comCode");
	if (comCode == null || comCode == '') {
		Dialog.alert("请选择所属保险公司后再选择二级分类！");
		return;
	}
	var diag = new Dialog("Diag2");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "附件分类列表";
	diag.URL = "Resource/ClaimsInfoClassifyDialog.jsp?comCode="+comCode;
	diag.OKEvent = browseSave;
	diag.show();
}


function browseSave(){
	var arr = $DW.$NV("ClassifyType");
	if(!arr||arr.length==0){
		return;
	}
	var OtherLibTable = $("OtherLibTable");
	var sb = [];
	for(var i=0;i<arr.length;i++){
		sb.push("<tr><td><input type='hidden' name='OtherID' value='"+arr[i]+"'>"+$DW.$("span_"+arr[i]).innerHTML+"</td><td><img src='../Framework/Images/icon_cancel.gif' title='删除' onclick='deleteOtherID(this);' /></td></tr>");	
	}
	OtherLibTable.outerHTML="<table width='100%' border='1' cellspacing='0' id='OtherLibTable' bordercolor='#eeeeee'>"+sb.join('')+"</table>";
	$D.close();
}

function deleteOtherID(ele){
	ele.parentElement.parentElement.parentElement.deleteRow(ele.parentElement.parentElement.rowIndex);
	return false;
}

function OK(){
	upload();;
}

function loadClassifyType() {
	var dc = new DataCollection();
	var comCode = $V("comCode");
	if (comCode == null || comCode == '') {
		$("ClassifyTypeTd").innerHTML  = '';
		return;
	}
	dc.add("comCode",comCode);
	Server.sendRequest("com.sinosoft.cms.resource.ClaimsInfo.getClassifyType",dc,function(response){
		$("ClassifyTypeTd").innerHTML  = response.get("ClassifyType");
	})
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<form id="form2" >
<input type="hidden" id="FileType" name="FileType" value="Claims">
<input type="hidden" id="id" name="id" value="${id}">

<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
			<td  valign="top">
			<fieldset><legend> <strong>附件:</strong></legend>
			<table id="Videotable0" style="display:" width="100%" cellpadding="2"
				cellspacing="0">
				<tr id="tr1">
					<td width="16%" align="right">上传说明：</td>
					<td width="84%" colspan="2">允许大小 ${fileMaxSizeHuman} <br>支持格式 ${allowType}等文件</td>
				</tr>
				<tr id="tr2">
					<td align="right">附件浏览：</td>
					<td colspan="2">
                    <z:uploader id="AttachFile" action="../Editor/filemanager/upload/zuploader.jsp" onComplete="onUploadCompleted" allowtype="${allowType}" fileMaxSize="${fileMaxSize}" fileCount="1"/>
						</td>
				</tr>
				<tr>
					<td align="right">附件名称：</td>
					<td><input id="FileName" name="FileName" type="text"
						class="input1" value="${name}" size="40" verify="附件名称|NotNull" /></td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr >
			<td  valign="top">
			<fieldset><legend> <label><strong>参数设置</strong></label></legend>
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td width="58%" align="left">
					<table width="100%" cellpadding="2" cellspacing="0">
						<tr>
							<td width="25%">是否热门推荐:</td>
							<td colspan="2"><z:select name="isHot" id="isHot" style="width:50px;">${YesOrNo}</z:select></td>
						</tr>
						<tr>
							<td width="25%">排序:</td>
							<td colspan="2"><input name="orderFlag" type="text" class="input1" id="orderFlag" size="20" verify="排序|Int" value="${orderFlag}"></td>
						</tr>
						<tr>
							<td width="25%">所属保险公司分类:</td>
							<td colspan="2"><z:select name="comCode" id="comCode" onChange="loadClassifyType();"style="width:150px;" verify="所属保险公司分类|NotNull" >${supplier}</z:select></td>
						</tr>
						<tr>
							<td valign="top">所属二级分类:</td>
							<td colspan="2" id="ClassifyTypeTd">${ClassifyType}
							</td>
						</tr>
					</table>
					<div id="divMsg" align="center"></div>
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