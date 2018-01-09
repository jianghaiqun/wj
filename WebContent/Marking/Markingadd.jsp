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
var usertype = "";
function ChannelCosts(){
	var ChannelCode = document.getElementById("ChannelCode").value;
	var diag = new Dialog("Diagsss2");
	diag.Width = 800;
	diag.Height = 300;
	diag.Title = "新建";
	diag.URL = "Marking/ChannelCosts.jsp?ChannelCode="+ChannelCode;
	diag.onLoad = function(){};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "营销费用维护";
	diag.Message = "只有固定费用才能搭配其他费用模式，否则只能选取一种费用模式";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function addSaveCosts(){
	//prompt("", $E.getTopLevelWindow().$("_DialogFrame_Diagsss2").document._DialogDiv_Diagsss2.innerHTML);
	var i = $("dg3").getSelectedRows()[0].rowIndex-1;
	var arr = DataGrid.getSelectedRows("dg3");
	var dc = $DW.Form.getData("form3");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.sinosoft.platform.Marking.addChannelCosts",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg3");
				$D.close();
			}
		});
	});
}
function GenerationNM(){
	var TerminalCodef = document.all.TerminalCode.value;//营销终端
	var ProductMajorCategory = document.all.ProductMajorCategory.value;//产品大类：
	var ProductCategory = document.all.ProductCategory.value;//产品细类：
	var ChannelTypeCode = document.all.ChannelTypeCode.value;//渠道类型
	var AdvTypeCode = document.all.AdvTypeCode.value;//广告类型：
	var ShowFormCode = document.all.ShowFormCode.value;//展现形式：
	var AdvertisementNM = document.all. AdvertisementNM.value; //广告流水号
	var ChannelNameCode = document.all.ChannelNameCode;//渠道名称
	var CompanyName = document.all.CompanyName;//渠道公司名称
	var dc = new DataCollection();
	dc.add("TerminalCode","100");
	dc.add("ProductMajorCategory",ProductMajorCategory);
	dc.add("ProductCategory",ProductCategory);
	dc.add("ChannelTypeCode",ChannelTypeCode);
	dc.add("AdvTypeCode",AdvTypeCode);
	dc.add("ShowFormCode",ShowFormCode);
	dc.add("AdvertisementNM",AdvertisementNM);
	dc.add("ChannelNameCode",ChannelNameCode);
	dc.add("CompanyName",CompanyName);	
	Server.sendRequest("com.sinosoft.platform.Marking.GenerationNM",dc,function(response){
		var ChannelCode = response.get("ChannelCode");
		var ChannelCodeHiden = response.get("ChannelCodeHiden");
		var TerminalCodef = response.get("TerminalCodef");
		var ProductMajorCategoryf = response.get("ProductMajorCategoryf");
		var ProductCategoryf = response.get("ProductCategoryf");
		var ChannelTypeCodef = response.get("ChannelTypeCodef");
		var AdvTypeCodef = response.get("AdvTypeCodef");
		var ShowFormCodef = response.get("ShowFormCodef");
		var ChannelNameCodef = response.get("ChannelNameCodef");
		var CompanySerial = response.get("CompanySerial");
		
		if(ChannelCode){
			document.all.ChannelCode.value=ChannelCode;
		}else{
			document.all.ChannelCode.value="";
	
     }
		if(ChannelCodeHiden){
			document.all.ChannelCodeHiden.value=ChannelCodeHiden;
		}else{
			document.all.ChannelCodeHiden.value="";
	
     }
		if(TerminalCodef){
			document.all.TerminalCodef.value=TerminalCodef;
		}else{
			document.all.TerminalCodef.value="";
	
     }
		if(ProductMajorCategoryf){
			document.all.ProductMajorCategoryf.value=ProductMajorCategoryf;
		}else{
			document.all.ProductMajorCategoryf.value="";
	
     }
		if(ProductCategoryf){
			document.all.ProductCategoryf.value=ProductCategoryf;
		}else{
			document.all.ProductCategoryf.value="";
	
     }
		if(ChannelTypeCodef){
			document.all.ChannelTypeCodef.value=ChannelTypeCodef;
		}else{
			document.all.ChannelTypeCodef.value="";
	
     }
		if(AdvTypeCodef){
			document.all.AdvTypeCodef.value=AdvTypeCodef;
		}else{
			document.all.AdvTypeCodef.value="";
	
     }
		if(ShowFormCodef){
			document.all.ShowFormCodef.value=ShowFormCodef;
		}else{
			document.all.ShowFormCodef.value="";
	
     }
		if(ChannelNameCodef){
			document.all.ChannelNameCodef.value=ShowFormCodef;
		}else{
			document.all.ChannelNameCodef.value="";
	
     }
		if(CompanySerial){
			document.all.CompanySerial.value=ShowFormCodef;
		}else{
			document.all.CompanySerial.value="";
		
	
     }	
	})
}
function AdvertisementNM(){
	
	var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.platform.Marking.AdvertisementNM",dc,function(response){
		var maxNM = response.get("maxNM");
		if(maxNM){
			//$("maxNM").innerHTML=AdvertisementNM;
			document.all.AdvertisementNM.value=maxNM
		}else{
			//$("maxNM").innerHTML="";
			document.all.AdvertisementNM.value="";
		}
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody" onload="AdvertisementNM()">
<z:init method="com.sinosoft.platform.Marking.init">
<form id="form2">
	<table width="1900" align="left" cellpadding="2" cellspacing="0">
		<tr>
			<td align="left">着陆地址</td>
			<td height="30" width='50'><input id="LandingPage" name="Landing" type="text"
				value="" class="input1" size=36 /></td>
			<td align="left">投放地址：</td>
			<td height="30" width='50'><input id="SendAddress " name="SendAddress " type="text"
				value=""  class="input1" size=36/></td>
		    <td>上载页面：	<input name='WaterMarkImage' id='WaterMarkImage' type='file' value='' size='20'></td> 
		</tr>
		<tr>
			<td align="left">渠道公司名：</td>
			<td height="30"><input id="CompanyName" name="Company" type="text" value=""  class="input1" size=36 /></td>     
			<td align="left">备注：</td>
			<td height="30"><input id="Remark" name="Remarks" type="text"
				value=""  class="input1" size=36 /></td>
		</tr>
		<tr>
			<td align="left">保险公司名：</td>
			<td height="30"><input id="InsuranceCompany" name="InsuranceCompany" type="text" value=""  class="input1" size=36 /></td>          
			<td align="left">产品名称：</td>
			<td height="30"><input id="ProductName" name="ProductName" type="text"
				value=""  class="input1" size=36 /></td>
						</tr>
						          <tr>
		       				      <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">营销终端：</td>
						          <td ><z:select id="TerminalCode">${Markingzd}</z:select></td>
       	       				      <td height="35" bordercolor="#eeeeee" class="tdgrey1">产品大类：</td>
						          <td ><z:select id="ProductMajorCategory">${Markingkg}</z:select></td>
						          <td >产品细类：<z:select id="ProductCategory">${ProductCategory}</z:select></td>
   		       				      <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">渠道类型：</td>
						          <td ><z:select id="ChannelTypeCode">${ChannelTypeCode}</z:select></td>
   		       				      <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">广告类型：</td>
						          <td ><z:select id="AdvTypeCode">${AdvTypeCode}</z:select></td>
   		       				      <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">展现形式：</td>
						          <td ><z:select id="ShowFormCode">${ShowFormCode}</z:select></td>
   		       				      <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">渠道名称：</td>
						          <td ><z:select id="ChannelNameCode">${ChannelName}</z:select></td>
						          </tr>
			         	 <tr>
								  <td align="left">广告编号：</td>
								  <td height="30"><input id="AdvertisementNM " name="AdvertisementNM" type="text"
										 class="input1" size=36/></td>
						 </tr>
						 <tr>                   
                         <td> <z:tbutton id="b2" onClick="GenerationNM()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>生成编号</z:tbutton></td>  
                         <td><input id='ChannelCode' type="text" value=""></td> 
                         <td><input type="hidden" id="ChannelCodeHiden" value="${ChannelCodeHiden}"></td> 
                         <td><input type="hidden" id="TerminalCodef" value="${TerminalCodef}"></td>
                         <td><input type="hidden" id="ProductMajorCategoryf" value="${ProductMajorCategoryf}"></td>
                         <td><input type="hidden" id="ProductCategoryf" value="${ProductCategoryf}"></td>
                         <td><input type="hidden" id="ChannelTypeCodef" value="${ChannelTypeCodef}"></td>
                         <td><input type="hidden" id="AdvTypeCodef" value="${AdvTypeCodef}"></td>
                         <td><input type="hidden" id="ShowFormCodef" value="${ShowFormCodef}"></td>
                         <td><input type="hidden" id="ChannelNameCodef" value="${ChannelNameCodef}"></td>    
                         <td><input type="hidden" id="CompanySerial" value="${CompanySerial}"></td>                      
						 </tr>
						 <tr>
						 <td></td>
						 </tr>
						 <tr>
                        <td> <z:tbutton id="b2" onClick="ChannelCosts()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>渠道费用维护</z:tbutton></td>
						 </tr>
			</table>
	</form>
</z:init>
</body>
</html>
