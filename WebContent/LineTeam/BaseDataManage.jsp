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
var currentTab = "BranchManage";
function onTabChange(tab){
	currentTab = tab;
	var url;
	url = Server.ContextPath+"LineTeam/"+currentTab+".jsp";
	if(Tab.getChildTab(tab).src.indexOf(url)<0){
		Tab.getChildTab(tab).src = url;
	}
}
Page.onLoad(function(){
	url = Server.ContextPath+"LineTeam/"+currentTab+".jsp";
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
			<z:childtab id="BranchManage" onClick="onTabChange('BranchManage')" src="about:blank" selected="true">
				<img src="../Icons/icon002a1.gif" />
				<b>线下分支机构维护</b>
			</z:childtab>
			<z:childtab id="RiskTypeManage" onClick="onTabChange('RiskTypeManage')" src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>线下出单险种维护</b>
			</z:childtab>
			<z:childtab id="CompanyManage" onClick="onTabChange('CompanyManage')" src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>线下出单保险公司维护</b>
			</z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>