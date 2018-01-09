<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>作者详细统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//订单查询
function doSearch(){ 
	
	DataGrid.setParam("dg1","year",$V("year"));
	DataGrid.setParam("dg1","month",$V("month"));
	DataGrid.setParam("dg1","salesName",$NV("salesName"));
	DataGrid.loadData("dg1");
}

function add() {
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 100;
	diag.Title = "添加月计划保费信息";
	diag.URL = "DaRen/MonthGoalsInputDialog.jsp?type=Add";
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加月计划保费信息";
	diag.show();
}
function addSave() {

	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.MonthGoalsInput.add", dc, function() {
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
	diag.Width = 700;
	diag.Height = 100;
	diag.Title = "修改月计划保费信息";
	diag.URL = "DaRen/MonthGoalsInputDialog.jsp?type=Modify&ID=" + dr.get("id");
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改月计划保费信息";
	diag.show();
}

function editSave() {

	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.MonthGoalsInput.edit", dc, function() {
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

function del() {
	var arr = DataGrid.getSelectedValue("dg1");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的数据！");
		return;
	}
	
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.wangjin.daren.MonthGoalsInput.delete", dc,
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
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
				<td>
				<z:init method="com.wangjin.daren.MonthGoalsInput.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>年月：</td>
		                	<td><z:select id="year" name="year" style="width:50px">${year}</z:select>&nbsp;&nbsp;年&nbsp;&nbsp;<z:select id="month" name="month" style="width:50px">${month}</z:select>&nbsp;&nbsp;月</td>
							<td>业务员：</td>
							<td nowrap>${salesName}</td>
						</tr>
						<tr height="10px">
							<td ></td>
						</tr>
						<tr>
						   <td colspan="4" nowrap><z:tButtonPurview>${_DaRen_MonthGoalsInput_Button}</z:tButtonPurview></td>
		            	</tr>
		             </table>
		             </z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.daren.MonthGoalsInput.dg1DataBind" size="15" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="40" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="70" style="text-align:center;"><b>月份</b></td>
						        <td width="100" style="text-align:center;"><b>业务员姓名</b></td>
						        <td width="100" style="text-align:center;"><b>计划完成保费</b></td>
								<td width="90" style="text-align:center;"><b>创建时间</b></td>
								<td width="70" style="text-align:center;"><b>创建者</b></td>
								<td width="90" style="text-align:center;"><b>修改时间</b></td>
								<td width="70" style="text-align:center;"><b>修改者</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td width="15">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
								<td style="text-align:center;" title="${month}">${month}</td>
								<td style="text-align:center;" title="${createUser}">${createUser}</td>
								<td style="text-align:center;" title="${goalsPrem}">${goalsPrem}</td>
								<td style="text-align:center;" title="${createDate}">${createDate}</td>
								<td style="text-align:center;" title="${createUser}">${createUser}</td>
								<td style="text-align:center;" title="${modifyDate}">${modifyDate}</td>
								<td style="text-align:center;" title="${modifyUser}">${modifyUser}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>
