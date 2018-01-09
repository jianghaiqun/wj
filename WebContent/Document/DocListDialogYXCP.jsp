<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 380;
	diag.Height = 250;
	diag.Title = "常见问题";
	diag.URL = "Document/DocListDialogAddCJWT.jsp?RelaId="+$V("RelaId");
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建常见问题";
	diag.show();
}
function addSave() {
	var dc = $DW.Form.getData("form2");
	var noCheckArr = [];
	if ($DW.Verify.hasError(noCheckArr, "form2")) {
		return;
	}
	Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.add", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData("dg1");
					}
				});
			});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的问题！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改一条信息！");
		return;
	}
	dr = drs[0];
  editDialog(dr.get("Id"));
}
function editDialog(ID){
	var diag = new Dialog("Diag2");
	diag.Width = 380;
	diag.Height = 250;
	diag.Title = "修改常见问题";
	diag.URL = "Document/DocListDialogEditCJWT.jsp?Id="+ID;
	diag.onLoad = function(){
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改常见问题";
	diag.show();
}
function editSave() {
	var dc = $DW.Form.getData("form2");
	var noCheckArr = [];
	if ($DW.Verify.hasError(noCheckArr, "form2")) {
		return;
	}
	Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.save", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData("dg1");
					}
				});
			});
}
function create(){
	Dialog.confirm("您确认要生成问题吗?",function(){
		var dc = new DataCollection();
		var arr = DataGrid.getSelectedValue("dg1");
		dc.add("Id", arr.join());
		Server.sendRequest("com.sinosoft.cms.document.ArticleListYXCP.create", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	});
	
}


function del() {
	Dialog.confirm("您确认要删除问题吗?",function(){
		var dc = new DataCollection();
		var arr = DataGrid.getSelectedValue("dg1");
		dc.add("Id", arr.join());
		Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.del", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	});
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	var order = $("dg1").DataSource.get(rowIndex-1,"OrderFlag");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	var i = rowIndex-1;
	if(i!=0){
		target = ds.get(i-1,"OrderFlag");
		dc.add("Type","Before");
	}else{
		dc.add("Type","After");
		target = $("dg1").DataSource.get(1,"OrderFlag");
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.sortColumn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}
function setArticleId(id){
	document.getElementById("RelaId").value=id;
	DataGrid.setParam("dg1","RelaId",id);
	DataGrid.loadData("dg1");
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<input name="RelaId" type="hidden" id="RelaId" />
<z:tbutton id="article_manage" onClick="add()"><img src="../Icons/icon003a2.gif" />新建</z:tbutton>
<z:tbutton id="article_manage" onClick="edit()"><img src="../Icons/icon003a4.gif" />修改</z:tbutton>
<z:tbutton id="article_manage" onClick="del()"><img src="../Icons/icon018a3.gif" />删除</z:tbutton>
<z:tbutton id="article_manage" onClick="create()"><img src="../Icons/icon003a2.gif" />生成</z:tbutton>
<div style="padding:5px;"><z:datagrid id="dg1" method="com.sinosoft.cms.document.ArticleListCJWT.dg2DataBind">
	<table width="100%" cellpadding="2" cellspacing="0" 
		class="dataTable" afterDrag="afterRowDragEnd">
		<tr ztype="head" class="dataTableHead">
			<td width="8%" ztype="RowNo" drag="true"><img
							src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
			<td width="3%" height="30" ztype="Selector" field="id">&nbsp;</td>
			<td width="20%""><b>常见问题</b></td>
			<td width="30%"><strong>问题答案</strong></td>
			<td width="20%"><strong>操作时间</strong></td>
		</tr>
		<tr style1="background-color:#FFFFFF"
			style2="background-color:#F9FBFC">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${FAQName}</td>
			<td>${FAQContent}</td>
			<td>${ADDTime}</td>
		</tr>
	</table>
</z:datagrid></div>
</body>
</html>
