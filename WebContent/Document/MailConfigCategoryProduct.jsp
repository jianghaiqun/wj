<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
//创建
function create(){
	var diag = new Dialog("Diag3");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "邮件模板-产品品类新增";
	diag.URL = "Document/MailConfigCategoryProductAdd.jsp";
	diag.onLoad = function() {
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新增品类";
	diag.show();
}
function addSave(){
	var dc = $DW.Form.getData("form2");
	Dialog.confirm("确认要保存？", function() {
		Server.sendRequest("com.sinosoft.cms.document.MailCategoryProduct.saveConfig",
				dc, function() {
					Dialog.endWait();
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData('dg1');
						}
					});
				});
	});
}
//编辑
function edit(){
	var arrs = DataGrid.getSelectedData("dg1");
	var drs = arrs.Rows;
	var dr = drs[0];
	if (!drs || drs.length == 0) {
		Dialog.alert("请选择一条进行编辑 ！");
		return;
	}
	if (!drs || drs.length >= 2) {
		Dialog.alert("只能选择一条进行编辑！");
		return;
	}
	var diag = new Dialog("Diag3");
	diag.Width = 400;
	diag.Height = 200;
	diag.Title = "邮件模板-产品品类编辑";
	diag.URL = "Document/MailConfigCategoryProductAdd.jsp?ID=" + dr.get("ID")+"&ProductCategory="+dr.get("ProductCategory");
	diag.OKEvent = addSave;
	diag.show();
}
//删除
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("ID", arr);
	Dialog.confirm("确认要删除？", function() {
		Server.sendRequest("com.sinosoft.cms.document.MailCategoryProduct.deleteConfig", dc, function(
				response) {
			Dialog.alert(response.Message, function() {
				if (response.Status == 1) {
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}
//关联产品
function relProduct(){
	var arrs = DataGrid.getSelectedData("dg1");
	var drs = arrs.Rows;
	var dr = drs[0];
	if (!drs || drs.length == 0) {
		Dialog.alert("请选择一条进行编辑 ！");
		return;
	}
	if (!drs || drs.length >= 2) {
		Dialog.alert("只能选择一条进行编辑！");
		return;
	}
	var productID = drs[0].get("product");
	var id = drs[0].get("ID");
	var diag = new Dialog("Diag3");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "产品列表";
	diag.URL = "Document/MailConfigProductDialog.jsp?productID="+productID+"&relProductId="+id;
	diag.OKEvent = saveRelProduct;
	diag.show();
	diag.CancelButton.value = "取消";
	var  producttimeoutID=setTimeout(function (){
		var productArray=productID.split(",");
		for(var i=0;i<productArray.length;i++){
			$DW.DataGrid.select($DW.$("dg2"),productArray[i]);
		}
		clearTimeout(producttimeoutID);
	},1500 );
}
//保存选中产品
function saveRelProduct(){
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	var dc = new DataCollection();
	if(!arr){
		Dialog.alert("请选择产品!");
		return;
	}
	if(arr.length > 3){
		Dialog.alert("超出上限 3 款产品!");
		return;
	}
	var id = $DW.$V("relProductId");
	dc.add("ProductIDs",arr);
	dc.add("ID",id);
	
	Server.sendRequest("com.sinosoft.cms.document.MailCategoryProduct.saveRelProduct", dc, function(
				response) {
		Dialog.alert(response.Message, function() {
				if (response.Status == 1) {
					$D.close();
					DataGrid.loadData('dg1');
				}
			});
	});
}
</script>
</head>
<body>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">
			<tr height="10px;"></tr>
			<tr height="10px;"></tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td style="padding:6px 10px;" class="blockTd">
						<div style="float: left">
							<z:tbutton onClick="create()"><img src="../Icons/icon018a4.gif" />新建</z:tbutton> 
		                    <z:tbutton onClick="edit()"><img src="../Icons/icon018a4.gif" />编辑</z:tbutton> 
		                    <z:tbutton onClick="del()"><img src="../Icons/icon018a4.gif" />删除</z:tbutton>
		                    <z:tbutton onClick="relProduct()"><img src="../Icons/icon018a4.gif" />关联产品</z:tbutton>  
						</div>
						</td>
					</tr>
					<tr>
						<td style="padding:2px 10px;">
						</td>
					</tr>
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
						<z:datagrid id="dg1" method="com.sinosoft.cms.document.MailCategoryProduct.dg1DataBind"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" afterdrag="afterRowDragEnd"
								style="table-layout: fixed" fixedHeight="240px">
								<tr ztype="head" class="dataTableHead">
									<td width="10%" height="30" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="10%" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td width="30%"><strong>产品品类</strong></td>
								</tr>
								<tr style="background-color: #F9FBFC">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>${ProductCategoryName}</td>
								</tr>
							</table>
						
						</z:datagrid></td>
					</tr>
				</table>
				</td>
			<tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>