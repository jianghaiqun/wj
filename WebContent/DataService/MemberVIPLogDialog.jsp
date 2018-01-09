<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
</head>
<body>
<z:init>
	<table width="100%" border="0" cellspacing="0" cellpadding="6"
		class="blockTable" style="table-layout: fixed;">
		<tr>
			<td
				style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.dataservice.Member.memberVipLogInquery"
					size="10" scroll="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" style="text-align: center; table-layout: fixed;"
						fixedHeight="250px">
						<tr ztype="head" class="dataTableHead">
							<td width="30" ztype="RowNo"><strong>序号</strong></td>
							<td width="100"><b>操作</b></td>
							<td width="70"><b>操作人</b></td>
							<td width="70"><b>操作时间</b></td>

						</tr>
						<tr style="background-color: #F9FBFC">
							<td>&nbsp;</td>
							<td>${operation}</td>
							<td>${operaUser}</td>
							<td>${operaTime}</td>
						</tr>
						<tr ztype="pagebar">
							<td height="25" colspan="4">${PageBar}
							<input type="hidden" id="memberId" value="${memberId}"/></td>
						</tr>
					</table>
				</z:datagrid>
			</td>
		</tr>
	</table>
</z:init>
</body>
</html>