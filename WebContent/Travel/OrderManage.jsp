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
<title>订单信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
function doSearch(){ 
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","contactName",$V("contactName"));
	DataGrid.setParam("dg1","orderStatus",$V("orderStatus"));
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate")); 
	DataGrid.loadData("dg1");
}

function doExamine(orderStatus){
	 
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length!=1){
		Dialog.alert("请先选择一条信息！");
		return false;
	}
 	
	var select_data = DataGrid.getSelectedData("dg1"); 
	if(select_data.Rows[0].get("orderstatusname") == '已支付'){
		Dialog.alert("已支付的订单不可审核!");
		return;	
	}
	
	Dialog.confirm("审核后不可恢复，确认要审核通过？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		dc.add("orderStatus", orderStatus);
		Server.sendRequest("com.sinosoft.cms.travel.OrderManage.doExamine", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
								DataGrid.loadData("dg1");
						}
					});
				});
	});
	
}


function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的信息！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 500;
	diag.Title = "编辑订单";
	diag.URL = "Travel/OrderInfoEdit.jsp?id="+arr[0];
	diag.onLoad = function(){
		 
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	
	var select_data = DataGrid.getSelectedData("dg1"); 
	if(select_data.Rows[0].get("orderstatusname") == '已支付'){
		Dialog.alert("已支付的订单不可以修改!");
		return;	
	}
	
	
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
  }
  
	var dt = $DW.DataGrid.getAllData("dg2");
		 
	dc.add("DT",dt);
		
	Server.sendRequest("com.sinosoft.cms.travel.OrderManage.doModify",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		})
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
			              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 订单信息</td>
			          </tr>
					<tr>
					<td>
					<z:init method="com.sinosoft.cms.travel.OrderManage.init">
						 <table  cellspacing="0" cellpadding="3">
							<tr>
								<td>订单号：</td>
								<td> <input name="orderSn" type="text" id="orderSn" value="" style="width:100px"></td>
								<td>产品名称：</td>
								<td> <input name="productName" type="text" id="productName" value="" style="width:100px"></td>
								<td>联系人姓名：</td>
								<td> <input name="contactName" type="text" id="contactName" value="" style="width:100px"></td>
								<td>订单状态</td>
								<td><z:select style="width:100px;" name="orderStatus" id="orderStatus">${OrderStatus}</z:select></td>
			                	<td>订单时间 从：</td>
			                	<td> <input name="startDate" type="text" id="startDate" value="${startDate}" style="width:100px" ztype="date"></td>
								<td>至：</td>
								<td><input name="endDate" type="text" id="endDate" value="${endDate}"style="width:100px" ztype="date"></td>
							</tr>
							<tr>
							   	<td  colspan="8" nowrap>
							   		<z:tbutton onClick="doSearch()"><img src="../Icons/icon403a5.gif" />查询</z:tbutton>
							   		<z:tbutton onClick="doExamine(1)"><img src="../Icons/icon404a4.gif" />审核通过</z:tbutton>
							   		<z:tbutton onClick="doExamine(3)"><img src="../Icons/icon404a3.gif" />审核不通过</z:tbutton>
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
									<z:datagrid id="dg1" method="com.sinosoft.cms.travel.OrderManage.orderInquery" size="15" scroll="true" lazy="true">
										<table width="100%" cellpadding="2" cellspacing="0"
											class="dataTable" style="text-align: center;table-layout : fixed;"  >
											<tr ztype="head" class="dataTableHead">
												<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
												<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
										        <td style="text-align:center;"><b>订单编号</b></td>
										        <td style="text-align:center;"><b>订单状态</b></td>
										        <td style="text-align:center;"><b>总费用</b></td>
										        <td style="text-align:center;"><b>出行人数</b></td>
										        <td style="text-align:center;"><b>产品名称</b></td>
										        <td style="text-align:center;"><b>联系人</b></td>
										        <td style="text-align:center;"><b>联系电话</b></td>
												
												
											</tr>
											<tr  style="background-color:#F9FBFC" onDblClick="edit(this)"  >
												<td width="3%">&nbsp;</td>
												<td style="text-align:center;">&nbsp;</td>
												<td style="text-align:center;" title="${orderSn}"> ${orderSn} </td>
												<td title="${orderStatusName}">${orderStatusName}</td>
												<td title="${totalPrice}">${totalPrice}</td>
												<td style="text-align:center;" title="${travelNum}">${travelNum}</td>
												<td style="text-align:center;" title="${productName}">${productName}</td>
												<td style="text-align:center;" title="${contactName}">${contactName}</td>
												<td style="text-align:center;" title="${contactPhone}">${contactPhone}</td>
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
</html>
