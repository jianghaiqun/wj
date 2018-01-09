<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>天猫活动消费信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var KID = "<%=KID%>";
function doSearch(){
	var an = $V("appntName");
	var ci = $V("certificateId");
	var tds = $V("tradeDateStart");
	var tde = $V("tradeDateEnd");
	var tn = $V("tradeSumMin");
	var tx = $V("tradeSumMax");
	var un = $V("useSumMin");
	var ux = $V("useSumMax");
	var en = $V("effSumMin");
	var ex = $V("effSumMax");
	var all=an+ci+tds+tde+tn+tx+un+ux+en+ex;
	if(all == null || all == '' || all == '-1'){
		Dialog.alert("查询条件不能为空,至少要有一个查询条件！");
		return;
	}
	if(tde < tds){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	if(isNaN(tn)||isNaN(tx)||isNaN(un)||isNaN(ux)||isNaN(en)||isNaN(ex)){
	    Dialog.alert("金额必须为数字！");
		return;
	}

	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","appntName",$V("appntName"));
	DataGrid.setParam("dg1","certificateId",$V("certificateId"));
	DataGrid.setParam("dg1","tradeDateStart",$V("tradeDateStart"));
	DataGrid.setParam("dg1","tradeDateEnd",$V("tradeDateEnd"));
	DataGrid.setParam("dg1","tradeSumMin",$V("tradeSumMin"));
	DataGrid.setParam("dg1","tradeSumMax",$V("tradeSumMax"));
	DataGrid.setParam("dg1","useSumMin",$V("useSumMin"));
	DataGrid.setParam("dg1","useSumMax",$V("useSumMax"));
	DataGrid.setParam("dg1","effSumMin",$V("effSumMin"));
	DataGrid.setParam("dg1","effSumMax",$V("effSumMax"));
	DataGrid.loadData("dg1");
}
function doblank(){
	appntName.value="";
	certificateId.value="";
	tradeDateStart.value="";
	tradeDateEnd.value="";
	tradeSumMin.value="";
	tradeSumMax.value="";
	useSumMin.value="";
	useSumMax.value="";
	effSumMin.value="";
	effSumMax.value="";
}
function edit(){
	var arr = DataGrid.getSelectedData("dg1");
	if(!arr || arr.getRowCount() == 0){
		Dialog.alert("请先选择要编辑的条目！");
		return;
	}
	if(!arr||arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 560;
	diag.Height = 210;
	diag.Title = "人工修改天猫活动消费信息";
	diag.URL = "Marking/TBTradeRecordEdit.jsp?id=" + arr.get(0, "id") 
			+ "&appntName=" + arr.get(0, "appntName")
			+ "&certificateTypeName=" + arr.get(0, "certificateTypeName")
			+ "&certificateId=" + arr.get(0, "certificateId")
			+ "&tradeDate=" + arr.get(0, "tradeDate")
			+ "&tradeSum=" + arr.get(0, "tradeSum")
			+ "&useSum=" + arr.get(0, "useSum")
			+ "&effSum=" + arr.get(0, "effSum");
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "人工修改天猫活动消费信息";
	diag.OKEvent = editSave;
	diag.show();
}
function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.sinosoft.cms.dataservice.TBTradeRecord.save",dc,function(response){
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 天猫活动消费信息</td>
				</tr>
				<tr>
					<td>
						<z:init method="com.sinosoft.cms.dataservice.TBTradeRecord.init">
							<table  cellspacing="0" cellpadding="3">
								<tr>
									<td>投保人：</td>
									<td> <input name="appntName" type="text" id="appntName" style="width:100px"></td>
									<td>证件号：</td>
									<td> <input name="certificateId" type="text" id="certificateId" value="" style="width:100px"></td>
									<td>消费时间 从：</td>
				                	<td> <input name="tradeDateStart" type="text" id="tradeDateStart" value="${tradeDateStart}" style="width:100px" ztype="date"></td>
									<td>至：</td>
									<td><input name="tradeDateEnd" type="text" id="tradeDateEnd" value="${tradeDateEnd}"style="width:100px" ztype="date"></td>
								</tr>
								<tr>
									<td>消费金额 从：</td>
				                	<td> <input name="tradeSumMin" type="text" id="tradeSumMin" style="width:100px" ztype="text"></td>
									<td>至：</td>
									<td><input name="tradeSumMax" type="text" id="tradeSumMax" style="width:100px" ztype="text"></td>
									<td>使用金额 从：</td>
				                	<td> <input name="useSumMin" type="text" id="useSumMin" style="width:100px" ztype="text"></td>
									<td>至：</td>
									<td><input name="useSumMax" type="text" id="useSumMax" "style="width:100px" ztype="text"></td>
									<td>有效金额 从：</td>
				                	<td> <input name="effSumMin" type="text" id="effSumMin" style="width:100px" ztype="text"></td>
									<td>至：</td>
									<td><input name="effSumMax" type="text" id="effSumMax" style="width:100px" ztype="text"></td>
								</tr>
								<tr>
									<td colspan="4">
										<z:tbutton onClick="doSearch()" >
											<img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
										</z:tbutton>
										<z:tbutton onClick="doblank()">
											<img src="../Icons/icon401a3.gif" width="20" height="20" />清空查询条件
										</z:tbutton>
										<z:tbutton onClick="edit()" >
											<img src="../Icons/icon021a4.gif" width="20" height="20"/>人工修改数据
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
									<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.TBTradeRecord.dg1DataBind" scroll="true" lazy="true" size="20" >
										<table width="100%" cellpadding="2" cellspacing="0"
											class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
											<tr ztype="head" class="dataTableHead">
												<td width="10%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
												<td width="5%" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
										        <td width="15%" style="text-align:center;"><b>投保人</b></td>
										        <td width="10%" style="text-align:center;"><b>证件类型</b></td>
										        <td width="20%" style="text-align:center;"><b>证件号</b></td>
										        <td width="10%" style="text-align:center;"><b>消费时间</b></td>
										        <td width="10%" style="text-align:center;"><b>消费金额</b></td>
										        <td width="10%" style="text-align:center;"><b>使用金额</b></td>
										        <td width="10%" style="text-align:center;"><b>有效金额</b></td>
										        <td style="display:none"></td>
											</tr>
											<tr style="background-color:#F9FBFC">
												<td>&nbsp;</td>
												<td style="text-align:center;">&nbsp;</td>
												<td style="text-align:center;">${appntName}</td>
												<td style="text-align:center;">${certificateTypeName}</td>
												<td style="text-align:center;">${certificateId}</td>
												<td style="text-align:center;">${tradeDate}</td>
												<td style="text-align:right;">${tradeSum}</td>
												<td style="text-align:right;">${useSum}</td>
												<td style="text-align:right;">${effSum}</td>
												<td style="display:none">${id}</td>
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
