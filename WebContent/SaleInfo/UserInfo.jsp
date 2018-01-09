<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.UserInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>用户注册统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statStaff(){
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","registType",$V("registType"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function statAll(){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1","allDate","1");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
//显示当天注册的用户列表
function showRegistDetail(registDate,orderType){
	var registType=document.getElementById("registType").value; 
    var arr = DataGrid.getSelectedValue("dg1");
	if(registDate&&registDate!=""){
		arr = new Array();
		arr[0] = registDate;
		arr[1] = registType;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 350;
	diag.Title = "当天注册用户列表查看";
	diag.URL = "SaleInfo/UserListDialog.jsp?registDate="+arr[0]+"&registType="+registType+"&orderType="+orderType;
		diag.onLoad = function() {
			$DW.init();
		};
		diag.ShowMessageRow = true;
		diag.MessageTitle = "查看会员信息";
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value = "关闭";
	}
</script>
</head>
<body>
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:520px">
						统计时间：从
						<input value="${yesterday}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						注册类型：
						<z:select name="registType" id="registType">
										<span value="0" selected>全部</span>
										<span value="1">正常注册</span>
										<span value="2">自动注册</span>
										<span value="3">淘宝注册</span>
									</z:select> </span>
						<z:tbutton onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.UserInfo.dg1DataBind">
						<table width="25%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="60" ztype="RowNo"><b>序号</b></td>
								<td width="100">注册日期</td>
								<td width="100">注册会员数</td>
								<td width="120">未投保会员数</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td>${registDate}</td>
								<td align="right"><a style="cursor: hand;" onClick="showRegistDetail('${registDate}','0')">${count}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right"><a style="cursor: hand;" onClick="showRegistDetail('${registDate}','1')">${ncount}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</body>
</html>
</z:init>