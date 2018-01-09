<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义表单</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
	function codeList(id) {
		var diag = new Dialog("Diag1");
		diag.Width = 800;
		diag.Height = 400;
		diag.Title = "栏目字段管理";
		diag.URL = "message/CatalogColumn.jsp";
		diag.onLoad = function() {
			$DW.changeStatus(id);
		};
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value="关闭";
	}
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 300;
		diag.Height = 100;
		diag.Title = "新建栏目类型";
		diag.URL = "message/CatalogTypeDialog.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.show();
	}

	function addSave() {
		var dc = $DW.Form.getData("form2");
		var noCheckArr = [];
		if ($DW.Verify.hasError(noCheckArr, "form2")) {
			return;
		}
		Server.sendRequest("com.sinosoft.message.CatalogTypeUI.save", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData("dg1");
						}
					});
				});
	}
	function edit() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要修改的行！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("只能修改1条信息！");
			return;
		}
		dr = drs[0];
		editDialog(dr.get("ID"));
	}

	function editDialog(ID) {
		var diag = new Dialog("Diag1");
		diag.Width = 300;
		diag.Height = 100;
		diag.Title = "编辑栏目类型";
		diag.URL = "message/CatalogTypeDialog.jsp?ID=" + ID;
		diag.onLoad = function() {
		};
		diag.OKEvent = editSave;
		diag.show();
	}

	function editSave() {
		var dc = $DW.Form.getData("form2");
		var noCheckArr = [];
		if ($DW.Verify.hasError(noCheckArr, "form2")) {
			return;
		}
		Server.sendRequest("com.sinosoft.message.CatalogTypeUI.save", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData("dg1");
						}
					});
				});
	}

	function del() {
		Dialog.confirm("您确认要删除评论吗?",function(){
			var dc = new DataCollection();
			var arr = DataGrid.getSelectedValue("dg1");
			dc.add("IDs", arr.join());
			Server.sendRequest("com.sinosoft.message.CatalogTypeUI.del", dc,
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
</script>
</head>
<body>
	<z:init>
		<div style="padding: 2px;">
			<table width="100%" cellpadding="0" cellspacing="0"
				style="margin-bottom: 4px;">
				<tr>
					<td><input name="CatalogID" id="CatalogID"
						value="${CatalogID}" type="hidden" /> <input name="InnerCode"
						type="hidden" id="InnerCode" value="${InnerCode}" /> <z:tbutton
							id="BtnAdd" priv="article_manage" onClick="add()">
							<img src="../Icons/icon024a2.gif" />新建</z:tbutton> <z:tbutton id="BtnEdit"
							priv="article_manage" onClick="edit()">
							<img src="../Icons/icon024a4.gif" />编辑</z:tbutton> <z:tbutton id="BtnDel"
							priv="article_manage" onClick="del()">
							<img src="../Icons/icon024a3.gif" />删除</z:tbutton>
				</tr>
			</table>
			<z:datagrid id="dg1"
				method="com.sinosoft.message.CatalogTypeUI.dg1DataBind" page="false">
				<table width="100%" cellpadding="2" cellspacing="0"
					class="dataTable">
					<tr ztype="head" class="dataTableHead">
						<td width="2%" ztype="RowNo">&nbsp;</td>
						<td width="3%" ztype="selector" field="id">&nbsp;</td>
						<td width="15%"><b>字段ID</b>
						</td>
						<td width="30%"><b>名称</b>
						</td>
						<td width="20%"><b>代码</b>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>${Id}</td>
						<td><span class="fc_bl3"><a href="javascript:void(0);" onClick="codeList(${Id});">${TypeName}</a></span></td>
						<td>${TypeCode}</td>
					</tr>
				</table>
			</z:datagrid>
		</div>
	</z:init>
</body>
</html>
