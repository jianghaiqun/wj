<!DOCTYPE HTML>
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预约信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","PreconURL",$V("PreconURL"));
	DataGrid.setParam("dg1","Search","search");
	DataGrid.loadData("dg1");
}
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />预约信息列表</td>
				</tr>
			<tr>
				<td>
				<table>
					<tr>
						<td>操作时间：&nbsp;&nbsp;从</td>
		                <td> <input name="createDate" type="text" id="createDate" value="" style="width:100px" ztype="date"></td>
						<td>&nbsp;&nbsp;至</td>
						<td><input name="endDate" type="text" id="endDate" value=""style="width:100px" ztype="date"></td>
						<td>&nbsp;&nbsp;信息来源：</td>
						<td><input name="PreconURL" type="text" id="PreconURL" value=""
							style="width: 90px" class="inputText"></td>
						<td>&nbsp;&nbsp;<input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
					</tr>
				</table>
				</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<div style="width:1300px;  overflow: auto;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.PrecontractInfo.dg1DataBind" size="20">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo"><strong>序号</strong></td>
								<td width="2%" ztype="selector" field="id">&nbsp;</td>
								<td width="5%"><b>姓名</b></td>
								<td width="3%"><b>性别</b></td>
								<td width="12%"><b>邮箱</b></td>
								<td width="8%"><b>手机号码</b></td>
								<td width="27%"><b>信息来源</b></td>
								<td width="12%"><b>预约时间</b></td>
								<td width="10%"><b>维析ID</b></td>
								<td width="18%"><b>客户留言</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${PreconName}">${PreconName}</td>
								<td title="${PreconSex}">${PreconSex}</td>
								<td title="${PreconEmail}">${PreconEmail}</td>
								<td title="${PreconPhone}">${PreconPhone}</td>
								<td title="${PreconURL}">${PreconURL}</td>
								<td title="${CreateDate}">${CreateDate}</td>
								<td title="${WXID}"><b>${WXID}</b></td>
								<td title="${Remark}"><b>${Remark}</b></td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</div>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>