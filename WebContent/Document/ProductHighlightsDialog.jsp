<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function addPro(){
	var productId = $V("productId");
	var content = $V("content");
	var dc = new DataCollection();
	dc.add("productId",productId);
	dc.add("content",content);
	Server.sendRequest("com.sinosoft.cms.dataservice.ProductListYX.addHighlights",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData('dg1');
				$S("content","");
			}
		});
	});
}
function delPro(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要删除的数据！");
		return;
	}
	var arr1 = DataGrid.getSelectedData("dg1");
	var drs = arr1.Rows;
	var dr = drs[0];
	var dc = new DataCollection();
	dc.add("Id",dr.get("id"));
	dc.add("productId",dr.get("productID"));
	Server.sendRequest("com.sinosoft.cms.dataservice.ProductListYX.deleteHighlights",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData('dg1');
				$S("content","");
			}
		});
	});
}
function savePro(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var arr1 = DataGrid.getSelectedData("dg1");
	var drs = arr1.Rows;
	var dr = drs[0];
	var dc = new DataCollection();
	dc.add("Id",dr.get("id"));
	dc.add("productId",dr.get("productID"));
	dc.add("content",$V("content"));
	Server.sendRequest("com.sinosoft.cms.dataservice.ProductListYX.updateHighlights",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData('dg1');
				$S("content","");
			}
		});
	});
}
function setValue(){
	var arr1 = DataGrid.getSelectedData("dg1");
	var drs = arr1.Rows;
	var dr = drs[0];
	$S("content",dr.get("detail"));
}
</script>
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.ProductListYX.initDialog">
<form name="fm_reply" id="fm_reply">
			<table width="100%" cellpadding="2" cellspacing="0"
				class="blockTable" id="proHighlights">
				<input type="hidden" value ="${productId}" id="productId">
				<tr height="50px">
					<td align="center"><textarea id="content"
							style="width: 530px; height: 30px" ></textarea>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td style="text-align: left;"><input id="add" name="add" onClick="addPro();"
						type='button' value='添加'>
						<input id="del" name="del" onClick="delPro();"
						type='button' value='删除'>
						<input id="save" name="save" onClick="savePro();"
						type='button' value='修改'>
					</td>
				</tr>
			</table>
</form>
</z:init>
		<table width="100%" border="0" cellspacing="0" cellpadding="3"
			class="blockTable">
			<tr>
				<td
					style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
					<z:datagrid id="dg1"
						method="com.sinosoft.cms.dataservice.ProductListYX.dg1DataBindDialog" size="5"
						scroll="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable"
							style="text-align: center; table-layout: fixed;"
							fixedHeight="480px">
							<tr ztype="head" class="dataTableHead">
								<td width="35" ztype="RowNo"><strong>序号</strong></td>
								<td width="20" ztype="selector" field="UserName">&nbsp;</td>
								<td width="80" style="text-align: center;"><b>产品ID</b>
								</td>
								<td width="180" style="text-align: center;"><b>产品亮点</b>
								</td>
							</tr>
							<tr style="background-color: #F9FBFC" onclick="setValue();">
								<td width="20">&nbsp;</td>
								<td>&nbsp;</td>
								<td style="text-align: center;" title="${productID}">${productID}</td>
								<td style="text-align: center;" title="${detail}">${detail}</td>
							</tr>
						</table>
					</z:datagrid>
				</td>
			</tr>
		</table>
</body>
</html>