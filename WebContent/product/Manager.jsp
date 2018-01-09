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
var currentTab = "MenuManager";
function onTabChange(tab){
	currentTab = tab;
	var url;
	url = Server.ContextPath+"weixin/"+currentTab+".jsp";
	if(Tab.getChildTab(tab).src.indexOf(url)<0){
		Tab.getChildTab(tab).src = url;
	}
}
Page.onLoad(function(){
	url = Server.ContextPath+"weixin/"+currentTab+".jsp";
	if(Tab.getChildTab(currentTab).src.indexOf(url)<0){
		Tab.getChildTab(currentTab).src = url;
	}
});
</script>
</head>
<body oncontextmenu="return false;">
<input type="hidden" id="CatalogID">
<input type="hidden" id="CatalogInnerCode">
<input type="hidden" id="Name">
<input type="hidden" id="CatalogType" value="1">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td><z:tab>
			<z:childtab id="MenuManager" onClick="onTabChange('MenuManager')"
				src="about:blank" selected="true">
				<img src="../Icons/icon002a1.gif" />
				<b>菜单管理</b>
			</z:childtab>
			<z:childtab id="ActivityManage" onClick="onTabChange('ActivityManage')"
				src="about:blank">
				<img src="../Icons/icon023a7.gif" width="20" height="20" />
				<b>活动页管理</b>
			</z:childtab>
			<%-- <z:childtab id="ModuleConfigure" onClick="onTabChange('ModuleConfigure')"
				src="about:blank">
				<img src="../Icons/icon018a1.gif" />
				<b>模板配置</b>
			</z:childtab>
			<z:childtab id="ProductTemplate" onClick="onTabChange('ProductTemplate')"
				src="about:blank">
				<img src="../Icons/icon003a1.gif" />
				<b>产品模板</b>
			</z:childtab> --%>
		</z:tab></td>
	</tr>
</table>
</body>
</html>
