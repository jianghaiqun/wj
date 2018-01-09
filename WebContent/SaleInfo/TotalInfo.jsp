<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>关键词设置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr>
			<td>
				<z:tab>
					<z:childtab id="insureTypeTotal" src="InsureTypeTotal.jsp" selected="true"><img src="../Icons/icon003a20.gif" /><b>各险种销售情况</b></z:childtab>
					<z:childtab id="insureCompanyTotal" src="InsureCompanyTotal.jsp" ><img src="../Icons/icon003a20.gif" /><b>保险公司销售情况</b></z:childtab>
					<z:childtab id="cityTotal" src="CityTotal.jsp" ><img src="../Icons/icon003a20.gif" /><b>各城市销售情况</b></z:childtab>	
					<z:childtab id="userTotal" src="UserTotal.jsp" ><img src="../Icons/icon003a20.gif" /><b>消费次数</b></z:childtab>
					<z:childtab id="userTotalByDay" src="UserTotalByDay.jsp" ><img src="../Icons/icon003a20.gif" /><b>会员回购率</b></z:childtab>
					<z:childtab id="PriceRange" src="PriceRange.jsp?ParentID=Member" ><img src="../Icons/icon003a20.gif" /><b>客单价区间</b></z:childtab>
				</z:tab>
			</td>
		</tr>
	</table>
</body>
</html>
