<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.DialogInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>对话统计情况</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function valCheck(obj){
	if(isNaN(obj.value)){
		alert('输入内容必须全部为数字');
		obj.focus();
		obj.select();
	}
}

function statStaff(){
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.setParam("dg2","startTime",$V("startTime"));
	DataGrid.setParam("dg2","endTime",$V("endTime"));
	DataGrid.setParam("dg2","responseTimeStart",$V("responseTimeStart"));
	DataGrid.setParam("dg2","responseTimeEnd",$V("responseTimeEnd"));
	DataGrid.setParam("dg2","reps",$V("reps"));
	DataGrid.setParam("dg2","displayFlag",$V("displayFlag"));
	DataGrid.setParam("dg2","dialogId",$V("dialogId"));
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

function exportStaff(){
	DataGrid.toExcel("dg2",1);
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
						<span style="float: left;width:800px">
						统计时间：从 
						<input value="${yesterday}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						<input value="" type="text" id="startTime" 
							name="startTime" ztype="Time"  class="inputText" size="14" >
						到 <input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						<input value="" type="text" id="endTime" 
							name="endTime" ztype="Time"  class="inputText" size="14" >
						&nbsp;&nbsp;&nbsp;&nbsp;会话ID：
						<input value="" type="text" id="dialogId" 
							name="dialogId" ztype="String"  class="inputText" size="14" onblur = "valCheck(this)">
						</span>
					</td>
				</tr>
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:600px">
						每问响应时间：从 
						<input value="" type="text" id="responseTimeStart" 
							name="responseTimeStart" ztype="String"  class="inputText" size="6" onblur = "valCheck(this)">
						到 <input value="" type="text" id="responseTimeEnd" 
							name="responseTimeEnd" ztype="String"  class="inputText" size="6" onblur = "valCheck(this)">
						&nbsp;&nbsp;&nbsp;&nbsp;客服：
						<input value="" type="text" id="reps" 
							name="reps" ztype="String"  class="inputText" size="14" >
						&nbsp;&nbsp;&nbsp;&nbsp;对话显示类型：
						<z:select id="displayFlag" name="displayFlag" style="width:50px">${displayFlag}</z:select>
						</span>
						<z:tbutton onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:5px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.wangjin.infoseeker.DialogInfo.dg2DataBind">
						<table width="90%" cellpadding="2" cellspacing="0"
							class="dataTable"  style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo"><b>序号</b></td>
								<td width="7%">会话ID</td>
								<td width="36%">问题内容</td>
								<td width="8%" ><b>每问响应时间</b></td>
								<td width="5%" ><b>客服ID</b></td>
								<td width="5%" ><b>客服名字</b></td>
								<td width="12%" ><b>对话开始时间</b></td>
								<td width="12%" ><b>对话结束时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td style="text-align:right;">${dialogId}</td>
								<td style="text-align:left;">${questionContext}</td>
								<td style="text-align:right;">${responseTime}</td>
								<td style="text-align:right;">${oid}</td>
								<td style="text-align:right;">${ona}</td>
								<td style="text-align:right;">${questionStartTime}</td>
								<td style="text-align:right;">${questionEndTime}</td>
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