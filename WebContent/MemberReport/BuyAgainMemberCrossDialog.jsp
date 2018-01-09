<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>

<z:init method="com.sinosoft.cms.memberreport.BuyAgainCrossReport.initDialog">
<script>
Page.onLoad(function(){
	doSearch();
});
function doSearch(){
	DataGrid.setParam("dg1", Constant.PageIndex, 0);
	DataGrid.setParam("dg1", "type", '${type}');
	DataGrid.setParam("dg1", "startDate", '${startDate}');
	DataGrid.setParam("dg1", "endDate", '${endDate}');
	// 会员渠道
	DataGrid.setParam("dg1", "contant", '${contant}');
	DataGrid.setParam("dg1", "Search","search");
	DataGrid.setParam("dg1", "members",'${members}');
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
</script>
</z:init>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
                    <td>
                	    <z:tbutton onClick="exportStaff()"><img src="../Icons/icon021a3.gif" />导出EXCEL</z:tbutton>
                    </td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.BuyAgainCrossReport.dg1DataBind_Mdialog" size="20" lazy="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><strong>序号</strong></td>
								<td width="18%" style="text-align:center;"><b>会员邮箱/手机号</b></td>
								<td width="6%" style="text-align:center;"><b>用户名</b></td>
								<td width="5%" style="text-align:center;"><b>真实姓名</b></td>
								<td width="5%" style="text-align:center;"><b>证件类型</b></td>
								<td width="12%" style="text-align:center;"><b>证件号码</b></td>
								<td width="5%" style="text-align:center;"><b>注册类型</b></td>
								<td width="5%" style="text-align:center;"><b>会员类型</b></td>
								<td width="5%" style="text-align:center;"><b>会员等级</b></td>
								<td width="11%" style="text-align:center;"><b>创建日期</b></td>
								<td width="11%" style="text-align:center;"><b>最近登录时间</b></td>
								<td width="5%" style="text-align:center;"><b>时间间隔</b></td>
							</tr>
							<tr onDblClick="view('${id}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>${email}/${mobileNO}</td>
								<td>${username}</td>
								<td>${realName}</td>
								<td>${IDTypeName}</td>
								<td>${IDNO}</td>
								<td style="text-align:center;">${registerTypeName}</td>
								<td style="text-align:center;">${VIPTypename}</td>
								<td style="text-align:center;">${gradevip}</td>
								<td style="text-align:center;">${createDate}</td>
								<td style="text-align:center;">${loginDate}</td>
								<td style="text-align:center;">${days}</td>
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
</body>
</html>