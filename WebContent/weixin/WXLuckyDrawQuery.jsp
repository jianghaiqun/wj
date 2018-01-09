<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信活动管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
	function doSearch() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "mobile", $V("mobile"));
		DataGrid.loadData("dg1");
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
								src="../Icons/icon018a6.gif" />微信活动列表</td>
						</tr>

						<tr>
							<td style="padding: 8px 10px;">
								<div style="float: right">
									手机号：<input name="mobile" type="text" id="mobile" value=""
										style="width: 90px"> <input type="button"
										name="Submit" value="查询" onClick="doSearch()">
								</div> 
							</td>
						</tr>
						<tr>
							<td
								style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
								<z:datagrid id="dg1"
									method="com.sinosoft.cms.document.WXActivity.dg3DataBind"
									size="15" lazy="true">
									<table width="100%" cellpadding="2" cellspacing="0"
										class="dataTable">
										<tr>
											<td></td>
										</tr>
										<tr ztype="head" class="dataTableHead">
											<td width="4%" ztype="RowNo">序号</td>
											<td><b>活动名称</b></td>
											<td><b>用户名</b></td>
											<td><b>邮箱</b></td>
											<td><b>中奖信息</b></td>

										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${name}</td>
											<td>${username}</td>
											<td>${email}</td>
											<td>${prop2}</td>
											
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