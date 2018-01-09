<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function upload(){
	var diag = new Dialog("Diag1");
	diag.Width = 330;
	diag.Height = 200;
	diag.Title = "导入退款结果数据";
	diag.URL = "DataService/RefundResultImport.jsp";
    diag.OKEvent=OK;
	diag.show();
}

function OK(){
	$DW.upload();
}

function onFileUploadCompleted(){
	DataGrid.setParam("dg3",Constant.PageIndex,0);
	DataGrid.loadData("dg3");
	if($D){
		setTimeout(function(){$D.close()}, 100);
	}
}

function doSearch(){
	if ($V("StartDate") != '' && $V("StartTime") == '') {
		Dialog.alert("请输入开始时间。");
		return;
	}
	if ($V("EndDate") != '' && $V("EndTime") == '') {
		Dialog.alert("请输入结束时间。");
		return;
	}
	DataGrid.setParam("dg4",Constant.PageIndex,0);
	DataList.setParam("dg4","StartDate",$V("StartDate"));
	DataList.setParam("dg4","EndDate",$V("EndDate"));
	DataList.setParam("dg4","StartTime",$V("StartTime"));
	DataList.setParam("dg4","EndTime",$V("EndTime"));
	DataList.setParam("dg4","OrderSn",$V("OrderSn"));
	DataList.setParam("dg4","PaySn",$V("PaySn"));
	DataGrid.loadData("dg4");
}

function edit(){
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的退款信息！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 300;
	diag.Title = "修改退款信息";
	diag.URL = "DataService/RefundResultDialog.jsp?Id="+dr.get("Id");
	diag.onLoad = function(){
		//$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑退款信息";
	diag.Message = "设置退款信息";
	diag.show();
}

function editSave() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg3');
			}
		});
	});
}
</script>
</head>
<body onContextMenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<fieldset>
		<legend><label>代客支付撤单查询</label></legend> 
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:4px 10px;">撤单时间： 从 <input type="text" id="StartDate" style="width:100px; " ztype='date'>
				<span class="tdgrey2">
				<input name="StartTime" type="text" class="inputText"
					id="StartTime" ztype="Time" size=14 verify="NotNull" /> </span>
				 至 <input type="text" id="EndDate" style="width:100px; " ztype='date'>
				<span class="tdgrey2">
				<input name="EndTime" type="text" class="inputText"
					id="EndTime" ztype="Time" size=14 verify="NotNull" /> </span>
				&nbsp;订单号: <input name="OrderSn" type="text" id="OrderSn" style="width:150px"> 
				商户订单号： <input type="text" id="PaySn" name="PaySn" style="width:150px; ">
				<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton"></td>
			</tr>
		</table>
		</fieldset>
		<br />
		<table>
			<tr>
				<td style="padding:0px 5px;">
				<z:datagrid id="dg4" method="com.sinosoft.cms.dataservice.InitiateRefundManage.dg4DataBind" size="15" lazy="true">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterDrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" ztype="selector" field="id">&nbsp;</td>
							<td width="12%"><b>订单号</b></td>
							<td width="12%"><b>保单号</b></td>
							<td width="12%"><b>商户订单号</b></td>
							<td width="12%"><b>交易流水号</b></td>
							<td width="6%"><b>退款金额</b></td>
							<td width="6%"><b>支付方式</b></td>
							<td width="10%"><b>撤单日期</b></td>
							<td width="15%"><b>保全记录</b></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${OrderSn}</td>
							<td>${policyNo}</td>
							<td>${PaySn}</td>
							<td>${tradeCheckSeriNo}</td>
							<td>${RefundAmount}</td>
							<td title="${payTypeName}">${payTypeName}</td>
							<td>${cancelDate}</td>
							<td title="${remark}">${remark}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="10" align="center">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
