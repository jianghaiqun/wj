<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.sinosoft.framework.Config" %>
<html >
<head>
<title>车险</title>
<meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/carweixin_weixi.js"></script>
</head>
<body>
	 <iframe name="weizhang" src="http://m.cheshouye.com/api/weizhang/?t=ff9600" width="100%" height="500px;" frameborder="0" scrolling="no"></iframe>
</body>
</html>

