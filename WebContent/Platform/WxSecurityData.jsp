<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>缺口保障数据统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<z:init method="com.sinosoft.platform.WXFuLi.initRiskTypeManageList">
<script type="text/javascript">
	Page.onLoad(function() {
		select();
	});

	/**
	 * 查询
	 */
	function select() {
		var startDate = $V("startDate");
		var stopDate = $V("stopDate");
		if (stopDate != '' && stopDate < startDate) {
			Dialog.alert("查询条件-出单日期结束日期不能小于开始日期！");
			return;
		}
		if (stopDate != '' && stopDate < startDate) {
			Dialog.alert("查询条件-保险起期结束日期不能小于开始日期！");
			return;
		}
		DataGrid.setParam("dg1", "startDate", startDate);
		DataGrid.setParam("dg1", "stopDate", stopDate);
		DataGrid.loadData("dg1");
		
		var dc1 = new DataCollection();
		dc1.add("startDate", $V("startDate"));
		dc1.add("stopDate", $V("stopDate"));
		Server.sendRequest(
				"com.sinosoft.platform.GapGuarantee.orderCountForNewRegist", dc1,
				function() {
					var response = Server.getResponse();
					var strs = response.Message;
					jQuery("#totalAll").text(strs);
				});
		
		var dc2 = new DataCollection();
		dc2.add("startDate", $V("startDate"));
		dc2.add("stopDate", $V("stopDate"));
		Server.sendRequest(
				"com.sinosoft.platform.GapGuarantee.orderCountForReceiveCoupon", dc2,
				function() {
					var response = Server.getResponse();
					var strs = response.Message;
					jQuery("#chargeAll").text(strs);
				});
		
		var dc3 = new DataCollection();
		dc3.add("startDate", $V("startDate"));
		dc3.add("stopDate", $V("stopDate"));
		Server.sendRequest(
				"com.sinosoft.platform.GapGuarantee.orderCountForUseCoupon", dc3,
				function() {
					var response = Server.getResponse();
					var strs = response.Message;
					jQuery("#useCount").text(strs);
				});
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
						<td><z:init method="com.sinosoft.platform.GapGuarantee.init">
								<table cellspacing="0" cellpadding="3">
									<tr>
										<td>查询日期 从：</td>
										<td><input name="startDate" type="text" id="startDate"
											value="${yesterday}" style="width: 90px" ztype="date">
										</td>
										<td>至：</td>
										<td><input name="stopDate" type="text" id="stopDate"
											value="${today}" style="width: 90px" ztype="date"></td>
									</tr>
								</table>
								<tr>
									<td>&nbsp;&nbsp;缺口保障渠道新注册会员的数：<em id="totalAll">0.00</em></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;领取优惠券的用户数量：<em id="chargeAll">0.00</em></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;使用优惠券的用户数量：<em id="useCount">0.00</em></td>
								</tr>
								<tr>
									<td style="padding:0 8px 4px;">
									<z:tbutton onClick="select()"><img src="../Icons/icon018a4.gif" />查询</z:tbutton>
									</td>
								</tr>
							</z:init></td>
					</tr>
					<tr>
						<td style="padding: 0px 0px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="6"
								class="blockTable" style="table-layout: fixed;">
								<tr>
									<td
										style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
										<z:datagrid id="dg1"
											method="com.sinosoft.platform.GapGuarantee.dg1DataBind"
											size="15" scroll="true" lazy="true">
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable" fixedHeight="370px">
												<tr ztype="head" class="dataTableHead">
													<td width="40" style="text-align: center;" ztype="RowNo"><strong>序号</strong></td>
													<td width="60" style="text-align: center;"><b>数据类型</b></td>
													<td width="60" style="text-align: center;"><b>手机号</b></td>
													<td width="60" style="text-align: center;"><b>优惠券号</b></td>
													<td width="150" style="text-align: center;"><b>创建时间</b></td>
													<td width="150" style="text-align: center;"><b>状态</b></td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td width="40">&nbsp;</td>
													<td style="text-align: left;" title="${type}">${type}</td>
													<td style="text-align: left;" title="${mobileNo}">${mobileNo}</td>
													<td style="text-align: left;" title="${couponSn}">${couponSn}</td>
													<td style="text-align: left;" title="${createdate}">${createdate}</td>
													<td style="text-align: left;" title="${status}">${status}</td>
												</tr>
												<tr ztype="pagebar">
													<td height="25" colspan="11">${PageBar}</td>
												</tr>
											</table>
										</z:datagrid>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</z:init>
</html>
