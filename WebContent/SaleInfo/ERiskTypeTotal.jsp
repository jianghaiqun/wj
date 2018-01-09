<% 
/* JSP程序简要描述信息 
************************************************************** 
*  程序名 :   	主站每日品类销售数据统计
*  建立日期:  	2015-01-13
*  作者   :    	jiaomengying 
*  模块   :    	销售统计 
*  描述   :  	统计 主站每日品类销售数据
*  备注   :   
* ------------------------------------------------------------ 
*  修改历史 
*  序号   		日期   				修改人   			  修改原因 
* 	1 		2015-01-13		jiaomengying	1860req20141229001-主站每日品类销售数据
* 	2 
************************************************************** 
*/ 
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.TotalInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>主站每日各险种销售情况</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statStaff(cancelFlag){
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","allDate","0");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","cancelFlag",cancelFlag);
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
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
						<span style="float: left;width:500px">
						统计时间：从
						<input value="${yesterday}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						</span>
						<z:tbutton onClick="statStaff(0)"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<z:tbutton onClick="statStaff(1)"> <img src="../Icons/icon031a1.gif" />撤单统计</z:tbutton>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.ERiskTypeTotal.dg1DataBind" lazy="true">
						<table width="50%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="8%"><b>序号</b></td>
								<td width="20%">险种</td>
								<td width="15%">订单数</td>
								<td width="20%" ><b>保费收入</b></td>
								<td width="20%" ><b>手续费收入</b></td>
								<td width="20%" ><b>单均收入</b></td>	
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">${rowNo}</td>
								<td>${productGenera}</td>
								<td style="text-align:right;">${count}</td>
								<td style="text-align:right;">${totalAmount}</td>
								<td style="text-align:right;">${charge}</td>
								<td style="text-align:right;">${avg}</td>
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