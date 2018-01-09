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
	DataGrid.setParam("dg1","ComCode",$V("ComCode"));
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
</script>
</head>
<body>
	<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" target="targetFrame" action="PropductCountrySave.jsp"
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
								<td><A href="./product_country.xls">主站旅游目的地信息模板下载</A></td>
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
                                                                        保险公司编码：<input name="ComCode" type="text" id="ComCode" value="" style="width: 190px" class="inputText">
                    </td>
				</tr>
				<tr>
                    <td colspan=2>
                    <z:tbutton onClick="queryinfo()"><img src="../Icons/icon021a4.gif" width="20" height="20" />查询</z:tbutton>
                    <z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
                    </td>
				</tr>
				<tr>
					<td colspan=2 style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.product.ProductInfo.dg8DataBind" size="20">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><strong>序号</strong></td>
								<td width="10%"><b>产品编码</b></td>
								<td width="10%"><b>保险公司编码</b></td>
								<td width="15%"><b>旅游目的地编码</b></td>
								<td width="20%"><b>旅游目的地显示值</b></td>
								<td width="10%"><b>旅游目的地英文编码</b></td>
								<td width="10%"><b>是否为申根地区</b></td>
								<td width="10%"><b>是否为常用地区</b></td>
								<td width="12%">是否为默认目的地</td>
								<td width="12%"><b>录入时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td>&nbsp;</td>
								<td>${ProductID}</td>
								<td>${InsuranceCode}</td>
								<td>${CodeValue}</td>
								<td>${CodeName}</td>
								<td>${CodeEnName}</td>
								<td>${FlagType}</td>
								<td>${ComFlag}</td>
								<td>${DefFlag}</td>
								<td>${CreateDate}</td>
								
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