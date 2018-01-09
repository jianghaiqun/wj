<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script src="../Framework/Main.js"></script>
	<script type="text/javascript" src="../template/common/js/jquery.js"></script>
	<script type="text/javascript">

	</script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<z:init method="com.wangjin.lineteam.LineTeamPolicyInfo.initDialog">
	<form id="form1">
		<input type="hidden" value="${id}" id="id" name="id">
		<input type="hidden" value="${agentPrice}" id="agentPrice" name="agentPrice">
		<table width="260" height="140" align="center" cellpadding="2" cellspacing="0">
			<tr>
				<td valign="top">
					<table>
						<tr>
							<td align="right" height="35">撤单时间：</td>
							<td><input name="cancelTime" id="cancelTime" value="" type="text" size="24" ztype="Date" verify="撤单时间|NotNull"/>
							</td>
						</tr>
						<tr>
							<td align="right" height="35">冲销金额：</td>
							<td><input id="prem" name="prem"  value="" type="text" size="24" verify="冲销金额|NotNull&&Number"/></td>
						</tr>
					</table>	     
				</td>
			</tr>
		</table>
	</form>
</z:init>
</body>
</html>
