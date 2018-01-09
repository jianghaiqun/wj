<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人保财险人工承保</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var KID = "<%=KID%>";
function doSearch(){
	var sd = $V("createDate");
	var ed = $V("endCreateDate");
	var tn = $V("tbTradeSeriNo");
	var od = $V("orderSn");
	var pn = $V("policyNo");
	var uf = $V("underwritingStatus");
	var all=sd+ed+tn+od+pn+uf;
	if(all == null || all == '' || all == '-1'){
		Dialog.alert("查询条件不能为空,至少要有一个查询条件！");
		return;
	}
	if(ed < sd){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
	DataGrid.setParam("dg1","tbTradeSeriNo",$V("tbTradeSeriNo"));
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","policyNo",$V("policyNo"));
	DataGrid.setParam("dg1","underwritingStatus",$V("underwritingStatus"));
	DataGrid.loadData("dg1");
}
function doblank(){
	createDate.value="";
	endCreateDate.value="";
	tbTradeSeriNo.value="";
	orderSn.value="";
	policyNo.value="";
	underwritingStatus.value="";
}
function underWriting(){
	var arr = DataGrid.getSelectedData("dg1");
	if(!arr || arr.getRowCount() == 0){
		Dialog.alert("请先选择要查看的条目！");
		return;
	}
	if(!arr||arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	if(arr.get(0 , "underwritingStatus") == "异常"){
		Dialog.alert("该订单保单号异常，请联系技术人员！");
		return;
	}
	if(arr.get(0 , "underwritingStatus") == "已承保"){
		Dialog.alert("该订单已承保，若需变更，请联系技术人员！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 560;
	diag.Height = 410;
	diag.Title = "人保财险人工承保";
	diag.URL = "DataService/RBCXUnderwritingDetail.jsp?orderSn=" + arr.get(0, "orderSn") 
			+ "&insuredSn=" + arr.get(0, "insuredSn")
			+ "&recognizeeName=" + arr.get(0, "recognizeeName")
			+ "&recognizeeIdentityTypeName=" + arr.get(0, "recognizeeIdentityTypeName")
			+ "&recognizeeIdentityId=" + arr.get(0, "recognizeeIdentityId")
			+ "&recognizeeBirthday=" + arr.get(0, "recognizeeBirthday")
			+ "&recognizeeSexName=" + arr.get(0, "recognizeeSexName")
			+ "&receiveDate=" + arr.get(0, "receiveDate")
			+ "&channel=" + arr.get(0, "channel")
			+ "&timePrem=" + arr.get(0, "timePrem")
			+ "&productName=" + arr.get(0, "productName");
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.ShowButtonRow = false;
	diag.MessageTitle = "人保财险人工承保";
	diag.show();
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 人保财险人工承保</td>
				</tr>
				<tr>
					<td>
						<z:init method="com.sinosoft.cms.dataservice.RBCXUnderwriting.init">
							<table cellspacing="0" cellpadding="3">
								<tr>
									<td>外部订单号：</td>
									<td> <input name="tbTradeSeriNo" type="text" id="tbTradeSeriNo" value="" style="width:100px"></td>
									<td>系统订单号：</td>
									<td> <input name="orderSn" type="text" id="orderSn" value="" style="width:100px"></td>
									<td>保单号：</td>
									<td> <input name="policyNo" type="text" id="policyNo" value="" style="width:100px"></td>
									<td>订单时间 从:</td>
									<td> <input name="createDate" type="text" id="createDate" value="${createDate}" style="width:100px" ztype="date"></td>
									<td>到</td>
									<td> <input name="endCreateDate" type="text" id="endCreateDate" value="${endCreateDate}" style="width:100px" ztype="date"></td>
									<td>承保状态：</td>
									<td> <z:select style="width:100px;" name="underwritingStatus" id="underwritingStatus">${underwritingStatusList}</z:select></td>
								</tr>
								<tr>
								<tr>
									<td colspan="4">
										<z:tbutton onClick="doSearch()" >
											<img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
										</z:tbutton>
										<z:tbutton onClick="doblank()">
											<img src="../Icons/icon401a3.gif" width="20" height="20" />清空查询条件
										</z:tbutton>
										<z:tbutton onClick="underWriting()" >
											<img src="../Icons/icon021a4.gif" width="20" height="20"/>人工承保
										</z:tbutton>
					            	</td>
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
									<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.RBCXUnderwriting.dg1DataBind" scroll="true" lazy="true" size="20" >
										<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style="table-layout : fixed"  fixedHeight="480px"  afterdrag="afterRowDragEnd">
											<tr ztype="head" class="dataTableHead">
												<td width="30" ztype="RowNo"><strong>序号</strong></td>
												<td width="15" ztype="selector" field="insuredSn">&nbsp;</td>
												<td width="120"><b>外部订单号</b></td>
												<td width="120"><b>系统订单号</b></td>
												<td width="140"><b>被保人编号</b></td>
												<td width="160"><b>航延保单号</b></td>
												<td width="160"><b>航意保单号</b></td>
												<td width="55"><b>承保状态</b></td>
												<td width="120" style="text-align:center;"><b>支付时间</b></td>
						        				<td width="120" style="text-align:center;"><b>生效日期</b></td>
						        				<td width="180" style="text-align:center;"><b>产品名称</b></td>
						        				<td width="50" style="text-align:center;"><b>保费</b></td>
						        				<td width="70" style="text-align:center;"><b>投保人姓名</b></td>
												<td width="90" style="text-align:center;"><b>投保人证件类型</b></td>
												<td width="140" style="text-align:center;"><b>投保人证件号码</b></td>
												<td width="90" style="text-align:center;"><b>投保人出生日期</b></td>
												<td width="70" style="text-align:center;"><b>投保人性别</b></td>
												<td width="100" style="text-align:center;"><b>投保人联系方式</b></td>
												<td width="150" style="text-align:center;"><b>投保人邮箱</b></td>
						        				<td width="70" style="text-align:center;"><b>被保人姓名</b></td>
												<td width="90" style="text-align:center;"><b>被保人证件类型</b></td>
												<td width="140" style="text-align:center;"><b>被保人证件号码</b></td>
												<td width="90" style="text-align:center;"><b>被保人出生日期</b></td>
												<td width="70" style="text-align:center;"><b>被保人性别</b></td>
												<td width="55" style="text-align:center;"><b>渠道</b></td>
											</tr>
											<tr style="background-color:#F9FBFC">
												<td width="10%">&nbsp;</td>
												<td>&nbsp;</td>
												<td style="text-align:center;">${tbTradeSeriNo}</td>
												<td style="text-align:center;">${orderSn}</td>
												<td style="text-align:center;">${insuredSn}</td>
												<td style="text-align:center;">${policyNo}</td>
												<td style="text-align:center;">${applyPolicyNo}</td>
												<td style="text-align:center;">${underwritingStatus}</td>
												<td style="text-align:center;">${receiveDate}</td>
												<td style="text-align:center;">${svaliDate}</td>
												<td style="text-align:center;">${productName}</td>
												<td style="text-align:center;">${timePrem}</td>
												<td style="text-align:center;">${applicantName}</td>
												<td style="text-align:center;">${applicantIdentityTypeName}</td>
												<td style="text-align:center;">${applicantIdentityId}</td>
												<td style="text-align:center;">${applicantBirthday}</td>
												<td style="text-align:center;">${applicantSexName}</td>
												<td style="text-align:center;">${applicantMobile}</td>
												<td style="text-align:center;">${applicantMail}</td>
												<td style="text-align:center;">${recognizeeName}</td>
												<td style="text-align:center;">${recognizeeIdentityTypeName}</td>
												<td style="text-align:center;">${recognizeeIdentityId}</td>
												<td style="text-align:center;">${recognizeeBirthday}</td>
												<td style="text-align:center;">${recognizeeSexName}</td>
												<td style="text-align:center;">${channel}</td>
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
