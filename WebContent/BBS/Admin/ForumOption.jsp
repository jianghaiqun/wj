<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Spell.js"></script>
</head>
<body>
<z:init method="com.sinosoft.bbs.admin.ForumOption.initOption">
	<z:tab>
		<z:childtab id="ForumBasic" src="ForumBasic.jsp?ID=${ID}" selected="true"><img src="../../Icons/icon002a1.gif" /><b>基本设置</b></z:childtab>
		<z:childtab id="PostOption" src="PostOption.jsp?ID=${ID}"><img src="../../Icons/icon002a1.gif" /><b>帖子选项</b></z:childtab>
	</z:tab>
</z:init>
</body>
</html>
