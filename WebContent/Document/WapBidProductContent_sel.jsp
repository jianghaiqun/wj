<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.cms.document.WapSiteBidPageManage.initProductSel">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>产品选择</title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		function search1(){
			DataGrid.setParam("product_dg1",Constant.PageIndex,0);
			DataGrid.setParam("product_dg1","ProductID",$V("ProductID"));
			DataGrid.setParam("product_dg1","ProductName",$V("ProductName"));
			DataGrid.setParam("product_dg1","CompanyCode",$V("CompanyCode"));
			DataGrid.loadData("product_dg1");
		}
	</script>
	</head>
	<body class="dialogBody">
	<form id="form3">
	<table width="100%" border="0">
		<tr>
			<td style="padding:3px 5px;">
				<fieldset>
	   			   <legend><label>产品查询信息</label></legend>
					<div style="float:left;"> 
						 产品编码&nbsp;<input name="ProductID" type="text" id="ProductID"> &nbsp;
						 产品名称&nbsp;<input name="ProductName" type="text" id="ProductName"> &nbsp;
						 保险公司&nbsp;<z:select id='CompanyCode'>${CompanyList}</z:select> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search1()">
				    </div>
				  </fieldset>
			</td>
		</tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="product_dg1" method="com.sinosoft.cms.document.WapSiteBidPageManage.productGd1DataBind" page="true" lazy="false" size="10" autoFill="true">
		              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
		                <tr ztype="head" class="dataTableHead">
		                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
		                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
		                  <td width="12%"><strong>产品编码</strong></td>
		                  <td width="32%"><strong>产品名称</strong></td>
		                  <td width="30%"><strong>保险公司名称</strong></td>
		                  <td width="10%"><strong>险种类型</strong></td>
		                  <td width="10%"><strong>上架状态</strong></td>
		                </tr>
		                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" >
		                  <td>&nbsp;</td>
		                  <td>&nbsp;</td>
		                  <td>${ProductID}</td>
		                  <td>${ProductName}</td>
		                  <td>${InsureName}</td>
		                  <td>${ProductGenera}</td>
		                  <td>${IsPublish}</td>
		                </tr>
						<tr ztype="pagebar">
							<td colspan="7" align="left">${PageBar}</td>
						</tr>
		              </table>
		            </z:datagrid>
			</td>
		</tr>
	</table>
	</form>
	</body>
	</html>
</z:init>