<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员特权管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	
	// 查询
	function query() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.loadData("dg1");
	}

	// 新建
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 600;
		diag.Height = 360;
		diag.Title = "会员特权-新增";
		diag.URL = "DataService/MemberPrivilegesAddDialog.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "会员特权";
		diag.show();
	}

	// 新建保存
	function addSave() {
		var dc = $DW.Form.getData("AddForm");
		if ($DW.Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.addPrivileges",
				dc, function() {
					Dialog.endWait();
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	}

	// 编辑
	function edit() {
		var arrs = DataGrid.getSelectedData("dg1");
		var drs = arrs.Rows;
		var dr = drs[0];
		if (!drs || drs.length == 0) {
			Dialog.alert("请选择一条进行编辑 ！");
			return;
		}
		if (!drs || drs.length >= 2) {
			Dialog.alert("只能选择一条进行编辑！");
			return;
		}

		var diag = new Dialog("Diag1");
		diag.Width = 600;
		diag.Height = 360;
		diag.Title = "会员特权-编辑";
		diag.URL = "DataService/MemberPrivilegesEditDialog.jsp?id=" + dr.get("id");
		diag.OKEvent = editSave;
		diag.show();
	}

	//编辑保存
	function editSave(){
		var dc = $DW.Form.getData("EditForm");
		if ($DW.Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.editPrivileges",dc,function(){
			var response = Server.getResponse();
			Dialog.alert(response.Message);
			if(response.Status==1){
					$D.close();
					DataGrid.loadData('dg1');
			}
		});
	}

	// 删除
	function del() {
		var arr = DataGrid.getSelectedValue("dg1");
		if(arr == null){
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		var dc = new DataCollection();
		
		dc.add("ids", arr);
		Dialog.confirm("确认要删除？", function() {
			Server.sendRequest("com.sinosoft.cms.dataservice.Member.delPrivileges", dc, function(
					response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData('dg1');
					}
				});
			});
		});
	}
</script>
</head>
<body>
	<table>
		<tr>
			<td>
			 <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
   			 <z:tbutton onClick="edit()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>编辑</z:tbutton>
    		 <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
    		</td>
		</tr>
	</table>
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.dataservice.Member.memberPrivileges" size="10"
					scroll="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16">
							</td>
							<td width="5%" ztype="Selector" field="id">&nbsp;</td>
							<td width="15%"><strong>会员特权名称</strong>
							</td>
							<td width="15%"><strong>会员级别</strong>
							</td>
							<td width="5%"><strong>是否启用</strong>
							</td>
							<td width="5%"><strong>展示顺序</strong>
							</td>
							<td width="30%"><strong>展示内容</strong>
							</td>
						</tr>
						<tr style="background-color: #F9FBFC">
							<td width="3%">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${PrivilegesName}</td>
							<td>${MemberLevel}</td>
							<td>${useFlag}</td>
							<td>${orderFlag}</td>
							<td>${content}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid>
			</td>
		</tr>
	</table>
</body>
</html>