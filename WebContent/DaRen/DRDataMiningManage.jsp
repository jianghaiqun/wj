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
var currentTab = "LinkCheck";
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
			<z:childtab id="LinkCheck" onClick="onTabChange('LinkCheck')"
				src="about:blank" selected="true">
				<img src="../Icons/icon002a1.gif" />
				<b>链接检查</b>
			</z:childtab>
			<z:childtab id="EmailExtract" onClick="onTabChange('EmailExtract')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>邮箱提取</b>
			</z:childtab>
			<z:childtab id="LinkInfoManage" onClick="onTabChange('LinkInfoManage')"
				src="about:blank">
				<img src="../Icons/icon002a1.gif" width="20" height="20" />
				<b>帖子链接管理</b>
			</z:childtab>
		</z:tab></td>
	</tr>
</table>
</body>
</html>