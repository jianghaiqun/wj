<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发布统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body >
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td>
		 <z:tab>
			<z:childtab id="ConfigMemberLevel" src="MemberLevel.jsp" selected="true"><img src="../Icons/icon401a1.gif" /><b>配置会员等级</b></z:childtab>
			<z:childtab id="ConfigMemberField" src="MemberField.jsp" ><img src="../Icons/icon025a6.gif" /><b>配置会员扩展字段</b></z:childtab>
		 </z:tab>
	  </td>
   </tr>
</table>
</body>
</html>