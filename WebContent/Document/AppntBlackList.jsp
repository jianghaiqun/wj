<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	
	Page.onLoad(function() {
		$("dg1").afterEdit = function(tr, dr) {
			dr.set("PartnersName", $V("PartnersName"));
			return true;
		};
	});

	// 查询
	function query() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "applicantName", $V("applicantName"));
		DataGrid.setParam("dg1", "appntIDNo", $V("appntIDNo"));
		DataGrid.loadData("dg1");
	}

	// 新建
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 400;
		diag.Height = 300;
		diag.Title = "黑名单-新增";
		diag.URL = "Document/AppntBlackListAdd.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "黑名单";
		diag.show();
	}

	// 新建保存
	function addSave() {
		var dc = $DW.Form.getData("form2");
		if ($DW.Verify.hasError()) {
			return;
		}
			Server.sendRequest("com.sinosoft.cms.document.BlackListManage.saveAppntBlackListManage",
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
	function editor() {
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
		diag.Width = 400;
		diag.Height = 300;
		diag.Title = "黑名单-编辑";
		diag.URL = "Document/AppntBlackListEditor.jsp?ID=" + dr.get("ID");
		diag.OKEvent = editSave;
		diag.show();
	}

	//编辑保存
	function editSave(){
		var dc = $DW.Form.getData("BlackListEditorForm");
		if ($DW.Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.sinosoft.cms.document.BlackListManage.appntUpdate",dc,function(){
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
		
		dc.add("ID", arr);
		Dialog.confirm("确认要删除？", function() {
			Server.sendRequest("com.sinosoft.cms.document.BlackListManage.appntDelete", dc, function(
					response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData('dg1');
					}
				});
			});
		});
	}

	function addBatch() {
		var diag = new Dialog("Diag1");
		diag.Width = 400;
		diag.Height = 150;
		diag.Title = "黑名单批量导入";
		diag.MessageTitle = "黑名单-批量导入";
		diag.ShowMessageRow = true;
		diag.URL = "Document/AppntBlackListBatch.jsp";
		diag.CancelEvent = function(){
			$D.close();
			DataGrid.loadData("dg1");
		};
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value = "关闭";
	}
	
</script>
</head>
<body>
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td valign="top">
				<fieldset>
					<legend>
						<label>投保人黑名单管理</label>
					</legend>
					<form id="form1">
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">姓名：</td>
								<td><input value="" type="text" id="applicantName" name="applicantName"
									ztype="String" class="inputText" size="20" />
									</td>
									<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">证件号：</td>
								<td><input value="" type="text" id="appntIDNo" name="appntIDNo"
									ztype="String" class="inputText" size="20" />
									</td>
									<td><z:tbutton id="BtnAdd" onClick="query()">
								              <img src="../Icons/icon403a3.gif" width="20" height="20" />查询</z:tbutton> 
								    </td>
							</tr>
						</table>
					</form>
				</fieldset>
				<table>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table> <z:tButtonPurview>${_Document_AppntBlackList_Button}</z:tButtonPurview>
			</td>
		</tr>
		<tr>
			<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.document.BlackListManage.queryAppntBlackList" size="20"
					scroll="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16">
							</td>
							<td width="5%" ztype="Selector" field="ID">&nbsp;</td>
							<td width="10%"><strong>保险公司</strong>
							</td>
							<td width="15%"><strong>投保人姓名</strong>
							</td>
							<td width="10%"><strong>证件类型</strong>
							</td>
							<td width="25%"><strong>证件号码</strong>
							</td>
							<td width="10%"><strong>创建时间</strong>
							</td>
						</tr>
						<tr style="background-color: #F9FBFC">
							<td width="3%">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${appntCompanyName}</td>
							<td>${applicantName}</td>
							<td>${appntIDTypeName}</td>
							<td>${appntIDNo}</td>
							<td>${createDate}</td>
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