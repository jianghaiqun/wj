<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>变更履历</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script>
	//查询
	function doSearch() {
		var sd = $V("createDate");
		var ed = $V("endCreateDate");
		var np = $V("newPaySn");
		var op = $V("oldPaySn");
		var ip = $V("initPaySn");
		var no = $V("newOrderSn");
		var oo = $V("oldOrderSn");
		var io = $V("initOrderSn");
		var ct = $V("changeType");
		var all = sd + ed + np + op + ip + no + oo + io + ct;
		if (all == null || all == '') {
			Dialog.alert("查询条件不能为空,至少要有一个查询条件！");
			return;
		}
		if (ed < sd) {
			Dialog.alert("结束日期不能小于开始日期！");
			return;
		}

		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "createDate", $V("createDate"));
		DataGrid.setParam("dg1", "endCreateDate", $V("endCreateDate"));
		DataGrid.setParam("dg1", "newPaySn", $V("newPaySn"));
		DataGrid.setParam("dg1", "oldPaySn", $V("oldPaySn"));
		DataGrid.setParam("dg1", "initPaySn", $V("initPaySn"));
		DataGrid.setParam("dg1", "newOrderSn", $V("newOrderSn"));
		DataGrid.setParam("dg1", "oldOrderSn", $V("oldOrderSn"));
		DataGrid.setParam("dg1", "initOrderSn", $V("initOrderSn"));
		DataGrid.setParam("dg1", "changeType", $V("changeType"));
		DataGrid.loadData("dg1");
	}

	//清空查询条件
	function doblank() {
		createDate.value = "";
		endCreateDate.value = "";
		newPaySn.value = "";
		oldPaySn.value = "";
		initPaySn.value = "";
		newOrderSn.value = "";
		oldOrderSn.value = "";
		initOrderSn.value = "";
		changeType.value = "";
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
							src="../Icons/icon018a6.gif" width="20" height="20" /> 保单变更信息</td>
					</tr>
					<tr>
						<td><z:init
								method="com.sinosoft.cms.dataservice.ChangeInfoInquity.init">
								<table cellspacing="0" cellpadding="3">
									<tr>
										<td>新交易流水号：</td>
										<td><input name="newPaySn" type="text" id="newPaySn"
											value="" style="width: 130px">
										</td>
										<td>旧交易流水号：</td>
										<td><input name="oldPaySn" type="text" id="oldPaySn"
											value="" style="width: 130px">
										</td>
										<td>初始交易流水号：</td>
										<td><input name="initPaySn" type="text" id="initPaySn"
											value="" style="width: 130px">
										</td>
										<td>新订单号：</td>
										<td><input name="newOrderSn" type="text" id="newOrderSn"
											value="" style="width: 100px">
										</td>
										<td>旧订单号：</td>
										<td><input name="oldOrderSn" type="text" id="oldOrderSn"
											value="" style="width: 100px">
										</td>
										<td>初始订单号：</td>
										<td><input name="initOrderSn" type="text"
											id="initOrderSn" value="" style="width: 100px">
										</td>
									</tr>
									<tr>
										<td>变更时间 从：</td>
										<td><input name="createDate" type="text" id="createDate"
											value="${createDate}" style="width: 100px" ztype="date">
										</td>
										<td>至：</td>
										<td><input name="endCreateDate" type="text"
											id="endCreateDate" value="${endCreateDate}"
											style="width: 100px" ztype="date">
										</td>
										<td>变更类型：</td>
										<td><z:select name="changeType" id="changeType" value="">${changeType}</z:select>
										</td>
									</tr>
									<tr>
										<td colspan="12"><z:tbutton onClick="doSearch();"
												id="doSearch">
												<img src="../Icons/icon021a4.gif" width="20" height="20" />查询
		            	   </z:tbutton> <z:tbutton onClick="doblank();" id="doblank">
												<img src="../Icons/icon021a4.gif" width="20" height="20" />清空查询条件
		            	   </z:tbutton></td>
									</tr>
								</table>
							</z:init></td>
					</tr>
					<tr>
						<td style="padding: 0px 5px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="6"
								class="blockTable" style="table-layout: fixed;">
								<tr>
									<td
										style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
										<z:datagrid id="dg1"
											method="com.sinosoft.cms.dataservice.ChangeInfoInquity.dg1Inquery"
											size="20" scroll="true" lazy="true">
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable"
												style="text-align: center; table-layout: fixed;"
												fixedHeight="500px">
												<tr ztype="head" class="dataTableHead">
													<td width="30" ztype="RowNo" style="text-align: center;"><strong>序号</strong>
													</td>
													<td width="15" ztype="selector" field="id"
														style="text-align: center;">&nbsp;</td>
													<td width="150" style="text-align: center;"><b>新交易流水号</b>
													</td>
													<td width="150" style="text-align: center;"><b>旧交易流水号</b>
													</td>
													<td width="150" style="text-align: center;"><b>初始交易流水号</b>
													</td>
													<td width="120" style="text-align: center;"><b>新订单号</b>
													</td>
													<td width="120" style="text-align: center;"><b>旧订单号</b>
													</td>
													<td width="120" style="text-align: center;"><b>初始订单号</b>
													</td>
													<td width="120" style="text-align: center;"><b>变更时间</b>
													</td>
													<td width="120" style="text-align: center;"><b>变更项</b>
													</td>
													<td width="150" style="text-align: center;"><b>变更前</b>
													</td>
													<td width="150" style="text-align: center;"><b>变更后</b>
													</td>
													<td width="80" style="text-align: center;"><b>操作用户</b>
													</td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td width="3%">&nbsp;</td>
													<td style="text-align: center;">&nbsp;</td>
													<td style="text-align: center;" title="${newPaySn}">${newPaySn}</td>
													<td style="text-align: center;" title="${oldPaySn}">${oldPaySn}</td>
													<td style="text-align: center;" title="${initPaySn}">${initPaySn}</td>
													<td style="text-align: center;" title="${newOrderSn}">${newOrderSn}</td>
													<td style="text-align: center;" title="${oldOrderSn}">${oldOrderSn}</td>
													<td style="text-align: center;" title="${initOrderSn}">${initOrderSn}</td>
													<td style="text-align: center;" title="${createDate}">${createDate}</td>
													<td style="text-align: center;" title="${changeTypeName}">${changeTypeName}</td>
													<td style="text-align: center;" title="${beforeValue}">${beforeValue}</td>
													<td style="text-align: center;" title="${afterValue}">${afterValue}</td>
													<td style="text-align: center;" title="${createUser}">${createUser}</td>
												</tr>
												<tr ztype="pagebar">
													<td height="25" colspan="11">${PageBar}</td>
												</tr>
											</table>
										</z:datagrid>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
		</td>
		</tr>
	</table>
</body>
</html>