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
	diag.Height = 200;
	diag.Title = "种类品类关联-新增";
	diag.URL = "Document/MailConfigCategoryAdd.jsp?emailType="+$V("emailType");
	diag.onLoad = function() {
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新增种类品类关联";
	diag.show();
}
function addSave(){
	var dc = $DW.Form.getData("form2");
	Dialog.confirm("确认要保存？", function() {
		Server.sendRequest("com.sinosoft.cms.document.MailConfigCategory.saveConfig",
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
	diag.Title = "种类品类关联-编辑";
	diag.URL = "Document/MailConfigCategoryAdd.jsp?ID=" + dr.get("ID")+"&emailType="+$V("emailType")+
			"&ProductCategory="+dr.get("ProductCategory")+"&productType="+dr.get("ProductType");
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
		Server.sendRequest("com.sinosoft.cms.document.MailConfigCategory.deleteConfig", dc, function(
				response) {
			Dialog.alert(response.Message, function() {
				if (response.Status == 1) {
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}
</script>
</head>
<body>
<input type="hidden" id="emailType" name="emailType" value="<%=request.getParameter("emailType")%>">
<input type="hidden" id="productID" name="productID" value="<%=request.getParameter("productID")%>">
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
						<z:datagrid id="dg1" method="com.sinosoft.cms.document.MailConfigCategory.dg1DataBind"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" afterdrag="afterRowDragEnd"
								style="table-layout: fixed" fixedHeight="240px">
								<tr ztype="head" class="dataTableHead">
									<td width="10%" height="30" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="10%" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td width="20%"><strong>产品种类</strong></td>
									<td width="30%"><strong>产品品类</strong></td>
								</tr>
								<tr style="background-color: #F9FBFC">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>${ProductTypeName}</td>
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