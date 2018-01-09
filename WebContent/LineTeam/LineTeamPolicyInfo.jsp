<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>线下预收系统</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

	Page.onLoad(function() {
		//searchdata();
	});
	/*
	 * 添加
	 */
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 750;
		diag.Height = 270;
		diag.Title = "添加线下出单";
		diag.URL = "LineTeam/LineTeamPolicyInfoAdd.jsp";
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "添加预售数据";
		diag.show();
	}
	/**
	 * 保存添加数据
	 */
	function addSave() {
		if ($DW.$V("citys") == '') {
			Dialog.alert("请选择所在地区");
			return;
		}
		if ($DW.$V("companyCode") == '') {
			Dialog.alert("请选择保险公司");
			return;
		}
		if ($DW.$V("riskType") == '') {
			Dialog.alert("请选择险种");
			return;
		}
		var dc = $DW.Form.getData("form1");
		if ($DW.Verify.hasError()) {
			return;
		}
		Dialog.wait("正在保存，请稍候......");
		Server.sendRequest("com.wangjin.lineteam.LineTeamPolicyInfo.add", dc,
				function(response) {
					Dialog.endWait();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	}

	/*
	 * 删除
	 */
	function del() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的数据！");
			return;
		}
		if (arr.length > 1) {
			Dialog.alert("请单条选择要删除的数据！");
			return;
		}
		Dialog.confirm("确认要删除？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest(
					"com.wangjin.lineteam.LineTeamPolicyInfo.delete", dc,
					function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData("dg1");
							}
						});
					});
		});
	}

	/*
	 * 撤单
	 */
	function cancelOrder() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要撤单的数据！");
			return;
		}
		if (arr.length > 1) {
			Dialog.alert("每次只能撤销一条数据！");
			return;
		}
		var dt = DataGrid.getSelectedData("dg1");
		var dr = dt.Rows[0];
		var status = dr.get("status");
		if (status == "1") {
			Dialog.alert("该单已撤销！");
			return;
		}
		var diag = new Dialog("Diag1");
		diag.Width = 450;
		diag.Height = 150;
		diag.Title = "撤单";
		diag.URL = "LineTeam/LineTeamCancelOrder.jsp?id=" + dr.get("id")  + "&agentPrice=" + dr.get("agentPrice");
		diag.OKEvent = cancelSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "撤单";
		diag.show();
	}

	/**
	 * 撤单回调
	 */
	function cancelSave() {
		var dc = $DW.Form.getData("form1");
		if ($DW.Verify.hasError()) {
			return;
		}
		Dialog.wait("正在撤销，请稍候......");
		Server.sendRequest(
				"com.wangjin.lineteam.LineTeamPolicyInfo.cancelOrder", dc,
				function(response) {
					Dialog.endWait();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	}

	/**
	 * 批量导入
	 */
	function batchAdd() {
		var diag = new Dialog("Diag1");
		diag.Width = 400;
		diag.Height = 300;
		diag.Title = "批量添加";
		diag.URL = "LineTeam/LineTeam_batchDialog.jsp";
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value = "关  闭";
	}

	/**
	 * 批次删除
	 */

	function batchdel() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请按照要删除的批次号选择对应一条数据");
			return;
		}
		if (arr.length > 1) {
			Dialog.alert("请单条选择要删除的数据！");
			return;
		}
		Dialog.confirm("确认要删除？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest(
					"com.wangjin.lineteam.LineTeamPolicyInfo.batchdel", dc,
					function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData("dg1");
							}
						});
					});
		});
	}

	/**
	 * 查询
	 */
	function searchdata() {
		var policyNo = $V("policyNo");
		var beginInsureDate = $V("beginInsureDate");
		var endInsureDate = $V("endInsureDate");
		var riskType = $V("riskType");
		var companyCode = $V("companyCode");
		var beginStartDate = $V("beginStartDate");
		var endStartDate = $V("endStartDate");
		var provs = $V("provs");
		var citys = $V("citys");
		var customName = $V("customName");
		var beginCreatedate = $V("beginCreatedate");
		var endCreatedate = $V("endCreatedate");
		var beginCreatetime = $V("beginCreatetime");
		var endCreatetime = $V("endCreatetime");
		if (endInsureDate != '' && endInsureDate < beginInsureDate) {
			Dialog.alert("查询条件-出单日期结束日期不能小于开始日期！");
			return;
		}
		if (endStartDate != '' && endStartDate < beginStartDate) {
			Dialog.alert("查询条件-保险起期结束日期不能小于开始日期！");
			return;
		}
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "policyNo", policyNo);
		DataGrid.setParam("dg1", "beginInsureDate", beginInsureDate);
		DataGrid.setParam("dg1", "endInsureDate", endInsureDate);
		DataGrid.setParam("dg1", "companyCode", companyCode);
		DataGrid.setParam("dg1", "riskType", riskType);
		DataGrid.setParam("dg1", "beginStartDate", beginStartDate);
		DataGrid.setParam("dg1", "endStartDate", endStartDate);
		DataGrid.setParam("dg1","provs",provs);
		DataGrid.setParam("dg1","citys",citys);
		DataGrid.setParam("dg1", "customName", customName);
		DataGrid.setParam("dg1", "beginCreatedate", beginCreatedate);
		DataGrid.setParam("dg1", "endCreatedate", endCreatedate);
		DataGrid.setParam("dg1", "beginCreatetime", beginCreatetime);
		DataGrid.setParam("dg1", "endCreatetime", endCreatetime);
		DataGrid.loadData("dg1");
		
		var dc = new DataCollection();
		dc.add("policyNo", $V("policyNo"));
		dc.add("beginInsureDate", $V("beginInsureDate"));
		dc.add("endInsureDate", $V("endInsureDate"));
		dc.add("riskType", $V("riskType"));
		dc.add("companyCode", $V("companyCode"));
		dc.add("beginStartDate", $V("beginStartDate"));
		dc.add("endStartDate", $V("endStartDate"));
		dc.add("provs", $V("provs"));
		dc.add("citys", $V("citys"));
		dc.add("customName", $V("customName"));
		dc.add("style", "0");
		dc.add("beginCreatedate", beginCreatedate);
		dc.add("endCreatedate", endCreatedate);
		dc.add("beginCreatetime", beginCreatetime);
		dc.add("endCreatetime", endCreatetime);
		Server.sendRequest("com.wangjin.lineteam.LineTeamPolicyInfo.orderCount", dc,function() {
					var response = Server.getResponse();
					var strs= new Array(); //定义一数组 
					strs=response.Message.split("&");
					jQuery("#totalAll").text(strs[0]);
					jQuery("#chargeAll").text(strs[1]);
				});
	}
	/**
	* 所在地区省改变重新加载市
	*/
	function changeProv(){
		jQuery("#citys").remove();
		jQuery("#provs").after("<select id=\"citys\" style=\"height:21px;width:116px;margin-left:4px\"></select>");
		var prov=jQuery("#provs").val();
		var dc = new DataCollection();
		dc.add("prov", prov);
		Server.sendRequest("com.wangjin.lineteam.LineTeamPolicyInfo.loadCitys", dc,
			function(response) {
				if (response.Status == 1) {
					if(response.Message != null){
						jQuery("#citys").append(response.Message);
					}
					/* changeCity(); */
				}
			});
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
						<z:init method="com.wangjin.lineteam.LineTeamPolicyInfo.init">
							<table  cellspacing="0" cellpadding="3">
								<tr>
									<td>保单号：</td>
									<td>
										<input name="policyNo" type="text" id="policyNo" value="" style="width:200px" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>出单日期 从：</td>
				                	<td> <input name="beginInsureDate" type="text" id="beginInsureDate" value="" style="width:90px" ztype="date"></td>
									<td>至：</td>
									<td><input name="endInsureDate" type="text" id="endInsureDate" value="" style="width:90px" ztype="date"></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>保险起期 从：</td>
				                	<td> <input name="beginStartDate" type="text" id="beginStartDate" value="" style="width:90px" ztype="date"></td>
									<td>至：</td>
									<td><input name="endStartDate" type="text" id="endStartDate" value="" style="width:90px" ztype="date"></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>导入日期 从：</td>
				                	<td> <input name="beginCreatedate" type="text" id="beginCreatedate" value="" style="width:90px" ztype="date"></td>
				                	<td> <input name="beginCreatetime" type="text" id="beginCreatetime" value="" style="width:90px" ztype="time"></td>
									<td>至：</td>
									<td><input name="endCreatedate" type="text" id="endCreatedate" value="" style="width:90px" ztype="date"></td>
									<td><input name="endCreatetime" type="text" id="endCreatetime" value="" style="width:90px" ztype="time"></td>
								</tr>
							</table>
							<table>
								<tr>
									<td>保险公司：</td>
									<td>
										<z:select id="companyCode" name="companyCode" style="width:120px"> ${companys} </z:select>
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>险种：</td>
									<td>
										<z:select id="riskType" name="riskType"> ${riskTypes} </z:select>
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td align="right" height="35">所在地区：</td>
									<td>
									<z:select id="provs" onChange="changeProv()" style="height:21px;width:116px;">${provs}</z:select>
									<z:select id="citys" style="height:21px;width:116px;">${citys}</z:select>
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>客户姓名：</td>
									<td>
										<input name="customName" type="text" id="customName" value="" style="width:120px">
									</td>
								</tr>
									<tr>
										<td>&nbsp;&nbsp;保费总额：<em id="chargeAll">0.00</em></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;手续费总额：<em id="totalAll">0.00</em></td>
									</tr>
									<tr>
								   <td colspan="8" nowrap><z:tButtonPurview>${_LineTeam_LineTeamPolicyInfo_Button}</z:tButtonPurview></td>
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
					<z:datagrid id="dg1" method="com.wangjin.lineteam.LineTeamPolicyInfo.dg1DataBind" size="15" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="40" style="text-align:center;" ztype="RowNo" ><strong>序号</strong></td>
								<td width="15" style="text-align:center;" ztype="selector" field="id" >&nbsp;</td>
						        <td width="100" style="text-align:center;"><b>区域</b></td>
								<td width="60" style="text-align:center;"><b>保险公司</b></td>
						        <td width="60" style="text-align:center;"><b>客户姓名</b></td>
						        <td width="60" style="text-align:center;"><b>车牌</b></td>
						        <td width="150" style="text-align:center;"><b>保单号</b></td>
						        <td width="60" style="text-align:center;"><b>保费</b></td>
								<td width="60" style="text-align:center;"><b>手续费收入</b></td>
								<td width="80" style="text-align:center;"><b>结算费率</b></td>
						        <td width="80" style="text-align:center;"><b>工作室</b></td>
								<td width="60" style="text-align:center;"><b>险种</b></td>
								<td width="80" style="text-align:center;"><b>出单日期</b></td>
								<td width="60" style="text-align:center;"><b>保险起期</b></td>
								<td width="60" style="text-align:center;"><b>撤单时间</b></td>
								<td width="60" style="text-align:center;"><b>订单类型</b></td>
								<td width="60" style="text-align:center;"><b>批次号</b></td>
								<td width="60" style="text-align:center;"><b>导入时间</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td width="15">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
								<td style="text-align:left;" title="${destination}">${prov}-${city}</td>
								<td style="text-align:left;" title="${companyName}">${companyName}</td>
								<td style="text-align:left;" title="${customName}">${customName}</td>
								<td style="text-align:left;" title="${plateNumber}">${plateNumber}</td>
								<td style="text-align:left;" title="${policyNo}">${policyNo}</td>
								<td style="text-align:left;" title="${prem}">${prem}</td>
								<td style="text-align:left;" title="${agentPrice}">${agentPrice}</td>
								<td style="text-align:left;" title="${agentRate}">${agentRate}</td>
								<td style="text-align:left;" title="${agentName}">${agentName}</td>
								<td style="text-align:left;" title="${riskTypeName}">${riskTypeName}</td>
								<td style="text-align:left;" title="${insureDate}">${insureDate}</td>
								<td style="text-align:left;" title="${startDate}">${startDate}</td>
								<td style="text-align:left;" title="${cancelTime}">${cancelTime}</td>
								<td style="text-align:left;" title="${remark1}">${remark1}</td>
								<td style="text-align:left;" title="${remark2}">${remark2}</td>
								<td style="text-align:left;" title="${createdate}">${createdate}</td>
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
