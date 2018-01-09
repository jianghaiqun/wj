<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员基本属性</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","standardDate",$V("standardDate"));
	DataGrid.setParam("dg1","statisticsDate",$V("statisticsDate"));
	if( $V("standardDate") == null || $V("standardDate") == "" ){
		Dialog.alert("请填写基准时间！");
		return;
	}
	if($V("statisticsDate") == null || $V("statisticsDate") == "" ){
		Dialog.alert("请填写统计时间段！");
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
function showMember(type, channelsn, count){
	if ('0' == count || '0.0' == count) {
		return;
	}
	if (channelsn == '' || channelsn == null) {
		channelsn = $NV("contant");
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1020;
	diag.Height = 580;
	diag.Title = "会员信息";
	diag.URL = "MemberReport/ShowMemberDialog.jsp?type="+type+"&standardDate="+$V("standardDate")+"&statisticsDate="+$V("statisticsDate")+"&channelsn="+channelsn;
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
							method="com.sinosoft.platform.Channel.treeDataBindForVip"
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
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员基本属性</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.memberreport.MemberReport.init">
				<table>
					<tr>	
							<td>基准时间：</td>
						<td><input name="standardDate" type="text" id="standardDate" value="${standardDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
							<td>统计时间：</td>
						<td><input name="statisticsDate" type="text" id="statisticsDate" value="${statisticsDate}"
							style="width: 90px" class="inputText"></td>
						<td><input type="checkbox" onclick="showchannel()" id="showchannel" name="showchannel"/>会员来源</td>
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
					
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.MemberReport.dg1DataBind" size="18" lazy="true" page="false">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="80"><b>会员渠道</b></td>
								<td width="40"><b>会员数</b></td>
								<td width="100"><b>旅游险贡献</b></td>
								<td width="100"><b>意外险贡献</b></td>
								<td width="100"><b>健康险贡献</b></td>
								<td width="80"><b>R≤30</b></td>
								<td width="80"><b>30＜R≤90</b></td>
								<td width="80"><b>90＜R≤150</b></td>
								<td width="80"><b>150＜R≤210</b></td>
								<td width="80"><b>210＜R≤365</b></td>
								<td width="80"><b>365＜R</b></td>
								<td width="60"><b>F=1</b></td>
								<td width="60"><b>F=2</b></td>
								<td width="60"><b>F=3</b></td>
								<td width="60"><b>4≤F≤5</b></td>
								<td width="60"><b>6≤F</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${ChannelName}">${ChannelName}</td>
								<td title="${membercount}"><a href="#" onclick="showMember('membercount','${ChannelCode}', ${membercount});">${membercount}</a></td>
								<td title="${am}"><a href="#" onclick="showMember('am','${ChannelCode}', ${am});">${am}</a></td>
								<td title="${bm}"><a href="#" onclick="showMember('bm','${ChannelCode}', ${bm});">${bm}</a></td>
								<td title="${dm}"><a href="#" onclick="showMember('dm','${ChannelCode}', ${dm});">${dm}</a></td>
								<td title="${rm30}"><a href="#" onclick="showMember('rm30','${ChannelCode}', ${rm30});">${rm30}</a></td>
								<td title="${rm90}"><a href="#" onclick="showMember('rm90','${ChannelCode}', ${rm90});">${rm90}</a></td>
								<td title="${rm150}"><a href="#" onclick="showMember('rm150','${ChannelCode}', ${rm150});">${rm150}</a></td>
								<td title="${rm210}"><a href="#" onclick="showMember('rm210','${ChannelCode}', ${rm210});">${rm210}</a></td>
								<td title="${rm365}"><a href="#" onclick="showMember('rm365','${ChannelCode}', ${rm365});">${rm365}</a></td>
								<td title="${rm366}"><a href="#" onclick="showMember('rm366','${ChannelCode}', ${rm366});">${rm366}</a></td>
								<td title="${f1}"><a href="#" onclick="showMember('f1','${ChannelCode}', ${f1});">${f1}</a></td>
								<td title="${f2}"><a href="#" onclick="showMember('f2','${ChannelCode}', ${f2});">${f2}</a></td>
								<td title="${f3}"><a href="#" onclick="showMember('f3','${ChannelCode}', ${f3});">${f3}</a></td>
								<td title="${f45}"><a href="#" onclick="showMember('f45','${ChannelCode}', ${f45});">${f45}</a></td>
								<td title="${f6}"><a href="#" onclick="showMember('f6','${ChannelCode}', ${f6});">${f6}</a></td>
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