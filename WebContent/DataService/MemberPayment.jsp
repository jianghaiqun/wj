<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>快速理赔信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script>
	//订单查询
	function doSearch() {
		var sd = $V("createDate");
		var ed = $V("endCreateDate");
		var ins = $V("insureName");
		var od = $V("idNO");
		var cn = $V("contactName");
		var cm = $V("contactMobile");
		var mail = $V("contactMail");
		var ord = $V("orderSn");
		var st = $V("state");
		var all = sd + ed + ins + od + cn + cm + mail + ord + st;
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
		DataGrid.setParam("dg1", "insureName", $V("insureName"));
		DataGrid.setParam("dg1", "idNO", $V("idNO"));
		DataGrid.setParam("dg1", "contactName", $V("contactName"));
		DataGrid.setParam("dg1", "contactMobile", $V("contactMobile"));
		DataGrid.setParam("dg1", "contactMail", $V("contactMail"));
		DataGrid.setParam("dg1", "orderSn", $V("orderSn"));
		DataGrid.setParam("dg1", "state", $V("state"));
		DataGrid.loadData("dg1");
	}

	//保全记录
	function baoquanedit(orderSn, id) {
		var arr = DataGrid.getSelectedData("dg1");
		if (!arr || arr.getRowCount() == 0) {
			Dialog.alert("请先选择要处理的条目！");
			return;
		}
		if (!arr || arr.getRowCount() >= 2) {
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		if (id == null || id == '') {
			id = arr.get(0, "id");
		}
		if (orderSn == null || orderSn == '') {
			orderSn = arr.get(0, "orderSn");
		}
		var d = new Dialog("Diag1");
		d.Width = 550;
		d.Height = 450;
		d.Title = "保全记录";
		d.URL = "DataService/PaymentBaoquanEdit.jsp?prop1=" + id + "&orderSn="
				+ orderSn;
		d.ShowMessageRow = true;
		d.MessageTitle = "保全记录 ";
		d.show();
		d.OKButton.hide();
		d.CancelButton.value = "关闭";
	}

	//清空查询条件
	function doblank() {
		insureName.value = "";
		idNO.value = "";
		createDate.value = "";
		endCreateDate.value = "";
		contactName.value = "";
		contactMobile.value = "";
		contactMail.value = "";
		orderSn.value = "";
		document.getElementById("state").value = "";
	}

	// 受理
	function access(id, state) {
		var arr = DataGrid.getSelectedData("dg1");
		if (!arr || arr.getRowCount() == 0) {
			Dialog.alert("请先选择要处理的条目！");
			return;
		}
		if (!arr || arr.getRowCount() >= 2) {
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		if (id == null || id == '') {
			id = arr.get(0, "id");
		}
		if (state == null || state == '') {
			state = arr.get(0, "state");
		}
		if (state == '1') {
			Dialog.alert('该报案信息正在处理中！');
			return;
		}
		if (state == '2') {
			Dialog.alert('该报案信息已处理完！');
			return;
		}
		var dc = new DataCollection();
		dc.add("id", id);
		var method = "com.wangjin.cms.payment.MemberPayment.paymentAccess";

		Server.sendRequest(method, dc, function(response) {
			Dialog.alert(response.Message);
			if (response.Status == 1) {
				DataGrid.loadData('dg1');
			}
		});
	}

	// 处理完成
	function dealEnd(id, state) {
		var arr = DataGrid.getSelectedData("dg1");
		if (!arr || arr.getRowCount() == 0) {
			Dialog.alert("请先选择要处理的条目！");
			return;
		}
		if (!arr || arr.getRowCount() >= 2) {
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		if (id == null || id == '') {
			id = arr.get(0, "id");
		}
		if (state == null || state == '') {
			state = arr.get(0, "state");
		}
		if (state == '2') {
			Dialog.alert('该报案信息已处理完！');
			return;
		}
		var dc = new DataCollection();
		dc.add("id", id);
		var method = "com.wangjin.cms.payment.MemberPayment.paymentDealEnd";

		Server.sendRequest(method, dc, function(response) {
			Dialog.alert(response.Message);
			if (response.Status == 1) {
				DataGrid.loadData('dg1');
			}
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
						<td valign="middle" class="blockTd"><img
							src="../Icons/icon018a6.gif" width="20" height="20" /> 理赔报案信息</td>
					</tr>
					<tr>
						<td><z:init
								method="com.wangjin.cms.payment.MemberPayment.init">
								<table cellspacing="0" cellpadding="3">
									<tr>
										<td>被保险人姓名：</td>
										<td><input name="insureName" type="text" id="insureName"
											value="" style="width: 300px">
										</td>
										<td>联系人姓名：</td>
										<td><input name="contactName" type="text"
											id="contactName" value="" style="width: 100px">
										</td>
										<td>处理状态：</td>
										<td><z:select style="width:100px;">
												<select name="state" id="state">
													<option value=""></option>
													<option value="0">未处理</option>
													<option value="1">处理中</option>
													<option value="2">已处理</option>
												</select>
											</z:select>
										</td>
										<td>提交时间 从：</td>
										<td><input name="createDate" type="text" id="createDate"
											value="${createDate}" style="width: 100px" ztype="date">
										</td>
										<td>至：</td>
										<td><input name="endCreateDate" type="text"
											id="endCreateDate" value="${endCreateDate}"
											style="width: 100px" ztype="date">
										</td>
									</tr>
									<tr>
										<td>被保险人证件号：</td>
										<td><input name="idNO" type="text" id="idNO" 
											style="width: 300px">
										</td>
										<td>联系人电话：</td>
										<td><input name="contactMobile" type="text"
											id="contactMobile" style="width: 100px" />
										</td>
										<td>联系人邮箱：</td>
										<td><input name="contactMail" type="text"
											id="contactMail" value="" style="width: 100px" />
										</td>
										<td>订单号：</td>
										<td><input name="orderSn" type="text" id="orderSn"
											value="" style="width: 100px" />
										</td>
									</tr>
									<tr>
										<td colspan="8"><z:tButtonPurview>${_DataService_MemberPayment_Button}</z:tButtonPurview>
										</td>
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
											method="com.wangjin.cms.payment.MemberPayment.paymentInquery"
											size="20" scroll="true" lazy="true">
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable"
												style="text-align: center; table-layout: fixed;"
												fixedHeight="400px">
												<tr ztype="head" class="dataTableHead">
													<td width="30" ztype="RowNo" style="text-align: center;"><strong>序号</strong>
													</td>
													<td width="15" ztype="selector" field="id"
														style="text-align: center;">&nbsp;</td>
													<td width="120"><b>订单号</b>
													</td>
													<td width="105"><b>保全记录</b>
													</td>
													<td width="120"><b>提交时间</b>
													</td>
													<td width="120"><b>修改时间</b>
													</td>
													<td width="80"><b>处理状态</b>
													</td>
													<td width="220"><b>被保险人</b>
													</td>
													<td width="320"><b>被保险人证件号</b>
													</td>
													<td width="100"><b>联系人</b>
													</td>
													<td width="100"><b>联系人电话</b>
													</td>
													<td width="110"><b>联系人邮箱</b>
													</td>
													<td width="315"><b>申请保障类别</b>
													</td>
													<td width="115"><b>事故发生时间</b>
													</td>
													<td width="210"><b>事故发生地点</b>
													</td>
													<td width="410"><b>事故发生描述</b>
													</td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td width="3%">&nbsp;</td>
													<td style="text-align: center;">&nbsp;</td>
													<td style="text-align: left;" title="${orderSn}">${orderSn}</td>
													<td style="text-align: left;" title="${remark}">${remark}</td>
													<td style="text-align: left;" title="${createDate}">${createDate}</td>
													<td style="text-align: left;" title="${modifyDate}">${modifyDate}</td>
													<td style="text-align: left;" title="${stateName}">${stateName}</td>
													<td style="text-align: left;" title="${insureName}">${insureName}</td>
													<td style="text-align: left;" title="${insureIdentityId}">${insureIdentityId}</td>
													<td style="text-align: left;" title="${contactName}">${contactName}</td>
													<td style="text-align: left;" title="${contactMobile}">${contactMobile}</td>
													<td style="text-align: left;" title="${contactMail}">${contactMail}</td>
													<td style="text-align: left;" title="${ensureType}">${ensureType}</td>
													<td style="text-align: left;" title="${happenTime}">${happenTime}</td>
													<td style="text-align: left;" title="${happenArea}">${happenArea}</td>
													<td style="text-align: left;" title="${happenDesc}">${happenDesc}</td>
												</tr>
												<tr ztype="pagebar">
													<td height="25" colspan="11">${PageBar}</td>
													<input name="id" type="hidden" id="id" value="${id}"
														style="width: 150px">
													<input name="orderSn" type="hidden" id="orderSn"
														value="${orderSn}" style="width: 150px">
													<input name="state" type="hidden" id="state"
														value="${state}">
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