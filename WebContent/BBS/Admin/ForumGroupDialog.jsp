<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>论坛版块管理</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script>

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
					<td valign="middle" class="blockTd"><img src="../../Icons/icon022a1.gif" width="20" height="20" />板块列表</td>
				</tr>
				<tr>
					<td style="padding:0px 5px;">
					<z:datagrid id="dg1" method="com.sinosoft.bbs.admin.ForumOption.selectGroup" page="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" afterdrag="sortMenu">
							<tr ztype="head" class="dataTableHead">
								<td width="8%" ztype="RowNo"></td>
								<td width="8%" ztype="selector" field="ID">&nbsp;</td>
								<td width="22%"><b>组名称</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;${Name}</td>
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
