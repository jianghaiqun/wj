<!DOCTYPE HTML>
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中SOS活动领取信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.setParam("dg1","ChannelTypeCode",$V("ChannelTypeCode"));
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
</script>
</head>
<body>
<z:init method="com.sinosoft.cms.dataservice.SOSInfo.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
					<tr>
						<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />SOS活动领取信息</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td>统计起期：&nbsp;&nbsp;从</td>
					                <td> <input name="StartDate" type="text" id="StartDate" value="${StartDate}" style="width:100px" ztype="date"></td>
									<td>&nbsp;&nbsp;至</td>
									<td><input name="EndDate" type="text" id="EndDate" value="${EndDate}"style="width:100px" ztype="date"></td>
									<td>领取渠道：</td>
									<td><z:select id="ChannelTypeCode">${ChannelTypeCode}</z:select></td>
									<td><z:tbutton onClick="doSearch()"> <img src="../Icons/icon031a7.gif" />查询</z:tbutton></td>
					               	<td><z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton></td>
			               		</tr>
							</table>	
						</td>
					</tr>
					<tr>
						<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
						<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.SOSInfo.dg1DataBind" size="50" lazy="true">
							<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
								<tr ztype="head" class="dataTableHead">
									<td width="2%" ztype="RowNo"><strong>序号</strong></td>
									<td width="2%" ztype="selector" field="id">&nbsp;</td>
									<td width="10%"><b>姓名</b></td>
									<td width="10%"><b>手机号码</b></td>
									<td width="15%"><b>Email</b></td>
									<td width="5%"><b>性别</b></td>
									<td width="10%"><b>生日</b></td>
									<td width="10%"><b>地区</b></td>
									<td width="10%"><b>渠道</b></td>
								</tr>
								<tr style1="background-color:#FFFFFF"
									style2="background-color:#F9FBFC">
									<td width="4%">&nbsp;</td>
									<td>&nbsp;</td>
									<td>${appntName}</td>
									<td>${appntMobile}</td>
									<td>${appntEmail}</td>
									<td>${appntSexName}</td>
									<td>${appntBirthDay}</td>
									<td>${appntArea}</td>
									<td>${channelSn}</td>
								</tr>
								<tr ztype="pagebar">
									<td height="25" colspan="11">${PageBar}</td>
								</tr>
							</table>
						</z:datagrid></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</z:init>
</body>
</html>