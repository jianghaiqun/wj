<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品模版配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 720;
	diag.Height = 450;
	diag.Title = "产品模版配置";
	diag.URL = "Module/ProductTemplateAddDialog.jsp";
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addExcel(){
	var diag = new Dialog("Diag2");
	diag.Width = 720;
	diag.Height = 450;
	diag.Title = "产品Excel模版配置";
	diag.URL = "Module/ProductTemplateExcelDialog.jsp";
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = addExcelSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	
	var DT_Product = $DW.DataGrid.getSelectedData("tempalte_dg1");
	if(!DT_Product || DT_Product.getRowCount() == 0){
		Dialog.alert("请选择产品！");
		return;
	}
	dc.add("DT_Product", DT_Product);
	
	var DT_Template = $DW.DataGrid.getSelectedData("tempalte_dg2");
	if(!DT_Template || DT_Template.getRowCount() == 0){
		Dialog.alert("请选择模版！");
		return;
	}
	dc.add("DT_Template", DT_Template);
	
	Server.sendRequest("com.sinosoft.module.ProductTemplate.addSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("template_dg1");
			}
		});
	});
}
function addExcelSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	
	var DT_Product = $DW.DataGrid.getSelectedData("tempalte_dg3");
	if(!DT_Product || DT_Product.getRowCount() == 0){
		Dialog.alert("请选择产品！");
		return;
	}
	dc.add("DT_Product", DT_Product);
	
	var DT_Template = $DW.DataGrid.getSelectedData("tempalte_dg4");
	if(!DT_Template || DT_Template.getRowCount() == 0){
		Dialog.alert("请选择模版！");
		return;
	}
	dc.add("DT_Template", DT_Template);
	
	Server.sendRequest("com.sinosoft.module.ProductTemplate.addExcelSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("template_dg1");
			}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("template_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("删除后可能影响购买流程，确认要删除？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.module.ProductTemplate.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("template_dg1");
				}
			});
		});
	});
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if( ele.id == 'ProductID' || ele.id == 'ProductName'||  ele.id == 'TemplateName' ){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("template_dg1",Constant.PageIndex,0);
	DataGrid.setParam("template_dg1","ProductID",$V("ProductID"));
	DataGrid.setParam("template_dg1","ProductName",$V("ProductName"));
	DataGrid.setParam("template_dg1","TemplateName",$V("TemplateName"));
	DataGrid.loadData("template_dg1");
}

function showWap(){
	var pid = $V("ProductID").trim();
	if(pid == ""){
		Dialog.alert("请输入产品编码！");
		return;
	}
	var diag = new Dialog("Diag2");
	diag.Width = 720;
	diag.Height = 450;
	diag.Title = "显示wap元素排序";
	diag.URL = "Module/WapModuleOrderDialog.jsp?pid="+pid;
	diag.show();
	diag.OKButton.hide();
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:0 8px 4px;">
					    <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建产品模板</z:tbutton>
					    <z:tbutton onClick="addExcel()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>配置excel模板</z:tbutton>
             		    <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
             		    <z:tbutton onClick="showWap()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>设置wap投保元素顺序</z:tbutton>
	            	</td>
	           </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
						 产品编码&nbsp;<input name="ProductID" type="text" id="ProductID"> &nbsp;
						 产品名称&nbsp;<input name="ProductName" type="text" id="ProductName"> &nbsp;
						 模版名称&nbsp;<input name="TemplateName" type="text" id="TemplateName"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="template_dg1" method="com.sinosoft.module.ProductTemplate.dg1DataBind"  size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="8%"><strong>产品编码</strong></td>
                  <td width="15%"><strong>产品名称</strong></td>
                  <td width="10%"><strong>模版名称</strong></td>
                  <td width="10%"><strong>Excel模版名称</strong></td>
                  <td width="45%"><strong>Excel模版说明</strong></td>
                  <td width="10%"><strong>创建时间</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${productId}</td>
                  <td>${ProductName}</td>
                  <td>${TemplateName} </td>
                  <td>${ExcelTemplate} </td>
                  <td>${ExcelRemark} </td>
                  <td>${MakeDate}</td>
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
</body>
</html>
