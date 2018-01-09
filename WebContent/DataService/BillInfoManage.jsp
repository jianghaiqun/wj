<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发票管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","memberId",$V("memberId"));
	DataGrid.setParam("dg1","deliverName",$V("deliverName"));
	DataGrid.setParam("dg1","deliverTel",$V("deliverTel"));
	DataGrid.setParam("dg1","updateUser",$V("updateUser"));
	DataGrid.setParam("dg1","status",$V("status"));
	DataGrid.setParam("dg1","mobileNO",$V("mobileNO"));
	DataGrid.setParam("dg1","email",$V("email"));
	DataGrid.loadData("dg1");
}

function edit(id){
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要编辑的发票！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var dr = arr.Rows[0];
	if (dr.get("Status") == "04") {
		Dialog.alert("已撤销的发票不能修改 ！");
		return;
	}

	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 500;
	diag.Title = "物流信息填写";
	diag.URL = "DataService/BillInfoEditDialog.jsp?id="+dr.get("id");
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "物流信息填写";
	diag.show(); 
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.BillInfoManage.dg1Edit",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}

function dealing() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要处理的发票！");
		return;
	}
	var arr1 = DataGrid.getSelectedData("dg1");
	for (var i = 0; i < arr1.getRowCount(); i++) {
		if (arr1.Rows[i].get("Status") != '01') {
			Dialog.alert("只能处理发票状态是审核中（客户已申请）的发票");
			return;
		}
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.sinosoft.cms.dataservice.BillInfoManage.dealing",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

function lookLogistics() {
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要查看物流信息的发票 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var dr = arr.Rows[0];
	if (dr.get("Status") != "03") {
		Dialog.alert("只有已邮寄状态的发票才可以查看物流信息 ！");
		return;
	}
	if (dr.get("Logistics") == null || dr.get("Logistics") == '') {
		Dialog.alert("填写物流公司后才可以查看物流信息 ！");
		return;
	}
	if (dr.get("WayBillNo") == null || dr.get("WayBillNo") == '') {
		Dialog.alert("填写运单号后才可以查看物流信息 ！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 500;
	diag.Title = "物流信息查看";
	diag.URL = "DataService/BillLogisticsInfoDialog.jsp?WayBillNo="+dr.get("WayBillNo") + "&LogisticsId="+dr.get("LogisticsId");
	diag.ShowMessageRow = true;
	diag.MessageTitle = "物流信息查看";
	diag.show(); 
	diag.OKButton.hide();
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
						<td valign="middle" class="blockTd"><img
							src="../Icons/icon018a6.gif" />发票列表</td>
					</tr>
					<tr>
						<td>
						<z:init method="com.sinosoft.cms.dataservice.BillInfoManage.init">
							<table>
								<tr>
									<td width="50px" align="right">会员ID：</td>
									<td><input name="memberId" type="text" id="memberId"
										value="" style="width: 90px" class="inputText">
									</td>
									<td width="50px" align="right">邮箱：</td>
									<td><input name="email" type="text" id="email"
										value="" style="width: 90px" class="inputText">
									</td>
									<td width="50px" align="right">电话：</td>
									<td><input name="mobileNO" type="text" id="mobileNO"
										value="" style="width: 90px" class="inputText">
									</td>
									<td width="80px" align="right">收件人姓名：</td>
									<td><input name="deliverName" type="text"
										id="deliverName" value="" style="width: 90px"
										class="inputText"></td>
									<td width="80px" align="right">收件人电话：</td>
									<td><input name="deliverTel" type="text"
										id="deliverTel" value="" style="width: 90px"
										class="inputText"></td>
									<td width="100px" align="right">处理人用户名：</td>
									<td><input name="updateUser" type="text"
										id="updateUser" value="" style="width: 90px"
										class="inputText"></td>
									<td width="80px" align="right">发票状态：</td>
									<td><z:select name="status" id="status" style="width: 80px">${Status}</z:select></td>
								</tr>
								<tr>
               						<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
            					</tr>
							</table>
						</z:init>
						</td>
					</tr>
					<tr>
                    	<td>
                    		<z:tbutton onClick="edit()"><img src="../Icons/icon021a4.gif" />修改</z:tbutton> 
                    		<z:tbutton onClick="dealing()"><img src="../Icons/icon021a4.gif" />处理中</z:tbutton>
                    		<z:tbutton onClick="lookLogistics()"><img src="../Icons/icon021a4.gif" />查看物流信息</z:tbutton> 
                    	</td>
					</tr>
					<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.BillInfoManage.dg1DataBind" size="20" lazy="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="30px" ztype="RowNo"><strong>序号</strong></td>
								<td width="30px" ztype="selector" field="id">&nbsp;</td>
								<td width="70px"><b>会员ID</b></td>
								<td width="60px"><b>会员等级</b></td>
								<td width="70px"><b>发票ID</b></td>
								<td width="70px"><b>收件人姓名</b></td>
								<td width="80px"><b>收件人电话</b></td>
								<td width="60px"><b>发票状态</b></td>
								<td width="60px"><b>发票类型</b></td>
								<td width="100px"><b>发票抬头</b></td>
								<td width="60px"><b>发票金额</b></td>
								<td width="50px"><b>邮编</b></td>
								<td width="100px"><b>邮寄地址</b></td>
								<td width="70px"><b>开票申请表</b></td>
								<td width="60px"><b>物流公司</b></td>
								<td width="90px"><b>运单号</b></td>
								<td width="120px"><b>申请时间</b></td>
								<td width="60px"><b>处理人</b></td>
								<td width="120px"><b>处理时间</b></td>
							</tr>
							<tr onDblClick="edit('${id}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${MemberId}">${MemberId}</td>
								<td title="${gradeName}">${gradeName}</td>
								<td title="${id}">${id}</td>
								<td title="${DeliverName}">${DeliverName}</td>
								<td title="${DeliverTel}">${DeliverTel}</td>
								<td title="${statusName}">${statusName}</td>
								<td title="${BillTypeName}">${BillTypeName}</td>
								<td title="${BillTitle}">${BillTitle}</td>
								<td title="${BillAmount}">${BillAmount}</td>
								<td title="${DeliverZipCode}">${DeliverZipCode}</td>
								<td title="${DeliverAddr}">${DeliverAddr}</td>
								<td ><a href="${BillReqUrl}">${BillReqUrlName}</a></td>
								<td title="${Logistics}">${Logistics}</td>
								<td title="${WayBillNo}">${WayBillNo}</td>
								<td title="${CreateDate}">${CreateDate}</td>
								<td title="${UpdateUser}">${UpdateUser}</td>
								<td title="${UpdateDate}">${UpdateDate}</td>
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
</body>
</html>