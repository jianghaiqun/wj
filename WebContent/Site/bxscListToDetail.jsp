 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.data.DataTable,com.sinosoft.framework.data.QueryBuilder"%>
<html>
<head>
<meta name="robots" content="noindex, nofollow" />
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<% 
String productId = request.getParameter("productId"); 
String channel = request.getParameter("channel");
String LastUrl = request.getParameter("LastUrl");

String detailUrl = "";
String remark1 = "";
String remark2 = "";
if(channel== null || "".equals(channel)){
	if(null == session.getAttribute("channel") || "".equals(session.getAttribute("channel"))){
		channel = "JRJ";
		
	} else {
		channel = (String)session.getAttribute("channel");
		
	}
}

if( LastUrl== null || "".equals(LastUrl)){
	LastUrl = (String)session.getAttribute("LastUrl");
	
}

String coopSql = "select detailUrl,remark1,remark2 from ZDCooperationConfig  where channel=?";
DataTable dt1 = new QueryBuilder(coopSql,channel).executeDataTable();
if(dt1.getRowCount()>0){
	detailUrl = dt1.getString(0, 0);
    remark1 = dt1.getString(0, 1);
    remark2 = dt1.getString(0, 2);
}

boolean rFlag = false;
String globalflag = (String)session.getAttribute("globalflag");
if(("Y").equals(globalflag)){
	 detailUrl = remark2.replace("@@@", LastUrl );
     rFlag = true;
}else if(remark1 != null && !"".equals(remark1 ) && remark1.indexOf("@@@") != -1){
      detailUrl = remark1.replace("@@@", LastUrl );
      rFlag = true;
}

String url = detailUrl+"?product="+productId+"&channel="+channel;


if(null != LastUrl && !"".equals(LastUrl) && !rFlag){
    url += "&LastUrl=" + LastUrl;
}
%>
<script type="text/javascript">
	window.location.href="<%=url%>";
</script>
</body>
</html>
