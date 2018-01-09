<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var currentTab = "AuthorDetailInfo";
function onTabChange(tab){
	currentTab = tab;
	var url;
	url = Server.ContextPath+"DaRen/"+currentTab+".jsp";
	if(Tab.getChildTab(tab).src.indexOf(url)<0){
		Tab.getChildTab(tab).src = url;
	}
}
Page.onLoad(function(){
	url = Server.ContextPath+"DaRen/"+currentTab+".jsp";
	if(Tab.getChildTab(currentTab).src.indexOf(url)<0){
		Tab.getChildTab(currentTab).src = url;
	}
});
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td><z:tab>
			<z:childtab id="AuthorDetailInfo" onClick="onTabChange('AuthorDetailInfo')"
				src="about:blank" selected="true">
				<img src="../Icons/icon002a1.gif" />
				<b>作者详细录入</b>
			</z:childtab>
			<z:childtab id="TravelNotesStatistics" onClick="onTabChange('TravelNotesStatistics')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>游记总体录入</b>
			</z:childtab>
			<z:childtab id="MonthGoalsInput" onClick="onTabChange('MonthGoalsInput')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>月计划保费录入</b>
			</z:childtab>
			<z:childtab id="MonthGoalsStatistics" onClick="onTabChange('MonthGoalsStatistics')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>月计划完成统计</b>
			</z:childtab>
			<z:childtab id="SummaryStatistics" onClick="onTabChange('SummaryStatistics')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>汇总统计</b>
			</z:childtab>

			<z:childtab id="ContactedAuthor" onClick="onTabChange('ContactedAuthor')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>已联系作者查询</b>
			</z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>