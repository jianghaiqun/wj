<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	var ImgPath = $V("LogoUrl");
	if(ImgPath!=null && ImgPath !=''){
		$("Img").src =$V("UploadFilePath") + ImgPath;
	}
});
/**
 * 图片选择
 */
function imageBrowse(ele){
	var diag = new Dialog("Diag4");
	diag.Width = 800;	
	diag.Height = 460;
	diag.Title ="浏览图片库";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = BrowseOK;
	diag.show();
}
/**
 * 确定图片
 */
function BrowseOK(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}
/**
 * 上传回调
 */
function onUploadCompleted(returnID){
	onReturnBack(returnID);
}
/**
 * 上传回调
 */
function onReturnBack(returnID){
	var ImgID = returnID.split(",");
	var dc = new DataCollection();
	dc.add("ImgID",ImgID[0]);
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.getImgSrc",dc,function(response){
		$S("LogoUrl",response.get("ImgSrc"));
		$("Img").src = $V("UploadFilePath")+response.get("ImgSrc");
	});
}
/**
 * 校验是否选择图片
 */
function checkImgPath(){
	var Flag = true;
	var ImgPathes = $N("LogoUrl");
	if(ImgPathes!=null){
		for(var i=0;i<ImgPathes.length;i++){
			if(ImgPathes[i].value==null||ImgPathes[i].value==""){
				Flag = false;
			}
		}
	}else{
		Flag = false;
	}
	return Flag;
}
</script>
</head>
<body class="dialogBody">
	<input type="hidden" id="ActivityStatusFlag" value="<%=request.getParameter("ActivityStatusFlag")%>">
	<z:init method="com.sinosoft.cms.document.PromotionManage.initDialogActivity">
		<form id="form2">
			<input type="hidden"  id="ID" name="ID" value="<%=request.getParameter("ID")%>">
			<input type="hidden" id="UploadFilePath" name="UploadFilePath" value="${UploadFilePath}" />
			<input type="hidden" id="LogoUrl" name="LogoUrl" value="${LogoUrl}"/>
			<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活动名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${ActivityName}" type="text" id="ActivityName" name="ActivityName" ztype="String"  class="inputText" size="20" maxlength=200 verify="活动名称|NotNull" ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">参与人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${peopleNum}" type="text" id="peopleNum" name="peopleNum" ztype="String"  class="inputText" size="20"  maxlength=10  verify="参与人数|NotNull&&Number" ></td>
										</tr>
							     </table>
							     <table>
							     	<tr >
							     	<td align="left">活动图片：</td>
								    <td width="10%" style="border-bottom:1px dotted #D9D9D9;">
		                            	<img id="Img" style="cursor:pointer;" title="点击选择或上传图片" alt="" onerror="this.alt='图片载入失败'" onClick="imageBrowse(this);" src="${UploadFilePath}/wj/Images/addpicture.jpg" width="120" height="90" >
		                            </td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;&nbsp;&nbsp;跳转地址：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								    <td ><input value="${URL}" type="text" id="URL" name="URL" ztype="String"  class="inputText" size="20"   maxlength=500   verify="跳转地址|NotNull" ></td>
								    </tr>
							     </table>
							     <table>
								          <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否显示：</td>
										  <td>${isShow}</td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">终&nbsp;&nbsp;&nbsp;止&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：</td>
								          <td><input name="endDate" id="endDate" value="${endDate}" type="text" size="14" ztype="Date" verify="结束日期|NotNull"/>
								          <input name="endTime" id="endTime" value="${endTime}" type="text" size="14" ztype="Time" verify="结束时间|NotNull"/></td>
								          </tr>
							     </table>
			               </fieldset>
					</td>
			    </tr>
			  </table>
		</form>
	</z:init>
</body>
</html>
