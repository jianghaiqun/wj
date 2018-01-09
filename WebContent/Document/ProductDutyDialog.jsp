<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 		return;
    	}
		dr.set("orderBy",$V("orderBy"));
		return true;
	}
});
function save(){
	DataGrid.save("dg1","com.sinosoft.cms.dataservice.ProductListYX.dg1Edit",function(){DataGrid.loadData('dg1');});
}
</script>
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.ProductListYX.initDialog">
<table width="100%" border="0" cellspacing="0" cellpadding="3"
			class="blockTable">
<tr><td><z:tbutton onClick="save();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>修改责任排序</z:tbutton></td></tr>
</table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="3"
			class="blockTable">
			<input type="hidden" value ="${productId}" id="productId">
			<tr>
				<td
					style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
					<z:datagrid id="dg1"
						method="com.sinosoft.cms.dataservice.ProductListYX.dg1DataBindDialog2" size="10"
						scroll="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" table-layout: fixed;" fixedHeight="480px">
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo"><strong>序号</strong></td>
								<td width="20" ztype="selector" field="UserName">&nbsp;</td>
								<td width="50" style="text-align: center;"><b>产品ID</b>
								</td>
								<td width="80" style="text-align: center;"><b>产品责任编码</b>
								</td>
								<td width="150" style="text-align: center;"><b>产品责任</b>
								</td>
								<td width="80" style="text-align: center;"><b>排序</b>
								</td>
							</tr>
							<tr style="background-color: #F9FBFC">
								<td width="20">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${productID}">${productID}</td>
								<td title="${dutyCode}">${dutyCode}</td>
								<td title="${dutyDetail}">${dutyDetail}</td>
								<td title="${OrderBy}">${OrderBy}</td>
							</tr>
							<tr ztype="edit" bgcolor="#E1F3FF">
								<td width="20">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${productID}">${productID}</td>
								<td title="${dutyCode}">${dutyCode}</td>
								<td title="${DutyName}">${DutyName}</td>
								<td><input type="text" id="orderBy" value="${OrderBy}"></td>
							</tr>
						</table>
					</z:datagrid>
				</td>
			</tr>
		</table>
</z:init>
</body>
</html>