<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>链接</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg2").afterEdit= function(tr,dr){
		dr.set("Orderflag",$V("Orderflag"));
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 400;
	diag.Height = 150;
	diag.MessageTitle = "请填写链接名称和链接URL";
	diag.Title = "新建链接";
	diag.URL = "Site/NoticeDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.ShowMessageRow = true;
	diag.OKEvent = addSave;
	diag.show();
}
function addSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("myform");
	Server.sendRequest("com.sinosoft.cms.site.Notice.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg2');
			}
		});
	});
}
function edit(){
	var arr = DataGrid.getSelectedValue("dg2");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要编辑的动态内容！");
		return;
	}if(arr.length>1){
		Dialog.alert("只能同时编辑一条动态内容！");
		return;
	}
	var id = arr[0];
	var diag = new Dialog("Diag2");
	diag.Width = 400;
	diag.Height = 150;
	diag.Title = "链接修改";
	diag.URL = "Site/NoticeDialog.jsp?ID="+id;
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.ShowMessageRow = true;
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("myform");
	Server.sendRequest("com.sinosoft.cms.site.Notice.edit",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg2');
		}
	});
}
function del(){
	var arr = DataGrid.getSelectedValue("dg2");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("您确认要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.site.Notice.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg2');
				}
			});
		});
	});
}
function save(){
	DataGrid.save("dg2","com.sinosoft.cms.site.Notice.save",function(){DataGrid.loadData('dg2');});
}
function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	
	var order = sourceDr.get("OrderFlag");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg2").DataSource;
	var i = rowIndex-1;
	if(i!=0){
		target = targetDr.get("OrderFlag");
		dc.add("Type","After");		
	}else{
		dc.add("Type","Before");
		target = $("dg2").DataSource.get(1,"OrderFlag");
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	DataGrid.showLoading("dg2");
	Server.sendRequest("com.sinosoft.cms.site.Notice.sortColumn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg2");
			}
		});
	});
}
</script>
</head>
<body>
<z:init>
<input type="hidden" id="LinkGroupID" value="${LinkGroupID}">
<input type="hidden" id="Type" name="Type" value="${Type}">
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
		<tr>
			<td style="padding:4px 5px;">
			<z:tbutton onClick="add();"><img src="../Icons/icon028a2.gif" />新建</z:tbutton> 
			<z:tbutton onClick="edit();"><img src="../Icons/icon028a16.gif" />修改</z:tbutton> 
			<z:tbutton onClick="save();"><img src="../Icons/icon028a16.gif" />保存</z:tbutton> 
			<z:tbutton onClick="del();"><img src="../Icons/icon028a3.gif" />删除</z:tbutton></td>
		</tr>
		<tr>
			<td
				style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<z:datagrid id="dg2" method="com.sinosoft.cms.site.Notice.dg1DataBind">
				<table width="100%" cellpadding="2" cellspacing="0"
					class="dataTable" afterDrag="afterRowDragEnd">
					<tr ztype="head" class="dataTableHead">
						<td width="8%" ztype="RowNo" drag="true"><img
							src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
						<td width="8%" ztype="selector" field="id">&nbsp;</td>
						<td width="10%"><strong>链接名称</strong></td>
						<td width="15%"><strong>链接地址</strong></td>
						<td width="10%"><strong>顺序值</strong></td>
						<td width="20%"><strong>类型</strong></td>
					</tr>
					<tr>
						<td width="6%">&nbsp;</td>
						<td width="5%">&nbsp;</td>
						<td>${Name}</td>
						<td>${URL}</td>
						<td>${Orderflag}</td>
						<td>${NoticeTypeName}</td>
					</tr>
					<tr ztype="edit">
						<td width="8%">&nbsp;</td>
						<td width="8%">&nbsp;</td>
						<td>${Name}</td>
						<td>${URL}</td>
						<td><input type="text" class="input1" id="Orderflag" value="${Orderflag}"
							style="width:250px"></td>
						<td>${NoticeTypeName}</td>
					</tr>
						
				</table>
			</z:datagrid></td>
		</tr>
	</table>
</z:init>
</body>
</html>
