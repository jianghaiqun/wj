<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
//产品列表
function getProductList1(){
	var diag = new Dialog("Diag11");
	diag.Width =600;
	diag.Height = 360;
	diag.Title = "选择产品";
	diag.URL = "Document/PKProductSearchDialog.jsp";
	diag.onLoad = function() {
	};
	diag.OKEvent = addProduct1;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "选择产品";
	diag.show();
}

function addProduct1() {
	var arr = $DW.DataGrid.getSelectedValue("dg4");
	if (!arr || arr.length ==0) {
		Dialog.alert("请选择产品！");
		return;
	}
	if (arr.length > 1) {
		Dialog.alert("只能选择一个产品！");
		return;
	}
	
	jQuery("#ProductName1").val($DW.DataGrid.getSelectedData("dg4").Rows[0].get("productname"));
	jQuery("#ProductId1").val(arr[0]);
	$D.close();
}

function getProductList2(){
	var diag = new Dialog("Diag12");
	diag.Width =600;
	diag.Height = 360;
	diag.Title = "选择产品";
	diag.URL = "Document/PKProductSearchDialog.jsp";
	diag.onLoad = function() {
	};
	diag.OKEvent = addProduct2;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "选择产品";
	diag.show();
}

function addProduct2() {
	
	var arr = $DW.DataGrid.getSelectedValue("dg4");
	if (!arr || arr.length ==0) {
		Dialog.alert("请选择产品！");
		return;
	}
	if (arr.length > 1) {
		Dialog.alert("只能选择一个产品！");
		return;
	}
	
	jQuery("#ProductName2").val($DW.DataGrid.getSelectedData("dg4").Rows[0].get("productname"));
	jQuery("#ProductId2").val(arr[0]);
	$D.close();
}

</script>
</head>
<body>
	<z:init method="com.sinosoft.cms.document.ShoppingGuidePKManage.initPKProductDialog">
	<form id="form4">
	<input type="hidden" id="ArticleId" name="ArticleId" value="${articleid}">
	
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td>产品1:</td>
			<td><input name="ProductName1" type="text" value="${ProductName1}"
				style="width: 250px" class="inputText" id="ProductName1"
				disabled="disabled" size="30" readonly="readonly" onmouseover="" />
				<input type="hidden" id="ProductId1" value="${ProductId1}"/>
				<input type="button" id="chooseProduct1" name="chooseProduct1"
				value="查 找" onClick="getProductList1()"></td>
		</tr>
		<tr>
			<td>产品2:</td>
			<td><input name="ProductName2" type="text" value="${ProductName2}"
				style="width: 250px" class="inputText" id="ProductName2"
				disabled="disabled" size="30" readonly="readonly" onmouseover="" />
				<input type="hidden" id="ProductId2" value="${ProductId2}"/>
				<input type="button" id="chooseProduct2" name="chooseProduct2"
				value="查 找" onClick="getProductList2()"></td>
		</tr>
	</table>
	</form>
	</z:init>
</body>
</html>