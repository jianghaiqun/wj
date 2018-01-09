<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/Tabpage.js"></script>
<script>
var currentTab = "Config";
var grantType ;
var currentTreeItem,lastTreeItem;
Page.onLoad(function(){
});

Page.onClick(function(){
	var div = $("_DivContextMenu");
	if(div){
		$E.hide(div);
	}
});

function onTabChange(tab){
	currentTab = tab;
	var tabUrl = Server.ContextPath+"DataChannel/Deploy"+tab+".jsp";
	Tab.getChildTab(currentTab).src = tabUrl;
}

</script>
</head>

<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td><z:tab>
			<z:childtab id="Template0" selected="true"  src="IntegralSystem.jsp"><img src="../Icons/icon003a12.gif" /><b>积分设置</b></z:childtab>
			<z:childtab id="Template1" src="ProductIntegral.jsp"><img src="../Icons/icon003a12.gif" /><b>产品积分设置</b></z:childtab>
			<z:childtab id="Template2" src="OtherIntegral.jsp"><img src="../Icons/icon003a12.gif" /><b>其他积分设置</b></z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>
