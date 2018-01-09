<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>询单转化管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr>
			<td>
				<z:tab>
					<z:childtab id="ServiceInquiryImport" src="ServiceInquiryImport.jsp" selected="true" ><img src="../Icons/icon018a1.gif" /><b>询单信息上传</b></z:childtab>
					<z:childtab id="ServiceInquiryReport" src="ServiceInquiryReport.jsp" ><img src="../Icons/icon018a1.gif" /><b>询单转化报表</b></z:childtab>
					<z:childtab id="ServiceEfficiencyReport" src="ServiceEfficiencyReport.jsp" ><img src="../Icons/icon018a1.gif" /><b>客服效率汇总</b></z:childtab>
				</z:tab>
			</td>
		</tr>
	</table>
</body>
</html>