<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分整体信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	if( $V("startDate") == null || $V("startDate") == "" ){
		Dialog.alert("请填写统计开始时间！");
		return;
	}
	if($V("endDate") == null || $V("endDate") == "" ){
		Dialog.alert("请填写统计结束时间！");
		return;
	}
	// 会员渠道
	DataGrid.setParam("dg1","channelsn",$NV("contant"));
	
	DataGrid.setParam("dg1","Search","search");
	DataGrid.loadData("dg1");
}

function showchannel(){
	var check_flag=jQuery('#showchannel').is(':checked');
	if(check_flag==true){
		jQuery("#channeltree").show();
	}else{
		jQuery("#channeltree").hide();
	}
}
function showDialog(type, channelsn, count){
	if ('0' == count || '0.0' == count) {
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1020;
	diag.Height = 580;
	diag.Title = "订单信息";
	diag.URL = "MemberReport/PointsOrderDialog.jsp?type="+type+"&startDate="+$V("startDate")+"&endDate="+$V("endDate")+"&channelsn="+channelsn;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
</script>

</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td id="channeltree" style="display: none">
				<table width="50" border="0" cellspacing="0" cellpadding="6"
					class="blockTable">
					<tr>
						<td style="padding: 6px;"  width="50" class="blockTd"><z:tree id="tree1"
							style="height:440px;width:120px;"
							method="com.sinosoft.platform.Channel.treeDataBind"
							level="3" lazy="true" resizeable="true">
							<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
							<p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
								name="contant" id='contant_${ID}' value='${ChannelCode}'
								onClick="onCheck(this);"><label for="contant_${ID}"><span id="span_${ID}">${Name}</span></label></p>
						</z:tree></td>
					</tr>
				</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />积分整体情况</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.memberreport.MemberReport.init_points">
				<table>
					<tr>	
							<td>统计时间：</td>
						<td><input name="startDate" type="text" id="startDate" value="${startDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
							<td>到：</td>
						<td><input name="endDate" type="text" id="endDate" value="${endDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
						<td><input type="checkbox" onclick="showchannel()" id="showchannel" name="showchannel"/>订单渠道</td>
					</tr>
					<tr>
               			<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
            		</tr>
				</table>
				</z:init>
				</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.MemberReport.dg2DataBind" size="18" lazy="true" page="false">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo"><strong>序号</strong></td>
								<td width="80"><b>渠道</b></td>
								<td width="80"><b>购买发放</b></td>
								<td width="100"><b>订单评论</b></td>
								<td width="100"><b>会员生日月</b></td>
								<td width="100"><b>会员等级加成</b></td>
								<td width="80"><b>推荐好友购买</b></td>
								<td width="80"><b>购买抵值</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td title="${channelname}">${channelname}</td>
								<td title="${sums0}"><a href="#" onclick="showDialog('sums0','${ChannelCode}', ${sums0});">${sums0}</a></td>
								<td title="${sums1}"><a href="#" onclick="showDialog('sums1','${ChannelCode}', ${sums1});">${sums1}</a></td>
								<td title="${sums26}"><a href="#" onclick="showDialog('sums26','${ChannelCode}', ${sums26});">${sums26}</a></td>
								<td title="${sumsvip}"><a href="#" onclick="showDialog('sumsvip','${ChannelCode}', ${sumsvip});">${sumsvip}</a></td>
								<td title="${sums21}"><a href="#" onclick="showDialog('sums21','${ChannelCode}', ${sums21});">${sums21}</a></td>
								<td title="${sums2}"><a href="#" onclick="showDialog('sums2','${ChannelCode}', ${sums2});">${sums2}</a></td>
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