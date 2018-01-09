<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>详细页产品推荐设置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../wwwroot/kxb/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../template/common/js/base.js"></script>
<script type="text/javascript" src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","productCode",$V("productCode"));
	DataGrid.setParam("dg1","supplierCode",$V("supplierCode"));
	DataGrid.loadData("dg1");
}

function setRecommend(codeValue,codeName){
    var arr = DataGrid.getSelectedValue("dg1");
	if(codeValue && codeValue != ""){
		arr = new Array();
		arr[0] = codeValue;
	}
	if(!arr || arr.length == 0){
		Dialog.alert("请先选择要编辑的条目！");
		return;
	}
	if(!arr||arr.length>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条！");
		return;
	}
	var diag = new Dialog("Diag");
	diag.Width = 550;
	diag.Height = 350;
	diag.Title = "设置"+codeName+"详细页推荐产品";
	var url = "Document/RecommendToDetailDailog.jsp?codeValue="+arr[0]+"&codeName="+codeName;
	diag.URL = encodeURI(url);
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置"+codeName+"详细页推荐产品";
	diag.OKEvent = addSave;
	diag.show();
	diag.CancelButton.value = "取消";
}

function addSave() {
	var productId1 = $DW.$V("ProductId1");
	var productId2 = $DW.$V("ProductId2");
	var productId3 = $DW.$V("ProductId3");
	if (productId1 != null && productId1 != '' && productId2 != null && productId2 != '') {
		if (productId1 == productId2) {
			Dialog.alert("推荐产品1与推荐产品2重复！请重新设置！");
			return;
		}
	}
	if (productId1 != null && productId1 != '' && productId3 != null && productId3 != '') {
		if (productId1 == productId3) {
			Dialog.alert("推荐产品1与推荐产品3重复！请重新设置！");
			return;
		}
	}
	
	if (productId2 != null && productId2 != '' && productId3 != null && productId3 != '') {
		if (productId2 == productId3) {
			Dialog.alert("推荐产品2与推荐产品3重复！请重新设置！");
			return;
		}
	}
	
	var dc_info = $DW.Form.getData("fm");
	Server.sendRequest("com.sinosoft.cms.document.RecommendToDetail.saveRecomProduct",dc_info,function(response){
		Dialog.alert(response.Message);
	});
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="70%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			    <tr>
		            <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 详细页产品推荐设置</td>
		        </tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.document.RecommendToDetail.dg2DataBind" scroll="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="20%" style="text-align:center;"><b>产品分类编码</b></td>
								<td width="25%" style="text-align:center;"><b>产品分类名称</b></td>
								<td width="10%" style="text-align:center;"><b>操作</b></td>
							</tr>
							<tr onDblClick="setRecommend('${CodeValue}','${CodeName}');" style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td >${CodeValue}</td>
								<td >${CodeName}</td>
								<td ><a style="cursor: hand;" onClick="setRecommend('${CodeValue}','${CodeName}');">设置推荐</a></td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>