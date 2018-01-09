<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>

<z:init method="com.sinosoft.cms.memberreport.MemberReport.initDialog">
<script>
Page.onLoad(function(){
	doSearch();
});
function doSearch(){
	DataGrid.setParam("dg1", Constant.PageIndex, 0);
	DataGrid.setParam("dg1", "type", '${type}');
	DataGrid.setParam("dg1", "standardDate", '${standardDate}');
	DataGrid.setParam("dg1", "statisticsDate", '${statisticsDate}');
	// 会员渠道
	DataGrid.setParam("dg1", "channelsn", '${channelsn}');
	DataGrid.setParam("dg1", "Search","search");
	DataGrid.loadData("dg1");
}
</script>
</z:init>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员列表</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					
					<z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.MemberReport.dg1DataBind_dialog" size="20" lazy="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="60"><b>真实姓名</b></td>
								<td width="30"><b>性别</b></td>
								<td width="90"><b>生日</b></td>
								<td width="120"><b>地址</b></td>
								<td width="120"><b>E-mail</b></td>
								<td width="120"><b>证件号码</b></td>
								<td width="90"><b>手机号码</b></td>
								<td width="150"><b>R(最近一次购买时间间隔)</b></td>
								<td width="180"><b>M(累计金额)(含部分撤单保费)</b></td>
								<td width="100"><b>F(购买次数)</b></td>
								<td width="110"><b>K(会员等级基数)</b></td>
								<td width="80"><b>会员价值等级</b></td>
								<td width="100"><b>第一投保人姓名</b></td>
								<td width="90"><b>生日</b></td>
								<td width="30"><b>性别</b></td>
								<td width="100"><b>手机号码</b></td>
								<td width="120"><b>邮箱</b></td>
								<td width="120"><b>累计实际支付金额</b></td>
								<td width="150"><b>累计实际购买次数</b></td>
								<td width="60"><b>会员等级</b></td>
								<td width="60"><b>冻结积分</b></td>
								<td width="120"><b>积分商城消耗积分</b></td>
								<td width="120"><b>购买抵值消耗积分</b></td>
								<td width="60"><b>可用积分</b></td>
								<td width="120"><b>推荐注册获得积分</b></td>
								<td width="120"><b>推荐购买获得积分</b></td>
								<td width="70"><b>包含团单数</b></td>
								<td width="120"><b>最近登录时间</b></td>
								<td width="120"><b>会员创建时间</b></td>
								<td width="100"><b>会员来源</b></td>
							</tr>
							<tr onDblClick="view('${id}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${realName}">${realName}</td>
								<td title="${sex}">${sex}</td>
								<td title="${birthday}">${birthday}</td>
								<td title="${address}">${address}</td>
								<td title="${email}">${email}</td>
								<td title="${IDNO}">${IDNO}</td>
								<td title="${mobileNo}">${mobileNo}</td>
								<td title="${RValue}">${RValue}</td>
								<td title="${MValue}">${MValue}</td>
								<td title="${FValue}">${FValue}</td>
								<td title="${KValue}">${KValue}</td>
								<td title="${memberrank}">${memberrank}</td>
								<td title="${applicantName}">${applicantName}</td>
								<td title="${applicantBirthday}">${applicantBirthday}</td>
								<td title="${applicantSex}">${applicantSex}</td>
								<td title="${applicantMobile}">${applicantMobile}</td>
								<td title="${applicantMail}">${applicantMail}</td>
								<td title="${consumeAmount}">${consumeAmount}</td>
								<td title="${buyCount}">${buyCount}</td>
								<td title="${gradeName}">${gradeName}</td>
								<td title="${point}">${point}</td>
								<td title="${exchangeUsed}">${exchangeUsed}</td>
								<td title="${buyUsed}">${buyUsed}</td>
								<td title="${currentValidatePoint}">${currentValidatePoint}</td>
								<td title="${recommendRegPoints}">${recommendRegPoints}</td>
								<td title="${recommendBuyPoints}">${recommendBuyPoints}</td>
								<td title="${groupCount}">${groupCount}</td>
								<td title="${loginDate}">${loginDate}</td>
								<td title="${createdate}">${createdate}</td>
								<td title="${fromName}">${fromName}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
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