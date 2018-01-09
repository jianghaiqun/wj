<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<z:init method="com.wangjin.infoseeker.ClaimStatistics.initStaff">

	<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>理赔率统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript">
	/* Page.onLoad(function(){
 
	 DataList.loadData("dg1");
	 } */
	function query() {
		DataGrid.setParam("dg1", "startDate", $V("startDate"));
		DataGrid.setParam("dg1", "endDate", $V("endDate"));
		DataGrid.setParam("dg1", "company", $V("company"));
		DataGrid.setParam("dg1", "contant", $NV("contant"));
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid
				.loadData(
						"dg1",
						function() {
							var arrs = DataGrid.getAllData("dg1");
							var drs = arrs.Rows;
							var dr = drs[0];
							if (drs[0] != "" && drs[0] != null) {
								var fee = dr.get("fee");
								var claimStatistics = dr.get("claimStatistics");
								document.getElementById("fee").value = fee;
								document.getElementById("claimStatistics").value = claimStatistics;
							} else {
								document.getElementById("fee").value = "";
								document.getElementById("claimStatistics").value = "";
							}

						});

	}
	function importData() {
		//var currentPath = Explorer.baseDir+"/"+Explorer.currentPath;
		var diag = new Dialog("Diag1");
		diag.Width = 400;
		diag.Height = 150;
		diag.Title = "上传文件";
		diag.URL = "SaleInfo/ClaimStatisticsFileImportDialog.jsp";
		diag.ShowButtonRow = true;
		diag.OKEvent = function() {
			$DW.upload();
		};
		diag.show();
	}
	function exportTemplate() {
		var eleA = document.createElement("a");
		eleA.href = "../DataService/ADTemplate/claimStatisticsTemplate.xls"; //url 是你得到的连接
		eleA.target = "_self"; //指定在新窗口打开
		eleA.download="理赔率统计清单模板";
		document.body.appendChild(eleA);
		eleA.click();
		document.body.removeChild(eleA);
	}
	function del() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		Dialog.confirm("您确认要删除选择的行吗？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Dialog.wait("正在清楚数据");
			Server.sendRequest("com.wangjin.infoseeker.ClaimStatistics.del",
					dc, function(response) {
						Dialog.endWait();
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								query();
							}
						});
					});
		});
	}
</script>
</head>
<body>
	<div id="StaffStat">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr valign="top">
				<td>
					<table width="20" border="0" cellspacing="0" cellpadding="6"
						class="blockTable">
						<tr>
							<td style="padding: 6px;" class="blockTd"><z:tree id="tree1"
									style="height:440px;width:120px;"
									method="com.sinosoft.platform.Channel.treeAllDataBind"
									level="3" resizeable="true">
									<img src="../Framework/Images/icon_drag.gif" width="16"
										height="16">
									<p cid='${ID}' level="${TreeLevel}">
										<input type="checkbox" name="contant" id='contant_${ID}'
											value='${ChannelCode}' onClick="onCheck(this);"><label
											for="contant_${ID}"><span id="span_${ID}">${Name}</span></label>
									</p>
								</z:tree></td>
						</tr>
					</table>
				</td>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="6"
						class="blockTable">
						<tr>
							<td style="padding: 4px 8px;" class="blockTd"><span
								style="float: left; width: 700px"> 统计时间：从 <input
									value="${startDate}" type="text" id="startDate"
									name="startDate" ztype="Date" class="inputText" size="14">
									到<input value="${endDate}" type="text" id="endDate"
									name="endDate" ztype="Date" class="inputText" size="14">
									保险品牌： <z:select id="company">${company}</z:select>
							</span> <z:tbutton onClick="query()">
									<img src="../Icons/icon031a15.gif" />统计</z:tbutton> <z:tbutton
									onClick="del()">
									<img src="../Icons/icon032a3.gif" />删除</z:tbutton> <z:tbutton
									onClick="importData()">
									<img src="../Icons/icon031a16.gif" />导入清单</z:tbutton> <z:tbutton
									onClick="exportTemplate()">
									<img src="../Icons/icon031a7.gif" />导出模板</z:tbutton></td>
						</tr>
						<tr>
							<td
								style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
								<z:datagrid id="dg1"
									method="com.wangjin.infoseeker.ClaimStatistics.dg1DataBind"
									lazy="true" size="15">
									<table cellpadding="2" cellspacing="0"
										style="width: 1500px; overflow: auto;" class="dataTable"
										style="text-align: center;">
										<tr ztype="head" class="dataTableHead">
											<td width="5%" ztype="RowNo"><b>序号</b></td>
											<td width="5%" ztype="selector" field="id">&nbsp;</td>
											<td width="20%"><b>保单号</b></td>
											<%--<td width="10%"><b>保险品牌</b></td>--%>
											<td width="10%">渠道</td>
											<td width="20%"><b>理赔项目</b></td>
											<td width="10%"><b>理赔金额</b></td>
											<%-- <td width="10%"><b>保费</b></td> --%>
											<td width="10%"><b>理赔日期</b></td>
										</tr>
										<tr style1="background-color:#FFFFFF"
											style2="background-color:#F9FBFC">
											<td align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
											<td style="text-align: left;">${policyNo}</td>
											<%-- <td style="text-align: center;">${CodeName}</td> --%>
											<td style="text-align: center;">${ChannelName}</td>
											<td style="text-align: center;">${claimsItemsName}</td>
											<td style="text-align: right;">${claimsAmount}</td>
											<%-- <td style="text-align: right;">${timePrem}</td> --%>
											<td style="text-align: center;">${claimsDate}</td>
										</tr>
										<tr ztype="pagebar">
											<td colspan="7">${PageBar}</td>
										</tr>
									</table>
								</z:datagrid>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset id="statistics" style="width: 500px;">
									<legend> 统计数据 </legend>
									<label> 总保费(元) </label> <input type="text" id="fee"
										readonly="readonly"> <label> 理赔率 </label> <input
										type="text" id="claimStatistics" readonly="readonly">
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<iframe name="InputorStat" id="InputorStat" frameborder="0"
		scrolling="auto" style="width: 100%; height: 100%; display: none;"></iframe>
</body>
	</html>
</z:init>