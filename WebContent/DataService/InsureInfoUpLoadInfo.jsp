<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/new_header.css"/>
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/shop/css/new_shops.css"/>

<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/skins/default.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.OrderImport.initDialog2">
<table width="100%" cellpadding="2" cellspacing="0" class="blockTable">
<tr height="30px"><td align="center">${Content}</td></tr>
<tr><td align="center"><textarea style="width:300px;height:80px" readonly/>${url}</textarea></td></tr>
</table>
</z:init>
</body>
</html>