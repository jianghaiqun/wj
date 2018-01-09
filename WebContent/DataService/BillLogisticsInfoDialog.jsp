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
<z:init method="com.sinosoft.cms.dataservice.BillInfoManage.lookLogistics">
	<table width="100%" border="0" cellspacing="0" cellpadding="6"
		class="blockTable" style="table-layout: fixed;">
		<tr>
			<td
				style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				${table}
			</td>
		</tr>
	</table>
</z:init>
</body>
</html>