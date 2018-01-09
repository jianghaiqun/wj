<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.module.ProductTemplate.init">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>产品Excel模版配置</title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		function search1(){
			DataGrid.setParam("tempalte_dg3",Constant.PageIndex,0);
			DataGrid.setParam("tempalte_dg3","ProductID",$V("ProductID"));
			DataGrid.setParam("tempalte_dg3","ProductName",$V("ProductName"));
			DataGrid.loadData("tempalte_dg3");
		}
		
		function search2(){
			DataGrid.setParam("tempalte_dg4",Constant.PageIndex,0);
			DataGrid.setParam("tempalte_dg4","FactorName",$V("FactorName"));
			DataGrid.loadData("tempalte_dg4");
		}
	</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	<table width="100%" height="100%" border="0">
		<tr>
			<td style="padding:3px 5px;">
				<fieldset>
	   			   <legend><label>产品查询信息</label></legend>
					<div style="float:left;"> 
						 产品编码&nbsp;<input name="ProductID" type="text" id="ProductID"> &nbsp;
						 产品名称&nbsp;<input name="ProductName" type="text" id="ProductName"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search1()">
				    </div>
				  </fieldset>
			</td>
		</tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="tempalte_dg3" method="com.sinosoft.module.ProductTemplate.dg3DataBind" page="true" lazy="false" size="10" autoFill="true">
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
	   <tr>
			<td style="padding:3px 5px;">
				<fieldset>
	   			   <legend><label>模版查询信息</label></legend>
					<div style="float:left;"> 
						 模版名称&nbsp;<input name="FactorName" type="text" id="FactorName"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search2()">
				    </div>
				  </fieldset>
			</td>
		</tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="tempalte_dg4" method="com.sinosoft.module.ProductTemplate.dg4DataBind" page="true" lazy="false" size="10" autoFill="true"  multiSelect="false">
		              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
		                <tr ztype="head" class="dataTableHead">
		                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
		                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
		                  <td width="15%"><strong>Excel模板名称</strong></td>
		                  <td width="45%"><strong>备注</strong></td>
		                </tr>
		                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
						  <td>&nbsp;</td>
						  <td>&nbsp;</td>
		                  <td>${templateName}</td>
						  <td>${ExcelRemark}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="4" align="left">${PageBar}</td>
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