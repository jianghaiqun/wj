<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>理赔申请信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function doSearch(index){
	var startCreateDate = $V("createDate");
	var endCreateDate = $V("endCreateDate");
	var startModifyDate = $V("modifyDate");
	var endModifyDate = $V("endModifyDate");

	if(endCreateDate < startCreateDate){
		Dialog.alert("申请日结束日期不能小于开始日期！");
		return;
	}

	if(endModifyDate < startModifyDate){
		Dialog.alert("申请日结束日期不能小于开始日期！");
		return;
	}

	DataGrid.setParam("dg1", Constant.PageIndex, index);
	DataGrid.setParam("dg1", "orderSn", $V("orderSn"));
	DataGrid.setParam("dg1", "policyNo", $V("policyNo"));
	DataGrid.setParam("dg1", "memberId", $V("memberId"));
	DataGrid.setParam("dg1", "contactName", $V("contactName"));
	DataGrid.setParam("dg1", "contactMobile", $V("contactMobile"));
	DataGrid.setParam("dg1", "contactMail", $V("contactMail"));
	DataGrid.setParam("dg1", "claimsNo", $V("claimsNo"));
	DataGrid.setParam("dg1", "status", $V("status"));
	DataGrid.setParam("dg1", "insureName", $V("insureName"));
	DataGrid.setParam("dg1", "insureIdentityId", $V("insureIdentityId"));
	DataGrid.setParam("dg1", "supplierCode",$V("supplierCode"));
	DataGrid.setParam("dg1", "startCreateDate",startCreateDate);
	DataGrid.setParam("dg1", "endCreateDate",endCreateDate);
	DataGrid.setParam("dg1", "startModifyDate",startModifyDate);
	DataGrid.setParam("dg1", "endModifyDate",endModifyDate);
	DataGrid.loadData("dg1");
}

//保全记录
function baoquanedit(id) {
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
	
	var d = new Dialog("Diag1");
	d.Width = 550;
	d.Height = 450;
	d.Title = "保全记录";
	d.URL = "DataService/PaymentBaoquanEdit.jsp?prop1=" + id+"&orderSn="+arr.get(0, "orderSn");
	d.ShowMessageRow = true;
	d.MessageTitle = "保全记录 ";
	d.show();
	d.OKButton.hide();
	d.CancelButton.value = "关闭";
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的理赔申请！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0]; 
	
	var diag = new Dialog("Diag1");
	diag.Width = 1150;
	diag.Height = 350;
	diag.Title = "理赔申请信息修改";
	diag.URL = "Payment/ClaimsInfoEditDialog.jsp?id="+dr.get("id")+"&claimsNo="+dr.get("claimsNo");
	diag.onLoad = function(){
		if (dr.get("status")=='06') {
			$DW.$("finishTR").show();
		} else if (dr.get("status")=='03' || dr.get("status")=='07') {
			$DW.$("returnTR").show();
		}
		if (dr.get("caseType")=='02') {
			$DW.$("courierTR").show();
		}
		
	};

	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	var status = $DW.$V("status");
	if (status=='03' || status=='07') {
		// 拒赔/退回
		if ($DW.$V("returnDesc") == '') {
			Dialog.alert("拒赔/补充材料：请填写退回原因！");
			return;
		}
	}
	if (status == '04') {
		if ($DW.$V("caseType")=='01') {
			Dialog.alert("电子理赔无待邮寄理赔状态！");
			return;
		}
		if ($DW.$V("sendAddress") == '') {
			// 待邮寄
			Dialog.alert("请填写邮寄地址！");
			return;
		}
	}
	Dialog.wait("正在保存......"); 
	Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.saveCliamsInfo",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();});
			doSearch(DataGrid.getParam("dg1",Constant.PageIndex));
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function lookClaimsData() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要查看理赔资料的理赔申请！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0]; 
	
	var diag = new Dialog("Diag1");
	diag.Width = 950;
	diag.Height = 600;
	diag.Title = "理赔资料查看";
	diag.URL = "Payment/ClaimsDataEditDialog.jsp?id="+dr.get("id")+"&claimsNo="+dr.get("claimsNo");

	diag.show();
	diag.OKButton.hide();
}

function sendClaimsData() {
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要发送理赔资料的理赔 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var dr = arr.Rows[0];
	var dc = new DataCollection();
	dc.add("claimsNo", dr.get("claimsNo"));
	Dialog.wait("正在发送......"); 
	Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.sendClaimsData",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();});
			doSearch(DataGrid.getParam("dg1",Constant.PageIndex));
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function applyClaims() {
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要向保险公司申请的理赔 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	
	var dr = arr.Rows[0];
	if (dr.get("productId").indexOf("2049") != 0) {
		Dialog.alert("只有安联才可以向保险公司发出申请！");
		return;
	}
	
	var d = new Dialog("Diag1");
	d.Width = 750;
	d.Height = 200;
	d.Title = "向保险公司申请理赔";
	d.URL = "Payment/ApplyClaimsToCompanyDialog.jsp?id="+dr.get("id")+"&claimsNo="+dr.get("claimsNo");
	d.ShowMessageRow = true;
	d.MessageTitle = "向保险公司申请理赔";
	
	d.OKEvent = sendClaimsXml;
	d.show();
	//d.OKButton.hide();
	d.OKButton.value = "发送";
	d.CancelButton.value = "关闭";
}

function sendClaimsXml() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	
	if ($DW.$V("kindOfLoss") == 'KOL170') {
		if ($DW.$V("claimReason") == '' || $DW.$V("claimReason") == null) {
			Dialog.alert("请选择保险公司理赔原因！");
			return;
		}
		if ($DW.$V("flightNo") == '' || $DW.$V("flightNo") == null) {
			Dialog.alert("请填写航班号！");
			return;
		}
		if ($DW.$V("flightTime") == '' || $DW.$V("flightTime") == null) {
			Dialog.alert("请填写计划起飞时间！");
			return;
		}
	}
	
	Dialog.wait("正在发送......"); 
	Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.sendClaimsXML",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();});
			doSearch(DataGrid.getParam("dg1",Constant.PageIndex));
		}else{
			Dialog.alert(response.Message);
			return;
		}
		
	});
}

function lookLogistics() {
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要查看物流信息的理赔 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var dr = arr.Rows[0];
	if (dr.get("courierFirm") == null || dr.get("courierFirm") == '') {
		Dialog.alert("填写快递信息后才可以查看物流信息 ！");
		return;
	}
	if (dr.get("courierNumber") == null || dr.get("courierNumber") == '') {
		Dialog.alert("填写快递信息后才可以查看物流信息 ！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 500;
	diag.Title = "物流信息查看";
	diag.URL = "DataService/BillLogisticsInfoDialog.jsp?WayBillNo="+dr.get("courierNumber") + "&LogisticsId="+dr.get("courierFirm");
	diag.ShowMessageRow = true;
	diag.MessageTitle = "物流信息查看";
	diag.show(); 
	diag.OKButton.hide();
}

function sendFile() {
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要向保险公司上传文件的理赔 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	
	var dr = arr.Rows[0];
	if (dr.get("productId").indexOf("2049") != 0) {
		Dialog.alert("只有安联才可以向保险公司上传文件！");
		return;
	}
	
	var dr = arr.Rows[0];
	var dc = new DataCollection();
	dc.add("id", dr.get("id"));
	Dialog.wait("正在上传......"); 
	Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.sendAllFile",dc,function(response){
		Dialog.closeEx();
		Dialog.alert(response.Message);
	});
}

function queryClaimsInfo() {
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要查询的理赔 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	
	var dr = arr.Rows[0];
	if (dr.get("productId").indexOf("2049") != 0) {
		if(dr.get("productId").indexOf("2005") != 0||dr.get("productId").indexOf("2101") != 0){
		Dialog.alert("只有安联或人保才可以查询保险公司理赔进度！");
		return;
		}
	}
	
	var dr = arr.Rows[0];
	var dc = new DataCollection();
	dc.add("id", dr.get("id"));
	Dialog.wait("正在查询......"); 
	Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.queryClaimsInfo",dc,function(response){
		Dialog.closeEx();
		Dialog.alert(response.Message);
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />理赔申请信息列表</td>
				</tr>
				<tr>
					<td>
						<z:init method="com.sinosoft.cms.payment.PaymentManage.init">
			
						<table width="70%">
							<tr>
								<td width="60px;">订单号：</td>
								<td width="120px;"><input name="orderSn" type="text" id="orderSn" value="" style="width: 150px" class="inputText"></td>
								<td width="60px;">保单号：</td>
								<td width="120px;"><input name="policyNo" type="text" id="policyNo" value="" style="width: 150px" class="inputText"></td>
								<td width="90px;">会员ID：</td>
								<td width="120px;"><input name="memberId" type="text" id="memberId" value="" style="width: 150px" class="inputText"></td>
								<td width="80px;">联系人姓名：</td>
								<td width="90px;"><input name="contactName" type="text" id="contactName" value="" style="width: 90px" class="inputText"></td>
								<td width="90px;">联系人电话：</td>
								<td width="90px;"><input name="contactMobile" type="text" id="contactMobile" value="" style="width: 90px" class="inputText"></td>
								<td width="80px;">联系人邮箱：</td>
								<td width="90px;"><input name="contactMail" type="text" id="contactMail" value="" style="width: 120px" class="inputText"></td>
							</tr>
							<tr>
								<td width="60px;">理赔单号：</td>
								<td width="120px;"><input name="claimsNo" type="text" id="claimsNo" value="" style="width: 150px" class="inputText"></td>
								<td width="60px;">理赔状态：</td>
								<td width="120px;"><z:select name="status" id="status" style="width:80px;">${ClaimsStatus}</z:select></td>
								<td width="80px;">被保人姓名：</td>
								<td width="90px;"><input name="insureName" type="text" id="insureName" value="" style="width: 90px" class="inputText"></td>
								<td width="90px;">被保人证件号：</td>
								<td width="90px;" colspan="3"><input name="insureIdentityId" type="text" id="insureIdentityId" value="" style="width: 150px" class="inputText"></td>
								
							</tr>
							<tr>
								<td width="60px;">保险公司：</td>
								<td> <z:select name="supplierCode" id="supplierCode" style="width:100px;">${supplier}</z:select></td>
								<td>申请时间:</td>
								<td width="80px;"><input name="createDate" type="text" id="createDate"
										   value="" style="width: 100px" ztype="date"></td>
								<td>至：</td>
								<td><input name="endCreateDate" type="text"
										   id="endCreateDate" value=""
										   style="width: 100px" ztype="date"></td>
								<td width="80px;">更新时间:</td>
								<td><input name="modifyDate" type="text" id="modifyDate"
										   value="" style="width: 100px" ztype="date"></td>
								<td>至：</td>
								<td><input name="endModifyDate" type="text"
										   id="endModifyDate" value=""
										   style="width: 100px" ztype="date"></td>
						     </tr>
						</table>
						</z:init>
					</td>
				</tr>

				<tr>
					<td>
						<z:tbutton onClick="doSearch(0);"><img src="../Icons/icon018a4.gif" />查询</z:tbutton> 
						<z:tbutton onClick="edit();"><img src="../Icons/icon018a4.gif" />修改理赔信息</z:tbutton>
						<z:tbutton onClick="lookClaimsData();"><img src="../Icons/icon018a4.gif" />查看理赔资料</z:tbutton>
						<z:tbutton onClick="baoquanedit();"><img src="../Icons/icon018a4.gif" />保全记录</z:tbutton>
						<z:tbutton onClick="lookLogistics()"><img src="../Icons/icon021a4.gif" />查看物流信息</z:tbutton>
						<z:tbutton onClick="sendClaimsData()"><img src="../Icons/icon021a4.gif" />资料发送保险公司</z:tbutton> 
						<z:tbutton onClick="applyClaims()"><img src="../Icons/icon021a4.gif" />保险公司申请理赔</z:tbutton> 
						<z:tbutton onClick="sendFile()"><img src="../Icons/icon021a4.gif" />保险公司上传文件</z:tbutton> 
						<z:tbutton onClick="queryClaimsInfo()"><img src="../Icons/icon021a4.gif" />保险公司理赔进度查询</z:tbutton> 
					</td>
				</tr>
	<tr>
		<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
		<div style="width:1980px;  overflow: auto;">
		<z:datagrid id="dg1" method="com.sinosoft.cms.payment.PaymentManage.dg1DataBind" size="15">
			<table width="120%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
				<tr ztype="head" class="dataTableHead">
					<td width="3%" ztype="RowNo">&nbsp;</td>
					<td width="2%" ztype="selector" field="id">&nbsp;</td>
					<td width="6%"><b>理赔单号</b></td>
					<td width="4%"><b>理赔项目</b></td>
					<td width="6%"><b>保单号</b></td>
					<td width="6%"><b>订单号</b></td>
					<td width="4%"><b>产品名</b></td>
					<td width="4%"><b>渠道</b></td>
					<td width="3%"><b>保全记录</b></td>
					<td width="4%"><b>会员ID</b></td>
					<td width="4%"><b>被保人姓名</b></td>
					<td width="6%"><b>被保人证件号</b></td>
					<td width="3%"><b>理赔状态</b></td>
					<td width="3%"><b>补充资料</b></td>
					<td width="3%"><b>申请日期</b></td>
					<td width="4%"><b>联系人姓名</b></td>
					<td width="4%"><b>联系人电话</b></td>
					<td width="4%"><b>联系人邮箱</b></td>
					<td width="3%"><b>案件类型</b></td>
					<td width="6%"><b>申请人与被保人关系</b></td>
					<td width="4%"><b>客户上传资料</b></td>
					<td width="5%"><b>保险公司报案号</b></td>
					<td width="6%"><b>保险公司上传资料</b></td>
					<td width="7%"><b>更新日期</b></td>
				</tr>
				<tr style="background-color: #F9FBFC">
					<td align="center">&nbsp;</td>
					<td>&nbsp;</td>
					<td title="${claimsNo}">${claimsNo}</td>
					<td title="${claimsItemsName}">${claimsItemsName}</td>
					<td title="${policyNo}">${policyNo}</td>
					<td title="${orderSn}">${orderSn}</td>
					<td title="${productName}">${productName}</td>
					<td title="${channelsn}">${channelsn}</td>
					<td title="${remark}">${remark}</td>
					<td title="${memberId}">${memberId}</td>
					<td title="${insureName}">${insureName}</td>
					<td title="${insureIdentityId}">${insureIdentityId}</td>
					<td title="${statusName}">${statusName}</td>
					<td title="${compleStatus}">${compleStatus}</td>
					<td title="${appliDate}">${appliDate}</td>
					<td title="${contactName}">${contactName}</td>
					<td title="${contactMobile}">${contactMobile}</td>
					<td title="${contactMail}">${contactMail}</td>
					<td title="${caseTypeName}">${caseTypeName}</td>
					<td title="${insureRelation}">${insureRelation}</td>
					<td title="${cusIsUpload}">${cusIsUpload}</td>
					<td title="${notificationNo}">${notificationNo}</td>
					<td title="${isUpload}">${isUpload}</td>
					<td title="${ModifyDate}">${ModifyDate}</td>
				</tr>
				<tr ztype="pagebar">
					<td colspan="18">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid></div></td>
	</tr>
</table>
</td>
		</tr>
	</table>
</body>
</html>
