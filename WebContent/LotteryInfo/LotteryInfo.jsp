<!DOCTYPE HTML>
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中奖会员信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","WinnerName",$V("WinnerName"));
	DataGrid.setParam("dg1","WinnerEmail",$V("WinnerEmail"));
	DataGrid.setParam("dg1","Award",$V("Award"));
	DataGrid.setParam("dg1","WinnerMobil",$V("WinnerMobil"));
	DataGrid.setParam("dg1","Search","search");
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />中奖会员列表</td>
				</tr>
			<tr>
				<td>
				<table>
					<tr>
						<td>会员姓名：</td>
						<td><input name="WinnerName" type="text" id="WinnerName" value=""
							style="width: 90px" class="inputText"></td>
						<td>E-mail：</td>
						<td><input name="WinnerEmail" type="text" id="WinnerEmail" value=""
							style="width: 90px" class="inputText"></td>
						<td>所中奖项：</td>
						<td><z:select style="width:100px;"><select name="Award" id="Award" > 
						    <option value=""></option> 
							<option value="10积分">10积分</option> 
		                 	<option value="家财险10元代金券（100减10元）">家财险10元代金券（100减10元） </option>
		                 	<option value="意外险10元代金券（100减10元）">意外险10元代金券（100减10元） </option>
		                 	<option value="旅游险10元代金券（100减10元）">旅游险10元代金券（100减10元）</option> 
		                 	<option value="500积分">500积分 </option>
		                 	<option value="开心保抱枕">开心保抱枕 </option>
		                 	<option value="50元话费">50元话费</option> 
		                 	<option value="14-双11-100积分">14-双11-100积分 </option>
		                 	<option value="14-双11-1000积分">14-双11-1000积分</option>
		                 	<option value="14-双11-10000积分">14-双11-10000积分</option>
		                	</select></z:select>
						</td>
						<td>手机号码：</td>
						<td><input name="WinnerMobil" type="text" id="WinnerMobil" value=""
							style="width: 90px" class="inputText"></td>	
					</tr>
					<tr>
               			<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
            		</tr>
				</table>
				</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<div style="width:1300px;  overflow: auto;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.LotteryInfo.dg1DataBind" size="20">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="2" ztype="RowNo"><strong>序号</strong></td>
								<td width="2" ztype="selector" field="id">&nbsp;</td>
								<td width="5"><b>会员姓名</b></td>
								<td width="10"><b>Email</b></td>
								<td width="10"><b>手机号码</b></td>
								<td width="20"><b>所中奖项</b></td>
								<td width="20"><b>中奖时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${WinnerName}">${WinnerName}</td>
								<td title="${WinnerEmail}">${WinnerEmail}</td>
								<td title="${WinnerMobil}">${WinnerMobil}</td>
								<td title="${Award}">${Award}</td>
								<td title="${WinTime}">${WinTime}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</div>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>