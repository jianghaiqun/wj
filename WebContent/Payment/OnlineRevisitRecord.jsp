<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>在线回访记录列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
// 搜索
function searchdata(){
	var beginCreatedate = $V("beginCreatedate");
	var endCreatedate = $V("endCreatedate");
	var beginCreatetime = $V("beginCreatetime");
	var endCreatetime = $V("endCreatetime");
	var status = $V("status");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","status",status);
	DataGrid.setParam("dg1", "beginCreatedate", beginCreatedate);
	DataGrid.setParam("dg1", "endCreatedate", endCreatedate);
	DataGrid.setParam("dg1", "beginCreatetime", beginCreatetime);
	DataGrid.setParam("dg1", "endCreatetime", endCreatetime);
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
					<td>
						<z:init method="com.wangjin.payment.OnlineRevisitRecord.init">
						<table  cellspacing="0" cellpadding="3">
							<tr>
								<td>统计时间 从：</td>
			                	<td> <input name="beginCreatedate" type="text" id="beginCreatedate" value="" style="width:90px" ztype="date"></td>
			                	<td> <input name="beginCreatetime" type="text" id="beginCreatetime" value="" style="width:90px" ztype="time"></td>
								<td>至：</td>
								<td><input name="endCreatedate" type="text" id="endCreatedate" value="" style="width:90px" ztype="date"></td>
								<td><input name="endCreatetime" type="text" id="endCreatetime" value="" style="width:90px" ztype="time"></td>
								<td align="right" height="35">回访状态：</td>
								<td><z:select id="status" style="height:21px;width:116px;">${status}</z:select>
								<td colspan="8" nowrap><z:tButtonPurview>${_Payment_OnlineRevisitRecord_Button}</z:tButtonPurview></td>
							</tr>
						</table>
						</z:init>
					</td>
				</tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.payment.OnlineRevisitRecord.dg1DataBind" size="15" scroll="true" lazy="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								 <td width="10" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
                                 <td width="80" style="text-align:center;"><b>投保人</b></td>
                                 <td width="80" style="text-align:center;"><b>联系电话</b></td>
                                 <td width="80" style="text-align:center;"><b>购买产品</b></td>
                                 <td width="80" style="text-align:center;"><b>支付时间</b></td>
                                 <td width="80" style="text-align:center;"><b>回访状态</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								 <td style="text-align:left;" title="${applicantName}">${applicantName}</td>
								 <td style="text-align:left;" title="${applicantMobile}">${applicantMobile}</td>
								 <td style="text-align:left;" title="${productName}">${productName}</td>
								 <td style="text-align:left;" title="${createDate}">${createDate}</td>
								 <td style="text-align:left;" title="${status}">${status}</td>
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
			</td>
		</tr>
	</table>
</body>
</html>