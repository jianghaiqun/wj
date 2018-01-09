<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.User"%>
<%@page import="com.finance.util.JedisCommonUtil"%>
<%@page import="java.util.UUID"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%
	QueryBuilder qb = new QueryBuilder("select value from zdconfig where type = 'B2BContextPath'");
	String b2bPath = qb.executeString();
	String user = User.getUserName();
	String authKey = user + "-" + UUID.randomUUID().toString();
	String authCode = StringUtil.md5Hex(user+"-"+authKey);
	JedisCommonUtil.setString(2, authCode , authKey);
%>
<script type="text/javascript">
    location.href = "<%=b2bPath%>/team/teamOrderForWj.jsp?extUser=admin&channelsn=wap_ht&authCode=<%=authCode%>";
</script>
</head>
<body>
</body>
</html>