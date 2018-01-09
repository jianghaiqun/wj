<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>全站销售统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr>
			<td>
				<z:tab>
					<z:childtab id="GlobalCount" src="GlobalCount.jsp" selected="true"><img src="../Icons/icon003a20.gif" /><b>主站业绩总量统计</b></z:childtab>
					<z:childtab id="RiskCount" src="RiskCount.jsp" ><img src="../Icons/icon003a20.gif" /><b>产品分类总保费及总件数统计</b></z:childtab>
					<z:childtab id="CompanyCount" src="CompanyCount.jsp" ><img src="../Icons/icon003a20.gif" /><b>保险公司总保费及总件数统计</b></z:childtab>	
					<z:childtab id="SingleProductMoney" src="SingleProductMoney.jsp" ><img src="../Icons/icon003a20.gif" /><b>单一产品总保费及总件数统计</b></z:childtab>
				</z:tab>
			</td>
		</tr>
	</table>
</body>
</html>
