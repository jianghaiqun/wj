<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>每日快讯栏目发布统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statColumn(){
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

function statColumnAll(){
	DataGrid.setParam("dg2","startDate","");
	DataGrid.setParam("dg2","endDate","");
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

function exportColumn(){
	DataGrid.toExcel("dg2",0);
}
</script>
</head>
<body>
	<input type="hidden" id="ID" value="${ID}" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<z:init method="com.sinosoft.cms.dataservice.PublishColumn.initSearch">
						<span style="float: left;width:300px">
						统计时间：从
						<input value="${today}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" ztype="Date"  class="inputText" size="14" >
						</span>
						
						<z:tbutton onClick="statColumn()"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
						<z:tbutton onClick="statColumnAll()"> <img src="../Icons/icon031a15.gif" />全部统计</z:tbutton>
						<z:tbutton onClick="exportColumn()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
						
						</z:init>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.PublishStaff.dg14DataBind" page="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo" align="center"><b>名次</b></td>
								<td width="15%" align="center">站点名称</td>
								<td width="8%" align="center"><b>每日快讯</b></td>
								<td width="8%" align="center"><b>总分数</b></td>
								<td width="26%" ><b>&nbsp;</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td align="center">${SName}</td>
								<td align="center">${Coun}</td>
								<td align="center">${Coun}</td>
								<td><b>&nbsp;</b></td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		  
	</table>
</body>

</html>
