<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head> 
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品上线信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function search(){
	DataGrid.setParam("module_dg1",Constant.PageIndex,0);
	DataGrid.setParam("module_dg1","ComCode",$V("ComCode"));
	DataGrid.setParam("module_dg1","InsureName",$V("InsureName"));
	DataGrid.loadData("module_dg1");
}

function baseInfoEdit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 500;
	diag.Title = "保险公司基础信息配置" ;
	diag.URL = "Document/ProductBaseEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}
 
function tbDataSyn(channelSn){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 500;
	diag.Title = "淘宝/去哪儿基础数据同步" ;
	diag.URL = "Document/TBDataSynDialog.jsp?Id="+arr[0]+"&channelSn="+channelSn;
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	$D.close();
	DataGrid.loadData("module_dg1");
}

</script>
</head>
<body>
<z:init method="com.sinosoft.product.ProductInfo.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:0 8px 4px;">
					    <z:tbutton onClick="baseInfoEdit()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>基础信息配置</z:tbutton>
					    <z:tbutton onClick="tbDataSyn('TB')"><img src="../Icons/icon022a2.gif" width="20" height="20"/>淘宝数据同步配置</z:tbutton>
					    <z:tbutton onClick="tbDataSyn('QN')"><img src="../Icons/icon022a2.gif" width="20" height="20"/>去哪数据同步配置</z:tbutton>
	            	</td>
	           </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
						 保险公司编码：<input name="ComCode" type="text" id="ComCode"> &nbsp;
						 保险公司名称：<input name="InsureName" type="text" id="InsureName"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="module_dg1" method="com.sinosoft.product.ProductInfo.dg5DataBind"  size="20" autoFill="false" multiSelect="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="10%"><strong>保险公司编码</strong></td>
                  <td width="10%"><strong>保险公司名称</strong></td>
                  <td width="15%"><strong>投保人与被保人关系</strong></td>
                  <td width="15%"><strong>受益人与被保人关系</strong></td>
                  <td width="30%"><strong>证件类型</strong></td>
                  <td width="5%"><strong>性别</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${Remark6}</td>
                  <td>${InsureName} </td>
                  <td>${Relation} </td>
                  <td>${BnfRelation} </td>
                  <td>${Certificate} </td>
                  <td>${Sex} </td>
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
