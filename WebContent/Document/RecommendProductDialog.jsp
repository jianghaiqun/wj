<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript" src="../Framework/Main.js"></script>
<script type="text/javascript">
	function sear() {
		DataGrid.setParam("dg4", Constant.PageIndex, 0);
		DataGrid.setParam("dg4", "productname", $V("productname"));
		DataGrid.setParam("dg4", "codeValue", $V("codeValue"));
		DataGrid.loadData("dg4");
	}
</script>
</head>
<body>
<z:init>
<input name="codeValue" type="hidden" id="codeValue" value="${codeValue}" />
<input name="num" type="hidden" id="num" value="${num}" />
</z:init>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" height="100%" align="center" cellpadding="0" cellspacing="0" id="ConfigTable">
			<tr>
			    <td>
				产品名称：
				<input name="productname" type="text" id="productname" value="" style="width:180px" />
                <input type="button"  value="查询" onClick="sear()" /></td>
			</tr>
			<tr>
				<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" height="100%" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
					<tr>
						<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
							<z:datagrid id="dg4" method="com.sinosoft.cms.document.RecommendToDetail.dg1DataBindAllProduct"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
								<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd" style="table-layout: fixed" fixedHeight="270px">
									<tr ztype="head" class="dataTableHead">
										<td width="20" height="30" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
										<td width="15" height="30" ztype="Selector" field="id">&nbsp;</td>
										<td width="100"><strong>产品代码</strong></td>
										<td width="150"><strong>产品名称</strong></td>
									</tr>
									<tr style="background-color: #F9FBFC">
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>${id}</td>
										<td>${productname}</td>
									</tr>
								</table>
							</z:datagrid>
						</td>
					</tr>
				</table>
				</td>
			<tr>
		</table>
	</td>
	</tr>
</table>

</body>
</html>