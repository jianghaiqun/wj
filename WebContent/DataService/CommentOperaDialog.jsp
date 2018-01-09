<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body class="dialogBody">
	<input type="hidden"  id="ID" name="ID" value="${ID}">
	<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
		<tr>
			<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<z:datagrid id="dg3" method="com.sinosoft.cms.dataservice.Comment.dg3DataBind" size="15" autoFill="true" scroll="true" lazy="false" multiSelect="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd" style="table-layout: fixed" >
						<tr ztype="head" class="dataTableHead">
							<td width="8%" height="30" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="12%"><strong>原排序数</strong></td>
							<td width="12%"><strong>现排序数</strong></td>
							<td width="12%"><strong>原点赞数</strong></td>
							<td width="12%"><strong>现点赞数</strong></td>
							<td width="12%"><strong>修改人</strong></td>
							<td width="26%"><strong>修改时间</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>${prevSortNum}</td>
							<td>${sortNum}</td>
							<td>${prevPraisedNum}</td>
							<td>${praisedNum}</td>
							<td>${MakeUser}</td>
							<td>${MakeDate}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="7" align="left">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid>
			</td>
		</tr>
	</table>
</body>
</html>