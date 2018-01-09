<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
Page.onLoad(function(){
	var ImgPath = $V("ImgPath");
	if(ImgPath!=null && ImgPath !=''){
		$("Img").src =$V("UploadFilePath") + ImgPath;
	}
	var GiftDialogStatusFlag=jQuery("#GiftDialogStatusFlag").val();
	if('add'!=GiftDialogStatusFlag){
		jQuery("#Type").attr("disabled","disabled");
		jQuery("#Type").attr("readonly","readonly");
		jQuery("#PointsExchangeType").attr("disabled","disabled");
		jQuery("#PointsExchangeType").attr("readonly","readonly");
		$("Type").disable();
		$("PointsExchangeType").disable();
		jQuery("#searchProductButton").hide();
		var Type=jQuery("#Type").val();
		if("1"!=Type){
			jQuery("#table_product").hide();
		}
		changeType('');
	}else{
		jQuery("#Type").attr("disabled","disabled");
		jQuery("#Type").attr("readonly","readonly");
		$("Type").disable();
	}
});
/**
 * 选择保险产品
 */
function getProductList(){
	//产品id
	var productid=jQuery("#ProductID").val();
	var diag = new Dialog("Diag2");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "产品列表";
	diag.URL = "PointsMall/ProductDialog.jsp";
	diag.OKEvent = chooseProduct;
	diag.show();
	diag.CancelButton.value = "取消";
	if(productid!=null&&productid!=''){
		var  producttimeoutID=setTimeout(function (){
			$DW.DataGrid.select($DW.$("dg3"),productid);
			clearTimeout(producttimeoutID);
		}, 1000 );
	}
}
/**
 * 确定保险产品
 */
function chooseProduct(){
	var productid=$DW.DataGrid.getSelectedValueByColumn("dg3","id");
	var productname=$DW.DataGrid.getSelectedValueByColumn("dg3","productname");
	jQuery("#ProductID").val(productid);
	jQuery("#productname").val(productname);
	$D.close();
}

/**
 * 图片选择
 */
function imageBrowse(ele){
	var diag = new Dialog("Diag2");
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
		$S("ImgPath",response.get("ImgSrc"));
		$("Img").src = $V("UploadFilePath")+response.get("ImgSrc");
	});
}
/**
 * 校验是否选择图片
 */
function checkImgPath(){
	var Flag = true;
	var ImgPathes = $N("ImgPath");
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
 * 更改类别
 */
function  changeType(typecode){
	var Type=parseInt(jQuery("#Type").val());//类型
	var PointsExchangeType=parseInt(jQuery("#PointsExchangeType").val());//模式
	switch (Type) {
	case 1:
		//保险产品
		jQuery("#table_product").show();
		jQuery("#LastNum").attr("disabled",false);
		jQuery("#lastnum_td").show();
		jQuery("#lastnum_input").show();
		jQuery("#points_td").hide();
		jQuery("#point_nbsp").hide();
		jQuery("#Points").hide();
		jQuery("#giftprice_td").hide();
		jQuery("#GiftPrice").hide();
		jQuery("#MetaDescriptionTR").hide();
		jQuery("#flowtype").hide();
		jQuery("#FlowSize").hide();
		jQuery("#FlowType_use").hide();
		jQuery("#FlowSize_use").hide();
		jQuery("#FuLuGoodsID").hide();
		jQuery("#FuLuGoods_use").hide();
		if(typecode=="change"){
			jQuery("#GiftPrice").val('');
			jQuery("#Points").val('');
			jQuery("#lastnum_input").val('');
			UE.getEditor('MetaDescription').setContent('');
		}

		break;
	case 2:
		//虚拟产品
		jQuery("#table_product").hide();
		if(jQuery("#PointsExchangeType").val()=="2"){
			jQuery("#LastNum").attr("disabled",false);
			jQuery("#lastnum_td").show();
			jQuery("#lastnum_input").show();
			}else{
			jQuery("#LastNum").attr("disabled",true);
			jQuery("#lastnum_td").hide();
			jQuery("#lastnum_input").hide();
			}  
		jQuery("#points_td").show();
		jQuery("#point_nbsp").show();
		jQuery("#Points").show();
		jQuery("#giftprice_td").show();
		jQuery("#GiftPrice").show();
		jQuery("#MetaDescriptionTR").show();
		jQuery("#flowtype").hide();
		jQuery("#FlowSize").hide();
		jQuery("#FlowType_use").hide();
		jQuery("#FlowSize_use").hide();
		jQuery("#FuLuGoodsID").hide();
		jQuery("#FuLuGoods_use").hide();
		if(typecode=="change"){
			jQuery("#GiftPrice").val('');
			jQuery("#Points").val('');
			jQuery("#lastnum_input").val('');
		}
		break;
	case 3:
		//卡密产品
		jQuery("#table_product").hide();
		if(jQuery("#PointsExchangeType").val()=="2"){
			jQuery("#LastNum").attr("disabled",false);
			jQuery("#lastnum_td").show();
			jQuery("#lastnum_input").show();
			}else{
			jQuery("#LastNum").attr("disabled",true);
			jQuery("#lastnum_td").hide();
			jQuery("#lastnum_input").hide();
			}  
		jQuery("#points_td").show();
		jQuery("#point_nbsp").show();
		jQuery("#Points").show();
		jQuery("#giftprice_td").show();
		jQuery("#GiftPrice").show();
		jQuery("#MetaDescriptionTR").show();
		jQuery("#flowtype").hide();
		jQuery("#FlowSize").hide();
		jQuery("#FlowType_use").hide();
		jQuery("#FlowSize_use").hide();
		jQuery("#FuLuGoodsID").show();
		jQuery("#FuLuGoods_use").show();
		if(jQuery("#PointsExchangeType").val()==1){
			jQuery("#FuLuGoodsID").hide();
			jQuery("#FuLuGoods_use").hide();
		}
		if(typecode=="change"){
			jQuery("#GiftPrice").val('');
			jQuery("#Points").val('');
			jQuery("#lastnum_input").val('');
		}
		break;
	case 4:
		//卡号产品
		jQuery("#table_product").hide();
		if(jQuery("#PointsExchangeType").val()=="2"){
			jQuery("#LastNum").attr("disabled",false);
			jQuery("#lastnum_td").show();
			jQuery("#lastnum_input").show();
			}else{
			jQuery("#LastNum").attr("disabled",true);
			jQuery("#lastnum_td").hide();
			jQuery("#lastnum_input").hide();
			}  
		jQuery("#points_td").show();
		jQuery("#point_nbsp").show();
		jQuery("#Points").show();
		jQuery("#giftprice_td").show();
		jQuery("#GiftPrice").show();
		jQuery("#MetaDescriptionTR").show();
		jQuery("#flowtype").hide();
		jQuery("#FlowSize").hide();
		jQuery("#FlowType_use").hide();
		jQuery("#FlowSize_use").hide();
		jQuery("#FuLuGoodsID").hide();
		jQuery("#FuLuGoods_use").hide();
		if(typecode=="change"){
			jQuery("#GiftPrice").val('');
			jQuery("#Points").val('');
			jQuery("#lastnum_input").val('');
		}
		break;
	case 5:
		//流量直冲
		//jQuery("#PointsExchangeType").val('2'); //自主选择模式
		jQuery("#table_product").hide();
		jQuery("#LastNum").attr("disabled",true);
		if(jQuery("#PointsExchangeType").val()=="2"){
			jQuery("#LastNum").attr("disabled",false);
			jQuery("#lastnum_td").show();
			jQuery("#lastnum_input").show();
			}else{
			jQuery("#LastNum").attr("disabled",true);
			jQuery("#lastnum_td").hide();
			jQuery("#lastnum_input").hide();
			}  
		jQuery("#points_td").show();
		jQuery("#point_nbsp").show();
		jQuery("#Points").show();
		jQuery("#giftprice_td").show();
		jQuery("#GiftPrice").show();
		jQuery("#MetaDescriptionTR").show();
		jQuery("#flowtype").show();
		jQuery("#FlowSize").show();
		jQuery("#FlowType_use").show();
		jQuery("#FlowSize_use").show();
		jQuery("#FuLuGoodsID").hide();
		jQuery("#FuLuGoods_use").hide();
		if(typecode=="change"){
			jQuery("#GiftPrice").val('');
			jQuery("#Points").val('');
			jQuery("#lastnum_input").val('');
		}
		break;
	case 6:
		//账号直冲
		jQuery("#table_product").hide();
		if(jQuery("#PointsExchangeType").val()=="2"){
			jQuery("#LastNum").attr("disabled",false);
			jQuery("#lastnum_td").show();
			jQuery("#lastnum_input").show();
			}else{
			jQuery("#LastNum").attr("disabled",true);
			jQuery("#lastnum_td").hide();
			jQuery("#lastnum_input").hide();
			}  
		jQuery("#points_td").show();
		jQuery("#point_nbsp").show();
		jQuery("#Points").show();
		jQuery("#giftprice_td").show();
		jQuery("#GiftPrice").show();
		jQuery("#MetaDescriptionTR").show();
		jQuery("#flowtype").hide();
		jQuery("#FlowSize").hide();
		jQuery("#FlowType_use").hide();
		jQuery("#FlowSize_use").hide();
		jQuery("#FuLuGoodsID").show();
		jQuery("#FuLuGoods_use").show();
		if(typecode=="change"){
			jQuery("#GiftPrice").val('');
			jQuery("#Points").val('');
			jQuery("#lastnum_input").val('');
		}
		break;
	default:
		
		break;
	}
}

 function changeModelType(type){
	var ModelType=parseInt(jQuery("#PointsExchangeType").val());//模式
	var Type=parseInt(jQuery("#Type").val());//类型
	if(jQuery("#PointsExchangeType").val()=="2"){
		jQuery("#LastNum").attr("disabled",false);
		jQuery("#lastnum_td").show();
		jQuery("#lastnum_input").show();
		}else{
		jQuery("#LastNum").attr("disabled",true);
		jQuery("#lastnum_td").hide();
		jQuery("#lastnum_input").hide();
		}  
	if(ModelType==2){
		if(Type==5||Type==6){
		jQuery("#lastnum_td").show();
		jQuery("#lastnum_input").show();
		jQuery("#LastNum").attr("disabled",false);
		}
	}else{
		if(Type==5||Type==6){
	    jQuery("#lastnum_td").hide();
	    jQuery("#lastnum_input").hide();
	    jQuery("#LastNum").attr("disabled",true);
	    }
	}
    if(ModelType==1||ModelType==2){
	jQuery("#Type").attr("disabled","");
	jQuery("#Type").attr("readonly","");
    }else{
    	jQuery("#Type").attr("disabled","disabled");
    	jQuery("#Type").attr("readonly","readonly");
    }
	if(Type==3&&ModelType==1){
		jQuery("#FuLuGoodsID").hide();
		jQuery("#FuLuGoods_use").hide();
	}
	if(Type==3&&ModelType==2){
		jQuery("#FuLuGoodsID").show();
		jQuery("#FuLuGoods_use").show();
	}
} 

/**
 * 获得温馨提示
 */
function getMetaDescription(){
	var MetaDescription = UE.getEditor('MetaDescription').getContent()
	if (MetaDescription == '' || MetaDescription == null) {
		return '';
	}
	return MetaDescription;
}
</script>
</head>
<body class="dialogBody">
	<input type="hidden" id="GiftDialogStatusFlag" value="<%=request.getParameter("GiftDialogStatusFlag")%>">
	<z:init method="com.wangjin.pointsMall.GiftManage.initDialog">
		<form id="form2">
			<input type="hidden" id="UploadFilePath" name="UploadFilePath" value="${UploadFilePath}" />
			<input type="hidden" id="ImgPath" name="ImgPath" value="${LogoUrl}"/>
			<input type="hidden"  id="ID" name="ID" value="${ID}">
			<table width="920" height="580" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								         <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">礼品标题：</td>
								          <td ><input value="${GiftTitle}" type="text" id="GiftTitle" name="GiftTitle" ztype="String"  class="inputText" size="60" verify="礼品标题|NotNull" maxlength=50></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模式：</td>
									      <td><z:select style="width:120px;" id="PointsExchangeType"  verify="模式|NotNull" onChange="changeModelType('change')"  value="${GiftPointsExchangeType}">${PointsExchangeType}</z:select></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">类别：</td>
										  <td><z:select style="width:120px;" id="Type"  verify="类别|NotNull" onChange="changeType('change')" value="${GiftType}">${Type}</z:select></td>
										</tr>
							     </table>
							     <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">礼品描述：</td>
								          <td ><input value="${GiftName}" type="text" id="GiftName" name="GiftName" ztype="String"  class="inputText" size="130" verify="礼品描述|NotNull" maxlength=500></td>
										</tr>
							     </table>
							     <table>
							     	<tr >
							     	<td align="left">礼品图片：</td>
								    <td width="10%" style="border-bottom:1px dotted #D9D9D9;">
		                            	<img id="Img" style="cursor:pointer;" title="点击选择或上传图片" alt="" onerror="this.alt='图片载入失败'" onClick="imageBrowse(this);" src="${UploadFilePath}/wj/Images/addpicture.jpg" width="120" height="90" >
		                            </td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								    </tr>
							     </table>
							     <table>
							     	<tr >
								     	<td id="points_td">积分兑换值：</td>
									    <td><input value="${Points}" type="text" id="Points" name="Points" ztype="String"  class="inputText"  size="20"  maxlength=30  ></td>
										</br>
										<td id="point_nbsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											人气：
										</td>
										<td><input value="${Popularity}" type="text" id="Popularity" name="Popularity" ztype="String"  class="inputText"  size="20"  maxlength=30 verify="人气|NotNull" ></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>状态：</td>
										<td><z:select style="width:120px;" id="status"  verify="礼品状态|NotNull"  value="${giftstatus}">${status}</z:select></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td id="lastnum_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">库存：</td>
								        <td id="lastnum_input"><input value="${LastNum}" type="text" id="LastNum" name="LastNum" ztype="String"  class="inputText"  size="20"   maxlength=10></td>
								    </tr>
							     </table>
							     <table>
							     		<tr>
								          <td height="35" align="right" class="tdgrey1" bordercolor="#eeeeee">归&nbsp;&nbsp;&nbsp;属&nbsp;&nbsp;&nbsp;模&nbsp;&nbsp;&nbsp;块：</td>
								          <td>
								          	${ModelType}
								          </td>
								        </tr>
							     </table>
							     <table>
								          <tr>
										        <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">开始时间：</td>
												<td><input name="createStartDate" id="createStartDate" value="${StartDate}" type="text" size="14" ztype="Date"  verify="开始日期|NotNull" /></td>
												<td><input name="createStartTime" id="createStartTime" value="${StartTime}" type="text" size="14" ztype="Time" verify="开始时间|NotNull"/></td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
												<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">结束时间：</td>
												<td><input name="createEndDate" id="createEndDate" value="${EndDate}" type="text" size="14" ztype="Date" verify="结束日期|NotNull"/></td>
												<td><input name="createEndTime" id="createEndTime" value="${EndTime}" type="text" size="14" ztype="Time" verify="结束时间|NotNull"/></td>
								          </tr>
							     </table>
							     <table>
								          <tr>
										        <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否推荐：</td>
										        <td>
											        <input type="radio" value="Y" ${checkY} name="Recommend" id="Recommend" >是 
											        <input type="radio" value="N" ${checkN} name="Recommend" id="Recommend" >否 
										        </td>
										        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										        <td id="giftprice_td">礼品价值：</td>
									    		<td><input value="${GiftPrice}" type="text" id="GiftPrice" name="GiftPrice" ztype="String"  class="inputText"  size="10"  maxlength=30 ></td>
										        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										        <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">wap站是否显示：</td>
										        <td><z:select style="width:60px;" id="isWap" value="${isWapValue}">${YesOrNo}</z:select></td>
										        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          		<td id="FuLuGoods_use" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">福禄商品编号：</td>
									            <td><input value="${GiftFuLuGoodsID}" type="text" id="FuLuGoodsID" name="FlowSize" ztype="String"  class="inputText"  size="5"  maxlength=30 ></td>
									            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          </tr>
								          <tr>
								          		<td id="FlowType_use" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">流量类型：</td>
									            <td><z:select style="width:120px;" id="flowtype" value="${giftFlowType}">${FlowType}</z:select></td>
										        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										        <td id="FlowSize_use">流量大小(MB)：</td>
									    		<td><input value="${FlowSize}" type="text" id="FlowSize" name="FlowSize" ztype="String"  class="inputText"  size="5"  maxlength=30 ></td>
										        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          </tr>
							     </table>
							   	<table  id="table_product">
								          <tr>
								          		<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >保险产品：</td><td >
								          		<input name="productname" type="text" value="${ProductName}" style="width:450px" class="inputText" id="productname" disabled="disabled" 
		              							size="30"   readonly = "readonly"  /> 
		              							<input name="ProductID" type="hidden" value="${ProductID}" style="width:450px" class="inputText" id="ProductID" disabled="disabled" 
		              							size="30"   readonly = "readonly" /> 
		              							<span id="searchProductButton"><input type="button"  value="查 找" onClick="getProductList()"></span>
								          </tr>
							     </table>
							     <table>
							      	<tr id="MetaDescriptionTR">
							      	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">温馨提示：</td>
							      	<td>
							      		<textarea id="MetaDescription" name="MetaDescription" style="height:300px;width:750px;">${MetaDescription}</textarea>
										<script type="text/javascript">var ue = UE.getEditor("MetaDescription");</script>
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
