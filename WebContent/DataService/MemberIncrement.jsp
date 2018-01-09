<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员增量报表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function doSearch(){

    if(!$V("BeginDate")){
    	Dialog.alert("请选择起始日期!");
		return;
    }

    if(!$V("EndDate")){
    	Dialog.alert("请选择结束日期!");
		return;
    }

    if($V("BeginDate") > $V("EndDate")){
    	Dialog.alert("开始日期不能大于结束日期!");
		return;
    }
	
	DataGrid.setParam("dg1","BeginDate",$V("BeginDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.setParam("dg1","QueryStatus",$V("QueryStatus"));
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员营销</td>
				</tr>
				<tr>
				<td>
				<z:init method="com.sinosoft.cms.dataservice.MemberAddedDetail.init">
				<table>
					<tr>
						<td>&nbsp;&nbsp;时间区间：</td>
						<td><input name="BeginDate" type="text" id="BeginDate" value="${createDate}"
							style="width: 120px" class="inputText" ztype="date"></td>
							
						<td>&nbsp;&nbsp;到 ：</td>
						<td><input name="EndDate" type="text" id="EndDate" value="${endCreateDate}"
							style="width: 120px" class="inputText" ztype="date"></td>
							
						<td>&nbsp;&nbsp;查询类型：</td>
						<td><select style="width:100px;" name="QueryStatus" id="QueryStatus">
							<option value="1">投保人</option>
							<option value="2">被保人</option>
						</select>
						</td>
               			<td>&nbsp;&nbsp;<input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
				</table>
				</z:init>
				</td>
				</tr>
				<tr>
				
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<div>
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.MemberIncrement.dg1DataBind" size="20" lazy="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="25%"><b>渠道</b></td>
								<td width="25%"><b>客户新增</b></td>
								<td width="25%"><b>当年客户合计</b></td>
								<td width="25%"><b>全部客户累计</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td title="${channelName}">${channelName}</td>
								<td title="${xz}">${xz}</td>
								<td title="${dn}">${dn}</td>
								<td title="${qn}">${qn}</td>
							</tr>
						</table>
					</z:datagrid></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>