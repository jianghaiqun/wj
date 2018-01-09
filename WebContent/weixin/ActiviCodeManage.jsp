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
<script src="ActiviCodeManage.js"></script>
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
	<form name="fm" target="targetFrame" action="ActiviCodeManageSave.jsp"
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
								<td><A href="./ActiviCode.xls">模板下载</A></td>
								<td></td>
							</tr>
							<tr>
								<td>1，每次导入的数据最多为300条</td>
								<td></td>
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
                                                                       激活码：<input name="ActiveCode" type="text" id="ActiveCode" value="" style="width: 290px" class="inputText">
                    </td>
				</tr>
				<tr>
					<td colspan=2 style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.framework.utility.weixin.common.ActiviCodeUpLoad.dg1DataBind" size="15" lazy="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="5" ztype="RowNo"><strong>序号</strong></td>
								<td width="90"><b>激活码</b></td>
								<td width="30"><b>激活网站</b></td>
								<td width="30"><b>是否发放</b></td>
								<td width="90"><b>OpenId</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td title="${ActiveCode}">${ActiveCode}</td>
								<td title="${ReceiveCode}">${ReceiveCode}</td>
								<td title="${ReceiveFlag}">${ReceiveFlag}</td>
								<td title="${OpenID}">${OpenID}</td>
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