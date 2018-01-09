<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
//产品列表
function getProductList(num){
	var diag = new Dialog("Diag11");
	diag.Width =600;
	diag.Height = 360;
	diag.Title = "选择"+$V("codeName")+"分类的产品";
	diag.URL = "Document/RecommendProductDialog.jsp?codeValue="+$V("codeValue")+"&num="+num;
	diag.onLoad = function() {
	};

	diag.OKEvent = selectProduct;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "选择"+$V("codeName")+"分类的产品";
	diag.show();
}

function selectProduct() {
	var arr = $DW.DataGrid.getSelectedValue("dg4");
	var num = $DW.$V("num");
	if (!arr || arr.length ==0) {
		jQuery("#ProductName"+num).val("");
		jQuery("#ProductId"+num).val("");
	} else {
		if (arr.length > 1) {
			Dialog.alert("只能选择一个产品！");
			return;
		}
		
		jQuery("#ProductName"+num).val($DW.DataGrid.getSelectedData("dg4").Rows[0].get("productname"));
		jQuery("#ProductId"+num).val(arr[0]);
	}
	
	$D.close();
}

</script>
</head>
<body>
<form name="fm" id="fm">
<z:init method="com.sinosoft.cms.document.RecommendToDetail.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class=common>
				<td height="30" width="80px">推荐产品1：</td>
				<td width="300px"><input name="ProductName1" type="text" value="${ProductName1}"
				style="width: 250px" class="inputText" id="ProductName1"
				disabled="disabled" size="30" readonly="readonly" onmouseover="" />
				<input type="hidden" id="ProductId1" value="${ProductId1}"/>
				<input type="button" id="chooseProduct" name="chooseProduct"
				value="查 找" onClick="getProductList(1)"></td>
			</tr>
			<tr class=common>
				<td height="30">推荐产品2：</td>
				<td><input name="ProductName2" type="text" value="${ProductName2}"
				style="width: 250px" class="inputText" id="ProductName2"
				disabled="disabled" size="30" readonly="readonly" onmouseover="" />
				<input type="hidden" id="ProductId2" value="${ProductId2}"/>
				<input type="button" id="chooseProduct" name="chooseProduct"
				value="查 找" onClick="getProductList(2)"></td>
			</tr>
			<tr class=common>
				<td height="30">推荐产品3：</td>
				<td><input name="ProductName3" type="text" value="${ProductName3}"
				style="width: 250px" class="inputText" id="ProductName3"
				disabled="disabled" size="30" readonly="readonly" onmouseover="" />
				<input type="hidden" id="ProductId3" value="${ProductId3}"/>
				<input type="button" id="chooseProduct" name="chooseProduct"
				value="查 找" onClick="getProductList(3)"></td>
			</tr>
			<tr height="200px">
				<td style="padding: 0px 5px;" colspan="2"><input type="hidden" id="codeValue" value="${codeValue}"/>
				<input type="hidden" id="codeName" value="${codeName}"/>
				<fieldset><legend> <label>系统推荐详细页产品</label> </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td style="padding: 0px 5px;"><z:datagrid id="RecommGrid2" 
							method="com.sinosoft.cms.document.RecommendToDetail.dg1DataBind"
							 scroll="false" multiSelect="false">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" 
								fixedHeight="100px" afterdrag="afterRowDragEnd">
								<tr ztype="head" class="dataTableHead">
									<td width="3%" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16">
									</td>
									<td width="10%"><b>文章ID</b></td>
									<td width="30%"><b>文章名称</b></td>
								</tr>
								<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" >
									<td>&nbsp;</td>
									<td>${RelaArticleID}</td>
									<td>${ProductName}</td>
								</tr>
							</table>
						</z:datagrid></td>
					</tr>
				</table>
				</fieldset>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</z:init>
</form>
</body>
</html>