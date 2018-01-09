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
var currentTab = "PropductBaseInfo";
function onTabChange(tab){ 
	currentTab = tab;
	var url;
	url = Server.ContextPath+"Document/"+currentTab+".jsp";
	if(Tab.getChildTab(currentTab).src.indexOf(url)<0){
		Tab.getChildTab(currentTab).src = url;
	}
}
Page.onLoad(function(){
	url = Server.ContextPath+"Document/"+currentTab+".jsp";
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
			<z:childtab id="PropductBaseInfo" onClick="onTabChange('PropductBaseInfo')"
				src="about:blank" selected="true">
				<img src="../Icons/icon002a1.gif" />
				<b>基础信息配置</b>
			</z:childtab>
			<z:childtab id="PropductInfoOne" onClick="onTabChange('PropductInfoOne')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" />
				<b>主站产品管理</b>
			</z:childtab>
			<z:childtab id="PropductInfoTwo" onClick="onTabChange('PropductInfoTwo')"
				src="about:blank">
				<img src="../Icons/icon023a7.gif" width="20" height="20" />
				<b>淘宝/去哪儿产品映射关系管理</b>
			</z:childtab>
			<z:childtab id="PropductCountry" onClick="onTabChange('PropductCountry')"
				src="about:blank">
				<img src="../Icons/icon023a7.gif" width="20" height="20" />
				<b>主站产品旅游目的地配置</b>
			</z:childtab>
			<z:childtab id="PropductOccu" onClick="onTabChange('PropductOccu')"
				src="about:blank">
				<img src="../Icons/icon023a7.gif" width="20" height="20" />
				<b>主站产品职业配置</b>
			</z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>
