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
/**
 * 选择产品
 */
function getProductList() {
	var diag = new Dialog("Diag3");
	diag.Width =600;
	diag.Height = 360;
	diag.Title = "选择产品";
	diag.URL = "Promotion/Homepage/PromotionProductSearchDialog.jsp?products="+$V("productid");
	diag.onLoad = function() {
	};
	diag.OKEvent = addProduct;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "选择产品";
	diag.show();
}
/**
 * 保存产品信息
 */
function addProduct(){
	var arr = $DW.DataGrid.getSelectedValue("dg3");
	if (!arr || arr.length == 0) {
		Dialog.alert("请选择产品！");
		return;
	}
	$S("productid",arr.join());
	var productname1=$DW.DataGrid.getSelectedValueByColumns("dg3","productname");
	$S("productname1",productname1);
	$D.close();
}
</script>
</head>
<body class="dialogBody">
	<z:init method="com.sinosoft.cms.document.PromotionManage.initDialogSpecialProduct">
		<form id="form2">
			<input type="hidden" id="UploadFilePath" name="UploadFilePath" value="${UploadFilePath}" />
			<input type="hidden" id="LogoUrl" name="LogoUrl" value="${LogoUrl}"/>
			<input type="hidden" id="productid" name="productid" value="${productid}"/>
			<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">产品描述：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${ProductName}" type="text" id="ProductName" name="ProductName" ztype="String"  class="inputText" size="20"  maxlength=200   verify="品牌名称|NotNull" ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">购买人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${buynum}" type="text" id="buynum" name="buynum" ztype="String"  class="inputText" size="20"   maxlength=100   verify="参与人数|NotNull&&Number" ></td>
										</tr>
							     </table>
							     <table>
							     	<tr >
							     	<td align="left">产品图片：</td>
								    <td width="10%" style="border-bottom:1px dotted #D9D9D9;">
		                            	<img id="Img" style="cursor:pointer;" title="点击选择或上传图片" alt="" onerror="this.alt='图片载入失败'" onClick="imageBrowse(this);" src="${UploadFilePath}/wj/Images/addpicture.jpg" width="120" height="90" >
		                            </td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								    </tr>
							     </table>
							     <table>
								          <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">购买人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								    	  <td ><input value="${buynum}" type="text" id="buynum" name="buynum" ztype="String"  class="inputText" size="20" verify="购买人数|NotNull&&Number" ></td>
								    	  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">终&nbsp;&nbsp;&nbsp;止&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：</td>
								          <td><input name="endDate" id="endDate" value="${endDate}" type="text" size="14" ztype="Date" verify="结束日期|NotNull"/>
								          <input name="endTime" id="endTime" value="${endTime}" type="text" size="14" ztype="Time" verify="结束时间|NotNull"/></td>
								          </tr>
							     </table>
							     <table>
								          <tr>
									          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >产品：</td><td >
									          		<input name="productname1" type="text" value="${productname1}" style="width:350px" class="inputText" id="productname1" disabled="disabled" size="30"   readonly = "readonly"  onmouseover=""/> 
									         		 <input type="button" value="查 找" onClick="getProductList()">
							          		  </td>
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
