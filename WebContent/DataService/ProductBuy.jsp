<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品购买</title>
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

function editOrder() {
	var diag = new Dialog("Diag");
	diag.Width = 350;
	diag.Height = 150;
	diag.Title = "修改订单支付类型等信息";
	var url = "DataService/ProductBuyOrderDailog.jsp";
	diag.URL = encodeURI(url);
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改订单支付类型等信息页面 ";
	diag.OKEvent = updateOrder;
	diag.show();
	
	diag.CancelButton.value="关闭";
}

function updateOrder() {
	var dc = $DW.Form.getData("form2");
	var ordersn = $DW.$V('ordersn');
	if (ordersn == null || ordersn=='') {
		Dialog.alert("订单号不能为空！");
		return;
	}
	var buyForCustomerFlag = $DW.$V('buyForCustomerFlag');
	var oldOrdersn = $DW.$V('oldOrdersn');
	if (buyForCustomerFlag == 'Y' && oldOrdersn == '') {
		Dialog.alert("撤单重出订单，需要输入原始订单号！");
		return;
	}
	if (buyForCustomerFlag != 'Y' && oldOrdersn != '') {
		Dialog.alert("不是撤单重出订单，不需要输入原始订单号！");
		return;
	}
	
	var cmsBuySource = dc.get("CmsBuySource");
	if (cmsBuySource == null || cmsBuySource=='') {
		Dialog.alert("请选择订单来源！");
		return;
	}
	
	Dialog.confirm("确认修改吗？", function() {
		Dialog.wait("修改中，请稍后..."); 
		Server.sendRequest("com.sinosoft.cms.dataservice.OrderImport.updateOrder",dc,function(){
			Dialog.closeEx();
			var response = Server.getResponse();
			Dialog.alert(response.Message);
		});
	});
}

function insure(productID,productName,htmlPath){
    var arr = DataGrid.getSelectedValue("dg1");
	if(productID && productID != ""){
		arr = new Array();
		arr[0] = productID;
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
	diag.Width = 350;
	diag.Height = 250;
	diag.Title = "投保要素录入";
	var url = "DataService/ProductBuyDailog.jsp?productID="+arr[0]+"&productName="+productName+"&htmlPath="+htmlPath;
	diag.URL = encodeURI(url);
	diag.ShowMessageRow = true;
	diag.MessageTitle = "投保要素录入页面 ";
	diag.OKEvent = openLink;
	diag.show();
	
	diag.CancelButton.value="关闭";
}

function openLink() {
	var dc = $DW.Form.getData("form2");
	
	var buyForCustomerFlag = $DW.$V('buyForCustomerFlag');
	if (buyForCustomerFlag == '') {
		Dialog.alert("请选择支付类型！");
		return;
	}
	var oldOrdersn = $DW.$V('oldOrdersn');
	if (buyForCustomerFlag == 'Y' && oldOrdersn == '') {
		Dialog.alert("撤单重出订单，需要输入原始订单号！");
		return;
	}
	var param = sinosoft.base+"/shop/order_config_new!buyNow.action?productId="+dc.get("productID")+"&Period="+dc.get("Period");
	var tr = $DW.document.getElementsByTagName('tr');
	for (var i = 0; i < tr.length; i++) {
 		var id = tr[i].id;
 		if (id != "" && id != null) {
 			id = id.replace("TR", "");
 			if (tr[i].style.display == undefined || tr[i].style.display == "") {
 				param += "&" + id + "=" + $DW.document.getElementById(id).value;
 			}
 		}
 	}
	param += "&sourceFlag=" + dc.get("CmsBuySource");
	document.getElementById("link").href = param;
	$D.close();
	document.getElementById("link").click();
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			    <tr>
		            <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 购买流程</td>
		        </tr>
				<tr>
				<td>
					<a href="" id="link" style="display:none" target="_blank"></a>
					<z:init method="com.sinosoft.cms.dataservice.OrderImport.init">
					<table  cellspacing="0" cellpadding="3">
						<tr>
							<td>产品名称：</td>
							<td> <input name="productName" type="text" id="productName" value="" style="width:200px"></td>
							<td width="100px" align="right">产品编码：</td>
							<td> <input name="productCode" type="text" id="productCode" value="" style="width:120px"></td>
							<td width="100px" align="right">保险公司：</td>
							<td> <z:select name="supplierCode" id="supplierCode" style="width:230px;">${supplier}</z:select></td>
						    <td>
			            	   <z:tbutton onClick="doSearch();"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            	</td>
			            	<td>
			            	   <z:tbutton onClick="editOrder();"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>修改订单支付类型
			            	   </z:tbutton>
			            	</td>
		            	</tr>
		            </table>
		            </z:init>
				</td>
			</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.OrderImport.dg2DataBind" size="20" scroll="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="20%" style="text-align:center;"><b>保险公司</b></td>
								<td width="25%" style="text-align:center;"><b>产品名称</b></td>
								<td width="10%" style="text-align:center;"><b>产品编码</b></td>
								<td width="10%" style="text-align:center;"><b>操作</b></td>
							</tr>
							<tr onDblClick="insure('${ProductID}','${ProductName}','${HtmlPath}');" style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td >${SupplierName}</td>
								<td >${ProductName}</td>
								<td >${ProductID}</td>
								<td ><a style="cursor: hand;" onClick="insure('${ProductID}','${ProductName}','${HtmlPath}');">投保</a></td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="6">${PageBar}</td>
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