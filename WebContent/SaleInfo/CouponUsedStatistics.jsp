<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠券使用统计</title>
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
	DataGrid.setParam("dg1","provideChannel",$V("provideChannel"));
	DataGrid.setParam("dg1","usedChannel",$V("usedChannel"));
	DataGrid.setParam("dg1","provideChannelName",$SN("provideChannel"));
	DataGrid.setParam("dg1","usedChannelName",$SN("usedChannel"));
	DataGrid.loadData("dg1");
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
							src="../Icons/icon018a6.gif" width="20" height="20" /> 优惠券月度汇总</td>
					</tr>
					<tr>
						<td>
							<table cellspacing="0" cellpadding="3">
								<tr>
									<td>发放渠道：</td>
									<td><z:select name="provideChannel" id="provideChannel">
										<span value="" selected>全部</span>
										<span value="wj">主站</span>
										<span value="kxb_app">主站app</span>
										<span value="wap_ht">wap站出单</span>
										<span value="wap_wx">wap站微信</span>
									</z:select></td>
									<td>使用渠道：</td>
									<td><z:select name="usedChannel" id="usedChannel">
										<span value="" selected>全部</span>
										<span value="wj">主站</span>
										<span value="kxb_app">主站app</span>
										<span value="wap_ht">wap站出单</span>
										<span value="wap_wx">wap站微信</span>
									</z:select></td>
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
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								class="blockTable" style="table-layout: fixed;">
								<tr>
									<td
										style="padding-top: 0px; padding-left: 3px; padding-right: 3px; padding-bottom: 0px;">
										<z:datagrid id="dg1"
											method="com.wangjin.coupon.CouponStatistics.dg3DataBind"
											size="24" lazy="true" >
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable"
												style="table-layout: fixed;"
												fixedHeight="550px">
												<tr ztype="head" class="dataTableHead">
													<td width="30" ztype="RowNo"><strong>序号</strong>
													</td>
													<td width="120" ><b>发放渠道</b>
													</td>
													<td width="120" ><b>使用渠道</b>
													</td>
													<td width="100" ><b>发放次数</b>
													</td>
													<td width="100" ><b>使用次数</b>
													</td>
													<td width="100" ><b>使用率</b>
													</td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td width="3%">&nbsp;</td>
													<td >${_Columns_0}</td>
													<td >${_Columns_1}</td>
													<td >${_Columns_2}</td>
													<td >${_Columns_3}</td>
													<td >${_Columns_4}</td>
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