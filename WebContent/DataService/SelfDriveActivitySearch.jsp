<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>大都会自驾车险领取信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
	function doSearch() {
	
		var standardDateStart=$V("standardDateStart");
		var standardDateEnd=$V("standardDateEnd");
		var isStartNull=$V("standardDateStart") == null || $V("standardDateStart") == "";
		var isEndNull=$V("standardDateEnd") == null || $V("standardDateEnd") == "";
		if (isStartNull) {
			Dialog.alert("请填写开始时间！");
			return;
		}
		if (isEndNull) {
			Dialog.alert("请填写结束时间！");
			return;
		}
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "standardDateStart", $V("standardDateStart"));
		DataGrid.setParam("dg1", "standardDateEnd", $V("standardDateEnd"));
		DataGrid.setParam("dg1", "realName", $V("realName"));
		DataGrid.setParam("dg1", "mobileNo", $V("mobileNo"));
		DataGrid.setParam("dg1", "idCode", $V("idCode"));
		DataGrid.loadData("dg1");
	}

	function edit() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要编辑的行！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("只能修改1条信息！");
			return;
		}
		dr = drs[0];
		var diag = new Dialog("Diag1");
		diag.Width = 300;
		diag.Height = 150;
		diag.Title = "大都会自驾车险信息修改";
		diag.URL = "DataService/SelfDriveActivityEdit.jsp?id=" + dr.get("id");
		diag.onLoad = function() {
			//$DW.$("SelfDriveActivityEdit").focus();
		};
		diag.OKEvent = editSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "修改大都会自驾车险信息";
		diag.show();
	}
	function editSave() {
		if ($DW.Verify.hasError()) {
			return;
		}
		var dc = $DW.Form.getData("form2");
		Server.sendRequest("com.sinosoft.cms.document.SelfDriveActivity.edit", dc,
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
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		if (arr.length > 1) {
			Dialog.alert("只能逐条删除");
			return;
		}
		Dialog.confirm("确定要删除该条信息吗？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.sinosoft.cms.document.SelfDriveActivity.del", dc,
					function(response) {
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
	<z:init>
		<table width="100%" border="0" cellspacing="6" cellpadding="0"
			style="border-collapse: separate; border-spacing: 6px;">
			<tr valign="top">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="6"
						class="blockTable">
						<tr>
							<td valign="middle" class="blockTd"><img
								src="../Icons/icon018a6.gif" />大都会车险信息列表</td>
						</tr>

						<tr>
							<td style="padding: 8px 10px;">
								<div style="float: right">
									查询日期：<input name=standardDateStart type="text" id="standardDateStart" value="${standardDateStart}" style="width: 90px" class="inputText" ztype="date"> 
									到：<input name=standardDateEnd type="text" id="standardDateEnd" value="${standardDateEnd}" style="width: 90px" class="inputText" ztype="date"> 
									姓名：<input name="realName" type="text" id="realName" value="" style="width: 90px">
									手机号：<input name="mobileNo" type="text" id="mobileNo" value="" style="width: 90px">
									身份证号：<input name="idCode" type="text" id="idCode" value="" style="width: 90px"> 
									<input type="button" name="Submit" value="查询" onClick="doSearch()">
								</div> 
								<z:tbutton onClick="edit()"> <img src="../Icons/icon021a4.gif" />修改</z:tbutton> 
								<z:tbutton onClick="del()"> <img src="../Icons/icon021a4.gif" />删除</z:tbutton>
							</td>
						</tr>
						<tr>
							<td
								style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
								<z:datagrid id="dg1"
									method="com.sinosoft.cms.document.SelfDriveActivity.dg1DataBind"
									size="15" lazy="true">
									<table width="100%" cellpadding="2" cellspacing="0"
										class="dataTable">
										<tr>
											<td></td>
										</tr>
										<tr ztype="head" class="dataTableHead">
											<td width="4%" ztype="RowNo">序号</td>
											<td width="4%" ztype="selector" field="ID">&nbsp;</td>
											<td><b>id</b></td>
											<td><b>姓名</b></td>
											<td><b>手机号</b></td>
											<td><b>电子邮箱</b></td>
											<td><b>身份证号</b></td>
											<td><b>填写时间</b></td>
											<td><b>修改时间</b></td>

										</tr>
										<tr ondblclick="edit();">
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>${id}</td>
											<td>${realname}</td>
											<td>${mobileno}</td>
											<td>${email}</td>
											<td>${idcode}</td>
											<td>${createdate}</td>
											<td>${modifydate}</td>
										</tr>
										<tr ztype="pagebar">
											<td colspan="10" align="center">${PageBar}</td>
										</tr>
									</table>
								</z:datagrid>
							</td>
						</tr>
					</table> </z:init>
</body>
</html>