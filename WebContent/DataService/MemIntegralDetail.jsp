<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看积分明细</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>


<script>
function doSearch(){
	var startdate = $V("StartDate");
	var enddate = $V("EndDate");
	if(startdate>enddate){
		Dialog.alert("起始日期不得早于截止日期！");
		return;		
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Source",$V("Source"));
	DataGrid.setParam("dg1","Status",$V("Status"));
	DataGrid.setParam("dg1","Manner",$V("Manner"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
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
					<td style="padding:8px 10px;">
					 
				 	从&nbsp;
					<input value="" type="text" id="StartDate" size="14" name="StartDate" ztype="Date"  class="inputText" >
					至&nbsp;
					<input value="" type="text" id="EndDate" size="14" name="EndDate" ztype="Date"  class="inputText" >                  
                  	积分来源：<input name="Source" type="text" id="Source" value="" style="width:90px"> 
                  	状态:
                   	<z:select style="width:90px;" >
						<select name="Status" id="Status"> 
						<option value=""></option> 
		                  <option value="0">正常</option>
		                  <option value="1">冻结</option>
		                </select>               
					</z:select>
					方式:
                   	<z:select style="width:90px;" >
						<select name="Manner" id="Manner"> 
						<option value=""></option> 
		                  <option value="0">收入</option>
		                  <option value="1">支出</option>
		                </select>               
					</z:select>
                    <input type="button" name="Submit" value="查询" onClick="doSearch()">                    
					</td>
				</tr>
	<tr>
			<td style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
			<z:datagrid id="dg1" method="com.sinosoft.message.MemberIntegral.dg2DataBind" size="10">
				<table width="100%" cellpadding="2" cellspacing="0"
					class="dataTable">
					<tr><td ></td></tr>
					<tr ztype="head" class="dataTableHead">
						<td width="4%" ztype="RowNo">&nbsp;</td>
						<td width="4%" ztype="selector" field="ID">&nbsp;</td>
						<td><b>积分</b></td>
						<td><b>积分来源</b></td>
						<td><b>积分说明</td>
						<td><b>积分收入/支出</b></td>
						<td><b>时间</b></td>
						<td><b>积分状态</b></td>
						
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>${Integral}</td>
						<td>${Source}</td>
						<td>${description}</td>
						<td>${manner}</td>
						<td>${CreateDate} ${CreateTime}</td>
						<td>${Status}</td>
						
					</tr>
					<tr ztype="pagebar">
					  <td colspan="10" align="center">${PageBar}</td>
					</tr>
				</table>
			</z:datagrid></td>
		</tr>
</table>
</body>
</html>