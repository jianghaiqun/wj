<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>浙金支付管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","tradeId",$V("tradeId"));
	DataGrid.setParam("dg1","refundflag",$V("refundflag"));
	DataGrid.setParam("dg1","insuredname",$V("insuredname"));
	DataGrid.setParam("dg1","idNO",$V("idNO"));
	DataGrid.setParam("dg1","mobile",$V("mobile"));
	DataGrid.loadData("dg1");
}
function refund(){
	Dialog.confirm("您确认要申请退款吗?",function(){
		if(!checked()){
			return ;
		}
		var dc = new DataCollection();
		var arr = DataGrid.getSelectedValue("dg1");
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.cms.dataservice.ZjfaePayManage.refund", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						} 
							
					});
				});
	});
}
function checked(){
	var flag = true;
	var arr1 = DataGrid.getSelectedData("dg1");
	var drs = arr1.Rows;
	for (var i = 0; i < arr1.getRowCount(); i++) {
		dr = drs[i];
		if(dr.get("appStatus")=="1"){
			Dialog.alert("订单承保状态，请先撤单！");
			flag =false;
			return flag;
		}
	}
	return flag;
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
				  <z:init method="com.sinosoft.cms.dataservice.ZjfaePayManage.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>订单号：</td>
							<td> <input name="orderSn" type="text" id="orderSn" value="" style="width:100px"></td>
							<td>支付流水号：</td>
							<td> <input name="tradeId" type="text" id="tradeId" value="" style="width:100px"></td>
							<td>订单状态</td>
							<td>
							<z:select style="width:100px;"><select name="refundflag" id="refundflag"  > 
							<option value="">请选择订单状态</option> 
							<option value="1">未退款 </option>
		                 	<option value="2">已退款 </option>
		                 	</select></z:select></td>
							<td>订单时间 从:</td>
							<td> <input name="startDate" type="text" id="startDate" value="${startDate}" style="width:100px" ztype="date"></td>
							<td>到</td>
							<td><input name="endDate" type="text" id="endDate" value="${endDate}" style="width:100px" ztype="date"></td>
						</tr>
						<tr>
						<tr>
							<td>被保人：</td>
							<td> <input name="insuredname" type="text" id="insuredname" value="" style="width:100px" /></td>
							<td>证件号码：</td>
							<td> <input name="idNO" type="text" id="idNO" value="" style="width:100px" /></td>
							<td>手机号：</td>
							<td> <input name="mobile" type="text" id="mobile" value="" style="width:100px"></td>
						</tr>
						<tr>
						   <td  colspan="4">
			            	   <z:tbutton onClick="doSearch()"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            	   <z:tbutton onClick="refund()"  id="Submitbutton">
			            	     <img src="../Icons/icon021a4.gif" width="20" height="20"/>退款
			            	   </z:tbutton>
			            	</td>
		            	</tr>
		             </table>
		           </z:init>
				</td>
			</tr>
				<tr>
				  <td style="padding: 0px 5px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="6"
							class="blockTable" style="table-layout: fixed;" >
							<tr>
								<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
									<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.ZjfaePayManage.dg1DataBind" scroll="true" lazy="true" size="20" >
										<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style="table-layout : fixed"  fixedHeight="480px"  afterdrag="afterRowDragEnd">
											<tr ztype="head" class="dataTableHead">
												<td width="20" ztype="RowNo"><strong>序号</strong></td>
												<td width="15"  ztype="selector" field="recognizeeSn">&nbsp;</td>
												<td width="110"><b>订单号</b></td>
												<td width="130"><b>被保人编号</b></td>
												<td width="80"><b>已付费用</b></td>
												<td width="60"><b>被保人姓名</b></td>
												<td width="130"><b>被保人证件号码</b></td>
												<td width="100"><b>被保人手机</b></td>
												<td width="110"><b>起保时间</b></td>
												<td width="110"><b>止保时间</b></td>
												<td width="110"><b>撤单日期</b></td>
												<td width="50"><b>保单状态</b></td>
											</tr>
											<tr style="background-color:${color}">
												<td width="10%">&nbsp;</td>
												<td>&nbsp;</td>
												<td>${ordersn}</td>
												<td>${recognizeeSn}</td>
												<td>${SubPayPrice}</td>
												<td>${recognizeeName}</td>
												<td>${recognizeeIdentityId}</td>
												<td>${recognizeeMobile}</td>
												<td>${svaliDate}</td>
												<td>${evaliDate} </td>
												<td>${cancelDate} </td>
												<td>${appStatus} </td>
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
