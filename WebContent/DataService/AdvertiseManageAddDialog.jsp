<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=Config.getValue("App.Name")%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/StyleToolbar.js"></script>
<script type="text/javascript">
var imgIndex = 1;
var textIndex = 1;
var flashIndex = 1;
var NowSwf = "";

var url = "http://"
var txt = "";
var path = "";
var src  = "../Images/addpicture.jpg";
var color = "#333333";

Page.onLoad(function(){
	var AdType = $V("hAdType");
	if(AdType==""){
		AdType = $V("AdType");
	}
	$S("AdType",AdType);
	$S("hAdType",AdType);
	if($V("imgADLinkTarget")=="Old"){
		$("imgADLinkTargetOld").checked = true;
	}else{
		$("imgADLinkTargetNew").checked = true;
		$S("imgADLinkTarget","New");
	}
	var AdContent = $V("AdContent");
	if(AdContent!=null && AdContent !=''){
		$("Img").src =$V("UploadFilePath") + AdContent;
	}
});

function checkImgPath(){
	var Flag = true;
	var ImgPathes = $N("ImgPath");
	for(var i=0;i<ImgPathes.length;i++){
		if(ImgPathes[i].value==null||ImgPathes[i].value==""){
			Flag = false;
		}
	}
	return Flag;
}

function imageBrowse(ele){
	var diag = new Dialog("Diag4");
	diag.Width = 800;	
	diag.Height = 460;
	diag.Title ="浏览图片库";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = BrowseOK;
	diag.show();
}

function BrowseOK(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}

function onReturnBack(returnID){
	var ImgID = returnID.split(",");
	var dc = new DataCollection();
	dc.add("ImgID",ImgID[0]);
	Server.sendRequest("com.sinosoft.cms.dataservice.TopAdInformation.getImgSrc",dc,function(response){
		$S("ImgPath",response.get("ImgSrc"));
		$("Img").src = $V("UploadFilePath")+response.get("ImgSrc");
	});
}

function UpLoadSwf(ele){
	NowSwf = ele;
	$("adform").submit();
}

function afterUploadSwf(returnStr,SwfFileStr){
	if(returnStr!=""){
		Dialog.alert(returnStr);
		return;
	}else{
		$S(NowSwf.id+"Path",SwfFileStr);
		NowSwf.parentElement.innerHTML = "<input type='file' name='SwfFile' id='"+NowSwf.id+"' onChange='UpLoadSwf(this);'/>";
	}
}

function changeImgLinkTarget(type){
	$S("imgADLinkTarget",type);
	if(type=="New"){
		$("imgADLinkTargetNew").checked = true;
		$("imgADLinkTargetOld").checked = false;
	}else{
		$("imgADLinkTargetNew").checked = false;
		$("imgADLinkTargetOld").checked = true;
	}
}

function changeShowAlt(){
	var showalt = $V("showAlt");	
	var showaltbox = $("showAltBox");
	if(showalt=="Y"){
		showaltbox.checked = false;
		$S("showAlt","N");
	}else{
		showaltbox.checked = true;
		$S("showAlt","Y");
	}
}
function hidEndTime(id){
	if($V(id)!="1"){
		$("hidEndTime").show();
		if($V("HidEDate") != null && $V("HidEDate") !=''){
			$S("EndDate",$V("HidEDate"));
			$S("ETime",$V("HidETime"));
		}else{
			$S("StartDate","");
			$S("STime","");
		}
	}else{
		$("hidEndTime").hide();
		$S("EndDate","");
		$S("ETime","");
		if($V('StartDate')==null || $V('StartDate')==''){
			  var d = new Date();
			  var vYear = d.getFullYear();
			  var vMon = d.getMonth() + 1;
			  var vDay = d.getDate();
			  var h = d.getHours(); 
			  var m = d.getMinutes(); 
			  var se = d.getSeconds(); 
			  var ymd=vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay);
			  var hms=(h<10 ? "0"+ h : h)+":"+(m<10 ? "0" + m : m)+":"+(se<10 ? "0" +se : se);
			  $S("StartDate",ymd);
			  $S("STime",hms);
		  };
	}
}

</script>
</head>
<body>
<z:init method="com.sinosoft.cms.dataservice.TopAdInformation.DialogInit">
<div style="display:none"><iframe name="formTarget" src="javascript:void(0)"></iframe></div>
<form id="adform" method="post" target="formTarget" enctype="multipart/form-data" action="AdvertiseUpload.jsp">
	<table width="100%" border="0" cellpadding="4" cellspacing=""
		bordercolor="#DEDEDC" style="border-collapse:collapse;">
		<tr>
			<td width="240" height="12"></td>
			<td width="921"></td>
		</tr>
		<tr>
			<td align="right" width="240">广告标题：</td>
			<td width="921"><input type="text" name="AdName" size="45" id="AdName" value="${AdName}" verify="广告标题|NotNull" />
            <input type="hidden" id="ID" name="ID" value="${ID}" /></td>
		</tr>
		<tr>
			<td align="right" valign="top" width="240">所属版位：</td>
			<td width="921"><b style="color:#F60;">${AdSpaceName}</b>&nbsp;&nbsp;[${AdSpaceTypeName}]
            <input type="hidden" id="RelaID" name="RelaID" value="${RelaID}" />
            <input type="hidden" id="AdSpaceType" name="AdSpaceType" value="${AdSpaceType}" />
            </td>
		</tr>
		<tr>
			<td align="right" valign="top">广告类型：</td>
			<td>
			<z:select name="AdType" id="AdType"> ${AdTypeOptions}</z:select>
            <input type="hidden" id="hAdType" name="hAdType" value="${AdType}" />
            <input type="hidden" id="UploadFilePath" name="UploadFilePath" value="${UploadFilePath}" />
			</td>
		</tr>
		
		<tr>
			<td align="right" valign="top" width="240">发布方式：</td>
			<td width="921"> 
				<input id="IsShowText_0" class="inputRadio" type="radio" onclick="hidEndTime('IsShowText_0');" value="1" name="IsShowText">
				<label for="IsShowText_0">立即发布</label>
				<input id="IsShowText_1" class="inputRadio" type="radio" onclick="hidEndTime('IsShowText_1');" value="2" name="IsShowText">
				<label for="IsShowText_1">定时发布</label>
			</td>
		</tr>
        <tr>
			<td align="right" valign="top" width="240">上线时间：</td>
			<td width="921"><input name="StartDate" id="StartDate" value="${StartDate}" type="text" size="14" ztype="Date"/>
			  <input name="STime" id="STime" value="${STime}" type="text" size="10" ztype="Time"/></td>
		</tr>
        <tr id="hidEndTime" style="display:none">
			<td align="right" valign="top" width="240">下线时间：</td>
			<td width="921"><input name="EndDate" id="EndDate" value="${EndDate}" type="text" size="14" ztype="Date"/> 
	  		<input name="ETime" id="ETime" value="${ETime}" type="text" size="10" ztype="Time"/></td>
		</tr>
		<tr>
			<td valign="top" colspan="2">
			<table width='100%' border='0' cellpadding='4' cellspacing='0' id="imageTable">
				<tr>
					<td colspan='2' align='center' bgcolor="#E6F8FF"><b>内容设置--图片</b></td>
				</tr>
				<tr>
					<td colspan="2">
                    <div style="border:#D9D9D9 dotted 1px;">
                    <table id="imgADTable" width="100%" cellpadding='2'>
                    	<tr>
                            <td width="75%" style="border-bottom:1px dotted #D9D9D9;">
                            &nbsp;&nbsp;链接地址：<input type="text" id="imgADLinkUrl" name="imgADLinkUrl" value="${PicLinkURL}" size="40" condition="$V('AdType')=='image'"/><br/><br/>
                            &nbsp;&nbsp;文字提示：<input type="text" id="imgADAlt" name="imgADAlt" value="${PicDesc}" size="40"/><input type="hidden" id="ImgPath" name="ImgPath" value="${AdContent}"/>
                            </td>
                            <td width="10%" style="border-bottom:1px dotted #D9D9D9;">
                            <img style="cursor:pointer;" title="点击选择或上传图片" alt="" onerror="this.alt='图片载入失败'" onClick="imageBrowse(this);" src="${UploadFilePath}/Images/addpicture.jpg" width="120" height="90" id="Img">
                            </td>
                        </tr>
                    </table>
                    </div>
                  </td>
				</tr>
				<tr>
					<td width="27%" align='right'>链接目标：</td>
					<td width="73%" align='left'>
                    <label for="imgADLinkTargetNew">
                    <input name="imgADLinkTargetNew" type="radio" id="imgADLinkTargetNew" onClick="changeImgLinkTarget('New');" />新窗口
                    </label>&nbsp;&nbsp; 
                    <label for="imgADLinkTargetOld">
                    <input name="imgADLinkTargetOld" type="radio" id="imgADLinkTargetOld" onClick="changeImgLinkTarget('Old');" /> 原窗口</label>
                    <input type="hidden" id="imgADLinkTarget" name="imgADLinkTarget" value="${imgADLinkTarget}" />
                    <input type="hidden" id="AdContent" name="AdContent" value="${AdContent}" />
                 	<input type="hidden" id="HidEDate" name="HidEDate" value="${EndDate}"/>
    				<input type="hidden" id="HidETime" name="HidETime" value="${ETime}"/>
                    <span id="showSpan" style="padding-left:20px;display:none;"><label for="showAltBox"><input type="checkbox" id="showAltBox" onClick="changeShowAlt();" checked/>显示文字提示</label></span><input type="hidden" id="showAlt" name="showAlt" value="${showAlt}"/>
                    </td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
</z:init>
</body>
</html>