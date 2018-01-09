<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广告预览</title>
</head>
<body>
<table width="100%" border="0" cellspacing="0" align="center" >
<%
String ID = request.getParameter("ID");
DataTable dt = new QueryBuilder("select * from zcadposition where ID = ?",Long.parseLong(ID)).executeDataTable();
String Str = "";
if(dt.getString(0,"PositionType").equals("code")){
	DataTable adDt = new QueryBuilder("select * from ZCAdvertisement where PositionID = ? and IsOpen = 'Y'",Long.parseLong(ID)).executeDataTable();
	Str += adDt.getString(0,"AdContent");
}else{
	String JSSrc = Config.getValue("StaticResourcePath") + "/" + dt.getString(0,"JSName");
	Str += "<script language='javascript' src='"+JSSrc+"'></script>";
}

%>
<tr align="center" valign="middle">
	<td align="center" valign="middle"><%=Str %></td>
</tr>
</table>
</body>
</html>
