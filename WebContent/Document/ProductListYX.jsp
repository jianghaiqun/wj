<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品维护管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function searchData(){
	DataGrid.setParam("product_dg1",Constant.PageIndex,0);
	DataGrid.setParam("product_dg1","productName",$V("productName"));
	DataGrid.setParam("product_dg1","productID",$V("productID"));
	DataGrid.setParam("product_dg1","SupplierCode",$V("SupplierCode"));
	DataGrid.loadData("product_dg1");
}

function resetSerch(){
	$S("productName","");
	$S("productID","");
	$S("SupplierCode","");
}

function updateLights(){
	var arr = DataGrid.getSelectedValue("product_dg1");
	var arr1 = DataGrid.getSelectedData("product_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var drs = arr1.Rows;
	var dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width =550;
	diag.Height = 250;
	diag.Title = "修改产品亮点" ;
	diag.URL = "Document/ProductHighlightsDialog.jsp?productId="+dr.get("productid");
	diag.show();
	diag.OKButton.hide();
	
	
	/*var dc = new DataCollection();
	dc.add("productID", dr.get("productid"));
	dc.add("Points", dr.get("Points"));
	Server.sendRequest("com.sinosoft.cms.dataservice.ProductListYX.setPoints", dc,
			function(response) {
				Dialog.alert(response.Message);
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			}); */
}

function orderDuty(){
	var arr = DataGrid.getSelectedValue("product_dg1");
	var arr1 = DataGrid.getSelectedData("product_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var drs = arr1.Rows;
	var dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width =800;
	diag.Height = 320;
	diag.Title = "修改产品亮点" ;
	diag.URL = "Document/ProductDutyDialog.jsp?productId="+dr.get("productid");
	diag.show();
	diag.OKButton.hide();
	
}
</script>
</head>
<body >
<z:init method="com.sinosoft.cms.dataservice.ProductListYX.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
          <tr>
				<td style="padding:10px 10px;">
					<div style="float:left;"> 
			    		 产品名称&nbsp;<input name="productName" type="text" id="productName"> &nbsp;
			    		 产品编码&nbsp;<input name="productID" type="text" id="productID"> &nbsp;
						 保险公司&nbsp;
						 <z:select name='SupplierCode' id='SupplierCode' style="width:150px;">
						 	${SupplierCode}
						 </z:select>&nbsp;
				    </div>
				</td>
			</tr>
			<tr>
				<td>
					<z:tbutton onClick="searchData();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>查询</z:tbutton>
					<z:tbutton onClick="resetSerch();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>重置查询条件</z:tbutton>
					<z:tbutton onClick="updateLights();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>修改产品亮点</z:tbutton>
             		<z:tbutton onClick="orderDuty();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>产品责任排序</z:tbutton>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="product_dg1" method="com.sinosoft.cms.dataservice.ProductListYX.dg1DataBind"  page="true" size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="18%"><strong>产品名称</strong></td>
                  <td width="10%"><strong>产品编码</strong></td>
                  <td width="10%"><strong>保险公司</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ProductName}</td>
                  <td>${ProductID} </td>
                  <td>${InsureName}</td>
                </tr>
                <tr ztype="pagebar">
					<td colspan="6" align="left">${PageBar}</td>
				</tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
 </z:init>
</body>
</html>
