<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>导购频道配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function addBranch(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	var ParentId="";
	if(drs&&drs.length>0){
		ParentId = drs[0].get("Id");
	}
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 260;
	diag.Title = "新建导购频道";
	diag.URL = "DataService/ShoppingGuideDialog.jsp?ParentId="+ParentId;
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加导购频道";
	diag.Message = "设置导购频道名称、上级导购频道等";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.ShoppingGuide.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的导购频道！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 300;
	diag.Title = "修改导购频道";
	diag.URL = "DataService/ShoppingGuideEditDialog.jsp?Id="+dr.get("Id");
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑导购频道";
	diag.Message = "设置导购频道名称、上级导购频道等";
	diag.show();
}

function editSave() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.ShoppingGuide.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Dialog.confirm("注意：这是级联删除，下级组导购频道会一起删除，您确认要删除吗？",function(){
		Server.sendRequest("com.sinosoft.cms.dataservice.ShoppingGuide.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}				
			});
		});
	});
}

function sortBranch(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	
	var ds = $("dg1").DataSource;
	if (ds.get(rowIndex-1,"Id").length == 4) {
		alert("您选择的是总机构，总机构不需要排序！");
		DataGrid.loadData("dg1");
		return;
	}
	
	if (rowIndex-1 == 0) {
		alert("任何子机构都不能排在总机构前面");
		DataGrid.loadData("dg1");
		return;
	}
	
	var type = "";
	var orderBranch = "";
	var nextBranch = "";
	if (ds.get(rowIndex-1,"ParentId") == ds.get(rowIndex,"ParentId")) {
		type = "Before";
		orderBranch = ds.get(rowIndex-1,"Id");
		nextBranch = ds.get(rowIndex,"Id");
	} else if (ds.get(rowIndex-1,"ParentId") == ds.get(rowIndex-2,"ParentId")) {
		type = "After";
		orderBranch = ds.get(rowIndex-1,"Id");
		nextBranch = ds.get(rowIndex-2,"Id");
	} else {
		alert("不在同一机构下的子机构不能排序！");
		DataGrid.loadData("dg1");
		return;
	}
	var dc = new DataCollection();
	dc.add("OrderType",type);
	dc.add("OrderBranch",orderBranch);
	dc.add("NextBranch",nextBranch);
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.sinosoft.platform.Branch.sortBranch",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

function up(){
	window.history.back(-1);
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon042a1.gif" width="20" height="20" />导购频道树</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;"><z:tbutton onClick="addBranch()">
						<img src="../Icons/icon042a2.gif" width="20" height="20"/>新建</z:tbutton>
					<z:tbutton onClick="edit()">
						<img src="../Icons/icon042a4.gif" width="20" height="20"/>编辑</z:tbutton>
					<z:tbutton onClick="del()">
						<img src="../Icons/icon042a3.gif" width="20" height="20"/>删除</z:tbutton>
						</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.ShoppingGuide.dg1DataBind"
					page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="4%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="4%" ztype="selector" field="Id">&nbsp;</td>
							<td width="29%" ztype="tree" level="TreeLevel"><b>名称</b></td>
							<td width="18%"><strong>链接</strong></td>
							<td width="17%"><strong>图片地址</strong></td>
						</tr>
						<tr onDblClick="edit();">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}</td>
							<td>${Link}</td>
							<td>${Image}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
