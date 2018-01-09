<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员积分管理-积分明细</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
</head>
<body>
				 <table width="100%" border="0" cellspacing="6" cellpadding="0"
		                style="border-collapse: separate; border-spacing: 6px;" >
		           <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.integralDetail" size="20">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="7%"><b>时间</b></td>
								<td width="7%"><b>收入/支出</b></td>
								<td width="10%"><b>详细说明</b></td>
								<td width="5%"><b>积分状态</b></td>
							</tr>
							<tr>
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${createdate} ${createtime}">${createdate} ${createtime}</td>
								<td title="${MannerName}:${integral}">${MannerName}:${integral}</td>
								<td title="${SourceName}${businessid}">${SourceName}${businessid}</td>
								<td title="${StatusName}">${StatusName}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			 </table>
</body>
