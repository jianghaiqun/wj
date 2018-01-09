
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员级别管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr>
			<td>
				<z:tab>
					<z:childtab id="MemberGrade" src="MemberGrade.jsp" selected="true" ><img src="../Icons/icon018a1.gif" /><b>会员级别管理</b></z:childtab>
					<z:childtab id="MemberPrivileges" src="MemberPrivileges.jsp" ><img src="../Icons/icon018a1.gif" /><b>会员特权管理</b></z:childtab>
				</z:tab>
			</td>
		</tr>
	</table>
</body>
</html>