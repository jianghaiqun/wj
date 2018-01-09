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
<title>游记总体统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//订单查询
function doSearch(){ 
	var sd = $V("statisticalTime");
	var ed = $V("endStatisticalTime");
	if(ed < sd){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","statisticalTime",sd);
	DataGrid.setParam("dg1","endStatisticalTime",ed);
	DataGrid.setParam("dg1","buyAuthor",$V("buyAuthor"));
	DataGrid.setParam("dg1","contactPeople",$NV("contactPeople"));
	DataGrid.loadData("dg1");
}

function add() {
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 200;
	diag.Title = "添加游记统计信息";
	diag.URL = "DaRen/TravelNotesStatisticsDialog.jsp?type=Add";
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加游记统计信息";
	diag.show();
}
function addSave() {

	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.TravelNotesStatistics.add", dc, function() {
		Dialog.endWait();
		var response = Server.getResponse();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});	
}

function edit() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的数据！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择1条信息修改！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 200;
	diag.Title = "修改游记统计信息";
	diag.URL = "DaRen/TravelNotesStatisticsDialog.jsp?type=Modify&ID=" + dr.get("id");
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改游记统计信息";
	diag.show();
}

function editSave() {

	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.TravelNotesStatistics.edit", dc, function() {
		Dialog.endWait();
		var response = Server.getResponse();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});	
}

function del() {
	var arr = DataGrid.getSelectedValue("dg1");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的数据！");
		return;
	}
	
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.wangjin.daren.TravelNotesStatistics.delete", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						}
					});
				});
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
					<z:init method="com.wangjin.daren.TravelNotesStatistics.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>统计时间 从：</td>
		                	<td> <input name="statisticalTime" type="text" id="statisticalTime" value="" style="width:90px" ztype="date"></td>
							<td>至：</td>
							<td><input name="endStatisticalTime" type="text" id="endStatisticalTime" value="" style="width:90px" ztype="date"></td>
							<td>购买作者：</td>
							<td> <input name="buyAuthor" type="text" id="buyAuthor" value="" style="width:100px"></td>
						</tr>
						<tr>
							<td>创建者：</td>
							<td colspan="9" nowrap>${contactPeople}</td>
						</tr>
						<tr><td height="10px"></td></tr>
						<tr>
						   <td colspan="4" nowrap><z:tButtonPurview>${_DaRen_TravelNotesStatistics_Button}</z:tButtonPurview></td>
		            	</tr>
		             </table>
		             </z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 5px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.daren.TravelNotesStatistics.dg1DataBind" size="15" scroll="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="350px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="90" style="text-align:center;"><b>统计时间</b></td>
						        <td width="70" style="text-align:center;"><b>作者量</b></td>
						        <td width="70" style="text-align:center;"><b>文章量</b></td>
						        <td width="70" style="text-align:center;"><b>流量</b></td>
						        <td width="70" style="text-align:center;"><b>成本</b></td>
						        <td width="70" style="text-align:center;"><b>订单量</b></td>
						        <td width="70" style="text-align:center;"><b>总保费</b></td>
								<td width="80" style="text-align:center;"><b>购买作者</b></td>
								<td width="70" style="text-align:center;"><b>作者订单量</b></td>
								<td width="70" style="text-align:center;"><b>作者保费</b></td>
								<td width="70" style="text-align:center;"><b>辅助转化数</b></td>
								<td width="90" style="text-align:center;"><b>创建时间</b></td>
								<td width="70" style="text-align:center;"><b>创建者</b></td>
								<td width="90" style="text-align:center;"><b>修改时间</b></td>
								<td width="70" style="text-align:center;"><b>修改者</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="30">&nbsp;</td>
								<td style="text-align:center;">&nbsp;<input name="id" type="hidden" id="id" value="${id}" style="width:150px"/></td>
								<td title="${statisticalTime}">${statisticalTime}</td>
								<td title="${authorNum}">${authorNum}</td>
								<td title="${articleNum}">${articleNum}</td>
								<td title="${flow}">${flow}</td>
								<td title="${cost}">${cost}</td>
								<td title="${orderNum}">${orderNum}</td>
								<td title="${sumPrem}">${sumPrem}</td>
								<td title="${buyAuthor}">${buyAuthor}</td>
								<td title="${authorOrderNum}">${authorOrderNum}</td>
								<td title="${authorSumPrem}">${authorSumPrem}</td>
								<td title="${convertNum}">${convertNum}</td>
								<td title="${createDate}">${createDate}</td>
								<td title="${createUser}">${createUser}</td>
								<td title="${modifyDate}">${modifyDate}</td>
								<td title="${modifyUser}">${modifyUser}</td>
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
