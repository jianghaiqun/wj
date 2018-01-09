<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.GlobalCount.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>分支机构费率设置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
function onTreeClick() {
	var ele = Tree.CurrentItem;
	var cid = ele.getAttribute("cid");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","branchInnerCode",cid);
	DataGrid.loadData("dg1");
}

function add() {
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 250;
	diag.Title = "添加结算费率";
	diag.URL = "LineTeam/RateManageDialog.jsp?operFlag=add";
	diag.OKEvent = save;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加结算费率";
	diag.show();
}

function edit() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的数据！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择1条信息修改！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 250;
	diag.Title = "修改结算费率";
	diag.URL = "LineTeam/RateManageDialog.jsp?operFlag=edit&ID=" + dr.get("id");
	diag.OKEvent = save;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改结算费率";
	diag.show();
}
function save() {

	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.lineteam.RateManage.save", dc, function() {
		var response = Server.getResponse();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});	
}

function del() {
	var arr = DataGrid.getSelectedValue("dg1");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的数据！");
		return;
	}
	Dialog.confirm("您确认要删除吗？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.wangjin.lineteam.RateManage.delete", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						}
					});
				});
	});
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td width="10%">
			<table width="10%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding: 6px;" class="blockTd"><z:tree id="tree1"
						style="height:440px;width:120px;"
						method="com.wangjin.lineteam.RateManage.treeDataBind"
						level="3">
						<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
						<p cid='${ID}' cname='${Name}' InnerCode='${InnerCode}' onClick="onTreeClick(this);">${Name}</label></p>
					</z:tree></td>
				</tr>
			</table>
			</td>
			<td width="90%">
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr height="30px">
					<td style="padding:4px 8px;" class="blockTd">
						<z:tButtonPurview>${_LineTeam_RateManage_Button}</z:tButtonPurview>
					</td>
				</tr>
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.lineteam.RateManage.dg1DataBind" size="20" scroll="true" >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" afterdrag="sortBranch">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo" drag="true"><img
									src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
								<td width="4%" ztype="selector" field="id">&nbsp;</td>
								<td width="10%"><b>险种</b></td>
								<td width="10%"><b>保险公司</b></td>
								<td width="10%"><strong>机构</strong></td>
								<td width="6%"><strong>结算费率</strong></td>
								<td width="8%"><strong>创建者</strong></td>
								<td width="12%"><strong>创建时间</strong></td>
								<td width="8%"><strong>修改者</strong></td>
								<td width="12%"><strong>修改时间</strong></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${riskTypeName}</td>
								<td>${companyName}</td>
								<td>${branchName}</td>
								<td>${rate}</td>
								<td>${createUserName}</td>
								<td>${createDate}</td>
								<td>${modifyUserName}</td>
								<td>${modifyDate}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="10">${PageBar}</td>
								<input name="id" type="hidden" id="id" value="${id}">
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
</z:init>