<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>理赔异常统计</title>
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
	
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.loadData("dg2");
}

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
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
							<table width="100%" border="0" cellspacing="0" cellpadding="0"class="blockTable">
								<tr>
									<td
										style="padding-top: 0px; padding-left: 3px; padding-right: 3px; padding-bottom: 0px;">
										<z:datagrid id="dg2" method="com.sinosoft.cms.payment.PaymentManage.dg2DataBind" size="20" lazy="true" >
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable">
												<tr ztype="head" class="dataTableHead">
													<td width="30px" ztype="RowNo" style="text-align: center;"><strong>序号</strong>
													</td>
													<td width="80px" ><b>被保人姓名</b>
													</td>
													<td width="80px;" ><b>被保人证件号</b></td>
													<td width="80px" ><b>异常时间</b></td>
													<td width="80px" ><b>理赔次数</b></td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td>&nbsp;</td>
													<td title="${insureNames}">${insureNames}</td>
													<td title="${insureIdentityId}">${insureIdentityId}</td>
													<td title="${abnormalTime}">${abnormalTime}</td>
													<td title="${cliamsCount}">${cliamsCount}</td>
												</tr>
												<tr ztype="pagebar">
													<td colspan="5">${PageBar}</td>
												</tr>
											</table>
										</z:datagrid>
									</td>
								</tr>
							</table></td>
					
		</tr>
	</table>
</body>
</html>