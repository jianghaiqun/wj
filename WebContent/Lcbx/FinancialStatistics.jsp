<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>理财险财务统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
	function doSearch() {
		var standardDateStart=$V("standardDateStart");
		var standardDateEnd=$V("standardDateEnd");
		var isStartNull=$V("standardDateStart") == null || $V("standardDateStart") == "";
		var isEndNull=$V("standardDateEnd") == null || $V("standardDateEnd") == "";
		if (isStartNull) {
			Dialog.alert("请填写开始时间！");
			return;
		}
		if (isEndNull) {
			Dialog.alert("请填写结束时间！");
			return;
		}
				
		DataGrid.setParam("dg1", "standardDateStart", $V("standardDateStart"));
		DataGrid.setParam("dg1", "standardDateEnd", $V("standardDateEnd"));
		DataGrid.setParam("dg1", Constant.PageIndex, 0);		
		DataGrid.setParam("dg1", "Search", "search");
		DataGrid.loadData("dg1", function() {

			var $trs = jQuery("#dg1  tr[ztype!='head']");

			$trs.each(function() {
				var $this = jQuery(this);
				var $sumtimePrem = $this.children('td[name=sumtimePrem]');
				var sumtimePrem = $sumtimePrem.html();
				var $Income = $this.children('td[name=Income]');
				var Income = $Income.html();
				var $fsumtotal = $this.children('td[name=fsumtotal]');
				var fsumtotal = $fsumtotal.html();
				var $usumtotal = $this.children('td[name=usumtotal]');
				var usumtotal = $usumtotal.html();

				if (sumtimePrem != Income) {
					$sumtimePrem.css("color", "red");
					$Income.css("color", "red");
				}
				if (fsumtotal != usumtotal) {
					$fsumtotal.css("color", "red");
					$usumtotal.css("color", "red");
				}
			});

		});

	}
	
	function showInsure(dom) {
		var $td=jQuery(dom).parent().parent("td");
		var $channelsn = $td.siblings(".channelsn");
		var channelsn=$channelsn.html();
		DataGrid.setParam("dg2", Constant.PageIndex, 0);
		var standardDateStart=$V("standardDateStart");
		var standardDateEnd=$V("standardDateEnd");
		var isStartNull=$V("standardDateStart") == null || $V("standardDateStart") == "";
		var isEndNull=$V("standardDateEnd") == null || $V("standardDateEnd") == "";
		if (isStartNull) {
			Dialog.alert("请填写开始时间！");
			return;
		}
		if (isEndNull) {
			Dialog.alert("请填写结束时间！");
			return;
		}	
		DataGrid.setParam("dg2", "standardDateStart", $V("standardDateStart"));
		DataGrid.setParam("dg2", "standardDateEnd", $V("standardDateEnd"));
		DataGrid.setParam("dg2", "channelsn", channelsn);
		DataGrid.setParam("dg2", "Search", "search");
		DataGrid.loadData("dg2", function() {

			var $trs = jQuery("#dg2  tr[ztype!='head']");

			$trs.each(function() {
				var $this = jQuery(this);
				var $partnerName = $this.children('td[name=partnerName]');
				var partnerName = $partnerName.html();
				var $orderSn = $this.children('td[name=orderSn]');
				var orderSn = $orderSn.html();
				var $PolicyNo = $this.children('td[name=PolicyNo]');
				var PolicyNo = $orderSn.html();
				var $timePrem = $this.children('td[name=timePrem]');
				var timePrem = $timePrem.html();
				var $Income = $this.children('td[name=Income]');
				var Income = $Income.html();

				if (Number(timePrem) != Number(Income)) {
					$partnerName.css("color", "red");
					$orderSn.css("color", "red");
					$PolicyNo.css("color", "red");
					$timePrem.css("color", "red");
					$Income.css("color", "red");
				}
			});

		});
	}

	function showRefund(dom) {
		var $td=jQuery(dom).parent().parent("td");
		var $channelsn = $td.siblings(".channelsn");
		var channelsn=$channelsn.html();
		DataGrid.setParam("dg3", Constant.PageIndex, 0);
		var standardDateStart=$V("standardDateStart");
		var standardDateEnd=$V("standardDateEnd");
		var isStartNull=$V("standardDateStart") == null || $V("standardDateStart") == "";
		var isEndNull=$V("standardDateEnd") == null || $V("standardDateEnd") == "";
		if (isStartNull) {
			Dialog.alert("请填写开始时间！");
			return;
		}
		if (isEndNull) {
			Dialog.alert("请填写结束时间！");
			return;
		}
		DataGrid.setParam("dg3", "standardDateStart", $V("standardDateStart"));
		DataGrid.setParam("dg3", "standardDateEnd", $V("standardDateEnd"));
		DataGrid.setParam("dg3", "channelsn", channelsn);
		DataGrid.setParam("dg3", "Search", "search");
		DataGrid.loadData("dg3", function() {

			var $trs = jQuery("#dg3  tr[ztype!='head']");

			$trs.each(function() {
				var $this = jQuery(this);
				var $partnerName = $this.children('td[name=partnerName]');
				var partnerName = $partnerName.html();
				var $orderSn = $this.children('td[name=orderSn]');
				var orderSn = $orderSn.html();
				var $PolicyNo = $this.children('td[name=PolicyNo]');
				var PolicyNo = $PolicyNo.html();
				var $total1 = $this.children('td[name=total1]');
				var total1 = $total1.html();
				var $total2 = $this.children('td[name=total2]');
				var total2 = $total2.html();
				var $RefundAplaySn = $this.children('td[name=RefundAplaySn]');
				var RefundAplaySn = $RefundAplaySn.html();
				var $TradeDate = $this.children('td[name=TradeDate]');
				var TradeDate = $TradeDate.html();
				
				if (Number(total1) != Number(total2)) {
					$partnerName.css("color", "red");
					$orderSn.css("color","red");
					$PolicyNo.css("color","red");
					$total1.css("color", "red");
					$total2.css("color", "red");
					$RefundAplaySn.css("color", "red");
					$TradeDate.css("color", "red");
				}
				if (0 == Number(RefundAplaySn) || 0 == Number(TradeDate)) {
					$partnerName.css("color", "red");
					$orderSn.css("color","red");
					$PolicyNo.css("color","red");
					$total1.css("color", "red");
					$total2.css("color", "red");
					$RefundAplaySn.css("color", "red");
					$TradeDate.css("color", "red");
				}
			});

		});
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
						<td valign="middle" class="blockTd"><img
							src="../Icons/icon018a6.gif" />财务统计</td>
					</tr>
					<tr>
						<td><z:init
								method="com.sinosoft.cms.memberreport.MemberReport.init">
								<table>
									<tr>
										<td>查询日期：</td>
										<td><input name=standardDateStart type="text"
											id="standardDateStart" value="${standardDateStart}" style="width: 90px"
											class="inputText" ztype="date"></td>
										<td>到</td>
										<td><input name="standardDateEnd" type="text"
											id="standardDateEnd" value="${standardDateEnd}" style="width: 90px"
											class="inputText" ztype="date"></td>
									</tr>
									<tr>
										<td><input type="button" name="Submit" value="查询"
											onClick="doSearch()"></td>
									</tr>
								</table>
							</z:init></td>
					</tr>
					<tr>
						<td  colspan="2"
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">

							<z:datagrid id="dg1"
								method="com.sinosoft.cms.memberreport.FinancialStatistics.select"
								size="18" lazy="true" page="false">
								<table width="100%" style="" cellpadding="2" cellspacing="0"
									class="dataTable">
									<tr ztype="head" class="dataTableHead">
										<td width="10%" ztype="RowNo"><strong>序号</strong></td>
										<td width="15%" title="合作方"><b>合作方</b></td>
										<td width="15%" title="投保成功保费总额"><b>投保成功保费总额</b></td>
										<td width="15%" title="合作方已对账总额"><b>合作方已对账总额</b></td>
										<td width="15%" title="退保成功保费总额"><b>退保成功保费总额</b></td>
										<td width="15%" title="合作方已划拨总额"><b>合作方已划拨总额</b></td>
										<td width="15%" title="详情"><b>详情</b></td>
										<td width="0" style="display: none;"><b>channelsn</b></td>

									</tr>
									<tr style1="background-color:#FFFFFF"
										style2="background-color:#F9FBFC">
										<td width="10%">&nbsp;</td>
										<td width="15%" class = "partnerName" name="partnerName" title="${partnerName}">${partnerName}</td>
										<td width="15%" name="sumtimePrem" title="${sumtimePrem}">${sumtimePrem}</td>
										<td width="15%" name="Income" title="${Income}">${Income}</td>
										<td width="15%" name="fsumtotal" title="${fsumtotal}">${fsumtotal}</td>
										<td width="15%" name="usumtotal" title="${usumtotal}">${usumtotal}</td>
										<td width="15%" ><input type="button" name="Submit" value="投保查询" onClick="showInsure(this)"> 
										<input type="button" name="Submit" value="退保查询" onClick="showRefund(this)"></td>
										<td width="0" style="display: none;" class = "channelsn" name="channelsn" title="${channelsn}">${channelsn}</td>
									</tr>
								</table>
							</z:datagrid>
						</td>
					</tr>
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;width:40%;vertical-align: top;">
							<z:datagrid id="dg2"
								method="com.sinosoft.cms.memberreport.FinancialStatistics.Insure"
								size="18" lazy="true" page="false">
								<table width="100%" style="" cellpadding="2" cellspacing="0"
									class="dataTable">
									<tr ztype="head" class="dataTableHead">
										<td width="15%" ztype="RowNo"><strong>序号</strong></td>
										<td width="15%" title="合作方"><b>合作方</b></td>
										<td width="15%" title="订单号"><b>订单号</b></td>
										<td width="15%" title="保单号"><b>保单号</b></td>
										<td width="15%" title="投保成功保费金额"><b>投保成功保费金额</b></td>
										<td width="15%" title="合作方已对账金额"><b>合作方已对账金额</b></td>

									</tr>
									<tr style1="background-color:#FFFFFF"
										style2="background-color:#F9FBFC">
										<td width="4%">&nbsp;</td>
										<td name="partnerName" title="${partnerName}">${partnerName}</td>
										<td name="orderSn" title="${orderSn}">${orderSn}</td>
										<td name="orderSn" title="${PolicyNo}">${PolicyNo}</td>
										<td name="timePrem" title="${timePrem}">${timePrem}</td>
										<td name="Income" title="${Income}">${Income}</td>
									</tr>
								</table>
							</z:datagrid>
						</td>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;width:60%;vertical-align: top;">
							<z:datagrid id="dg3"
								method="com.sinosoft.cms.memberreport.FinancialStatistics.Refund"
								size="18" lazy="true" page="false">
								<table width="100%" style="" cellpadding="2" cellspacing="0"
									class="dataTable">
									<tr ztype="head" class="dataTableHead">
										<td width="15%" ztype="RowNo"><strong>序号</strong></td>
										<td width="15%" title="合作方"><b>合作方</b></td>
										<td width="15%" title="订单号"><b>订单号</b></td>
										<td width="15%" title="保单号"><b>保单号</b></td>
										<td width="15%" title="退保成功保费金额"><b>退保成功保费金额</b></td>
										<td width="15%" title="合作方已划拨金额"><b>合作方已划拨金额</b></td>
										<td width="15%" title="退保申请流水号"><b>退保申请流水号</b></td>
										<td width="15%" title="退保交易确认时间"><b>退保交易确认时间</b></td>

									</tr>
									<tr style1="background-color:#FFFFFF"
										style2="background-color:#F9FBFC">
										<td width="4%">&nbsp;</td>
										<td name="partnerName" title="${partnerName}">${partnerName}</td>
										<td name="orderSn" title="${orderSn}">${orderSn}</td>
										<td name="orderSn" title="${PolicyNo}">${PolicyNo}</td>
										<td name="total1" title="${total1}">${total1}</td>
										<td name="total2" title="${total2}">${total2}</td>
										<td name="RefundAplaySn" title="${RefundAplaySn}">${RefundAplaySn}</td>
										<td name="TradeDate" title="${TradeDate}">${TradeDate}</td>
									</tr>
								</table>
							</z:datagrid>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>