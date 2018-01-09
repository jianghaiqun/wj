<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模版配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if( ele.id == 'FactorType' || ele.id == 'FactorCode'||  ele.id == 'FactorName' ){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("module_dg3",Constant.PageIndex,0);
	DataGrid.setParam("module_dg3","FactorType",$V("FactorType"));
	DataGrid.setParam("module_dg3","FactorCode",$V("FactorCode"));
	DataGrid.setParam("module_dg3","FactorName",$V("FactorName"));
	DataGrid.loadData("module_dg3");
}


function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "生成模版" ;
	diag.URL = "Module/ModuleConfigureAddDialog.jsp" ;
	diag.onLoad = function(){
		$DW.$("FactorName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	var dt = $DW.DataGrid.getSelectedData("module_dg2");
	if(!dt || dt.getRowCount() == 0){
		Dialog.alert("请先要生成模版的数据！");
		return;
	}
	dc.add("DT",dt);
	Server.sendRequest("com.sinosoft.module.ModuleConfigure.addSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("module_dg3");
			}
		})
	});

}

function del(){
	var dt = DataGrid.getSelectedData("module_dg3");
	if(dt.getRowCount()==0 ){
		Dialog.alert("请先选择要删除的行!");
		return;
	}
	dr = dt.getDataRow(0);
	Dialog.confirm("删除后可能影响购买流程，确认要删除？",function(){
		var dc = new DataCollection();
		dc.add("FactorId",dr.get("FactorId"));
		Server.sendRequest("com.sinosoft.module.ModuleConfigure.delModule",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg3");
				}
			});
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("module_dg3");
	if(dt.getRowCount()==0 ){
		Dialog.alert("先选择要编辑的数据!");
		return;
	}
	dr = dt.getDataRow(0);
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 450;
	diag.Title = "编辑模版" ;
	diag.URL = "Module/ModuleConfigureEditDialog.jsp?FactorId=" + dr.get("FactorId");
	diag.onLoad = function(){
		$DW.$("FactorName").focus();
	};
	diag.CancelEvent = CancelClose;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
}

function CancelClose(){
	$D.close();
	DataGrid.loadData("module_dg3");
}
function show(){
	var dt = DataGrid.getSelectedData("module_dg3");
	if(dt.getRowCount()==0 ){
		Dialog.alert("请先选择要预览的页面!");
		return;
	}
	dr = dt.getDataRow(0);
	var diag = new Dialog("Diag1");
	diag.Width = 1005;
	diag.Height = 560;
	diag.Title = "投保录入页--预览" ;
	diag.URL = "/shop/order_show!buyNow.action?productId="+dr.get("FactorId")+"&period=1Y-18Y&FactorId=" + dr.get("FactorId");
	diag.onLoad = function(){
		$DW.$("FactorName").focus();
	};
	diag.CancelEvent = CancelClose;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
}

</script>
</head>
<body>
<z:init method="com.sinosoft.module.ModuleConfigure.initModuleElement">
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding: 0 8px 4px;"><z:tbutton onClick="add()">
						<img src="../Icons/icon022a2.gif" width="20" height="20" />新建</z:tbutton> <z:tbutton
						onClick="edit()">
						<img src="../Icons/icon022a4.gif" width="20" height="20" />编辑</z:tbutton> <z:tbutton
						onClick="del()">
						<img src="../Icons/icon022a3.gif" width="20" height="20" />删除</z:tbutton> <z:tbutton
						onClick="show()">
						<img src="../Icons/icon022a1.gif" width="20" height="20" />预览</z:tbutton></td>
				</tr>
				<tr>
					<td style="padding: 2px 10px;">
						<div style="float: left;">
							投保模板名称&nbsp;<input name="FactorName" type="text" id="FactorName">&nbsp;  
							要素类型&nbsp;<z:select id='FactorType'>${FactorTypeList}</z:select>&nbsp;
							要素编码&nbsp;<input name="FactorCode" type="text" id="FactorCode">&nbsp;  
						 	 <input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
					 	</div>
					</td>
				</tr>
				<tr>
					<td style="padding: 0px 5px;">
					  <z:datagrid id="module_dg3" method="com.sinosoft.module.ModuleConfigure.dg1DataBind" page="true" size="15" autoFill="false" multiSelect="false">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="20%"><strong>投保模板名称</strong></td>
								<td width="20%"><strong>备注</strong></td>
							</tr>
							<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${FactorName}</td>
								<td>${Memo}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="6" align="left">${PageBar}</td>
							</tr>
						</table>
					  </z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</z:init>
</body>
</html>
