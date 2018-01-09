<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动优惠月度汇总</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function dosearch() { 
	if(Verify.hasError()){
		return;
	}
	var sd = $V("startDate");
	var ed = $V("endDate");
	if(ed < sd){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	var day = sd.split("-")[2]; 
	if (day == null || day == '') {
		Dialog.alert("开始日期不是正确的日期！！");
		return;
	}
	day = ed.split("-")[2]; 
	if (day == null || day == '') {
		Dialog.alert("结束日期不是正确的日期！！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.loadData("dg1");
}

function showInformation(month) {
	var sd = $V("startDate");
	var ed = $V("endDate");
	var startDate = "";
	var endDate = "";
	if (sd.indexOf(month) >= 0) {
		startDate = sd + " 00:00:00";
	}
	
	if (ed.indexOf(month) >= 0) {
		endDate = ed + " 23:59:59";
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 960;
	diag.Height = 460;
	diag.Title = "查看"+month+"活动优惠明细";
	diag.URL = "SaleInfo/ActivityStatisticsDetail.jsp?month="+month+"&&startDate="+startDate+"&&endDate="+endDate;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "明细信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="3"
					class="blockTable">
					<tr>
						<td valign="middle" class="blockTd"><img
							src="../Icons/icon018a6.gif" width="20" height="20" /> 活动优惠月度汇总</td>
					</tr>
					<tr>
						<td>
							<table cellspacing="0" cellpadding="3">
								<tr>
									<td>统计时间 从：</td>
									<td><input name="startDate" type="text" id="startDate"
										style="width: 100px" ztype="date" verify="统计起期|NotNull&&Date">
									</td>
									<td>至：</td>
									<td><input name="endDate" type="text"
										id="endDate" 
										style="width: 100px" ztype="date" verify="统计止期|NotNull&&Date">
									</td>
									<td><z:tbutton onClick="dosearch()" id="dosearch">
											<img src="../Icons/icon021a4.gif" width="20" height="20" />统计
		            	   </z:tbutton>
									</td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td style="padding: 0px 5px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="6"
								class="blockTable" style="table-layout: fixed;">
								<tr>
									<td
										style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
										<z:datagrid id="dg1"
											method="com.wangjin.coupon.ActivityStatistics.dg1DataBind"
											size="10" scroll="true" lazy="true" >
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable"
												style="text-align: center; table-layout: fixed;"
												fixedHeight="280px">
												<tr ztype="head" class="dataTableHead">
													<td width="30" ztype="RowNo" ><strong>序号</strong>
													</td>
													<td width="15" ztype="selector" field="id">&nbsp;</td>
													<td width="200" ><b>统计月份</b>
													</td>
													<td width="400" ><b>活动优惠额度</b>
													</td>
												</tr>
												<tr style="background-color: #F9FBFC" onDblClick="showInformation('${month}');">
													<td width="3%">&nbsp;</td>
													<td >&nbsp;</td>
													<td title="${month}">${month}</td>
													<td title="${sumActivity}">${sumActivity}</td>
												</tr>
												<tr ztype="pagebar">
													<td height="25" colspan="4">${PageBar}</td>
												</tr>
											</table>
										</z:datagrid>
									</td>
								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>