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
<script src="PlanAmountManage.js"></script>
<script>
function queryinfo(){
	DataGrid.setParam("dg1","ActiveCode",$V("ActiveCode"));
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
	<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" target="targetFrame" action="PlanAmountManageSave.jsp"
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
								<td><A href="./PlanAmount.xls">模板下载</A></td>
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
                     <z:tbutton onClick="queryinfo()"><img src="../Icons/icon021a4.gif" width="20" height="20" />查询</z:tbutton>
                                                                       年份：<input name="ActiveCode" type="text" id="ActiveCode" value="" style="width: 290px" class="inputText">
                    </td>
				</tr>
				<tr>
					<td colspan=2 style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.framework.utility.PlanAmountUpLoad.dg1DataBind" size="15" lazy="true" autoFill="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="10" ztype="RowNo"><strong>序号</strong></td>
								<td width="60"><b>年份</b></td>
								<td width="100"><b>类别</b></td>
								<td width="70"><b>一月保费</b></td>
								<td width="80"><b>一月手续费</b></td>
								<td width="70"><b>二月保费</b></td>
								<td width="80"><b>二月手续费</b></td>
								<td width="70"><b>三月保费</b></td>
								<td width="80"><b>三月手续费</b></td>
								<td width="70"><b>四月保费</b></td>
								<td width="80"><b>四月手续费</b></td>
								<td width="70"><b>五月保费</b></td>
								<td width="80"><b>五月手续费</b></td>
								<td width="70"><b>六月保费</b></td>
								<td width="80"><b>六月手续费</b></td>
								<td width="70"><b>七月保费</b></td>
								<td width="80"><b>七月手续费</b></td>
								<td width="70"><b>八月保费</b></td>
								<td width="80"><b>八月手续费</b></td>
								<td width="70"><b>九月保费</b></td>
								<td width="80"><b>九月手续费</b></td>
								<td width="70"><b>十月保费</b></td>
								<td width="80"><b>十月手续费</b></td>
								<td width="70"><b>十一月保费</b></td>
								<td width="80"><b>十一月手续费</b></td>
								<td width="70"><b>十二月保费</b></td>
								<td width="80"><b>十二月手续费</b></td>
								<td width="70"><b>全年保费</b></td>
								<td width="80"><b>全年手续费</b></td>
								<td width="120"><b>导入时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td title="${PlanYear}">${PlanYear}</td>
								<td title="${RiskTypeName}">${RiskTypeName}</td>
								<td title="${MonP1}">${MonP1}</td>
								<td title="${MonF1}">${MonF1}</td>
								<td title="${MonP2}">${MonP2}</td>
								<td title="${MonF2}">${MonF2}</td>
								<td title="${MonP3}">${MonP3}</td>
								<td title="${MonF3}">${MonF3}</td>
								<td title="${MonP4}">${MonP4}</td>
								<td title="${MonF4}">${MonF4}</td>
								<td title="${MonP5}">${MonP5}</td>
								<td title="${MonF5}">${MonF5}</td>
								<td title="${MonP6}">${MonP6}</td>
								<td title="${MonF6}">${MonF6}</td>
								<td title="${MonP7}">${MonP7}</td>
								<td title="${MonF7}">${MonF7}</td>
								<td title="${MonP8}">${MonP8}</td>
								<td title="${MonF8}">${MonF8}</td>
								<td title="${MonP9}">${MonP9}</td>
								<td title="${MonF9}">${MonF9}</td>
								<td title="${MonP10}">${MonP10}</td>
								<td title="${MonF10}">${MonF10}</td>
								<td title="${MonP11}">${MonP11}</td>
								<td title="${MonF11}">${MonF11}</td>
								<td title="${MonP12}">${MonP12}</td>
								<td title="${MonF12}">${MonF12}</td>
								<td title="${AllYearP}">${AllYearP}</td>
								<td title="${AllYearF}">${AllYearF}</td>
								<td title="${CreateDate}">${CreateDate}</td>
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