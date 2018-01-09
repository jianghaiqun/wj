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
<title>撤单退款管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var KID = "<%=KID%>";
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","ordersn",$V("ordersn"));
	DataGrid.setParam("dg1","refundStatus",$V("refundStatus"));
	DataGrid.setParam("dg1","cancelFrom",$V("cancelFrom"));
	DataGrid.loadData("dg1");
}
//审核
function audit() {
	changeRefundStatus(1);
}
//退款
function refund() {
	changeRefundStatus(2);
}
function changeRefundStatus(status){
	var arr = DataGrid.getSelectedData("dg1");
	if(!arr || arr.getRowCount() == 0){
		Dialog.alert("请先选择要操作的条目！");
		return;
	}
	if(!arr||arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}

	var policyNo = arr.get(0,"policyNo");
	var dc = new DataCollection();
	dc.add("policyNo", policyNo);
	dc.add("refundStatus", status);
	Server.sendRequest("com.sinosoft.cms.dataservice.CancelContRefundManage.changeRefundStatus", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData("dg1");
					}

				});
			});
}
//保全记录
function baoquanedit(){
	var arr = DataGrid.getSelectedData("dg1");
	if(!arr || arr.getRowCount() == 0){
		Dialog.alert("请先选择要查看的条目！");
		return;
	}
	if(!arr||arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var orderSn=arr.get(0,"orderSn");

	var d = new Dialog("Diag1");
	d.Width = 550;
	d.Height = 450;
	d.Title = "保全记录";
	d.URL = "DataService/BaoquanEdit.jsp?orderSn="+orderSn;
	d.ShowMessageRow = true;
	d.MessageTitle = "保全记录 ";
	d.show();
	d.OKButton.hide();
	d.CancelButton.value="关闭";
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" />撤单退款管理</td>
		          </tr>
				<tr>
				<td>
				 <table  cellspacing="0" cellpadding="3">
					<tr>
						<td>订单号：</td>
						<td> <input name="ordersn" type="text" id="ordersn" value="" style="width: 120px"></td>
						<td>退款状态</td>
						<td>
						<z:select style="width:100px;"><select name="refundStatus" id="refundStatus"  >
						<option value="-1">请选退款状态</option>
						<option value="0">待审核</option>
						<option value="1">已审核</option>
						<option value="2">已退款</option>
						</select></z:select></td>
						<td>撤单方式</td>
						<td>
							<z:select style="width:100px;"><select name="cancelFrom" id="cancelFrom"  >
								<option value="-1">请选撤单方式</option>
								<option value="0">会员撤单</option>
								<option value="1">CMS后台撤单</option>
							</select></z:select></td>
					</tr>
					<tr>
					   <td  colspan="6">
						   <z:tButtonPurview>
							   ${_DataService_CancelContRefund_Button}
						   </z:tButtonPurview>
						</td>
					</tr>
				 </table>
				</td>
			</tr>
				<tr>
				  <td style="padding: 0px 5px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="6"
							class="blockTable" style="table-layout: fixed;" >
								<tr>
									<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
										<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.CancelContRefundManage.getOrderInfo" scroll="true" lazy="true" size="20" >
											<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style="table-layout : fixed"  fixedHeight="480px"  afterdrag="afterRowDragEnd">
												<tr ztype="head" class="dataTableHead">
													<td  width="30" ztype="RowNo"><strong>序号</strong></td>
													<td  width="15"  ztype="selector" field="insuredSn">&nbsp;</td>
													<td  width="120"><b>订单号</b></td>
													<td  width="130"><b>保单号</b></td>
													<td  width="80"><b>保费</b></td>
													<td  width="80"><b>实际支付保费</b></td>
													<td  width="80"><b>审核状态</b></td>
													<td  width="80"><b>撤单来源</b></td>
													<td  width="100"><b>撤单日期</b></td>
												</tr>
												<tr  style="background-color:#F9FBFC">
													<td width="10%">&nbsp;</td>
													<td>&nbsp;</td>
													<td>${orderSn}</td>
													<td>${policyNo}</td>
													<td>${prem}</td>
													<td>${payPrem}</td>
													<td>${status}</td>
													<td>${cancelFrom}</td>
													<td>${repealDate}</td>
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
