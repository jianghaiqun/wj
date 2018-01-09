<%
	/* 微信活动激活码导入画面
	 */
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>激活码导入</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="PropductInfoTwo.js"></script>
<script>
function queryinfo(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","ProductID",$V("ProductID"));
	DataGrid.setParam("dg1","ProductName",$V("ProductName"));
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
	<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" target="targetFrame" action="PropductInfoTwoSave.jsp"
		method="post" enctype="multipart/form-data"
		onSubmit="return ImportExcel();">
		<div id="StaffStat">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"
				style="border-collapse: separate; border-spacing: 6px;">
				<tr valign="top">
					<td colspan=2>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="blockTable">
							<tr height="20px">
							</tr>
							<tr valign="middle">
								<td nowrap><input type="file" style="width: 300px"
									name="importCommentFile" id="importCommentFile"
									onchange="getFileSuff();" /> <input name="importBtn"
									type="button" class="inputButton" id="importBtn"
									onClick="return ImportExcel();" value="上  传" /></td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td><A href="./product_tb_20161027.xls">淘宝产品映射信息模板下载</A></td>
								<td></td>
							</tr>
							<tr>
							</tr> 
							<tr height='20px'>
								<td></td>
								<td></td>
							</tr>
						</table></td>
				</tr>
				<tr>
                    <td colspan=2>
                                                                       产品编码：<input name="ProductID" type="text" id="ProductID" value="" style="width: 190px" class="inputText">
                                                                       产品名称：<input name="ProductName" type="text" id="ProductName" value="" style="width: 190px" class="inputText">
                    <z:tbutton onClick="queryinfo()"><img src="../Icons/icon021a4.gif" width="20" height="20" />查询</z:tbutton>
                    </td>
				</tr>
				<tr>
					<td colspan=2 style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.product.ProductInfo.dg4DataBind" size="15" lazy="true" autoFill="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><strong>序号</strong></td>
								<td width="10%"><b>产品编码</b></td>
								<td width="20%"><b>产品名称</b></td>
								<td width="10%"><b>淘宝产品编码</b></td>
								<td width="10%"><b>计划编码</b></td>
								<td width="7%"><b>保险期间</b></td>
								<td width="10%"><b>对应主站保险期间</b></td>
								<td width="7%"><b>投保年龄</b></td>
								<td width="10%"><b>对应主站投保年龄</b></td>
								<td width="5%"><b>性别</b></td>
								<td width="6%"><b>缴费年期</b></td>
								<td width="12%"><b>导入时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td>&nbsp;</td>
								<td title="${ERiskID}">${ERiskID}</td>
								<td title="${ProductName}">${ProductName}</td>
								<td title="${CustomCode}">${CustomCode}</td>
								<td title="${PlanCode}">${PlanCode}</td>
								<td title="${CoverageYear}">${CoverageYear}</td>
								<td title="${Period}">${Period}</td>
								<td title="${AgeIssue}">${AgeIssue}</td>
								<td title="${TextAge}">${TextAge}</td>
								<td title="${Gender}">${Gender}</td>
								<td title="${PaymentMethods}">${PaymentMethods}</td>
								<td title="${MAKEDATE}">${MAKEDATE}</td>
								
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>